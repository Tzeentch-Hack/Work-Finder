from typing import Annotated, List

from fastapi import Depends, FastAPI, File, UploadFile, Request, HTTPException
from fastapi.responses import FileResponse
import uvicorn
import authorization
import image_manager
import os

import models
import database
import coursera_parser
import resume_document_compiler
from resume_generator import ResumeBuilder
import user_recommendations


app = FastAPI()
app.include_router(authorization.router)


@app.get("/", tags=["Main API"])
def root():
    return {"message": "Work Finder"}


@app.get("/courses", response_model=list[models.CourseraCourse], tags=["Courses"])
def get_coursera_courses(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                         search_params):
    try:
        search_params = current_user.profile.preferred_specialization
        results = coursera_parser.get_parsed_courses(search_params)
        return results
    except Exception as ex:
        return {"message": str(ex)}


@app.post("/specializations", response_model=models.Specializations, tags=["Main API"])
def post_specialization_preferences(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)]):
    if current_user.has_questionary:
        list_of_preferences = current_user.profile.preferences.split(',')
        list_of_preferences.append(current_user.profile.labor_preference)
        result_list = user_recommendations.process_user_recommendations_fields(list_of_preferences)
        response = models.Specializations(specializations=result_list)
        database.update_user_info(current_user)
        return response
    else:
        raise HTTPException(status_code=404, detail="Profile does not exist")


@app.get("/vacancies", response_model=models.Vacancies, tags=["Main API"])
def get_vacancies(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)]):
    if current_user.has_questionary:
        list_of_preferences = current_user.profile.preferences.split(',')
        list_of_preferences.append(current_user.profile.labor_preference)
        current_user.profile.preferred_specialization = list_of_preferences[0]
        database.update_user_info(current_user)
        input_dict = {"age": current_user.profile.age,
                      "gender": current_user.profile.gender,
                      "education": current_user.profile.education,
                      "place_of_living": current_user.profile.residence,
                      "type_of_work": current_user.profile.labor_preference,
                      "work_schedule": current_user.profile.work_mode,
                      "form_of_work": current_user.profile.remote_or_local,
                      "preferences": current_user.profile.preferences,
                      "specialization": current_user.profile.preferred_specialization,
                      "email": current_user.profile.email,
                      "phone": current_user.profile.phone_number
                      }
        result = user_recommendations.process_user_recommendations_vacancies(input_dict)
        response_list = []
        for dict_vac in result:
            new_vacancy = models.Vacancy(name=dict_vac["name"],
                                         employment=dict_vac["employment"],
                                         specialization=dict_vac["specialization"],
                                         url=dict_vac["url"])
            response_list.append(new_vacancy)
        response = models.Vacancies(vacancies=response_list)
        return response
    else:
        raise HTTPException(status_code=404, detail="Profile does not exist")


@app.post("/generate_cv", response_model=models.UrlData, tags=["CV"])
async def generate_cv(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                pdf: bool,
                request: Request,
                file: UploadFile = File(...)):
    contents = file.file.read()
    image_data = image_manager.FileData(filename=file.filename, content=contents)
    image_path = image_manager.set_images_to_user(username=current_user.username, image_data=image_data)
    resume_builder = ResumeBuilder("gpt-3.5-turbo", 2000, 'ru')

    user_mock_info = {
        'age': current_user.profile.age,
        'gender': current_user.profile.gender,
        'education': current_user.profile.education,
        'place_of_living': current_user.profile.residence,
        'type_of_work': current_user.profile.labor_preference,
        'work_schedule': current_user.profile.work_mode,
        'form_of_work': current_user.profile.remote_or_local,
        'specialization': current_user.profile.preferred_specialization,
        'email': current_user.profile.email,
        'phone': current_user.profile.phone_number
    }
    import time
    start = time.time()
    resume_info = resume_builder.build_resume(**user_mock_info)
    end = time.time()
    print('end - start', end - start)

    if pdf:
        file_path = resume_document_compiler.build_pdf_resume(resume_info, image_path, current_user.username)
        result = os.path.join(os.path.join(f'{request.base_url}download/{file_path}'))
        response = models.UrlData(url=result)
    else:
        file_path = resume_document_compiler.build_docx_resume(resume_info, image_path, current_user.username)
        result = os.path.join(os.path.join(f'{request.base_url}download/{file_path}'))
        response = models.UrlData(url=result)
    return response


@app.post("/update_user_info", tags=["User management"])
async def add_profile(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                      profile: models.UserProfile):
    current_user.profile = profile
    current_user.has_questionary = True
    database.update_user_info(current_user)
    return {"message": "User profile has been uploaded"}


@app.get("/download/{path:path}",  tags=["Download"])
async def download_file(path: str):
    if os.path.exists(path):
        return FileResponse(path, filename=path)
    else:
        raise HTTPException(status_code=520, detail="File not found")


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)

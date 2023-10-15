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


app = FastAPI()
app.include_router(authorization.router)


@app.get("/", tags=["Main API"])
def root():
    return {"message": "Work Finder"}


@app.get("/courses", response_model=list[models.CourseraCourse], tags=["Courses"])
def get_coursera_courses(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                         search_params):
    try:
        results = coursera_parser.get_parsed_courses(search_params)
        return results
    except Exception as ex:
        return {"message": str(ex)}


@app.post("/specializations", )


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
        'age': 30,
        'gender': 'Male',
        'education': 'No education',
        'place_of_living': 'Tashkent',
        'type_of_work': 'Physical',
        'work_schedule': 'Full-time',
        'form_of_work': 'Local',
        'specialization': 'Cleaner',
        'email': 'example@email.com',
        'phone': '+1 123-456-7890'
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
    return {"User profile has been uploaded"}


@app.get("/download/{path:path}",  tags=["Download"])
async def download_file(path: str):
    if os.path.exists(path):
        return FileResponse(path, filename=path)
    else:
        raise HTTPException(status_code=520, detail="File not found")


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)

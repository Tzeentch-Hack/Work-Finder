from typing import Annotated, List

from fastapi import Depends, FastAPI, File, UploadFile, Request, HTTPException
from fastapi.responses import FileResponse
import uvicorn
import authorization
import os

import models
import coursera_parser

app = FastAPI()
app.include_router(authorization.router)


@app.get("/", tags=["Main API"])
def root():
    return {"message": "Work Finder"}


@app.get("/coursera_courses", response_model=list[models.CourseraCourse], tags=["Courses"])
def get_coursera_courses(current_user: Annotated[models.User, Depends(authorization.get_current_active_user)],
                         search_params):
    try:
        results = coursera_parser.get_parsed_courses(search_params)
        return results
    except Exception as ex:
        return {"message": str(ex)}


@app.get("/download/{path:path}",  tags=["Download"])
async def download_file(path: str):
    if os.path.exists(path):
        return FileResponse(path, filename=path)
    else:
        raise HTTPException(status_code=520, detail="File not found")


if __name__ == "__main__":
    uvicorn.run(app, host="0.0.0.0", port=8000)

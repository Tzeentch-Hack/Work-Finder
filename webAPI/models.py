from pydantic import BaseModel


class Token(BaseModel):
    access_token: str
    token_type: str


class TokenData(BaseModel):
    username: str | None = None


class User(BaseModel):
    username: str
    email: str | None = None
    full_name: str | None = None
    disabled: bool | None = None
    has_questionary: bool | None = None


class CourseraCourse(BaseModel):
    title: str
    preview_image_url : str
    sub_title: str
    href: str
    content: str | None = None


class UrlData(BaseModel):
    url: str


class UserInDB(User):
    hashed_password: str

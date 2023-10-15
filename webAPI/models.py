from pydantic import BaseModel


class Token(BaseModel):
    access_token: str
    token_type: str


class TokenData(BaseModel):
    username: str | None = None


class UserProfile(BaseModel):
    email: str | None = None
    phone_number: str | None = None
    age: str | None = None
    gender: str | None = None
    education: str | None = None
    residence: str | None = None
    labor_preference: str | None = None
    work_mode: str | None = None
    remote_or_local: str | None = None
    preferred_specialization: str | None = None


class User(BaseModel):
    username: str
    full_name: str | None = None
    avatar_url: str | None = None
    disabled: bool | None = None
    has_questionary: bool | None = None
    profile: UserProfile | None = None


class Specializations(BaseModel):



class UserInDB(User):
    hashed_password: str


class CourseraCourse(BaseModel):
    title: str
    preview_image_url : str
    sub_title: str
    href: str
    content: str | None = None


class UrlData(BaseModel):
    url: str

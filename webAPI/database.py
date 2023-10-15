import sqlalchemy.sql.sqltypes
from sqlalchemy import create_engine, ForeignKey
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from sqlalchemy import Column, Integer, String, BOOLEAN, JSON
from sqlalchemy.sql import update
import json

import models


SQLALCHEMY_DATABASE_URL = "sqlite:///./databases/users.db"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL, connect_args={"check_same_thread": False}
)

Base = sqlalchemy.orm.declarative_base()


class UserInDBSQL(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    username = Column(String, nullable=True)
    full_name = Column(String, nullable=True)
    avatar_url = Column(String, nullable=True)
    disabled = Column(BOOLEAN, nullable=True)
    hashed_password = Column(String, nullable=True)
    has_questionary = Column(BOOLEAN, nullable=True)
    profile_data = Column(JSON, nullable=True)


def update_user_info(user_model: models.User):
    updated_user_for_db = db.query(UserInDBSQL).filter(UserInDBSQL.username == user_model.username).first()
    updated_user_for_db.profile_data = user_model.profile.model_dump_json()
    updated_user_for_db.has_questionary = True
    #updated_user_for_db = UserInDBSQL(username=user_model.username,
    #                                  full_name=user_model.full_name,
    #                                  avatar_url=user_model.avatar_url,
    #                                  disabled=user_model.disabled,
    #                                  hashed_password=user_model.hashed_password,
    #                                  has_questionary=user_model.has_questionary,
    #                                  profile_data=user_model.profile.model_dump_json())
    db.add(updated_user_for_db)
    db.commit()


Base.metadata.create_all(bind=engine)
SessionLocal = sessionmaker(autoflush=False, bind=engine)
db = SessionLocal()

app = None

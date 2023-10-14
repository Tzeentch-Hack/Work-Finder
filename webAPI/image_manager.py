import os
from dataclasses import dataclass

image_path = "./Images"


@dataclass
class FileData:
    filename: str
    content: bytes


def set_images_to_user(username, image_data):
    if not os.path.exists(image_path):
        os.makedirs(image_path)
    user_path = os.path.join(image_path, username)
    if not os.path.exists(user_path):
        os.makedirs(user_path)
    try:
        image_file_path = os.path.join(user_path, image_data.filename)
        with open(image_file_path, 'wb') as f:
            f.write(image_data.content)
    except Exception("I\\O error") as e:
        raise e
    return image_file_path



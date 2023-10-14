from typing import Any

import textract
import tempfile


def extract_text_from_doc(file_path: str) -> Any | None:
    try:
        text = textract.process(file_path)
        return text.decode("utf-8")
    except Exception as e:
        print(f"Error occurred while extracting text: {e}")
        return None


def extract_text_from_doc_bytes(doc_bytes, file_type="docx"):
    if file_type.lower() == "doc":
        suffix = ".doc"
    else:
        suffix = ".docx"

    with tempfile.NamedTemporaryFile(suffix=suffix) as temp:
        temp.write(doc_bytes)
        temp.flush()
        text = textract.process(temp.name, encoding='utf-8')

    return text.decode("utf-8")







if __name__ == "__main__":
    # bytes_data = doc_to_bytes("/home/i_gore/PycharmProjects/resume_improver/data/raw/ID resume.doc")
    #
    # # file_path = "/home/i_gore/PycharmProjects/resume_improver/data/raw/ID resume.docx"
    # # print(extract_text_from_doc(file_path))
    # print('type: ', type(bytes_data))
    # bytes_string = extract_text_from_doc_bytes(bytes_data)
    # print(bytes_string)
    pass
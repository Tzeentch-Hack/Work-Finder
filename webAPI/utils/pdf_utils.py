import PyPDF2
import io


def extract_text_from_pdf(file_path):
    """
    This function extracts text from a given PDF file.

    Args:
        file_path (str): Path of the PDF file.

    Returns:
        text (str): Extracted text from the PDF file.
    """
    text = []

    with open(file_path, "rb") as pdf_file_obj:
        pdf_reader = PyPDF2.PdfReader(pdf_file_obj)
        for page in pdf_reader.pages:
            text.append(page.extract_text())

    return "".join(text)


def extract_text_from_pdf_bytes(pdf_bytes):
    """
    This function extracts text from a given PDF file in bytes format.

    Args:
        pdf_bytes (bytes): Bytes of the PDF file.

    Returns:
        text (str): Extracted text from the PDF file.
    """
    text = []

    pdf_file_obj = io.BytesIO(pdf_bytes)
    pdf_reader = PyPDF2.PdfReader(pdf_file_obj)
    for page in pdf_reader.pages:
        text.append(page.extract_text())

    return "".join(text)




if __name__ == "__main__":
    """
    The main function that is executed when the script is run directly.
    """
    print(extract_text_from_pdf("/home/i_gore/PycharmProjects/resume_improver/data/raw/Asadbek's Resume.pdf"))

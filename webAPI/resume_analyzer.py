from text_generator import TextGenerator

from utils import docx_utils, pdf_utils

class ResumeAnalyzer(TextGenerator):
    def __init__(self, model_engine, max_tokens):
        super().__init__(model_engine, max_tokens)
        self.resume_raw_information = ""
        self.system_prompt = ""
        self.prompt = ""

    def analyze_resume(self, document_bytes: bytes, document_type: str):
        if document_type == "pdf":
            document_text = pdf_utils.extract_text_from_pdf_bytes(document_bytes)
        elif document_type == "docx" or document_type == "doc":
            document_text = docx_utils.extract_text_from_doc_bytes(document_bytes)
        else:
            raise ValueError("Unsupported file extension")
        self.resume_raw_information = document_text
        self.prompt = "RESUME RAW INFORMATION:\n" + self.resume_raw_information
        analyzed_text = self.get_response(self.system_prompt, self.prompt)
        return analyzed_text


if __name__ == "__main__":
    analyzer = ResumeAnalyzer()
    path_docx = "/home/yonnie/PycharmProjects/resume.docx"
    path_pdf = "/home/yonnie/PycharmProjects/resume_1.pdf"
    with open("resume.txt", "w") as f:
        f.write(analyzer.analyze_resume(path_pdf))

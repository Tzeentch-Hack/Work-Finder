from src.models.text_generator import TextGenerator

from utils import docx_utils, pdf_utils
from utils.general_utils import read_prompt_from_file, write_prompt_to_file


class ResumeImprover(TextGenerator):
    def __init__(self, model_engine, max_tokens):
        super().__init__(model_engine, max_tokens)
        self.resume_raw_information = ""
        self.company_vacancy_information = ""
        self.system_prompt = read_prompt_from_file('resume_improver_system_prompt')
        self.prompt = read_prompt_from_file('resume_improver_prompt')

    def improve_resume(self, initial_resume, company_vacancy_combined):
        self.resume_raw_information = initial_resume
        self.company_vacancy_information = company_vacancy_combined
        self.prompt = (
                "INFORMATION ABOUT COMPANY AND VACANCY:\n"
                + self.resume_raw_information
                + "\n\nRESUME INFORMATION:\n"
                + self.resume_raw_information
        )
        analyzed_text = self.get_response(self.system_prompt, self.prompt)
        return analyzed_text


if __name__ == "__main__":
    improver = ResumeImprover()
    with open("improved_resume.txt", "w") as f:
        f.write(improver.improve_resume("resume.txt", "combined.txt"))

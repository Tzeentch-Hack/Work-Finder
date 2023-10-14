from text_generator import TextGenerator
import re
from translator import trans
class ResumeBuilder(TextGenerator):
    def __init__(self, model_engine, max_tokens, language='ru'):
        super().__init__(model_engine, max_tokens)
        self.system_prompt = "You perfectly personalize resume for any type of work"
        self.language = language  # The source language

    def _translate_to_english(self, text):
        if self.language != 'en':
            return trans.translate(text, source=self.language, destination='en')
        return text

    def _translate_from_english(self, text):
        if self.language != 'en':
            return trans.translate(text, source='en', destination=self.language)
        return text

    def _post_process(self, text, unwanted_headers):
        for header in unwanted_headers:
            # The regex ensures case-insensitive search and removes the entire line
            pattern = re.compile(rf"^{header}\s*.*$", re.IGNORECASE | re.MULTILINE)
            text = pattern.sub("", text).strip()
        return text

    def get_response(self, system_prompt, user_prompt):
        user_prompt_translated = self._translate_to_english(user_prompt)
        response = super().get_response(system_prompt, user_prompt_translated)
        return self._translate_from_english(response)

    def _post_process(self, text, unwanted_headers):
        for header in unwanted_headers:
            # The regex ensures case-insensitive search and removes the entire line
            pattern = re.compile(rf"^{header}\s*.*$", re.IGNORECASE | re.MULTILINE)
            text = pattern.sub("", text).strip()
        return text

    def generate_skills(self, specialization):
        self.prompt = f"""Provide a list of skills typically required for a {specialization} position. It is only part of the resume, so do not write too much'
        """
        skills = self.get_response(self.system_prompt, self.prompt)
        return self._post_process(skills, ["Skills", "Skills:"])

    def generate_profile(self, age, gender, education, place_of_living, type_of_work, work_schedule, form_of_work,
                         specialization):
        self.prompt = f"Based on the following details: Age: {age}, Gender: {gender}, Education: {education}, Place of living: {place_of_living}, Preferred type of work: {type_of_work}, Work schedule: {work_schedule}, Form of work: {form_of_work}, Specialization: {specialization}. Generate a professional profile. It is only part of the resume, so do not write too much."
        profile = self.get_response(self.system_prompt, self.prompt)
        return self._post_process(profile, ["Professional profile", "Profile", "Professional Profile:"])

    def generate_additional_info(self, specialization):
        self.prompt = f"Suggest additional information (like projects, associations, volunteer work) suitable for a {specialization} resume. if it is suitable."
        additional_info = self.get_response(self.system_prompt, self.prompt)
        return self._post_process(additional_info, ["Additional information", "Additional Information:"])

    def build_resume(self, age, gender, education, place_of_living, type_of_work, work_schedule, form_of_work,
                     specialization, email, phone):
        skills = self.generate_skills(specialization)
        profile = self.generate_profile(age, gender, education, place_of_living, type_of_work, work_schedule,
                                        form_of_work, specialization)
        additional_info = self.generate_additional_info(specialization)

        resume = {
            'skills': skills,
            'professional_profile': profile,
            'additional_information': additional_info,
            'contact_information': f"Email: {email}\nPhone: {phone}"
        }

        return resume








if __name__ == "__main__":
    # Sample usage:
    resume_builder = ResumeBuilder("gpt-3.5-turbo", 2000)
    user_info = {
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
    result = resume_builder.build_resume(**user_info)
    for section, content in result.items():
        print(f"=== {section} ===\n{content}\n")

import requests


class HHAPI:
    BASE_URL = 'https://api.hh.ru'
    HEADERS = {
        "User-Agent": "HH-User-Agent"
    }

    def __init__(self, area_id):
        self.area_id = area_id

    def _make_request(self, endpoint, params=None):
        response = requests.get(f"{self.BASE_URL}{endpoint}", headers=self.HEADERS, params=params)
        response.raise_for_status()
        return response.json()

    def fetch_specializations(self):
        data = self._make_request("/specializations")
        return {spec['id']: spec['name'] for prof_field in data for spec in prof_field['specializations']}

    def fetch_vacancies(self):
        params = {
            'area': self.area_id,
            'per_page': 100,
            'page': 0
        }
        fields_count = {}
        clean_vacancies = []
        while True:
            vacancies = self._make_request("/vacancies", params=params)
            print('got a request')
            for vacancy in vacancies['items']:
                clean_vacancy = {}
                clean_vacancy['name'] = vacancy['name']
                clean_vacancy['url'] = vacancy['alternate_url']
                clean_vacancy['specialization'] = vacancy['professional_roles'][0]['name']
                clean_vacancy['employment'] = vacancy['employment']['name'] # полная ли занятость
                clean_vacancies.append(clean_vacancy)
                for spec in vacancy['professional_roles']:
                    spec_name = spec['name']
                    fields_count[spec_name] = fields_count.get(spec_name, 0) + 1

            if params['page'] >= vacancies['pages'] - 1:
                break

            params['page'] += 1

        return fields_count, clean_vacancies

    @staticmethod
    def display_sorted_fields(fields_count):
        """Sort and display fields."""
        sorted_fields = sorted(fields_count.items(), key=lambda x: x[1], reverse=True)
        for field, count in sorted_fields:
            print(f"{field}: {count}")


if __name__ == "__main__":
    api = HHAPI(area_id=97)
    import time
    start = time.time()
    fields_count, cleaned_vacancies = api.fetch_vacancies()
    #api.display_sorted_fields(fields_count)
    print('cleaned_vacancies', cleaned_vacancies)
    end = time.time()
    print('time taken:', end - start)
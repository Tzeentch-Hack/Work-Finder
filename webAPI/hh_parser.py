import requests


class HHAPI:
    BASE_URL = 'https://api.hh.ru'
    HEADERS = {
        "User-Agent": "HH-User-Agent"
    }

    def __init__(self, area_id):
        self.area_id = area_id

    def _make_request(self, endpoint, params=None):
        """Make a request to HH API and return JSON data."""
        response = requests.get(f"{self.BASE_URL}{endpoint}", headers=self.HEADERS, params=params)
        response.raise_for_status()
        return response.json()

    def fetch_specializations(self):
        """Fetch all specializations."""
        data = self._make_request("/specializations")
        return {spec['id']: spec['name'] for prof_field in data for spec in prof_field['specializations']}

    def fetch_vacancies(self):
        """Fetch all vacancies and return professional roles."""
        params = {
            'area': self.area_id,
            'per_page': 100,
            'page': 0
        }
        fields_count = {}

        while True:
            vacancies = self._make_request("/vacancies", params=params)

            for vacancy in vacancies['items']:
                for spec in vacancy['professional_roles']:
                    spec_name = spec['name']
                    fields_count[spec_name] = fields_count.get(spec_name, 0) + 1

            if params['page'] >= vacancies['pages'] - 1:
                break

            params['page'] += 1

        return fields_count

    @staticmethod
    def display_sorted_fields(fields_count):
        """Sort and display fields."""
        sorted_fields = sorted(fields_count.items(), key=lambda x: x[1], reverse=True)
        for field, count in sorted_fields:
            print(f"{field}: {count}")


def main():
    area_id = 97
    api = HHAPI(area_id)
    api.fetch_specializations()
    fields_count = api.fetch_vacancies()
    api.display_sorted_fields(fields_count)


if __name__ == "__main__":
    main()

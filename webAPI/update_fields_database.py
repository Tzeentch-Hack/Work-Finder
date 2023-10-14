from bert_utils import SentenceTransformer
from hh_parser import HHAPI
import json

area_id = 97  # Uzbekistan
api = HHAPI(area_id)
init_model = SentenceTransformer()


def preprocess_work_fields(work_fields):
    field_names = list(work_fields.keys())

    embeddings = init_model(field_names)

    processed_data = []
    for idx, name in enumerate(field_names):
        processed_data.append({
            "name": name,
            "count": work_fields[name],
            "embedding": embeddings[idx].tolist()
        })

    return processed_data

def preprocess_vacancies(vacancies):
    names = [vacancy['name'] for vacancy in vacancies]
    specializations = [vacancy['specialization'] for vacancy in vacancies]
    employments = [vacancy['employment'] for vacancy in vacancies]

    names_embeddings = init_model(names)
    specializations_embeddings = init_model(specializations)
    employments_embeddings = init_model(employments)

    processed_vacancies = []

    for idx, vacancy in enumerate(vacancies):
        processed_vacancies.append({
            "url": vacancy["url"],
            "name": vacancy["name"],
            "name_embedding": names_embeddings[idx].tolist(),
            "specialization": vacancy["specialization"],
            "specialization_embedding": specializations_embeddings[idx].tolist(),
            "employment": vacancy["employment"],
            "employment_embedding": employments_embeddings[idx].tolist(),
        })

    return processed_vacancies





def save_to_json(data, filename):
    with open(filename, 'w', encoding='utf-8') as outfile:
        json.dump(data, outfile, ensure_ascii=False)


def load_from_json(filename):
    with open(filename, 'r', encoding='utf-8') as infile:
        data = json.load(infile)
    return data


def update():
    api.fetch_specializations()
    fields_count, cleaned_vacancies = api.fetch_vacancies()
    processed_fields = preprocess_work_fields(work_fields=fields_count)
    processed_vacancies = preprocess_vacancies(vacancies=cleaned_vacancies)
    return processed_fields, processed_vacancies


if __name__ == "__main__":
    import time

    start = time.time()
    processed_data, processed_vacancies = update()
    print('processed_data', processed_data)
    end = time.time()

    print('time taken', end - start)

    save_to_json(processed_data, 'processed_fields.json')
    save_to_json(processed_vacancies, 'processed_vacancies.json')

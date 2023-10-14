from bert_utils import SentenceTransformer
from hh_parser import HHAPI
import json
area_id = 97 # Uzbekistan
api = HHAPI(area_id)
init_model = SentenceTransformer()


def preprocess(work_fields):
    field_names = list(work_fields.keys())

    embeddings = init_model(field_names)

    processed_data = []
    for idx, name in enumerate(field_names):
        processed_data.append({
            "name": name,
            "count": work_fields[name],
            "embedding": embeddings[idx].tolist()  # Convert tensor to list
        })

    return processed_data

def save_to_json(data, filename):
    with open(filename, 'w', encoding='utf-8') as outfile:
        json.dump(data, outfile, ensure_ascii=False)

def load_from_json(filename):
    with open(filename, 'r', encoding='utf-8') as infile:
        data = json.load(infile)

    # Convert the embedding lists back to PyTorch tensors
    # for item in data:
    #     item['embedding'] = torch.tensor(item['embedding'])

    return data


def update():
    api.fetch_specializations()
    fields_count = api.fetch_vacancies()
    processed_data = preprocess(work_fields=fields_count)
    return processed_data


if __name__ == "__main__":
    import time

    start = time.time()
    processed_data = update()
    print('processed_data', processed_data)
    end = time.time()

    print('time taken', end - start)

    save_to_json(processed_data, 'processed_data.json')
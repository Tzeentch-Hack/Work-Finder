import torch
from update_fields_database import init_model, load_from_json

current_popular_fields = load_from_json('processed_fields.json')
current_popular_vacancies = load_from_json('processed_vacancies.json')


def cosine_similarity(a, b):
    dot_product = torch.dot(a, b)
    norm_a = torch.norm(a)
    norm_b = torch.norm(b)
    return dot_product / (norm_a * norm_b)


def process_user_recommendations_fields(user_keywords):
    user_embeddings = init_model(user_keywords)

    scores = {}
    for field in current_popular_fields:
        field_name = field['name']
        field_embedding = torch.tensor(field['embedding'])
        field_count = field['count']

        cumulative_similarity = 0.0
        for user_embedding in user_embeddings:
            cumulative_similarity += cosine_similarity(user_embedding, field_embedding)

        average_similarity = cumulative_similarity / len(user_keywords)

        scores[field_name] = (average_similarity, field_count)

    sorted_fields = sorted(scores.keys(), key=lambda x: (scores[x][0], scores[x][1]), reverse=True)

    return sorted_fields[:5]


def process_user_recommendations_vacancies(user_info):

    user_preferences_embeddings = init_model(user_info['preferences'])[0]
    user_specialization_embeddings = init_model(user_info['specialization'])[0]
    user_employment_embeddings = init_model(user_info['work_schedule'])[0]

    scores = {}
    for vacancy in current_popular_vacancies:
        vacancy_name = vacancy['name']
        vacancy_specialization = vacancy['specialization']
        vacancy_employment = vacancy['employment']
        vacancy_url = vacancy['url']

        vacancy_name_embedding = torch.tensor(vacancy['name_embedding'])
        vacancy_specialization_embedding = torch.tensor(vacancy['specialization_embedding'])
        vacancy_employment_embedding = torch.tensor(vacancy['employment_embedding'])

        name_similarity = cosine_similarity(user_preferences_embeddings, vacancy_name_embedding)
        specialization_similarity = cosine_similarity(user_specialization_embeddings, vacancy_specialization_embedding)
        employment_similarity = cosine_similarity(user_employment_embeddings, vacancy_employment_embedding)

        total_average_similarity = name_similarity + specialization_similarity + employment_similarity

        scores[vacancy_name] = (total_average_similarity, vacancy_name, vacancy_specialization, vacancy_employment, vacancy_url)

    sorted_vacancies = sorted(scores.keys(), key=lambda x: (scores[x][0], x), reverse=True)

    top_5_vacancies = [{
        'name': scores[vacancy][1],
        'specialization': scores[vacancy][2],
        'employment': scores[vacancy][3],
        'url': scores[vacancy][4]
    } for vacancy in sorted_vacancies[:5]]

    return top_5_vacancies





if __name__ == "__main__":
    # Example
    user_info = {
        'age': 30,
        'gender': 'Мужской',
        'education': 'Высшее',
        'place_of_living': 'Ташкент',
        'type_of_work': 'Умственный',
        'work_schedule': 'Полная занятость',
        'form_of_work': 'Удаленно',
        'preferences': 'Программист',
        'specialization': 'Программист, разработчик',
        'email': 'example@email.com',
        'phone': '+1 123-456-7890'
    }
    user_keywords_list = ["программист", "Исследователь"]
    print(process_user_recommendations_fields(user_keywords_list))
    print('===============================')
    import time
    start = time.time()
    print('vacancies:', process_user_recommendations_vacancies(user_info))
    end = time.time()
    print('time taken:', end - start)

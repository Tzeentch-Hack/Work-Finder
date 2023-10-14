import torch
from update_fields_database import init_model, load_from_json

current_popular_fields = load_from_json('processed_data.json')  # Load data from the database


def cosine_similarity(a, b):
    """Compute cosine similarity between two vectors."""
    dot_product = torch.dot(a, b)
    norm_a = torch.norm(a)
    norm_b = torch.norm(b)
    return dot_product / (norm_a * norm_b)


def process_user_recommendations(user_keywords):
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



if __name__ == "__main__":
    # Example
    user_keywords_list = ["программист", "Исследователь"]
    print(process_user_recommendations(user_keywords_list))

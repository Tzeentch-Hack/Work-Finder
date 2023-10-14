from transformers import AutoTokenizer, AutoModel
import torch
from torch.nn.functional import cosine_similarity
import time
# device = 'cuda' if torch.cuda.is_available() else 'cpu'
device = 'cpu'
class SentenceTransformer(object):

    def __init__(self):
        tokenizer = AutoTokenizer.from_pretrained("sberbank-ai/sbert_large_mt_nlu_ru")
        enc = AutoModel.from_pretrained("sberbank-ai/sbert_large_mt_nlu_ru")
        # tokenizer = AutoTokenizer.from_pretrained("sentence-transformers/paraphrase-multilingual-mpnet-base-v2")
        # enc = AutoModel.from_pretrained("sentence-transformers/paraphrase-multilingual-mpnet-base-v2")
        self.model = {
            'tokenizer': tokenizer,
            'encoder': enc,
        }

    def mean_pooling(self, model_output, attention_mask):
        token_embeddings = model_output[0]
        input_mask_expanded = attention_mask.unsqueeze(-1).expand(token_embeddings.size()).float()
        sum_embeddings = torch.sum(token_embeddings * input_mask_expanded, 1)
        sum_mask = torch.clamp(input_mask_expanded.sum(1), min=1e-9)
        return sum_embeddings / sum_mask

    def __call__(self, sentences):
        encoded_input = self.model['tokenizer'](sentences, padding=True, truncation=True, max_length=24,
                                                return_tensors='pt')
        encoded_input = encoded_input.to(device)
        with torch.no_grad():
            print('device:', device)
            model_output = self.model['encoder'].to(device)(**encoded_input)
        sentence_embeddings = self.mean_pooling(model_output, encoded_input['attention_mask'])
        return sentence_embeddings

    def compute_similarity(self, embeddings1, embeddings2):
        return cosine_similarity(embeddings1, embeddings2)




def get_sentence_similarity(model, requested_sentence, embedding_database):
    if isinstance(requested_sentence, str):
        requested_sentence = [requested_sentence]
    requested_embeddings = model(requested_sentence)
    return model.compute_similarity(requested_embeddings, embedding_database)


if __name__ == "__main__":
    current_model = SentenceTransformer()
    some_embeddings = current_model(["I hate apples"])
    start = time.time()
    print('similarities', get_sentence_similarity(current_model, ["I love apples"], some_embeddings))
    end = time.time()
    print('time:', end - start)
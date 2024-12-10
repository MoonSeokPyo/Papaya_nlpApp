import pandas as pd
import numpy as np
csv_path = "07_24_P_CSV/review_list.csv"
df = pd.read_csv(csv_path, usecols=['restaurant_id', 'review'], encoding='utf-8')

print(df.info())
print(df.head())

import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification
import torch.nn as nn



# 모델 정의
class KcELECTRAForRegression(nn.Module):
    def __init__(self):
        super(KcELECTRAForRegression, self).__init__()
        self.kcelectra = AutoModelForSequenceClassification.from_pretrained("beomi/KcELECTRA-base-v2022", num_labels=1)  # 회귀
        self.regression_head = nn.Linear(self.kcelectra.config.hidden_size, 1)

    def forward(self, input_ids, attention_mask, labels=None):
        outputs = self.kcelectra.electra(input_ids, attention_mask)
        logits = self.regression_head(outputs.last_hidden_state[:, 0, :])  # [CLS] 토큰 임베딩 사용
        loss = None
        if labels is not None:
            loss_fn = nn.MSELoss()
            loss = loss_fn(logits.squeeze(), labels)
        return logits.squeeze(), loss

try:
    tokenizer = AutoTokenizer.from_pretrained("beomi/KcELECTRA-base-v2022")
except Exception as e:
    raise RuntimeError(f"토크나자 로드 실패: {str(e)}")

model = KcELECTRAForRegression()

try:
    model.load_state_dict(torch.load("kcelectra_regression_model.pt", map_location=torch.device('cpu')))
except Exception as e:
    raise RuntimeError(f"모델 로드 실패: {str(e)}")

model.eval()
device = torch.device("cuda") if torch.cuda.is_available() else torch.device("cpu")
model.to(device)


# 테스트 함수 정의
def predict_review(review):
    # 리뷰 토크나이징
    inputs = tokenizer(review, padding="max_length", truncation=True, return_tensors="pt")
    inputs = {k: v.to(device) for k, v in inputs.items() if k in ['input_ids', 'attention_mask']}  # 필요한 입력만 전달

    # 예측 수행
    with torch.no_grad():
        logits, _ = model(**inputs)
        predicted_score = logits.item()  # 예측된 점수

        # 0.5 단위로 반올림하고 0.5에서 5.0 사이의 값으로 제한
        rounded_score = round(predicted_score * 2) / 2  # 0.5 단위 반올림
        limited_score = max(0.5, min(5.0, rounded_score))  # 0.5 ~ 5.0 사이로 제한

        return limited_score
    # 정상적으로 리뷰 분석이 안 된 경우
    return -1

# 'score' 열 추가
print('score 열 추가')
df['score'] = df['review'].apply(predict_review)
output_file = '07_24_P_CSV/predict_review.csv'
# 결과를 새 CSV 파일로 저장
df.to_csv(output_file, index=False)

print(f"새로운 CSV 파일이 '{output_file}'에 저장되었습니다.")
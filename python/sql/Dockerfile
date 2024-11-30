# 베이스 이미지 선택
FROM python:3.12-slim

# 필요한 패키지 설치
RUN apt-get update && apt-get install -y git-lfs

# Git LFS 초기화
RUN git lfs install

# 작업 디렉토리 설정
WORKDIR /python/sql

# Python 파일 및 필요 파일 복사
# COPY 파일명, 디렉토리 위치
COPY . .

WORKDIR /python/sql/07_24_P_CSV

# Git LFS 파일 풀기
RUN git lfs pull

WORKDIR /python/sql

# 필요한 패키지 설치 (requirements.txt가 있을 경우)
# COPY requirements.txt /app
RUN pip install -r requirements.txt

# 실행 명령
CMD ["python", "RestaurantTableCreate.py"]

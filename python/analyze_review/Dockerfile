# Dockerfile
# FROM python:3.9-slim
FROM continuumio/miniconda3:latest


#conda설치 필요 없음. 파이썬 환경설정

WORKDIR /python/analyze_review
 
COPY . .
# RUN pip install --upgrade pip
# RUN pip install -r requirements.txt

# RUN conda install python==3.8.20 -y
RUN conda install python==3.12.0 -y

# 필수 패키지 설치
RUN apt-get update && apt-get install -y curl build-essential

# Rust 설치
RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y

# Rust 환경 변수 추가
ENV PATH="/root/.cargo/bin:${PATH}"

RUN pip install transformers

# RUN for i in 1 2 3; do conda install pytorch==2.4.1 torchvision==0.19.1 torchaudio==2.4.1 pytorch-cuda=12.4 -c pytorch -c nvidia -y && break || sleep 10; done
RUN for i in 1 2 3; do conda install pytorch torchvision torchaudio pytorch-cuda=12.4 -c pytorch -c nvidia -y && break || sleep 10; done

# RUN conda install pytorch==2.4.1 torchvision==0.19.1 torchaudio==2.4.1 pytorch-cuda=12.4 -c pytorch -c nvidia -y
# RUN pip install torch==2.4.1 torchvision==0.19.1 torchaudio==2.4.1 --index-url https://download.pytorch.org/whl/cu124

# Rust 설치
# RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y && \
#     source $HOME/.cargo/env && \
#     echo 'source $HOME/.cargo/env' >> ~/.bashrc

# RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y && \
#     . $HOME/.cargo/env && \
#     echo '. $HOME/.cargo/env' >> ~/.bashrc

# RUN curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh -s -- -y && \
#     . $HOME/.cargo/env && \
#     echo '. $HOME/.cargo/env' >> ~/.bashrc

RUN pip install uvicorn[standard]
RUN pip install fastapi

CMD ["uvicorn", "analyze_review:app", "--host", "0.0.0.0", "--port", "8000"]

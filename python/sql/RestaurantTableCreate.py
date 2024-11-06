# MainSqlCreate
import pandas as pd
import numpy as np

#                       *** path check ***
csv_path_1 = "07_24_P_CSV/fulldata_07_24_01_P_관광식당.csv"
csv_path_2 = "07_24_P_CSV/fulldata_07_24_02_P_관광유흥음식점업.csv"
csv_path_3 = "07_24_P_CSV/fulldata_07_24_03_P_외국인전용유흥음식점업.csv"
csv_path_4 = "07_24_P_CSV/fulldata_07_24_04_P_일반음식점.csv"
csv_path_5 = "07_24_P_CSV/fulldata_07_24_05_P_휴게음식점.csv"
###################################################################
# 1~3
csv_data = pd.read_csv(csv_path_1, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'])
filtered_data = csv_data[csv_data['영업상태구분코드'] == 1].reset_index(drop=True)
filtered_data['업태구분명'] = '관광식당업'

# csv_data = pd.read_csv(csv_path_2, usecols=["영업상태구분코드", "사업장명", "소재지전체주소", "소재지우편번호", "도로명전체주소", "도로명우편번호", "소재지전화", "업태구분명", "좌표정보(x)", "좌표정보(y)"])
# filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]])

# csv_data = pd.read_csv(csv_path_3, usecols=["영업상태구분코드", "사업장명", "소재지전체주소", "소재지우편번호", "도로명전체주소", "도로명우편번호", "소재지전화", "업태구분명", "좌표정보(x)", "좌표정보(y)"])
# # filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]]).dropna().reset_index(drop=True)
# filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]]).reset_index(drop=True)

###################################################################
# 4, 5

# 필요한 열만 선택하면서 10,000행씩 나누어 읽기
data_iter = pd.read_csv(csv_path_4, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'], chunksize=10000)

# 영업상태구분코드	소재지전체주소	소재지우편번호	도로명전체주소	도로명우편번호	소재지전화		사업장명    업태구분명	좌표정보(x)	좌표정보(y)
# 01 영업

for chunk in data_iter:
    # 각 청크에서 조건에 맞는 행 필터링 (예: 열1이 특정값과 일치)
    filtered_chunk = chunk[chunk["영업상태구분코드"] == 1]
    
    # 필터링된 청크를 결과 데이터프레임에 추가
    filtered_data = pd.concat([filtered_data, filtered_chunk], ignore_index=True)


data_iter = pd.read_csv(csv_path_5, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'], chunksize=10000)
for chunk in data_iter:
    filtered_chunk = chunk[chunk["영업상태구분코드"] == 1]
    filtered_data = pd.concat([filtered_data, filtered_chunk], ignore_index=True)

filtered_data.drop(columns=['영업상태구분코드'], inplace=True)
###################################################################
# 데이터 정제
filtered_data.loc[367757, '사업장명'] = '블루펄호텔커피'
filtered_data.loc[367757, '업태구분명'] = '경양식'
filtered_data.loc[367757, '좌표정보(y)'] = np.nan

filtered_data.loc[498750, '사업장명'] = '길현동할머니떡볶이'
filtered_data.loc[498750, '업태구분명'] = '분식'
filtered_data.loc[498750, '좌표정보(y)'] = np.nan

filtered_data.drop(columns=['좌표정보(x)'], inplace=True)
filtered_data.drop(columns=['좌표정보(y)'], inplace=True)
filtered_data

filtered_data = filtered_data.rename(
    columns={
        "소재지전화": "location_phone",
        "소재지우편번호": "location_zipcode",
        "소재지전체주소": "location_address",
        "도로명전체주소": "road_address",
        "도로명우편번호": "road_zipcode",
        "사업장명": "business_name",
        "업태구분명": "business_type",
    }
)
###################################################################
# sql insert
from sqlalchemy import create_engine, text, String

#                                   *** sql server check ***
engine = create_engine("mysql+pymysql://root:1234@192.168.112.1:3307/testdb")

# filtered_data.to_sql("table_name", con=engine, if_exists="append", index=False)

# 데이터프레임을 테이블에 저장 (기존 테이블이 있으면 삭제 후 새로 생성)
filtered_data.to_sql(
    "restauranttable", 
    con=engine, 
    if_exists="replace", 
    index=False, 
    dtype={
        "location_phone": String(255),
        "location_zipcode": String(255),
        "location_address": String(255),
        "road_address": String(255),
        "road_zipcode": String(255),
        "business_name": String(255),
        "business_type": String(255),
    }
)

# 테이블에 `id` 열을 추가하여 자동 증가 설정
with engine.connect() as conn:
    conn.execute(
        text("""
        ALTER TABLE restauranttable 
        ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST
    """)
    )
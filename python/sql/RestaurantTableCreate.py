# MainSqlCreate
import pandas as pd
import numpy as np

################################################################### path check
# 출처
# https://www.localdata.go.kr/main.do
csv_path_1 = "07_24_P_CSV/fulldata_07_24_01_P_관광식당.csv"
csv_path_2 = "07_24_P_CSV/fulldata_07_24_02_P_관광유흥음식점업.csv"
csv_path_3 = "07_24_P_CSV/fulldata_07_24_03_P_외국인전용유흥음식점업.csv"
csv_path_4 = "07_24_P_CSV/fulldata_07_24_04_P_일반음식점.csv"
csv_path_5 = "07_24_P_CSV/fulldata_07_24_05_P_휴게음식점.csv"
# kakao api를 이용해 수집한 좌표코드
csv_path_6 = "07_24_P_CSV/restaurant_data.csv"
# 크롤링한 리뷰데이터에 ai 적용한 리뷰 점수
csv_path_7 = "07_24_P_CSV/predict_review.csv"
###################################################################
# 1~3
csv_data = pd.read_csv(csv_path_1, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'], encoding='utf-8')
filtered_data = csv_data[csv_data['영업상태구분코드'] == 1].reset_index(drop=True)
filtered_data['업태구분명'] = '관광식당업'

# csv_data = pd.read_csv(csv_path_2, usecols=["영업상태구분코드", "사업장명", "소재지전체주소", "소재지우편번호", "도로명전체주소", "도로명우편번호", "소재지전화", "업태구분명", "좌표정보(x)", "좌표정보(y)"], encoding='utf-8')
# filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]])

# csv_data = pd.read_csv(csv_path_3, usecols=["영업상태구분코드", "사업장명", "소재지전체주소", "소재지우편번호", "도로명전체주소", "도로명우편번호", "소재지전화", "업태구분명", "좌표정보(x)", "좌표정보(y)"], encoding='utf-8')
# # filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]]).dropna().reset_index(drop=True)
# filtered_data = pd.concat([filtered_data, csv_data[csv_data['영업상태구분코드'] == 1]]).reset_index(drop=True)

###################################################################
# 4, 5

# 필요한 열만 선택하면서 10,000행씩 나누어 읽기
data_iter = pd.read_csv(csv_path_4, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'], chunksize=10000, encoding='utf-8')

# 영업상태구분코드	소재지전체주소	소재지우편번호	도로명전체주소	도로명우편번호	소재지전화		사업장명    업태구분명	좌표정보(x)	좌표정보(y)
# 01 영업

for chunk in data_iter:
    # 각 청크에서 조건에 맞는 행 필터링 (예: 열1이 특정값과 일치)
    filtered_chunk = chunk[chunk["영업상태구분코드"] == 1]
    
    # 필터링된 청크를 결과 데이터프레임에 추가
    filtered_data = pd.concat([filtered_data, filtered_chunk], ignore_index=True)


data_iter = pd.read_csv(csv_path_5, usecols=['영업상태구분코드', '사업장명', '소재지전체주소', '소재지우편번호', '도로명전체주소', '도로명우편번호', '소재지전화', '업태구분명', '좌표정보(x)', '좌표정보(y)'], chunksize=10000, encoding='utf-8')
for chunk in data_iter:
    filtered_chunk = chunk[chunk["영업상태구분코드"] == 1]
    filtered_data = pd.concat([filtered_data, filtered_chunk], ignore_index=True)

filtered_data.drop(columns=['영업상태구분코드'], inplace=True)
###################################################################
# GPS coordinate
gpsDf = pd.read_csv(csv_path_6, usecols=['latitude', 'longitude'], encoding='utf-8')
# 비어있는 값 -1로 채우기
gpsDf.fillna(-1, inplace=True)
###################################################################
# review, score
reviewDf = pd.read_csv(csv_path_7, usecols=['restaurant_id', 'review', 'score'], encoding='utf-8')
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
# filtered_data

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
# average_score 데이터 생성
# filtered_data의 행 개수 계산
filtered_data_row_count = filtered_data.shape[0]

# restaurant_id의 전체 범위를 1부터 filtered_data의 열 개수까지 생성
all_ids = pd.DataFrame({"restaurant_id": range(1, filtered_data_row_count + 1)})

# 기존 데이터에서 리뷰 개수와 평균 점수 계산
scoreDf = reviewDf.groupby("restaurant_id").agg(
    review_count=("review", "count"),
    average_score=("score", "mean")
).reset_index()

# 모든 ID와 기존 데이터를 병합하여 누락된 ID를 채움
scoreDf = pd.merge(all_ids, scoreDf, on="restaurant_id", how="left")

# 결측값(NaN)을 리뷰 개수는 0, 평균 점수는 0.0으로 채움
scoreDf["review_count"] = scoreDf["review_count"].fillna(0).astype(int)
scoreDf["average_score"] = scoreDf["average_score"].fillna(0.0)
###################################################################
# data frame test
print('filtered_data test')
print(filtered_data)
print('gpsDf test')
print(gpsDf)
print('scoreDf test')
print(scoreDf)
print('reviewDf test')
print(reviewDf)
###################################################################
# sql insert
from sqlalchemy import create_engine, text, String, DECIMAL, Integer, Text
from sqlalchemy.exc import SQLAlchemyError

# MySQL 서버 연결
try:
    ################################################################### sql server check
    engine = create_engine("mysql+pymysql://root:1234@mysql:3306/papayadb?charset=utf8mb4")
    print("Connected to the database successfully.")
except SQLAlchemyError as e:
    print("Failed to connect to the database:", e)
    engine = None

if engine:
    # gpscoordinates 외래 키 제약 조건 삭제 (기존 외래 키 제거)
    try:
        with engine.connect() as conn:
            # 외래 키 존재 여부 확인
            fk_query = """
            SELECT CONSTRAINT_NAME
            FROM information_schema.REFERENTIAL_CONSTRAINTS
            WHERE TABLE_NAME = 'gpscoordinates' AND CONSTRAINT_SCHEMA = 'papayadb';
            """
            result = conn.execute(text(fk_query)).fetchone()

            if result:
                # 외래 키가 존재하면 삭제
                conn.execute(text("ALTER TABLE gpscoordinates DROP FOREIGN KEY fk_restaurant;"))
                print("Foreign key constraint 'fk_restaurant' dropped successfully.")
            else:
                print("Foreign key 'fk_restaurant' does not exist, skipping drop.")
    except SQLAlchemyError as e:
        print(f"Error occurred while dropping foreign key 'fk_restaurant': {e}")
    
    # reviews 외래 키 제약 조건 삭제 (기존 외래 키 제거)
    try:
        with engine.connect() as conn:
            # 외래 키 존재 여부 확인
            fk_query = """
            SELECT CONSTRAINT_NAME
            FROM information_schema.REFERENTIAL_CONSTRAINTS
            WHERE TABLE_NAME = 'reviews' AND CONSTRAINT_SCHEMA = 'papayadb';
            """
            result = conn.execute(text(fk_query)).fetchone()

            if result:
                # 외래 키가 존재하면 삭제
                conn.execute(text("ALTER TABLE reviews DROP FOREIGN KEY fk_reviews_restaurant;"))
                print("Foreign key constraint 'fk_reviews_restaurant' dropped successfully.")
            else:
                print("Foreign key 'fk_reviews_restaurant' does not exist, skipping drop.")
    except SQLAlchemyError as e:
        print(f"Error occurred while dropping foreign key 'fk_reviews_restaurant': {e}")
    
    # scores 외래 키 제약 조건 삭제 (기존 외래 키 제거)
    try:
        with engine.connect() as conn:
            # 외래 키 존재 여부 확인
            fk_query = """
            SELECT CONSTRAINT_NAME
            FROM information_schema.REFERENTIAL_CONSTRAINTS
            WHERE TABLE_NAME = 'scores' AND CONSTRAINT_SCHEMA = 'papayadb';
            """
            result = conn.execute(text(fk_query)).fetchone()

            if result:
                # 외래 키가 존재하면 삭제
                conn.execute(text("ALTER TABLE scores DROP FOREIGN KEY fk_scores_restaurant;"))
                print("Foreign key constraint 'fk_scores_restaurant' dropped successfully.")
            else:
                print("Foreign key 'fk_scores_restaurant' does not exist, skipping drop.")
    except SQLAlchemyError as e:
        print(f"Error occurred while dropping foreign key 'fk_scores_restaurant': {e}")
    
    # 데이터프레임을 테이블에 저장 (기존 테이블이 있으면 삭제 후 새로 생성)
    try:
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
                "id": Integer,  # 명시적으로 지정
            }
        )
        print("Data successfully inserted into the table.")
    except SQLAlchemyError as e:
        print("Error occurred while inserting data:", e)

    # 테이블에 `id` 열을 추가하여 자동 증가 설정
    try:
        with engine.connect() as conn:
            conn.execute(
                text("""
                ALTER TABLE restauranttable 
                ADD COLUMN id INT AUTO_INCREMENT PRIMARY KEY FIRST
                """)
            )
        print("Auto-incrementing 'id' column successfully added.")
    except SQLAlchemyError as e:
        print("Error occurred while adding 'id' column:", e)
    ###################################################################
    # restauranttable의 id와 gpsDf 데이터 병합
    # gpscoordinates 테이블 생성 및 데이터 저장
    try:
        gpsDf['restaurant_id'] = range(1, len(gpsDf) + 1)

        gpsDf.to_sql(
            "gpscoordinates",
            con=engine,
            if_exists="replace",
            index=False,
            dtype={
                "restaurant_id": Integer,
                "latitude": DECIMAL(10, 6),  # 소수점 이하 6자리 설정
                "longitude": DECIMAL(10, 6),  # 소수점 이하 6자리 설정
            }
        )
        print("gpscoordinates table created successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while creating gpscoordinates table: {e}")

    # gpscoordinates 테이블에 외래 키 설정
    try:
        with engine.connect() as conn:
            conn.execute(
                text("""
                ALTER TABLE gpscoordinates
                ADD CONSTRAINT fk_restaurant
                FOREIGN KEY (restaurant_id) REFERENCES restauranttable(id)
            """)
            )
        print("Foreign key 'fk_restaurant' added successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while adding the foreign key: {e}")
    ###################################################################
    # reviewDf 테이블 생성 및 데이터 저장
    try:
        reviewDf.to_sql(
            "reviews",
            con=engine,
            if_exists="replace",
            index=False,
            dtype={
                "restaurant_id": Integer,
                "review": Text,  # 긴 텍스트를 저장하기 위해 TEXT 타입 사용
                "score": DECIMAL(3, 1),  # 0.5~5.0 범위를 커버하도록 설정
            }
        )
        print("reviews table created successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while creating reviews table: {e}")

    # reviews 테이블에 외래 키 설정
    try:
        with engine.connect() as conn:
            conn.execute(
                text("""
                ALTER TABLE reviews
                ADD CONSTRAINT fk_reviews_restaurant
                FOREIGN KEY (restaurant_id) REFERENCES restauranttable(id)
            """)
            )
        print("Foreign key 'fk_reviews_restaurant' added successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while adding the foreign key to reviews table: {e}")
    ###################################################################
    # scoreDf 테이블 생성 및 데이터 저장
    try:
        scoreDf.to_sql(
            "scores",
            con=engine,
            if_exists="replace",
            index=False,
            dtype={
                "restaurant_id": Integer,
                "review_count": Integer,
                "average_score": DECIMAL(3, 2),  # 평균 점수 소수점 이하 2자리 설정
            }
        )
        print("scores table created successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while creating scores table: {e}")

    # scores 테이블에 외래 키 설정
    try:
        with engine.connect() as conn:
            conn.execute(
                text("""
                ALTER TABLE scores
                ADD CONSTRAINT fk_scores_restaurant
                FOREIGN KEY (restaurant_id) REFERENCES restauranttable(id)
            """)
            )
        print("Foreign key 'fk_scores_restaurant' added successfully.")
    except SQLAlchemyError as e:
        print(f"Error occurred while adding the foreign key to scores table: {e}")
###################################################################
    # db test
    print('db test')

    print('restauranttable test')
    # SQL 쿼리 실행하여 음식점 데이터 불러오기 (테이블에서 첫 10줄 가져오기)
    query = "SELECT * FROM restauranttable LIMIT 10;"
    df = pd.read_sql(query, engine)
    # 출력
    print(df)

    print('gpscoordinates test')
    # SQL 쿼리 실행하여 좌표 데이터 불러오기 (테이블에서 첫 10줄 가져오기)
    query = "SELECT * FROM gpscoordinates LIMIT 10;"
    df = pd.read_sql(query, engine)
    # 출력
    print(df)

    print('reviews test')
    # SQL 쿼리 실행하여 좌표 데이터 불러오기 (테이블에서 첫 10줄 가져오기)
    query = "SELECT * FROM reviews LIMIT 10;"
    df = pd.read_sql(query, engine)
    # 출력
    print(df)

    print('scores test')
    # SQL 쿼리 실행하여 좌표 데이터 불러오기 (테이블에서 첫 10줄 가져오기)
    query = "SELECT * FROM scores LIMIT 10;"
    df = pd.read_sql(query, engine)
    # 출력
    print(df)
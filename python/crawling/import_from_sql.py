import pymysql
import pandas as pd

# MySQL 데이터베이스에 연결
connection = pymysql.connect(
    host='localhost',      # 호스트 이름
    user='root',      # 사용자 이름
    password='root',  # 비밀번호
    database='restauranttable',  # 데이터베이스 이름
    charset='utf8'
)

# SQL 쿼리 작성
query = "SELECT * FROM restauranttable"  # 원하는 테이블명으로 변경

# 데이터 프레임에 쿼리 결과 로드
df = pd.read_sql(query, connection)

# CSV 파일로 저장
df.to_csv('./data/restaurant_list.csv', index=False, encoding='utf-8-sig')

# 연결 종료
connection.close()
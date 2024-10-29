import json
import time
from time import sleep
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

# CSV 파일 읽기
df = pd.read_csv('./data/restaurant_list.csv')  # 'restaurant_data.csv'는 CSV 파일 경로로 변경

# Selenium 설정
options = webdriver.ChromeOptions()
# options.add_argument('--headless')  # 브라우저 창을 띄우지 않음
options.add_argument('--no-sandbox')
options.add_argument('--disable-dev-shm-usage')

# WebDriver 실행
driver = webdriver.Chrome(options=options)

# 결과 저장을 위한 리스트
all_reviews = []

# 카카오맵 페이지로 이동
driver.get('https://map.kakao.com/')
time.sleep(2)  # 페이지 로딩 대기

for index, row in df.iterrows():
    # 검색어 생성
    restaurant_id = row['id'] # 결과물 저장을 위한 index
    place_name = row['placeName']
    address = row['address']
    roadAddress = row['roadAddress']
    search_query = f"{place_name}"
    
    # 카카오맵 검색 창에 검색어 입력
    search_area = driver.find_element(By.XPATH, '//*[@id="search.keyword.query"]') # 네이버지도 검색창
    search_area.clear()	
    search_area.send_keys(search_query)  # 검색어 전달
    search_area.send_keys(Keys.ENTER) # ENTER
    time.sleep(3)
    
    # 가게명과 주소가 일치하는 경우에 상세페이지로 이동 후 리뷰 크롤링
    
    # 같은이름의 음식점이 10개이상인 경우도 있으니 주소가 일치하는 경우 바로 상세 페이지 조회해야함
    count = driver.find_element(By.XPATH, '//*[@id="info.search.place.cnt"]').text
    print(count)
    # for i in count:
    #     first_address = driver.find_element(By.XPATH, '//*[@id="info.search.place.list"]/li[1]/div[5]/div[2]/p[1]').text
    #     first_roadaddress = driver.find_element(By.XPATH, '//*[@id="info.search.place.list"]/li/div[5]/div[2]/p[2]').text
    #     print(first_address, first_roadaddress)
    #     time.sleep(99999)
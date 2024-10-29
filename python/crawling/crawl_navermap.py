import time
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from bs4 import BeautifulSoup

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

# 네이버 지도 페이지로 이동
driver.get('https://map.naver.com/p')
time.sleep(2)  # 페이지 로딩 대기

for index, row in df.iterrows():
    # 검색어 생성
    restaurant_id = row['id']
    place_name = row['placeName']
    address = row['address']
    search_query = f"{place_name}"
    
    # 네이버 지도 검색 창에 검색어 입력
    search_area = driver.find_element(By.XPATH, '/html/body/div[1]/div/div[2]/div[1]/div/div[1]/div/div/div/input') # 네이버지도 검색창
    search_area.clear()	
    search_area.send_keys(search_query)  # 검색어 전달
    search_area.send_keys(Keys.ENTER) # ENTER
    time.sleep(3)
    
    review_tab = driver.find_element(By.CSS_SELECTOR, '#app-root > div > div > div > div.place_fixed_maintab > div > div > div > div > a:nth-child(5)')
    review_tab.click()
    time.sleep(99999)
    

# DataFrame으로 변환하여 CSV로 저장
review_df = pd.DataFrame(all_reviews)
review_df.to_csv('./data/reviews_output.csv', index=False, encoding='utf-8-sig')

# 브라우저 닫기
driver.quit()
import json
import time
from time import sleep
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
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

# 상세페이지 리뷰 추출 함수
def extract_review():
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    
    # 후기 목록 찾기
    review_lists = soup.select('.list_evaluation > li')
    
    count = 0
    rev = []
    # 리뷰가 있는 경우
    if len(review_lists) != 0:
        for review in review_lists:
            comment = review.select('.txt_comment > span')[0].text  # 리뷰

            print(review) # 리뷰 html 출력
            
            if len(comment) != 0:
                rev.append(comment)
                
    # 없으면 빈칸 추가
    else:
        rev.append(' ')
        
    
    # 다시 검색 탭으로 전환
    driver.close()
    driver.switch_to.window(driver.window_handles[0])
    # time.sleep(2)
    time.sleep(99999)
    
    return rev

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
    search_area = driver.find_element(By.XPATH, '//*[@id="search.keyword.query"]') # 카카오맵 검색창
    search_area.clear()	
    search_area.send_keys(search_query)  # 검색어 전달
    search_area.send_keys(Keys.ENTER) # ENTER
    time.sleep(2)
    

    count = driver.find_element(By.XPATH, '//*[@id="info.search.place.cnt"]').text
    # print(count)
    i = 1
    # 결과 저장을 위한 리스트
    temp = []
    all_reviews = []
    
    while True:
        try:
            
            result_address = driver.find_element(By.XPATH, f'//*[@id="info.search.place.list"]/li[{i}]/div[5]/div[2]/p[1]').text # i번째 지번주소
            # 2번째 지번주소 //*[@id="info.search.place.list"]/li[2]/div[5]/div[2]/p[1]
            result_roadaddress = driver.find_element(By.XPATH, f'//*[@id="info.search.place.list"]/li[{i}]/div[5]/div[2]/p[2]').text # i번째 도로명주소
            # 2번째 도로명 주소 //*[@id="info.search.place.list"]/li[2]/div[5]/div[2]/p[2]
            print(f'{restaurant_id}. {place_name} / {result_address} / {result_roadaddress} / 검색결과개수: {count}개')
            # print(type(result_address) ,type(address))
            
            # 검색결과와 데이터의 주소가 일치하는 경우에 상세페이지 클릭
            if (result_address.find(address) or result_roadaddress.find(roadAddress)): # 상세페이지 새창열기 후 리뷰 데이터프레임에 적재
                # //*[@id="info.search.place.list"]/li[1]/div[5]/div[4]/a[1] 상세보기 첫번째 xpath
                # //*[@id="info.search.place.list"]/li[2]/div[5]/div[4]/a[1] 상세보기 두번째 xpath
                driver.find_element(By.XPATH, f'//*[@id="info.search.place.list"]/li[{i}]/div[5]/div[4]/a[1]').send_keys(Keys.ENTER)
                driver.switch_to.window(driver.window_handles[-1])
                time.sleep(2)
                rev = extract_review()  # 리뷰 추출 함수 실행
                print(rev)
                break
            else: # 음식점이름 검색결과는 있지만 데이터와 주소가 일치하지 않는 경우 공백으로 삽입 (index, id, review(공백), star(공백) )
                
                break
        except: # 음식점이름 검색결과가 없을 경우
            break

        
        i = i + 1
        time.sleep(2)
        
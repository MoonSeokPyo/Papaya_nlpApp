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
# df = pd.read_csv('python/crawling/data/restaurant_list.csv')  # 'restaurant_data.csv'는 CSV 파일 경로로 변경  python\crawling\data\restaurant_list.csv
df = pd.read_csv('python/crawling/data/restaurant_list_test.csv')  # 음식점 30개 존재하는 test용 데이터
df_output = pd.DataFrame(columns=['restaurant_id', 'review', 'score'])

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

            # print(review) # 리뷰 html 출력
            
            if len(comment) != 0:
                rev.append(comment)
                
    # 없으면 빈칸 추가 / (2024-11-12)추가 없을경우 공백 추가하지않고 넘기기
    # else:
    #     rev.append('')
        
    
    # 다시 검색 탭으로 전환
    driver.close()
    driver.switch_to.window(driver.window_handles[0])
    time.sleep(2)
    # time.sleep(99999)
    
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
    i = 1 # xpath 탐색을 위한 변수
    # 결과 저장을 위한 리스트
    temp = []
    all_reviews = []
    
    while True: # 음식점 검색결과에서 주소와 일치하는 음식점 찾기
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
                if not (rev[0].isspace()): # 리뷰가 존재하는경우에만 데이터프레임에 적재 (리뷰가 공백이어도 적재하고 싶을경우 if문 제거)
                    data = {
                        'restaurant_id': [restaurant_id] * len(rev),  # 동일한 ID 반복
                        'review': rev,  # 리뷰 리스트
                        'score': '' * len(rev)  # 각 리뷰에 맞는 평점 리스트
                    }
                    df_row = pd.DataFrame(data)
                    # print(df_row)
                    df_output = pd.concat([df_output,df_row])
                    print('review appended')
                break
            else: # 음식점이름 검색결과는 있지만 데이터와 주소가 일치하지 않는 경우 공백으로 삽입 (index, id, review(공백), star(공백) )
                
                break
        except: # 중간에 이슈생길경우 진행상황까지 저장해서 내보내기 review_list_temp.csv
            df_output.to_csv('python/crawling/data/review_list_temp.csv', index=False)

        
        i = i + 1 
        time.sleep(2)
        
print(df_output)
# 크롤링한 모든 리뷰 review_list.csv 에 저장
df_output.to_csv('python/crawling/data/review_list.csv', index=False)
print('saved to review_list.csv')

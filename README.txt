NLP를 이용한 음식점 추천 어플리케이션 만들기
PAPAYA

![시스템 구성도](https://github.com/user-attachments/assets/295baf18-f7bc-4362-8e32-a86c4cb83cfe)

-------------------------------------------------------
vscode에서 py 파일을 windows 에서 실행할 경우 성능저하가 발생할 수 있다고 한다.
wsl에서 파일 관리후 결과물 위주로 복사 저장하는 방식으로 하겠다.

github에서 수정을 하면(ex README)
local에서 수정하고 sourcetree로 push할 때 에러 발생함
해결 방법이 있긴 하겠으나 github는 건드리지 않고 sourcetree 만 이용할 것	
-------------------------------------------------------
sql file encoding: utf-8
WGS84 경/위도	EPSG:4326

Main Table (restraurant table)
[id, '소재지전화', '소재지우편번호', '소재지전체주소', '도로명전체주소', '도로명우편번호', '사업장명', '업태구분명']
'좌표정보(x)', '좌표정보(y) ' 제외했다.

mysql table 추출
mysqldump -u 계정이름 -p --default-character-set=utf8mb4 db이름 table이름 > 저장할table파일이름.sql

mysql table 삽입
mysql -u 계정이름 -p db이름 < 저장한table파일이름.sql

100MB 넘는 파일 업로드 할 경우
git lfs install
git lfs track "*.파일 확장자"
-------------------------------------------------------
이슈란에 각자 맡은 기능 서술
라벨붙이기

[Restaurant Sql 삽입시 주의사항]
1. csv 파일 path 확인
2. sql 서버 확인(host 192.168.112.1:3307로 되어 있습니다.)
	mysql+pymysql://사용자명:비밀번호@호스트:포트/스키마

------------------------------------------------------
#OAUTH2
#google
spring.security.oauth2.client.registration.google.client-id={clientID}
spring.security.oauth2.client.registration.google.client-authentication-method=client_secret_basic
spring.security.oauth2.client.registration.google.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.google.scope=profile, email
spring.security.oauth2.client.registration.google.redirect-uri={baseUrl}/login/oauth2/code/{registrationId}

#naver
spring.security.oauth2.client.registration.naver.client-id={clientId}
spring.security.oauth2.client.registration.naver.client-secret={secret}
spring.security.oauth2.client.registration.naver.redirect-uri=http://localhost:8080/login/oauth2/code/naver
spring.security.oauth2.client.registration.naver.authorization-grant-type=authorization_code
spring.security.oauth2.client.registration.naver.scope=name,email
spring.security.oauth2.client.provider.naver.authorization-uri=https://nid.naver.com/oauth2.0/authorize
spring.security.oauth2.client.provider.naver.token-uri=https://nid.naver.com/oauth2.0/token
spring.security.oauth2.client.provider.naver.user-info-uri=https://openapi.naver.com/v1/nid/me
spring.security.oauth2.client.provider.naver.user-name-attribute=response
------------------------------------------------------
lfs로 올린 대용량 파일은 'git lfs pull' 로 pull 해야지 다운 된다.
< 현재 LFS 사용 X >
------------------------------------------------------
Google Drive 링크
https://drive.google.com/drive/folders/1q4H2f1HI5A0JkM15lDoTadeVe3G0zkyW?usp=drive_link
------------------------------------------------------
용량이 커서 올릴 수가 없기 때문에 google drive에서 다운하기
kcelectra_regression_model.pt
path: Papaya_nlpApp/python/analyze_review/kcelectra_regression_model.pt

07_24_P_CSV
path: Papaya_nlpApp/python/sql/07_24_P_CSV

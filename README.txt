NLP를 이용한 음식점 추천 어플리케이션 만들기
PAPAYA
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
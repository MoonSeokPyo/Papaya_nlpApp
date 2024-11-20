console.log("JavaScript 파일이 로드되었습니다.");

// 페이지 스크롤 시 버튼 표시
window.onscroll = function() {
    scrollFunction();
};

function scrollFunction() {
    const topButton = document.getElementById("topButton");
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        topButton.style.display = "block"; // 스크롤이 20px 이상 내려가면 버튼 표시
    } else {
        topButton.style.display = "none"; // 그렇지 않으면 버튼 숨김
    }
}

// 버튼 클릭 시 페이지 상단으로 이동
document.getElementById("topButton").onclick = function() {
    document.body.scrollTop = 0; // 사파리용
    document.documentElement.scrollTop = 0; // 크롬, 파이어폭스, IE, 오페라용
};

//메뉴 아이콘 js
document.getElementById('menuIcon').addEventListener('click', function() {
    const menu = document.getElementById('dropdownMenu');
    menu.style.display = menu.style.display === 'block' ? 'none' : 'block';
});

// 클릭 시 메뉴를 닫는 기능 추가 (화면 다른 곳 클릭 시 닫히게 함)
document.addEventListener('click', function(event) {
    const menu = document.getElementById('dropdownMenu');
    const icon = document.getElementById('menuIcon');
    if (!icon.contains(event.target) && !menu.contains(event.target)) {
        menu.style.display = 'none';
    }
});

//div 링크 설정 href X
function navigateToPage(url) {
    window.location.href = url;
}

//배너 이미지 슬라이드 
document.addEventListener('DOMContentLoaded', () => {
    const images = document.querySelectorAll('.banner');
    let currentImageIndex = 0;

    // 초기화: 첫 번째 이미지를 활성화
    images[currentImageIndex].classList.add('active');

    function showNextImage() {
        // 현재 이미지 숨기기
        images[currentImageIndex].classList.remove('active');

        // 다음 이미지로 인덱스 업데이트, 배열 길이에 맞게 순환
        currentImageIndex = (currentImageIndex + 1) % images.length;

        // 다음 이미지 활성화
        images[currentImageIndex].classList.add('active');
    }

    // 1.3초마다 이미지 변경
    setInterval(showNextImage, 1300);
});

// 제주로 이동
function navigateToJeju() {
    window.location.href = "jeju.html";
}

// 서울로 이동
function navigateToSeoul() {
    window.location.href = "seoul3.html";
}

//메인페이지 드롭다운 왼쪽 메뉴바
function toggleSidebarDropdown(menuId) {
    // 모든 드롭다운을 숨김
    document.querySelectorAll('.dropdown-content').forEach(dropdown => {
        if (dropdown.id !== menuId) {
            dropdown.style.display = "none";
        }
    });

    // 클릭된 드롭다운을 토글
    const dropdown = document.getElementById(menuId);
    dropdown.style.display = dropdown.style.display === "block" ? "none" : "block";

     // 모든 드롭다운 닫기
     document.querySelectorAll('.dropdown-content').forEach((dropdown) => {
      if (dropdown.id !== menuId) {
        dropdown.style.display = 'none';
      }
    });
  
    
}
  


//가운데 검색 버튼 드롭 다운 메뉴 설정
// 드롭다운 메뉴 표시/숨김
// 드롭다운 메뉴 표시/숨김
document.addEventListener('DOMContentLoaded', () => {
  console.log("JavaScript 파일이 로드되었습니다.");

  // 드롭다운 표시/숨김 토글
  function toggleSearchDropdown() {
      console.log("toggleSearchDropdown 함수 실행");
      const dropdown = document.getElementById('dropdown-menu');
      if (!dropdown) {
          console.error("dropdown-menu 요소를 찾을 수 없습니다!");
          return;
      }
      dropdown.classList.toggle('hidden');
      console.log("toggleSearchDropdown: 현재 hidden 상태인가?", dropdown.classList.contains('hidden'));
  }

  // 지역 선택
  function selectRegion(region) {
      const selectedRegionElement = document.getElementById('selected-region');
      selectedRegionElement.textContent = region;
      document.getElementById('dropdown-menu').classList.add('hidden');
  }

  // 검색 버튼 클릭
  function searchRegion() {
      const selectedRegion = document.getElementById('selected-region').textContent;

      if (selectedRegion === '지역을 선택해주세요') {
          alert('지역을 선택해주세요!');
          return;
      }

      const regionUrlMap = {
          '강남구': 'map-gangnam.html',
          '종로구': 'map-jongno.html',
          '서초구': 'map-seocho.html',
          '마포구': 'map-mapogu.html',
          '강동구': 'map-gangdong.html',
      };

      const targetUrl = regionUrlMap[selectedRegion] || '/';
      window.location.href = targetUrl;
  }

  // 이벤트 리스너 등록
  document.getElementById('search-dropdown-trigger').addEventListener('click', toggleSearchDropdown);
  document.getElementById('search-button').addEventListener('click', searchRegion);
});







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

<<<<<<< HEAD

//메뉴바 클릭시 보이게 설정
function toggleMenu() {
    var menu = document.getElementById("dropdownMenu");
    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
}
=======
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
>>>>>>> addfrontpage

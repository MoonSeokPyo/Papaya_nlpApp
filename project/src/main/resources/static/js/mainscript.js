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


//메뉴바 클릭시 보이게 설정
function toggleMenu() {
    var menu = document.getElementById("dropdownMenu");
    if (menu.style.display === "block") {
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
    }
}

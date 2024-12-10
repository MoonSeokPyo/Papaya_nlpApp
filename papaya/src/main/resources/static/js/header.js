document.addEventListener('DOMContentLoaded', function() {
    const sidebarToggle = document.getElementById('sidebarToggle');
    const sidebar = document.getElementById('sidebar');
    const sidebarClose = document.getElementById('sidebarClose');
    const loginButton = document.getElementById('loginButton');
    const logoutButton = document.getElementById('logoutButton');
    const sidebarLoginContent = document.getElementById('sidebarLoginContent');
    const sidebarUserContent = document.getElementById('sidebarUserContent');

    sidebarToggle.addEventListener('click', function() {
        sidebar.classList.add('active');
    });

    sidebarClose.addEventListener('click', function() {
        sidebar.classList.remove('active');
    });

    loginButton.addEventListener('click', function() {
        // 로그인 로직을 여기에 구현하세요
        console.log('로그인 버튼이 클릭되었습니다.');
        sidebarLoginContent.style.display = 'none';
        sidebarUserContent.style.display = 'block';
    });

    logoutButton.addEventListener('click', function() {
        // 로그아웃 로직을 여기에 구현하세요
        console.log('로그아웃 버튼이 클릭되었습니다.');
        sidebarLoginContent.style.display = 'block';
        sidebarUserContent.style.display = 'none';
    });
});





// /**
//  * 사이드 메뉴를 열고 닫는 함수
//  */
// function toggleMenu() {
//     const sideMenu = document.getElementById("sideMenu"); // 사이드 메뉴 DOM 요소를 가져옴
//     sideMenu.classList.toggle("open"); // 'open' 클래스를 추가하거나 제거하여 메뉴 표시 상태 변경
// }
//
// /**
//  * 레이아웃을 동적으로 조정하는 함수
//  * - 헤더 높이, 로고 크기, 검색창의 위치와 크기를 화면 크기에 비례해 설정
//  */
// function adjustLayout() {
//     const header = document.querySelector('header'); // 헤더 요소 선택
//     const body = document.querySelector('body');    // body 요소 선택
//     const logo = document.querySelector('.logo img'); // 로고 이미지 요소 선택
//     const searchBar = document.querySelector('.search-bar'); // 검색창 컨테이너 선택
//     const searchInput = document.querySelector('.search-bar input'); // 검색창 입력 필드 선택
//     const searchButton = document.querySelector('.search-bar button'); // 검색 버튼 선택
//     const menuButton = document.querySelector('.menu-btn');
//
//     // 화면 높이에 비례해 헤더 높이를 설정 (예: 화면 높이의 10%)
//     const headerHeight = window.innerHeight * 0.1; // 헤더 높이는 화면 높이의 10%로 설정
//     header.style.height = `${headerHeight}px`; // 동적으로 헤더 높이 지정
//
//     // body의 상단 여백(padding)을 헤더 높이로 설정
//     body.style.paddingTop = `${headerHeight}px`;
//
//     // 로고 크기를 헤더 높이에 비례해 설정
//     const logoHeight = headerHeight * 0.8; // 로고 높이는 헤더 높이의 80%로 설정
//     logo.style.height = `${logoHeight}px`; // 로고 높이 동적 설정
//     logo.style.width = 'auto'; // 원본 비율을 유지하며 너비 자동 조정
//
//     // 검색창 위치와 크기를 화면 크기에 비례해 설정
//     const headerWidth = window.innerWidth; // 화면 가로 길이 가져오기
//     const searchWidth = headerWidth / 3; // 검색창 너비는 화면 가로의 1/3
//     searchBar.style.width = `${searchWidth}px`; // 검색창 컨테이너의 너비 설정
//     searchBar.style.marginLeft = `${headerWidth / 3}px`; // 검색창을 화면 가운데로 위치시키기
//
//     // 검색창 내부 요소 조정
//     // searchInput.style.width = `calc(100% - ${headerHeight}px)`; // 검색창 입력 필드 너비 조정 (버튼 크기 제외)
//     // searchInput.style.paddingRight = `${headerHeight / 3}px`; // 검색 버튼과 간격 확보
//     // searchButton.style.width = `${headerHeight / 2}px`; // 버튼 너비는 헤더 높이에 비례
//     // searchButton.style.height = `${headerHeight * 0.8}px`; // 버튼 높이 조정
//     // searchInput.style.height = `${headerHeight * 0.7 }px`;
//     // searchInput.style.paddingRight = `${searchWidth * (1 / 10) }px`; // 검색 버튼과 간격 확보
//     searchInput.style.width = `${searchWidth * (6 / 10) }px`; // 검색창 입력 필드 너비 조정 (버튼 크기 제외)
//     searchButton.style.width = `${searchWidth * 0.15 }px`; // 버튼 너비는 헤더 높이에 비례
//     searchButton.style.height = `${headerHeight * 0.6}px`; // 버튼 높이 조정
//
//     // 사이드 버튼 조정
//     menuButton.style.height = `${headerHeight * 0.8}px`;
//     menuButton.style.width = `${headerHeight * 0.8}px`;
//     menuButton.style.right = `${headerHeight * 0.5}px`;
// }
//
// // 페이지 로드 시 레이아웃 조정 함수 실행
// window.addEventListener('load', adjustLayout);
//
// // 화면 크기 변경 시 레이아웃 조정 함수 실행
// window.addEventListener('resize', adjustLayout);

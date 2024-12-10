// 공지사항 데이터
const notices = [
    { title: "여름 휴가 기간 고객센터 운영 안내", content: "2023년 7월 1일부터 7월 15일까지 고객센터 운영 시간이 변경됩니다.", date: "2023년 7월 1일" },
    { title: "장기 미사용 계정 정책 안내", content: "장기 미사용 계정은 2023년 6월 1일부터 사용 제한됩니다. 자세한 내용을 확인하세요.", date: "2023년 6월 1일" },
    { title: "서비스 업데이트 안내", content: "서비스 업데이트가 2023년 6월 1일부터 적용됩니다. 자세한 내용은 공지사항을 참고하세요.", date: "2023년 6월 1일" },
    { title: "개인정보 처리방침 변경", content: "개인정보 처리방침이 2023년 5월 15일부터 변경됩니다. 변경된 내용을 꼭 확인해주세요.", date: "2023년 5월 15일" },
    { title: "긴급 서버 점검 안내", content: "2023년 5월 1일 오후 3시부터 6시까지 서버 점검이 있습니다. 서비스 이용에 참고 부탁드립니다.", date: "2023년 5월 1일" },
    { title: "신규 기능 출시 안내", content: "신규 기능이 추가되었습니다. 새롭게 업데이트된 기능을 확인하세요.", date: "2023년 4월 20일" },
    { title: "시스템 점검 안내", content: "2023년 4월 10일 오전 1시부터 5시까지 시스템 점검이 예정되어 있습니다. 이용에 불편을 드려 죄송합니다.", date: "2023년 4월 10일" },
    { title: "아이디 및 비밀번호 변경 권고", content: "고객님의 보안을 위해 정기적으로 비밀번호를 변경해주세요.", date: "2023년 3월 25일" },
];

// 공지사항 날짜 기준 내림차순 정렬
notices.sort((a, b) => new Date(b.date) - new Date(a.date));
// 공지사항 리스트 동적으로 추가
function loadNotices() {
    const noticeList = document.getElementById("notice-list");
    notices.forEach((notice, index) => {
        const noticeItem = document.createElement("a");
        noticeItem.className = "list-group-item list-group-item-action d-flex justify-content-between align-items-center";
        noticeItem.href = "#";
        noticeItem.dataset.bsToggle = "modal";
        noticeItem.dataset.bsTarget = "#noticeModal";
        noticeItem.dataset.index = index; // 데이터 인덱스 설정

        // 제목과 날짜 추가
        noticeItem.innerHTML = `
            <div>${notice.title}</div>
            <div class="notice-date">${notice.date}</div>
        `;

        noticeList.appendChild(noticeItem);
    });
}

// 모달 내용 업데이트
function updateModal(event) {
    const clickedElement = event.target.closest("a"); // 클릭된 항목의 부모 `a` 태그 찾기
    if (!clickedElement) return; // 클릭한 요소가 `a` 태그가 아니면 종료

    const index = clickedElement.dataset.index; // 인덱스 가져오기
    const notice = notices[index]; // 공지사항 데이터 가져오기

    if (notice) {
        // 모달 제목과 내용 업데이트
        document.getElementById("noticeModalLabel").innerText = notice.title;
        document.getElementById("modal-content").innerText = notice.content;
    }
}

// 페이지 로드 시 공지사항 불러오기 및 이벤트 핸들러 추가
document.addEventListener("DOMContentLoaded", () => {
    loadNotices();

    const noticeList = document.getElementById("notice-list");
    noticeList.addEventListener("click", updateModal);
});
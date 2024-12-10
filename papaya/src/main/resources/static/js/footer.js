// 푸터 관련 JavaScript
document.addEventListener('DOMContentLoaded', () => {
    const newsletterForm = document.getElementById('newsletterForm');
    newsletterForm.addEventListener('submit', (e) => {
        e.preventDefault();
        alert('뉴스레터 구독이 완료되었습니다!');
        newsletterForm.reset();
    });
});
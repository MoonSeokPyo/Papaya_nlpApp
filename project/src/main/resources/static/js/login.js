function login() {
    const userId = document.getElementById('user-id').value;
    const password = document.getElementById('user-password').value;

    if (!userId || !password) {
        alert("아이디와 비밀번호를 모두 입력해주세요.");
        return;
    }

    fetch('/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: userId, password: password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert(`환영합니다 ${data.username}님!`);
        } else {
            alert("다시 확인해주세요.");
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert("오류가 발생했습니다. 다시 시도해주세요.");
    });
}

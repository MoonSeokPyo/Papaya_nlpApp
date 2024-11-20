function searchByBoundary() {			//브라우저를 통해 현재 위치정보를 받아옴
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
	} else {
		alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
	}

	function successCallback(position) {
		const lat = position.coords.latitude;
		const lng = position.coords.longitude;
		const API_KEY = '1aecc8adda8186c6f0963b2994520212';

		fetch(`https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`, {
			headers: {
				'Authorization': `KakaoAK ${API_KEY}`				//카카오 맵 api 해더
			}
		})
			.then(response => response.json())
			.then(data => {
				if (data.documents && data.documents.length > 0) {
					const address = data.documents[0].address; 				// 주소 정보
					window.location.href = `/search/boundary?address=${encodeURIComponent(address.address_name)}`;
				} else {
					console.log("해당 좌표에 대한 주소를 찾을 수 없습니다.");
				}
			})
			.catch(error => console.error('Error:', error));
	}

	function errorCallback(error) {
		alert("위치 정보를 가져올 수 없습니다: " + error.message);
	}
}

function searchByName(name) {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
	} else {
		alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
	}

	function successCallback(position) {
		const lat = position.coords.latitude;
		const lng = position.coords.longitude;
		const API_KEY = '1aecc8adda8186c6f0963b2994520212';

		fetch(`https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`, {
			headers: {
				'Authorization': `KakaoAK ${API_KEY}`				//카카오 맵 api 해더
			}
		})
			.then(response => response.json())
			.then(data => {
				if (data.documents && data.documents.length > 0) {
					const address = data.documents[0].address; 				// 주소 정보
					window.location.href = `/search/restaurant?address=${encodeURIComponent(address.address_name)}&name=${name}`;
				} else {
					console.log("해당 좌표에 대한 주소를 찾을 수 없습니다.");
				}
			})
			.catch(error => console.error('Error:', error));
	}

	function errorCallback(error) {
		alert("위치 정보를 가져올 수 없습니다: " + error.message);
	}
}

function searchByCategory(category) {
	if (navigator.geolocation) {
		navigator.geolocation.getCurrentPosition(successCallback, errorCallback);
	} else {
		alert("이 브라우저는 위치 정보를 지원하지 않습니다.");
	}

	function successCallback(position) {
		const lat = position.coords.latitude;
		const lng = position.coords.longitude;
		const API_KEY = '1aecc8adda8186c6f0963b2994520212';

		fetch(`https://dapi.kakao.com/v2/local/geo/coord2address.json?x=${lng}&y=${lat}`, {
			headers: {
				'Authorization': `KakaoAK ${API_KEY}`				//카카오 맵 api 해더
			}
		})
			.then(response => response.json())
			.then(data => {
				if (data.documents && data.documents.length > 0) {
					const address = data.documents[0].address; 				// 주소 정보
					window.location.href = `/search/category?address=${encodeURIComponent(address.address_name)}&category=${category}`;
				} else {
					console.log("해당 좌표에 대한 주소를 찾을 수 없습니다.");
				}
			})
			.catch(error => console.error('Error:', error));
	}

	function errorCallback(error) {
		alert("위치 정보를 가져올 수 없습니다: " + error.message);
	}
}
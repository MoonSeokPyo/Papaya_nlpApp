function initMap() {
    const mapContainer = document.getElementById('map');

    if (!latitude || !longitude) {
        console.error("Latitude or longitude is undefined. Check your Thymeleaf setup.");
        return;
    }

    const mapCenter = new kakao.maps.LatLng(latitude, longitude);

    // 지도 옵션 설정
    const mapOption = {
        center: mapCenter,
        level: 1 // 확대 수준
    };

    // 지도 생성
    const map = new kakao.maps.Map(mapContainer, mapOption);

    // 마커 생성
    const restMarker = new kakao.maps.Marker({
        position: mapCenter,
        map: map,
        title: "현위치",
    });
}

window.onload = initMap;

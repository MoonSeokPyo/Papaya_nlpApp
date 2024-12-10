// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// var container = document.getElementById('map');
// var options = {
//     // center: new kakao.maps.LatLng(33.450701, 126.570667),
//     // center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 중심 좌표
//     center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울 중심 좌표
//     // level: 4 // 확대 레벨
//     level: 5 // 확대 레벨
// };

// var map = new kakao.maps.Map(container, options);

// 일반 지도와 스카이뷰로 지도 타입을 전환할 수 있는 지도타입 컨트롤을 생성합니다
var mapTypeControl = new kakao.maps.MapTypeControl();

// 지도에 컨트롤을 추가해야 지도위에 표시됩니다
// kakao.maps.ControlPosition은 컨트롤이 표시될 위치를 정의하는데 TOPRIGHT는 오른쪽 위를 의미합니다
map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);

// 지도 확대 축소를 제어할 수 있는  줌 컨트롤을 생성합니다
var zoomControl = new kakao.maps.ZoomControl();
map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
//////////////////////////////////////////////////
// 장소 검색 객체를 생성합니다
// var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex: 1});


// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        alert('키워드를 입력해주세요!');
        return false;
    }

    // DB에서 검색 결과를 가져오기 위해 서버에 요청
    axios.get('/api/restaurants/search', {
        params: {
            keyword: keyword    // Spring Boot로 전달될 키워드
        }
    })
        .then(response => {
            const places = response.data;   // 서버에서 받은 검색 결과
            displayPlaces(places);  // 검색 결과를 지도에 표시
        })
        .catch(error => {
            console.error('검색 중 오류 발생:', error);
            alert('검색 중 문제가 발생했습니다. 나중에 다시 시도해주세요.');
        });

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    // ps.keywordSearch(keyword, placesSearchCB);
}

// 검색 결과 목록과 마커를 지도에 표시
function displayPlaces(places) {
    var listEl = document.getElementById('placesList'),
        bounds = new kakao.maps.LatLngBounds(),
        fragment = document.createDocumentFragment();

    // 기존 검색 결과 제거
    removeAllChildNods(listEl);
    removeMarker();

    places.forEach((place, i) => {
        // 장소 위치를 설정
        var placePosition = new kakao.maps.LatLng(place.latitude, place.longitude),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, place); // 리스트 항목 생성

        // LatLngBounds 객체에 좌표 추가 (지도 범위 조정을 위함)
        bounds.extend(placePosition);

        // 마커와 리스트 항목에 mouseover 이벤트 추가
        (function (marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function () {
                displayInfowindow(marker, title);
            });
            kakao.maps.event.addListener(marker, 'mouseout', function () {
                infowindow.close();
            });
            itemEl.onmouseover = function () {
                displayInfowindow(marker, title);
            };
            itemEl.onmouseout = function () {
                infowindow.close();
            };
        })(marker, place.name);

        fragment.appendChild(itemEl);
    });

    // 검색 결과를 목록에 추가
    listEl.appendChild(fragment);

    // 지도 범위를 검색된 장소 위치로 재설정
    map.setBounds(bounds);
}

// 검색 결과 항목을 Element로 반환
function getListItem(index, place) {
    var el = document.createElement('li'),
        itemStr = `<span class="markerbg marker_${index + 1}"></span>
                   <div class="info">
                       <h5>${place.name}</h5>
                       <span>${place.address}</span>
                       <span class="tel">${place.phone || '전화번호 없음'}</span>
                   </div>`;

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 마커 생성 및 지도에 표시
function addMarker(position, idx) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png',
        imageSize = new kakao.maps.Size(36, 37),
        imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691),
            spriteOrigin: new kakao.maps.Point(0, idx * 46),
            offset: new kakao.maps.Point(13, 37)
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
            position: position,
            image: markerImage
        });

    marker.setMap(map); // 지도에 마커 표시
    markers.push(marker); // 마커 배열에 추가

    return marker;
}

// 지도에 표시된 모든 마커 제거
function removeMarker() {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
}

// 검색 결과 목록의 자식 Element 제거
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild(el.lastChild);
    }
}

// 마커 클릭 시 인포윈도우 표시
function displayInfowindow(marker, title) {
    var content = `<div style="padding:5px;z-index:1;">${title}</div>`;
    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
// 카카오 API를 통해 검색 결과를 가져오는 함수
// function placesSearchCB(data, status, pagination) {
//     if (status === kakao.maps.services.Status.OK) {
//
//         // 정상적으로 검색이 완료됐으면
//         // 검색 목록과 마커를 표출합니다
//         displayPlaces(data);
//
//         // 페이지 번호를 표출합니다
//         displayPagination(pagination);
//
//     } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
//
//         alert('검색 결과가 존재하지 않습니다.');
//         return;
//
//     } else if (status === kakao.maps.services.Status.ERROR) {
//
//         alert('검색 결과 중 오류가 발생했습니다.');
//         return;
//
//     }
// }

// // 검색 결과 목록과 마커를 표출하는 함수입니다
// function displayPlaces(places) {
//
//     var listEl = document.getElementById('placesList'),
//         menuEl = document.getElementById('menu_wrap'),
//         fragment = document.createDocumentFragment(),
//         bounds = new kakao.maps.LatLngBounds(),
//         listStr = '';
//
//     // 검색 결과 목록에 추가된 항목들을 제거합니다
//     removeAllChildNods(listEl);
//
//     // 지도에 표시되고 있는 마커를 제거합니다
//     removeMarker();
//
//     for (var i = 0; i < places.length; i++) {
//
//         // 마커를 생성하고 지도에 표시합니다
//         var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
//             marker = addMarker(placePosition, i),
//             itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
//
//         // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
//         // LatLngBounds 객체에 좌표를 추가합니다
//         bounds.extend(placePosition);
//
//         // 마커와 검색결과 항목에 mouseover 했을때
//         // 해당 장소에 인포윈도우에 장소명을 표시합니다
//         // mouseout 했을 때는 인포윈도우를 닫습니다
//         (function (marker, title) {
//             kakao.maps.event.addListener(marker, 'mouseover', function () {
//                 displayInfowindow(marker, title);
//             });
//
//             kakao.maps.event.addListener(marker, 'mouseout', function () {
//                 infowindow.close();
//             });
//
//             itemEl.onmouseover = function () {
//                 displayInfowindow(marker, title);
//             };
//
//             itemEl.onmouseout = function () {
//                 infowindow.close();
//             };
//         })(marker, places[i].place_name);
//
//         fragment.appendChild(itemEl);
//     }
//
//     // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
//     listEl.appendChild(fragment);
//     menuEl.scrollTop = 0;
//
//     // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//     map.setBounds(bounds);
// }

// 검색결과 항목을 Element로 반환하는 함수입니다
// function getListItem(index, places) {
//
//     var el = document.createElement('li'),
//         itemStr = '<span class="markerbg marker_' + (index + 1) + '"></span>' +
//             '<div class="info">' +
//             '   <h5>' + places.place_name + '</h5>';
//
//     if (places.road_address_name) {
//         itemStr += '    <span>' + places.road_address_name + '</span>' +
//             '   <span class="jibun gray">' + places.address_name + '</span>';
//     } else {
//         itemStr += '    <span>' + places.address_name + '</span>';
//     }
//
//     itemStr += '  <span class="tel">' + places.phone + '</span>' +
//         '</div>';
//
//     el.innerHTML = itemStr;
//     el.className = 'item';
//
//     return el;
// }

// // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
// function addMarker(position, idx, title) {
//     var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
//         imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
//         imgOptions = {
//             spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
//             spriteOrigin: new kakao.maps.Point(0, (idx * 46) + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
//             offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
//         },
//         markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
//         marker = new kakao.maps.Marker({
//             position: position, // 마커의 위치
//             image: markerImage
//         });
//
//     marker.setMap(map); // 지도 위에 마커를 표출합니다
//     markers.push(marker);  // 배열에 생성된 마커를 추가합니다
//
//     return marker;
// }

// // 지도 위에 표시되고 있는 마커를 모두 제거합니다
// function removeMarker() {
//     for (var i = 0; i < markers.length; i++) {
//         markers[i].setMap(null);
//     }
//     markers = [];
// }

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild(paginationEl.lastChild);
    }

    for (i = 1; i <= pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i === pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function (i) {
                return function () {
                    pagination.gotoPage(i);
                }
            })(i);
        }

        fragment.appendChild(el);
    }
    paginationEl.appendChild(fragment);
}

// 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
// 인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + '</div>';

    infowindow.setContent(content);
    infowindow.open(map, marker);
}

// // 검색결과 목록의 자식 Element를 제거하는 함수입니다
// function removeAllChildNods(el) {
//     while (el.hasChildNodes()) {
//         el.removeChild(el.lastChild);
//     }
// }

// // 지도에 마커와 인포윈도우를 표시하는 함수입니다
// function displayMarker(locPosition, message) {
//
//     // 마커를 생성합니다
//     var marker = new kakao.maps.Marker({
//         map: map,
//         position: locPosition
//     });
//
//     var iwContent = message, // 인포윈도우에 표시할 내용
//         iwRemoveable = true;
//
//     // 인포윈도우를 생성합니다
//     var infowindow = new kakao.maps.InfoWindow({
//         content: iwContent,
//         removable: iwRemoveable
//     });
//
//     // 인포윈도우를 마커위에 표시합니다
//     infowindow.open(map, marker);
//
//     // 지도 중심좌표를 접속위치로 변경합니다
//     map.setCenter(locPosition);
// }

// ///////현재위치 이동버튼//////////////////////
function currentLocation() {
// HTML5의 geolocation으로 사용할 수 있는지 확인합니다 
    if (navigator.geolocation) {

        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition((position) => {

                var lat = position.coords.latitude, // 위도
                    lon = position.coords.longitude; // 경도

                var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                    message = '<div style="padding:5px;">여기에 계신가요?!</div>'; // 인포윈도우에 표시될 내용입니다

                // 마커와 인포윈도우를 표시합니다
                displayMarker(locPosition, message);

            },
            (error) => {
                console.error("위치 정보를 가져올 수 없습니다.", error);
                alert("현위치를 가져올 수 없습니다. 위치 권한을 허용해주세요.");
            }
        );

    } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다

        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),
            message = 'geolocation을 사용할수 없어요..'

        displayMarker(locPosition, message);
    }
///////////////////////////////////////////////////////// 
}

//////////////////////////////////////////////////
// // 장소 검색 객체를 생성합니다
// var ps = new kakao.maps.services.Places();
//
// // 키워드로 장소를 검색합니다
// ps.keywordSearch('이태원 맛집', placesSearchCB);
//
// // 키워드 검색 완료 시 호출되는 콜백함수 입니다
// function placesSearchCB(data, status, pagination) {
//     if (status === kakao.maps.services.Status.OK) {
//
//         // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
//         // LatLngBounds 객체에 좌표를 추가합니다
//         var bounds = new kakao.maps.LatLngBounds();
//
//         for (var i = 0; i < data.length; i++) {
//             displayMarker(data[i]);
//             bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x));
//         }
//
//         // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
//         map.setBounds(bounds);
//     }
// }
//
// // 지도에 마커를 표시하는 함수입니다
// function displayMarker(place) {
//
//     // 마커를 생성하고 지도에 표시합니다
//     var marker = new kakao.maps.Marker({
//         map: map,
//         position: new kakao.maps.LatLng(place.y, place.x)
//     });
//
//     // 마커에 클릭이벤트를 등록합니다
//     kakao.maps.event.addListener(marker, 'click', function () {
//         // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
//         infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>');
//         infowindow.open(map, marker);
//     });


// // 카카오 지도 초기화 및 현위치 설정
// function initMap() {
//     // 기본 지도 옵션 (초기 중심은 서울로 설정)
//     const container = document.getElementById("map");
//     const options = {
//         center: new kakao.maps.LatLng(37.5665, 126.9780), // 서울 중심
//         level: 5, // 지도 확대 레벨
//     };
//
//     // 지도 생성
//     const map = new kakao.maps.Map(container, options);
//
//     // GeoLocation으로 현위치 가져오기
//     if (navigator.geolocation) {
//         navigator.geolocation.getCurrentPosition(
//             (position) => {
//                 const lat = position.coords.latitude; // 위도
//                 const lng = position.coords.longitude; // 경도
//                 const userLocation = new kakao.maps.LatLng(lat, lng);
//
//                 // 지도 중심을 현위치로 변경
//                 map.setCenter(userLocation);
//
//                 // 사용자 위치에 마커 추가
//                 const marker = new kakao.maps.Marker({
//                     position: userLocation,
//                 });
//                 marker.setMap(map);
//
//                 // 현위치 정보창 표시
//                 const infoWindow = new kakao.maps.InfoWindow({
//                     content: '<div style="padding:5px;">현위치</div>',
//                 });
//                 infoWindow.open(map, marker);
//             },
//             (error) => {
//                 console.error("위치 정보를 가져올 수 없습니다.", error);
//                 alert("현위치를 가져올 수 없습니다. 위치 권한을 허용해주세요.");
//             }
//         );
//     } else {
//         alert("GeoLocation을 지원하지 않는 브라우저입니다.");
//     }
// }
//
// // 좌표로 부드럽게 이동
// function panTo(latitude, longitude) {
//     // 이동할 위도 경도 위치를 생성합니다
//     var moveLatLon = new kakao.maps.LatLng(latitude, longitude);
//
//     // 지도 중심을 부드럽게 이동시킵니다
//     // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//     map.panTo(moveLatLon);
// }
//
// // 페이지 로드 시 지도 초기화
// window.onload = initMap;


// TODO: 음식점 마커 추가
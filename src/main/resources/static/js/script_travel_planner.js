


/* ============================== 시작 ============================== */

/* ------------------------------ 끝 ------------------------------ */




/* ============================== 좌표로 주소 얻기 시작 ============================== */
/* ------------------------------ 좌표로 주소 얻기 끝 ------------------------------ */




/* ============================== 키워드 검색, 목록 표출 시작 ============================== */
// 마커를 담을 배열입니다
var markers = [];

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places();

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({zIndex:1});

// 키워드로 장소를 검색합니다
searchPlaces();

// 키워드 검색을 요청하는 함수입니다
function searchPlaces() {

    var keyword = document.getElementById('keyword').value;

    if (!keyword.replace(/^\s+|\s+$/g, '')) {
        //alert('키워드를 입력해주세요!');
        return false;
    }

    // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
    ps.keywordSearch( keyword, placesSearchCB);
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {

        // 정상적으로 검색이 완료됐으면
        // 검색 목록과 마커를 표출합니다
        displayPlaces(data);

        // 페이지 번호를 표출합니다
        displayPagination(pagination);

    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

        alert('검색 결과가 존재하지 않습니다.');
        return;

    } else if (status === kakao.maps.services.Status.ERROR) {

        alert('검색 결과 중 오류가 발생했습니다.');
        return;

    }
}

// 검색 결과 목록과 마커를 표출하는 함수입니다
function displayPlaces(places) {

    var listEl = document.getElementById('placesList'),
    menuEl = document.getElementById('menu_wrap'),
    fragment = document.createDocumentFragment(),
    bounds = new kakao.maps.LatLngBounds(),
    listStr = '';

    // 검색 결과 목록에 추가된 항목들을 제거합니다
    removeAllChildNods(listEl);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarker();

    for ( var i=0; i<places.length; i++ ) {

        // 마커를 생성하고 지도에 표시합니다
        var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

        // 마커 클릭 이벤트 추가
        (function(marker, place) {
            kakao.maps.event.addListener(marker, 'click', function() {
                // 도로명주소와 지번주소를 폼 필드에 입력합니다
                map.panTo(marker.getPosition());

                var roadAddr = place.road_address_name ? place.road_address_name : '없음';
                var jibunAddr = place.address_name || '없음';
                document.getElementById('roadAddr').value = roadAddr;
                document.getElementById('jibunAddr').value = jibunAddr;

                // 인포윈도우에 클릭한 위치에 대한 상세 주소 정보를 표시합니다
                var content = '<div class="bAddr">' +
                              '<span class="title">법정동 주소정보</span>' +
                              '<div>도로명주소 : ' + roadAddr + '</div>' +
                              '<div>지번 주소 : ' + jibunAddr + '</div>' +
                              '</div>';
                infowindow.setContent(content);
                infowindow.open(map, marker);
            });
        })(marker, places[i]);

        // 목록 클릭 이벤트 추가
        (function(itemEl, place) {
            itemEl.onclick = function() {
                var placePosition = new kakao.maps.LatLng(place.y, place.x);
                map.panTo(placePosition);

                var roadAddr = place.road_address_name ? place.road_address_name : '없음';
                var jibunAddr = place.address_name || '없음';
                document.getElementById('roadAddr').value = roadAddr;
                document.getElementById('jibunAddr').value = jibunAddr;

                var content = '<div class="bAddr">' +
                              '<span class="title">법정동 주소정보</span>' +
                              '<div>도로명주소 : ' + roadAddr + '</div>' +
                              '<div>지번 주소 : ' + jibunAddr + '</div>' +
                              '</div>';
                infowindow.setContent(content);
                infowindow.open(map, markers[places.indexOf(place)]);
            };
        })(itemEl, places[i]);

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
        // LatLngBounds 객체에 좌표를 추가합니다
        bounds.extend(placePosition);

        // 마커와 검색결과 항목에 mouseover 했을때
        // 해당 장소에 인포윈도우에 장소명을 표시합니다
        // mouseout 했을 때는 인포윈도우를 닫습니다
        (function(marker, title) {
            kakao.maps.event.addListener(marker, 'mouseover', function() {
                displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, 'mouseout', function() {
                infowindow.close();
            });

            itemEl.onmouseover =  function () {
                displayInfowindow(marker, title);
            };

            itemEl.onmouseout =  function () {
                infowindow.close();
            };
        })(marker, places[i].place_name);

        fragment.appendChild(itemEl);
    }

    // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
    listEl.appendChild(fragment);
    menuEl.scrollTop = 0;

    // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
    map.setBounds(bounds);
}

// 검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, places) {

    var el = document.createElement('li'),
    itemStr = '<span class="markerbg marker_' + (index+1) + '"></span>' +
                '<div class="info">' +
                '   <h5>' + places.place_name + '</h5>';

    if (places.road_address_name) {
        itemStr += '    <span>' + places.road_address_name + '</span>' +
                    '   <span class="jibun gray">' +  places.address_name  + '</span>';
    } else {
        itemStr += '    <span>' +  places.address_name  + '</span>';
    }

      itemStr += '  <span class="tel">' + places.phone  + '</span>' +
                '</div>';

    el.innerHTML = itemStr;
    el.className = 'item';

    return el;
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(0, (idx*46)+10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
    for ( var i = 0; i < markers.length; i++ ) {
        markers[i].setMap(null);
    }
    markers = [];
}

// 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
function displayPagination(pagination) {
    var paginationEl = document.getElementById('pagination'),
        fragment = document.createDocumentFragment(),
        i;

    // 기존에 추가된 페이지번호를 삭제합니다
    while (paginationEl.hasChildNodes()) {
        paginationEl.removeChild (paginationEl.lastChild);
    }

    for (i=1; i<=pagination.last; i++) {
        var el = document.createElement('a');
        el.href = "#";
        el.innerHTML = i;

        if (i===pagination.current) {
            el.className = 'on';
        } else {
            el.onclick = (function(i) {
                return function() {
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

 // 검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
    while (el.hasChildNodes()) {
        el.removeChild (el.lastChild);
    }
}
/* ------------------------------ 키워드 검색, 목록 표출 끝 ------------------------------ */



/* ============================== 지도 타입 바꾸기2 시작 ============================== */
var mapTypes = {
    terrain : kakao.maps.MapTypeId.TERRAIN,
    traffic :  kakao.maps.MapTypeId.TRAFFIC,
    bicycle : kakao.maps.MapTypeId.BICYCLE,
    useDistrict : kakao.maps.MapTypeId.USE_DISTRICT
};

// 체크 박스를 선택하면 호출되는 함수입니다
function setOverlayMapTypeId() {
    var chkTerrain = document.getElementById('chkTerrain'),
        chkTraffic = document.getElementById('chkTraffic'),
        chkBicycle = document.getElementById('chkBicycle'),
        chkUseDistrict = document.getElementById('chkUseDistrict');

    // 지도 타입을 제거합니다
    for (var type in mapTypes) {
        map.removeOverlayMapTypeId(mapTypes[type]);
    }

    // 지적편집도정보 체크박스가 체크되어있으면 지도에 지적편집도정보 지도타입을 추가합니다
    if (chkUseDistrict.checked) {
        map.addOverlayMapTypeId(mapTypes.useDistrict);
    }

    // 지형정보 체크박스가 체크되어있으면 지도에 지형정보 지도타입을 추가합니다
    if (chkTerrain.checked) {
        map.addOverlayMapTypeId(mapTypes.terrain);
    }

    // 교통정보 체크박스가 체크되어있으면 지도에 교통정보 지도타입을 추가합니다
    if (chkTraffic.checked) {
        map.addOverlayMapTypeId(mapTypes.traffic);
    }

    // 자전거도로정보 체크박스가 체크되어있으면 지도에 자전거도로정보 지도타입을 추가합니다
    if (chkBicycle.checked) {
        map.addOverlayMapTypeId(mapTypes.bicycle);
    }
}
/* ------------------------------ 지도 타입 바꾸기2 끝 ------------------------------ */


/* ============================== 지도 정보 얻어오기 시작 ============================== */
function getInfo() {
    // 지도의 현재 중심좌표를 얻어옵니다
    var center = map.getCenter();

    // 지도의 현재 레벨을 얻어옵니다
    var level = map.getLevel();

    // 지도타입을 얻어옵니다
    var mapTypeId = map.getMapTypeId();

    // 지도의 현재 영역을 얻어옵니다
    var bounds = map.getBounds();

    // 영역의 남서쪽 좌표를 얻어옵니다
    var swLatLng = bounds.getSouthWest();

    // 영역의 북동쪽 좌표를 얻어옵니다
    var neLatLng = bounds.getNorthEast();

    // 영역정보를 문자열로 얻어옵니다. ((남,서), (북,동)) 형식입니다
    var boundsStr = bounds.toString();


    var message = '지도 중심좌표는 위도 ' + center.getLat() + ', <br>';
    message += '경도 ' + center.getLng() + ' 이고 <br>';
    message += '지도 레벨은 ' + level + ' 입니다 <br> <br>';
    message += '지도 타입은 ' + mapTypeId + ' 이고 <br> ';
    message += '지도의 남서쪽 좌표는 ' + swLatLng.getLat() + ', ' + swLatLng.getLng() + ' 이고 <br>';
    message += '북동쪽 좌표는 ' + neLatLng.getLat() + ', ' + neLatLng.getLng() + ' 입니다';

    // 개발자도구를 통해 직접 message 내용을 확인해 보세요.
    console.log(message);
}
/* ------------------------------ 지도 정보 얻어오기 끝 ------------------------------ */

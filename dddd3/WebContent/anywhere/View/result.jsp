<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="cpas.model.Food" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>가게 검색 결과</title>
    <meta charset="UTF-8">
    <style>
        /* 스타일링을 위한 CSS 코드 */
        body {
            display: flex; /* Flexbox를 사용하여 레이아웃 설정 */
        }
        #left-content {
            flex: 1; /* 왼쪽 콘텐츠가 자동으로 남은 공간을 차지하도록 설정 */
            padding: 20px;
            overflow-y: scroll; /* 스크롤이 필요한 경우 스크롤을 표시 */
            max-height: 100vh; /* 최대 화면 높이까지 스크롤 가능하도록 설정 */
        }
        #right-content {
            flex: 1; /* 오른쪽 콘텐츠가 자동으로 남은 공간을 차지하도록 설정 */
            padding: 20px;
            border-left: 1px solid #ccc; /* 왼쪽과 오른쪽 콘텐츠를 구분하는 경계선 추가 */
        }
        iframe {
            width: 100%; /* iframe이 부모 컨테이너의 100% 너비를 차지하도록 설정 */
            height: 100%; /* 부모 컨테이너의 높이를 100%로 설정 */
            border: none; /* iframe 테두리 제거 */
        }
    </style>
</head>
<body>
    <h2>가게 검색 결과</h2>
    <ul class="store-list">
        <% 
            List<Food> foods = (List<Food>) request.getAttribute("foods");
            if (foods != null) {
                for (Food food : foods) {
        %>
        <div>
            <h3><a href="#" onclick="loadStore('<%= food.getStoreAddress() %>', '<%= food.getStoreName() %>'); return false;"><%= food.getStoreName() %></a></h3>
            <p>Address: <%= food.getStoreAddress() %></p>
            <p>Tel: <%= food.getStoreTell() %></p>
            <a href="#" onclick="openReviewsWindow('<%= food.getStoreName() %>'); return false;">리뷰 하기</a>
        </div>
        <%
                }
            }
        %>
    </ul>
    <div id="right-content">
        <!-- 가게 페이지를 로드할 iframe -->
        <iframe id="storeFrame" src=""></iframe>
    </div>

    <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
    
    <script>
        // Kakao.init()을 호출하여 Kakao JavaScript SDK 초기화
        Kakao.init('f5fb468455abb768835f1cae5f631b25'); // 여기에 카카오 앱 키를 입력하세요
    
        function loadStore(address, name) {
            // 클릭된 가게 페이지를 iframe에 로드
            var iframe = document.getElementById("storeFrame");
            iframe.src = "/asd/anywhere/showStore?address=" + encodeURIComponent(address) + "&name=" + encodeURIComponent(name);
            
            // 결과 화면 스크롤을 최상단으로 이동
            var leftContent = document.getElementById("left-content");
            leftContent.scrollTop = 0;
        }
    
        function openReviewsWindow(storeName) {
            // 카카오톡 로그인 상태 확인
            Kakao.Auth.getStatusInfo(function (statusInfo) {
                if (statusInfo.status === 'connected') {
                    // 이미 로그인된 경우 리뷰 창을 열기
                    var url = "/anywhere/review.html?storeName=" + encodeURIComponent(storeName);
                    var reviewWindow = window.open(url, '리뷰 하기', 'width=600,height=400');
    
                    // 리뷰 창을 저장한 변수를 부모 창에 전달
                    if (reviewWindow) {
                        reviewWindow.opener = window;
                    }
                } else {
                    // 로그인 상태가 아닌 경우 메시지 표시
                    alert("카카오톡 계정으로 로그인해 주세요.");
                }
            });
        }
    </script>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Store Location</title>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=f5fb468455abb768835f1cae5f631b25&libraries=services"></script>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            font-family: 'Arial', sans-serif;
        }

        #map {
            height: 50%;
            margin-bottom: 1%;
        }

        .info-box {
            padding: 1%;
            margin-bottom: 1%;
            border: 1px solid #ddd;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
        }

        h3, h4 {
            margin: 0.5% 0;
        }

        p {
            margin: 0.3% 0;
        }
    </style>
</head>
<body>
<div id="map"></div>

<div id="store-info"> 

<% 
String address = (String) request.getAttribute("address");
String storeName = (String) request.getAttribute("name");
String JDBC_URL_NEW_FOOD = "jdbc:mysql://localhost:3306/new_food_info";
String JDBC_URL_REVIEW = "jdbc:mysql://localhost:3306/review";
String DB_USER = "root";
String DB_PASSWORD = "0000";

Connection connection = null;
PreparedStatement preparedStatement = null;
ResultSet resultSet = null;
String storeInfo = "";
Map<String, String> menus = new HashMap<>();

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    connection = DriverManager.getConnection(JDBC_URL_NEW_FOOD, DB_USER, DB_PASSWORD);

    String[] tables = {"store_ch", "store_han", "store_jp"};

    for (String table : tables) {
        String sql = "SELECT store_namecol, store_menu, menu_price, store_address, store_tell " +
                     "FROM " + table + " WHERE store_namecol=?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, storeName);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (storeInfo.isEmpty()) {
                storeInfo = "<h1>" + resultSet.getString("store_namecol") + "</h1>" +
                            "<p>sex 주소: " + resultSet.getString("store_address") + "</p>" +
                            "<p>전화 번호: " + resultSet.getString("store_tell") + "</p>";
            }
            menus.put(resultSet.getString("store_menu"), resultSet.getString("menu_price"));
        }
    }
} catch (Exception e) {
    e.printStackTrace();
} finally {
    if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
    if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {}
    if (connection != null) try { connection.close(); } catch (SQLException e) {}
}
%>

<div class="info-box">
    <% out.println(storeInfo); %>
</div>

<div class="info-box">
    <h3>메뉴</h3>
    <%
    for (Map.Entry<String, String> entry : menus.entrySet()) {
        out.println("<p>" + entry.getKey() + " - " + entry.getValue() + "</p>");
    }
    %>
</div>
</div>

<div id="reviews">
    <h3>리뷰 목록</h3>
    <% 
    Connection connectionReview = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connectionReview = DriverManager.getConnection(JDBC_URL_REVIEW, DB_USER, DB_PASSWORD);

        String reviewSql = "SELECT user_id, user_review, user_rating, store_namecol FROM user_review1 WHERE store_namecol=?";
        preparedStatement = connectionReview.prepareStatement(reviewSql);
        preparedStatement.setString(1, storeName);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
    %>
        <div class="info-box">
            <h4>User ID: <%= resultSet.getString("user_id") %></h4>
            <p>리뷰 내용: <%= resultSet.getString("user_review") %></p>
            <p>평점: <%= resultSet.getString("user_rating") %></p>
        </div>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (resultSet != null) try { resultSet.close(); } catch (SQLException e) {}
        if (preparedStatement != null) try { preparedStatement.close(); } catch (SQLException e) {}
        if (connectionReview != null) try { connectionReview.close(); } catch (SQLException e) {}
    }
    %>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    var container = document.getElementById('map');
    var options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3
    };

    var map = new kakao.maps.Map(container, options);
    var geocoder = new kakao.maps.services.Geocoder();

    <% 
    if (address != null && storeName != null) {
    %>
        geocoder.addressSearch('<%= address %>', function(result, status) {
            if (status === kakao.maps.services.Status.OK) {
                var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
                var marker = new kakao.maps.Marker({
                    map: map,
                    position: coords
                });

                var infowindow = new kakao.maps.InfoWindow({
                    content: '<div style="width:200px;text-align:center;padding:10px;">'
                           + '<%= storeName %><br>'
                           + '<a href="https://map.kakao.com/link/to/' + result[0].address_name + ',' + result[0].y + ',' + result[0].x + '" target="_blank">'
                           + '<button type="button">길찾기</button></a></div>'
                });

                kakao.maps.event.addListener(marker, 'click', function() {
                    infowindow.open(map, marker);
                });

                map.setCenter(coords);
            }
        });
    <% 
    }
    %>
});
</script>
</body>
</html>

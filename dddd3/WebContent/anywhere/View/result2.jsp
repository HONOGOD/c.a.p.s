<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="cpas.model.Food" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>추천결과</title>
    <meta charset="UTF-8">
    <style>
        pre {
            font-size: 18px;
        }
    </style>
</head>
<body>
    <h2>추천 목록</h2>
    <%
        List<Food> foods = (List<Food>) request.getAttribute("foods");
        if (foods != null) {
            for (Food food : foods) {
    %>
        <div>
<h3><a href="showStore?address=<%= food.getStoreAddress() %>&name=<%= food.getStoreName() %>"><%= food.getStoreName() %></a></h3>
            <p>Address: <%= food.getStoreAddress() %></p>
            <p>Tel: <%= food.getStoreTell() %></p>
        </div>
    <%
            }
        }
    %>
</body>
</html>

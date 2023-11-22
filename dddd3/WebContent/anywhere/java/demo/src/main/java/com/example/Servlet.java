package com.example;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;


public class Servlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/food";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "1234";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // 검색어 가져오기
        String searchQuery = request.getParameter("query");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = conn.createStatement()) {

            // SQL 쿼리 생성
            String sql = "SELECT * FROM store WHERE StoreName LIKE '%" + searchQuery + "%'";
            ResultSet resultSet = statement.executeQuery(sql);

            // 검색 결과 저장
            List<String> searchResults = new ArrayList<>();
            while (resultSet.next()) {
                searchResults.add(resultSet.getString("StoreName"));
            }

            // 검색 결과를 JSON 형태로 변환
            Gson gson = new Gson();
            String jsonResults = gson.toJson(searchResults);

            // JSON 형태의 결과를 JavaScript 변수에 할당
            request.setAttribute("jsonResults", jsonResults);

            // 결과를 result.html로 포워드
            RequestDispatcher dispatcher = request.getRequestDispatcher("/result.html");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while processing the request.");
        }
    }
}

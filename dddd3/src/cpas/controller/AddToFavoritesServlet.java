package cpas.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/AddToFavoritesServlet")
public class AddToFavoritesServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청에서 가게 이름과 사용자 ID 추출
        String storeName = request.getParameter("storeName");
        String userId = request.getParameter("userId");

        if (storeName != null && userId != null) {
            // 데이터베이스 연결 정보
            String JDBC_URL = "jdbc:mysql://localhost:3306/review";
            String DB_USER = "root";
            String DB_PASSWORD = "0000";

            Connection connection = null;
            PreparedStatement preparedStatement = null;

            try {
                // 데이터베이스 연결
                connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

                // SQL 쿼리: 사용자의 즐겨찾기 테이블에 가게 추가
                String insertSQL = "INSERT INTO favorite (user_id, store_name) VALUES (?, ?)";
                preparedStatement = connection.prepareStatement(insertSQL);
                preparedStatement.setString(1, userId);
                preparedStatement.setString(2, storeName);

                // 쿼리 실행
                int rowsInserted = preparedStatement.executeUpdate();

                if (rowsInserted > 0) {
                    // 즐겨찾기 추가가 성공하면 "success" 반환
                    response.getWriter().write("success");
                } else {
                    // 즐겨찾기 추가에 실패하면 "failure" 반환
                    response.getWriter().write("failure");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (preparedStatement != null) {
                        preparedStatement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // 요청에서 필수 매개변수가 누락된 경우 "failure" 반환
            response.getWriter().write("failure");
        }
    }
}


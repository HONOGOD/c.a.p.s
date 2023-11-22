package cpas.controller;

import com.google.gson.Gson;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReviewServlet")
public class ReviewServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/review";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "0000";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String user_id = request.getParameter("user_name"); // 클라이언트에서 전송한 user_name
        String user_review = request.getParameter("user_review");
        String user_rating = request.getParameter("user_rating");
        String store_name = request.getParameter("store_name");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
        	conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        	String sql = "INSERT INTO user_review1 (user_id, user_review, user_rating, store_namecol) VALUES (?, ?, ?, ?)"; // store_name 추가
        	stmt = conn.prepareStatement(sql);
        	stmt.setString(1, user_id);
        	stmt.setString(2, user_review);
        	stmt.setString(3, user_rating);
        	stmt.setString(4, store_name); // store_name을 4번째 파라미터로 추가


            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failure");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("database_failure: " + e.getMessage());
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

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

@WebServlet("/FetchReviewsServlet")
public class FetchReviewsServlet extends HttpServlet {
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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT user_id, user_review, user_rating, store_namecol FROM user_review1");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String user_id = rs.getString("user_id");
                String user_review = rs.getString("user_review");
                String user_rating = rs.getString("user_rating");
                String store_name = rs.getString("store_namecol");

                reviews.add(new Review(user_id, user_review, user_rating, store_name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String reviewJson = new Gson().toJson(reviews);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(reviewJson);
    }
}

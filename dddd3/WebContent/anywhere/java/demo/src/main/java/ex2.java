import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ex2 {
    public static void main(String[] args) {
      
String url = "jdbc:mysql://localhost:3306/food"; 
String user = "root"; 
String password = "1234"; 


try {
    Class.forName("com.mysql.cj.jdbc.Driver");
} catch (ClassNotFoundException e) {
    e.printStackTrace();
    return;
}

try (Connection connection = DriverManager.getConnection(url, user, password);
     Statement statement = connection.createStatement()) {


    String query = "SELECT * FROM store"; 


    ResultSet resultSet = statement.executeQuery(query);

    while (resultSet.next()) {
        int ID = resultSet.getInt("ID");
        String firstName = resultSet.getString("StoreName");
        String lastName = resultSet.getString("Address");

        System.out.println("Employee ID: " + ID + ", Name: " + firstName + " " + lastName);
    }

} catch (SQLException e) {
    e.printStackTrace();
}
    }
}

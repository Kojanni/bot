package anonymbot.model;

import java.sql.*;

public class Login {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://Localhost:3306/bot?useUnicode=tru&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

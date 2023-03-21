package ma.enset.javafx_tp.dao.connectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnectionDB {
    private static final String dbName = "db_product";
    private static final String host = "jdbc:mysql://localhost:3307/"+dbName;
    private static final String username = "root";
    private static final String password = "";
    private static Connection connection;

    private SingletonConnectionDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(host, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            if(connection==null || connection.isClosed())
                new SingletonConnectionDB();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}

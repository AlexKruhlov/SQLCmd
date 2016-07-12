package ua.com.rafael;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * Created by sigmund69 on 12.07.2016.
 */
public class SQLExecutor {

    public static void regDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("[ERROR]: MySQL driver was not registered!");
        }
    }

    public static Connection userCheckIn(String database, String user, String password) {
        Connection connection = null;
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database +
                            "?autoReconnect=true&useSSL=false", user, password);
            System.out.println("Your registration has been successful!");
            return connection;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
}






















package ua.com.rafael;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by sigmund69 on 13.07.2016.
 */
public class DBConnector {
    public static void regDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("[ERROR]: MySQL driver was not registered!");
        }
    }

    public static Connection getConnection(UserInfo userInfo) {
        System.out.println("Connecting...");
        return userCheckIn(userInfo.getDatabase(), userInfo.getUser(), userInfo.getPassword());
    }


//
//
//
//    } while (choice.equals("Y") || choice.equals("y"));
//        System.exit(1);
//        return null;
//    }

    private static Connection userCheckIn(String database, String user, String password) {
        Connection connection = null;
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/" + database +
                            "?autoReconnect=true&useSSL=false", user, password);
            System.out.println("Your registration has been successful!");
            return connection;
        } catch (SQLException ex) {
            // handle any errors
            System.out.println(ex.getCause().getMessage());
            System.out.println("Please, check your database name, user name and password!");
        }
        return null;
    }
}

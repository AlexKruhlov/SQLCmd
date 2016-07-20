package ua.com.rafael;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Pipe;
import java.nio.channels.SelectableChannel;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;

import com.mysql.jdbc.*;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.PreparedStatement;

/**
 * Created by sigmund69 on 08.07.2016.
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to console database manager!\n");
        Scanner scanner = new Scanner(System.in);

//      Connection block
        Connection connection = null;
        String choice = null;

//        do {
//            UserInfo userInfo = new UserInfo();
//            userInfo.inputAll();
//            connection = null;//DBConnector.getConnection(userInfo);
//            if (connection != null) break;
//            choice = getChoice(scanner);
//            if (choice.equals("N") || choice.equals("n")) System.exit(1);
//        } while (choice.equals("Y") || choice.equals("y"));

    }

    private static String getChoice(Scanner scanner) {
        String resultStr = null;
        do {
            System.out.println("Do you want to try again? (<Y>-yes, <N>-no): ");
            resultStr = scanner.nextLine();
        } while (isFail(resultStr));
        return resultStr;
    }

    public static boolean isFail(String resultStr) {
        return resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y"));
    }
}




















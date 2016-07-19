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
        DBConnector.regDriver();
        Connection connection = null;
        String choice = null;

        do {
            UserInfo userInfo = new UserInfo();
            userInfo.inputAll();
            connection = DBConnector.getConnection(userInfo);
            if (connection != null) break;
            choice = getChoice(scanner);
            if (choice.equals("N") || choice.equals("n")) System.exit(1);
        } while (choice.equals("Y") || choice.equals("y"));




//      Execution block
        Statement statement = null;
        ResultSet resultSet = null;



        /*
        try {
            statement = connection.createStatement();
//            statement.execute("UPDATE myschema.mytable SET myname='Viktor' where mynum>1");
            statement.executeUpdate("INSERT INTO myschema.mytable (mynum,myname,age) VALUES (4,'Emma',33)");

        } catch (SQLException ex) {
            System.out.println("Quiry Error!!!");
        } finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlEx) { } // ignore

                resultSet = null;
            }

            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException sqlEx) { } // ignore

                statement = null;
            }
        }
        */
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




















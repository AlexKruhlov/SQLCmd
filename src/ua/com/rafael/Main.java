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

    private static void printSQLExceptionmassage(SQLException ex) {
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to console database manager!");
        SQLExecutor.regDriver();
        String choice;
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        do {
            System.out.print("Please, input your database name: ");
            String database = scanner.nextLine();
            System.out.print("Please, input your user name: ");
            String user = scanner.nextLine();
            System.out.print("Please, input your password: ");
            String password = scanner.nextLine();
            System.out.println("Connecting...");
            connection = SQLExecutor.userCheckIn(database, user, password);
            if (connection != null) {
                break;
            }
            choice = getChoice(scanner);
            if (choice.equals("N") || choice.equals("n")) System.exit(1);
        } while (choice.equals("Y") || choice.equals("y"));


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
        } while (resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y")));
        return resultStr;
    }
}




















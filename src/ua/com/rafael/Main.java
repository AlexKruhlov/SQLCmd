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
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {

            System.out.println("Registration ERROR!!!");
            // handle the error
        }
        System.out.println("Driver refistration Ok!");

        Connection connection= null;
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/myschema?autoReconnect=true&useSSL=false",
                            "root", "independence24");
            // Do something with the Connection
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

        Statement statement = null;
        ResultSet resultSet = null;


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

    }
}




















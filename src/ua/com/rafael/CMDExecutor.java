package ua.com.rafael;


import com.mysql.jdbc.authentication.MysqlClearPasswordPlugin;
import com.mysql.jdbc.exceptions.jdbc4.MySQLDataException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLInvalidAuthorizationSpecException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import java.sql.*;
import java.util.Scanner;
import java.util.function.Supplier;

/**
 * Created by sigmund69 on 12.07.2016.
 */
public class CMDExecutor {
    private Connection connection;

    public CMDExecutor(Connection connection) {
        this.connection = connection;
    }
    /*Commands:
    1. help
    2. exit, -h
    3. list - print the list of all database tables
    4. find tableName - print table
    5. find tableName LIMITOFFSET
    6. update
    7. find tableName with conditions
    8. delete
    9. create table
    10. insert
    find NOTEXISTStable??
*/


    public ResultSet getTables() throws SQLException {
        ResultSet resultSet = null;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("show tables from " + connection.getCatalog());
            return resultSet;
        } catch (SQLException exc) {
            System.out.println("[ERROR]: Wrong sql syntax!");
            throw exc;
        } /*finally {
            statement.close();
        }*/
    }

    public String toString(ResultSet resultSet) throws SQLException {
        String resultString = "[";
        if (resultSet.first() == false) {
            return resultString + "]";
        }
        while (!resultSet.isLast()) {
            resultString += resultSet.getString(1) + ", ";
            resultSet.next();
        }
        resultString += resultSet.getString(1) + "]";
        return resultString;
    }

    public void createTable(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("CREATE TABLE ")) {
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Table was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }

    public void insert(String tablename) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("INSERT INTO" + connection.getSchema() + "." + tablename +
                     "(")) {
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Table was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }
}





/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");
        */
/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");*/















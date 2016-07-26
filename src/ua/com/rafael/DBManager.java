package ua.com.rafael;


import java.sql.*;
import java.util.Objects;

/**
 * Created by sigmund69 on 12.07.2016.
 */
public class DBManager {
    byte FIRST_COLUMN = 1;
    private Connection connection;

    /*Commands:
    1. help
    2. exit, -h
    3. ++list - print the list of all database tables
    4. find tableName - print table
    5. find tableName LIMITOFFSET
    6. update
    7. find tableName with conditions
    8. delete
    9. create table
    10. insert
    11. clear - delete all rows of current table
    find NOTEXISTStable??
*/

    public void connection(UserInfo userInfo) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("[ERROR]: MySQL driver was not registered!");
        }
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/" + userInfo.getDatabase() +
                            "?autoReconnect=true&useSSL=false", userInfo.getUser(), userInfo.getPassword());
            System.out.println("Your registration has been successful!");
        } catch (SQLException ex) {
            // handle any errors
            System.out.println(ex.getCause().getMessage());
            System.out.println("Please, check your database name, user name and password!");
        }
    }

    public String[] getTableList() throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("show tables from " + connection.getCatalog())) {
//          return null if resultSet is empty
            if (!resultSet.last()) {
                return null;
            }
            int tableLength = resultSet.getRow();
            int index = 0;
            String[] result = new String[tableLength];
            resultSet.first();
            for (int i = 0; i < tableLength; i++) {
                result[i] = resultSet.getString(FIRST_COLUMN);
                resultSet.next();
            }
            return result;
        } catch (SQLException exc) {
            System.out.println(exc.getCause().getMessage());
            throw exc;
        }
    }

    public Row[] getDataTable(String tablleName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + connection.getCatalog() + "."
                     + tablleName)) {
            if (!resultSet.last()) {
                return null;
            }
            int rowCount = resultSet.getRow();
            int columnCount = resultSet.getMetaData().getColumnCount();
            Row[] tables = new Row[rowCount];
            resultSet.first();
            for (int i = 0; i < rowCount; i++) {
                tables[i] = new Row(columnCount);
                for (int j = 0; j < tables[i].getColumnSize(); j++) {
                    tables[i].put(resultSet.getMetaData().getColumnName(j + 1), resultSet.getObject(j + 1));
                }
                resultSet.next();
            }
            return tables;
        } catch (SQLException exc) {
            throw exc; //todo exc
        }
    }

    public void createTable(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("CREATE TABLE ")) {
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Row was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }

    //    public
    public void insert(String tablename, Row newRow) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + connection.getCatalog() + "." + tablename +
                        "(" + getFormatedFieldNames(newRow.getNames(), "%s,") + ")" +
                        "VALUES (" + getFormatedValues(newRow.getData(), "?,") + ")")) {
//            todo make accodance to update()
            setObjectsToPreaparedStatememnt(newRow, preparedStatement);
            preparedStatement.execute();
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Row was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }

    public void setObjectsToPreaparedStatememnt(Row newRow, PreparedStatement preparedStatement) throws SQLException {
        Object[] newValues = newRow.getData();
        for (int i = 0; i < newRow.getColumnSize(); i++) {
            preparedStatement.setObject(i + 1, newValues[i]);
        }
    }


    public void update(String tableName, int id, Row newValue) throws SQLException {
        String primaryKeyName = getPrimaryKey(tableName);

        String query = "UPDATE " + tableName + " set " +
                getFormatedFieldNames(newValue.getNames(), "%s=?,") +
                " where " + primaryKeyName + "=" + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setObjectsToPreaparedStatememnt(newValue, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException exc) {
            throw exc;
        }
    }

    public void clear(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + connection.getCatalog() + "." + tableName);
        } catch (SQLException exc) {
            throw exc;
        }
    }

    public String getPrimaryKey(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW KEYS FROM " + connection.getCatalog() + "." +
                     tableName + " WHERE Key_name = 'PRIMARY'")) {
            if (isEmpty(resultSet)) {
                return null;
            }
            String primaryKeyName = resultSet.getString("Column_name");
            return primaryKeyName;
        } catch (SQLException exc) {
            throw exc;
        }
    }

    public String getFormatedValues(Object[] value, String format) {
        String result = "";
        for (int i = 0; i < value.length; i++) {
            result += String.format(format, value);
        }
        return result.substring(0, result.length() - 1);
    }

    public String getFormatedFieldNames(String[] names, String format) {
        String result = "";
        for (String name : names) {
            result += String.format(format, name);
        }
        return result.substring(0, result.length() - 1);
    }

    public boolean isEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();
    }

}



/*todo Is primary key usage important?*/

/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");
        */
/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");*/















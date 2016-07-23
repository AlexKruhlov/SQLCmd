package ua.com.rafael;


import java.sql.*;

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

    public Table[] getDataTable(String tablleName) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + connection.getCatalog() + "."
                     + tablleName)) {
            if (!resultSet.last()) {
                return null;
            }
            int rowCount = resultSet.getRow();
            int columnCount = resultSet.getMetaData().getColumnCount();
            Table[] tables = new Table[rowCount];
            resultSet.first();
            for (int i = 0; i < rowCount; i++) {
                tables[i] = new Table(columnCount);
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
            System.out.println("[Query syntax ERROR]: Table was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }

    //    public
    public void insert(String tablename) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("INSERT INTO" + connection.getCatalog() + "." + tablename +
                     "(")) {
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Table was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc;
        }
    }

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

    public void update(String tableName, int id, Table newValue) throws SQLException {
        String primaryKeyName = getPrimaryKey(tableName);

        String query = "UPDATE " + tableName + " set " +
                getFormatedFieldNames(newValue.getNames(), "%s=?,") +
                " where " + primaryKeyName + "=" + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            Object[] newObjects = newValue.getData();
            for (int i = 0; i < newValue.getColumnSize(); i++) {
                preparedStatement.setObject(i+1, newObjects[i]);
            }
            preparedStatement.execute();
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





/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");
        */
/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");*/















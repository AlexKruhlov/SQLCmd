package ua.com.rafael.manager;

import java.sql.*;

/**
 * Created by sigmund69 on 12.07.2016.
 */

public class MySqlDBManager implements DBManager {
    byte FIRST_COLUMN = 1;
    private Connection connection;

    @Override
    public Connection connection(String dataBase, String user, String password) {

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println("[ERROR]: MySQL driver was not registered!");
        }
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dataBase +
                            "?autoReconnect=true&useSSL=false", user, password);
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("Please, check your database name, user name and password!", ex);
        }
    }

    @Override
    public boolean isConnect() {
        return connection != null;
    }

    @Override
    public String[] getTableList() {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("show tables from " + connection.getCatalog())) {
            if (isEmpty(resultSet)) {
                return null;
            }
            resultSet.last();
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
            throw new RuntimeException("This command hasn't been peformed!", exc);
        }
    }

    @Override
    public Row[] getDataTable(String tablleName) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM " + connection.getCatalog() + "."
                     + tablleName)) {
            if (isEmpty(resultSet)) {
                return null;
            }
            resultSet.last();
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
            throw new RuntimeException("This command hasn't been peformed!", exc);
        }
    }

    @Override
    public void createTable(String[] columnsData) throws SQLException {
        final int TABLE_NAME = 0;


        String query = "CREATE TABLE";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("CREATE TABLE ")) {
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Row was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc; // TODO exception
        }
    }

    @Override
    public void insert(String tablename, Row newRow) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + connection.getCatalog() + "." + tablename +
                        "(" + getFormatedFieldNames(newRow.getNames(), "%s,") + ")" +
                        "VALUES (" + getFormatedValues(newRow.getData(), "?,") + ")")) {
            setObjectsToPreaparedStatememnt(newRow, preparedStatement);
            preparedStatement.execute();
        } catch (SQLSyntaxErrorException exc) {
            System.out.println("[Query syntax ERROR]: Row was not created.");
            throw exc;
        } catch (SQLException exc) {
            throw exc; //todo exception
        }
    }

    @Override
    public void update(String tableName, int id, Row newValue) throws SQLException {
        String primaryKeyName = getPrimaryKey(tableName);
        String query = "UPDATE " + tableName + " set " +
                getFormatedFieldNames(newValue.getNames(), "%s=?,") +
                " where " + primaryKeyName + "=" + id;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setObjectsToPreaparedStatememnt(newValue, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException exc) {
            throw exc; //todo exception
        }
    }

    @Override
    public void clear(String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + connection.getCatalog() + "." + tableName);
        } catch (SQLException exc) {
            throw exc; //todo exception
        }
    }

    @Override
    public String[] getColumnNames(String table) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW COLUMNS FROM " + table)) {
            if (isEmpty(resultSet)) {
                return null;
            }
            resultSet.last();
            int numberOfColumns = resultSet.getRow();
            String[] columnNames = new String[numberOfColumns];
            resultSet.first();
            for (int i = 0; i < numberOfColumns; i++) {
                columnNames[i] = resultSet.getString(FIRST_COLUMN);
                resultSet.next();
            }
            return columnNames;
        } catch (SQLException exc) {
            System.out.println("This table has not created");
            //todo exc
        }
        return null;
    }

    private void setObjectsToPreaparedStatememnt(Row newRow, PreparedStatement preparedStatement) throws SQLException {
        Object[] newValues = newRow.getData();
        for (int i = 0; i < newRow.getColumnSize(); i++) {
            preparedStatement.setObject(i + 1, newValues[i]);
        }
    }

    private String getPrimaryKey(String tableName) throws SQLException {  //todo think about method usage
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW KEYS FROM " + connection.getCatalog() + "." +
                     tableName + " WHERE Key_name = 'PRIMARY'")) {
            if (isEmpty(resultSet)) {
                return null;
            }
            String primaryKeyName = resultSet.getString("Column_name");
            return primaryKeyName;
        } catch (SQLException exc) {
            throw exc; //todo exception
        }
    }

    private String getFormatedValues(Object[] value, String format) {
        String result = "";
        for (int i = 0; i < value.length; i++) {
            result += String.format(format, value);
        }
        return result.substring(0, result.length() - 1);
    }

    private String getFormatedFieldNames(String[] names, String format) {
        String result = "";
        for (String name : names) {
            result += String.format(format, name);
        }
        return result.substring(0, result.length() - 1);
    }

    private boolean isEmpty(ResultSet resultSet) throws SQLException {
        return !resultSet.first();
    }
}

/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");
        */
/* TODO exc.getCause().getMessage();
        System.out.println("Please, use help information to deside this problem!");*/















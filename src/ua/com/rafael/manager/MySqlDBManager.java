package ua.com.rafael.manager;

import ua.com.rafael.controller.command.exception.SqlQueryException;

import java.sql.*;

public class MySqlDBManager implements DBManager {

    byte FIRST_COLUMN = 1;
    private Connection connection;

    @Override
    public Connection connection(String dataBase, String user, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception exc) {
            throw new RuntimeException("[ERROR]: MySQL driver was not registered!", exc);// can not find cases to test this line
        }
        try {
            connection =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dataBase +
                            "?autoReconnect=true&useSSL=false", user, password);
            return connection;
        } catch (SQLException exc) {
            throw new RuntimeException("Please, check your database name, user name and password!", exc);
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
            String[] result = new String[tableLength];
            resultSet.first();
            for (int i = 0; i < tableLength; i++) {
                result[i] = resultSet.getString(FIRST_COLUMN);
                resultSet.next();
            }
            return result;
        } catch (SQLException exc) {
            throw new SqlQueryException(exc); // can not find cases to test this line
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
                tables[i] = new Row();
                for (int j = 0; j < columnCount; j++) {
                    tables[i].put(resultSet.getMetaData().getColumnName(j + 1), resultSet.getObject(j + 1));
                }
                resultSet.next();
            }
            return tables;
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }

    @Override
    public void createTable(String[] tableData) {
        final int TABLE_NAME = 0;
        String query = "CREATE TABLE " + tableData[TABLE_NAME] + "(" + getColumnsProperties(tableData) + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }

    private String getColumnsProperties(String[] tableData) {
        final int NEXT_COLUMN = 2;
        String tableProperties = "";
        for (int columnName = 1; columnName < tableData.length; columnName += NEXT_COLUMN) {
            int columnType = columnName + 1;
            tableProperties += tableData[columnName] + " " + tableData[columnType] + " NOT NULL,";
        }
        tableProperties = tableProperties.substring(0, tableProperties.length() - 1);
        return tableProperties;
    }

    @Override
    public void insert(String tablename, Row newRow) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO " + connection.getCatalog() + "." + tablename +
                        "(" + getFormatedFieldNames(newRow.getNames(), "%s,") + ")" +
                        "VALUES (" + getFormatedValues(newRow.getData(), "?,") + ")")) {
            setObjectsToPreparedStatememnt(newRow, preparedStatement);
            preparedStatement.execute();
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }

    @Override
    public void update(String tableName, String keyColumnName, Object key, Row newValue) {
        String query = "UPDATE " + tableName + " set " +
                getFormatedFieldNames(newValue.getNames(), "%s=?,") +
                " where " + keyColumnName + "=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            setObjectsToPreparedStatememnt(newValue, preparedStatement);
            preparedStatement.setObject(newValue.getColumnSize() + 1, key);
            preparedStatement.execute();
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }

    @Override
    public void delete(String tableName, String keyColumnName, Object rowValueToDeleteRow) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " +
                connection.getCatalog() + "." + tableName + " WHERE " + keyColumnName + "=?")) {
            preparedStatement.setObject(1, rowValueToDeleteRow);
            preparedStatement.execute();
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }


    @Override
    public void clear(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM " + connection.getCatalog() + "." + tableName);
        } catch (SQLException exc) {
            throw new SqlQueryException(exc);
        }
    }

    @Override
    public String[] getColumnNames(String table) {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SHOW COLUMNS FROM " + table)) {
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
            throw new SqlQueryException(exc);
        }
    }

    @Override
    public void drop(String tableName) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE " + tableName);
        } catch (Exception exc) {
            throw new SqlQueryException(exc);
        }
    }

    private void setObjectsToPreparedStatememnt(Row newRow, PreparedStatement preparedStatement) throws SQLException {
        Object[] newValues = newRow.getData();
        for (int i = 0; i < newRow.getColumnSize(); i++) {
            preparedStatement.setObject(i + 1, newValues[i]);
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















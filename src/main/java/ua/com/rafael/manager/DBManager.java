package ua.com.rafael.manager;

import java.sql.Connection;

public interface DBManager {

    Connection connection(String dataBase, String user, String password);

    boolean isConnect();

    String[] getTableList();

    Row[] getDataTable(String tablleName);

    void createTable(String[] tableData);

    void insert(String tablename, Row newRow);

    void update(String tableName, String keyColumnName, Object key, Row newValue);

    void delete(String tableName, String keyColumnName, Object rowValueToDeleteRow);

    void clear(String tableName);

    String[] getColumnNames(String tableName);

    void drop(String tableName);
}

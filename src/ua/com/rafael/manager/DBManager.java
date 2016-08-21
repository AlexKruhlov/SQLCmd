package ua.com.rafael.manager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Alexandr Kruhlov on 27.07.2016.
 */
public interface DBManager {
    Connection connection(String dataBase, String user, String password);

    boolean isConnect();

    String[] getTableList();

    Row[] getDataTable(String tablleName);

    void createTable(String[] tableData);

    //    public
    void insert(String tablename, Row newRow);

    void update(String tableName,String keyColumnName ,int key, Row newValue) throws SQLException;

    void clear(String tableName);

    String[] getColumnNames(String tableName);

    void drop(String tableName);
}

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
    void insert(String tablename, Row newRow) throws SQLException;

    void update(String tableName, int id, Row newValue) throws SQLException;

    void clear(String tableName) throws SQLException;

    String[] getColumnNames(String tableName);

    void drop(String tableName);
}

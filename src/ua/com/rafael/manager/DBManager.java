package ua.com.rafael.manager;

import java.sql.SQLException;

/**
 * Created by Alexandr Kruhlov on 27.07.2016.
 */
public interface DBManager {
    void connection(String dataBase, String user, String password);

    String[] getTableList() throws SQLException;

    Row[] getDataTable(String tablleName) throws SQLException;

    void createTable(String tableName) throws SQLException;

    //    public
    void insert(String tablename, Row newRow) throws SQLException;

    void update(String tableName, int id, Row newValue) throws SQLException;

    void clear(String tableName) throws SQLException;
}

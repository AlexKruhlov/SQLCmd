package ua.com.rafael;

import com.mysql.jdbc.ResultSetImpl;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.*;

/**
 * Created by sigmund69 on 15.07.2016.
 */
public class CMDExecutorTest {

    @Test
    public void showTablesTest() throws Exception {
        Connection connection = connectionforTest(new UserInfo("myschema", "root", "independence24"));
        CMDExecutor executor = new CMDExecutor(connection);
        String expected = "[mytable, ok]";
        ResultSet resultSet = executor.getTables();
        Assert.assertEquals(expected, executor.toString(resultSet));

        connection = connectionforTest(new UserInfo("outoftables", "root", "independence24"));
        executor = new CMDExecutor(connection);
        expected = "[]";
        resultSet = executor.getTables();
        Assert.assertEquals(expected, executor.toString(resultSet));
    }

    @Test
    public void createTable() throws Exception {
        //executor.insert("mytable");
    }


    private Connection connectionforTest(UserInfo userInfo) {
        DBConnector.regDriver();
        return DBConnector.getConnection(userInfo);
    }


}
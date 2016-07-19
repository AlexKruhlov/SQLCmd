package ua.com.rafael;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by sigmund69 on 15.07.2016.
 */
public class CMDExecutorTest {
    DBManager dBase = new DBManager();

    @Before
    public void preSetup(){
        dBase.connection(new UserInfo("myschema", "root", "independence24"));
    }

    @Test
    public void showTablesTest() throws Exception {
        String expected = "[mytable, ok]";
        ResultSet resultSet = dBase.getTables();
        Assert.assertEquals(expected, dBase.toString(resultSet));

        dBase.connection(new UserInfo("outoftables","root","independence24"));
        expected = "[]";
        resultSet = dBase.getTables();
        Assert.assertEquals(expected, dBase.toString(resultSet));
    }

    @Test
    public void createTable() throws Exception {
        //executor.insert("mytable");
    }
    
}
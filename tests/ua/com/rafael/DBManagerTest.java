package ua.com.rafael;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by sigmund69 on 15.07.2016.
 */
public class DBManagerTest {
    DBManager dBase = new DBManager();

    @Before
    public void preSetup(){
        dBase.connection(new UserInfo("myschema", "root", "independence24"));
    }

    @Test
    public void getTableListTest() throws Exception {
        String[] expected = {"actor","address"};
        Assert.assertArrayEquals(expected, dBase.getTableList());

        dBase.connection(new UserInfo("outoftables","root","independence24"));
        expected = null;
        Assert.assertArrayEquals(expected, dBase.getTableList());
    }


    @Test
    public void getDataTable() throws Exception {
        
    }


    @Test
    public void createTable() throws Exception {
        //executor.insert("mytable");
    }



}
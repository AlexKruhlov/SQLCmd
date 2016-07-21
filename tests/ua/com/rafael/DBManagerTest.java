package ua.com.rafael;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Arrays;

/**
 * Created by sigmund69 on 15.07.2016.
 */
public class DBManagerTest {

    DBManager dBase = new DBManager();

    @Before
    public void preSetup() {
        dBase.connection(new UserInfo("myschema", "root", "independence24"));
    }

    @Test
    public void getTableListTest() throws Exception {
        String[] expected = {"actor", "address"};
        Assert.assertArrayEquals(expected, dBase.getTableList());

        dBase.connection(new UserInfo("outoftables", "root", "independence24"));
        expected = null;
        Assert.assertArrayEquals(expected, dBase.getTableList());
    }


    @Test
    public void getDataTableTest() throws Exception {
        Table[] expected = new Table[3];
        expected[0] = new Table(3);
        expected[0].put("actor_id", 1);
        expected[0].put("first_name", "PENELOPE");
        expected[0].put("last_name", "GUINESS");

        expected[1] = new Table(3);
        expected[1].put("actor_id", 2);
        expected[1].put("first_name", "NICK");
        expected[1].put("last_name", "WAHLBERG");

        expected[2] = new Table(3);
        expected[2].put("actor_id", 3);
        expected[2].put("first_name", "ED");
        expected[2].put("last_name", "CHASE");
        Table[] actual = dBase.getDataTable("actor");

        Assert.assertEquals(Arrays.toString(expected),Arrays.toString(actual));
    }

    @Test
    public void getFormatFields() throws Exception {
        Table[] tables = dBase.getDataTable("actor");
        String[] tableNames = tables[1].getNames();
        String actual = dBase.getFormatFields(tableNames,"%s=?,");

        String expected = "actor_id=?,first_name=?,last_name=?";

        Assert.assertEquals(expected,actual);
    }




}
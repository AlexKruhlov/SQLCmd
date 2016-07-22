package ua.com.rafael;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void getFormatFieldsTest() throws Exception {
        Table[] tables = dBase.getDataTable("actor");
        String[] tableNames = tables[1].getNames();
        String actual = dBase.getFormatedFieldNames(tableNames,"%s=?,");

        String expected = "actor_id=?,first_name=?,last_name=?";

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getPrimaryKeyTest() throws Exception {
        String[] tableNames = dBase.getTableList();
        String[] actual = new String[2];
        for (int i = 0; i<tableNames.length;i++){
            System.out.println(tableNames[0]+", "+tableNames[1]);
            actual[i] = dBase.getPrimaryKey(tableNames[i]);
            System.out.println(actual[i]);
        }

        String[] expected = {"actor_id", null};

        Assert.assertArrayEquals(expected,actual);

    }

    @Test
    public void update() throws Exception {
        Table[] toExpectation = dBase.getDataTable("actor");
        toExpectation[1] = new Table(3);
        toExpectation[1].put("actor_id", 2);
        toExpectation[1].put("first_name", "JACK");
        toExpectation[1].put("last_name", "BLACK");

        Table toUpdate = new Table(3);
        toUpdate.put("actor_id", 2);
        toUpdate.put("first_name", "JACK");
        toUpdate.put("last_name", "BLACK");
        dBase.update("actor",2,toUpdate);
        Table[] afterUpdate = dBase.getDataTable("actor");

        String[]
                expected = new String[3],
                actual = new String[3];

        for (int i = 0; i<actual.length;i++){
            expected[i]=toExpectation[i].toString();
            actual[i] = afterUpdate[i].toString();
        }

        Assert.assertArrayEquals(expected,actual);
    }
}































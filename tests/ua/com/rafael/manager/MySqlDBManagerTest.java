package ua.com.rafael.manager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 15.07.2016.
 */
public class MySqlDBManagerTest {

    MySqlDBManager dBase = new MySqlDBManager();

    @Before
    public void preSetup() throws SQLException {
        dBase.connection("myschema", "root", "independence24");
        dBase.createTable(new String[]{"test", "id", "int", "fname", "varchar(45)", "time", "float"});
    }

    @After
    public void finish() {
        dBase.drop("test");
    }

    @Test
    public void isConnectTest() throws Exception {
        boolean[] expected = {true, true, false};

        boolean[] actual = new boolean[3];
        actual[0] = dBase.isConnect();
        try {
            dBase.connection("schema", "root", "independence24");
        } catch (Exception ex) {
        }
        actual[1] = dBase.isConnect();
        dBase = new MySqlDBManager();
        actual[2] = dBase.isConnect();

        Assert.assertArrayEquals(expected, actual);

        dBase.connection("myschema", "root", "independence24");
    }

    @Test
    public void createTableTest() throws Exception {
        String[] input = new String[]{"student",
                "id", "int",
                "first_name", "varchar(45)",
                "second_name", "varchar(45)",
                "mark", "float"};
        String[] currentTables = dBase.getTableList();
        String[] expected = new String[currentTables.length + 1];
        System.arraycopy(currentTables, 0, expected, 0, currentTables.length);
        expected[expected.length - 1] = "student";
        Arrays.sort(expected);

        dBase.createTable(input);
        String[] actual = dBase.getTableList();

        dBase.drop("student");

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void getTableListTest() throws Exception {
        dBase.createTable(new String[]{"test1", "id", "int"});
        String[] expected = {"test", "test1"};
        String[] actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);

        dBase.drop("test");
        dBase.drop("test1");
        expected = null;
        actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);

        dBase.createTable(new String[]{"test", "id", "int"});
    }

    @Test
    public void getDataTableTest() throws Exception {
        Row[] expected = new Row[3];
        expected[0] = new Row(3);
        expected[0].put("id", 1);
        expected[0].put("fname", "PENELOPE");
        expected[0].put("time", 23.0);
        expected[1] = new Row(3);
        expected[1].put("id", 2);
        expected[1].put("fname", "NICK");
        expected[1].put("time", 17.0);
        expected[2] = new Row(3);
        expected[2].put("id", 3);
        expected[2].put("fname", "ED");
        expected[2].put("time", 15.5);

        for (Row row : expected) {
            dBase.insert("test", row);
        }

        Row[] actual = createActual("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));

        expected = null;
        actual = createActual("teat1");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    private Row[] createActual(String tablename) {
        Row[] actual = null;
        try {
            actual = dBase.getDataTable(tablename);
        } catch (Exception ecx) {
        }
        return actual;
    }

    @Test
    public void clearTest() throws Exception {
        Row[] expected = null;

        for (int i = 1; i <= 3; i++) {
            Row input = new Row(3);
            input.put("id", i);
            input.put("fname", "JACK");
            input.put("time", 17);
            dBase.insert("test", input);
        }
        dBase.clear("test");
        Row[] actual = dBase.getDataTable("test");

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void insertTest() throws Exception {
        Row input = new Row(3);
        input.put("id", 1);
        input.put("fname", "JACK");
        input.put("time", 33.1);
        Row[] expected = new Row[]{input};

        dBase.insert("test", input);
        Row[] actual = dBase.getDataTable("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void updateTest() throws Exception {
        Row input = new Row(3);
        input.put("id", 1);
        input.put("fname", "JACK");
        input.put("time", 15.0);
        dBase.insert("test", input);

        Row toUpdate = new Row(3);
        toUpdate.put("id", 1);
        toUpdate.put("fname", "Emma");
        toUpdate.put("time", 14.9);
        Row[] expected = new Row[]{toUpdate};

        dBase.update("test", "id", 1, toUpdate);
        Row[] actual = dBase.getDataTable("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void getColumnNamesTest() throws Exception {
        String[] expected = {"id", "fname", "time"};
        String[] actual = dBase.getColumnNames("test");
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void dropTest() throws Exception {
        String[] expected = new String[]{"test", "test1"};
        dBase.createTable(new String[]{"test1", "id", "int", "fname", "varchar(45)", "time", "float"});
        String[] actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);

        dBase.drop("test1");
        expected = new String[]{"test"};
        actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);
    }


//    @Test
//    public void getFormatFieldNamesTest() throws Exception {
//        Row input = new Row(3);
//        input.put("actor_id", 1);
//        input.put("first_name", "JACK");
//        input.put("last_name", "BLACK");
//        String[] tableNames = input.getNames();
//        String actual = dBase.getFormatedFieldNames(tableNames, "%s=?,");
//
//        String expected = "actor_id=?,first_name=?,last_name=?";
//
//        Assert.assertEquals(expected, actual);
//    }

//    @Test
//    public void getPrimaryKeyTest() throws Exception {
//        String[] tableNames = dBase.getTableList();
//        String[] actual = new String[2];
//        for (int i = 0; i < tableNames.length; i++) {
//            actual[i] = dBase.getPrimaryKey(tableNames[i]);
//        }
//
//        String[] expected = {"actor_id", null};
//
//        Assert.assertArrayEquals(expected, actual);
//    }

//    @Test
//    public void getFormatedValuesTest() throws Exception {
//        Row input = new Row(3);
//        input.put("actor_id", 2);
//        input.put("first_name", "JACK");
//        input.put("last_name", "BLACK");
//        String expected = "?,?,?";
//        String actual = dBase.getFormatedValues(input.getData(), "?,");
//        Assert.assertEquals(expected, actual);
//    }
}































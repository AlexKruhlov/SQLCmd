package ua.com.rafael.manager;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class MySqlDBManagerTest {

    private MySqlDBManager dBase = new MySqlDBManager();
    private final String dataBaseName = "myschema";
    private final String userName = "root";
    private final String password = "independence24";

    @Before
    public void preSetup() {
        dBase.connection(dataBaseName, userName, password);
        dBase.createTable(new String[]{"test", "id", "int", "fname", "varchar(45)", "time", "double"});
    }

    @After
    public void finish() {
        dBase.drop("test");
    }

    @Test
    public void isConnectTest() {
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

        dBase.connection(dataBaseName, userName, password);
    }

    @Test
    public void createTableTest() {
        String[] input = new String[]{"student",
                "id", "int",
                "first_name", "varchar(45)",
                "second_name", "varchar(45)",
                "mark", "double"};
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
    public void getTableListTest() {
        dBase.createTable(new String[]{"test1", "id", "int"});
        String[] expected = {"test", "test1"};
        String[] actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);

        dBase.drop("test");
        dBase.drop("test1");
        actual = dBase.getTableList();
        Assert.assertArrayEquals(null, actual);

        dBase.createTable(new String[]{"test", "id", "int"});
    }

    @Test
    public void getDataTableTest() {
        Row[] expected = new Row[3];
        expected[0] = new Row();
        expected[0].put("id", 1);
        expected[0].put("fname", "PENELOPE");
        expected[0].put("time", 23.0);
        expected[1] = new Row();
        expected[1].put("id", 2);
        expected[1].put("fname", "NICK");
        expected[1].put("time", 17.0);
        expected[2] = new Row();
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

    private Row[] createActual(String tableName) {
        Row[] actual = null;
        try {
            actual = dBase.getDataTable(tableName);
        } catch (Exception ecx) {
        }
        return actual;
    }

    @Test
    public void deleteTest() {
        Row[] expected = {createRow(1, "Alex", 14.31)};
        dBase.insert("test", createRow(1, "Alex", 14.31));
        dBase.insert("test", createRow(2, "Penny", 10.11));
        dBase.delete("test", "id", "2");
        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(dBase.getDataTable("test")));

        dBase.delete("test", "time", "14.31");
        Assert.assertArrayEquals(null, dBase.getDataTable("test"));
    }

    private Row createRow(int id, String fname, double time) {
        Row rowToInsert = new Row();
        rowToInsert.put("id", id);
        rowToInsert.put("fname", fname);
        rowToInsert.put("time", time);
        return rowToInsert;
    }

    @Test
    public void clearTest() {
        for (int i = 1; i <= 3; i++) {
            Row input = new Row();
            input.put("id", i);
            input.put("fname", "JACK");
            input.put("time", 17);
            dBase.insert("test", input);
        }
        dBase.clear("test");
        Row[] actual = dBase.getDataTable("test");

        Assert.assertArrayEquals(null, actual);
    }

    @Test
    public void insertTest() {
        Row input = new Row();
        input.put("id", 1);
        input.put("fname", "JACK");
        input.put("time", 33.1);
        Row[] expected = new Row[]{input};

        dBase.insert("test", input);
        Row[] actual = dBase.getDataTable("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void updateTest() {
        Row input = new Row();
        input.put("id", 1);
        input.put("fname", "JACK");
        input.put("time", 15.0);
        dBase.insert("test", input);

        Row toUpdate = new Row();
        toUpdate.put("id", 1);
        toUpdate.put("fname", "Emma");
        toUpdate.put("time", 14.1);
        Row[] expected = new Row[]{toUpdate};

        dBase.update("test", "fname", "JACK", toUpdate);
        Row[] actual = dBase.getDataTable("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));

        toUpdate = new Row();
        toUpdate.put("id", 1);
        toUpdate.put("fname", "STAN");
        toUpdate.put("time", 11.0);
        expected = new Row[]{toUpdate};

        dBase.update("test", "time", 14.1, toUpdate);
        actual = dBase.getDataTable("test");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void getColumnNamesTest() {
        String[] expected = {"id", "fname", "time"};
        String[] actual = dBase.getColumnNames("test");
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void dropTest() {
        String[] expected = new String[]{"test", "test1"};
        dBase.createTable(new String[]{"test1", "id", "int", "fname", "varchar(45)", "time", "double"});
        String[] actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);

        dBase.drop("test1");
        expected = new String[]{"test"};
        actual = dBase.getTableList();
        Assert.assertArrayEquals(expected, actual);
    }
}































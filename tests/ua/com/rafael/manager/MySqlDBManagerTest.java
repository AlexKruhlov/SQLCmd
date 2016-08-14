package ua.com.rafael.manager;

import com.sun.deploy.association.AssociationAlreadyRegisteredException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.manager.Row;

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
        dBase.clear("actor");
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
    }

    @Test
    public void createTable() throws Exception {
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
        Assert.assertArrayEquals(expected, actual);

    }

    @Test
    public void getTableListTest() throws Exception {
        String[] expected = {"actor", "address"};
        Assert.assertArrayEquals(expected, dBase.getTableList());

        dBase.connection("outoftables", "root", "independence24");
        expected = null;
        Assert.assertArrayEquals(expected, dBase.getTableList());
    }

    @Test
    public void getDataTableTest() throws Exception {
        Row[] expected = new Row[3];
        expected[0] = new Row(3);
        expected[0].put("actor_id", 1);
        expected[0].put("first_name", "PENELOPE");
        expected[0].put("last_name", "GUINESS");
        expected[1] = new Row(3);
        expected[1].put("actor_id", 2);
        expected[1].put("first_name", "NICK");
        expected[1].put("last_name", "WAHLBERG");
        expected[2] = new Row(3);
        expected[2].put("actor_id", 3);
        expected[2].put("first_name", "ED");
        expected[2].put("last_name", "CHASE");

        for (Row row : expected) {
            dBase.insert("actor", row);
        }
        Row[] actual = dBase.getDataTable("actor");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void clearTest() throws Exception {
        Row[] expected = null;

        for (int i = 1; i <= 3; i++) {
            Row input = new Row(3);
            input.put("actor_id", i);
            input.put("first_name", "JACK");
            input.put("last_name", "BLACK");
            dBase.insert("actor", input);
        }
        dBase.clear("actor");
        Row[] actual = dBase.getDataTable("actor");

        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void insertTest() throws Exception {
        Row input = new Row(3);
        input.put("actor_id", 1);
        input.put("first_name", "JACK");
        input.put("last_name", "BLACK");
        Row[] expected = new Row[]{input};

        dBase.insert("actor", input);
        Row[] actual = dBase.getDataTable("actor");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void updateTest() throws Exception {
        Row input = new Row(3);
        input.put("actor_id", 1);
        input.put("first_name", "JACK");
        input.put("last_name", "BLACK");
        dBase.insert("actor", input);

        Row toUpdate = new Row(3);
        toUpdate.put("actor_id", 1);
        toUpdate.put("first_name", "Emma");
        toUpdate.put("last_name", "Cloud");
        Row[] expected = new Row[]{toUpdate};

        dBase.update("actor", 1, toUpdate);
        Row[] actual = dBase.getDataTable("actor");

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(actual));
    }

    @Test
    public void getColumbNamesTest() throws Exception {
        String[] expected = {"actor_id", "first_name", "last_name"};
        String[] actual = dBase.getColumnNames("actor");
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void drop() throws Exception {
        String[] expected = dBase.getTableList();

        dBase.createTable(new String[]{"test", "id", "int", "fname", "varchar(45)", "time", "float"});
        dBase.drop("test");

        String[] actual = dBase.getTableList();

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































package ua.com.rafael.manager;

import org.junit.Assert;
import org.junit.Test;

public class RowTest {

    @Test
    public void getNamesTest() {
        Row row = new Row();
        row.put("id", 1);
        row.put("name", "Ema");
        row.put("age", 17);
        String[] expected = {"id", "name", "age"};
        String[] actual = row.getNames();
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void getDataTest() {
        Row row = new Row();
        row.put("id", 1);
        row.put("name", "Ema");
        row.put("age", 17);
        Object[] expected = {1, "Ema", 17};
        Object[] actual = row.getData();
        Assert.assertArrayEquals(expected, actual);
    }
}
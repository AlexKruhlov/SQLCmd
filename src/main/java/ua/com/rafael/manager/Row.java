package ua.com.rafael.manager;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Row {

    private final Map<String, Object> dataTable;

    public Row() {
        this.dataTable = new LinkedHashMap<>();
    }

    public int getColumnSize() {
        return dataTable.size();
    }

    public String[] getNames() {
        Object[] names = dataTable.keySet().toArray();
        String[] results = new String[dataTable.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = String.valueOf(names[i]);
        }
        return results;
    }

    public Object[] getData() {
        return dataTable.values().toArray();
    }

    @Override
    public String toString() {
        return "Row{" +
                "Names: " + Arrays.toString(getNames()) + "\n" +
                "Values: " + Arrays.toString(getData()) + " }";
    }

    public void put(String columnName, Object columnValue) {
        dataTable.put(columnName, columnValue);
    }
}

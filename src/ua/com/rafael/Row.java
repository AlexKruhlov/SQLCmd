package ua.com.rafael;

import java.util.Arrays;

/**
 * Created by sigmund69 on 19.07.2016.
 */
public class Row {
    private DataTable[] dataTable; //// TODO: 20.07.2016
    private int index = 0;
    private int columnSize = 0;

    public Row(int columnSize) {
        this.columnSize = columnSize;
        dataTable = new DataTable[this.columnSize];
    }

    public int getColumnSize() {
        return columnSize;
    }

    class DataTable {
        private String name;
        private Object data;

        public DataTable(String name, Object data) {
            this.name = name;
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public Object getData() {
            return data;
        }
    }

    public String[] getNames() {
        String[] names = new String[columnSize];
        for (int i = 0; i < columnSize; i++) {
            names[i] = dataTable[i].getName();
        }
        return names;
    }

    public Object[] getData() {
        Object[] values = new Object[columnSize];
        for (int i = 0; i < columnSize; i++) {
            values[i] = dataTable[i].getData();
        }
        return values;
    }

    @Override
    public String toString() {
        return "Row{"+
        "Names: "+Arrays.toString(getNames()) +"\n"+
         "Values: "+Arrays.toString(getData())+ "\n"+'}';
    }

    public void put(String name, Object data) {
        dataTable[index++] = new DataTable(name, data);
    }

}

package ua.com.rafael;

import java.util.Arrays;

/**
 * Created by sigmund69 on 19.07.2016.
 */
public class Table {
    private DataTable[] dataTable; //// TODO: 20.07.2016
    private int index = 0;
    private int rowSize = 0;

    public Table(int rowSize) {
        this.rowSize = rowSize;
        dataTable = new DataTable[this.rowSize];
    }

    public int getRowSize() {
        return rowSize;
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
        String[] names = new String[rowSize];
        for (int i = 0; i < rowSize; i++) {
            names[i] = dataTable[i].getName();
        }
        return names;
    }

    public Object[] getData() {
        Object[] values = new Object[rowSize];
        for (int i = 0; i < rowSize; i++) {
            values[i] = dataTable[i].getData();
        }
        return values;
    }

    @Override
    public String toString() {
        return "Table{"+
        Arrays.toString(getNames()) +"\n"+
                Arrays.toString(getData())+
                "\n"+'}';
    }

    public void put(String name, Object data) {
        dataTable[index++] = new DataTable(name, data);
    }


}

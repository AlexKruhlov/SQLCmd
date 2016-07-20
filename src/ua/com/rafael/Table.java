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

        @Override
        public String toString() {
            return "DataTable{" +
                    "name='" + name + '\'' +
                    ", data=" + data.toString() +
                    '}';
        }
    }

    public void put(String name, Object data) {
        dataTable[index++] = new DataTable(name, data);
    }


}

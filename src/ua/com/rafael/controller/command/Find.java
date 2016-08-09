package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class Find implements Command {
    private View view;
    private DBManager dbManager;

    private final String command = "find command";

    public Find(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        final byte COMMAND = 0;
        String[] commandElements = command.split(" ");
        String[] thisCommandElements = this.command.split(" ");
        return commandElements.length == thisCommandElements.length &&
                commandElements[COMMAND].equals(thisCommandElements[COMMAND]);
    }

    @Override
    public void start(String command) {
        String[] commandElements = command.split(" ");
        final byte TABLE_NAME = 1;

        String[] columnNames = dbManager.getColumnNames(commandElements[TABLE_NAME]);
        if (columnNames == null) {
            view.print("Table has not created");
            return;
        }

        final int
                COLUMN_SIZE = 40,
                LINE_LENGTH_FOR_ONE_COLUMN = COLUMN_SIZE + 2,
                LINE_LENGTH = LINE_LENGTH_FOR_ONE_COLUMN * columnNames.length - 1;
        printTableLine(LINE_LENGTH);
        printTableTop(columnNames, COLUMN_SIZE);
        printTableLine(LINE_LENGTH);
        printTableRows(commandElements[TABLE_NAME],COLUMN_SIZE);
        printTableLine(LINE_LENGTH);
    }

    private void printTableLine(int length) {
//        char[] result = new char[length];
        view.print("\t|");
        for (int i = 0; i < length; i++) {
//            result[i] = '-';
            view.print("-");
        }
        view.print("|\n");
    }

    private void printTableTop(String[] columnNames, int columnSize) {
        String format = " %-" + columnSize + "s";
        view.print("\t|");
        for (int i = 0; i < columnNames.length; i++) {
            view.print(String.format(format, columnNames[i]) + "|");
        }
        view.print("\n");
    }

    private void printTableRows(String tableName, int columnSize) {
        Row[] rows = dbManager.getDataTable(tableName);
        if (rows != null) {
            String format = " %-" + columnSize + "s";
            for (int i = 0; i < rows.length; i++) {
                view.print("\t|");
                Object[] rowData = rows[i].getData();
                for (int j = 0; j < rowData.length; j++) {
                    view.print(String.format(format, rowData[j]) + "|");
                }
                view.print("\n");
            }
        }
    }
}















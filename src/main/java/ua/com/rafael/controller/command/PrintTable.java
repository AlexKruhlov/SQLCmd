package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

public class PrintTable extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public PrintTable(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "print" + SIGN_FOR_SPLIT + "[table_name]";
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (isNotTheSameSize(commandModelElements, commandElements)) {
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }
        final int TABLE_NAME_INDEX = 1;
        final String[] columnNames = dbManager.getColumnNames(commandElements[TABLE_NAME_INDEX]);
        final int
                COLUMN_SIZE = 40,
                LINE_LENGTH_FOR_ONE_COLUMN = COLUMN_SIZE + 2,
                LINE_LENGTH = LINE_LENGTH_FOR_ONE_COLUMN * columnNames.length - 1;
        printTableLine(LINE_LENGTH);
        printTableTop(columnNames, COLUMN_SIZE);
        printTableLine(LINE_LENGTH);
        printTableRows(commandElements[TABLE_NAME_INDEX], COLUMN_SIZE);
        printTableLine(LINE_LENGTH);
    }

    private void printTableLine(int length) {
        view.print("\t|");
        for (int i = 0; i < length; i++) {
            view.print("-");
        }
        view.print("|\n");
    }

    private void printTableTop(String[] columnNames, int columnSize) {
        String format = " %-" + columnSize + "s";
        view.print("\t|");
        for (String columnName : columnNames) {
            view.print(String.format(format, columnName) + "|");
        }
        view.print("\n");
    }

    private void printTableRows(String tableName, int columnSize) {
        Row[] rows = dbManager.getDataTable(tableName);
        if (rows != null) {
            String format = " %-" + columnSize + "s";
            for (Row row : rows) {
                view.print("\t|");
                Object[] rowData = row.getData();
                for (Object aRowData : rowData) {
                    view.print(String.format(format, aRowData) + "|");
                }
                view.print("\n");
            }
        }
    }

    @Override
    public String getHelp() {
        return commandModel +
                "\n\t\tdisplays data of the given table (table name).";
    }
}















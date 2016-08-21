package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class Find extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Find(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "find table_Name";

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);
        final byte TABLE_NAME = 1;

        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }

        final String[] columnNames = dbManager.getColumnNames(commandElements[TABLE_NAME]);

        final int
                COLUMN_SIZE = 40,
                LINE_LENGTH_FOR_ONE_COLUMN = COLUMN_SIZE + 2,
                LINE_LENGTH = LINE_LENGTH_FOR_ONE_COLUMN * columnNames.length - 1;
        printTableLine(LINE_LENGTH);
        printTableTop(columnNames, COLUMN_SIZE);
        printTableLine(LINE_LENGTH);
        printTableRows(commandElements[TABLE_NAME], COLUMN_SIZE);
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















package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 16.08.2016.
 */
public class Insert extends ConsoleCommand{

    private View view;
    private DBManager dbManager;

    public Insert(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "insert table_name column_name column_value";
    private final String[] columnType = {"int", "float", "varchar"};

    @Override
    public boolean isValid(String command) {
        final int COMMAND_INDEX = 0;
        String[] commandModelElements = commandModel.split(" ");
        String[] commandElements = command.split(" ");
        return commandModelElements[COMMAND_INDEX].equals(commandElements[COMMAND_INDEX]);
    }

    @Override
    public void start(String command) {
        String[] commandElements = command.split(" ");
//        if (!isValidSizeAndColumnType(commandElements)) {
//            view.print("Command error. Please, check the command parameters.");
//            return;
//        }
        dbManager.insert(commandElements[TABLE_NAME_INDEX],getRowForInsertion(commandElements));
        view.print("The table gets new row.");

    }

    private Row getRowForInsertion(final String[] commandElements) {
        int columnNameIndex = 2;
        final byte NEXT_COLUMN = 2;
        final int numberOfValues = (commandElements.length - columnNameIndex) / 2;
        Row row = new Row(numberOfValues);
        while (columnNameIndex < commandElements.length) {
            int columnValueIndex = columnNameIndex + 1;
            row.put(commandElements[columnNameIndex], commandElements[columnValueIndex]);
            columnNameIndex += NEXT_COLUMN;
        }
        return row;
    }


}

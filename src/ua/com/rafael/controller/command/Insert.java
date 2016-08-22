package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 16.08.2016.
 */
public class Insert extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Insert(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "insert table_name column_name column_value";

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);

        if (!isValidSize(commandModelElements,commandElements)){
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }

        dbManager.insert(commandElements[TABLE_NAME_INDEX], createRowForInsertion(commandElements));
        view.print("The table has got new row.");
    }

    private Row createRowForInsertion(final String[] commandElements) {
        int columnNameIndex = 2;
        final byte NEXT_COLUMN = 2;
        final int numberOfValues = (commandElements.length - columnNameIndex) / 2;
        final Row row = new Row(numberOfValues);
        while (columnNameIndex < commandElements.length) {
            int columnValueIndex = columnNameIndex + 1;
            row.put(commandElements[columnNameIndex], commandElements[columnValueIndex]);
            columnNameIndex += NEXT_COLUMN;
        }
        return row;
    }


}

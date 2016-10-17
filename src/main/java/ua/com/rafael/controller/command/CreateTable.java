package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

public class CreateTable extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public CreateTable(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "create" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[column_name]" +
                SIGN_FOR_SPLIT + "[column_data_type]";
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (isInvalidSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }
        if (!isColumnType(commandElements)) {
            view.print(INCORRECT_COLUMN_TYPE);
            return;
        }
        final int TABLE_NAME_INDEX = 1;
        final String[] argumentsForNewTable
                = Arrays.copyOfRange(commandElements, TABLE_NAME_INDEX, commandElements.length);
        dbManager.createTable(argumentsForNewTable);
        view.print("Table " + commandElements[TABLE_NAME_INDEX] + " was created");
    }

    @Override
    public String getHelp() {
        return commandModel + " ..." +
                "\n\t\tcreates a table with user pointed columns (table name must consist of one word)." +
                "\n\t\tTypes of column: int - integer, varchar([size]) - string with size," +
                "\n\t\tdouble - floating point number." +
                "\n\t\tExample: create student id int first_name varchar(45) mark double";
    }
}










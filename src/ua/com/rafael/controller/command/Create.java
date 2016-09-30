package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

public class Create extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Create(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "create table id int";

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);
        if (!isValidSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }
        if (!isColumnType(commandElements)) {
            view.print(INCORRECT_COLUMN_TYPE);
            return;
        }
        final String[] argumentsForNewTable = Arrays.copyOfRange(commandElements, TABLE_NAME_INDEX, commandElements.length);
        dbManager.createTable(argumentsForNewTable);
        view.print("Table " + commandElements[TABLE_NAME_INDEX] + " was created");
    }
}










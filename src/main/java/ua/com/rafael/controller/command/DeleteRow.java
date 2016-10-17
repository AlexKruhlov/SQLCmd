package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class DeleteRow extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public DeleteRow(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "delete" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[column_name]" +
                SIGN_FOR_SPLIT + "[row_value]";
    }

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (isNotTheSameSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }
        final int TABLE_NAME_INDEX = 1;
        final int COLUMN_NAME_INDEX = 2;
        final int VALUE_INDEX = 3;
        dbManager.delete(commandElements[TABLE_NAME_INDEX],
                commandElements[COLUMN_NAME_INDEX], commandElements[VALUE_INDEX]);
        view.print("The row was deleted.");
    }

    @Override
    public String getHelp() {
        return commandModel +
                "\n\t\tdeletes in pointed table row that contains pointed value (row value)" +
                "\n\t\tin pointed column (column name).";
    }
}
















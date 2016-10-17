package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class DeleteRow extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public DeleteRow(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        return compareCommandName(getCommandModel(), command);
    }

    @Override
    public void start(String command) {
        final String[] commandModelElements = getCommandElements(getCommandModel());
        final String[] commandElements = getCommandElements(command);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }
        dbManager.delete(commandElements[TABLE_NAME_INDEX],
                commandElements[COLUMN_NAME_INDEX], commandElements[VALUE_INDEX]);
        view.print("The row was deleted.");
    }

    @Override
    public String getCommandModel() {
        return "delete" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[column_name]" +
                SIGN_FOR_SPLIT + "[row_value]";
    }

    @Override
    public String getHelp() {
        return getCommandModel() +
                "\n\t\tdeletes in pointed table row that contains pointed value (row value)" +
                "\n\t\tin pointed column (column name).";
    }
}
















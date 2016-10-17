package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class InsertRow extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public InsertRow(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "insert" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[column_name]" +
                SIGN_FOR_SPLIT + "[column_value]";
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
        final int TABLE_NAME_INDEX = 1;
        final int COLUMN_NAME_INDEX = 2;
        dbManager.insert(commandElements[TABLE_NAME_INDEX],
                createRowForInsertionOrToUpdate(commandElements, COLUMN_NAME_INDEX));
        view.print("The table has got new row.");
    }

    @Override
    public String getHelp() {
        return commandModel + " ..." +
                "\n\t\tinserts a new row with data into table.";
    }
}

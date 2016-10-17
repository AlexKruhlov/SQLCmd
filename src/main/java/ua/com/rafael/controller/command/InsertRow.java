package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class InsertRow extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public InsertRow(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(getCommandModel(), command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(getCommandModel());
        final String[] commandElements = getCommandElements(command);
        if (!isValidSize(commandModelElements, commandElements)) {
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
    public String getCommandModel() {
        return "insert" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[column_name]" +
                SIGN_FOR_SPLIT + "[column_value]";
    }

    @Override
    public String getHelp() {
        return getCommandModel() + " ..." +
                "\n\t\tinserts a new row with data into table.";
    }
}

package ua.com.rafael.controller.command;


import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class ClearTable extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public ClearTable(View view, DBManager dbManager) {
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
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }
        dbManager.clear(commandElements[TABLE_NAME_INDEX]);
        view.print("This table was cleared.");
    }

    @Override
    public String getCommandModel() {
        return "clear" + SIGN_FOR_SPLIT + "[table_name]";
    }

    @Override
    public String getHelp() {
        return getCommandModel() +
                "\n\t\tdeletes all rows in pointed table (table name).";
    }
}








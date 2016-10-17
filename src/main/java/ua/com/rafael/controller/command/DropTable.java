package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class DropTable extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public DropTable(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "drop" + SIGN_FOR_SPLIT + "[table_name]";
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (isNotTheSameSize(commandModelElements, commandElements)) {
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }
        final int TABLE_NAME_INDEX = 1;
        dbManager.drop(commandElements[TABLE_NAME_INDEX]);
        view.print("The table was deleted.");
    }

    @Override
    public String getHelp() {
        return commandModel +
                "\n\t\tdeletes a pointed table of current database.";
    }
}

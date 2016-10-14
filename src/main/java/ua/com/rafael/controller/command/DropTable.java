package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class DropTable extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public DropTable(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "drop table_name";

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }
        dbManager.drop(commandElements[TABLE_NAME_INDEX]);
        view.print("The table was deleted.");
    }

    @Override
    public String getHelp() {
        return "drop [table name]" +
                "\n\t\tdeletes a pointed table of current database.";
    }
}

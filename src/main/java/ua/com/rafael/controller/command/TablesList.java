package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

public class TablesList extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public TablesList(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "list";
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
            view.print(NO_PARAMETER_MESSAGE);
            return;
        }
        final String[] tableList = dbManager.getTableList();
        if (tableList == null) {
            view.print("Current database haven't any table");
            return;
        }
        view.print(Arrays.toString(tableList));
    }

    @Override
    public String getHelp() {
        return commandModel +
                "\n\t\tdisplays all tables names of the current database.";
    }
}

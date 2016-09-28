package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 28.09.2016.
 */
public class Delete extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Delete(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "delete table column value";

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);

        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }

        dbManager.delete(commandElements[TABLE_NAME_INDEX],
                commandElements[COLUMN_NAME_INDEX], commandElements[VALUE_INDEX]);

        view.print("The row was deleted.");
    }
}
















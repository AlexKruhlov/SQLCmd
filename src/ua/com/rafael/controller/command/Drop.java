package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 14.08.2016.
 */
public class Drop extends ConsoleCommand {
    private final View view;
    private final DBManager dbManager;

    public Drop(View view, DBManager dbManager) {
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
        final int TABLE_NAME_INDEX = 1;
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);
        if (!isTheSameSize(commandModelElements,commandElements)){
            view.print(ONE_PARAMETER_MESSAGE);
            return;
        }
        dbManager.drop(commandElements[TABLE_NAME_INDEX]);
        view.print("The table was deleted.");
    }
}

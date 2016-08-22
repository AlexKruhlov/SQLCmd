package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class List extends ConsoleCommand {
    private final View view;
    private final DBManager dbManager;

    private final String commandModel = "list";

    public List(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);

        if (!isTheSameSize(commandModelElements, commandElements)) {
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
}

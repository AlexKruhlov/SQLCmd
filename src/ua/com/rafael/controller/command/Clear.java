package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 14.08.2016.
 */
public class Clear extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Clear(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "clear table_name";

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
        if (!isTableExist(commandElements[TABLE_NAME_INDEX], dbManager.getTableList())) {
            view.print(TABLE_NOT_EXIST_MESSAGE);
            return;
        }
        dbManager.clear(commandElements[TABLE_NAME_INDEX]);
        view.print("This table was cleared.");
    }
}

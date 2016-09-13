package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 09.09.2016.
 */
public class Update extends ConsoleCommand {
    private final View view;
    private final DBManager dbManager;

    public Update(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "update table key_column key_value column_name_for_new_value new_value";

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);

        if (!isValidSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }

        final int keyColumnIndex = 2;
        final int keyValueIndex = keyColumnIndex + 1;
        final int firstColumnNameIndex = 4;
        dbManager.update(commandElements[TABLE_NAME_INDEX], commandElements[keyColumnIndex],
                commandElements[keyValueIndex], createRowForInsertionOrToUpdate(commandElements, firstColumnNameIndex));
        view.print("The table has updated.");
    }
}

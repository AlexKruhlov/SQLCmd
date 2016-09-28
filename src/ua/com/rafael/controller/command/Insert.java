package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 16.08.2016.
 */
public class Insert extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Insert(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "insert table_name column_name column_value";

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);

        if (!isValidSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }

        int columnNameIndex = 2;

        dbManager.insert(commandElements[TABLE_NAME_INDEX],
                createRowForInsertionOrToUpdate(commandElements, columnNameIndex));
        view.print("The table has got new row.");
    }


}

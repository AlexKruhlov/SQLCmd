package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class UpdateRow extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;
    private final String commandModel;

    public UpdateRow(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commandModel = "update" + SIGN_FOR_SPLIT + "[table_name]" + SIGN_FOR_SPLIT + "[key_column]" + SIGN_FOR_SPLIT +
                "[key_value]" + SIGN_FOR_SPLIT + "[column_name_for_new_value]" + SIGN_FOR_SPLIT + "[new_value]";
    }

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (isInvalidSize(commandModelElements, commandElements)) {
            view.print(MANY_PARAMETERS_MESSAGE);
            return;
        }
        final int TABLE_NAME_INDEX = 1;
        final int COLUMN_NAME_INDEX = 2;
        final int VALUE_INDEX = 3;
        final int FIRST_COLUMN_NAME_INDEX = 4;
        dbManager.update(commandElements[TABLE_NAME_INDEX], commandElements[COLUMN_NAME_INDEX],
                commandElements[VALUE_INDEX],
                createRowForInsertionOrToUpdate(commandElements, FIRST_COLUMN_NAME_INDEX));
        view.print("The table was updated.");
    }

    @Override
    public String getHelp() {
        return commandModel + " ..." +
                "\n\t\tsets inputted values (new value) into row that has pointed value (key value) in pointed column" +
                "\n\t\t(key column).Example:" +
                "\n\t\tupdate test id 1 id 1 fname John weight 90.5";
    }
}

package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

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
        final int FIRST_COLUMN_NAME_INDEX = 4;
        dbManager.update(commandElements[TABLE_NAME_INDEX], commandElements[COLUMN_NAME_INDEX],
                commandElements[VALUE_INDEX],
                createRowForInsertionOrToUpdate(commandElements, FIRST_COLUMN_NAME_INDEX));
        view.print("The table was updated.");
    }

    @Override
    public String getHelp() {
        return "update [table name] [key column] [key value] [column name for new value] [new value] ..." +
                "\n\t\tsets inputed values (new value) into row that has pointed value (key value) in pointed column" +
                "\n\t\t(key column).Example:" +
                "\n\t\tupdate test id 1 id 1 fname John weight 90.5";
    }
}

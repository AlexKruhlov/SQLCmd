package ua.com.rafael.controller.command;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 17.08.2016.
 */
abstract class ConsolCommand implements Command {

    protected final int
            COMMAND_NAME_INDEX = 0,
            TABLE_NAME_INDEX = 1;

    protected final String SIGN_FOR_SPLIT = " ";

    private final String[] columnType = {"int", "float", "varchar"};

    public boolean compareCommandName(String commandModel, String command) {
        final int COMMAND_INDEX = 0;
        String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        String[] commandElements = command.split(SIGN_FOR_SPLIT);
        return commandModelElements[COMMAND_INDEX].equals(commandElements[COMMAND_INDEX]);
    }

    public boolean isValidSize(String[] commandModelElements, String[] commandElements) {
        return commandElements.length >= commandModelElements.length &&
                commandElements.length % 2 == commandModelElements.length % 2;
    }

    public boolean isTheSameSize(String[] commandModelElements, String[] commandElements) {
        return commandElements.length == commandModelElements.length;
    }

    public boolean isColumnType(String[] commandElements) {
        final byte
                TYPE_WITHOUT_SIZE = 0,
                FIRST_TYPE = 3,
                NEXT_TYPE = 2;

        for (int currentType = FIRST_TYPE; currentType < commandElements.length; currentType += NEXT_TYPE) {
            String[] typeElements = commandElements[currentType].split("[(]");
            if (Arrays.binarySearch(columnType, typeElements[TYPE_WITHOUT_SIZE]) == -1) {
                return false;
            }
        }
        return true;
    }

    public boolean isTableExist(final String tableName, final String[] databaseTableList) {
        return Arrays.binarySearch(databaseTableList, tableName) == -1 ? false : true;
    }


}

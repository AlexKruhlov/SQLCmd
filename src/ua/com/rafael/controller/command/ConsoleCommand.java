package ua.com.rafael.controller.command;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 17.08.2016.
 */
abstract class ConsoleCommand implements Command {

    public static final int
            COMMAND_NAME_INDEX = 0,
            TABLE_NAME_INDEX = 1;

    public static final String SIGN_FOR_SPLIT = " ";

    public static final String
            NO_PARAMETER_MESSAGE = "Command error. This command hasn't any parameters.",
            ONE_PARAMETER_MESSAGE = "Command error. This command must have one parameter.",
            MANY_PARAMETERS_MESSAGE = "Command error. Please, check the number of command parameters.",
            TABLE_NOT_EXIST_MESSAGE = "Command error. This table does not exist in current database.",
            INCORRECT_COLUMN_TYPE = "Some of these column types are incorrect";

    private static final String[] correctColumnTypes = {"int", "float", "varchar"};

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
                FIRST_TYPE = 3,
                NEXT_TYPE = 2;

        final byte TYPE_WITHOUT_SIZE = 0;

        for (int currentType = FIRST_TYPE; currentType < commandElements.length; currentType += NEXT_TYPE) {
            String[] typeElements = commandElements[currentType].split("[(]");
            if (!isCorrectColumnType(typeElements[TYPE_WITHOUT_SIZE])) {
                return false;
            }
        }
        return true;
    }

    private boolean isCorrectColumnType(String currentColumnType) {

        String[] sortedCorrectColumnTypes = Arrays.copyOf(correctColumnTypes, correctColumnTypes.length);
        Arrays.sort(sortedCorrectColumnTypes);

        int resultOfTypeSerching = Arrays.binarySearch(sortedCorrectColumnTypes, currentColumnType);

        final int FIRST_CONDITION_ELEMENT_NOT_FOUND = -1,
                SECOND_CONDITION_ELEMENT_NOT_FOUND = sortedCorrectColumnTypes.length;

        if (resultOfTypeSerching <= FIRST_CONDITION_ELEMENT_NOT_FOUND ||
                resultOfTypeSerching == SECOND_CONDITION_ELEMENT_NOT_FOUND) {
            return false;
        } else {
            return true;
        }
    }
}

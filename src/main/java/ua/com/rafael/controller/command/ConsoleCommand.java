package ua.com.rafael.controller.command;

import org.apache.commons.lang3.StringUtils;
import ua.com.rafael.manager.Row;

import java.util.Arrays;

abstract class ConsoleCommand implements Command {

    public final String SIGN_FOR_SPLIT = " ";

    public final String
            NO_PARAMETER_MESSAGE = "Command error. This command hasn't any parameters.",
            ONE_PARAMETER_MESSAGE = "Command error. This command must have one parameter.",
            MANY_PARAMETERS_MESSAGE = "Command error. Please, check the number of command parameters.",
            INCORRECT_COLUMN_TYPE = "Some of these column types are incorrect";

    private final String[] correctColumnTypes = {"int", "double", "varchar"};

    public boolean compareCommandName(String commandModel, String command) {
        final int COMMAND_INDEX = 0;
        String[] commandModelElements = getCommandElements(commandModel);
        String[] commandElements = getCommandElements(command);
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
            String[] typeElements = StringUtils.split(commandElements[currentType], '(');
            if (!isCorrectColumnType(typeElements[TYPE_WITHOUT_SIZE])) {
                return false;
            }
        }
        return true;
    }

    private boolean isCorrectColumnType(String currentColumnType) {
        String[] sortedCorrectColumnTypes = Arrays.copyOf(correctColumnTypes, correctColumnTypes.length);
        Arrays.sort(sortedCorrectColumnTypes);
        int resultOfTypeSearching = Arrays.binarySearch(sortedCorrectColumnTypes, currentColumnType);
        final int FIRST_CONDITION_ELEMENT_NOT_FOUND = -1,
                SECOND_CONDITION_ELEMENT_NOT_FOUND = sortedCorrectColumnTypes.length;
        return !(resultOfTypeSearching <= FIRST_CONDITION_ELEMENT_NOT_FOUND ||
                resultOfTypeSearching == SECOND_CONDITION_ELEMENT_NOT_FOUND);
    }

    public Row createRowForInsertionOrToUpdate(final String[] commandElements, int columnNameIndex) {
        final byte NEXT_COLUMN = 2;
        final Row row = new Row();
        while (columnNameIndex < commandElements.length) {
            int columnValueIndex = columnNameIndex + 1;
            row.put(commandElements[columnNameIndex], commandElements[columnValueIndex]);
            columnNameIndex += NEXT_COLUMN;
        }
        return row;
    }

    protected String[] getCommandElements(String command) {
        return StringUtils.split(command, SIGN_FOR_SPLIT);
    }
}

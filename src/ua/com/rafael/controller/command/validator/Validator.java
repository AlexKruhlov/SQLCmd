package ua.com.rafael.controller.command.validator;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 17.08.2016.
 */
public class Validator {

    private final String[] columnType = {"int", "float", "varchar"};

    public boolean isValid(String commandModel, String command){
        final int COMMAND_INDEX = 0;
        String[] commandModelElements = commandModel.split(" ");
        String[] commandElements = command.split(" ");
        return commandModelElements[COMMAND_INDEX].equals(commandElements[COMMAND_INDEX]);
    }

    public boolean isValidSizeAndColumnType(String[] commandModelElements, String[] commandElements) {
        return commandElements.length % 2 == 0 && commandElements.length >= commandModelElements.length &&
                isColumnType(commandElements);
    }

    private boolean isColumnType(String[] commandElements) {
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
}

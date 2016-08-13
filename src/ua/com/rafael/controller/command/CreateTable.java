package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 12.08.2016.
 */
public class CreateTable implements Command {

    private View view;
    private DBManager dbManager;

    public CreateTable(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String command = "create";
    private final String[] columnType = {"int", "float", "varchar"};

    @Override
    public boolean isValid(String command) {
        final byte COMMAND = 0;
        String[] commandElements = command.split(" ");
        return this.command.equals(commandElements[COMMAND]);
    }

    @Override
    public void start(String command) {
        String[] commandElements = command.split(" ");
        if (!isValidSizeAndColumnType(commandElements)) {
            throw new RuntimeException("Argument error");
        }
        final byte TABLE_NAME = 1;
        String[] argumentsForNewTable = Arrays.copyOfRange(commandElements, TABLE_NAME, commandElements.length);
        dbManager.createTable(argumentsForNewTable);
        view.print("Table "+ commandElements[TABLE_NAME]+" was created");
    }

    private boolean isValidSizeAndColumnType(String[] commandElements) {
        return commandElements.length % 2 == 0 && isColumnType(commandElements);
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










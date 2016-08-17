package ua.com.rafael.controller.command;

import ua.com.rafael.controller.command.validator.Validator;
import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 12.08.2016.
 */
public class Create implements Command {

    private View view;
    private DBManager dbManager;
    private Validator validator;

    public Create(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        validator = new Validator();
    }

    private final String commandModel = "create table id int";


    @Override
    public boolean isValid(String command) {
        return validator.isValid(commandModel, command);
    }

    @Override
    public void start(String command) {
        String[] commandModelElements = commandModel.split(" ");
        String[] commandElements = command.split(" ");
        if (!validator.isValidSizeAndColumnType(commandModelElements, commandElements)) {
            view.print("Command error. Please, check the commandModel parameters.");
            return;
        }
        final byte TABLE_NAME = 1;
        String[] argumentsForNewTable = Arrays.copyOfRange(commandElements, TABLE_NAME, commandElements.length);
        dbManager.createTable(argumentsForNewTable);
        view.print("Table " + commandElements[TABLE_NAME] + " was created");
    }
}










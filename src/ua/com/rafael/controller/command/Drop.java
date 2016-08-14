package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 14.08.2016.
 */
public class Drop implements Command {
    private View view;
    private DBManager dbManager;

    public Drop(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private String command = "drop table_name";

    @Override
    public boolean isValid(String command) {
        final byte COMMAND_INDEX = 0;
        String[] commandElements = command.split(" ");
        String[] thisCommandElements = this.command.split(" ");
        return thisCommandElements[COMMAND_INDEX].equals(commandElements[COMMAND_INDEX]);
    }

    @Override
    public void start(String command) {
        String[] commandElements = command.split(" ");
        String[] thisCommandElements = this.command.split(" ");
        if (thisCommandElements.length != commandElements.length) {
            view.print("Command error. This command has only one argument.");
            return;
        }
    }
}

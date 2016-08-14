package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 14.08.2016.
 */
public class Clear implements Command{

    private View view;
    private DBManager dbManager;

    public Clear(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private String command = "clear table_name";

    @Override
    public boolean isValid(String command) {
        final byte COMMAND_INDEX = 0;
        String[] thisCommandElements = this.command.split(" ");
        String[] commandElements = command.split(" ");
        return thisCommandElements[COMMAND_INDEX].equals(commandElements[COMMAND_INDEX]);
    }

    @Override
    public void start(String command) {
    }
}

package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class Exit implements Command {

    private final View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals("exit");
    }

    @Override
    public void start() {
        System.exit(0);
    }
}

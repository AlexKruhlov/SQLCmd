package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class Exit implements Command {
    private final View view;

    private final String command = "exit";

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals(this.command);
    }

    @Override
    public void start(String command) {
        throw  new ExitException();
    }
}

package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

public class Undetected extends ConsoleCommand {

    final private View view;

    public Undetected(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return true;
    }

    @Override
    public void start(String command) {
        final String[] commandElelments = command.split(SIGN_FOR_SPLIT);
        view.print("Undetected command [" + commandElelments[COMMAND_NAME_INDEX] + "]");
    }
}

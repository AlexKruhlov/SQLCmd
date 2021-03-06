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
        final int COMMAND_NAME_INDEX = 0;
        final String[] commandElements = getCommandElements(command);
        view.print("Undetected command [" + commandElements[COMMAND_NAME_INDEX] + "]");
    }

    @Override
    public String getHelp() {
        return null;
    }
}

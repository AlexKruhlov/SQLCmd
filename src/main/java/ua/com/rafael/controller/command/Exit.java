package ua.com.rafael.controller.command;

import ua.com.rafael.controller.command.exception.ExitException;
import ua.com.rafael.view.View;

public class Exit extends ConsoleCommand {

    private final View view;

    private final String commandModel = "exit";

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(final String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(final String command) {
        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(NO_PARAMETER_MESSAGE);
            return;
        }
        throw new ExitException();
    }

    @Override
    public String getHelp() {
        return "exit" +
                "\n\t\tcompletes database manager execution.";
    }
}

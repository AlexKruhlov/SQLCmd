package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
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
        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(NO_PARAMETER_MESSAGE);
            return;
        }
        throw new ExitException();
    }
}

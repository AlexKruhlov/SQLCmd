package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Help extends ConsoleCommand {

    private final View view;

    private final List<Command> allCommands;

    public Help(View view, List allCommands) {
        this.view = view;
        this.allCommands = allCommands;
    }

    private final String commandModel = "help";

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {

        final String[] commandModelElements = getCommandElements(commandModel);
        final String[] commandElements = getCommandElements(command);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(NO_PARAMETER_MESSAGE);
            return;
        }

        view.print("List of commands:");
        printListOfCommandsHelp(getSortedListOfCommandsHelp(allCommands));

    }

    private List<String> getSortedListOfCommandsHelp(List<Command> allCommands) {
        List<String> helpList = new ArrayList<>();
        for (Command com : allCommands) {
            if (com.getHelp() != null) {
                helpList.add(com.getHelp());
            }
        }
        Collections.sort(helpList);
        return helpList;
    }

    private void printListOfCommandsHelp(List<String> helpList) {
        for (String comHelp : helpList) {
            view.print("\n\t" + comHelp);
        }
    }

    @Override
    public String getHelp() {
        return "help" +
                "\n\t\tprovides the information of all database manager commands.";
    }
}

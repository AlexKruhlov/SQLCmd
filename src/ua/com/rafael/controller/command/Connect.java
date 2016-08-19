package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.sql.Connection;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class Connect extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public Connect(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    private final String commandModel = "connect";

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

        String choice;
        do {
            view.print("Please, input your database name: ");
            String database = view.readLine();
            view.print("Please, input your user name: ");
            String user = view.readLine();
            view.print("Please, input your password: ");
            String password = view.readLine();
            Connection connection = null;
            try {
                view.print("\nConnection process...");
                connection = dbManager.connection(database, user, password);
            } catch (RuntimeException ex) {
                view.print("\n\n" + ex.getCause().getMessage());
                view.print("\n" + ex.getMessage());
            }
            if (connection != null) {
                view.print("\n\nConnection has been successful!\n");
                break;
            }
            choice = getChoice();
        } while (choice.equals("Y") || choice.equals("y"));
    }

    private String getChoice() {
        String choice = null;
        do {
            view.print("\n\nDo you want to try again? (<Y>-yes, <N>-no): ");
            choice = view.readLine();
        } while (isFail(choice));
        return choice;
    }

    private boolean isFail(final String resultStr) {
        return resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y"));
    }
}

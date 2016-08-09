package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.sql.Connection;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class Connect implements Command {
    private View view;
    private DBManager dbManager;

    private final String command = "connect";

    public Connect(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals(this.command);
    }

    @Override
    public void start(String command) {
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

    private boolean isFail(String resultStr) {
        return resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y"));
    }
}

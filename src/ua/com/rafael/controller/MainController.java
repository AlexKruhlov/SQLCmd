package ua.com.rafael.controller;

import ua.com.rafael.controller.command.*;
import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.view.View;

import java.sql.Connection;

/**
 * Created by Alexandr Kruhlov on 07.08.2016.
 */
public class MainController {
    View view;
    DBManager dbManager;
    Command[] command;

    public MainController(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        command = new Command[]{new Help(view), new Exit(view), new List(view, dbManager), new Find(view, dbManager)};
    }

    public void run() {
        view.print("Welcome to console database manager!\n");
        if (connectToDBase()) {
            view.print("\n\nYour registration has been successful!\n");
        } else {
            System.exit(1); // TODO: 07.08.2016
        }

        int COMMAND = 0;
        while (true) {
            view.print("\nPlease, input your command:\n");
            String inputedCommand = view.readLine();
            for (Command comm : command) {
                if (comm.isValid(inputedCommand)) {
                    comm.start(inputedCommand);
                    break;
                }
            }
//
// int COMMAND = 0;
//            int PARAMETER = 1;
//            if (command[COMMAND].equals("help")) {
//                showHelp();
//            } else if (command[COMMAND].equals("list")) {
//                print(Arrays.toString(dbManager.getTableList()));
//            } else if (command[COMMAND].equals("find")) {
//                doFind(command[PARAMETER]);
//            } else if (command[COMMAND].equals("exit")) {
//                System.exit(0);
//            } else {
//                print("Command not found");
//            }
        }
    }

    private boolean connectToDBase() {
        boolean SUCCESSFUL_CONEXCTION = true;
        boolean CONNECTION_FAILED = false;
        String choice;
        do {
            view.print("\nPlease, input your database name: ");
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
                break;
            }
            choice = getChoice();
            if (choice.equals("N") || choice.equals("n")) return CONNECTION_FAILED;
        } while (choice.equals("Y") || choice.equals("y"));
        return SUCCESSFUL_CONEXCTION;
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

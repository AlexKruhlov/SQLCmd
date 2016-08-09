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
        command = new Command[]{
                new Connect(view, dbManager),
                new Help(view),
                new Exit(view),
                new IsConnect(view, dbManager),
                new List(view, dbManager),
                new Find(view, dbManager),
                new Undetected(view)};
    }

    public void run() {
        final int COMMAND = 0;
        view.print("Welcome to console database manager!\n");
        String inputedCommand = command[COMMAND].getClass().getSimpleName().toString().toLowerCase();

        while (true) {
            for (Command comm : command) {
                if (comm.isValid(inputedCommand)) {
                    comm.start(inputedCommand);
                    break;
                }
            }
            view.print("\nPlease, input your command:\n");
            inputedCommand = view.readLine();
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
}
package ua.com.rafael.controller;

import ua.com.rafael.controller.command.*;
import ua.com.rafael.controller.command.exception.ExitException;
import ua.com.rafael.controller.command.exception.SqlQueryException;
import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

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
                new Create(view, dbManager),
                new List(view, dbManager),
                new Find(view, dbManager),
                new Insert(view, dbManager),
                new Update(view, dbManager),
                new Clear(view, dbManager),
                new Drop(view, dbManager),
                new Undetected(view)};
    }

    public void run() {
        final int COMMAND = 0;
        view.print("Welcome to console database manager!\n");
        String inputedCommand = command[COMMAND].getClass().getSimpleName().toString().toLowerCase();
        try {
            while (true) {
                for (Command comm : command) {
                    if (comm.isValid(inputedCommand)) {
                        try {
                            comm.start(inputedCommand);
                        } catch (SqlQueryException exc) {
                            view.print(exc.getCause().getMessage());
                        }
                        break;
                    }
                }
                view.print("\nPlease, input your command:\n");
                inputedCommand = view.readLine();
            }
        } catch (ExitException exc) {
            view.print(exc.getMessage());
        }
    }
}
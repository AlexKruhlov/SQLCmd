package ua.com.rafael.controller;

import ua.com.rafael.controller.command.*;
import ua.com.rafael.controller.command.exception.ExitException;
import ua.com.rafael.controller.command.exception.SqlQueryException;
import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {

    private final View view;
    private final DBManager dbManager;
    private List<Command> commands;

    public MainController(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
        commands = new ArrayList<>();
        Command[] containerForCommands = new Command[]{
                new Connect(view, dbManager),
                new Help(view, commands),
                new Exit(view),
                new IsConnect(view, dbManager),
                new Create(view, dbManager),
                new TablesList(view, dbManager),
                new Find(view, dbManager),
                new Insert(view, dbManager),
                new Update(view, dbManager),
                new Delete(view, dbManager),
                new Clear(view, dbManager),
                new Drop(view, dbManager),
                new Undetected(view)
        };
        commands.addAll(Arrays.asList(containerForCommands));
    }

    public void run() {
        final int COMMAND = 0;
        view.print("Welcome to console database manager!\n");
        String inputedCommand = commands.get(COMMAND).getClass().getSimpleName().toString().toLowerCase();
        try {
            while (true) {
                for (Command comm : (List<Command>) commands) {
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
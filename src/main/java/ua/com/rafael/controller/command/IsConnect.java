package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

public class IsConnect extends ConsoleCommand {

    private final View view;
    private final DBManager dbManager;

    public IsConnect(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(final String command) {
        return !dbManager.isConnect();
    }

    @Override
    public void start(final String command) {
        view.print("Please, connect to database! (For database connection you have to use command \"connect\")");
    }
}

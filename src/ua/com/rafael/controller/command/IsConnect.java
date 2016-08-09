package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class IsConnect implements Command {
    private View view;
    private DBManager dbManager;

    public IsConnect(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        return !dbManager.isConnect();
    }

    @Override
    public void start(String command) {
        view.print("Please, connect to database! (For database connection you have to use command \"connect\")");
    }
}

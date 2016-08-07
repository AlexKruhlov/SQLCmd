package ua.com.rafael.manager.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class List implements Command {
    View view;
    DBManager dbManager;

    public List(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    public List(DBManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals("list");
    }

    @Override
    public void start() {
        dbManager.getTableList();
    }
}

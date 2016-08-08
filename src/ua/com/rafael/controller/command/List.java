package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.sql.Array;
import java.util.Arrays;

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

    @Override
    public boolean isValid(String command) {
        return command.equals("list");
    }

    @Override
    public void start(String command) {
        view.print(Arrays.toString(dbManager.getTableList()));
    }
}

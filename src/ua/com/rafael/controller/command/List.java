package ua.com.rafael.controller.command;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.view.View;

import java.sql.Array;
import java.util.Arrays;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class List implements Command {
    private View view;
    private DBManager dbManager;

    private final String command = "list";

    public List(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals(this.command);
    }

    @Override
    public void start(String command) {
        String[] tableList = dbManager.getTableList();
        if (tableList == null) {
            view.print("Current database haven't any table");
            return;
        }
        view.print(Arrays.toString(tableList));
    }
}

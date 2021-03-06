package ua.com.rafael.controller;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.view.ConsoleView;
import ua.com.rafael.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new ConsoleView();
        DBManager dbManager = new MySqlDBManager();
        MainController controller = new MainController(view, dbManager);
        controller.run();
    }
}

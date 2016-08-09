package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class Undetected implements Command {
    View view;

    public Undetected(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return true;
    }

    @Override
    public void start(String command) {
        view.print("Undetected command ["+command+"]");
    }
}

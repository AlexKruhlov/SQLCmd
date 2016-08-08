package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals("help");
    }

    @Override
    public void start() {
        view.print("List of commands:" +
                "\n\thelp\n\t\tprovides the information of all database manager commands" +
                "\n\tlist\n\t\tdisplays all tabels of the current database" +
                "\n\tfind [table name]\n\t\tdisplays data of the given table which is called as table name." +
                "\n\texit\n\t\tcompletes database manager execution");
    }
}

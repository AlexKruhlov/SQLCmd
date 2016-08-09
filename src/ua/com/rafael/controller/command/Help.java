package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class Help implements Command {
    private View view;

    private final String command = "help";

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isValid(String command) {
        return command.equals(this.command);
    }

    @Override
    public void start(String command) {
        view.print("List of commands:" +
                "\n\tconnect\n\t\tconnects to database you need" +
                "\n\texit\n\t\tcompletes database manager execution" +
                "\n\tfind [table name]\n\t\tdisplays data of the given table which is called as table name." +
                "\n\thelp\n\t\tprovides the information of all database manager commands" +
                "\n\tlist\n\t\tdisplays all tabels of the current database");
    }
}

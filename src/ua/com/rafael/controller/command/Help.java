package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public class
Help implements Command {
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
                "\n\tcreate table [table name] [column name] [column data type] ..." +
                "\n\t\tcreates a table with inputed columns. Types of column: int - integer," +
                "\n\t\tvarchar([size]) - string with size, float - floating point number." +
                "\n\t\tExample: create table id int first_name varchar(45) mark float" +
                "\n\texit\n\t\tcompletes database manager execution" +
                "\n\tfind [table name]\n\t\tdisplays data of the given table which is called as table name." +
                "\n\thelp\n\t\tprovides the information of all database manager commands" +
                "\n\tlist\n\t\tdisplays all tables of the current database");
    }
}

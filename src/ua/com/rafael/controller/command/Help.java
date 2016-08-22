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
                "\n\tconnect\n\t\tconnects to database you need." +
                "\n\tcreate [table name] [column name] [column data type] ..." +
                "\n\t\tcreates a table with inputed columns (table name must consist of one word). " +
                "\n\t\tTypes of column: int - integer, varchar([size]) - string with size," +
                "\n\t\tfloat - floating point number." +
                "\n\t\tExample: create student id int first_name varchar(45) mark float" +
                "\n\tdrop [table name]\n\t\tdeletes a table of current database." +
                "\n\texit\n\t\tcompletes database manager execution." +
                "\n\tfind [table name]\n\t\tdisplays data of the given table which is called as table name." +
                "\n\thelp\n\t\tprovides the information of all database manager commands." +
                "\n\tinsert [table name] [column name] [column value] ..." +
                "\n\t\tinserts a new row with data into table." +
                "\n\tlist\n\t\tdisplays all table names of the current database.");
    }
}

package ua.com.rafael.controller.command;

import ua.com.rafael.view.View;

public class Help extends ConsoleCommand {

    private final View view;

    public Help(View view) {
        this.view = view;
    }

    private final String commandModel = "help";

    @Override
    public boolean isValid(String command) {
        return compareCommandName(commandModel, command);
    }

    @Override
    public void start(String command) {

        final String[] commandModelElements = commandModel.split(SIGN_FOR_SPLIT);
        final String[] commandElements = command.split(SIGN_FOR_SPLIT);
        if (!isTheSameSize(commandModelElements, commandElements)) {
            view.print(NO_PARAMETER_MESSAGE);
            return;
        }
        view.print("List of commands:" +
                "\n\tclear [table name]\n\t\tdeletes all rows in pointed table (table name)." +
                "\n\tconnect\n\t\tconnects to database you need." +
                "\n\tcreate [table name] [column name] [column data type] ..." +
                "\n\t\tcreates a table with user pointed columns (table name must consist of one word)." +
                "\n\t\tTypes of column: int - integer, varchar([size]) - string with size," +
                "\n\t\tdouble - floating point number." +
                "\n\t\tExample: create student id int first_name varchar(45) mark double" +
                "\n\tdelete [table name] [column name] [row value]" +
                "\n\t\tdeletes in pointed table row that contains pointed value (row value)" +
                "\n\t\tin pointed column (column name)." +
                "\n\tdrop [table name]\n\t\tdeletes a pointed table of current database." +
                "\n\texit\n\t\tcompletes database manager execution." +
                "\n\tfind [table name]\n\t\tdisplays data of the given table (table name)." +
                "\n\thelp\n\t\tprovides the information of all database manager commands." +
                "\n\tinsert [table name] [column name] [column value] ..." +
                "\n\t\tinserts a new row with data into table." +
                "\n\tlist\n\t\tdisplays all tables names of the current database." +
                "\n\tupdate [table name] [key column] [key value] [column name for new value] [new value] ..." +
                "\n\t\tsets inputed values (new value) into row that has pointed value (key value) in pointed column" +
                "\n\t\t(key column).Example:" +
                "\n\t\tupdate test id 1 id 1 fname John weight 90.5"
        );
    }
}

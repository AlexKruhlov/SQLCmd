package ua.com.rafael.manager.controller;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.manager.command.*;
import ua.com.rafael.view.View;

import java.sql.Connection;

/**
 * Created by Alexandr Kruhlov on 07.08.2016.
 */
public class MainController {
    View view;
    DBManager dbManager;
    Command[] command;

    public MainController(View console, DBManager dbManager) {
        this.view = console;
        this.dbManager = dbManager;
        command = new Command[]{new Help(view), new Exit(view), new List(view, dbManager)};
    }

    public void run() {
        view.print("Welcome to console database manager!\n");
        if (connectToDBase()) {
            view.print("\n\nYour registration has been successful!\n");
        } else {
            System.exit(1);
        }

        int COMMAND = 0;
        while (true) {
            view.print("\nPlease, input your command:\n");
            String[] inputedCommand = view.readLine().split(" ");
            for (Command comm : command) {
                if (comm.isValid(inputedCommand[COMMAND])) {
                    comm.start();
                }
            }
//
// int COMMAND = 0;
//            int PARAMETER = 1;
//            if (command[COMMAND].equals("help")) {
//                showHelp();
//            } else if (command[COMMAND].equals("list")) {
//                print(Arrays.toString(dbManager.getTableList()));
//            } else if (command[COMMAND].equals("find")) {
//                doFind(command[PARAMETER]);
//            } else if (command[COMMAND].equals("exit")) {
//                System.exit(0);
//            } else {
//                print("Command not found");
//            }
        }
    }

    private void showHelp() {

    }

    private boolean connectToDBase() {
        boolean SUCCESSFUL_CONEXCTION = true;
        boolean CONNECTION_FAILED = false;
        String choice;
        do {
            view.print("\nPlease, input your database name: ");
            String database = view.readLine();
            view.print("Please, input your user name: ");
            String user = view.readLine();
            view.print("Please, input your password: ");
            String password = view.readLine();
            dbManager = new MySqlDBManager();
            Connection connection = null;
            try {
                view.print("\nConnection process...");
                connection = dbManager.connection(database, user, password);
            } catch (RuntimeException ex) {
                view.print("\n\n" + ex.getCause().getMessage());
                view.print("\n" + ex.getMessage());
            }
            if (connection != null) {
                break;
            }
            choice = getChoice();
            if (choice.equals("N") || choice.equals("n")) return CONNECTION_FAILED;
        } while (choice.equals("Y") || choice.equals("y"));
        return SUCCESSFUL_CONEXCTION;
    }

    private String getChoice() {
        String choice = null;
        do {
            view.print("\n\nDo you want to try again? (<Y>-yes, <N>-no): ");
            choice = view.readLine();
        } while (isFail(choice));
        return choice;
    }

    private boolean isFail(String resultStr) {
        return resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y"));
    }

    private void doFind(String tableName) {
        final int
                COLUMN_SIZE = 40,
                LINE_LENGTH = COLUMN_SIZE + 2;
        String[] columnNames = dbManager.getColumnNames(tableName);
        if (columnNames == null) {
            view.print("Table has not created");
            return;
        }
        String line = getLine(LINE_LENGTH * columnNames.length - 1);
        view.print("\n\t|" + line + "|");
        printTableTop(columnNames, COLUMN_SIZE);
        view.print("\n\t|" + line + "|");
        Row[] rows = dbManager.getDataTable(tableName);
        if (rows != null) {
            printRows(rows, COLUMN_SIZE);
        }
        view.print("\n\t-" + line + "-");
    }

    private String getLine(int length) {
        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = '-';
        }
        return new String(result);
    }

    private void printTableTop(String[] columnNames, int columnSize) {
        String format = " %-" + columnSize + "s";
        view.print("\n\t" + "|");
        for (int i = 0; i < columnNames.length; i++) {
            view.print(String.format(format, columnNames[i]) + "|");
        }
    }

    private void printRows(Row[] rows, int columnSize) {
        String format = " %-" + columnSize + "s";
        for (int i = 0; i < rows.length; i++) {
            view.print("\n\t" + "|");
            Object[] rowData = rows[i].getData();
            for (int j = 0; j < rowData.length; j++) {
                view.print(String.format(format, rowData[j]) + "|");
            }
        }
    }
}

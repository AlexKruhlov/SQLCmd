package ua.com.rafael.view;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.manager.Row;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SplittableRandom;

/**
 * Created by Alexandr Kruhlov on 08.07.2016.
 */
public class ConsoleView implements View {
    DBManager dbManager;

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public String readLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void run() {
        print("Welcome to console database manager!\n");
        if (connectToDBase()) {
            print("\n\nYour registration has been successful!\n");
        } else {
            System.exit(1);
        }
        while (true) {
            print("\nPlease, input your command:\n");
            String[] command = readLine().split(" ");
            int COMMAND = 0;
            int PARAMETER = 1;
            if (command[COMMAND].equals("help")) {
                showHelp();
            } else if (command[COMMAND].equals("list")) {
                print(Arrays.toString(dbManager.getTableList()));
            } else if (command[COMMAND].equals("find")) {
                doFind(command[PARAMETER]);
            } else if (command[COMMAND].equals("exit")) {
                System.exit(0);
            } else {
                print("Command not found");
            }
        }
    }

    private void showHelp() {
        print("\nList of commands:" +
                "\n\thelp\n\t\tprovides the information of all database manager commands" +
                "\n\tlist\n\t\tdisplays all tabels of the current database" +
                "\n\tfind [table name]\n\t\tdisplays data of the given table which is called as table name." +
                "\n\texit\n\t\tcompletes database manager execution");
    }

    private boolean connectToDBase() {
        boolean SUCCESSFUL_CONEXCTION = true;
        boolean CONNECTION_FAILED = false;
        String choice;
        do {
            print("\nPlease, input your database name: ");
            String database = readLine();
            print("Please, input your user name: ");
            String user = readLine();
            print("Please, input your password: ");
            String password = readLine();
            dbManager = new MySqlDBManager();
            Connection connection = null;
            try {
                print("\nConnection process...");
                connection = dbManager.connection(database, user, password);
            } catch (RuntimeException ex) {
                print("\n\n" + ex.getCause().getMessage());
                print("\n" + ex.getMessage());
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
            print("\n\nDo you want to try again? (<Y>-yes, <N>-no): ");
            choice = readLine();
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
            print("Table has not created");
            return;
        }
        String line = getLine(LINE_LENGTH * columnNames.length - 1);
        print("\n\t|" + line + "|");
        printTableTop(columnNames, COLUMN_SIZE);
        print("\n\t|" + line + "|");
        Row[] rows = dbManager.getDataTable(tableName);
        if (rows != null) {
            printRows(rows, COLUMN_SIZE);
        }
        print("\n\t-" + line + "-");
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
        print("\n\t" + "|");
        for (int i = 0; i < columnNames.length; i++) {
            print(String.format(format, columnNames[i]) + "|");
        }
    }

    private void printRows(Row[] rows, int columnSize) {
        String format = " %-" + columnSize + "s";
        for (int i = 0; i < rows.length; i++) {
            print("\n\t" + "|");
            Object[] rowData = rows[i].getData();
            for (int j = 0; j < rowData.length; j++) {
                print(String.format(format, rowData[j]) + "|");
            }
        }
    }
}





















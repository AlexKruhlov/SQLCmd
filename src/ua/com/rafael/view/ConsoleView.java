package ua.com.rafael.view;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

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
    public void run(){
        print("Welcome to console database manager!\n");
        if (connectToDBase()) {
            print("\n\nYour registration has been successful!\n");
        } else{
            System.exit(1);
        }

        print("\nPlease, input your command:\n");
        String[] command = readLine().split(" ");
        int COMMAND = 0;
        int PARAMETER = 1;
        if (command[COMMAND].equals("help")){
            print("\nList of commands:");
            print("\n\t help " +
                    "\n\t\tprovides the information of all database manager commands");
            print("\n\t list " +
                    "\n\t\tdisplays all tabels of the current database");
            print("\n\t find " +
                    "\n\t\tdisplays table data");

            print("\n\t exit " +
                    "\n\t\tcompletes database manager execution");

        } else if (command[COMMAND].equals("list")){
            print(Arrays.toString(dbManager.getTableList()));
        } else if (command[COMMAND].equals("find")){
            doFind(command[PARAMETER]);
        }

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

    private void doFind(String s) {
        dbManager.getDataTable(s);
    }

}





















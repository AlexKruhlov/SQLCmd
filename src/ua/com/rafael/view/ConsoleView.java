package ua.com.rafael.view;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Alexandr Kruhlov on 08.07.2016.
 */
public class ConsoleView implements View {

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
    public int run() {
        int EXIT = 0;
        int PRETERM_EXIT = 1;
        String choice;

        print("Welcome to console database manager!\n");
        do {
            print("\nPlease, input your database name: ");
            String database = readLine();
            print("Please, input your user name: ");
            String user = readLine();
            print("Please, input your password: ");
            String password = readLine();
            DBManager dbManager = new MySqlDBManager();
            Connection connection = null;
            try {
                print("\nConnection process...");
                connection = dbManager.connection(database, user, password);
            } catch (RuntimeException ex) {
                print("\n\n" + ex.getCause().getMessage());
                print("\n" + ex.getMessage());
            }
            if (connection != null) {
                print("\n\nYour registration has been successful!");
                break;
            }
            choice = detChoice();
            if (choice.equals("N") || choice.equals("n")) return PRETERM_EXIT;
        } while (choice.equals("Y") || choice.equals("y"));
        return EXIT;
    }

    private String detChoice() {
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
}





















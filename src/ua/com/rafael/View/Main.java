package ua.com.rafael.view;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by Alexandr Kruhlov on 08.07.2016.
 */
public class Main {

//    Scanner scanner = new Scanner(System.in);
//    System.out.print("Please, input your database name: ");
//    database = scanner.nextLine();
//    System.out.print("Please, input your user name: ");
//    user = scanner.nextLine();
//    System.out.print("Please, input your password: ");
//    password = scanner.nextLine();

    public static void main(String[] args) throws SQLException {
        System.out.println("Welcome to console database manager!\n");
        Scanner scanner = new Scanner(System.in);

//      Connection block
        Connection connection = null;
        String choice = null;

//        do {
//            UserInfo userInfo = new UserInfo();
//            userInfo.inputAll();
//            connection = null;//DBConnector.getConnection(userInfo);
//            if (connection != null) break;
//            choice = getChoice(scanner);
//            if (choice.equals("N") || choice.equals("n")) System.exit(1);
//        } while (choice.equals("Y") || choice.equals("y"));
    }

    private static String getChoice(Scanner scanner) {
        String resultStr = null;
        do {
            System.out.println("Do you want to try again? (<Y>-yes, <N>-no): ");
            resultStr = scanner.nextLine();
        } while (isFail(resultStr));
        return resultStr;
    }

    public static boolean isFail(String resultStr) {
        return resultStr.length() == 0 ||
                !(resultStr.equals("N") || resultStr.equals("n") ||
                        resultStr.equals("Y") || resultStr.equals("y"));
    }
}




















package ua.com.rafael;

import java.util.Scanner;

/**
 * Created by sigmund69 on 14.07.2016.
 */
public class UserInfo {
    private String database;
    private String user;
    private String password;

    public UserInfo() {
    }

    public UserInfo(String database, String user, String password) {
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void inputAll() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please, input your database name: ");
        database = scanner.nextLine();
        System.out.print("Please, input your user name: ");
        user = scanner.nextLine();
        System.out.print("Please, input your password: ");
        password = scanner.nextLine();
    }
}

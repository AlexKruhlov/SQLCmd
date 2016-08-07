package ua.com.rafael.view;

import ua.com.rafael.manager.DBManager;
import ua.com.rafael.manager.MySqlDBManager;
import ua.com.rafael.manager.Row;
import ua.com.rafael.manager.command.Command;
import ua.com.rafael.manager.command.Exit;
import ua.com.rafael.manager.command.Help;
import ua.com.rafael.manager.command.List;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.SplittableRandom;

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
}





















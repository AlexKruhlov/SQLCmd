package ua.com.rafael.view;

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
}





















package ua.com.rafael.view;

import java.util.Scanner;

public class ConsoleView implements View {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}





















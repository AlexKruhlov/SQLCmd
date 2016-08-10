package ua.com.rafael.view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by Alexandr Kruhlov on 08.07.2016.
 */
public class ConsoleView implements View {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String string) {
        System.out.print(string);
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}





















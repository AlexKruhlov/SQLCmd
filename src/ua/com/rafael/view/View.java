package ua.com.rafael.view;

import java.sql.SQLException;

/**
 * Created by Alexandr Kruhlov on 29.07.2016.
 */
public interface View {
    void print(String string);
    String readLine();
    void run();
}

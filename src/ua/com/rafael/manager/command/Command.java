package ua.com.rafael.manager.command;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public interface Command {
    boolean isValid(String command);
    void start();
}

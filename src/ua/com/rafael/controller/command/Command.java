package ua.com.rafael.controller.command;

/**
 * Created by Alexandr Kruhlov on 06.08.2016.
 */
public interface Command {
    boolean isValid(String command);
    void start(String command);
}

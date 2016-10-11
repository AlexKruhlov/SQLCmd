package ua.com.rafael.controller.command;

public interface Command {

    boolean isValid(String command);

    void start(String command);

}

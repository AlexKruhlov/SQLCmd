package ua.com.rafael.controller.command;

/**
 * Created by Alexandr Kruhlov on 08.08.2016.
 */
public class Find implements Command {

    @Override
    public boolean isValid(String command) {
        return command.equals("find");
    }

    @Override
    public void start() {

    }
}

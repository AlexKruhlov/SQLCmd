package ua.com.rafael.controller.command.exception;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class ExitException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Your work in our manager is finished!\n" +
                "Goodluck!";
    }
}

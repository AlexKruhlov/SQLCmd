package ua.com.rafael.controller.command.exception;

public class ExitException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Your work in our manager is finished!\nGoodluck!";
    }
}

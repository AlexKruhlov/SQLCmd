package ua.com.rafael.controller.command.exception;

/**
 * Created by Alexandr Kruhlov on 20.08.2016.
 */
public class SqlQueryException extends RuntimeException {
    public SqlQueryException(Exception exc) {
        super(exc);
    }
}

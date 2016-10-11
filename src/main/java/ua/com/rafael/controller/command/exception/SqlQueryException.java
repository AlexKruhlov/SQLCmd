package ua.com.rafael.controller.command.exception;

public class SqlQueryException extends RuntimeException {
    public SqlQueryException(Exception exc) {
        super(exc);
    }
}

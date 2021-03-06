package ua.com.rafael.controller.command;

import java.io.IOException;
import java.io.OutputStream;

class LogOutStream extends OutputStream {

    private String log = "";

    @Override
    public void write(int b) throws IOException {
        log += String.valueOf((char) b);
    }

    public String getData() {
        return log;
    }
}

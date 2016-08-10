package ua.com.rafael.integration;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class LogOutStream extends OutputStream {
    private String log = "";

    @Override
    public void write(int b) throws IOException {
        log += String.valueOf((char) b);
    }

    public String getData() {
        return log;
    }
}

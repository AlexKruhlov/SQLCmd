package ua.com.rafael.integration;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class ConfigurableInputSream extends InputStream {

    private String line;
    private boolean isEndLine = false;

    @Override
    public int read() throws IOException {
        if (line.length() == 0) {
            return -1;
        }

        if (isEndLine) {
            isEndLine = false;
            return -1;
        }

        char ch = line.charAt(0);
        line = line.substring(1);

        if (ch == '\n') {
            isEndLine = true;
        }

        return (int) ch;
    }

    public void add(String line) {
        if (this.line == null) {
            this.line = line;
        } else {
            this.line += "\n" + line;
        }
    }
}

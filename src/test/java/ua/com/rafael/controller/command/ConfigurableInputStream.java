package ua.com.rafael.controller.command;

import java.io.IOException;
import java.io.InputStream;

class ConfigurableInputStream extends InputStream {

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

    public void addAll(String[] line) {
        for (String aLine : line) {
            add(aLine);
        }
    }

    private void add(String line) {
        if (this.line == null) {
            this.line = line;
        } else {
            this.line += "\n" + line;
        }
    }
}

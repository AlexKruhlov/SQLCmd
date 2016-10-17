package ua.com.rafael.manager;

import org.junit.Assert;
import org.junit.Test;
import ua.com.rafael.controller.Main;

public class MainTest {

    @Test
    public void mainClassTest() {
        boolean isMainClassCreated = true;
        try {
            new Main();
        } catch (Exception exc) {
            isMainClassCreated = false;
        }

        boolean actual = isMainClassCreated;

        Assert.assertEquals(true, actual);
    }
}

package ua.com.rafael.manager;

import org.junit.Assert;
import org.junit.Test;
import ua.com.rafael.controller.Main;

public class MainTest {

    @Test
    public void mainClassTest() {
        boolean isMainClassCreated = true;
        try {
            Main main = new Main();
        } catch (Exception exc) {
            isMainClassCreated = false;
        }
        boolean
                expected = true,
                actual = isMainClassCreated;

        Assert.assertEquals(expected, actual);
    }

}

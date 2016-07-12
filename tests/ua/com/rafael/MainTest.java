package ua.com.rafael;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sigmund69 on 12.07.2016.
 */
public class MainTest {
    @Test
    public void getChoice() throws Exception {
        String[] input = {"", "N", "n", "Y", "y", "g", "yddf"}; // "null" is impossible
        String[] expected = {"fail", "Ok", "Ok", "Ok", "Ok", "fail", "fail"};
        String[] actual = new String[7];

        for (int i = 0; i < actual.length; i++) {
            actual[i] = isFail(input[i]) ? "fail" : "Ok";
        }
        Assert.assertArrayEquals(actual, expected);
    }

    private boolean isFail(String s) {
        return s.length() == 0 ||
                !(s.equals("N") || s.equals("n") ||
                        s.equals("Y") || s.equals("y"));
    }

}
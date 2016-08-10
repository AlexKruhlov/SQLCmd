package ua.com.rafael.integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.rafael.controller.Main;

import java.io.PrintStream;
import java.nio.charset.MalformedInputException;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class IntegrationTest {
    private ConfigurableInputSream in;
    private LogOutStream out;

    @Before
    public void setup() {
        in = new ConfigurableInputSream();
        out = new LogOutStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testAllCommands() {
        in.addAll(new String[]{
                "myschema",
                "root",
                "independence24",
                "help",
                "list",
                "find actor",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(
                "Welcome to console database manager!\n" +
                        "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                        "Connection process...\n" +
                        "\n" +
                        "Connection has been successful!\n" +
                        "\n" +
                        "Please, input your command:\n" +
                        "List of commands:\n" +
                        "\tconnect\n" +
                        "\t\tconnects to database you need\n" +
                        "\texit\n" +
                        "\t\tcompletes database manager execution\n" +
                        "\tfind [table name]\n" +
                        "\t\tdisplays data of the given table which is called as table name.\n" +
                        "\thelp\n" +
                        "\t\tprovides the information of all database manager commands\n" +
                        "\tlist\n" +
                        "\t\tdisplays all tabels of the current database\n" +
                        "Please, input your command:\n" +
                        "[actor, address]\n" +
                        "Please, input your command:\n" +
                        "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                        "\t| actor_id                                | first_name                              | last_name                               |\n" +
                        "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                        "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                        "\n" +
                        "Please, input your command:\n" +
                        "Your work in our manager is finished!\n" +
                        "Goodluck!", out.getData());
    }

    @Test
    public void connectionFailTest() {
        in.addAll(new String[]{
                "schema",  //fail database name
                "root",
                "independence24",
                "n",  //Do you want to try again? (<Y>-yes, <N>-no): n
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Could not create connection to database server. Attempted reconnect 3 times. Giving up.\n" +
                "Please, check your database name, user name and password!\n" +
                "\n" +
                "Do you want to try again? (<Y>-yes, <N>-no): \n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

    @Test
    public void failSuccessConnectionTest() {
        in.addAll(new String[]{
                "schema",  //fail database name
                "root",
                "independence24",
                "y",  //Do you want to try again? (<Y>-yes, <N>-no): y
                "myschema",  //right database name
                "root",
                "independence24",
                "list",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Could not create connection to database server. Attempted reconnect 3 times. Giving up.\n" +
                "Please, check your database name, user name and password!\n" +
                "\n" +
                "Do you want to try again? (<Y>-yes, <N>-no): Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "[actor, address]\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }
}
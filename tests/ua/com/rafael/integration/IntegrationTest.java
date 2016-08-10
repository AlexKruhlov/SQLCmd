package ua.com.rafael.integration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ua.com.rafael.controller.Main;

import java.io.PrintStream;

/**
 * Created by Alexandr Kruhlov on 09.08.2016.
 */
public class IntegrationTest {
    private static ConfigurableInputSream in;
    private static LogOutStream out;

    @BeforeClass
    public static void setup(){
        in = new ConfigurableInputSream();
        out= new LogOutStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testExit(){
        in.add("myschema");
        in.add("root");
        in.add("independence24");
        in.add("help");
        in.add("list");
        in.add("find actor");
        in.add("exit");

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

}

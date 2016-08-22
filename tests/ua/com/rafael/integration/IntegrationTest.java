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
                        "\tcreate table [table name] [column name] [column data type] ...\n" +
                        "\t\tcreates a table with inputed columns. Types of column: int - integer,\n" +
                        "\t\tvarchar([size]) - string with size, float - floating point number.\n" +
                        "\t\tExample: create table id int first_name varchar(45) mark float\n" +
                        "\texit\n" +
                        "\t\tcompletes database manager execution\n" +
                        "\tfind [table name]\n" +
                        "\t\tdisplays data of the given table which is called as table name.\n" +
                        "\thelp\n" +
                        "\t\tprovides the information of all database manager commands\n" +
                        "\tlist\n" +
                        "\t\tdisplays all tables of the current database\n" +
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
                "test",  //right database name
                "root",
                "independence24",
                "create test id int first_name varchar(45)",
                "list",
                "drop test",
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
                "Table test was created\n" +
                "Please, input your command:\n" +
                "[test]\n" +
                "Please, input your command:\n" +
                "The table was deleted.\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());

    }

    @Test
    public void createTest() {
        in.addAll(new String[]{
                "test",  //right database name
                "root",
                "independence24",
                "list",
                "create test int first_name varchar(45)", // incorrect number of parameters
                "create test id inte first_name varchar(45)", // incorrect column type (inte, but int is correct)
                "create test id int first_name varchar(45)", // correct command
                "find test",
                "drop test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Current database haven't any table" +
                "\n" +
                "Please, input your command:\n" +
                "Command error. Please, check the number of command parameters." +
                "\n" +
                "Please, input your command:\n" +
                "Some of these column types are incorrect" +
                "\n" +
                "Please, input your command:\n" +
                "Table test was created" +
                "\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------------------------------------------------|\n" +
                "\t| id                                      | first_name                              |\n" +
                "\t|-----------------------------------------------------------------------------------|\n" +
                "\t|-----------------------------------------------------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "The table was deleted." +
                "\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

    @Test
    public void dropTest() {
        in.addAll(new String[]{
                "test",  //right database name
                "root",
                "independence24",
                "create test id int",
                "list",
                "drop test test",
                "drop test1",
                "drop test",
                "list",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Table test was created" +
                "\n" +
                "Please, input your command:\n" +
                "[test]" +
                "\n" +
                "Please, input your command:\n" +
                "Command error. This command must have one parameter." +
                "\n" +
                "Please, input your command:\n" +
                "Unknown table 'test.test1'" +
                "\n" +
                "Please, input your command:\n" +
                "The table was deleted." +
                "\n" +
                "Please, input your command:\n" +
                "Current database haven't any table" +
                "\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());

    }

    @Test
    public void exitTest() {
        in.addAll(new String[]{
                "test",  //right database name
                "root",
                "independence24",
                "list",
                "exit exit",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Current database haven't any table" +
                "\n" +
                "Please, input your command:\n" +
                "Command error. This command hasn't any parameters." +
                "\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }


    @Test
    public void clearTest() {
        in.addAll(new String[]{
                "test",
                "root",
                "independence24",
                "clear test",
                "create test id int",
                "insert test id 1",
                "find test",
                "clear test test",
                "clear test",
                "find test",
                "drop test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Table 'test.test' doesn't exist" +
                "\n" +
                "Please, input your command:\n" +
                "Table test was created\n" +
                "Please, input your command:\n" +
                "The table has got new row.\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "Command error. This command must have one parameter." +
                "\n" +
                "Please, input your command:\n" +
                "This table was cleared.\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "The table was deleted.\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

    @Test
    public void findTest() {
        in.addAll(new String[]{
                "test",
                "root",
                "independence24",
                "find test",
                "create test id int",
                "insert test id 1",
                "find test test",
                "find test",
                "clear test",
                "find test",
                "drop test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Table 'test.test' doesn't exist" +
                "\n" +
                "Please, input your command:\n" +
                "Table test was created\n" +
                "Please, input your command:\n" +
                "The table has got new row.\n" +
                "Please, input your command:\n" +
                "Command error. This command must have one parameter." +
                "\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "This table was cleared.\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "The table was deleted.\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

    @Test
    public void insertTest() {
        in.addAll(new String[]{
                "test",
                "root",
                "independence24",
                "create test id int",
                "find test",
                "insert test id",
                "insert test id int",
                "insert test id 1",
                "find test",
                "drop test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals("Welcome to console database manager!\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Table test was created\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "Command error. Please, check the number of command parameters." +
                "\n" +
                "Please, input your command:\n" +
                "Incorrect integer value: 'int' for column 'id' at row 1" +
                "\n" +
                "Please, input your command:\n" +
                "The table has got new row.\n" +
                "Please, input your command:\n" +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                "\n" +
                "Please, input your command:\n" +
                "The table was deleted.\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

    @Test
    public void isConnectTest() {
        in.addAll(new String[]{
                "test",
                "roota",  //incorrect login
                "independence24",
                "N",
                "list",
                "connect",
                "test",
                "root",  //correct login
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
                "Do you want to try again? (<Y>-yes, <N>-no): \n" +
                "Please, input your command:\n" +
                "Please, connect to database! (For database connection you have to use command \"connect\")" +
                "\n" +
                "Please, input your command:\n" +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                "\n" +
                "Please, input your command:\n" +
                "Current database haven't any table" +
                "\n" +
                "Please, input your command:\n" +
                "Your work in our manager is finished!\n" +
                "Goodluck!", out.getData());
    }

}














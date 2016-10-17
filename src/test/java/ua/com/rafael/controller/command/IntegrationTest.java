package ua.com.rafael.controller.command;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.com.rafael.controller.Main;

import java.io.PrintStream;

public class IntegrationTest {

    private final String INTRODUSE_MESSAGE = "Welcome to console database manager!\n" +
            "Please, input your database name: Please, input your user name: Please, input your password: \n" +
            "Connection process...\n" +
            "\nConnection has been successful!\n";
    private final String EXIT_MESSAGE = "Your work in our manager is finished!\n" +
            "Goodluck!";
    private final String INPUT_COMMAND_MESSAGE = "\nPlease, input your command:\n";

    private ConfigurableInputSream in;
    private LogOutStream out;
    private final String SIGN_OF_SPLIT = " ";
    private String databaseName;
    private String userName;
    private String password;

    @Before
    public void setup() {
        in = new ConfigurableInputSream();
        out = new LogOutStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));

        databaseName = "test";
        userName = "root";
        password = "independence24";
    }

    @Test
    public void connectionFailTest() {
        in.addAll(new String[]{
                "schema",  //fail database name
                "root",
                "independence24",
                "n",  //Do you want to try again? (<Y>-yes, <N>-no): n
                "connect" + SIGN_OF_SPLIT + "schema", //fail command syntax
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
                "Do you want to try again? (<Y>-yes, <N>-no): " +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command hasn't any parameters." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void failSuccessConnectionTest() {
        in.addAll(new String[]{
                "schema",  //fail database name
                "root",
                "independence24",
                "y",  //Do you want to try again? (<Y>-yes, <N>-no): y
                databaseName,  //right database name
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" +
                        SIGN_OF_SPLIT + "first_name" + SIGN_OF_SPLIT + "varchar(45)",
                "list",
                "drop" + SIGN_OF_SPLIT + "test",
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
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "[test]" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void createTableTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "list",
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "int" +
                        SIGN_OF_SPLIT + "first_name" + SIGN_OF_SPLIT + "varchar(45)", // incorrect number of parameters
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "inte" + SIGN_OF_SPLIT +
                        "first_name" + SIGN_OF_SPLIT + "varchar(45)", // incorrect column type (inte, but int is correct)
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" + SIGN_OF_SPLIT
                        + "first_name" + SIGN_OF_SPLIT + "varchar(45)", // correct command
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "create" + SIGN_OF_SPLIT + "test*$" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Current database haven't any table" +
                INPUT_COMMAND_MESSAGE +
                "Command error. Please, check the number of command parameters." +
                INPUT_COMMAND_MESSAGE +
                "Some of these column types are incorrect" +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------------------------------------------------|\n" +
                "\t| id                                      | first_name                              |\n" +
                "\t|-----------------------------------------------------------------------------------|\n" +
                "\t|-----------------------------------------------------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "You have an error in your SQL syntax; check the manual that corresponds to your MySQL server " +
                "version for the right syntax to use near '*$(id int NOT NULL)' at line 1" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void dropTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "list",
                "drop" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test1",
                "drop" + SIGN_OF_SPLIT + "test",
                "list",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "[test]" +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command must have one parameter." +
                INPUT_COMMAND_MESSAGE +
                "Unknown table 'test.test1'" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "Current database haven't any table" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void exitTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "list",
                "exit" + SIGN_OF_SPLIT + "exit",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Current database haven't any table" +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command hasn't any parameters." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void clearTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "clear" + SIGN_OF_SPLIT + "test",
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1",
                "print" + SIGN_OF_SPLIT + "test",
                "clear" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "test",
                "clear" + SIGN_OF_SPLIT + "test",
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table 'test.test' doesn't exist" +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command must have one parameter." +
                INPUT_COMMAND_MESSAGE +
                "This table was cleared." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void printTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "print" + SIGN_OF_SPLIT + "test",
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1",
                "print" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "test",
                "print" + SIGN_OF_SPLIT + "test",
                "clear" + SIGN_OF_SPLIT + "test",
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "print" + SIGN_OF_SPLIT + "test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table 'test.test' doesn't exist" +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command must have one parameter." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "This table was cleared." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "Table 'test.test' doesn't exist" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void insertTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "print" + SIGN_OF_SPLIT + "test",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1",
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "Command error. Please, check the number of command parameters." +
                INPUT_COMMAND_MESSAGE +
                "Incorrect integer value: 'int' for column 'id' at row 1" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void updateTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" + SIGN_OF_SPLIT +
                        "fname" + SIGN_OF_SPLIT + "varchar(45)" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "double",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT +
                        "1" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT + "Alex" + SIGN_OF_SPLIT +
                        "weight" + SIGN_OF_SPLIT + "70.1",
                "print" + SIGN_OF_SPLIT + "test",
                "updates" + SIGN_OF_SPLIT + "test",
                "update" + SIGN_OF_SPLIT + "test",
                "update" + SIGN_OF_SPLIT + "test1" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1" + SIGN_OF_SPLIT +
                        "id" + SIGN_OF_SPLIT + "1" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT +
                        "Maria" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "60",
                "update" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1" + SIGN_OF_SPLIT +
                        "id" + SIGN_OF_SPLIT + "1" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT +
                        "Maria" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "60",
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| id                                      | fname                                   | weight                                  |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| 1                                       | Alex                                    | 70.1                                    |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [updates]" +
                INPUT_COMMAND_MESSAGE +
                "Command error. Please, check the number of command parameters." +
                INPUT_COMMAND_MESSAGE +
                "Table 'test.test1' doesn't exist" +
                INPUT_COMMAND_MESSAGE +
                "The table was updated." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| id                                      | fname                                   | weight                                  |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| 1                                       | Maria                                   | 60.0                                    |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void deleteTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" + SIGN_OF_SPLIT +
                        "fname" + SIGN_OF_SPLIT + "varchar(45)" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "double",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1" + SIGN_OF_SPLIT +
                        "fname" + SIGN_OF_SPLIT + "Mike" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "75.1",
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "2" + SIGN_OF_SPLIT +
                        "fname" + SIGN_OF_SPLIT + "Ellis" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "60.02",
                "del" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT + "Mike",
                "delete" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "Mike",
                "delete" + SIGN_OF_SPLIT + "test1" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT + "Mike",
                "delete" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "fname" + SIGN_OF_SPLIT + "Mike",
                "print" + SIGN_OF_SPLIT + "test",
                "delete" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "weight" + SIGN_OF_SPLIT + "60.02",
                "print" + SIGN_OF_SPLIT + "test",
                "drop" + SIGN_OF_SPLIT + "test",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [del]" +
                INPUT_COMMAND_MESSAGE +
                "Command error. Please, check the number of command parameters." +
                INPUT_COMMAND_MESSAGE +
                "Table 'test.test1' doesn't exist" +
                INPUT_COMMAND_MESSAGE +
                "The row was deleted." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| id                                      | fname                                   | weight                                  |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| 2                                       | Ellis                                   | 60.02                                   |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The row was deleted." +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	| id                                      | fname                                   | weight                                  |\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                "	|-----------------------------------------------------------------------------------------------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
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
                databaseName,
                userName,
                password,
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
                "Do you want to try again? (<Y>-yes, <N>-no): " +
                INPUT_COMMAND_MESSAGE +
                "Please, connect to database! (For database connection you have to use command \"connect\")" +
                INPUT_COMMAND_MESSAGE +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                INPUT_COMMAND_MESSAGE +
                "Current database haven't any table" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void listTest() {
        in.addAll(new String[]{
                databaseName,
                userName,
                password,
                "create" + SIGN_OF_SPLIT + "test1" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" + SIGN_OF_SPLIT +
                        "first_name" + SIGN_OF_SPLIT + "varchar(45)",
                "create" + SIGN_OF_SPLIT + "test2" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int" + SIGN_OF_SPLIT +
                        "first_name" + SIGN_OF_SPLIT + "varchar(45)",
                "list" + SIGN_OF_SPLIT + "table",
                "list",
                "drop" + SIGN_OF_SPLIT + "test1",
                "drop" + SIGN_OF_SPLIT + "test2",
                "list",
                "exit"
        });

        Main.main(new String[0]);

        Assert.assertEquals(INTRODUSE_MESSAGE +
                INPUT_COMMAND_MESSAGE +
                "Table test1 was created" +
                INPUT_COMMAND_MESSAGE +
                "Table test2 was created" +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command hasn't any parameters." +
                INPUT_COMMAND_MESSAGE +
                "[test1, test2]" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "Current database haven't any table" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void helpTest() {
        in.addAll(new String[]{
                "test",
                "root",
                "independence2", // incorrect password
                "n",
                "help",
                "connect",
                databaseName,
                userName,
                password,
                "helps",
                "help" + SIGN_OF_SPLIT + "test",
                "help",
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
                "Do you want to try again? (<Y>-yes, <N>-no): " +
                INPUT_COMMAND_MESSAGE +
                "List of commands:\n" +
                "\tclear" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes all rows in pointed table (table name).\n" +
                "\tconnect\n" +
                "\t\tconnects to database you need.\n" +
                "\tcreate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_data_type] ...\n" +
                "\t\tcreates a table with user pointed columns (table name must consist of one word).\n" +
                "\t\tTypes of column: int - integer, varchar([size]) - string with size,\n" +
                "\t\tdouble - floating point number.\n" +
                "\t\tExample: create student id int first_name varchar(45) mark double\n" +
                "\tdelete" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[row_value]\n" +
                "\t\tdeletes in pointed table row that contains pointed value (row value)\n" +
                "\t\tin pointed column (column name).\n" +
                "\tdrop" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes a pointed table of current database.\n" +
                "\texit\n" +
                "\t\tcompletes database manager execution.\n" +
                "\thelp\n" +
                "\t\tprovides the information of all database manager commands.\n" +
                "\tinsert" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_value] ...\n" +
                "\t\tinserts a new row with data into table.\n" +
                "\tlist\n" +
                "\t\tdisplays all tables names of the current database.\n" +
                "\tprint" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdisplays data of the given table (table name).\n" +
                "\tupdate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[key_column]" + SIGN_OF_SPLIT +
                "[key_value]" + SIGN_OF_SPLIT + "[column_name_for_new_value]" + SIGN_OF_SPLIT + "[new_value] ...\n" +
                "\t\tsets inputed values (new value) into row that has pointed value (key value) in pointed column\n" +
                "\t\t(key column).Example:\n" +
                "\t\tupdate test id 1 id 1 fname John weight 90.5" +
                INPUT_COMMAND_MESSAGE +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [helps]" +
                INPUT_COMMAND_MESSAGE +
                "Command error. This command hasn't any parameters." +
                INPUT_COMMAND_MESSAGE +
                "List of commands:\n" +
                "\tclear" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes all rows in pointed table (table name).\n" +
                "\tconnect\n" +
                "\t\tconnects to database you need.\n" +
                "\tcreate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_data_type] ...\n" +
                "\t\tcreates a table with user pointed columns (table name must consist of one word).\n" +
                "\t\tTypes of column: int - integer, varchar([size]) - string with size,\n" +
                "\t\tdouble - floating point number.\n" +
                "\t\tExample: create student id int first_name varchar(45) mark double\n" +
                "\tdelete" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[row_value]\n" +
                "\t\tdeletes in pointed table row that contains pointed value (row value)\n" +
                "\t\tin pointed column (column name).\n" +
                "\tdrop" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes a pointed table of current database.\n" +
                "\texit\n" +
                "\t\tcompletes database manager execution.\n" +
                "\thelp\n" +
                "\t\tprovides the information of all database manager commands.\n" +
                "\tinsert" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_value] ...\n" +
                "\t\tinserts a new row with data into table.\n" +
                "\tlist\n" +
                "\t\tdisplays all tables names of the current database.\n" +
                "\tprint" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdisplays data of the given table (table name).\n" +
                "\tupdate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[key_column]" + SIGN_OF_SPLIT +
                "[key_value]" + SIGN_OF_SPLIT + "[column_name_for_new_value]" + SIGN_OF_SPLIT + "[new_value] ...\n" +
                "\t\tsets inputed values (new value) into row that has pointed value (key value) in pointed column\n" +
                "\t\t(key column).Example:\n" +
                "\t\tupdate test id 1 id 1 fname John weight 90.5" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }

    @Test
    public void undetectedTest() {
        in.addAll(new String[]{
                "test",
                "root",
                "independence", // incorrect password
                "n",
                "connection", //incorrect command
                "connect",
                databaseName,
                userName,
                password,
                "connection", //incorrect command
                "helps", //incorrect command
                "help",
                "creates" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int", //incorrect command
                "create" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "int",
                "lister", //incorrect command
                "list",
                "insertion" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1", //incorrect command
                "insert" + SIGN_OF_SPLIT + "test" + SIGN_OF_SPLIT + "id" + SIGN_OF_SPLIT + "1",
                "printe" + SIGN_OF_SPLIT + "test", //incorrect command
                "print" + SIGN_OF_SPLIT + "test",
                "clearing" + SIGN_OF_SPLIT + "test", //incorrect command
                "clear" + SIGN_OF_SPLIT + "test",
                "drops" + SIGN_OF_SPLIT + "test", //incorrect command
                "drop" + SIGN_OF_SPLIT + "test",
                "eksit", //incorrect command
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
                "Do you want to try again? (<Y>-yes, <N>-no): " +
                INPUT_COMMAND_MESSAGE +
                "Please, connect to database! (For database connection you have to use command \"connect\")" +
                INPUT_COMMAND_MESSAGE +
                "Please, input your database name: Please, input your user name: Please, input your password: \n" +
                "Connection process...\n" +
                "\n" +
                "Connection has been successful!\n" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [connection]" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [helps]" +
                INPUT_COMMAND_MESSAGE +
                "List of commands:\n" +
                "\tclear" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes all rows in pointed table (table name).\n" +
                "\tconnect\n" +
                "\t\tconnects to database you need.\n" +
                "\tcreate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_data_type] ...\n" +
                "\t\tcreates a table with user pointed columns (table name must consist of one word).\n" +
                "\t\tTypes of column: int - integer, varchar([size]) - string with size,\n" +
                "\t\tdouble - floating point number.\n" +
                "\t\tExample: create student id int first_name varchar(45) mark double\n" +
                "\tdelete" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[row_value]\n" +
                "\t\tdeletes in pointed table row that contains pointed value (row value)\n" +
                "\t\tin pointed column (column name).\n" +
                "\tdrop" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdeletes a pointed table of current database.\n" +
                "\texit\n" +
                "\t\tcompletes database manager execution.\n" +
                "\thelp\n" +
                "\t\tprovides the information of all database manager commands.\n" +
                "\tinsert" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[column_name]" + SIGN_OF_SPLIT +
                "[column_value] ...\n" +
                "\t\tinserts a new row with data into table.\n" +
                "\tlist\n" +
                "\t\tdisplays all tables names of the current database.\n" +
                "\tprint" + SIGN_OF_SPLIT + "[table_name]\n" +
                "\t\tdisplays data of the given table (table name).\n" +
                "\tupdate" + SIGN_OF_SPLIT + "[table_name]" + SIGN_OF_SPLIT + "[key_column]" + SIGN_OF_SPLIT +
                "[key_value]" + SIGN_OF_SPLIT + "[column_name_for_new_value]" + SIGN_OF_SPLIT + "[new_value] ...\n" +
                "\t\tsets inputed values (new value) into row that has pointed value (key value) in pointed column\n" +
                "\t\t(key column).Example:\n" +
                "\t\tupdate test id 1 id 1 fname John weight 90.5" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [creates]" +
                INPUT_COMMAND_MESSAGE +
                "Table test was created" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [lister]" +
                INPUT_COMMAND_MESSAGE +
                "[test]" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [insertion]" +
                INPUT_COMMAND_MESSAGE +
                "The table has got new row." +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [printe]" +
                INPUT_COMMAND_MESSAGE +
                "\t|-----------------------------------------|\n" +
                "\t| id                                      |\n" +
                "\t|-----------------------------------------|\n" +
                "\t| 1                                       |\n" +
                "\t|-----------------------------------------|\n" +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [clearing]" +
                INPUT_COMMAND_MESSAGE +
                "This table was cleared." +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [drops]" +
                INPUT_COMMAND_MESSAGE +
                "The table was deleted." +
                INPUT_COMMAND_MESSAGE +
                "Undetected command [eksit]" +
                INPUT_COMMAND_MESSAGE +
                EXIT_MESSAGE, out.getData());
    }
}
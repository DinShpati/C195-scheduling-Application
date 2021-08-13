package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * connects to the database
 * */

public class DBConn {

    /**
     * JDBC URL
     * */
    private static final String protocol = "jdbc";
    private static final String vendorName = ":MySQL:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07T67";

    /**
     * JDBC URL
     * */
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    /**
     * Driver interface reference
     * */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    static Connection conn = null;

    private static final String username = "U07T67"; //Username
    private static final String password = "53689123207"; //Password

    /**
     * This starts the connection database
     * @return returns the connection
     * */
    public static Connection startConn()
    {
        try
        {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch (ClassNotFoundException exc)
        {
            System.out.println(exc.getMessage());
        }

        catch (SQLException exc)
        {
            System.out.println(exc.getMessage());
        }

        return conn;
    }

    /**
     * This closes the connection to the database
     * */
    public static void closeConn()
    {
        try {
            conn.close();
            System.out.println("Connection closed!");
        }
        catch (SQLException exc)
        {
            System.out.println("Error:" + exc.getMessage());
        }
    }
}

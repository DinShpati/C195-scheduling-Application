package Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Used to query the database
 */
public class DBQuery {
    public static Statement statement;

    /**
     * Create Statement Object
     * @param conn the connection to the database
     * @throws SQLException if the query goes wrong
     * */
    public static void setStatementobj(Connection conn) throws SQLException {
        statement = conn.createStatement();
    }

    /**
     * Return statement object
     * @return the Statement
     * */
    public static Statement getStatementobj()
    {
        return statement;
    }

}
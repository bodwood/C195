package Utility;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {
    private static Statement statement;
    //create statement object

    public static void createStatement(Connection conn) throws SQLException {
        statement = conn.createStatement();

    }
    //return Statement object

    public static Statement getStatement() {
        return statement;
    }
}

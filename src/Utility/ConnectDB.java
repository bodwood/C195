package Utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    //jdbc url parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "// wgudb.ucertify.com/WJ06lwO";
//jdbc url
    private static final String jdbcURL = protocol+vendorName+ipAddress;
//driver interface reference
    private static final String mysqljdbcdriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    //username and password
    private static final String username = "U06lwO";
    private static String password = "53688802790";

    public static Connection getConnection() {
        try {
            Class.forName(mysqljdbcdriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: " +e.getMessage());
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
            System.out.println("Connection closed");
        }
        catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

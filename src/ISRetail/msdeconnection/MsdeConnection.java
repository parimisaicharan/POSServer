/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * USAGE
 * 
 * POJO and DO Class for Connection to Database and Connection Pooling
 * 
 * 
 */
package ISRetail.msdeconnection;

import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.Webservices.MsdeConnectionDetailsPojo;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MsdeConnection {

    public static String getConnectstring() {
        return connectstring;
    }

    public static void setConnectstring(String aConnectstring) {
        connectstring = aConnectstring;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }
    Connection con = null;
    Statement statement = null;
    private static String connectstring;
    private static String username;
    private static String password;

    /**
     * Creates a new instance of MsdeConnection
     */
    public MsdeConnection() {
    }

    /**
     * Closing the Database Connection
     */
    public Connection createConnection1() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = java.sql.DriverManager.getConnection("jdbc:sqlserver://localhost:1439;databaseName=ahddb", "sa", "satahd");
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
            return null;
        }

    }

    /**
     * Creating a connection and Returning Database Connection
     */
    public Connection createConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = java.sql.DriverManager.getConnection(connectstring, username, password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Trace in getConnection() : " + e.getMessage());
            return null;
        }

    }

    public void checkConnection() {
        String usernamefordb = "";
        String portnumb = "";
        String dbname = "";
        boolean sqlStart = false;
        try {
            BackgroundPropertiesFromFile bf = new BackgroundPropertiesFromFile();
            MsdeConnectionDetailsPojo msdeConnectionDetailsPojo;
            msdeConnectionDetailsPojo = bf.getMsdeConnectionDetails();
            usernamefordb = msdeConnectionDetailsPojo.getUsername();
            portnumb = msdeConnectionDetailsPojo.getPortno();
            dbname = msdeConnectionDetailsPojo.getDbname();

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try {
                con = java.sql.DriverManager.getConnection(connectstring, username, password);
            } catch (Exception e) {
                Runtime.getRuntime().exec("net start \"SQL Server (MSSQLSERVER)\"");//Added by Balachander V on 28.8.2021 to start SQL Services Automatically if Stopped.
                Thread.sleep(15000);
                con = java.sql.DriverManager.getConnection(connectstring, username, password);
            }

        } catch (Exception ex) {
            String msg = ex.getMessage();
            // System.out.println("MSG IS:"+msg);
            if (msg.equalsIgnoreCase("The TCP/IP connection to the host  has failed. java.net.ConnectException: Connection timed out: connect")) {
                JOptionPane.showMessageDialog(null, "Either your hostname or IP address is incorrect in config file or the port no given is incorrect or occupied.\nPlease check and correct the entries.", "Error Message", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, msg, "Error Message", JOptionPane.ERROR_MESSAGE);
            }
//            else if(msg.equalsIgnoreCase("Login failed for user"+" "+usernamefordb)){
//            JOptionPane.showMessageDialog(null,msg);
//            }else if(msg.equalsIgnoreCase("The port number"+" "+portnumb+" is not valid")){
//            JOptionPane.showMessageDialog(null,msg);
//            }
//            else if(msg.equalsIgnoreCase("Cannot open database requested in login"+" "+dbname+"."+"Login fails")){
//             JOptionPane.showMessageDialog(null,msg);
//            }
            System.exit(0);
        }

    }

    /**
     * Closing the Database Connection
     */
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.Logging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMaster;
import ISRetail.plantdetails.SiteMasterDO;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author ewitsupport
 */
public class ApiResponseLogger {

    //private static final String drivename = getCurrentDrive(System.getProperty("user.dir"));
    private static final String drivename = System.getProperty("user.dir");
    private static String LOG_DIRECTORY = "";
    private static String FILE_NAME = "";

    private Logger logger;
    private BufferedWriter writer;
    private static String  siteid= "";

    public ApiResponseLogger() {
        //Check if the directory exists, create it if not

        System.err.println("Inside UPI in API Response");
        LOG_DIRECTORY = drivename + "\\POSS LOGS\\KARNIVAL LOGS\\";
        FILE_NAME = "Karnival_responses.log";

        System.err.println("class name");
        System.err.println("LOG_DIRECTORY: " + LOG_DIRECTORY);
        MsdeConnection msde = new MsdeConnection();
        Connection con = msde.createConnection();
        SiteMasterDO smdo = new SiteMasterDO();
        siteid = smdo.getSiteId(con);
        File directory = new File(LOG_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory if it doesn't exist
        }

        try {
            writer = new BufferedWriter(new FileWriter(LOG_DIRECTORY + FILE_NAME, true));
            logger = Logger.getLogger(ApiResponseLogger.class.getName());
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logResponse(String response) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String formattedDateTime = currentTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a"));

            writer.write(formattedDateTime + " - " + "Store Code:" + siteid + " " + response);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to get the current drive
    private static String getCurrentDrive(String path) {
        System.err.println("String path: " + path);
        if (path != null && path.length() >= 2) {
            return path.substring(0, 1) + ":";
        } else {
            return "";
        }
    }
}

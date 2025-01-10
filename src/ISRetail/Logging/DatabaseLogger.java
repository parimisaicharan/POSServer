/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.Logging;

import ISRetail.msdeconnection.MsdeConnection;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.jdbc.JDBCAppender;

/**
 *
 * @author eyeplus
 */
public class DatabaseLogger {

    public static void setDatabaseLogger() {
        try {
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(Level.ERROR); 
            JDBCAppender jdbdappender = new JDBCAppender();
            jdbdappender.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            jdbdappender.setURL(MsdeConnection.getConnectstring());
            jdbdappender.setUser(MsdeConnection.getUsername());
            jdbdappender.setPassword(MsdeConnection.getPassword());
            jdbdappender.setSql("insert into tbl_pos_log values('POSSERVER',GETDATE(),'%c:%M:%L','%p','%m')");
            rootLogger.addAppender(jdbdappender);
        } catch (Exception e) {
            System.out.println("Failed to add appender !!");
        }
    }
    
}

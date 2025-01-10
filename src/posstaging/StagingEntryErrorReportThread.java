/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.PosStagingEntryReport.CheckStagingEntry;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public class StagingEntryErrorReportThread implements Runnable {
    //   private Thread stgthread;

    /*   public StagingEntryErrorReportThread(Thread stgthread) {
    //  this.con=con; 
    this.stgthread=stgthread;
    
    }   */
    public void run() {
        //Code Added on Feb 3rd 2010 for testing posstaging entry check
        MsdeConnection msdeConnection = new MsdeConnection();
        Connection conn = null;
        while (true) {
            try {
                if (conn == null) {
                    conn = msdeConnection.createConnection();
                }
                if (conn != null) {
                    CheckStagingEntry checkStagingEntry = new CheckStagingEntry();
                    checkStagingEntry.checkStagingEntries();
                    System.out.println("Report Executed");
                    Thread.sleep(PosConfigParamDO.getPosStagingErrorReportFreq(conn));
                }
            } catch (Exception e) {
            } finally {
            }
        }


    }
}

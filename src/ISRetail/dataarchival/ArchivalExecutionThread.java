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
 * Data Archival Thread.This class keeps un looking for the Data Archival Patch send by ISR and Executes the 
 * SQL Statements
 * 
 * 
 */
package ISRetail.dataarchival;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextArea;


public class ArchivalExecutionThread implements Runnable {
    private JTextArea statusArea;
    public ArchivalExecutionThread(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
    public void run() {
        MsdeConnection msdeConnection;
        //Connection con;
        Connection con=null; /*initialized by arun on 16 Apr 2012 for properly Closing DB connections*/
        ArrayList<DataArchiveRecordPOJO> dataArchiveRecordPOJOs;
        DataArchivalExecutionService dataArchivalExecutionService;
        ArchivalStatusSendingService archivalStatusSendingService;
        int freq = 0;
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                if(con == null){
                    con = msdeConnection.createConnection();
                }
                try {
                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        dataArchivalExecutionService = new DataArchivalExecutionService(this.statusArea);
                        statusArea.append("\n\nRunning.......");
                        dataArchiveRecordPOJOs = dataArchivalExecutionService.callWebService(ServerConsole.siteID, con);
                        if (dataArchiveRecordPOJOs != null) {
                            archivalStatusSendingService = new ArchivalStatusSendingService(statusArea);
                            archivalStatusSendingService.callWebService(ServerConsole.siteID, dataArchiveRecordPOJOs);
                        }
                        freq = PosConfigParamDO.getArchivalReq_Freq(con);
                        statusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                        Thread.sleep(freq);
                    } else {
                        freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                        statusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                        Thread.sleep(freq);//half an hour
                    }
                } catch (Exception e) {
                    statusArea.append("\nArchival Failed : Exception Occured : " + e.getMessage());
                    e.printStackTrace();
                    freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con); 
                    statusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);//half an hour
                } finally {
                    dataArchiveRecordPOJOs = null;
                    dataArchivalExecutionService = null;
                    archivalStatusSendingService = null;                 
                    /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
                    if(con != null){
                        con.close();
                        con=null;
                    }
                    /*End of code Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            statusArea.append("\nDownload Failed : Exception Occured" + e.getMessage());
        }
    }
}

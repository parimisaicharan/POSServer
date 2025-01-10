/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import VersionManagement.VersionManagementWebService;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class VersionUpdateThread implements Runnable {

    private JTextArea statusArea;
    private String typeofthread;
    ArticleWebService articleWebService = null;
    private int i = 0;

    public VersionUpdateThread(ArticleWebService articleWebService) {
        this.articleWebService = articleWebService;
    }

    public VersionUpdateThread(JTextArea statusArea, String typeofthread) {
        this.statusArea = statusArea;
        this.typeofthread = typeofthread;
    }

    public void run() {
        MsdeConnection msdeConnection;
        //Connection con;
        Connection con = null; /*initialized by arun on 16 Apr 2012 for properly Closing DB connections*/
        VersionManagementWebService versionManagementWebService = new VersionManagementWebService(statusArea, typeofthread);

        int freq = 0;
        try {
            Thread.sleep(2000);
            while (true) {
                msdeConnection = new MsdeConnection();
                //con = msdeConnection.createConnection();
                if (con == null) {
                    con = msdeConnection.createConnection();
                }
                try {

                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        statusArea.append("\n\nRunning.......");

                        int noofRowsUpdated = versionManagementWebService.execute();
                        statusArea.append("\n\n**********Version updates***********");
                        if (noofRowsUpdated > 0) {
                            statusArea.append("\n" + noofRowsUpdated + " Rows Successfully Downloaded");
                        } else {
                            statusArea.append("\nNo Records Downloaded");
                        }
                    }
                    statusArea.append("\nSleeping.....");
                    // Thread.sleep(ServerConsole.getMasterDataFreq());
                    freq = PosConfigParamDO.getVersionCheckReqFreq(con);
                    statusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);

                } catch (Exception e) {
                    statusArea.append("\nDownload Failed : Exception Occured:" + e.getMessage());
                    freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    statusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    try {
                        con.close();
                        con = null;
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                    Thread.sleep(freq);                    
                } finally {
                    msdeConnection = null;
                    try {
                        if (con != null) {
                            con.close();
                            con = null;
                        }

                    } catch (SQLException se1) {
                        se1.printStackTrace();
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
}

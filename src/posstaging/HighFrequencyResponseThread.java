/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class HighFrequencyResponseThread implements Runnable {

    private JTextArea statusArea;

    public HighFrequencyResponseThread(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con = null;
        int freq = 0;
        HighFrequencyResponseWebService highFrequencyResponseWebService = new HighFrequencyResponseWebService();
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                try {
                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        getStatusArea().append("\n\nRunning.......");
                        highFrequencyResponseWebService.callWebService(statusArea);
                    }
                    freq = PosConfigParamDO.getTransactionRespReqFreq(con);
                    getStatusArea().append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);
                } catch (Exception e) {
                    getStatusArea().append("\nDownload Failed : Exception Occured : " + e.getMessage());
                    e.printStackTrace();
                    freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    getStatusArea().append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);
                } finally {
                    msdeConnection = null;
                    con.close();
                    con = null;
                }
            }
        } catch (Exception e) {
            getStatusArea().append("\nDownload Failed : Exception Occured" + e.getMessage());
            e.printStackTrace();
        }
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
}

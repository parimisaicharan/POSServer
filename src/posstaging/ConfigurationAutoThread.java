/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.configurations.*;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class ConfigurationAutoThread implements Runnable {

    Thread t;
    private JTextArea statusArea;
    ConfigurationDetails details;
    private int i = 0;

    public ConfigurationAutoThread(ConfigurationDetails details, JTextArea statusArea) {
        this.details = details;
        this.statusArea = statusArea;

    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con;
        int freq = 0;
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                try {
                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        statusArea.append("\nRunning.......");
                        details.sendRequest(statusArea);
                    }
                    freq = PosConfigParamDO.getConfigDataReqFreq(con);
                    statusArea.append("\nSleeping.....for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);
                } catch (Exception e) {
                    setI(0);
                    statusArea.append("\nDownload Failed : Exception Occured" + e.getMessage());
                    freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    statusArea.append("\nSleeping.....for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);
                    
                } finally {
                    msdeConnection = null;
                    con.close();
                    con = null;
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
}

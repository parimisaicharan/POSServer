/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class ErrorLogThread implements Runnable {

    JTextArea StatusArea;

    public ErrorLogThread(JTextArea StatusArea) {
        this.StatusArea = StatusArea;
    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con;
        int freq=0;
         ErrorLogWebService errorLogWebService = new ErrorLogWebService(StatusArea);
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                try {
                    if (ServerConsole.performConnectionChecking(StatusArea)) {
                        ErrorLogDO errorLogDO = new ErrorLogDO();
                        StatusArea.append("\n\nRunning.......");
                       
                        msdeConnection = new MsdeConnection();
                        con = msdeConnection.createConnection();
                        ArrayList errorlist = new ArrayList();
                        errorlist = errorLogDO.checkerrorlogandpass(con);
                        errorLogWebService.execute(errorlist);
                        freq=PosConfigParamDO.getErrorLogFreq(con);
                         StatusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                        Thread.sleep(freq);
                    } else {
                        freq=PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                        StatusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                        Thread.sleep(freq);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    StatusArea.append("\n Failed : Exception Occured :" + ex.getMessage());
                    freq=PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    StatusArea.append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);
                }finally{
                     msdeConnection = null;
                     con.close();
                     con = null;
                }
                StatusArea.append("\nSleeping.....");
            }
        } catch (Exception e) {
            e.printStackTrace();
            StatusArea.append("\n Failed : Exception Occured" + e.getMessage());
        }
    }
}

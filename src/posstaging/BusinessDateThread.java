/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import posstaging.CheckSystemDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author enteg
 */
public class BusinessDateThread implements Runnable {

    private JTextArea statusArea;

    public BusinessDateThread(JTextArea statusArea) {
        this.setStatusArea(statusArea);
    }

    public void run() {
        while (true) {
            try {
                //   if (ServerConsole.performConnectionChecking(statusArea)) {
                //      getStatusArea().append("\n\nRunning.......");
                MsdeConnection connection = new MsdeConnection();
                Connection con = connection.createConnection();
                CheckSystemDate checkSystemDate = new CheckSystemDate();
                checkSystemDate.getBusinessDateFromWebservice();
                //    getStatusArea().append("\nSystem Date:" + checkSystemDate.getSystemDate());
                //     getStatusArea().append("\nBusiness Date:" + checkSystemDate.getBusinessDate());
                //    getStatusArea().append("\nFiscal Year:" + checkSystemDate.getFiscalYear());
                //   new SiteMasterDO().updateSystemAndPostingDates(con, checkSystemDate.getSystemDate(), checkSystemDate.getBusinessDate(), checkSystemDate.getFiscalYear());
                new SiteMasterDO().updateSyncdate(con, checkSystemDate.getBusinessDate());
                if (con != null) {
                    ServerConsole.setBusinessDate(con, null);
                }
                //   getStatusArea().append("\nSleeping.....");
                Thread.sleep(PosConfigParamDO.getBusinessDateReqFreq(con));
                //    }else{
                //     Thread.sleep(300000);
                //   }

            } catch (Exception e) {
                e.printStackTrace();
                getStatusArea().append("\nDownload Failed : Exception Occured:" + e.getMessage());
            }
        }
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
}

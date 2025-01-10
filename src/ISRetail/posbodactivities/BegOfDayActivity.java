/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.posbodactivities;

import ISRetail.creditnote.CreditNoteDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.stock.StockMasterDO;
import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author enteg
 */
public class BegOfDayActivity {

    JTextArea statusArea;

    public BegOfDayActivity(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public void performBegOfDayActivities() {
            CreditNoteDO creditNoteDO;
            StockMasterDO stockMasterDO;
            SiteMasterDO siteMasterDO;
            Connection con;
            MsdeConnection msdeConn;
            try {
                statusArea.append("\nClosing all Expired Credit Notes And GVs.......");
                msdeConn = new MsdeConnection();
                con = msdeConn.createConnection();
                creditNoteDO = new CreditNoteDO();
                stockMasterDO = new StockMasterDO();
                siteMasterDO = new SiteMasterDO();
                int numCred = creditNoteDO.closeAllExpiredCreditNotes(con, siteMasterDO.getSystemDate(con));
                int numGV = stockMasterDO.closeAllExpiredGVs(con, siteMasterDO.getSystemDate(con));
                statusArea.append("\nClosed all Expired Credit Notes And GVs");
                statusArea.append("\nNo: Of Closed Credit Notes =" + numCred);
                statusArea.append("\nNo: Of Closed GVs =" + numGV);

                con.close();

            } catch (Exception e) {
                statusArea.append("\nClosing all Expired Credit Notes And GVs- Failed : Exception Occured:" + e.getMessage());

            } finally {
                creditNoteDO = null;
                stockMasterDO = null;
                con = null;
                msdeConn = null;
                siteMasterDO = null;
            }
    }
}

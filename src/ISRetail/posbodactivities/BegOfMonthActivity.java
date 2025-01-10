/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.posbodactivities;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.stock.StockMasterDO;
import java.sql.Connection;
import javax.swing.JTextArea;

/**
 *
 * @author enteg
 */
public class BegOfMonthActivity {
JTextArea statusArea;

    public BegOfMonthActivity(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public void performBegOfMonthActivities() {
            StockMasterDO stockMasterDO;
            Connection con;
            MsdeConnection msdeConn;
            try {
                statusArea.append("\n\nArchiving all Redeemed GVs");
                msdeConn = new MsdeConnection();
                con = msdeConn.createConnection();
                stockMasterDO = new StockMasterDO();
                int numGV = stockMasterDO.archiveAllRedeemedGVs(con);
                statusArea.append("\nArchived all Redeemed GVs");
                statusArea.append("\nNo: Of Redeemed GVs =" + numGV);
                con.close();

            } catch (Exception e) {
                statusArea.append("\n Archiving all Redeemed GVs-Failed : Exception Occured:" + e.getMessage());

            } finally {
                stockMasterDO = null;
                con = null;
                msdeConn = null;
            }
    }
}

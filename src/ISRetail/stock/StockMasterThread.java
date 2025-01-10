/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.stock;

import ISRetail.article.*;
import ISRetail.Helpers.NetConnection;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.stock.StockMasterPOJO;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author Administrator
 */
public class StockMasterThread implements Runnable {

    StockMasterService stockWebService = null;
    private int i = 0;
    private int noofRowsUpdated = 0;
    ArrayList<MaterialListPOJO> materialLists;
    String site;
    boolean allFlag;
    JTextArea statusArea;

    public StockMasterThread(StockMasterService stockWebService, String site, ArrayList<MaterialListPOJO> materialLists, boolean allFlag, JTextArea statusArea) {
        this.stockWebService = stockWebService;
        this.site = site;
        this.materialLists = materialLists;
        this.allFlag = allFlag;
        this.statusArea = statusArea;
    }

    public void run() {
        try {
            boolean valid = true;
            statusArea.append("Checking For Internet Connection..");
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3 || internetconnection == 2) {
                valid = false;
                setI(2);
                statusArea.append("\nDownload Failed : Check Your Network for Internet Connection !");
                return;
            } else {
                 statusArea.append("Checking PI server is reachable..");
                internetconnection = NetConnection.checkXIConnection();
                if (internetconnection == 2) {
                    valid = false;
                    setI(3);
                    statusArea.append("\nFailed : Incorrect Server Address : Contact Administrator to Fix the Problem!");
                    return;
                } else if (internetconnection == 3 || internetconnection == 4) {
                    valid = false;
                    setI(4);
                    statusArea.append("\nFailed : Unknown Error : Contact Administrator to Fix the Problem!");
                    return;
                }
            }
            if (valid) {
                MsdeConnection msdeConnection = new MsdeConnection();
                Connection connection = msdeConnection.createConnection();
                ArrayList<StockMasterPOJO> stockList = stockWebService.callWebService(site, materialLists, allFlag);
                 noofRowsUpdated = stockWebService.updateStockMasterFromISRToPos(stockList);
                if (noofRowsUpdated == 0) {
                    setI(5);
                } else {
                    setI(1);
                }
            }
            return;
        } catch (Exception e) {
            setI(0);
            return;
        }

    }

    public int getNoofRowsUpdated(){
        return noofRowsUpdated;
    }
    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

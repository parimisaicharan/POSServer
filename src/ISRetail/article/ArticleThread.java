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
 * USAGE
 * Article Thread is the class to Down Load articles from the ISR
 * 
 * 
 */
package ISRetail.article;

import ISRetail.Helpers.NetConnection;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import java.sql.Connection;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;
import posstaging.ArticleWebService;


public class ArticleThread implements Runnable {

    ArticleWebService articleWebService = null;
    private int i = 0;
    JTextArea statusArea;
    private static Logger logger = Logger.getLogger(ArticleThread.class.getName());

    public ArticleThread(ArticleWebService articleWebService, JTextArea statusArea) {
        this.articleWebService = articleWebService;
        this.statusArea = statusArea;
    }

    public void run() {
        try {
            boolean valid = true;
            statusArea.append("\nChecking For Internet Connection..");
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3 || internetconnection == 2) {
                valid = false;
                setI(2);
                statusArea.append("\nDownload Failed : Check Your Network for Internet Connection !");
                return;
            } else {
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
                statusArea.append("\nSending request to ISR...");
                logger.error(" INFO : Downloading master data through manual download ");
                articleWebService.callWebService(new SiteMasterDO().getSiteId(connection));
                int noofRowsUpdated = articleWebService.downloadDataFromISR(connection);
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 *
 * @author ewitsupport
 */
public class StockAutoThread implements Runnable{
     private JTextArea statusArea;
    ArticleWebService articleWebService = null;
    private int i = 0;
    private static Logger logger = Logger.getLogger(ArticleAutoThread.class.getName());

    public StockAutoThread(ArticleWebService articleWebService) {
        this.articleWebService = articleWebService;
    }

    public StockAutoThread(JTextArea statusArea) {
        this.setStatusArea(statusArea);
    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con;
        int freq=0;
        int posstartupflag = 0;
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                
                try {
                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        Thread.sleep(10000);
                        getStatusArea().append("\n\nStock Master Running.......");
                        articleWebService = new ArticleWebService(getStatusArea());
                        System.err.println("posstartupflag value: "+posstartupflag);
                        if (posstartupflag == 0) {
                            getStatusArea().append("\nDownloading Stock Master");                            
                            articleWebService.setStockMasterFlag("X");                            
                            articleWebService.setIsAutoReq("X");
                        } 
                        logger.error(" INFO : Downloading stock data through automatic download ");
                        articleWebService.callWebService(ServerConsole.siteID);
                        int noofRowsUpdated = articleWebService.downloadDataFromISR(con);
                        if (noofRowsUpdated > 0) {
                            getStatusArea().append("\nASDT - " + noofRowsUpdated + " Rows Successfully Downloaded");
                        } else {
                            getStatusArea().append("\nASDT - No Records Downloaded");
                        }
                    }
                    posstartupflag = 1;
                    freq=PosConfigParamDO.getStockMasterDataReqFreq(con);
                    getStatusArea().append("\nAutoStockDownload - Sleeping.....for "+(freq/1000)+" Seconds");
                    Thread.sleep(freq);
                    System.err.println("after stock thread.sleep");
                    
                    
                } catch (Exception e) {
                    logger.error("Download Failed : Exception Occured:" + e.getMessage());
                    getStatusArea().append("\nDownload Failed : Exception Occured:" + e.getMessage());
                    freq=PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    Thread.sleep(freq);
                    getStatusArea().append("\nSD sleeping.....for "+(freq/1000)+" Seconds");                    
                } finally {
                    msdeConnection = null;
                    con.close();
                    con = null;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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

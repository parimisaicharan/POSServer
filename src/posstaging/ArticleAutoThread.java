/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import posstaging.ArticleWebService;
import ISRetail.msdeconnection.MsdeConnection;

import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.db.PopulateData;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class ArticleAutoThread implements Runnable {

    private JTextArea statusArea;
    ArticleWebService articleWebService = null;
    private int i = 0;
    private static Logger logger = Logger.getLogger(ArticleAutoThread.class.getName());

    public ArticleAutoThread(ArticleWebService articleWebService) {
        this.articleWebService = articleWebService;
    }

    public ArticleAutoThread(JTextArea statusArea) {
        this.setStatusArea(statusArea);
    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con;
        int freq = 0;
        int posstartupflag = 0;
        try {
            while (true) {
                msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();

                try {
                    if (ServerConsole.performConnectionChecking(statusArea)) {
                        getStatusArea().append("\n\nRunning.......");
                        articleWebService = new ArticleWebService(getStatusArea());
                        if (posstartupflag == 0) {
                            getStatusArea().append("\nDownloading All Masters");
                            articleWebService.setArticleCharacteristicsFlag("X");
                            articleWebService.setArticleDiscountFlag("X");
                            articleWebService.setArticleFlag("X");
                            articleWebService.setArticleTaxFlag("X");
                            articleWebService.setArticleUcpFlag("X");
                            articleWebService.setEmployeeMasterFlag("X");
                            articleWebService.setGvMasterFlag("X");
                            articleWebService.setOtherChargesFlag("X");
                            articleWebService.setPromotionsFlag("X");
                            articleWebService.setSiteMasterFlag("X");
                            articleWebService.setStockMasterFlag("X");
                            articleWebService.setConditionTypeMasterFlag("X");
                            articleWebService.setIsAutoReq("X");
                        } else {
                            // start : master data download development
                            getStatusArea().append("\nDownloading Master Data");
                            articleWebService.setSiteMasterFlag("X");
                            articleWebService.setPromotionsFlag("X");
                            articleWebService.setEmployeeMasterFlag("X");
                            articleWebService.setStockMasterFlag("X");
                            articleWebService.setIsAutoReq("X");
                            // end : master data download development
                        }
                        logger.error(" INFO : Downloading master data through automatic download ");
                        articleWebService.callWebService(ServerConsole.siteID);
                        int noofRowsUpdated = articleWebService.downloadDataFromISR(con);
                        if (noofRowsUpdated > 0) {
                            getStatusArea().append("\n AMD - " + noofRowsUpdated + " Rows Successfully Downloaded");
                        } else {
                            getStatusArea().append("\nAMD - No Records Downloaded");
                        }
                    }
                    posstartupflag = 1;
                    freq = PosConfigParamDO.getMasterDataReqFreq(con);
                    getStatusArea().append("\nAutoMasterDownload - Sleeping.....for " + (freq / 1000) + " Seconds");
                    Thread.sleep(freq);

                } catch (Exception e) {
                    logger.error("Download Failed : Exception Occured:" + e.getMessage());
                    getStatusArea().append("\nDownload Failed : Exception Occured:" + e.getMessage());
                    freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                    Thread.sleep(freq);
                    getStatusArea().append("\nSleeping.....for " + (freq / 1000) + " Seconds");
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

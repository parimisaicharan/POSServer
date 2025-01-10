/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.stock;

import ISRetail.article.MaterialListPOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JTextArea;

/**
 *
 * @author eyeplus
 */
public class AutoStockDirectDownloadThread implements Runnable {

    private JTextArea statusArea;
    private StockMasterThread stockMasterThread;
    private Thread thread;
    private boolean isFirstRun;

    public AutoStockDirectDownloadThread(JTextArea statusArea) {
        this.setStatusArea(statusArea);
        this.isFirstRun = true;
    }

    public void run() {
        try {
            while (true) {
//
//                if (isFirstRun) {
//                    // Wait for few minutes during the first run
//                    try {
//                         getStatusArea().append("\nStock Direct Download- Starting after 1 minute");
//                        Thread.sleep(1 * 60 * 1000); // 5 minutes in milliseconds
//                       
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    isFirstRun = false;
//                } else {
                    MsdeConnection msdeConnection = null;
                    Connection con = null;
                    int freq = 3600;
                    ArrayList<MaterialListPOJO> materialLists = null;
                    StockMasterService stockMasterService;
                    StockMasterDO smdo = null;
                    String flag = "";
                    try {
                        Thread.sleep(freq);
                        msdeConnection = new MsdeConnection();
                        con = msdeConnection.createConnection();
                        flag = PosConfigParamDO.getValForThePosConfigKey(con, "stock_download_enable");
                        if (Validations.isFieldNotEmpty(flag) && flag.equalsIgnoreCase("Y")) {
                            freq = Integer.parseInt(PosConfigParamDO.getValForThePosConfigKey(con, "stock_direct_download_freq"));
                            if (con != null) {
                                stockMasterService = new StockMasterService(statusArea);
                                smdo = new StockMasterDO();
                                materialLists = smdo.getArticlsForStockDirectDownlaod(con, freq);

                                if (materialLists != null && materialLists.size() > 0) {
                                    stockMasterThread = new StockMasterThread(stockMasterService, ServerConsole.siteID, materialLists, false, statusArea);
                                    thread = new Thread(stockMasterThread);
                                    thread.setDaemon(true);
                                    thread.start();
                                    thread.join();
                                    getStatusArea().append("\nStock Direct Download- " + stockMasterThread.getNoofRowsUpdated() + " Rows Successfully Downloaded");
                                } else {
                                    getStatusArea().append("\nNo articles available for stock direct download");
                                }
                            }
                        }
                        getStatusArea().append("\nStockDirectDownload - sleeping.....for " + freq + " Seconds");
                        getStatusArea().append("\n************Stock Direct Download End******************");
                        Thread.sleep(freq * 1000);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    } finally {
                        msdeConnection = null;
                        con.close();
                        con = null;
                    }
//                }
            }
        } catch (Exception e) {
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

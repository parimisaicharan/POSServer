/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.serverconsole;

import ISRetail.Logging.ApiResponseLogger;
import ISRetail.billing.BillingDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.utility.db.PopulateData;
import Titan.POSInvoice.POS;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author ewitsupport
 */
public class InvoiceDetailsSentThread implements Runnable {

    private boolean running = true;
    ApiResponseLogger logger = new ApiResponseLogger();

    public void run() {
        running = true;
        boolean datasendtokarnival = true;
        MsdeConnection msdeConnection = new MsdeConnection();
        Connection conn = null;
        PopulateData populateData = null;
        int freq = 0;
        try {
            while (true) {
                logger.logResponse("Karnival thread started");
                conn = msdeConnection.createConnection();
                BillingDO bdo = new BillingDO();
                String invoicevalues = "";
                boolean sendStatus = true;
                ArrayList<String> invlist = new ArrayList<String>();
                populateData = new PopulateData();
                String karnival_Url = populateData.getConfigValue(conn, "Karnival_Digital_InvoiceUrl");
                String karnivalPrescriptionEnabled = populateData.getConfigValue(conn, "Karnival_PrescriptionCopy_Enabled");
                int statuscount = PopulateData.getKarnivalinvoicecount(conn);
                logger.logResponse("Existing Karnival Invoice count with status N:" + statuscount);
                if (!(karnival_Url == null)) {
                    invoicevalues = bdo.getInvoiceNoToSent(conn, "N");
                    if (invoicevalues.startsWith("T") || invoicevalues.startsWith("I")) {
                        if (!(karnivalPrescriptionEnabled == null)) {
                            if (!karnivalPrescriptionEnabled.equalsIgnoreCase("Y")) {
                                sendStatus = false;
                            }
                        } else {
                            sendStatus = false;
                        }
                    }
                    if (sendStatus) {
                        if (invoicevalues.length() > 0) {
                            System.out.println("Before calling Karnival");
                            logger.logResponse("Inside thread Sending Karnival Invoice No:" + invoicevalues);
                            datasendtokarnival = POS.getInvoiceValues(invoicevalues, conn);
                            //POS.getInvoiceValues(invoicevalues, conn);
                            System.err.println("datasendtokarnival:" + datasendtokarnival);
//                            if (!datasendtokarnival) {
//                                stop();
//                                restartThread();
//                            }
                        }
                    }
                }
                if (datasendtokarnival) {
                    freq = PosConfigParamDO.getInvoiceDetailsSentReqFreq(conn);
                    if (freq > 0) {
                        Thread.sleep(freq);
                        logger.logResponse("Karnival thread entered in sleep mode");
                    } else {
                        Thread.sleep(500000);
                    }
                    logger.logResponse("Karnival thread exited from sleep mode");
                } else if (!datasendtokarnival) {
                    System.err.println("Data not sent to karnival");
                    freq = PosConfigParamDO.getInvoiceDetailsSentReqFreq(conn);
                    if (freq > 0) {
                        Thread.sleep(freq);
                        logger.logResponse("Karnival thread entered in sleep mode");
                    } else {
                        Thread.sleep(500000);
                    }
                    logger.logResponse("Karnival thread exited from sleep mode");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.logResponse("Exception occured in the Karnival thread");
            logger.logResponse("Exception: " + e);
            stop();
            restartThread();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDetailsSentThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void restartThread() {
        // Create a new thread with the same runnable and start it
        new Thread(new InvoiceDetailsSentThread()).start();
        logger.logResponse("Karnival thread Restarted");
        System.err.println("Karnival thread Restarted");
    }

    private void stop() {
        running = false;
        logger.logResponse("Karnival thread Stoped ---> Going to Restart the thread");
        System.err.println("Karnival thread stoped ---> Going to Restart the thread");
    }

}

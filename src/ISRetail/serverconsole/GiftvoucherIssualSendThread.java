/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.serverconsole;

import ISRetail.billing.BillingDO;
import ISRetail.msdeconnection.MsdeConnection;
import Titan.POSInvoice.POS;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ewitsupport
 */
public class GiftvoucherIssualSendThread implements Runnable{
    public void run() {
        MsdeConnection msdeConnection = new MsdeConnection();
        Connection conn = null;
        int freq=0;
        try {
            while (true) {
                conn = msdeConnection.createConnection();
                BillingDO bdo = new BillingDO();
                String invoicevalues = "";
                ArrayList<String> invlist = new ArrayList<String>();
                invoicevalues = bdo.getInvoiceNoToSent(conn, "N");
                if (invoicevalues.length() > 0) {
                    
                    POS.getInvoiceValues(invoicevalues, conn);
                }
               freq=PosConfigParamDO.getInvoiceDetailsSentReqFreq(conn);
               if(freq>0){
                       Thread.sleep(freq);
               }else{
                   Thread.sleep(500000);
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(InvoiceDetailsSentThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftVoucherIssualAndRedemptionService;

import ISRetail.billing.BillingDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.InvoiceDetailsSentThread;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ewitsupport
 */
public class GiftVoucherIssualSentThread implements Runnable {

    public void run() {
        MsdeConnection msdeConnection = new MsdeConnection();
        Connection conn = null;
        GiftVoucherIssualService giftVoucherIssual = new GiftVoucherIssualService();
        int freq = 0;
        int responseCode = 0;
        try {
            while (true) {
                conn = msdeConnection.createConnection();
                GiftVoucherDO gfDo = new GiftVoucherDO();
                GiftVoucherPojo gvPojo = new GiftVoucherPojo();
                gvPojo = gfDo.getGiftVoucherDetails(conn);
                if (Validations.isFieldNotEmpty(gvPojo.getGiftVoucherNo())) {
                    responseCode = giftVoucherIssual.sendGiftVoucherDetails(gvPojo);
                    if (responseCode == 200) {
                        gfDo.updateStatusOFGVTransDetails(conn, gvPojo.getGiftVoucherNo());
                    } else {
                    }
                }
                freq = PosConfigParamDO.getGVDetailsSentToSAPReqFreq(conn);
                if (freq > 0) {
                    Thread.sleep(freq);
                } else {
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

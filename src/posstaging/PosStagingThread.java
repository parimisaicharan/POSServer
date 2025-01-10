/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.CashReceipt.CashReceiptDO;
import ISRetail.GiftCardSelling.GiftCardSellingDO;
import ISRetail.Helpers.ConvertDate;
import ISRetail.quotations.QuotationsHeaderDO;
import ISRetail.SalesOrderBilling.InvoiceCancelwebservice;
import ISRetail.SalesOrderBilling.InvoiceCreationwebservice;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.MasterDetails;
import ISRetail.plantdetails.SystemDate;
import ISRetail.Webservices.Webservice;
import ISRetail.billing.Quickbillingwebservices;
import ISRetail.cashpayout.CashPayoutDO;
import ISRetail.creditnote.CreditNoteDO;
import ISRetail.customer.CustomerMasterDO;
import ISRetail.inquiry.InquiryDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.paymentdetails.AdvanceReceiptDO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SalesOrderHeaderDO;
import ISRetail.salesreturns.SalesReturnHeaderDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.serverconsole.StagingThread;
import ISRetail.services.ServiceAdvanceReceiptWebservice;
import ISRetail.services.ServiceInvoiceCreationwebservice;
import ISRetail.services.ServicesHeaderDO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import org.apache.log4j.Logger;

/**
 *
 * @author sukumar
 */
public class PosStagingThread implements Runnable {

    private JTextArea statusArea;
    StagingThread stagingThread;
    private static Logger logger = Logger.getLogger(PosStagingThread.class.getName());

    public PosStagingThread(StagingThread stagingThread, JTextArea statusArea) {
        this.statusArea = statusArea;
        this.stagingThread = stagingThread;
    }

    public void run() {
        MsdeConnection msdeConnection;
        Connection con=null;
        DataObject result;
        Webservice web;
        String updateStatus;
        PosStagingDO posStagingDO;
        SiteMasterDO siteMasterDO;
        PosStagingPOJO posStagingPOJO;
        SystemDate systemDate;
        int freq = 0;

        
        try {
            String checkInvoiceCreationAfterCancellation=getInvCreateandCancelCheck();
            while (true) {
                msdeConnection = new MsdeConnection();
                //con = msdeConnection.createConnection(); commented by arun on 16 Apr 2012
                /*Added by arun on 16 Apr 2012 for properly Closing DB connections*/
                if(con==null){
                    con = msdeConnection.createConnection();
                }
                /*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
               
                if (con == null) {
                    JOptionPane.showMessageDialog(stagingThread, "No connection to database !!", "Database connection failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    posStagingDO = new PosStagingDO();
                    siteMasterDO = new SiteMasterDO();
                    systemDate = siteMasterDO.getBusinessDate(con);
                    
                    try {
                        if (ServerConsole.performConnectionChecking(statusArea)) {
                            getStatusArea().append("\n\nRunning.......");
                            int sysdate = siteMasterDO.getSystemDate(con);
                            posStagingDO.updateStagingTableToAutoReinitiate(con, sysdate, PosConfigParamDO.getPeriodToResetTransAfterCancelled(con), "minute");//'second' it is stored in db, if minute give "minute" , if hour give "hour"
                            
                            getStatusArea().append("\n\nChecking POS Staging Table has any data to be sent..");
                            posStagingPOJO = posStagingDO.getPosValue(con,statusArea);
                            boolean isNotSuccess = true;
                            // boolean needToSend = true;
                            if (posStagingPOJO != null) {
                                getStatusArea().append("\nFinding POS Staging Table Record to be sent..");
                                if (Validations.isFieldNotEmpty(posStagingPOJO.getInterfaceID()) && Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID())) {
                                    if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF01")) {// For Customer
                                        getStatusArea().append("\n\nSending Customer Master :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getCusotmer(posStagingPOJO.getTransactionID(), con);
                                        web = new CustomerMasterDO();
                                        // sending request to the ISR
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        // if there is no exception then this line will update the POS status table
                                        if ((updateStatus != null && updateStatus.endsWith("true"))) {
                                            isNotSuccess = false;
                                            if(posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().equalsIgnoreCase("U")){
                                                posStagingDO.updateSapIdStatus(con, "C", posStagingPOJO.getMessageID());
                                            }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF03")) {// For Saleorder
                                        getStatusArea().append("\n\nSending Sale Order  :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getSaleOrder(con, posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new SalesOrderHeaderDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                            if(posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().equalsIgnoreCase("U")){
                                                posStagingDO.updateSapIdStatus(con, "C", posStagingPOJO.getMessageID());
                                            }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF05")) {// For Inquiry
                                        getStatusArea().append("\n\nSending Inquiry :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getInquiry(con, posStagingPOJO.getTransactionID());
                                        web = new InquiryDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("T"))) {
                                            isNotSuccess = false;
                                        }

                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF08")) {// For ackcreation
                                        getStatusArea().append("\n\nSending Acknowledgement Creation :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getSalesReturn(con, posStagingPOJO.getTransactionID());
                                        web = new SalesReturnHeaderDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("T"))) {
                                            isNotSuccess = false;
                                        }

                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF21")) {// For SalesReturn
                                        getStatusArea().append("\n\nSending Acknowledgement Updation / SalesReturn:" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getSalesReturn(con, posStagingPOJO.getTransactionID());
                                        web = new SalesReturnHeaderDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("T"))) {
                                            isNotSuccess = false;
                                        }

                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF06")) {// For AdvanceReceipt
                                        boolean isAdvanceReceipt = true;
                                        if (posStagingPOJO.getUpdateType() != null) {
                                            if (posStagingPOJO.getUpdateType().equalsIgnoreCase("F")) { // Only For Force Release
                                                getStatusArea().append("\n\nSending Advance Receipt Force Release :" + posStagingPOJO.getTransactionID());
                                                isAdvanceReceipt = false;
                                                result = MasterDetails.getSaleOrder(con, posStagingPOJO.getTransactionID(), "X");
                                                web = new AdvanceReceiptDO();
                                                updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                                getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                                logger.error(" INFO : Sending Advance Receipt Force Release  : " + posStagingPOJO.getTransactionID()+" Status : " + updateStatus);
                                                if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                                    isNotSuccess = false;
                                                }
                                            }
                                        }
                                        if (isAdvanceReceipt) {
                                            getStatusArea().append("\n\nSending Advance Receipt :" + posStagingPOJO.getTransactionID());
                                            result = MasterDetails.getAdvanceReceipt(con,posStagingPOJO.getTransactionID());
                                            web = new AdvanceReceiptDO();
                                            updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                            getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                            logger.error(" INFO : Sending Advance Receipt  : " + posStagingPOJO.getTransactionID()+" Status : " + updateStatus);
                                            if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                                isNotSuccess = false;
                                            }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF14")) {// For AdvanceReceipt SaleOrder Cancellation
                                        getStatusArea().append("\n\nSending AdvanceReceipt SaleOrder Cancellation :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        result = MasterDetails.getSaleOrderAdvanceReceiptCancelation(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new CreditNoteDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF07")) {// For Invoice Creation
                                         //Code added on June 23rd for Invoice creation after cancellation of invoice issue
                                      //  String SONumber=siteMasterDO.getSalesOrderNowithBillno(con,posStagingPOJO.getTransactionID());
                                        
                                        //End of //Code added on June 23rd for Invoice creation after cancellation of invoice issue
                                      /*  if(SONumber!=null && SONumber.length()>0){
                                        HashMap posstatus=siteMasterDO.getLastCancelledBillStatus(con, SONumber,posStagingPOJO.getTransactionID());
                                        HashMap posprevinvstatus=siteMasterDO.getLastBillStatus(con, SONumber, posStagingPOJO.getTransactionID());
                                        if(posstatus!=null && posstatus.get("updatestatus").toString().equalsIgnoreCase("Send")&&posstatus.get("sapidstatus").toString().equalsIgnoreCase("C")&& posprevinvstatus.get("updatestatus").toString().equalsIgnoreCase("Send")&&posprevinvstatus.get("sapidstatus").toString().equalsIgnoreCase("C")){
                                         getStatusArea().append("\n\nSending Invoice Creation :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        result = MasterDetails.getInvoice(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new InvoiceCreationwebservice();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }*/
                            
                                     //   }else if(posstatus!=null && posstatus.get("updatestatus").toString().equalsIgnoreCase("Send")&&!posstatus.get("sapidstatus").toString().equalsIgnoreCase("C")){
                                        
                                        
                                       //posStagingDO.updateStagingTableToSendInvoice(con,posStagingPOJO.getTransactionID(), SONumber);
                                        
                                      /*  }
                                            
                                        }else{*/
                                        getStatusArea().append("\n\nSending Invoice Creation :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        //Code commented on 29th January 2012 for Sending invoice creation after Invoice cancellation is completed
                                        /*result = MasterDetails.getInvoice(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new InvoiceCreationwebservice();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());    */
                                        
                                        //Code added on 29th January 2012 for Sending invoice creation after Invoice cancellation is completed
                                        if(checkInvoiceCreationAfterCancellation.equalsIgnoreCase("Y")){
                                            if(posStagingDO.sendInvoiceCreationAfterCancellation(con,posStagingPOJO.getTransactionID())){
                                                result = MasterDetails.getInvoice(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                                web = new InvoiceCreationwebservice();
                                                updateStatus = web.execute(result, posStagingPOJO.getUpdateType()); 
                                            }else{
                                                updateStatus="false";
                                            }
                                        }else{
                                            result = MasterDetails.getInvoice(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                            web = new InvoiceCreationwebservice();
                                            updateStatus = web.execute(result, posStagingPOJO.getUpdateType());    
                                         
                                        }
                                        //End of Code added on 29th January 2012 for Sending invoice creation after Invoice cancellation is completed
                                        
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                       // }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF04")) {// For Quick billing
                                        getStatusArea().append("\n\nSendingQuick Billing :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        result = MasterDetails.getDirectBilling(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new Quickbillingwebservices();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF16")) {// For Invoice Cancellation
                                        getStatusArea().append("\n\nSending Invoice Cancellation :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        result = MasterDetails.getInvoiceCancellation(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        //  needToSend = new SalesOrderBillingDO().checkSapRefIdForInvoiceCancelNo(con, posStagingPOJO.getTransactionID());
                                        //  if (needToSend) {
                                        web = new InvoiceCancelwebservice();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        //     }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF09")) {// For Cash Payout
                                        getStatusArea().append("\n\nSending Cash Payout :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getCashPayout(con,posStagingPOJO.getTransactionID());
                                        web = new CashPayoutDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF13")) {// For Advance Reversal
                                        getStatusArea().append("\n\nSending Advance Reversal :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getAdvanceReceiptCancellation(con,posStagingPOJO.getTransactionID());
                                        // needToSend = new AdvanceReceiptDO().checkSapRefIdForAdvanceReceiptNo(con, posStagingPOJO.getTransactionID());
                                        // if (needToSend) {
                                        web = new AdvanceReceiptDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        //    }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF17")) {// For Quotation
                                        getStatusArea().append("\n\nSending Quotation :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getQuotation(con,posStagingPOJO.getTransactionID());
                                        web = new QuotationsHeaderDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: STATUS=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF19")) {// For Service Order
                                        getStatusArea().append("\n\nSending Service Order :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getServiceOrder(con, posStagingPOJO.getTransactionID());
                                        web = new ServicesHeaderDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                            if(posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().equalsIgnoreCase("U")){
                                                posStagingDO.updateSapIdStatus(con, "C", posStagingPOJO.getMessageID());
                                            }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF20")) {// For Service Order billing
                                        getStatusArea().append("\n\nSending Service Order Billing :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getServiceOrderBilling(con, posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new ServiceInvoiceCreationwebservice();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF22")) {// For Service Order adv receipt
                                        getStatusArea().append("\n\nSending Service Advance Receipt :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getServiceAdvanceReceipt(con,posStagingPOJO.getTransactionID());
                                        web = new ServiceAdvanceReceiptWebservice();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    }else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF23")) {// For AdvanceReceipt SaleOrder Cancellation
                                        getStatusArea().append("\n\nSending AdvanceReceipt ServiceOrder Cancellation :" + posStagingPOJO.getTransactionID() + "        Credit Note No:" + posStagingPOJO.getTransactionID2());
                                        result = MasterDetails.getServiceOrderAdvanceReceiptCancelation(con,posStagingPOJO.getTransactionID(), posStagingPOJO.getTransactionID2());
                                        web = new CreditNoteDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    }  //Code Added on 11-01-2010 for GiftCard selling
                                     else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF24")) {// For AdvanceReceipt SaleOrder Cancellation
                                        getStatusArea().append("\n\nSending Gift Card Selling :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getGiftCardSelling(con,posStagingPOJO.getTransactionID());
                                        web = new GiftCardSellingDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    }  else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF25")) {// For AdvanceReceipt SaleOrder Cancellation
                                        getStatusArea().append("\n\nSending Gift Card Cancellation :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getGiftCardCancellation(con,posStagingPOJO.getTransactionID());
                                        web = new GiftCardSellingDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                        }
                                    }// Code Added on 12-12-2011 for Cash Receipt Sending
                                    else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF26")) {
                                        getStatusArea().append("\n\nSending Cash Receipt :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getCashReceiptDetails(con, posStagingPOJO.getTransactionID());
                                        web = new CashReceiptDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                            if(posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().equalsIgnoreCase("I")){
                                                posStagingDO.updateSapIdStatus(con, "C", posStagingPOJO.getMessageID());
                                            }
                                        }
                                    } else if (posStagingPOJO.getInterfaceID().equalsIgnoreCase("PW_IF27")) {
                                        getStatusArea().append("\n\nSending Cash Receipt Cancellation :" + posStagingPOJO.getTransactionID());
                                        result = MasterDetails.getCashReceiptDetails(con, posStagingPOJO.getTransactionID());
                                        web = new CashReceiptDO();
                                        updateStatus = web.execute(result, posStagingPOJO.getUpdateType());
                                        getStatusArea().append("\nData Sent: Status=" + updateStatus);
                                        if ((updateStatus != null && updateStatus.equalsIgnoreCase("true"))) {
                                            isNotSuccess = false;
                                            if(posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().equalsIgnoreCase("U")){
                                                posStagingDO.updateSapIdStatus(con, "C", posStagingPOJO.getMessageID());
                                            }
                                        }
                                        
                                    } 
                                    if (isNotSuccess) {
                                        if (posStagingPOJO.getNoOfAttempts() + 1 == posStagingPOJO.getRetryAttempts()) {
                                            posStagingPOJO.setUpdateStatus("Cancelled");
                                        } else {
                                            posStagingPOJO.setUpdateStatus("Waiting");
                                        }
                                        //  if (needToSend) {
                                        posStagingPOJO.setNoOfAttempts(posStagingPOJO.getNoOfAttempts() + 1);
                                        //   }
                                        posStagingPOJO.setLastAttemptDate(siteMasterDO.getSqlSystemDate(con));
                                        posStagingPOJO.setLastAttemptTime(siteMasterDO.getSqlSystemTime(con));
                                        posStagingDO.updateStatus(con, posStagingPOJO);
                                    } else {
                                        posStagingPOJO.setUpdateStatus("Send");
                                        posStagingPOJO.setNoOfAttempts(posStagingPOJO.getNoOfAttempts() + 1);
                                        posStagingPOJO.setLastAttemptDate(systemDate.getSystemDate());
                                        posStagingPOJO.setLastAttemptTime(ConvertDate.getCurrentTimeToString());
                                        posStagingDO.updateStatus(con, posStagingPOJO);
                                    }

                                }
                            } else {
                                getStatusArea().append("\nNo Records Found..");
                            }


                        }
                        freq = PosConfigParamDO.getTransactionSendingFreq(con);
                        Thread.sleep(freq);
                        getStatusArea().append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    } catch (Exception e) {
                        e.printStackTrace();
                        statusArea.append("\nData Sending Failed : Exception Occured" + e.getMessage());
                        freq = PosConfigParamDO.getReRunFreq_For_Any_SchedulerErr(con);
                        Thread.sleep(freq);//half an hour
                        getStatusArea().append("\n\nSleeping.......for " + (freq / 1000) + " Seconds");
                    } finally {
                        msdeConnection = null;
                        con.close();
                        con = null;
                        result = null;
                        web = null;
                        updateStatus = null;
                        posStagingDO = null;
                        siteMasterDO = null;
                        posStagingPOJO = null;
                        systemDate = null;
                    }
                }
                Thread.sleep(30*1000);//30 sec-- If database connection lost
            }

        } catch (Exception e) {
            e.printStackTrace();
            statusArea.append("\nDownload Failed : Exception Occured" + e.getMessage());
            /*Added by arun on 16 Apr 2012 for properly Closing DB connections*/
            try{
                con.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
            /*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
        }finally{
            /*Added by arun on 16 Apr 2012 for properly Closing DB connections*/
            try{
                if(con!=null){
                    con.close();
                    con=null;
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
            /*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
        }
        
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
    
    public String getInvCreateandCancelCheck() {
        Connection con=null;
        MsdeConnection msd=null;
        Statement st=null;
        ResultSet rs=null;
        String check="";
        try{
            msd=new MsdeConnection();
            con=msd.createConnection();
            st=con.createStatement();
            String query="select configval from tbl_pos_configparam where configkey like 'send_inv_creation_check'";
            rs=st.executeQuery(query);
                    
            while(rs.next()){
                check=rs.getString("configval");               
            }
            System.out.println("getInvCreateandCancelCheck() -->"+check);
            return check;
        }catch(SQLException se){
            se.printStackTrace();
            System.out.println("getInvCreateandCancelCheck() CATCH-->"+check);
            return "Y";
        }finally{
            try{
                if(con !=null){
                    con.close();
                }        
            }catch(Exception e){
                
            }
            con=null;
            msd=null;
            st=null;
            rs=null;
        }
    }
    
}

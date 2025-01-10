/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.GiftCardSelling.GiftCardSellingDO;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.SalesOrderBilling.SalesOrderBillingDO;
import ISRetail.SalesOrderBilling.SalesOrderBillingPOJO;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.cashpayout.CashPayoutDO;
import ISRetail.creditnote.CreditNoteDO;
import ISRetail.customer.CustomerMasterDO;
import ISRetail.customer.CustomerMasterPOJO;
import ISRetail.inquiry.InquiryDO;
import ISRetail.inquiry.InquiryPOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.paymentdetails.AdvanceReceiptDO;
import ISRetail.quotations.QuotationsHeaderDO;
import ISRetail.quotations.QuotationsHeaderPOJO;
import ISRetail.salesorder.SalesOrderHeaderDO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.salesreturns.AcknowledgementHeaderDO;
import ISRetail.salesreturns.AcknowledgementHeaderPOJO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.services.ServicesHeaderDO;
import ISRetail.services.ServicesHeaderPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OAsynchTransactions;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OGiftCard;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OadvanceReceipt;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcashPayOut;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNote;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNoteAdvSO;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCancellation;
import in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCreation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class HighFrequencyResponseWebService {

    private JTextArea statusArea;
    private static Logger logger = Logger.getLogger(HighFrequencyResponseWebService.class.getName());

    public void callWebService(JTextArea statusArea) {
        this.statusArea = statusArea;

        MsdeConnection msdeConnection = null;
        Connection connection = null;
        PosStagingDO posStagingDO = null;
        ArrayList<PosStagingPOJO> posStagingPOJOs = null;
        try { // Call Web Service Operation

            in.co.titan.highfrequencyresponse.MIOBSYNHighFrequencyResponseService service = new in.co.titan.highfrequencyresponse.MIOBSYNHighFrequencyResponseService();
            in.co.titan.highfrequencyresponse.MIOBSYNHighFrequencyResponse port = service.getMIOBSYNHighFrequencyResponsePort();
            // TODO initialize WS operation arguments here
            int request_timeout = 60000;
            Connection c = null;
            String timeout = "";
            String syn_head_count = "";
            String asy_head_count = "";
            int syn_header_count = 10;
            int asy_header_count = 10;
            int syn_added_count = 0;
            int asy_added_count = 0;
            try {
                c = new MsdeConnection().createConnection();
                timeout = PosConfigParamDO.getValForThePosConfigKey(c, "webservice_request_timeout");
                syn_head_count = PosConfigParamDO.getValForThePosConfigKey(c, "staging_syn_header_count");
                asy_head_count = PosConfigParamDO.getValForThePosConfigKey(c, "staging_ayn_header_count");
                if(Validations.isFieldNotEmpty(syn_head_count)){
                   syn_header_count = Integer.parseInt(syn_head_count);
                }
                if(Validations.isFieldNotEmpty(asy_head_count)){
                   asy_header_count = Integer.parseInt(asy_head_count);
                }
                if (Validations.isFieldNotEmpty(timeout)) {
                    request_timeout = Integer.parseInt(timeout);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                timeout = null;
            }
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);

            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyResponse&version=3.0&Sender.Service=X&Interface=Z%5EY");
            in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse();

            // TODO process result here
            msdeConnection = new MsdeConnection();
            connection = msdeConnection.createConnection();
            posStagingDO = new PosStagingDO();
            mtHighFrequencyResponse.setSITESEARCH(ServerConsole.siteID);
            if(syn_header_count > 0){
            mtHighFrequencyResponse = setAdvanceReceiptNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);//adv receipt
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getIadvanceReceipt().size());
            if(syn_header_count > syn_added_count){
            mtHighFrequencyResponse = setCashPayoutNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);//cash payout
            }
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getIcashPayOut().size());
            if(syn_header_count > syn_added_count){
            mtHighFrequencyResponse = setInvoiceNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);//billing so and  direct
            }
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getInvoiceCreation().size());
            
            if(syn_header_count > syn_added_count){
                mtHighFrequencyResponse = setCreditNoteNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//so cancel
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getIcreditNoteAdvSO().size());
            if(syn_header_count > syn_added_count){
                mtHighFrequencyResponse = setInvoiceCancelNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//inv cancel
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getIinvoiceCancellation().size());
            if(syn_header_count > syn_added_count){
                mtHighFrequencyResponse = setSalesReturnNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//sales return
            syn_added_count = getAddedCount(syn_added_count, mtHighFrequencyResponse.getIcreditNote().size());
            if(syn_header_count > syn_added_count){
                mtHighFrequencyResponse = setGCDocumentNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }
            }
            //setting asyn transaction details  into proxy  
            statusArea.append("--------------------------------------------------------\n");
            if(asy_header_count > 0){
            mtHighFrequencyResponse = setCustomerMasterNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);//Customer
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
            mtHighFrequencyResponse = setInquiryNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);//Inquiry
            }
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setSalesOrderNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Sale order
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setForceReleasedAdvReceipts(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Service Order
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setCancelled_withoutAdv_SoNosIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Service Order
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setAckCreationIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Ackcreation
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setAckUpdationIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//ack updation
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setQuotationNoIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Quotation
            asy_added_count = getAddedCount(asy_added_count, mtHighFrequencyResponse.getIAsynchTransactions().size());
            if(asy_header_count > asy_added_count){
                mtHighFrequencyResponse = setServiceOrderNoIntoProxy(connection, mtHighFrequencyResponse, posStagingDO, posStagingPOJOs);
            }//Service Order
            }



            if (mtHighFrequencyResponse.getIadvanceReceipt().size() > 0 || mtHighFrequencyResponse.getIcashPayOut().size() > 0 || mtHighFrequencyResponse.getInvoiceCreation().size() > 0 || mtHighFrequencyResponse.getIcreditNoteAdvSO().size() > 0 || mtHighFrequencyResponse.getIinvoiceCancellation().size() > 0 || mtHighFrequencyResponse.getIcreditNote().size() > 0 || mtHighFrequencyResponse.getIGiftCard().size() > 0 || mtHighFrequencyResponse.getIAsynchTransactions().size() > 0) {
                in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse result = port.miOBSYNHighFrequencyResponse(mtHighFrequencyResponse);
                statusArea.append("---------------------------------------------------------------\n");
                setSapAdvNoIntoAdvanceReceipt(connection, result, posStagingDO);//advance receipt
                setSapInvNoIntoCashPayoutTable(connection, result, posStagingDO);//cash payout
                setSapInvNoIntoInvoiceTable(connection, result, posStagingDO);//direct and so billing
                setSapInvNoIntoSOCreditNoteTable(connection, result, posStagingDO);//so cancellation
                setSapInvNoIntoInvoiceCancelTable(connection, result, posStagingDO);//invoice cancellation
                setSapInvNoIntoSRCreditNoteTable(connection, result, posStagingDO);//sales return
                setSapGFSNoIntoGFSSellingTable(connection, result, posStagingDO);
                //updating staging table  asyn transaction (updated in ISR or Not)
                statusArea.append("-------------------------------------------------------------------\n");
                setSapStatusIntoStagingTable(connection, result, posStagingDO);//for all async transaction which doesn't get FI feedback
            } else {
                statusArea.append(" No Data Found \n");
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            statusArea.append("\nDownload Failed : Exception Occured" + ex.getMessage());
        }

    }
    
    public int getAddedCount(int previousCount,int addedcount){
        return previousCount+addedcount;
    }

    //************************** METHOD TO SET ADVANCE RECEIPT NOS
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setAdvanceReceiptNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IadvanceReceipt iadvanceReceipt = null;
        try {
            //*************************************** SETTING THE ADVANCE RECEIPT NOS    ****************************************************/
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF06", "I");

            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iadvanceReceipt = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IadvanceReceipt();
                    iadvanceReceipt.setFYear(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iadvanceReceipt.setPOSAdvNo(posStagingPOJO.getTransactionID());
                    iadvanceReceipt.setSite(posStagingPOJO.getTid_storecode());
                    iadvanceReceipt.setMessageID(posStagingPOJO.getMessageID());

                    // printing values
                    mtHighFrequencyResponse.getIadvanceReceipt().add(iadvanceReceipt);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nAdvance Receipt - Sending :" + posStagingPOJOs.size() + "Records");
                }
            }
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF22", "I");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iadvanceReceipt = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IadvanceReceipt();
                    iadvanceReceipt.setFYear(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iadvanceReceipt.setPOSAdvNo(posStagingPOJO.getTransactionID());
                    iadvanceReceipt.setSite(posStagingPOJO.getTid_storecode());
                    iadvanceReceipt.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIadvanceReceipt().add(iadvanceReceipt);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nService Advance Receipt - Sending :" + posStagingPOJOs.size() + "Records");
                }
            }
            /**
             * ************************************* SETTING THE ADVANCE RECEIPT
             * REVERSAL NOS   ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF13", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iadvanceReceipt = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IadvanceReceipt();
                    iadvanceReceipt.setFYear(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iadvanceReceipt.setPOSAdvNo(posStagingPOJO.getTransactionID());
                    iadvanceReceipt.setSite(posStagingPOJO.getTid_storecode());
                    iadvanceReceipt.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIadvanceReceipt().add(iadvanceReceipt);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nAdvance Receipt Reversal - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iadvanceReceipt = null;
        }
        return mtHighFrequencyResponse;
    }

    //************************* METHOD TO SET INVOICE NO
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setInvoiceNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        SalesOrderBillingDO salesOrderBillingDO = null;
        SalesOrderBillingPOJO SalesOrderBillingPOJO = null;
        ArrayList<PosStagingPOJO> servOrderBillingPojos = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.InvoiceCreation invoiceCreation = null;
        try {
            /**
             * ************************************* SETTING THE SALEORDER
             * INVOICE NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF07", "");

            if (posStagingPOJOs != null) {
                servOrderBillingPojos = posStagingDO.getTrasactionIds(con, "PW_IF20", "");
                if (servOrderBillingPojos != null) {
                    posStagingPOJOs.addAll(servOrderBillingPojos);
                }
            } else {
                posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF20", "");
            }
            salesOrderBillingDO = new SalesOrderBillingDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    SalesOrderBillingPOJO = salesOrderBillingDO.getBillingHeaderByInvoiceNo(con, posStagingPOJO.getTransactionID(), posStagingPOJO.getTid_storecode(), posStagingPOJO.getTid_fiscalyear());
                    if (SalesOrderBillingPOJO != null) {
                        invoiceCreation = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.InvoiceCreation();
                        invoiceCreation.setFYear(String.valueOf(SalesOrderBillingPOJO.getFiscalyear()));
                        invoiceCreation.setSite(SalesOrderBillingPOJO.getStorecode());
                        invoiceCreation.setPOSBillNo(posStagingPOJO.getTransactionID());
                        invoiceCreation.setPOSSONo(SalesOrderBillingPOJO.getSalesorderno());
                        if (posStagingPOJO.getTransactionID2() != null) {
                            invoiceCreation.setPOSCrNoteNo(posStagingPOJO.getTransactionID2());
                        }
                        invoiceCreation.setMessageID(posStagingPOJO.getMessageID());
                        mtHighFrequencyResponse.getInvoiceCreation().add(invoiceCreation);
                    }
                }

            }
            /**
             * ************************************* SETTING THE DIRECT INVOICE
             * NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF04", "");
            salesOrderBillingDO = new SalesOrderBillingDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    SalesOrderBillingPOJO = salesOrderBillingDO.getBillingHeaderByInvoiceNo(con, posStagingPOJO.getTransactionID(), posStagingPOJO.getTid_storecode(), posStagingPOJO.getTid_fiscalyear());
                    if (SalesOrderBillingPOJO != null) {
                        invoiceCreation = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.InvoiceCreation();
                        invoiceCreation.setFYear(String.valueOf(SalesOrderBillingPOJO.getFiscalyear()));
                        invoiceCreation.setSite(SalesOrderBillingPOJO.getStorecode());
                        invoiceCreation.setPOSBillNo(posStagingPOJO.getTransactionID());
                        invoiceCreation.setPOSSONo(SalesOrderBillingPOJO.getSalesorderno());
                        if (posStagingPOJO.getTransactionID2() != null) {
                            invoiceCreation.setPOSCrNoteNo(posStagingPOJO.getTransactionID2());
                        }
                        invoiceCreation.setMessageID(posStagingPOJO.getMessageID());
                        mtHighFrequencyResponse.getInvoiceCreation().add(invoiceCreation);
                    }
                }
                if (mtHighFrequencyResponse.getInvoiceCreation().size() > 0) {
                    statusArea.append("\nDirect Billing ,Sale Order Billing - Sending :" + mtHighFrequencyResponse.getInvoiceCreation().size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            salesOrderBillingDO = null;
            SalesOrderBillingPOJO = null;
            invoiceCreation = null;
        }
        return mtHighFrequencyResponse;
    }

    //************************* METHOD TO SET INVOICE CANCEL NO
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setInvoiceCancelNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IinvoiceCancellation iinvoiceCancellation = null;
        try {
            /**
             * ************************************* SETTING THE INVOICE CANCEL
             * NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF16", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iinvoiceCancellation = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IinvoiceCancellation();
                    iinvoiceCancellation.setFYear(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iinvoiceCancellation.setSite(posStagingPOJO.getTid_storecode());
                    iinvoiceCancellation.setPOSInvCanNo(posStagingPOJO.getTransactionID());
                    iinvoiceCancellation.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIinvoiceCancellation().add(iinvoiceCancellation);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nInvoice Cancellation - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iinvoiceCancellation = null;
        }
        return mtHighFrequencyResponse;
    }
   //***************************METHOD TO SET GIFTCARD SELLING NO**********************//

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setGCDocumentNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IGiftCard iGiftCard = null;
        try {
            //*************************************** SETTING THE GIFTCARD NOS    ****************************************************/
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF24", "I");

            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iGiftCard = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IGiftCard();
                    iGiftCard.setGJAHR(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iGiftCard.setGCDOCNUM(posStagingPOJO.getTransactionID());
                    iGiftCard.setSITEID(posStagingPOJO.getTid_storecode());
                    iGiftCard.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIGiftCard().add(iGiftCard);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nGiftcard - Sending :" + posStagingPOJOs.size() + "Records");
                }
            }

            /**
             * ************************************* SETTING THE GIFT CARD
             * REVERSAL NOS   ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF25", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    iGiftCard = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IGiftCard();
                    iGiftCard.setGJAHR(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    iGiftCard.setGCDOCNUM(posStagingPOJO.getTransactionID());
                    iGiftCard.setSITEID(posStagingPOJO.getTid_storecode());
                    iGiftCard.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIGiftCard().add(iGiftCard);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nGiftCard Cancellation - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iGiftCard = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************END OF METHOD TO SET GIFTCARD NO*********************// 
    //************************* METHOD TO SET CASH PAYOUT NO
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setCashPayoutNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcashPayOut icashPayOut = null;
        try {
            /**
             * ************************************* SETTING THE CASH PAYOUT NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF09", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    icashPayOut = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcashPayOut();
                    icashPayOut.setFYear(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    icashPayOut.setSite(posStagingPOJO.getTid_storecode());
                    icashPayOut.setPOSCashPayNo(posStagingPOJO.getTransactionID());
                    icashPayOut.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIcashPayOut().add(icashPayOut);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nCash Payout - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            icashPayOut = null;
        }
        return mtHighFrequencyResponse;
    }

    //************************* METHOD TO SET SALE ORDER CANCELLATION CREDIT NOTE NO
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setCreditNoteNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcreditNoteAdvSO icreditNoteAdvSO = null;
        try {
            /**
             * ************************************* SETTING THE CASH PAYOUT NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF14", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    if (Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                        icreditNoteAdvSO = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcreditNoteAdvSO();
                        icreditNoteAdvSO.setFYear(String.valueOf(posStagingPOJO.getTid1_fiscalyear()));
                        icreditNoteAdvSO.setSite(posStagingPOJO.getTid1_storecode());
                        icreditNoteAdvSO.setPOSCrNoteNo(posStagingPOJO.getTransactionID2());
                        icreditNoteAdvSO.setMessageID(posStagingPOJO.getMessageID());
                        mtHighFrequencyResponse.getIcreditNoteAdvSO().add(icreditNoteAdvSO);
                    }
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nSale Order Cancellation - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }

            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF23", "");
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    if (Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                        icreditNoteAdvSO = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcreditNoteAdvSO();
                        icreditNoteAdvSO.setFYear(String.valueOf(posStagingPOJO.getTid1_fiscalyear()));
                        icreditNoteAdvSO.setSite(posStagingPOJO.getTid1_storecode());
                        icreditNoteAdvSO.setPOSCrNoteNo(posStagingPOJO.getTransactionID2());
                        icreditNoteAdvSO.setMessageID(posStagingPOJO.getMessageID());
                        mtHighFrequencyResponse.getIcreditNoteAdvSO().add(icreditNoteAdvSO);
                    }
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nService Order Cancellation - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            icreditNoteAdvSO = null;
        }
        return mtHighFrequencyResponse;
    }

    //************************* METHOD TO SET Sale return NO
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setSalesReturnNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcreditNote icreditNote = null;
        try {
            /**
             * ************************************* SETTING THE sale return NOS    ***************************************************
             */
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF21", "I");//salesreturn
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    icreditNote = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IcreditNote();
                    icreditNote.setFYear(String.valueOf(posStagingPOJO.getTid1_fiscalyear()));
                    // icreditNote.setSite(posStagingPOJO.getTid1_storecode());
                    icreditNote.setSONumber(posStagingPOJO.getTransactionID2());
                    icreditNote.setMessageID(posStagingPOJO.getMessageID());
                    mtHighFrequencyResponse.getIcreditNote().add(icreditNote);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nSales Return - Sending :" + posStagingPOJOs.size() + "Records");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            icreditNote = null;
        }
        return mtHighFrequencyResponse;
    }

    //*************************** METHOD TO SET ADVANCE RECEIPT SAP REF NO INTO ADVANCERECEIPT TABLE  
    public void setSapAdvNoIntoAdvanceReceipt(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OadvanceReceipt> oadvanceReceipts = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OadvanceReceipt oadvanceReceipt = null;
        AdvanceReceiptDO advanceReceiptDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        try {
            oadvanceReceipts = (ArrayList<OadvanceReceipt>) response.getOadvanceReceipt();
            if (oadvanceReceipts != null) {
                iterator = oadvanceReceipts.iterator();
                if (iterator != null) {
                    advanceReceiptDO = new AdvanceReceiptDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        oadvanceReceipt = (OadvanceReceipt) iterator.next();
                        if (oadvanceReceipt != null) {
                            try {
                                fiscalYear = Integer.parseInt(oadvanceReceipt.getFYear());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (Validations.isFieldNotEmpty(oadvanceReceipt.getISRAdvNo())) {
                                if (advanceReceiptDO.Updateadvsapreferenceid(con, oadvanceReceipt.getPOSAdvNo(), oadvanceReceipt.getISRAdvNo(), oadvanceReceipt.getSite(), fiscalYear) > 0) {
                                    if (posStagingDO != null) {
                                        posStagingDO.updateSapIdStatus(con, "C", oadvanceReceipt.getMessageID());
                                        totRowsUpdated++;
                                    }
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", oadvanceReceipt.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }
                        }
                    }
                    if (oadvanceReceipts.size() > 0) {
                        statusArea.append("\nAdvance Receipt - Received :" + totRowsUpdated + "Records");
                    }

                }
            }

        } catch (Exception e) {

        } finally {
            oadvanceReceipts = null;
            oadvanceReceipt = null;
            advanceReceiptDO = null;
            iterator = null;
        }

    }

//*************************** METHOD TO SET INVOICE SAP REF NO INTO INVOICE TABLE  
    public void setSapInvNoIntoInvoiceTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCreation> oinvoiceCreations = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCreation oinvoiceCreation = null;
        SalesOrderBillingDO salesOrderBillingDO = null;
        CreditNoteDO creditNoteDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        String possono;
        String stype;

        try {
            oinvoiceCreations = (ArrayList<OinvoiceCreation>) response.getOinvoiceCreation();
            if (oinvoiceCreations != null) {
                iterator = oinvoiceCreations.iterator();
                if (iterator != null) {
                    salesOrderBillingDO = new SalesOrderBillingDO();
                    creditNoteDO = new CreditNoteDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        oinvoiceCreation = (OinvoiceCreation) iterator.next();
                        if (oinvoiceCreation != null) {
                            try {
                                fiscalYear = Integer.parseInt(oinvoiceCreation.getFYear());
                            } catch (Exception e) {
                            }
                            possono = oinvoiceCreation.getPOSSONo();
                            stype = salesOrderBillingDO.getSaleTypeFromSaleorder(possono, con);
                            //System.out.println("possono::"+oinvoiceCreation.getPOSSONo()+"stype::"+stype);
                            if (stype != null && stype.trim().equalsIgnoreCase("CASH")) {
                                //System.out.println("oinvoiceCreation.getISRAdvNo()"+oinvoiceCreation.getISRAdvNo()+"oinvoiceCreation.getISRBillNo()"+oinvoiceCreation.getISRBillNo());
                                if (Validations.isFieldNotEmpty(oinvoiceCreation.getISRAdvNo()) && Validations.isFieldNotEmpty(oinvoiceCreation.getISRBillNo())) {
                                    if (Validations.isFieldNotEmpty(oinvoiceCreation.getPOSCrNoteNo()) || Validations.isFieldNotEmpty(oinvoiceCreation.getISrCrNoteNo())) {
                                        creditNoteDO.updateSapRefId(con, oinvoiceCreation.getISrCrNoteNo(), oinvoiceCreation.getPOSCrNoteNo(), oinvoiceCreation.getSite(), fiscalYear);
                                    }
                                    result = salesOrderBillingDO.updateSapRefId(con, oinvoiceCreation.getISRBillNo(), oinvoiceCreation.getISRAdvNo(), oinvoiceCreation.getPOSBillNo(), oinvoiceCreation.getSite(), fiscalYear);
                                    if (result > 0 && posStagingDO != null) {
                                        posStagingDO.updateSapIdStatus(con, "C", oinvoiceCreation.getMessageID());
                                        totRowsUpdated++;
                                    }
                                } else {
                                    posStagingDO.updateSapIdStatus(con, "X", oinvoiceCreation.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                                }

                            } else {

                                if (Validations.isFieldNotEmpty(oinvoiceCreation.getISRAdvNo()) || Validations.isFieldNotEmpty(oinvoiceCreation.getISRBillNo())) {
                                    if (Validations.isFieldNotEmpty(oinvoiceCreation.getPOSCrNoteNo()) || Validations.isFieldNotEmpty(oinvoiceCreation.getISrCrNoteNo())) {
                                        creditNoteDO.updateSapRefId(con, oinvoiceCreation.getISrCrNoteNo(), oinvoiceCreation.getPOSCrNoteNo(), oinvoiceCreation.getSite(), fiscalYear);
                                    }
                                    System.out.println("POSBILLNO:" + oinvoiceCreation.getPOSBillNo() + "ISRBillNo" + oinvoiceCreation.getISRBillNo() + "ISRAdvNo" + oinvoiceCreation.getISRAdvNo());
                                    result = salesOrderBillingDO.updateSapRefId(con, oinvoiceCreation.getISRBillNo(), oinvoiceCreation.getISRAdvNo(), oinvoiceCreation.getPOSBillNo(), oinvoiceCreation.getSite(), fiscalYear);
                                    System.out.println("result:" + result);
                                    System.out.println("POSStagingDO:" + posStagingDO);
                                    if (result > 0 && posStagingDO != null) {
                                        posStagingDO.updateSapIdStatus(con, "C", oinvoiceCreation.getMessageID());
                                        totRowsUpdated++;
                                    }
                                } else {
                                    posStagingDO.updateSapIdStatus(con, "X", oinvoiceCreation.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                                }

                            }

                        }
                    }
                    if (oinvoiceCreations.size() > 0) {
                        statusArea.append("\nDirect Billing ,Sale Order Billing - Received :" + totRowsUpdated + "Records");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oinvoiceCreations = null;
            oinvoiceCreation = null;
            salesOrderBillingDO = null;
            creditNoteDO = null;
            iterator = null;
        }

    }

    //*************************** METHOD TO SET INVOICE CANCEL SAP REF NO INTO INVOICECANCEL TABLE  
    public void setSapInvNoIntoInvoiceCancelTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCancellation> oinvoiceCancellations = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OinvoiceCancellation oinvoiceCancellation = null;
        SalesOrderBillingDO salesOrderBillingDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        try {
            oinvoiceCancellations = (ArrayList<OinvoiceCancellation>) response.getOinvoiceCancellation();
            if (oinvoiceCancellations != null) {
                iterator = oinvoiceCancellations.iterator();
                if (iterator != null) {
                    salesOrderBillingDO = new SalesOrderBillingDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        oinvoiceCancellation = (OinvoiceCancellation) iterator.next();
                        if (oinvoiceCancellation != null) {
                            try {
                                fiscalYear = Integer.parseInt(oinvoiceCancellation.getFYear());
                            } catch (Exception e) {
                            }

                            if (Validations.isFieldNotEmpty(oinvoiceCancellation.getISRInvCanNo()) || Validations.isFieldNotEmpty(oinvoiceCancellation.getISRRevDocNo())) {
                                result = salesOrderBillingDO.updateSapRefNoInvoiceCancel(con, oinvoiceCancellation.getISRInvCanNo(), oinvoiceCancellation.getISRRevDocNo(), oinvoiceCancellation.getPOSInvCanNo(), oinvoiceCancellation.getSite(), fiscalYear);
                                if (result > 0 && posStagingDO != null) {
                                    posStagingDO.updateSapIdStatus(con, "C", oinvoiceCancellation.getMessageID());
                                    totRowsUpdated++;
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", oinvoiceCancellation.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }

                        }
                    }
                    if (oinvoiceCancellations.size() > 0) {
                        statusArea.append("\nInvoice Cancellation - Received :" + totRowsUpdated + "Records");
                    }
                }
            }

        } catch (Exception e) {

        } finally {
            oinvoiceCancellations = null;
            oinvoiceCancellation = null;
            salesOrderBillingDO = null;
            iterator = null;
        }
    }

    //*************************** METHOD TO SET CASH PAYOUT SAP REF NO INTO CASH PAYOUT TABLE  
    public void setSapInvNoIntoCashPayoutTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcashPayOut> ocashPayOuts = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcashPayOut ocashPayOut = null;
        CashPayoutDO cashPayoutDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        try {
            ocashPayOuts = (ArrayList<OcashPayOut>) response.getOcashPayOut();
            if (ocashPayOuts != null) {
                iterator = ocashPayOuts.iterator();
                if (iterator != null) {
                    cashPayoutDO = new CashPayoutDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        ocashPayOut = (OcashPayOut) iterator.next();
                        if (ocashPayOut != null) {
                            try {
                                fiscalYear = Integer.parseInt(ocashPayOut.getFYear());
                            } catch (Exception e) {
                            }
                            if (Validations.isFieldNotEmpty(ocashPayOut.getISRCashPayNo())) {
                                result = cashPayoutDO.updateSapReferenceIdInPosDocTable(con, ocashPayOut.getPOSCashPayNo(), ocashPayOut.getISRCashPayNo(), ocashPayOut.getSite(), fiscalYear);
                                if (result > 0 && posStagingDO != null) {
                                    posStagingDO.updateSapIdStatus(con, "C", ocashPayOut.getMessageID());
                                    totRowsUpdated++;
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", ocashPayOut.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }
                        }
                    }
                    if (ocashPayOuts.size() > 0) {
                        statusArea.append("\nCash Payout - Received :" + totRowsUpdated + "Records");
                    }
                }
            }

        } catch (Exception e) {

        } finally {
            ocashPayOuts = null;
            ocashPayOut = null;
            cashPayoutDO = null;
            iterator = null;
        }
    }

    //*************************** METHOD TO SET Sale Order Cancellation Credit Notes 
    public void setSapInvNoIntoSOCreditNoteTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNoteAdvSO> ocreditNoteAdvSOs = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNoteAdvSO ocreditNoteAdvSO = null;
        CreditNoteDO creditNoteDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        try {
            ocreditNoteAdvSOs = (ArrayList<OcreditNoteAdvSO>) response.getOcreditNoteAdvSO();
            if (ocreditNoteAdvSOs != null) {
                iterator = ocreditNoteAdvSOs.iterator();
                if (iterator != null) {
                    creditNoteDO = new CreditNoteDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        ocreditNoteAdvSO = (OcreditNoteAdvSO) iterator.next();
                        if (ocreditNoteAdvSO != null) {
                            try {
                                fiscalYear = Integer.parseInt(ocreditNoteAdvSO.getFYear());
                            } catch (Exception e) {
                            }
                            if (Validations.isFieldNotEmpty(ocreditNoteAdvSO.getISRCrNoteNo())) {
                                result = creditNoteDO.updateSapRefId(con, ocreditNoteAdvSO.getISRCrNoteNo(), ocreditNoteAdvSO.getPOSCrNoteNo(), ocreditNoteAdvSO.getSite(), fiscalYear);
                                if (result > 0 && posStagingDO != null) {
                                    posStagingDO.updateSapIdStatus(con, "C", ocreditNoteAdvSO.getMessageID());
                                    totRowsUpdated++;
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", ocreditNoteAdvSO.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }
                        }
                    }
                    if (ocreditNoteAdvSOs.size() > 0) {
                        statusArea.append("\nSale Order Cancellation Credit Notes - Received :" + totRowsUpdated + "Records");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ocreditNoteAdvSOs = null;
            ocreditNoteAdvSO = null;
            creditNoteDO = null;
            iterator = null;
        }
    }

    //*************************** METHOD TO SET Sales ReturnT SAP REF NO INTO CASH PAYOUT TABLE  
    public void setSapInvNoIntoSRCreditNoteTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNote> ocreditNoteSRs = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OcreditNote ocreditNoteSR = null;
        CreditNoteDO creditNoteDO = null;
        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        try {
            ocreditNoteSRs = (ArrayList<OcreditNote>) response.getOcreditNote();
            if (ocreditNoteSRs != null) {
                iterator = ocreditNoteSRs.iterator();
                if (iterator != null) {
                    creditNoteDO = new CreditNoteDO();
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        ocreditNoteSR = (OcreditNote) iterator.next();
                        if (ocreditNoteSR != null) {
                            try {
                                fiscalYear = Integer.parseInt(ocreditNoteSR.getFYear());
                            } catch (Exception e) {
                            }
                            if (Validations.isFieldNotEmpty(ocreditNoteSR.getISRBillNo())) {
                                result = creditNoteDO.updateSapRefId_ByCreditNoteRefNo(con, ocreditNoteSR.getISRBillNo(), ocreditNoteSR.getSONumber(), fiscalYear);
                                if (result > 0 && posStagingDO != null) {
                                    posStagingDO.updateSapIdStatus(con, "C", ocreditNoteSR.getMessageID());
                                    totRowsUpdated++;
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", ocreditNoteSR.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }
                        }
                    }

                }
            }
            // if (totRowsUpdated.size() > 0) {
            statusArea.append("\nSales Return Credit Notes - Received :" + totRowsUpdated + "Records");
            // }
        } catch (Exception e) {

        } finally {
            ocreditNoteSRs = null;
            ocreditNoteSR = null;
            creditNoteDO = null;
            iterator = null;
        }
    }
    //***********************SET SAP GIFTCARD NOS INTO THE GIFTCARD_SELLING TABLE**********//

    public void setSapGFSNoIntoGFSSellingTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OGiftCard> oGiftCards = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OGiftCard oGiftCard = null;
        GiftCardSellingDO giftCardSellingDO;

        Iterator iterator = null;
        int fiscalYear = 0, totRowsUpdated = 0;
        int result = 0;
        String possono;
        String stype;

        try {
            oGiftCards = (ArrayList<OGiftCard>) response.getOGiftCard();
            if (oGiftCards != null) {
                iterator = oGiftCards.iterator();
                if (iterator != null) {
                    giftCardSellingDO = new GiftCardSellingDO();

                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        oGiftCard = (OGiftCard) iterator.next();
                        if (oGiftCard != null) {
                            try {
                                fiscalYear = Integer.parseInt(oGiftCard.getGJAHR());
                            } catch (Exception e) {
                            }
                            possono = oGiftCard.getGCDOCNUM();

                            if (Validations.isFieldNotEmpty(oGiftCard.getISRGCNO()) && Validations.isFieldNotEmpty(oGiftCard.getISRACCNO())) {
                                result = giftCardSellingDO.updateSapRefId(con, oGiftCard.getISRGCNO(), oGiftCard.getISRACCNO(), oGiftCard.getGCDOCNUM(), oGiftCard.getSITEID(), fiscalYear);
                                if (result > 0 && posStagingDO != null) {
                                    posStagingDO.updateSapIdStatus(con, "C", oGiftCard.getMessageID());
                                    totRowsUpdated++;
                                }
                            } else {
                                posStagingDO.updateSapIdStatus(con, "X", oGiftCard.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                            }

                        }
                    }
                    if (oGiftCards.size() > 0) {
                        statusArea.append("\nGiftCard Selling  - Received :" + totRowsUpdated + "Records");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            oGiftCards = null;
            oGiftCard = null;
            giftCardSellingDO = null;

            iterator = null;
        }

    }
//******************END OF SAP GIFTCARD NOS INTO THE GIFTCARD_SELLING TABLE***********//

    //***********Methods to Send Non-FI transactions to ISR- to get the UpdateStatus in ISR***************
    //****************1. Method to send Customer Master created/ Updated in POS --for which sapid status='N'
    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setCustomerMasterNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        CustomerMasterDO customerMasterDO;
        CustomerMasterPOJO customerMasterPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF01", "");//Customer Master
            customerMasterDO = new CustomerMasterDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    customerMasterPOJO = customerMasterDO.getCustomer(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("CUS");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(customerMasterPOJO.getCreatedDate());
                        time = ConvertDate.getSqlTimeFromString(customerMasterPOJO.getCreatedTime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(customerMasterPOJO.getModifiedDate());
                                time = ConvertDate.getSqlTimeFromString(customerMasterPOJO.getModifiedTime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nCustomer Master - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nCustomer Master - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            customerMasterDO = null;
            customerMasterPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************2. Method to send Inquiry created/ Updated in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setInquiryNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        InquiryDO inquiryDO;
        InquiryPOJO inquiryPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF05", "");//Inquiry
            inquiryDO = new InquiryDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    inquiryPOJO = inquiryDO.getInquiryByInqNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("INQ");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(inquiryPOJO.getCreateddate());
                        time = ConvertDate.getSqlTimeFromString(inquiryPOJO.getCreatedtime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(inquiryPOJO.getModifieddate());
                                time = ConvertDate.getSqlTimeFromString(inquiryPOJO.getModifiedtime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nInquiry - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nInquiry - No Records to be sent");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            inquiryDO = null;
            inquiryPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************3. Method to send Sales order created/ Updated in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setSalesOrderNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        SalesOrderHeaderDO salesOrderHeaderDO;
        SalesOrderHeaderPOJO salesOrderHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF03", "");//Sales Order

            salesOrderHeaderDO = new SalesOrderHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    salesOrderHeaderPOJO = salesOrderHeaderDO.getSaleOrderHeaderBySaleOrderNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("SAR");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getCreatedDate());
                        time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getCreatedTime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getModifiedDate());
                                time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getModifiedTime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nSales Order - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nSales Order - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            salesOrderHeaderDO = null;
            salesOrderHeaderPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************3. Method to send force released  --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setForceReleasedAdvReceipts(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        SalesOrderHeaderDO salesOrderHeaderDO;
        SalesOrderHeaderPOJO salesOrderHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF06", "F");//adv receipt force  release
            salesOrderHeaderDO = new SalesOrderHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    salesOrderHeaderPOJO = salesOrderHeaderDO.getSaleOrderHeaderBySaleOrderNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("FAR");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getCreatedDate());
                        time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getCreatedTime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("F")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getModifiedDate());
                                time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getModifiedTime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    if (salesOrderHeaderPOJO != null) {
                        iAsyn.setKey3(salesOrderHeaderPOJO.getReleaseStatus());
                    }
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nForce Released Sales Order - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nForce Released Sales Order - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            salesOrderHeaderDO = null;
            salesOrderHeaderPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************4. Method to send Sales order Cancelled (Without Advance) in POS --for which sapid status='N' or 'X'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setCancelled_withoutAdv_SoNosIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        SalesOrderHeaderDO salesOrderHeaderDO;
        ServicesHeaderDO servicesHeaderDO;
        ServicesHeaderPOJO servicesHeaderPOJO;
        SalesOrderHeaderPOJO salesOrderHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF14", "");//Cancelled Sales Order without adv
            salesOrderHeaderDO = new SalesOrderHeaderDO();
            int noRec = 0;
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    if (!Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {//Cancelled Sales Order without creditnoteno
                        salesOrderHeaderPOJO = salesOrderHeaderDO.getSaleOrderHeaderBySaleOrderNo(con, posStagingPOJO.getTransactionID());
                        iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                        iAsyn.setShortName("SOC");
                        iAsyn.setSite(posStagingPOJO.getTid_storecode());
                        iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                        try {
                            date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getCreatedDate());
                            time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getCreatedTime());
                            if (date != null) {
                                xmlDate = new XMLCalendar(date);
                                if (time != null) {
                                    xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        iAsyn.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    iAsyn.setCreatedDate(xmlDate);
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (posStagingPOJO.getUpdateType() != null) {
                            if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                                try {
                                    date = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getModifiedDate());
                                    time = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getModifiedTime());
                                    if (date != null) {
                                        xmlDate = new XMLCalendar(date);
                                        if (time != null) {
                                            xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                            if (xmlDate != null) {
                                                iAsyn.setModifiedDate(xmlDate);
                                            }
                                        }
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedTime(xmlDate);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                        iAsyn.setMessageID(posStagingPOJO.getMessageID());
                        iAsyn.setKey1(posStagingPOJO.getTransactionID());
                        iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                        mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                        noRec++;
                    }
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nSales Order Caneclled Without Advance- Sending :" + noRec + "Records");
                } else {
                    statusArea.append("\nSales Order Caneclled Without Advance - No Records to be sent");
                }

            }

            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF23", "");//Cancelled Sales Order without adv
            servicesHeaderDO = new ServicesHeaderDO();
            noRec = 0;
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    if (!Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {//Cancelled Sales Order without creditnoteno
                        servicesHeaderPOJO = servicesHeaderDO.getServiceOrderByServiceOrderNo(con, posStagingPOJO.getTransactionID());
                        iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                        iAsyn.setShortName("SRC");
                        iAsyn.setSite(posStagingPOJO.getTid_storecode());
                        iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                        try {
                            date = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getCreatedDate());
                            time = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getCreatedTime());
                            if (date != null) {
                                xmlDate = new XMLCalendar(date);
                                if (time != null) {
                                    xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        iAsyn.setCreatedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    iAsyn.setCreatedDate(xmlDate);
                                }
                            }
                        } catch (Exception e) {
                        }
                        if (posStagingPOJO.getUpdateType() != null) {
                            if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                                try {
                                    date = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getModifiedDate());
                                    time = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getModifiedTime());
                                    if (date != null) {
                                        xmlDate = new XMLCalendar(date);
                                        if (time != null) {
                                            xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                            if (xmlDate != null) {
                                                iAsyn.setModifiedDate(xmlDate);
                                            }
                                        }
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedTime(xmlDate);
                                        }
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }
                        iAsyn.setMessageID(posStagingPOJO.getMessageID());
                        iAsyn.setKey1(posStagingPOJO.getTransactionID());
                        iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                        mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                        noRec++;
                    }
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nService Order Caneclled Without Advance- Sending :" + noRec + "Records");
                } else {
                    statusArea.append("\nService Order Caneclled Without Advance - No Records to be sent");
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            salesOrderHeaderDO = null;
            salesOrderHeaderPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************4. Method to send Acknowledgements created in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setAckCreationIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        AcknowledgementHeaderDO acknowledgementHeaderDO;
        AcknowledgementHeaderPOJO acknowledgementHeaderPOJO;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF08", "");//Acknowledgement Creation
            acknowledgementHeaderDO = new AcknowledgementHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    acknowledgementHeaderPOJO = acknowledgementHeaderDO.getAcknowledgementDetailsByAckNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("ACK");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getCreateddate());
                        time = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getCreatedtime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }

                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nAcknowledgement Creation - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nAcknowledgement Creation - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            acknowledgementHeaderPOJO = null;
            acknowledgementHeaderDO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************5. Method to send Acknowledgements Updated in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setAckUpdationIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        AcknowledgementHeaderDO acknowledgementHeaderDO;
        AcknowledgementHeaderPOJO acknowledgementHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;
        int ackUpdRecords = 0;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF21", "U");//Acknowledgement Updation,
            acknowledgementHeaderDO = new AcknowledgementHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    ackUpdRecords = ackUpdRecords + 1;
                    acknowledgementHeaderPOJO = acknowledgementHeaderDO.getAcknowledgementDetailsByAckNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("ACK");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                        time = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(acknowledgementHeaderPOJO.getModifieddate());
                        time = ConvertDate.getSqlTimeFromString(acknowledgementHeaderPOJO.getModifiedtime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setModifiedDate(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setModifiedTime(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (ackUpdRecords > 0) {
                    statusArea.append("\nAcknowledgement Updation - Sending :" + ackUpdRecords + "Records");
                } else {
                    statusArea.append("\nAcknowledgement Updation - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            acknowledgementHeaderPOJO = null;
            acknowledgementHeaderDO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************6. Method to send Quotations Created/Updated in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setQuotationNoIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        QuotationsHeaderDO quotationsHeaderDO;
        QuotationsHeaderPOJO quotationsHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF17", "");//Quotation
            quotationsHeaderDO = new QuotationsHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    quotationsHeaderPOJO = quotationsHeaderDO.getQuotationListforThread(con, "select * from tbl_quotationheader where quotationno='" + posStagingPOJO.getTransactionID() + "'");
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("QUO");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getCreatedDate());
                        time = ConvertDate.getSqlTimeFromString(quotationsHeaderPOJO.getCreatedTime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getModifiedDate());
                                time = ConvertDate.getSqlTimeFromString(quotationsHeaderPOJO.getModifiedTime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nQuotation - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nQuotation - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            quotationsHeaderPOJO = null;
            quotationsHeaderDO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //****************7. Method to send Service orders Created/Updated in POS --for which sapid status='N'

    public in.co.titan.highfrequencyresponse.DTHighFrequencyResponse setServiceOrderNoIntoProxy(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResponse mtHighFrequencyResponse, PosStagingDO posStagingDO, ArrayList<PosStagingPOJO> posStagingPOJOs) {
        Iterator iterator = null;
        PosStagingPOJO posStagingPOJO = null;
        ServicesHeaderDO servicesHeaderDO;
        ServicesHeaderPOJO servicesHeaderPOJO;
        java.util.Date date;
        Calendar time;
        XMLCalendar xmlDate;

        in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions iAsyn = null;
        try {
            posStagingPOJOs = posStagingDO.getTrasactionIds(con, "PW_IF19", "");//Service Order
            servicesHeaderDO = new ServicesHeaderDO();
            if (posStagingPOJOs != null) {
                iterator = posStagingPOJOs.iterator();
                while (iterator.hasNext()) {
                    posStagingPOJO = (PosStagingPOJO) iterator.next();
                    servicesHeaderPOJO = servicesHeaderDO.getServiceOrderByServiceOrderNo(con, posStagingPOJO.getTransactionID());
                    iAsyn = new in.co.titan.highfrequencyresponse.DTHighFrequencyResponse.IAsynchTransactions();
                    iAsyn.setShortName("SER");
                    iAsyn.setSite(posStagingPOJO.getTid_storecode());
                    iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                    try {
                        date = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getCreatedDate());
                        time = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getCreatedTime());
                        if (date != null) {
                            xmlDate = new XMLCalendar(date);
                            if (time != null) {
                                xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    iAsyn.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                iAsyn.setCreatedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (posStagingPOJO.getUpdateType() != null) {
                        if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("U")) {
                            try {
                                date = ConvertDate.getUtilDateFromNumericDate(servicesHeaderPOJO.getModifiedDate());
                                time = ConvertDate.getSqlTimeFromString(servicesHeaderPOJO.getModifiedTime());
                                if (date != null) {
                                    xmlDate = new XMLCalendar(date);
                                    if (time != null) {
                                        xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                        if (xmlDate != null) {
                                            iAsyn.setModifiedDate(xmlDate);
                                        }
                                    }
                                    if (xmlDate != null) {
                                        iAsyn.setModifiedTime(xmlDate);
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                    iAsyn.setMessageID(posStagingPOJO.getMessageID());
                    iAsyn.setKey1(posStagingPOJO.getTransactionID());
                    iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                    mtHighFrequencyResponse.getIAsynchTransactions().add(iAsyn);
                }
                if (posStagingPOJOs.size() > 0) {
                    statusArea.append("\nService Order - Sending :" + posStagingPOJOs.size() + "Records");
                } else {
                    statusArea.append("\nService Order - No Records to be sent");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            iterator = null;
            posStagingPOJO = null;
            iAsyn = null;
            servicesHeaderDO = null;
            servicesHeaderPOJO = null;
            date = null;
            time = null;
            xmlDate = null;
        }
        return mtHighFrequencyResponse;
    }

    //*********** METHOD TO Save the ISR Status of transactions returned from ISR to POS Stsging table
    public void setSapStatusIntoStagingTable(Connection con, in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse response, PosStagingDO posStagingDO) {
        ArrayList<in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OAsynchTransactions> oAsyns = null;
        in.co.titan.highfrequencyresponse.DTHighFrequencyResResponse.OAsynchTransactions oAsyn = null;
        Iterator iterator = null;
        int res = 0, fiscalYear = 0, totRowsUpdated = 0, result = 0, custReceived = 0, custUpdated = 0, inqReceived = 0, inqUpdated = 0, soReceived = 0, forcerelReceieved = 0, soCancelWithoutAdvRecvd = 0, soCancelWithoutAdvUpd = 0, forcerelUpdated = 0, soUpdated = 0, ackCreationReceived = 0, ackCreationUpdated = 0, ackChangeReceived = 0, ackChangeUpdated = 0, quotReceived = 0, quotUpdated = 0, servOrderReceived = 0, servOrderUpdated = 0;
        try {
            oAsyns = (ArrayList<OAsynchTransactions>) response.getOAsynchTransactions();
            if (oAsyns != null) {
                iterator = oAsyns.iterator();
                if (iterator != null) {
                    totRowsUpdated = 0;
                    while (iterator.hasNext()) {
                        oAsyn = (OAsynchTransactions) iterator.next();
                        if (oAsyn != null && posStagingDO != null) {
                            if (oAsyn.getShortName() != null) {
                                if (oAsyn.getShortName().equalsIgnoreCase("CUS")) {
                                    custReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("INQ")) {
                                    inqReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("SAR")) {
                                    soReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("SOC") || oAsyn.getShortName().equalsIgnoreCase("SRC")) {//so cancel without adv
                                    soCancelWithoutAdvRecvd++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("FAR")) {//forcereleased
                                    forcerelReceieved++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("ACK") && oAsyn.getUpdateFlag().equalsIgnoreCase("I")) {
                                    ackCreationReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("ACK") && oAsyn.getUpdateFlag().equalsIgnoreCase("U")) {
                                    ackChangeReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("QUO")) {
                                    quotReceived++;
                                } else if (oAsyn.getShortName().equalsIgnoreCase("SER")) {
                                    servOrderReceived++;
                                }
                                if (oAsyn.getISRStatus() != null && oAsyn.getISRStatus().trim().equalsIgnoreCase("X")) {
                                    res = posStagingDO.updateSapIdStatus(con, "C", oAsyn.getMessageID());//means completed in isr - 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                                } else {
                                    res = posStagingDO.updateSapIdStatus(con, "X", oAsyn.getMessageID());//means not completed in isr as of now- 'C' completed, 'N' Waiting(not send to ISR),X-(send to isr but notcreated in isr
                                }
                                if (res > 0) {
                                    totRowsUpdated++;
                                    if (oAsyn.getShortName().equalsIgnoreCase("CUS")) {
                                        custUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("INQ")) {
                                        inqUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("SAR")) {
                                        soUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("SOC") || oAsyn.getShortName().equalsIgnoreCase("SRC")) {//so cancel without adv
                                        soCancelWithoutAdvUpd++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("FAR")) {//force release
                                        forcerelUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("ACK") && oAsyn.getUpdateFlag().equalsIgnoreCase("I")) {
                                        ackCreationUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("ACK") && oAsyn.getUpdateFlag().equalsIgnoreCase("U")) {
                                        ackChangeUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("QUO")) {
                                        quotUpdated++;
                                    } else if (oAsyn.getShortName().equalsIgnoreCase("SER")) {
                                        servOrderUpdated++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            statusArea.append("\nCustomer Master - Received :" + custReceived + "Records");
//            if (custReceived > 0) {
//                statusArea.append("\nCustomer Master - In POS Status Updated For:" + custUpdated + " Records");
//            }
            statusArea.append("\nInquiry - Received :" + inqReceived + "Records");
//            if (inqReceived > 0) {
//                statusArea.append("\nInquiry - In POS Status Updated For: :" + inqUpdated + " Records");
//            }
            statusArea.append("\nSales Order - Received :" + soReceived + "Records");
//            if (soReceived > 0) {
//                statusArea.append("\nSales Order - In POS Status Updated For: :" + soUpdated + " Records");
//            }
            statusArea.append("\nCancelled Sales Order without Adv - Received :" + soCancelWithoutAdvRecvd + "Records");
//            if (soReceived > 0) {
//                statusArea.append("\nCancelled Sales Order without Adv - In POS Status Updated For: :" + soCancelWithoutAdvUpd + " Records");
//            }
            statusArea.append("\nForceReleased Sales Orders - Received :" + forcerelReceieved + "Records");
//            if (soReceived > 0) {
//                statusArea.append("\nForceReleased Sales Orders - In POS Status Updated For: :" + forcerelUpdated + " Records");
//            }
            statusArea.append("\nAcknowedgement Creation - Received :" + ackCreationReceived + "Records");
//            if (ackCreationReceived > 0) {
//                statusArea.append("\nAcknowedgement Creation - In POS Status Updated For: :" + ackCreationUpdated + " Records");
//            }
            statusArea.append("\nnAcknowedgement Updation - Received :" + ackChangeReceived + "Records");
//            if (ackChangeReceived > 0) {
//                statusArea.append("\nnAcknowedgement Updation - In POS Status Updated For: :" + ackChangeUpdated + " Records");
//            }
            statusArea.append("\nQuotation - Received :" + quotReceived + "Records");
//            if (quotReceived > 0) {
//                statusArea.append("\nQuotation - In POS Status Updated For: :" + quotUpdated + " Records");
//            }
            statusArea.append("\nService Order - Received :" + servOrderReceived + "Records");
//            if (servOrderReceived > 0) {
//                statusArea.append("\nService Order - In POS Status Updated For: :" + servOrderUpdated + " Records");
//            }
        } catch (Exception e) {

        } finally {
            oAsyn = null;
            oAsyns = null;
            iterator = null;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.creditnote.CreditNoteDO;
import ISRetail.creditnote.CreditNotePOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Administrator
 */
public class ErrorLogWebService {

    JTextArea StatusArea;

    public ErrorLogWebService(JTextArea StatusArea) {
        this.StatusArea = StatusArea;
    }

    public void execute(ArrayList<ErrorLogPOJO> errorloglist) throws Exception {
        try { // Call Web Service Operation
            in.co.titan.highfrequencylog.MIOBASYNHighFrequencyLogService service = new in.co.titan.highfrequencylog.MIOBASYNHighFrequencyLogService();
            in.co.titan.highfrequencylog.MIOBASYNHighFrequencyLog port = service.getMIOBASYNHighFrequencyLogPort();
            // TODO process result here
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_HighFrequencyLog&version=3.0&Sender.Service=x&Interface=x%5Ex");
            // TODO initialize WS operation arguments here
            in.co.titan.highfrequencylog.DTHighFrequencyLog mtHighFrequencyLog = new in.co.titan.highfrequencylog.DTHighFrequencyLog();

            in.co.titan.highfrequencylog.DTHighFrequencyLog.HighFrequencyLog highFrequencyLog = null;
            if (errorloglist != null) {
                if (errorloglist.size() > 0) {
                    StatusArea.append("\n Sending Master Data Error List");
                } else {
                    StatusArea.append("\n No Master Data Error ..found..");
                }
                mtHighFrequencyLog.setSite(ServerConsole.siteID);
                mtHighFrequencyLog.setSITESEARCH(ServerConsole.siteID);
                Iterator iterator = errorloglist.iterator();

                while (iterator.hasNext()) {
                    highFrequencyLog = new in.co.titan.highfrequencylog.DTHighFrequencyLog.HighFrequencyLog();
                    ErrorLogPOJO errorLogPOJO = (ErrorLogPOJO) iterator.next();
                    highFrequencyLog.setShortName(errorLogPOJO.getShortname());
                    highFrequencyLog.setKey1(errorLogPOJO.getKey1());
                    highFrequencyLog.setKey2(errorLogPOJO.getKey2());
                    highFrequencyLog.setKey3(errorLogPOJO.getKey3());
                    highFrequencyLog.setKey4(errorLogPOJO.getKey4());
                    highFrequencyLog.setKey5(errorLogPOJO.getKey5());
                    highFrequencyLog.setKey6(errorLogPOJO.getKey6());
                    highFrequencyLog.setStatus(errorLogPOJO.getStatus());
                    highFrequencyLog.setErrorDesc(errorLogPOJO.getDescription());
                    try {
                        java.util.Date errorlogdate = ConvertDate.getUtilDateFromNumericDate(errorLogPOJO.getDateoflog());
                        Calendar errorlogtime = ConvertDate.getSqlTimeFromString(errorLogPOJO.getTimeoflog());
                        if (errorlogdate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(errorlogdate);
                            if (errorlogtime != null) {
                                xmlDate.setTime(errorlogtime.get(Calendar.HOUR_OF_DAY), errorlogtime.get(Calendar.MINUTE), errorlogtime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    highFrequencyLog.setTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                highFrequencyLog.setDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {

                    }
                    mtHighFrequencyLog.getHighFrequencyLog().add(highFrequencyLog);
                }

            }
            //Added By Smitha- to send POS Staging table status to ISR(End of the day)---  START

            in.co.titan.highfrequencylog.DTHighFrequencyLog.IAsynchTransactions iAsyn = null;
            PosStagingDO pOSStagingDO;
            ArrayList<PosStagingPOJO> posStagingPOJOs = null;
            java.util.Date date;
            XMLCalendar xmlDate;
            Calendar time;
            PosStagingPOJO posStagingPOJO;
            Iterator iterator;
            Connection con;
            CreditNoteDO creditNoteDO;
            CreditNotePOJO creditNotePOJO;
            MsdeConnection msdeConn;
            try {
                msdeConn = new MsdeConnection();
                con = msdeConn.createConnection();
                pOSStagingDO = new PosStagingDO();
                creditNoteDO = new CreditNoteDO();
                posStagingPOJOs = pOSStagingDO.getAllTrasactionIds_NotCompletedInISR(con);
                if (posStagingPOJOs != null) {
                    if (posStagingPOJOs != null) {
                        if (posStagingPOJOs.size() > 0) {
                            StatusArea.append("\n Sending Transaction Data Error List");
                        } else {
                            StatusArea.append("\n No Transaction Data Error ..found..");
                        }
                        iterator = posStagingPOJOs.iterator();
                        while (iterator.hasNext()) {
                            posStagingPOJO = (PosStagingPOJO) iterator.next();
                            if (posStagingPOJO != null) {
                                iAsyn = new in.co.titan.highfrequencylog.DTHighFrequencyLog.IAsynchTransactions();
                                if (posStagingPOJO.getInterfaceID() != null) {
                                    if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF01")) {
                                        iAsyn.setShortName("CUS");//customer
                                        StatusArea.append("\n Sending Customer..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF05")) {
                                        iAsyn.setShortName("INQ");//Inquiry
                                        StatusArea.append("\n Sending  Inquiry..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF03")) {
                                        iAsyn.setShortName("SAR");//sales order
                                        StatusArea.append("\n Sending  Sales Order..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF06") && posStagingPOJO.getUpdateType() != null && posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("F")) {
                                        iAsyn.setShortName("FAR");//. forcereleased adv receipt
                                        StatusArea.append("\n Sending  Force Relaesed Advance Receipt..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF08") || (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21") && !Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2()))) {
                                        iAsyn.setShortName("ACK");//acknowledgement creation or updation
                                        StatusArea.append("\n Sending Acknowledgement..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21") && Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                                        iAsyn.setShortName("SRT");//sales return
                                        if (posStagingPOJO.getTransactionID2() != null && posStagingPOJO.getTransactionID2().trim().length() > 0) {
                                            creditNotePOJO = creditNoteDO.getCreditNoteDetailsByRefNo(con, posStagingPOJO.getTransactionID2(), posStagingPOJO.getTid1_storecode(), posStagingPOJO.getTid1_fiscalyear());
                                            if (creditNotePOJO != null && creditNotePOJO.getCreditnoteno() != null) {
                                                iAsyn.setKey3(creditNotePOJO.getCreditnoteno());
                                            }
                                        }
                                        StatusArea.append("\n Sending  Sales Return..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF17")) {
                                        iAsyn.setShortName("QUO");//quotation
                                        StatusArea.append("\n Sending  Quotation..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF06") || posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF22")) {
                                        iAsyn.setShortName("ADR");//sale order or service order adv receipt
                                        StatusArea.append("\n Sending  Adv Receipt..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF07") || posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF20")) {
                                        iAsyn.setShortName("INV");//sale order or service order Billing
                                        if (posStagingPOJO.getTransactionID2() != null && posStagingPOJO.getTransactionID2().trim().length() > 0) {
                                            iAsyn.setKey3(String.valueOf(posStagingPOJO.getTransactionID2()));
                                        }
                                        StatusArea.append("\n Sending  Service / Sale Order Billing..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF04")) {
                                        iAsyn.setShortName("DIB");//Direct Billing
                                        if (posStagingPOJO.getTransactionID2() != null && posStagingPOJO.getTransactionID2().trim().length() > 0) {
                                            iAsyn.setKey3(String.valueOf(posStagingPOJO.getTransactionID2()));
                                        }
                                        StatusArea.append("\n Sending  Direct Billing..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF13")) {
                                        iAsyn.setShortName("ARR");//Adv reversal
                                        StatusArea.append("\n Sending  Adv Reversal..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF14")) {
                                        iAsyn.setShortName("SOC");//sale order cancellation                    
                                        if (posStagingPOJO.getTransactionID2() != null && posStagingPOJO.getTransactionID2().trim().length() > 0) {
                                            iAsyn.setKey3(String.valueOf(posStagingPOJO.getTransactionID2()));
                                        }
                                        StatusArea.append("\n Sending  Sale Order Cancellation..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF09")) {
                                        iAsyn.setShortName("CPA");//Cash Payout
                                        StatusArea.append("\n Sending  Cash Payout..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF16")) {
                                        iAsyn.setShortName("INC");//Inv Cancellation
                                        if (posStagingPOJO.getTransactionID2() != null && posStagingPOJO.getTransactionID2().trim().length() > 0) {
                                            iAsyn.setKey3(String.valueOf(posStagingPOJO.getTransactionID2()));
                                        }
                                        StatusArea.append("\n Sending Invoice Cancellaton..");
                                    } else if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF19")) {
                                        iAsyn.setShortName("SER");//serv order
                                        StatusArea.append("\n Sending  Service Order..");
                                    }
                                    //***If Sales return transactionid1 will be ack no and transactionid2 is sales return no
                                    if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21") && Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                                        iAsyn.setKey1(posStagingPOJO.getTransactionID2());
                                        StatusArea.append("  Transaction ID=" + posStagingPOJO.getTransactionID2());
                                    } else {
                                        iAsyn.setKey1(posStagingPOJO.getTransactionID());
                                        StatusArea.append("  Transaction ID=" + posStagingPOJO.getTransactionID());
                                    }
                                }
                                try {
                                    date = ConvertDate.getUtilDateFromNumericDate(posStagingPOJO.getCreatedDate());
                                    time = ConvertDate.getSqlTimeFromString(posStagingPOJO.getCreatedTime());
                                    if (date != null) {
                                        xmlDate = new XMLCalendar(date);
                                        if (time != null) {
                                            xmlDate.setTime(time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND));
                                            if (xmlDate != null) {
                                                if (posStagingPOJO.getUpdateType() != null) {
                                                    if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("I")) {
                                                        iAsyn.setCreatedTime(xmlDate);
                                                    } else {
                                                        if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21") && Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                                                            iAsyn.setCreatedTime(xmlDate);
                                                        } else {
                                                            iAsyn.setModifiedTime(xmlDate);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (xmlDate != null) {
                                            if (posStagingPOJO.getUpdateType() != null) {
                                                if (posStagingPOJO.getUpdateType().trim().equalsIgnoreCase("I")) {
                                                    iAsyn.setCreatedDate(xmlDate);
                                                } else {
                                                    if (posStagingPOJO.getInterfaceID().trim().equalsIgnoreCase("PW_IF21") && Validations.isFieldNotEmpty(posStagingPOJO.getTransactionID2())) {
                                                        iAsyn.setCreatedDate(xmlDate);
                                                    } else {
                                                        iAsyn.setModifiedDate(xmlDate);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                }
                                iAsyn.setSite(posStagingPOJO.getTid_storecode());
                                iAsyn.setMessageID(posStagingPOJO.getMessageID());
                                iAsyn.setUpdateFlag(posStagingPOJO.getUpdateType());
                                iAsyn.setKey2(String.valueOf(posStagingPOJO.getTid_fiscalyear()));
                                mtHighFrequencyLog.getIAsynchTransactions().add(iAsyn);
                            }
                        }
                    }

                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pOSStagingDO = null;
                posStagingPOJOs = null;
                iAsyn = null;
                creditNotePOJO = null;
                posStagingPOJOs = null;
                date = null;
                xmlDate = null;
                time = null;
                posStagingPOJO = null;
                iterator = null;
                con = null;
                msdeConn = null;
                creditNoteDO = null;
            }
            //Added By Smitha- to send POS Staging table status to ISR-END

            port.miOBASYNHighFrequencyLog(mtHighFrequencyLog);

            Map responseContext = ((BindingProvider) port).getResponseContext();
            Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

            System.out.println("ERROR LOG WEB SERICE RESPONSE:"+ responseCode);

        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            throw ex;
        }
    }
}

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
 * This class is used to Create a Invoice Cancellation Web Service Call
 * 
 * 
 */
package ISRetail.SalesOrderBilling;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.billing.BillingHeaderPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;
import java.text.DecimalFormat;

public class InvoiceCancelwebservice implements Webservice {

    private int updateStatus;
    private String status;
    BillingHeaderPOJO header = null;

    /**
     * To execute a call to the Invoice Cancellation Web service
     */
    public String execute(DataObject obj, String updateType) {

        in.co.titan.invoicecancellation.MIOBASYNInvoiceCancellationService service;
        in.co.titan.invoicecancellation.MIOBASYNInvoiceCancellation port;
        in.co.titan.invoicecancellation.DTInvoiceCancellation mtInvoceCancellation;
        Map<String, Object> ctxt;
        try {
            // Call Web Service Operation
            service = new in.co.titan.invoicecancellation.MIOBASYNInvoiceCancellationService();
            //port = service.getMIOBASYNInvoiceCancellationPort();
            port = service.getHTTPSPort();
            mtInvoceCancellation = new in.co.titan.invoicecancellation.DTInvoiceCancellation();
            port.miOBASYNInvoiceCancellation(mtInvoceCancellation);
        } catch (Exception ex) {

        }
        if (obj instanceof InvoiceCancellationBean) {
            InvoiceCancellationBean masterBean = new InvoiceCancellationBean();
            masterBean = (InvoiceCancellationBean) obj;
            header = masterBean.getHeader();
            try {
                service = new in.co.titan.invoicecancellation.MIOBASYNInvoiceCancellationService();
                //    port = service.getMIOBASYNInvoiceCancellationPort();// TODO initialize WS operation arguments here
                port = service.getHTTPSPort();
                ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_InvoiceCancellation&version=3.0&Sender.Service=X&Interface=Z%5EY");
                mtInvoceCancellation = new in.co.titan.invoicecancellation.DTInvoiceCancellation();
                mtInvoceCancellation.setSite(header.getStorecode());
                mtInvoceCancellation.setSITESEARCH(header.getStorecode());
                if (header.getBilledfrom().equals("QUICK")) {
                    mtInvoceCancellation.setIdentFlag(header.getBilledfrom());
                    mtInvoceCancellation.setPOSOrderStatus("CANCELLED");
                } else {
                    mtInvoceCancellation.setIdentFlag("");
                    mtInvoceCancellation.setPOSOrderStatus("OPEN");
                }

                if (header.getReasonforcancellation().equals("11")) {
                    mtInvoceCancellation.setReversalReason("11");
                } else if (header.getReasonforcancellation().equals("12")) {
                    mtInvoceCancellation.setReversalReason("12");
                } else {
                    mtInvoceCancellation.setCancelReason(header.getReasonforcancellation());
                }

                mtInvoceCancellation.setFyear(String.valueOf(header.getFiscalyear()));
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(header.getCreateddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(header.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtInvoceCancellation.setCreatedTime(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtInvoceCancellation.setCreatedDate(xmlDate);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtInvoceCancellation.setCreatedName(header.getCreatedby());
                try {
                    if (header.getInvoicedate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getInvoicedate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtInvoceCancellation.setDocumentDate((xmlDate));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mtInvoceCancellation.setISRPaymentRefID(header.getAccsaprefno());
                mtInvoceCancellation.setPOSInvliceNO(header.getInvoicecancelno());//POS invoice cancellation No.
                mtInvoceCancellation.setInvoiceNO(header.getSaprefno());//Sap invoiceno, if it has come from iSR
                mtInvoceCancellation.setPOSOldInvoiceNo(header.getInvoiceno());//Pos invoice no, which got cancelled
                if (header.getCreditNoteno() != null) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    mtInvoceCancellation.setPOSCreditNoteNO(header.getCreditNoteno());
                    if (header.getCreditnoteexpirydate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getCreditnoteexpirydate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtInvoceCancellation.setCrnExpDate((xmlDate));
                            }

                        }
                    }
                    mtInvoceCancellation.setPOSCreditNoteAmt(new BigDecimal(df.format(header.getExcessamount())));
                }
                //Code added on July 8th 2010
                if (header.getNrcreditnoteno() != null) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    mtInvoceCancellation.setPOSNRCreditNoteNo(header.getNrcreditnoteno());
                    if (header.getCreditnoteexpirydate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(header.getCreditnoteexpirydate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtInvoceCancellation.setCrnExpDate((xmlDate));
                            }

                        }
                    }
                    mtInvoceCancellation.setPOSNRCreditNoteAmt(new BigDecimal(df.format(header.getNrexcessamt())));
                }

                //End of Code added on July 8th 2010
                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("Invoice Cancellation Store Code " + mtInvoceCancellation.getSite());
                mtInvoceCancellation.setCancelledOTP(header.getCancelOtp());
                //if(mtInvoceCancellation.getSite()!=null && mtInvoceCancellation.getSite().trim().length()>0){
                if (Validations.isFieldNotEmpty(mtInvoceCancellation.getSite())) {
                    port.miOBASYNInvoiceCancellation(mtInvoceCancellation);
                    Map responseContext = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                    if (responseCode.intValue() == 200) {
                        return "true";
                    } else {
                        return "false";
                    }
                } else {
                    return "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNInvoiceCancellation(mtInvoceCancellation);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                    return "true";
                } else {
                    return "false";
                }*/
            } catch (Exception ex) {

            } finally {
                service = null;
                port = null;
                mtInvoceCancellation = null;
                ctxt = null;
            }

        }
        return status = String.valueOf(updateStatus);
    }
}

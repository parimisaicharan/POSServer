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
 * This class is used to Create a Invoice Creation Web Service Call
 * 
 * 
 */
package ISRetail.services;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import in.co.titan.advancereceipt.DTAdvanceReceipt.ItemTable;
import ISRetail.paymentdetails.AdvanceReceiptCancellationBean;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.salesorder.SaleOrderMasterBean;
import ISRetail.salesorder.SalesOrderDetailsDO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class ServiceAdvanceReceiptWebservice implements Webservice {

    public String execute(DataObject dataObject, String updateType) {
        AdvanceReceiptPOJO obj = null;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        ServiceAdvanceCreattionBean bean = null;
        AdvanceReceiptCancellationBean bean1 = null;
        SaleOrderMasterBean salesOrderMasterBean = null;

        try {
            try {
                if (dataObject instanceof SaleOrderMasterBean) {

                    salesOrderMasterBean = (SaleOrderMasterBean) dataObject;
                    SalesOrderHeaderPOJO salesOrderHeaderPOJO = salesOrderMasterBean.getSalesOrderHeader();
                    in.co.titan.advancereceipt.MIOBASYNAdvanceReceiptService service = new in.co.titan.advancereceipt.MIOBASYNAdvanceReceiptService();
                    //in.co.titan.advancereceipt.MIOBASYNAdvanceReceipt port = service.getMIOBASYNAdvanceReceiptPort();
                    in.co.titan.advancereceipt.MIOBASYNAdvanceReceipt port = service.getHTTPSPort();
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                    //((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceipt&version=3.0&Sender.Service=X&Interface=Z%5EY");
                    // TODO initialize WS operation arguments here
                    in.co.titan.advancereceipt.DTAdvanceReceipt mtAdvanceReceipt = new in.co.titan.advancereceipt.DTAdvanceReceipt();
                    // TODO process result here

                    mtAdvanceReceipt.setSaleOrderNO(salesOrderHeaderPOJO.getSaleorderno());
                    //  mtAdvanceReceipt.setCustomerNO("TIL-DR-CUS");
                    /**
                     * ***********
                     */
                    try {
                        MsdeConnection msdeConnection = new MsdeConnection();
                        Connection con = msdeConnection.createConnection();
                        String division = new ServicesDetailDO().getArticleDivisionByServiceOrderNo(con, salesOrderHeaderPOJO.getSaleorderno());
                        mtAdvanceReceipt.setDivision(division);
                    } catch (Exception e) {

                    }
                    mtAdvanceReceipt.setCustomerNO(salesOrderHeaderPOJO.getCustomercode());
                    // mtAdvanceReceipt.setCustomerNO("CAA0000076");

                    /**
                     * ****************
                     */
                    //  mtAdvanceReceipt.setDocumentDate("02/07/2008");
                    //       mtAdvanceReceipt.setDocumentDate(new BigInteger(String.valueOf(obj.getDateofpayment())));
                    try {
                        if (salesOrderHeaderPOJO.getSaleorderdate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getSaleorderdate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    mtAdvanceReceipt.setDocumentDate(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mtAdvanceReceipt.setCreatedBy(salesOrderHeaderPOJO.getCreatedBy());
                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getSaleorderdate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getCreatedTime());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtAdvanceReceipt.setCreatedTime(xmlDate);
                                    mtAdvanceReceipt.setModifiedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtAdvanceReceipt.setCreatedDate(xmlDate);
                                mtAdvanceReceipt.setModifiedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    mtAdvanceReceipt.setModifiedBy(salesOrderHeaderPOJO.getModifiedBy());
                    mtAdvanceReceipt.setFlagForceRelease("Y");

                    if (salesOrderHeaderPOJO.getSaletype().equalsIgnoreCase("Credit")) {
                        if (Validations.isFieldNotEmpty(salesOrderHeaderPOJO.getEd())) {
                            mtAdvanceReceipt.setCreditSaleReference(salesOrderHeaderPOJO.getEd());
                        }
                    }
                    mtAdvanceReceipt.setSaleType(salesOrderHeaderPOJO.getSaletype().toUpperCase());
                    if (salesOrderHeaderPOJO.getReleaseStatus().equalsIgnoreCase("FORCERELEASED")) {
                        mtAdvanceReceipt.setReleaseStatus("FORCERELEASED");
                    }

                    if (Validations.isFieldNotEmpty(salesOrderHeaderPOJO.getCreditsalereference())) {
                        mtAdvanceReceipt.setInstiID(salesOrderHeaderPOJO.getCreditsalereference());
                    }

                    ItemTable itemtable = new ItemTable();
                    itemtable.setSiteID(salesOrderHeaderPOJO.getStoreCode());
                    mtAdvanceReceipt.getItemTable().add(itemtable);

                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    System.out.println("Service order Advance Receipt Storecode " + mtAdvanceReceipt.getSITESEARCH());
                    if (mtAdvanceReceipt.getSITESEARCH() != null && mtAdvanceReceipt.getSITESEARCH().trim().length() > 0) {
                        port.miOBASYNAdvanceReceipt(mtAdvanceReceipt);
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
                    /*port.miOBASYNAdvanceReceipt(mtAdvanceReceipt);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                return "true";
                } else {
                return "false";
                }*/
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "false";
            }

            /**
             * ******************************* Advance Receipt Creation
             * ****************************************************
             */
            if (dataObject instanceof ServiceAdvanceCreattionBean) {

                bean = (ServiceAdvanceCreattionBean) dataObject;
                obj = bean.getAdvanceReceiptPOJO();

                v = (Vector<AdvanceReceiptDetailsPOJO>) bean.getAdvanceReceiptDetailsPOJOs();
                in.co.titan.advancereceipt.MIOBASYNAdvanceReceiptService service = new in.co.titan.advancereceipt.MIOBASYNAdvanceReceiptService();
                //in.co.titan.advancereceipt.MIOBASYNAdvanceReceipt port = service.getMIOBASYNAdvanceReceiptPort();
                in.co.titan.advancereceipt.MIOBASYNAdvanceReceipt port = service.getHTTPSPort();

                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);

                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                //((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceipt&version=3.0&Sender.Service=X&Interface=Z%5EY");

                // TODO initialize WS operation arguments here
                in.co.titan.advancereceipt.DTAdvanceReceipt mtAdvanceReceipt = new in.co.titan.advancereceipt.DTAdvanceReceipt();
                // TODO process result here
                mtAdvanceReceipt.setSITESEARCH(obj.getStorecode());
                // mtAdvanceReceipt.setSaleOrderNO(obj.getRefno());
                try {

                    if (obj.getDocumentno() != null) {
                        if (obj.getDocumentno().length() > 0) {
                            mtAdvanceReceipt.setFlagAdvance("Y");
                        }
                    }
                    if (obj.getReleasestatus() != null) {
                        if (obj.getReleasestatus().equalsIgnoreCase("FORCERELEASED") || obj.getReleasestatus().equalsIgnoreCase("RELEASE")) {
                            mtAdvanceReceipt.setFlagForceRelease("Y");

                            if (obj.getSaletype().equalsIgnoreCase("credit")) {
                                mtAdvanceReceipt.setCreditSaleReference(obj.getCreditsalereference());

                            }
                        }
                    }

                    if (obj.getSaletype() != null) {
                        mtAdvanceReceipt.setSaleType(obj.getSaletype().toUpperCase());
                    }

                    if (obj.getReleasestatus() != null) {
                        if (obj.getReleasestatus().equalsIgnoreCase("FORCERELEASED")) {
                            mtAdvanceReceipt.setReleaseStatus("FORCERELEASED");
                        }
                        if (obj.getReleasestatus().equalsIgnoreCase("RELEASE")) {
                            mtAdvanceReceipt.setReleaseStatus("RELEASE");
                        }
                        if (obj.getReleasestatus().equalsIgnoreCase("HOLD")) {
                            mtAdvanceReceipt.setReleaseStatus("HOLD");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mtAdvanceReceipt.setSaleOrderNO(obj.getRefno());
                try {
                    MsdeConnection msdeConnection = new MsdeConnection();
                    Connection con = msdeConnection.createConnection();
                    //          String division = new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(con,  obj.getRefno());
                    String division = new ServicesDetailDO().getArticleDivisionByServiceOrderNo(con, obj.getRefno());
                    mtAdvanceReceipt.setDivision(division);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /**
                 * ***********
                 */
                mtAdvanceReceipt.setCustomerNO(obj.getCustomercode());

                /**
                 * ****************
                 */
                //  mtAdvanceReceipt.setDocumentDate("02/07/2008");
                //       mtAdvanceReceipt.setDocumentDate(new BigInteger(String.valueOf(obj.getDateofpayment())));
                try {
                    if (obj.getDateofpayment() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(obj.getDateofpayment());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtAdvanceReceipt.setDocumentDate(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }
                mtAdvanceReceipt.setCreatedBy(obj.getCreatedby());
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(obj.getDateofpayment());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(obj.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtAdvanceReceipt.setCreatedTime(xmlDate);
                                mtAdvanceReceipt.setModifiedTime(xmlDate);
                                //System.out.println("xmlDate" + xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtAdvanceReceipt.setCreatedDate(xmlDate);
                            mtAdvanceReceipt.setModifiedDate(xmlDate);
                            //  System.out.println("xmlDate" + xmlDate);
                        }

                    }
                } catch (Exception e) {

                }

                mtAdvanceReceipt.setModifiedBy(obj.getModifiedby());

                //  mtAdvanceReceipt.setCustomerNO(value);
                //   mtAdvanceReceipt.setDataSheetNO(value);
                //  mtAdvanceReceipt.setDocumentDate(value);
                //  mtAdvanceReceipt.setStatus(value);
                Iterator iter = v.iterator();
                Object payobj = new Object();

                AdvanceReceiptDetailsPOJO advpojo;

                while (iter.hasNext()) {

                    advpojo = (AdvanceReceiptDetailsPOJO) iter.next();

                    ItemTable itemtable = new ItemTable();
                    itemtable.setPOSAdvRecNO(obj.getDocumentno());
                    try {
                        itemtable.setSiteID(advpojo.getStorecode());
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    //    itemtable.setRemarks("Advsave");
                    if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setBank(advpojo.getBank());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*branchname*/ itemtable.setBrType(String.valueOf(advpojo.getBranchname()));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        try {
                            itemtable.setSiteID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setBank(advpojo.getBank());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setCNCrAmount(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setCNFYear(new BigInteger(String.valueOf(advpojo.getFiscalyear())));
                        /*CNreference*/ itemtable.setCNText(advpojo.getCreditnotereference());

                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        // itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setBank(advpojo.getBank());
                        /*cardtype*/ itemtable.setBrType(advpojo.getCardtype());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("EML")) {

                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setELLoanAmt(new BigDecimal(String.valueOf(advpojo.getLoanamount())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setELName(advpojo.getEmployeename());
                        itemtable.setELDept(advpojo.getDepartment());
                        itemtable.setELLETRef(advpojo.getLetterreference());

                        /*Authorperson*/ itemtable.setELAuth(String.valueOf(advpojo.getAuthorizedperson()));
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        /*currencytype*/ itemtable.setBrType(String.valueOf(advpojo.getCurrencytype()));
                        itemtable.setFEEXrate(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*noofunits*/ itemtable.setFEUnits(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFV")) {
                        System.out.println("giftvoucher in");
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));

                        /*gvno*/ itemtable.setNo(advpojo.getInstrumentno());
                        if (!advpojo.getGVFrom().equalsIgnoreCase("") && !advpojo.getGVTO().equalsIgnoreCase("")) {
                            itemtable.setGVForm(advpojo.getGVFrom());

                            itemtable.setGVTo(advpojo.getGVTO());
                        }
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setGVDenAmount(new BigDecimal(String.valueOf(advpojo.getDenomination())));
                        /*validtext*/ itemtable.setGVText(advpojo.getValidationtext());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("LOY")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAppNos(advpojo.getApprovalno());
                        //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        itemtable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPRedPoint(String.valueOf(advpojo.getRedeemedpoints()));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setLPAppNos(advpojo.getApprovalno());
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        itemtable.setDate(xmlDate);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        itemtable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                        itemtable.setLPRedPoint(String.valueOf(advpojo.getRedeemedpoints()));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    }
                    mtAdvanceReceipt.getItemTable().add(itemtable);
                }
                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                if (mtAdvanceReceipt.getSITESEARCH() != null && mtAdvanceReceipt.getSITESEARCH().trim().length() > 0) {
                    port.miOBASYNAdvanceReceipt(mtAdvanceReceipt);

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
                /*port.miOBASYNAdvanceReceipt(mtAdvanceReceipt);
            Map responseContext = ((BindingProvider) port).getResponseContext();
            Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
            if (responseCode.intValue() == 200) {
            return "true";
            } else {
            return "false";
            }*/
            } /**
             * ******************************* End of Advance Receipt Creation
             * ****************************************************
             */
            /**
             * ***********************************Advance Receipt
             * Cancellation**********************************
             */
            else if (dataObject instanceof AdvanceReceiptCancellationBean) {

                bean1 = (AdvanceReceiptCancellationBean) dataObject;
                obj = bean1.getAdvanceReceiptPOJO();

                try { // Call Web Service Operation
                    in.co.titan.advancereceiptreversal.MIOBASYNARReversalService service = new in.co.titan.advancereceiptreversal.MIOBASYNARReversalService();
                    //in.co.titan.advancereceiptreversal.MIOBASYNARReversal port = service.getMIOBASYNARReversalPort();
                    in.co.titan.advancereceiptreversal.MIOBASYNARReversal port = service.getHTTPSPort();//Coded by Balachander v on 5.8.2020
                    Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                    //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                    ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);

                    ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");

                    // TODO initialize WS operation arguments here
                    in.co.titan.advancereceiptreversal.DTARReversal mtARReversal = new in.co.titan.advancereceiptreversal.DTARReversal();
                    //  BigInteger BI = new BigInteger(Integer.toString(obj.getFiscalyear()));   

                    BigInteger BI = new BigInteger(Integer.toString(2009));

                    System.out.println("SAPreferenceid" + obj.getSapreferenceid());
                    System.out.println("fiscal year" + BI);
                    System.out.println("siteid" + obj.getStorecode());
                    System.out.println("reasons" + obj.getReasonforcancellation());
                    mtARReversal.setADVRECNO(obj.getSapreferenceid());
                    mtARReversal.setPOSADVNO(obj.getDocumentno());
                    mtARReversal.setPOSREVNO(obj.getDatasheetno());
                    mtARReversal.setFYEAR(BI);

                    mtARReversal.setSITEID(obj.getStorecode());
                    mtARReversal.setREVREASON(obj.getReasonforcancellation());
                    mtARReversal.setCreatedBy(obj.getCreatedby());
                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(obj.getCreateddate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(obj.getCreatedtime());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtARReversal.setCreatedTime(xmlDate);
                                    mtARReversal.setModifiedTime(xmlDate);
                                    //System.out.println("xmlDate" + xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtARReversal.setCreatedDate(xmlDate);
                                mtARReversal.setModifiedDate(xmlDate);
                                // System.out.println("xmlDate" + xmlDate);
                            }

                        }
                    } catch (Exception e) {

                    }

                    mtARReversal.setModifiedBy(obj.getModifiedby());

                    if (!obj.getReleasestatus().equalsIgnoreCase("FORCERELEASED")) {

                        mtARReversal.setReleaseStatus(obj.getReleasestatus());

                    }
                    System.out.println("releasestatus" + obj.getReleasestatus());
                    System.out.println("refno" + obj.getRefno());
                    mtARReversal.setSALEORDERNO(obj.getRefno());
                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    if (mtARReversal.getSITEID() != null && mtARReversal.getSITEID().trim().length() > 0) {
                        port.miOBASYNARReversal(mtARReversal);
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
                    // TODO process result here
                    //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    /*port.miOBASYNARReversal(mtARReversal);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                return "true";
                } else {
                return "false";
                }*/
                } catch (Exception ex) {
                    return "false";
                    // TODO handle custom exceptions here
                }

            } /**
             * ************************End of Advance Receipt
             * Cancellation*************************
             */
            else {

                return "false";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }
}

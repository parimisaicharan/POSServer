/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.CashReceipt;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.paymentdetails.AdvanceReceiptDetailsPOJO;

import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.utility.validations.Validations;

import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.advancereceiptquick.DTAdvanceReceiptQuick.AdvanceTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author naveenn
 */
public class CashReceiptDO implements Webservice {

    public CashReceiptPOJO getCashReceiptHeaderForWebservice(Connection con, String queryString) {
        CashReceiptPOJO cashreceiptPOJO = null;
        Statement stmt = null;
        ResultSet rs = null;
        String companyCode = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(queryString);
            if (rs.next()) {
                cashreceiptPOJO = new CashReceiptPOJO();
                cashreceiptPOJO.setStorecode(rs.getString("storecode"));
                cashreceiptPOJO.setFiscalyear(rs.getInt("fiscalyear"));
                cashreceiptPOJO.setDocumentno(rs.getString("documentno"));
                cashreceiptPOJO.setDocumentdate(rs.getInt("documentdate"));
                cashreceiptPOJO.setRefno(rs.getString("refno"));
                cashreceiptPOJO.setRefdate(rs.getString("refdate"));
                cashreceiptPOJO.setDatasheetno(rs.getString("datasheetno"));
                cashreceiptPOJO.setAmount(rs.getDouble("totalamount"));
                cashreceiptPOJO.setCancelledstatus(rs.getString("cancelledstatus"));
                cashreceiptPOJO.setCreatedby(rs.getString("createdby"));
                cashreceiptPOJO.setCreateddate(rs.getInt("createddate"));
                cashreceiptPOJO.setCreatedtime(rs.getString("createdtime"));
                cashreceiptPOJO.setModifiedby(rs.getString("modifiedby"));
                cashreceiptPOJO.setModifieddate(rs.getInt("modifieddate"));
                cashreceiptPOJO.setModifiedtime(rs.getString("modifiedtime"));
                cashreceiptPOJO.setCancelOTP(rs.getString("cancelotp"));//Coded by Balachander V on 3.8.2020 to send cash receipt cancellation OTP to SAP
            }
            SiteMasterDO smdo = new SiteMasterDO();
            companyCode = smdo.getSiteCompanyCode(con);
            System.out.println("Company Code:" + companyCode);
            cashreceiptPOJO.setCompanyCode(companyCode);

            return cashreceiptPOJO;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {

            try {
                //con.close();
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(CashReceiptDO.class.getName()).log(Level.SEVERE, null, ex);
            }
            stmt = null;
            rs = null;
        }

    }

    public String execute(DataObject obj, String updateType) {
        String updatedInISR = "false";
        try {

            CashReceiptCreationBean bean = null;
            CashReceiptCancellationBean CancelBean = null;
            CashReceiptPOJO cashReceiptPOJO = null;
            Vector<AdvanceReceiptDetailsPOJO> v = null;

            if (obj instanceof CashReceiptCreationBean) {
                bean = (CashReceiptCreationBean) obj;
                v = (Vector<AdvanceReceiptDetailsPOJO>) bean.getAdvanceReceiptDetailsPOJO();
                cashReceiptPOJO = bean.getCashReceiptPOJO();

                in.co.titan.advancereceiptquick.MIOBASYNAdvanceReceiptQuickService service = new in.co.titan.advancereceiptquick.MIOBASYNAdvanceReceiptQuickService();
                // in.co.titan.advancereceiptquick.MIOBASYNAdvanceReceiptQuick port = service.getMIOBASYNAdvanceReceiptQuickPort();
                in.co.titan.advancereceiptquick.MIOBASYNAdvanceReceiptQuick port = service.getHTTPSPort();

                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceiptQuick&version=3.0&Sender.Service=X&Interface=Z%5EY");
                in.co.titan.advancereceiptquick.DTAdvanceReceiptQuick mtCashReceipt = new in.co.titan.advancereceiptquick.DTAdvanceReceiptQuick();
                System.out.println(cashReceiptPOJO.getStorecode() + "----" + String.valueOf(cashReceiptPOJO.getFiscalyear()) + "----" + cashReceiptPOJO.getDatasheetno());
                System.out.println(cashReceiptPOJO.getRefno() + "----" + cashReceiptPOJO.getCompanyCode() + "----" + cashReceiptPOJO.getDocumentno());
                mtCashReceipt.setSITESEARCH(cashReceiptPOJO.getStorecode());
                mtCashReceipt.setSITEID(cashReceiptPOJO.getStorecode());

                mtCashReceipt.setFISCALYEAR(String.valueOf(cashReceiptPOJO.getFiscalyear()));
                mtCashReceipt.setDataSheetNO(cashReceiptPOJO.getDatasheetno());
                mtCashReceipt.setCustomerNO(cashReceiptPOJO.getRefno());
                mtCashReceipt.setCOMPCODE(cashReceiptPOJO.getCompanyCode());
                mtCashReceipt.setPOSNEWNO(cashReceiptPOJO.getDocumentno());

                try {
                    if (cashReceiptPOJO.getDocumentdate() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(cashReceiptPOJO.getDocumentdate());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtCashReceipt.setDocumentDate(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }

                mtCashReceipt.setStatus(cashReceiptPOJO.getCancelledstatus());

                mtCashReceipt.setCreatedBy(cashReceiptPOJO.getCreatedby());
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(cashReceiptPOJO.getCreateddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(cashReceiptPOJO.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtCashReceipt.setCreatedTime(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtCashReceipt.setCreatedDate(xmlDate);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (cashReceiptPOJO.getModifiedby() != null) {
                    mtCashReceipt.setModifiedBy(cashReceiptPOJO.getModifiedby());
                    try {
                        java.util.Date modifiedDate = ConvertDate.getUtilDateFromNumericDate(cashReceiptPOJO.getModifieddate());
                        Calendar modifiedTime = ConvertDate.getSqlTimeFromString(cashReceiptPOJO.getModifiedtime());
                        if (modifiedDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(modifiedDate);
                            if (modifiedDate != null) {
                                xmlDate.setTime(modifiedTime.get(Calendar.HOUR_OF_DAY), modifiedTime.get(Calendar.MINUTE), modifiedTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtCashReceipt.setModifiedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtCashReceipt.setModifiedDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (Validations.isFieldNotEmpty(cashReceiptPOJO.getCancelOTP())) {//Coded by Balachander V on 3.8.2020 to send cash receipt cancellation OTP to sAP
                    mtCashReceipt.setCancelledOTP(cashReceiptPOJO.getCancelOTP());
                }

                Iterator it = v.iterator();
                AdvanceReceiptDetailsPOJO advpojo;

                while (it.hasNext()) {
                    AdvanceTable advTable = new AdvanceTable();
                    advpojo = (AdvanceReceiptDetailsPOJO) it.next();

                    if (advpojo.getModeofpayment().equalsIgnoreCase("CHQ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advpojo.setInstrumentno(advpojo.getInstrumentno());

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        advTable.setBank(advpojo.getBank());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*branchname*/ advTable.setBrType(String.valueOf(advpojo.getBranchname()));
                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS") || advpojo.getModeofpayment().equalsIgnoreCase("INS") || advpojo.getModeofpayment().equalsIgnoreCase("HCS")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());

                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setBank(advpojo.getBank());
                        if (Validations.isFieldNotEmpty(advpojo.getValidationtext())) {//added by charan for titan co brand
                                advTable.setCNText(advpojo.getValidationtext());
                            }
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ advTable.setBrType(String.valueOf(advpojo.getCardtype()));

                        advTable.setPaymentMode(advpojo.getModeofpayment());
                        if (Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())) {
                          //  advTable.set(advpojo.getAuthorizedperson());
                          advTable.setLPAppNos(advpojo.getAuthorizedperson());
                        }
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("BAJ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());

                        //      itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setBank(advpojo.getBank());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ advTable.setBrType(String.valueOf(advpojo.getCardtype()));

                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("PTM")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CRN")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        //   itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        advTable.setCNCrAmount(new BigDecimal(String.valueOf(advpojo.getCreditamount())));
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setCNFYear(String.valueOf(advpojo.getFiscalyear()));

                        /*CNreference*/ advTable.setCNText(advpojo.getCreditnotereference());

                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("DBC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        // itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));
                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setBank(advpojo.getBank());
                        /*cardtype*/ advTable.setBrType(advpojo.getCardtype());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));

                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        /*currencytype*/ advTable.setBrType(String.valueOf(advpojo.getCurrencytype()));
                        advTable.setFEEXrate(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*noofunits*/ advTable.setFEUnits(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("LOY")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setLPAppNos(advpojo.getApprovalno());
                        //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }

                        advTable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                        advTable.setLPRedPoint(String.valueOf(advpojo.getRedeemedpoints()));

                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FTD")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFH")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
//                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("UPI")) {//Code Added by Balachander V for new payment mode on 10.07.2019
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    }//Code Ended by Balachander V for new payment mode on 10.07.2019
                    else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {//Added by Balachander V to send the Tata Neu Point details to SAP
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setLPAppNos(advpojo.getApprovalno());
                        //    itemtable.setDate(new BigInteger(String.valueOf(advpojo.getInstrumentdate())));

                        try {
                            if (advpojo.getInstrumentdate() != 0) {
                                java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(advpojo.getInstrumentdate());
                                if (followDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(followDate);
                                    if (xmlDate != null) {
                                        advTable.setDate(xmlDate);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("instrument date not Set" + e);
                        }
                        advTable.setLPPoints(String.valueOf(advpojo.getLoyalitypoints()));
                        advTable.setLPRedPoint(String.valueOf(advpojo.getRedeemedpoints()));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("ZPI")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        if (Validations.isFieldNotEmpty(advpojo.getInstrumentno())) {
                            advTable.setNo(advpojo.getInstrumentno());
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getLoanamount())) {
                            advTable.setLPPoints(String.valueOf((int) advpojo.getLoanamount()));
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getApprovalno())) {
                            advTable.setLPAppNos(String.valueOf(advpojo.getApprovalno()));
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getAuthorizationcode())) {
                            advTable.setAuthCode(advpojo.getAuthorizationcode());
                        }
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (Validations.isFieldNotEmpty(advpojo.getModeofpayment())) {//Coded by Balachander V for General payment mode on 31.03.2020
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        advTable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        advTable.setNo(advpojo.getInstrumentno());
                        advTable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        advTable.setPaymentMode(advpojo.getModeofpayment());
                    }//Coded by Balachander V for General payment mode on 31.03.2020

                    mtCashReceipt.getAdvanceTable().add(advTable);

                }
                System.out.println(mtCashReceipt.getCustomerNO() + "--$$$$$--" + mtCashReceipt.getCOMPCODE());

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                if (mtCashReceipt.getSITEID() != null && mtCashReceipt.getSITEID().trim().length() > 0) {
                    port.miOBASYNAdvanceReceiptQuick(mtCashReceipt);

                    Map response = ((BindingProvider) port).getResponseContext();
                    Integer responseCode = (Integer) response.get(MessageContext.HTTP_RESPONSE_CODE);

                    if (responseCode.intValue() == 200) {
                        updatedInISR = "true";
                    } else {
                        updatedInISR = "false";
                    }
                } else {
                    updatedInISR = "false";
                }
                //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNAdvanceReceiptQuick(mtCashReceipt);
            Map response = ((BindingProvider)port).getResponseContext();
            Integer responseCode = (Integer) response.get(MessageContext.HTTP_RESPONSE_CODE);
            if(responseCode.intValue() == 200){
            updatedInISR= "true"; 
            }else{
            updatedInISR= "false"; 
            }*/
            }
            return updatedInISR;

        } catch (Exception ex) {
            ex.printStackTrace();
            return "false";
        }

        //   throw new UnsupportedOperationException("Not supported yet.");
    }
}

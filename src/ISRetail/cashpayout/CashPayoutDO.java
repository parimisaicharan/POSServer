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
 * This class file is used as a Data Object between Cash Payout Form and Database
 * This is used for transaction of Cash Payout data from and to the database
 * This class is used to save all database operations related to Cash Payout
 * 
 * 
 */
package ISRetail.cashpayout;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;

import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.utility.validations.Validations;

import in.co.titan.payoutcreditmemo.DTPayOut.PayOutItem;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class CashPayoutDO implements Webservice {

    /**
     * To get the Cash Pay Out POJO from the cash payout Number as argument
     */
    public CashPayoutPOJO getCashPayoutListforthread(Connection conn, String cashpayoutnum) {
        CashPayoutPOJO caspayoutpojoobj;
        Statement pstmt;
        // String searchstmt="SELECT  dbo.tbl_cashpayout.storecode, dbo.tbl_cashpayout.fiscalyear,  dbo.tbl_cashpayout.cashpayoutno,dbo.tbl_cashpayout.cashpayoutdate, dbo.tbl_cashpayout.customercode, dbo.tbl_cashpayout.refcreditnoteno,dbo.tbl_cashpayout.saprefernceid, dbo.tbl_cashpayout.issuedate, dbo.tbl_cashpayout.netamount, dbo.tbl_cashpayout.paymentmode, dbo.tbl_cashpayout.chequeno, dbo.tbl_cashpayout.bank, dbo.tbl_cashpayout.branchname, dbo.tbl_cashpayout.reasonforpayout, dbo.tbl_cashpayout.createdby, dbo.tbl_cashpayout.createddate, dbo.tbl_cashpayout.createdtime, dbo.tbl_cashpayout.modifiedby, dbo.tbl_cashpayout.modifieddate, dbo.tbl_cashpayout.modifiedtime,tbl_creditnote.refsalesorderno  from dbo.tbl_cashpayout left outer join tbl_creditnote on tbl_creditnote.creditnoteno = tbl_cashpayout.refcreditnoteno   where tbl_cashpayout.cashpayoutno='"+cashpayoutnum+"'"; 
        String searchstmt = "SELECT  dbo.tbl_cashpayout.storecode, dbo.tbl_cashpayout.fiscalyear,  dbo.tbl_cashpayout.cashpayoutno,dbo.tbl_cashpayout.cashpayoutdate, dbo.tbl_cashpayout.customercode, dbo.tbl_cashpayout.refcreditnoteno,dbo.tbl_cashpayout.saprefernceid, dbo.tbl_cashpayout.issuedate, dbo.tbl_cashpayout.netamount, dbo.tbl_cashpayout.paymentmode, dbo.tbl_cashpayout.chequeno, dbo.tbl_cashpayout.bank, dbo.tbl_cashpayout.branchname, dbo.tbl_cashpayout.reasonforpayout, dbo.tbl_cashpayout.createdby, dbo.tbl_cashpayout.createddate, dbo.tbl_cashpayout.createdtime, dbo.tbl_cashpayout.modifiedby, dbo.tbl_cashpayout.modifieddate, dbo.tbl_cashpayout.modifiedtime,tbl_cashpayout.cancelotp,tbl_creditnote.refsalesorderno,tbl_creditnote.reftype,tbl_creditnote.refno  from dbo.tbl_cashpayout left outer join tbl_creditnote on tbl_creditnote.creditnoteno = tbl_cashpayout.refcreditnoteno   where tbl_cashpayout.cashpayoutno='" + cashpayoutnum + "'";
        ResultSet rs;
        try {
            caspayoutpojoobj = new CashPayoutPOJO();
            pstmt = (Statement) conn.createStatement();
            rs = pstmt.executeQuery(searchstmt);
            while (rs.next()) {
                caspayoutpojoobj.setStorecode(rs.getString("storecode"));
                caspayoutpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                caspayoutpojoobj.setCashpayoutno(rs.getString("cashpayoutno"));
                caspayoutpojoobj.setDateofpayout(rs.getInt("cashpayoutdate"));
                caspayoutpojoobj.setCustomercode(rs.getString("customercode"));
                caspayoutpojoobj.setRefcreditnotenumber(rs.getString("refcreditnoteno"));
                caspayoutpojoobj.setSapreferenceid(rs.getString("saprefernceid"));
                caspayoutpojoobj.setNetamount(rs.getDouble("netamount"));
                caspayoutpojoobj.setPaymentmode(rs.getString("paymentmode"));
                caspayoutpojoobj.setReasonforpayout(rs.getString("reasonforpayout"));
                caspayoutpojoobj.setCreatedby(rs.getString("createdby"));
                caspayoutpojoobj.setCreateddate(rs.getInt("createddate"));
                caspayoutpojoobj.setCreatedtime(rs.getString("createdtime"));
                caspayoutpojoobj.setModifiedby(rs.getString("modifiedby"));
                caspayoutpojoobj.setModifieddate(rs.getInt("modifieddate"));
                caspayoutpojoobj.setModifiedtime(rs.getString("modifiedtime"));
                if (rs.getString("reftype").equalsIgnoreCase("Sales Return")) {
                    caspayoutpojoobj.setSaleorderno(rs.getString("refno"));
                } else {
                    caspayoutpojoobj.setSaleorderno(rs.getString("refsalesorderno"));
                }
                caspayoutpojoobj.setCancelOtp(rs.getString("cancelotp"));//Coded by Balachander V on 3.8.2020 to send cash payout OTP to SAP
            }
            return caspayoutpojoobj;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            searchstmt = null;
            rs = null;
        }
    }

    /**
     * To get the Cash Pay Out POJO from the Statement
     */
    public CashPayoutPOJO getCashPayoutList(Connection conn, String searchstmt) {
        CashPayoutPOJO caspayoutpojoobj = new CashPayoutPOJO();
        Statement pstmt;
        ResultSet rs;
        try {
            caspayoutpojoobj = new CashPayoutPOJO();
            pstmt = (Statement) conn.createStatement();
            rs = pstmt.executeQuery(searchstmt);
            while (rs.next()) {
                caspayoutpojoobj.setStorecode(rs.getString("storecode"));
                caspayoutpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                caspayoutpojoobj.setCashpayoutno(rs.getString("cashpayoutno"));
                caspayoutpojoobj.setDateofpayout(rs.getInt("cashpayoutdate"));
                caspayoutpojoobj.setCustomercode(rs.getString("customercode"));
                caspayoutpojoobj.setRefcreditnotenumber(rs.getString("refcreditnoteno"));
                caspayoutpojoobj.setSapreferenceid(rs.getString("saprefernceid"));
                caspayoutpojoobj.setNetamount(rs.getDouble("netamount"));
                caspayoutpojoobj.setPaymentmode(rs.getString("paymentmode"));
                caspayoutpojoobj.setReasonforpayout(rs.getString("reasonforpayout"));
                caspayoutpojoobj.setCreatedby(rs.getString("createdby"));
                caspayoutpojoobj.setCreateddate(rs.getInt("createddate"));
                caspayoutpojoobj.setCreatedtime(rs.getString("createdtime"));
                caspayoutpojoobj.setModifiedby(rs.getString("modifiedby"));
                caspayoutpojoobj.setModifieddate(rs.getInt("modifieddate"));
                caspayoutpojoobj.setModifiedtime(rs.getString("modifiedtime"));
            }
            return caspayoutpojoobj;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /*
    public ArrayList getCashPayoutsearchtableList(Connection conn,String searchstmt) {
    try {
    Statement pstmt = (Statement) conn.createStatement();  
    ResultSet rs = pstmt.executeQuery(searchstmt);
    ArrayList caslist=new ArrayList();
    while (rs.next()) {
    CashPayoutPOJO caspayoutpojoobj = new CashPayoutPOJO();     
    caspayoutpojoobj.setCashpayoutno(rs.getString("cashpayoutno"));
    caspayoutpojoobj.setDateofpayout(rs.getInt("cashpayoutdate"));
    caspayoutpojoobj.setCustomercode(rs.getString("customercode"));
    caspayoutpojoobj.setRefcreditnotenumber(rs.getString("refcreditnoteno"));   
    caspayoutpojoobj.setCustomername(rs.getString("firstname"));
    caslist.add(caspayoutpojoobj);
    }
    return caslist;
    }
    catch(SQLException e)
    {
    return null;
    }
    }
     */
    /**
     * To get the Cheque No Details
     */
    public CashPayoutDetailsPOJO getChequenofromcashpayoutno(String cashpayoutno, CashPayoutDetailsPOJO casdetobj, Connection con) {

        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select chequeno,issuedate,bank,branchname,saprefernceid,netamount from tbl_cashpayout where cashpayoutno='" + cashpayoutno + "'");
            while (rs.next()) {
                casdetobj.setInstrumentno(rs.getString("chequeno"));
                casdetobj.setIssuedate(rs.getInt("issuedate"));
                casdetobj.setBank(rs.getString("bank"));
                casdetobj.setBranchname(rs.getString("branchname"));
                casdetobj.setSapreferenceid(rs.getString("saprefernceid"));
                casdetobj.setAmount(rs.getDouble("netamount"));
            }
            return casdetobj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * To get the Cash PayOut Details
     */
    public Vector getCashpayoutdetailsforThread(String cashpayoutno, Vector caslist, Connection con) {
        CashPayoutDetailsPOJO casdetobj;
        Statement pstmt;
        ResultSet rs;
        try {
            casdetobj = new CashPayoutDetailsPOJO();
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select chequeno,issuedate,bank,branchname,saprefernceid,netamount from tbl_cashpayout where cashpayoutno='" + cashpayoutno + "'");
            while (rs.next()) {
                casdetobj.setInstrumentno(rs.getString("chequeno"));
                casdetobj.setIssuedate(rs.getInt("issuedate"));
                casdetobj.setBank(rs.getString("bank"));
                casdetobj.setBranchname(rs.getString("branchname"));
                casdetobj.setSapreferenceid(rs.getString("saprefernceid"));
                casdetobj.setAmount(rs.getDouble("netamount"));
            }
            caslist.add(casdetobj);
            return caslist;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * To get the Credit Note Details
     */
    public String getCreditnotenofromcashpayoutno(String cashpayoutno, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select refcreditnoteno from tbl_cashpayout where cashpayoutno='" + cashpayoutno + "'");
            while (rs.next()) {
                return rs.getString("refcreditnoteno");
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * To get the Reasons for Cash PayOut
     */
    public String getReasonsforpayoutfrompayoutno(String cashpayoutno, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select reasonforpayout from tbl_cashpayout where cashpayoutno='" + cashpayoutno + "'");
            while (rs.next()) {
                return rs.getString("reasonforpayout");
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * To get the Sale Order No for the Credit Note
     */
    public String getSaleordernumber(Connection conn, String creditnoteno) {
        String referencetype = null;
        String refno = null;
        Statement pstmt;
        Statement pstmt1;
        ResultSet rs;
        ResultSet rs1;
        String refernceno = null;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery("select refno,reftype from tbl_creditnote where creditnoteno='" + creditnoteno + "'");
            while (rs.next()) {
                refno = rs.getString("refno");
                referencetype = rs.getString("reftype");
            }
            if (referencetype.equalsIgnoreCase("SalesOrder CANCEL")) {
                return refno;
            } else if (referencetype.equalsIgnoreCase("INVOICE CANCEL")) {
                pstmt1 = conn.createStatement();
                String searchstmt = "select refno from tbl_billingheader where invoiceno=(select invoiceno from tbl_billcancelheader where invoicecancellationno = '" + refno + "')";
                rs1 = pstmt.executeQuery(searchstmt);
                while (rs1.next()) {
                    refernceno = rs1.getString(1);
                    return refernceno;
                }
                return null;

            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            pstmt1 = null;
            pstmt = null;
            rs = null;
            rs1 = null;
        }
    }

    /**
     * To Update the SAP Refernces
     */
    public int updateSapReferenceIdInPosDocTable(Connection conn, String cashpayoutno, String sapreferenceid, String storeCode, int fiscalYear) {

        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("update tbl_cashpayout set saprefernceid =? where cashpayoutno=? and storecode = ? and fiscalyear = ?");
            pstmt.setString(1, sapreferenceid);
            pstmt.setString(2, cashpayoutno);
            pstmt.setString(3, storeCode);
            pstmt.setInt(4, fiscalYear);
            int res = pstmt.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        } finally {
            pstmt = null;
        }

    }

    /**
     * To Get the Cash Payout Object based on the CashPayout No
     */
    public CashPayoutPOJO getCashPayoutByCashPayoutNo(Connection con, String cashPayoutNo) {
        CashPayoutPOJO cashPayoutPOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select storecode,fiscalyear from dbo.tbl_cashpayout where cashpayoutno = ?");
            pstmt.setString(1, cashPayoutNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cashPayoutPOJO = new CashPayoutPOJO();
                cashPayoutPOJO.setStorecode(rs.getString("storecode"));
                cashPayoutPOJO.setFiscalyear(rs.getInt("fiscalyear"));
            }
        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return cashPayoutPOJO;
    }

    /*
    public String selectcashpayoutLastnoTable(Connection conn) throws SQLException{
    String result = null;
    String cashpayoutnumber="";
    SiteMasterDO smdo =new SiteMasterDO();
    String siteid=smdo.getSiteNumberLogicValue(conn);
    try {
    Statement pstmt = conn.createStatement();
    ResultSet rs = pstmt.executeQuery("select cashpayoutno from tbl_posdoclastnumbers");
    if (rs.next()) {
    if(Validations.isFieldNotEmpty(rs.getString(1)))
    {
    String casindblastadvcancelnopos=rs.getString("cashpayoutno");
    cashpayoutnumber="P";
    cashpayoutnumber=cashpayoutnumber+siteid;
    String cplastpart=casindblastadvcancelnopos.substring(cashpayoutnumber.length());
    String finalnumber=cashpayoutnumber+cplastpart;
    //  return rs.getString("advancereceiptno");
    return finalnumber;
    // return rs.getString(1);
    }
    else
    {
    String cashpayoutnum = "P";
    Statement pstmt1 = conn.createStatement();
    //  PlantDetails plantDetails = new PlantDetails(conn);
    //    if (plantDetails.getSiteCode() != null) {
    //customerCode=plantDetails.getSiteCode();
    //   advancereceipt=plantDetails.getSiteCode();
    //to be done will append site specific value
    //  customerCode = customerCode + "AA" ;
    //  cashpayoutnum = cashpayoutnum + "AA";
    cashpayoutnum = cashpayoutnum + siteid;
    for(int i= cashpayoutnum.length();i<10;i++){
    cashpayoutnum=cashpayoutnum+"0";
    }
    result=cashpayoutnum;
    pstmt1.executeUpdate("update tbl_posdoclastnumbers set cashpayoutno ='"+cashpayoutnum+"'");
    }
    }
    return result;
    } catch (Exception e) {
    throw new SQLException();
    }
    }
     */
 /* public int archiveAllCashPayoutTables(Connection con,int fiscalYear) throws SQLException{
    int recDeleted = 0;
    PreparedStatement pstmt;
    String query;
    try {
    query = "delete from tbl_cashpayout where fisaclyear= ?";
    pstmt = con.prepareStatement(query);
    int res = pstmt.executeUpdate();
    recDeleted = recDeleted + res;
    } catch (Exception e) {
    e.printStackTrace();
    recDeleted=0;
    throw new SQLException();
    } finally {
    query = null;
    }
    return recDeleted;
    }
     */
    /**
     * To Execute the Web Service Call for the Cash Payout Object
     */
    public String execute(DataObject dataObject, String updateType) {

        CashPayoutPOJO obj = null;
        Vector<CashPayoutDetailsPOJO> v = null;
        CashPayoutBean bean = null;
        if (dataObject instanceof CashPayoutBean) {
            bean = (CashPayoutBean) dataObject;
            obj = bean.getCashpayoutpojo();
            v = (Vector<CashPayoutDetailsPOJO>) bean.getCashpayoutdetailspojo();

            try { // Call Web Service Operation
                in.co.titan.payoutcreditmemo.MIOBASYNPayOutService service = new in.co.titan.payoutcreditmemo.MIOBASYNPayOutService();
//                in.co.titan.payoutcreditmemo.MIOBASYNPayOut port = service.getMIOBASYNPayOutPort();
                in.co.titan.payoutcreditmemo.MIOBASYNPayOut port = service.getHTTPSPort();
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_PayOutCreditMemo&version=3.0&Sender.Service=x&Interface=x%5Ex");
                // TODO initialize WS operation arguments here
                in.co.titan.payoutcreditmemo.DTPayOut mtPayOut = new in.co.titan.payoutcreditmemo.DTPayOut();

                try {
                    if (obj.getDateofpayout() != 0) {
                        java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(obj.getDateofpayout());
                        if (followDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(followDate);
                            if (xmlDate != null) {
                                mtPayOut.setDate(xmlDate);
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println("instrument date not Set" + e);
                }
                /**
                 * ********Audit fields*********
                 */
                mtPayOut.setCreatedBy(obj.getCreatedby());
                try {
                    java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(obj.getCreateddate());
                    Calendar createdTime = ConvertDate.getSqlTimeFromString(obj.getCreatedtime());
                    if (createdDate != null) {
                        XMLCalendar xmlDate = new XMLCalendar(createdDate);
                        if (createdTime != null) {
                            xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                            if (xmlDate != null) {
                                mtPayOut.setCreatedTime(xmlDate);
                                mtPayOut.setModifiedTime(xmlDate);
                            }
                        }
                        if (xmlDate != null) {
                            mtPayOut.setCreatedDate(xmlDate);
                            mtPayOut.setModifiedDate(xmlDate);
                        }

                    }
                } catch (Exception e) {

                }

                mtPayOut.setModifiedBy(obj.getModifiedby());

                /**
                 * *******End of Audit fields****
                 */
                if (obj.getPaymentmode().equalsIgnoreCase("RO")) {
                    Iterator iter = v.iterator();

                    CashPayoutDetailsPOJO casdetpojo;

                    while (iter.hasNext()) {
                        casdetpojo = (CashPayoutDetailsPOJO) iter.next();

                        try {
                            if (casdetpojo.getIssuedate() != 0) {
                                java.util.Date issueDate = ConvertDate.getUtilDateFromNumericDate(casdetpojo.getIssuedate());
                                if (issueDate != null) {
                                    XMLCalendar xmlDate = new XMLCalendar(issueDate);
                                    if (xmlDate != null) {
                                        mtPayOut.setIssueDate(xmlDate);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            System.out.println("issuedate date not Set" + e);
                        }
                        mtPayOut.setChequeNO(casdetpojo.getInstrumentno());
                        mtPayOut.setBank(casdetpojo.getBank());
                        mtPayOut.setBranchName(casdetpojo.getBranchname());
                        mtPayOut.setSAPReferenceID(casdetpojo.getSapreferenceid());

                    }

                }
                mtPayOut.setCustomerCode(obj.getCustomercode());

                mtPayOut.setAmount(new BigDecimal(String.valueOf(obj.getNetamount())));
                if (obj.getPaymentmode().equalsIgnoreCase("RO")) {
                    mtPayOut.setPayMode(obj.getPaymentmode());
                } else {
                    mtPayOut.setPayMode("CAS");
                }

                mtPayOut.setReasRefund(obj.getReasonforPayout());
                mtPayOut.setStoreCode(obj.getStorecode());
                mtPayOut.setSITESEARCH(obj.getStorecode());
                mtPayOut.setSaleOrderNo(obj.getSaleorderno().toString());
                if (obj.getPaymentmode().equalsIgnoreCase("RO")) {
                    mtPayOut.setFYear(new BigInteger(String.valueOf(obj.getFiscalyear())));

                }
                if (Validations.isFieldNotEmpty(obj.getCancelOtp())) {//Coded by Balachander V on 3.8.2020 to send cash payout OTP to SAP
                    mtPayOut.setCancelledOTP(obj.getCancelOtp());
                }
                PayOutItem itemtable = new PayOutItem();
                itemtable.setSLNO("1");

                itemtable.setCrNoteNO(obj.getRefcreditnotenumber());
                itemtable.setAmount(new BigDecimal(String.valueOf(obj.getNetamount())));
                itemtable.setPosCashPayOutNo(obj.getCashpayoutno());
                mtPayOut.getPayOutItem().add(itemtable);

                // TODO process result here
                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("CashPayout Storecode " + mtPayOut.getStoreCode());
                if (mtPayOut.getStoreCode() != null && mtPayOut.getStoreCode().trim().length() > 0) {
                    port.miOBASYNPayOut(mtPayOut);
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
                /*port.miOBASYNPayOut(mtPayOut);
            Map responseContext = ((BindingProvider) port).getResponseContext();
            Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
            if (responseCode.intValue() == 200) {
            return "true";
            } else {
            return "false";
            }  
                 */
            } catch (Exception ex) {
                ex.printStackTrace();
                return "false";
                // TODO handle custom exceptions here
            }

        } else {
            return "false";
        }

    }
}

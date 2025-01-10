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
 * This class file is used as a Advance Receipt Data Object for Advance and Payment Detaisl
 * This is used for transaction of Advances in different scenario from and to the database
 * This class also implements the WebService for Advance Receipt
 * 
 */
package ISRetail.paymentdetails;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.salesorder.SaleOrderMasterBean;
import ISRetail.salesorder.SalesOrderDetailsDO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.services.ServicesDetailDO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.advancereceipt.DTAdvanceReceipt.ItemTable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Statement;
import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class AdvanceReceiptDO implements Webservice {

    /*
    public String selectAdvanceReceiptLastnoTable(Connection conn) throws SQLException{
    SiteMasterDO smdo = new SiteMasterDO();
    String siteid = smdo.getSiteNumberLogicValue(conn);
    String result = "";
    String advancenumber = "";
    try {
    Statement pstmt = conn.createStatement();
    ResultSet rs = pstmt.executeQuery("select advancereceiptno from tbl_posdoclastnumbers");
    if (rs.next()) {
    if (!rs.getString(1).equalsIgnoreCase("") && rs.getString(1).length() > 0) {
    String advindblastnopos = rs.getString("advancereceiptno");
    advancenumber = "A";
    advancenumber = advancenumber + siteid;
    String adlastpart = advindblastnopos.substring(advancenumber.length());
    String finalnumber = advancenumber + adlastpart;
    //  return rs.getString("advancereceiptno");
    return finalnumber;
    } else {
    String advancereceipt = "A";
    Statement pstmt1 = conn.createStatement();
    advancereceipt = advancereceipt + siteid;
    //  advancereceipt = advancereceipt + "AA";
    for (int i = advancereceipt.length(); i < 10; i++) {
    advancereceipt = advancereceipt + "0";
    }
    result = advancereceipt;
    pstmt1.executeUpdate("update tbl_posdoclastnumbers set advancereceiptno ='" + advancereceipt + "'");
    }
    }
    return result;
    } catch (Exception e) {
    e.printStackTrace();
    throw new SQLException();
    }
    }
     */

 /*  public int updateLastAdvanceRecptNoInPosDocTable(String advancerecptno, Connection conn) throws SQLException{
    try {
    PreparedStatement pstmt = conn.prepareStatement("update tbl_posdoclastnumbers set advancereceiptno ='" + advancerecptno + "'");
    int res = pstmt.executeUpdate();
    return res;
    } catch (Exception e) {
    throw new SQLException();
    }
    }
     */

 /*  public String selectAdvanceReceiptCancellationLastnoTable(Connection conn) {
    String result = "";
    String advancecancelnumber = "";
    SiteMasterDO smdo = new SiteMasterDO();
    String siteid = smdo.getSiteNumberLogicValue(conn);
    try {
    Statement pstmt = conn.createStatement();
    ResultSet rs = pstmt.executeQuery("select advancereceiptcancellationno from tbl_posdoclastnumbers");
    if (rs.next()) {
    if (Validations.isFieldNotEmpty(rs.getString("advancereceiptcancellationno"))) {
    String advindblastadvcancelnopos = rs.getString("advancereceiptcancellationno");
    advancecancelnumber = "D";
    advancecancelnumber = advancecancelnumber + siteid;
    String adlastpart = advindblastadvcancelnopos.substring(advancecancelnumber.length());
    String finalnumber = advancecancelnumber + adlastpart;
    //  return rs.getString("advancereceiptno");
    return finalnumber;
    //return rs.getString("advancereceiptcancellationno");
    } else {
    String advancereceipt = "D";
    Statement pstmt1 = conn.createStatement();
    //  PlantDetails plantDetails = new PlantDetails(conn);
    //    if (plantDetails.getSiteCode() != null) {
    //customerCode=plantDetails.getSiteCode();
    //   advancereceipt=plantDetails.getSiteCode();
    //to be done will append site specific value
    //  customerCode = customerCode + "AA" ;
    advancereceipt = advancereceipt + siteid;
    //  advancereceipt = advancereceipt + "AA";
    for (int i = advancereceipt.length(); i < 10; i++) {
    advancereceipt = advancereceipt + "0";
    }
    result = advancereceipt;
    pstmt1.executeUpdate("update tbl_posdoclastnumbers set advancereceiptcancellationno ='" + advancereceipt + "'");
    }
    }
    return result;
    } catch (Exception e) {
    e.printStackTrace();
    return result;
    }
    }
     */

 /*
    public int updateLastAdvanceRecptCancellationNoInPosDocTable(String advancerecptno, Connection conn) {
    try {
    System.out.println("cancelno" + advancerecptno);
    PreparedStatement pstmt = conn.prepareStatement("update tbl_posdoclastnumbers set advancereceiptcancellationno ='" + advancerecptno + "'");
    int res = pstmt.executeUpdate();
    return res;
    } catch (Exception e) {
    return 0;
    }
    }
     */
    public double GetTotalforadvagainstso(String sonumber, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            double total = 0;
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select a.documentno,a.refno,b.amount,b.documentno from tbl_advancereceiptheader as a,tbl_paymentdetails as b where a.refno='" + sonumber + "' and a.cancelledstatus like '%N%' and b.modeofpayment!='CHQ' and a.documentno=b.documentno");
            while (rs.next()) {
                total = total + rs.getInt("amount");
            }
            return total;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public double GetTotalforadvagainstincludechqso(String sonumber, Connection con, String advno, int advdate, String advtime) {
        Statement pstmt;
        String strquery;
        ResultSet rs;
        try {
            double total = 0;
            pstmt = (Statement) con.createStatement();
            //String strquery="select a.documentno,a.refno,a.amount,b.documentno,a.documentdate from tbl_advancereceiptheader as a,tbl_paymentdetails as b where a.refno='" + sonumber + "' and a.cancelledstatus like '%N%' and a.documentno!='"+advno+"' and a.documentno=b.documentno";
            //String strquery="select a.documentno,a.refno,b.amount,b.documentno,a.documentdate from dbo.tbl_advancereceiptheader as a, tbl_paymentdetails as b where a.documentno <> '"+advno+"' and a.refno = '"+sonumber+"' and a.createddate <="+advdate+" and a.createdtime <"+advtime+" and a.cancelledstatus like '%N%' and a.documentno=b.documentno";
            strquery = "select a.documentno,a.refno,b.amount,b.documentno,a.documentdate from dbo.tbl_advancereceiptheader as a, tbl_paymentdetails as b where a.documentno <> '" + advno + "' and a.documentno < '" + advno + "' and a.refno = 'TIND001808' and a.createddate <=20081112  and a.cancelledstatus like '%N%' and a.documentno=b.documentno";
            rs = pstmt.executeQuery(strquery);
            while (rs.next()) {
                total = total + rs.getInt("amount");
            }
            return total;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            strquery = null;
            rs = null;
        }

    }

    public int Updateadvsapreferenceid(Connection con, String advancereceiptno, String sapreferenceid, String storecode, int fiscalyear) {
        Statement pstmtup;
        String queryString;
        try {
            pstmtup = con.createStatement();
            queryString = "update tbl_advancereceiptheader set sapreferenceid='" + sapreferenceid + "' where documentno='" + advancereceiptno + "' and storecode = '" + storecode + "' and fiscalyear = " + fiscalyear;
            int rs = pstmtup.executeUpdate(queryString);
            if (rs > 0) {
                return rs;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmtup = null;
            queryString = null;
        }
    }

    /*  public int Updatesalordersaletypefromadv(Connection con,String advancereceiptno,String sapreferenceid)
    {
    try {
    Statement pstmtup = (Statement) con.createStatement();   
    int rs = pstmtup.executeUpdate("update salesorderheader1 set sapreferenceid='"+sapreferenceid+"' where documentno='"+advancereceiptno+"'");
    if(rs>0)
    {
    return rs;
    }
    else{return 0;}
    }catch(Exception e){
    e.printStackTrace();
    return 0;
    }
    }
     */
    public int getdocdatefromdocno(String docno, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            int docdate1 = 0;
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select documentdate from tbl_advancereceiptheader where documentno='" + docno + "'");
            while (rs.next()) {
                docdate1 = rs.getInt("documentdate");
            }
            return docdate1;
        } catch (SQLException ex) {
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    public String getadvwithsalesorderno(String salesordernumber, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            String advno = "";
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select documentno from tbl_advancereceiptheader where refno='" + salesordernumber + "'");
            while (rs.next()) {
                advno = rs.getString("documentno");
            }
            return advno;
        } catch (SQLException ex) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    public double gettotalfromdocno(String docno, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            // Date docdate1 = null;
            double total = 0;
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select totalamount from tbl_advancereceiptheader where documentno='" + docno + "'");
            while (rs.next()) {
                //  docdate1=rs.getDate("docdate");
                total = rs.getDouble("totalamount");
            }
            return total;
        } catch (SQLException ex) {
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    /*   public Vector getAdvanceReceiptList1(Connection conn,Vector advlist1,String searchstmt) {
    try {
    AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
    Statement pstmt = (Statement) conn.createStatement();
    //String searchstatement = "select * from customermasternew where  " + wherestatement;
    ResultSet rs = pstmt.executeQuery(searchstmt);
    ArrayList<AdvanceReceiptPOJO> advancereclist = new ArrayList<AdvanceReceiptPOJO>();
    while (rs.next()) {
    advancerecpojoobj.setReceiptno(rs.getString("receiptno"));
    advancerecpojoobj.setDocdate(rs.getDate("docdate"));
    advancerecpojoobj.setReceiptAmt(rs.getDouble("receiptamt"));
    advlist1.add(advancerecpojoobj);
    }
    return advlist1;
    }
    catch(SQLException e)
    {
    return null;
    }
    }*/
    public ArrayList getSearchlist(Connection conn, String sb) {
        Statement pstmt;
        ResultSet rs;
        try {

            pstmt = (Statement) conn.createStatement();
            ArrayList searchresult = new ArrayList();
            rs = pstmt.executeQuery(sb);
            while (rs.next()) {
                AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
                advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                advancerecpojoobj.setDateofpayment(rs.getInt("documentdate"));
                advancerecpojoobj.setAmount(rs.getDouble("totalamount"));
                advancerecpojoobj.setCancelledstatus(rs.getString("cancelledstatus"));
                searchresult.add(advancerecpojoobj);
            }
            return searchresult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public ArrayList getSearchlist1(Connection conn, String sb) {
        ResultSet rs;
        Statement pstmt;
        try {

            pstmt = (Statement) conn.createStatement();
            ArrayList searchresult = new ArrayList();
            rs = pstmt.executeQuery(sb);
            while (rs.next()) {
                AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
                advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                advancerecpojoobj.setRefno(rs.getString("serviceorderno"));
                advancerecpojoobj.setDateofpayment(rs.getInt("documentdate"));
                advancerecpojoobj.setAmount(rs.getDouble("totalamount"));
                //advancerecpojoobj.setCancelledstatus(rs.getString("cancelledstatus"));
                searchresult.add(advancerecpojoobj);
            }
            return searchresult;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public AdvanceReceiptPOJO getAdvanceReceiptCancelList(Connection conn, String advancereceiptno) {

        ResultSet rs;
        ResultSet rs1;
        Statement pstmt;
        Statement pstmt1;
        try {
            AdvanceReceiptPOJO advpojo = new AdvanceReceiptPOJO();
            pstmt = (Statement) conn.createStatement();
            pstmt1 = (Statement) conn.createStatement();
            //ResultSet rs = pstmt.executeQuery("select * from tbl_advancereceiptheader where documentno='"+advancereceiptno+"' ");
            rs = pstmt.executeQuery("select * from dbo.tbl_advancereceiptheader where documentno =(select refno from dbo.tbl_advancereceiptheader where documentno='" + advancereceiptno + "')");
            while (rs.next()) {
                advpojo.setSapreferenceid(rs.getString("sapreferenceid"));
                advpojo.setStorecode(rs.getString("storecode"));
                advpojo.setFiscalyear(rs.getInt("fiscalyear"));
                advpojo.setCreatedby(rs.getString("createdby"));
                advpojo.setCreateddate(rs.getInt("createddate"));
                advpojo.setCreatedtime(rs.getString("createdtime"));
                advpojo.setModifiedby(rs.getString("modifiedby"));
                advpojo.setModifieddate(rs.getInt("modifieddate"));
                advpojo.setModifiedtime(rs.getString("modifiedtime"));
                advpojo.setDocumentno(rs.getString("documentno"));
                advpojo.setDatasheetno(advancereceiptno);
                advpojo.setCancelOtp(rs.getString("cancelotp"));
                String docno = rs.getString("documentno");
                //  advpojo.setReasonforcancellation(rs.getString("reasonforcancellation"));   
                //  ResultSet rs1 = pstmt1.executeQuery("select reasonforcancellation from tbl_advancereceiptheader where documentno='" + advancereceiptno + "'");
                //  rs1=pstmt1.executeQuery("select x.reasonforcancellation,tbl_salesorderheader.releasestatus releasestatus,tbl_salesorderheader.saleorderno saleorderno from (select a.reasonforcancellation reasonforcancellation,b.refno refno from dbo.tbl_advancereceiptheader a left outer join tbl_advancereceiptheader b on a.refno = b.documentno where a.documentno= '"+advancereceiptno+"' ) x left outer join tbl_salesorderheader on tbl_salesorderheader.saleorderno = x.refno");
                rs1 = pstmt1.executeQuery("select x.reasonforcancellation,tbl_salesorderheader.releasestatus releasestatus,tbl_salesorderheader.saleorderno saleorderno from (select a.reasonforcancellation reasonforcancellation,b.refno refno from dbo.tbl_advancereceiptheader a  left outer join tbl_advancereceiptheader b  on a.refno = b.documentno where a.documentno= '" + advancereceiptno + "' )  x inner join tbl_salesorderheader on tbl_salesorderheader.saleorderno = x.refno union select x.reasonforcancellation,'',tbl_serviceorderheader.serviceorderno saleorderno from (select a.reasonforcancellation reasonforcancellation,b.refno refno from dbo.tbl_advancereceiptheader a left outer join tbl_advancereceiptheader b on a.refno = b.documentno where a.documentno= '" + advancereceiptno + "' ) x inner join tbl_serviceorderheader on tbl_serviceorderheader.serviceorderno = x.refno");
                while (rs1.next()) {
                    advpojo.setReasonforcancellation(rs1.getString("reasonforcancellation"));
                    advpojo.setReleasestatus(rs1.getString("releasestatus"));
                    advpojo.setRefno(rs1.getString("saleorderno"));
                }
                return advpojo;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt1 = null;
            rs1 = null;
            pstmt = null;
            rs = null;
        }
        return null;
    }

    public boolean checkSapRefIdForAdvanceReceiptNo(Connection con, String advanceReversalNo) {
        boolean status = false;
        Statement pstmt;
        ResultSet rs;
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select b.sapreferenceid  from dbo.tbl_advancereceiptheader a left outer join dbo.tbl_advancereceiptheader b on b.documentno = a.refno where a.documentno = '" + advanceReversalNo + "'";
            rs = pstmt.executeQuery(searchstatement);
            String sapRefID = null;
            if (rs.next()) {
                sapRefID = rs.getString("sapreferenceid");
                if (Validations.isFieldNotEmpty(sapRefID)) {
                    status = true;
                }
            }
        } catch (SQLException ex) {

        } finally {
            pstmt = null;
            rs = null;
            searchstatement = null;
        }
        return status;
    }

    public String getSapReferenceID(Connection con, String advancereceiptno) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select sapreferenceid from tbl_advancereceiptheader where documentno='" + advancereceiptno + "'");
            while (rs.next()) {
                return rs.getString("sapreferenceid");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * *******************getting status of adv before cancelling advance
     * receipt********************
     */
    public String getAdvanceStatusbeforecancel(Connection con, String advno) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select cancelledstatus from tbl_advancereceiptheader where documentno='" + advno + "'");
            while (rs.next()) {
                return rs.getString("cancelledstatus");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    /**
     * *******************End of getting status of adv before cancelling
     * advance receipt********************
     */
    public AdvanceReceiptPOJO getAdvanceReceiptList(Connection conn, String searchstmt) {
        Statement pstmt;
        ResultSet rs;
        try {

            AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
            pstmt = (Statement) conn.createStatement();
            rs = pstmt.executeQuery(searchstmt);
            ArrayList<AdvanceReceiptPOJO> advancereclist = new ArrayList<AdvanceReceiptPOJO>();
            while (rs.next()) {
                advancerecpojoobj.setStorecode(rs.getString("storecode"));
                advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                advancerecpojoobj.setDateofpayment(rs.getInt("documentdate"));
                advancerecpojoobj.setRefno(rs.getString("refno"));
                advancerecpojoobj.setRefdate(rs.getInt("refdate"));
                advancerecpojoobj.setAmount(rs.getDouble("totalamount"));
                advancerecpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                advancerecpojoobj.setSapreferenceid(rs.getString("sapreferenceid"));
                advancerecpojoobj.setCancelledstatus(rs.getString("cancelledstatus"));
                advancerecpojoobj.setCreditnotenopos(rs.getString("creditnotenopos"));
                advancerecpojoobj.setCreatedby(rs.getString("createdby"));
                advancerecpojoobj.setCreateddate(rs.getInt("createddate"));
                advancerecpojoobj.setCreatedtime(rs.getString("createdtime"));
                advancerecpojoobj.setModifiedby(rs.getString("modifiedby"));
                advancerecpojoobj.setModifieddate(rs.getInt("modifieddate"));
                advancerecpojoobj.setModifiedtime(rs.getString("modifiedtime"));
            }
            return advancerecpojoobj;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * *******************End of getting status of adv before cancelling
     * advance receipt********************
     */
    public ArrayList<AdvanceReceiptPOJO> getAdvanceCancelBasedOnSaleOrderNo(Connection conn, String saleOrderNo) {
        Statement pstmt;
        ResultSet rs;
        int i = 0;
        try {

            AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
            pstmt = (Statement) conn.createStatement();
            //  rs = pstmt.executeQuery("select * from tbl_advancereceiptheader where refno = '"+saleOrderNo+"'"); //  and reasonforcancellation =''
            //code changed by sharanya to avoid sending Advance Receipt entry if advance is cancelled before Sale order cancellation
            // rs = pstmt.executeQuery("select * from tbl_advancereceiptheader where refno = '"+saleOrderNo+"' and reasonforcancellation =''"); //  and reasonforcancellation =''
            //code changed on Jun 14 2011 to avoid sending Advance Receipt entry for the scenario were invoice cancelled(Automatically Advance is also cancelled) and then Sale order is cancelled and to send Advance during old Sale Order cancellation
            rs = pstmt.executeQuery("select * from tbl_advancereceiptheader where refno = '" + saleOrderNo + "' and reasonforcancellation ='' and (len(creditnotenopos)=10 or len(creditnotenopos)=0) ");
            ArrayList<AdvanceReceiptPOJO> advancereclist = null;
            while (rs.next()) {
                if (i == 0) {
                    advancereclist = new ArrayList<AdvanceReceiptPOJO>();
                    i = 1;
                }
                advancerecpojoobj = new AdvanceReceiptPOJO();
                advancerecpojoobj.setStorecode(rs.getString("storecode"));
                advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                advancerecpojoobj.setDateofpayment(rs.getInt("documentdate"));
                advancerecpojoobj.setRefno(rs.getString("refno"));
                advancerecpojoobj.setRefdate(rs.getInt("refdate"));
                advancerecpojoobj.setAmount(rs.getDouble("totalamount"));
                advancerecpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                advancerecpojoobj.setSapreferenceid(rs.getString("sapreferenceid"));
                advancerecpojoobj.setCancelledstatus(rs.getString("cancelledstatus"));
                advancerecpojoobj.setCreditnotenopos(rs.getString("creditnotenopos"));
                advancerecpojoobj.setCreatedby(rs.getString("createdby"));
                advancerecpojoobj.setCreateddate(rs.getInt("createddate"));
                advancerecpojoobj.setCreatedtime(rs.getString("createdtime"));
                advancerecpojoobj.setModifiedby(rs.getString("modifiedby"));
                advancerecpojoobj.setModifieddate(rs.getInt("modifieddate"));
                advancerecpojoobj.setModifiedtime(rs.getString("modifiedtime"));
                advancereclist.add(advancerecpojoobj);
            }
            return advancereclist;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    //******************************* Created By vandana
    public AdvanceReceiptPOJO getAdvanceReceiptByAdvanceReceiptNo(Connection conn, String advanceRaceiptNo) {
        Statement pstmt;
        ResultSet rs;
        try {
            AdvanceReceiptPOJO advancerecpojoobj = null;
            pstmt = (Statement) conn.createStatement();
            rs = pstmt.executeQuery("select storecode,fiscalyear from tbl_paymentdetails where documentno = ' " + advanceRaceiptNo + "'");
            while (rs.next()) {
                advancerecpojoobj = new AdvanceReceiptPOJO();
                advancerecpojoobj.setStorecode(rs.getString("storecode"));
                advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
            }
            return advancerecpojoobj;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * *******called in master details for force release status************
     */
    public AdvanceReceiptPOJO getAdvanceReceiptListforthread(Connection conn, String searchstmt) {
        Statement pstmt;
        ResultSet rs;
        String division = "";
        try {

            AdvanceReceiptPOJO advancerecpojoobj = new AdvanceReceiptPOJO();
            pstmt = (Statement) conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstmt);
            ArrayList<AdvanceReceiptPOJO> advancereclist = new ArrayList<AdvanceReceiptPOJO>();
            while (rs.next()) {
                advancerecpojoobj.setCustomercode(rs.getString("customercode"));
                advancerecpojoobj.setStorecode(rs.getString("storecode"));
                advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                advancerecpojoobj.setDateofpayment(rs.getInt("documentdate"));
                advancerecpojoobj.setRefno(rs.getString("refno"));
                advancerecpojoobj.setRefdate(rs.getInt("refdate"));
                advancerecpojoobj.setAmount(rs.getDouble("totalamount"));
                advancerecpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                advancerecpojoobj.setSapreferenceid(rs.getString("sapreferenceid"));
                advancerecpojoobj.setCancelledstatus(rs.getString("cancelledstatus"));
                advancerecpojoobj.setCreditnotenopos(rs.getString("creditnotenopos"));
                advancerecpojoobj.setCreatedby(rs.getString("createdby"));
                advancerecpojoobj.setCreateddate(rs.getInt("createddate"));
                advancerecpojoobj.setCreatedtime(rs.getString("createdtime"));
                advancerecpojoobj.setModifiedby(rs.getString("modifiedby"));
                advancerecpojoobj.setModifieddate(rs.getInt("modifieddate"));
                advancerecpojoobj.setModifiedtime(rs.getString("modifiedtime"));
                advancerecpojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                advancerecpojoobj.setReleasestatus(rs.getString("releasestatus"));
                advancerecpojoobj.setSaletype(rs.getString("saletype"));
                advancerecpojoobj.setReleasedate(rs.getInt("releasedate"));
                division = new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(conn, rs.getString("refno"));
                if (division != null && division.length() > 0) {
                    advancerecpojoobj.setDivision(division);
                } else {
                    division = new ServicesDetailDO().getArticleDivisionByServiceOrderNo(conn, rs.getString("refno"));
                    advancerecpojoobj.setDivision(division);
                }

            }
            return advancerecpojoobj;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public int archiveAllAdvReceiptTables(Connection con, int fiscalYear) throws SQLException {
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        try {

            query = "delete from tbl_advancereceiptheader where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            int res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
        } catch (Exception e) {
            e.printStackTrace();
            recDeleted = 0;
            throw new SQLException();
        } finally {
            pstmt = null;
            query = null;
        }
        return recDeleted;
    }

    /**
     * *******End of calling in master details for force release
     * status************
     */
    public String execute(DataObject dataObject, String updateType) {

        AdvanceReceiptPOJO obj = null;
        Vector<AdvanceReceiptDetailsPOJO> v = null;
        AdvanceReceiptBean bean = null;
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
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceipt&version=3.0&Sender.Service=X&Interface=Z%5EY");

                    // TODO initialize WS operation arguments here
                    in.co.titan.advancereceipt.DTAdvanceReceipt mtAdvanceReceipt = new in.co.titan.advancereceipt.DTAdvanceReceipt();
                    // TODO process result here
                    if (salesOrderHeaderPOJO != null) { //Null value check added on Jan 19th 2010
                        if (salesOrderHeaderPOJO.getSaleorderno() != null) { //Null value check added on Jan 19th 2010  
                            mtAdvanceReceipt.setSaleOrderNO(salesOrderHeaderPOJO.getSaleorderno());
                        }
                    }
                    //  mtAdvanceReceipt.setCustomerNO("TIL-DR-CUS");
                    /**
                     * ***********
                     */
                    String division = null;
                    if (salesOrderHeaderPOJO.getDivision() != null && salesOrderHeaderPOJO.getDivision().length() > 0) {
                        mtAdvanceReceipt.setDivision(salesOrderHeaderPOJO.getDivision());
                    } else {
                        MsdeConnection msdeConnection = new MsdeConnection();
                        Connection con = msdeConnection.createConnection();
                        try {

                            division = new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(con, salesOrderHeaderPOJO.getSaleorderno());
                            if (division != null) {
                                mtAdvanceReceipt.setDivision(division);
                            }
                            //else{
                            //  division =new ServicesDetailDO().getArticleDivisionByServiceOrderNo(con,  salesOrderHeaderPOJO.getSaleorderno());
                            // mtAdvanceReceipt.setDivision(division);
                            // }
                        } catch (Exception e) {

                        } finally {
                            if (con != null) {
                                con.close();
                            }
                            con = null;
                            msdeConnection = null;
                        }
                    }

                    if (salesOrderHeaderPOJO != null) {
                        if (salesOrderHeaderPOJO.getCustomercode() != null) {
                            mtAdvanceReceipt.setCustomerNO(salesOrderHeaderPOJO.getCustomercode());
                        }

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
                                    }
                                }
                                if (xmlDate != null) {
                                    mtAdvanceReceipt.setCreatedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            java.util.Date modDate = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getModifiedDate());
                            Calendar modTime = ConvertDate.getSqlTimeFromString(salesOrderHeaderPOJO.getModifiedTime());
                            if (modDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(modDate);
                                if (modTime != null) {
                                    xmlDate.setTime(modTime.get(Calendar.HOUR_OF_DAY), modTime.get(Calendar.MINUTE), modTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtAdvanceReceipt.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtAdvanceReceipt.setModifiedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mtAdvanceReceipt.setModifiedBy(salesOrderHeaderPOJO.getModifiedBy());
                        mtAdvanceReceipt.setFlagForceRelease("Y");
                        mtAdvanceReceipt.setSITESEARCH(salesOrderHeaderPOJO.getStoreCode());

                        if (salesOrderHeaderPOJO.getSaletype().equalsIgnoreCase("Credit")) {
                            if (Validations.isFieldNotEmpty(salesOrderHeaderPOJO.getEd())) {
                                mtAdvanceReceipt.setCreditSaleReference(salesOrderHeaderPOJO.getEd());
                            }
                        }
                        mtAdvanceReceipt.setSaleType(salesOrderHeaderPOJO.getSaletype().toUpperCase());
                        if (salesOrderHeaderPOJO.getReleaseStatus().equalsIgnoreCase("FORCERELEASED")) {
                            mtAdvanceReceipt.setReleaseStatus("FORCERELEASED");
                            try {
                                if (salesOrderHeaderPOJO.getReleasedate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(salesOrderHeaderPOJO.getReleasedate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            mtAdvanceReceipt.setReleaseDate(xmlDate);

                                        }

                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("release date not Set" + e);
                            }
                        }

                        if (Validations.isFieldNotEmpty(salesOrderHeaderPOJO.getCreditsalereference())) {
                            mtAdvanceReceipt.setInstiID(salesOrderHeaderPOJO.getCreditsalereference());
                        }
                    }
                    if (Validations.isFieldNotEmpty(salesOrderHeaderPOJO.getCancelOtp())) {
                        //    mtAdvanceReceipt.setc
                    }
                    ItemTable itemtable = new ItemTable();
                    itemtable.setSiteID(salesOrderHeaderPOJO.getStoreCode());

                    mtAdvanceReceipt.getItemTable().add(itemtable);

                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    //Code added by ravithota on 04 Jan 2013 to avoid sending payload if division is not available
                    System.out.println("Advance Receipt Force Release " + mtAdvanceReceipt.getSITESEARCH());
                    if (mtAdvanceReceipt.getSITESEARCH() != null && mtAdvanceReceipt.getSITESEARCH().trim().length() > 0 && mtAdvanceReceipt.getDivision() != null && mtAdvanceReceipt.getDivision().trim().length() > 0) {
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
                System.out.println(mtAdvanceReceipt.getItemTable().get(1).getAmount() + "+++++" + responseCode);
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
            if (dataObject instanceof AdvanceReceiptBean) {

                bean = (AdvanceReceiptBean) dataObject;
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
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceipt&version=3.0&Sender.Service=X&Interface=Z%5EY");

                // TODO initialize WS operation arguments here
                in.co.titan.advancereceipt.DTAdvanceReceipt mtAdvanceReceipt = new in.co.titan.advancereceipt.DTAdvanceReceipt();
                // TODO process result here

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
                    mtAdvanceReceipt.setSITESEARCH(obj.getStorecode());

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
                        if (obj.getReleasestatus().equalsIgnoreCase("FORCERELEASED") || obj.getReleasestatus().equalsIgnoreCase("RELEASE")) {
                            try {
                                if (obj.getReleasedate() != 0) {
                                    java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(obj.getReleasedate());
                                    if (followDate != null) {
                                        XMLCalendar xmlDate = new XMLCalendar(followDate);
                                        if (xmlDate != null) {
                                            mtAdvanceReceipt.setReleaseDate(xmlDate);
                                        }

                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("release date not Set" + e);
                            }

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                mtAdvanceReceipt.setSaleOrderNO(obj.getRefno());
                String division = null;
                try {

                    // String division = new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(con,  obj.getRefno());
                    //  mtAdvanceReceipt.setDivision(division);
                    if (obj.getDivision() != null && obj.getDivision().length() > 0) {
                        mtAdvanceReceipt.setDivision(obj.getDivision());
                    } else {
                        MsdeConnection msdeConnection = new MsdeConnection();
                        Connection con = msdeConnection.createConnection();
                        try {
                            division = new SalesOrderDetailsDO().getArticleDivisionBySaleOrderNo(con, obj.getRefno());
                            if (division != null) {
                                mtAdvanceReceipt.setDivision(division);
                            } else {
                                division = new ServicesDetailDO().getArticleDivisionByServiceOrderNo(con, obj.getRefno());
                                mtAdvanceReceipt.setDivision(division);
                            }
                        } finally {
                            if (con != null) {
                                con.close();
                            }
                            msdeConnection = null;
                            con = null;
                        }
                    }

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
                            }
                        }
                        if (xmlDate != null) {
                            mtAdvanceReceipt.setCreatedDate(xmlDate);
                            mtAdvanceReceipt.setModifiedDate(xmlDate);
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
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("CAS") || advpojo.getModeofpayment().equalsIgnoreCase("INS") || advpojo.getModeofpayment().equalsIgnoreCase("HCS")) {

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
                        if(Validations.isFieldNotEmpty(advpojo.getValidationtext())){//added by charan for titan co brand
                            itemtable.setCNText(advpojo.getValidationtext());
                        }
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*cardtype*/ itemtable.setBrType(String.valueOf(advpojo.getCardtype()));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                        if(Validations.isFieldNotEmpty(advpojo.getAuthorizedperson())){
                        itemtable.setLPAppNos(advpojo.getAuthorizedperson());}
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("BAJ")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        try {
                            itemtable.setSiteID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("PTM")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        try {
                            itemtable.setSiteID(advpojo.getStorecode());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("HCC")) {
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
                        itemtable.setAuthCode(advpojo.getAuthorizationcode());//Added by Balachander V on 16.2.2022 suggested by Naveen to send the Tenure period in Authorization code filed
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("FRC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        /*currencytype*/ itemtable.setBrType(String.valueOf(advpojo.getCurrencytype()));
                        itemtable.setFEEXrate(new BigDecimal(String.valueOf(advpojo.getExchangerate())));
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        /*noofunits*/ itemtable.setFEUnits(new BigDecimal(String.valueOf(advpojo.getNoofunits())));

                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFV")) {
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

                    } //Code added on March 3rd 2010 for Gift Card
                    else if (advpojo.getModeofpayment().equalsIgnoreCase("TNU")) {
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
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFC")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } //CODE ADDED ON 25/01/2011 FOR Fastrack SG Discount(FTD) PAYMENT MODE
                    else if (advpojo.getModeofpayment().equalsIgnoreCase("FTD")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);

                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());

                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("GFH")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
//                        itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("UPI")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (advpojo.getModeofpayment().equalsIgnoreCase("ZPI")) {
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        if (Validations.isFieldNotEmpty(advpojo.getInstrumentno())) {
                            itemtable.setNo(advpojo.getInstrumentno());
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getLoanamount())) {
                            itemtable.setLPPoints(String.valueOf((int) advpojo.getLoanamount()));
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getApprovalno())) {
                            itemtable.setLPAppNos(String.valueOf(advpojo.getApprovalno()));
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getAuthorizationcode())) {
                            itemtable.setAuthCode(advpojo.getAuthorizationcode());
                        }
                        if (Validations.isFieldNotEmpty(advpojo.getBank())) {//added by charan for zopper payment otp
                            itemtable.setBank(advpojo.getBank());
                        }
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    } else if (Validations.isFieldNotEmpty(advpojo.getModeofpayment())) {//Coded by Balachander V for General payment mode on 31.03.2020
                        advpojo.setLineitemno(v.indexOf(advpojo) + 1);
                        itemtable.setSrNO(new BigInteger(String.valueOf(advpojo.getLineitemno())));
                        itemtable.setNo(advpojo.getInstrumentno());
                        itemtable.setAmount(new BigDecimal(String.valueOf(advpojo.getAmount())));
                        itemtable.setPaymentMode(advpojo.getModeofpayment());
                    }//Coded by Balachander V for General payment mode on 31.03.2020

                    mtAdvanceReceipt.getItemTable().add(itemtable);

                }

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available
                //Code added by ravithota on 04 Jan 2013 to avoid sending payload if division is not available
                System.out.println("Advance Receipt Creation sitesearch " + mtAdvanceReceipt.getSITESEARCH());
                System.out.println("Advance Receipt Creation division " + mtAdvanceReceipt.getDivision());
                if (mtAdvanceReceipt.getSITESEARCH() != null && mtAdvanceReceipt.getSITESEARCH().trim().length() > 0 && mtAdvanceReceipt.getDivision() != null && mtAdvanceReceipt.getDivision().trim().length() > 0) {
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
                    in.co.titan.advancereceiptreversal.MIOBASYNARReversal port = service.getHTTPSPort();
                    Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                    //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
                    ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);

                    ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                    ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceiptReversal&version=3.0&Sender.Service=x&Interface=x%5Ex");

                    // TODO initialize WS operation arguments here
                    in.co.titan.advancereceiptreversal.DTARReversal mtARReversal = new in.co.titan.advancereceiptreversal.DTARReversal();
                    //  BigInteger BI = new BigInteger(Integer.toString(obj.getFiscalyear()));   

                    BigInteger BI = new BigInteger(Integer.toString(obj.getFiscalyear()));

                    mtARReversal.setADVRECNO(obj.getSapreferenceid());
                    mtARReversal.setPOSADVNO(obj.getDocumentno());
                    mtARReversal.setPOSREVNO(obj.getDatasheetno());
                    mtARReversal.setFYEAR(BI);

                    mtARReversal.setSITEID(obj.getStorecode());
                    mtARReversal.setSITESEARCH(obj.getStorecode());
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
                                }
                            }
                            if (xmlDate != null) {
                                mtARReversal.setCreatedDate(xmlDate);
                                mtARReversal.setModifiedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {

                    }

                    mtARReversal.setModifiedBy(obj.getModifiedby());

                    if (!obj.getReleasestatus().equalsIgnoreCase("FORCERELEASED")) {

                        mtARReversal.setReleaseStatus(obj.getReleasestatus());

                    }
                    mtARReversal.setSALEORDERNO(obj.getRefno());
                    if (Validations.isFieldNotEmpty(obj.getCancelOtp())) {//Coded by Balachander V on 3.8.2020 to send Advance reversal otp to SAP
                        mtARReversal.setCancelledOTP(obj.getCancelOtp());
                    }

                    //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available
                    System.out.println("Advance Receipt Reversal Storecode " + mtARReversal.getSITESEARCH());
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

                    //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                    // TODO process result here
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

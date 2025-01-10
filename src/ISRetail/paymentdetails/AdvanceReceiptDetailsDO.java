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
 * This class file is used as a Advance Receipt Detials Data Object for Advance and Payment Detaisl
 * This is used for transaction of Advances in different scenario from and to the database
 * This class also implements the WebService for Advance Receipt
 * 
 */
package ISRetail.paymentdetails;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class AdvanceReceiptDetailsDO {

    int casno = 0;
    double casamt = 0;

    /*    public int saveadvancereceiptDetails(Vector<AdvanceReceiptDetailsPOJO> advancereceiptpojoobjlist, Connection conn,AdvanceReceiptDetailsPOJO detobj) throws SQLException {
    int res=0;
    try {
    // AdvanceReceiptPOJO advancereceiptpojoobj=new AdvanceReceiptPOJO();
    Iterator it = advancereceiptpojoobjlist.iterator();
    int lineitemno=1;
    while (it.hasNext()) {
    AdvanceReceiptDetailsPOJO   advancereceiptdetailspojoobj = (AdvanceReceiptDetailsPOJO) it.next();
    // String insertstr = "insert into AdvanceReceiptDetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    if(!advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS"))
    {
    try
    {
    String insertstr ="insert into tbl_paymentdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    //String insertstr ="insert into tbl_paymentdetails(storecode, values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);

    pstmt.setString(1,detobj.getStorecode());
    pstmt.setInt(2,detobj.getFiscalyear());
    pstmt.setString(3,detobj.getDocumentno());


    pstmt.setInt(4,lineitemno);
    pstmt.setString(5,detobj.getPaymentfrom());
    pstmt.setString(6,advancereceiptdetailspojoobj.getModeofpayment());
    pstmt.setString(7,advancereceiptdetailspojoobj.getInstrumentno());
    pstmt.setInt(8,advancereceiptdetailspojoobj.getInstrumentdate());
    pstmt.setString(9,advancereceiptdetailspojoobj.getAuthorizationcode());
    pstmt.setString(10,advancereceiptdetailspojoobj.getBank());
    pstmt.setString(11,advancereceiptdetailspojoobj.getCardtype());
    pstmt.setString(12,advancereceiptdetailspojoobj.getBranchname());
    pstmt.setString(13,advancereceiptdetailspojoobj.getGVFrom());
    pstmt.setString(14,advancereceiptdetailspojoobj.getGVTO());
    pstmt.setDouble(15,advancereceiptdetailspojoobj.getDenomination());
    pstmt.setString(16,advancereceiptdetailspojoobj.getValidationtext());
    //pstmt.setDouble(17,advancereceiptdetailspojoobj.getCreditamount());
    //pstmt.setInt(18,advancereceiptdetailspojoobj.getCn_fiscalyear());
    //pstmt.setString(19,advancereceiptdetailspojoobj.getCreditnotereference());
    pstmt.setString(17,advancereceiptdetailspojoobj.getCurrencytype());
    pstmt.setDouble(18,advancereceiptdetailspojoobj.getExchangerate());
    pstmt.setDouble(19,advancereceiptdetailspojoobj.getNoofunits());
    pstmt.setString(20,advancereceiptdetailspojoobj.getEmployeename());
    pstmt.setString(21,advancereceiptdetailspojoobj.getDepartment());
    pstmt.setString(22,advancereceiptdetailspojoobj.getLetterreference());
    pstmt.setString(23,advancereceiptdetailspojoobj.getAuthorizedperson());
    pstmt.setDouble(24,advancereceiptdetailspojoobj.getLoanamount());
    pstmt.setInt(25,advancereceiptdetailspojoobj.getLoyalitypoints());
    pstmt.setInt(26,advancereceiptdetailspojoobj.getAsondate());
    pstmt.setInt(27,advancereceiptdetailspojoobj.getRedeemedpoints());
    pstmt.setString(28,advancereceiptdetailspojoobj.getApprovalno());
    pstmt.setString(29,advancereceiptdetailspojoobj.getOthercardtype());
    pstmt.setDouble(30,advancereceiptdetailspojoobj.getAmount());
    lineitemno++;


    res = pstmt.executeUpdate();
    }catch(SQLException e)
    {
    throw new SQLException();
    }

    }

    else if(advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS")&&casno==0){
    try
    {
    String insertstr ="insert into tbl_paymentdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);

    pstmt.setString(1,detobj.getStorecode());
    pstmt.setInt(2,detobj.getFiscalyear());
    pstmt.setString(3,detobj.getDocumentno());

    //pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
    pstmt.setInt(4,lineitemno);
    //pstmt.setString(5,advancereceiptdetailspojoobj.getPaymentfrom());
    pstmt.setString(5,detobj.getPaymentfrom());
    pstmt.setString(6,advancereceiptdetailspojoobj.getModeofpayment());
    pstmt.setString(7,advancereceiptdetailspojoobj.getInstrumentno());
    pstmt.setInt(8,advancereceiptdetailspojoobj.getInstrumentdate());
    pstmt.setString(9,advancereceiptdetailspojoobj.getAuthorizationcode());
    pstmt.setString(10,advancereceiptdetailspojoobj.getBank());
    pstmt.setString(11,advancereceiptdetailspojoobj.getCardtype());
    pstmt.setString(12,advancereceiptdetailspojoobj.getBranchname());
    pstmt.setString(13,advancereceiptdetailspojoobj.getGVFrom());
    pstmt.setString(14,advancereceiptdetailspojoobj.getGVTO());
    pstmt.setDouble(15,advancereceiptdetailspojoobj.getDenomination());
    pstmt.setString(16,advancereceiptdetailspojoobj.getValidationtext());
    //pstmt.setDouble(17,advancereceiptdetailspojoobj.getCreditamount());
    //pstmt.setInt(18,advancereceiptdetailspojoobj.getCn_fiscalyear());
    //pstmt.setString(19,advancereceiptdetailspojoobj.getCreditnotereference());
    pstmt.setString(17,advancereceiptdetailspojoobj.getCurrencytype());
    pstmt.setDouble(18,advancereceiptdetailspojoobj.getExchangerate());
    pstmt.setDouble(19,advancereceiptdetailspojoobj.getNoofunits());
    pstmt.setString(20,advancereceiptdetailspojoobj.getEmployeename());
    pstmt.setString(21,advancereceiptdetailspojoobj.getDepartment());
    pstmt.setString(22,advancereceiptdetailspojoobj.getLetterreference());
    pstmt.setString(23,advancereceiptdetailspojoobj.getAuthorizedperson());
    pstmt.setDouble(24,advancereceiptdetailspojoobj.getLoanamount());
    pstmt.setInt(25,advancereceiptdetailspojoobj.getLoyalitypoints());
    pstmt.setInt(26,advancereceiptdetailspojoobj.getAsondate());
    pstmt.setInt(27,advancereceiptdetailspojoobj.getRedeemedpoints());
    pstmt.setString(28,advancereceiptdetailspojoobj.getApprovalno());
    pstmt.setString(29,advancereceiptdetailspojoobj.getOthercardtype());
    pstmt.setDouble(30,advancereceiptdetailspojoobj.getAmount());


    casamt=advancereceiptdetailspojoobj.getAmount();
    lineitemno++;
    res = pstmt.executeUpdate();
    casno=1;
    } catch(SQLException e)

    {
    throw new SQLException();
    }
    }  else if(advancereceiptdetailspojoobj.getModeofpayment().equalsIgnoreCase("CAS")&&casno>0){

    Statement pstmt1 = conn.createStatement();

    double amt=casamt+advancereceiptdetailspojoobj.getAmount();

    try{
    String query="update tbl_paymentdetails set amount ="+amt+" where documentno='"+detobj.getDocumentno()+"' and modeofpayment='CAS'";
    int res1=pstmt1.executeUpdate(query);
    if(res1>0){}else{System.out.println("not updated");}

    }catch(Exception e){throw new SQLException();}
    }

    }
    } catch (Exception e) {
    e.printStackTrace();
    throw new SQLException();
    }
    return res;
    }
     */
    /*     public int saveadvancereceiptDetails2(ArrayList<AdvanceReceiptDetailsPOJO> advancereceiptpojoobjlist, Connection conn,String docno) {

    try {


    int res = 0;
    Iterator it = advancereceiptpojoobjlist.iterator();
    while (it.hasNext()) {
    AdvanceReceiptDetailsPOJO advancereceiptdetailspojoobj = (AdvanceReceiptDetailsPOJO) it.next();

    
    String insertstr ="insert into tbl_paymentdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);

    pstmt.setString(1,advancereceiptdetailspojoobj.getStorecode());
    pstmt.setInt(2,advancereceiptdetailspojoobj.getFiscalyear());
    pstmt.setString(3,advancereceiptdetailspojoobj.getDocumentno());

    pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
    pstmt.setString(5,advancereceiptdetailspojoobj.getPaymentfrom());
    pstmt.setString(6,advancereceiptdetailspojoobj.getModeofpayment());
    pstmt.setString(7,advancereceiptdetailspojoobj.getInstrumentno());
    pstmt.setInt(8,advancereceiptdetailspojoobj.getInstrumentdate());
    pstmt.setString(9,advancereceiptdetailspojoobj.getAuthorizationcode());
    pstmt.setString(10,advancereceiptdetailspojoobj.getBank());
    pstmt.setString(11,advancereceiptdetailspojoobj.getCardtype());
    pstmt.setString(12,advancereceiptdetailspojoobj.getBranchname());
    pstmt.setString(13,advancereceiptdetailspojoobj.getGVFrom());
    pstmt.setString(14,advancereceiptdetailspojoobj.getGVTO());
    pstmt.setDouble(15,advancereceiptdetailspojoobj.getDenomination());
    pstmt.setString(16,advancereceiptdetailspojoobj.getValidationtext());
    pstmt.setString(17,advancereceiptdetailspojoobj.getCurrencytype());
    pstmt.setDouble(18,advancereceiptdetailspojoobj.getExchangerate());
    pstmt.setInt(19,advancereceiptdetailspojoobj.getNoofunits());
    pstmt.setString(20,advancereceiptdetailspojoobj.getEmployeename());
    pstmt.setString(21,advancereceiptdetailspojoobj.getDepartment());
    pstmt.setString(22,advancereceiptdetailspojoobj.getLetterreference());
    pstmt.setString(23,advancereceiptdetailspojoobj.getAuthorizedperson());
    pstmt.setDouble(24,advancereceiptdetailspojoobj.getLoanamount());
    pstmt.setInt(25,advancereceiptdetailspojoobj.getLoyalitypoints());
    pstmt.setInt(26,advancereceiptdetailspojoobj.getAsondate());
    pstmt.setInt(27,advancereceiptdetailspojoobj.getRedeemedpoints());
    pstmt.setString(28,advancereceiptdetailspojoobj.getApprovalno());
    pstmt.setString(29,advancereceiptdetailspojoobj.getOthercardtype());
    pstmt.setDouble(30,advancereceiptdetailspojoobj.getAmount());



    res = pstmt.executeUpdate();
    }
    return res;

    } catch (Exception e) {
    e.printStackTrace();
    return 0;
    }
    }
     */
    /*   public int saveadvancereceiptDetails(Vector<AdvanceReceiptDetailsPOJO> advancereceiptpojoobjlist, Connection conn,String docno,String storecode,int fiscalyear) {

    try {


    int res = 0;
    Iterator it = advancereceiptpojoobjlist.iterator();
    while (it.hasNext()) {
    AdvanceReceiptDetailsPOJO advancereceiptdetailspojoobj = (AdvanceReceiptDetailsPOJO) it.next();

    
    String insertstr ="insert into tbl_paymentdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);

    pstmt.setString(1,advancereceiptdetailspojoobj.getStorecode());
    pstmt.setInt(2,advancereceiptdetailspojoobj.getFiscalyear());
    pstmt.setString(3,advancereceiptdetailspojoobj.getDocumentno());

    pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
    pstmt.setString(5,advancereceiptdetailspojoobj.getPaymentfrom());
    pstmt.setString(6,advancereceiptdetailspojoobj.getModeofpayment());
    pstmt.setString(7,advancereceiptdetailspojoobj.getInstrumentno());
    pstmt.setInt(8,advancereceiptdetailspojoobj.getInstrumentdate());
    pstmt.setString(9,advancereceiptdetailspojoobj.getAuthorizationcode());
    pstmt.setString(10,advancereceiptdetailspojoobj.getBank());
    pstmt.setString(11,advancereceiptdetailspojoobj.getCardtype());
    pstmt.setString(12,advancereceiptdetailspojoobj.getBranchname());
    pstmt.setString(13,advancereceiptdetailspojoobj.getGVFrom());
    pstmt.setString(14,advancereceiptdetailspojoobj.getGVTO());
    pstmt.setDouble(15,advancereceiptdetailspojoobj.getDenomination());
    pstmt.setString(16,advancereceiptdetailspojoobj.getValidationtext());
    pstmt.setString(17,advancereceiptdetailspojoobj.getCurrencytype());
    pstmt.setDouble(18,advancereceiptdetailspojoobj.getExchangerate());
    pstmt.setInt(19,advancereceiptdetailspojoobj.getNoofunits());
    pstmt.setString(20,advancereceiptdetailspojoobj.getEmployeename());
    pstmt.setString(21,advancereceiptdetailspojoobj.getDepartment());
    pstmt.setString(22,advancereceiptdetailspojoobj.getLetterreference());
    pstmt.setString(23,advancereceiptdetailspojoobj.getAuthorizedperson());
    pstmt.setDouble(24,advancereceiptdetailspojoobj.getLoanamount());
    pstmt.setInt(25,advancereceiptdetailspojoobj.getLoyalitypoints());
    pstmt.setInt(26,advancereceiptdetailspojoobj.getAsondate());
    pstmt.setInt(27,advancereceiptdetailspojoobj.getRedeemedpoints());
    pstmt.setString(28,advancereceiptdetailspojoobj.getApprovalno());
    pstmt.setString(29,advancereceiptdetailspojoobj.getOthercardtype());
    pstmt.setDouble(30,advancereceiptdetailspojoobj.getAmount());



    res = pstmt.executeUpdate();
    }
    return res;

    } catch (Exception e) {
    e.printStackTrace();
    return 0;
    }
    }*/
    public int getMaxSlnoOfAdvanceReceipt(Connection con) {

        Statement pstmt;
        ResultSet rs;
        String searchstatement;
        try {
            pstmt = (Statement) con.createStatement();
            searchstatement = "select max(lineitemno) from tbl_paymentdetails ";
            rs = pstmt.executeQuery(searchstatement);

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            rs = null;
            searchstatement = null;
        }

    }

    /****************used in adv display for printing**********************************/
    /*
    public ArrayList getAdvanceReceiptDetailListforprint(Connection conn,String searchstmt) {
    try {

    AdvanceReceiptDetailsPOJO advancerecpojoobj;
    //  AdvanceReceiptPOJO advancerecpojoobj1;
    Statement pstmt = conn.createStatement();
    System.out.println("getadvancereceiptdetaillist"+searchstmt);
    //String searchstatement = "select * from customermasternew where  " + wherestatement;
    ResultSet rs = pstmt.executeQuery(searchstmt);

    ArrayList<AdvanceReceiptDetailsPOJO> advancereclist = new ArrayList<AdvanceReceiptDetailsPOJO>();
    
    while (rs.next()) {
    advancerecpojoobj = new AdvanceReceiptDetailsPOJO();


    advancerecpojoobj.setDocumentno(rs.getString("documentno"));

    advancerecpojoobj.setStorecode(rs.getString("storecode"));
    advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
    advancerecpojoobj.setLineitemno(rs.getInt("lineitemno"));
    advancerecpojoobj.setPaymentfrom(rs.getString("paymentfrom"));
    advancerecpojoobj.setModeofpayment(rs.getString("modeofpayment"));
    advancerecpojoobj.setInstrumentno(rs.getString("instrumentno"));
    advancerecpojoobj.setInstrumentdate(rs.getInt("instrumentdate"));
    advancerecpojoobj.setAuthorizationcode(rs.getString("authorizationcode"));
    advancerecpojoobj.setBank(rs.getString("bank"));
    advancerecpojoobj.setCardtype(rs.getString("cardtype"));
    System.out.println("cardtype"+advancerecpojoobj.getCardtype());
    advancerecpojoobj.setBranchname(rs.getString("branchname"));
    advancerecpojoobj.setGVFrom(rs.getString("GVFrom"));
    advancerecpojoobj.setGVTO(rs.getString("GVTO"));
    advancerecpojoobj.setDenomination(rs.getDouble("Denomination"));
    advancerecpojoobj.setValidationtext(rs.getString("validationtext"));
    //   advancerecpojoobj.setCreditamount(rs.getDouble("creditamount"));
    //   advancerecpojoobj.setCn_fiscalyear(rs.getInt("cn_fiscalyear"));
    //    advancerecpojoobj.setCreditnotereference(rs.getString("creditnotereference"));
    advancerecpojoobj.setCurrencytype(rs.getString("currencytype"));
    advancerecpojoobj.setExchangerate(rs.getInt("exchangerate"));
    advancerecpojoobj.setNoofunits(rs.getDouble("noofunits"));
    advancerecpojoobj.setEmployeename(rs.getString("employeename"));
    advancerecpojoobj.setDepartment(rs.getString("department"));
    advancerecpojoobj.setLetterreference(rs.getString("letterreference"));
    advancerecpojoobj.setAuthorizedperson(rs.getString("authorizedperson"));
    advancerecpojoobj.setLoanamount(rs.getDouble("loanamount"));
    advancerecpojoobj.setLoyalitypoints(rs.getInt("loyalty_points"));
    advancerecpojoobj.setAsondate(rs.getInt("loyalty_asondate"));
    advancerecpojoobj.setRedeemedpoints(rs.getInt("loyalty_redeemedpoints"));
    advancerecpojoobj.setApprovalno(rs.getString("loyalty_approvalno"));
    advancerecpojoobj.setOthercardtype(rs.getString("othercardtype"));
    advancerecpojoobj.setAmount(rs.getDouble("amount"));
    advancereclist.add(advancerecpojoobj);



    }
    
    return advancereclist;
    }catch(SQLException e){
    e.printStackTrace();
    return null;

    }}
     */
    /*************End of used only in adv display for printing*********************/
    public Vector getAdvanceReceiptDetailList(Connection conn, Vector advlist, String searchstmt) {
        Statement pstmt;
        ResultSet rs;
        try {
            AdvanceReceiptDetailsPOJO advancerecpojoobj;
            //AdvanceReceiptPOJO advancerecpojoobj1;
            pstmt = conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstmt);
            // ArrayList<AdvanceReceiptDetailsPOJO> advancereclist = new ArrayList<AdvanceReceiptDetailsPOJO>();
            while (rs.next()) {
                advancerecpojoobj = new AdvanceReceiptDetailsPOJO();
                //advancerecpojoobj1 = new AdvanceReceiptPOJO();
                //advancerecpojoobj1.setAmount(rs.getDouble("totalamount"));
                //advancerecpojoobj1.setDateofpayment(rs.getInt("dateofpayment"));
                try {
                    advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                advancerecpojoobj.setStorecode(rs.getString("storecode"));
                advancerecpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                advancerecpojoobj.setLineitemno(rs.getInt("lineitemno"));
                advancerecpojoobj.setPaymentfrom(rs.getString("paymentfrom"));
                advancerecpojoobj.setModeofpayment(rs.getString("modeofpayment"));
                advancerecpojoobj.setInstrumentno(rs.getString("instrumentno"));
                advancerecpojoobj.setInstrumentdate(rs.getInt("instrumentdate"));
                advancerecpojoobj.setAuthorizationcode(rs.getString("authorizationcode"));
                advancerecpojoobj.setBank(rs.getString("bank"));
                advancerecpojoobj.setCardtype(rs.getString("cardtype"));
                advancerecpojoobj.setBranchname(rs.getString("branchname"));
                advancerecpojoobj.setGVFrom(rs.getString("GVFrom"));
                advancerecpojoobj.setGVTO(rs.getString("GVTO"));
                advancerecpojoobj.setDenomination(rs.getDouble("Denomination"));
                advancerecpojoobj.setValidationtext(rs.getString("validationtext"));
                //advancerecpojoobj.setCreditamount(rs.getDouble("creditamount"));
                //advancerecpojoobj.setCn_fiscalyear(rs.getInt("cn_fiscalyear"));
                //advancerecpojoobj.setCreditnotereference(rs.getString("creditnotereference"));
                advancerecpojoobj.setCurrencytype(rs.getString("currencytype"));
                advancerecpojoobj.setExchangerate(rs.getInt("exchangerate"));
                advancerecpojoobj.setNoofunits(rs.getDouble("noofunits"));
                advancerecpojoobj.setEmployeename(rs.getString("employeename"));
                advancerecpojoobj.setDepartment(rs.getString("department"));
                advancerecpojoobj.setLetterreference(rs.getString("letterreference"));
                advancerecpojoobj.setAuthorizedperson(rs.getString("authorizedperson"));
                advancerecpojoobj.setLoanamount(rs.getDouble("loanamount"));
                advancerecpojoobj.setLoyalitypoints(rs.getInt("loyalty_points"));
                advancerecpojoobj.setAsondate(rs.getInt("loyalty_asondate"));
                advancerecpojoobj.setRedeemedpoints(rs.getInt("loyalty_redeemedpoints"));
                advancerecpojoobj.setApprovalno(rs.getString("loyalty_approvalno"));
                advancerecpojoobj.setOthercardtype(rs.getString("othercardtype"));
                advancerecpojoobj.setAmount(rs.getDouble("amount"));
                //advlist.add(advlist.indexOf(advancerecpojoobj),advancerecpojoobj);
                advlist.add(advancerecpojoobj);
                // advancereclist.add(advancerecpojoobj);
            }
            return advlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }

    }

    public Vector getAdvanceReceiptDetailsearchList(Connection conn, Vector advlist, String searchstmt) {
        Statement pstmt;
        ResultSet rs;
        try {

            AdvanceReceiptDetailsPOJO advancerecpojoobj;
            //AdvanceReceiptPOJO advancerecpojoobj1;
            pstmt = conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstmt);
            //   ArrayList<AdvanceReceiptDetailsPOJO> advancereclist = new ArrayList<AdvanceReceiptDetailsPOJO>();
            while (rs.next()) {
                advancerecpojoobj = new AdvanceReceiptDetailsPOJO();
                //advancerecpojoobj1 = new AdvanceReceiptPOJO();
                //advancerecpojoobj1.setAmount(rs.getDouble("totalamount"));
                //advancerecpojoobj1.setDateofpayment(rs.getInt("dateofpayment"));
                try {
                    advancerecpojoobj.setDocumentno(rs.getString("documentno"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //advlist.add(advlist.indexOf(advancerecpojoobj),advancerecpojoobj);
                advlist.add(advancerecpojoobj);
                //advancereclist.add(advancerecpojoobj);
            }
            return advlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public double getamountfromadvnoforcancel(String advno, Connection con) {
        Statement pstmt;
        ResultSet rs;
        try {
            double amount = 0;
            pstmt = (Statement) con.createStatement();
            rs = pstmt.executeQuery("select amount from tbl_paymentdetails where documentno='" + advno + "'");
            while (rs.next()) {
                amount = rs.getDouble("amount");
            }
            return amount;
        } catch (SQLException ex) {
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }
    }
    /*  public int saveadvancereceiptDetails1(Vector advancereceiptpojoobjlist, Connection conn,String docno) {

    try {

    // AdvanceReceiptPOJO advancereceiptpojoobj=new AdvanceReceiptPOJO();
    int res = 0;
    Iterator it = advancereceiptpojoobjlist.iterator();
    while (it.hasNext()) {
    AdvanceReceiptDetailsPOJO advancereceiptdetailspojoobj = (AdvanceReceiptDetailsPOJO) it.next();
    //  String insertstr = "insert into AdvanceReceiptDetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    
    String insertstr ="insert into tbl_paymentdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement pstmt = conn.prepareStatement(insertstr);

    pstmt.setString(1,advancereceiptdetailspojoobj.getStorecode());
    pstmt.setInt(2,advancereceiptdetailspojoobj.getFiscalyear());
    pstmt.setString(3,advancereceiptdetailspojoobj.getDocumentno());

    pstmt.setInt(4,advancereceiptdetailspojoobj.getLineitemno());
    pstmt.setString(5,advancereceiptdetailspojoobj.getPaymentfrom());
    pstmt.setString(6,advancereceiptdetailspojoobj.getModeofpayment());
    pstmt.setString(7,advancereceiptdetailspojoobj.getInstrumentno());
    pstmt.setInt(8,advancereceiptdetailspojoobj.getInstrumentdate());
    pstmt.setString(9,advancereceiptdetailspojoobj.getAuthorizationcode());
    pstmt.setString(10,advancereceiptdetailspojoobj.getBank());
    pstmt.setString(11,advancereceiptdetailspojoobj.getCardtype());
    pstmt.setString(12,advancereceiptdetailspojoobj.getBranchname());
    pstmt.setString(13,advancereceiptdetailspojoobj.getGVFrom());
    pstmt.setString(14,advancereceiptdetailspojoobj.getGVTO());
    pstmt.setDouble(15,advancereceiptdetailspojoobj.getDenomination());
    pstmt.setString(16,advancereceiptdetailspojoobj.getValidationtext());
    //pstmt.setDouble(17,advancereceiptdetailspojoobj.getCreditamount());
    //pstmt.setInt(18,advancereceiptdetailspojoobj.getCn_fiscalyear());
    //pstmt.setString(19,advancereceiptdetailspojoobj.getCreditnotereference());
    pstmt.setString(17,advancereceiptdetailspojoobj.getCurrencytype());
    pstmt.setDouble(18,advancereceiptdetailspojoobj.getExchangerate());
    pstmt.setInt(19,advancereceiptdetailspojoobj.getNoofunits());
    pstmt.setString(20,advancereceiptdetailspojoobj.getEmployeename());
    pstmt.setString(21,advancereceiptdetailspojoobj.getDepartment());
    pstmt.setString(22,advancereceiptdetailspojoobj.getLetterreference());
    pstmt.setString(23,advancereceiptdetailspojoobj.getAuthorizedperson());
    pstmt.setDouble(24,advancereceiptdetailspojoobj.getLoanamount());
    pstmt.setInt(25,advancereceiptdetailspojoobj.getLoyalitypoints());
    pstmt.setInt(26,advancereceiptdetailspojoobj.getAsondate());
    pstmt.setInt(27,advancereceiptdetailspojoobj.getRedeemedpoints());
    pstmt.setString(28,advancereceiptdetailspojoobj.getApprovalno());
    pstmt.setString(29,advancereceiptdetailspojoobj.getOthercardtype());
    pstmt.setDouble(30,advancereceiptdetailspojoobj.getAmount());

    res = pstmt.executeUpdate();
    }
    return res;

    } catch (Exception e) {
    e.printStackTrace();
    return 0;
    }
    }
     */
}

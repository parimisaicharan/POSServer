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
 * This class file is used as a Data Object between Credit Note and Database
 * This is used for transaction of CreditNote  
 * 
 * 
 */
package ISRetail.creditnote;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.services.ServiceorderCancellationBean;
import ISRetail.services.ServicesHeaderPOJO;
import ISRetail.utility.validations.GenerateNextPosDocNumber;
import ISRetail.utility.validations.Validations;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

public class CreditNoteDO implements Webservice {

    /**
     * CreditNote Search List
     */
    public ArrayList getCNSearchResultList(Connection conn, String wherestatement) {
        
        Statement pstmt;
        ResultSet rs;
        String searchstatement = wherestatement;
        
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<CreditNotePOJO> cnList = new ArrayList<CreditNotePOJO>();
            
            while (rs.next()) {
                CreditNotePOJO cnSearchresultPojo = new CreditNotePOJO();
                cnSearchresultPojo.setCustomercode(rs.getString("customercode"));
                cnSearchresultPojo.setCustomername(rs.getString("firstname"));
                cnSearchresultPojo.setCreditnoteno(rs.getString("creditnoteno"));
                cnSearchresultPojo.setCreditnotedate(rs.getInt("creditnotedate"));
                cnSearchresultPojo.setReftype(rs.getString("reftype"));
                cnSearchresultPojo.setRefno(rs.getString("refno"));
                cnSearchresultPojo.setRefdate(rs.getInt("refdate"));
                cnSearchresultPojo.setAmount(rs.getDouble("amount"));
                cnSearchresultPojo.setFiscalyear(rs.getInt("fiscalyear"));
                cnList.add(cnSearchresultPojo);
            }
            return cnList;
            
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
            searchstatement = null;
        }
    }

    /**
     * Update Status of the CreditNote
     */
    public int updatestatusofcreditnote(Connection conn, String status, String creditnoteno) {
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement("update tbl_creditnote set status ='" + status + "' where creditnoteno='" + creditnoteno + "'");
            int res = pstmt.executeUpdate();
            return res;
        } catch (Exception e) {
            return 0;
        } finally {
            pstmt = null;
        }
    }

    /**
     * Get the List of Credit Note Details
     */
    public ArrayList getcreditnotedetailsbycreditnoteno(Connection conn, String creditnoteno1) {
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        
        try {
            pstmt = conn.createStatement();
            searchstatement = "select * from tbl_creditnote where creditnoteno='" + creditnoteno1 + "'";
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<CreditNotePOJO> cnList = new ArrayList<CreditNotePOJO>();
            CreditNotePOJO cnSearchresultPojo = null;
            while (rs.next()) {
                cnSearchresultPojo = new CreditNotePOJO();
                cnSearchresultPojo.setCreditnoteno(rs.getString("creditnoteno"));
                cnSearchresultPojo.setCreditnotedate(rs.getInt("creditnotedate"));
                cnSearchresultPojo.setFiscalyear(rs.getInt("fiscalyear"));
                cnSearchresultPojo.setReftype(rs.getString("reftype"));
                cnSearchresultPojo.setRefno(rs.getString("refno"));
                cnSearchresultPojo.setAmount(rs.getDouble("amount"));
                cnList.add(cnSearchresultPojo);
            }
            return cnList;
            
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            searchstatement = null;
            rs = null;
        }
        
    }

    /**
     * Get Credit Note Object
     */
    public CreditNotePOJO getcreditnotedetailsbycreditnotenooforcreate(Connection conn, String creditnoteno1, String customercode) {
        CreditNotePOJO cnSearchresultPojo = null;
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        
        try {
            pstmt = conn.createStatement();
            searchstatement = "select * from tbl_creditnote where creditnoteno='" + creditnoteno1 + "' and status='OPEN' and customercode='" + customercode + "'";
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {
                cnSearchresultPojo = new CreditNotePOJO();
                cnSearchresultPojo.setCreditnoteno(rs.getString("creditnoteno"));
                cnSearchresultPojo.setCreditnotedate(rs.getInt("creditnotedate"));
                cnSearchresultPojo.setFiscalyear(rs.getInt("fiscalyear"));
                cnSearchresultPojo.setReftype(rs.getString("reftype"));
                cnSearchresultPojo.setRefno(rs.getString("refno"));
                cnSearchresultPojo.setAmount(rs.getDouble("amount"));
            }
            return cnSearchresultPojo;
            
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
            searchstatement = null;
        }
    }

    /**
     * Get the Reference SaleOrder No
     */
    public String getCreditsaleordernoforcreditnoteno(String creditnoteno, Connection con) {
        Statement pstmt;
        String searchst;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            searchst = "select refsalesorderno from tbl_creditnote where creditnoteno='" + creditnoteno + "'";
            rs = pstmt.executeQuery(searchst);
            while (rs.next()) {
                return rs.getString(1);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            searchst = null;
            rs = null;
        }
    }

    /**
     * Get the Reference Date for the CreditNote
     */
    public int getcreditdateforcreditno(Connection conn, String creditnoteno) {
        Statement pstmt;
        ResultSet rs;
        try {
            
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery("select refdate from tbl_creditnote where creditnoteno='" + creditnoteno + "'");
            while (rs.next()) {
                return rs.getInt(1);
                
            }
            return 0;
        } catch (Exception e) {
            
            e.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * Get the Credit Note Status
     */
    public String getcreditstatusforcreditno(Connection conn, String creditnoteno) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery("select status from tbl_creditnote where creditnoteno='" + creditnoteno + "'");
            while (rs.next()) {
                return rs.getString(1);
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
     * Get the Credit Note Status by Sale order No
     */
    public CreditNotePOJO getCreditNoteDetailsBysaleorderNo(Connection conn, String creditnoteno, String category) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_creditnote where creditnoteno=? and category=?");
            pstmt.setString(1, creditnoteno);
            pstmt.setString(2, category);
            rs = pstmt.executeQuery();
            CreditNotePOJO creditnotepojoobj = new CreditNotePOJO();
            if (rs.next()) {
                creditnotepojoobj.setStorecode(rs.getString("storecode"));
                creditnotepojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                creditnotepojoobj.setCreditnoteno(rs.getString("creditnoteno"));
                creditnotepojoobj.setCreditnotedate(rs.getInt("creditnotedate"));
                creditnotepojoobj.setCustomercode(rs.getString("customercode"));
                creditnotepojoobj.setReferencesapid(rs.getString("referencesapid"));
                creditnotepojoobj.setAmount(rs.getDouble("amount"));
                //Code added on May 26th 2010
                creditnotepojoobj.setRefno(rs.getString("refno"));
                //End of code added on May 26th 2010
                creditnotepojoobj.setReftype(rs.getString("reftype"));
                creditnotepojoobj.setExpirydate(rs.getInt("expirydate"));
                creditnotepojoobj.setCategory(rs.getString("category"));
                return creditnotepojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }
//Code added on May 26th 2010 for NR credit note generation

    public NRCreditNotePOJO getNRCreditNoteDetailsBysaleorderNo(Connection conn, String creditnoteno, String category) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_creditnote where creditnoteno=? and category=?");
            pstmt.setString(1, creditnoteno);
            pstmt.setString(2, category);
            rs = pstmt.executeQuery();
            NRCreditNotePOJO creditnotepojoobj = new NRCreditNotePOJO();
            if (rs.next()) {
                creditnotepojoobj.setStorecode(rs.getString("storecode"));
                creditnotepojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                creditnotepojoobj.setCreditnoteno(rs.getString("creditnoteno"));
                creditnotepojoobj.setCreditnotedate(rs.getInt("creditnotedate"));
                creditnotepojoobj.setCustomercode(rs.getString("customercode"));
                creditnotepojoobj.setReferencesapid(rs.getString("referencesapid"));
                creditnotepojoobj.setAmount(rs.getDouble("amount"));
                creditnotepojoobj.setReftype(rs.getString("reftype"));
                //Code added on May 26th 2010
                creditnotepojoobj.setRefno(rs.getString("refno"));
                //End of code added on May 26th 2010
                creditnotepojoobj.setExpirydate(rs.getInt("expirydate"));
                creditnotepojoobj.setCategory(rs.getString("category"));
                System.out.println("NRCREDITNOTE---returned");
                return creditnotepojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    //End of code added on May 26th 2010
    //Code added on May 26th 2010 for NR credit note generation
    public NRCreditNotePOJO getNRCreditNoteDetailsByRefNo(Connection conn, String refno, String reftype, String category) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_creditnote where refno=? and reftype=? and category=?");
            pstmt.setString(1, refno);
            pstmt.setString(2, reftype);
            pstmt.setString(3, category);
            rs = pstmt.executeQuery();
            NRCreditNotePOJO creditnotepojoobj = new NRCreditNotePOJO();
            if (rs.next()) {
                creditnotepojoobj.setStorecode(rs.getString("storecode"));
                creditnotepojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                creditnotepojoobj.setCreditnoteno(rs.getString("creditnoteno"));
                creditnotepojoobj.setCreditnotedate(rs.getInt("creditnotedate"));
                creditnotepojoobj.setCustomercode(rs.getString("customercode"));
                creditnotepojoobj.setReferencesapid(rs.getString("referencesapid"));
                creditnotepojoobj.setAmount(rs.getDouble("amount"));
                creditnotepojoobj.setReftype(rs.getString("reftype"));
                //Code added on May 26th 2010
                creditnotepojoobj.setRefno(rs.getString("refno"));
                //End of code added on May 26th 2010
                creditnotepojoobj.setExpirydate(rs.getInt("expirydate"));
                creditnotepojoobj.setCategory(rs.getString("category"));
                System.out.println("NRCREDITNOTE---returned");
                return creditnotepojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    //End of code added on May 26th 2010
    /**
     * Generate the creditNote No
     */
    public String generateCreditNoteNo(Connection con) {
        String nextCreditNo = null;
        String prevCreditNo = null, prevCreditLastNoPart = null;
        Statement pstmt;
        ResultSet rs;
        try {
            //generate firtsfixedPart for credit not no
            String firstFixedString = "M";
            String numLogicForStore = new SiteMasterDO().getSiteNumberLogicValue(con);
            if (numLogicForStore != null) {
                numLogicForStore = numLogicForStore.trim();
            }
            firstFixedString = firstFixedString + numLogicForStore;
            //getting lastno
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select creditnoteno from tbl_posdoclastnumbers");
            if (rs.next()) {
                prevCreditNo = rs.getString(1);
            }
            
            if (Validations.isFieldNotEmpty(prevCreditNo)) {//if lastNo exists
                if (prevCreditNo.length() > firstFixedString.length()) {
                    prevCreditLastNoPart = prevCreditNo.substring(firstFixedString.length());
                    prevCreditNo = firstFixedString + prevCreditLastNoPart;//eg: MAC0000014
                }
            } else {//if no last no exists
                String creditNoLastPart = "";
                for (int i = firstFixedString.length(); i < 10; i++) {
                    creditNoLastPart = creditNoLastPart + "0";
                }
                prevCreditNo = firstFixedString + creditNoLastPart;//eg: MAC0000000
            }
            nextCreditNo = new GenerateNextPosDocNumber().generatenumber(prevCreditNo, firstFixedString.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return nextCreditNo;
    }

    /**
     * Update Last Credit Note in POS Last Doc Numbers
     */
    public int updateLastCreditNoteNo(Connection con, String lastDocNo) {
        String query = "update tbl_posdoclastnumbers set creditnoteno =? ";
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, lastDocNo);
            int res = pstmt.executeUpdate();
            return res;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        } finally {
            query = null;
            pstmt = null;
        }
        
    }

    /**
     * Get Credit Note Details by Ref No
     */
    public CreditNotePOJO getCreditNoteDetailsByRefNo(Connection conn, String refno, String storeCode, int fiscalYear) {//added by smitha

        PreparedStatement pstmt;
        try {
            // pstmt = conn.prepareStatement("select creditnoteno,creditnotedate,customercode,referencesapid,amount,reftype,expirydate from tbl_creditnote where refno=? and storecode=? and fiscalyear=? ");
            pstmt = conn.prepareStatement("select creditnoteno,creditnotedate,customercode,referencesapid,amount,reftype,expirydate from tbl_creditnote where refno=? and storecode=?");
            pstmt.setString(1, refno);
            pstmt.setString(2, storeCode);
            //  pstmt.setInt(3, fiscalYear);
            ResultSet rs = pstmt.executeQuery();
            CreditNotePOJO creditnotepojoobj = new CreditNotePOJO();
            if (rs.next()) {
                creditnotepojoobj.setStorecode(storeCode);
                creditnotepojoobj.setFiscalyear(fiscalYear);
                creditnotepojoobj.setRefno(refno);
                creditnotepojoobj.setCreditnoteno(rs.getString("creditnoteno"));
                creditnotepojoobj.setCreditnotedate(rs.getInt("creditnotedate"));
                creditnotepojoobj.setCustomercode(rs.getString("customercode"));
                creditnotepojoobj.setReferencesapid(rs.getString("referencesapid"));
                creditnotepojoobj.setAmount(rs.getDouble("amount"));
                creditnotepojoobj.setReftype(rs.getString("reftype"));
                creditnotepojoobj.setExpirydate(rs.getInt("expirydate"));
                
                return creditnotepojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
        }
        
    }

    public CreditNotePOJO getCreditNoteDetailsByRefNoandCategory(Connection conn, String refno, String storeCode, int fiscalYear) {//added by smitha

        PreparedStatement pstmt;
        try {
            // pstmt = conn.prepareStatement("select creditnoteno,creditnotedate,customercode,referencesapid,amount,reftype,expirydate from tbl_creditnote where refno=? and storecode=? and fiscalyear=? ");
            pstmt = conn.prepareStatement("select creditnoteno,creditnotedate,customercode,referencesapid,amount,reftype,expirydate from tbl_creditnote where refno=? and storecode=? and category='RF'");
            pstmt.setString(1, refno);
            pstmt.setString(2, storeCode);
            //  pstmt.setInt(3, fiscalYear);
            ResultSet rs = pstmt.executeQuery();
            CreditNotePOJO creditnotepojoobj = new CreditNotePOJO();
            if (rs.next()) {
                creditnotepojoobj.setStorecode(storeCode);
                creditnotepojoobj.setFiscalyear(fiscalYear);
                creditnotepojoobj.setRefno(refno);
                creditnotepojoobj.setCreditnoteno(rs.getString("creditnoteno"));
                creditnotepojoobj.setCreditnotedate(rs.getInt("creditnotedate"));
                creditnotepojoobj.setCustomercode(rs.getString("customercode"));
                creditnotepojoobj.setReferencesapid(rs.getString("referencesapid"));
                creditnotepojoobj.setAmount(rs.getDouble("amount"));
                creditnotepojoobj.setReftype(rs.getString("reftype"));
                creditnotepojoobj.setExpirydate(rs.getInt("expirydate"));
                
                return creditnotepojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
        }
        
    }

    /**
     * Archival utility
     */
    public int archiveAllCreditnoteTables(Connection con, int fiscalYear) throws SQLException {
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        try {
            query = "delete from tbl_creditnote where fisaclyear= ?";
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
     * Execute Credit Note Creation
     */
    public String execute(DataObject obj, String updateType) {
        
        try { // Call Web Service Operation
            if (obj instanceof SaleorderCancellationBean) {
                SaleorderCancellationBean bean = (SaleorderCancellationBean) obj;
                SalesOrderHeaderPOJO pojo = bean.getSalesOrderHeader();
                CreditNotePOJO creditnotepojo = bean.getCreditNotePojo();
                NRCreditNotePOJO nRCreditNotePOJO = bean.getNRCreditNotePOJO();
                in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellationService service = new in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellationService();
                //in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellation port = service.getMIOBASYNAdvanceReceiptSaleOrderCancellationPort();
                in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellation port = service.getHTTPSPort();
                // TODO initialize WS operation arguments here
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation dtAdvanceReceiptSaleOrderCancellation = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation();
                // TODO process result here
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceiptSaleOrderCancellation&version=3.0&Sender.Service=X&Interface=Z%5EY");
                XMLCalendar xmlDate = null;
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.AdvanceItem advItem = null;
                if (pojo != null) {
                    
                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(pojo.getSaleordercancellationdate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(pojo.getCancelledTime());
                        if (createdDate != null) {
                            xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    dtAdvanceReceiptSaleOrderCancellation.setCancelledTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                dtAdvanceReceiptSaleOrderCancellation.setCancelledDate(xmlDate);
                                dtAdvanceReceiptSaleOrderCancellation.setDocumentDate(xmlDate);
                            }
                        }
                    } catch (Exception e) {
                    }
                    dtAdvanceReceiptSaleOrderCancellation.setCancelledBy(pojo.getCancelledby());
                    //code added on 21 Jun 2011 for name capture in Sale Order Cancel
                    dtAdvanceReceiptSaleOrderCancellation.setCancelledPersonName(pojo.getStaffresponsible());
                    dtAdvanceReceiptSaleOrderCancellation.setSITESEARCH(pojo.getStoreCode());
                    if (Validations.isFieldNotEmpty(pojo.getSaleorderno())) {
                        dtAdvanceReceiptSaleOrderCancellation.setSONO(pojo.getSaleorderno());
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getSaleorderno())) {
                        dtAdvanceReceiptSaleOrderCancellation.setSaleOrderNO(pojo.getSaleorderno());
                    }
                    
                    dtAdvanceReceiptSaleOrderCancellation.setCancelType("S");  // HardCoded
                    dtAdvanceReceiptSaleOrderCancellation.setPOSOrderStatus(pojo.getOrderStatus());
                    
                    if (Validations.isFieldNotEmpty(pojo.getReasonforcancellation())) {
                        dtAdvanceReceiptSaleOrderCancellation.setResREJ(pojo.getReasonforcancellation());
                        
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getDatasheetno())) {
                        dtAdvanceReceiptSaleOrderCancellation.setDataSheetNO(pojo.getDatasheetno());
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getCustomercode())) {
                        dtAdvanceReceiptSaleOrderCancellation.setCustomerNO(pojo.getCustomercode());
                    }
                    if (Validations.isFieldNotEmpty(pojo.getCancelOtp())) {//Coded by Balachander V on 3.8.2020 to send Advance Sales Order Cancellation otp to SAP
                        dtAdvanceReceiptSaleOrderCancellation.setCancelledOTP(pojo.getCancelOtp());
                    }
                }

                /*  Credit Note Details*/
                if (creditnotepojo != null && !pojo.getReasonforcancellation().equalsIgnoreCase("15")) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    // dtAdvanceReceiptSaleOrderCancellation.setDocumentDate(xmlDate);
                    //  in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();

                    if (Validations.isFieldNotEmpty(creditnotepojo.getAmount())) {
                        String creditnoteAmount = String.valueOf(creditnotepojo.getAmount());
                        
                        itemTab.setCnCrAmount(new BigDecimal(df.format(creditnotepojo.getAmount())));
                    }
                    
                    if (Validations.isFieldNotEmpty(creditnotepojo.getCreditnoteno())) {
                        itemTab.setPosCreditNoteNO(creditnotepojo.getCreditnoteno());
                    }
                    
                    itemTab.setPaymentMode("CRN");
                    
                    itemTab.setSrNO(new BigInteger(Integer.toString(1)));//  Hard ceded Serial Number
                    try {
                        if (creditnotepojo.getExpirydate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(creditnotepojo.getExpirydate());
                            if (followDate != null) {
                                xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    itemTab.setDate((xmlDate));
                                }
                                
                            }
                        }
                        
                    } catch (Exception e) {
                        System.out.println("DocumentDate date not Set" + e);
                    }
                    
                    dtAdvanceReceiptSaleOrderCancellation.setCancelType("A");  // HardCoded
                    dtAdvanceReceiptSaleOrderCancellation.setPOSOrderStatus(pojo.getOrderStatus());
                    if (Validations.isFieldNotEmpty(pojo.getStoreCode())) {
                        itemTab.setSiteId(pojo.getStoreCode());
                        
                    }
                    dtAdvanceReceiptSaleOrderCancellation.getItemTable().add(itemTab);
                }  //Credit note pojo closed

                //Code added on May 26th 2010 for Non refundable credit note
                /*  Credit Note Details*/
                if (nRCreditNotePOJO != null && !pojo.getReasonforcancellation().equalsIgnoreCase("15")) {
                    itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();
                    DecimalFormat df = new DecimalFormat("#0.00");
                    // dtAdvanceReceiptSaleOrderCancellation.setDocumentDate(xmlDate);//Code commented on June 1st 2010
                    //  in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();

                    if (Validations.isFieldNotEmpty(nRCreditNotePOJO.getAmount())) {
                        String creditnoteAmount = String.valueOf(nRCreditNotePOJO.getAmount());
                        
                        itemTab.setCnCrAmount(new BigDecimal(df.format(nRCreditNotePOJO.getAmount())));
                    }
                    
                    if (Validations.isFieldNotEmpty(nRCreditNotePOJO.getCreditnoteno())) {
                        itemTab.setPosCreditNoteNO(nRCreditNotePOJO.getCreditnoteno());
                    }
                    
                    itemTab.setPaymentMode("NCR");
                    if (creditnotepojo != null) {
                        itemTab.setSrNO(new BigInteger(Integer.toString(2)));//  Hard ceded Serial Number
                    } else {
                        itemTab.setSrNO(new BigInteger(Integer.toString(1)));//  Hard ceded Serial Number
                    }
                    try {
                        if (nRCreditNotePOJO.getExpirydate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(nRCreditNotePOJO.getExpirydate());
                            if (followDate != null) {
                                xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    itemTab.setDate((xmlDate));
                                }
                                
                            }
                        }
                        
                    } catch (Exception e) {
                        System.out.println("DocumentDate date not Set" + e);
                    }
                    if (Validations.isFieldNotEmpty(pojo.getStoreCode())) {
                        itemTab.setSiteId(pojo.getStoreCode());
                        
                    }
                    
                    dtAdvanceReceiptSaleOrderCancellation.setCancelType("A");  // HardCoded
                    dtAdvanceReceiptSaleOrderCancellation.setPOSOrderStatus(pojo.getOrderStatus());
                    dtAdvanceReceiptSaleOrderCancellation.getItemTable().add(itemTab);
                }  //Credit note pojo closed

                //End of code added on May 26th 2010 for Non refundable credit note
                //AdvanceReceiptPOJO advanceReceiptPOJO = null;
                if (bean.getAdvanceReceiptPOJOs() != null) {
                    for (AdvanceReceiptPOJO advanceReceiptPOJO : bean.getAdvanceReceiptPOJOs()) {
                        advItem = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.AdvanceItem();
                        advItem.setFYear(String.valueOf(advanceReceiptPOJO.getFiscalyear()));
                        advItem.setPOSAdvNO(advanceReceiptPOJO.getDocumentno());
                        advItem.setSite(advanceReceiptPOJO.getStorecode());
                        
                        dtAdvanceReceiptSaleOrderCancellation.getAdvanceItem().add(advItem);
                    }
                    
                }
                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                System.out.println("SO Cancellation Store code " + dtAdvanceReceiptSaleOrderCancellation.getSITESEARCH());
                if (dtAdvanceReceiptSaleOrderCancellation.getSITESEARCH() != null && dtAdvanceReceiptSaleOrderCancellation.getSITESEARCH().trim().length() > 0) {
                    port.miOBASYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
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

                //dtAdvanceReceiptSaleOrderCancellation.getItemTable().add(itemTab);
                //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                /*port.miOBASYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                    return "true";
                } else {
                    return "false";
                }*/
            } else if (obj instanceof ServiceorderCancellationBean) {
                ServiceorderCancellationBean bean = (ServiceorderCancellationBean) obj;
                ServicesHeaderPOJO pojo = bean.getServiceOrderHeader();
                CreditNotePOJO creditnotepojo = bean.getCreditNotePojo();
                in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellationService service = new in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellationService();
                // in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellation port = service.getMIOBASYNAdvanceReceiptSaleOrderCancellationPort();
                in.co.titan.advancereceiptsaleordercancellation.MIOBASYNAdvanceReceiptSaleOrderCancellation port = service.getHTTPSPort();
                // TODO initialize WS operation arguments here
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation dtAdvanceReceiptSaleOrderCancellation = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation();
                // TODO process result here
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_AdvanceReceiptSaleOrderCancellation&version=3.0&Sender.Service=X&Interface=Z%5EY");
                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.AdvanceItem advItem = null;
                XMLCalendar xmlDate = null;
                if (pojo != null) {
                    
                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(pojo.getDateofcancellation());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(pojo.getCancelledTime());
                        if (createdDate != null) {
                            xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    dtAdvanceReceiptSaleOrderCancellation.setCancelledTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                dtAdvanceReceiptSaleOrderCancellation.setCancelledDate(xmlDate);
                                
                            }
                        }
                    } catch (Exception e) {
                    }
                    dtAdvanceReceiptSaleOrderCancellation.setCancelledBy(pojo.getCancelledBy());
                    dtAdvanceReceiptSaleOrderCancellation.setSITESEARCH(pojo.getStoreCode());
                    if (Validations.isFieldNotEmpty(pojo.getServiceorderno())) {
                        dtAdvanceReceiptSaleOrderCancellation.setSONO(pojo.getServiceorderno());
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getServiceorderno())) {
                        dtAdvanceReceiptSaleOrderCancellation.setSaleOrderNO(pojo.getServiceorderno());
                    }
                    
                    dtAdvanceReceiptSaleOrderCancellation.setCancelType("S");  // HardCoded
                    dtAdvanceReceiptSaleOrderCancellation.setPOSOrderStatus(pojo.getOrderstatus());
                    
                    if (Validations.isFieldNotEmpty(pojo.getReasonforcancellation())) {
                        dtAdvanceReceiptSaleOrderCancellation.setResREJ(pojo.getReasonforcancellation());
                        
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getDataSheetNo())) {
                        dtAdvanceReceiptSaleOrderCancellation.setDataSheetNO(pojo.getDataSheetNo());
                    }
                    
                    if (Validations.isFieldNotEmpty(pojo.getCustomercode())) {
                        dtAdvanceReceiptSaleOrderCancellation.setCustomerNO(pojo.getCustomercode());
                    }
                    
                }

                /*  Credit Note Details*/
                if (creditnotepojo != null) {
                    DecimalFormat df = new DecimalFormat("#0.00");
                    dtAdvanceReceiptSaleOrderCancellation.setDocumentDate(xmlDate);
                    //  in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable itemTab = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.ItemTable();

                    if (Validations.isFieldNotEmpty(creditnotepojo.getAmount())) {
                        String creditnoteAmount = String.valueOf(creditnotepojo.getAmount());
                        
                        itemTab.setCnCrAmount(new BigDecimal(df.format(creditnotepojo.getAmount())));
                    }
                    try {
                        if (creditnotepojo.getExpirydate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(creditnotepojo.getExpirydate());
                            if (followDate != null) {
                                xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    itemTab.setDate((xmlDate));
                                }
                                
                            }
                        }
                        
                    } catch (Exception e) {
                        System.out.println("DocumentDate date not Set" + e);
                    }
                    
                    if (Validations.isFieldNotEmpty(creditnotepojo.getCreditnoteno())) {
                        itemTab.setPosCreditNoteNO(creditnotepojo.getCreditnoteno());
                    }
                    
                    itemTab.setPaymentMode("CRN");
                    
                    itemTab.setSrNO(new BigInteger(Integer.toString(1)));//  Hard ceded Serial Number

                    dtAdvanceReceiptSaleOrderCancellation.setCancelType("A");  // HardCoded
                    dtAdvanceReceiptSaleOrderCancellation.setPOSOrderStatus(pojo.getOrderstatus());
                    
                }  //Credit note pojo closed
                if (bean.getAdvanceReceiptPOJOs() != null) {
                    for (AdvanceReceiptPOJO advanceReceiptPOJO : bean.getAdvanceReceiptPOJOs()) {
                        advItem = new in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellation.AdvanceItem();
                        advItem.setFYear(String.valueOf(advanceReceiptPOJO.getFiscalyear()));
                        advItem.setPOSAdvNO(advanceReceiptPOJO.getDocumentno());
                        advItem.setSite(advanceReceiptPOJO.getStorecode());
                        
                        dtAdvanceReceiptSaleOrderCancellation.getAdvanceItem().add(advItem);
                    }
                    
                }
                if (Validations.isFieldNotEmpty(pojo.getStoreCode())) {
                    itemTab.setSiteId(pojo.getStoreCode());
                    
                }
                
                dtAdvanceReceiptSaleOrderCancellation.getItemTable().add(itemTab);

                //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
                if (dtAdvanceReceiptSaleOrderCancellation.getSITESEARCH() != null && dtAdvanceReceiptSaleOrderCancellation.getSITESEARCH().trim().length() > 0) {
                    port.miOBASYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
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
                /* port.miOBASYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);

//                if (creditnotepojo == null) {
//                    in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellationResponse result = port.miOBSYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
//                    result.getMsgType();
//                    return result.getMsgType();
//                } else if (Validations.isFieldNotEmpty(creditnotepojo.getCreditnoteno())) {
//                    in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellationResponse result = port.miOBSYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
//                    result.getCreditNoteNO();
//                    return result.getCreditNoteNO();
//                } else {
//
//                    in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellationResponse result = port.miOBSYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
//                    result.getMsgType();
//                    return result.getMsgType();
//
//                }



//                in.co.titan.advancereceiptsaleordercancellation.DTAdvanceReceiptSaleOrderCancellationResponse result = port.miOBSYNAdvanceReceiptSaleOrderCancellation(dtAdvanceReceiptSaleOrderCancellation);
//                 
//               
//                    result.getCreditNoteNO();
//                    return result.getCreditNoteNO();


                //                result.getCreditNoteNO();



                // return result.getCreditNoteNO();

                Map responseContext = ((BindingProvider) port).getResponseContext();
                Integer responseCode = (Integer) responseContext.get(MessageContext.HTTP_RESPONSE_CODE);
                if (responseCode.intValue() == 200) {
                    return "true";
                } else {
                    return "false";
                }*/
            } else {
                return null;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        
    }

    /**
     * Get SAP Refernce for Credit Note
     */
    public boolean checkSapRefIdForCreditNoteNo(Connection con, String creditnoteno) {
        boolean status = false;
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        String sapRefID;
        try {
            pstmt = con.createStatement();
            searchstatement = "select referencesapid from tbl_creditnote where creditnoteno='" + creditnoteno + "'";
            rs = pstmt.executeQuery(searchstatement);
            sapRefID = null;
            if (rs.next()) {
                sapRefID = rs.getString("referencesapid");
                if (Validations.isFieldNotEmpty(sapRefID)) {
                    status = true;
                }
            }
        } catch (SQLException ex) {
            
        } finally {
            pstmt = null;
            searchstatement = null;
            rs = null;
        }
        return status;
    }

    /**
     * Find if the credit note is available
     */
    public boolean getCredditNoteNo(Connection con, String creditnoteno) {
        boolean status = false;
        Statement pstmt;
        String searchstatement;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            searchstatement = "select * from tbl_creditnote where creditnoteno='" + creditnoteno + "'and status='OPEN'";
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {
                status = true;
            }
        } catch (SQLException ex) {
        } finally {
            pstmt = null;
            rs = null;
            searchstatement = null;
        }
        return status;
    }

    /**
     * Update SAP Reference for Credit Note
     */
    public int updateSapRefId(Connection con, String sapRefNo, String creditNo, String storeCode, int fiscalYear) {
        int result = 0;
        PreparedStatement stmtUpdate;
        try {
            stmtUpdate = con.prepareStatement("UPDATE tbl_creditnote SET referencesapid = ? where creditnoteno=? and storecode=? and fiscalyear = ?");
            stmtUpdate.setString(1, sapRefNo);
            stmtUpdate.setString(2, creditNo);
            stmtUpdate.setString(3, storeCode);
            stmtUpdate.setInt(4, fiscalYear);
            result = stmtUpdate.executeUpdate();
        } catch (Exception ed) {
            ed.printStackTrace();
        } finally {
            stmtUpdate = null;
        }
        return result;
    }

    /**
     * Update SAP Reference for Credit Note
     */
    public int updateSapRefId_ByCreditNoteRefNo(Connection con, String sapRefNo, String creditNoteRefNo, int fiscalyear) {
        int result = 0;
        try {
            PreparedStatement stmtUpdate;//Code commented and changed for fiscal year prob on April 5th 2010
            //  stmtUpdate = con.prepareStatement("UPDATE tbl_creditnote SET referencesapid = ? where refno=?  and fiscalyear = ?");
            stmtUpdate = con.prepareStatement("UPDATE tbl_creditnote SET referencesapid = ? where refno=?");
            stmtUpdate.setString(1, sapRefNo);
            stmtUpdate.setString(2, creditNoteRefNo);
            //  stmtUpdate.setInt(3, fiscalyear);
            result = stmtUpdate.executeUpdate();
        } catch (Exception ed) {
            ed.printStackTrace();
        }
        return result;
    }

    /**
     * Close all Credit Note which are expired
     */
    public int closeAllExpiredCreditNotes(Connection con, int sysDate) {
        
        int result = 0;
        PreparedStatement stmtUpdate;
        try {
            stmtUpdate = con.prepareStatement("UPDATE tbl_creditnote SET status = 'CLOSED' where expirydate < ?");
            stmtUpdate.setInt(1, sysDate);
            result = stmtUpdate.executeUpdate();
        } catch (Exception ed) {
            ed.printStackTrace();
        } finally {
            stmtUpdate = null;
        }
        return result;
    }
}

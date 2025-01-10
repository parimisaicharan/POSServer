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
 * Data Object for SiteMaster 
 * 
 */
package ISRetail.quotations;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.XMLCalendar;
import ISRetail.Webservices.DataObject;
import ISRetail.salesorder.*;
import ISRetail.SalesOrderBilling.SalesOrderBillingPOJO;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.Webservice;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.article.ArticleDO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.creditnote.CreditNotePOJO;
import ISRetail.inquiry.CharacteristicDO;
import ISRetail.inquiry.ClinicalHistoryDO;
import ISRetail.inquiry.ClinicalHistoryPOJO;
import ISRetail.inquiry.ContactLensDO;
import ISRetail.inquiry.ContactLensPOJO;
import ISRetail.inquiry.Inquiry_POS;
import ISRetail.inquiry.PrescriptionDO;
import ISRetail.inquiry.PrescriptionPOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author Administrator
 */
public class QuotationsHeaderDO implements Webservice {

//    public int saveSaleorderheader(SalesOrderHeaderPOJO salesorderheaderpojoobj, Connection conn) throws SQLException{
//      int res = 0;
//        try { 
//            SiteMasterDO siteMasterDO = new SiteMasterDO();
//            String insertstr = "insert into tbl_salesorderheader values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            PreparedStatement pstmt = conn.prepareStatement(insertstr);
//            pstmt.setString(1, salesorderheaderpojoobj.getStoreCode());
//            pstmt.setInt(2, MainForm1.fiscalYear);
//            pstmt.setString(3, salesorderheaderpojoobj.getSaleorderno());
//            pstmt.setInt(4, salesorderheaderpojoobj.getSaleorderdate());
//            pstmt.setString(5, salesorderheaderpojoobj.getCustomercode());
//            pstmt.setString(6, salesorderheaderpojoobj.getDatasheetno());
//            pstmt.setString(7, salesorderheaderpojoobj.getPrintordertype());
//            pstmt.setString(8, salesorderheaderpojoobj.getSegmentSize());
//            pstmt.setString(9, salesorderheaderpojoobj.getSegmentHeight());
//            pstmt.setString(10, salesorderheaderpojoobj.getEd());
//            pstmt.setString(11, salesorderheaderpojoobj.getDistancepd());
//            pstmt.setString(12, salesorderheaderpojoobj.getNearpd());
//            pstmt.setString(13, salesorderheaderpojoobj.getFittinglabInstruction());
//            pstmt.setString(14, salesorderheaderpojoobj.getLensvendorInstruction());
//            pstmt.setInt(15, salesorderheaderpojoobj.getPriority());
//            pstmt.setInt(16, salesorderheaderpojoobj.getPlannedDate());
//            pstmt.setInt(17, salesorderheaderpojoobj.getProposedDate());
//            pstmt.setInt(18, salesorderheaderpojoobj.getTargetcompletedate());
//            pstmt.setString(19, salesorderheaderpojoobj.getVerifiedby());
//            pstmt.setDouble(20, salesorderheaderpojoobj.getAmount());
//            pstmt.setString(21, salesorderheaderpojoobj.getOldSaleorderNo());
//            pstmt.setString(22, salesorderheaderpojoobj.getReasonforReturn());
//            pstmt.setString(23, salesorderheaderpojoobj.getSaletype());
//            pstmt.setString(24, "");
//            pstmt.setString(25, salesorderheaderpojoobj.getReleaseStatus());
//            pstmt.setString(26, "");
//            pstmt.setString(27, null);
//            pstmt.setString(28, salesorderheaderpojoobj.getOrderStatus());
//            pstmt.setString(29, salesorderheaderpojoobj.getCreatedBy());
//            pstmt.setInt(30, siteMasterDO.getSystemDate(conn));
//            pstmt.setString(31, salesorderheaderpojoobj.getCreatedTime());
//            pstmt.setString(32, "");
//            pstmt.setInt(33, 0);
//            pstmt.setString(34, "");
//            pstmt.setString(35, "");
//            pstmt.setString(36, "");
//            res = pstmt.executeUpdate();
//            return res;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new SQLException();
//               }
//      
//    }
// public int saveQuotationHeader(QuotationsHeaderPOJO quotationheaderpojoobj, Connection conn) throws SQLException
//    {
//   int res = 0;
//         try {
//            String insertstr = "insert into tbl_quotationheader values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//            PreparedStatement pstmt = conn.prepareStatement(insertstr);
//                pstmt.setString(1, quotationheaderpojoobj.getStoreCode());
//                pstmt.setInt(2, quotationheaderpojoobj.getFiscalYear());
//                pstmt.setString(3, quotationheaderpojoobj.getQuotationno());
//                pstmt.setInt(4,quotationheaderpojoobj.getQuotationdate());
//                pstmt.setString(5,quotationheaderpojoobj.getCustomercode());
//                pstmt.setString(6,quotationheaderpojoobj.getDatasheetno());
//                pstmt.setString(7, quotationheaderpojoobj.getPrintordertype()); 
//                pstmt.setInt(8,quotationheaderpojoobj.getValidito());
//                pstmt.setDouble(9,quotationheaderpojoobj.getAmount());
//                pstmt.setString(10,quotationheaderpojoobj.getOrderStatus());
//                pstmt.setString(11,quotationheaderpojoobj.getCreatedBy());
//                pstmt.setInt(12,quotationheaderpojoobj.getCreatedDate());
//                pstmt.setString(13,quotationheaderpojoobj.getCreatedTime());
//                pstmt.setString(14,quotationheaderpojoobj.getModifiedBy());
//                pstmt.setInt(15,quotationheaderpojoobj.getModifiedDate());
//                pstmt.setString(16,quotationheaderpojoobj.getModifiedTime());
//                
//                res = pstmt.executeUpdate();
//            }
//        catch(SQLException ex){
//            ex.printStackTrace();
//               throw new SQLException();
//                      
//            }
//             
//        return res;
//    }
//       public int insertingintoCreditNote(CreditNotePOJO creditnotepojoobj, Connection conn) {
//        try {
//            SiteMasterDO siteMasterDO = new SiteMasterDO();
//            PreparedStatement pstmt = conn.prepareStatement("insert into tbl_creditnote values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//            pstmt.setString(1, creditnotepojoobj.getStorecode());
//            pstmt.setInt(2, MainForm1.fiscalYear);
//            pstmt.setString(3, creditnotepojoobj.getCreditnoteno());
//            pstmt.setInt(4, creditnotepojoobj.getCreditnotedate());
//            pstmt.setString(5, creditnotepojoobj.getCustomercode());
//            pstmt.setString(6, creditnotepojoobj.getReferencesapid());
//            pstmt.setDouble(7, creditnotepojoobj.getAmount());
//            pstmt.setString(8, creditnotepojoobj.getReftype());
//            pstmt.setString(9, creditnotepojoobj.getRefno());
//            pstmt.setInt(10, creditnotepojoobj.getRefdate());
//            pstmt.setString(11, creditnotepojoobj.getRefsalesorderno());
//            pstmt.setInt(12, 0);
//            pstmt.setDouble(13, 0);
//            pstmt.setString(14, creditnotepojoobj.getStatus());
//            pstmt.setInt(15, creditnotepojoobj.getExpirydate());
//            pstmt.setString(16, creditnotepojoobj.getCreatedby());
//            pstmt.setInt(17, siteMasterDO.getSystemDate(conn));
//            pstmt.setString(18, creditnotepojoobj.getCreatedtime());
//            pstmt.setString(19, "");
//            pstmt.setInt(20, 0);
//            pstmt.setString(21, "");
//            int res = pstmt.executeUpdate();
//            return res;
//
//        } catch (Exception e) {
//            System.out.println("Exception:" + e);
//            return 0;
//        }
//

    // }
        public int updatingAdvancereceipt(CreditNotePOJO creditnotepojoobj, SalesOrderHeaderPOJO saleorderheaderpojoobj, Connection con) throws SQLException {
        int result = 0;
        PreparedStatement pstmt;
        try {
            String querystmt = "update tbl_advancereceiptheader set cancelledstatus=?, creditnotenopos=? where refno=? ";
            pstmt = con.prepareStatement(querystmt);
            pstmt.setString(1, creditnotepojoobj.getAdvcancelStatus());
            pstmt.setString(2, creditnotepojoobj.getCreditnoteno());
            pstmt.setString(3, saleorderheaderpojoobj.getSaleorderno());
            result = pstmt.executeUpdate();
            System.out.println("Res" + result);
            System.out.println("Updated in  tbl_Advancerecept database");


        } catch (Exception e) {
            System.out.println("Exception:" + e);
            throw new SQLException();
        }finally {
            pstmt= null;
        }
        return result;
    }

    public String selectQuotationLastnoTable(Connection conn) throws SQLException {
        String result = "";
        String quotationnumber = "";
        SiteMasterDO smdo = new SiteMasterDO();
        String siteid = smdo.getSiteNumberLogicValue(conn);
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery("select quotationno from tbl_posdoclastnumbers");
            if (rs.next()) {
                if (Validations.isFieldNotEmpty(rs.getString("quotationno"))) {
                    String qotindblastadvcancelnopos = rs.getString("quotationno");
                    System.out.println("lastqut" + rs.getString(1));
                    System.out.println("length" + rs.getString(1).length());
                    quotationnumber = "Q";
                    quotationnumber = quotationnumber + siteid;
                    String qtlastpart = qotindblastadvcancelnopos.substring(quotationnumber.length());
                    String finalnumber = quotationnumber + qtlastpart;
                    return finalnumber;

                } else {
                    String quotation = "Q";
                    Statement pstmt1 = conn.createStatement();
                    quotation = quotation + siteid;
                    for (int i = quotation.length(); i < 10; i++) {
                        quotation = quotation + "0";
                        System.out.println("quotationno" + quotation);
                    }
                    result = quotation;
                    pstmt1.executeUpdate("update tbl_posdoclastnumbers set quotationno ='" + quotation + "'");
                }
            }
            return result;
        } catch (Exception e) {
            throw new SQLException();
        }finally {
            pstmt= null;
            rs = null;
        }

    }

    /* New Quotation Serial Number */
    public int updateLastQuotationno(Connection con, String quotationNo) throws SQLException {
        int result = 0;
        Statement pstmt;
        try {
            pstmt = con.createStatement();
            String searchstatement = "update tbl_posdoclastnumbers set quotationno='" + quotationNo + "'";
            result = pstmt.executeUpdate(searchstatement);
            return result;
        } catch (SQLException sQLException) {
            throw new SQLException();
        }finally {
            pstmt= null;
        }
    }

    
/*    public int editsaleorderheader(SalesOrderHeaderPOJO saleorderheaderpojoobj, Connection conn) {
        try {
            SiteMasterDO siteMasterDO = new SiteMasterDO();
            String updateStr = " update tbl_salesorderheader set saleorderdate=?,datasheetno=?,printordertype=?,segmentsize=?,segmentheight=?,ed=?,distancepd=?,nearpd=?,fittinglabInstruction=?,lensvendor=?,priority=?,planneddate=?,proposeddate=?,targetdate=?,verifiedby=?,modifiedby=?,modifieddate=?,modifiedtime=?,oldsaleordernumber=?,reasonforreturn = ?,amount=?,releasestatus=? where saleorderno=? and storecode=? and fiscalyear = ? ";
            PreparedStatement pstmt = conn.prepareStatement(updateStr);
            pstmt.setInt(1, saleorderheaderpojoobj.getSaleorderdate());
            pstmt.setString(2, saleorderheaderpojoobj.getDatasheetno());
            pstmt.setString(3, saleorderheaderpojoobj.getPrintordertype());
            pstmt.setString(4, saleorderheaderpojoobj.getSegmentSize());
            pstmt.setString(5, saleorderheaderpojoobj.getSegmentHeight());
            pstmt.setString(6, saleorderheaderpojoobj.getEd());
            pstmt.setString(7, saleorderheaderpojoobj.getDistancepd());
            pstmt.setString(8, saleorderheaderpojoobj.getNearpd());
            pstmt.setString(9, saleorderheaderpojoobj.getFittinglabInstruction());
            pstmt.setString(10, saleorderheaderpojoobj.getLensvendorInstruction());
            pstmt.setInt(11, saleorderheaderpojoobj.getPriority());
            pstmt.setInt(12, saleorderheaderpojoobj.getPlannedDate());
            pstmt.setInt(13, saleorderheaderpojoobj.getProposedDate());
            pstmt.setInt(14, saleorderheaderpojoobj.getTargetcompletedate());
            pstmt.setString(15, saleorderheaderpojoobj.getVerifiedby());
            // pstmt.setString(16, saleorderheaderpojoobj.getSaletype());
            pstmt.setString(16, saleorderheaderpojoobj.getModifiedBy());
            pstmt.setInt(17, siteMasterDO.getSystemDate(conn));
            pstmt.setString(18, saleorderheaderpojoobj.getModifiedTime());
            pstmt.setString(19, saleorderheaderpojoobj.getOldSaleorderNo());
            pstmt.setString(20, saleorderheaderpojoobj.getReasonforReturn());
            pstmt.setDouble(21, saleorderheaderpojoobj.getAmount());
            pstmt.setString(22, saleorderheaderpojoobj.getReleaseStatus());
            pstmt.setString(23, saleorderheaderpojoobj.getSaleorderno());
            pstmt.setString(24, saleorderheaderpojoobj.getStoreCode());
            pstmt.setInt(25, saleorderheaderpojoobj.getFiscalYear());

            int res = pstmt.executeUpdate();
            System.out.println("Res" + res);
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }
 * /

    /*-----------------------Getting Net Bill Amount For Sale order Cancellation---------------*/
    public double getNetBillAmount(Connection con, String salorderno) {
    Statement pstmt;
    ResultSet rs;          
    String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select amount from tbl_salesorderheader where saleorderno='" + salorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getDouble("amount");
            } else {
                return 0;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 

    }

    /*-----------------------Getting Net Bill Amount For Sale order Cancellation---------------*/
    public int getFiscalYear(Connection con, String salorderno) {
    Statement pstmt;
    ResultSet rs;          
    String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select fiscalyear from tbl_salesorderheader where saleorderno='" + salorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getInt("fiscalyear");
            } else {
                return 0;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 

    }

    /*********************  add by vandana 26.09.2008      ***********************/
    /*-----------------------Getting verified by field by saleorderno---------------*/
    public String getVarifiedBy(Connection con, String salorderno) {
    Statement pstmt;
    ResultSet rs;          
    String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select verifiedby from tbl_salesorderheader where saleorderno='" + salorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 

    }

    /*-----------------------Getting verified by field by saleorderno---------------*/
    public String getDatasheetNO(Connection con, String salorderno) {
    Statement pstmt;
    ResultSet rs;          
    String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select datasheetno from tbl_salesorderheader where saleorderno='" + salorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 

    }

    /********************* end-------- add by vandana 26.09.2008      ***********************/
    /*******Getting Bill amount from salesorder to advance receipt **********************/
    public double getBillamountforadvrecpt(String salesordernum, Connection con) {
        Statement pstmt;
        ResultSet rs;          
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select amount from tbl_salesorderheader where saleorderno='" + salesordernum + "'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getDouble(1);
            } else {
                return 0;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 
    }

    /*******End of Getting Bill amount from salesorder to advance receipt ***************/
    /*********************update release status and sale type from advance receipt*********************/
    public SalesOrderHeaderPOJO getstatusandsaletypeandprintoption(Connection con, SalesOrderHeaderPOJO pojoobj, String saleorderno) {

        Statement pstmt;
        ResultSet rs;          
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select saletype,releasestatus,printordertype,creditsalereference from tbl_salesorderheader where saleorderno='" + saleorderno + "'";
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {
                pojoobj.setSaletype(rs.getString("saletype"));
                pojoobj.setReleaseStatus(rs.getString("releasestatus"));
                pojoobj.setPrintordertype(rs.getString("printordertype"));
                pojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                return pojoobj;
            }
            return null;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 



    }

    /*********************update release status and sale type from advance receipt*********************/
    public int updatestatusandsaletype(String sonumber, String status, String saletype, String creditsalereference, Connection con) {
        Statement pstmt;    
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "update tbl_salesorderheader set saletype='" + saletype + "',releasestatus='" + status + "',creditsalereference='" + creditsalereference + "' where saleorderno='" + sonumber + "'";
            System.out.println("searchstmt" + searchstatement);
            int res = pstmt.executeUpdate(searchstatement);
            return res;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
        }finally {
                pstmt=null;          
		searchstatement=null;
        } 


        return 0;
    }

    /*********************End of update release status and sale type from advance receipt*********************/
    /*********************End of update release status and sale type from advance receipt*********************/
    /*******End of Getting Bill amount from salesorder to advance receipt ***************/
    /*Credit Note Number Retrieving From Database*/
    String getMaxCreditNoteNumber(Connection con) {
        Statement pstmt;
        ResultSet rs;          
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select creditnoteno from tbl_posdoclastnumbers ";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 



    }

    /* Sale order Serial number retrieving from Database*/
    public String getMaxSaleorderNo(Connection con) {
        Statement pstmt;
        ResultSet rs;          
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select saleorderno from tbl_posdoclastnumbers ";
            rs = pstmt.executeQuery(searchstatement);

            if (rs.next()) {
                return rs.getString(1);

            } else {
                return null;
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 

    }

    public int updateLastCreditNoteNumber(Connection con, String CreditNoteNumber1) throws SQLException {
        int result = 0;
        Statement pstmt;        
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "update tbl_posdoclastnumbers set creditnoteno='" + CreditNoteNumber1 + "'";
            result = pstmt.executeUpdate(searchstatement);
            System.out.println("Updated Credit Note Number:" + searchstatement);
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            throw new SQLException();
        }finally {
                pstmt=null;          
		searchstatement=null;
        } 
        return result;
    }

    public ArrayList getSaleorderlist(Connection conn, String searchstatement) {
        Statement pstmt;
        ResultSet rs;                  
        try {
            pstmt = conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<SalesOrderBillingPOJO> saleorderlist = new ArrayList<SalesOrderBillingPOJO>();
            int i = 0;
            while (rs.next()) {
                SalesOrderBillingPOJO saleorderlistpojoobj = new SalesOrderBillingPOJO();
                saleorderlistpojoobj.setSalesorderno(rs.getString("saleorderno"));
                saleorderlistpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                saleorderlistpojoobj.setCustomercode(rs.getString("customercode"));
                saleorderlistpojoobj.setCustomername(rs.getString("firstname"));
                saleorderlistpojoobj.setCustomerlastname(rs.getString("mobileno"));
                //saleorderlistpojoobj.setDatasheetno(rs.getString(3));
                //     saleorderlistpojoobj.setStyleconsult(rs.getnarchstatement)         
                saleorderlist.add(saleorderlistpojoobj);

            }
            return saleorderlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          		
        } 
    }

    public ArrayList getSOSearchResultListBetweenTwoDates(Connection conn, String wherestatement, int creatFromDate, int createToDate) {
        PreparedStatement pstmt;
        ResultSet rs;              
        try {
            pstmt = conn.prepareStatement(wherestatement);
            pstmt.setInt(1, creatFromDate);
            pstmt.setInt(2, createToDate);
            System.out.println("SO serchstmtn=" + wherestatement);
            rs = pstmt.executeQuery();
            ArrayList<SalesOrderBillingPOJO> soList = new ArrayList<SalesOrderBillingPOJO>();
            SalesOrderBillingPOJO saleorderlistpojoobj = null;
            while (rs.next()) {
                saleorderlistpojoobj = new SalesOrderBillingPOJO();
                saleorderlistpojoobj.setSalesorderno(rs.getString("saleorderno"));
                saleorderlistpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                saleorderlistpojoobj.setCustomercode(rs.getString("customercode"));
                saleorderlistpojoobj.setCustomername(rs.getString("firstname"));
                saleorderlistpojoobj.setCustomerlastname(rs.getString("mobileno"));
                soList.add(saleorderlistpojoobj);
            }
            return soList;

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          	
        } 

    }


    /* New Sale order Serial Number */
    public int updateLastSaleorder(Connection con, String saleorderno) throws SQLException {
        int result = 0;
        Statement pstmt;        
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "update tbl_posdoclastnumbers set saleorderno='" + saleorderno + "'";
            result = pstmt.executeUpdate(searchstatement);
            return result;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            throw new SQLException();
        }finally {
                pstmt=null;          
		searchstatement=null;
        } 
    }

    
    public SalesOrderHeaderPOJO getSaleOrderHeaderBySaleOrderNo(Connection conn, String saleorderno) {

        PreparedStatement pstmt;
        ResultSet rs;              
        try {
            pstmt = conn.prepareStatement("select * from tbl_salesorderheader where saleorderno=? ");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {
                //  salesorderheaderpojoobj.setSlno(rs.getInt("slno"));
                // salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setSegmentSize(rs.getString("segmentsize"));
                salesorderheaderpojoobj.setSegmentHeight(rs.getString("segmentheight"));
                salesorderheaderpojoobj.setEd(rs.getString("ed"));
                salesorderheaderpojoobj.setDistancepd(rs.getString("distancepd"));
                salesorderheaderpojoobj.setNearpd(rs.getString("nearpd"));
                salesorderheaderpojoobj.setFittinglabInstruction(rs.getString("fittinglabinstruction"));
                salesorderheaderpojoobj.setLensvendorInstruction(rs.getString("lensvendor"));
                salesorderheaderpojoobj.setPriority(rs.getInt("priority"));
                salesorderheaderpojoobj.setPlannedDate(rs.getInt("planneddate"));
                salesorderheaderpojoobj.setProposedDate(rs.getInt("proposeddate"));
                salesorderheaderpojoobj.setTargetcompletedate(rs.getInt("targetdate"));
                salesorderheaderpojoobj.setVerifiedby(rs.getString("verifiedby"));
                salesorderheaderpojoobj.setAmount(rs.getDouble("amount"));
                salesorderheaderpojoobj.setSaletype(rs.getString("saletype"));
                salesorderheaderpojoobj.setReleaseStatus(rs.getString("releasestatus"));
                salesorderheaderpojoobj.setOrderStatus(rs.getString("orderstatus"));
                salesorderheaderpojoobj.setOldSaleorderNo(rs.getString("oldsaleordernumber"));
                salesorderheaderpojoobj.setReasonforReturn(rs.getString("reasonforreturn"));
                salesorderheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                salesorderheaderpojoobj.setCreditsalereference(rs.getString("creditsalereference"));
                salesorderheaderpojoobj.setCreatedBy(rs.getString("createdby"));
                salesorderheaderpojoobj.setCreatedDate(rs.getInt("createddate"));
                salesorderheaderpojoobj.setCreatedTime(rs.getString("createdtime"));
                salesorderheaderpojoobj.setModifiedBy(rs.getString("modifiedby"));
                salesorderheaderpojoobj.setModifiedDate(rs.getInt("modifieddate"));
                salesorderheaderpojoobj.setModifiedTime(rs.getString("modifiedtime"));

                try {
                } catch (Exception e) {
                }

                return salesorderheaderpojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          	
        } 

    }
    /*credit note display-to get the reasons for cancellation*/

    public String getReasonsforCancel(String Saleorderno, Connection conn) {
        String reasons = null;
        String cancelreason = "";
        PopulateData populateData = new PopulateData();
        Properties retReasonKeyVals1 = populateData.populateReasonsforSaleorderCancel(conn, null);
        PreparedStatement pstmt;
        ResultSet rs;              
        try {
            pstmt = conn.prepareStatement("select reasonforcancellation from tbl_salesorderheader where saleorderno='" + Saleorderno + "'");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                reasons = rs.getString("reasonforcancellation");
            }
            cancelreason = retReasonKeyVals1.getProperty(reasons);
        } catch (Exception es) {
            es.printStackTrace();
        }finally {
                pstmt=null;
                rs=null;          	
        } 
        return cancelreason;
    }
    
    /*End if credit note display-getting reasons for cancellation*/

    public SalesOrderHeaderPOJO getSaleordercancellationDetailsBySaleorderNo(Connection conn, String saleorderno) {

        PreparedStatement pstmt;
        ResultSet rs;              
        try {
            pstmt = conn.prepareStatement("select * from tbl_salesorderheader where saleorderno=? ");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {
                salesorderheaderpojoobj.setStoreCode(rs.getString("storecode"));
                salesorderheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                salesorderheaderpojoobj.setSaleorderno(rs.getString("saleorderno"));
                salesorderheaderpojoobj.setSaleordercancellationdate(rs.getInt("dateofcancellation"));
                salesorderheaderpojoobj.setCustomercode(rs.getString("customercode"));
                salesorderheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                salesorderheaderpojoobj.setReasonforcancellation(rs.getString("reasonforcancellation"));
                return salesorderheaderpojoobj;
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          	
        } 

    }

    public String getSaleorderNo(int customerslno) {
        String customercode = "SOO";
        for (int i = 0; i < 10 - (3 + String.valueOf(customerslno).length()); i++) {
            customercode = customercode + "0";
        }
        customercode = customercode + String.valueOf(customerslno);
        return customercode;
    }

    public QuotationsHeaderPOJO getQuotationListforThread(Connection conn, String searchstatement) {
    
        Statement pstmt;
        ResultSet rs;              
        try {
            QuotationsHeaderPOJO quotationsheaderpojoobj = new QuotationsHeaderPOJO();
            pstmt = conn.createStatement();
            //String searchstatement = "select * from customermasternew where  " + wherestatement;
            rs = pstmt.executeQuery(searchstatement);
            while (rs.next()) {
                quotationsheaderpojoobj.setStoreCode(rs.getString("storecode"));
                quotationsheaderpojoobj.setFiscalYear(rs.getInt("fiscalyear"));
                quotationsheaderpojoobj.setQuotationno(rs.getString("quotationno"));
                quotationsheaderpojoobj.setQuotationdate(rs.getInt("quotationdate"));
                quotationsheaderpojoobj.setCustomercode(rs.getString("customercode"));
                quotationsheaderpojoobj.setDatasheetno(rs.getString("datasheetno"));
                quotationsheaderpojoobj.setPrintordertype(rs.getString("printordertype"));
                quotationsheaderpojoobj.setValidito(rs.getInt("validto"));
                quotationsheaderpojoobj.setAmount(rs.getDouble("amount"));
                quotationsheaderpojoobj.setCreatedBy(rs.getString("createdby"));
                quotationsheaderpojoobj.setCreatedDate(rs.getInt("createddate"));
                quotationsheaderpojoobj.setCreatedTime(rs.getString("createdtime"));
                quotationsheaderpojoobj.setCreatedBy(rs.getString("modifiedby"));
                quotationsheaderpojoobj.setCreatedDate(rs.getInt("modifieddate"));
                quotationsheaderpojoobj.setCreatedTime(rs.getString("modifiedtime"));
            }
            return quotationsheaderpojoobj;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 
    }


    /*=====================Validating OLD Sale orderNo ======================*/
    public String getoldSaleorderNo(Connection con, String salorderno) {
        Statement pstmt;
        ResultSet rs;          
        String searchstatement;
        try {
            pstmt = con.createStatement();
            searchstatement = "select saleorderno from tbl_salesorderheader where saleorderno='" + salorderno + "' and orderstatus='BILLED'";
            rs = pstmt.executeQuery(searchstatement);
            if (rs.next()) {
                return rs.getString(1);
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
                pstmt=null;
                rs=null;          
		searchstatement=null;
        } 
    }

    
    public int getSaleOrderDateBySaleOrderNo(Connection conn, String saleorderno) {
    
        PreparedStatement pstmt;
        ResultSet rs;              
        try {
            pstmt = conn.prepareStatement("select saleorderdate from tbl_salesorderheader where saleorderno like'%" + saleorderno + "%'");
            rs = pstmt.executeQuery();
            SalesOrderHeaderPOJO salesorderheaderpojoobj = new SalesOrderHeaderPOJO();
            if (rs.next()) {

                int salesorderdate = rs.getInt(1);

                return salesorderdate;
            } else {
                return 0;
            }


        } catch (SQLException sQLException) {
            System.out.println("exception in tbl_saleorderheader1 Searching=" + sQLException);
            sQLException.printStackTrace();
            return 0;
        }finally {
                pstmt=null;
                rs=null;          	
        } 
    }

    public in.co.titan.quotation.DTQuotation.QuotationCondition setQoConDition(ConditionTypePOJO conditionTypePOJO, int refLineItemNo, int condLineItemNo, String typeOfCondition) {
        in.co.titan.quotation.DTQuotation.QuotationCondition qOCondition = null;
        DecimalFormat df = new DecimalFormat("0.00");
        if (conditionTypePOJO != null) {
            qOCondition = new in.co.titan.quotation.DTQuotation.QuotationCondition();
            qOCondition.setRefLineItem(new BigInteger(Integer.toString(refLineItemNo)));
            qOCondition.setConditionLineItemNo(new BigInteger(Integer.toString(condLineItemNo)));
            //  if(conditionTypePOJO.getPromotionId()!=null)
            //     sOCondition.setPromotionID(conditionTypePOJO.getPromotionId());

//            if (Validations.isFieldNotEmpty(conditionTypePOJO.getFreeGoodsCategory())) {
//                qOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
//            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getDummyconditiontype())) {
                qOCondition.setPOSCondType(conditionTypePOJO.getDummyconditiontype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getValue())) {
                qOCondition.setAmount(new BigDecimal(df.format(conditionTypePOJO.getValue())));
            }

//            if (Validations.isFieldNotEmpty(conditionTypePOJO.getPromotionId())) {
//                qOCondition.setPromotionID(conditionTypePOJO.getPromotionId());
//            }
//
//            if (Validations.isFieldNotEmpty(conditionTypePOJO.getFreeGoodsCategory())) {
//                qOCondition.setFreeGoodsCat(conditionTypePOJO.getFreeGoodsCategory());
//            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getCalculationtype())) {
                qOCondition.setCalcType(conditionTypePOJO.getCalculationtype());
            }

            if (Validations.isFieldNotEmpty(conditionTypePOJO.getNetAmount())) {
                qOCondition.setNetAmount(new BigDecimal(df.format(conditionTypePOJO.getNetAmount())));
            }

            if (Validations.isFieldNotEmpty(typeOfCondition)) {
                qOCondition.setTypeOfCondition(typeOfCondition);
            }



        }
        return qOCondition;
    }
 public int archiveAllQuotationTables(Connection con,int fiscalYear) throws SQLException{
        int recDeleted = 0;
        PreparedStatement pstmt;
        String query;
        ClinicalHistoryDO clinicalHistoryDO;
        PrescriptionDO prescriptionDO;
        ContactLensDO contactLensDO;
        CharacteristicDO characteristicDO;
        try {
            clinicalHistoryDO = new ClinicalHistoryDO();
            prescriptionDO = new PrescriptionDO();
            characteristicDO = new CharacteristicDO();
            contactLensDO = new ContactLensDO();
            int res = characteristicDO.archiveAllCharacteristicsForFiscalYear(fiscalYear, con, "QO");
            recDeleted = recDeleted + res;
            res = clinicalHistoryDO.archiveAllClinicalHistoryForFiscalYear(fiscalYear, con, "QO");
            recDeleted = recDeleted + res;
            res = prescriptionDO.archiveAllPrescriptionForFiscalYear(fiscalYear, con, "QO");
            recDeleted = recDeleted + res;
            res = contactLensDO.archiveAllContactLensForFiscalYear(fiscalYear, con, "QO");
            recDeleted = recDeleted + res;

            query = "delete from tbl_quotation_condtion where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
            
            query = "delete from tbl_quotationlineitems where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
            
            query = "delete from tbl_quotation_condtion where fisaclyear= ?";
            pstmt = con.prepareStatement(query);
            res = pstmt.executeUpdate();
            recDeleted = recDeleted + res;
        } catch (Exception e) {
            e.printStackTrace();
            recDeleted=0;
            throw new SQLException();
        } finally {
            query = null;
            clinicalHistoryDO = null;
            prescriptionDO = null;
            characteristicDO = null;
            contactLensDO = null;
        }
        return recDeleted;
    }

    public String execute(DataObject obj, String updateType) {

        ClinicalHistoryPOJO cliniPojo = null;
        PrescriptionPOJO specPojo = null;
        try { // Call Web Service Operation
            in.co.titan.quotation.MIOBASYNQuotationService service = new in.co.titan.quotation.MIOBASYNQuotationService();
            in.co.titan.quotation.MIOBASYNQuotation port = service.getMIOBASYNQuotationPort();
            // TODO initialize WS operation arguments here
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());//XIConnectionDetailsPojo.getUsername()"test_enteg"
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//"enteg321"
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_Quotation&version=3.0&Sender.Service=x&Interface=x%5Ex");

            in.co.titan.quotation.DTQuotation mtQuotation = new in.co.titan.quotation.DTQuotation();

            ContactLensPOJO clensPojo = null;


            /******************/
            QuotationsHeaderPOJO quotationsHeaderPOJO = null;

            Collection qArrayList = null;

            MsdeConnection msdeconn = new MsdeConnection();
            Connection con = msdeconn.createConnection();

            /*Retrieving CompanyCode from siteMaster*/
            SiteMasterDO smdo = new SiteMasterDO();
            SiteMasterPOJO ampojoobj = new SiteMasterPOJO();
            String companyCode1 = smdo.getSiteCompanyCode(con);
            System.out.println("Company Code:" + companyCode1);
            ampojoobj.setCompanycode(companyCode1);

            if (obj instanceof QuotationsBean) {
                QuotationsBean masterBean = new QuotationsBean();
                masterBean = (QuotationsBean) obj;
                quotationsHeaderPOJO = masterBean.getQuotationHeaderPOJO();
                qArrayList = masterBean.getQuotationDetailsPOJOs();

                cliniPojo = masterBean.getCliniPojo();
                specPojo = masterBean.getPresPojo();
                clensPojo = masterBean.getClensPojo();
                String flagcat = null;
                if (cliniPojo != null && specPojo != null && clensPojo != null) {
                    flagcat = "C3";
                } else if (cliniPojo != null && specPojo != null) {
                    flagcat = "CP";
                } else if (cliniPojo != null && clensPojo != null) {
                    flagcat = "CC";
                } else if (specPojo != null && clensPojo != null) {
                    flagcat = "PC";
                } else if (cliniPojo != null) {
                    flagcat = "CH";
                } else if (specPojo != null) {
                    flagcat = "PR";
                } else if (clensPojo != null) {
                    flagcat = "CL";
                } else {
                    flagcat = " ";
                }

                if (flagcat != null) {
                    mtQuotation.setFlagCat(flagcat);//1
                }
                if (updateType != null) {
                    mtQuotation.setFlagMode(updateType);//2
                }


                mtQuotation.setCompanyCode(flagcat);


                //      if (plantobj != null) {

//                    mtSalesOrder.setDistributionChannel(plantobj.getDistrChannel());//3
//                    mtSalesOrder.setDivision(plantobj.getDivision());//4
//                    mtSalesOrder.setSalesOrganization(plantobj.getSalesOrg());//5
//                    mtSalesOrder.setSalesDocType("TEST");//6
                //mtSalesOrder.setSaleType(inquiryno)

                //     }

                mtQuotation.setParameterID("BKSAL");                  //   PARAMETER ID (mandatory)
                // mtSalesOrder.setDivision(salesorderheaderpojoobj.getDivision());
                // System.out.println("DIVISION:"+salesorderheaderpojoobj.getDivision());



                if (Validations.isFieldNotEmpty(companyCode1)) {
                    mtQuotation.setCompanyCode(companyCode1);
                } //  COMPANY CODE (mandatory)

                if (quotationsHeaderPOJO != null) {

                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getStoreCode())) {
                        mtQuotation.setStoreCode(quotationsHeaderPOJO.getStoreCode());
                        mtQuotation.setSITESEARCH(quotationsHeaderPOJO.getStoreCode());
                    }  // STORE CODE  (mandatory)

                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getFiscalYear())) {
                        mtQuotation.setFYear(String.valueOf(quotationsHeaderPOJO.getFiscalYear()));
                    }       // FISCAL YEAR  (mandatory)

                    mtQuotation.setQuotationNo(quotationsHeaderPOJO.getQuotationno());  // SALE ORDERNO (mandatory)
                  
                    System.out.println("quotation valid to"+quotationsHeaderPOJO.getValidito());
                    try {
                        if (quotationsHeaderPOJO.getValidito() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getValidito());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtQuotation.setValidityPeriod(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        System.out.println("setvalidityDate Not Set" + e);
                    }

                    
                    try {
                        if (quotationsHeaderPOJO.getQuotationdate() != 0) {
                            java.util.Date date1 = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getQuotationdate());
                            if (date1 != null) {
                                XMLCalendar xmlDate = new XMLCalendar(date1);
                                if (xmlDate != null) {
                                    mtQuotation.setDocumentDate(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        System.out.println("setSaleOderDate Not Set" + e);
                    }

                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getCustomercode())) {
                        mtQuotation.setCustomerNo(quotationsHeaderPOJO.getCustomercode());
                    }

                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getDatasheetno())) {
                        mtQuotation.setDataSheetNO(quotationsHeaderPOJO.getDatasheetno());
                    }


                    try {
                        System.out.println("setSaleOderDate Not Set" + quotationsHeaderPOJO.getPrintordertype());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getPrintordertype())) {
                        mtQuotation.setOrderType(quotationsHeaderPOJO.getPrintordertype());
                    }//23 'Spectacle Rx' or 'Cotact lens' or 'Others;


                    if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getCreatedBy())) {
                        mtQuotation.setCreatedBy(quotationsHeaderPOJO.getCreatedBy());
                    }

                    try {
                        java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getCreatedDate());
                        Calendar createdTime = ConvertDate.getSqlTimeFromString(quotationsHeaderPOJO.getCreatedTime());
                        if (createdDate != null) {
                            XMLCalendar xmlDate = new XMLCalendar(createdDate);
                            if (createdTime != null) {
                                xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                if (xmlDate != null) {
                                    mtQuotation.setCreatedTime(xmlDate);
                                }
                            }
                            if (xmlDate != null) {
                                mtQuotation.setCreatedDate(xmlDate);
                            }

                        }
                    } catch (Exception e) {

                    }

                    if (updateType.equalsIgnoreCase("U")) {
                        if (Validations.isFieldNotEmpty(quotationsHeaderPOJO.getModifiedBy())) {
                            mtQuotation.setModifiedBy(quotationsHeaderPOJO.getModifiedBy());
                        }


                        try {
                            java.util.Date createdDate = ConvertDate.getUtilDateFromNumericDate(quotationsHeaderPOJO.getModifiedDate());
                            Calendar createdTime = ConvertDate.getSqlTimeFromString(quotationsHeaderPOJO.getModifiedTime());
                            if (createdDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(createdDate);
                                if (createdTime != null) {
                                    xmlDate.setTime(createdTime.get(Calendar.HOUR_OF_DAY), createdTime.get(Calendar.MINUTE), createdTime.get(Calendar.SECOND));
                                    if (xmlDate != null) {
                                        mtQuotation.setModifiedTime(xmlDate);
                                    }
                                }
                                if (xmlDate != null) {
                                    mtQuotation.setModifiedDate(xmlDate);
                                }

                            }
                        } catch (Exception e) {

                        }
                    }

                }
                //FLAGCAT
                //CH:ClinicalHistory
                //PR:Spectacle Prescription
                //CL:Contact Lens
                //CP:ClinicalHistory and Prescription
                //CC:ClinicalHistory and Contact Lens
                //PC:Prescription and ContactLens
                //C3:ClinicalHistory, Prescription and Contact Lens
                //FLAGMODE
                //I:Creation
                //U:Updation

                if (cliniPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("CH") || flagcat.trim().equals("CP") || flagcat.trim().equals("CC"))) {

                    /******** Setting Clinical History values *************/
                    if (Validations.isFieldNotEmpty(cliniPojo.getExaminedby())) {
                        mtQuotation.setChExaminedBy(cliniPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getPasthistory())) {
                        mtQuotation.setChPastHistory(cliniPojo.getPasthistory());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getComments())) {
                        mtQuotation.setChComments(cliniPojo.getComments());
                    }

                    if (Validations.isFieldNotEmpty(cliniPojo.getSlitlampexamination())) {
                        mtQuotation.setChSlExamination(cliniPojo.getSlitlampexamination());
                    }

                    ArrayList<in.co.titan.quotation.DTQuotation.CHRightEYE> ch_rightEyes = new ArrayList<in.co.titan.quotation.DTQuotation.CHRightEYE>();
                    ArrayList<in.co.titan.quotation.DTQuotation.CHLeftEYE> ch_leftEyes = new ArrayList<in.co.titan.quotation.DTQuotation.CHLeftEYE>();
                    /************CH RIGHT*************/
                    Map CH_RightEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "R");
                    if (CH_RightEyes != null) {
                        if (CH_RightEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.CHRightEYE righteye;
                            String desc = null, val = null;
                            Iterator chRight = CH_RightEyes.keySet().iterator();
                            if (chRight != null) {
                                while (chRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(chRight.next());
                                        val = String.valueOf(CH_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.quotation.DTQuotation.CHRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            ch_rightEyes.add(righteye);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /************CH LEFT*************/
                    Map CH_LeftEyes = Inquiry_POS.getAndsetCHCharacteristics(cliniPojo, "L");
                    if (CH_LeftEyes != null) {
                        if (CH_LeftEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.CHLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator chLeft = CH_LeftEyes.keySet().iterator();
                            if (chLeft != null) {
                                while (chLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(chLeft.next());
                                        val = String.valueOf(CH_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            lefteye = new in.co.titan.quotation.DTQuotation.CHLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            ch_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    mtQuotation.getCHLeftEYE().addAll(ch_leftEyes);
                    mtQuotation.getCHRightEYE().addAll(ch_rightEyes);
                }
                if (specPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("PR") || flagcat.trim().equals("CP") || flagcat.trim().equals("PC"))) {

                    /******** Setting Prescription values *************/
                    if (Validations.isFieldNotEmpty(specPojo.getExaminedby())) {
                        mtQuotation.setPrExaminedBy(specPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_name())) {
                        mtQuotation.setPrDocName(specPojo.getDoctor_name());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_contactno())) {
                        mtQuotation.setPrDocCno(specPojo.getDoctor_contactno());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_area())) {
                        mtQuotation.setPrDocArea(specPojo.getDoctor_area());
                    }


                    if (Validations.isFieldNotEmpty(specPojo.getDoctor_city())) {
                        mtQuotation.setPrDocCity(specPojo.getDoctor_city());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getOthername())) {
                        mtQuotation.setPrExamByOthers(specPojo.getOthername());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getFollowup())) {
                        mtQuotation.setPrFollowUp(specPojo.getFollowup());
                    }
                    try {
                        if (specPojo.getFollowupdate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(specPojo.getFollowupdate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    mtQuotation.setPrFollowDat(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Spec Rx FollowupdateNot Set" + e);
                    }


                    if (Validations.isFieldNotEmpty(specPojo.getReferred_to())) {
                        mtQuotation.setPrRefered(specPojo.getReferred_to());
                    }

                    if (Validations.isFieldNotEmpty(specPojo.getComments())) {
                        mtQuotation.setPrComments(specPojo.getComments());
                    }


                    if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                        mtQuotation.setPrPresSale(specPojo.getRight_Sale());
                    } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                        mtQuotation.setPrPresSale(specPojo.getLeft_Sale());
                    }
                    if (Validations.isFieldNotEmpty(specPojo.getRight_Sale())) {
                        mtQuotation.setPrPresSale(specPojo.getRight_Sale());
                        if (specPojo.getRight_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter())) {
                                mtQuotation.setPrPresBp("DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                mtQuotation.setPrPresBp("DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                mtQuotation.setPrPresBp("IN");
                            }
                        }
                    } else if (Validations.isFieldNotEmpty(specPojo.getLeft_Sale())) {
                        mtQuotation.setPrPresSale(specPojo.getLeft_Sale());
                        if (specPojo.getLeft_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter())) {
                                mtQuotation.setPrPresBp("DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                mtQuotation.setPrPresBp("DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                mtQuotation.setPrPresBp("IN");
                            }
                        }
                    }
                    ArrayList<in.co.titan.quotation.DTQuotation.PRRightEYE> pr_rightEyes = new ArrayList<in.co.titan.quotation.DTQuotation.PRRightEYE>();
                    ArrayList<in.co.titan.quotation.DTQuotation.PRLeftEYE> pr_leftEyes = new ArrayList<in.co.titan.quotation.DTQuotation.PRLeftEYE>();

                    /************PR RIGHT*************/
                    Map PR_RighttEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, null, "R");
                    if (PR_RighttEyes != null) {
                        if (PR_RighttEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.PRRightEYE righteye = null;
                            String desc = null, val = null;
                            Iterator prRight = PR_RighttEyes.keySet().iterator();
                            if (prRight != null) {
                                while (prRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(prRight.next());
                                        val = String.valueOf(PR_RighttEyes.get(desc));
                                        //     System.out.println("###########################################val="+val);
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.quotation.DTQuotation.PRRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            pr_rightEyes.add(righteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }


                    /************PR LEFT*************/
                    Map PR_LeftEyes = Inquiry_POS.getAndsetPRCharacteristics(specPojo, null, "L");
                    if (PR_LeftEyes != null) {
                        if (PR_LeftEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.PRLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator prLeft = PR_LeftEyes.keySet().iterator();
                            if (prLeft != null) {
                                while (prLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(prLeft.next());
                                        val = String.valueOf(PR_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            lefteye = new in.co.titan.quotation.DTQuotation.PRLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            pr_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }


                    mtQuotation.getPRLeftEYE().addAll(pr_leftEyes);
                    mtQuotation.getPRRightEYE().addAll(pr_rightEyes);
                }
                if (clensPojo != null && (flagcat.trim().equals("C3") || flagcat.trim().equals("CL") || flagcat.trim().equals("CC") || flagcat.trim().equals("PC"))) {

                    /******** Setting Contact Lens values *************/
                    if (Validations.isFieldNotEmpty(clensPojo.getExaminedby())) {
                        mtQuotation.setClExaminedBy(clensPojo.getExaminedby());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_name())) {
                        mtQuotation.setClDocName(clensPojo.getDoctor_name());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_contactno())) {
                        mtQuotation.setClDocCno(clensPojo.getDoctor_contactno());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_area())) {
                        mtQuotation.setClDocArea(clensPojo.getDoctor_area());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getDoctor_city())) {
                        mtQuotation.setClDocCity(clensPojo.getDoctor_city());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getOthername())) {
                        mtQuotation.setClExamOthers(clensPojo.getOthername());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCl_fp_specialcomments())) {
                        mtQuotation.setClComments(clensPojo.getCl_fp_specialcomments());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getFollowup())) {
                        mtQuotation.setClFollowUp(clensPojo.getFollowup());
                    }

                    try {
                        if (clensPojo.getFollowupdate() != 0) {
                            java.util.Date followDate = ConvertDate.getUtilDateFromNumericDate(clensPojo.getFollowupdate());
                            if (followDate != null) {
                                XMLCalendar xmlDate = new XMLCalendar(followDate);
                                if (xmlDate != null) {
                                    mtQuotation.setClFollowDt(xmlDate);
                                }

                            }
                        }
                    } catch (Exception e) {
                        System.out.println("C Lens FollowupdateNot Set" + e);
                    }


                    if (Validations.isFieldNotEmpty(clensPojo.getOor_slitlamp_examination())) {
                        mtQuotation.setClSlExamination(clensPojo.getOor_slitlamp_examination());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getRight_trails())) {
                        mtQuotation.setClRightTrial(clensPojo.getRight_trails());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getLeft_trails())) {
                        mtQuotation.setClLeftTrial(clensPojo.getLeft_trails());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_aquasoft_mps())) {
                        mtQuotation.setClAquaSoftMps(clensPojo.getCaresys_aquasoft_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_complete_mps())) {
                        mtQuotation.setClCompleteMps(clensPojo.getCaresys_complete_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_gp_solution())) {
                        mtQuotation.setClGpsolutions(clensPojo.getCaresys_gp_solution());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_lubricant_mps())) {
                        mtQuotation.setClLubricants(clensPojo.getCaresys_lubricant_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_moisture_mps())) {
                        mtQuotation.setClMoistureLoc(clensPojo.getCaresys_moisture_mps());
                    }

                    if (Validations.isFieldNotEmpty(clensPojo.getCaresys_renu_mps())) {
                        mtQuotation.setClReNuMps(clensPojo.getCaresys_renu_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_rewetting_mps()))) {
                        mtQuotation.setClRewetting(clensPojo.getCaresys_rewetting_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_silkens_mps()))) {
                        mtQuotation.setCLSilkensMps(clensPojo.getCaresys_silkens_mps());
                    }

                    if (Validations.isFieldNotEmpty((clensPojo.getCaresys_solo_mps()))) {
                        mtQuotation.setClSoloMps(clensPojo.getCaresys_solo_mps());
                    }



                    ArrayList<in.co.titan.quotation.DTQuotation.CLRightEYE> cl_rightEyes = new ArrayList<in.co.titan.quotation.DTQuotation.CLRightEYE>();
                    ArrayList<in.co.titan.quotation.DTQuotation.CLLeftEYE> cl_leftEyes = new ArrayList<in.co.titan.quotation.DTQuotation.CLLeftEYE>();

                    /*********CL RIGHT *****************/
                    Map CL_RightEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "R");
                    if (CL_RightEyes != null) {
                        if (CL_RightEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.CLRightEYE righteye = null;
                            String desc = null, val = null;
                            Iterator clRight = CL_RightEyes.keySet().iterator();
                            if (clRight != null) {
                                while (clRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(clRight.next());
                                        val = String.valueOf(CL_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {

                                            righteye = new in.co.titan.quotation.DTQuotation.CLRightEYE();
                                            righteye.setDescription(desc);
                                            righteye.setValue(val);
                                            cl_rightEyes.add(righteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }


                    /************CL lEFT*************/
                    Map CL_LeftEyes = Inquiry_POS.getAndsetCLCharacteristics(clensPojo, "L");
                    if (CL_LeftEyes != null) {
                        if (CL_LeftEyes.keySet() != null) {
                            in.co.titan.quotation.DTQuotation.CLLeftEYE lefteye = null;
                            String desc = null, val = null;
                            Iterator clLeft = CL_LeftEyes.keySet().iterator();
                            if (clLeft != null) {
                                while (clLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(clLeft.next());
                                        val = String.valueOf(CL_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            lefteye = new in.co.titan.quotation.DTQuotation.CLLeftEYE();
                                            lefteye.setDescription(desc);
                                            lefteye.setValue(val);
                                            cl_leftEyes.add(lefteye);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }


                    mtQuotation.getCLLeftEYE().addAll(cl_leftEyes);
                    mtQuotation.getCLRightEYE().addAll(cl_rightEyes);
                }

                /*************SALE ORDER LINE ITEMS *********************/
                ArrayList<in.co.titan.quotation.DTQuotation.QuotationCondition> qoConditions = new ArrayList<in.co.titan.quotation.DTQuotation.QuotationCondition>();
                ArrayList<in.co.titan.quotation.DTQuotation.QuotationItem> qoItems = new ArrayList<in.co.titan.quotation.DTQuotation.QuotationItem>();
                if (qArrayList != null) {
                    Iterator qo_DetailsList = qArrayList.iterator();
                    if (qo_DetailsList != null) {
                        in.co.titan.quotation.DTQuotation.QuotationItem qoItem = null;
                        SOLineItemPOJO detailsPojo = null;
                        String priority = null, regular = null;
                        in.co.titan.quotation.DTQuotation.QuotationCondition qOCondition;
                        int i = 0;
                        DecimalFormat df = new DecimalFormat("#0.00");
                        while (qo_DetailsList.hasNext()) {
                            int j = 0;
                            detailsPojo = (SOLineItemPOJO) qo_DetailsList.next();
                            if (detailsPojo != null) {
                                qoItem = new in.co.titan.quotation.DTQuotation.QuotationItem();
                                BigDecimal amt = new BigDecimal(detailsPojo.getNetamount());
                                qoItem.setPOSLineItem(new BigInteger(Integer.toString(++i)));

                                if (Validations.isFieldNotEmpty(detailsPojo.getMaterial())) {
                                    qoItem.setItemCode(detailsPojo.getMaterial().toUpperCase());
                                    String division = new ArticleDO().getDivisionByMaterial(con, detailsPojo.getMaterial());
                                    if (Validations.isFieldNotEmpty(division)) {
                                        mtQuotation.setDivision(division);
                                    }
                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getBatchId())) {
//                                    qoItem.setBatch(detailsPojo.getBatchId());
//                                    if (detailsPojo.getBatchId().equalsIgnoreCase(quotationsHeaderPOJO.getQuotationno())) {
//                                        qoItem.setBatch("FREE");
//                                    }
//                                }
//                                if (quotationsHeaderPOJO.getPriority() == 1) {
//                                    soItem.setPriority("X");
//                                }
                                if (Validations.isFieldNotEmpty((detailsPojo.getQuantity()))) {
                                    qoItem.setQuantity(new BigInteger(Integer.toString(detailsPojo.getQuantity())));
                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getDiscountRefNo())) {
//                                    qoItem.setEmployeeNoDiscount(detailsPojo.getDiscountRefNo());
//                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getTaxableValue())) {
                                    qoItem.setTaxableValue(new BigDecimal(df.format(detailsPojo.getTaxableValue())));
                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getFoc())) {
//                                    qoItem.setFOC(detailsPojo.getFoc());
//                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getEyeSide())) {
//                                    qoItem.setEye(detailsPojo.getEyeSide());
//                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getTypeOfLens())) {
//                                    qoItem.setTypeOfLens(detailsPojo.getTypeOfLens());
//                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getStyleConsultant())) {
                                    qoItem.setStyleConsultant(detailsPojo.getStyleConsultant());
                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getCustomerItem())) {
                                    qoItem.setCustomerItem(detailsPojo.getCustomerItem());
                                }

//                                if (Validations.isFieldNotEmpty(detailsPojo.getModelNo())) {
//                                    qoItem.setModelNO(detailsPojo.getModelNo());
//                                }
//
//                                if (Validations.isFieldNotEmpty(detailsPojo.getBrandColor())) {
//                                    qoItem.setBrand(detailsPojo.getBrandColor());
//                                }
//
//                                if (Validations.isFieldNotEmpty(detailsPojo.getApproxValue())) {
//                                    qoItem.setAppValue(detailsPojo.getApproxValue());
//                                }
//
//                                if (Validations.isFieldNotEmpty(detailsPojo.getAnyVisibleDefect())) {
//                                    qoItem.setAnyVisibleDefect(detailsPojo.getAnyVisibleDefect());
//                                }

                                if (Validations.isFieldNotEmpty(detailsPojo.getComments())) {
                                    qoItem.setComments(detailsPojo.getComments());
                                }

//                                if (detailsPojo.getPromotionSelected() != null) {
//                                    if (Validations.isFieldNotEmpty(detailsPojo.getPromotionSelected().getPromotionID())) {
//                                        soItem.setPromotionID(detailsPojo.getPromotionSelected().getPromotionID());
//                                    }
//                                }

                                if (detailsPojo.getUCP() != null) {
                                    if (detailsPojo.getUCP().getDummyconditiontype() != null) {
                                        qOCondition = setQoConDition(detailsPojo.getUCP(), i, ++j, "U");
                                        if (qOCondition != null) {
                                            if (qOCondition.getPOSCondType() != null) {
                                                qoConditions.add(qOCondition);
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.getDiscountSelected() != null) {
                                    if (detailsPojo.getDiscountSelected().getDummyconditiontype() != null) {
                                        qOCondition = setQoConDition(detailsPojo.getDiscountSelected(), i, ++j, "D");
                                        if (qOCondition != null) {
                                            if (qOCondition.getPOSCondType() != null) {
                                                qoConditions.add(qOCondition);
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.getOfferPromotion() != null) {
                                    if (detailsPojo.getOfferPromotion().getDummyconditiontype() != null) {
                                        qOCondition = setQoConDition(detailsPojo.getOfferPromotion(), i, ++j, "P");
                                        if (qOCondition != null) {
                                            if (qOCondition.getPOSCondType() != null) {
                                                qoConditions.add(qOCondition);
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.getTaxDetails() != null) {
                                    Iterator iterator = detailsPojo.getTaxDetails().iterator();
                                    while (iterator.hasNext()) {
                                        ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                        if (conditionTypePOJO != null) {
                                            if (conditionTypePOJO.getDummyconditiontype() != null) {

                                                qOCondition = setQoConDition(conditionTypePOJO, i, ++j, "T");
                                                if (qOCondition != null) {
                                                    if (qOCondition.getPOSCondType() != null) {
                                                        qoConditions.add(qOCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (detailsPojo.isOtherChargesPresent()) {
                                    Iterator iterator = detailsPojo.getOtherCharges().iterator();
                                    while (iterator.hasNext()) {
                                        ConditionTypePOJO conditionTypePOJO = (ConditionTypePOJO) iterator.next();
                                        if (conditionTypePOJO != null) {
                                            if (conditionTypePOJO.getDummyconditiontype() != null) {
                                                qOCondition = setQoConDition(conditionTypePOJO, i, ++j, "O");
                                                if (qOCondition != null) {
                                                    if (qOCondition.getPOSCondType() != null) {
                                                        qoConditions.add(qOCondition);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                qoItems.add(qoItem);
                            }
                        }
                    }
                    mtQuotation.getQuotationItem().addAll(qoItems);
                    mtQuotation.getQuotationCondition().addAll(qoConditions);
                }


            /********************/
            }
            //Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 
            System.out.println("Quotation Storecode "+mtQuotation.getStoreCode());
            if(mtQuotation.getStoreCode()!=null && mtQuotation.getStoreCode().trim().length()>0){
                port.miOBASYNQuotation(mtQuotation);
                Map responseContext = ((BindingProvider)port).getResponseContext();
                       Integer responseCode = (Integer)responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

                if(responseCode.intValue() == 200){
                return "true";
                }else{
                 return "false";
                }
            }else{
                return "false";
            }
            //End of Code added by arun on 8 May 2012 to avoid sending payload if storecode is not available 

            //Code commented by arun on 8 May 2012 to avoid sending payload if storecode is not available 
            /*port.miOBASYNQuotation(mtQuotation);
            Map responseContext = ((BindingProvider)port).getResponseContext();
                   Integer responseCode = (Integer)responseContext.get(MessageContext.HTTP_RESPONSE_CODE);

            if(responseCode.intValue() == 200){
            return "true";
            }else{
             return "false";
            }*/
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            return "false";
        }

    //  throw new UnsupportedOperationException("Not supported yet.");
    }
}

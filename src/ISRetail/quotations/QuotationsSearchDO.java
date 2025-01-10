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
import ISRetail.salesorder.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class QuotationsSearchDO {

//    public ArrayList getSOSearchResultList(Connection conn, String wherestatement) {
//
//        try {
//            Statement pstmt = conn.createStatement();
//            String searchstatement = wherestatement;
//            System.out.println("SO serchstmtn=" + searchstatement);
//            ResultSet rs = pstmt.executeQuery(searchstatement);
//            ArrayList<SaleOrderSearchResultPojo> soList = new ArrayList<SaleOrderSearchResultPojo>();
//            SaleOrderSearchResultPojo soSearchresultPojo = null;
//            while (rs.next()) {
//                soSearchresultPojo = new SaleOrderSearchResultPojo();
//                soSearchresultPojo.setCustomerCode(rs.getString("customercode"));
//                soSearchresultPojo.setCustomerFirstName(rs.getString("firstname"));
//                soSearchresultPojo.setCustomerLastName(rs.getString("lastname"));
//                soSearchresultPojo.setSaleOrderNo(rs.getString("saleorderno"));
//                soSearchresultPojo.setSaleOrderDate(rs.getInt("saleorderdate"));
//                soSearchresultPojo.setInvoiceNo(rs.getString("invoiceno"));
//                soSearchresultPojo.setDocDate(rs.getInt("invoicedate"));                
//                soList.add(soSearchresultPojo);
//            }
//            return soList;
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
//    
//    
//     public ArrayList getSOSearchResultListBetweenTwoDates(Connection conn, String wherestatement,int creatFromDate,int createToDate) {
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(wherestatement);
//            pstmt.setInt(1, creatFromDate);
//            pstmt.setInt(2,createToDate);
//            System.out.println("SO serchstmtn=" + wherestatement);
//            ResultSet rs = pstmt.executeQuery();
//            ArrayList<SaleOrderSearchResultPojo> soList = new ArrayList<SaleOrderSearchResultPojo>();
//            SaleOrderSearchResultPojo soSearchresultPojo = null;
//            while (rs.next()) {
//                soSearchresultPojo = new SaleOrderSearchResultPojo();
//                soSearchresultPojo.setCustomerCode(rs.getString("customercode"));
//                soSearchresultPojo.setCustomerFirstName(rs.getString("firstname"));
//                soSearchresultPojo.setCustomerLastName(rs.getString("lastname"));
//                soSearchresultPojo.setSaleOrderNo(rs.getString("saleorderno"));
//                soSearchresultPojo.setSaleOrderDate(rs.getInt("saleorderdate"));
//                soSearchresultPojo.setInvoiceNo(rs.getString("invoiceno"));
//                soSearchresultPojo.setDocDate(rs.getInt("invoicedate"));        
//                soList.add(soSearchresultPojo);
//            }
//            return soList;
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
//      public ArrayList getSOSearchResultList1(Connection conn, String wherestatement) {
//
//        try {
//            Statement pstmt = conn.createStatement();
//            String searchstatement = wherestatement;
//            System.out.println("SO serchstmtn=" + searchstatement);
//            ResultSet rs = pstmt.executeQuery(searchstatement);
//            ArrayList<SaleOrderSearchResultPojo> soList = new ArrayList<SaleOrderSearchResultPojo>();
//            SaleOrderSearchResultPojo soSearchresultPojo = null;
//            while (rs.next()) {
//                soSearchresultPojo = new SaleOrderSearchResultPojo();
//                soSearchresultPojo.setCustomerCode(rs.getString("customercode"));
//                soSearchresultPojo.setCustomerFirstName(rs.getString("firstname"));
//                soSearchresultPojo.setCustomerLastName(rs.getString("lastname"));
//                soSearchresultPojo.setSaleOrderNo(rs.getString("saleorderno"));
//                soSearchresultPojo.setSaleOrderDate(rs.getInt("saleorderdate"));
//                /*Commented on 16.9.2008*/
//              // soSearchresultPojo.setInvoiceNo(rs.getString("documentno"));
//              // soSearchresultPojo.setDocDate(rs.getInt("documentdate"));  
//                soSearchresultPojo.setInvoiceNo(rs.getString("invoiceno"));
//                soSearchresultPojo.setDocDate(rs.getInt("invoicedate"));
//                soList.add(soSearchresultPojo);
//            }
//            return soList;
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
//    
//    
//     public ArrayList getSOSearchResultListBetweenTwoDates1(Connection conn, String wherestatement,int creatFromDate,int createToDate) {
//
//        try {
//            PreparedStatement pstmt = conn.prepareStatement(wherestatement);
//            pstmt.setInt(1, creatFromDate);
//            pstmt.setInt(2,createToDate);
//            System.out.println("SO serchstmtn=" + wherestatement);
//            ResultSet rs = pstmt.executeQuery();
//            ArrayList<SaleOrderSearchResultPojo> soList = new ArrayList<SaleOrderSearchResultPojo>();
//            SaleOrderSearchResultPojo soSearchresultPojo = null;
//            while (rs.next()) {
//                soSearchresultPojo = new SaleOrderSearchResultPojo();
//                soSearchresultPojo.setCustomerCode(rs.getString("customercode"));
//                soSearchresultPojo.setCustomerFirstName(rs.getString("firstname"));
//                soSearchresultPojo.setCustomerLastName(rs.getString("lastname"));
//                soSearchresultPojo.setSaleOrderNo(rs.getString("saleorderno"));
//                soSearchresultPojo.setSaleOrderDate(rs.getInt("saleorderdate"));
//                soSearchresultPojo.setInvoiceNo(rs.getString("invoiceno"));
//                soSearchresultPojo.setDocDate(rs.getInt("invoicedate"));
//                soList.add(soSearchresultPojo);
//            }
//            return soList;
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
//
//    

     /*   04.09.2008*/
     
      public String getCustomerCodeFromSaleOrder(String saleOrderNo,Connection conn)
    {
        String custCode=null;
        String query;
        PreparedStatement pstmt;
        ResultSet rs;
        try
        {
                query = "select customercode from tbl_salesorderheader where saleorderno=?";
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1,saleOrderNo);
                rs= pstmt.executeQuery();
                if(rs.next())
                {
                    custCode=rs.getString(1);
                }

        }catch(Exception e)
        {
            e.printStackTrace();
        }finally {
            rs = null;
            pstmt= null;
            query = null;
        }
        return custCode;
             
    }
      
//        public ArrayList getSOSearchResultList1foradvcancel(Connection conn, String wherestatement) {
//
//        try {
//            Statement pstmt = conn.createStatement();
//            String searchstatement = wherestatement;
//            System.out.println("SO serchstmtn=" + searchstatement);
//            ResultSet rs = pstmt.executeQuery(searchstatement);
//            ArrayList<SaleOrderSearchResultPojo> soList = new ArrayList<SaleOrderSearchResultPojo>();
//            SaleOrderSearchResultPojo soSearchresultPojo = null;
//            while (rs.next()) {
//                soSearchresultPojo = new SaleOrderSearchResultPojo();
//                soSearchresultPojo.setCustomerCode(rs.getString("customercode"));
//                soSearchresultPojo.setCustomerFirstName(rs.getString("firstname"));
//                soSearchresultPojo.setCustomerLastName(rs.getString("lastname"));
//                soSearchresultPojo.setSaleOrderNo(rs.getString("saleorderno"));
//                soSearchresultPojo.setSaleOrderDate(rs.getInt("saleorderdate"));
//                /*Commented on 16.9.2008*/
//               soSearchresultPojo.setInvoiceNo(rs.getString("documentno"));
//               soSearchresultPojo.setDocDate(rs.getInt("documentdate"));  
//               
//                soList.add(soSearchresultPojo);
//            }
//            return soList;
//
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//
//    }
//    

}

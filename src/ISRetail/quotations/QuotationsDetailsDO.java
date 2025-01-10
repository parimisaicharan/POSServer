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
import ISRetail.Webservices.ConditionTypePOJO;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class QuotationsDetailsDO {  
    

     public ArrayList<SOLineItemPOJO> getQuotationDetailByQuotationNumber(Connection conn, String QuotationNo) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_quotationlineitems where quotationno=? order by lineitemno");
            pstmt.setString(1, QuotationNo);
            rs = pstmt.executeQuery();
            ArrayList<SOLineItemPOJO> list = new ArrayList<SOLineItemPOJO>();
            int lineitemno;
            while (rs.next()) {                
                SOLineItemPOJO quotationdetailpojoobj = new SOLineItemPOJO();
               // salesorderdetailspojoobj.setSaleordernumber(rs.getString("saleordernumber"));
               // salesorderdetailspojoobj.setSno(rs.getInt("sno"));                
                lineitemno = rs.getInt("lineitemno");                
               // servicesdetailpojoobj.setCustomerItem(rs.getString("customeritem"));                
                quotationdetailpojoobj.setMaterial(rs.getString("materialcode"));
                //servicesdetailpojoobj.setBatchId(rs.getString("batchID"));
                quotationdetailpojoobj.setQuantity(rs.getInt("Qty"));
                quotationdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
           //     servicesdetailpojoobj.setUCP(rs.getDouble("Unitprice"));
                quotationdetailpojoobj.setUCP(getSOCondition(conn, QuotationNo, lineitemno, "U"));
          //      servicesdetailpojoobj.setDiscountSelected(getSOCondition(conn, ServiceOrderNo, lineitemno, "D"));
                quotationdetailpojoobj.setTaxDetails(getArraySOCondition(conn, QuotationNo, lineitemno, "T"));               
               // salesorderdetailspojoobj.setDiscount_type(rs.getString("discount_type"));
               // salesorderdetailspojoobj.setDiscount_percent(rs.getByte("discount_percent"));
              //  salesorderdetailspojoobj.setTaxableValue(rs.getByte("taxamount"));
               // salesorderdetailspojoobj.setn(rs.getByte("netamount"));               
                list.add(quotationdetailpojoobj);
             }
            return list;
        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderDetails Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            rs = null;
        }
     }
     
      public Vector getQuotationDetailByQuotationNumberforThread(Connection conn, String QuotationNo) {
        
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_quotationlineitems where quotationno=? order by lineitemno");
            pstmt.setString(1, QuotationNo);
            rs = pstmt.executeQuery();
            Vector list = new Vector();
            int lineitemno;
            while (rs.next()) {                
                SOLineItemPOJO quotationdetailpojoobj = new SOLineItemPOJO();              
                lineitemno = rs.getInt("lineitemno");
                quotationdetailpojoobj.setMaterial(rs.getString("materialcode"));
                quotationdetailpojoobj.setQuantity(rs.getInt("Qty"));
                quotationdetailpojoobj.setStyleConsultant(rs.getString("styleconsultant"));
                quotationdetailpojoobj.setCustomerItem(rs.getString("customeritem"));
                quotationdetailpojoobj.setComments(rs.getString("cl_comments"));
                quotationdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                quotationdetailpojoobj.setOtherCharges(getArraySOCondition(conn, QuotationNo, lineitemno, "O"));
                if (quotationdetailpojoobj.getOtherCharges() != null) {
                    quotationdetailpojoobj.setOtherChargesPresent(true);
                }
                quotationdetailpojoobj.setUCP(getSOCondition(conn, QuotationNo, lineitemno, "U"));
         
                quotationdetailpojoobj.setTaxDetails(getArraySOCondition(conn, QuotationNo, lineitemno, "T"));
               
             
                list.add(quotationdetailpojoobj);
                
                lineitemno++;

             }

            return list;

        } catch (SQLException sQLException) {
            System.out.println("exception in saleorderDetails Searching=" + sQLException);
            sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            rs = null;
        }
     }
//     public Vector getQuotationDetailByQuotationNumberforPrint(Connection conn, String QuotationNo) {
//        
//        
//        try {
//            PreparedStatement pstmt = conn.prepareStatement("select * from tbl_quotationlineitems where quotationno=? order by lineitemno");
//     
//            pstmt.setString(1, QuotationNo);
//            ResultSet rs = pstmt.executeQuery();
//            Vector list = new Vector();
//            int lineitemno;
//            while (rs.next()) {
//                 SOLineItemPOJO salesorderdetailspojoobj = new SOLineItemPOJO();
//                
//              
//
//                SOLineItemPOJO quotationdetailpojoobj = new SOLineItemPOJO();
//               // salesorderdetailspojoobj.setSaleordernumber(rs.getString("saleordernumber"));
//               // salesorderdetailspojoobj.setSno(rs.getInt("sno"));
//                
//                lineitemno = rs.getInt("lineitemno");
//                
//               // servicesdetailpojoobj.setCustomerItem(rs.getString("customeritem")); 
//                
//                quotationdetailpojoobj.setMaterial(rs.getString("materialcode"));
//                quotationdetailpojoobj=PricingEngine.getItemdescriptionbymaterialcode(quotationdetailpojoobj,conn,quotationdetailpojoobj.getMaterial());
//                //servicesdetailpojoobj.setBatchId(rs.getString("batchID"));
//                quotationdetailpojoobj.setQuantity(rs.getInt("Qty"));
//                quotationdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
//           //     servicesdetailpojoobj.setUCP(rs.getDouble("Unitprice"));
//                quotationdetailpojoobj.setUCP(getSOCondition(conn, QuotationNo, lineitemno, "U"));
//          //      servicesdetailpojoobj.setDiscountSelected(getSOCondition(conn, ServiceOrderNo, lineitemno, "D"));
//                quotationdetailpojoobj.setTaxDetails(getArraySOCondition(conn, QuotationNo, lineitemno, "T"));
//               
//               // salesorderdetailspojoobj.setDiscount_type(rs.getString("discount_type"));
//               // salesorderdetailspojoobj.setDiscount_percent(rs.getByte("discount_percent"));
//              //  salesorderdetailspojoobj.setTaxableValue(rs.getByte("taxamount"));
//               // salesorderdetailspojoobj.setn(rs.getByte("netamount"));
//               
//                list.add(quotationdetailpojoobj);
//                
//                
//
//             }
//
//            return list;
//
//        } catch (SQLException sQLException) {
//            System.out.println("exception in saleorderDetails Searching=" + sQLException);
//            sQLException.printStackTrace();
//            return null;
//        }
//     }
//     
        public ConditionTypePOJO getSOCondition(Connection conn,String QuotationNumber,int lineItemNo,String flag){
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try
        {
          //  PreparedStatement pstmt = conn.prepareStatement("select * from tbl_quotation_condtion where quotationno = ? and reflineitemno=? and typeofcondition=?");
            pstmt = conn.prepareStatement("select * from dbo.tbl_quotation_condtion left outer join tbl_conditiontypemaster on tbl_quotation_condtion.poscondtype =tbl_conditiontypemaster.poscondtype where quotationno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, QuotationNumber);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();

            conditionTypePOJO = new ConditionTypePOJO();
            if(rs.next()){
              conditionTypePOJO.setDummyconditiontype(rs.getString("poscondtype"));  
              conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
              conditionTypePOJO.setValue(rs.getDouble("amount"));
              conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
              conditionTypePOJO.setConditionName(rs.getString("condtypedescription"));
            }
            
        }catch(Exception e){
           e.printStackTrace();
        }finally {
            pstmt= null;
            rs = null;
        }
        return conditionTypePOJO;
    }
    
    
      public ArrayList<ConditionTypePOJO> getArraySOCondition(Connection conn,String QuotationNumber,int lineItemNo,String flag){
       ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try
        {
           // PreparedStatement pstmt = conn.prepareStatement("select * from tbl_quotation_condtion where quotationno = ? and reflineitemno=? and typeofcondition=?");
            pstmt = conn.prepareStatement("select * from dbo.tbl_quotation_condtion left outer join tbl_conditiontypemaster on tbl_quotation_condtion.poscondtype =tbl_conditiontypemaster.poscondtype where quotationno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, QuotationNumber);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            conditionTypePOJOs = new ArrayList<ConditionTypePOJO>();
            while(rs.next()){
              ConditionTypePOJO conditionTypePOJO = new ConditionTypePOJO();
              conditionTypePOJO.setDummyconditiontype(rs.getString("poscondtype"));  
              conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
              conditionTypePOJO.setValue(rs.getDouble("amount"));
              conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
              conditionTypePOJO.setConditionName(rs.getString("condtypedescription"));
              conditionTypePOJOs.add(conditionTypePOJO);
            }
            
        }catch(Exception e){
         e.printStackTrace();   
        }finally {
            pstmt= null;
            rs = null;
        }
        return conditionTypePOJOs;
    }
      
}

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
 * This class file is used as a Data Object between Quotation Form and Database
 * This is used for transaction of Quotation data from and to the database
 * 
 * 
 * 
 */


package ISRetail.Quotation;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.salesorder.SOLineItemPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class QuotationDetailsDO {
        
    /**
    * To Get the List of Quotation Line Items from the Quotation Line items Table based on the Quotation NO passed as argument
    */    
    public Vector getQuotationDetailByQuotationNumberforThread(Connection conn, String QuotationNo) {          
          PreparedStatement pstmt;
          ResultSet rs;
          Vector list;
        try {
            pstmt = conn.prepareStatement("select * from tbl_quotationlineitems where quotationno=? order by lineitemno");     
            pstmt.setString(1, QuotationNo);
            rs = pstmt.executeQuery();
            list = new Vector();
            
            int lineitemno;
            while (rs.next()) {                
                SOLineItemPOJO quotationdetailpojoobj = new SOLineItemPOJO();
                lineitemno = rs.getInt("lineitemno");                                
                quotationdetailpojoobj.setMaterial(rs.getString("materialcode"));                
                quotationdetailpojoobj.setQuantity(rs.getInt("Qty"));
                quotationdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));           
                quotationdetailpojoobj.setUCP(getSOCondition(conn, QuotationNo, lineitemno, "U"));          
                quotationdetailpojoobj.setTaxDetails(getArraySOCondition(conn, QuotationNo, lineitemno, "T"));               
                list.add(quotationdetailpojoobj);
             }
            return list;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
          pstmt = null;
          rs = null;
        }          
     }
      
    /**
    * To Get the List of Quotation Line Items from the Quotation Line items Table  for Print 
    */     
    public Vector getQuotationDetailByQuotationNumberforPrint(Connection conn, String QuotationNo) {                
         PreparedStatement pstmt;
         ResultSet rs;
         Vector list;
        try {
            pstmt = conn.prepareStatement("select * from tbl_quotationlineitems where quotationno=? order by lineitemno");     
            pstmt.setString(1, QuotationNo);
            rs = pstmt.executeQuery();
            list = new Vector();
            int lineitemno;
            while (rs.next()) {
                SOLineItemPOJO salesorderdetailspojoobj = new SOLineItemPOJO();
                SOLineItemPOJO quotationdetailpojoobj = new SOLineItemPOJO();               
                lineitemno = rs.getInt("lineitemno");                               
                quotationdetailpojoobj.setMaterial(rs.getString("materialcode"));
                quotationdetailpojoobj.setQuantity(rs.getInt("Qty"));
                quotationdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));           
                quotationdetailpojoobj.setUCP(getSOCondition(conn, QuotationNo, lineitemno, "U"));          
                quotationdetailpojoobj.setTaxDetails(getArraySOCondition(conn, QuotationNo, lineitemno, "T"));               
                list.add(quotationdetailpojoobj);
             }
            return list;
        }catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
          pstmt = null;
          rs = null;
        }       
     }
     
    /**
    * To Get the Condition TYPE POJO from Quotation Condition Line Items 
    */     
     
    public ConditionTypePOJO getSOCondition(Connection conn,String QuotationNumber,int lineItemNo,String flag){
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try
        {
            pstmt = conn.prepareStatement("select * from tbl_quotation_condtion where quotationno = ? and reflineitemno=? and typeofcondition=?");
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
            }            
        }catch(Exception e){
           e.printStackTrace();
        } finally {
          pstmt = null;
          rs = null;
        }        
        return conditionTypePOJO;
    }
    
    /**
    * To Get the List of Condition TYPE Line Items from Quotation Condition Line Items 
    */     
        
    public ArrayList<ConditionTypePOJO> getArraySOCondition(Connection conn,String QuotationNumber,int lineItemNo,String flag){
        ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try
        {
            pstmt = conn.prepareStatement("select * from tbl_quotation_condtion where quotationno = ? and reflineitemno=? and typeofcondition=?");
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
              conditionTypePOJOs.add(conditionTypePOJO);
            }
            
        }catch(Exception e){
         e.printStackTrace();   
        }finally {
          pstmt = null;
          rs = null;
        }
        return conditionTypePOJOs;
    }
            
}

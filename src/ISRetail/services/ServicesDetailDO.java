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
package ISRetail.services;


import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.salesorder.SOLineItemPOJO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ServicesDetailDO {

    
    public ArrayList<SOLineItemPOJO> getServiceDetailByServiceOrderNumber(Connection conn, String ServiceOrderNo) {
        PreparedStatement pstmt;
        ResultSet rs;        
        try {
            pstmt = conn.prepareStatement("select * from tbl_serviceorderlineitems where serviceordernumber=? order by lineitemno");
            pstmt.setString(1, ServiceOrderNo);
            rs = pstmt.executeQuery();
            ArrayList<SOLineItemPOJO> list = new ArrayList<SOLineItemPOJO>();
            int lineitemno;
            while (rs.next()) {
                SOLineItemPOJO servicesdetailpojoobj = new SOLineItemPOJO();
                lineitemno = rs.getInt("lineitemno");
                servicesdetailpojoobj.setCustomerItem(rs.getString("customeritem")); 
                servicesdetailpojoobj.setModelNo(rs.getString("CI_modelno"));
                servicesdetailpojoobj.setBrandColor(rs.getString("CI_brand_color"));
                servicesdetailpojoobj.setApproxValue(rs.getString("CI_approx_value"));
                servicesdetailpojoobj.setAnyVisibleDefect(rs.getString("CI_visibledefects"));
                servicesdetailpojoobj.setComments(rs.getString("CI_comments"));
                servicesdetailpojoobj.setProductDetail(rs.getString("CI_productdetail"));
                servicesdetailpojoobj.setNatureOfService(rs.getString("CI_natureofservice"));
                servicesdetailpojoobj.setMaterial(rs.getString("materialcode"));
                servicesdetailpojoobj.setBatchId(rs.getString("batchID"));
                servicesdetailpojoobj.setQuantity(rs.getInt("Qty"));
                servicesdetailpojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                servicesdetailpojoobj.setUCP(getSOCondition(conn, ServiceOrderNo, lineitemno, "U"));
                servicesdetailpojoobj.setDiscountSelected(getSOCondition(conn, ServiceOrderNo, lineitemno, "D"));
                servicesdetailpojoobj.setTaxDetails(getArraySOCondition(conn, ServiceOrderNo, lineitemno, "T"));
                list.add(servicesdetailpojoobj);
            }
            return list;
        } catch (SQLException sQLException) {
            
            sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            rs = null;
        }
    }

    public ConditionTypePOJO getSOCondition(Connection conn, String ServiceOrderNumber, int lineItemNo, String flag) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_serviceorder_condition where serviceorderno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, ServiceOrderNumber);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            conditionTypePOJO = new ConditionTypePOJO();
            if (rs.next()) {
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummycondtype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pstmt= null;
            rs = null;
        }
        return conditionTypePOJO;
    }

    public ArrayList<ConditionTypePOJO> getArraySOCondition(Connection conn, String ServiceOrderNumber, int lineItemNo, String flag) {
        ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_serviceorder_condition where serviceorderno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, ServiceOrderNumber);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            conditionTypePOJOs = new ArrayList<ConditionTypePOJO>();
            while (rs.next()) {
                ConditionTypePOJO conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummycondtype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJOs.add(conditionTypePOJO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pstmt= null;
            rs = null;
        }
        return conditionTypePOJOs;
    }

    /************************  Add by Saurabh on  30.09.2008        ************************/
    public void deleteAllLineItemsBasedOnServiceOrderNo(Connection con, String serviceOrderNo) {
        Statement statement;        
        try {
            statement = con.createStatement();
            statement.executeUpdate("delete from tbl_serviceorderlineitems where serviceordernumber= '" + serviceOrderNo + "'");

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            statement= null;            
        }
    }

    public void deleteAllSoConditionsBasedOnServiceOrderNo(Connection con, String serviceOrderNo) {
        Statement statement;        
        try {
            statement = con.createStatement();
            statement.executeUpdate("delete from tbl_serviceorder_condition where serviceorderno= '" + serviceOrderNo + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            statement= null;
            
        }
    }
    
        public AdvanceReceiptPOJO getAdvanceReceiptListforthread(Connection conn, String searchstmt) {
            Statement pstmt;
            ResultSet rs;            
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
                advancerecpojoobj.setCreditsalereference("");
                advancerecpojoobj.setReleasestatus("");
                advancerecpojoobj.setSaletype("");
            }
            return advancerecpojoobj;
        } catch (SQLException e) {
            return null;
        }finally {
            pstmt= null;
            rs = null;
        }
    }        
    public String getArticleDivisionByServiceOrderNo(Connection con, String saleOrderNo) {
        String division = null;
        Statement stmt;
        ResultSet rs;        
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select division from tbl_articlemaster left outer join tbl_serviceorderlineitems on tbl_serviceorderlineitems.materialcode = tbl_articlemaster.materialcode where tbl_serviceorderlineitems.serviceordernumber = '" + saleOrderNo + "'");
            if (rs.next()) {
                division = rs.getString("division");
            }
        } catch (Exception e) {

        }finally {
            stmt= null;
            rs = null;
        }
        return division;
    }        
    /****   end  addition ***/
}

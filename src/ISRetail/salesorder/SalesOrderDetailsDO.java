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
package ISRetail.salesorder;

import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.PromotionConditionTypePOJO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import java.sql.*;
import java.util.ArrayList;

public class SalesOrderDetailsDO {
    //SOLineItemPOJO solineitempojo=new SOLineItemPOJO();   

    /**
     * ************************* Changed by vandana 26.09.2008     *************************
     */
    /*Saleorder display line items    4.09.08*/
    public ArrayList<SOLineItemPOJO> getSaleOrderDetailsBySaleOrderNoForWebService(Connection conn, String saleorderno) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_salesorderlineitems where saleorderno=? order by lineitemno");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            ArrayList<SOLineItemPOJO> list = new ArrayList<SOLineItemPOJO>();
            int lineitemno;
            while (rs.next()) {
                
                SOLineItemPOJO salesorderdetailspojoobj = new SOLineItemPOJO();
                lineitemno = rs.getInt("lineitemno");
                salesorderdetailspojoobj.setSite(rs.getString("storecode"));
                salesorderdetailspojoobj.setFoc(rs.getString("foc"));
                salesorderdetailspojoobj.setCustomerItem(rs.getString("customeritem"));
                salesorderdetailspojoobj.setModelNo(rs.getString("cl_modelno"));
                salesorderdetailspojoobj.setBrandColor(rs.getString("cl_brand_color"));
                salesorderdetailspojoobj.setApproxValue(rs.getString("cl_approx_value"));
                salesorderdetailspojoobj.setAnyVisibleDefect(rs.getString("cl_visibledefects"));
                salesorderdetailspojoobj.setComments(rs.getString("cl_comments"));
                salesorderdetailspojoobj.setMaterial(rs.getString("materialcode"));
                String ordertype = getOrdertypeBySaleOrderNo(conn, saleorderno);
                if(ordertype.equalsIgnoreCase("CL") && ServerConsole.contactlensdualpricingenabled.equalsIgnoreCase("Y")){//Added by charan for contact lens dual pricing
                salesorderdetailspojoobj.setBatchId(rs.getString("batchid"));
                salesorderdetailspojoobj.setFrombatch(rs.getString("frombatch"));
                }else{
                 salesorderdetailspojoobj.setBatchId(rs.getString("frombatch"));   
                }
                salesorderdetailspojoobj.setDiscountRefNo(rs.getString("discountrefno"));
                salesorderdetailspojoobj.setQuantity(rs.getInt("qty"));
                salesorderdetailspojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                salesorderdetailspojoobj.setUCP(getSOConditionForDisplay(conn, saleorderno, lineitemno, "U"));
                salesorderdetailspojoobj.setDiscountSelected(getSOCondition(conn, saleorderno, lineitemno, "D"));
                salesorderdetailspojoobj.setOfferPromotion(getSOCondition(conn, saleorderno, lineitemno, "P"));

                salesorderdetailspojoobj.setTaxDetails(getArraySOCondition(conn, saleorderno, lineitemno, "T"));
                salesorderdetailspojoobj.setOtherCharges(getArraySOCondition(conn, saleorderno, lineitemno, "O"));
                salesorderdetailspojoobj.setDivision(getArticleDivisionBySaleOrderNo(conn, saleorderno)); // added by Ravi thoto for division tag in Webservice on 23012012
                if (salesorderdetailspojoobj.getOtherCharges() != null) {
                    salesorderdetailspojoobj.setOtherChargesPresent(true);
                }
                salesorderdetailspojoobj.setStyleConsultant(rs.getString("styleconsultant"));
                salesorderdetailspojoobj.setEyeSide(rs.getString("eyeside"));
                salesorderdetailspojoobj.setTypeOfLens(rs.getString("typeoflens"));
                String sellingPromotionID = rs.getString("sellingpromotionid");
                if (Validations.isFieldNotEmpty(sellingPromotionID)) {
                    PromotionConditionTypePOJO promotionConditionTypePOJO = new PromotionConditionTypePOJO();
                    promotionConditionTypePOJO.setPromotionID(sellingPromotionID);
                    salesorderdetailspojoobj.setPromotionSelected(promotionConditionTypePOJO);
                }
                salesorderdetailspojoobj.setLineItemEmpid(rs.getString("empid"));
                salesorderdetailspojoobj.setFrameColor(rs.getString("framecolor"));
                salesorderdetailspojoobj.setLensTech(rs.getString("lenstech"));
                salesorderdetailspojoobj.setLensColor(rs.getString("lenscolor"));
                salesorderdetailspojoobj.setGradient(rs.getString("gradient"));
                salesorderdetailspojoobj.setAudioUniqueID(rs.getString("AudioUniqueID"));//added by charan for audiology
                salesorderdetailspojoobj.setAudioRPTA(rs.getString("AudioRPTA"));
                salesorderdetailspojoobj.setAudioLPTA(rs.getString("AudioLPTA"));
                salesorderdetailspojoobj.setAudioProvDiag(rs.getString("AudioProvDiag"));//audiology end
                list.add(salesorderdetailspojoobj);
                System.out.println("AUDIO DB UNIQUE ID FROM SERVER TO PI:"+rs.getString("AudioUniqueID"));
                System.out.println("AUDIO DB RPTA FROM SERVER TO PI:"+rs.getString("AudioRPTA"));
                System.out.println("AUDIO DB LPTA ID FROM SERVER TO PI:"+rs.getString("AudioLPTA"));
                System.out.println("AUDIO DB PRO DIAG FROM SERVER TO PI:"+rs.getString("AudioProvDiag"));
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
     * ************************* end Changed by vandana 26.09.2008     *************************
     */
    /**
     * ************************************* changed by vandana 26.09.2008
     */
    public ConditionTypePOJO getSOCondition(Connection conn, String saleOrderNo, int lineItemNo, String flag) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from dbo.tbl_so_condition left outer join tbl_conditiontypemaster on tbl_so_condition.dummyconditiontype =tbl_conditiontypemaster.poscondtype where saleorderno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, saleOrderNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummyconditiontype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setConditiontype(rs.getString("condtype"));
                conditionTypePOJO.setConditionName(rs.getString("condtypedescription"));

                conditionTypePOJO.setCessOnCondType(rs.getString("basetax"));
                if (rs.getString("taxcode") != null) {
                    if (rs.getString("taxcode").equalsIgnoreCase("x")) {
                        conditionTypePOJO.setIsCess(true);
                    } else {
                        conditionTypePOJO.setIsCess(false);
                    }
                }
                conditionTypePOJO.setPromotionId(rs.getString("promotinid"));
                conditionTypePOJO.setFreeGoodsCategory(rs.getString("freegoodscat"));
                conditionTypePOJO.setMaxamt(rs.getDouble("maxamount"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJO.setPromotionLineno(rs.getString("promotionlineno"));
                conditionTypePOJO.setPromotionRandomNo(rs.getInt("promotiongroupid"));

            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return conditionTypePOJO;
    }

    public ConditionTypePOJO getSOConditionForDisplay(Connection conn, String saleOrderNo, int lineItemNo, String flag) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from dbo.tbl_so_condition left outer join tbl_conditiontypemaster on tbl_so_condition.dummyconditiontype =tbl_conditiontypemaster.poscondtype where saleorderno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, saleOrderNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummyconditiontype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setConditiontype(rs.getString("condtype"));
                conditionTypePOJO.setConditionName(rs.getString("condtypedescription"));
                conditionTypePOJO.setCessOnCondType(rs.getString("basetax"));
                if (rs.getString("taxcode") != null) {
                    if (rs.getString("taxcode").equalsIgnoreCase("x")) {
                        conditionTypePOJO.setIsCess(true);
                    } else {
                        conditionTypePOJO.setIsCess(false);
                    }
                }
                conditionTypePOJO.setPromotionId(rs.getString("promotinid"));
                conditionTypePOJO.setFreeGoodsCategory(rs.getString("freegoodscat"));
                conditionTypePOJO.setMaxamt(rs.getDouble("maxamount"));
                if (rs.getDouble("amount") < 0) {
                    conditionTypePOJO.setValue(-rs.getDouble("amount"));
                } else {
                    conditionTypePOJO.setValue(rs.getDouble("amount"));
                }
                if (rs.getDouble("amount") < 0) {
                    conditionTypePOJO.setNetAmount(-rs.getDouble("netamount"));
                } else {
                    conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                }
                conditionTypePOJO.setPromotionLineno(rs.getString("promotionlineno"));
                conditionTypePOJO.setPromotionRandomNo(rs.getInt("promotiongroupid"));

            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return conditionTypePOJO;
    }

    public ArrayList<ConditionTypePOJO> getArraySOCondition(Connection conn, String saleOrderNo, int lineItemNo, String flag) {
        ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from dbo.tbl_so_condition left outer join tbl_conditiontypemaster on tbl_so_condition.dummyconditiontype =tbl_conditiontypemaster.poscondtype where saleorderno = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, saleOrderNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            rs = pstmt.executeQuery();
            int i = 0;
            while (rs.next()) {
                if (i == 0) {
                    conditionTypePOJOs = new ArrayList<ConditionTypePOJO>();
                }
                i++;
                ConditionTypePOJO conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummyconditiontype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setConditiontype(rs.getString("condtype"));
                conditionTypePOJO.setConditionName(rs.getString("condtypedescription"));
                conditionTypePOJO.setCessOnCondType(rs.getString("basetax"));
                if (rs.getString("taxcode") != null) {
                    if (rs.getString("taxcode").equalsIgnoreCase("x")) {
                        conditionTypePOJO.setIsCess(true);
                    } else {
                        conditionTypePOJO.setIsCess(false);
                    }
                }
                conditionTypePOJO.setMaxamt(rs.getDouble("maxamount"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJO.setPromotionLineno(rs.getString("promotionlineno"));
                conditionTypePOJO.setPromotionRandomNo(rs.getInt("promotiongroupid"));
                conditionTypePOJOs.add(conditionTypePOJO);
            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return conditionTypePOJOs;
    }

    public String getArticleDivisionBySaleOrderNo(Connection con, String saleOrderNo) {
        String division = null;
        Statement stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select division from tbl_articlemaster left outer join tbl_salesorderlineitems on tbl_salesorderlineitems.materialcode = tbl_articlemaster.materialcode where tbl_salesorderlineitems.saleorderno = '" + saleOrderNo + "'");
            if (rs.next()) {
                division = rs.getString("division");
            }
        } catch (Exception e) {

        } finally {
            stmt = null;
            rs = null;
        }

        return division;
    }
    
    public String getOrdertypeBySaleOrderNo(Connection con, String saleOrderNo) {
        String ordertype = null;
        Statement stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select printordertype from tbl_salesorderheader where saleorderno='" + saleOrderNo + "'");
            if (rs.next()) {
                ordertype = rs.getString("printordertype");
            }
        } catch (Exception e) {

        } finally {
            stmt = null;
            rs = null;
        }

        return ordertype;
    }
    
    
    /**
     * *************************************end changed by vandana  26.09.2008
     */
}

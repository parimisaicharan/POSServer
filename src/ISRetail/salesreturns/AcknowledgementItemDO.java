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
package ISRetail.salesreturns;
import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.PromotionConditionTypePOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import ISRetail.Helpers.Decimalvalue;
import ISRetail.salesorder.SalesOrderHeaderDO;
import java.text.ParseException;
import java.util.HashMap;

/**
 *
 * @author Administrator
 */
public class AcknowledgementItemDO {

    public int saveAcknowledgementItemDetails(Connection con, SOLineItemPOJO acknowledgementitempojoobj, AcknowledgementHeaderPOJO acknowledgementHeaderPojo, int lineItemNo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;
        try {
            pstmt = con.prepareStatement("insert into tbl_ack_lineitems values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//acknumber
            pstmt.setInt(4, lineItemNo);//lineitemno
            pstmt.setInt(5, acknowledgementHeaderPojo.getAcknowledgementdate());//ackdate
            pstmt.setString(6, acknowledgementitempojoobj.getMaterial());//mat code
            pstmt.setInt(7, acknowledgementitempojoobj.getQuantity());//billedqty
            pstmt.setDouble(8, acknowledgementitempojoobj.getNetamount());//billednetamt
            pstmt.setInt(9, acknowledgementitempojoobj.getReturnedqty());//returned qty
            pstmt.setDouble(10, acknowledgementitempojoobj.getReturnednetamount());//returnednetamt
            pstmt.setDouble(11, acknowledgementitempojoobj.getTaxableValue());//taxable avlue
            pstmt.setString(12, acknowledgementitempojoobj.getReturnreason());////return reason
            if (acknowledgementitempojoobj.getPromotionSelected() != null) {
                pstmt.setString(13, acknowledgementitempojoobj.getPromotionSelected().getPromotionID());//promotion id
            } else {
                pstmt.setString(13, "");//promotion id    
            }
            pstmt.setString(14, acknowledgementitempojoobj.getOtherretreason());//Other return reason
            res = pstmt.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
            pstmt = null;
        }
    }

    public int deleteAcknowledgementItemDetails(Connection con, AcknowledgementHeaderPOJO acknowledgementHeaderPojo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            pstmt = con.prepareStatement("delete tbl_ack_lineitems where storecode=? and fiscalyear=? and refacknumber=?");
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//acknumber
            res = pstmt.executeUpdate();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public int saveAckCondition(ConditionTypePOJO conditionTypePOJO, Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo, int ackLineItemNo, int condLineItemNo, String typeofcondition, String saleReturnNo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            double ucp = 0;
            int quantity = 0;
            double netAmount1 = 0;
            double netAmount = 0;
            String insertstr = "insert into tbl_ack_condition values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            pstmt.setInt(4, ackLineItemNo);//reflineitemno
            pstmt.setInt(5, condLineItemNo);//cond lineitemno
            pstmt.setInt(6, acknowledgementHeaderPojo.getAcknowledgementdate());//refackdate
            pstmt.setString(7, conditionTypePOJO.getDummyconditiontype());//posconditiontype
            pstmt.setString(8, saleReturnNo);//salereturnno
            pstmt.setString(9, conditionTypePOJO.getPromotionId());//promotionid
            //freegoodscat
            if (conditionTypePOJO.getFreeGoodsCategory() != null) {
                pstmt.setString(10, conditionTypePOJO.getFreeGoodsCategory());
            } else {
                pstmt.setString(10, "");
            }

            if (typeofcondition.equalsIgnoreCase("D") || typeofcondition.equalsIgnoreCase("P")) {
                pstmt.setDouble(11, -conditionTypePOJO.getValue());
            } else {
                pstmt.setDouble(11, conditionTypePOJO.getValue());
            }
            pstmt.setString(12, conditionTypePOJO.getCalculationtype());
            if (typeofcondition.equalsIgnoreCase("D") || typeofcondition.equalsIgnoreCase("P")) {
                pstmt.setDouble(13, -conditionTypePOJO.getNetAmount());
            } else {
                pstmt.setDouble(13, conditionTypePOJO.getNetAmount());
            }
            pstmt.setString(14, typeofcondition);
            //Added by Thangaraj - 07.07.2014
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getUlpno())){
            pstmt.setString(15,conditionTypePOJO.getUlpno());
            }
            else{
               pstmt.setString(15,"");
            }
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyPoints())){
             pstmt.setDouble(16, conditionTypePOJO.getLoyaltyPoints());
            }else{
               pstmt.setDouble(16, 0);
            }
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getAsOnDate())){
            pstmt.setInt(17, conditionTypePOJO.getAsOnDate());
            }
            else{
                pstmt.setInt(17, conditionTypePOJO.getAsOnDate());
            }
            try {
//            pstmt.setDouble(18, conditionTypePOJO.getLoyaltyRedeemedPoints());
                if(Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyRedeemedPoints())){
                pstmt.setDouble(18, Decimalvalue.numberFormatToDouble(Decimalvalue.numberFormat(conditionTypePOJO.getLoyaltyRedeemedPoints())));
                }else{
                    pstmt.setDouble(18, 0);
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
            }
//            if(conditionTypePOJO.getRrno() != null){
            if(Validations.isFieldNotEmpty(conditionTypePOJO.getLoyaltyRedeemedPoints())){
            pstmt.setString(19, conditionTypePOJO.getRrno());
            }
            else{
                pstmt.setString(19, "");
            }
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public int deleteAckCondition(Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            String insertstr = "delete tbl_ack_condition where storecode=? and fiscalyear=? and refacknumber=?";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public int updateAckCondition(Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo, String saleReturnNo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            double ucp = 0;
            int quantity = 0;
            double netAmount1 = 0;
            double netAmount = 0;
            String insertstr = "update tbl_ack_condition set salereturnno=? where storecode=? and fiscalyear=? and refacknumber=? ";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, saleReturnNo);
            pstmt.setString(2, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(3, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(4, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public int saveAckDefectReason(Connection con, AcknowledgementHeaderPOJO acknowledgementHeaderPojo, AckDefectReasonPOJO ackDefectReasonPojo) {
        PreparedStatement pstmt = null;
        int res = 0;
        String query;
        try {
            query = "insert into tbl_ack_defectreason values (?,?,?,?,?,?,?)";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            pstmt.setInt(4, ackDefectReasonPojo.getReflineitemno());//reflineitemno
            pstmt.setInt(5, ackDefectReasonPojo.getRefackdate());//foc ref ack date
            pstmt.setString(6, ackDefectReasonPojo.getDefectreasonID());//defect reason
            pstmt.setString(7, ackDefectReasonPojo.getDefOtherReasonDesc());//Other defect reason
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            query = null;
        }
    }

    public int deletAckDefectReason(Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            String insertstr = "delete tbl_ack_defectreason where storecode=? and fiscalyear=? and refacknumber=?";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public int saveAckFocSpares(Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo, AcknowledgementFocSparesPOJO ackFocItemPojo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;
        try {
            String insertstr = "insert into tbl_ack_focspares values (?,?,?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            pstmt.setInt(4, ackFocItemPojo.getReflineitemno());//reflineitemno
            pstmt.setInt(5, ackFocItemPojo.getLineitemno());//foc lineitemno
            pstmt.setInt(6, ackFocItemPojo.getRefackdate());//refackdate
            pstmt.setString(7, ackFocItemPojo.getMaterialcode());//posconditiontype
            pstmt.setInt(8, ackFocItemPojo.getQty());//salereturnno
            pstmt.setDouble(9, ackFocItemPojo.getUnitprice());//promotionid
            pstmt.setDouble(10, ackFocItemPojo.getAmount());//freegoodscat
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public ArrayList<AcknowledgementFocSparesPOJO> getFOCDetailsByAckNo(Connection con, String ackNo, String storeCode, int fiscalYear) throws SQLException {
        Statement statement = null;
        int res = 0;

        try {
            if (!Validations.isFieldNotEmpty(storeCode)) {
                storeCode = new SiteMasterDO().getSiteId(con);
            }
            if (fiscalYear == 0) {
                fiscalYear = new SiteMasterDO().getFiscalYear(con);
            }
            ArrayList<AcknowledgementFocSparesPOJO> focPOJOs = new ArrayList<AcknowledgementFocSparesPOJO>();
            statement = con.createStatement();
           // String searchstatement = "select * from tbl_ack_focspares where storecode = '" + storeCode + "' and fiscalyear = " + fiscalYear + " and refacknumber = '" + ackNo + "'";
            String searchstatement = "select * from tbl_ack_focspares where storecode = '" + storeCode + "' and refacknumber = '" + ackNo + "'";
            ResultSet rs = statement.executeQuery(searchstatement);
            AcknowledgementFocSparesPOJO focpojoobj;
            String refMaterialCode;
            while (rs.next()) {
                focpojoobj = new AcknowledgementFocSparesPOJO();
                String materialCode = rs.getString("materialcode");
                focpojoobj.setAmount(rs.getDouble("amount"));
                focpojoobj.setFiscalyear(fiscalYear);
                focpojoobj.setLineitemno(rs.getInt("lineitemno"));
                focpojoobj.setMaterialcode(materialCode);
                focpojoobj.setQty(rs.getInt("qty"));
                focpojoobj.setRefackdate(rs.getInt("refackdate"));
                focpojoobj.setRefacknumber(ackNo);
                focpojoobj.setReflineitemno(rs.getInt("reflineitemno"));
                focpojoobj.setStorecode(storeCode);
                focpojoobj.setUnitprice(rs.getDouble("unitprice"));
                refMaterialCode = getRefMaterialCodeForFOC(con, ackNo, storeCode, fiscalYear, focpojoobj.getReflineitemno());
                focpojoobj.setRefmaterialcode(refMaterialCode);
                focPOJOs.add(focpojoobj);
            }
            return focPOJOs;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement.close();
        }

    }

    public int deletAckFocSpares(Connection conn, AcknowledgementHeaderPOJO acknowledgementHeaderPojo) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;

        try {
            String insertstr = "delete tbl_ack_focspares where storecode=? and fiscalyear=? and refacknumber=?";
            pstmt = conn.prepareStatement(insertstr);
            pstmt.setString(1, acknowledgementHeaderPojo.getStorecode());//storecode
            pstmt.setInt(2, acknowledgementHeaderPojo.getFiscalyear());//fiscalyear
            pstmt.setString(3, acknowledgementHeaderPojo.getAcknumber());//refacknumber
            res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            pstmt.close();
        }
    }

    public String getRefMaterialCodeForFOC(Connection con, String ackNo, String storeCode, int fiscalYear, int lineItemNo) throws SQLException {
        String refMatCodeForFOC = null;
        Statement statement = null;
        try {
            if (!Validations.isFieldNotEmpty(storeCode)) {
                storeCode = new SiteMasterDO().getSiteId(con);
            }
            if (fiscalYear == 0) {
                fiscalYear = new SiteMasterDO().getFiscalYear(con);
            }
            statement = con.createStatement();
            String searchstatement = "select materialcode from tbl_ack_lineitems where storecode = '" + storeCode + "' and fiscalyear = " + fiscalYear + " and refacknumber = '" + ackNo + "' and lineitemno = '" + lineItemNo + "' ";
            ResultSet rs = statement.executeQuery(searchstatement);
            if (rs.next()) {
                refMatCodeForFOC = rs.getString("materialcode");
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement.close();
        }
        return refMatCodeForFOC;
    }

    public ArrayList<SOLineItemPOJO> getAcknowledgementItemDetailsByAckNo(Connection con, String ackNo, String storeCode, int fiscalYear) throws SQLException {
        Statement statement = null;
//        String refSaleOrderNo=null;
//        HashMap<String,String>hmap=new HashMap<String, String>();
//        hmap=SalesOrderHeaderDO.getSOLineItemDetailsByOrderNo(con, refOrderNo);
        try {
            if (!Validations.isFieldNotEmpty(storeCode)) {
                storeCode = new SiteMasterDO().getSiteId(con);
            }
            if (fiscalYear == 0) {
                fiscalYear = new SiteMasterDO().getFiscalYear(con);
            }
            ArrayList<SOLineItemPOJO> acknowledgementItemPOJOs = new ArrayList<SOLineItemPOJO>();
            statement = con.createStatement();//Code commented and changed for fiscal year prob on April 5th 2010
          //  String searchstatement = "select * from tbl_ack_lineitems where storecode = '" + storeCode + "' and fiscalyear = " + fiscalYear + " and refacknumber = '" + ackNo + "'";
             String searchstatement = "select * from tbl_ack_lineitems where storecode = '" + storeCode + "'  and refacknumber = '" + ackNo + "'";
            ResultSet rs = statement.executeQuery(searchstatement);
            SOLineItemPOJO acknowledgementitempojoobj;
            int lineitemno = 0;
            while (rs.next()) {
                lineitemno = rs.getInt("lineitemno");
                acknowledgementitempojoobj = new SOLineItemPOJO();
                String materialCode = rs.getString("materialcode");
                acknowledgementitempojoobj.setMaterial(materialCode);
                acknowledgementitempojoobj.setQuantity(rs.getInt("billedqty"));
                acknowledgementitempojoobj.setNetamount(rs.getDouble("billednetamount"));

                acknowledgementitempojoobj.setReturnedqty(rs.getInt("returnedqty"));

                acknowledgementitempojoobj.setUCP(getAckCondition(con, ackNo, lineitemno, "U", false));
                acknowledgementitempojoobj.setDiscountSelected(getAckCondition(con, ackNo, lineitemno, "D", false));
                acknowledgementitempojoobj.setULPdiscountSelected(getAckCondition(con, ackNo, lineitemno, "D", true));//Added by Thangaraj - 07.07.2014
                acknowledgementitempojoobj.setTaxDetails(getArrayAckCondition(con, ackNo, lineitemno, "T"));
                acknowledgementitempojoobj.setOfferPromotion(getAckCondition(con, ackNo, lineitemno, "P", false));

                acknowledgementitempojoobj.setOtherCharges(getArrayAckCondition(con, ackNo, lineitemno, "O"));
                if (acknowledgementitempojoobj.getOtherCharges() != null) {
                    acknowledgementitempojoobj.setOtherChargesPresent(true);
                }
                acknowledgementitempojoobj.setReturnednetamount(rs.getDouble("returnednetamount"));
                acknowledgementitempojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                acknowledgementitempojoobj.setReturnreason(rs.getString("returnreason"));
                acknowledgementitempojoobj.setOtherretreason(rs.getString("otherretreason"));
                fiscalYear = rs.getInt("fiscalyear");
                ArrayList<AckDefectReasonPOJO> ackDefectReasonPojos = getDefectReasonPojos(con, storeCode, fiscalYear, ackNo, lineitemno);
                acknowledgementitempojoobj.setAckdefectreasonpojos(ackDefectReasonPojos);
                
                String sellingPromotionID = rs.getString("sellingpromotionid");
                if (Validations.isFieldNotEmpty(sellingPromotionID)) {
                    PromotionConditionTypePOJO promotionConditionTypePOJO = new PromotionConditionTypePOJO();
                    promotionConditionTypePOJO.setPromotionID(sellingPromotionID);
                    acknowledgementitempojoobj.setPromotionSelected(promotionConditionTypePOJO);
                }
                acknowledgementitempojoobj.setFrombatch(rs.getString("frombatch"));
//                if(hmap.size()>0){
//                    String fromBatch=hmap.get(rs.getString("materialcode"));
//                    acknowledgementitempojoobj.setFrombatch(fromBatch);
//                }
                
                acknowledgementItemPOJOs.add(acknowledgementitempojoobj);
                

            }
            
            return acknowledgementItemPOJOs;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement.close();
        }

    }

    public ConditionTypePOJO getAckCondition(Connection conn, String ackNo, int lineItemNo, String flag, boolean ULPdiscountCheck) throws SQLException {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt = null;
        try {
            if(flag.equalsIgnoreCase("D") && ULPdiscountCheck) {
            pstmt = conn.prepareStatement("select * from tbl_ack_condition where refacknumber = ? and reflineitemno=? and typeofcondition=? and poscondtype =?");
            }
            else if(flag.equalsIgnoreCase("D")){
                pstmt = conn.prepareStatement("select * from tbl_ack_condition where refacknumber = ? and reflineitemno=? and typeofcondition=? and poscondtype !=?");
            }
            else{
                pstmt = conn.prepareStatement("select * from tbl_ack_condition where refacknumber = ? and reflineitemno=? and typeofcondition=?");
            }
            pstmt.setString(1, ackNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            if(flag.equalsIgnoreCase("D")) {//Added by Thangaraj - 07.07.2014
            pstmt.setString(4,"ZLOM");
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("poscondtype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJO.setPromotionId(rs.getString("promotionid"));
                conditionTypePOJO.setFreeGoodsCategory(rs.getString("freegoodscat"));
                  //Added by Thangaraj - 07.07.2014
                conditionTypePOJO.setUlpno(rs.getString("ulpno"));
                conditionTypePOJO.setAsOnDate(rs.getInt("loyaltyasondate"));
                conditionTypePOJO.setRrno(rs.getString("loyaltyapprovalno"));
                conditionTypePOJO.setLoyaltyPoints(rs.getDouble("loyaltypoints"));
                conditionTypePOJO.setLoyaltyRedeemedPoints(rs.getDouble("loyaltyredeemedpoints"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
        }
        return conditionTypePOJO;
    }

    public ArrayList<ConditionTypePOJO> getArrayAckCondition(Connection conn, String ackNo, int lineItemNo, String flag) throws SQLException {
        ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement("select * from tbl_ack_condition where refacknumber = ? and reflineitemno=? and typeofcondition=?");
            pstmt.setString(1, ackNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (i == 0) {
                    conditionTypePOJOs = new ArrayList<ConditionTypePOJO>();
                }
                i++;
                ConditionTypePOJO conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("poscondtype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJOs.add(conditionTypePOJO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
        }
        return conditionTypePOJOs;
    }

    public ArrayList<AckDefectReasonPOJO> getDefectReasonPojos(Connection con, String storeCode, int fiscalYear, String ackNo, int refLineItemNo) throws SQLException {
        ArrayList<AckDefectReasonPOJO> ackDefReasonPojos = null;
        PreparedStatement pstmt = null;
        AckDefectReasonPOJO ackDefReasonPojo;
        try {
            pstmt = con.prepareStatement("select defectreason,refackdate,otherdefectreason from tbl_ack_defectreason where storecode=? and fiscalyear=? and refacknumber = ? and reflineitemno=? ");
            pstmt.setString(1, storeCode);
            pstmt.setInt(2, fiscalYear);
            pstmt.setString(3, ackNo);
            pstmt.setInt(4, refLineItemNo);
            ResultSet rs = pstmt.executeQuery();
            int i = 0;

            while (rs.next()) {
                if (i == 0) {
                    ackDefReasonPojos = new ArrayList<AckDefectReasonPOJO>();
                }
                i++;
                ackDefReasonPojo = new AckDefectReasonPOJO();
                ackDefReasonPojo.setStorecode(storeCode);
                ackDefReasonPojo.setFiscalyear(fiscalYear);
                ackDefReasonPojo.setRefacknumber(ackNo);
                ackDefReasonPojo.setReflineitemno(refLineItemNo);
                ackDefReasonPojo.setRefackdate(rs.getInt("refackdate"));
                ackDefReasonPojo.setDefectreasonID(rs.getString("defectreason"));
                ackDefReasonPojo.setDefOtherReasonDesc(rs.getString("otherdefectreason"));
                ackDefReasonPojos.add(ackDefReasonPojo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            pstmt = null;
            ackDefReasonPojo = null;
        }
        return ackDefReasonPojos;
    }
}

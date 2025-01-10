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
 * This class file is used as a Data Object between SalesOrder Billing Form and Database
 * This is used for transaction of SalesOrder Billing data from and to the database
 * 
 * 
 * 
 */
package ISRetail.SalesOrderBilling;

import ISRetail.Webservices.ConditionTypePOJO;
import ISRetail.Webservices.PromotionConditionTypePOJO;
import ISRetail.billing.BillingHeaderPOJO;
import ISRetail.paymentdetails.AdvanceReceiptPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.GenerateNextPosDocNumber;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComboBox;

public class SalesOrderBillingDO {

    /**
     * The following function is used to get previous payment details from
     * advance recepitable
     */
    public ArrayList getSaleorderlistforadvrecptno(Connection conn, String searchstatement) {
        Statement pstmt;
        ResultSet rs;
        ArrayList<SalesOrderBillingPOJO> saleorderlist;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);
            saleorderlist = new ArrayList<SalesOrderBillingPOJO>();
            int i = 0;
            while (rs.next()) {
                SalesOrderBillingPOJO saleorderlistpojoobj = new SalesOrderBillingPOJO();
                saleorderlistpojoobj.setSalesorderno(rs.getString(3));
                saleorderlistpojoobj.setCustomercode(rs.getString(4));
                saleorderlistpojoobj.setSaleorderdate(rs.getInt(5));
                saleorderlistpojoobj.setCustomername(rs.getString(7));
                saleorderlist.add(saleorderlistpojoobj);
            }
            return saleorderlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * The following function is used to get saleorderdetails and customer
     * details
     */
    SalesOrderBillingPOJO searchSaleorderno(Connection con, String salorderno_for_serarch) {
        SalesOrderBillingPOJO SERCHSOB = new SalesOrderBillingPOJO();
        PreparedStatement statement = null;
        ResultSet rs;
        try {
            statement = con.prepareStatement("select s.saleorderno ,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,s.amount,c.customercode ,c.firstname,c.lastname,c.mobileno from tbl_salesorderheader as  s ,tbl_customermastermain as c where s.saleorderno=? and s.customercode=c.customercode and s.orderstatus='OPEN'");
            statement.setString(1, salorderno_for_serarch);
            rs = statement.executeQuery();
            if (rs.next()) {
                SERCHSOB.setSalesorderno(rs.getString(1));
                SERCHSOB.setCustomercode(rs.getString(2));
                SERCHSOB.setDatasheetno(rs.getString(3));
                SERCHSOB.setSaleorderdate(rs.getInt(4));
                SERCHSOB.setSaletype(rs.getString(5));
                SERCHSOB.setCreditreference(rs.getString(6));
                SERCHSOB.setNetbillamount(rs.getDouble(7));
                SERCHSOB.setCustomername(rs.getString(9));
                SERCHSOB.setCustomerlastname(rs.getString(10));
                SERCHSOB.setTelephoneno(rs.getString(11));
                return SERCHSOB;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            statement = null;
            rs = null;
        }

    }

    /**
     * The following function is used to get previous payment details from
     * advance recepitable
     */
    public ArrayList getPrevpaymentdetails(Connection con, String salorderno_for_serarch) {
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<SalesOrderBillingPaymentPOJO> Paymentlist;
        try {
            int i = 1;
            statement = con.prepareStatement("select documentno ,refno,documentdate,totalamount from tbl_advancereceiptheader where refno=? and cancelledstatus='N'");
            statement.setString(1, salorderno_for_serarch);
            rs = statement.executeQuery();
            Paymentlist = new ArrayList<SalesOrderBillingPaymentPOJO>();
            while (rs.next()) {
                SalesOrderBillingPaymentPOJO salesorderbillingPaymentpojoobj = new SalesOrderBillingPaymentPOJO();
                salesorderbillingPaymentpojoobj.setSlno(i);
                salesorderbillingPaymentpojoobj.setAdvancerecepitno(rs.getString(1));
                salesorderbillingPaymentpojoobj.setDocdate(rs.getInt(3));
                salesorderbillingPaymentpojoobj.setAmount(rs.getDouble(4));
                i++;
                Paymentlist.add(salesorderbillingPaymentpojoobj);
            }
            return Paymentlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement = null;
            rs = null;
        }

    }

    /**
     * The following function is used to get previous payment details
     */
    public ArrayList getPrevInvoicelist(Connection con, String invoiceForSerarch) {
        PreparedStatement statement;
        ResultSet rs;
        ArrayList<SalesOrderBillingPaymentPOJO> Paymentlist;
        try {
            int i = 1;
            statement = con.prepareStatement("select invoiceno ,refno,invoicedate,invoicevalue from tbl_billingheader where invoiceno=?");
            statement.setString(1, invoiceForSerarch);
            rs = statement.executeQuery();
            Paymentlist = new ArrayList<SalesOrderBillingPaymentPOJO>();
            while (rs.next()) {
                SalesOrderBillingPaymentPOJO salesorderbillingPaymentpojoobj = new SalesOrderBillingPaymentPOJO();
                salesorderbillingPaymentpojoobj.setSlno(i);
                salesorderbillingPaymentpojoobj.setAdvancerecepitno(rs.getString(1));
                salesorderbillingPaymentpojoobj.setDocdate(rs.getInt(3));
                salesorderbillingPaymentpojoobj.setAmount(rs.getDouble(4));
                i++;
                Paymentlist.add(salesorderbillingPaymentpojoobj);
            }
            return Paymentlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement = null;
            rs = null;
        }

    }

    /**
     * The following function is used to get previous payment details from
     * advance recepitable
     */
    public Vector getAdvanceReceiptList(Connection conn, Vector advlistadv, String searchstmt) {
        Statement pstmt;
        ResultSet rs;
        AdvanceReceiptPOJO advancerecpojoobj;
        try {
            pstmt = (Statement) conn.createStatement();
            rs = pstmt.executeQuery(searchstmt);
            while (rs.next()) {
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
                advlistadv.add(advancerecpojoobj);
            }
            return advlistadv;
        } catch (SQLException e) {
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * The following function is used to get Sale order List
     */
    public ArrayList getSaleorderlist(Connection conn, String searchstatement) {
        Statement pstmt;
        ResultSet rs;
        ArrayList<SalesOrderBillingPOJO> saleorderlist;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);
            saleorderlist = new ArrayList<SalesOrderBillingPOJO>();
            int i = 0;
            while (rs.next()) {
                SalesOrderBillingPOJO saleorderlistpojoobj = new SalesOrderBillingPOJO();
                saleorderlistpojoobj.setSalesorderno(rs.getString(1));
                saleorderlistpojoobj.setSaleorderdate(rs.getInt(2));
                saleorderlistpojoobj.setCustomercode(rs.getString(3));
                saleorderlistpojoobj.setCustomername(rs.getString(4));
                saleorderlistpojoobj.setCustomerlastname(rs.getString(5));
                saleorderlist.add(saleorderlistpojoobj);
            }
            return saleorderlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * The following function is used to get Sale order Details for the invoice
     * Number
     */
    public ArrayList<SOLineItemPOJO> getSaleOrderDetailsByInvoiceno(Connection conn, String saleorderno) {
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList<SOLineItemPOJO> list;
        try {
            pstmt = conn.prepareStatement("select * from tbl_billinglineitem where invoiceno=? order by lineitemno");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            list = new ArrayList<SOLineItemPOJO>();
            int lineitemno;
            while (rs.next()) {
                SOLineItemPOJO salesorderdetailspojoobj = new SOLineItemPOJO();
                lineitemno = rs.getInt("lineitemno");
                salesorderdetailspojoobj.setFoc(rs.getString("foc"));
                salesorderdetailspojoobj.setSite(rs.getString("storecode"));
                salesorderdetailspojoobj.setMaterial(rs.getString("materialcode"));
                salesorderdetailspojoobj.setBatchId(rs.getString("batchid"));
                salesorderdetailspojoobj.setExpDate(rs.getString("expirydate"));
                salesorderdetailspojoobj.setQuantity(rs.getInt("qty"));
                salesorderdetailspojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                salesorderdetailspojoobj.setNetamount(rs.getDouble("netamount"));
                salesorderdetailspojoobj.setUCP(getSOCondition(conn, saleorderno, lineitemno, "U", false));
                salesorderdetailspojoobj.setDiscountSelected(getSOCondition(conn, saleorderno, lineitemno, "D", false));
                salesorderdetailspojoobj.setULPdiscountSelected(getSOCondition(conn, saleorderno, lineitemno, "D", true));
                salesorderdetailspojoobj.setOfferPromotion(getSOCondition(conn, saleorderno, lineitemno, "P", false));
                salesorderdetailspojoobj.setTaxDetails(getArraySOCondition(conn, saleorderno, lineitemno, "T"));
                salesorderdetailspojoobj.setOtherCharges(getArraySOCondition(conn, saleorderno, lineitemno, "O"));
                if (salesorderdetailspojoobj.getOtherCharges() != null) {
                    salesorderdetailspojoobj.setOtherChargesPresent(true);
                }
                salesorderdetailspojoobj.setStyleConsultant(rs.getString("styleconsultant"));
                String sellingPromotionID = rs.getString("sellingpromotionid");
                salesorderdetailspojoobj.setDiscountRefNo(rs.getString("discountrefno"));
                if (Validations.isFieldNotEmpty(sellingPromotionID)) {
                    PromotionConditionTypePOJO promotionConditionTypePOJO = new PromotionConditionTypePOJO();
                    promotionConditionTypePOJO.setPromotionID(sellingPromotionID);
                    salesorderdetailspojoobj.setPromotionSelected(promotionConditionTypePOJO);
                }
                salesorderdetailspojoobj.setLineItemEmpid(rs.getString("empid"));
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
     * The following function is used to get Sale order Condition Details for
     * the invoice Number
     */
    public ConditionTypePOJO getSOCondition(Connection conn, String saleOrderNo, int lineItemNo, String flag, boolean ULPdiscountCheck) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            //Added by Dileep - 16.06.2014
            if (flag.equalsIgnoreCase("D") && ULPdiscountCheck) {
                pstmt = conn.prepareStatement("select * from dbo.tbl_billing_condition where invoiceno = ? and reflineitemno=? and typeofcondition=? and dummyconditiontype =?");
            } else if (flag.equalsIgnoreCase("D")) {
                pstmt = conn.prepareStatement("select * from dbo.tbl_billing_condition where invoiceno = ? and reflineitemno=? and typeofcondition=? and dummyconditiontype !=?");
            } else {
                pstmt = conn.prepareStatement("select * from dbo.tbl_billing_condition where invoiceno = ? and reflineitemno=? and typeofcondition=?");
            }
            pstmt.setString(1, saleOrderNo);
            pstmt.setInt(2, lineItemNo);
            pstmt.setString(3, flag);
            if (flag.equalsIgnoreCase("D")) {
                pstmt.setString(4, "ZLOM");
            }
            rs = pstmt.executeQuery();

            if (rs.next()) {
                conditionTypePOJO = new ConditionTypePOJO();
                conditionTypePOJO.setDummyconditiontype(rs.getString("dummyconditiontype"));
                conditionTypePOJO.setCalculationtype(rs.getString("calctype"));
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJO.setFreeGoodsCategory(rs.getString("freegoodscat"));
                conditionTypePOJO.setPromotionId(rs.getString("promotinid"));
                conditionTypePOJO.setPromotionLineno(rs.getString("promotionlineno"));
                conditionTypePOJO.setPromotionRandomNo(rs.getInt("promotiongroupid"));
                conditionTypePOJO.setAsOnDate(rs.getInt("loyaltyasondate"));
                conditionTypePOJO.setUlpno(rs.getString("ulpno"));
                conditionTypePOJO.setRrno(rs.getString("loyaltyapprovalno"));
                conditionTypePOJO.setLoyaltyPoints(rs.getDouble("loyaltypoints"));
                conditionTypePOJO.setLoyaltyRedeemedPoints(rs.getDouble("loyaltyredeemedpoints"));

            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }

        return conditionTypePOJO;
    }

    /**
     * The following function is used to get Billing order Line Items as a
     * Collection for Invoice
     */
    public ArrayList<SOLineItemPOJO> getSaleOrderDetailsByInvoicenoForDisplay(Connection conn, String saleorderno) {

        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from tbl_billinglineitem where invoiceno=? order by lineitemno");
            pstmt.setString(1, saleorderno);
            rs = pstmt.executeQuery();
            ArrayList<SOLineItemPOJO> list = new ArrayList<SOLineItemPOJO>();
            int lineitemno;
            while (rs.next()) {
                SOLineItemPOJO salesorderdetailspojoobj = new SOLineItemPOJO();
                lineitemno = rs.getInt("lineitemno");
                salesorderdetailspojoobj.setFoc(rs.getString("foc"));
                salesorderdetailspojoobj.setMaterial(rs.getString("materialcode"));
                salesorderdetailspojoobj.setBatchId(rs.getString("batchid"));
                salesorderdetailspojoobj.setQuantity(rs.getInt("qty"));
                salesorderdetailspojoobj.setTaxableValue(rs.getDouble("taxablevalue"));
                salesorderdetailspojoobj.setNetamount(rs.getDouble("netamount"));
                salesorderdetailspojoobj.setUCP(getSOConditionForDisplay(conn, saleorderno, lineitemno, "U"));
                salesorderdetailspojoobj.setTaxDetails(getArraySOCondition(conn, saleorderno, lineitemno, "T"));
                salesorderdetailspojoobj.setDiscountSelected(getSOConditionForDisplay(conn, saleorderno, lineitemno, "D"));
                salesorderdetailspojoobj.setOfferPromotion(getSOConditionForDisplay(conn, saleorderno, lineitemno, "P"));
                salesorderdetailspojoobj.setOtherCharges(getArraySOCondition(conn, saleorderno, lineitemno, "O"));
                if (salesorderdetailspojoobj.getOtherCharges() != null) {
                    salesorderdetailspojoobj.setOtherChargesPresent(true);
                }
                salesorderdetailspojoobj.setStyleConsultant(rs.getString("styleconsultant"));
                list.add(salesorderdetailspojoobj);
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
     * The following function is used to get Billing Condition for Display
     */
    public ConditionTypePOJO getSOConditionForDisplay(Connection conn, String saleOrderNo, int lineItemNo, String flag) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from dbo.tbl_billing_condition left outer join tbl_conditiontypemaster on tbl_billing_condition.dummyconditiontype =tbl_conditiontypemaster.poscondtype where invoiceno = ? and reflineitemno=? and typeofcondition=?");
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
            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }

        return conditionTypePOJO;
    }

    /**
     * The following function is used to get Material Description for the
     * Article
     */
    public ConditionTypePOJO getMaterialDiscription(Connection conn, String material) {
        ConditionTypePOJO conditionTypePOJO = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = conn.prepareStatement("select materialdescription from dbo.tbl_articlemaster where materialcode = ?");
            pstmt.setString(1, material);

            rs = pstmt.executeQuery();
            conditionTypePOJO = new ConditionTypePOJO();
            if (rs.next()) {
                conditionTypePOJO.setDummyconditiontype(rs.getString("materialdescription"));
            }
        } catch (Exception e) {

        }
        return conditionTypePOJO;
    }

    /**
     * The following function is used to get List of Billing Condition
     */
    public ArrayList<ConditionTypePOJO> getArraySOCondition(Connection conn, String saleOrderNo, int lineItemNo, String flag) {

        ArrayList<ConditionTypePOJO> conditionTypePOJOs = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select * from dbo.tbl_billing_condition where invoiceno = ? and reflineitemno=? and typeofcondition=?");
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
                conditionTypePOJO.setValue(rs.getDouble("amount"));
                conditionTypePOJO.setNetAmount(rs.getDouble("netamount"));
                conditionTypePOJOs.add(conditionTypePOJO);
            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return conditionTypePOJOs;
    }

    /**
     * The following function is used to save billing cancel Header
     */
    public int saveBillingCancelderheader(BillingHeaderPOJO BillingHeaderPOJOobj, Connection con) throws SQLException {
        int result_billing = 0;
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("insert into tbl_billcancelheader values  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, BillingHeaderPOJOobj.getStorecode());
            statement.setInt(2, BillingHeaderPOJOobj.getFiscalyear());
            statement.setString(3, BillingHeaderPOJOobj.getInvoiceno());
            statement.setInt(4, BillingHeaderPOJOobj.getInvoicedate());
            statement.setString(5, BillingHeaderPOJOobj.getRefno());
            statement.setString(6, BillingHeaderPOJOobj.getCustomercode());
            statement.setDouble(7, BillingHeaderPOJOobj.getInvoicevalue());
            statement.setString(8, BillingHeaderPOJOobj.getFirstprint());
            statement.setDouble(9, BillingHeaderPOJOobj.getRoundoff());
            statement.setString(10, BillingHeaderPOJOobj.getBilledfrom());
            statement.setString(11, BillingHeaderPOJOobj.getSaprefno());
            statement.setString(12, BillingHeaderPOJOobj.getAccsaprefno());
            statement.setString(13, BillingHeaderPOJOobj.getCancelstatus());
            statement.setString(14, BillingHeaderPOJOobj.getReasonforcancellation());
            statement.setString(15, BillingHeaderPOJOobj.getCreatedby());
            statement.setInt(16, BillingHeaderPOJOobj.getCreateddate());
            statement.setString(17, BillingHeaderPOJOobj.getCreatedtime());
            statement.setString(18, BillingHeaderPOJOobj.getModifiedby());
            statement.setInt(19, BillingHeaderPOJOobj.getModifieddate());
            statement.setString(20, BillingHeaderPOJOobj.getModifiedtime());
            result_billing = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            statement = null;
        }

        return result_billing;
    }

    /**
     * The following function is used to Update the advance header if the Sale
     * order is cancelled
     */
    public int Updateadvancereceiptheader(Connection con, String saleorderno, String creditnotenopos) throws SQLException {
        int rs;
        Statement pstmtup;
        try {
            pstmtup = (Statement) con.createStatement();
            rs = pstmtup.executeUpdate("update tbl_advancereceiptheader set cancelledstatus='Y',creditnotenopos='" + creditnotenopos + "' where refno='" + saleorderno + "'");
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            pstmtup = null;
        }
        return rs;
    }

    /**
     * The following function is used to Update the Sale Order status Updation
     */
    public int saleOrderUpdation(Connection con, String saleorderno, BillingHeaderPOJO HJU) throws SQLException {
        int saleUpation = 0;
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("update tbl_salesorderheader set orderstatus=?,cancelledby=?,cancelledtime=?,reasonforcancellation=?, dateofcancellation=? where saleorderno=?");
            statement.setString(1, "CANCELED");
            statement.setString(2, HJU.getCreatedby());
            statement.setString(3, HJU.getCreatedtime());
            statement.setString(4, HJU.getReasonforcancellation());
            statement.setInt(5, HJU.getCreateddate());
            statement.setString(6, saleorderno);
            saleUpation = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        } finally {
            statement = null;
        }
        return saleUpation;
    }

    /**
     * The following function is used to Update the Sale Order status Updation
     * after Billing
     */
    public int saleOrderUpdationForsaleorderbilling(Connection con, String saleorderno, BillingHeaderPOJO HJU) throws SQLException {
        int saleUpation = 0;
        PreparedStatement statement;
        try {

            statement = con.prepareStatement("update tbl_salesorderheader set orderstatus=? where saleorderno=?");
            statement.setString(1, "OPEN");
            statement.setString(2, saleorderno);
            saleUpation = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        } finally {
            statement = null;
        }

        return saleUpation;
    }

    /**
     * The following function is used to Update the Billing Header after Billing
     */
    public int billingUpdation(Connection con, String invoiceno, BillingHeaderPOJO HJU) throws SQLException {
        int saleUpation = 0;
        PreparedStatement statement;
        try {
            statement = con.prepareStatement("update tbl_billingheader set cancelstatus=?,reasonforcancellation=?,modifiedby=?,modifieddate=?,modifiedtime=? where invoiceno=?");
            statement.setString(1, "Y");
            statement.setString(2, HJU.getReasonforcancellation());
            statement.setString(3, HJU.getCreatedby());
            statement.setInt(4, HJU.getCreateddate());
            statement.setString(5, HJU.getCreatedtime());
            statement.setString(6, invoiceno);
            saleUpation = statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new SQLException();
        } finally {
            statement = null;
        }

        return saleUpation;
    }

    /**
     * The following function is used to for Search
     */
    public ArrayList getSaleorderlistforadvsearch(Connection conn, String searchstatement) {
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<SalesOrderBillingPOJO> saleorderlist = new ArrayList<SalesOrderBillingPOJO>();
            int i = 0;
            while (rs.next()) {
                SalesOrderBillingPOJO saleorderlistpojoobj = new SalesOrderBillingPOJO();
                saleorderlistpojoobj.setSalesorderno(rs.getString("saleorderno"));
                saleorderlistpojoobj.setCustomercode(rs.getString("customercode"));
                saleorderlistpojoobj.setSaleorderdate(rs.getInt("saleorderdate"));
                saleorderlistpojoobj.setCustomername(rs.getString("firstname"));
                saleorderlist.add(saleorderlistpojoobj);

            }
            return saleorderlist;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    /**
     * The following function is used to get the Sale order header for sending
     * the same to webservice
     */
    public SalesOrderBillingPOJO getHeaderForWebservice(Connection conn, String invoiceno, String creditNoteNo) {
        SalesOrderBillingPOJO header = new SalesOrderBillingPOJO();
        double roundoff = 0;
        double netamount = 0;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            // added for ULP development in POS
            //pstmt= conn.prepareStatement("select s.saleorderno,s.saletype,s.creditsalereference,i.invoiceno,i.invoicedate,i.roundoff,i.createdby,i.createdtime,i.createddate,i.fiscalyear,i.storecode,s.orderstatus,i.freefield1,i.freefield2,i.freefield3,i.freefield4,i.freefield5 from tbl_salesorderheader as s ,tbl_billingheader as i where s.saleorderno=i.refno and i.invoiceno=?");
//            pstmt= conn.prepareStatement("select s.saleorderno,s.saletype,s.creditsalereference,i.invoiceno,i.invoicedate,i.roundoff,i.createdby,i.createdtime,i.createddate,i.fiscalyear,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno from tbl_salesorderheader as s ,tbl_billingheader as i where s.saleorderno=i.refno and i.invoiceno=?");
            // Code Added by BALA for Comfort data,time and empid on 10.10.2017
//            pstmt = conn.prepareStatement("select s.saleorderno,s.saletype,s.creditsalereference,i.invoiceno,i.invoicedate,i.roundoff,i.createdby,i.createdtime,i.createddate,i.fiscalyear,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno,i.comfortdate,i.comforttime,i.empid from tbl_salesorderheader as s ,tbl_billingheader as i where s.saleorderno=i.refno and i.invoiceno=?");
            pstmt = conn.prepareStatement("select s.saleorderno,s.saletype,s.creditsalereference,i.invoiceno,i.invoicedate,i.roundoff,i.createdby,i.createdtime,i.createddate,i.fiscalyear,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno,i.comfortdate,i.comforttime,i.empid,i.refname,i.refinvoiceno,i.refmobileno,i.insuranceId from tbl_salesorderheader as s ,tbl_billingheader as i where s.saleorderno=i.refno and i.invoiceno=?");
            // Code Added by BALA for Comfort data,time and empid on 10.10.2017
            pstmt.setString(1, invoiceno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                header.setSalesorderno(rs.getString("saleorderno"));
                header.setSaletype(rs.getString("saletype"));
                header.setCreditreference(rs.getString("creditsalereference"));
                header.setInvoiceno(rs.getString("invoiceno"));
                header.setInvoicedate(rs.getInt("invoicedate"));
                header.setRoundoff(rs.getDouble("roundoff"));
                header.setCreatedby(rs.getString("createdby"));
                header.setCreatedtime(rs.getString("createdtime"));
                header.setCreateddate(rs.getInt("createddate"));
                header.setFiscalyear(rs.getInt("fiscalyear"));
                header.setStorecode(rs.getString("storecode"));
                header.setOrderStatus(rs.getString("orderstatus"));
//                header.setFreefield1(rs.getString("freefield1"));
//                header.setFreefield2(rs.getString("freefield2"));
//                header.setFreefield3(rs.getString("freefield3"));
//                // added for ULP development in POS
//                //header.setFreefield4(rs.getInt("freefield4"));
//                //header.setFreefield5(rs.getDouble("freefield5"));
//                header.setFreefield4(rs.getString("freefield4"));
//                header.setFreefield5(rs.getString("freefield5"));
                header.setFreefield1(rs.getString("membershipcardno"));
                header.setFreefield2(rs.getString("vistarefgiftvocno"));
                header.setFreefield3(rs.getString("unifiedloyno"));
                header.setFreefield4(rs.getString("existingloyno"));
                header.setFreefield5(rs.getString("existingchannel"));
                header.setVistaRefValNo(rs.getString("vistarefvalno"));
                // Code Added by BALA for Comfort data,time and empid on 10.10.2017
                header.setComfortdate(rs.getInt("comfortdate"));
                header.setComforttime(rs.getString("comforttime"));
                header.setEmpid(rs.getString("empid"));
                // Code Added by BALA for Comfort data,time and empid on 10.10.2017
                //Added by Balachander on 21.6.2018 to capture referral mobile number and name
                header.setRefname(rs.getString("refname"));
                header.setRefinvoiceno(rs.getString("refinvoiceno"));
                header.setRefmobileno(rs.getString("refmobileno"));
                header.setZopperInsuranceId(rs.getString("insuranceId"));
                //Ended by Balachander on 21.6.2018 to capture referral mobile number and name
            }
            if (header.getRoundoff() > 0 || header.getRoundoff() < 0) {
                PreparedStatement statment = conn.prepareStatement("select lineitemno,materialcode,netamount from tbl_billinglineitem where invoiceno=? ");
                statment.setString(1, invoiceno);
                ResultSet result = statment.executeQuery();
                while (result.next()) {
                    netamount = result.getDouble("netamount");
                    if (netamount > 1) {
                        header.setLineitemno(result.getInt("lineitemno"));
                        header.setMatrialcode(result.getString("materialcode"));
                        header.setNetbillamount(result.getDouble("netamount"));
                        break;
                    }
                }
            }
            if (creditNoteNo != null) {
                PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? and category='RF'");
                statment.setString(1, creditNoteNo);
                ResultSet resultCredit = statment.executeQuery();
                while (resultCredit.next()) {
                    header.setExcessamount(resultCredit.getDouble("amount"));
                    header.setExcessReftype(resultCredit.getString("reftype"));
                    header.setCreditNoteNo(creditNoteNo);
                    header.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                }
            }
            //Code added on July 7th 2010 for capturing NR credit note details

            PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where refno=? and category='NR'");
            statment.setString(1, invoiceno);
            ResultSet resultCredit = statment.executeQuery();
            while (resultCredit.next()) {
                header.setNrcreditnoteno(resultCredit.getString("creditnoteno"));
                header.setNrexcessamt(resultCredit.getDouble("amount"));
                header.setExcessReftype(resultCredit.getString("reftype"));
                header.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
            }

            //End of Code added on July 7th 2010 for capturing NR credit note details  
        } catch (SQLException ee) {
            ee.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return header;
    }

    /**
     * The following function is used for Updating the SAP refernece ID for the
     * Invoice
     */
    public int UpdateadvsapreferenceidForInvoiceCreation(Connection con, String invoiceno, String sapreferenceid) {
        Statement pstmtup;
        try {

            pstmtup = (Statement) con.createStatement();
            int rs = pstmtup.executeUpdate("update dbo.tbl_billingheader set saprefno='" + sapreferenceid + "' where invoiceno='" + invoiceno + "'");

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
        }
    }

    /**
     * The following function is used for Updating the header for Billing for
     * Invoice Cancel
     */
    public BillingHeaderPOJO getHeaderForWebserviceForInvoiceCancel(Connection conn, String invoiceno, String CreditNoteNo) {
        BillingHeaderPOJO header = new BillingHeaderPOJO();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select tbl_billcancelheader.invoiceno,tbl_billcancelheader.invoicecancellationno,tbl_billcancelheader.invoicecancellationdate,tbl_billingheader.saprefno ,tbl_billcancelheader.cancelstatus,tbl_billcancelheader.reasonforcancellation,tbl_billcancelheader.fiscalyear,tbl_billcancelheader.createdby,tbl_billcancelheader.createddate,tbl_billcancelheader.createdtime,tbl_billingheader.accsaprefno,tbl_billcancelheader.billedfrom,tbl_billcancelheader.storecode,tbl_billcancelheader.cancelotp from tbl_billcancelheader left outer join tbl_billingheader on tbl_billcancelheader.invoiceno = tbl_billingheader.invoiceno where  tbl_billcancelheader.invoicecancellationno = ?");
            pstmt.setString(1, invoiceno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                header.setInvoiceno(rs.getString(1));
                header.setInvoicecancelno(rs.getString(2));
                header.setInvoicedate(rs.getInt(3));
                header.setSaprefno(rs.getString(4));
                header.setCancelstatus(rs.getString(5));
                header.setReasonforcancellation(rs.getString(6));
                header.setFiscalyear(rs.getInt(7));
                header.setCreatedby(rs.getString(8));
                header.setCreateddate(rs.getInt(9));
                header.setCreatedtime(rs.getString(10));
                header.setAccsaprefno(rs.getString(11));
                header.setBilledfrom(rs.getString(12));
                header.setStorecode(rs.getString(13));
                header.setCancelOtp(rs.getString(14));
                if (CreditNoteNo != null) {
                    PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? and category='RF'");
                    statment.setString(1, CreditNoteNo);
                    ResultSet resultCredit = statment.executeQuery();
                    while (resultCredit.next()) {
                        header.setExcessamount(resultCredit.getDouble("amount"));
                        header.setExcessReftype(resultCredit.getString("reftype"));
                        header.setCreditNoteno(CreditNoteNo);
                        header.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                    }
                }
                //Code added on July 8th 2010 for capturing NR credit note details

                PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where refno=? and category='NR'");
                statment.setString(1, header.getInvoicecancelno());
                ResultSet resultCredit = statment.executeQuery();
                while (resultCredit.next()) {
                    header.setNrcreditnoteno(resultCredit.getString("creditnoteno"));
                    header.setNrexcessamt(resultCredit.getDouble("amount"));
                    header.setExcessReftype(resultCredit.getString("reftype"));
                    header.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                }

                //End of Code added on July 8th 2010 for capturing NR credit note details  
            }
        } catch (SQLException ee) {
            ee.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return header;

    }

    /**
     * The following function is used to check for SAP refernece ID for the
     * Invoice for Cancellation
     */
    public boolean checkSapRefIdForInvoiceCancelNo(Connection con, String invoiceCancelNo) {
        boolean status = false;
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = con.createStatement();
            String searchstatement = "select tbl_billingheader.saprefno from tbl_billcancelheader left outer join tbl_billingheader on tbl_billcancelheader.invoiceno = tbl_billingheader.invoiceno where  tbl_billcancelheader.invoicecancellationno = '" + invoiceCancelNo + "'";
            rs = pstmt.executeQuery(searchstatement);
            String sapRefID = null;
            if (rs.next()) {
                sapRefID = rs.getString("saprefno");
                if (Validations.isFieldNotEmpty(sapRefID)) {
                    status = true;
                }
            }
        } catch (SQLException ex) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return status;
    }

    /**
     * The following function is used to Set Rounf OFF Values
     */
    public SalesOrderBillingPOJO setRoundoffValuesForWebservice(Connection con, String invoiceno) {
        SalesOrderBillingPOJO SOBPOJOobj = null;
        double roundoff = 0;
        double netamount = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select roundoff from tbl_billingheader where invoiceno=? ");
            pstmt.setString(1, invoiceno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                roundoff = rs.getDouble("roundoff");
            }
            if (roundoff > 0) {
                PreparedStatement statment = con.prepareStatement("select lineitemno,materialcode,netamount from tbl_billinglineitem where invoiceno=? ");
                statment.setString(1, invoiceno);
                ResultSet result = statment.executeQuery();
                while (result.next()) {
                    netamount = result.getDouble("netamount");
                    if (netamount > 1) {
                        SOBPOJOobj.setLineitemno(result.getInt("lineitemno"));
                        SOBPOJOobj.setMatrialcode(result.getString("materialcode"));
                        SOBPOJOobj.setNetbillamount(result.getDouble("netamount"));
                        break;
                    }
                }
            }

        } catch (Exception e) {

        } finally {
            pstmt = null;
            rs = null;
        }
        return SOBPOJOobj;
    }

    /**
     * The following function is used to generate Invoice cancel Number
     */
    public String generateInvoiceCancelNo(Connection con) {
        String nextInvoiceCancelNo = null;
        Statement pstmt;
        ResultSet rs;
        try {
            String prevCreditNo = null, prevCreditLastNoPart = null;
            String firstFixedString = "I";
            String numLogicForStore = new SiteMasterDO().getSiteNumberLogicValue(con);
            if (numLogicForStore != null) {
                numLogicForStore = numLogicForStore.trim();
            }
            firstFixedString = firstFixedString + numLogicForStore;
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select invoicecancel from tbl_posdoclastnumbers");
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
            nextInvoiceCancelNo = new GenerateNextPosDocNumber().generatenumber(prevCreditNo, firstFixedString.length());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return nextInvoiceCancelNo;
    }

    /**
     * The following function is used to Update the Last Invoice Cancel No
     */
    public int updateLastInvoicecancelNo(Connection con, String lastDocNo) {
        String query = "update tbl_posdoclastnumbers set invoicecancel =? ";
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
            pstmt = null;
            query = null;
        }

    }

    /**
     * The following function is used to Get the Stock for the Material for NON
     * batch article
     */
    public int getQtyForBilling(String matrial, Connection con) throws SQLException {
        int qty = 0;
        String query = "select quantity from tbl_stockmaster where materialcode =? ";
        PreparedStatement pstmt;
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, matrial);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                qty = rs.getInt("quantity");
            }
        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        } finally {
            pstmt = null;
            query = null;
        }
        return qty;

    }

    /**
     * The following function is used to Update Stock for Non batch articles
     */
    public int updateStockForBilling(String matrial, Connection con, int qty) throws SQLException {

        int status = 0;
        String query = "update tbl_stockmaster set quantity=? where materialcode =? ";
        PreparedStatement pstmt;
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, qty);
            pstmt.setString(2, matrial);
            status = pstmt.executeUpdate();

        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        } finally {
            pstmt = null;
            query = null;
        }
        return status;

    }

    /**
     * The following function is used to Update Stock for Batch articles
     */
    public int updateStockForBillingwithbatch(String matrial, Connection con, int qty, String batchid) throws SQLException {

        int status = 0;
        PreparedStatement pstmt;
        String query = "update tbl_stockmaster_batch set quantity=? where materialcode =? and batch=?";
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, qty);
            pstmt.setString(2, matrial);
            pstmt.setString(3, batchid);
            status = pstmt.executeUpdate();

        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        } finally {
            pstmt = null;
            query = null;
        }
        return status;
    }

    /**
     * The following function is used to Get the Stock for the Material for NON
     * batch article
     */
    public int getQtyForBillingBatch(String matrial, Connection con, String batchid) throws SQLException {

        int qty = 0;
        String query = "select quantity from tbl_stockmaster_batch where materialcode =? and batch=?";
        PreparedStatement pstmt;
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, matrial);
            pstmt.setString(2, batchid);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                qty = rs.getInt("quantity");
            }
        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        } finally {
            pstmt = null;
            query = null;
        }
        return qty;
    }

    /**
     * The following function is used to Get the Batch Indicator for article
     */
    public String getBtchforarticle(String matrial, Connection con) throws SQLException {
        String batchindicator = null;
        String query = "select batchindicator from tbl_articlemaster where materialcode =? ";
        PreparedStatement pstmt;
        ResultSet rs;
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, matrial);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                batchindicator = rs.getString("batchindicator");
            }
        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        } finally {
            pstmt = null;
            rs = null;
        }
        return batchindicator;
    }

    /**
     * The following function is used to Get the Round OFF from Billing
     */
    public double getRoundoff(String invoiceno, Connection con) {
        double round = 0;
        String query = "select roundoff from tbl_billingheader where invoiceno =? ";
        PreparedStatement pstmt;
        ResultSet rs;
        try {

            pstmt = con.prepareStatement(query);
            pstmt.setString(1, invoiceno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                round = rs.getDouble("roundoff");
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return round;
    }

    /**
     * The following function is used to Get the Total of Advance Receipt
     */
    public double getTotalAdvanceReceipt(String qurrey, Connection conn) {
        double amount = 0;
        Statement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.createStatement();
            rs = pstmt.executeQuery(qurrey);
            while (rs.next()) {
                amount = amount + rs.getDouble("totalamount");
            }
        } catch (SQLException er) {
        } finally {
            pstmt = null;
            rs = null;
        }
        return amount;
    }

    /**
     * The following function is used to Get whether the Line ITem is a Customer
     * ITEM or Not
     */
    public boolean getCustomerItem(String saleorderno, Connection con, int lineitem) {
        boolean status = true;
        String query = "select customeritem from tbl_salesorderlineitems where saleorderno =? and lineitemno=?";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, saleorderno);
            pstmt.setInt(2, lineitem);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("customeritem") != null) {
                    if (!(rs.getString("customeritem").equals("X"))) {
                        status = false;
                    }
                } else {
                    status = false;
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return status;
    }

    /**
     * The following function is used to Get the Batches for the Article
     */
    public void getBatchForMaterialFromSaleorder(String materialCode, JComboBox combo, Connection con, String saleorderno, int lineitemno) {
        Statement stmt;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select batchid from tbl_salesorderlineitems where saleorderno='" + saleorderno + "' and lineitemno='" + lineitemno + "' and materialcode= '" + materialCode + "'");
            while (rs.next()) {

                String batch = rs.getString(1);
                combo.addItem(batch);
            }
        } catch (Exception e) {

        } finally {
            stmt = null;
            rs = null;
        }

    }

    /**
     * The following function is used to get the Sale order Vierifed Status
     */
    public boolean getSaleorderVerifiedBySatus(String saleorderNo, Connection con) {
        boolean status = true;
        String query = "select verifiedby from tbl_salesorderheader where saleorderno =? and orderstatus='OPEN' and createdby<>'POS'";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, saleorderNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("verifiedby") != null) {
                    if ((rs.getString("verifiedby").equals("")) || (rs.getString("verifiedby").equals(" "))) {
                        status = false;
                    }
                } else {
                    status = false;
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return status;
    }

    /**
     * The following function is used to get the Sale order Release Status
     */
    public boolean getSaleorderReleaseBySatus(String saleorderNo, Connection con) {
        boolean status = true;
        String query = "select releasestatus from tbl_salesorderheader where saleorderno =? and orderstatus='OPEN' and createdby<>'POS'";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, saleorderNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("releasestatus") != null) {
                    if (!(rs.getString("releasestatus").equals("RELEASE")) && (!(rs.getString("releasestatus").equals("FORCERELEASED")))) {
                        status = false;
                    }
                } else {
                    status = false;
                }
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return status;
    }

    /**
     * The following function is used to get whether the Sale order Status is
     * OPEN
     */
    public boolean getSaleorderSatus(String saleorderNo, Connection con) {
        boolean status = true;
        String query = "select saleorderno from tbl_salesorderheader where saleorderno =? and orderstatus='OPEN' and createdby<>'POS'";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, saleorderNo);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }

        return status;
    }

    /**
     * The following function is used to get Sale Type of the Sale Order
     */
    public String getSaleTypeFromSaleorder(String saleorderNo, Connection con) {
        String status = null;
        String query = "select saletype from tbl_salesorderheader where saleorderno =? and orderstatus='BILLED'";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, saleorderNo);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                status = rs.getString("saletype");
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return status;
    }

    /**
     * The following function is get Billing Header Data for a Invoice for Sales
     * order
     */
    public SalesOrderBillingPOJO getBillingHeaderByInvoiceNo(Connection con, String invoiceno, String storeCode, int fiscalYear) {
        SalesOrderBillingPOJO salesOrderBillingPOJO = null;
        String query = "select storecode,fiscalyear,refno from dbo.tbl_billingheader where invoiceno = ? and storecode = ? and fiscalyear = ?";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, invoiceno);
            pstmt.setString(2, storeCode);
            pstmt.setInt(3, fiscalYear);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                salesOrderBillingPOJO = new SalesOrderBillingPOJO();
                salesOrderBillingPOJO.setStorecode(rs.getString("storecode"));
                salesOrderBillingPOJO.setFiscalyear(rs.getInt("fiscalyear"));
                salesOrderBillingPOJO.setSalesorderno(rs.getString("refno"));
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return salesOrderBillingPOJO;
    }

    /**
     * The following function is get Billing Header Data for a Invoice Cancel
     */
    public SalesOrderBillingPOJO getBillingHeaderByInvoiceCancelNo(Connection con, String invoicecancelno, String storeCode, int fiscalyear) {
        SalesOrderBillingPOJO salesOrderBillingPOJO = null;
        String query = "select storecode,fiscalyear from dbo.tbl_billcancelheader where invoicecancellationno = ? and storecode = ? and fiscalyear = ?";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, invoicecancelno);
            pstmt.setString(2, storeCode);
            pstmt.setInt(3, fiscalyear);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                salesOrderBillingPOJO = new SalesOrderBillingPOJO();
                salesOrderBillingPOJO.setStorecode(rs.getString("storeccode"));
                salesOrderBillingPOJO.setFiscalyear(rs.getInt("fiscalyear"));
            }
        } catch (Exception E) {
            E.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
            query = null;
        }
        return salesOrderBillingPOJO;
    }

    /**
     * The following function is get Billing Header Data for a Invoice for
     * Service order
     */
    public SalesOrderBillingPOJO getHeaderForWebserviceServiceBilling(Connection conn, String invoiceno, String creditNoteNo) {
        SalesOrderBillingPOJO header = new SalesOrderBillingPOJO();
        double roundoff = 0;
        double netamount = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select s.orderstatus,s.serviceorderno,i.invoiceno,i.invoicedate,i.roundoff,i.createdby,i.createdtime,i.createddate,i.fiscalyear,i.storecode from tbl_serviceorderheader as s ,tbl_billingheader as i where s.serviceorderno=i.refno and i.invoiceno=?");
            pstmt.setString(1, invoiceno);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                header.setSalesorderno(rs.getString("serviceorderno"));
                header.setInvoiceno(rs.getString("invoiceno"));
                header.setInvoicedate(rs.getInt("invoicedate"));
                header.setRoundoff(rs.getDouble("roundoff"));
                header.setCreatedby(rs.getString("createdby"));
                header.setCreatedtime(rs.getString("createdtime"));
                header.setCreateddate(rs.getInt("createddate"));
                header.setFiscalyear(rs.getInt("fiscalyear"));
                header.setStorecode(rs.getString("storecode"));
                header.setOrderStatus(rs.getString("orderstatus"));
            }
            if (header.getRoundoff() > 0 || header.getRoundoff() < 0) {
                PreparedStatement statment = conn.prepareStatement("select lineitemno,materialcode,netamount from tbl_billinglineitem where invoiceno=? ");
                statment.setString(1, invoiceno);
                ResultSet result = statment.executeQuery();
                while (result.next()) {
                    netamount = result.getDouble("netamount");
                    if (netamount > 1) {
                        header.setLineitemno(result.getInt("lineitemno"));
                        header.setMatrialcode(result.getString("materialcode"));
                        header.setNetbillamount(result.getDouble("netamount"));
                        break;
                    }
                }
            }
            if (creditNoteNo != null) {
                PreparedStatement statment = conn.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? ");
                statment.setString(1, creditNoteNo);
                ResultSet resultCredit = statment.executeQuery();
                while (resultCredit.next()) {
                    header.setExcessamount(resultCredit.getDouble("amount"));
                    header.setExcessReftype(resultCredit.getString("reftype"));
                    header.setCreditNoteNo(creditNoteNo);
                    header.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                }
            }
        } catch (SQLException ee) {
            ee.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return header;
    }

    /**
     * The following function is to update SAP Reference ID for Billing
     */
    public int updateSapRefId(Connection con, String sapRefNo, String sapAccNo, String invoiceNo, String storeCode, int fiscalYear) {
        int result = 0;
        PreparedStatement stmtUpdate;
        try {
            stmtUpdate = con.prepareStatement("UPDATE tbl_billingheader SET saprefno = ?,accsaprefno=? where invoiceno=? and storecode = ? and fiscalyear = ?");
            stmtUpdate.setString(1, sapRefNo);
            stmtUpdate.setString(2, sapAccNo);
            stmtUpdate.setString(3, invoiceNo);
            stmtUpdate.setString(4, storeCode);
            stmtUpdate.setInt(5, fiscalYear);
            result = stmtUpdate.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            stmtUpdate = null;
        }
        return result;
    }

    /**
     * The following function is to update SAP Reference ID for Bill Cancel
     */
    public int updateSapRefNoInvoiceCancel(Connection con, String sapRefNo, String accNo, String invoiceCancelNo, String storeCode, int fiscalYear) {
        int result = 0;
        PreparedStatement stmtUpdate;
        try {
            stmtUpdate = con.prepareStatement("UPDATE tbl_billcancelheader SET saprefno = ?,accsaprefno=? where invoicecancellationno=? and storecode = ? and fiscalyear = ?");
            stmtUpdate.setString(1, sapRefNo);
            stmtUpdate.setString(2, accNo);
            stmtUpdate.setString(3, invoiceCancelNo);
            stmtUpdate.setString(4, storeCode);
            stmtUpdate.setInt(5, fiscalYear);
            result = stmtUpdate.executeUpdate();
        } catch (SQLException s) {
            s.printStackTrace();
        } finally {
            stmtUpdate = null;
        }
        return result;
    }

    //Code Added on May 20th 2010
    public int deleteLockEntry(String sonumber, Connection con) throws SQLException {
        int result_billing = 0;
        PreparedStatement statement = null;
        try {
            if (sonumber != null && sonumber.length() == 10) {
                statement = con.prepareStatement("delete from tbl_locktable where saleorderno='" + sonumber + "'");
            } else {
                statement = con.prepareStatement("delete from tbl_locktable");
            }

            result_billing = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        } finally {
            statement.close();
            statement = null;
        }
        return result_billing;
    }
    //End of Code Added on May 20th 2010

}

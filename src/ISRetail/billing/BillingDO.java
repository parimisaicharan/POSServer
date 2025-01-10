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
 * This class file is used as a Data Object between Direct billing Form and Database
 * This is used for transaction of Direct Billing data from and to the database
 * This class is used to save all database operations related to Directbilling
 * 
 * 
 */
package ISRetail.billing;

import ISRetail.article.ArticleDO;
import ISRetail.salesorder.SOLineItemPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class BillingDO {

    private Exception SQLException;
    private String sitecode;
    private int i;
    private String new_saleorderno_new;
    private int result;

    /**
     * To get the Invoice Header Details
     */
    public BillingHeaderPOJO searchInvoiceNo(Connection con, String salorderno_for_serarch, String creditNoteNo) {
        BillingHeaderPOJO SERCHSOB = new BillingHeaderPOJO();
        double netamount;
        PreparedStatement statement = null;

        try {
            //Code commented and added on 29-01-2010 to include the free fields  
            // statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            // added for ULP development in POS
            //statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus,i.freefield1,i.freefield2,i.freefield3,i.freefield4,i.freefield5 from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            //changed for field names change in billing header table
            //statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus,i.freefield1,i.freefield2,i.freefield3,i.freefield4,i.freefield5,i.vistarefvalno from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            //statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            // Code Added by BALA for  empid on 10.10.2017
//            statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno,i.empid from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,c.customercode ,c.firstname,c.lastname,c.telephoneno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.fiscalyear,i.createdby,i.createddate,i.createdtime,i.modifiedby,i.modifieddate,i.modifiedtime,i.roundoff,i.storecode,s.orderstatus,i.membershipcardno,i.vistarefgiftvocno,i.unifiedloyno,i.existingloyno,i.existingchannel,i.vistarefvalno,i.empid,i.refname,i.refinvoiceno,i.refmobileno,i.insuranceId,s.secondsale_refno,s.customername,s.mobileno,s.gstn_no from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno and s.createdby='POS' order by i.invoiceno");
            // Code Ended by BALA for  empid on 10.10.2017
            statement.setString(1, salorderno_for_serarch);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SERCHSOB.setSaleorderno(rs.getString(1));
                SERCHSOB.setCustomercode(rs.getString(2));
                SERCHSOB.setDatasheetno(rs.getString(3));
                SERCHSOB.setSaleorderdate(rs.getInt(4));
                SERCHSOB.setSaletype(rs.getString(5));
                SERCHSOB.setCreditsalereference(rs.getString(6));
                SERCHSOB.setCustomerfirstname(rs.getString(8));
                SERCHSOB.setCustomerlastname(rs.getString(9));
                SERCHSOB.setTelephoneno(rs.getString(10));
                SERCHSOB.setInvoiceno(rs.getString(11));
                SERCHSOB.setInvoicedate(rs.getInt(12));
                SERCHSOB.setBilledfrom(rs.getString(13));
                SERCHSOB.setCancelstatus(rs.getString(14));
                SERCHSOB.setFiscalyear(rs.getInt(15));
                SERCHSOB.setCreatedby(rs.getString(16));
                SERCHSOB.setCreateddate(rs.getInt(17));
                SERCHSOB.setCreatedtime(rs.getString(18));
                SERCHSOB.setModifiedby(rs.getString(19));
                SERCHSOB.setModifieddate(rs.getInt(20));
                SERCHSOB.setModifiedtime(rs.getString(21));
                SERCHSOB.setRoundoff(rs.getDouble(22));
                SERCHSOB.setStorecode(rs.getString(23));
                SERCHSOB.setOrderStatus(rs.getString(24));
                //Code added on 29-01-2010 for capturing the free fields
                SERCHSOB.setFreefield1(rs.getString(25));
                SERCHSOB.setFreefield2(rs.getString(26));
                SERCHSOB.setFreefield3(rs.getString(27));
                // changed for ULP development in POS
                //SERCHSOB.setFreefield4(rs.getInt(28));
                //SERCHSOB.setFreefield5(rs.getDouble(29));
                SERCHSOB.setFreefield4(rs.getString(28));
                SERCHSOB.setFreefield5(rs.getString(29));
                SERCHSOB.setVistaRefValNo(rs.getString(30));
                // Code Added by BALA for  empid on 10.10.2017
                SERCHSOB.setEmpid(rs.getString(31));
                // Code Added by BALA for  empid on 10.10.2017
                //Added by Balachander on 21.6.2018 to capture referral mobile number and name
                SERCHSOB.setRefname(rs.getString(32));
                SERCHSOB.setRefinvoiceno(rs.getString(33));
                SERCHSOB.setRefmobileno(rs.getString(34));
                SERCHSOB.setZopperInsuraceId(rs.getString(35));
//Ended by Balachander on 21.6.2018 to capture referral mobile number and name
                SERCHSOB.setReferencePromoOrder(rs.getString(36));
                SERCHSOB.setcustname(rs.getString(37));//added by surekha k
                SERCHSOB.setMobileno(rs.getString(38));
                SERCHSOB.setGst_no(rs.getString(39));
                if (SERCHSOB.getRoundoff() > 0 || SERCHSOB.getRoundoff() < 0) {
                    PreparedStatement statment = con.prepareStatement("select lineitemno,materialcode,netamount from tbl_billinglineitem where invoiceno=? ");
                    statment.setString(1, salorderno_for_serarch);
                    ResultSet result = statment.executeQuery();
                    while (result.next()) {
                        netamount = result.getDouble("netamount");
                        if (netamount > 1) {
                            SERCHSOB.setLineitemno(result.getInt("lineitemno"));
                            SERCHSOB.setMatrialcode(result.getString("materialcode"));
                            SERCHSOB.setAmount(result.getDouble("netamount"));
                            break;
                        }
                    }
                }
                if (creditNoteNo != null) {
                    PreparedStatement statment = con.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where creditnoteno=? and category='RF'");
                    statment.setString(1, creditNoteNo);
                    ResultSet resultCredit = statment.executeQuery();
                    while (resultCredit.next()) {
                        SERCHSOB.setExcessamount(resultCredit.getDouble("amount"));
                        SERCHSOB.setExcessReftype(resultCredit.getString("reftype"));
                        SERCHSOB.setCreditNoteno(creditNoteNo);
                        SERCHSOB.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                    }
                }
                //Code added on July 7th 2010 for capturing NR credit note details

                PreparedStatement statment = con.prepareStatement("select creditnoteno,amount,refno,reftype,expirydate from tbl_creditnote where refno=? and category='NR'");
                statment.setString(1, salorderno_for_serarch);
                ResultSet resultCredit = statment.executeQuery();
                while (resultCredit.next()) {
                    SERCHSOB.setNrcreditnoteno(resultCredit.getString("creditnoteno"));
                    SERCHSOB.setNrexcessamt(resultCredit.getDouble("amount"));
                    SERCHSOB.setExcessReftype(resultCredit.getString("reftype"));
                    SERCHSOB.setCreditnoteexpirydate(resultCredit.getInt("expirydate"));
                }

                //End of Code added on July 7th 2010 for capturing NR credit note details  
                return SERCHSOB;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            statement = null;
        }

    }

    /**
     * To get the Invoice Header Details which is cancelled
     */
    public BillingHeaderPOJO searchInvoiceNoForCancel(Connection con, String salorderno_for_serarch) {
        BillingHeaderPOJO SERCHSOB = new BillingHeaderPOJO();
        PreparedStatement statement;
        try {

            statement = con.prepareStatement("select s.saleorderno,s.customercode,s.datasheetno,s.saleorderdate,s.saletype,s.creditsalereference,s.amount,c.customercode ,c.firstname,c.lastname,c.mobileno,i.invoiceno,i.invoicedate,i.billedfrom,i.cancelstatus,i.accsaprefno,i.saprefno from tbl_salesorderheader as  s ,tbl_customermastermain as c ,tbl_billingheader as i where i.invoiceno =? and s.customercode=c.customercode and s.saleorderno=i.refno order by i.invoiceno");
            statement.setString(1, salorderno_for_serarch);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SERCHSOB.setSaleorderno(rs.getString(1));
                SERCHSOB.setCustomercode(rs.getString(2));
                SERCHSOB.setDatasheetno(rs.getString(3));
                SERCHSOB.setSaleorderdate(rs.getInt(4));
                SERCHSOB.setSaletype(rs.getString(5));
                SERCHSOB.setCreditsalereference(rs.getString(6));
                SERCHSOB.setAmount(rs.getDouble(7));
                SERCHSOB.setCustomerfirstname(rs.getString(9));
                SERCHSOB.setCustomerlastname(rs.getString(10));
                SERCHSOB.setTelephoneno(rs.getString(11));
                SERCHSOB.setInvoiceno(rs.getString(12));
                SERCHSOB.setInvoicedate(rs.getInt(13));
                SERCHSOB.setBilledfrom(rs.getString(14));
                SERCHSOB.setCancelstatus(rs.getString(15));
                SERCHSOB.setAccsaprefno(rs.getString(16));
                SERCHSOB.setSaprefno(rs.getString(17));
                return SERCHSOB;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            statement = null;
        }
    }

    /**
     * To Find an article is aGV Article
     */
    public boolean isGvArticle(Connection con, SOLineItemPOJO sOLineItemPOJO) {
        boolean isGv = false;
        try {
            ArrayList<String> merchCats = new ArticleDO().getMerchCategoriesForGV(con);
            if (Validations.presentInArrayList(merchCats, sOLineItemPOJO.getMdseCategory())) {
                isGv = true;
            }
        } catch (Exception e) {

        }
        return isGv;
    }

    /**
     * To get the Merch Category by material Code
     */
    public static String getMerchCatbymaterialcode(Connection conn, String materialcode) {
        Statement st;
        ResultSet rs;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select merchcategory from tbl_articlemaster where materialcode = '" + materialcode + "'");
            if (rs.next()) {
                return rs.getString("merchcategory");
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            st = null;
            rs = null;
        }
    }

    public String getInvoiceNoToSent(Connection conn, String invoicestatus) {
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select invoiceno from tbl_invoiceprint_status where send_status=?");
            pstmt.setString(1, invoicestatus);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("invoiceno");
            } else {
                return "";
            }
        } catch (Exception e) {
            return "";
        } finally {
            try {
                pstmt.close();
            } catch (SQLException e) {
            }
            pstmt = null;
            rs = null;
        }
    }

}

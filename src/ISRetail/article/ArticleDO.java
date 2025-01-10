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
 * Class to Handle all the Article Tranasctions with the Database
 * 
 * 
 * 
 */
package ISRetail.article;

import ISRetail.Helpers.ConvertDate;

import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComboBox;

public class ArticleDO {

    Statement statement = null;
    PreparedStatement pstmt = null;

    /**
     * Save Article Master in the Database
     */
    public int saveArticle(Connection con, ArticleMasterPOJO scpojo) throws SQLException {
        int result = 0;

//            PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_articlemaster values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_articlemaster values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//Added by Bala 17.06.2017 for GST
        pstmt1.setString(1, scpojo.getStorecode());
        pstmt1.setString(2, scpojo.getMaterialcode());
        pstmt1.setString(3, scpojo.getDivision());
        pstmt1.setString(4, scpojo.getMaterialdescription());
        pstmt1.setString(5, scpojo.getArticletype());
        pstmt1.setString(6, scpojo.getMerchcategory());
        pstmt1.setString(7, scpojo.getUom());
        pstmt1.setString(8, scpojo.getBatchindicator());
        pstmt1.setInt(9, scpojo.getPlanneddeltime());
        pstmt1.setString(10, scpojo.getMinshelflife());
        pstmt1.setString(11, scpojo.getWarrantyperiod());
        pstmt1.setString(12, scpojo.getDeletionind());
        pstmt1.setString(13, scpojo.getUpdatestatus());
        pstmt1.setString(14, scpojo.getCreatedby());
        pstmt1.setInt(15, scpojo.getCreateddate());
        pstmt1.setString(16, scpojo.getCreatedtime());
        pstmt1.setString(17, scpojo.getModifiedby());
        pstmt1.setInt(18, scpojo.getModifieddate());
        pstmt1.setString(19, scpojo.getModifiedtime());
        pstmt1.setString(20, scpojo.getCategory());
        pstmt1.setInt(21, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(22, ConvertDate.getCurrentTimeToString());
        pstmt1.setString(23, scpojo.getHsn_sac_code()); //Added by Bala 17.06.2017 for GST
        result = pstmt1.executeUpdate();

        return result;
    }

    /**
     * Update Article Master in the Database
     */
    public int updateArticle(Connection con, ArticleMasterPOJO scpojo) throws SQLException {
        int result = 0;
        //Added by Bala 17.06.2017 for GST
        if (Validations.isFieldNotEmpty(scpojo.getHsn_sac_code())) {
            PreparedStatement pstmt1;
            pstmt1 = con.prepareStatement("update tbl_articlemaster set division = ?,materialdescription=?,articletype=?,merchcategory=?,uom=?,batchindicator=?,planneddeltime=?,minshelflife=?,warrantyperiod=?,deletionind=?,updatestatus=?,createdby=?,createddate=?,createdtime=?,modifiedby=?,modifieddate=?,modifiedtime=?,category =?,data_syncdate=?,data_synctime=?,hsn_sac_code=? where storecode='" + scpojo.getStorecode() + "' and materialcode = '" + scpojo.getMaterialcode() + "'");
            pstmt1.setString(1, scpojo.getDivision());
            pstmt1.setString(2, scpojo.getMaterialdescription());
            pstmt1.setString(3, scpojo.getArticletype());
            pstmt1.setString(4, scpojo.getMerchcategory());
            pstmt1.setString(5, scpojo.getUom());
            pstmt1.setString(6, scpojo.getBatchindicator());
            pstmt1.setInt(7, scpojo.getPlanneddeltime());
            pstmt1.setString(8, scpojo.getMinshelflife());
            pstmt1.setString(9, scpojo.getWarrantyperiod());
            pstmt1.setString(10, scpojo.getDeletionind());
            pstmt1.setString(11, scpojo.getUpdatestatus());
            pstmt1.setString(12, scpojo.getCreatedby());
            pstmt1.setInt(13, scpojo.getCreateddate());
            pstmt1.setString(14, scpojo.getCreatedtime());
            pstmt1.setString(15, scpojo.getModifiedby());
            pstmt1.setInt(16, scpojo.getModifieddate());
            pstmt1.setString(17, scpojo.getModifiedtime());
            pstmt1.setString(18, scpojo.getCategory());
            pstmt1.setInt(19, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(20, ConvertDate.getCurrentTimeToString());
            pstmt1.setString(21, scpojo.getHsn_sac_code()); // Added by BALA on 17.6.2017 for GST
            result = pstmt1.executeUpdate();

            return result;
        } else {
            PreparedStatement pstmt1;
            pstmt1 = con.prepareStatement("update tbl_articlemaster set division = ?,materialdescription=?,articletype=?,merchcategory=?,uom=?,batchindicator=?,planneddeltime=?,minshelflife=?,warrantyperiod=?,deletionind=?,updatestatus=?,createdby=?,createddate=?,createdtime=?,modifiedby=?,modifieddate=?,modifiedtime=?,category =?,data_syncdate=?,data_synctime=? where storecode='" + scpojo.getStorecode() + "' and materialcode = '" + scpojo.getMaterialcode() + "'");
            pstmt1.setString(1, scpojo.getDivision());
            pstmt1.setString(2, scpojo.getMaterialdescription());
            pstmt1.setString(3, scpojo.getArticletype());
            pstmt1.setString(4, scpojo.getMerchcategory());
            pstmt1.setString(5, scpojo.getUom());
            pstmt1.setString(6, scpojo.getBatchindicator());
            pstmt1.setInt(7, scpojo.getPlanneddeltime());
            pstmt1.setString(8, scpojo.getMinshelflife());
            pstmt1.setString(9, scpojo.getWarrantyperiod());
            pstmt1.setString(10, scpojo.getDeletionind());
            pstmt1.setString(11, scpojo.getUpdatestatus());
            pstmt1.setString(12, scpojo.getCreatedby());
            pstmt1.setInt(13, scpojo.getCreateddate());
            pstmt1.setString(14, scpojo.getCreatedtime());
            pstmt1.setString(15, scpojo.getModifiedby());
            pstmt1.setInt(16, scpojo.getModifieddate());
            pstmt1.setString(17, scpojo.getModifiedtime());
            pstmt1.setString(18, scpojo.getCategory());
            pstmt1.setInt(19, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(20, ConvertDate.getCurrentTimeToString());
            result = pstmt1.executeUpdate();

            return result;
        }
    }

    /**
     * Save Article Characteristice in the Database
     */
    public void saveArticleCharacterstic(Connection con, ArticleCharacteristicsPOJO scpojo) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_articlemaster_characteristics values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, scpojo.getStorecode());
        pstmt1.setString(2, scpojo.getMaterialcode());
        pstmt1.setString(3, scpojo.getCharacteristics());
        pstmt1.setString(4, scpojo.getValueno());
        pstmt1.setString(5, scpojo.getValue());
        pstmt1.setString(6, scpojo.getUpdatestatus());
        pstmt1.setString(7, scpojo.getCreatedby());
        pstmt1.setInt(8, scpojo.getCreateddate());
        pstmt1.setString(9, scpojo.getCreatedtime());
        pstmt1.setString(10, scpojo.getModifiedby());
        pstmt1.setInt(11, scpojo.getModifieddate());
        pstmt1.setString(12, scpojo.getModifiedtime());
        pstmt1.setInt(13, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(14, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();

    }

    /**
     * Update Article Characteristice in the Database
     */
    public int updateArticleCharacterstic(Connection con, ArticleCharacteristicsPOJO scpojo) throws SQLException {
        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("update tbl_articlemaster_characteristics set value=?,updatestatus=?,createdby=?,createddate=?,createdtime=?,modifiedby=?,modifieddate=?,modifiedtime=?,data_syncdate=?,data_synctime=? where storecode='" + scpojo.getStorecode() + "' and materialcode = '" + scpojo.getMaterialcode() + "' and characteristics = '" + scpojo.getCharacteristics() + "' and valueno = '" + scpojo.getValueno() + "'");
        pstmt1.setString(1, scpojo.getValue());
        pstmt1.setString(2, scpojo.getUpdatestatus());
        pstmt1.setString(3, scpojo.getCreatedby());
        pstmt1.setInt(4, scpojo.getCreateddate());
        pstmt1.setString(5, scpojo.getCreatedtime());
        pstmt1.setString(6, scpojo.getModifiedby());
        pstmt1.setInt(7, scpojo.getModifieddate());
        pstmt1.setString(8, scpojo.getModifiedtime());
        pstmt1.setInt(9, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(10, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        return result;
    }

    /**
     * Save Article UCP
     */
    public void saveArticleUCP(Connection con, ArticleUCPPOJO articleUCPPOJO, boolean autoDownload) throws SQLException {
        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_ucp values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, articleUCPPOJO.getStorecode());
        pstmt1.setString(2, articleUCPPOJO.getCondtype());
        pstmt1.setString(3, articleUCPPOJO.getMaterialcode());
        pstmt1.setString(4, articleUCPPOJO.getCondrecno());
        pstmt1.setDouble(5, articleUCPPOJO.getUcpamount());
        pstmt1.setString(6, articleUCPPOJO.getCurrency());
        pstmt1.setInt(7, articleUCPPOJO.getFromdate());
        pstmt1.setInt(8, articleUCPPOJO.getTodate());
        pstmt1.setString(9, articleUCPPOJO.getUpdatestatus());
        pstmt1.setString(10, articleUCPPOJO.getDeletionind());
        pstmt1.setString(11, articleUCPPOJO.getPriority());
        if (autoDownload) {
            pstmt1.setInt(12, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(13, ConvertDate.getCurrentTimeToString());
        } else {
            pstmt1.setInt(12, 0);
            pstmt1.setString(13, "");
        }
        //Added by Dileep - 25.07.2014
        if (!autoDownload) {
            pstmt1.setInt(14, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(15, ConvertDate.getCurrentTimeToString());
        } else {
            pstmt1.setInt(14, 0);
            pstmt1.setString(15, "");
        }
        pstmt1.executeUpdate();
        pstmt1 = null;
    }

    /**
     * Update Article UCP
     */
    public int updateArticleUCP(Connection con, ArticleUCPPOJO articleUCPPOJO, boolean autoDownload) throws SQLException {
        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("update tbl_ucp set ucpamount = ?,currency=?,fromdate=?,todate=?,updatestatus=?,deletionind=?,priority=?,data_syncdate=?,data_synctime=?,datamanual_syncdate=?,datamanual_synctime=? where storecode='" + articleUCPPOJO.getStorecode() + "' and condtype = '" + articleUCPPOJO.getCondtype() + "' and materialcode = '" + articleUCPPOJO.getMaterialcode() + "' and condrecno = '" + articleUCPPOJO.getCondrecno() + "'");
        pstmt1.setDouble(1, articleUCPPOJO.getUcpamount());
        pstmt1.setString(2, articleUCPPOJO.getCurrency());
        pstmt1.setInt(3, articleUCPPOJO.getFromdate());
        pstmt1.setInt(4, articleUCPPOJO.getTodate());
        pstmt1.setString(5, articleUCPPOJO.getUpdatestatus());
        pstmt1.setString(6, articleUCPPOJO.getDeletionind());
        pstmt1.setString(7, articleUCPPOJO.getPriority());
        if (autoDownload) {
            System.out.println("Automatic downlaod For UCP");
            pstmt1.setInt(8, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(9, ConvertDate.getCurrentTimeToString());
        } else {
            pstmt1.setInt(8, 0);
            pstmt1.setString(9, "");
        }
        //Added by Dileep - 25.07.2014
        if (!autoDownload) {
            pstmt1.setInt(10, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(11, ConvertDate.getCurrentTimeToString());
        } else {
            pstmt1.setInt(10, 0);
            pstmt1.setString(11, "");
        }
        result = pstmt1.executeUpdate();
        pstmt1 = null;
        return result;
    }

    /**
     * Save Article Discount
     */
    public void saveArticleDiscount(Connection con, ArticleDiscountPOJO articleDiscountPOJO) throws SQLException {

        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_discounts values (?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, articleDiscountPOJO.getStorecode());
        pstmt1.setString(2, articleDiscountPOJO.getCondtype());
        pstmt1.setString(3, articleDiscountPOJO.getMerchcat());
        pstmt1.setString(4, articleDiscountPOJO.getCondrecno());
        pstmt1.setDouble(5, articleDiscountPOJO.getDiscount());
        pstmt1.setString(6, articleDiscountPOJO.getCalculationtype());
        pstmt1.setInt(7, articleDiscountPOJO.getFromdate());
        pstmt1.setInt(8, articleDiscountPOJO.getTodate());
        pstmt1.setString(9, articleDiscountPOJO.getUpdatestatus());
        pstmt1.setString(10, articleDiscountPOJO.getDeletionind());
        pstmt1.setInt(11, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(12, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article Discount
     */
    public int updateArticleDiscount(Connection con, ArticleDiscountPOJO articleDiscountPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("update tbl_discounts set discount=?,calculationtype=?,fromdate=?,todate=?,updatestatus=?,deletionind=?,data_syncdate=?,data_synctime=? where storecode = '" + articleDiscountPOJO.getStorecode() + "' and condtype = '" + articleDiscountPOJO.getCondtype() + "' and merchcat='" + articleDiscountPOJO.getMerchcat() + "' and condrecno = '" + articleDiscountPOJO.getCondrecno() + "'");
        pstmt1.setDouble(1, articleDiscountPOJO.getDiscount());
        pstmt1.setString(2, articleDiscountPOJO.getCalculationtype());
        pstmt1.setInt(3, articleDiscountPOJO.getFromdate());
        pstmt1.setInt(4, articleDiscountPOJO.getTodate());
        pstmt1.setString(5, articleDiscountPOJO.getUpdatestatus());
        pstmt1.setString(6, articleDiscountPOJO.getDeletionind());
        pstmt1.setInt(7, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(8, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;
        return result;
    }

    /**
     * Save Article Delay Discount
     */
    public void saveArticleDelayDiscount(Connection con, ArticleDiscountPOJO articleDiscountPOJO) throws SQLException {

        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_delaydeliverydiscount values (?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, articleDiscountPOJO.getCondtype());
        pstmt1.setString(2, articleDiscountPOJO.getCompCode());
        pstmt1.setString(3, articleDiscountPOJO.getSaleOrg());
        pstmt1.setString(4, articleDiscountPOJO.getCondrecno());
        pstmt1.setDouble(5, articleDiscountPOJO.getDiscount());
        pstmt1.setString(6, articleDiscountPOJO.getCalculationtype());
        pstmt1.setInt(7, articleDiscountPOJO.getFromdate());
        pstmt1.setInt(8, articleDiscountPOJO.getTodate());
        pstmt1.setString(9, articleDiscountPOJO.getDeletionind());
        pstmt1.setInt(10, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(11, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;
    }

    /**
     * Update Article Delay Discount
     */
    public int updateArticleDelayDiscount(Connection con, ArticleDiscountPOJO articleDiscountPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("update tbl_delaydeliverydiscount set amount=?,calculationtype=?,fromdate=?,todate=?,deletionind=?,data_syncdate=?,data_synctime=? where condtype = '" + articleDiscountPOJO.getCondtype() + "' and companycode = '" + articleDiscountPOJO.getCompCode() + "' and salesorg='" + articleDiscountPOJO.getSaleOrg() + "' and condrecno = '" + articleDiscountPOJO.getCondrecno() + "'");
        pstmt1.setDouble(1, articleDiscountPOJO.getDiscount());
        pstmt1.setString(2, articleDiscountPOJO.getCalculationtype());
        pstmt1.setInt(3, articleDiscountPOJO.getFromdate());
        pstmt1.setInt(4, articleDiscountPOJO.getTodate());
        pstmt1.setString(5, articleDiscountPOJO.getDeletionind());
        pstmt1.setInt(6, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(7, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;
        return result;
    }

    /**
     * Save Article Other Charges
     */
    public void saveArticleOtherCharges(Connection con, ArticleOtherChargesPOJO articleOtherChargesPOJO) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_othercharges values (?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, articleOtherChargesPOJO.getStorecode());
        pstmt1.setString(2, articleOtherChargesPOJO.getCondtype());
        pstmt1.setString(3, articleOtherChargesPOJO.getMerchcat());
        pstmt1.setString(4, articleOtherChargesPOJO.getCondrecno());
        pstmt1.setDouble(5, articleOtherChargesPOJO.getOtherChargeValue());
        pstmt1.setString(6, articleOtherChargesPOJO.getCalculationtype());
        pstmt1.setInt(7, articleOtherChargesPOJO.getFromdate());
        pstmt1.setInt(8, articleOtherChargesPOJO.getTodate());
        pstmt1.setString(9, articleOtherChargesPOJO.getUpdatestatus());
        pstmt1.setString(10, articleOtherChargesPOJO.getDeletionind());
        pstmt1.setInt(11, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(12, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article Other Charges
     */
    public int updateArticleOtherCharges(Connection con, ArticleOtherChargesPOJO articleOtherChargesPOJO) throws SQLException {
        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("update tbl_othercharges set amount=?,calculationtype=?,fromdate=?,todate=?,updatestatus=?,deletionind=?,data_syncdate=?,data_synctime=? where storecode = '" + articleOtherChargesPOJO.getStorecode() + "' and condtype = '" + articleOtherChargesPOJO.getCondtype() + "' and merchcat='" + articleOtherChargesPOJO.getMerchcat() + "' and condrecno = '" + articleOtherChargesPOJO.getCondrecno() + "'");
        pstmt1.setDouble(1, articleOtherChargesPOJO.getOtherChargeValue());
        pstmt1.setString(2, articleOtherChargesPOJO.getCalculationtype());
        pstmt1.setInt(3, articleOtherChargesPOJO.getFromdate());
        pstmt1.setInt(4, articleOtherChargesPOJO.getTodate());
        pstmt1.setString(5, articleOtherChargesPOJO.getUpdatestatus());
        pstmt1.setString(6, articleOtherChargesPOJO.getDeletionind());
        pstmt1.setInt(7, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(8, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;
        return result;
    }

    /**
     * Save Article TAX
     */
    public void saveArticleTax(Connection con, ArticleTaxPOJO articleTaxPOJO) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_gst_taxdetails values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, articleTaxPOJO.getStorecode());
        pstmt1.setString(2, articleTaxPOJO.getCondtype());
        pstmt1.setString(3, articleTaxPOJO.getState());
        // pstmt1.setString(4, articleTaxPOJO.getMerchcat());
        pstmt1.setString(4, articleTaxPOJO.getHsn_sac_code()); // Added by Bala on 19.6.2017 for GST
        pstmt1.setString(5, articleTaxPOJO.getCondrecno());
        pstmt1.setDouble(6, articleTaxPOJO.getTax());
        pstmt1.setString(7, articleTaxPOJO.getCalculationtype());
        pstmt1.setInt(8, articleTaxPOJO.getFromdate());
        pstmt1.setInt(9, articleTaxPOJO.getTodate());
        pstmt1.setString(10, articleTaxPOJO.getUpdatestatus());
        pstmt1.setString(11, articleTaxPOJO.getDeletionind());
        pstmt1.setInt(12, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(13, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article TAX
     */
    public int updateArticleTax(Connection con, ArticleTaxPOJO articleTaxPOJO) throws SQLException {
        int result = 0;

//            PreparedStatement pstmt1 = con.prepareStatement("update tbl_taxdetails set tax=?,calculationtype=?,fromdate=?,todate=?,updatestatus=?,deletionind=?,data_syncdate=?,data_synctime=? where storecode='" + articleTaxPOJO.getStorecode() + "' and condtype = '" + articleTaxPOJO.getCondtype() + "' and state = '" + articleTaxPOJO.getState() + "' and merchcat = '" + articleTaxPOJO.getMerchcat() + "' and condrecno = '" + articleTaxPOJO.getCondrecno() + "'");
        PreparedStatement pstmt1 = con.prepareStatement("update tbl_gst_taxdetails set tax=?,calculationtype=?,fromdate=?,todate=?,updatestatus=?,deletionind=?,data_syncdate=?,data_synctime=? where storecode='" + articleTaxPOJO.getStorecode() + "' and condtype = '" + articleTaxPOJO.getCondtype() + "' and state = '" + articleTaxPOJO.getState() + "' and hsn_sac_code= '" + articleTaxPOJO.getHsn_sac_code() + "' and condrecno = '" + articleTaxPOJO.getCondrecno() + "'");
        pstmt1.setString(1, articleTaxPOJO.getStorecode());
        pstmt1.setDouble(1, articleTaxPOJO.getTax());
        pstmt1.setString(2, articleTaxPOJO.getCalculationtype());
        pstmt1.setInt(3, articleTaxPOJO.getFromdate());
        pstmt1.setInt(4, articleTaxPOJO.getTodate());
        pstmt1.setString(5, articleTaxPOJO.getUpdatestatus());
        pstmt1.setString(6, articleTaxPOJO.getDeletionind());
        pstmt1.setInt(7, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(8, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;

        return result;
    }

    /**
     * Save Article Promotion Header
     */
    public void saveArticlePromotionHeader(Connection con, PromotionHeaderPOJO promotionHeaderPOJO) throws SQLException {
        //Code added on 28th Jan 2010 -added one more field applicable_to one more input value in insert query added 
        int result = 0;
        try {
            PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_promotion_header values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt1.setString(1, promotionHeaderPOJO.getStorecode());
            pstmt1.setString(2, promotionHeaderPOJO.getCondtypeid());
            pstmt1.setString(3, promotionHeaderPOJO.getPromotionid());
            pstmt1.setString(4, promotionHeaderPOJO.getDescription());
            pstmt1.setInt(5, promotionHeaderPOJO.getFromdate());
            pstmt1.setInt(6, promotionHeaderPOJO.getTodate());
            pstmt1.setInt(7, promotionHeaderPOJO.getOpttype());
            pstmt1.setString(8, promotionHeaderPOJO.getSun());
            pstmt1.setString(9, promotionHeaderPOJO.getMon());
            pstmt1.setString(10, promotionHeaderPOJO.getTue());
            pstmt1.setString(11, promotionHeaderPOJO.getWed());
            pstmt1.setString(12, promotionHeaderPOJO.getThu());
            pstmt1.setString(13, promotionHeaderPOJO.getFri());
            pstmt1.setString(14, promotionHeaderPOJO.getSat());
            pstmt1.setString(15, promotionHeaderPOJO.getActive());
            pstmt1.setString(16, promotionHeaderPOJO.getIsComboOffer());
            pstmt1.setDouble(17, promotionHeaderPOJO.getPromMaxValue());
            pstmt1.setInt(18, promotionHeaderPOJO.getCreateddate());
            pstmt1.setString(19, promotionHeaderPOJO.getCreatedtime());
            pstmt1.setString(20, promotionHeaderPOJO.getCreateduser());
            pstmt1.setInt(21, promotionHeaderPOJO.getModifieddate());
            pstmt1.setString(22, promotionHeaderPOJO.getModifiedtime());
            pstmt1.setString(23, promotionHeaderPOJO.getModifieduser());
            pstmt1.setString(24, promotionHeaderPOJO.getUpdatestatus());
            pstmt1.setInt(25, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(26, ConvertDate.getCurrentTimeToString());
            //Code added on Jan 28th 2010 for applicable_to fld in promotion header
            //pstmt.setString(27,promotionHeaderPOJO.getApplicable_to());

            //Code corrected on Jun 07th 2011 for applicable_to fld in promotion header
            pstmt1.setString(27, promotionHeaderPOJO.getApplicable_to());
            //Code Added By Mr. Thangaraj on 29.02.2016 for Promotion Approval
            pstmt1.setString(28, promotionHeaderPOJO.getApproved());
            pstmt1.setString(29, promotionHeaderPOJO.getUsage_limit());
            //End of Code Added By Mr. Thangaraj on 29.02.2016 for Promotion Approval
            //Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
            pstmt1.setString(30, promotionHeaderPOJO.getUnique_promo_ind());
            pstmt1.setString(31, promotionHeaderPOJO.getSalereturn_ind());
            //Code end By Mr. Thangaraj on 25.05.2017 for unique Promotion
            pstmt1.setDouble(32, promotionHeaderPOJO.getMaxInvoiceVal());
            pstmt1.setString(33, promotionHeaderPOJO.getCustomercode());//Added by Balachander V on 18.12.2018 to show promotion for particular customer only
            pstmt1.setString(34, promotionHeaderPOJO.getRestrictLineItem());//Added by Balachander V on 10.10.2019 to validate promo applied line item count
            pstmt1.setString(35, promotionHeaderPOJO.getCombineArticles());//Added by Balachander V on 10.10.2019 to validate combile articles for the applied promo
            pstmt1.setString(36, promotionHeaderPOJO.getEligibleNoOfDays());//Added by Balachander V on 10.10.2019 to consider previous order values
            pstmt1.setString(37, promotionHeaderPOJO.getCustomerItemAllowed());//Added by Balachander V on 14.01.2020 to validate customer item allowed in current order
            pstmt1.setString(38, promotionHeaderPOJO.getUcpValueCheck());//Added by Balachander V on 14.01.2020 to validate UCP based on Frame or lens or contact lens 
            pstmt1.setDouble(39, promotionHeaderPOJO.getPromMaxCap());//ADDED BY CHARAN FOR PROMO MAX CAP VERSION 101
            pstmt1.executeUpdate();

            try {
                if (pstmt1 != null) {
                    pstmt1.close();
                }
                pstmt1 = null;
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Update Article Promotion Header
     */
    public int updateArticlePromotionHeader(Connection con, PromotionHeaderPOJO promotionHeaderPOJO) throws SQLException {
        int result = 0;
        try {
            //PreparedStatement pstmt1 = con.prepareStatement("update  tbl_promotion_header set description=?,fromdate=?,todate=?,opttype=?,sun=?,mon=?,tue=?,wed=?,thu=?,fri=?,sat=?,active=?,createddate=?,createdtime=?,createduser=?,modifieddate=?,modifiedtime=?,modifieduser=?,updatestatus=?,isComboOffer = ?,maxPromValue = ?,data_syncdate=?,data_synctime=?,applicable_to=? where storecode='" + promotionHeaderPOJO.getStorecode() + "' and condtypeid = '" + promotionHeaderPOJO.getCondtypeid() + "' and promotionid = '" + promotionHeaderPOJO.getPromotionid() + "'");
            //Edited By Mr. Thangaraj on 25.05.2017 for unique Promotion 
            // PreparedStatement pstmt1 = con.prepareStatement("update  tbl_promotion_header set description=?,fromdate=?,todate=?,opttype=?,sun=?,mon=?,tue=?,wed=?,thu=?,fri=?,sat=?,active=?,createddate=?,createdtime=?,createduser=?,modifieddate=?,modifiedtime=?,modifieduser=?,updatestatus=?,isComboOffer = ?,maxPromValue = ?,data_syncdate=?,data_synctime=?,applicable_to=?,approved=?,usage_limit=?,uniquepromoind=?,salereturnind=?, max_invoice_val=?  where storecode='" + promotionHeaderPOJO.getStorecode() + "' and condtypeid = '" + promotionHeaderPOJO.getCondtypeid() + "' and promotionid = '" + promotionHeaderPOJO.getPromotionid() + "'");
            PreparedStatement pstmt1 = con.prepareStatement("update  tbl_promotion_header set description=?,fromdate=?,todate=?,opttype=?,sun=?,mon=?,tue=?,wed=?,thu=?,fri=?,sat=?,active=?,createddate=?,createdtime=?,createduser=?,modifieddate=?,modifiedtime=?,modifieduser=?,updatestatus=?,isComboOffer = ?,maxPromValue = ?,data_syncdate=?,data_synctime=?,applicable_to=?,approved=?,usage_limit=?,uniquepromoind=?,salereturnind=?, max_invoice_val=?,customercode=?,restrictlineitem=?,combineArticles=?,eligibleNoOfDays=?,customerItemAllowed=?,ucpValueCheck=?,promomaxcap=? where storecode='" + promotionHeaderPOJO.getStorecode() + "' and condtypeid = '" + promotionHeaderPOJO.getCondtypeid() + "' and promotionid = '" + promotionHeaderPOJO.getPromotionid() + "'");
            pstmt1.setString(1, promotionHeaderPOJO.getDescription());
            pstmt1.setInt(2, promotionHeaderPOJO.getFromdate());
            pstmt1.setInt(3, promotionHeaderPOJO.getTodate());
            pstmt1.setInt(4, promotionHeaderPOJO.getOpttype());
            pstmt1.setString(5, promotionHeaderPOJO.getSun());
            pstmt1.setString(6, promotionHeaderPOJO.getMon());
            pstmt1.setString(7, promotionHeaderPOJO.getTue());
            pstmt1.setString(8, promotionHeaderPOJO.getWed());
            pstmt1.setString(9, promotionHeaderPOJO.getThu());
            pstmt1.setString(10, promotionHeaderPOJO.getFri());
            pstmt1.setString(11, promotionHeaderPOJO.getSat());
            pstmt1.setString(12, promotionHeaderPOJO.getActive());
            pstmt1.setInt(13, promotionHeaderPOJO.getCreateddate());
            pstmt1.setString(14, promotionHeaderPOJO.getCreatedtime());
            pstmt1.setString(15, promotionHeaderPOJO.getCreateduser());
            pstmt1.setInt(16, promotionHeaderPOJO.getModifieddate());
            pstmt1.setString(17, promotionHeaderPOJO.getModifiedtime());
            pstmt1.setString(18, promotionHeaderPOJO.getModifieduser());
            pstmt1.setString(19, promotionHeaderPOJO.getUpdatestatus());
            pstmt1.setString(20, promotionHeaderPOJO.getIsComboOffer());
            pstmt1.setDouble(21, promotionHeaderPOJO.getPromMaxValue());
            pstmt1.setInt(22, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(23, ConvertDate.getCurrentTimeToString());
            //Code added on Jan 28th 2010 one more entry added in update query
            pstmt1.setString(24, promotionHeaderPOJO.getApplicable_to());
            pstmt1.setString(25, promotionHeaderPOJO.getApproved());
            pstmt1.setString(26, promotionHeaderPOJO.getUsage_limit());
            //Code Added By Mr. Thangaraj on 25.05.2017 for unique Promotion 
            pstmt1.setString(27, promotionHeaderPOJO.getUnique_promo_ind());
            pstmt1.setString(28, promotionHeaderPOJO.getSalereturn_ind());
            pstmt1.setDouble(29, promotionHeaderPOJO.getMaxInvoiceVal());
            //Code end By Mr. Thangaraj on 25.05.2017 for unique Promotion 
            pstmt1.setString(30, promotionHeaderPOJO.getCustomercode());//Added by Balachander V on 18.12.2018 to show promotion for particular customer only
            pstmt1.setString(31, promotionHeaderPOJO.getRestrictLineItem());//Added by Balachander V on 10.10.2019 to validate promo applied line item count
            pstmt1.setString(32, promotionHeaderPOJO.getCombineArticles());//Added by Balachander V on 10.10.2019 to validate article eligible for the applied promotion
            pstmt1.setString(33, promotionHeaderPOJO.getEligibleNoOfDays());//Added by Balachander V on 10.10.2019 to validate No of days to calculate the Old order value
            pstmt1.setString(34, promotionHeaderPOJO.getCustomerItemAllowed());//Added by Balachander V on 14.01.2020 to validate customer item allowed in current order
            pstmt1.setString(35, promotionHeaderPOJO.getUcpValueCheck());//Added by Balachander V on 14.01.2020 to validate UVP based on Frame or lens or contact lens
            pstmt1.setDouble(36, promotionHeaderPOJO.getPromMaxCap());//ADDED BY CHARAN FOR PROMO MAX CAP VERSION 101
            result = pstmt1.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Save Article Promotion Selling Item
     */
    public void saveArticlePromotionSellingItem(Connection con, PromotionSellingItemPOJO promotionSellingItemPOJO) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_selling_item values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, promotionSellingItemPOJO.getStorecode());
        pstmt1.setString(2, promotionSellingItemPOJO.getPromotionid());
        pstmt1.setString(3, promotionSellingItemPOJO.getSellinglineno());
        pstmt1.setString(4, promotionSellingItemPOJO.getSellingmerch());
        pstmt1.setString(5, promotionSellingItemPOJO.getSellingarticle());
        pstmt1.setInt(6, promotionSellingItemPOJO.getSellingqty());
        pstmt1.setDouble(7, promotionSellingItemPOJO.getSellingminrate());
        pstmt1.setDouble(8, promotionSellingItemPOJO.getSellingmaxrate());
        pstmt1.setDouble(9, promotionSellingItemPOJO.getOffermaxrate());
        pstmt1.setDouble(10, promotionSellingItemPOJO.getOfferamount());
        pstmt1.setString(11, promotionSellingItemPOJO.getCalcmethod());
        pstmt1.setInt(12, promotionSellingItemPOJO.getDamagedqty());
        pstmt1.setInt(13, promotionSellingItemPOJO.getDamagedcount());
        pstmt1.setDouble(14, promotionSellingItemPOJO.getTintValue());
        pstmt1.setString(15, promotionSellingItemPOJO.getTintCalculationType());
        pstmt1.setString(16, promotionSellingItemPOJO.getDeletionind());
        pstmt1.setString(17, promotionSellingItemPOJO.getUpdatestatus());
        pstmt1.setInt(18, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(19, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article Promotion Selling Item
     */
    public int updateArticlePromotionSellingItem(Connection con, PromotionSellingItemPOJO promotionSellingItemPOJO) throws SQLException {
        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("update tbl_selling_item set sellingmerch=?,sellingarticle=?,sellingqty=?,sellingminrate=?,sellingmaxrate=?,offermaxrate=?,offeramount=?,calcmethod=?,damagedqty=?,damagedcount=?,updatestatus=?,deletionind=?,tintvalue=?,tintcalc=?,data_syncdate=?,data_synctime=? where storecode = '" + promotionSellingItemPOJO.getStorecode() + "' and promotionid ='" + promotionSellingItemPOJO.getPromotionid() + "' and sellinglineno = '" + promotionSellingItemPOJO.getSellinglineno() + "'");
        pstmt1.setString(1, promotionSellingItemPOJO.getSellingmerch());
        pstmt1.setString(2, promotionSellingItemPOJO.getSellingarticle());
        pstmt1.setInt(3, promotionSellingItemPOJO.getSellingqty());
        pstmt1.setDouble(4, promotionSellingItemPOJO.getSellingminrate());
        pstmt1.setDouble(5, promotionSellingItemPOJO.getSellingmaxrate());
        pstmt1.setDouble(6, promotionSellingItemPOJO.getOffermaxrate());
        pstmt1.setDouble(7, promotionSellingItemPOJO.getOfferamount());
        pstmt1.setString(8, promotionSellingItemPOJO.getCalcmethod());
        pstmt1.setInt(9, promotionSellingItemPOJO.getDamagedqty());
        pstmt1.setInt(10, promotionSellingItemPOJO.getDamagedcount());
        pstmt1.setString(11, promotionSellingItemPOJO.getUpdatestatus());
        pstmt1.setString(12, promotionSellingItemPOJO.getDeletionind());
        pstmt1.setDouble(13, promotionSellingItemPOJO.getTintValue());
        pstmt1.setString(14, promotionSellingItemPOJO.getTintCalculationType());
        pstmt1.setInt(15, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(16, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;

        return result;
    }

    /**
     * Save Article Promotion Offer Item
     */
    public void saveArticlePromotionOfferItem(Connection con, PromotionOfferItemPOJO promotionOfferItemPOJO) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_offer values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, promotionOfferItemPOJO.getStorecode());
        pstmt1.setString(2, promotionOfferItemPOJO.getPromotionid());
        pstmt1.setString(3, promotionOfferItemPOJO.getOfferlineno());
        pstmt1.setInt(4, promotionOfferItemPOJO.getFyear());
        pstmt1.setString(5, promotionOfferItemPOJO.getOffermerch());
        pstmt1.setString(6, promotionOfferItemPOJO.getOfferarticle());
        pstmt1.setInt(7, promotionOfferItemPOJO.getOfferqty());
        pstmt1.setDouble(8, promotionOfferItemPOJO.getOffermaxrate());
        pstmt1.setDouble(9, promotionOfferItemPOJO.getOfferamount());
        pstmt1.setString(10, promotionOfferItemPOJO.getCalcmethod());
        pstmt1.setString(11, promotionOfferItemPOJO.getFreegoodscategory());
        pstmt1.setDouble(12, promotionOfferItemPOJO.getOfferMinRate());
        pstmt1.setDouble(13, promotionOfferItemPOJO.getTintValue());
        pstmt1.setString(14, promotionOfferItemPOJO.getTintCalculationType());
        pstmt1.setString(15, promotionOfferItemPOJO.getDeletionind());
        pstmt1.setString(16, promotionOfferItemPOJO.getUpdatestatus());
        pstmt1.setInt(17, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(18, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article Promotion Offer Item
     */
    public int updateArticlePromotionOfferItem(Connection con, PromotionOfferItemPOJO promotionOfferItemPOJO) throws SQLException {
        int result = 0;

        PreparedStatement pstmt1 = con.prepareStatement("update tbl_offer set fyear=?,offermerch=?,offerarticle=?,offerqty=?,offermaxrate=?,offeramount=?,calcmethod=?,freegoodscategory=?,deletionind=?,updatestatus=?,offerminrate=?,tintvalue=?,tintcalculationtype=?,data_syncdate=?,data_synctime=? where storecode = '" + promotionOfferItemPOJO.getStorecode() + "' and promotionid ='" + promotionOfferItemPOJO.getPromotionid() + "' and promotionlineno = '" + promotionOfferItemPOJO.getOfferlineno() + "'");
        pstmt1.setInt(1, promotionOfferItemPOJO.getFyear());
        pstmt1.setString(2, promotionOfferItemPOJO.getOffermerch());
        pstmt1.setString(3, promotionOfferItemPOJO.getOfferarticle());
        pstmt1.setInt(4, promotionOfferItemPOJO.getOfferqty());
        pstmt1.setDouble(5, promotionOfferItemPOJO.getOffermaxrate());
        pstmt1.setDouble(6, promotionOfferItemPOJO.getOfferamount());
        pstmt1.setString(7, promotionOfferItemPOJO.getCalcmethod());
        pstmt1.setString(8, promotionOfferItemPOJO.getFreegoodscategory());
        pstmt1.setString(9, promotionOfferItemPOJO.getDeletionind());
        pstmt1.setString(10, promotionOfferItemPOJO.getUpdatestatus());
        pstmt1.setDouble(11, promotionOfferItemPOJO.getOfferMinRate());
        pstmt1.setDouble(12, promotionOfferItemPOJO.getTintValue());
        pstmt1.setString(13, promotionOfferItemPOJO.getTintCalculationType());
        pstmt1.setInt(14, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(15, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();
        pstmt1 = null;

        return result;
    }

    /**
     * Update Article Condition Type Master
     */
    public void saveArticleConditionTypeMaster(Connection con, ConditionTypeMasterPOJO conditionTypeMasterPOJO) throws SQLException {

        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("insert into tbl_conditiontypemaster values (?,?,?,?,?,?,?,?,?,?,?,?)");
        pstmt1.setString(1, conditionTypeMasterPOJO.getCondType());
        pstmt1.setString(2, conditionTypeMasterPOJO.getCountry());
        pstmt1.setString(3, conditionTypeMasterPOJO.getRegion());
        pstmt1.setString(4, conditionTypeMasterPOJO.getCondTypeDescriptiion());
        pstmt1.setString(5, conditionTypeMasterPOJO.getPosCondType());
        pstmt1.setDouble(6, conditionTypeMasterPOJO.getMaxAmount());
        pstmt1.setString(7, conditionTypeMasterPOJO.getTaxCode());
        pstmt1.setString(8, conditionTypeMasterPOJO.getBaseTax());
        pstmt1.setString(9, conditionTypeMasterPOJO.getConditionCategory());
        pstmt1.setString(10, conditionTypeMasterPOJO.getUpdateStatus());
        pstmt1.setInt(11, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(12, ConvertDate.getCurrentTimeToString());
        pstmt1.executeUpdate();
        pstmt1 = null;

    }

    /**
     * Update Article Condition Type Master
     */
    public int updateArticleConditionTypeMaster(Connection con, ConditionTypeMasterPOJO conditionTypeMasterPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt1 = con.prepareStatement("update tbl_conditiontypemaster set condtypedescription=?,poscondtype=?,maxamount=?,taxcode=?,basetax=?,updatestatus=?,conditioncategory = ?,data_syncdate=?,data_synctime=? where condtype = ? and country =? and region =?");
        pstmt1.setString(1, conditionTypeMasterPOJO.getCondTypeDescriptiion());
        pstmt1.setString(2, conditionTypeMasterPOJO.getPosCondType());
        pstmt1.setDouble(3, conditionTypeMasterPOJO.getMaxAmount());
        pstmt1.setString(4, conditionTypeMasterPOJO.getTaxCode());
        pstmt1.setString(5, conditionTypeMasterPOJO.getBaseTax());
        pstmt1.setString(6, conditionTypeMasterPOJO.getUpdateStatus());
        pstmt1.setString(7, conditionTypeMasterPOJO.getConditionCategory());
        pstmt1.setInt(8, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(9, ConvertDate.getCurrentTimeToString());
        pstmt1.setString(10, conditionTypeMasterPOJO.getCondType());
        pstmt1.setString(11, conditionTypeMasterPOJO.getCountry());
        pstmt1.setString(12, conditionTypeMasterPOJO.getRegion());

        result = pstmt1.executeUpdate();

        return result;
    }

    /**
     * To get the Round Off Condition Type
     */
    public String getRoundOffCondType(Connection con) {
        String roundOffCondType = "";
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select poscondtype from tbl_conditiontypemaster where conditioncategory = 'R'");
            if (rs.next()) {
                roundOffCondType = rs.getString("poscondtype");
            }
        } catch (Exception e) {

        }
        return roundOffCondType;
    }

    /**
     * Populate the List of Charateristics Value for a particular Article
     */
    public void setCharactersticDropDownValues(Connection con, String charactersticName, JComboBox jComboBox) {
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select distinct(value) from tbl_articlemaster_characteristics where characteristics = '" + charactersticName + "' and len(value)>0 ");
            while (rs.next()) {
                jComboBox.addItem(rs.getString("value"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * To Get the Lens Type Value for the Material Code(Article)
     */
    public String getLensTypevalue(Connection con, String matCode) {
        String value = null;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select distinct(value) from tbl_articlemaster_characteristics where characteristics = 'LENSTYPE_PEWH' and materialcode='" + matCode + "' ");
            while (rs.next()) {
                value = rs.getString("value");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Populate the Brand Values
     */
    public void populateBrandValues(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "PRODUCTNAME_PEWH", jComboBox);
    }

    /**
     * Get the list of Merchandising Category for a particular Category
     */
    public ArrayList<String> getMerchCatsBasedOnCategory(Connection con, String category) {
        ArrayList<String> merchCategory = null;
        try {
            merchCategory = new ArrayList<String>();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select merchcategory from tbl_articlemaster where category = '" + category + "'");
            while (rs.next()) {
                merchCategory.add(rs.getString("merchcategory"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return merchCategory;
    }

    /**
     * Get the list of Merchandising Category for Frames
     */
    public ArrayList<String> getMerchCategoriesForFrames(Connection con) {
        return getMerchBasedOnType(con, "MERCHFRAME");
    }

    /**
     * Get the list of Merchandising Category for Lenses
     */
    public ArrayList<String> getMerchCategoriesForLens(Connection con) {
        return getMerchBasedOnType(con, "MERCHLENS");
    }

    /**
     * Get the list of Merchandising Category for Contact Lenses
     */
    public ArrayList<String> getMerchCategoriesForContactLens(Connection con) {
        return getMerchBasedOnType(con, "MERCHCONLENS");
    }

    /**
     * Get the list of Merchandising Category for Spares
     */
    public ArrayList<String> getMerchCategoriesForSpares(Connection con) {
        return getMerchBasedOnType(con, "MERCHSPARE");
    }

    /**
     * Get the list of Merchandising Category for Customer Item
     */
    public ArrayList<String> getMerchCategoriesForCustomerItem(Connection con) {
        return getMerchBasedOnType(con, "MERCHCUSTITEM");
    }

    /**
     * Get the list of Merchandising Category for GV
     */
    public ArrayList<String> getMerchCategoriesForGV(Connection con) {
        return getMerchBasedOnType(con, "MERCHGV");
    }

    /**
     * Get the list of Merchandising Category for Accessories
     */
    public ArrayList<String> getMerchCategoriesForAccessories(Connection con) {
        return getMerchBasedOnType(con, "MERCHACCE");
    }

    /**
     * Get the list of Merchandising Category for Sunglasses
     */
    public ArrayList<String> getMerchCategoriesForSunglass(Connection con) {
        return getMerchBasedOnType(con, "MERCHSUNGL");
    }

    /**
     * Get the list of Merchandising Category for Cleaning Solution
     */
    public ArrayList<String> getMerchCategoriesForCleaningSolution(Connection con) {
        return getMerchBasedOnType(con, "MERCHCLEANSOL");
    }

    /**
     * Populate the Category Values in a drop down
     */
    public void populateCategoryValues(Connection con, JComboBox jComboBox, int saleType) {// HERE SALE TYPE IS 1 for spectacle rx and 2 for contact lens and 3 for others and 0 for nothing and 4 for both contactlens and others
        try {
            StringBuffer sb = new StringBuffer("select distinct(category) from tbl_articlemaster where merchcategory ");
            statement = con.createStatement();
            jComboBox.removeAllItems();
            System.out.print("sale type in category  " + saleType);
            if (saleType == 1) {//1 for saleorder spectacle rx
                int i = 0;
                Iterator iterator = getMerchCategoriesForLens(con).iterator();
                while (iterator.hasNext()) {
                    if (i == 0) {
                        sb.append(" in ( '");
                    } else {
                        sb.append(" ,'");
                    }
                    String val = (String) iterator.next();
                    sb.append(val);
                    sb.append("'");
                    i++;
                }
                iterator = getMerchCategoriesForFrames(con).iterator();
                while (iterator.hasNext()) {
                    if (i == 0) {
                        sb.append(" in ( '");
                    } else {
                        sb.append(" ,'");
                    }
                    String val = (String) iterator.next();
                    sb.append(val);
                    sb.append("'");
                    i++;
                }
                if (i == 0) {
                    sb.append(" not in ('') ");
                } else {
                    sb.append(" ) ");
                }
            } else if (saleType == 2) {//2 for contact lens
                int i = 0;
                Iterator iterator = getMerchCategoriesForContactLens(con).iterator();
                while (iterator.hasNext()) {
                    if (i == 0) {
                        sb.append(" in ( '");
                    } else {
                        sb.append(" ,'");
                    }
                    String val = (String) iterator.next();
                    sb.append(val);
                    sb.append("'");
                    i++;
                }
                if (i == 0) {
                    sb.append(" not in ('') ");
                } else {
                    sb.append(" ) ");
                }
            } else if (saleType == 3 || saleType == 4) {//3 for saleorder others //4 for direct billing 

                int i = 0;
                if (saleType == 3) {
                    Iterator iterator = getMerchCategoriesForContactLens(con).iterator();
                    while (iterator.hasNext()) {
                        if (i == 0) {
                            sb.append(" not in ( '");
                        } else {
                            sb.append(" ,'");
                        }
                        String val = (String) iterator.next();
                        sb.append(val);
                        sb.append("'");
                        i++;
                    }
                    iterator = getMerchCategoriesForCustomerItem(con).iterator();
                    while (iterator.hasNext()) {
                        if (i == 0) {
                            sb.append(" not in ( '");
                        } else {
                            sb.append(" ,'");
                        }
                        String val = (String) iterator.next();
                        sb.append(val);
                        sb.append("'");
                        i++;
                    }
                    iterator = getMerchCategoriesForGV(con).iterator();
                    while (iterator.hasNext()) {
                        if (i == 0) {
                            sb.append(" not in ( '");
                        } else {
                            sb.append(" ,'");
                        }
                        String val = (String) iterator.next();
                        sb.append(val);
                        sb.append("'");
                        i++;
                    }
                    iterator = getMerchCategoriesForSpares(con).iterator();
                    while (iterator.hasNext()) {
                        if (i == 0) {
                            sb.append(" not in ( '");
                        } else {
                            sb.append(" ,'");
                        }
                        String val = (String) iterator.next();
                        sb.append(val);
                        sb.append("'");
                        i++;
                    }
                }
                Iterator iterator = getMerchCategoriesForLens(con).iterator();
                while (iterator.hasNext()) {
                    if (i == 0) {
                        sb.append(" not in ( '");
                    } else {
                        sb.append(" ,'");
                    }
                    String val = (String) iterator.next();
                    sb.append(val);
                    sb.append("'");
                    i++;
                }
                if (i == 0) {
                    sb.append(" not in ('') ");
                } else {
                    sb.append(" ) ");
                }
            } else if (saleType == 5) {//For spares 
                int i = 0;
                Iterator iterator = getMerchCategoriesForContactLens(con).iterator();
                while (iterator.hasNext()) {
                    if (i == 0) {
                        sb.append(" in ( '");
                    } else {
                        sb.append(" ,'");
                    }
                    String val = (String) iterator.next();
                    sb.append(val);
                    sb.append("'");
                    i++;
                }
                if (i == 0) {
                    sb.append(" not in ('') ");
                } else {
                    sb.append(" ) ");
                }
            } else {
                sb.append(" not in ('') ");
            }

            System.out.print("category query  " + sb.toString());
            ResultSet rs = statement.executeQuery(sb.toString());
            jComboBox.addItem("");
            while (rs.next()) {
                if (Validations.isFieldNotEmpty(rs.getString("category"))) {
                    jComboBox.addItem(rs.getString("category"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Populate the Type Values
     */
    public void populateTypeValues(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "PEW_TYPE", jComboBox);
    }

    /**
     * Populate the Rim Coating Values
     */
    public void populateCoatingRim(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "COATING_PEWH", jComboBox);
    }

    /**
     * Populate the Dia Size
     */
    public void populateDiaSize(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "DIAMETER_PEWH", jComboBox);
    }

    /**
     * Populate the Eye Values
     */
    public void populateEye(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "EYE", jComboBox);
    }

    /**
     * Populate the Mat/Rim
     */
    public void populateMatRiRim(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "MATL-REFRACTVEINDX_PEWH", jComboBox);
    }

    /**
     * Populate the Power and Shape
     */
    public void populateAddPowerShape(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "ADDNLPOWER_PEWH", jComboBox);
    }

    /**
     * Populate the Tint Temp Color
     */
    public void populateTintTempMatColor(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "TINTING-COLOR_PEWH", jComboBox);
    }

    /**
     * Populate the Ornamentation
     */
    public void populateOrnametation(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "ORNAMENTATION_PEWH", jComboBox);
    }

    /**
     * Populate the Spher
     */
    public void populateSpher(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "PWR-SPHCYL-CYL_PEWH", jComboBox);
    }

    /**
     * Populate the Axis
     */
    public void populateAxis(Connection con, JComboBox jComboBox) {
        setCharactersticDropDownValues(con, "AXIS-PRISM_PEWH", jComboBox);
    }

    /**
     * Populate the Merchandising Category based on Type
     */
    public ArrayList<String> getMerchBasedOnType(Connection con, String merchType) {
        ArrayList<String> merchCategory = null;
        ResultSet rs;
        try {
            merchCategory = new ArrayList<String>();
            statement = con.createStatement();
            rs = statement.executeQuery("select param2 from tbl_articleparam where param1 = '" + merchType + "'");
            while (rs.next()) {
                merchCategory.add(rs.getString("param2"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs = null;
        }

        return merchCategory;
    }

    /**
     * Get the Division of the Material
     */
    public String getDivisionByMaterial(Connection con, String material) {
        Statement statement;
        ResultSet rs;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select division from tbl_articlemaster where materialcode = '" + material + "'");
            if (rs.next()) {
                return rs.getString("division");
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            statement = null;
            rs = null;
        }
    }

    /**
     * Get the Merchandising Category for the Material
     */
    public static String getMerchCatForMaterial(Connection con, String material) {
        Statement statement;
        ResultSet rs;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select merchcategory from tbl_articlemaster where materialcode = '" + material + "' and deletionind =''");
            if (rs.next()) {
                return rs.getString("merchcategory");
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            statement = null;
            rs = null;
        }

    }

    //***********************  Method to save brand master ******************/
    public void insertBrandMaster(Connection con, String brand, String brandDesc, String deletionInd) {
        PreparedStatement pstatement = null;
        try {
            pstatement = con.prepareStatement("insert into tbl_brandmaster  values (?,?,?)");
            pstatement.setString(1, brand);
            pstatement.setString(2, brandDesc);
            pstatement.setString(3, deletionInd);
            pstatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstatement != null) {
                try {
                    pstatement.close();
                } catch (SQLException ex) {

                }
            }
        }
    }

    //***********************  Method to save brand color master ******************/
    public void insertBrandColorMaster(Connection con, String brand, String color, String deletionInd) {
        PreparedStatement pstatement = null;
        try {
            pstatement = con.prepareStatement("insert into tbl_brandcolormaster  values (?,?,?)");
            pstatement.setString(1, brand);
            pstatement.setString(2, color);
            pstatement.setString(3, deletionInd);
            pstatement.executeUpdate();
        } catch (Exception e) {

        } finally {
            if (pstatement != null) {
                try {
                    pstatement.close();
                } catch (SQLException ex) {

                }
            }
        }
    }

    //***************** Deleting brand values    
    public void deleteBrandAndColorValues(Connection con) {
        Statement statement1 = null;
        try {
            statement1 = con.createStatement();
            statement1.executeUpdate("delete from tbl_brandcolorMaster");
            statement1.executeUpdate("delete from tbl_brandMaster");
        } catch (Exception e) {

        } finally {
            if (statement1 != null) {
                try {
                    statement1.close();
                } catch (Exception e) {

                }
            }
        }
    }

    //******************** Inserting brand & color masters based on article characteristics
    public void insertBrandAndColorMaster(Connection con) {
        ResultSet rs = null;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select distinct(value) from tbl_articlemaster_characteristics where characteristics = 'PRODUCTNAME_PEWH' and len(value)>0 ");
            while (rs.next()) {
                insertBrandMaster(con, rs.getString("value"), "", "");
            }
            statement = con.createStatement();
            rs = statement.executeQuery("select a.value as brand,b.value as color from tbl_articlemaster_characteristics a left outer join tbl_articlemaster_characteristics b on a.materialcode = b.materialcode where a.characteristics = 'PRODUCTNAME_PEWH' and b.characteristics = 'TINTING-COLOR_PEWH' and len(a.value)>0 and len(b.value)>0");
            while (rs.next()) {
                insertBrandColorMaster(con, rs.getString("brand"), rs.getString("color"), "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
            }
            statement = null;
            rs = null;
        }
    }

    //******************** Inserting brand & color masters based on article characteristics
    public void insertBrandAndColorMasterVer2(Connection con) {
        ResultSet rs = null;
        try {
            deleteBrandAndColorValues(con);
            statement = con.createStatement();
            rs = statement.executeQuery("select distinct(value) from tbl_articlemaster_characteristics a left outer join tbl_articlemaster b on a.materialcode = b. materialcode where characteristics = 'PRODUCTNAME_PEWH' and len(value)>0 and b.merchcategory in ( select param2 from tbl_articleparam where  param1 = 'MERCHCONLENS')");
            while (rs.next()) {
                insertBrandMaster(con, rs.getString("value"), "", "");
            }
            statement = con.createStatement();
            rs = statement.executeQuery("select a.value as brand,b.value as color from (select distinct(value),a.materialcode from tbl_articlemaster_characteristics a left outer join tbl_articlemaster b on a.materialcode = b. materialcode where characteristics = 'PRODUCTNAME_PEWH' and len(value)>0 and b.merchcategory in ( select param2 from tbl_articleparam where  param1 = 'MERCHCONLENS')) a left outer join tbl_articlemaster_characteristics b on a.materialcode = b.materialcode where b.characteristics = 'TINTING-COLOR_PEWH' and len(b.value)>0");
            while (rs.next()) {
                insertBrandColorMaster(con, rs.getString("brand"), rs.getString("color"), "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
            }
            statement = null;
            rs = null;
        }
    }

}

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
package ISRetail.plantdetails;

import ISRetail.Helpers.ConvertDate;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import posstaging.PosStagingPOJO;

public class SiteMasterDO {

    public int insertSite(Connection con, SiteMasterPOJO siteMasterPOJO) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("insert into tbl_sitemaster values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?)");
            pstmt.setString(1, siteMasterPOJO.getSiteid());
            pstmt.setString(2, siteMasterPOJO.getSitedescription());
            pstmt.setString(3, siteMasterPOJO.getSiteaddress_housenumber());
            pstmt.setString(4, siteMasterPOJO.getSiteaddress_street());
            pstmt.setString(5, siteMasterPOJO.getSiteaddress_area());
            pstmt.setString(6, siteMasterPOJO.getSiteaddress_city());
            pstmt.setString(7, siteMasterPOJO.getSiteaddress_postalcode());
            pstmt.setString(8, siteMasterPOJO.getSiteaddress_state());
            pstmt.setString(9, siteMasterPOJO.getSiteaddress_country());
            pstmt.setString(10, siteMasterPOJO.getSite_contact_telephone());
            pstmt.setString(11, siteMasterPOJO.getSite_contact_fax());
            pstmt.setString(12, siteMasterPOJO.getSite_contact_email());
            pstmt.setString(13, siteMasterPOJO.getCompanycode());
//            pstmt.setString(14, siteMasterPOJO.getTransaction_pwd());
            pstmt.setString(14, Validations.passWordEncription(siteMasterPOJO.getTransaction_pwd()));// change by jyoti 25/07/2017
            pstmt.setInt(15, siteMasterPOJO.getPasswordvaliditydate());
            pstmt.setString(16, siteMasterPOJO.getPasswordvalidityindicator());
            pstmt.setInt(17, siteMasterPOJO.getInaugurationdate());
            pstmt.setString(18, siteMasterPOJO.getNum_rangelogic());
            pstmt.setString(19, siteMasterPOJO.getCstno());
            pstmt.setString(20, siteMasterPOJO.getVat_tin_no());
            pstmt.setString(21, siteMasterPOJO.getZone());
            pstmt.setString(22, siteMasterPOJO.getCorresponding_cfa_id());
            pstmt.setString(23, siteMasterPOJO.getCfa_contact_telephone());
            pstmt.setString(24, siteMasterPOJO.getCfa_contact_email());
            pstmt.setString(25, siteMasterPOJO.getTax_calculation_indicator());
            pstmt.setDouble(26, siteMasterPOJO.getCash_payout_limit());
            pstmt.setString(27, siteMasterPOJO.getCorresponding_regoffice_name());
            pstmt.setString(28, siteMasterPOJO.getRegoff_contact_telephone());
            pstmt.setString(29, siteMasterPOJO.getRegoff_contact_email());
            pstmt.setInt(30, siteMasterPOJO.getPostingdate());
            pstmt.setInt(31, siteMasterPOJO.getSystemdate());
            pstmt.setString(32, siteMasterPOJO.getQuotationvalidityperiod());
            pstmt.setString(33, siteMasterPOJO.getQuotationvalidityindicator());
            pstmt.setString(34, siteMasterPOJO.getCreditnotevalidityduration());
            pstmt.setString(35, siteMasterPOJO.getCreditnotevalidityindicator());
            pstmt.setString(36, siteMasterPOJO.getMinadvancepercentage());
            pstmt.setString(37, siteMasterPOJO.getPromotionordiscount());
            pstmt.setString(38, siteMasterPOJO.getIpaddress());
            // pstmt.setString95,siteMasterPOJO.getIsrversion());
            pstmt.setString(39, siteMasterPOJO.getPosversion());
            //   pstmt.setString(37,siteMasterPOJO.getIsrpatchversion());
            pstmt.setString(40, siteMasterPOJO.getPospatchversion());
            //   pstmt.setString(39,siteMasterPOJO.getIsrdbversion());
            //   pstmt.setString(40,siteMasterPOJO.getPosdbversion());
            pstmt.setString(41, siteMasterPOJO.getFiscalyear());
            pstmt.setDouble(42, siteMasterPOJO.getRoundoffvalue());
            //   pstmt.setString(43,siteMasterPOJO.getRegdoffname());
            pstmt.setString(43, siteMasterPOJO.getRegdoffaddress());
            pstmt.setString(44, siteMasterPOJO.getMarquee());
            pstmt.setString(45, siteMasterPOJO.getPan_no());
            pstmt.setString(46, siteMasterPOJO.getPostingindicator());
            pstmt.setString(47, siteMasterPOJO.getDeletionInd());
            pstmt.setInt(48, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt.setString(49, ConvertDate.getCurrentTimeToString());
            pstmt.setString(50, siteMasterPOJO.getLbt_no());
            pstmt.setString(51, siteMasterPOJO.getStore_revw_link());
            pstmt.setString(52, siteMasterPOJO.getTaxFlowIndicator());
            result = pstmt.executeUpdate();
            if (result > 0) {
                int fiscalYear = 0;
                try {
                    fiscalYear = Integer.parseInt(siteMasterPOJO.getFiscalyear());
                } catch (Exception e) {
                }
                // insertRecToPosDocLastNoTable( fiscalYear);//iserts only if no record(for the passed fiscalyear) already exist
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }

        return result;
    }

    public int insertRecToPosDocLastNoTable(int fiscalYear) throws SQLException {
        int res = 0;
        Connection conn = null;
        MsdeConnection msdeConn = null;
        String lastYearCustCode = "", lastYearInq = "", lastYearSO = "", lastYearQuot = "", lastYearAckNo = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            msdeConn = new MsdeConnection();
            conn = msdeConn.createConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select fiscalyear from tbl_posdoclastnumbers where fiscalyear= ? ");
            pstmt.setInt(1, fiscalYear);
            rs = pstmt.executeQuery();
            if (!rs.next()) {//if no records for the fiscalyear passed--insert a new record
                //Getting last years non-fi numbers
                pstmt = conn.prepareStatement("select customercode,inquiryno,saleorderno,quotationno,acknowledgementno from tbl_posdoclastnumbers where fiscalyear= ? ");
                pstmt.setInt(1, (fiscalYear - 1));
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    lastYearCustCode = rs.getString("customercode");
                    lastYearInq = rs.getString("inquiryno");
                    lastYearSO = rs.getString("saleorderno");
                    lastYearQuot = rs.getString("quotationno");
                    lastYearAckNo = rs.getString("acknowledgementno");
                }
                pstmt = conn.prepareStatement("insert into tbl_posdoclastnumbers values(?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, lastYearCustCode);
                pstmt.setString(2, lastYearInq);
                pstmt.setString(3, lastYearSO);
                pstmt.setString(4, "");
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setString(7, "");
                pstmt.setString(8, "");
                pstmt.setString(9, "");
                pstmt.setString(10, lastYearAckNo);
                pstmt.setString(11, lastYearQuot);
                pstmt.setInt(12, fiscalYear);
                res = pstmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            conn.rollback();
            throw new SQLException();
        } finally {
            conn = null;
            msdeConn = null;
            lastYearCustCode = null;
            lastYearInq = null;
            lastYearSO = null;
            lastYearQuot = null;
            pstmt = null;
            rs = null;
        }
        return res;
    }

    public int updateSite(Connection con, SiteMasterPOJO siteMasterPOJO) throws SQLException {
        PreparedStatement pstmt;
        int result = 0;

        try {
//        pstmt = con.prepareStatement("update tbl_sitemaster set sitedescription=?,siteaddress_housenumber=?,siteaddress_street=?,siteaddress_area=?,siteaddress_city=?,siteaddress_postalcode=?,siteaddress_state=?,siteaddress_country=?,site_contact_telephone=?,site_contact_email=?,companycode=?,transaction_pwd=?,passwordvaliditydate=?,paaswordvalidityindicator=?,inaugurationdate=?,num_rangelogic=?,cstno=?,vat_no=?,zone=?,corresponding_cfa_id=?,cfa_contact_telephone=?,cfa_contact_email=?,tax_calculation_indicator=?,cash_payout_limit=?,corresponding_regoffice_name=?,regoff_contact_telephone=?,regoff_contact_email=?,quotationvalidityperiod=?,quotationvaliditytype=?,creditnotevalidityduration=?,creditnotevalidatytype=?,minadvancepercentage=?,promotionordiscount=?,ipaddress=?,posversion=?,pospatchversion=?,fiscalyear=?,roundoffvalue=?,regdoffaddress=?,marquee=?,pan_no=?,data_syncdate=?,data_synctime=?,deletionind=? where siteid = '" + siteMasterPOJO.getSiteid() + "'");
            pstmt = con.prepareStatement("update tbl_sitemaster set sitedescription=?,siteaddress_housenumber=?,siteaddress_street=?,siteaddress_area=?,siteaddress_city=?,siteaddress_postalcode=?,siteaddress_state=?,siteaddress_country=?,site_contact_telephone=?,site_contact_email=?,companycode=?,transaction_pwd=?,passwordvaliditydate=?,paaswordvalidityindicator=?,inaugurationdate=?,num_rangelogic=?,cstno=?,vat_no=?,zone=?,corresponding_cfa_id=?,cfa_contact_telephone=?,cfa_contact_email=?,tax_calculation_indicator=?,cash_payout_limit=?,corresponding_regoffice_name=?,regoff_contact_telephone=?,regoff_contact_email=?,quotationvalidityperiod=?,quotationvaliditytype=?,creditnotevalidityduration=?,creditnotevalidatytype=?,minadvancepercentage=?,promotionordiscount=?,ipaddress=?,posversion=?,pospatchversion=?,fiscalyear=?,roundoffvalue=?,regdoffaddress=?,marquee=?,pan_no=?,data_syncdate=?,data_synctime=?,deletionind=?,lst_no=?,review_link=?,taxflowind=? where siteid = '" + siteMasterPOJO.getSiteid() + "'");
        //pstmt = con.prepareStatement("update tbl_sitemaster set sitedescription=?,siteaddress_housenumber=?,siteaddress_street=?,siteaddress_area=?,siteaddress_city=?,siteaddress_postalcode=?,siteaddress_state=?,siteaddress_country=?,site_contact_telephone=?,site_contact_email=?,companycode=?,transaction_pwd=?,passwordvaliditydate=?,paaswordvalidityindicator=?,inaugurationdate=?,num_rangelogic=?,cstno=?,vat_no=?,zone=?,corresponding_cfa_id=?,cfa_contact_telephone=?,cfa_contact_email=?,tax_calculation_indicator=?,cash_payout_limit=?,corresponding_regoffice_name=?,regoff_contact_telephone=?,regoff_contact_email=?,postingdate=?,quotationvalidityperiod=?,quotationvaliditytype=?,creditnotevalidityduration=?,creditnotevalidatytype=?,minadvancepercentage=?,promotionordiscount=?,ipaddress=?,posversion=?,pospatchversion=?,fiscalyear=?,roundoffvalue=?,regdoffaddress=?,marquee=?,pan_no=?,postingindicator=?,data_syncdate=?,data_synctime=? where siteid = '"+siteMasterPOJO.getSiteid()+"'");
            //pstmt.setString(1,siteMasterPOJO.getSiteid());
            System.out.println("updateSite inside block");
            pstmt.setString(1, siteMasterPOJO.getSitedescription());
            pstmt.setString(2, siteMasterPOJO.getSiteaddress_housenumber());
            pstmt.setString(3, siteMasterPOJO.getSiteaddress_street());
            pstmt.setString(4, siteMasterPOJO.getSiteaddress_area());
            pstmt.setString(5, siteMasterPOJO.getSiteaddress_city());
            pstmt.setString(6, siteMasterPOJO.getSiteaddress_postalcode());
            pstmt.setString(7, siteMasterPOJO.getSiteaddress_state());
            pstmt.setString(8, siteMasterPOJO.getSiteaddress_country());
            pstmt.setString(9, siteMasterPOJO.getSite_contact_telephone());
            pstmt.setString(10, siteMasterPOJO.getSite_contact_email());
            pstmt.setString(11, siteMasterPOJO.getCompanycode());
//        pstmt.setString(12, siteMasterPOJO.getTransaction_pwd());
            pstmt.setString(12, Validations.passWordEncription(siteMasterPOJO.getTransaction_pwd()));// change by jyoti 25/07/2017
            pstmt.setInt(13, siteMasterPOJO.getPasswordvaliditydate());
            pstmt.setString(14, siteMasterPOJO.getPasswordvalidityindicator());
            pstmt.setInt(15, siteMasterPOJO.getInaugurationdate());
            pstmt.setString(16, siteMasterPOJO.getNum_rangelogic());
            pstmt.setString(17, siteMasterPOJO.getCstno());
            pstmt.setString(18, siteMasterPOJO.getVat_tin_no());
            pstmt.setString(19, siteMasterPOJO.getZone());
            pstmt.setString(20, siteMasterPOJO.getCorresponding_cfa_id());
            pstmt.setString(21, siteMasterPOJO.getCfa_contact_telephone());
            pstmt.setString(22, siteMasterPOJO.getCfa_contact_email());
            pstmt.setString(23, siteMasterPOJO.getTax_calculation_indicator());
            pstmt.setDouble(24, siteMasterPOJO.getCash_payout_limit());
            pstmt.setString(25, siteMasterPOJO.getCorresponding_regoffice_name());
            pstmt.setString(26, siteMasterPOJO.getRegoff_contact_telephone());
            pstmt.setString(27, siteMasterPOJO.getRegoff_contact_email());
            //pstmt.setInt(28,siteMasterPOJO.getPostingdate());
            pstmt.setString(28, siteMasterPOJO.getQuotationvalidityperiod());
            pstmt.setString(29, siteMasterPOJO.getQuotationvalidityindicator());
            pstmt.setString(30, siteMasterPOJO.getCreditnotevalidityduration());
            pstmt.setString(31, siteMasterPOJO.getCreditnotevalidityindicator());
            pstmt.setString(32, siteMasterPOJO.getMinadvancepercentage());
            pstmt.setString(33, siteMasterPOJO.getPromotionordiscount());
            pstmt.setString(34, siteMasterPOJO.getIpaddress());
            // pstmt.setString35,siteMasterPOJO.getIsrversion());
            pstmt.setString(35, siteMasterPOJO.getPosversion());
            //   pstmt.setString(37,siteMasterPOJO.getIsrpatchversion());
            pstmt.setString(36, siteMasterPOJO.getPospatchversion());
        //   pstmt.setString(39,siteMasterPOJO.getIsrdbversion());
            //   pstmt.setString(40,siteMasterPOJO.getPosdbversion());
            pstmt.setString(37, siteMasterPOJO.getFiscalyear());
            pstmt.setDouble(38, siteMasterPOJO.getRoundoffvalue());
            //   pstmt.setString(43,siteMasterPOJO.getRegdoffname());
            pstmt.setString(39, siteMasterPOJO.getRegdoffaddress());
            pstmt.setString(40, siteMasterPOJO.getMarquee());
            pstmt.setString(41, siteMasterPOJO.getPan_no());

        //Removed for the Business Date Check
            //  pstmt.setString(43,siteMasterPOJO.getPostingindicator());
            pstmt.setInt(42, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt.setString(43, ConvertDate.getCurrentTimeToString());
            pstmt.setString(44, siteMasterPOJO.getDeletionInd());
            pstmt.setString(45, siteMasterPOJO.getLbt_no());
            pstmt.setString(46, siteMasterPOJO.getStore_revw_link());
            pstmt.setString(47, siteMasterPOJO.getTaxFlowIndicator());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * ******************************** Method to update business date & posting
     * date      **************************************
     */
    public int updateSystemAndPostingDates(Connection con, int systemDate, int businessDate, int fiscalyear) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            if (systemDate != 0 && businessDate != 0 && fiscalyear > 0) {
                pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?,fiscalyear=?");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, systemDate);
                pstmt.setInt(3, fiscalyear);
                result = pstmt.executeUpdate();
                if (result > 0) {
                    insertRecToPosDocLastNoTable(fiscalyear);//iserts to POS Doc last num table ,only if no record(for the passed fiscalyear) already exist
                }

            } else if (systemDate != 0 && businessDate != 0) {//Code added on Feb 5th 2010
                pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, systemDate);
                result = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int insertSiteForceRelease(Connection con, SiteForceReleasePOJO siteForceReleasePOJO) throws SQLException {
        int result = 0;

        PreparedStatement pstmt = con.prepareStatement("insert into  tbl_siteforcerelease values(?,?,?,?,?, ?,?,?,?,?)");
        pstmt.setString(1, siteForceReleasePOJO.getSiteId());
        pstmt.setInt(2, siteForceReleasePOJO.getSlno());
        pstmt.setInt(3, siteForceReleasePOJO.getFromdate());
        pstmt.setInt(4, siteForceReleasePOJO.getTodate());
        pstmt.setInt(5, siteForceReleasePOJO.getForcerelease_quantity());
        pstmt.setInt(6, siteForceReleasePOJO.getCurrentcount());
        pstmt.setString(7, siteForceReleasePOJO.getUpdateStatus());
        pstmt.setString(8, siteForceReleasePOJO.getRecordStatus());
        pstmt.setInt(9, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(10, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();

        return result;
    }

    public void deleteAllRowsInForceRelease(Connection con) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from tbl_siteforcerelease");
        } catch (Exception e) {
        } finally {
            stmt = null;
        }
    }

    public int updateSiteForceRelease(Connection con, SiteForceReleasePOJO siteForceReleasePOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt = con.prepareStatement("update  tbl_siteforcerelease set fromdate=?,todate=?,forcerelease_quantity=?,currentcount=?,updatestatus=?,recordstatus=?,data_syncdate=?,data_synctime=? where siteid ='" + siteForceReleasePOJO.getSiteId() + "' and slno = " + siteForceReleasePOJO.getSlno());
        pstmt.setInt(1, siteForceReleasePOJO.getFromdate());
        pstmt.setInt(2, siteForceReleasePOJO.getTodate());
        pstmt.setInt(3, siteForceReleasePOJO.getForcerelease_quantity());
        pstmt.setInt(4, siteForceReleasePOJO.getCurrentcount());
        pstmt.setString(5, siteForceReleasePOJO.getUpdateStatus());
        pstmt.setString(6, siteForceReleasePOJO.getRecordStatus());
        pstmt.setInt(7, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(8, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();

        return result;
    }

    public int insertSiteCreditSaleReference(Connection con, SiteCreditSaleReference siteCreditSaleReference) throws SQLException {
        int result = 0;

        PreparedStatement pstmt = con.prepareStatement("insert into  tbl_creditsalereference values(?,?,?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, siteCreditSaleReference.getSlno());
        pstmt.setString(2, siteCreditSaleReference.getSiteId());
        pstmt.setString(3, siteCreditSaleReference.getSapcustomerno());
        pstmt.setInt(4, siteCreditSaleReference.getConditionno());
        pstmt.setString(5, siteCreditSaleReference.getInstitutionname());
        pstmt.setInt(6, siteCreditSaleReference.getFromdate());
        pstmt.setInt(7, siteCreditSaleReference.getTodate());
        pstmt.setString(8, siteCreditSaleReference.getUpdatestatus());
        pstmt.setString(9, siteCreditSaleReference.getRecordStatus());
        pstmt.setInt(10, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(11, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();

        return result;
    }

    public void deleteAllRowsInCreditSaleReference(Connection con) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from tbl_creditsalereference");
        } catch (Exception e) {
        } finally {
            stmt = null;
        }
    }

    public int updateSiteCreditSaleReference(Connection con, SiteCreditSaleReference siteCreditSaleReference) throws SQLException {
        int result = 0;
        PreparedStatement pstmt = con.prepareStatement("update tbl_creditsalereference set siteid = ?,sapcustomerno=?,conditionno=?,institutionname=?,fromdate=?,todate=?,updatestatus=?,recordstatus=?,data_syncdate=?,data_synctime=? where slno =" + siteCreditSaleReference.getSlno());
        pstmt.setString(1, siteCreditSaleReference.getSiteId());
        pstmt.setString(2, siteCreditSaleReference.getSapcustomerno());
        pstmt.setInt(3, siteCreditSaleReference.getConditionno());
        pstmt.setString(4, siteCreditSaleReference.getInstitutionname());
        pstmt.setInt(5, siteCreditSaleReference.getFromdate());
        pstmt.setInt(6, siteCreditSaleReference.getTodate());
        pstmt.setString(7, siteCreditSaleReference.getUpdatestatus());
        pstmt.setString(8, siteCreditSaleReference.getRecordStatus());
        pstmt.setInt(9, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(10, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();
        return result;
    }

    public int insertSiteHolidayCalender(Connection con, SiteHolidayCalenderPOJO siteHolidayCalenderPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt = con.prepareStatement("insert into  tbl_holidaycalendar values(?,?,?,?,?,?,?,?,?)");
        pstmt.setInt(1, siteHolidayCalenderPOJO.getSlno());
        pstmt.setString(2, siteHolidayCalenderPOJO.getSiteId());
        pstmt.setString(3, siteHolidayCalenderPOJO.getYear());
        pstmt.setInt(4, siteHolidayCalenderPOJO.getDate());
        pstmt.setString(5, siteHolidayCalenderPOJO.getDescription());
        pstmt.setString(6, siteHolidayCalenderPOJO.getUpdateStatus());
        pstmt.setString(7, siteHolidayCalenderPOJO.getDeletionInd());
        pstmt.setInt(8, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(9, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();
        return result;
    }

    public void deleteAllRowsInHolidayCalender(Connection con) {
        Statement stmt;
        try {
            stmt = con.createStatement();
            stmt.executeUpdate("delete from tbl_holidaycalendar");
        } catch (Exception e) {
        } finally {
            stmt = null;
        }
    }

    public int updateSiteHolidayCalender(Connection con, SiteHolidayCalenderPOJO siteHolidayCalenderPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt = con.prepareStatement("update  tbl_holidaycalendar set year=?,date=?,description=?,updatestatus=?,data_syncdate=?,data_synctime=? where siteid='" + siteHolidayCalenderPOJO.getSiteId() + "' and slno=" + siteHolidayCalenderPOJO.getSlno());
        pstmt.setString(1, siteHolidayCalenderPOJO.getYear());
        pstmt.setInt(2, siteHolidayCalenderPOJO.getDate());
        pstmt.setString(3, siteHolidayCalenderPOJO.getDescription());
        pstmt.setString(4, siteHolidayCalenderPOJO.getUpdateStatus());
        pstmt.setInt(5, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt.setString(6, ConvertDate.getCurrentTimeToString());
        result = pstmt.executeUpdate();
        return result;
    }

    public int insertSitePosArichvalData(Connection con, SitePosArichvalDataPOJO sitePosArichvalDataPOJO) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("insert into  tbl_posarchivaldata values(?,?,?,?)");
            pstmt.setString(1, sitePosArichvalDataPOJO.getSiteId());
            pstmt.setInt(2, sitePosArichvalDataPOJO.getFromdate());
            pstmt.setInt(3, sitePosArichvalDataPOJO.getTodate());
            pstmt.setInt(4, sitePosArichvalDataPOJO.getDateofarcival());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updateSitePosArichvalData(Connection con, SitePosArichvalDataPOJO sitePosArichvalDataPOJO) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update  tbl_posarchivaldata set fromdate=?,todate=?,dateofarcival=? and siteid='" + sitePosArichvalDataPOJO.getSiteId() + "'");
            pstmt.setInt(1, sitePosArichvalDataPOJO.getFromdate());
            pstmt.setInt(2, sitePosArichvalDataPOJO.getTodate());
            pstmt.setInt(3, sitePosArichvalDataPOJO.getDateofarcival());

            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    /**
     * *********************************** METHOD TO GET THE SITE DETAILS          *********************************************
     */
    public SiteMasterPOJO getSite(Connection con) {
        PreparedStatement pstmt;
        ResultSet rs;
        SiteMasterPOJO siteMasterPOJO = new SiteMasterPOJO();
        try {
            pstmt = con.prepareStatement("select * from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteMasterPOJO.setSiteid(rs.getString("siteid"));
                siteMasterPOJO.setSitedescription(rs.getString("sitedescription"));
                siteMasterPOJO.setSite_contact_fax(rs.getString("site_contact_fax"));
                siteMasterPOJO.setCompanycode(rs.getString("companycode"));
                siteMasterPOJO.setNum_rangelogic(rs.getString("num_rangelogic"));
                siteMasterPOJO.setCstno(rs.getString("cstno"));
                siteMasterPOJO.setZone(rs.getString("zone"));
                siteMasterPOJO.setFiscalyear(rs.getString("fiscalyear"));
                siteMasterPOJO.setCorresponding_cfa_id(rs.getString("corresponding_cfa_id"));
                siteMasterPOJO.setCfa_contact_telephone(rs.getString("cfa_contact_telephone"));
                siteMasterPOJO.setCfa_contact_email(rs.getString("cfa_contact_email"));
                siteMasterPOJO.setCash_payout_limit(rs.getDouble("cash_payout_limit"));
                siteMasterPOJO.setMinadvancepercentage(rs.getString("minadvancepercentage"));
                siteMasterPOJO.setRegoff_contact_telephone(rs.getString("regoff_contact_telephone"));
                siteMasterPOJO.setRegoff_contact_email(rs.getString("regoff_contact_email"));
                siteMasterPOJO.setRegdoffaddress(rs.getString("regdoffaddress"));
                siteMasterPOJO.setQuotationvalidityperiod(rs.getString("quotationvalidityperiod"));
                siteMasterPOJO.setQuotationvalidityindicator(rs.getString("quotationvaliditytype"));
                siteMasterPOJO.setCreditnotevalidityduration(rs.getString("creditnotevalidityduration"));
                siteMasterPOJO.setCreditnotevalidityindicator(rs.getString("creditnotevalidatytype"));
                siteMasterPOJO.setPan_no(rs.getString("pan_no"));
                siteMasterPOJO.setSiteaddress_state(rs.getString("siteaddress_state"));
                siteMasterPOJO.setSiteaddress_city(rs.getString("siteaddress_city"));
                siteMasterPOJO.setSiteaddress_country(rs.getString("siteaddress_country"));
                siteMasterPOJO.setSiteaddress_housenumber(rs.getString("siteaddress_housenumber"));
                siteMasterPOJO.setSiteaddress_street(rs.getString("siteaddress_street"));
                siteMasterPOJO.setSiteaddress_area(rs.getString("siteaddress_area"));
                siteMasterPOJO.setSiteaddress_postalcode(rs.getString("siteaddress_postalcode"));
                siteMasterPOJO.setVat_tin_no(rs.getString("vat_no"));
                siteMasterPOJO.setSite_contact_telephone(rs.getString("site_contact_telephone"));
                siteMasterPOJO.setCorresponding_regoffice_name(rs.getString("corresponding_regoffice_name"));
                siteMasterPOJO.setPostingdate(rs.getInt("postingdate"));
                siteMasterPOJO.setSite_contact_email(rs.getString("site_contact_email"));//Added by Bala on 25.12.2017
                siteMasterPOJO.setLbt_no(rs.getString("lst_no"));
               siteMasterPOJO.setMarquee(rs.getString("marquee"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
        return siteMasterPOJO;
    }

    public ArrayList<SiteForceReleasePOJO> getSiteForceRelease(Connection con) {
        int result = 0;
        Statement pstmt;
        ResultSet rs;
        ArrayList<SiteForceReleasePOJO> sfrpojos = null;
        try {
            sfrpojos = new ArrayList<SiteForceReleasePOJO>();
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select * from  tbl_siteforcerelease ");
            while (rs.next()) {
                SiteForceReleasePOJO siteForceReleasePOJO = new SiteForceReleasePOJO();
                siteForceReleasePOJO.setSiteId(rs.getString(1));
                siteForceReleasePOJO.setSlno(rs.getInt(2));
                siteForceReleasePOJO.setFromdate(rs.getInt(3));
                siteForceReleasePOJO.setTodate(rs.getInt(4));
                siteForceReleasePOJO.setForcerelease_quantity(rs.getInt(5));
                siteForceReleasePOJO.setCurrentcount(rs.getInt(6));
                siteForceReleasePOJO.setUpdateStatus(rs.getString(7));
                siteForceReleasePOJO.setRecordStatus(rs.getString(8));
                sfrpojos.add(siteForceReleasePOJO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return sfrpojos;
    }

    public ArrayList<SiteCreditSaleReference> getSiteCreditSaleReference(Connection con) {
        int result = 0;
        Statement pstmt;
        ResultSet rs;
        ArrayList<SiteCreditSaleReference> sfrpojos = null;
        try {
            sfrpojos = new ArrayList<SiteCreditSaleReference>();

            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select * from  tbl_creditsalereference ");
            while (rs.next()) {
                SiteCreditSaleReference siteCreditSaleReference = new SiteCreditSaleReference();
                siteCreditSaleReference.setSiteId(rs.getString(2));
                siteCreditSaleReference.setSlno(rs.getInt(1));
                siteCreditSaleReference.setFromdate(rs.getInt(6));
                siteCreditSaleReference.setTodate(rs.getInt(7));
                siteCreditSaleReference.setSapcustomerno(rs.getString(3));
                siteCreditSaleReference.setConditionno(rs.getInt(4));
                siteCreditSaleReference.setUpdatestatus(rs.getString(8));
                siteCreditSaleReference.setInstitutionname(rs.getString(5));
                sfrpojos.add(siteCreditSaleReference);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return sfrpojos;
    }

    public ArrayList<SiteHolidayCalenderPOJO> getSiteHolidayCalender(Connection con) {
        int result = 0;
        Statement pstmt;
        ResultSet rs;
        ArrayList<SiteHolidayCalenderPOJO> sfrpojos = null;
        try {
            sfrpojos = new ArrayList<SiteHolidayCalenderPOJO>();

            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select * from  tbl_holidaycalendar ");
            while (rs.next()) {
                SiteHolidayCalenderPOJO siteHolidayCalenderPOJO = new SiteHolidayCalenderPOJO();
                siteHolidayCalenderPOJO.setSiteId(rs.getString(2));
                siteHolidayCalenderPOJO.setSlno(rs.getInt(1));
                siteHolidayCalenderPOJO.setYear(rs.getString(3));
                siteHolidayCalenderPOJO.setDate(rs.getInt(4));
                siteHolidayCalenderPOJO.setDescription(rs.getString(5));

                siteHolidayCalenderPOJO.setUpdateStatus(rs.getString(6));

                sfrpojos.add(siteHolidayCalenderPOJO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return sfrpojos;
    }

    public SiteMasterPOJO getSiteAddress(Connection con) {
        SiteMasterPOJO siteMasterPOJO = new SiteMasterPOJO();
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select siteaddress_housenumber,sitedescription,siteaddress_street,siteaddress_area,siteaddress_postalcode,vat_no,site_contact_telephone,siteaddress_city,siteaddress_state,siteaddress_country,corresponding_regoffice_name,cstno,regdoffaddress  from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteMasterPOJO.setSiteaddress_state(rs.getString("siteaddress_state"));
                siteMasterPOJO.setSiteaddress_city(rs.getString("siteaddress_city"));
                siteMasterPOJO.setSiteaddress_country(rs.getString("siteaddress_country"));
                siteMasterPOJO.setSiteaddress_housenumber(rs.getString("siteaddress_housenumber"));
                siteMasterPOJO.setSiteaddress_street(rs.getString("siteaddress_street"));
                siteMasterPOJO.setSiteaddress_area(rs.getString("siteaddress_area"));
                siteMasterPOJO.setSiteaddress_postalcode(rs.getString("siteaddress_postalcode"));
                siteMasterPOJO.setVat_tin_no(rs.getString("vat_no"));
                siteMasterPOJO.setCstno(rs.getString("cstno"));
                siteMasterPOJO.setSite_contact_telephone(rs.getString("site_contact_telephone"));
                siteMasterPOJO.setCorresponding_regoffice_name(rs.getString("corresponding_regoffice_name"));
                siteMasterPOJO.setSitedescription(rs.getString("sitedescription"));
                siteMasterPOJO.setRegdoffaddress(rs.getString("regdoffaddress"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return siteMasterPOJO;
    }

    public String getSiteNumberLogicValue(Connection con) {
        String numRangeLogic = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select num_rangelogic from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                numRangeLogic = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return numRangeLogic;
    }

    public String getSiteCompanyCode(Connection con) {
        String companyCode = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select companycode from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

                companyCode = rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return companyCode;
    }

    public String getSiteId(Connection con) {
        String siteId = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select siteid from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

                siteId = rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return siteId;
    }

    public int getFiscalYear(Connection con) {
        int fiscalYear = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select fiscalyear from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fiscalYear = rs.getInt("fiscalyear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return fiscalYear;
    }

    public String getCashPayoutLimit(Connection con) {
        String cashPayoutLimit = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select cash_payout_limit from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cashPayoutLimit = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return cashPayoutLimit;
    }

    public String getMinAdvancePercent(Connection con) {
        String minAdvancePercent = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select minadvancepercentage from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                minAdvancePercent = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return minAdvancePercent;
    }

    public String getCreditNoteValidity(Connection con) {
        String creditnotevalidityduration = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select creditnotevalidityduration from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                creditnotevalidityduration = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return creditnotevalidityduration;
    }

    public String getMarquee(Connection con) {
        String creditnotevalidityduration = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select marquee from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                creditnotevalidityduration = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return creditnotevalidityduration;
    }

    /**
     * ******************************** method to get the business date     **************************************
     */
    public SystemDate getBusinessDate(Connection con) {
        SystemDate businessDate = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select systemdate,postingdate,postingindicator from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                businessDate = new SystemDate();
                businessDate.setSystemDate(rs.getInt(1));
                businessDate.setPostingDate(rs.getInt(2));
                businessDate.setPostingIndicator(rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return businessDate;
    }

    //credit sale reference    
    // updating force release count
    /**
     * ***Method to get quotation validity period***********
     */
    public String getQuotationValidityperiod(Connection con) {
        String quotationvaliditprd = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select quotationvalidityperiod from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                quotationvaliditprd = rs.getString("quotationvalidityperiod");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return quotationvaliditprd;
    }

    /**
     * ***End of Method to get quotation validity period***********
     */
    /**
     * *******getting force release transactio pwd******************
     */
    public String getForceReleaseTransactionPwd(Connection con) {
        String companyCode = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select transaction_pwd from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

//                companyCode = rs.getString("transaction_pwd");
                companyCode = Validations.passWordDecrypt(rs.getString("transaction_pwd"));               

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return companyCode;
    }

    /**
     * ****End of getting force release transactio pwd****
     */
    /**
     * *****************************Advance Receipt**************************************************************
     */
    /**
     * ****************************** Sale Order ******************************
     */
    public String getCreditNoteValidityTye(Connection con) {
        String creditNoteValidityTye = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select creditnotevalidatytype  from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                creditNoteValidityTye = rs.getString("creditnotevalidatytype");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return creditNoteValidityTye;
    }

    /**
     * ********************************* METHOD TO GET OFFER TYPE FROM SITE
     * MASTER         *****************************************
     */
    public String getOfferType(Connection con) {
        String offerType = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select promotionordiscount  from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                offerType = rs.getString("promotionordiscount");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return offerType;
    }

    /**
     * ********************************************************* METHOD TO GET
     * THE HOLIDAY CALENDER LIST   ***************************************************
     */
    public ArrayList<String> getHolidayList(Connection con) {

        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList<String> holidayList = new ArrayList<String>();
        try {
            pstmt = con.prepareStatement("select date  from tbl_holidaycalendar");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                holidayList.add(ConvertDate.getNumericToDate(rs.getInt("date")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return holidayList;
    }

    public int getSystemDate(Connection con) {

        int systemDate = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select systemdate  from tbl_sitemaster");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                systemDate = rs.getInt("systemdate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return systemDate;
    }

    /**
     * ****************************** Sale Order ******************************
     */
    /**
     * *******getting force release transactio pwd******************
     */
    public int getForceReleaseTransactionPwdvalidity(Connection con) {
        int transactionpwdvalidity = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select passwordvaliditydate from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                transactionpwdvalidity = rs.getInt("passwordvaliditydate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return transactionpwdvalidity;
    }

    /**
     * ****End of getting force release transactio pwd****
     */
    /**
     * ****************************** Sale Order ******************************
     */
    //*********getting Site InaugurationDAte*******************/
    public int getSiteInaugurationDate(Connection con) {
        int siteInaugurationDate = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select inaugurationdate from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                siteInaugurationDate = rs.getInt("inaugurationdate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return siteInaugurationDate;
    }
    //******End of *getting Site InaugurationDAte******/

    public String getSqlSystemTime(Connection con) {

        String systemDate = "";
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select Replicate(\'0\',2-Len(rtrim(convert(char(2),datepart(hh,getdate())))))+rtrim(convert(char(2),datepart(hh,getdate())))+Replicate(\'0\',2-Len(rtrim(convert(char(2),datepart(mi,getdate())))))+rtrim(convert(char(2),datepart(mi,getdate())))+Replicate(\'0\',2-Len(ltrim(rtrim(convert(char(2),datepart(ss,getdate()))))))+ltrim(rtrim(convert(char(2),datepart(ss,getdate())))) systemtime");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                systemDate = rs.getString("systemtime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return systemDate;
    }

    public int getSqlSystemDate(Connection con) {

        int systemDate = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select convert(numeric(8),convert(char(8),getDate(),112)) systemdate");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                systemDate = rs.getInt("systemdate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return systemDate;
    }

    public int updatelasttransactiontime(Connection con, int lasttransadate, String lasttransactime) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            if (lasttransactime != null) {
                pstmt = con.prepareStatement("update tbl_lasttransactiontime set lasttransactiondate=?,lasttransactiontime=?");
                pstmt.setInt(1, lasttransadate);
                pstmt.setString(2, lasttransactime);
                result = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public String getLasttransactiontime(Connection con) {

        String lasttranstime = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select lasttransactiontime  from tbl_lasttransactiontime");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lasttranstime = rs.getString("lasttransactiontime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return lasttranstime;
    }

    public int getLasttransactiondate(Connection con) {

        int lasttransdate = 0;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select lasttransactiondate  from tbl_lasttransactiontime");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                lasttransdate = rs.getInt("lasttransactiondate");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return lasttransdate;
    }

    public int updateSyncdate(Connection con, int syncdate) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            if (syncdate != 0) {
                pstmt = con.prepareStatement("update tbl_syncdate set syncdate=?");
                pstmt.setInt(1, syncdate);
                result = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updateFiscalyear(Connection con, int fiscalyear) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            if (fiscalyear > 0) {
                pstmt = con.prepareStatement("update tbl_sitemaster set fiscalyear=?");
                pstmt.setInt(1, fiscalyear);

                result = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public String getSalesOrderNowithBillno(Connection con, String invnumber) {

        String salorderno = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            //  pstmt = con.prepareStatement("select refno from dbo.tbl_billingheader where invoiceno!='"+invnumber+"' and refno=(select refno from dbo.tbl_billingheader where invoiceno='"+invnumber+"'");

            pstmt = con.prepareStatement("select refno from dbo.tbl_billingheader where refno=(select refno from dbo.tbl_billingheader where invoiceno='" + invnumber + "') and invoiceno!='" + invnumber + "' and invoiceno< '" + invnumber + "'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                salorderno = rs.getString("refno");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }

        return salorderno;
    }

    public HashMap getLastCancelledBillStatus(Connection con, String SoNumber, String invnumber) {
        PreparedStatement pstmt;
        ResultSet rs;
        HashMap posstag = null;
        try {
            pstmt = con.prepareStatement("select updatestatus,sapidstatus from tbl_pos_staging where transactionid1=(select invoicecancellationno from tbl_billcancelheader where invoiceno=(select  top 1 invoiceno from tbl_billingheader where invoiceno != '" + invnumber + "' and invoiceno<'" + invnumber + "' and refno='" + SoNumber + "' order by invoiceno desc))");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                posstag = new HashMap();
                posstag.put("updatestatus", rs.getString("updatestatus"));
                posstag.put("sapidstatus", rs.getString("sapidstatus"));

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
        return posstag;
    }

    public HashMap getLastBillStatus(Connection con, String SoNumber, String invnumber) {
        PreparedStatement pstmt;
        ResultSet rs;
        HashMap posstag = null;
        try {
            pstmt = con.prepareStatement("select updatestatus,sapidstatus from tbl_pos_staging where transactionid1=(select  top 1 invoiceno from tbl_billingheader where invoiceno != '" + invnumber + "' and invoiceno<'" + invnumber + "' and refno='" + SoNumber + "' order by invoiceno desc)");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                posstag = new HashMap();
                posstag.put("updatestatus", rs.getString("updatestatus"));
                posstag.put("sapidstatus", rs.getString("sapidstatus"));

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
        return posstag;
    }
    public static String getSiteCategory(Connection con) {
        String siteId = null;
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select siteCategory from tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

                siteId = rs.getString(1);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
        return siteId;
    }
}

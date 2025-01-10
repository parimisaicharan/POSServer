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
 * This class file is used as a Data Object between Contact Lens Data and Database
 * This is used for transaction of Contact Lens Data from and to the database
 * 
 */
package ISRetail.inquiry;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

/**
 *
 * @author Administrator
 */
public class ContactLensDO {

    public int saveContactLensPres(ContactLensPOJO contactlenspojoobj, Connection con) throws SQLException {
        String insertstr = "insert into tbl_contactlens values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(insertstr);
        pstmt = con.prepareStatement(insertstr);
        pstmt.setString(1, contactlenspojoobj.getContactlensslno());
        pstmt.setString(2, contactlenspojoobj.getExaminedby());
        pstmt.setString(3, contactlenspojoobj.getOor_slitlamp_examination());
        pstmt.setString(4, contactlenspojoobj.getCl_fp_specialcomments());
        pstmt.setString(5, contactlenspojoobj.getRight_trails());
        pstmt.setString(6, contactlenspojoobj.getLeft_trails());
        pstmt.setString(7, contactlenspojoobj.getFollowup());
        pstmt.setInt(8, contactlenspojoobj.getFollowupdate());
        pstmt.setString(9, contactlenspojoobj.getCaresys_renu_mps());
        pstmt.setString(10, contactlenspojoobj.getCaresys_complete_mps());
        pstmt.setString(11, contactlenspojoobj.getCaresys_solo_mps());
        pstmt.setString(12, contactlenspojoobj.getCaresys_silkens_mps());
        pstmt.setString(13, contactlenspojoobj.getCaresys_aquasoft_mps());
        pstmt.setString(14, contactlenspojoobj.getCaresys_lubricant_mps());
        pstmt.setString(15, contactlenspojoobj.getCaresys_moisture_mps());
        pstmt.setString(16, contactlenspojoobj.getCaresys_rewetting_mps());
        pstmt.setString(17, contactlenspojoobj.getCaresys_gp_solution());
        pstmt.setString(18, contactlenspojoobj.getDoctor_name());
        pstmt.setString(19, contactlenspojoobj.getDoctor_contactno());
        pstmt.setString(20, contactlenspojoobj.getDoctor_area());
        pstmt.setString(21, contactlenspojoobj.getDoctor_city());
        pstmt.setString(22, contactlenspojoobj.getOthername());
        int res = pstmt.executeUpdate();
        return res;

    }

    public int editContactLensPres(ContactLensPOJO contactlenspojoobj, Connection con) {
        String updatestr;
        PreparedStatement pstmt;
        try {
            updatestr = "update tbl_contactlens set examinedby=?,oor_slitlamp_examination=?,cl_fp_specialcomments=?,right_trails=?,left_trails=?,followup=?,followupdate=?,caresys_renu_mps=?,caresys_complete_mps=?,caresys_solo_mps=?,caresys_silkens_mps=?,caresys_aquasoft_mps=?,caresys_lubricant_mps=?,caresys_moisture_mps=?,caresys_rewetting_mps=?,caresys_gp_solution=?,doctor_name=?,doctor_contactno=?,doctor_area=?,doctor_city=?,othername=? where contactlensslno=?";
            pstmt = con.prepareStatement(updatestr);
            pstmt.setString(1, contactlenspojoobj.getExaminedby());
            pstmt.setString(2, contactlenspojoobj.getOor_slitlamp_examination());
            pstmt.setString(3, contactlenspojoobj.getCl_fp_specialcomments());
            pstmt.setString(4, contactlenspojoobj.getRight_trails());
            pstmt.setString(5, contactlenspojoobj.getLeft_trails());
            pstmt.setString(6, contactlenspojoobj.getFollowup());
            pstmt.setInt(7, contactlenspojoobj.getFollowupdate());
            pstmt.setString(8, contactlenspojoobj.getCaresys_renu_mps());
            pstmt.setString(9, contactlenspojoobj.getCaresys_complete_mps());
            pstmt.setString(10, contactlenspojoobj.getCaresys_solo_mps());
            pstmt.setString(11, contactlenspojoobj.getCaresys_silkens_mps());
            pstmt.setString(12, contactlenspojoobj.getCaresys_aquasoft_mps());
            pstmt.setString(13, contactlenspojoobj.getCaresys_lubricant_mps());
            pstmt.setString(14, contactlenspojoobj.getCaresys_moisture_mps());
            pstmt.setString(15, contactlenspojoobj.getCaresys_rewetting_mps());
            pstmt.setString(16, contactlenspojoobj.getCaresys_gp_solution());
            pstmt.setString(17, contactlenspojoobj.getDoctor_name());
            pstmt.setString(18, contactlenspojoobj.getDoctor_contactno());
            pstmt.setString(19, contactlenspojoobj.getDoctor_area());
            pstmt.setString(20, contactlenspojoobj.getDoctor_city());
            pstmt.setString(21, contactlenspojoobj.getOthername());
            pstmt.setString(22, contactlenspojoobj.getContactlensslno());

            int res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            return 0;
        } finally {
            pstmt = null;
            updatestr = null;
        }

    }

    public ContactLensPOJO getContactlensByInquiryNo(String docNo, String inqOrSoFlag, Connection con) throws SQLException {
        ContactLensPOJO contactlenspojoobj = null;
        Inquiry_POS inqPos = new Inquiry_POS();
        String query = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            if (inqOrSoFlag != null) {
                if (inqOrSoFlag.trim().equalsIgnoreCase("INQ")) {
                    //clens.* is using since all this table fields are required to be retrieved
                    query = "select inq.datasheetno,inq.customercode,clens.* from tbl_contactlens as clens inner join tbl_inquiry as inq on(inq.inquiryno=clens.contactlensslno)where contactlensslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("SO")) {
                    //clens.* is using since all this table fields are required to be retrieved
                    query = "select soheader.datasheetno,soheader.customercode,clens.* from tbl_contactlens as clens inner join tbl_salesorderheader as  soheader on(soheader.saleorderno=clens.contactlensslno)where contactlensslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("QO")) {
                    //clens.* is using since all this table fields are required to be retrieved
                    query = "select qoheader.datasheetno,qoheader.customercode,clens.* from tbl_contactlens as clens inner join tbl_quotationheader as  qoheader on(qoheader.quotationno=clens.contactlensslno)where contactlensslno=?";
                }
                pstmt = con.prepareStatement(query);
                pstmt.setString(1, docNo);
                rs = pstmt.executeQuery();
                contactlenspojoobj = getValuesToPojoFromResultSet(rs);
                if (contactlenspojoobj != null) {
                    contactlenspojoobj = inqPos.setCLPojoFromCharPojo(con, docNo, contactlenspojoobj);
                }
            }
            return contactlenspojoobj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt.close();
            inqPos = null;
            query = null;
            rs = null;
            pstmt = null;
        }
    }

    public ContactLensPOJO getValuesToPojoFromResultSet(ResultSet rs) {
        ContactLensPOJO contactlenspojoobj = null;
        try {

            if (rs != null) {
                if (rs.next()) {

                    contactlenspojoobj = new ContactLensPOJO();

                    contactlenspojoobj.setContactlensslno(rs.getString("contactlensslno"));
                    contactlenspojoobj.setExaminedby(rs.getString("examinedby"));
                    contactlenspojoobj.setOor_slitlamp_examination(rs.getString("oor_slitlamp_examination"));
                    contactlenspojoobj.setCl_fp_specialcomments(rs.getString("cl_fp_specialcomments"));
                    contactlenspojoobj.setRight_trails(rs.getString("right_trails"));
                    contactlenspojoobj.setLeft_trails(rs.getString("left_trails"));
                    contactlenspojoobj.setFollowup(rs.getString("followup"));
                    contactlenspojoobj.setFollowupdate(rs.getInt("followupdate"));
                    contactlenspojoobj.setCaresys_renu_mps(rs.getString("caresys_renu_mps"));
                    contactlenspojoobj.setCaresys_complete_mps(rs.getString("caresys_complete_mps"));
                    contactlenspojoobj.setCaresys_solo_mps(rs.getString("caresys_solo_mps"));
                    contactlenspojoobj.setCaresys_silkens_mps(rs.getString("caresys_silkens_mps"));
                    contactlenspojoobj.setCaresys_aquasoft_mps(rs.getString("caresys_aquasoft_mps"));
                    contactlenspojoobj.setCaresys_lubricant_mps(rs.getString("caresys_lubricant_mps"));
                    contactlenspojoobj.setCaresys_moisture_mps(rs.getString("caresys_moisture_mps"));
                    contactlenspojoobj.setCaresys_rewetting_mps(rs.getString("caresys_rewetting_mps"));
                    contactlenspojoobj.setCaresys_gp_solution(rs.getString("caresys_gp_solution"));
                    contactlenspojoobj.setDoctor_name(rs.getString("doctor_name"));
                    contactlenspojoobj.setDoctor_contactno(rs.getString("doctor_contactno"));
                    contactlenspojoobj.setDoctor_area(rs.getString("doctor_area"));
                    contactlenspojoobj.setDoctor_city(rs.getString("doctor_city"));
                    contactlenspojoobj.setOthername(rs.getString("othername"));
                    contactlenspojoobj.setDatasheetno(rs.getString("datasheetno"));
                    contactlenspojoobj.setCustomercode(rs.getString("customercode"));
                    contactlenspojoobj.setPres_verify(rs.getString("pres_verified"));
                    contactlenspojoobj.setPres_deviation(rs.getString("pres_deviation"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contactlenspojoobj;
    }

    public int archiveAllContactLensForFiscalYear(int fiscalYear, Connection con, String transType) throws SQLException {
        int res = 0;
        String query = "";
        PreparedStatement pstmt;
        try {
            if (transType != null) {
                if (transType.trim().equalsIgnoreCase("INQ")) {
                    query = "delete from tbl_contactlens where contactlensslno in ( select contactlensslno from tbl_contactlens clens,tbl_inquiry inq where clens.contactlensslno=inq.Inquiryno and inq.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("SO")) {
                    query = "delete from tbl_contactlens where contactlensslno in ( select contactlensslno from tbl_contactlens clens,tbl_salesorderheader so where clens.contactlensslno=so.saleorderno and so.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("QO")) {
                    query = "delete from tbl_contactlens where contactlensslno in ( select contactlensslno from tbl_contactlens clens,tbl_quotationheader quot where clens.contactlensslno=quot.quotationno and quot.fiscalyear = ? )";
                }
                pstmt = con.prepareStatement(query);
                pstmt.setInt(1, fiscalYear);
                res = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query = null;
            pstmt = null;
        }
        return res;

    }
}

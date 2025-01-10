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
 * This class file is used as a Data Object between Prescription Data and Database
 * This is used for transaction of Eye Prescription Data from and to the database
 * 
 */
package ISRetail.inquiry;

import ISRetail.inquiry.Inquiry_POS;
import java.sql.*;

/**
 *
 * @author Administrator
 */
public class PrescriptionDO {

    public int savePrescription(PrescriptionPOJO prescriptionpojoobj, Connection con) throws SQLException {

        String insertstr = "insert into tbl_prescription values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = con.prepareStatement(insertstr);

        pstmt.setString(1, prescriptionpojoobj.getPrescriptionslno());
        pstmt.setString(2, prescriptionpojoobj.getExaminedby());
        pstmt.setString(3, prescriptionpojoobj.getComments());
        pstmt.setString(4, prescriptionpojoobj.getFollowup());
        pstmt.setInt(5, prescriptionpojoobj.getFollowupdate());
        pstmt.setString(6, prescriptionpojoobj.getReferred_to());
        pstmt.setString(7, prescriptionpojoobj.getDoctor_name());
        pstmt.setString(8, prescriptionpojoobj.getDoctor_contactno());
        pstmt.setString(9, prescriptionpojoobj.getDoctor_area());
        pstmt.setString(10, prescriptionpojoobj.getDoctor_city());
        pstmt.setString(11, prescriptionpojoobj.getOthername());
        pstmt.setString(12, prescriptionpojoobj.getPres_verify());
        pstmt.setString(13, prescriptionpojoobj.getPres_deviation());
        int res = pstmt.executeUpdate();

        return res;

    }

    public int editPrescription(PrescriptionPOJO prescriptionpojoobj, Connection con) throws SQLException {

        String insertstr = "update tbl_prescription set examinedby=?,comments=?,followup=?,followupdate=?,referred_to=?,doctor_name=?,doctor_contactno=?,doctor_area=?,doctor_city=?,othername=?  where  prescriptionslno=?";

        PreparedStatement pstmt = con.prepareStatement(insertstr);

        pstmt.setString(1, prescriptionpojoobj.getExaminedby());
        pstmt.setString(2, prescriptionpojoobj.getComments());
        pstmt.setString(3, prescriptionpojoobj.getFollowup());
        pstmt.setInt(4, prescriptionpojoobj.getFollowupdate());
        pstmt.setString(5, prescriptionpojoobj.getReferred_to());
        pstmt.setString(6, prescriptionpojoobj.getDoctor_name());
        pstmt.setString(7, prescriptionpojoobj.getDoctor_contactno());
        pstmt.setString(8, prescriptionpojoobj.getDoctor_area());
        pstmt.setString(9, prescriptionpojoobj.getDoctor_city());
        pstmt.setString(10, prescriptionpojoobj.getOthername());
        pstmt.setString(11, prescriptionpojoobj.getPrescriptionslno());

        int res = pstmt.executeUpdate();

        return res;
    }

    public PrescriptionPOJO getPrescriptionByInquiryNo(String docNo, String inqOrSoFlag, Connection con) throws SQLException {
        PrescriptionPOJO prescriptionpojoobj = null;
        Inquiry_POS inqPos = new Inquiry_POS();
        String query = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            if (inqOrSoFlag != null) {
                if (inqOrSoFlag.trim().equalsIgnoreCase("INQ")) {
                    query = "select inq.datasheetno,inq.customercode,pres.prescriptionslno,pres.examinedby,pres.comments,pres.followup,pres.followupdate,pres.referred_to,pres.doctor_name,pres.doctor_contactno,pres.doctor_area,pres.doctor_city,pres.othername,pres.pres_verified,pres.pres_deviation from tbl_prescription as pres inner join tbl_inquiry inq on(inq.inquiryno=pres.prescriptionslno)where prescriptionslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("SO")) {
                    query = "select soheader.datasheetno,soheader.customercode,pres.prescriptionslno,pres.examinedby,pres.comments,pres.followup,pres.followupdate,pres.referred_to,pres.doctor_name,pres.doctor_contactno,pres.doctor_area,pres.doctor_city,pres.othername,pres.pres_verified,pres.pres_deviation from tbl_prescription as pres inner join tbl_salesorderheader as soheader on(soheader.saleorderno=pres.prescriptionslno)where prescriptionslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("QO")) {
                    query = "select qoheader.datasheetno,qoheader.customercode,pres.prescriptionslno,pres.examinedby,pres.comments,pres.followup,pres.followupdate,pres.referred_to,pres.doctor_name,pres.doctor_contactno,pres.doctor_area,pres.doctor_city,pres.othername,pres.pres_verified,pres.pres_deviation from tbl_prescription as pres inner join tbl_quotationheader as qoheader on(qoheader.quotationno=pres.prescriptionslno)where prescriptionslno=?";
                }

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, docNo);
                rs = pstmt.executeQuery();
                prescriptionpojoobj = getValuesToPojoFromResultSet(rs);
                if (prescriptionpojoobj != null) {
                    prescriptionpojoobj = inqPos.setPRPojoFromCharPojo(con, docNo, prescriptionpojoobj);
                }
            }
            return prescriptionpojoobj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            pstmt.close();
            pstmt = null;
            query = null;
            rs = null;
            inqPos = null;
        }
    }

    public static PrescriptionPOJO getValuesToPojoFromResultSet(ResultSet rs) {
        PrescriptionPOJO prescriptionpojoobj = null;

        try {
            if (rs != null) {
                if (rs.next()) {
                    prescriptionpojoobj = new PrescriptionPOJO();
                    prescriptionpojoobj.setPrescriptionslno(rs.getString("prescriptionslno"));
                    prescriptionpojoobj.setExaminedby(rs.getString("examinedby"));
                    prescriptionpojoobj.setComments(rs.getString("comments"));
                    prescriptionpojoobj.setFollowup(rs.getString("followup"));
                    prescriptionpojoobj.setFollowupdate(rs.getInt("followupdate"));
                    prescriptionpojoobj.setReferred_to(rs.getString("referred_to"));
                    prescriptionpojoobj.setDoctor_name(rs.getString("doctor_name"));
                    prescriptionpojoobj.setDoctor_contactno(rs.getString("doctor_contactno"));
                    prescriptionpojoobj.setDoctor_area(rs.getString("doctor_area"));
                    prescriptionpojoobj.setDoctor_city(rs.getString("doctor_city"));
                    prescriptionpojoobj.setOthername(rs.getString("othername"));
                    prescriptionpojoobj.setDatasheetno(rs.getString("datasheetno"));
                    prescriptionpojoobj.setCustomercode(rs.getString("customercode"));
                    prescriptionpojoobj.setPres_verify(rs.getString("pres_verified"));
                    prescriptionpojoobj.setPres_deviation(rs.getString("pres_deviation"));

                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return prescriptionpojoobj;
    }

    public int archiveAllPrescriptionForFiscalYear(int fiscalYear, Connection con, String transType) throws SQLException {
        int res = 0;
        String query = "";
        PreparedStatement pstmt;
        try {
            if (transType != null) {
                if (transType.trim().equalsIgnoreCase("INQ")) {
                    query = "delete from tbl_prescription where prescriptionslno in ( select prescriptionslno from tbl_prescription pres,tbl_inquiry inq where pres.prescriptionslno=inq.Inquiryno and inq.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("SO")) {
                    query = "delete from tbl_prescription where prescriptionslno in ( select prescriptionslno from tbl_prescription pres,tbl_salesorderheader so where pres.prescriptionslno=so.saleorderno and so.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("QO")) {
                    query = "delete from tbl_prescription where prescriptionslno in ( select prescriptionslno from tbl_prescription pres,tbl_quotationheader quot where pres.prescriptionslno=quot.quotationno and quot.fiscalyear = ? )";
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

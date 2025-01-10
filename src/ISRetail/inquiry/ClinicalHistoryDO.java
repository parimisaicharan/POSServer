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
 * This class file is used as a Data Object between Clinical History Data and Database
 * This is used for transaction of Clinical History Data from and to the database
 * 
 */
package ISRetail.inquiry;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ClinicalHistoryDO {

    public int saveClinicalHistory(ClinicalHistoryPOJO clinicalhistorypojoobj, Connection con) throws SQLException{

            String insertstr = "insert into tbl_clinicalhistory values (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(insertstr);
            pstmt.setString(1, clinicalhistorypojoobj.getClinicalhistoryslno());
            pstmt.setString(2, clinicalhistorypojoobj.getExaminedby());
            pstmt.setString(3, clinicalhistorypojoobj.getPasthistory());
            pstmt.setString(4, clinicalhistorypojoobj.getComments());
            pstmt.setString(5, clinicalhistorypojoobj.getSlitlampexamination());

            int res = pstmt.executeUpdate();
            return res;

    }

    public static ClinicalHistoryPOJO getValuesToPojoFromResultSet(ResultSet rs, String docNo) {
        ClinicalHistoryPOJO clinicalhistorypojoobj = null;
        try {
            if (rs != null) {
                if (rs.next()) {
                    clinicalhistorypojoobj = new ClinicalHistoryPOJO();

                    clinicalhistorypojoobj.setClinicalhistoryslno(rs.getString("clinicalhistoryslno"));
                    clinicalhistorypojoobj.setExaminedby(rs.getString("examinedby"));
                    clinicalhistorypojoobj.setPasthistory(rs.getString("pasthistory"));
                    clinicalhistorypojoobj.setComments(rs.getString("comments"));
                    clinicalhistorypojoobj.setSlitlampexamination(rs.getString("slitlampexamination"));
                    clinicalhistorypojoobj.setDatasheetno(rs.getString("datasheetno"));
                    clinicalhistorypojoobj.setCustomercode(rs.getString("customercode"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clinicalhistorypojoobj;
    }

    public static ClinicalHistoryPOJO getClinicalHistoryByClinicalNumber(String docNo, String inqOrSoFlag, Connection con) throws SQLException {
        ClinicalHistoryPOJO clinicalhistorypojoobj = null;
        Inquiry_POS inqPos = new Inquiry_POS();
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String query = null;
        try {
            if (inqOrSoFlag != null) {
                if (inqOrSoFlag.trim().equalsIgnoreCase("INQ")) {
                    query = "select inq.datasheetno,inq.customercode,clini.clinicalhistoryslno,clini.examinedby,clini.pasthistory,clini.comments,clini.slitlampexamination from tbl_clinicalhistory as clini inner join tbl_inquiry inq on(inq.inquiryno=clini.clinicalhistoryslno)where clinicalhistoryslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("SO")) {
                    query = "select soheader.datasheetno,soheader.customercode,clini.clinicalhistoryslno,clini.examinedby,clini.pasthistory,clini.comments,clini.slitlampexamination from tbl_clinicalhistory as clini inner join tbl_salesorderheader soheader on(soheader.saleorderno=clini.clinicalhistoryslno)where clinicalhistoryslno=?";
                } else if (inqOrSoFlag.trim().equalsIgnoreCase("QO")) {
                    query = "select qoheader.datasheetno,qoheader.customercode,clini.clinicalhistoryslno,clini.examinedby,clini.pasthistory,clini.comments,clini.slitlampexamination from tbl_clinicalhistory as clini inner join tbl_quotationheader qoheader on(qoheader.quotationno=clini.clinicalhistoryslno)where clinicalhistoryslno=?";
                }

                pstmt = con.prepareStatement(query);
                pstmt.setString(1, docNo);
                rs = pstmt.executeQuery();
                clinicalhistorypojoobj = getValuesToPojoFromResultSet(rs, docNo);
                if (clinicalhistorypojoobj != null) {
                    clinicalhistorypojoobj = inqPos.setCHPojoFromCharPojo(con, docNo, clinicalhistorypojoobj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            inqPos = null;
            rs = null;
            pstmt = null;
            query = null;
        }
        return clinicalhistorypojoobj;
    }

    public int editClinicalHistory(ClinicalHistoryPOJO clinicalhistorypojoobj, Connection con) throws SQLException {
            String insertstr = "update tbl_clinicalhistory set examinedby=?,pasthistory=?,comments=?,slitlampexamination=? where clinicalhistoryslno=? ";
            PreparedStatement pstmt = con.prepareStatement(insertstr);
            pstmt.setString(1, clinicalhistorypojoobj.getExaminedby());
            pstmt.setString(2, clinicalhistorypojoobj.getPasthistory());
            pstmt.setString(3, clinicalhistorypojoobj.getComments());
            pstmt.setString(4, clinicalhistorypojoobj.getSlitlampexamination());
            pstmt.setString(5, clinicalhistorypojoobj.getClinicalhistoryslno());
            int res = pstmt.executeUpdate();
            return res;
    }
    
  public int archiveAllClinicalHistoryForFiscalYear(int fiscalYear, Connection con, String transType) throws SQLException {
        int res = 0;
        String query = "";
        PreparedStatement pstmt;
        try {
            if (transType != null) {
                if (transType.trim().equalsIgnoreCase("INQ")) {
                    query = "delete from tbl_clinicalhistory where clinicalhistoryslno in ( select clinicalhistoryslno from tbl_clinicalhistory clini,tbl_inquiry inq where clini.clinicalhistoryslno=inq.Inquiryno and inq.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("SO")) {
                    query = "delete from tbl_clinicalhistory where clinicalhistoryslno in ( select clinicalhistoryslno from tbl_clinicalhistory clini,tbl_salesorderheader so where clini.clinicalhistoryslno=so.saleorderno and so.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("QO")) {
                    query = "delete from tbl_clinicalhistory where clinicalhistoryslno in ( select clinicalhistoryslno from tbl_clinicalhistory clini,tbl_quotationheader quot where clini.clinicalhistoryslno=quot.quotationno and quot.fiscalyear = ? )";
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

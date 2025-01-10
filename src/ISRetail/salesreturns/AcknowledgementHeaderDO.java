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

import ISRetail.utility.validations.GenerateNextPosDocNumber;
import ISRetail.utility.validations.Validations;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class AcknowledgementHeaderDO {

    public int saveAcknowledgementHeader(Connection con, AcknowledgementHeaderPOJO acknowledgementheaderpojoobj) throws SQLException{
        PreparedStatement pstmt =null;
        int res=0;
        try {
            //pstmt = con.prepareStatement("insert into tbl_ack_header (storecode,fiscalyear,acknumber,acknowledgementdate,ackstatus,revertdate,refstorecode,refsaleorderno,refposinvoiceno,refposinvoicedate,customercode,totalnetamount,visibledefects,comments,approvedby,approveddate,returntocustomerreason,createdby,createddate,createdtime)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            //code modified on 21 Jun 2011 for sale return name capture
            pstmt = con.prepareStatement("insert into tbl_ack_header (storecode,fiscalyear,acknumber,acknowledgementdate,ackstatus,revertdate,refstorecode,refsaleorderno,refposinvoiceno,refposinvoicedate,customercode,totalnetamount,visibledefects,comments,approvedby,approveddate,returntocustomerreason,createdby,createddate,createdtime,staffresponsible)values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, acknowledgementheaderpojoobj.getStorecode());
            pstmt.setInt(2, acknowledgementheaderpojoobj.getFiscalyear());
            pstmt.setString(3, acknowledgementheaderpojoobj.getAcknumber());
            pstmt.setInt(4, acknowledgementheaderpojoobj.getAcknowledgementdate());
            pstmt.setString(5, acknowledgementheaderpojoobj.getAckstatus());
            pstmt.setInt(6, acknowledgementheaderpojoobj.getRevertdate());
            pstmt.setString(7, acknowledgementheaderpojoobj.getRefstorecode());
            pstmt.setString(8, acknowledgementheaderpojoobj.getRefsaleorderno());
            pstmt.setString(9, acknowledgementheaderpojoobj.getRefposinvoiceno());
            pstmt.setInt(10, acknowledgementheaderpojoobj.getRefposinvoicedate());
            pstmt.setString(11, acknowledgementheaderpojoobj.getCustomercode());
            pstmt.setDouble(12, acknowledgementheaderpojoobj.getTotalnetamount());
            pstmt.setString(13, acknowledgementheaderpojoobj.getVisibledefects());
            pstmt.setString(14, acknowledgementheaderpojoobj.getComments());
            pstmt.setString(15, acknowledgementheaderpojoobj.getApprovedby());
            pstmt.setInt(16, acknowledgementheaderpojoobj.getApproveddate());
            pstmt.setString(17, acknowledgementheaderpojoobj.getReturntocustomerreason());
            pstmt.setString(18, acknowledgementheaderpojoobj.getCreatedby());
            pstmt.setInt(19, acknowledgementheaderpojoobj.getCreateddate());
            pstmt.setString(20, acknowledgementheaderpojoobj.getCreatedtime());
            //code modified on 21 Jun 2011 for sale return name capture 
            pstmt.setString(21, acknowledgementheaderpojoobj.getStaffresp());
            int result = pstmt.executeUpdate();

            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
            pstmt.close();
        }
    }

    public int updateAcknowledgementHeader(Connection con, AcknowledgementHeaderPOJO acknowledgementheaderpojoobj) throws SQLException{
        PreparedStatement pstmt =null;
        int res=0;
        try {
            //pstmt = con.prepareStatement("update  tbl_ack_header set ackstatus=?,revertdate=?,totalnetamount=?,visibledefects=?,comments=?,approvedby=?,approveddate=?,returntocustomerreason=?,modifiedby=?,modifieddate=?,modifiedtime=? where storecode=? and fiscalyear=? and acknumber=?");
            //code modified on 21 Jun 2011 for sale return name capture
            pstmt = con.prepareStatement("update  tbl_ack_header set ackstatus=?,revertdate=?,totalnetamount=?,visibledefects=?,comments=?,approvedby=?,approveddate=?,returntocustomerreason=?,modifiedby=?,modifieddate=?,modifiedtime=?,staffresponsible=? where storecode=? and fiscalyear=? and acknumber=?");
            pstmt.setString(1, acknowledgementheaderpojoobj.getAckstatus());
            pstmt.setInt(2, acknowledgementheaderpojoobj.getRevertdate());
            pstmt.setDouble(3, acknowledgementheaderpojoobj.getTotalnetamount());
            pstmt.setString(4, acknowledgementheaderpojoobj.getVisibledefects());
            pstmt.setString(5, acknowledgementheaderpojoobj.getComments());
            pstmt.setString(6, acknowledgementheaderpojoobj.getApprovedby());
            pstmt.setInt(7, acknowledgementheaderpojoobj.getApproveddate());
            pstmt.setString(8, acknowledgementheaderpojoobj.getReturntocustomerreason());


            pstmt.setString(9, acknowledgementheaderpojoobj.getModifiedby());
            pstmt.setInt(10, acknowledgementheaderpojoobj.getModifieddate());
            pstmt.setString(11, acknowledgementheaderpojoobj.getModifiedtime());
            //code modified on 21 Jun 2011 for sale return name capture
            pstmt.setString(12, acknowledgementheaderpojoobj.getStaffresp());
            pstmt.setString(13, acknowledgementheaderpojoobj.getStorecode());
            pstmt.setInt(14, acknowledgementheaderpojoobj.getFiscalyear());
            pstmt.setString(15, acknowledgementheaderpojoobj.getAcknumber());

            res = pstmt.executeUpdate();

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
            pstmt.close();
        }
    }

    public String generateAckNo(Connection con, String firstFixedString) throws SQLException{
        String nextAckNo = null;
        Statement statement=null;
        try {
            String prevAckNo = null;
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select acknowledgementno from tbl_posdoclastnumbers");
            if (rs.next()) {
                prevAckNo = rs.getString(1);
            }
            if (Validations.isFieldNotEmpty(prevAckNo)) {
                prevAckNo = firstFixedString + prevAckNo;
            } else {
                String inqNoLastPart = "";
                for (int i = firstFixedString.length(); i < 10; i++) {
                    inqNoLastPart = inqNoLastPart + "0";
                }
                prevAckNo = firstFixedString + inqNoLastPart;
            }
            nextAckNo = new GenerateNextPosDocNumber().generatenumber(prevAckNo, firstFixedString.length());
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            statement.close();
        }
        return nextAckNo;
    }

    public int updateLastAckNo(Connection con, String lastDocNo) throws SQLException{
                PreparedStatement pstmt =null;
        int res=0;
        try {
            String query = "update tbl_posdoclastnumbers set acknowledgementno =? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, lastDocNo);
             res = pstmt.executeUpdate();
            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally{
            pstmt.close();
        }

    }

    public AcknowledgementHeaderPOJO getAcknowledgementDetailsByAckNo(Connection con, String ackNo) throws SQLException{
        Statement statement=null;
        try {
            AcknowledgementHeaderPOJO acknowledgementheaderpojoobj = new AcknowledgementHeaderPOJO();
            statement  = con.createStatement();
            String searchstatement = "select * from tbl_ack_header where acknumber = '" + ackNo + "'";
            ResultSet rs = statement.executeQuery(searchstatement);
            if (rs.next()) {
                acknowledgementheaderpojoobj.setStorecode(rs.getString("storecode"));
                acknowledgementheaderpojoobj.setFiscalyear(rs.getInt("fiscalyear"));
                acknowledgementheaderpojoobj.setAcknumber(rs.getString("acknumber"));
                acknowledgementheaderpojoobj.setAcknowledgementdate(rs.getInt("acknowledgementdate"));
                acknowledgementheaderpojoobj.setAckstatus(rs.getString("ackstatus"));
                acknowledgementheaderpojoobj.setRevertdate(rs.getInt("revertdate"));
                acknowledgementheaderpojoobj.setRefstorecode(rs.getString("refstorecode"));
                acknowledgementheaderpojoobj.setRefsaleorderno(rs.getString("refsaleorderno"));
                acknowledgementheaderpojoobj.setRefposinvoiceno(rs.getString("refposinvoiceno"));
                acknowledgementheaderpojoobj.setRefposinvoicedate(rs.getInt("refposinvoicedate"));
                acknowledgementheaderpojoobj.setCustomercode(rs.getString("customercode"));
                acknowledgementheaderpojoobj.setTotalnetamount(rs.getDouble("totalnetamount"));
                acknowledgementheaderpojoobj.setVisibledefects(rs.getString("visibledefects"));
                acknowledgementheaderpojoobj.setComments(rs.getString("comments"));
                acknowledgementheaderpojoobj.setApprovedby(rs.getString("approvedby"));
                acknowledgementheaderpojoobj.setApproveddate(rs.getInt("approveddate"));
                acknowledgementheaderpojoobj.setReturntocustomerreason(rs.getString("returntocustomerreason"));
                acknowledgementheaderpojoobj.setCreatedby(rs.getString("createdby"));
                acknowledgementheaderpojoobj.setCreateddate(rs.getInt("createddate"));
                acknowledgementheaderpojoobj.setCreatedtime(rs.getString("createdtime"));
                acknowledgementheaderpojoobj.setModifiedby(rs.getString("modifiedby"));
                acknowledgementheaderpojoobj.setModifieddate(rs.getInt("modifieddate"));
                acknowledgementheaderpojoobj.setModifiedtime(rs.getString("modifiedtime"));
                acknowledgementheaderpojoobj.setStaffresp(rs.getString("staffresponsible"));//code added on 21 Jun 2011 for sale return name capture    
                acknowledgementheaderpojoobj.setSelectedStore(rs.getString("returnchannel"));
                acknowledgementheaderpojoobj.setOtherchannelname(rs.getString("otherchannelname"));
                acknowledgementheaderpojoobj.setInsuranceId(rs.getString("insuranceId"));
                acknowledgementheaderpojoobj.setSFComplaintID(rs.getString("sfccomplaintid"));
                return acknowledgementheaderpojoobj;
            } else {
                return null;
            }

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally{
            statement.close();
        }

    }
}

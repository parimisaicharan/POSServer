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
package ISRetail.rowlock;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author enteg
 */
public class RowLockDO {

    public int insertRowToRowLock(Connection con, RowLockPOJO rowLockPOJO) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        int sno = 0, res = 0;
        try {
            pstmt = con.prepareStatement("insert into tbl_pos_rowlock  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1, rowLockPOJO.getSno());
            pstmt.setString(2, rowLockPOJO.getTransactionname());
            pstmt.setString(3, rowLockPOJO.getTransactionid1());
            pstmt.setString(4, rowLockPOJO.getTid1_storecode());
            pstmt.setInt(5, rowLockPOJO.getTid1_fiscalyear());
            pstmt.setString(6, rowLockPOJO.getTransactionid2());
            pstmt.setString(7, rowLockPOJO.getTid2_storecode());
            pstmt.setInt(8, rowLockPOJO.getTid2_fiscalyear());
            pstmt.setString(9, rowLockPOJO.getTransactionid3());
            pstmt.setString(10, rowLockPOJO.getTid3_storecode());
            pstmt.setInt(11, rowLockPOJO.getTid3_fiscalyear());
            pstmt.setString(12, rowLockPOJO.getUpdatetype());
            pstmt.setString(13, rowLockPOJO.getCreatedby());
            pstmt.setInt(14, rowLockPOJO.getCreateddate());
            pstmt.setString(15, rowLockPOJO.getCreatedtime());
            res = pstmt.executeUpdate();
            sno = rowLockPOJO.getSno();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            pstmt = null;
            rs = null;
        }
        return sno;

    }

    public int getLastNoOfRowLock(Connection con) throws SQLException {
        Statement stmt = null;
        ResultSet rs;
        int lastNo = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select max(sno)as sno from tbl_pos_rowlock");
            if (rs.next()) {
                lastNo = rs.getInt("sno");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            stmt = null;
            rs = null;
        }
        return lastNo;
    }

     public ArrayList<RowLockPOJO> getRowLockList(Connection con,String searchStmt) throws SQLException {
        Statement stmt = null;
        ResultSet rs;
        ArrayList<RowLockPOJO> lockList=null;
        RowLockPOJO rowLockPOJO=null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(searchStmt);
            int cnt=0;
            while (rs.next()) {
                if(cnt==0){
                    lockList=new  ArrayList<RowLockPOJO>();
                }
                rowLockPOJO=new RowLockPOJO();
                rowLockPOJO.setSno(rs.getInt("sno"));
                rowLockPOJO.setTransactionname(rs.getString("transactionname"));
                rowLockPOJO.setTransactionid1(rs.getString("transactionid1"));
                rowLockPOJO.setTid1_fiscalyear(rs.getInt("tid1_fiscalyear"));
                rowLockPOJO.setTid1_storecode(rs.getString("tid1_storecode"));
                rowLockPOJO.setCreatedby(rs.getString("createdby"));
                rowLockPOJO.setCreateddate(rs.getInt("createddate"));
                rowLockPOJO.setCreatedtime(rs.getString("createdtime"));
                lockList.add(rowLockPOJO);
                cnt++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            stmt = null;
            rs = null;
            rowLockPOJO=null;
        }
        return lockList;
    }
    public int deletefRowLockEntry(Connection con, int sno) throws SQLException {
        PreparedStatement pstmt = null;
        int res = 0;
        try {
                pstmt = con.prepareStatement("delete tbl_pos_rowlock where sno= ? ");
               if (sno > 0) {
                pstmt.setInt(1, sno);
                res = pstmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            pstmt = null;
        }
        return res;
    }

    public boolean isRowLockedForTransaction(Connection con, String transactionName, String updateType) throws SQLException {
        boolean isLocked = false;
        Statement stmt = null;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select sno from tbl_pos_rowlock where transactionname='" + transactionName + "' and updatetype='" + updateType + "' ");
            if (rs.next()) {
                int s=rs.getInt("sno");
                if (s != 0) {
                    isLocked = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            stmt = null;
            rs = null;
        }
        return isLocked;
    }
 public boolean isRowLockedForTransactionChangeMode(Connection con, String transactionName, String updateType,String transID1,String tid1StoreCode,int tid1FiscalYear) throws SQLException {
        boolean isLocked = false;
        Statement stmt = null;
        ResultSet rs;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select sno from tbl_pos_rowlock where transactionname='" + transactionName + "' and updatetype='" + updateType + "' and transactionid1='"+transID1+"' and tid1_storecode='"+tid1StoreCode+"' and tid1_fiscalyear="+tid1FiscalYear+" ");
            if (rs.next()) {
                int s=rs.getInt("sno");
                if (s != 0) {
                    isLocked = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            stmt = null;
            rs = null;
        }
        return isLocked;
    }
    public boolean isRowLockedForSaleOrderCreation(Connection con) {
        boolean isLocked = false;
        try {
            isLocked = isRowLockedForTransaction(con, "SaleOrder", "creation");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return isLocked;
    }
       public boolean isRowLockedForAckChange(Connection con,String ackNumber,String storeCode,int fiscalYear) {
        boolean isNotLocked = false;
        try {
            isNotLocked = isRowLockedForTransactionChangeMode(con, "Ack", "change",ackNumber,storeCode,fiscalYear);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return isNotLocked;
    }

    public RowLockPOJO getRowLockPOJOForSaleOrderCreation(RowLockPOJO rowLockPOJO) {
        try {
            rowLockPOJO.setTransactionname("SaleOrder");
            rowLockPOJO.setUpdatetype("creation");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return rowLockPOJO;
    }
    
     public RowLockPOJO getRowLockPOJOToOpenSRAckInChangeMode(RowLockPOJO rowLockPOJO) {
        try {
            rowLockPOJO.setTransactionname("Ack");
            rowLockPOJO.setUpdatetype("change");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return rowLockPOJO;
    }
    
     public RowLockPOJO getRowLockPOJOForInquiryCreation(RowLockPOJO rowLockPOJO) {
        try {
            rowLockPOJO.setTransactionname("Inquiry");
            rowLockPOJO.setUpdatetype("creation");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return rowLockPOJO;
    }
}

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
 * This class file represents the Data Object for the Data Archival 
 * 
 * 
 * 
 */

package ISRetail.dataarchival;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author enteg
 */
public class DataArchivalDO {
    
    public int insertDataArchiveRecord(Connection con, DataArchiveRecordPOJO dataArchiveRecordPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("insert into tbl_dataarchive values(?,?,?,?,?,?,?,?,?,?)");
            pstmt.setString(1, dataArchiveRecordPOJO.getArchivalid());
            pstmt.setInt(2, dataArchiveRecordPOJO.getSino());
            pstmt.setString(3, dataArchiveRecordPOJO.getSiteid());
            pstmt.setInt(4, dataArchiveRecordPOJO.getArchivalfromdate());
            pstmt.setInt(5, dataArchiveRecordPOJO.getArchivaltodate());
            pstmt.setInt(6, dataArchiveRecordPOJO.getArchivaldate());
            pstmt.setInt(7, dataArchiveRecordPOJO.getStatusupdateddate());
            pstmt.setString(8, dataArchiveRecordPOJO.getStatusupdatedtime());
            pstmt.setString(9, dataArchiveRecordPOJO.getStatus());
            pstmt.setString(10, dataArchiveRecordPOJO.getStatusmessage());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }finally {
            pstmt= null;
        }
        return result;
    }

    public int getLastSnoFromfDataArchiveTable(Connection con) throws SQLException {
        //fiscal year i scur fiscal year- one new record should be iserted at the time of fiscal year updation
        Statement statement;
        ResultSet rs;
        int lastSno = 0;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select max(sino) sno from tbl_dataarchive");
            if (rs.next()) {
                lastSno = rs.getInt("sino");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            rs = null;
        }
        return lastSno;
    }

    public int updateDataArchivalStatus(Connection con, DataArchiveRecordPOJO dataArchiveRecordPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update tbl_dataarchive set status=?,statusmessage=?,statusupdateddate=?,statusupdatedtime=? where sino=? ");
            pstmt.setString(1, dataArchiveRecordPOJO.getStatus());
            pstmt.setString(2, dataArchiveRecordPOJO.getStatusmessage());
            pstmt.setInt(3, dataArchiveRecordPOJO.getStatusupdateddate());
            pstmt.setString(4, dataArchiveRecordPOJO.getStatusupdatedtime());
            pstmt.setInt(5, dataArchiveRecordPOJO.getSino());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }finally {
            pstmt= null;
        }
        
        return result;
    }

    public int updateDataArchiveRecord(Connection con, DataArchiveRecordPOJO dataArchiveRecordPOJO) throws SQLException {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update tbl_dataarchive set siteid=?,archivalfromdate=? ,archivaltodate=?,archivaldate=?,statusupdateddate=?,statusupdatedtime=? ,status=?,statusmessage=? where archivalid=? and sino=?");
            pstmt.setString(1, dataArchiveRecordPOJO.getSiteid());
            pstmt.setInt(2, dataArchiveRecordPOJO.getArchivalfromdate());
            pstmt.setInt(3, dataArchiveRecordPOJO.getArchivaltodate());
            pstmt.setInt(4, dataArchiveRecordPOJO.getArchivaldate());
            pstmt.setInt(5, dataArchiveRecordPOJO.getStatusupdateddate());
            pstmt.setString(6, dataArchiveRecordPOJO.getStatusupdatedtime());
            pstmt.setString(7, dataArchiveRecordPOJO.getStatus());
            pstmt.setString(8, dataArchiveRecordPOJO.getStatusmessage());
            pstmt.setString(9, dataArchiveRecordPOJO.getArchivalid());
            pstmt.setInt(10, dataArchiveRecordPOJO.getSino());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException();
        }finally {
            pstmt= null;
        }
        return result;
    }

    public int executeDeleteStmt(Connection conn, String Stmt) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(Stmt);        
        return (pstmt.executeUpdate());
    }

    public void executeShrinkStmt(Connection conn, String Stmt) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(Stmt);
        pstmt.execute();
    }

    public void executeBackupStmt(Connection conn, String Stmt) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(Stmt);
        pstmt.execute();
    }
}

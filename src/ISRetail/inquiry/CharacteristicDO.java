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
 * This class file is used as a Data Object between Eye Characteristics Data and Database
 * This is used for transaction of Eye Characteristics Data from and to the database
 * 
 */
package ISRetail.inquiry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author enteg
 */
public class CharacteristicDO {
    public int saveCharacteristics(ArrayList<CharacteresticsPOJO> charPojoList, Connection conn) throws SQLException {
        int res = 0;
        if (charPojoList != null) {
            Iterator it = charPojoList.iterator();
            if (it != null) {
                while (it.hasNext()) {
                    CharacteresticsPOJO charPojoObj = (CharacteresticsPOJO) it.next();
                    String insertstr = "insert into tbl_eyecharacteristicvalues values (?,?,?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(insertstr);
                    pstmt.setString(1, charPojoObj.getDoctype());
                    pstmt.setString(2, charPojoObj.getDocnumber());
                    pstmt.setString(3, charPojoObj.getSideindicator());
                    pstmt.setString(4, charPojoObj.getCharname());
                    pstmt.setString(5, charPojoObj.getCharvalue());
                    res = pstmt.executeUpdate();
                }
            }
        }
        return res;
    }

   
     public Map getCharacteristicsByDocNo(Connection conn, String docType, String documentNo, String R_L) {
        Map charList = null;

        try {
            charList = new HashMap();
            PreparedStatement pstmt = conn.prepareStatement("select charname,charvalue from tbl_eyecharacteristicvalues where doctype=? and docnumber=? and sideindicator =?");
            pstmt.setString(1, docType);
            pstmt.setString(2, documentNo);
            pstmt.setString(3, R_L);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                charList.put(rs.getString("charname"), rs.getString("charvalue"));
                System.out.println("key"+rs.getString("charname")+"val"+ rs.getString("charvalue"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return charList;
    }

   
     public int deleteAllCharacteristicsForDocNum(String docNumber, Connection con) throws SQLException {
        int res = 0;
        String query;
        PreparedStatement pstmt;
        try {
            query = "delete from tbl_eyecharacteristicvalues where docnumber=?";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, docNumber);
            res = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query = null;
            pstmt = null;
        }
        return res;

    }
     

    
     public int archiveAllCharacteristicsForFiscalYear(int fiscalYear, Connection con, String transType) throws SQLException {
        int res = 0;
        String query = "";
        PreparedStatement pstmt;
        try {
            if (transType != null) {
                if (transType.trim().equalsIgnoreCase("INQ")) {
                    query = "delete from tbl_eyecharacteristicvalues where docnumber in ( select docnumber from tbl_eyecharacteristicvalues charval,tbl_inquiry inq where charval.docnumber=inq.Inquiryno and inq.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("SO")) {
                    query = "delete from tbl_eyecharacteristicvalues where docnumber in ( select docnumber from tbl_eyecharacteristicvalues charval,tbl_salesorderheader so where charval.docnumber=so.saleorderno and so.fiscalyear = ? )";
                } else if (transType.trim().equalsIgnoreCase("QO")) {
                    query = "delete from tbl_eyecharacteristicvalues where docnumber in ( select docnumber from tbl_eyecharacteristicvalues charval,tbl_quotationheader quot where charval.docnumber=quot.quotationno and quot.fiscalyear = ? )";
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

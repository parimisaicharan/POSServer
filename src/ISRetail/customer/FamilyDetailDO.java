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
 * This class file is used as a Data Object between Customer Family Details and Database
 * This is used for transaction of Customer Family Details Data from and to the database
 * 
 */
package ISRetail.customer;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class FamilyDetailDO {

   /* public int saveFamilyDetailList(ArrayList<FamilyDetailPOJO> familydetailpojoobjlist, Connection conn,String customercode) throws SQLException {
        int res = 0;
        try {
            Iterator it = familydetailpojoobjlist.iterator();
            while (it.hasNext()) {
                FamilyDetailPOJO familydetailpojoobj = (FamilyDetailPOJO) it.next();
                PreparedStatement pstmt = conn.prepareStatement("insert into tbl_familydetails values (?,?,?,?,?,?,?)");
                pstmt.setString(2,customercode);
                pstmt.setInt(1, familydetailpojoobj.getFamilymemberno());
                pstmt.setString(3, familydetailpojoobj.getName());
                pstmt.setString(4, familydetailpojoobj.getRelationship());
                pstmt.setInt(5, familydetailpojoobj.getDob());
                pstmt.setString(6, familydetailpojoobj.getContactno());
                pstmt.setString(7, familydetailpojoobj.getWearingspetacles());
                

                res = pstmt.executeUpdate();
            }
            return res;

        } catch (Exception e) {
          conn.rollback();
            return 0;
        }
    }
*/
  

    public ArrayList<FamilyDetailPOJO> getFamilyDetalisByCustomer(Connection con, String customercode) {
        Statement pstmt;
        String searchstatement;
        ResultSet rs;        
        try {            
            pstmt = con.createStatement();           
            searchstatement = "select * from tbl_familydetails where customercode ='" + customercode + "'";
            rs = pstmt.executeQuery(searchstatement);
            ArrayList<FamilyDetailPOJO> list = new ArrayList<FamilyDetailPOJO>();
            while (rs.next()) {
                FamilyDetailPOJO familydetailpojoobj = new FamilyDetailPOJO();
                familydetailpojoobj.setCustomercode(rs.getString("customercode"));
                familydetailpojoobj.setFamilymemberno(rs.getInt("familymemberno"));
                familydetailpojoobj.setName(rs.getString("name"));
                familydetailpojoobj.setRelationship(rs.getString("relationship"));
                familydetailpojoobj.setDob(rs.getInt("dob"));
                familydetailpojoobj.setContactno(rs.getString("contactno"));
                familydetailpojoobj.setWearingspetacles(rs.getString("wearingspectacles"));
                list.add(familydetailpojoobj);
            }
            return list;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }finally {
            pstmt= null;
            searchstatement= null;
            rs = null;
        }

    }
    
    
    public void deleteFamilyDetailsByCustomer(Connection conn,String customercode)
    {
        PreparedStatement pstmt;
        try
        {
            pstmt = conn.prepareStatement("delete from tbl_familydetails where customercode='"+customercode+"'");
            pstmt.executeUpdate();
        }catch(Exception e)
        {
            
        }finally {
            pstmt= null;
        }
        
    }
}

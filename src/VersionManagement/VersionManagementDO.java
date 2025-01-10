/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package VersionManagement;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class VersionManagementDO {
    
    public int updateVersion(Connection con,VersionHeaderPOJO versionHeaderPOJO)
    {
        PreparedStatement pstmt1;
        try
        {
     int result = 0;
        try {
            pstmt1 = con.prepareStatement("update tbl_latestposversion set versionid=?,versiondescription=?,dateofrelease=?");
            pstmt1.setString(1, versionHeaderPOJO.getVersionId());
            pstmt1.setString(2, versionHeaderPOJO.getVersiondescription());
            pstmt1.setInt(3, versionHeaderPOJO.getDateofrelease());
           
            result = pstmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
        }catch(Exception e)
        {
        e.printStackTrace();
        return 0;
        }finally {
            pstmt1= null;
        }
    
    }
    
     public int updatePatch(Connection con,int patchid)
    {
         PreparedStatement pstmt1;
        try
        {
     int result = 0;
        try {
            pstmt1 = con.prepareStatement("update tbl_latestposversion set latestpatchid=?");
            pstmt1.setInt(1,patchid);
           
            result = pstmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
        }catch(Exception e)
        {
        e.printStackTrace();
        return 0;
        }finally {
            pstmt1= null;
        }
    
         
    }
    
    public void savepatches(Connection con,VersionDetailPOJO versionDetailPOJO)
    {
        PreparedStatement pstmt1;
     try {
            pstmt1 = con.prepareStatement("insert into tbl_patches values (?,?,?,?)");
            pstmt1.setString(1, versionDetailPOJO.getPatchId());
            pstmt1.setString(2, versionDetailPOJO.getPatchdescription());
            pstmt1.setString(3, versionDetailPOJO.getPatchstatement());
            pstmt1.setInt(4, versionDetailPOJO.getPatchdate());
                  
            pstmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pstmt1= null;
        }

    
    
    }
public void saveVersiondetails(Connection con, VersionHeaderPOJO versionHeaderPOJO) {
    PreparedStatement pstmt1;
      try {
            pstmt1 = con.prepareStatement("insert into tbl_latestposversion values (?,?,?)");
            pstmt1.setString(1, versionHeaderPOJO.getVersionId());
            pstmt1.setString(2, versionHeaderPOJO.getVersiondescription());
            pstmt1.setInt(3, versionHeaderPOJO.getDateofrelease());
                  
            pstmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pstmt1= null;
        }
    }
public int executepatchstatements(Connection con,ArrayList<VersionDetailPOJO> versionArraylist)
    {
     int result = 0;
     int noofpatchexecuted=0;
     PreparedStatement pstmt1;
        try {
            Iterator iterator = versionArraylist.iterator();
            while (iterator.hasNext()) {
              
            VersionDetailPOJO versionDetailPOJO = (VersionDetailPOJO) iterator.next();
             //  if(!checkifpatchidexists(versionDetailPOJO.getPatchId(),con))
              {
            String patchstmt=versionDetailPOJO.getPatchstatement();            
            pstmt1 = con.prepareStatement(patchstmt);            
            result = pstmt1.executeUpdate();
            noofpatchexecuted=noofpatchexecuted+1;
            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
     if(noofpatchexecuted==1)
     {
     return result;
     }else if(noofpatchexecuted>1)
     {
        return noofpatchexecuted;
     }
     else{
     return result;
     }   
        
    }

public String getCurrentVersion(Connection con){
    Statement pstmt1;
    String currentVersion = null;
    ResultSet rs = null;
    try{
        pstmt1 = con.createStatement();
        rs = pstmt1.executeQuery("select versionid from tbl_latestposversion");
        if(rs.next()){
            currentVersion = rs.getString("versionid");
        }
    }catch(Exception e){
        
    }
    return currentVersion;
}



//public boolean checkifpatchidexists(String patchid,Connection con)
//{
// try{
//        
//     Statement pstmt = (Statement) con.createStatement();   
//     ResultSet rs = pstmt.executeQuery("select patchid from tbl_patches where patchid='"+patchid+"'");
//         if(rs.next())
//         {
//        
//             return true;
//         }
//         else
//         {
//           return false;
//         }
//  
//        }catch(Exception ex){ex.printStackTrace();
//        return false;
//        }
//
//}

}

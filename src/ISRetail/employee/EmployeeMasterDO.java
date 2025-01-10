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
 * This class file is used as a Data Object between Employee Master and Database
 * This is used for transaction of Employee Master Data from and to the database
 * 
 */

package ISRetail.employee;
import ISRetail.Helpers.ConvertDate;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeMasterDO {
    
    //******************** METHOD TO THE SAVE EMPLOYEE DETAILS AND EMPLOYEE PASSWORD DETAILS
    
   public void insertEmployee(Connection connection, EmployeeMasterPOJO employeeMasterPOJO) throws SQLException{
        PreparedStatement statement = null;
        String query = null;
        int result = 0;
       
            query = "insert into tbl_employeemaster  values (?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, employeeMasterPOJO.getEmpid());//empid
            statement.setString(2, employeeMasterPOJO.getStorecode());//storecode
            statement.setInt(3, employeeMasterPOJO.getFiscalyear());//fiscalyear
            statement.setString(4, employeeMasterPOJO.getTitle());//title
            statement.setString(5, employeeMasterPOJO.getFirstName());//firstname
            statement.setString(6, employeeMasterPOJO.getLastName());//lastname
            statement.setString(7, employeeMasterPOJO.getDesignation());//designation
            statement.setString(8, employeeMasterPOJO.getUserId());//userid
            statement.setString(9, employeeMasterPOJO.getStatus());//status
            statement.setInt(10,ConvertDate.getDateNumeric(ServerConsole.businessDate,"dd/MM/yyyy"));
            statement.setString(11,ConvertDate.getCurrentTimeToString());
            result = statement.executeUpdate();
            if (result > 0) {
                query = "insert into tbl_employeepassword  values (?,?,?,?,?,?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, employeeMasterPOJO.getEmpid());//empid
                statement.setString(2, employeeMasterPOJO.getStorecode());//storecode
                statement.setInt(3, employeeMasterPOJO.getFiscalyear());//fiscalyear
                statement.setString(4, employeeMasterPOJO.getPassWord());//Password
                statement.setInt(5, employeeMasterPOJO.getPassWordValidto());//password valid to
                statement.setInt(6, employeeMasterPOJO.getRoleIdAssigned());//Role assigned
                result = statement.executeUpdate();
            }       
    }
   
    //******************** METHOD TO THE UPDATE EMPLOYEE DETAILS BASED ON PRIMARY KEY
    
      public int updateEmployee(Connection connection, EmployeeMasterPOJO employeeMasterPOJO) throws  SQLException{
        int result = 0;
        PreparedStatement statement = null;
        String query = null;
      
            query = "update tbl_employeemaster  set fiscalyear =?,title=?,firstname=?,lastname=?,designation=?,userid=?,status=?,data_syncdate=?,data_synctime=? where empid='" + employeeMasterPOJO.getEmpid() + "' and storecode = '" + employeeMasterPOJO.getStorecode() + "'";
            statement = connection.prepareStatement(query);
            statement.setInt(1, employeeMasterPOJO.getFiscalyear());//fiscalyear
            statement.setString(2, employeeMasterPOJO.getTitle());//title
            statement.setString(3, employeeMasterPOJO.getFirstName());//firstname
            statement.setString(4, employeeMasterPOJO.getLastName());//lastname
            statement.setString(5, employeeMasterPOJO.getDesignation());//designation
            statement.setString(6, employeeMasterPOJO.getUserId());//userid
            statement.setString(7, employeeMasterPOJO.getStatus());//status
            statement.setInt(8,ConvertDate.getDateNumeric(ServerConsole.businessDate,"dd/MM/yyyy"));
            statement.setString(9,ConvertDate.getCurrentTimeToString());
            result = statement.executeUpdate();

             //Code Added on 02/06/2011  - Naveen
             if(result > 0){
                query = "update tbl_employeepassword  set passwordvalidto = ?,roleIdAssigned = ? where empid='" + employeeMasterPOJO.getEmpid() + "' and storecode = '" + employeeMasterPOJO.getStorecode() + "' and fiscalyear = " + employeeMasterPOJO.getFiscalyear();
                statement = connection.prepareStatement(query);
                statement.setInt(1,employeeMasterPOJO.getPassWordValidto());
                statement.setInt(2,employeeMasterPOJO.getRoleIdAssigned());
                result = statement.executeUpdate();

            }
            //End of Code Added on 02/06/2011  - Naveen
        return result;
    }
      
  /*To check the current version of POS*/
     public String getCurrentVersionofPOS(Connection con) {
        Statement statement;
        ResultSet rs;
        String version = null;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select posversion from tbl_sitemaster");
            if (rs.next()) {
                version = rs.getString("posversion");
            }
        } catch (Exception e) {
        } finally {
            statement = null;
            rs = null;
        }
        return version;
    }
     
     /*End of checking current version of POS*/
     
     /*To check the current version of POS*/
     public String getLatestVersionofPOS(Connection con) {
        Statement statement;
        ResultSet rs;
        String version = null;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery("select versionno from tbl_posversion");
            if (rs.next()) {
                version = rs.getString("versionno");
            }
        } catch (Exception e) {
        } finally {
            statement = null;
            rs = null;
        }
        return version;
    }
     
     //Code addded by Bala on 08/08/2018 to get empid from Employee Name
     public String getEmpid(Connection con, String empname) {
        Statement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select empid from tbl_employeemaster where firstname='" + empname + "'");
            if (rs.next()) {
//                System.out.println("Verified by from sales order header"+rs.getString(1));
                return rs.getString(1);                        
            } else {
                return null;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
            }
            pstmt = null;
            rs = null;
        }

    }
     //Code Endded by Bala on 08/08/2018 to get empid from Employee Name
     
}  
     

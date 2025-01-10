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
 * Data Object for Handling the Transactions between POS Staging Table and the Server Application
 * 
 * 
 */

package ISRetail.Webservices;

import ISRetail.msdeconnection.MsdeConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class PosStatusDO {
    
    
    /**
    * Save One Row in the POS Staging Table
    */    
    public int saveStatus(Connection con,PosStatusBean bean) throws SQLException   {
        
        PreparedStatement pstmt = null;
        int result = 0;
         try {    
              pstmt = con.prepareStatement("Insert into POS_Status values(?,?,?,?,?,?)");
              pstmt.setDate(1,bean.getExecuionDate());
              pstmt.setString(2,bean.getMessageType());
              pstmt.setString(3,bean.getPkKeyValue1());
              pstmt.setString(4,bean.getPkKeyValue2());
              pstmt.setString(5,bean.getActionType());
              pstmt.setString(6,bean.getStatus());
              result = pstmt.executeUpdate();
         } catch (Exception e)  { 
             e.printStackTrace();
         }
         
        finally { 
            pstmt.close();
        }
    
        return result;
    }
    
    /**
    * Get the List of the transactions which are not a success in POS Staging
    */    
    public static ArrayList<PosStatusBean>  getUpdatedValues() throws SQLException { 
        
        Connection con = null ;
        PreparedStatement statement = null ; 
        ResultSet rs = null; 
        ArrayList<PosStatusBean> status = null ;   
        PosStatusBean bean = null ;  
        MsdeConnection msdeconn = null ;
        
        try {
            msdeconn = new MsdeConnection()  ;  
            con = msdeconn.createConnection1();
            status = new ArrayList<PosStatusBean>();
            statement = con.prepareStatement("select messageType,pk_field_1,pk_fileld_2,actionType from POS_Status where status!='success'");
            rs = statement.executeQuery(); 
            while(rs.next()) {  { 
                bean = new PosStatusBean(); 
                bean.setMessageType(rs.getString(1)); 
                bean.setPkKeyValue1(rs.getString(2));
                bean.setPkKeyValue2(rs.getString(3));
                bean.setActionType(rs.getString(4));
                status.add(bean);
            }
                
            }
            
            
        }catch(Exception e) { 
            
         e.printStackTrace();    
        }
        
        
        
        finally { 
            con.close();
        }
       
         return status;
       
    }        
    
    
    /**
    * Set Update Status of all the Transactions
    */    
     public void updateStatus(Connection con ,String pk_value, String status )   { 
           
           PreparedStatement statement = null; 
           
           try { 
             statement = con.prepareStatement("update  POS_Status set  status=? where pk_field_1=?  ");             
             statement.setString(1, status);
             statement.setString(2, pk_value);
             statement.executeUpdate();  
              
           }catch(Exception e ) { 
               
               e.printStackTrace();
               
           }
           
           
     }

     
    /**
    * Set Status of the POS Staging
    */    
     public static void  setStatusTable(Connection con, String messageType, String primaryKey1,String primaryKey2,String actionType ) { 
         
         
         try { 
             // setting the values to the statusBean 
             PosStatusBean statusBean = new PosStatusBean() ; 
             statusBean.setExecuionDate(new Date(System.currentTimeMillis()));
             statusBean.setMessageType(messageType);
             statusBean.setPkKeyValue1(primaryKey1);
             statusBean.setPkKeyValue2(primaryKey2);
             statusBean.setActionType(actionType);
             // TODO need to confirm that whether it is needed from PropertyFile or not 
             statusBean.setStatus("new");
             new PosStatusDO().saveStatus(con, statusBean);
             
         }catch(Exception e) { 
             e.fillInStackTrace(); 
         }
         
     }
     
     
    /**
    * Set Status of the POS Staging
    */    

   public static void  setStatusTable(String messageType, String primaryKey1,String primaryKey2,String actionType ) throws SQLException { 
         
          Connection con = null ;
          MsdeConnection msdeconn = null ;
          
         
         try { 
             // cretating the connection 
             msdeconn = new MsdeConnection()  ;  
             con = msdeconn.createConnection1();
             // setting the values to the statusBean 
             PosStatusBean statusBean = new PosStatusBean() ; 
             statusBean.setExecuionDate(new Date(System.currentTimeMillis()));
             statusBean.setMessageType(messageType);
             statusBean.setPkKeyValue1(primaryKey1);
             statusBean.setPkKeyValue2(primaryKey2);
             statusBean.setActionType(actionType);
             // TODO need to confirm that whether it is needed from PropertyFile or not 
             statusBean.setStatus("new");
             new PosStatusDO().saveStatus(con, statusBean);
             
         }catch(Exception e) { 
             e.fillInStackTrace(); 
         }
         
          finally  {
              con.close();
          }
     }
    
}

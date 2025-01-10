/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comfortcall;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ewitsupport
 */
public class ComfortDO {

    public ArrayList getOrderNumbers(Connection con) throws SQLException, ClassNotFoundException {
        ComfortPojo compojo;
        PreparedStatement statement = null;
        ResultSet rs = null;
int creatDate,comfDate;
String creatTime,comfTime=null;
String comfortDate,createdDate,comfortTime,createdTime=null;
        try {
            statement = con.prepareStatement("select bill.storecode,bill.refno,bill.invoiceno,bill.invoicedate,bill.createdtime,bill.customercode,cust.firstname,cust.mobileno,cmain.city,bill.comfortdate,bill.comforttime,bline.materialcode,bill.invoicevalue,bline.styleconsultant from tbl_billingheader bill inner join tbl_customermastermain cust on bill.customercode=cust.customercode inner join tbl_customermaster cmain on cmain.customercode=cust.customercode inner join tbl_billinglineitem bline on bline.invoiceno=bill.invoiceno inner join tbl_sitemaster site on bill.comfortdate=site.systemdate where bill.billedfrom = 'FINAL' order by invoiceno desc");
            rs = statement.executeQuery();
            ArrayList<ComfortPojo> totalValues = new ArrayList<ComfortPojo>();
            while (rs.next()) {
                compojo = new ComfortPojo();
                compojo.setStorecode(rs.getString(1));
                compojo.setSalesorderno(rs.getString(2));
                compojo.setInvoiceno(rs.getString(3));
                creatDate=rs.getInt(4);
                createdDate=ConvertDate.getNumericToDate(creatDate);
                compojo.setCreateddate(createdDate);
                creatTime=rs.getString(5);
                createdTime=ConvertDate.getformattedTimeFromTime(creatTime);
                compojo.setCreatedtime(createdTime);
                compojo.setCustomercode(rs.getString(6));
                compojo.setCustomername(rs.getString(7));
                compojo.setMobileno(rs.getString(8));
                compojo.setCustomercity(rs.getString(9));
                comfDate=rs.getInt(10);
                comfortDate=ConvertDate.getNumericToDate(comfDate);
               compojo.setComfortcalldate(comfortDate);
               comfTime=rs.getString(11);
               comfortTime=ConvertDate.getformattedTimeFromTime(comfTime);
                compojo.setComfortcalltime(comfortTime);
                compojo.setMaterialcode(rs.getString(12));
                compojo.setNetvalue(rs.getInt(13));
                compojo.setCreatedby(rs.getString(14));
                totalValues.add(compojo);
            }
            return totalValues;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                statement.close();
            } catch (SQLException e) {
            }
            statement = null;
            rs = null;
        }
    }

    public int getLastTransactionDate(Connection con) {
        int date = 0;
        String query;
        ResultSet rs = null;
        PreparedStatement psstatement = null;
        try {
            query = "select lasttransactiondate from tbl_lasttransactiontime";
            psstatement = con.prepareStatement(query);
            rs = psstatement.executeQuery();
            while (rs.next()) {
                date = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
    public int getTotalInvoiceCount(Connection con) {
        int invCount = 0;
        String query;
        ResultSet rs = null;
        PreparedStatement psstatement = null;
        try {
            query = "select count(bill.invoiceno) from tbl_billingheader  bill inner join tbl_sitemaster site on bill.comfortdate=site.systemdate";
            psstatement = con.prepareStatement(query);
            rs = psstatement.executeQuery();
            while (rs.next()) {
                invCount = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return invCount;
    }
     public boolean isSent(Connection con,int date) {        
        ResultSet rs = null;
        PreparedStatement psstatement = null;
        boolean isSend = false;
        try {             
            psstatement = con.prepareStatement("select top 1 status from tbl_mail_status where category = 'comfortcall' and sentdate = '"+date+"' and status = 'Y'");
            rs = psstatement.executeQuery();
            if(!rs.next()) {                
               isSend = true;
            }
                
            
        } catch (Exception e) {
            e.printStackTrace();
            return isSend;
        }
        return isSend;
    }
    public void updateMailStatus(Connection con,String category,int date,String time,String status){
        
        
        PreparedStatement pstmt1 = null;
        try{
         pstmt1 = con.prepareStatement("insert into tbl_mail_status values (?,?,?,?)");
            pstmt1.setString(1, category);
            pstmt1.setInt(2, date);
            pstmt1.setInt(3, Integer.parseInt(time));
            pstmt1.setString(4, status);
                  
            pstmt1.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            pstmt1= null;
        }
        
        
    }
}


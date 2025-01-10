/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comfortcall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ewitsupport
 */
public class MailDo {
    //public static List<String> nameOfImg = new ArrayList<String>();//Added by Jyoti for image in mail body 
        public String[] getMail_Password(Connection con){
        Statement pstmt = null;
        ResultSet rs = null;
        String[] mail = new String[3];
        try{
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select mailid,password,change_password from tbl_mailsettings");
            if(rs.next()) {
                mail[0] = rs.getString(1);
                mail[1] = rs.getString(2);
                mail[2] = rs.getString(3);
                return mail;
            }
            
        }catch(Exception e){
          e.printStackTrace();
        }
        return null;
    }
    
    public String getMailId(Connection con){
        Statement pstmt = null;
        ResultSet rs = null;
        String mail = "";
        try{
            pstmt = con.createStatement();
            rs = pstmt.executeQuery("select site_contact_email from tbl_sitemaster");
            if(rs.next()) {
                mail = rs.getString(1);                
                return mail;
            }
            
        }catch(Exception e){
          e.printStackTrace();
        }
        return null;
    }
    }
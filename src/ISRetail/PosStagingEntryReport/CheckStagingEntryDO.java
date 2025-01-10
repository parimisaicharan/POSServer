/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.PosStagingEntryReport;



import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Administrator
 */
public class CheckStagingEntryDO {
    
   // ArrayList<CheckStagingEntryPOJO> errorentry;
    
public ArrayList checkCustomerEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,customercode,createddate,createdtime from tbl_customermastermain where createddate between "+startingdate+" and "+sysdate+" and customercode not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF01' or interfaceid='PW_IF02')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
           while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("customercode"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
    
    
        
 public ArrayList checkSalesorderEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  //
   searchstmt="select storecode,saleorderno,createddate,createdtime from tbl_salesorderheader where createdby not like '%POS%' and saleorderdate between "+startingdate+" and "+sysdate+"  and saleorderno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF03')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
         while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("saleorderno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
          return errorentry;  
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
public ArrayList checkBillingEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,invoiceno,createddate,createdtime from tbl_billingheader where invoicedate between "+startingdate+" and "+sysdate+" and invoiceno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF04' or interfaceid='PW_IF07' or interfaceid='PW_IF20')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
     while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("invoiceno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}

public ArrayList checkInquiryEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,inquiryno,createddate,createdtime from tbl_inquiry where createddate between "+startingdate+" and "+sysdate+" and inquiryno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF05')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("inquiryno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
public ArrayList checkAdvanceReceipt(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,documentno,createddate,createdtime from tbl_advancereceiptheader where documentdate between "+startingdate+" and "+sysdate+" and documentno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF06' or interfaceid='PW_IF13' or interfaceid='PW_IF22')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("documentno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
public ArrayList checkAckEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,acknumber,createddate,createdtime from tbl_ack_header where acknowledgementdate between "+startingdate+" and "+sysdate+" and acknumber not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF08')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("acknumber"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
public ArrayList checkCashPayoutEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,cashpayoutno,createddate,createdtime from tbl_cashpayout where cashpayoutdate between "+startingdate+" and "+sysdate+" and cashpayoutno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF09')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("cashpayoutno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}
public ArrayList checkQuotationEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,quotationno,createddate,createdtime from tbl_quotationheader where quotationdate between "+startingdate+" and "+sysdate+" and quotationno not in (select transactionid1 from tbl_pos_staging where interfaceid='PW_IF17')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("quotationno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}

public ArrayList checkSalesReturnEntry(Connection conn,ArrayList<CheckStagingEntryPOJO> errorentry,int startingdate,int sysdate){
 String searchstmt;
 try{  
  
   searchstmt="select storecode,salereturnno,createddate,createdtime from tbl_salereturn_header where salereturndate between "+startingdate+" and "+sysdate+" and salereturnno not in (select transactionid2 from tbl_pos_staging where interfaceid='PW_IF21')";
    PreparedStatement pstmt;
        ResultSet rs;
      
            pstmt = conn.prepareStatement(searchstmt);
           
            rs = pstmt.executeQuery();
            while(rs.next()){
              CheckStagingEntryPOJO checkStagingEntryPOJO = new CheckStagingEntryPOJO();             
              checkStagingEntryPOJO.setStorecode(rs.getString("storecode"));
              checkStagingEntryPOJO.setTransactionid(rs.getString("salereturnno"));
              checkStagingEntryPOJO.setCreatedDate(rs.getInt("createddate"));
              checkStagingEntryPOJO.setCreatedTime(rs.getString("createdtime"));
              errorentry.add(checkStagingEntryPOJO);
            }
        return errorentry;    
 
 }catch(Exception e){
 return null;
 }finally{
 }
}

public CheckStagingEntryPOJO getToEmailID(Connection conn){
try{
    CheckStagingEntryPOJO checkStagingEntryPOJO=new CheckStagingEntryPOJO();
String emailid=null;
// String searchstmt="select configval from dbo.tbl_pos_configparam where configkey='errorreport_emailid'";
  
String searchstmt="select * from dbo.tbl_pos_configparam where configkey like '%errorreport_%'";
PreparedStatement pstmt;
int i=0;
    ResultSet rs;
    pstmt = conn.prepareStatement(searchstmt);
    rs = pstmt.executeQuery();
     while(rs.next()){
         if(i==0){
         checkStagingEntryPOJO.setEmailid(rs.getString("configval"));
         }else if(i==1){
          checkStagingEntryPOJO.setFromemailid(rs.getString("configval"));
         }else if(i==2){
          checkStagingEntryPOJO.setPasswd(rs.getString("configval"));
         }else if(i==3){
          checkStagingEntryPOJO.setSmtpid(rs.getString("configval"));
         }else if(i==4){
          checkStagingEntryPOJO.setUsername(rs.getString("configval"));
         }

    i++;
     }
    return checkStagingEntryPOJO;
}catch(Exception e){
return null;

}finally{

}

}

public void sendMail(ArrayList<CheckStagingEntryPOJO> errorentry,CheckStagingEntryPOJO checkStagingEntryPOJO) throws Exception{
 boolean debug = false;
 
 try{

    //Set the host smtp address
    Properties props = new Properties();
    
    props.put("mail.smtp.host", checkStagingEntryPOJO.getSmtpid());
    props.put("mail.smtp.auth", "true");     
    // create some properties and get the default Session             
    Authenticator auth = new Authenticator()  {
        
            @Override
        public PasswordAuthentication getPasswordAuthentication() 
    {
                MsdeConnection msdeConnection;
                Connection connection=null;;
        try{        
        CheckStagingEntryPOJO checkStagingEntryPOJO=new CheckStagingEntryPOJO();
        CheckStagingEntryDO checkStagingEntryDO=new CheckStagingEntryDO();
        msdeConnection=new MsdeConnection();
        connection=msdeConnection.createConnection();
        checkStagingEntryPOJO=checkStagingEntryDO.getToEmailID(connection);
        String username = checkStagingEntryPOJO.getUsername().trim();
        String password = checkStagingEntryPOJO.getPasswd().trim();
        return new PasswordAuthentication(username,password);
        }catch(Exception e){
        return null;
        }finally{
            try{
        connection=null;
        connection.close();
        
            }catch(Exception e){}
        }
    }        
    };
        
    Session session = Session.getDefaultInstance(props,auth);        
    session.setDebug(debug);
    // create a message
    Message msg = new MimeMessage(session);
    
    // set the from and to address
    InternetAddress addressFrom = new InternetAddress(checkStagingEntryPOJO.getFromemailid().trim());
    msg.setFrom(addressFrom);
    
    InternetAddress addressTo = new InternetAddress(checkStagingEntryPOJO.getEmailid());
    msg.setRecipient(Message.RecipientType.TO, addressTo);    
    //InternetAddress[] addressTo = new InternetAddress[recipients.length]; 
    //for (int i = 0; i < recipients.length; i++)
    //{
    //    addressTo[i] = new InternetAddress(recipients[i]);
    //}
    //msg.setRecipients(Message.RecipientType.TO, addressTo);
    // Optional : You can also set your custom headers in the Email if you Want       
    msg.addHeader("MyHeaderName", "myHeaderValue");
    // Setting the Subject and Content Type
    msg.setSubject("Test Mail");  
    Multipart multipart = new MimeMultipart(); 
   
    BodyPart messageBodyPart = new MimeBodyPart(); 
    StringBuffer sb=new StringBuffer();
  //  messageBodyPart.setText(); 
 sb.append("STORECODE"+"  "+"TRANSACTIONID"+"  "+"CREATEDDATE"+"  "+"CREATEDTIME"+"\n");
 sb.append("--------------------------------------------------"+"\n");
  multipart.addBodyPart(messageBodyPart);    
 Iterator iter=errorentry.iterator();
 
while(iter.hasNext()){
checkStagingEntryPOJO  = (CheckStagingEntryPOJO) iter.next();
sb.append(""+checkStagingEntryPOJO.getStorecode()+"       ");
sb.append(""+checkStagingEntryPOJO.getTransactionid()+"     ");
sb.append(""+ConvertDate.getNumericToDate(checkStagingEntryPOJO.getCreatedDate())+"     ");
sb.append(""+ConvertDate.getformattedTimeFromTime(checkStagingEntryPOJO.getCreatedTime())+"\n");
}
 messageBodyPart.setText(sb.toString());
 multipart.addBodyPart(messageBodyPart);  
 msg.setContent(multipart);
   // DataSource source = new FileDataSource(new File("C:/tchs_resinstall.JPG"));
    
      
          
         //add the message body to the mime message 
       //  multipart.addBodyPart(messageBodyPart); 
        

   // msg.setDataHandler(new DataHandler(source));
    msg.setFileName("Testfile");
     
    
    
    Transport.send(msg);
    System.out.println("Mail send");
    
}catch(Exception e){
    e.printStackTrace();
}
}
}
   

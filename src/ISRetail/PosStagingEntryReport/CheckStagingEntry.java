/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.PosStagingEntryReport;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Administrator
 */
public class CheckStagingEntry {
ArrayList<CheckStagingEntryPOJO> errorentry;
CheckStagingEntryDO checkStagingEntryDO;
CheckStagingEntryPOJO checkStagingEntryPOJO;
MsdeConnection msdeConnection;
Connection con;
public void checkStagingEntries(){
try{
msdeConnection=new MsdeConnection();
con=msdeConnection.createConnection();  
checkStagingEntryDO=new CheckStagingEntryDO();
SiteMasterDO siteMasterDO=new SiteMasterDO();
int sysdate=siteMasterDO.getSystemDate(con);
String startdate=String.valueOf(sysdate).substring(0,6)+"01";
System.out.println("START DATE::::"+startdate);
int startingdate=Integer.parseInt(startdate);
errorentry=new ArrayList<CheckStagingEntryPOJO>();
errorentry=checkStagingEntryDO.checkCustomerEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkSalesorderEntry(con,errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkBillingEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkInquiryEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkAdvanceReceipt(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkAckEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkCashPayoutEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkQuotationEntry(con, errorentry,startingdate,sysdate);
errorentry=checkStagingEntryDO.checkSalesReturnEntry(con, errorentry,startingdate,sysdate);
System.out.println("  errorentry.size()  :================="+errorentry.size());
if(errorentry!=null&&errorentry.size()>0){
     
     checkStagingEntryPOJO=checkStagingEntryDO.getToEmailID(con);
    checkStagingEntryDO.sendMail(errorentry,checkStagingEntryPOJO);
/*Iterator iter=errorentry.iterator();
while(iter.hasNext()){
checkStagingEntryPOJO  = (CheckStagingEntryPOJO) iter.next();
System.out.println("StoreCode    :================="+checkStagingEntryPOJO.getStorecode());
System.out.println("Transactionid:================"+checkStagingEntryPOJO.getTransactionid());
System.out.println("Created Date:================"+checkStagingEntryPOJO.getCreatedDate());
System.out.println("Created Time:================"+checkStagingEntryPOJO.getCreatedTime());
}*/
}
}catch(Exception e){
e.printStackTrace();
}finally{

}

}

}

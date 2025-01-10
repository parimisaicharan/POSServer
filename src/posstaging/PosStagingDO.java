/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Helpers.ConvertDate;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import javax.swing.JTextArea;

/**
 *
 * @author sukumar
 */
public class PosStagingDO {

    /****************************************************************/
    public PosStagingPOJO getPosValue(Connection con,JTextArea textArea)throws Exception {
        PreparedStatement statement = null;
        ResultSet rs = null;
        PosStagingPOJO posStagingPOJO = null;
        SiteMasterDO siteMasterDO = new SiteMasterDO();
        StringBuffer query;
        try {
            
            //Query Modified On 03-02-2011 for changing the sequence based on time and date
          /*  textArea.append("\nGetting data to send");
            int sysDate = siteMasterDO.getSystemDate(con);
            query = new StringBuffer("select messageid,transactionid1,transactionid2,transactionid3,updatetype,indexid, ");
            query.append(" tbl_pos_staging.interfaceid,attempts,retryattempts,createddate,createdtime from tbl_pos_staging ");
            query.append(" left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid ");
            query.append(" where updatestatus = 'Waiting' and createddate= (select min(createddate)from tbl_pos_staging where updatestatus='Waiting') and createdtime=(Select min(createdtime) from tbl_pos_staging where updatestatus='Waiting') and priority = (select min(priority) from tbl_pos_staging ");
            query.append(" left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid ");
            query.append(" where updatestatus = 'Waiting' and createddate= (select min(createddate)from tbl_pos_staging where updatestatus='Waiting') and createdtime=(Select min(createdtime) from tbl_pos_staging where updatestatus='Waiting') and  attempts < retryattempts and messageid in( ");
            query.append(" select messageid from(select dateadd(minute,retryinterval,");
            query.append(" ( SELECT CONVERT(datetime,(CONVERT(varchar,lastattemptdate,112) ");
            query.append(" +' '+SUBSTRING(lastattempttime, 1, 2)+':'+SUBSTRING(lastattempttime, 3, 2)");
            query.append(" +':'+SUBSTRING(lastattempttime, 5, 2)),120))) as newtime,messageid ,lastattemptdate,");
            query.append(" lastattempttime,createddate,createdtime from dbo.tbl_pos_staging)result1 ");
            query.append(" where  (CONVERT(datetime,'" + getSysDateTime(sysDate) + "',120) > newtime and lastattemptdate= ? )  or lastattemptdate < ? ");
            query.append(" or (createddate = lastattemptdate and createdtime = lastattempttime))) order by createddate,createdtime ");*/
            //End of Query Modified On 03-02-2011 for changing the sequence based on time and date
            textArea.append("\nGetting data to send");
            int sysDate = siteMasterDO.getSystemDate(con);
            query = new StringBuffer("select messageid,transactionid1,transactionid2,transactionid3,updatetype,indexid, ");
            query.append(" tbl_pos_staging.interfaceid,attempts,retryattempts from tbl_pos_staging ");
            query.append(" left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid ");
            query.append(" where updatestatus = 'Waiting' and priority = (select min(priority) from tbl_pos_staging ");
            query.append(" left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid ");
            query.append(" where updatestatus = 'Waiting' and  attempts < retryattempts and messageid in( ");
            query.append(" select messageid from(select dateadd(minute,retryinterval,");
            query.append(" ( SELECT CONVERT(datetime,(CONVERT(varchar,lastattemptdate,112) ");
            query.append(" +' '+SUBSTRING(lastattempttime, 1, 2)+':'+SUBSTRING(lastattempttime, 3, 2)");
            query.append(" +':'+SUBSTRING(lastattempttime, 5, 2)),120))) as newtime,messageid ,lastattemptdate,");
            query.append(" lastattempttime,createddate,createdtime from dbo.tbl_pos_staging)result1 ");
            query.append(" where  (CONVERT(datetime,'" + getSysDateTime(sysDate) + "',120) > newtime and lastattemptdate= ? )  or lastattemptdate < ? ");
            query.append(" or (createddate = lastattemptdate and createdtime = lastattempttime))) order by createddate,createdtime ");
            
            
            
            //new query-Eg
              /*  select messageid,transactionid1,transactionid2,transactionid3,updatetype,indexid,  
            tbl_pos_staging.interfaceid,attempts,retryattempts,createddate,createdtime from tbl_pos_staging  
            left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid  
            where updatestatus = 'Waiting' and priority = (select min(priority) from tbl_pos_staging 
            left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid 
            where updatestatus = 'Waiting' and  attempts < retryattempts and messageid 
            in(  select messageid from(select dateadd(minute,retryinterval, ( SELECT CONVERT(datetime,(CONVERT(varchar,
            lastattemptdate,112)  +' '+SUBSTRING(lastattempttime, 1, 2)+':'+SUBSTRING(lastattempttime, 3, 2) +':'+SUBSTRING
            (lastattempttime, 5, 2)),120))) as newtime,messageid ,lastattemptdate, lastattempttime,createddate,createdtime 
            from dbo.tbl_pos_staging)result1  where  (CONVERT(datetime,'200919 16:52:21',120) > newtime and lastattemptdate= 200919 )
            or lastattemptdate < 200919  or (createddate = lastattemptdate and createdtime = lastattempttime)) ) order by createddate,createdtime 
             */
            //OLD QUERY//statement = con.prepareStatement("select messageid,transactionid1,transactionid2,transactionid3,updatetype,indexid,tbl_pos_staging.interfaceid,attempts,retryattempts from tbl_pos_staging left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid where updatestatus = 'Waiting' and priority = (select min(priority) from tbl_pos_staging left outer join tbl_pos_stagingmaster on tbl_pos_staging.interfaceid=tbl_pos_stagingmaster.interfaceid where updatestatus = 'Waiting' and ((lastattempttime+(retryinterval*100) < ? and lastattemptdate = ?  ) or lastattemptdate < ? or (createddate = lastattemptdate and createdtime = lastattempttime)) and attempts < retryattempts) order by createddate,createdtime");
            statement = con.prepareStatement(query.toString());
            System.out.println("query::"+query.toString());
            statement.setInt(1, sysDate);
            statement.setInt(2, sysDate);
            rs = statement.executeQuery();
            if (rs.next()) {
                posStagingPOJO = new PosStagingPOJO();
                posStagingPOJO.setMessageID(rs.getString("messageid"));
                posStagingPOJO.setTransactionID(rs.getString("transactionid1"));
                posStagingPOJO.setTransactionID2(rs.getString("transactionid2"));
                posStagingPOJO.setTransactionID3(rs.getString("transactionid3"));
                posStagingPOJO.setUpdateType(rs.getString("updatetype"));
                posStagingPOJO.setIndexID(rs.getString("indexid"));
                posStagingPOJO.setInterfaceID(rs.getString("interfaceid"));
                posStagingPOJO.setNoOfAttempts(rs.getInt("attempts"));
                posStagingPOJO.setRetryAttempts(rs.getInt("retryattempts"));
                textArea.append("\nData Retrieved..");
            }
        } catch (Exception e) {
             textArea.append("\nException occured while retrieving data to send : "+e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            statement = null;
            rs = null;
            siteMasterDO = null;
            query = null;
        }
        return posStagingPOJO;
    }

    public int updateStatus(Connection con, PosStagingPOJO posStagingPOJO) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update  tbl_pos_staging set updatestatus=?,attempts=?,lastattemptdate=?,lastattempttime=? where messageid = ?");
            pstmt.setString(1, posStagingPOJO.getUpdateStatus());
            pstmt.setInt(2, posStagingPOJO.getNoOfAttempts());
            pstmt.setInt(3, posStagingPOJO.getLastAttemptDate());
            pstmt.setString(4, posStagingPOJO.getLastAttemptTime());
            pstmt.setString(5, posStagingPOJO.getMessageID());
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    /************************************** METHOD TO GET TRANSACTION IDS BASED ON INTERFACE ID       ***************************************/
  public ArrayList<PosStagingPOJO> getTrasactionIds(Connection con, String interfaceId, String updateType) {
        PreparedStatement statement = null;
        ResultSet rs,rs1 = null;
        ArrayList<PosStagingPOJO> posStagingPOJOs = null;
        PosStagingPOJO posStagingPOJO = null;
        int i = 0;
        String query = "";
        try {
            statement = con.prepareStatement("select configval from tbl_pos_configparam where configkey = '"+interfaceId+"'");
            rs1 = statement.executeQuery();
            if(rs1.next()){
                 query = "top "+""+rs1.getString("configval");
            }
            if (Validations.isFieldNotEmpty(updateType)) {
                statement = con.prepareStatement("select "+query+" * from dbo.tbl_pos_staging where interfaceid = ? and (updatetype = '" + updateType + "' ) and (sapidstatus = 'N' or sapidstatus = 'X') and updatestatus = 'send'");
            } else {
                statement = con.prepareStatement("select "+query+" * from dbo.tbl_pos_staging where interfaceid = ?  and (sapidstatus = 'N' or sapidstatus = 'X') and updatestatus = 'send'");
            }
            
            statement.setString(1, interfaceId);
            rs = statement.executeQuery();
            while (rs.next()) {
                if (i == 0) {
                    posStagingPOJOs = new ArrayList<PosStagingPOJO>();
                }
                posStagingPOJO = new PosStagingPOJO();
                posStagingPOJO.setMessageID(rs.getString("messageid"));
                posStagingPOJO.setTransactionID(rs.getString("transactionid1"));
                posStagingPOJO.setTransactionID2(rs.getString("transactionid2"));
                posStagingPOJO.setTransactionID3(rs.getString("transactionid3"));
                posStagingPOJO.setUpdateType(rs.getString("updatetype"));
                posStagingPOJO.setIndexID(rs.getString("indexid"));
                posStagingPOJO.setInterfaceID(rs.getString("interfaceid"));
                posStagingPOJO.setNoOfAttempts(rs.getInt("attempts"));
                posStagingPOJO.setTid_fiscalyear(rs.getInt("tid1_fiscalyear"));
                posStagingPOJO.setTid_storecode(rs.getString("tid1_storecode"));
                posStagingPOJO.setTid1_fiscalyear(rs.getInt("tid2_fiscalyear"));
                posStagingPOJO.setTid1_storecode(rs.getString("tid2_storecode"));
                posStagingPOJO.setTid2_fiscalyear(rs.getInt("tid3_fiscalyear"));
                posStagingPOJO.setTid2_storecode(rs.getString("tid3_storecode"));
                posStagingPOJOs.add(posStagingPOJO);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            rs = null;
            posStagingPOJO = null;
        }
        return posStagingPOJOs;
    }

    /************************************** METHOD TO GET TRANSACTION IDS BASED ON INTERFACE ID       ***************************************/
    public ArrayList<PosStagingPOJO> getAllTrasactionIds_NotCompletedInISR(Connection con) {
        PreparedStatement statement = null;
        ResultSet rs = null;
        ArrayList<PosStagingPOJO> posStagingPOJOs = null;
        PosStagingPOJO posStagingPOJO = null;
        int i = 0;
        try {
            statement = con.prepareStatement("select * from tbl_pos_staging where  (sapidstatus = 'N' or sapidstatus = 'X') and updatestatus = 'send'");
            rs = statement.executeQuery();
            while (rs.next()) {
                if (i == 0) {
                    posStagingPOJOs = new ArrayList<PosStagingPOJO>();
                }
                posStagingPOJO = new PosStagingPOJO();
                posStagingPOJO.setMessageID(rs.getString("messageid"));
                posStagingPOJO.setTransactionID(rs.getString("transactionid1"));
                posStagingPOJO.setTransactionID2(rs.getString("transactionid2"));
                posStagingPOJO.setTransactionID3(rs.getString("transactionid3"));
                posStagingPOJO.setUpdateType(rs.getString("updatetype"));
                posStagingPOJO.setIndexID(rs.getString("indexid"));
                posStagingPOJO.setInterfaceID(rs.getString("interfaceid"));
                posStagingPOJO.setNoOfAttempts(rs.getInt("attempts"));
                posStagingPOJO.setTid_fiscalyear(rs.getInt("tid1_fiscalyear"));
                posStagingPOJO.setTid_storecode(rs.getString("tid1_storecode"));
                posStagingPOJO.setTid1_fiscalyear(rs.getInt("tid2_fiscalyear"));
                posStagingPOJO.setTid1_storecode(rs.getString("tid2_storecode"));
                posStagingPOJO.setTid2_fiscalyear(rs.getInt("tid3_fiscalyear"));
                posStagingPOJO.setTid2_storecode(rs.getString("tid3_storecode"));
                posStagingPOJO.setCreatedDate(rs.getInt("createddate"));
                posStagingPOJO.setCreatedTime(rs.getString("createdtime"));
                posStagingPOJOs.add(posStagingPOJO);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            rs = null;
            posStagingPOJO = null;
        }
        return posStagingPOJOs;
    }

    public int updateSapIdStatus(Connection con, String sapIdStatus, String messageId) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update  tbl_pos_staging set sapidstatus = ? where messageid = ?");
            pstmt.setString(1, sapIdStatus);
            pstmt.setString(2, messageId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public ArrayList<PosStagingPOJO> getStagingTableStaus(Connection con, String searchstatement) throws SQLException {
        Statement statement = null;
        PosStagingPOJO stagingPOJO;
        ResultSet rs;
        try {
            ArrayList<PosStagingPOJO> stagingList = new ArrayList<PosStagingPOJO>();
            statement = con.createStatement();
            rs = statement.executeQuery(searchstatement);
            while (rs.next()) {
                stagingPOJO = new PosStagingPOJO();
                stagingPOJO.setMessageID(rs.getString("messageid"));
                stagingPOJO.setCreatedBy(rs.getString("createdby"));
                stagingPOJO.setCreatedDate(rs.getInt("createddate"));
                stagingPOJO.setCreatedTime(rs.getString("createdtime"));
                stagingPOJO.setUpdateType(rs.getString("updatetype"));
                stagingPOJO.setInterfaceID(rs.getString("interfaceid"));
                stagingPOJO.setInterfaceName(rs.getString("interfacename"));
                stagingPOJO.setLastAttemptDate(rs.getInt("lastattemptdate"));
                stagingPOJO.setLastAttemptTime(rs.getString("lastattempttime"));
                stagingPOJO.setNoOfAttempts(rs.getInt("attempts"));
                stagingPOJO.setUpdateStatus(rs.getString("updatestatus"));
                stagingPOJO.setSapIdStatus(rs.getString("sapidstatus"));
                stagingPOJO.setTransactionID(rs.getString("transactionid1"));
                stagingPOJO.setTransactionID2(rs.getString("transactionid2"));
                stagingPOJO.setTransactionID3(rs.getString("transactionid3"));
                stagingList.add(stagingPOJO);

            }
            return stagingList;

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            statement.close();
            statement = null;
            rs = null;
            searchstatement = null;
        }
    }

    public int updateStagingTableToReinitiateManually(Connection con, String messageId) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            //pstmt = con.prepareStatement("update  tbl_pos_staging set attempts = 0 , updatestatus='Waiting' where messageid = ?");
            pstmt = con.prepareStatement("update  tbl_pos_staging set attempts = 0 , updatestatus='Waiting' where messageid = ? and updatestatus = 'Cancelled'");
            pstmt.setString(1, messageId);
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updateStagingTableToAutoReinitiate(Connection con, int sysdate, int freq, String unitOfFreq) {
        int result = 0;
        String sysDateTime = null;
        StringBuffer query = null;
        try {
            //PreparedStatement pstmt = con.prepareStatement("update  tbl_pos_staging set attempts = 0 , updatestatus='Waiting' where updatestatus='Cancelled' and ? > lastattemptdate");
            //PreparedStatement pstmt = con.prepareStatement("update  tbl_pos_staging set attempts = 0 , updatestatus='Waiting' where updatestatus='Cancelled' and ? > lastattempttime + ? ");
            sysDateTime = getSysDateTime(sysdate);
            query = new StringBuffer("update tbl_pos_staging set updatestatus='Waiting', attempts=0  ");
            //query.append("where updatestatus='Cancelled' and messageid in ");
            query.append("where updatestatus='Cancelled' and ");
            query.append("datepart(year,convert(varchar,lastattemptdate))= datepart(year,getdate()) and datepart(month,convert(varchar,lastattemptdate))= datepart(month,getdate()) and messageid in ");
            query.append("(select messageid    from ( select dateadd(" + unitOfFreq + "," + freq + ",");//dateadd(minute,10,'20081224 18:15:4')
            query.append("( SELECT CONVERT(datetime,(CONVERT(varchar,lastattemptdate,112) +' '+"); // 112 is the convert format yyyyymmdd
            query.append("SUBSTRING(lastattempttime, 1, 2)+':'+SUBSTRING(lastattempttime, 3, 2)+':'+");
            query.append("SUBSTRING(lastattempttime, 5, 2)),120))) as newtime,dbo.tbl_pos_staging.messageid ");//120 is the convert format yyyy-mm-dd hh:mi:ss(24h) or yyyymmdd hh:mi:ss(24h)
            query.append("from dbo.tbl_pos_staging) result1 where CONVERT(datetime,'" + sysDateTime + "',120) > newtime )  ");//120 is the convert format yyyy-mm-dd hh:mi:ss(24h) or yyyymmdd hh:mi:ss(24h)
            //eg: query will be like this- update tbl_pos_staging set updatestatus='Waiting', attempts=0  where updatestatus='Cancelled' and  messageid in (select messageid    from ( select dateadd(minute,120,( SELECT CONVERT(datetime,(CONVERT(varchar,lastattemptdate,112) +' '+SUBSTRING(lastattempttime, 1, 2)+':'+SUBSTRING(lastattempttime, 3, 2)+':'+SUBSTRING(lastattempttime, 5, 2)),120))) as newtime,dbo.tbl_pos_staging.messageid from dbo.tbl_pos_staging) result1 where CONVERT(datetime,'20081224 18:15:4',120) > newtime )  
            PreparedStatement pstmt = con.prepareStatement(query.toString());
            result = pstmt.executeUpdate();
//            System.out.println("*************updateStagingTableToAutoReinitiate**************"+result+"---query ----"+query.toString());
           // System.out.println("ARUN--Freq-----"+freq+"--->sysdatetime------>"+sysDateTime+"--->result---->"+query);
            System.out.println("result---->"+query);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sysDateTime = null;
            query = null;
        }
        return result;
    }

    public String getSysDateTime(int baseDate) {
        String newTime = "";
        Calendar cal, baseTime1;
        java.util.Date baseDate1;
        try {
            if (baseDate != 0) {
                cal = Calendar.getInstance();
                baseTime1 = Calendar.getInstance();
                baseDate1 = ConvertDate.getUtilDateFromNumericDate(baseDate);
                cal.setTime(baseDate1);
                cal.set(Calendar.HOUR_OF_DAY, baseTime1.get(Calendar.HOUR_OF_DAY));
                cal.set(Calendar.MINUTE, baseTime1.get(Calendar.MINUTE));
                cal.set(Calendar.SECOND, baseTime1.get(Calendar.SECOND));
                String month = "", day, hour, min, sec;
                if ((cal.get(Calendar.MONTH) + 1) < 10) {
                    month = "0" + (cal.get(Calendar.MONTH) + 1);
                } else {
                    month = "" + (cal.get(Calendar.MONTH) + 1);
                }
                if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
                    day = "0" + (cal.get(Calendar.DAY_OF_MONTH));
                } else {
                    day = "" + (cal.get(Calendar.DAY_OF_MONTH));
                }
                if (cal.get(Calendar.HOUR_OF_DAY) < 10) {
                    hour = "0" + (cal.get(Calendar.HOUR_OF_DAY));
                } else {
                    hour = "" + (cal.get(Calendar.HOUR_OF_DAY));
                }
                if (cal.get(Calendar.MINUTE) < 10) {
                    min = "0" + (cal.get(Calendar.MINUTE));
                } else {
                    min = "" + (cal.get(Calendar.MINUTE));
                }
                if (cal.get(Calendar.SECOND) < 10) {
                    sec = "0" + (cal.get(Calendar.SECOND));
                } else {
                    sec = "" + (cal.get(Calendar.SECOND));
                }
                newTime = cal.get(Calendar.YEAR) + "" + month + "" + day + " " + hour + ":" + min + ":" + sec;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cal = null;
            baseTime1 = null;
            baseDate1 = null;
        }
        return newTime;

    }
    //Code added on June 23rd 2010 for Invoice Creation after cancellation Problem
    public boolean AllowInvoiceCreation(Connection con, String searchstatement) throws SQLException {
        Statement statement = null;
         String updstatus;String sapstatus;
         boolean allowinv=true;int allow=0;
        ResultSet rs;
        try {
            statement = con.createStatement();
            rs = statement.executeQuery(searchstatement);
            while (rs.next()) {
                
                updstatus=rs.getString("updatestatus");
                sapstatus=rs.getString("sapidstatus");
                if(!updstatus.equalsIgnoreCase("Send")|| !sapstatus.equalsIgnoreCase("C")){
                allow++;
                }
            
            }
           if(allow>0){
           allowinv=false;
           }else{
           allowinv=true;
           }
          return allowinv;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return true;
        } finally {
            statement.close();
            statement = null;
            rs = null;
            searchstatement = null;
        }
    }
  public int updateStagingTableToSendInvoice(Connection con, String invnumber,String SoNumber) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update tbl_pos_staging set updatestatus='Waiting',sapidstatus='N' where transactionid1=(select invoicecancellationno from tbl_billcancelheader where invoiceno=select invoicecancellationno from tbl_billcancelheader where invoiceno=(select  top 1 invoiceno from tbl_billingheader where invoiceno != '"+invnumber+"' and invoiceno<'"+invnumber+"' and refno='"+SoNumber+"' order by invoiceno desc))");
         
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }
   
  //Code added on January 29th 2012 for Invoice Creation after cancellation Problem
   public boolean sendInvoiceCreationAfterCancellation(Connection con, String invnumber) {
        boolean result = true;
        Statement stmt=null;
        ResultSet rs=null;
        StringBuilder qry=new StringBuilder("");
        try {
            stmt = con.createStatement();
            qry.append("select top 1 invoicecancellationno from tbl_billcancelheader a inner join ( ");
            qry.append(" select invoiceno ,createddate ,createdtime from tbl_billingheader where refno in(select refno from tbl_billingheader where invoiceno like '"+invnumber+"') and invoiceno not like '"+invnumber+"' ");
            qry.append("  ) b on b.invoiceno=a.invoiceno ");
            qry.append(" where  len(a.saprefno)<=0  and SUBSTRING(CONVERT(varchar,a.createddate),0,7) in (SELECT SUBSTRing(replace(convert(varchar, getdate(),111),'/',''),0,7) ) ");
            qry.append("  order by invoicecancellationno desc");

            System.out.println("sendInvoiceCreationAfterCancellation qry"+qry.toString());
            rs=stmt.executeQuery(qry.toString());
            while(rs.next()){
                result=false;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return true;
        } finally {
            stmt = null;
            rs=null;
        }
        return result;
    }

   public boolean getPendingTransOnTheDay(Connection con) {
        boolean result = false;
        Statement stmt=null;
        ResultSet rs=null;
        try {
            stmt = con.createStatement();
            int currentDate = ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy");
            rs=stmt.executeQuery("select * from tbl_pos_staging where (updatestatus in ('Waiting','Cancelled') or sapidstatus in ('N','X')) and createddate = " +currentDate );
            while(rs.next()){
                result=true;
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return true;
        } finally {
            stmt = null;
            rs=null;
        }
        return result;
    }
    
   public String getsourcelocation(Connection con){
       String sourcelocation = "";
       Statement stmt = null;
       ResultSet rs = null;
       try{
           stmt = con.createStatement();
           rs = stmt.executeQuery("select filelocation from tbl_ftp_filepath");
           if(rs.next()){
               sourcelocation = rs.getString("filelocation");
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       
       
       return sourcelocation;
   }
   
   public HashMap getFilePath(Connection con){
       HashMap<String,String> filepath = new HashMap<String, String>();
       Statement stmt = null;
       ResultSet rs = null;
       try{
           stmt = con.createStatement();
           rs = stmt.executeQuery("SELECT filename,dest_location from tbl_ftp_filepath");
           while(rs.next()){
               filepath.put(rs.getString("filename"), rs.getString("dest_location"));
           }
       }catch(Exception e){
           e.printStackTrace();
       }
       
       return filepath;
   }
    
}

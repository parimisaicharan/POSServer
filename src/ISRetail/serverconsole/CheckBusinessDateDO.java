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
 * Data Object for SiteMaster 
 * 
 */
package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.NetConnection;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTextArea;
import posstaging.CheckSystemDate;

public class CheckBusinessDateDO {

    public int getLastBusinessdate(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        int lastbusinessdate = 0;
        try {
            if (conn != null) {
                pstmt = conn.prepareStatement("select postingdate from tbl_sitemaster");
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    lastbusinessdate = rs.getInt(1);
                    // System.out.println("sitedateinfo check -- getLastBusinessdate -resultset lastbusinessdate---"+lastbusinessdate);
                    return lastbusinessdate;
                } else {
                    // System.out.println("sitedateinfo check -- getLastBusinessdate -no rows returned -lastbusinessdate---"+lastbusinessdate);
                    return 0;
                }
            } else {
                //  System.out.println("sitedateinfo check -- getLastBusinessdate -conn null -- lastbusinessdate---"+lastbusinessdate);
                return 0;
            }
        } catch (SQLException sQLException) {
            //System.out.println("sitedateinfo check -- getLastBusinessdate -catch block--lastbusinessdate---"+lastbusinessdate);
            // System.out.println("Exception time---"+DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date()));
            sQLException.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public int getLastSyncdate(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select syncdate from tbl_syncdate");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                int lastsyndate = rs.getInt(1);
                return lastsyndate;
            } else {
                return 0;
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return 0;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public String getCheckBlock(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select postingindicator from dbo.tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

                String blockindicator = rs.getString("postingindicator");

                return blockindicator;
            } else {
                return null;
            }


        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public SiteMasterPOJO getCheckSiteBlock(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        SiteMasterPOJO siteMasterPOJO = new SiteMasterPOJO();
        try {
            pstmt = conn.prepareStatement("select siteid,deletionind from dbo.tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {

                // String blockindicator = rs.getString("deletionind");
                siteMasterPOJO.setSiteid(rs.getString("siteid"));
                siteMasterPOJO.setDeletionInd(rs.getString("deletionind"));
                return siteMasterPOJO;
            } else {
                return null;
            }


        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public String getTransactionPwd(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select transaction_pwd from dbo.tbl_sitemaster");
            rs = pstmt.executeQuery();
            if (rs.next()) {
               //CODE String transaction_password = Validations.passWordDecrypt(rs.getString("transaction_pwd"));   
                String transaction_password = rs.getString("transaction_pwd");                                      //Change by jyoti Nev_03_2017
                return transaction_password;
            } else {
                return null;
            }


        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return null;
        } finally {
            pstmt = null;
            rs = null;
        }
    }
    //******************** METHOD TO THE UPDATE TRANSACTION PASSWORD Created by jyoti Nev_03_2017
    public void updateEncriptTransactionPwd(Connection connection,String passWord) {
        
        PreparedStatement statement = null;
        
        String query = null;
        try {
            query = "update tbl_sitemaster set transaction_pwd= ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, Validations.passWordEncription(passWord));
            int result = statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            statement = null;
            query = null;
        }        
    }

    public int updateSystemAndPostingDates(Connection con, int businessDate) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            System.out.println("updated successfully");
            if (businessDate != 0) {
                pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, businessDate);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updateSystemAndPostingDateswonetcn(Connection con, int businessDate, int systemdate) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            //System.out.println("sitedateinfo check -- updateSystemAndPostingDateswonetcn -ISRPostingdate---"+businessDate);
            System.out.println("updated successfully");
            if (businessDate != 0) {
                pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, systemdate);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            //   System.out.println("sitedateinfo check -- updateSystemAndPostingDateswonetcn -catch block--ISRPostingdate---"+businessDate);
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updateSystemPostingdatefiscalyrblock(Connection con, int businessDate, int systemdate, int fiscalyr, String blockind) {
        int result = 0;
        PreparedStatement pstmt;
        try {

            //  System.out.println("sitedateinfo check -- updateSystemPostingdatefiscalyrblock --ISRPostingdate---"+businessDate);
            if (businessDate != 0) {
                if (fiscalyr != 0) {
                    pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?,fiscalyear=?,postingindicator=?");
                    pstmt.setInt(1, businessDate);
                    pstmt.setInt(2, systemdate);
                    //if(fiscalyr!=0){Added on Dec 7th 09 to stop fiscal yr getting zero
                    pstmt.setInt(3, fiscalyr);
                    //}else{int fyr=Integer.parseInt(String.valueOf(businessDate).substring(0,4));
                    //   System.out.println("Fiscalyear:"+Integer.parseInt(String.valueOf(businessDate).substring(0,4)));
                    //if(Integer.parseInt(String.valueOf(businessDate).substring(4,6)<=4){
                    //   System.out.println("Month:"+Integer.parseInt(String.valueOf(businessDate).substring(4,6)));
                    //pstmt.setInt(3,fyr);
                    //}else{
                    //pstmt.setInt(3,fyr+1);
                    //}
                    //}
                    pstmt.setString(4, blockind);

                    result = pstmt.executeUpdate();
                    System.out.println("updated all values successfully");
                } else {
                    pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?,systemdate=?,postingindicator=?");
                    pstmt.setInt(1, businessDate);
                    pstmt.setInt(2, systemdate);



                    //pstmt.setInt(3,fyr);
                    //}else{
                    //pstmt.setInt(3,fyr+1);
                    //}
                    //}
                    pstmt.setString(3, blockind);

                    result = pstmt.executeUpdate();
                    System.out.println("updated all values successfully without fyear");

                }
            }
        } catch (Exception e) {
            //  System.out.println("sitedateinfo check -- updateSystemPostingdatefiscalyrblock -catch block--ISRPostingdate---"+businessDate);
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updatePostingDates(Connection con, int businessDate) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            System.out.println("updated successfully");
            if (businessDate != 0) {
                pstmt = con.prepareStatement("update tbl_sitemaster set postingdate=?");
                pstmt.setInt(1, businessDate);
                //pstmt.setInt(2, businessDate);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            // System.out.println("sitedateinfo check -- updatePostingDates -catch block--ISRPostingdate---"+businessDate);
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int updatePostingIndicator(Connection con, String postingind) {
        int result = 0;
        PreparedStatement pstmt;
        try {
            pstmt = con.prepareStatement("update tbl_sitemaster set postingindicator=?");
            pstmt.setString(1, postingind);
            result = pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public void updateblockindfromISR(Connection con) {
        CheckBusinessDateDO checkBusinessDateDO;
        CheckSystemDate checkSystemDate;
        try {
            checkBusinessDateDO = new CheckBusinessDateDO();
            checkSystemDate = new CheckSystemDate();
            checkSystemDate.getBusinessDateFromWebservice();
            String ISRBlkind = checkSystemDate.getPostingindicator();
            checkBusinessDateDO.updatePostingIndicator(con, ISRBlkind);
        } catch (Exception ex) {
        } finally {
            checkBusinessDateDO = null;
            checkSystemDate = null;
        }
    }

    public boolean getBusinessdatafromISR(Connection con) {
        CheckBusinessDateDO checkBusinessDateDO;
        CheckSystemDate checkSystemDate;
        String ISRBlkind;
        try {
            checkSystemDate = new CheckSystemDate();
//            if (checkSystemDate.catchSoapfaultError()) {
//                return true;
//            } else {
//                return false;
//            }
            if (Validations.isFieldNotEmpty(checkSystemDate.getPostingindicator()) && checkSystemDate.getPostingindicator().equalsIgnoreCase("X")) {
                return false;
            }else{
                return true;
            }
        } catch (Exception ex) {
            return false;
        } finally {
            checkBusinessDateDO = null;
            checkSystemDate = null;
            ISRBlkind = null;
        }
    }

    public void updatewithmanualdatewithInetconn(Connection con, int businessdate, int systemdate, int fisyr, String blockind) {
        CheckBusinessDateDO checkBusinessDateDO;
        try {
            checkBusinessDateDO = new CheckBusinessDateDO();
            //checkBusinessDateDO.updateSystemPostingdatefiscalyrblock(con, businessdate, systemdate, fisyr, blockind);
            updateSystemPostingdatefiscalyrblock(con, businessdate, systemdate, fisyr, blockind);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            checkBusinessDateDO = null;
        }
    }

    public void updatewithmanualdatewoInetconn(Connection con, int businessdate, int systemdate) {
        CheckBusinessDateDO checkBusinessDateDO;
        try {
            checkBusinessDateDO = new CheckBusinessDateDO();
            checkBusinessDateDO.updateSystemAndPostingDateswonetcn(con, businessdate, systemdate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            checkBusinessDateDO = null;
        }
    }

    public void updateonlybusinessdate(Connection con, String businessdate) {
        CheckBusinessDateDO checkBusinessDateDO;
        try {
            checkBusinessDateDO = new CheckBusinessDateDO();
            int budate = ConvertDate.getDateNumeric(businessdate, "dd/MM/yyyy");
            System.out.println("mmdate" + budate);
            checkBusinessDateDO.updatePostingDates(con, budate);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            checkBusinessDateDO = null;
        }
    }

    public void updatewithmanualdateauditfields(Connection con, int businessdate, String manualdate) {
        CheckBusinessDateDO checkBusinessDateDO;
        DateFormat dateFormat;
        try {
            checkBusinessDateDO = new CheckBusinessDateDO();
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new java.util.Date();
            checkBusinessDateDO.updateManualDateAudit(con, businessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdate, "dd/MM/yyyy"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            checkBusinessDateDO = null;
            dateFormat = null;
        }
    }

    public boolean isfiscalyearpresent(int fiscalyear, Connection con) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("select fiscalyear from tbl_posdoclastnumbers");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (fiscalyear == rs.getInt("fiscalyear")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    public String getCorPPass(String ss, String scode) {

        char s[] = ss.toCharArray();
        char c;
        char spl = '#';
        int sum = 0;
        int i;
        int ascii;
        for (i = 0; i < s.length; i++) {
            c = s[i];
            if (Character.isDigit(c)) {
                sum = sum + Integer.parseInt(String.valueOf(c));
            }
        }


        java.util.Date tdate = ConvertDate.getStringToDate(ss, "dd/MM/yyyy");


        int sindex = 0;
        int cindex = 0;

        if (tdate.toString().indexOf("Sun") != -1) {
            sindex = 1;
            cindex = 64 + sindex;
            spl = '#';
        } else if (tdate.toString().indexOf("Mon") != -1) {
            sindex = 2;
            cindex = 96 + sindex;
            spl = '#';
        } else if (tdate.toString().indexOf("Tue") != -1) {
            sindex = 3;
            cindex = 64 + sindex;
            spl = '$';
        } else if (tdate.toString().indexOf("Wed") != -1) {
            sindex = 4;
            cindex = 96 + sindex;
            spl = '$';
        } else if (tdate.toString().indexOf("Thu") != -1) {
            sindex = 5;
            cindex = 64 + sindex;
            spl = '@';
        } else if (tdate.toString().indexOf("Fri") != -1) {
            sindex = 6;
            cindex = 96 + sindex;
            spl = '@';
        } else if (tdate.toString().indexOf("Sat") != -1) {
            sindex = 7;
            cindex = 64 + sindex;
            spl = '*';
        }





        StringBuffer sb = new StringBuffer();

        ascii = (int) (scode.toLowerCase().charAt(1));
        sb.append(ascii);
        ascii = (int) (scode.toUpperCase().charAt(2));
        sb.append(ascii);
        ascii = (int) (scode.toLowerCase().charAt(3));
        sb.append(ascii);
        String bnum = sb.toString().trim();
        int num = Integer.parseInt(bnum);
        num = num * (sum / sindex);

        sb = new StringBuffer();

        sb.append(num);
        sb.append((char) cindex);
        sb.append(spl);
        //sb.append(sindex);                      
        return sb.toString().trim();

    }

    public boolean performConnectionChecking(JTextArea statusArea) {
        boolean valid = true;
        try {
            // statusArea.append("\nChecking for Network Connection ....\n");
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3 || internetconnection == 2) {
                valid = false;
                // statusArea.append("\nDownload Failed : Check Your Network for Internet Connection !");
            } else {
                // statusArea.append("\nChecking PI Server is Reachable....\n");
                internetconnection = NetConnection.checkXIConnection();
                if (internetconnection == 2) {
                    valid = false;
                    //    statusArea.append("\nFailed : Incorrect Server Address : Contact Administrator to Fix the Problem!");
                } else if (internetconnection == 3 || internetconnection == 4) {
                    valid = false;
                    //    statusArea.append("\nFailed : Unknown Error : Contact Administrator to Fix the Problem!");
                }
            }
        } catch (Exception e) {
            valid = false;
            // statusArea.append("\nFailed : Exception : " + e);
        } finally {
        }
        return valid;
    }

    public int updateManualDateAudit(Connection con, int businessDate, int systemdate, int manualdate) {
        int result = 0;
        String currenttime = ConvertDate.getCurrentTimeToString();
        PreparedStatement pstmt;
        try {
            System.out.println("Audit fields updated successfully");
            if (businessDate != 0) {
                pstmt = con.prepareStatement("insert into tbl_manualdate values(?,?,?,?)");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, systemdate);
                pstmt.setString(3, currenttime);
                pstmt.setInt(4, manualdate);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public int insertManualDateAudit(Connection con, int businessDate, int systemdate, int manualdate, String selectiontype) {
        int result = 0;
        String currenttime = ConvertDate.getCurrentTimeToString();
        PreparedStatement pstmt;
        try {
            System.out.println("Audit fields updated successfully");
            if (businessDate != 0) {
                pstmt = con.prepareStatement("insert into tbl_manualdate_audit values(?,?,?,?,?)");
                pstmt.setInt(1, businessDate);
                pstmt.setInt(2, systemdate);
                pstmt.setString(3, currenttime);
                pstmt.setInt(4, manualdate);
                pstmt.setString(5, selectiontype);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }

    public String getCorporatePassword(String ds, String storecode) {
        char s[] = ds.toCharArray();
        char c;
        char spl = '#';
        int sum = 0;
        int i;
        int ascii;
        for (i = 0; i < s.length; i++) {
            c = s[i];
            if (Character.isDigit(c)) {
                sum = sum + Integer.parseInt(String.valueOf(c));
            }
        }
        java.util.Date tdate = ConvertDate.getStringToDate(ds, "dd/MM/yyyy");

        Calendar cal = Calendar.getInstance();
        cal.setTime(tdate);
        int dyyear = cal.get(Calendar.DAY_OF_YEAR);
        sum = sum + dyyear;

        System.out.println(sum);
        int sindex = 0;
        int cindex = 0;
        if (tdate.toString().indexOf("Sun") != -1) {
            sindex = 1;
            cindex = 64 + sindex;
            spl = '#';
        } else if (tdate.toString().indexOf("Mon") != -1) {
            sindex = 2;
            cindex = 96 + sindex;
            spl = '#';
        } else if (tdate.toString().indexOf("Tue") != -1) {
            sindex = 3;
            cindex = 64 + sindex;
            spl = '$';
        } else if (tdate.toString().indexOf("Wed") != -1) {
            sindex = 4;
            cindex = 96 + sindex;
            spl = '$';
        } else if (tdate.toString().indexOf("Thu") != -1) {
            sindex = 5;
            cindex = 64 + sindex;
            spl = '@';
        } else if (tdate.toString().indexOf("Fri") != -1) {
            sindex = 6;
            cindex = 96 + sindex;
            spl = '@';
        } else if (tdate.toString().indexOf("Sat") != -1) {
            sindex = 7;
            cindex = 64 + sindex;
            spl = '*';
        }
        StringBuffer sb = new StringBuffer();
        ascii = (int) (storecode.toLowerCase().charAt(1));
        sb.append(ascii);
        ascii = (int) (storecode.toUpperCase().charAt(2));
        sb.append(ascii);
        ascii = (int) (storecode.toLowerCase().charAt(3));
        sb.append(ascii);
        String bnum = sb.toString().trim();
        int num = Integer.parseInt(bnum);
        num = num / sindex;
        System.out.println(num);
        num = num * sum;
        System.out.println(num);
        sb = new StringBuffer();
        sb.append(num);
        sb.append((char) cindex);
        sb.append(spl);
//        System.out.println(sb.toString().trim());//Removed by Dileep - 20.10.2014
        return sb.toString().trim();
    }
    /*To check if flag has come from ISR in startup of server*/

    public boolean checkatserverstart(Connection conn) {
        PreparedStatement pstmt;
        ResultSet rs;
        try {
            pstmt = conn.prepareStatement("select startupdate from dbo.tbl_posstartup");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }


        } catch (SQLException sQLException) {

            sQLException.printStackTrace();
            return false;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public int insertposstartupdetails(Connection con, int businessDate) {
        int result = 0;
        String currenttime = ConvertDate.getCurrentTimeToString();
        PreparedStatement pstmt;
        try {

            if (businessDate != 0) {
                pstmt = con.prepareStatement("insert into tbl_posstartup values(?,?)");
                pstmt.setInt(1, businessDate);
                pstmt.setString(2, currenttime);
                result = pstmt.executeUpdate();
                System.out.println("updated successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt = null;
        }
        return result;
    }
}

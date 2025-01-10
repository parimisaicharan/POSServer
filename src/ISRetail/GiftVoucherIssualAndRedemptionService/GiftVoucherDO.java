/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.GiftVoucherIssualAndRedemptionService;

import ISRetail.Helpers.NetConnection;
import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.utility.db.PopulateData;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ewitsupport
 */
public class GiftVoucherDO {

    public GiftVoucherPojo getGiftVoucherDetails(Connection con) throws InterruptedException, SQLException {
        Statement pstmt = null;
        GiftVoucherPojo gvpojo = null;
        ResultSet rs = null;
        pstmt = con.createStatement();
        rs = pstmt.executeQuery("select * from tbl_gvtransactiondetails where gv_sentstatus='A'");
        gvpojo = new GiftVoucherPojo();
        if (rs.next()) {
            gvpojo = new GiftVoucherPojo();
            gvpojo.setGiftVoucherNo(rs.getString("gvcode"));
            gvpojo.setGvActiveCount(rs.getString("gvcount"));
            gvpojo.setCustMobileNo(rs.getString("mobileno"));
            gvpojo.setFromDate(rs.getInt("fromdate"));
            gvpojo.setToDate(rs.getInt("todate"));
            gvpojo.setRefNo(rs.getString("refno"));
            gvpojo.setSiteId(rs.getString("siteid"));
            gvpojo.setGvRedemptionStatus(rs.getString("gv_sentstatus"));
            gvpojo.setRedeemedGvCode(rs.getString("redeemedgv"));
        } 
       
        pstmt = null;
        rs = null;
        return gvpojo;
    }

    public void updateStatusOFGVTransDetails(Connection con, String gvCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("update tbl_gvtransactiondetails set gv_sentstatus='Y' where gvcode='" + gvCode + "'");
            int res = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            pstmt = null;
        }

    }

    public GiftVoucherPojo getGVCodeToSentSMS(Connection con) throws SQLException {
        GiftVoucherPojo gvPojo = new GiftVoucherPojo();
        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("select gvcode,gvcount,mobileno from tbl_gvtransactiondetails where sms_sentstatus='N'");
            if (res.next()) {
                gvPojo.setGvActiveCount(res.getString("gvcount"));
                gvPojo.setCustMobileNo(res.getString("mobileno"));
                gvPojo.setGiftVoucherNo(res.getString("gvcode"));
            }
           System.out.println("<========> GV CODE IS <=========> "+gvPojo.getGiftVoucherNo() + "\n<==========> CUSTOMER MOBILE NUMBER <========> "+gvPojo.getCustMobileNo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gvPojo;
    }

    public int sentGVCodeToCustomer(String customernumber, Connection con, String gvCode, int date, String time, int gvCount) throws SQLException {
        PopulateData pdata = new PopulateData();
        int finalResult=0;
        String SMS_Username = "";
        String SMS_password = "";
        String SMS_Sender = "";
        String SMS_URL = "";
        String status = "";
        String gv_Enabled = "";
        StringBuffer gv_Text = null;
        GiftVoucherDO gvDo = null;
        try {
            gvDo = new GiftVoucherDO();
            System.out.println("**********************GV Code Sent to Customer***************************");
            //NetConnection.
            NetConnection.setProxy();
            gv_Text = new StringBuffer();
            gv_Enabled = pdata.getConfigValue(con, "Gv_SMS_Enabled");

            if (gv_Enabled.equalsIgnoreCase("Y")) {
                String SMS_Text = gvDo.getGvSMSTextBasedOnGvCount(con, gvCount);
                gv_Text.append(SMS_Text.toString());
                String gvText=gv_Text.toString();
               gvText=gvText.replace("XXX", gvCode);
 //                int indexOfString = gv_Text.indexOf("code") + 8;
//                gv_Text.insert(indexOfString, gvCode);
                SMS_Username = pdata.getConfigValue(con, "SMS_Username");
                SMS_password = pdata.getConfigValue(con, "SMS_password");
                SMS_Sender = pdata.getConfigValue(con, "SMS_Sender");
                SMS_URL = pdata.getConfigValue(con, "SMS_URL");
                String queryString = "&username=" + SMS_Username + "&password=" + SMS_password + "&to=" + customernumber + "&from=" + SMS_Sender + "&text=" + gvText;
                URL url = new URL(SMS_URL);
                URLConnection huc = url.openConnection();
                huc.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(huc.getOutputStream());
                wr.write(queryString);
                wr.flush();
                BufferedReader rd = new BufferedReader(new InputStreamReader(huc.getInputStream()));
                String result = rd.readLine();
                wr.close();
                rd.close();
                if (result == null) {
                    finalResult++;
                    System.out.println("No response received ");
//                    status = "No response received (GV)";
//                    gvDo.webMsgOrMailStatusUpdate(con, gvCode, "Msg", date, time, status);
                } else if (result.startsWith("Sent")) {
                    finalResult++;
                    System.out.println("GV Message sent successfully");
//                    status = "GV Message sent successfully";
//                    gvDo.webMsgOrMailStatusUpdate(con, gvCode, "Msg", date, time, status);
                } else {
                    System.out.println("Error - " + result);
//                    status = "GV Error - " + result;
//                    gvDo.webMsgOrMailStatusUpdate(con, gvCode, "Msg", date, time, status);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //status = e.getMessage();
            //Logger.getLogger(GoogleReviewMsg.class.getName()).log(Level.SEVERE, null, e);
            // gvDo.webMsgOrMailStatusUpdate(con, gvCode, "Msg", date, time, status);
        } finally {
            NetConnection.removeProxy();
        }
        return finalResult;
    }

    public String getGvSMSTextBasedOnGvCount(Connection con, int gvCount) throws SQLException {
        String GvSMSText = "";
        Statement stmt = null;
        ResultSet res = null;
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("select gvsms_text from tbl_gvseries where gvcount='" + gvCount + "'");
            if (res.next()) {
                GvSMSText = res.getString("gvsms_text");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GvSMSText;
    }

//    public int webMsgOrMailStatusUpdate(Connection con, String transactionno, String category, int date, String time, String status) throws SQLException {
//        int result = 0;
//        PreparedStatement statement = null;
//        try {
//
//            statement = con.prepareStatement("insert into tbl_web_status values  (?,?,?,?,?)");
//            statement.setString(1, transactionno);
//            statement.setString(2, category);
//            statement.setInt(3, date);
//            statement.setInt(4, Integer.parseInt(time));
//            if (status.length() > 90) {
//                statement.setString(5, status.substring(0, 90));
//            } else {
//                statement.setString(5, status);
//            }
//
//            result = statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            statement.close();
//        }
//        return result;
//    }
    public void updateSMSSentStatus(String gvCode, Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement("update tbl_gvtransaciondetails set sms_sentstatus='Y' where gvcode='" + gvCode + "'");
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
public void updateSMSSentStatus(Connection con, String gvCode) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            pstmt = con.prepareStatement("update tbl_gvtransactiondetails set sms_sentstatus='Y' where gvcode='" + gvCode + "'");
            int res = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pstmt.close();
            pstmt = null;
        }
    }
}

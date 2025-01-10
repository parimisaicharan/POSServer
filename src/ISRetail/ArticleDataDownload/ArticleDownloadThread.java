/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.ArticleDataDownload;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.NetConnection;
import ISRetail.msdeconnection.MsdeConnection;
//import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JTextArea;

/**
 *
 * @author eyeplus
 */
public class ArticleDownloadThread implements Runnable {
//    JTextArea statusArea;

    private int i = 0;
    private static int freq;
    private static String articleLimit;
    ArticleDownloadService articleDownloadWebService = new ArticleDownloadService();
    ArrayList<ArticleListPOJO> articlelist1, templist;

    public ArticleDownloadThread() {
//         this.articleDownloadWebService = articleDownloadWebService;
//        this.statusArea = statusArea;
//        this.artLists = ArticleList1;
    }

    public void run() {
        while(true){
        try {
            Thread.sleep(2000);
            boolean valid = true;
            System.err.println("Checking For Internet Connection..");
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3 || internetconnection == 2) {
                valid = false;
                setI(2);
                System.err.println("Download Failed : Check Your Network for Internet Connection !");
                return;
            } else {
                System.err.println("Checking PI server is reachable");
                //  statusArea.append("TESTING");
                internetconnection = NetConnection.checkXIConnection();
                if (internetconnection == 2) {
                    valid = false;
                    setI(3);
                    System.err.println("Failed : Incorrect Server Address : Contact Administrator to Fix the Problem!");
                    return;
                } else if (internetconnection == 3 || internetconnection == 4) {
                    valid = false;
                    setI(4);
                    System.err.println("Failed : Unknown Error : Contact Administrator to Fix the Problem!");
                    return;
                }
            }


            if (valid) {
                MsdeConnection msdeConnection = null;
                Connection connection = null;
                try {
                    msdeConnection = new MsdeConnection();
                    connection = msdeConnection.createConnection();
//                    deleteDuplicateRecordsByComparingDate(connection);
                    deleteDuplicateRecordsByComparingUCP(connection);
                    if (!Validations.isFieldNotEmpty(articleLimit)) {
                        freq = PosConfigParamDO.getArticleDataDownlaodFreq(connection);
                        articleLimit = PosConfigParamDO.getValForThePosConfigKey(connection, "revisedUCPdownloadLimit");
                    }
                    if (freq <= 0) {
                        freq = (7200 * 1000);
                    }

                    if (!Validations.isFieldNotEmpty(articleLimit)) {
                        articleLimit = "3";
                    }
                    System.err.println("Sending request to ISR...");
                    if (checkAutoThread(connection)) {
                        while(!articlelist1.isEmpty()){
                        if(articlelist1.size()<= Integer.parseInt(articleLimit)){
                            articleDownloadWebService.callWebService(ServerConsole.siteID, articlelist1);
                            articlelist1.clear();
                             System.err.println("Array list is cleared");
                                 int noofRowsUpdated = articleDownloadWebService.downloadDataFromISR(connection);
                        if (noofRowsUpdated == 0) {
                            setI(5);
                        } else {
                            setI(1);
                        }
                        }else if(articlelist1.size() > Integer.parseInt(articleLimit)){
                            
                            templist =  new ArrayList<ArticleListPOJO>();
                            for(int m =0;m < Integer.parseInt(articleLimit); m++){
                            templist.add(articlelist1.get(0));
                            articlelist1.remove(0);
                            }
//                            System.err.println("templist  -- 11");
//                            for(int m =0;m < Integer.parseInt(articleLimit); m++){
//                                System.err.println("templist  -- "+templist.get(m));
//                            }
                            articleDownloadWebService.callWebService(ServerConsole.siteID, templist);
                            int noofRowsUpdated = articleDownloadWebService.downloadDataFromISR(connection);
                        if (noofRowsUpdated == 0) {
                            setI(5);
                        } else {
                            setI(1);
                        }
                        }
                        }
                        System.err.println("outside while loop");
                    } else {
                        System.err.println("No Article need to downlaod for Revised UCP ...");
                    }
                } catch (Exception e) {
                    System.out.println("**************************************************************");
                    System.out.println("********************Revised UCP is not updated ***************************");
                    System.out.println("**************************************************************");
                    e.printStackTrace();

                } finally {
                    msdeConnection = null;
                    try {
                        System.out.println("Revised UCP sleeping >>> "+freq);
                        Thread.sleep(freq);//As of now 2 hours
                        if (connection != null) {
                            connection = null;
                        }
                    } catch (Exception e) {
                         e.printStackTrace();
                    }
                }
            }
            
            
        } catch (Exception e) {
            try{
            Thread.sleep(freq);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
            setI(0);
            return;
        }
        }
    }

    public void deleteDuplicateRecordsByComparingDate(Connection con) {
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            //pstmt = con.prepareStatement("delete tbl_reviseducplist from  tbl_ucp inner join tbl_reviseducplist on tbl_ucp.materialcode = tbl_reviseducplist.materialcode WHERE tbl_ucp.fromdate = tbl_reviseducplist.fromdate");
            
            pstmt = con.prepareStatement("delete tbl_reviseducplist where materialcode in(select distinct tbl_reviseducplist.materialcode from tbl_reviseducplist inner join tbl_ucp on tbl_reviseducplist.materialcode = tbl_ucp.materialcode where tbl_reviseducplist.fromdate <= tbl_ucp.fromdate)");
            
            result = pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
    }
    public void deleteDuplicateRecordsByComparingUCP(Connection con) { // Added by Thangaraj for cleaning revised ucp table by comparing ucp and date
        int result = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
                        
            pstmt = con.prepareStatement("delete tbl_reviseducplist where materialcode in(select distinct tbl_reviseducplist.materialcode from tbl_reviseducplist inner join tbl_ucp on tbl_reviseducplist.materialcode = tbl_ucp.materialcode where tbl_reviseducplist.fromdate <= tbl_ucp.fromdate and tbl_reviseducplist.ucpamount = tbl_ucp.ucpamount)");
            
            result = pstmt.executeUpdate();
            System.out.println("Revised ucp deletion operation executed successfully"+result);
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            pstmt = null;
            rs = null;
        }
    }
    public boolean checkAutoThread(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean check = false;
        articlelist1 = new ArrayList<ArticleListPOJO>();
        try {
            pstmt = con.prepareStatement("SELECT tbl_reviseducplist.materialcode FROM tbl_articlemaster INNER JOIN  tbl_reviseducplist ON tbl_articlemaster.materialcode = tbl_reviseducplist.materialcode where updatedate <= '" + ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy") + "'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ArticleListPOJO ArtPojo = new ArticleListPOJO();
                ArtPojo.setMaterialCode(rs.getString("materialcode"));
                ArtPojo.setUCPFlag(true);
                articlelist1.add(ArtPojo);
                check = true;
            }
            return check;

        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return false;
        } finally {
            pstmt = null;
            rs = null;
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

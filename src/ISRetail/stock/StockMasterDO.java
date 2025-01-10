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
 * DO
 * 
 */
package ISRetail.stock;

import ISRetail.Helpers.ConvertDate;
import ISRetail.article.MaterialListPOJO;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import ISRetail.utility.validations.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JComboBox;

/**
 *
 * @author Administrator
 */
public class StockMasterDO {

    Statement statement = null;
    PreparedStatement pstmt = null;

    /**
     * ******************************** METHOD TO SAVE ONE STOCK DETAIL  ********************************
     */
    public int saveStockDetails(StockMasterPOJO scpojo, Connection con) throws SQLException {
        int result = 0;

        String insertStatement = null;
        String batchInd = getBtchforarticle(scpojo.getMaterialcode(), con);
        if (batchInd != null && batchInd.equalsIgnoreCase("X")) {
            insertStatement = "insert into tbl_stockmaster_batch values (?,?,?,?,?,?,?,?)";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            pstmt1.setString(1, scpojo.getStorecode());
            pstmt1.setString(2, scpojo.getStorageloc());
            pstmt1.setString(3, scpojo.getMaterialcode());
            pstmt1.setString(4, scpojo.getBatch());
            pstmt1.setInt(5, scpojo.getQuantity());
            pstmt1.setString(6, scpojo.getUpdatestatus());
            pstmt1.setInt(7, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(8, ConvertDate.getCurrentTimeToString());

            result = pstmt1.executeUpdate();
        } else {
            insertStatement = "insert into tbl_stockmaster values (?,?,?,?,?,?,?)";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            pstmt1.setString(1, scpojo.getStorecode());
            pstmt1.setString(2, scpojo.getStorageloc());
            pstmt1.setString(3, scpojo.getMaterialcode());
            pstmt1.setInt(4, scpojo.getQuantity());
            pstmt1.setString(5, scpojo.getUpdatestatus());
            pstmt1.setInt(6, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(7, ConvertDate.getCurrentTimeToString());
            result = pstmt1.executeUpdate();
        }

        return result;
    }

    /**
     * ******************************** METHOD TO UPDATE ONE STOCK DETAIL  ********************************
     */
    public int updateStockDetails(StockMasterPOJO scpojo, Connection con) throws SQLException {
        int result = 0;

        String insertStatement = null;
        String batchInd = getBtchforarticle(scpojo.getMaterialcode(), con);
        if (batchInd != null && batchInd.equalsIgnoreCase("X")) {
            insertStatement = "update tbl_stockmaster_batch set quantity = ?,updatestatus=?,data_syncdate=?,data_synctime=? where storecode='" + scpojo.getStorecode() + "' and storageloc='" + scpojo.getStorageloc() + "' and materialcode='" + scpojo.getMaterialcode() + "'and batch ='" + scpojo.getBatch() + "'";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            pstmt1.setInt(1, scpojo.getQuantity());
            pstmt1.setString(2, scpojo.getUpdatestatus());
            pstmt1.setInt(3, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(4, ConvertDate.getCurrentTimeToString());
            result = pstmt1.executeUpdate();
        } else {
            insertStatement = "update tbl_stockmaster set quantity = ?,updatestatus=?,data_syncdate=?,data_synctime=? where storecode='" + scpojo.getStorecode() + "' and storageloc='" + scpojo.getStorageloc() + "' and materialcode='" + scpojo.getMaterialcode() + "'";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            pstmt1.setInt(1, scpojo.getQuantity());
            pstmt1.setString(2, scpojo.getUpdatestatus());
            pstmt1.setInt(3, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
            pstmt1.setString(4, ConvertDate.getCurrentTimeToString());
            result = pstmt1.executeUpdate();
        }

        return result;
    }

    //*************delete stock -will be called before Active ISR stock  updationfrom stock master maual download only
    public int deleteStock(StockMasterPOJO scpojo, Connection con) throws SQLException {
        int result = 0;
        String insertStatement = null;
        String batchInd = getBtchforarticle(scpojo.getMaterialcode(), con);
        if (batchInd != null && batchInd.equalsIgnoreCase("X")) {
            insertStatement = "delete from tbl_stockmaster_batch  where storecode='" + scpojo.getStorecode() + "' and storageloc='" + scpojo.getStorageloc() + "' and materialcode='" + scpojo.getMaterialcode() + "'and batch ='" + scpojo.getBatch() + "'";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            result = pstmt1.executeUpdate();
        } else {
            insertStatement = "delete from tbl_stockmaster where storecode='" + scpojo.getStorecode() + "' and storageloc='" + scpojo.getStorageloc() + "' and materialcode='" + scpojo.getMaterialcode() + "'";
            PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
            result = pstmt1.executeUpdate();
        }
        return result;
    }

    public String getBtchforarticle(String matrial, Connection con) throws SQLException {
        String batchindicator = null;
        try {
            String query = "select batchindicator from tbl_articlemaster where materialcode =? ";
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, matrial);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                batchindicator = rs.getString("batchindicator");
            }
        } catch (Exception E) {
            E.printStackTrace();
            throw new SQLException();
        }
        return batchindicator;
    }

    /**
     * ******************************** METHOD TO SAVE ONE STOCK DETAIL  ********************************
     */
    public int saveGVStockDetails(StockMasterPOJO scpojo, Connection con) throws SQLException {
        int result = 0;

        String insertStatement = null;
        insertStatement = "insert into tbl_gvmaster values (?,?,?,?,? ,?,?,?,?,? ,?)";
        PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
        pstmt1.setString(1, scpojo.getStorecode());
        pstmt1.setString(2, scpojo.getStorageloc());
        pstmt1.setString(3, scpojo.getMaterialcode());
        pstmt1.setString(4, scpojo.getGvSerialNo());
        pstmt1.setString(5, scpojo.getGvStatus());
        pstmt1.setInt(6, scpojo.getValidFrom());
        pstmt1.setInt(7, scpojo.getValidTo());
        pstmt1.setString(8, scpojo.getUpdatestatus());
        pstmt1.setString(9, scpojo.getRecordStatus());
        pstmt1.setInt(10, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(11, ConvertDate.getCurrentTimeToString());
        result = pstmt1.executeUpdate();

        return result;
    }

    /**
     * ******************************** METHOD TO UPDATE ONE STOCK DETAIL  ********************************
     */
    public int updateGVStockDetails(StockMasterPOJO scpojo, Connection con) throws SQLException {
        int result = 0;
        String insertStatement = null;
        insertStatement = "update tbl_gvmaster set storageloc = ?,updatestatus=?,data_syncdate=?,data_synctime=?,status =?  where storecode='" + scpojo.getStorecode() + "' and serialno='" + scpojo.getGvSerialNo() + "' and materialcode='" + scpojo.getMaterialcode() + "' and gvstatus = '" + scpojo.getGvStatus() + "'";
        PreparedStatement pstmt1 = con.prepareStatement(insertStatement);
        pstmt1.setString(1, scpojo.getStorageloc());
        pstmt1.setString(2, scpojo.getUpdatestatus());
        pstmt1.setInt(3, ConvertDate.getDateNumeric(ServerConsole.businessDate, "dd/MM/yyyy"));
        pstmt1.setString(4, ConvertDate.getCurrentTimeToString());
        pstmt1.setString(5, scpojo.getRecordStatus());
        result = pstmt1.executeUpdate();
        return result;
    }

    public int closeAllExpiredGVs(Connection con, int sysDate) {
        //*****to update the status of all expired (ie. expired date before current business date) credit note as 'CLOSED'
        int result = 0;
        try {
            PreparedStatement stmtUpdate;
            stmtUpdate = con.prepareStatement("UPDATE tbl_gvmaster SET gvstatus = 'CLOSED' where validto < ?");
            stmtUpdate.setInt(1, sysDate);
            result = stmtUpdate.executeUpdate();
        } catch (Exception ed) {
            ed.printStackTrace();
        }
        return result;
    }

    public int archiveAllRedeemedGVs(Connection con) {
        //*****to update the status of all expired (ie. expired date before current business date) credit note as 'CLOSED'
        int result = 0;
        try {
            PreparedStatement stmtUpdate;
            stmtUpdate = con.prepareStatement("delete from tbl_gvmaster where gvstatus = 'REDEEMED'");
            result = stmtUpdate.executeUpdate();
        } catch (Exception ed) {
            ed.printStackTrace();
        }
        return result;
    }

    /**
     * ************************************ METHOD TO GET THE STOCK MASTER         ************************************************
     */
    public ArrayList<StockMasterPOJO> getStock(Connection con) {
        ArrayList<StockMasterPOJO> stockMasterPOJOs = null;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from tbl_stockmaster order by materialcode");
            stockMasterPOJOs = new ArrayList<StockMasterPOJO>();
            while (rs.next()) {
                StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                stockMasterPOJO.setMaterialcode(rs.getString("materialcode"));
                stockMasterPOJO.setQuantity(rs.getInt("quantity"));
                stockMasterPOJO.setStorageloc(rs.getString("storageloc"));
                stockMasterPOJOs.add(stockMasterPOJO);
            }
        } catch (Exception e) {
        }
        return stockMasterPOJOs;
    }

    /**
     * ************************************ METHOD TO GET THE STOCK MASTER         ************************************************
     */
    public ArrayList<StockMasterPOJO> getStockByMaterial(Connection con, String materialCode) {
        ArrayList<StockMasterPOJO> stockMasterPOJOs = null;
        try {
            stockMasterPOJOs = new ArrayList<StockMasterPOJO>();
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select * from tbl_stockmaster where materialcode = '" + materialCode + "'");
            while (rs.next()) {
                StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                stockMasterPOJO.setMaterialcode(rs.getString("materialcode"));
                stockMasterPOJO.setQuantity(rs.getInt("quantity"));
                stockMasterPOJO.setStorageloc(rs.getString("storageloc"));
                stockMasterPOJOs.add(stockMasterPOJO);
            }
            statement = con.createStatement();
            rs = statement.executeQuery("select * from tbl_stockmaster_batch where materialcode = '" + materialCode + "'");
            while (rs.next()) {
                StockMasterPOJO stockMasterPOJO = new StockMasterPOJO();
                stockMasterPOJO.setMaterialcode(rs.getString("materialcode"));
                stockMasterPOJO.setQuantity(rs.getInt("quantity"));
                stockMasterPOJO.setStorageloc(rs.getString("storageloc"));
                stockMasterPOJO.setBatch(rs.getString("batch"));
                stockMasterPOJOs.add(stockMasterPOJO);
            }

        } catch (Exception e) {
        }
        return stockMasterPOJOs;
    }

    /**
     * ******************************** METHOD TO THE AVAILABILITY OF STOCK FOR
     * SPECIFIED MATERIAL ***********************************
     */
    public boolean checkStockAvailability(Connection con, String materialCode, int quantityGiven) {
        boolean isAvailable = false;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select quantity from tbl_stockmaster where materialcode = '" + materialCode + "'");
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity - quantityGiven >= 0) {
                    isAvailable = true;
                }
            }
        } catch (Exception e) {
        }
        return isAvailable;
    }

    /**
     * ******************************** METHOD TO THE AVAILABILITY OF STOCK FOR
     * SPECIFIED MATERIAL ***********************************
     */
    /*    public boolean checkStockAvailability1(Connection con, String materialCode, int quantityGiven) {
    boolean isAvailable = false;
    try {
    statement = con.createStatement();
    ResultSet rs = statement.executeQuery("select quantity from tbl_stockmaster_batch where materialcode = '" + materialCode + "'");
    if (rs.next()) {
    int quantity = rs.getInt("quantity");
    if (quantity - quantityGiven >= 0) {
    isAvailable = true;
    }
    }
    } catch (Exception e) {
    }
    return isAvailable;
    }*/
    public int updatetbl_stock_batch_quantity(Connection con, String materialCode, int quantityGiven) throws Exception {
        int result = 0;
        try {
            statement = con.createStatement();
            result = statement.executeUpdate("update tbl_stockmaster_batch set quantity = quantity - " + quantityGiven + "where materialcode = '" + materialCode + "' and batch='FREE'");
        } catch (Exception e) {
            throw new SQLException();
        }
        return result;
    }

    /**
     * *************************** METHOD TO UPDATE THE STOCK OF PARTICULAR
     * MATERIAL -Created by Smitha            ************************
     */
    public int updateStock(Connection con, String materialCode, int quantityGiven) throws Exception {
        int result = 0;
        try {
            statement = con.createStatement();
            result = statement.executeUpdate("update tbl_stockmaster set quantity = quantity - " + quantityGiven + " where materialcode = '" + materialCode + "'");
        } catch (Exception e) {
            throw new SQLException();
        }
        return result;
    }

    public boolean checkStockAvailabilityForBatchenabled(Connection con, String materialCode, int quantityGiven, String batchid) {
        boolean isAvailable = false;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select quantity from tbl_stockmaster_batch where materialcode = '" + materialCode + "' and batch='" + batchid + "'");
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity - quantityGiven >= 0) {
                    isAvailable = true;
                }
            }
        } catch (Exception e) {
        }
        return isAvailable;
    }

    public int checkStockAvailabilityDisplay(Connection con, String materialCode, int quantityGiven) {
        int isAvailable = 0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select quantity from tbl_stockmaster where materialcode = '" + materialCode + "'");
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                //   if (quantity - quantityGiven >= 0) {
                isAvailable = quantity;
                //    }
            }
        } catch (Exception e) {
        }
        return isAvailable;
    }

    public int checkStockAvailabilityDisplayBAtchenabled(Connection con, String materialCode, int quantityGiven, String batchid) {
        int isAvailable = 0;
        try {
            statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select quantity from tbl_stockmaster_batch where materialcode = '" + materialCode + "'and batch='" + batchid + "'");
            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                //   if (quantity - quantityGiven >= 0) {
                isAvailable = quantity;
                //    }
            }
        } catch (Exception e) {
        }
        return isAvailable;
    }

//    public StockMasterPOJO get_tbl_stock_BatchValues (Connection con,StockMasterPOJO stkmasterpojoobj, String materialCode)
//    {
//        try {
//            Statement pstmt = con.createStatement();
//            String searchstatement = "select * from tbl_salesorderheader where saleorderno='" + saleorderno + "'";
//            ResultSet rs = pstmt.executeQuery(searchstatement);
//            while (rs.next()) {
//                pojoobj.setSaletype(rs.getString("saletype"));
//                pojoobj.setReleaseStatus(rs.getString("releasestatus"));
//                pojoobj.setPrintordertype(rs.getString("printordertype"));
//                return pojoobj;
//            }
//            return null;
//        } catch (SQLException sQLException) {
//            sQLException.printStackTrace();
//            return null;
//        }
//        
//    }
//    
    /**
     * **************************** METHOD TO THE AVAILABILITY OF STOCK FOR
     * SPECIFIED MATERIAL FROM GV STOCK TABLE *****************************
     */
    /*  public String checkGvStockAvailability(Connection con,String materialCode,int quantityGiven){
    boolean isAvailable = false;
    try
    {
    statement = con.createStatement();            
    ResultSet rs = statement.executeQuery("select quantity from test_gvmaster where materialcode = '"+materialCode+"'");
    if(rs.next()){
    int quantity = rs.getInt("quantity");
    if(quantity-quantityGiven>=0){
    isAvailable = true;
    }
    }
    }catch(Exception e){          
    }
    return isAvailable;
    } */
    public void getBatchForMaterial(String materialCode, JComboBox combo, Connection con) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select batch from tbl_stockmaster_batch where materialcode= '" + materialCode + "'");
            while (rs.next()) {
                String batch = rs.getString(1);
                combo.addItem(batch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // added for automatic stock direct download
    public ArrayList<MaterialListPOJO> getArticlsForStockDirectDownlaod(Connection con, int downloadfrq) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<MaterialListPOJO> materialLists = null;
        String matID = null;
        MaterialListPOJO matPojo = null;
        try {
            Calendar c = Calendar.getInstance();
            Date todate = new Date(c.getTimeInMillis());
            c.add(Calendar.SECOND, -downloadfrq);
            Date fromdate = new Date(c.getTimeInMillis());
            String fromtime = ConvertDate.getTimeString(fromdate);
            String totime = ConvertDate.getTimeString(todate);
            materialLists = new ArrayList<MaterialListPOJO>();
            //****Commented by Naveen N on 13.05.24 and new code added for new logic to consider SKU which has qty and not synched on current day
            //ps = con.prepareStatement("select distinct a.materialcode from tbl_salesorderlineitems as a inner join tbl_salesorderheader as b on a.saleorderno = b.saleorderno where b.createddate =  (select convert(numeric(8),convert(char(8),getDate(),112)))  and b.createdtime between ? and ? and a.materialcode not like 'CUST%'");
            //ps.setString(1, fromtime);
            //ps.setString(2, totime);
            ps = con.prepareStatement("select distinct a.materialcode from (select top 10 materialcode as materialcode  from tbl_stockmaster_batch where quantity > 0 and ( case when len(data_syncdate) = 8 then replace( CAST( CAST( data_syncdate AS char(8)) AS date ),'-','') else '' end ) < ( CONVERT(VARCHAR(8), getdate(), 112) ) order by data_syncdate desc ) a");
            rs = ps.executeQuery();
            while (rs.next()) {
                matID = String.valueOf(rs.getString("materialcode"));
                if (matID != null && matID.trim().length() > 0) {
                    System.out.println("Direct stock download ********** " + matID.toUpperCase());
                    matPojo = new MaterialListPOJO();
                    matPojo.setSlNo(rs.getRow());
                    matPojo.setMaterialCode(matID.toUpperCase());
                    materialLists.add(matPojo);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return materialLists;
    }

    public ArrayList getNCMaterialList(Connection conn, ArrayList<MaterialListPOJO> materialList) {
        ResultSet rs = null;
        Statement stmt = null;
        ArrayList<MaterialListPOJO> materialLists = (ArrayList<MaterialListPOJO>) materialList.clone();
//        StringBuffer materialquery = null;
        String materialcode = null;
        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
//        String curentmonth = df.format(calendar.getTime())+"01";
        String stockquery = "";
//        System.out.println("curentmonth "+curentmonth);
        try {
//            materialquery = new StringBuffer("select distinct(materialcode) from tbl_salesorderlineitems where saleorderno in (\n" +
//        "select saleorderno from tbl_salesorderheader inner join  tbl_pos_staging ps on ps.transactionid1 = tbl_salesorderheader.saleorderno\n" +
//        "where sapidstatus in ('N','X') and ps.createddate >= '"+curentmonth+"' or  saleorderno in\n" +
//        "(select refno from tbl_billingheader inner join  tbl_pos_staging ps on ps.transactionid1 = tbl_billingheader.invoiceno \n" +
//        "where sapidstatus in ('N','X') and ps.createddate >= '"+curentmonth+"')) and materialcode in ('");
//            for (MaterialListPOJO mlpojo : materialLists) {
//                materialquery.append(mlpojo.getMaterialCode());
//                if (!(materialLists.size() == materialLists.indexOf(mlpojo) + 1)) {
//                    materialquery.append("','");
//                }
//            }
//            materialquery.append("')");
            stockquery = "select distinct materialcode from \n"
                    + "(\n"
                    + "select materialcode from tbl_billinglineitem where invoiceno in\n"
                    + "(select transactionid1 from tbl_pos_staging where interfaceid in('PW_IF04','PW_IF07') and sapidstatus='X' and createddate >= convert(varchar(10), DATEADD(m, DATEDIFF(m, 0, GETDATE()), 0) ,112)) \n"
                    + "union\n"
                    + "select materialcode from tbl_salesorderlineitems where saleorderno in \n"
                    + "(select transactionid1 from tbl_pos_staging where interfaceid in ('PW_IF03') and sapidstatus='X' and updatetype='I' and createddate >= convert(varchar(10), DATEADD(m, DATEDIFF(m, 0, GETDATE()), 0) ,112))\n"
                    + "union\n"
                    + "select materialcode from tbl_billinglineitem where invoiceno in\n"
                    + "(select invoiceno from tbl_billcancelheader where invoicecancellationno in\n"
                    + "(select transactionid1 from tbl_pos_staging where interfaceid='PW_IF16' and sapidstatus='X' and createddate >= convert(varchar(10), DATEADD(m, DATEDIFF(m, 0, GETDATE()), 0) ,112)\n"
                    + ")\n"
                    + ")\n"
                    + ") as t1";

            stmt = conn.createStatement();
            rs = stmt.executeQuery(stockquery.toString());
            while (rs.next()) {
                materialcode = rs.getString(1);
                for (MaterialListPOJO mlpojo : materialLists) {
                    if (mlpojo.getMaterialCode().equalsIgnoreCase(materialcode)) {
                        materialLists.remove(mlpojo);
                        break;
                    }
                }

            }
            //rs = stmt.executeQuery("select  saleorderlineitem.materialcode from tbl_pos_staging  staging INNER JOIN tbl_salesorderlineitems saleorderlineitem on saleorderlineitem.saleorderno = staging.transactionid1 where (staging.sapidstatus in ('N','X') or staging.updatestatus in ('Waiting','Cancelled')) and (staging.transactionid1 like 'T%' or staging.transactionid1 like 'B%') and  staging.createddate > '20160801' and saleorderlineitem.materialcode in ('t1001c1a1 +"')");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return materialLists;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comfortcall;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author ewitsupport
 */
public class ComfortCall implements Runnable {
    private JTextArea statusArea;
     public ComfortCall(JTextArea statusArea) {
        this.statusArea = statusArea;
    }
    public ComfortCall()
    {
    }
    

    public void run() {
        int freq = 20000;
        try {
            System.out.println("********* "+freq);
            Thread.sleep(freq);
        } catch (InterruptedException ex) {
            Logger.getLogger(ComfortCall.class.getName()).log(Level.SEVERE, null, ex);
        }
        MsdeConnection msde = null;
        Connection con = null;
        ComfortMailSend comftMail;
        ComfortDO comftDo = null;
        SiteMasterPOJO sitePojo = null;
        SiteMasterDO siteMastDo = null;
         msde = new MsdeConnection();
         ArrayList<String> invoiceCount=null;
         ArrayList<String> invccount=null;
//           msde.checkConnection();
//        con = msde.createConnection();
//        int invoiceCount=comftDo.getTotalInvoiceCount(con);
//        if(invoiceCount>=1){
        
      String filePath = "./config/Comfortcalldetails.xls";//"\"./config/\" + Comfortcalldetails + \".xls";
       File file = null;
//        msde = new MsdeConnection();
//        msde.checkConnection();
        con = msde.createConnection();
        try {
           statusArea.append("\n************Comfort Call Mail Started*************");
            comftDo = new ComfortDO();
            int transDate = comftDo.getLastTransactionDate(con);
            if(transDate > 0){
            if(comftDo.isSent(con, transDate))    {
                
            ArrayList totList = comftDo.getOrderNumbers(con);
            invccount=totList;
            if(totList.size()>=1){
            ComfortCall comcall = new ComfortCall();
            comcall.createExcel(totList, filePath);
            file=new File(filePath);
            }
           
            if(invccount.size()>=1){
                
            comftMail = new ComfortMailSend();
//            comftDo = new ComfortDO();
//            msde = new MsdeConnection();
//            msde = new MsdeConnection();
//            msde.checkConnection();
//            con = msde.createConnection();
            siteMastDo = new SiteMasterDO();
           // sitePojo = new SiteMasterPOJO();
            sitePojo = siteMastDo.getSite(con);
            String siteId = sitePojo.getSiteid();
            String siteEmail = sitePojo.getSite_contact_email();
            
            
                System.out.println("date ** "+ConvertDate.getNumericToDate(transDate));
                
           int status = comftMail.mail(con, siteId, siteEmail,transDate, ConvertDate.getNumericToDate(transDate), filePath);
          
            System.out.println("Comfort call status" + status);
            statusArea.append("\n********** Comfort Call Details Mail has been Sent!!! **********");
                
            try{
            if(file.delete())
            {
                System.out.println("File deleted successfully");
            }
            else
            {
                System.out.println("Failed to delete the file");
                file.delete();
            }
            }catch(Exception e){
                e.printStackTrace();
            }
    }else{
                statusArea.append("\n********** Comfort Call Details is not availabe***********");
            }
            statusArea.append("\n sleeping....");
        }else{
                statusArea.append("\n************Early Comfort Call Mail has been sent*************");
            }}
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("1*******Faild********1");
        }   catch (SQLException ex) {
            System.out.println("3*******Faild********3");
                Logger.getLogger(ComfortCall.class.getName()).log(Level.SEVERE, null, ex);
                
            } catch (ClassNotFoundException ex) {
                System.out.println("2*******Faild********2");
                Logger.getLogger(ComfortCall.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (Exception e) {
            e.printStackTrace();
        }

    }
//}

    public void createExcel(List<ComfortPojo> comftdetails, String excelFilePath) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        int rowcount = 1;
        short i = 0;
        HSSFSheet sheet = workbook.createSheet("Comfort Call Details");
        for (int j = 0; j < 15; j++) {
            sheet.setColumnWidth(j,3900);
        }
        sheet.setColumnWidth(rowcount, rowcount);
        HSSFFont font = workbook.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        HSSFRow rowhead = sheet.createRow((short) 0);
        rowhead.createCell(0).setCellValue("StoreCode");
        rowhead.createCell(1).setCellValue("SO Number");
        rowhead.createCell(2).setCellValue("Invoice Number");
        rowhead.createCell(3).setCellValue("Created Date");
        rowhead.createCell(4).setCellValue("Created Time");
        rowhead.createCell(5).setCellValue("Customer Code");
        rowhead.createCell(6).setCellValue("Customer Name");
        rowhead.createCell(7).setCellValue("Mobile No");
        rowhead.createCell(8).setCellValue("Customer City");
        rowhead.createCell(9).setCellValue("ComfortCallDate");
        rowhead.createCell(10).setCellValue("ComfortCallTime");
        rowhead.createCell(11).setCellValue("MaterialCode");
        rowhead.createCell(12).setCellValue("NetValue");
        rowhead.createCell(13).setCellValue("StyleConsultant");

        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
        workbook.write(fileOut);
        for (ComfortPojo comftdetail : comftdetails) {
            HSSFRow row = sheet.createRow(++rowcount);
            writeIntoExcel(comftdetail, row);
        }
        FileOutputStream outputStream = new FileOutputStream(excelFilePath);
        workbook.write(outputStream);
        fileOut.close();
        outputStream.close();
    }
    private void writeIntoExcel(ComfortPojo comftdetail, HSSFRow row) {
        int i = 0;
        HSSFCell cell = null;
        cell = row.createCell(i);
        cell.setCellValue(comftdetail.getStorecode());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getSalesorderno());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getInvoiceno());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCreateddate());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCreatedtime());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCustomercode());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCustomername());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getMobileno());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCustomercity());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getComfortcalldate());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getComfortcalltime());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getMaterialcode());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getNetvalue());
        cell = row.createCell(++i);
        cell.setCellValue(comftdetail.getCreatedby());
    }
}

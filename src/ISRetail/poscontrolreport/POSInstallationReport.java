/*
 * POSInstallationReport.java
 *
 * Created on January 8, 2009, 12:07 PM
 */

package ISRetail.poscontrolreport;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;

import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.ServerConsole;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author  Administrator
 */
public class POSInstallationReport extends javax.swing.JPanel {
    ServerConsole serverConsole; 
    /** Creates new form POSInstallationReport */
    public POSInstallationReport( ServerConsole serverConsole) {
      this.serverConsole = serverConsole;
        initComponents();
            reporttable.getTableHeader().setBackground(new Color(88, 109, 148));
            reporttable.getTableHeader().setForeground(new Color(201, 232, 252));
            reporttable.getTableHeader().setFont(new java.awt.Font("Verdana", 1, 12));

        reportgeneration();
    }
     public void reportgeneration()
    {
        try {
            MsdeConnection msdeconn = new MsdeConnection();
            Connection con = msdeconn.createConnection();
            Statement pstmt = con.createStatement();
            Statement pstmt1 = con.createStatement();
            Vector v;
            int quantity=0;
            int quantity1=0;
            int slno=1;
            
            ResultSet rs = pstmt.executeQuery("select * from tbl_posdoclastnumbers");
            
            if(rs.next()) {
                        
             v= new Vector();
             v.addElement(slno++);
             v.addElement("AdvanceReceipt");
             String advno=rs.getString("advancereceiptno");
             v.addElement(advno);
             v.addElement("");
             //start : changed on 17.01.2012 by ravi 
             ResultSet rs1 = pstmt1.executeQuery("select documentdate,createddate,createdtime from tbl_advancereceiptheader where documentno='"+advno+"'");
             if (rs1.next()) {
                v.addElement(ConvertDate.getNumericToDate(rs1.getInt("documentdate")));
                v.addElement(ConvertDate.getNumericToDate(rs1.getInt("createddate")));
                v.addElement(ConvertDate.getformattedTimeFromTime(rs1.getString("createdtime")));
             } else {
                ResultSet rscashreceipt = pstmt1.executeQuery("select documentdate,createddate,createdtime from dbo.tbl_cashreceiptheader where documentno='" + advno + "'");
                if (rscashreceipt.next()) {
                    v.addElement(ConvertDate.getNumericToDate(rscashreceipt.getInt("documentdate")));
                    v.addElement(ConvertDate.getNumericToDate(rscashreceipt.getInt("createddate")));
                    v.addElement(ConvertDate.getformattedTimeFromTime(rscashreceipt.getString("createdtime")));
                }
             }
             //end : changed on 17.01.2012 by ravi thota 
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("AdvanceReceipt Reversal");
             v.addElement(rs.getString("advancereceiptcancellationno"));
             v.addElement("");
             String advcancelno=rs.getString("advancereceiptcancellationno");
             ResultSet rs12 = pstmt1.executeQuery("select documentdate,createddate,createdtime from tbl_advancereceiptheader where documentno='"+advcancelno+"'");
            if(rs12.next()){
             v.addElement(ConvertDate.getNumericToDate(rs12.getInt("documentdate")));
             v.addElement(ConvertDate.getNumericToDate(rs12.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs12.getString("createdtime")));
             }
              
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("CashPayout No");
             v.addElement(rs.getString("cashpayoutno"));
              v.addElement("");
             String caspayoutno=rs.getString("cashpayoutno");
             ResultSet rs13 = pstmt1.executeQuery("select cashpayoutdate,createddate,createdtime from tbl_cashpayout where cashpayoutno='"+caspayoutno+"'");
            if(rs13.next()){
             v.addElement(ConvertDate.getNumericToDate(rs13.getInt("cashpayoutdate")));
             v.addElement(ConvertDate.getNumericToDate(rs13.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs13.getString("createdtime")));
             }
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("CreditNote No");
             v.addElement(rs.getString("creditnoteno"));
              v.addElement("");
             String creditno=rs.getString("creditnoteno");
             ResultSet rs14 = pstmt1.executeQuery("select creditnotedate,createddate,createdtime from tbl_creditnote where creditnoteno='"+creditno+"'");
            if(rs14.next()){
             v.addElement(ConvertDate.getNumericToDate(rs14.getInt("creditnotedate")));
             v.addElement(ConvertDate.getNumericToDate(rs14.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs14.getString("createdtime")));
             }
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Invoice No");
             v.addElement(rs.getString("invoiceno"));
              v.addElement("");
             String invno=rs.getString("invoiceno");
             ResultSet rs15 = pstmt1.executeQuery("select invoicedate,createddate,createdtime from tbl_billingheader where invoiceno='"+invno+"'");
            if(rs15.next()){
             v.addElement(ConvertDate.getNumericToDate(rs15.getInt("invoicedate")));
             v.addElement(ConvertDate.getNumericToDate(rs15.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs15.getString("createdtime")));
             }
             
             
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("InvoiceCancellation No");
             v.addElement(rs.getString("invoicecancel"));
              v.addElement("");
             String invcancelno=rs.getString("invoicecancel");
             ResultSet rs16 = pstmt1.executeQuery("select invoicecancellationdate,createddate,createdtime from tbl_billcancelheader where invoicecancellationno='"+invcancelno+"'");
            if(rs16.next()){
             v.addElement(ConvertDate.getNumericToDate(rs16.getInt("invoicecancellationdate")));
             v.addElement(ConvertDate.getNumericToDate(rs16.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs16.getString("createdtime")));
             }
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Saleorder No");
             v.addElement(rs.getString("saleorderno"));
              v.addElement("");
             String salno=rs.getString("saleorderno");
             ResultSet rs17 = pstmt1.executeQuery("select saleorderdate,createddate,createdtime from tbl_salesorderheader where saleorderno='"+salno+"'");
            if(rs17.next()){
             v.addElement(ConvertDate.getNumericToDate(rs17.getInt("saleorderdate")));
             v.addElement(ConvertDate.getNumericToDate(rs17.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs17.getString("createdtime")));
             }
             
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Inquiry No");
             v.addElement(rs.getString("inquiryno"));
              v.addElement("");
             String inqno=rs.getString("inquiryno");
             ResultSet rs18 = pstmt1.executeQuery("select createddate,createdtime from tbl_inquiry where inquiryno='"+inqno+"'");
            
             if(rs18.next()){
             v.addElement("");
             v.addElement(ConvertDate.getNumericToDate(rs18.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs18.getString("createdtime")));
             }
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Quotation No");
             v.addElement(rs.getString("quotationno"));
              v.addElement("");
             String quotno=rs.getString("quotationno");
             ResultSet rs19 = pstmt1.executeQuery("select quotationdate,createddate,createdtime from tbl_quotationheader where quotationno='"+quotno+"'");
             if(rs19.next()){
             v.addElement(ConvertDate.getNumericToDate(rs19.getInt("quotationdate")));
             v.addElement(ConvertDate.getNumericToDate(rs19.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs19.getString("createdtime")));
             }
             settable(v); 
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Customer No");
             v.addElement(rs.getString("customercode"));
             v.addElement("");
             String cusno=rs.getString("acknowledgementno");
             ResultSet rs20 = pstmt1.executeQuery("select createddate,createdtime from tbl_customermastermain where customercode='"+cusno+"'");
             if(rs20.next()){
             v.addElement("");
             v.addElement(ConvertDate.getNumericToDate(rs20.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs20.getString("createdtime")));
             }
             
             settable(v); 
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Acknowledgement No");
             v.addElement(rs.getString("acknowledgementno"));
              v.addElement("");
             String ackno=rs.getString("acknowledgementno");
             ResultSet rs21 = pstmt1.executeQuery("select acknowledgementdate,createddate,createdtime from tbl_ack_header where acknumber='"+ackno+"'");
             if(rs21.next()){
             v.addElement(ConvertDate.getNumericToDate(rs21.getInt("acknowledgementdate")));
             v.addElement(ConvertDate.getNumericToDate(rs21.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs21.getString("createdtime")));
             }
             settable(v);
             //COde Added on feb 10th 2010 for GFS number and GFScancellation no
             v = new Vector();
             v.addElement(slno++);
             v.addElement("GiftCard No");
             v.addElement(rs.getString("giftcardno"));
             v.addElement("");
             String giftcardno=rs.getString("giftcardno");
             ResultSet rs22 = pstmt1.executeQuery("select giftcarddate,createddate,createdtime from tbl_giftcard_selling where gcdocumentno='"+giftcardno+"'");
             if(rs22.next()){
             v.addElement(ConvertDate.getNumericToDate(rs22.getInt("giftcarddate")));
             v.addElement(ConvertDate.getNumericToDate(rs22.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs22.getString("createdtime")));
             }
             settable(v);
             v = new Vector();
             v.addElement(slno++);
             v.addElement("GiftCard Cancellation No");
             v.addElement(rs.getString("giftcardcancellationno"));
             v.addElement("");
             String gccancellationnum=rs.getString("giftcardcancellationno");
             ResultSet rs23 = pstmt1.executeQuery("select giftcarddate,createddate,createdtime from tbl_giftcard_selling where gcdocumentno='"+gccancellationnum+"'");
             if(rs23.next()){
             v.addElement(ConvertDate.getNumericToDate(rs23.getInt("giftcarddate")));
             v.addElement(ConvertDate.getNumericToDate(rs23.getInt("createddate")));
             v.addElement(ConvertDate.getformattedTimeFromTime(rs23.getString("createdtime")));
             }
             settable(v);
        }
            
            con.close();
            Connection con1 = msdeconn.createConnection();
            Statement stmt1 = con1.createStatement();
            ResultSet rs1 = stmt1.executeQuery("select count(*) from tbl_articlemaster");
            if(rs1.next())
            {
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Total No of Article");
             v.addElement("");
             v.addElement(rs1.getInt(1));
             settable(v);
            }
            con1.close();
            Connection con2 = msdeconn.createConnection();
            Statement stmt2 = con2.createStatement();
            //ResultSet rs2 = stmt2.executeQuery("select quantity from tbl_stockmaster_batch");
            ResultSet rs2 = stmt2.executeQuery("select count(*) from tbl_stockmaster_batch");
            
            while(rs2.next())
            {
           // quantity=quantity+rs2.getInt("quantity");
           // }
          //  {
             v = new Vector();
             v.addElement(slno++);
            // v.addElement("Sum of Article with batch");
             v.addElement("No of rows with batch");
             v.addElement("");
           //  v.addElement(quantity);
             v.addElement(rs2.getInt(1));
             settable(v); 
           
            }
            con2.close();
            Connection con3 = msdeconn.createConnection();
            Statement stmt3 = con3.createStatement();
          //  ResultSet rs3 = stmt3.executeQuery("select quantity from tbl_stockmaster");
            ResultSet rs3 = stmt3.executeQuery("select count(*) from tbl_stockmaster");
            while(rs3.next())
            {
           // quantity1=quantity1+rs3.getInt("quantity");
          //  }
       
         //   {
             v = new Vector();
             v.addElement(slno++);
           //  v.addElement("Sum of Article without batch");
             v.addElement("No of rows without batch");
             v.addElement("");
            // v.addElement(quantity1);
             v.addElement(rs3.getInt(1));
             settable(v); 
           
            }
             con3.close();
            Connection con4 = msdeconn.createConnection();
            Statement stmt4 = con4.createStatement();
            ResultSet rs4 = stmt4.executeQuery("select versionid from tbl_latestposversion");
            
            if(rs4.next())
            {
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Versionno");
             v.addElement("");
             v.addElement(rs4.getString("versionid"));
             settable(v); 
           
            }
            con4.close();
            Connection con5 = msdeconn.createConnection();
            Statement stmt5 = con5.createStatement();
            ResultSet rs5 = stmt5.executeQuery("select latestpatchid from tbl_latestposversion");
            
            if(rs5.next())
            {
             v = new Vector();
             v.addElement(slno++);
             v.addElement("Patchno");
              v.addElement("");
             v.addElement(rs5.getString("latestpatchid"));
             settable(v); 
           
            }
            
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
         
    }
    
    private void settable(Vector v)
    {
     ((DefaultTableModel) reporttable.getModel()).addRow(v);
     int rowCount = reporttable.getRowCount();
     ((DefaultTableModel) reporttable.getModel()).fireTableRowsInserted(rowCount, rowCount);
     
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        refreshbutton = new javax.swing.JButton();
        firstSeparator = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reporttable = new javax.swing.JTable();
        firstSeparator1 = new javax.swing.JSeparator();
        PrintButton = new javax.swing.JButton();

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));
        jDesktopPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jDesktopPane1MouseMoved(evt);
            }
        });

        refreshbutton.setFont(new java.awt.Font("Verdana", 1, 12));
        refreshbutton.setText("Refresh");
        refreshbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshbuttonActionPerformed(evt);
            }
        });
        refreshbutton.setBounds(10, 10, 130, 25);
        jDesktopPane1.add(refreshbutton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        firstSeparator.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        firstSeparator.setBounds(10, 43, 1120, -1);
        jDesktopPane1.add(firstSeparator, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(new java.awt.Color(237, 247, 252));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(88, 109, 148), new java.awt.Color(201, 232, 252)));

        jLabel2.setBackground(new java.awt.Color(206, 218, 238));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setForeground(new java.awt.Color(88, 109, 148));
        jLabel2.setText("POS Control Report");
        jLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 2, true));
        jLabel2.setMaximumSize(new java.awt.Dimension(127, 18));
        jLabel2.setMinimumSize(new java.awt.Dimension(127, 18));
        jLabel2.setOpaque(true);

        reporttable.setBackground(new java.awt.Color(237, 247, 252));
        reporttable.setFont(new java.awt.Font("Verdana", 1, 12));
        reporttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sl No.", "Transaction", "Last Document No", "Date From ISR", "Posting Date", "Transaction Date", "Transaction Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reporttable.getTableHeader().setReorderingAllowed(false);
        reporttable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                reporttableMouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(reporttable);
        reporttable.getColumnModel().getColumn(0).setResizable(false);
        reporttable.getColumnModel().getColumn(0).setPreferredWidth(30);
        reporttable.getColumnModel().getColumn(1).setPreferredWidth(150);
        reporttable.getColumnModel().getColumn(2).setResizable(false);
        reporttable.getColumnModel().getColumn(3).setResizable(false);
        reporttable.getColumnModel().getColumn(4).setResizable(false);
        reporttable.getColumnModel().getColumn(5).setResizable(false);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 46, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBounds(10, 50, 1030, 360);
        jDesktopPane1.add(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        firstSeparator1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(201, 232, 252), new java.awt.Color(88, 109, 148)));
        firstSeparator1.setBounds(20, 428, 1040, -1);
        jDesktopPane1.add(firstSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        PrintButton.setFont(new java.awt.Font("Verdana", 1, 12));
        PrintButton.setText("Print");
        PrintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrintButtonActionPerformed(evt);
            }
        });
        PrintButton.setBounds(30, 450, 130, 30);
        jDesktopPane1.add(PrintButton, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1159, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 787, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshbuttonActionPerformed
        // TODO add your handling code here:
       ((DefaultTableModel) reporttable.getModel()).setRowCount(0);
        reportgeneration();
}//GEN-LAST:event_refreshbuttonActionPerformed

    private void PrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButtonActionPerformed
        // TODO add your handling code here:
        printControlReport();
    }//GEN-LAST:event_PrintButtonActionPerformed

    private void jDesktopPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDesktopPane1MouseMoved
        // TODO add your handling code here:
        
         evt.setSource(serverConsole.MainTabbedPane);
        serverConsole.myMainTabbedPaneMouseMoved(evt);
    }//GEN-LAST:event_jDesktopPane1MouseMoved

    private void reporttableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reporttableMouseMoved
        // TODO add your handling code here:
        
         evt.setSource(serverConsole.MainTabbedPane);
        evt.translatePoint(jScrollPane1.getX()+100, jScrollPane1.getY());
        serverConsole.myMainTabbedPaneMouseMoved(evt);
    }//GEN-LAST:event_reporttableMouseMoved
     public void printControlReport() {
        MsdeConnection msdeConn;
        Connection con;
        ArrayList<POSControlReportPOJO> posControlPOJOs;
     //   String customerCode = "C";
        try {//print 
            msdeConn = new MsdeConnection();
            con = msdeConn.createConnection();
            posControlPOJOs=new ArrayList<POSControlReportPOJO> ();
            POSControlReportPOJO posControlPOJO;
                        
         //    customerCode = customerCode + new SiteMasterDO().getSiteNumberLogicValue(con);

            for(int tableLoopCnt=0;tableLoopCnt<reporttable.getRowCount();tableLoopCnt++){
                posControlPOJO=new POSControlReportPOJO();
                posControlPOJO.setSno(Integer.valueOf(""+reporttable.getValueAt(tableLoopCnt, 0)));
                posControlPOJO.setTransaction(""+reporttable.getValueAt(tableLoopCnt, 1));
                if(reporttable.getValueAt(tableLoopCnt, 1)!=null){
               // if((""+reporttable.getValueAt(tableLoopCnt, 1)).equals("Customer No")){
                //    posControlPOJO.setLastNo(customerCode+""+reporttable.getValueAt(tableLoopCnt, 2));
               // }else{
                posControlPOJO.setLastNo(""+reporttable.getValueAt(tableLoopCnt, 2));
                //System.out.println("lastno"+reporttable.getValueAt(tableLoopCnt, 2));
               // }
                }
                posControlPOJO.setDatafromisr(""+reporttable.getValueAt(tableLoopCnt, 3));
                posControlPOJO.setPostingdate(""+reporttable.getValueAt(tableLoopCnt, 4));
                posControlPOJO.setTransactiondate(""+reporttable.getValueAt(tableLoopCnt, 5));
                posControlPOJO.setTransactiontime(""+reporttable.getValueAt(tableLoopCnt, 6));
                
                posControlPOJOs.add(posControlPOJO);
            }
            
            JFrame f2 = new JFrame("POS Control Report");
            f2.setLocation(200, 40);
            JApplet applet = new POS_ControlReportPrintPreview(posControlPOJOs);
            f2.getContentPane().add("Center", applet);
            applet.init();
            f2.pack();
            f2.setSize(new Dimension(800, 800));
            f2.show();
            con.close();
        } catch (Exception ee) {
            ee.printStackTrace();
        } finally {
            msdeConn = null;
            con = null;

        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PrintButton;
    private javax.swing.JSeparator firstSeparator;
    private javax.swing.JSeparator firstSeparator1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshbutton;
    private javax.swing.JTable reporttable;
    // End of variables declaration//GEN-END:variables
    
}

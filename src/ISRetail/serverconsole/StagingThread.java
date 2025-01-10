/*
 * PosStagingTableStatus.java
 *
 * Created on December 4, 2008, 12:53 PM
 */
package ISRetail.serverconsole;

import ISRetail.configurations.ConfigurationDetails;
import ISRetail.dataarchival.ArchivalExecutionThread;
import posstaging.ConfigurationAutoThread;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.stock.AutoStockDirectDownloadThread;
import java.sql.Connection;
import javax.swing.JTextArea;
import posstaging.ArticleAutoThread;
import posstaging.DataBaseBackupThread;
import posstaging.ErrorLogThread;
import posstaging.FTPCallThread;
import posstaging.FTPFilesDownloadThread;
import posstaging.HighFrequencyResponseThread;
import posstaging.PosStagingThread;
import posstaging.VersionUpdateThread;
import posstaging.StagingEntryErrorReportThread;
import ISRetail.ArticleDataDownload.ArticleDownloadThread;
import ISRetail.GiftVoucherIssualAndRedemptionService.GiftVoucherIssualSentThread;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import comfortcall.ComfortCall;
import posstaging.StockAutoThread;

/**
 *
 * @author enteg
 */
public class StagingThread extends javax.swing.JPanel {

    /**
     * Creates new form PosStagingTableStatus
     */
    ServerConsole serverConsole;

    public StagingThread(ServerConsole serverConsole) {
        initComponents();
        this.serverConsole = serverConsole;
        headingBusDate.setVisible(false);
        busDateScrollPane.setVisible(false);
        BusDateStatus.setVisible(false);
        startServer();
    }

    public JTextArea getBackupdisplayArea() {
        return dbBackupStatus;
    }

    //start : checking not cleared trasactions while pos server closing
    public JTextArea getHighFreResDisplayArea() {
        return transactionResponseStatus;
    }

    //end : checking not cleared trasactions while pos server closing
    public void startServer() {
        Connection con;
        MsdeConnection msdeConn;
        String site;
        PopulateData populateData = null;
        java.io.File currentDir;
        String currentdirectory;
        Runtime rt;
        String digitalInvoiceSend = "";
        Process pr;
        try {
            currentDir = new java.io.File("");
            currentdirectory = currentDir.getAbsolutePath();
            // System.out.println("currentdir::"+currentdirectory);
            String command2 = "cmd /C start " + currentdirectory + "\\launchsstat.bat";
            System.out.println("ST::::" + command2);
            rt = Runtime.getRuntime();
            pr = rt.exec(command2);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Thread articledatathread, threadMaster, threadTransactions, threadResponse, threadBusinessDate, threadConfig, threadBackup, threadversionupdate, threaderrorlog, threadDataARchival, threadFTPCall, threadposentry, stockdirectautodown, stockAutoThread, FTP_FilesDownloadThread, threadComfortMailSend, InvoiceDetails_SentThread, GiftVoucherIssualSentThread, GiftVoucherSMSThread;
        try {
            msdeConn = new MsdeConnection();
            con = msdeConn.createConnection();
            populateData = new PopulateData();
            site = new SiteMasterDO().getSiteId(con);
            digitalInvoiceSend = populateData.getConfigValue(con, "Karnival_Invoice_Send");
            con.close();

//            BusDateStatus.append("\n************Started*************");
//            threadBusinessDate = new Thread(new BusinessDateThread(BusDateStatus));
//            threadBusinessDate.setDaemon(true);
//            threadBusinessDate.start();
            masterDataStatus.append("\n************All Masters Download Started*************");
            threadMaster = new Thread(new ArticleAutoThread(masterDataStatus));
            threadMaster.setDaemon(true);
            threadMaster.start();
//            masterDataStatus.append("\n************Revised UCP Download Started*************");
            articledatathread = new Thread(new ArticleDownloadThread());
            articledatathread.setDaemon(true);
            articledatathread.start();

            // added for automatic stock direct download
            ConfigDataStatus.append("\n************Started*************");
            ConfigurationDetails details = new ConfigurationDetails();
            details.setIV_FLAG_CITY(true);
            details.setIV_FLAG_TITLE(true);
            details.setIV_FLAG_RELATIONSHIP(true);
            details.setIV_FLAG_STATE(true);
            details.setIV_FLAG_COUNTRY(true);
            details.setIV_FLAG_DESIGNATION(true);
            details.setIV_FLAG_EDUCATION(true);
            details.setIV_FLAG_OCCUPATION(true);
            details.setIV_FLAG_MARTIALSTATUS(true);
            details.setIV_FLAG_CUSTOMERCATEGORY(true);
            details.setIV_FLAG_UPDATIONMODE(true);
            details.setIV_FLAG_RETREASON(true);
            details.setIV_FLAG_PAYMENTMODE(true);
            details.setIV_FLAG_CARDTYPES(true);
            details.setIV_FLAG_CURRENCYTYPE(true);
            details.setIV_FLAG_FOLLOWUP(true);
            details.setIV_FLAG_COMMPRIORITY(true);
            details.setIV_FLAG_ADVCANRES(true);
            details.setIV_FLAG_CHARVALUES(true);
            details.setIV_FLAG_BRANDTINT(false);
            details.setIV_FLAG_CANCELREASON(true);
            details.setIV_FLAG_DEFECTREASON(true);
            details.setIV_FLAG_CONDMAP(true);
            details.setIV_FLAG_BILLCANCREASON(true);
            details.setIV_FLAG_POSPARAM(true);
            details.setIV_FLAG_SITES(true);
            details.setIV_FLAG_SGL_ATTRIBUTES(true);
            details.setIV_FLAG_MASTERS(true);
            details.setIV_FLAG_DOCTOR_NAME(true);
            details.setIV_FLAG_DELIV_MERCH(true);
            details.setIV_FLAG_DELIV_ARTICLE(true);
            details.setIV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES(true);
            details.setIV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES(true);
            details.setIV_FLAG_CONTACT_LENS_TYPES(true);
            details.setIV_FLAG_CONTACT_LENS_BASE_CURVE(true);
            details.setIV_FLAG_BRANDTINT(true);
            threadConfig = new Thread(new ConfigurationAutoThread(details, ConfigDataStatus));
            threadConfig.setDaemon(true);
            threadConfig.start();

            transactionStatus.append("\n************Started*************");
            threadTransactions = new Thread(new PosStagingThread(this, transactionStatus));
            threadTransactions.setDaemon(true);
            threadTransactions.start();

            transactionResponseStatus.append("\n************Started*************");
            threadResponse = new Thread(new HighFrequencyResponseThread(transactionResponseStatus));
            threadResponse.setDaemon(true);
            threadResponse.start();

            ErrorTrackerStatus.append("\n************Started*************");
            threaderrorlog = new Thread(new ErrorLogThread(ErrorTrackerStatus));
            threaderrorlog.setDaemon(true);
            threaderrorlog.start();

            dbBackupStatus.append("\n************Started*************");
            threadBackup = new Thread(new DataBaseBackupThread(dbBackupStatus));
            threadBackup.setDaemon(true);
            threadBackup.start();

            versionupdatestatus.append("\n************Started*************");
            threadversionupdate = new Thread(new VersionUpdateThread(versionupdatestatus, "version"));
            threadversionupdate.setDaemon(true);
            threadversionupdate.start();

            DataArchivalStatusArea.append("\n************Started*************");
            threadDataARchival = new Thread(new ArchivalExecutionThread(DataArchivalStatusArea));
            threadDataARchival.setDaemon(true);
            threadDataARchival.start();

            //Added On 06.08.2009 by Srivandana.P
            threadFTPCall = new Thread(new FTPCallThread());
            threadFTPCall.setDaemon(true);
            threadFTPCall.start();
            //End
//Added by Bala on 25.12.2017 for Automatic comfort call details send to same store code through email attachement
            threadComfortMailSend = new Thread(new ComfortCall(versionupdatestatus));
            threadComfortMailSend.setDaemon(true);
            threadComfortMailSend.start();
//            try{
//                threadComfortMailSend.sleep(1000000000);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//Ended by Bala on 25.12.2017 for Automatic comfort call details send to same store code through email attachement
            //Added on 03-02-2010 by sharanya.R
            threadposentry = new Thread(new StagingEntryErrorReportThread());
            threadposentry.setDaemon(true);
            threadposentry.start();

            masterDataStatus.append("\n************Stock Direct Download Started*************");
            stockdirectautodown = new Thread(new AutoStockDirectDownloadThread(masterDataStatus));
            stockdirectautodown.setDaemon(true);
            stockdirectautodown.start();

            masterDataStatus.append("\n************Stock Master Download Started*************");
            stockAutoThread = new Thread(new StockAutoThread(masterDataStatus));
            stockAutoThread.setDaemon(true);
            stockAutoThread.start();

            // Starting Additional file download thread
            FTP_FilesDownloadThread = new Thread(new FTPFilesDownloadThread(versionupdatestatus));
            FTP_FilesDownloadThread.setDaemon(true);
            FTP_FilesDownloadThread.start();
            //Code Added by Balachander V on 17.10.2018 to automatically send the invoice Details to Karnival vendor
            if (Validations.isFieldNotEmpty(digitalInvoiceSend) && digitalInvoiceSend.equalsIgnoreCase("Y")) {
                InvoiceDetails_SentThread = new Thread(new InvoiceDetailsSentThread());
                InvoiceDetails_SentThread.setDaemon(true);
                InvoiceDetails_SentThread.start();
            }
            //Code Ended by Balachander V on 17.10.2018 to automatically send the invoice Details to Karnival vendor

            //Code Added by Balachander V on 1.11.2018 to automatically sent the Gift voucher Details to SAP
            GiftVoucherIssualSentThread = new Thread(new GiftVoucherIssualSentThread());
            GiftVoucherIssualSentThread.setDaemon(true);
            GiftVoucherIssualSentThread.start();
            //Code Ended by Balachander V on 1.11.2018 to automatically sent the Gift voucher Details to SAP

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con = null;
            msdeConn = null;
            site = null;
            threadMaster = null;
            threadConfig = null;
            threadBusinessDate = null;
            threadTransactions = null;
            threadResponse = null;
            threadBackup = null;
        }
    }

    public void appendToBusinessdateSatusArea(String text) {
        BusDateStatus.append(text);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MainDesktopPane = new javax.swing.JDesktopPane();
        headingDataArchival = new javax.swing.JLabel();
        ResponseScrollPane = new javax.swing.JScrollPane();
        transactionResponseStatus = new javax.swing.JTextArea();
        headingTransaction = new javax.swing.JLabel();
        transScrollPane2 = new javax.swing.JScrollPane();
        transactionStatus = new javax.swing.JTextArea();
        headingTransaction2 = new javax.swing.JLabel();
        masterSrollPane = new javax.swing.JScrollPane();
        masterDataStatus = new javax.swing.JTextArea();
        headingBusDate = new javax.swing.JLabel();
        configDataScrollPane = new javax.swing.JScrollPane();
        ConfigDataStatus = new javax.swing.JTextArea();
        headingConfig = new javax.swing.JLabel();
        busDateScrollPane = new javax.swing.JScrollPane();
        BusDateStatus = new javax.swing.JTextArea();
        headingDBBackup = new javax.swing.JLabel();
        dbBackupScrollPane = new javax.swing.JScrollPane();
        dbBackupStatus = new javax.swing.JTextArea();
        versionUpdateScrollPane = new javax.swing.JScrollPane();
        versionupdatestatus = new javax.swing.JTextArea();
        headingTransResponse = new javax.swing.JLabel();
        DataArchScrollPane = new javax.swing.JScrollPane();
        DataArchivalStatusArea = new javax.swing.JTextArea();
        headingVersionUpdate = new javax.swing.JLabel();
        headingErrorlogger = new javax.swing.JLabel();
        ErrTrackerScrollPane = new javax.swing.JScrollPane();
        ErrorTrackerStatus = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        setBackground(new java.awt.Color(153, 204, 255));

        MainDesktopPane.setBackground(new java.awt.Color(153, 204, 255));
        MainDesktopPane.setOpaque(false);
        MainDesktopPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                MainDesktopPaneMouseMoved(evt);
            }
        });

        headingDataArchival.setBackground(new java.awt.Color(88, 109, 148));
        headingDataArchival.setFont(new java.awt.Font("Verdana", 1, 12));
        headingDataArchival.setForeground(new java.awt.Color(227, 243, 254));
        headingDataArchival.setText(" Data Archival Server");
        headingDataArchival.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingDataArchival.setOpaque(true);
        headingDataArchival.setBounds(384, 400, 370, 18);
        MainDesktopPane.add(headingDataArchival, javax.swing.JLayeredPane.DEFAULT_LAYER);

        transactionResponseStatus.setBackground(new java.awt.Color(237, 247, 252));
        transactionResponseStatus.setColumns(20);
        transactionResponseStatus.setEditable(false);
        transactionResponseStatus.setRows(5);
        transactionResponseStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        ResponseScrollPane.setViewportView(transactionResponseStatus);

        ResponseScrollPane.setBounds(384, 227, 370, 170);
        MainDesktopPane.add(ResponseScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingTransaction.setBackground(new java.awt.Color(88, 109, 148));
        headingTransaction.setFont(new java.awt.Font("Verdana", 1, 12));
        headingTransaction.setForeground(new java.awt.Color(227, 243, 254));
        headingTransaction.setText("  Transaction Data Sender (TO PI)");
        headingTransaction.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingTransaction.setOpaque(true);
        headingTransaction.setBounds(10, 210, 370, 18);
        MainDesktopPane.add(headingTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);

        transactionStatus.setBackground(new java.awt.Color(237, 247, 252));
        transactionStatus.setColumns(20);
        transactionStatus.setEditable(false);
        transactionStatus.setRows(5);
        transactionStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        transScrollPane2.setViewportView(transactionStatus);

        transScrollPane2.setBounds(10, 227, 370, 170);
        MainDesktopPane.add(transScrollPane2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingTransaction2.setBackground(new java.awt.Color(88, 109, 148));
        headingTransaction2.setFont(new java.awt.Font("Verdana", 1, 12));
        headingTransaction2.setForeground(new java.awt.Color(227, 243, 254));
        headingTransaction2.setText("  Master Data Downloader");
        headingTransaction2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingTransaction2.setOpaque(true);
        headingTransaction2.setBounds(10, 20, 745, 18);
        MainDesktopPane.add(headingTransaction2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        masterDataStatus.setBackground(new java.awt.Color(237, 247, 252));
        masterDataStatus.setColumns(20);
        masterDataStatus.setEditable(false);
        masterDataStatus.setRows(5);
        masterDataStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        masterSrollPane.setViewportView(masterDataStatus);

        masterSrollPane.setBounds(10, 37, 745, 170);
        MainDesktopPane.add(masterSrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingBusDate.setBackground(new java.awt.Color(88, 109, 148));
        headingBusDate.setFont(new java.awt.Font("Verdana", 1, 12));
        headingBusDate.setForeground(new java.awt.Color(227, 243, 254));
        headingBusDate.setText("  Business Date Downloader");
        headingBusDate.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingBusDate.setOpaque(true);
        headingBusDate.setBounds(10, 20, 370, 18);
        MainDesktopPane.add(headingBusDate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ConfigDataStatus.setBackground(new java.awt.Color(237, 247, 252));
        ConfigDataStatus.setColumns(20);
        ConfigDataStatus.setEditable(false);
        ConfigDataStatus.setRows(5);
        ConfigDataStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        ConfigDataStatus.setName("config"); // NOI18N
        ConfigDataStatus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ConfigDataStatusMouseMoved(evt);
            }
        });
        configDataScrollPane.setViewportView(ConfigDataStatus);

        configDataScrollPane.setBounds(760, 37, 370, 170);
        MainDesktopPane.add(configDataScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingConfig.setBackground(new java.awt.Color(88, 109, 148));
        headingConfig.setFont(new java.awt.Font("Verdana", 1, 12));
        headingConfig.setForeground(new java.awt.Color(227, 243, 254));
        headingConfig.setText("  Configuration Master Downloader");
        headingConfig.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingConfig.setOpaque(true);
        headingConfig.setBounds(760, 20, 370, 18);
        MainDesktopPane.add(headingConfig, javax.swing.JLayeredPane.DEFAULT_LAYER);

        BusDateStatus.setBackground(new java.awt.Color(237, 247, 252));
        BusDateStatus.setColumns(20);
        BusDateStatus.setEditable(false);
        BusDateStatus.setRows(5);
        BusDateStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        busDateScrollPane.setViewportView(BusDateStatus);

        busDateScrollPane.setBounds(10, 37, 370, 170);
        MainDesktopPane.add(busDateScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingDBBackup.setBackground(new java.awt.Color(88, 109, 148));
        headingDBBackup.setFont(new java.awt.Font("Verdana", 1, 12));
        headingDBBackup.setForeground(new java.awt.Color(227, 243, 254));
        headingDBBackup.setText(" Database Backup Server");
        headingDBBackup.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingDBBackup.setOpaque(true);
        headingDBBackup.setBounds(10, 400, 370, 18);
        MainDesktopPane.add(headingDBBackup, javax.swing.JLayeredPane.DEFAULT_LAYER);

        dbBackupStatus.setBackground(new java.awt.Color(237, 247, 252));
        dbBackupStatus.setColumns(20);
        dbBackupStatus.setEditable(false);
        dbBackupStatus.setRows(5);
        dbBackupStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        dbBackupScrollPane.setViewportView(dbBackupStatus);

        dbBackupScrollPane.setBounds(10, 417, 370, 170);
        MainDesktopPane.add(dbBackupScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        versionupdatestatus.setBackground(new java.awt.Color(237, 247, 252));
        versionupdatestatus.setColumns(20);
        versionupdatestatus.setEditable(false);
        versionupdatestatus.setRows(5);
        versionupdatestatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        versionupdatestatus.setName("version"); // NOI18N
        versionUpdateScrollPane.setViewportView(versionupdatestatus);

        versionUpdateScrollPane.setBounds(760, 417, 370, 170);
        MainDesktopPane.add(versionUpdateScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingTransResponse.setBackground(new java.awt.Color(88, 109, 148));
        headingTransResponse.setFont(new java.awt.Font("Verdana", 1, 12));
        headingTransResponse.setForeground(new java.awt.Color(227, 243, 254));
        headingTransResponse.setText(" Transaction Status Receiver (From ISR)");
        headingTransResponse.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingTransResponse.setOpaque(true);
        headingTransResponse.setBounds(384, 210, 370, 18);
        MainDesktopPane.add(headingTransResponse, javax.swing.JLayeredPane.DEFAULT_LAYER);

        DataArchivalStatusArea.setBackground(new java.awt.Color(237, 247, 252));
        DataArchivalStatusArea.setColumns(20);
        DataArchivalStatusArea.setEditable(false);
        DataArchivalStatusArea.setRows(5);
        DataArchivalStatusArea.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        DataArchScrollPane.setViewportView(DataArchivalStatusArea);

        DataArchScrollPane.setBounds(384, 417, 370, 170);
        MainDesktopPane.add(DataArchScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingVersionUpdate.setBackground(new java.awt.Color(88, 109, 148));
        headingVersionUpdate.setFont(new java.awt.Font("Verdana", 1, 12));
        headingVersionUpdate.setForeground(new java.awt.Color(227, 243, 254));
        headingVersionUpdate.setText(" Version : Patch Updation Server");
        headingVersionUpdate.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingVersionUpdate.setOpaque(true);
        headingVersionUpdate.setBounds(760, 400, 370, 18);
        MainDesktopPane.add(headingVersionUpdate, javax.swing.JLayeredPane.DEFAULT_LAYER);

        headingErrorlogger.setBackground(new java.awt.Color(88, 109, 148));
        headingErrorlogger.setFont(new java.awt.Font("Verdana", 1, 12));
        headingErrorlogger.setForeground(new java.awt.Color(227, 243, 254));
        headingErrorlogger.setText(" Master / Transaction Error Tracker");
        headingErrorlogger.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(203, 243, 245), new java.awt.Color(88, 109, 148)));
        headingErrorlogger.setOpaque(true);
        headingErrorlogger.setBounds(760, 210, 370, 18);
        MainDesktopPane.add(headingErrorlogger, javax.swing.JLayeredPane.DEFAULT_LAYER);

        ErrorTrackerStatus.setBackground(new java.awt.Color(237, 247, 252));
        ErrorTrackerStatus.setColumns(20);
        ErrorTrackerStatus.setEditable(false);
        ErrorTrackerStatus.setRows(5);
        ErrorTrackerStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        ErrorTrackerStatus.setName("master"); // NOI18N
        ErrorTrackerStatus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ErrorTrackerStatusMouseMoved(evt);
            }
        });
        ErrTrackerScrollPane.setViewportView(ErrorTrackerStatus);

        ErrTrackerScrollPane.setBounds(760, 227, 370, 170);
        MainDesktopPane.add(ErrTrackerScrollPane, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jSeparator1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(88, 109, 148), 1, true));
        jSeparator1.setBounds(10, 610, 1120, 3);
        MainDesktopPane.add(jSeparator1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(MainDesktopPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1192, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(223, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(MainDesktopPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 649, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ConfigDataStatusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConfigDataStatusMouseMoved
        // TODO add your handling code here:
        //System.out.println("ConfigData.getX() "+configDataScrollPane.getX());
        evt.setSource(serverConsole.MainTabbedPane);
        evt.translatePoint(configDataScrollPane.getX(), configDataScrollPane.getY());
        serverConsole.myMainTabbedPaneMouseMoved(evt);
    }//GEN-LAST:event_ConfigDataStatusMouseMoved

    private void MainDesktopPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainDesktopPaneMouseMoved
        // TODO add your handling code here:

        evt.setSource(serverConsole.MainTabbedPane);
        serverConsole.myMainTabbedPaneMouseMoved(evt);

    }//GEN-LAST:event_MainDesktopPaneMouseMoved

    private void ErrorTrackerStatusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ErrorTrackerStatusMouseMoved
        // TODO add your handling code here:
        evt.setSource(serverConsole.MainTabbedPane);
        evt.translatePoint(ErrTrackerScrollPane.getX(), ErrTrackerScrollPane.getY());
        serverConsole.myMainTabbedPaneMouseMoved(evt);
    }//GEN-LAST:event_ErrorTrackerStatusMouseMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea BusDateStatus;
    private javax.swing.JTextArea ConfigDataStatus;
    private javax.swing.JScrollPane DataArchScrollPane;
    private javax.swing.JTextArea DataArchivalStatusArea;
    private javax.swing.JScrollPane ErrTrackerScrollPane;
    private javax.swing.JTextArea ErrorTrackerStatus;
    private javax.swing.JDesktopPane MainDesktopPane;
    private javax.swing.JScrollPane ResponseScrollPane;
    private javax.swing.JScrollPane busDateScrollPane;
    private javax.swing.JScrollPane configDataScrollPane;
    private javax.swing.JScrollPane dbBackupScrollPane;
    private javax.swing.JTextArea dbBackupStatus;
    private javax.swing.JLabel headingBusDate;
    private javax.swing.JLabel headingConfig;
    private javax.swing.JLabel headingDBBackup;
    private javax.swing.JLabel headingDataArchival;
    private javax.swing.JLabel headingErrorlogger;
    private javax.swing.JLabel headingTransResponse;
    private javax.swing.JLabel headingTransaction;
    private javax.swing.JLabel headingTransaction2;
    private javax.swing.JLabel headingVersionUpdate;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea masterDataStatus;
    private javax.swing.JScrollPane masterSrollPane;
    private javax.swing.JScrollPane transScrollPane2;
    private javax.swing.JTextArea transactionResponseStatus;
    private javax.swing.JTextArea transactionStatus;
    private javax.swing.JScrollPane versionUpdateScrollPane;
    private javax.swing.JTextArea versionupdatestatus;
    // End of variables declaration//GEN-END:variables
}

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
 * Start up Class for the POS Server Which Checks for the Business Date
 * 
 * 
 */
package ISRetail.serverconsole;

import ISRetail.utility.db.runwaycon;
import ISRetail.Helpers.CopyFiles;
import ISRetail.ServerRmi.StartRMIServer;
import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.HttpsWebservice.WebServiceKeyStore;
import ISRetail.Webservices.MsdeConnectionDetailsPojo;
import ISRetail.billing.BillingDO;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.utility.db.GetSiteDescription;
import ISRetail.utility.db.PopulateData;
import VersionManagement.VersionManagementDO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import posstaging.CheckSystemDate;
import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class CheckBusinessDate extends javax.swing.JFrame {

    JTextArea businessdate;
    int counttransactpwd = 0;
    int countcorppasswd = 0;
    String manualdategiven;

    /** Creates new form CheckBusinessDate */
    public CheckBusinessDate() {
        runwaycon.getRunwayconDetails();
        SplScr3.showStartUpScreen();
        
        copywsdlfiles();

        RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
        final int runtimePid = Integer.parseInt(rt.getName().substring(0, rt.getName().indexOf("@")));
        boolean issingleton = getMonitoredVMs(runtimePid);

        SplScr3.endStartUpScreen();


        if (issingleton) {
            initComponents();
            initialcheck();


        } else {
            JOptionPane.showMessageDialog(null, "This Server Application is already running.");
            System.exit(0);
        }
        rt = null;
    }

    public void versionCheck() {
        String databaseVersion = null;
        VersionManagementDO vmdo = null;
        MsdeConnection msde = null;
        Connection con = null;
        java.io.File currentDir = null;
        String presentDirectory;
        PopulateData pd = null;
        boolean isStopRequired = false;
        FileWriter fw = null;
        File file = null;
        String ftp_send = null;
        String ftp_receive = null;
        String ftp_remove = null;
        boolean ftpsend = false;
        boolean ftpreceive = false;
        boolean ftpremove = false;
        //StringBuffer ftpstr = null;
        try {
            pd = new PopulateData();
            vmdo = new VersionManagementDO();
            msde = new MsdeConnection();
            msde.checkConnection();
            con = msde.createConnection();
            databaseVersion = vmdo.getCurrentVersion(con);
            currentDir = new java.io.File("");
            System.out.println("File" + currentDir.getAbsolutePath());
            presentDirectory = currentDir.getAbsolutePath();
            file = new File("FTPDownload.bat");
            fw = new FileWriter(file);
            ftp_send = pd.getFTPSend(con);
            ftp_receive = pd.getFTPReceive(con);
            ftp_remove = pd.getFTPRemove(con);
            //ftpstr = new StringBuffer();
            if (ftp_send != null && ftp_send.equalsIgnoreCase("Y")) {
                ftpsend = true;
            }
            if (ftp_receive != null && ftp_receive.equalsIgnoreCase("Y")) {
                ftpreceive = true;
            }
            if (ftp_remove != null && ftp_remove.equalsIgnoreCase("Y")) {
                ftpremove = true;
            }
            fw.write("cd FTPDownload\n");

            fw.write("java -jar FTPDownload.jar " + MsdeConnection.getConnectstring() + " " + MsdeConnection.getUsername() + " " + MsdeConnection.getPassword());

            if (databaseVersion != null && !databaseVersion.trim().equalsIgnoreCase(ServerConsole.currentversion)) {
                fw.write(" y");
            } else {
                fw.write(" n");
            }
            if (ftpsend) {
                fw.write(" y");
            } else {
                fw.write(" n");
            }
            if (ftpreceive) {
                fw.write(" y");
            } else {
                fw.write(" n");
            }
            if (ftpremove) {
                fw.write(" y");
            } else {
                fw.write(" n");
            }
            fw.write(" y\n");
            fw.close();
            if ((databaseVersion != null && !databaseVersion.trim().equalsIgnoreCase(ServerConsole.currentversion))) {
                System.out.print("Operating System: ");
                System.out.println(System.getProperty("os.name"));
                System.out.println("Hello!!");
                if (System.getProperty("os.name").equalsIgnoreCase("Windows 10") || System.getProperty("os.name").equalsIgnoreCase("Windows 11")) {//added by charan for version release of windows 11
                    try {
                        System.out.println("Inside Windows 10/11 version code");
                        System.out.println("Running the updation process");
                        String command = "cmd /C start /B " + presentDirectory + "\\FTPDownload.bat";
                        Runtime run = Runtime.getRuntime();
                        run.exec(command);
                        String updateinfo = "Updated";
                        System.out.println(updateinfo);
                        if (updateinfo.equalsIgnoreCase("Updated")) {
                            String[] command1 = {"taskkill", "/f", "/im", "cmd.exe"};
                            Runtime run1 = Runtime.getRuntime();
                            run1.exec(command1);
                            System.out.println("Closing the version updation");
                        }
                        System.out.println("After Windows 10/11 code");
                        System.out.println("Closing Server");

                        isStopRequired = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } //charan end
                else {
                    System.out.println("Starting Auto FTP download");
                    String command = "cmd /C start /B " + presentDirectory + "\\launchftpdownload.bat";
                    Runtime rt2 = Runtime.getRuntime();
//              execution with batch file
                    Process pr2 = rt2.exec(command);
                    InputStream i = pr2.getInputStream();
                    StringBuilder sb = new StringBuilder();
                    for (int c = 0; (c = i.read()) > -1;) {
                        sb.append((char) c);
                    }
                    i.close();
                    System.out.println("Inside Previous Windows version code");
                    System.out.println("Result : " + sb.toString());
                    System.out.println("Closing Server");
                    isStopRequired = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            databaseVersion = null;
            vmdo = null;
            msde = null;
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                }
                if (fw != null) {
                    fw.close();
                }
            } catch (Exception e) {
            }
            con = null;
            fw = null;
            file = null;
            //ftpstr = null;
        }
        if (isStopRequired) {
            System.out.println("Server Closed");
            System.exit(0);
        }
    }

    public void checkbusinessdate() {
        Thread checkbusinessdatethread;
        checkbusinessdatethread = new Thread(new CheckBusinessDateThread());
        checkbusinessdatethread.setDaemon(true);
        checkbusinessdatethread.start();
        Thread checksystemdate;
        checksystemdate = new Thread(new CheckSystemdateThread(checkbusinessdatethread));
        checksystemdate.setDaemon(true);
        checksystemdate.start();
//        try{
//             MsdeConnection msdeConnection = new MsdeConnection();
//        Connection con = null;
//         con = msdeConnection.createConnection();
//            BillingDO bdo=new BillingDO();
//            ArrayList<String> invlist=new ArrayList<String>();
//                    invlist=bdo.getInvoiceNoToSent(con, "N");
//            final String invoicelist[]=invlist.toArray(new String[invlist.size()]);
//           if(invoicelist.length>0){
//            Thread t1=new Thread(new Runnable() {
//                 @Override
//                 public void run() {
//                    // t1.sleep(10000);
//                    if(invoicelist.length>0){
//                //for(int i=0;i<invoicelist.length;i++){
//                    POS.main(invoicelist);
//               // }
//                
//            }
//                 }
//             });
//            t1.start();
//           // t1.sleep(100000);
//           }
//            
////        String[] incoiceno = {"BAA0008290"};
////        POS.main(incoiceno);
//        System.out.println("*************It's working***************");
//        }catch(Exception e){
//            e.printStackTrace();
//        }
        
    }

    public void initialcheck() {

        try {
            BackgroundPropertiesFromFile bf = new BackgroundPropertiesFromFile();
            MsdeConnectionDetailsPojo msdeConnectionDetailsPojo;
            msdeConnectionDetailsPojo = bf.getMsdeConnectionDetails();
            String connectstring1 = "";
            if (msdeConnectionDetailsPojo.getDbname().equalsIgnoreCase("null")) {
                connectstring1 = "jdbc:sqlserver://" + msdeConnectionDetailsPojo.getHostname() + ":" + msdeConnectionDetailsPojo.getPortno();
            } else {
                connectstring1 = "jdbc:sqlserver://" + msdeConnectionDetailsPojo.getHostname() + ":" + msdeConnectionDetailsPojo.getPortno() + ";databaseName=" + msdeConnectionDetailsPojo.getDbname();
            }

            
            MsdeConnection.setConnectstring(connectstring1);
            MsdeConnection.setUsername(msdeConnectionDetailsPojo.getUsername());
            MsdeConnection.setPassword(msdeConnectionDetailsPojo.getPassword());
            bf.getXIConnectionDetails();
             //added by ravi thota on 31.05.2013 to restrict closing pos server before pos client
            StartRMIServer.HOST_NAME = msdeConnectionDetailsPojo.getHostname();


            //Added by Dileep - 07.08.2014 to set the keystore for Https web service Request
            WebServiceKeyStore keystoreobj = new WebServiceKeyStore();
            String serverPath = System.getProperty("user.dir");
            serverPath = serverPath.replace("POSServer", "POSServer\\config\\Isr_Pos_BackGround_Colors.properties");
            keystoreobj.propertiesEdit(serverPath);
            String clientPath = serverPath.replace("POSServer\\config\\Isr_Pos_BackGround_Colors.properties", "POSClient\\config\\Isr_Pos_BackGround_Colors.properties");
            keystoreobj.propertiesEdit(clientPath);
            WebServiceKeyStore.keyStoreValues();
            keystoreobj.setKeyStoreSettings();
            keystoreobj.keyStoreValidatidityCheck();
            //End: Added by Dileep - 07.08.2014



        } catch (Exception e) {
            e.printStackTrace();

        }
        versionCheck();
        //  this.setVisible(true);
        // sendFilesToFTPServer();
        //  this.setVisible(false);
        checkandupdatestartupflag();
        checkbusinessdate();

        //  new CheckBusinessDate().setVisible(true);

    }

    private void checkandupdatestartupflag() {
        MsdeConnection msdeConnection;
        Connection con = null;
        CheckBusinessDateDO checkBusinessDateDO;
        CheckSystemDate checkSystemDate;
        SiteMasterPOJO siteMasterPOJO;
        String flag = "";
        try {
            msdeConnection = new MsdeConnection();
            msdeConnection.checkConnection();


            con = msdeConnection.createConnection();
            checkBusinessDateDO = new CheckBusinessDateDO();
            checkSystemDate = new CheckSystemDate();
            siteMasterPOJO = new SiteMasterPOJO();
            siteMasterPOJO = checkBusinessDateDO.getCheckSiteBlock(con);
            //if(siteMasterPOJO!=null){
// if(!siteMasterPOJO.getDeletionInd().equalsIgnoreCase("X")){
            if (!checkBusinessDateDO.checkatserverstart(con)) {

                checkSystemDate.getBusinessDateFromWebserviceforinitialbuild();
                //flag=checkSystemDate.getBusinessDate();
            }
// }else{

// JOptionPane.showMessageDialog(null,"Site" +" "+siteMasterPOJO.getSiteid()+" is blocked!","Error Message",JOptionPane.ERROR_MESSAGE);
// System.exit(0);
// }}else{

// JOptionPane.showMessageDialog(null,"The Site details are not available!","Error Message",JOptionPane.ERROR_MESSAGE);
// }
        } catch (Exception e) {
        } finally {
            try {
                msdeConnection = null;
                con.close();
                con = null;
                checkBusinessDateDO = null;
                checkSystemDate = null;
            } catch (SQLException ex) {
            }

        }



    }

    private static boolean getMonitoredVMs(int processPid) {

        MonitoredHost host;
        Set vms;
        HostIdentifier hostiden;
        try {
            hostiden = new HostIdentifier((String) null);
            host = MonitoredHost.getMonitoredHost(hostiden);
            vms = host.activeVms();
        } catch (java.net.URISyntaxException sx) {
            throw new InternalError(sx.getMessage());
        } catch (MonitorException mx) {
            throw new InternalError(mx.getMessage());
        }


        MonitoredVm mvm = null;
        String processName = null;
        try {
            mvm = host.getMonitoredVm(new VmIdentifier(String.valueOf(processPid)));
            processName = MonitoredVmUtil.commandLine(mvm);
            processName = processName.substring(processName.lastIndexOf("\\") + 1, processName.length());
            mvm.detach();
        } catch (Exception ex) {
            ex.printStackTrace(); // added on 28 Jun 2011 for printing the exception
        }
        // This line is just to verify the process name. It can be removed. 
        //  JOptionPane.showMessageDialog(null,processName);  
        for (Object vmid : vms) {
            if (vmid instanceof Integer) {
                int pid = ((Integer) vmid).intValue();
                String name = vmid.toString(); // default to pid if name not available  
                try {
                    mvm = host.getMonitoredVm(new VmIdentifier(name));
                    // use the command line as the display name  
                    name = MonitoredVmUtil.commandLine(mvm);

                    name = name.substring(name.lastIndexOf("\\") + 1, name.length());
                    mvm.detach();
                    if ((name.equalsIgnoreCase(processName)) && (processPid != pid)) {
                        return false;
                    }
                } catch (Exception x) {
                    //ignore
                    System.out.println("Ignore the exception");// added on 28 Jun 2011 for printing the exception
                    x.printStackTrace(); // added on 28 Jun 2011 for printing the exception
                }
            }
        }

        return true;
    }

    
    
    private void copywsdlfiles() {
        File ff = new File("c:/POS_WSDLS_new");
        if (!ff.exists()) {
            ff.mkdir();
        }

        ff = new File("");
        String currdir = ff.getAbsolutePath();
        ff = new File(currdir + "/wsdlfiles");
        File[] allfiles = ff.listFiles();
        String wfname1 = "";
        String wfname2 = "";
        CopyFiles cfiles = new CopyFiles();
        for (int i = 0; i < allfiles.length; i++) {
            try {
                wfname1 = allfiles[i].toString();
                wfname2 = "c:/POS_WSDLS_new/" + allfiles[i].getName();
                cfiles.copyFile(wfname1, wfname2);
            } catch (Exception ex) {
            }
        }

    }

//    public boolean checkcorporatepwd()
//    {
//        try
//        {          if(countcorppasswd<3)
//                   {
//                    transacpwd.setText("");
//                    int optionSelected1 = JOptionPane.showOptionDialog(this, transacpwd, "Enter Corporate Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
//                   
//                    String corppwd = transacpwd.getText();
//                    if (optionSelected1 == JOptionPane.OK_OPTION) {
//                      // String transacctionpwd=checkBusinessDateDO.getTransactionPwd(con);
//                        if(corppwd.equalsIgnoreCase("TITAN"))
//                        {
//                          return true;
//                        }
//                        else
//                        {
//                        JOptionPane.showMessageDialog(null,"The Password entered is incorrect");
//                       countcorppasswd++;
//                        boolean pwd=checkcorporatepwd();
//                        return pwd;
//                        }
//                        } 
//                    else {
//                       return false;
//                    }
//                   }else
//                   {
//                   System.exit(0);
//                   return false;
//                   }
//    
//        } catch (Exception e) {
//            return false;
//        }
//    
//    }
//   public boolean checktransactionpwd(Connection con)
//    {
//      
//        CheckBusinessDateDO checkBusinessDateDO=new CheckBusinessDateDO(); 
//           
//        try
//        {      
//            String transacctionpwd=checkBusinessDateDO.getTransactionPwd(con);
//            if(counttransactpwd<=3)
//                   {
//                    transacpwd.setText("");
//                    int optionSelected1 = JOptionPane.showOptionDialog(this, transacpwd, "Enter Transaction Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
//                   
//                    String tranppwd = transacpwd.getText();
//                    if (optionSelected1 == JOptionPane.OK_OPTION) {
//                     
//                        if(tranppwd.equalsIgnoreCase(transacctionpwd))
//                        {
//                          updatewithmanualdate(con,manualdategiven);
//                          
//                          System.out.println("success");
//                           new ServerConsole().setVisible(true);
//                           ServerConsole.setBusinessDate();
//                          return true;
//                          
//                        }
//                        else
//                        {
//                        JOptionPane.showMessageDialog(null,"The Password entered is incorrect");
//                       counttransactpwd++;
//                        boolean pwd=checktransactionpwd(con);
//                        return pwd;
//                        }
//                        } 
//                    else {
//                       return false;
//                    }
//                   }else
//                   {
//                   System.exit(0);
//                   return false;
//                   }
//    
//        } catch (Exception e) {
//            return false;
//        }
//    
//    }
//   
//   public void updatewithmanualdate(Connection con,String businessdate)
//   {
//       try
//       {
//   CheckBusinessDateDO checkBusinessDateDO=new CheckBusinessDateDO();
//   int budate=ConvertDate.getDateNumeric(businessdate,"dd/MM/yyyy");
//  
//   System.out.println("mmdate"+budate);
//   checkBusinessDateDO.updateSystemAndPostingDates(con,budate);
//       }catch(Exception e)
//       {
//       e.printStackTrace();
//       }
//   }
//    
//   public boolean validatemanualdate(Connection con)
//   {
//   try
//   {
//   int optionSelected = JOptionPane.showOptionDialog(this, manualdate, "Enter the Manual Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
//                  
//                    if (optionSelected == JOptionPane.OK_OPTION) {
//                         String mandate = manualdate.getText();
//                        if(Validations.validateDate(mandate))
//                        {
//                           manualdategiven= manualdate.getText();
//                             return true;
//                        }
//                        else
//                        {
//                        JOptionPane.showMessageDialog(null,"Please enter a valid date(DD/MM/YYYY)");
//                        boolean flag =validatemanualdate(con);
//                        return flag;
//                        }
//                        } 
//                    else
//                    {
//                    return false;
//                    }
//   }catch(Exception e)
//   {
//   e.printStackTrace();
//   return false;
//   }
//   }
//   
//    public  void manualdatemethod(Connection con,String businessdate)
//    {
//  try {
//      //System date - businessdate=1 then allow to login
//     DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//     java.util.Date date = new java.util.Date();
//     System.out.println("Current Date: " + dateFormat.format(date));
//     long difference=Validations.getDiffbtweendates(businessdate, dateFormat.format(date)); 
//     System.out.println("difference:"+difference);
//     if(difference==1)
//     {
//     new ServerConsole().setVisible(true);
//     }
//     else if(difference>1 && difference<=3)
//     {
//      
//    boolean manndate=validatemanualdate(con);
//    if(manndate)
//    {
//    boolean trspwd=checktransactionpwd(con);
//    if(trspwd)
//    {
//     System.out.println("transactionsuccess");
//    updatewithmanualdate(con,manualdategiven);
//    }
//    }
//              
//     }
//     else if(difference>3)
//     {
//   //  JOptionPane.showMessageDialog(null,"The system is block");
//     boolean corppasswd=checkcorporatepwd();
//     if(corppasswd)
//     {
//      boolean manndate=validatemanualdate(con);
//     if(manndate)
//    {
//    checktransactionpwd(con);
//    
//     }
//     }
//     }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    //transacpwdframe.setVisible(true);
//        
//        //JOptionPane.showInputDialog(null,"Enter Password","Enter Password",JOptionPane.OK_OPTION);
//    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        manualdate = new javax.swing.JTextField();
        transacpwd = new javax.swing.JPasswordField();
        corppasswd = new javax.swing.JPasswordField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        transacpwdframe = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        corppwdframe = new javax.swing.JInternalFrame();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        corppwdcontinuebutton = new javax.swing.JButton();
        setbusinessdateframe = new javax.swing.JInternalFrame();
        jLabel3 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        manualdate.setFont(new java.awt.Font("Verdana", 1, 12));
        manualdate.setText("DD/MM/YYYY");
        manualdate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                manualdateFocusGained(evt);
            }
        });

        transacpwd.setText("jPasswordField1");

        corppasswd.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jDesktopPane1.setBackground(new java.awt.Color(153, 204, 255));

        transacpwdframe.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel1.setText("Enter the Transaction Password");

        jButton1.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton1.setText("Continue");

        org.jdesktop.layout.GroupLayout transacpwdframeLayout = new org.jdesktop.layout.GroupLayout(transacpwdframe.getContentPane());
        transacpwdframe.getContentPane().setLayout(transacpwdframeLayout);
        transacpwdframeLayout.setHorizontalGroup(
            transacpwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(transacpwdframeLayout.createSequentialGroup()
                .add(transacpwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(transacpwdframeLayout.createSequentialGroup()
                        .add(24, 24, 24)
                        .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 249, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 158, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(transacpwdframeLayout.createSequentialGroup()
                        .add(157, 157, 157)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        transacpwdframeLayout.setVerticalGroup(
            transacpwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(transacpwdframeLayout.createSequentialGroup()
                .add(50, 50, 50)
                .add(transacpwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jTextField1)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .add(41, 41, 41)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        transacpwdframe.setBounds(0, 20, 480, 220);
        jDesktopPane1.add(transacpwdframe, javax.swing.JLayeredPane.DEFAULT_LAYER);

        corppwdframe.setBackground(new java.awt.Color(153, 204, 255));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel2.setText("Enter the corporate password");

        corppwdcontinuebutton.setFont(new java.awt.Font("Verdana", 1, 12));
        corppwdcontinuebutton.setText("Continue");

        org.jdesktop.layout.GroupLayout corppwdframeLayout = new org.jdesktop.layout.GroupLayout(corppwdframe.getContentPane());
        corppwdframe.getContentPane().setLayout(corppwdframeLayout);
        corppwdframeLayout.setHorizontalGroup(
            corppwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(corppwdframeLayout.createSequentialGroup()
                .add(corppwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(corppwdframeLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 210, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 169, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(corppwdframeLayout.createSequentialGroup()
                        .add(143, 143, 143)
                        .add(corppwdcontinuebutton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 132, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(43, Short.MAX_VALUE))
        );
        corppwdframeLayout.setVerticalGroup(
            corppwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(corppwdframeLayout.createSequentialGroup()
                .add(50, 50, 50)
                .add(corppwdframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 24, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(44, 44, 44)
                .add(corppwdcontinuebutton)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        corppwdframe.setBounds(10, 17, 460, 220);
        jDesktopPane1.add(corppwdframe, javax.swing.JLayeredPane.DEFAULT_LAYER);

        setbusinessdateframe.setBackground(new java.awt.Color(153, 204, 255));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 12));
        jLabel3.setText("Enter Todays date");

        jTextField3.setFont(new java.awt.Font("Verdana", 1, 12));
        jTextField3.setText("DD/MM/YYYY");

        jButton2.setFont(new java.awt.Font("Verdana", 1, 12));
        jButton2.setText("SET BUSINESS DATE");

        org.jdesktop.layout.GroupLayout setbusinessdateframeLayout = new org.jdesktop.layout.GroupLayout(setbusinessdateframe.getContentPane());
        setbusinessdateframe.getContentPane().setLayout(setbusinessdateframeLayout);
        setbusinessdateframeLayout.setHorizontalGroup(
            setbusinessdateframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(setbusinessdateframeLayout.createSequentialGroup()
                .add(setbusinessdateframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(setbusinessdateframeLayout.createSequentialGroup()
                        .add(71, 71, 71)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 154, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(setbusinessdateframeLayout.createSequentialGroup()
                        .add(117, 117, 117)
                        .add(jButton2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 188, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        setbusinessdateframeLayout.setVerticalGroup(
            setbusinessdateframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(setbusinessdateframeLayout.createSequentialGroup()
                .add(59, 59, 59)
                .add(setbusinessdateframeLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(29, 29, 29)
                .add(jButton2)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        setbusinessdateframe.setBounds(30, 20, 410, 210);
        jDesktopPane1.add(setbusinessdateframe, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 491, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jDesktopPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void manualdateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_manualdateFocusGained
        // TODO add your handling code here:
        //  manualdate.setText("");
    }//GEN-LAST:event_manualdateFocusGained

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                
                //SplScr3.showStartUpScreen();
            }
        });
        
        new CheckBusinessDate();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField corppasswd;
    private javax.swing.JButton corppwdcontinuebutton;
    private javax.swing.JInternalFrame corppwdframe;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField manualdate;
    private javax.swing.JInternalFrame setbusinessdateframe;
    private javax.swing.JPasswordField transacpwd;
    private javax.swing.JInternalFrame transacpwdframe;
    // End of variables declaration//GEN-END:variables
}

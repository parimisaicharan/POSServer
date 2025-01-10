/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.db.PopulateData;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.sql.Connection;
import java.util.Set;
import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

/**
 *
 * @author Administrator
 */
public class FTPCallThread implements Runnable {

    public void run() {
        MsdeConnection msde = null;
        Connection con = null;
        java.io.File currentDir = null;
        String presentDirectory;
        PopulateData pd = null;
        FileWriter fw = null;
        File file = null;
        String ftp_send = null;
        String ftp_receive = null;
        String ftp_remove = null;
        boolean ftpsend = false;
        boolean ftpreceive = false;
        boolean ftpremove = false;
        int freq = 0;

        while (true) {
            RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean();
            final int runtimePid = Integer.parseInt(rt.getName().substring(0, rt.getName().indexOf("@")));
            boolean issingleton = getMonitoredVMs(runtimePid);
            try {
                ftpsend = false;
                ftpreceive = false;
                ftpremove = false;
                pd = new PopulateData();
                msde = new MsdeConnection();
                msde.checkConnection();
                con = msde.createConnection();
                currentDir = new java.io.File("");
                System.out.println("File" + currentDir.getAbsolutePath());
                presentDirectory = currentDir.getAbsolutePath();
                file = new File("FTPDownload.bat");
                fw = new FileWriter(file);
                ftp_send = pd.getFTPSend(con);
                ftp_receive = pd.getFTPReceive(con);
                ftp_remove = pd.getFTPRemove(con);
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
                // if (databaseVersion != null && !databaseVersion.trim().equalsIgnoreCase(ServerConsole.currentversion)) {
                //fw.write(" y");
                //} else {                
                {
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
                fw.write(" n\n");
                fw.write("Exit\n");
                if (fw != null) {
                        fw.close();
                }                
                if (((ftpsend) || (ftpreceive) || (ftpremove)) && issingleton) {
                    waitAndExecuteAutoCloseProcess("cmd /C start /B " + presentDirectory + "\\launchftpdownload.bat");
                }
                freq = PosConfigParamDO.getVersionCheckReqFreq(con);
            } catch (Exception e) {
                e.printStackTrace();
                freq = PosConfigParamDO.getVersionCheckReqFreq(con);
            } finally {

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
                try {
                    Thread.sleep(freq);
                } catch (Exception ec) {
                }
            }
        }
    }

    private static boolean getMonitoredVMs(int processPid) {
        MonitoredHost host;
        Set vms;
        try {
            host = MonitoredHost.getMonitoredHost(new HostIdentifier((String) null));
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
                // ignore  
                }
            }
        }

        return true;
    }

    public void waitAndExecuteAutoCloseProcess(String text) {
        try {
            writeToLocalBatFile(text);
           // String command2 = "c:\\b.bat";
            String command2 = "c:\\dbbackup-ftp\\b.bat";
            MyThread th = new MyThread(command2, true);
            Thread thread = new Thread(th);
            thread.setDaemon(true);
            thread.start();
            thread.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void writeToLocalBatFile(String... str) {
        try {
              //Code added on Jan 5th 2011 for Windows 7 c: copy issue for bat files
            File file1=new File("c:\\dbbackup-ftp");
                boolean exists = file1.exists();
            if (!exists) {
               boolean success = (new File("C:\\dbbackup-ftp")).mkdir();  
            }
        //End of Code added on Jan 5th 2011 for Windows 7 c: copy issue for bat files
            PrintWriter output = null;
          //  File file = new File("c:\\b.bat");
            File file = new File("c:\\dbbackup-ftp\\b.bat");
            output = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < str.length; i++) {
                output.println(str[i]);
            }
            output.println("EXIT");
            output.flush();
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    class MyThread implements Runnable {

        boolean running = true;
        String command;
        boolean wait = true;

        public MyThread(String command, boolean wait) {
            this.command = command;
            this.wait = wait;
            System.out.println("command=" + command);
        }

        public void run() {
            System.out.println("Mythread...");
            Runtime rt = Runtime.getRuntime();
            try {
                Process pr = rt.exec(command);

                if (wait) {
                    DataInputStream in = new DataInputStream(pr.getInputStream());
                    byte buf[] = new byte[512];
                    while ((in.read(buf)) != -1) {
                        System.out.println("reading..");
                        System.out.println("output: " + in);
                    }
                    in.close();
                } else {
                    Thread.sleep(2000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

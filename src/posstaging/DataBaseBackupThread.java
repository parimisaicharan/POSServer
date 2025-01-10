/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * @author enteg
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * @author <authorname>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * USAGE
 * Data Base Backup Thread for running a database backup for an interval parameter specified in the Database Table
 * This uses the quartz frame work to activate the backup.
 * 
 * 
 */
package posstaging;

import ISRetail.serverconsole.DatabaseBackup;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JTextArea;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class DataBaseBackupThread implements Runnable {

    private JTextArea statusArea;

    public DataBaseBackupThread() {

    }

    public DataBaseBackupThread(JTextArea statusArea) {
        this.statusArea = statusArea;
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
            File file = new File("c:\\dbbackup-ftp\\a.bat");//Path changed on Jan 5th 2011
           //  File file = new File("c:\\a.bat");//Commented on Jan 5th 2011
            output = new PrintWriter(new FileWriter(file));
            for (int i = 0; i < str.length; i++) {
                output.println(str[i]);
            }
            output.println("EXIT");
            output.flush();
            output.close();
        } catch (IOException ex) {

        }

    }

    public void waitAndExecuteAutoCloseProcess(String text) {
        try {
            writeToLocalBatFile(text);
           // String command2 = "c:\\a.bat";//Commented and path changed on Jan 5th 2011
            String command2 = "c:\\dbbackup-ftp\\a.bat";
            MyThread th = new MyThread(command2, true);
            Thread thread = new Thread(th);
            thread.setDaemon(true);
            thread.start();
            thread.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    class MyThread implements Runnable {

        boolean running = true;
        String command;
        boolean wait = true;

        
        

          ;   
              
              
            public MyThread(String command, boolean wait)
   {
     this.command=command;
     this.wait=wait;
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
                        //System.out.println("reading..");
                        //System.out.println("output: " + in);
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

    private boolean CreatePDF_Folder(String Mailpath) {
        boolean valid = false;
        try {
            File folder = new File(""+Mailpath);
            if (!folder.exists()) {
                if (folder.mkdirs()) {
                    valid = true;
                    System.out.println("MailCopy folder is created!");
                    return valid;
                } else {
                    System.out.println("Failed to create MailCopy folder!");
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to create MailCopy folder!");
            e.printStackTrace();
        }
        return false;
    }
    
    private boolean Delete_MailCopy_Folder(String mailCopyPath){
        boolean clearMailCopyFiles = false; 
        try {
             
            File folder = new File(""+mailCopyPath);
            
            if(folder.isDirectory()){
                String files[] = folder.list();

        	   for (String temp : files) {
        	      //construct the file structure
                       if(temp.contains("pdf")){
        	      File fileDelete = new File(folder, temp);
                      System.out.println("Deleting files----------"+fileDelete);
        	      //recursive delete
        	     fileDelete.delete();       
                       clearMailCopyFiles = true;                       
                       }              
                   }
                   return clearMailCopyFiles;
               
            }  
        
         } catch (Exception e) {
            System.out.println("Failed to remove MailCopy files!");
            e.printStackTrace();
        }
        return clearMailCopyFiles;
    }
    
    
    
    public void run() {
        java.io.File currentDir;
        String currentdirectory;
        String command;
        Runtime rt;
        Process pr;
        boolean folderCreate = false;
        boolean clearMailCopyFiles = false;
        try {
            String storeCode;
            String cmdCode1;
            String cmdCode2;
            try {
                statusArea.append("\n\nRunning.......");
                currentDir = new java.io.File("");
                currentdirectory = currentDir.getAbsolutePath();
                //  command = "cmd /C start " + currentdirectory + "\\dbbackup.bat";
                //   rt = Runtime.getRuntime();
                //   pr = rt.exec(command);
                String mailcopypath = currentdirectory.replace("POSServer", "POSClient\\MailCopy");
                System.err.println("*********--------***********"+mailcopypath);
                folderCreate = CreatePDF_Folder(mailcopypath);                               
                if(folderCreate){
                statusArea.append("\nMailCopy folder is created....");
                }
                if(mailcopypath.endsWith("MailCopy")){
                clearMailCopyFiles = Delete_MailCopy_Folder(mailcopypath);
                }
                if(clearMailCopyFiles){
                statusArea.append("\nMail copy files deleted......");
                }
                
                System.out.println("cmd /C start /MIN ");
                waitAndExecuteAutoCloseProcess("cmd /C start /B /MIN " + currentdirectory + "\\dbbackup.bat");
                statusArea.append("\nBackup Taken Successfully......");
                statusArea.append("\nSleeping......");
            } catch (Exception e) {
                e.printStackTrace();
                statusArea.append("\nBackup Failed.Exception Occured:  " + e);
            } finally {
                storeCode = null;
                cmdCode2 = null;
                cmdCode1 = null;
                pr = null;
                rt = null;
                command = null;
                currentDir = null;
                currentdirectory = null;
            }

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler sched = sf.getScheduler();
            JobDetail job = new JobDetail("job1", "group1", DatabaseBackup.class);
            int freq = PosConfigParamDO.getDatabaseBackupReqFreq();
            String fr = "0 0 0/" + String.valueOf(freq) + " * * ?";
            //String fr="0 0/1 * * * ?";
            System.out.println("The frequency given:" + fr);
            CronTrigger trigger = new CronTrigger("trigger1", "group1", "job1",
                    "group1", fr);
            sched.scheduleJob(job, trigger);
            sched.start();
        } catch (Exception ex) {

        } finally {

        }


    }
}

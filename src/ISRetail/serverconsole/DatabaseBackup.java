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
 * Data Base Backup
 * 
 */

package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import javax.swing.JTextArea;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class DatabaseBackup implements Job {

	private JTextArea statusArea;
	public DatabaseBackup() {  
            this.statusArea = ServerConsole.getBackupdisp();
	}        
        public DatabaseBackup(JTextArea statusArea) {
           this.statusArea=statusArea;
	}
	public void execute(JobExecutionContext context)
			throws JobExecutionException  {  	
                 Runtime rt;
                 Process pr;
                 java.io.File currentDir;
                 String currentdirectory;
                 String command;
                 try {
                    System.out.println("backup taken successfully @"+ConvertDate.getCurrentTimeToString());                
                    currentDir = new java.io.File("");
                    currentdirectory = currentDir.getAbsolutePath();
                    command = "cmd /C start /B " + currentdirectory + "\\dbbackupscheduled.bat";
                    rt = Runtime.getRuntime();
                    pr = rt.exec(command); 
                    statusArea.append("\nScheduled DBBackup Taken "+new java.util.Date().toString()+"..");
                    statusArea.append("\nSleeping......");
                 } catch (Exception e) {
                    e.printStackTrace();                
                 } finally {                  
                    pr=null;
                    rt=null;
                    currentDir = null;
                    currentdirectory = null;
                    command = null;                    
                 }
	}
}
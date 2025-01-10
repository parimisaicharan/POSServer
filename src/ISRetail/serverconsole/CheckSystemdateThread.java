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
 * Thread Object for SiteMaster 
 * 
 */
package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class CheckSystemdateThread implements Runnable {
    // private Connection con;
    private Thread checkbthread;

    public CheckSystemdateThread(Thread checkbthread) {
        //  this.con=con; 
        this.checkbthread = checkbthread;

    }

    public void run() {
        MsdeConnection msdeConnection = new MsdeConnection();
        //Connection conn;
        Connection conn = null; /*initialized by arun on 16 Apr 2012 for properly Closing DB connections*/
        SiteMasterDO siteMasterDO = new SiteMasterDO();
        CheckBusinessDateDO checkBusinessDateDO = new CheckBusinessDateDO();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        String lasttransactime;
        long differenceindates;
        int lastbusinessdate;
        int currentsystemsdate;
        int lasttransacdate;
        String bdate;
        int currenttimefromsys;
        String wrongtimemsg;
        String wrongdatemsg;
        long SysDateCheckFreq= 0;


        while (true) {
            try {
                //conn = msdeConnection.createConnection();
                        /*Added by arun on 16 Apr 2012 for properly Closing DB connections*/
                if (conn == null) {
                    conn = msdeConnection.createConnection();
                }
                /*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
                //System.out.println("DriverManager.getLoginTimeout()"+DriverManager.getLoginTimeout());
                if (conn != null) {
                    //System.out.println("Current Date: " + dateFormat.format(date));
                    // System.out.println("calling getLastBusinessdate from checksystemthread ");
                    lastbusinessdate = checkBusinessDateDO.getLastBusinessdate(conn);
                    date = new java.util.Date();
                    currentsystemsdate = ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy");
                    bdate = ConvertDate.getNumericToDate(lastbusinessdate);
                    differenceindates = Validations.getDiffbtweendates(bdate, dateFormat.format(date));
                    //System.out.println("systhreaddiff****************************" + differenceindates);
                    currenttimefromsys = ConvertDate.getCurrentTimeToNumeric();
                    SysDateCheckFreq=PosConfigParamDO.getSystemDateCheckFreq(conn);
                    if (differenceindates >= 0) {
                        lasttransacdate = siteMasterDO.getLasttransactiondate(conn);
                        if (currentsystemsdate == lastbusinessdate) {
                            if (lasttransacdate == lastbusinessdate) {
                                lasttransactime = siteMasterDO.getLasttransactiontime(conn);
                                if (lasttransactime != null) {
                                    int lattranstime = Integer.parseInt(lasttransactime);
                                    currenttimefromsys = ConvertDate.getCurrentTimeToNumeric();
                                    if (lattranstime < (currenttimefromsys + 5)) {

                                    // if((currenttimefromsys-lattranstime)<=5){
                                    } else {
                                        if (ServerConsole.siteID != null) {
                                            ServerConsole.stopServerSocket();
                                        }
                                        checkbthread.stop();
                                        //JOptionPane.showMessageDialog(null, "There is a time mismatch please check your systemtime and restart the server.\n Your Last transaction time is "+" "+ConvertDate.getformattedTimeFromTime(lasttransactime),"Time Mismatch",JOptionPane.ERROR_MESSAGE);
                                        wrongtimemsg = "POS Error - \nPOS startup for time : " + ConvertDate.getformattedTimeFromTime(String.valueOf(currenttimefromsys)) + " aborted\nCheck your System Date and Time and Restart the Server";
                                        JOptionPane.showMessageDialog(null, wrongtimemsg, "Time Mismatch", JOptionPane.ERROR_MESSAGE);
                                        //   Thread.sleep(60000);                                                                            
                                        //   if (i == JOptionPane.YES_NO_CANCEL_OPTION) {
                                        conn.close();
                                        System.exit(0);
                                    //  }
                                    }
                                }
                            }
                        }
                    } else {
                        // Thread.sleep(60000);
                        if (ServerConsole.siteID != null) {
                            ServerConsole.stopServerSocket();
                        }
                        checkbthread.stop();
                        wrongdatemsg = "POS Error - \nPOS startup for date " + ConvertDate.getNumericToDate(currentsystemsdate) + " aborted.\nPOS Last Business Date is   " + ConvertDate.getNumericToDate(lastbusinessdate) + "\nCheck your System Date and Time and Restart the Server";
                        //JOptionPane.showMessageDialog(null, "There is a date mismatch please check your systemdate and restart the server.\n Your Last Businessdate is"+" "+ConvertDate.getNumericToDate(lastbusinessdate),"Date Mismatch",JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(null, wrongdatemsg, "Date Mismatch", JOptionPane.ERROR_MESSAGE);
                        conn.close();
                        System.exit(0);
                    }
                    //Thread.sleep(10000);
                   // Thread.sleep(PosConfigParamDO.getSystemDateCheckFreq(conn));
                }
            } catch (Exception e) {
                /*code added by arun on 16 Apr 2012 for properly Closing DB connections*/
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                   // Thread.sleep(PosConfigParamDO.getSystemDateCheckFreq(conn));

                } catch (SQLException se) {
                    se.printStackTrace();
                    
                } 
                /*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
                
                e.printStackTrace();
            } finally {
                //conn.close();               
                conn = null;   /*code added by arun on 16 Apr 2012 for properly Closing DB connections*/
                try{
                    System.out.println("before Thread.sleep() in chk system date thread");
                    Thread.sleep(SysDateCheckFreq);
                }catch(InterruptedException ie){
                    ie.printStackTrace();
                }
            }

        }
    }
}

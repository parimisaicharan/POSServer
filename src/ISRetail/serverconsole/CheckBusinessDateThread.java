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
 * Business Date Thread constantly running on the POS Server to check the Business Date from the ISR
 * 
 */
package ISRetail.serverconsole;

import ISRetail.Helpers.ConvertDate;
import ISRetail.PosStagingEntryReport.CheckStagingEntry;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.utility.validations.Validations;
import java.awt.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import posstaging.CheckSystemDate;

public class CheckBusinessDateThread extends javax.swing.JFrame implements Runnable {

    JTextArea businessdate;
    int counttransactpwd = 0;
    int countcorppasswd = 0;
    String manualdategiven;
    boolean isfirstime = false;
    static boolean isfiscalyrchanged = false;
    boolean blockedinetavail;
    boolean internetcon;
    boolean bdateavail;
    boolean diffsys;

    /** Creates new form CheckBusinessDate */
    public CheckBusinessDateThread() {
        initComponents();
        //Code Added on Jan 12th 2010 for testing posstaging entry check
          /*     try{ 
        CheckStagingEntry checkStagingEntry=new CheckStagingEntry();
        checkStagingEntry.checkStagingEntries();
        }catch(Exception e){
        }*/
    }

    public void run() {

        MsdeConnection msdeConnection = new MsdeConnection();
        Connection con = null;
        CheckBusinessDateDO checkBusinessDateDO;
        checkBusinessDateDO = new CheckBusinessDateDO();
        SiteMasterDO siteMasterDO = new SiteMasterDO();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        System.out.println("Current Date: " + dateFormat.format(date));
        boolean passwordtrns;
        String storecode = "";
        int lastbusinessdate = 0;
        String bdate;
        String blockind;
        int lastsyncdate = 0;
        String syncdate;
        int ISRpostingdate = 0;
        int ISRSystemdate = 0;
        int ISRfiscalyr = 0;
        String ISRblockind = null;
        long differencebwdates;
        long businessdatefreq = 0;
        long businessdatenormalfreq = 0;
        long differenceindates;
        //initializing all the one time variables before loop 
        if (con == null) {
            try {
                con = msdeConnection.createConnection();
                businessdatefreq = PosConfigParamDO.getBusinessDateReqFreq(con);
                businessdatenormalfreq = PosConfigParamDO.getBusinessDateReqFreqNormal(con);
                storecode = siteMasterDO.getSiteId(con);
            } catch (Exception e) {
                e.printStackTrace();
                // con = null;
            } finally {
                try {
                    con.close();
                    con = null;
                    //System.out.println("Connection closed");
                    } catch (Exception e) {
                    //System.out.println("Connection not closed");
                    e.printStackTrace();
                }
            }
        }
        //businessdatefreq = PosConfigParamDO.getBusinessDateReqFreq(con);
        // businessdatenormalfreq = PosConfigParamDO.getBusinessDateReqFreqNormal(con);
        System.out.println("businessdatefreq" + businessdatefreq);
        //SiteMasterDO sitemasterdo = new SiteMasterDO();
        CheckSystemDate checkSystemDate = new CheckSystemDate();
        // storecode = siteMasterDO.getSiteId(con);


        while (true) {
            if (con == null) {
                try {
                    con = msdeConnection.createConnection();
                } catch (Exception e) {
                    e.printStackTrace();
                    con = null;
                }
            }
            if (con != null) {
                try {
                    Thread threadBusinessDate;
                    //check for Internet Connection Net Availability
                    if (checkBusinessDateDO.performConnectionChecking(businessdate)) {
                        blockedinetavail = true;
                        checkBusinessDateDO.updateblockindfromISR(con);//update the block indicator
                    }
                    //check for block
                    // System.out.println("calling getLastBusinessdate from checkBusinessdatethread ");
                    lastbusinessdate = checkBusinessDateDO.getLastBusinessdate(con);
                    bdate = ConvertDate.getNumericToDate(lastbusinessdate);

                    blockind = checkBusinessDateDO.getCheckBlock(con);
                    if (blockind != null && blockind.equalsIgnoreCase("X")) {
                        //With Block Indicator to be checked again                     
                        //updateonlybusinessdate(con, bdate);
                        //updatewithmanualdate(con, bdate);
                        checkBusinessDateDO.updatewithmanualdatewoInetconn(con, lastbusinessdate, lastbusinessdate);
                        if (!isfirstime) {
                            new ServerConsole().setVisible(true);
                            if (blockedinetavail) {
                                checkBusinessDateDO.insertManualDateAudit(con, lastbusinessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), lastbusinessdate, "IAV_AUTO_BLOCKED");
                            } else {
                                checkBusinessDateDO.insertManualDateAudit(con, lastbusinessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), lastbusinessdate, "INA_AUTO_BLOCKED");
                            }
                        }
                        isfirstime = true;
                        ServerConsole.setBusinessDate(con, null);
                    } else {
                        System.out.println("bdate" + bdate);
                        //System.out.println("diff bw syn business" + differenceindates); Systemconsoleprint
                        //Condition 1:Network is available                    

                        if (checkBusinessDateDO.performConnectionChecking(businessdate)) {
                            internetcon = true;
                            if (checkBusinessDateDO.getBusinessdatafromISR(con)) {
                                bdateavail = true;
//                                checkSystemDate.getBusinessDateFromWebservice();
                                ISRpostingdate = checkSystemDate.getBusinessDate();
                                ISRSystemdate = checkSystemDate.getSystemDate();
                                ISRfiscalyr = checkSystemDate.getFiscalYear();
                                ISRblockind = checkSystemDate.getPostingindicator();
                                siteMasterDO.updateSyncdate(con, ISRpostingdate);
                            } else {
                                bdateavail = false;
                            }
                        } else {
                            internetcon = false;
                        }


                        //if (checkBusinessDateDO.performConnectionChecking(businessdate)&&checkBusinessDateDO.getBusinessdatafromISR(con)) {                    

                        if (internetcon && bdateavail) {
                            internetcon = true;
                            //    threadBusinessDate = new Thread(new BusinessDateThread(businessdate));
//                            threadBusinessDate.setDaemon(true);
//                            threadBusinessDate.start();                        
//                        checkSystemDate.getBusinessDateFromWebservice();
//                        ISRpostingdate=checkSystemDate.getBusinessDate();
//                        ISRSystemdate=checkSystemDate.getSystemDate();
//                        ISRfiscalyr=checkSystemDate.getFiscalYear();
//                        ISRblockind=checkSystemDate.getPostingindicator();                      
//                        siteMasterDO.updateSyncdate(con, ISRpostingdate);   

                            lastsyncdate = checkBusinessDateDO.getLastSyncdate(con);
                            syncdate = ConvertDate.getNumericToDate(lastsyncdate);
                            differenceindates = Validations.getDiffbtweendates(bdate, syncdate);
                            System.out.println("diffindate::::" + differenceindates);
                            if (con != null) {
                                ServerConsole.setBusinessDate(con, null);
                            }
                            //difference between ISR and POS last business date is 0-No action
                            if (differenceindates == 0) {
                                //NO Action
                                if (!isfirstime) {
                                    new ServerConsole().setVisible(true);
                                }
                                isfirstime = true;
                                // updatewithmanualdate(con, syncdate);
                                //ServerConsole.setBdateColor(Color.BLUE);
                                // ServerConsole.setBdateStatus("Business Date Selection is Automatic");
                                ServerConsole.setBdateStatus("");
                                ServerConsole.setBdateStatus1("");
                                ServerConsole.setCorrectionMessage("");
                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ISRpostingdate, ISRSystemdate, ISRfiscalyr, ISRblockind);
                                ServerConsole.setBusinessDate(con, null);
                                //  checksystemdate.start();
                                Thread.sleep(businessdatenormalfreq);

                            } else if (differenceindates == 1) {
                                //sync will happen and the date will get updated
                                //? Sync has already happened
                                if (!isfirstime) {
                                    new ServerConsole().setVisible(true);
                                }
                                isfirstime = true;
                                //  updatewithmanualdate(con, syncdate);
                                // ServerConsole.setBdateColor(Color.BLUE);
                                //  ServerConsole.setBdateStatus("Business Date Selection is Automatic");
                                ServerConsole.setBdateStatus("");
                                ServerConsole.setBdateStatus1("");
                                ServerConsole.setCorrectionMessage("");
                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ISRpostingdate, ISRSystemdate, ISRfiscalyr, ISRblockind);

                                ServerConsole.setBusinessDate(con, null);
                                Thread.sleep(businessdatenormalfreq);
//                                checksystemdate.start();
                            } // else if (differenceindates >= 2 && differenceindates <= 9) {
                            else if (differenceindates >= PosConfigParamDO.getMin_date_diff_with_internet(con) && differenceindates <= PosConfigParamDO.getMax_date_diff_with_internet(con)) {
                                JOptionPane.showMessageDialog(null, "POS Server is starting after " + (differenceindates - 1) + " day(s) \nLast Posting  Date            : " + bdate + "\nISR date                                 : " + syncdate + "\nSelect a date between " + bdate + "  and  " + syncdate);
                                if (validatemanualdatelimited(con, bdate, syncdate, "sync", siteMasterDO)) {
                                    passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind, checkBusinessDateDO, "IAV_MANUAL_TPWD_BETWEEN_" + PosConfigParamDO.getMin_date_diff_with_internet(con) + "_AND_" + PosConfigParamDO.getMax_date_diff_with_internet(con));
                                    if (passwordtrns) {
                                        //   checksystemdate.start();
                                        //  Thread.sleep(1200000);
                                        checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind);
                                        ServerConsole.setBdateColor(Color.RED);
                                        ServerConsole.setBdateStatus("Posting Date Manual/I(PENDING TRANSACTIONS)");
                                        ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                        if (Validations.getDiffbtweendates(syncdate, manualdategiven) == 0) {
                                            //   ServerConsole.setCorrectionMessage("No Restart of Server Required for Updating the Posting Date");
                                            ServerConsole.setCorrectionMessage("");
                                            Thread.sleep(businessdatefreq);
                                        } else {
                                            ServerConsole.setCorrectionMessage("Restart server to reset date ");
                                            Thread.sleep(businessdatefreq);
                                            ServerConsole.stopServerSocket();
                                            JOptionPane.showMessageDialog(this, "Entry period for pending transaction is over \nRestart server to reset date", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                                            System.exit(0);
                                        }


                                    } else {
                                        System.exit(0);
                                    }
                                } else {
                                    System.exit(0);
                                }
                            } //  else if (differenceindates > 9) {
                            else if (differenceindates > PosConfigParamDO.getMax_date_diff_with_internet(con)) {
                                //JOptionPane.showMessageDialog(null, "System is starting after " + differenceindates + " days \n Last Business Date :" + bdate + "\n ISR date :" + syncdate);
                                JOptionPane.showMessageDialog(null, "POS Server is starting after " + (differenceindates - 1) + " days \nLast Posting  Date              : " + bdate + "\nISR date                                 : " + syncdate + "\nSelect a date between " + bdate + "  and  " + syncdate);
                                date = new java.util.Date();
                                if (checkcorporatepwd(dateFormat.format(date), storecode, checkBusinessDateDO)) {
                                    //  if (validatemanualdate(con, bdate)) {
                                    if (validatemanualdatelimited(con, bdate, syncdate, "sync", siteMasterDO)) {
                                        passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind, checkBusinessDateDO, "IAV_MANUAL_TPWD_CPWD_MORETHAN_" + PosConfigParamDO.getMax_date_diff_with_internet(con));

                                        if (passwordtrns) {
                                            //checksystemdate.start();
                                            // Thread.sleep(1200000);
                                            ServerConsole.setBdateColor(Color.RED);

                                            ServerConsole.setBdateStatus("Posting Date - Manual/I(PENDING TRANSACTIONS)");
                                            ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                            if (Validations.getDiffbtweendates(syncdate, manualdategiven) == 0) {
                                                //ServerConsole.setCorrectionMessage("No Restart of Server Required for Updating the Business Date");
                                                ServerConsole.setCorrectionMessage("");
                                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind);
                                                Thread.sleep(businessdatefreq);
                                            } else {
                                                ServerConsole.setCorrectionMessage("Restart server to reset date ");
                                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind);
                                                Thread.sleep(businessdatefreq);
                                                ServerConsole.stopServerSocket();
                                                JOptionPane.showMessageDialog(this, "Entry period for pending transaction is over \nRestart server to reset date", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                                                System.exit(0);
                                            }


                                        } else {
                                            System.exit(0);
                                        }
                                    } else {
                                        System.exit(0);
                                    }
                                }
                            } else if (differenceindates < 0) {
                                JOptionPane.showMessageDialog(null, "POS is starting backdated \nLast Posting Date       : " + bdate + "\nISR date                        : " + syncdate);
                                if (checkcorporatepwd(dateFormat.format(date), storecode, checkBusinessDateDO)) {
                                    if (validatemanualdate(con, bdate, siteMasterDO)) {
                                        passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind, checkBusinessDateDO, "IAV_TPWD_CPWD_ISR_BACKDATE");
                                        if (passwordtrns) {
                                            //  checksystemdate.start();
                                            //  Thread.sleep(1200000);
                                            ServerConsole.setBdateColor(Color.RED);
                                            ServerConsole.setBdateStatus("Posting Date-Manual/I(PENDING TRANSACTIONS)");
                                            ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                            ServerConsole.setCorrectionMessage("");
                                            checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ISRfiscalyr, ISRblockind);
                                            Thread.sleep(businessdatefreq);
                                        } else {
                                            System.exit(0);
                                        }
                                    } else {
                                        System.exit(0);
                                    }
                                } else {
                                    System.exit(0);
                                }
                            }

                        } else {
                            //Internet connection not available
                            // internetcon=false;

                            differencebwdates = Validations.getDiffbtweendates(bdate, dateFormat.format(date));

                            if (differencebwdates == 0) {

                                System.out.println("difference*****:" + differencebwdates);
                                //  updatewithmanualdate(con, dateFormat.format(date));
                                checkBusinessDateDO.updatewithmanualdatewoInetconn(con, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"));
                                if (!isfirstime) {
                                    new ServerConsole().setVisible(true);
                                    //    checksystemdate.start();
                                }
                                isfirstime = true;
                                ServerConsole.setBdateColor(Color.BLUE);

                                if (internetcon && !bdateavail) {
                                    ServerConsole.setBdateStatus("Posting Date-Auto-Internet Available -No Posting Date from PI Server");
                                    ServerConsole.setCorrectionMessage("");
                                } else {
                                    ServerConsole.setBdateStatus("Posting Date-Auto-No Internet");
                                    ServerConsole.setCorrectionMessage("");
                                }

                                ServerConsole.setBusinessDate(con, null);
                                Thread.sleep(businessdatenormalfreq);

                            } else if (differencebwdates == 1) {
                                diffsys = true;
                                JOptionPane.showMessageDialog(null, "POS is starting with today's System Date.\nLast Posting  Date           : " + bdate + "\nSystem date                      : " + dateFormat.format(date) + "\nNo Internet Connection");
                                if (checktransactionpwd(con, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), 0, null, checkBusinessDateDO, "INA_AUTO_TPWD_NEXTDAY")) {
                                    //  updatewithmanualdate(con, dateFormat.format(date));
                                    if (!isfirstime) {
                                        new ServerConsole().setVisible(true);
                                    }
                                    isfirstime = true;
                                    ServerConsole.setBdateColor(Color.BLUE);
                                    if (internetcon && !bdateavail) {
                                        ServerConsole.setBdateStatus("Posting Date-Auto-Internet Available -No Posting Date from PI Server");
                                        ServerConsole.setCorrectionMessage("");
                                    } else {
                                        ServerConsole.setBdateStatus("Posting Date-Auto-No Internet");
                                        ServerConsole.setCorrectionMessage("");
                                    }

                                    ServerConsole.setBusinessDate(con, null);
                                    Thread.sleep(businessdatenormalfreq);
                                    //     checksystemdate.start();
                                } else {

                                    System.exit(0);
                                }
                            } // else if (differencebwdates > 1 && differencebwdates <= 3) {
                            else if (differencebwdates > PosConfigParamDO.getMin_date_diff_without_internet(con) && differencebwdates <= PosConfigParamDO.getMax_date_diff_without_internet(con)) {
                                JOptionPane.showMessageDialog(null, "POS is starting after " + (differencebwdates - 1) + " day(s) \nLast Posting  Date            : " + bdate + "\nSystem date                       : " + dateFormat.format(date) + "\nNo Internet Connection");
                                if (validatemanualdatelimited(con, bdate, dateFormat.format(date), "sys", siteMasterDO)) {
                                    passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), 0, null, checkBusinessDateDO, "INA_MANUAL_TPWD_BETWEEN_" + PosConfigParamDO.getMin_date_diff_without_internet(con) + "_AND_" + PosConfigParamDO.getMax_date_diff_without_internet(con));
                                    if (passwordtrns) {
                                        //    checksystemdate.start();
                                        //  Thread.sleep(1200000);
                                        //JOptionPane.showMessageDialog(null, "You have Selected " + dateFormat.format(date) + "as the Business Date and This business date will last for the next "+ businessdatefreq + "Micro Seconds"); 
                                        ServerConsole.setBdateColor(Color.RED);
                                        ServerConsole.setBdateStatus("Posting Date - Manual/NI");
                                        ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                        if (Validations.getDiffbtweendates(dateFormat.format(date), manualdategiven) == 0) {
                                            //    ServerConsole.setCorrectionMessage("No Restart of Server Required for Updating the Business Date");
                                            Thread.sleep(businessdatefreq);

                                        } else {
                                            ServerConsole.setCorrectionMessage("Restart server to reset date ");
                                            Thread.sleep(businessdatefreq);
                                            ServerConsole.stopServerSocket();
                                            JOptionPane.showMessageDialog(this, "Entry period for pending transactions is over \nRestart server to reset date", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                                            System.exit(0);
                                        }



                                    } else {
                                        System.exit(0);
                                    }
                                } else {
                                    System.exit(0);
                                }
                            } //  else if (differencebwdates > 3) {
                            else if (differencebwdates > PosConfigParamDO.getMax_date_diff_without_internet(con)) {
                                JOptionPane.showMessageDialog(null, "POS is starting after " + (differencebwdates - 1) + " days \nLast Posting  Date          : " + bdate + "\nSystem date                     : " + dateFormat.format(date) + "\nNo Internet Connection");
                                if (checkcorporatepwd(dateFormat.format(date), storecode, checkBusinessDateDO)) {
                                    // if (validatemanualdate(con, bdate)) {
                                    if (validatemanualdatelimited(con, bdate, dateFormat.format(date), "sys", siteMasterDO)) {
                                        passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), 0, null, checkBusinessDateDO, "INA_MANUAL_TPWD_CPWD_MORETHAN_" + PosConfigParamDO.getMax_date_diff_without_internet(con));
                                        if (passwordtrns) {
                                            //checksystemdate.start();
                                            //Thread.sleep(1200000);
                                            //JOptionPane.showMessageDialog(null, "You have Selected " + dateFormat.format(date) + "as the Business Date and This business date will last for the next "+ businessdatefreq + "Micro Seconds"); 
                                            ServerConsole.setBdateColor(Color.RED);
                                            ServerConsole.setBdateStatus("Posting date- Manual/NI(PENDING TRANSACTIONS)");
                                            ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                            if (Validations.getDiffbtweendates(dateFormat.format(date), manualdategiven) == 0) {
                                                //  ServerConsole.setCorrectionMessage("No Restart of Server Required for Updating the Business Date");
                                                Thread.sleep(businessdatefreq);
                                            } else {
                                                ServerConsole.setCorrectionMessage("Restart server to reset date ");
                                                Thread.sleep(businessdatefreq);
                                                ServerConsole.stopServerSocket();
                                                JOptionPane.showMessageDialog(this, "Entry period for pending transaction is over \nRestart server to reset date", "Information Message", JOptionPane.INFORMATION_MESSAGE);
                                                System.exit(0);
                                            }

                                        } else {
                                            System.exit(0);
                                        }
                                    }
                                }
                            } else if (differencebwdates < 0) {

                                JOptionPane.showMessageDialog(null, "POS is starting backdated \nLast Posting Date         : " + bdate + "\nSystem date                  : " + dateFormat.format(date) + "\nNo Internet Connection");
                                if (checkcorporatepwd(dateFormat.format(date), storecode, checkBusinessDateDO)) {
                                    if (validatemanualdate(con, bdate, siteMasterDO)) {
                                        passwordtrns = checktransactionpwd(con, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyy"), 0, null, checkBusinessDateDO, "INA_TPWD_CPWD_ISR_BACKDATE");
                                        if (passwordtrns) {
                                            //      checksystemdate.start();
                                            //   Thread.sleep(1200);
                                            ServerConsole.setBdateColor(Color.RED);
                                            ServerConsole.setBdateStatus("Posting Date - Manual/NI(PENDING TRANSACTIONS)");
                                            ServerConsole.setBdateStatus1("Applicable for next " + (businessdatefreq / 1000) / 3600 + " Hour(s)");
                                            Thread.sleep(businessdatefreq);
                                        } else {
                                            System.exit(0);
                                        }
                                    } else {
                                        System.exit(0);
                                    }
                                } else {
                                    System.exit(0);
                                }
                            }
                        }
                    }
                    // con.close();
                } catch (Exception e) {
                    try {
                        con.close();
                        con = null;
                        Thread.sleep(businessdatenormalfreq);
                    } catch (Exception th) {
                        //  System.out.println("Thread.sleep() in catch block exception");
                        //th.printStackTrace();
                    }
                    // System.out.println("After Thread.sleep() in catch block");
                    e.printStackTrace();
                } finally {
                    try {
                        if (con != null) {
                            con.close();
                            con = null;
                        }
                        //   System.out.println("Connection closed");
                    } catch (Exception e) {
                        //    System.out.println("Connection not closed");
                        e.printStackTrace();
                    }

                }

            }
        }
    }

    public boolean checkcorporatepwd(String todaysdate, String storecode, CheckBusinessDateDO checkBusinessDateDO) {
        String corppwdgenerated;
        String corppwd;
        try {
            corppwdgenerated = checkBusinessDateDO.getCorporatePassword(todaysdate, storecode);
            if (countcorppasswd < 3) {
                transacpwd.setText("");
                int optionSelected1 = JOptionPane.showOptionDialog(this, transacpwd, "Enter Corporate Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
                corppwd = transacpwd.getText();
                if (optionSelected1 == JOptionPane.OK_OPTION) {
                    if (corppwd.equals(corppwdgenerated)) {
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "The Password Entered is incorrect");
                        countcorppasswd++;
                        boolean pwd = checkcorporatepwd(todaysdate, storecode, checkBusinessDateDO);
                        return pwd;
                    }
                } else {
                    System.exit(0);
                    return false;
                }
            } else {
                System.exit(0);
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            corppwdgenerated = null;
            corppwd = null;
        }

    }

    public boolean checktransactionpwd(Connection con, int busidate, int systdate, int fyr, String blkind, CheckBusinessDateDO checkBusinessDateDO, String typeofselection) {
        String transacctionpwd;
        boolean pwd;
        boolean transaction_pwdMatch= false;        //Created by jyoti Nev_03_2017
        String tranppwd;
        String EncriptTransactionPwd;               //Created by jyoti Nev_03_2017
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date date = new java.util.Date();
        try {
            transacctionpwd = checkBusinessDateDO.getTransactionPwd(con);
            if (counttransactpwd <= 3) {
                transacpwd.setText("");
                int optionSelected1 = JOptionPane.showOptionDialog(this, transacpwd, "Enter Transaction Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
                tranppwd = transacpwd.getText();
                if (optionSelected1 == JOptionPane.OK_OPTION) {
                    EncriptTransactionPwd = Validations.passWordEncription(tranppwd);          //Created by jyoti Nev_03_2017                    
                    
                    if(EncriptTransactionPwd.equals(transacctionpwd)){                          //Created by jyoti Nev_03_2017
                        transaction_pwdMatch = true;
                    }
                    else if(tranppwd.equals(transacctionpwd)){                                  //Created by jyoti Nev_03_2017
                        checkBusinessDateDO.updateEncriptTransactionPwd(con,transacctionpwd);
                        transaction_pwdMatch = true;                        
                    }
                      
                    //if (tranppwd.equalsIgnoreCase(transacctionpwd)) {                         
                    if (transaction_pwdMatch) {                                                 //Change by jyoti Nev_03_2017
                        int lastbusinessdate = checkBusinessDateDO.getLastBusinessdate(con);
                        checkBusinessDateDO.updateManualDateAudit(con, lastbusinessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"));
                        if (Validations.isFieldNotEmpty(manualdategiven)) {
                            checkBusinessDateDO.insertManualDateAudit(con, lastbusinessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), typeofselection);
                        } else {
                            checkBusinessDateDO.insertManualDateAudit(con, lastbusinessdate, ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), ConvertDate.getDateNumeric(dateFormat.format(date), "dd/MM/yyyy"), typeofselection);
                        }
                        if (internetcon) {
                            //checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven,"dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven,"dd/MM/yyyy"), fyr, blkind);
                            if (bdateavail) {
                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), fyr, blkind);
                            } else {
                                if (diffsys) {
                                    checkBusinessDateDO.updatewithmanualdatewoInetconn(con, busidate, systdate);
                                } else {
                                    checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), fyr, blkind);
                                }
                            }
                        } else {
                            if (diffsys) {
                                checkBusinessDateDO.updatewithmanualdatewoInetconn(con, busidate, systdate);
                            } else {
                                checkBusinessDateDO.updatewithmanualdatewithInetconn(con, ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), ConvertDate.getDateNumeric(manualdategiven, "dd/MM/yyyy"), fyr, blkind);
                            }
                        }
                        checkBusinessDateDO.updatewithmanualdateauditfields(con, lastbusinessdate, manualdategiven);
                        //System.out.println("success");
                        if (!isfirstime) {
                            new ServerConsole().setVisible(true);
                        }
                        isfirstime = true;
                        if (manualdategiven != null) {
                            if (manualdategiven.length() > 0) {
                                ServerConsole.setBusinessDate(con, manualdategiven);
                            } else {
                                ServerConsole.setBusinessDate(con, null);
                            }
                        } else {
                            ServerConsole.setBusinessDate(con, null);
                        }
                        return true;

                    } else {
                        JOptionPane.showMessageDialog(null, "The Password Typed is incorrect");
                        counttransactpwd++;
                        pwd = checktransactionpwd(con, busidate, systdate, fyr, blkind, checkBusinessDateDO, typeofselection);
                        return pwd;
                    }
                } else {
                    return false;
                }
            } else {
                System.exit(0);
                return false;
            }

        } catch (Exception e) {
            return false;
        } finally {
            transacctionpwd = null;
            tranppwd = null;
            date = null;
            dateFormat = null;
        }

    }
//    public static void checkiffiscalyearchanged(String busdate,CheckBusinessDateDO checkBusinessDateDO,Connection con,SiteMasterDO siteMasterDO){
//     try{
//    if(busdate!=null){    
//        if(!isfiscalyrchanged){
//    int fyear=Integer.parseInt(busdate.substring(6,10));
//    if(Integer.parseInt(busdate.substring(3,5))==04){
//           
//    //check if this fiscal year is  there in lastpos no table
//     boolean flag=checkBusinessDateDO.isfiscalyearpresent(fyear+1, con);
//    //if yes no action
//     //if no then fiscal year is changed in sitemaster and row insertion in lastposno table
//     if(!flag){
//      siteMasterDO.insertRecToPosDocLastNoTable(fyear);
//      siteMasterDO.updateFiscalyear(con,fyear+1);
//      isfiscalyrchanged=true;
//     }
//   
//    }
//    }
//    }
//     }catch(Exception e){
//     
//     }finally{
//     
//     }
//    }
//    

    public boolean validatemanualdate(Connection con, String bdate, SiteMasterDO siteMasterDO) {
        boolean flag;
        String lasttransacdate;
        Object[] obj;
        String mandate;
        try {
            // SiteMasterDO siteMasterDO = new SiteMasterDO();
            lasttransacdate = ConvertDate.getNumericToDate(siteMasterDO.getLasttransactiondate(con));
            obj = new Object[2];
            obj[0] = "Enter a posting date after " + bdate + "\n";
            obj[1] = manualdate;

            int optionSelected = JOptionPane.showOptionDialog(this, obj, "Enter the Posting Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");

            if (optionSelected == JOptionPane.OK_OPTION) {
                mandate = manualdate.getText();
                if (Validations.validateAndSetDate(mandate) != null) {
                    manualdate.setText(Validations.validateAndSetDate(mandate));
                    System.out.println("busidate:" + bdate);
                    System.out.println("mandate:" + mandate);
                    if (Validations.isToDateAfterFromDate(bdate, manualdate.getText())) {
                        manualdategiven = manualdate.getText();
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(null, "Date cannot be less than last posting date.\nEnter a date after" + " " + bdate);
                        flag = validatemanualdate(con, bdate, siteMasterDO);
                        return flag;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid date(DD/MM/YYYY)");
                    flag = validatemanualdate(con, bdate, siteMasterDO);
                    return flag;
                }
            } else {
                System.exit(0);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            obj = null;
            lasttransacdate = null;
            mandate = null;

        }
    }

    public boolean validatemanualdatelimited(Connection con, String bdate, String sysorsyncdate, String connectstatus, SiteMasterDO siteMasterDO) {
        Object[] obj;
        String lasttransacdate;
        String mandate;
        boolean flag;
        try {
            lasttransacdate = ConvertDate.getNumericToDate(siteMasterDO.getLasttransactiondate(con));
            obj = new Object[2];
            obj[0] = "Enter a date between " + bdate + " " + "and" + " " + sysorsyncdate + "\n";
            obj[1] = manualdate;

            int optionSelected = JOptionPane.showOptionDialog(this, obj, "Enter the Posting Date", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"OK", "Cancel"}, "");
            if (optionSelected == JOptionPane.OK_OPTION) {
                mandate = manualdate.getText();
                if (Validations.validateAndSetDate(mandate) != null) {
                    manualdate.setText(Validations.validateAndSetDate(mandate));
                    System.out.println("busidate:" + bdate);
                    System.out.println("mandate:" + mandate);
                    if (Validations.isToDateAfterFromDate(bdate, manualdate.getText())) {
                        if (Validations.getDiffbtweendates(sysorsyncdate, manualdate.getText()) <= 0) {
                            // start : added by ravi thota on 18.11.2011 for checking manual date should be in the same month , added if clase
                            System.out.println("Difference in Months:" + Validations.getDiffInMonths(sysorsyncdate, manualdate.getText()));
                            if (Validations.getDiffInMonths(sysorsyncdate, manualdate.getText()) == 0) {
                                manualdategiven = manualdate.getText();
                                return true;
                            } else {
                                JOptionPane.showMessageDialog(null, " Date given should be in the current month and year ");
                                flag = validatemanualdatelimited(con, bdate, sysorsyncdate, connectstatus, siteMasterDO);
                                return flag;
                            }
                            // end : added by ravi thota on 18.11.2011 for checking manual date should be in the same month
                        } else {
                            if (connectstatus.equalsIgnoreCase("sys")) {
                                JOptionPane.showMessageDialog(null, "Date given cannot exceed current system date.\nEnter a date between " + bdate + " and " + sysorsyncdate);

                            } else {
                                JOptionPane.showMessageDialog(null, "Date given cannot exceed current ISR date.\nEnter a date between " + bdate + " and " + sysorsyncdate);
                                // manualdate.setText("DD/MM/YYYY");

                            }
                            flag = validatemanualdatelimited(con, bdate, sysorsyncdate, connectstatus, siteMasterDO);
                            return flag;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Date cannot be less than last posting date.\n Enter a date between " + bdate + " and " + sysorsyncdate);
                        flag = validatemanualdatelimited(con, bdate, sysorsyncdate, connectstatus, siteMasterDO);
                        return flag;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a valid date(DD/MM/YYYY)");
                    flag = validatemanualdatelimited(con, bdate, sysorsyncdate, connectstatus, siteMasterDO);
                    return flag;
                }
            } else {
                System.exit(0);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {

            obj = null;
            lasttransacdate = null;
            mandate = null;

        }
    }

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
                new CheckBusinessDateThread();
            }
        });
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

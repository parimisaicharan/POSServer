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
 * This class is used to Create a Data Archival Web Service Call
 * 
 * 
 */
package ISRetail.dataarchival;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import posstaging.*;

import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;


public class DataArchivalExecutionService {

    private JTextArea statusArea;

    public DataArchivalExecutionService(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public ArrayList<DataArchiveRecordPOJO> callWebService(String site,Connection con) throws Exception {
        java.util.Date date;
        ArrayList<DataArchiveRecordPOJO> dataArchiveRecordPOJOs = null;
        DataArchiveRecordPOJO dataArchiveRecordPOJO = null;
        try { // Call Web Service Operation
            in.co.titan.dataarchivalexecution.MIOBSYNDataArchivalExecutionService service = new in.co.titan.dataarchivalexecution.MIOBSYNDataArchivalExecutionService();
            in.co.titan.dataarchivalexecution.MIOBSYNDataArchivalExecution port = service.getMIOBSYNDataArchivalExecutionPort();
            
            int request_timeout = 60000;
            Connection c = null;
            String timeout = "";
            try {
                c = new MsdeConnection().createConnection();
                timeout = PosConfigParamDO.getValForThePosConfigKey(c, "webservice_request_timeout");
                if (Validations.isFieldNotEmpty(timeout)) {
                    request_timeout = Integer.parseInt(timeout);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    c.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                timeout = null;
            }
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_DataArchivalExecution&version=3.0&Sender.Service=x&Interface=x%5Ex");
            // TODO initialize WS operation arguments here
            in.co.titan.dataarchivalexecution.DTDataArchivalExecution mtDataArchivalExecution = new in.co.titan.dataarchivalexecution.DTDataArchivalExecution();
            // TODO process result here
            mtDataArchivalExecution.setSiteID(site);
            mtDataArchivalExecution.setSITESEARCH(site);
            statusArea.append("\nSending Request to ISR..to check Archival is to be Done..");

            in.co.titan.dataarchivalexecution.DTDataArchivalExecutionResponse result = port.miOBSYNDataArchivalExecution(mtDataArchivalExecution);
            statusArea.append("\nRequest Sent..");
            if (result != null) {

                List<in.co.titan.dataarchivalexecution.DTDataArchivalExecutionResponse.ArchivalScript> scripts = result.getArchivalScript();
                if (scripts != null) {
                    statusArea.append("\nReceiving Archival Scripts From ISR..");
                    dataArchiveRecordPOJOs = new ArrayList<DataArchiveRecordPOJO>();
                    for (in.co.titan.dataarchivalexecution.DTDataArchivalExecutionResponse.ArchivalScript script : scripts) {
                        if (script != null) {
                            dataArchiveRecordPOJO = new DataArchiveRecordPOJO();
                            dataArchiveRecordPOJO.setSiteid(result.getSite());
                            dataArchiveRecordPOJO.setArchivalid(result.getArchivalID());
                            dataArchiveRecordPOJO.setArchivaldate(ConvertDate.getDateNumeric(result.getArchivalDate().toGregorianCalendar().getTime()));
                            dataArchiveRecordPOJO.setArchivalfromdate(ConvertDate.getDateNumeric(result.getFromDate().toGregorianCalendar().getTime()));
                            dataArchiveRecordPOJO.setArchivaltodate(ConvertDate.getDateNumeric(result.getToDate().toGregorianCalendar().getTime()));
                            dataArchiveRecordPOJO.setSino(script.getSINo().intValue());
                            dataArchiveRecordPOJO.setSqlStatement(script.getSqlStatement());
                            dataArchiveRecordPOJOs.add(dataArchiveRecordPOJO);
                            //System.out.println("Scripts Available : "+script.getSqlStatement());
                            statusArea.append("\nReceived Script :" + dataArchiveRecordPOJO.getSino());
                        }
                    }
                    if (dataArchiveRecordPOJOs != null && dataArchiveRecordPOJOs.size() > 0) {
                        statusArea.append("\n" + dataArchiveRecordPOJOs.size() + " : Archival Scripts Received From ISR..");
                        dataArchiveRecordPOJOs = executeArchivalStatements(dataArchiveRecordPOJOs,con);
                    } else {
                        statusArea.append("\nNo Archival Scripts Received From ISR..Exiting..");
                    }
                }
            }


        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            throw ex;

        } finally {
            dataArchiveRecordPOJO = null;
        }
        return dataArchiveRecordPOJOs;
    }

    public ArrayList<DataArchiveRecordPOJO> executeArchivalStatements(ArrayList<DataArchiveRecordPOJO> dataArchiveRecordPOJOs,Connection con) {
        DataArchivalDO dataArchivalDO;
        SiteMasterDO siteMasterDO;
        try {
            dataArchivalDO = new DataArchivalDO();
            siteMasterDO=new SiteMasterDO();
            int result = 0;
            statusArea.append("\nExecuting Archival Scripts..");
            for (DataArchiveRecordPOJO dataArchiveRecordPOJO : dataArchiveRecordPOJOs) {
                if (dataArchiveRecordPOJO != null) {
                    dataArchiveRecordPOJO.setStatusupdateddate(siteMasterDO.getSqlSystemDate(con));
                    dataArchiveRecordPOJO.setStatusupdatedtime(siteMasterDO.getSqlSystemTime(con));
                    try {
                        if (dataArchiveRecordPOJO.getSqlStatement() != null) {
                            if (dataArchiveRecordPOJO.getSqlStatement().toUpperCase().contains("SHRINK")) {//database shrink
                                dataArchivalDO.executeShrinkStmt(con, dataArchiveRecordPOJO.getSqlStatement());
                                dataArchiveRecordPOJO.setStatus("S");//"Success"-shrink- first and last stmt-
                                dataArchiveRecordPOJO.setStatusmessage("Shrinked");
                                statusArea.append("\nExecuted Script No- " + dataArchiveRecordPOJO.getSino() + " :    Shrinked ");
                                if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                    dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                }
                            } else if (dataArchiveRecordPOJO.getSqlStatement().toUpperCase().contains("BACKUP") && dataArchiveRecordPOJO.getSqlStatement().toUpperCase().contains("DATABASE")) {//database backup
                                try {
                                    dataArchivalDO.executeBackupStmt(con, dataArchiveRecordPOJO.getSqlStatement());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    statusArea.append("\nFailure in Taking Backup Before Archival--So Archival Not Done..Error:" + e.getMessage());
                                    dataArchiveRecordPOJO.setStatus("F");//Failure
                                    
                                    if (e.getMessage() != null) {
                                        if (e.getMessage().length() > 255) {
                                            dataArchiveRecordPOJO.setStatusmessage(e.getMessage().substring(0, 254));
                                        } else {
                                            dataArchiveRecordPOJO.setStatusmessage(e.getMessage());
                                        }
                                    }
                                    if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                        dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                    }
                                    return null;
                                }

                                //code for confirming backup has happended(Sanjeevi --28012009)                                                       
                                String sqlbackup = dataArchiveRecordPOJO.getSqlStatement();
                                String fname = sqlbackup.substring(sqlbackup.indexOf("'") + 1, sqlbackup.lastIndexOf("'"));
                                File backupfile = new File(fname);
                                if (backupfile.exists()) {
                                    backupfile = null;
                                //do nothing 
                                } else {
                                    statusArea.append("\nBackup File not got created before Archival--So Archival Not Done:");
                                    dataArchiveRecordPOJO.setStatus("F");//Failure
                                    dataArchiveRecordPOJO.setStatusmessage("Backe up not created");
                                    if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                        dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                    }
                                    return null;
                                }
                                //end of code for confirming backup has happended(Sanjeevi --28012009)                           

                                dataArchiveRecordPOJO.setStatus("S");//Success"
                                dataArchiveRecordPOJO.setStatusmessage("Backed Up");
                                statusArea.append("\nExecuted Script No-" + dataArchiveRecordPOJO.getSino() + " :    Database backed Up ");
                                if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                    dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                }
                            } else if (dataArchiveRecordPOJO.getSqlStatement().toUpperCase().contains("BACKUP") && dataArchiveRecordPOJO.getSqlStatement().toUpperCase().contains("LOG")) {//log file backup
                                dataArchivalDO.executeBackupStmt(con, dataArchiveRecordPOJO.getSqlStatement());
                                dataArchiveRecordPOJO.setStatus("S");//Success"
                                dataArchiveRecordPOJO.setStatusmessage("Log File Backed Up");
                                statusArea.append("\nExecuted Script No-" + dataArchiveRecordPOJO.getSino() + " :    Log File backed Up ");
                                if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                    dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                }
                            } else {//delete statements
                                result = dataArchivalDO.executeDeleteStmt(con, dataArchiveRecordPOJO.getSqlStatement());
                                if (result > 0) {
                                    dataArchiveRecordPOJO.setStatus("S");//"Success"
                                    if (result > 1) {
                                        dataArchiveRecordPOJO.setStatusmessage(result + " Row Deleted");
                                    } else {
                                        dataArchiveRecordPOJO.setStatusmessage(result + " Rows Deleted");
                                    }
                                    statusArea.append("\nExecuted Script No-" + dataArchiveRecordPOJO.getSino() + " :    Rows Deleted= " + result);
                                } else {
                                    dataArchiveRecordPOJO.setStatus("V");//Verify
                                    dataArchiveRecordPOJO.setStatusmessage("0 Rows Affected");
                                    statusArea.append("\nExecuted Script No-" + dataArchiveRecordPOJO.getSino() + " :    No Data  ");
                                }
                                if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                                    dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                                }
                            }
                        }
                    } catch (Exception e) {
                        dataArchiveRecordPOJO.setStatus("F");//Failure
                        if (e.getMessage() != null) {
                            if (e.getMessage().length() > 255) {
                                dataArchiveRecordPOJO.setStatusmessage(e.getMessage().substring(0, 254));
                            } else {
                                dataArchiveRecordPOJO.setStatusmessage(e.getMessage());
                            }
                        }
                        if (dataArchivalDO.updateDataArchiveRecord(con, dataArchiveRecordPOJO) <= 0) {
                            dataArchivalDO.insertDataArchiveRecord(con, dataArchiveRecordPOJO);
                        }
                        statusArea.append("\nExecution Failed for Script No-:" + dataArchiveRecordPOJO.getSino() + " Exception :" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dataArchivalDO = null;
            siteMasterDO=null;
        }

        return dataArchiveRecordPOJOs;
    }
}

  

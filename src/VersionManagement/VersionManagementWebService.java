/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VersionManagement;

import ISRetail.Helpers.ConvertDate;
import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import in.co.titan.versionmanagement.DTVersionManagementResponse.Patch;
import in.co.titan.versionmanagement.DTVersionManagementResponse.Patch.Statement;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Administrator
 */
public class VersionManagementWebService {

    in.co.titan.versionmanagement.DTVersionManagementResponse result;
    private JTextArea statusArea;
    private ArrayList executelist;
    private String executeresult;
    private String typeofthread;
    private String statement;
    private boolean ispatchavailable;//To check if patch has come from ISR
    in.co.titan.patchstatus.MIOBASYNPatchStatusService serviceres = new in.co.titan.patchstatus.MIOBASYNPatchStatusService();
    in.co.titan.patchstatus.MIOBASYNPatchStatus portres = serviceres.getMIOBASYNPatchStatusPort();
    in.co.titan.patchstatus.DTPatchStatus.Version versiondet = new in.co.titan.patchstatus.DTPatchStatus.Version();

    public VersionManagementWebService(JTextArea statusArea, String typeofthread) {
        this.statusArea = statusArea;
        this.typeofthread = typeofthread;
        ispatchavailable = false;
    }

    public int execute() {
        int noOfRowsUpdated = 0;

        try { // Call Web Service Operation
            in.co.titan.versionmanagement.MIOBSYNVersionManagementService service = new in.co.titan.versionmanagement.MIOBSYNVersionManagementService();
            in.co.titan.versionmanagement.MIOBSYNVersionManagement port = service.getMIOBSYNVersionManagementPort();
            // TODO initialize WS operation arguments here
            int request_timeout = 60000;
            Connection c = null;
            String timeout = "";
            try {
                c = new MsdeConnection().createConnection();
                timeout = PosConfigParamDO.getValForThePosConfigKey(c, "webservice_request_timeout");
                if (Validations.isFieldNotEmpty(timeout)){
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
            // TODO process result here
            Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
            //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);
            ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_VersionManagement&version=3.0&Sender.Service=x&Interface=x%5Ex");
            in.co.titan.versionmanagement.DTVersionManagement mtVersionManagement = new in.co.titan.versionmanagement.DTVersionManagement();
            mtVersionManagement.setSiteID(ServerConsole.siteID);
            mtVersionManagement.setSITESEARCH(ServerConsole.siteID);
            result = port.miOBSYNVersionManagement(mtVersionManagement);
            VersionManagementDO versionManagementDO = new VersionManagementDO();
            VersionHeaderPOJO versionHeaderPOJO = new VersionHeaderPOJO();
            if (result.getVersion().getVersionID() != null) {
                versionHeaderPOJO.setSiteId(result.getVersion().getSiteID());
                if (result.getVersion().getVersionID() != null) {
                    versionHeaderPOJO.setVersionId(result.getVersion().getVersionID());
                }
                if (result.getVersion().getVersionDesc() != null) {
                    versionHeaderPOJO.setVersiondescription(result.getVersion().getVersionDesc());
                }
                if (result.getVersion().getDOR() != null) {
                    versionHeaderPOJO.setDateofrelease(ConvertDate.getDateNumeric(result.getVersion().getDOR().toGregorianCalendar().getTime()));
                }
            }
            // TODO initialize WS operation arguments here
            versiondet.setSiteID(versionHeaderPOJO.getSiteId());
            versiondet.setVersionID(versionHeaderPOJO.getVersionId());
            MsdeConnection mc = new MsdeConnection();
            Connection con = mc.createConnection();
            String currentVersion = versionManagementDO.getCurrentVersion(con);
            if (versionHeaderPOJO != null) {
                if (versionHeaderPOJO.getVersionId().equalsIgnoreCase(ServerConsole.currentversion) && versionHeaderPOJO.getVersionId().equalsIgnoreCase(currentVersion)) {
                    savePatch();
                } else {
                    if (versionManagementDO.updateVersion(con, versionHeaderPOJO) <= 0) {
                        versionManagementDO.saveVersiondetails(con, versionHeaderPOJO);
                    } else if (versionManagementDO.updateVersion(con, versionHeaderPOJO) > 0) {
                        noOfRowsUpdated = noOfRowsUpdated + 1;
                    }
                    savePatch();
                }
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        // TODO handle custom exceptions here
        }
        return noOfRowsUpdated;
    }

    public int getLatestPatchId(Connection con) {

        try {
            java.sql.Statement pstmt = con.createStatement();
            ResultSet rs = pstmt.executeQuery("select latestpatchid from tbl_latestposversion");
            while (rs.next()) {
                return rs.getInt("latestpatchid");
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }


    }

    public void savePatch() {
        int noofpatchstmtececuted = 0;
        try {
            ArrayList<in.co.titan.versionmanagement.DTVersionManagementResponse.Patch> patchlistexecuted = (ArrayList<Patch>) result.getPatch();
            Iterator iterator = patchlistexecuted.iterator();
            in.co.titan.versionmanagement.DTVersionManagementResponse.Patch patch = null;
            VersionManagementDO versionManagementDO = new VersionManagementDO();
            ArrayList versionarraylist = new ArrayList();
//                   VersionDetailPOJO versionDetailPOJO=null;
            MsdeConnection msdeConnection = new MsdeConnection();
            Connection con = msdeConnection.createConnection();
            in.co.titan.patchstatus.DTPatchStatus mtPatchStatus = new in.co.titan.patchstatus.DTPatchStatus();


            in.co.titan.patchstatus.DTPatchStatus.Patch patchres = null;




            List<Patch> pat = result.getPatch();
            Iterator<Patch> iter = pat.iterator();


            Object oo = new Object();
            Patch pp;
            VersionDetailPOJO pe = new VersionDetailPOJO();
            // VersionDetailPOJO versionDetailPOJO=new VersionDetailPOJO();
            ArrayList<VersionDetailPOJO> patchlist = new ArrayList<VersionDetailPOJO>();
            //  PatchList pl = new PatchList();
            VersionDetailPOJO pl = new VersionDetailPOJO();
            String patchno;
            ArrayList<VersionDetailPOJO> peal;
            patchres = new in.co.titan.patchstatus.DTPatchStatus.Patch();

            getStatusArea().append("\n************Patches**********");
            while (iter.hasNext()) {

                ispatchavailable = true;
                patchres = new in.co.titan.patchstatus.DTPatchStatus.Patch();
                pl = new VersionDetailPOJO();
                oo = iter.next();
                pp = (Patch) oo;
                int patchcoming = 0;

                patchcoming = Integer.parseInt(pp.getPatchID().trim());
                int currentpatchid = getLatestPatchId(con);
                int expectedpatchid = currentpatchid + 1;

                System.out.println(patchcoming + " is the patch that came");

                //if(patchcoming>=expectedpatchid)
                //{
                getStatusArea().append("\n Patchid" + pp.getPatchID() + " " + "executed");

                patchno = pp.getPatchID();
                //  patchres.setPatchID(new BigInteger(pp.getPatchID()));
                patchres.setPatchID(new BigInteger(pp.getPatchID().trim()));
                patchres.setPatchAppliedStatus("X");

                Statement stmt;
                List<Statement> stm = pp.getStatement();
                Iterator<Statement> iter1 = stm.iterator();

                peal = new ArrayList<VersionDetailPOJO>();

                while (iter1.hasNext()) {
                    in.co.titan.patchstatus.DTPatchStatus.Patch.Statement stmtforpatch = new in.co.titan.patchstatus.DTPatchStatus.Patch.Statement();
                    pe = new VersionDetailPOJO();
                    stmt = (Statement) iter1.next();
                    pe.setPatchId(patchno);
                    pe.setSlno(Integer.parseInt(stmt.getSrNo().trim()));
                    pe.setStatement(stmt.getSQL());
                    System.out.println(stmt.getSQL());
                    try {
                        pe.getExecuteResult(con);
                    } catch (Exception e) {

                    }


                    stmtforpatch.setSrNo(new BigInteger(String.valueOf(pe.getSlno())));
                    if (pe.getStatement() != null) {
                        if (pe.getStatement().trim().length() > 0) {
                            stmtforpatch.setErrorMessage(pe.getExeresult());
                        } else {
                            stmtforpatch.setErrorMessage("NO STATEMENT");
                        }
                    } else {
                        stmtforpatch.setErrorMessage("NO STATEMENT");
                    }

                    if (pe.getStatement() != null) {
                        stmtforpatch.setStatement(pe.getStatement());
                    } else {
                        stmtforpatch.setStatement("");
                    }
                    stmtforpatch.setStatus(pe.getStatus());


                    patchres.getStatement().add(stmtforpatch);
                // peal.add(pe);

                }

                mtPatchStatus.getPatch().add(patchres);

                //     pl.setExecutelist(peal);

                //     patchlist.add(pl);


                //}



                //updating the patch ID

                try {

                    if (currentpatchid < patchcoming) {
                        versionManagementDO.updatePatch(con, patchcoming);
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }




            }

            mtPatchStatus.setVersion(versiondet);
            mtPatchStatus.setSITESEARCH(ServerConsole.siteID);
            if (!ispatchavailable) {
                getStatusArea().append("\n************No Patches executed**********");
            }
            //  execute1forpatchstatus(patchlist);    
            Map<String, Object> ctxtres = ((BindingProvider) portres).getRequestContext();           //ctxt.put(JAXWSProperties.CONNECT_TIMEOUT, connectionTimeout);
            ctxtres.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
            ((BindingProvider) portres).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
            ((BindingProvider) portres).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
            ((BindingProvider) portres).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_PatchStatus&version=3.0&Sender.Service=x&Interface=x%5Ex");
            portres.miOBASYNPatchStatus(mtPatchStatus);
        //  }      
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
//    public void execute1forpatchstatus(mtPatchStatus)
//    {
//        try
//        {
//       
//            
//            
//            
//            
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            // TODO handle custom exceptions here
//        }
//
//    
//    
//    }
//    
    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public ArrayList getExecutelist() {
        return executelist;
    }

    public void setExecutelist(ArrayList executelist) {
        this.executelist = executelist;
    }

    public String getExecuteresult() {
        return executeresult;
    }

    public void setExecuteresult(String executeresult) {
        this.executeresult = executeresult;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
    }

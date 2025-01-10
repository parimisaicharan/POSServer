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
 * This class file is used to Send the result of the Archival Execution
 *
 * 
 * 
 */

package ISRetail.dataarchival;

import ISRetail.Webservices.XIConnectionDetailsPojo;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Map;
import posstaging.*;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Administrator
 */
public class ArchivalStatusSendingService {

    private JTextArea statusArea;

    public ArchivalStatusSendingService(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public void callWebService(String site, ArrayList<DataArchiveRecordPOJO> dataArchiveRecordPOJOs) throws Exception {
        java.util.Date date;
        String archivalId = null;
        ArrayList<in.co.titan.dataarchieving.DTDataArchieving.ArchivalStatus> statusList = null;
        in.co.titan.dataarchieving.DTDataArchieving.ArchivalStatus statusDetails = null;
        try { // Call Web Service Operation
            if (dataArchiveRecordPOJOs != null && dataArchiveRecordPOJOs.size() > 0) {
                in.co.titan.dataarchieving.MIOBASYNDataArchievingService service = new in.co.titan.dataarchieving.MIOBASYNDataArchievingService();
                in.co.titan.dataarchieving.MIOBASYNDataArchieving port = service.getMIOBASYNDataArchievingPort();
                Map<String, Object> ctxt = ((BindingProvider) port).getRequestContext();
                ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 60000);
                ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());
                ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_DataArchieving&version=3.0&Sender.Service=x&Interface=x%5Ex");

                // TODO initialize WS operation arguments here
                in.co.titan.dataarchieving.DTDataArchieving mtDataArchieving = new in.co.titan.dataarchieving.DTDataArchieving();


                mtDataArchieving.setSite(site);
                mtDataArchieving.setSITESEARCH(site);

                statusArea.append("\nSending Archival Status to ISR..");
                statusList = new ArrayList<in.co.titan.dataarchieving.DTDataArchieving.ArchivalStatus>();

                for (DataArchiveRecordPOJO dataArchiveRecordPOJO : dataArchiveRecordPOJOs) {
                    if (dataArchiveRecordPOJO != null) {
                        statusDetails = new in.co.titan.dataarchieving.DTDataArchieving.ArchivalStatus();
                        archivalId = dataArchiveRecordPOJO.getArchivalid();
                        statusDetails.setSiNo(new BigInteger(String.valueOf(dataArchiveRecordPOJO.getSino())));
                        statusDetails.setSqlStatement(dataArchiveRecordPOJO.getSqlStatement());
                        statusDetails.setStatus(dataArchiveRecordPOJO.getStatus());
                        statusDetails.setStatusMessage(dataArchiveRecordPOJO.getStatusmessage());
                        statusList.add(statusDetails);
                    }

                }
                mtDataArchieving.getArchivalStatus().addAll(statusList);
                statusArea.append("\nArchival Status is sent to ISR..");

                mtDataArchieving.setArchivalId(archivalId);

                port.miOBASYNDataArchieving(mtDataArchieving);
            }else{
                
            }

        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
            statusArea.append("\nfailure in Sending Archival Status to ISR..Error:" + ex.getMessage());
           
            throw ex;
        }
    }
}

  

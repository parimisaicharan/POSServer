/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package posstaging;

import ISRetail.Webservices.*;
import ISRetail.Helpers.ConvertDate;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.serverconsole.CheckBusinessDateDO;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author Administrator
 */
public class CheckSystemDate {

    private URL url;
    private HttpURLConnection conn;
    private static int businessDate;
    private static int systemDate;
    private static String postingindicator;
    private static int fiscalYear;
    // public static int sitDateInfoCount = 0;

    public boolean checkInternetConnection() {
        boolean available = false;
        try {
            url = new URL("http://netxi:50000/index.html");
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            if (conn.getResponseMessage().equals("OK")) {
                available = true;
            }
        } catch (Exception e) {
        }

        return available;
    }

    public boolean catchSoapfaultError() {
        boolean errorval;
        Connection con = null;
        try {
            //Calendar calendar = new GregorianCalendar();
            //int hour = calendar.get(Calendar.HOUR);
            //int minute = calendar.get(Calendar.MINUTE);
            //int second = calendar.get(Calendar.SECOND);
            //System.out.println("SiteDateInfo payload Sending at Soapfault error Count : "+ (++sitDateInfoCount) +" Time : "+hour+":"+minute+":"+second);

            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService service = new in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService();
            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfo port = service.getMIOBSYNSiteDateInfoPort();
            // TODO initialize WS operation arguments here
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
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SiteDateInfo&version=3.0&Sender.Service=X&Interface=X^X");
            in.co.titan.sitedateinfo.DTSiteDateInfo mtSiteDateInfo = new in.co.titan.sitedateinfo.DTSiteDateInfo();
            // TODO process result here
            MsdeConnection connection = new MsdeConnection();
            //Connection con = connection.createConnection(); /*Commented by arun on 16 Apr 2012 for properly Closing DB connections*/
            con = connection.createConnection(); /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/

            String site = new SiteMasterDO().getSiteId(con);
            mtSiteDateInfo.setSITE(site);
            mtSiteDateInfo.setSITESEARCH(site);
            in.co.titan.sitedateinfo.DTSiteDateInfoResponse result = port.miOBSYNSiteDateInfo(mtSiteDateInfo);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally { /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
            try {
                con.close();
                con = null;
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }/*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/

    }

    public void getBusinessDateFromWebservice() {
        //Calendar calendar = new GregorianCalendar();
        /*int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);*/
        // System.out.println("SiteDateInfo payload Sending at getbusiness date Count : "+ (++sitDateInfoCount) +" Time : "+hour+":"+minute+":"+second);

        CheckBusinessDateDO checkBusinessDateDO = new CheckBusinessDateDO();
        Connection con = null; /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
        try { // Call Web Service Operation
            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService service = new in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService();
            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfo port = service.getMIOBSYNSiteDateInfoPort();
            // TODO initialize WS operation arguments here
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
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SiteDateInfo&version=3.0&Sender.Service=X&Interface=X%5EX");
            in.co.titan.sitedateinfo.DTSiteDateInfo mtSiteDateInfo = new in.co.titan.sitedateinfo.DTSiteDateInfo();

            // TODO process result here
            MsdeConnection connection = new MsdeConnection();
            //Connection con = connection.createConnection(); /*Commented by arun on 16 Apr 2012 for properly Closing DB connections*/
            con = connection.createConnection(); /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
            String site = new SiteMasterDO().getSiteId(con);
            mtSiteDateInfo.setSITE(site);
            mtSiteDateInfo.setSITESEARCH(site);
            in.co.titan.sitedateinfo.DTSiteDateInfoResponse result = port.miOBSYNSiteDateInfo(mtSiteDateInfo);

            if (result.getBusinessDate().toGregorianCalendar() != null) {
                this.setBusinessDate(ConvertDate.getDateNumeric(result.getBusinessDate().toGregorianCalendar().getTime()));
            }
            if (result.getFyear() != null) {
                this.setFiscalYear(result.getFyear().intValue());
            }
            if (result.getSystemDate() != null) {
                this.setSystemDate(ConvertDate.getDateNumeric(result.getSystemDate().toGregorianCalendar().getTime()));
            }
            if (result.getPostInd() != null) {
                this.setPostingindicator(result.getPostInd());
            }
            con.close();

            //  System.out.println("CheckSystemDate****Current time****"+hour+":"+minute+":"+second);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally { /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
            try {
                con.close();
                con = null;
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }/*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/
    }

    public void getBusinessDateFromWebserviceforinitialbuild() {
        //Calendar calendar = new GregorianCalendar();
       /* int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);*/
        //  System.out.println("SiteDateInfo Payload Sending at initial build Count : "+ (++sitDateInfoCount) +" Time : "+hour+":"+minute+":"+second);

        CheckBusinessDateDO checkBusinessDateDO = new CheckBusinessDateDO();
        Connection con = null; /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
        try { // Call Web Service Operation
            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService service = new in.co.titan.sitedateinfo.MIOBSYNSiteDateInfoService();
            in.co.titan.sitedateinfo.MIOBSYNSiteDateInfo port = service.getMIOBSYNSiteDateInfoPort();
            // TODO initialize WS operation arguments here
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
            ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port()+"/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_SiteDateInfo&version=3.0&Sender.Service=X&Interface=X^X");
            in.co.titan.sitedateinfo.DTSiteDateInfo mtSiteDateInfo = new in.co.titan.sitedateinfo.DTSiteDateInfo();
            mtSiteDateInfo.setPOSStartUPFlag("X");
            // TODO process result here
            System.out.println("its in web service");
            MsdeConnection connection = new MsdeConnection();
            //Connection con = connection.createConnection(); /*Commented by arun on 16 Apr 2012 for properly Closing DB connections*/
            con = connection.createConnection(); /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
            String site = new SiteMasterDO().getSiteId(con);
            mtSiteDateInfo.setSITE(site);
            mtSiteDateInfo.setSITESEARCH(site);
            in.co.titan.sitedateinfo.DTSiteDateInfoResponse result = port.miOBSYNSiteDateInfo(mtSiteDateInfo);
            if (result.getBusinessDate().toGregorianCalendar() != null) {
                this.setBusinessDate(ConvertDate.getDateNumeric(result.getBusinessDate().toGregorianCalendar().getTime()));
            }

            if (result.getFyear() != null) {
                this.setFiscalYear(result.getFyear().intValue());
            }
            if (result.getSystemDate() != null) {
                this.setSystemDate(ConvertDate.getDateNumeric(result.getSystemDate().toGregorianCalendar().getTime()));
            }

            if (result.getISRResFlag() != null && result.getISRResFlag().equalsIgnoreCase("X")) {

                if (result.getBusinessDate().toGregorianCalendar() != null) {

                    checkBusinessDateDO.insertposstartupdetails(con, this.getBusinessDate());
                }
            }
            con.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally { /*Aded by arun on 16 Apr 2012 for properly Closing DB connections*/
            try {
                con.close();
                con = null;
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }/*End of code added by arun on 16 Apr 2012 for properly Closing DB connections*/

    }

    public int getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(int businessDate) {
        this.businessDate = businessDate;
    }

    public int getSystemDate() {
        return systemDate;
    }

    public void setSystemDate(int systemDate) {
        this.systemDate = systemDate;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getPostingindicator() {
        return postingindicator;
    }

    public void setPostingindicator(String postingindicator) {
        this.postingindicator = postingindicator;
    }
}

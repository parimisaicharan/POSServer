/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.utility.db;

import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.Webservices.MsdeConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.serverconsole.SplScr3;
import java.sql.Connection;

/**
 *
 * @author e1775501
 */
public class runwaycon {

    public static void getRunwayconDetails() {
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
            MsdeConnection msde = new MsdeConnection();
            Connection con1 = msde.createConnection();
            SiteMasterDO smdo = new SiteMasterDO();
            SiteMasterPOJO smpojo = smdo.getSite(con1);
            SplScr3.siteId = smpojo.getSiteid();
            SplScr3.sitemarquee = smpojo.getMarquee();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

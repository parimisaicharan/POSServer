/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.utility.db;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;

/**
 *
 * @author e1775501
 */
public class GetSiteDescription {

    public String GetSiteDescriptionforRunway() {
        String Sitedesc = "";
        try {
            MsdeConnection runwaymsde = new MsdeConnection();
            Connection con = runwaymsde.createConnection();
            SiteMasterDO runwaydo = new SiteMasterDO();
            SiteMasterPOJO runwaypojo = runwaydo.getSite(con);
            if (Validations.isFieldNotEmpty(runwaypojo) && Validations.isFieldNotEmpty(runwaypojo.getMarquee())) {
                Sitedesc = runwaypojo.getMarquee();
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Sitedesc;
    }
}

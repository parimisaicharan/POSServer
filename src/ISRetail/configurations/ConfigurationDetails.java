/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.configurations;

import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.serverconsole.ServerConsole;
import ISRetail.utility.validations.Validations;
import com.sun.xml.ws.client.BindingProviderProperties;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JTextArea;
import javax.xml.ws.BindingProvider;

/**
 *
 * @author enteg
 */
public class ConfigurationDetails {

    //private boolean IV_FLAG_CARDTYPES;  
    //   private boolean IV_FLAG_CHARACTS;
    private boolean IV_FLAG_CITY;
    private boolean IV_FLAG_COUNTRY;
    private boolean IV_FLAG_CURRENCYTYPE;
    private boolean IV_FLAG_CUSTOMERCATEGORY;
    private boolean IV_FLAG_CANCELREASON;
    private boolean IV_FLAG_DESIGNATION;
    private boolean IV_FLAG_EMPLOYEEMASTER;
    private boolean IV_FLAG_EDUCATION;
    private boolean IV_FLAG_FOLLOWUP;
    private boolean IV_FLAG_MARTIALSTATUS;
    private boolean IV_FLAG_OCCUPATION;
    private boolean IV_FLAG_PAYMENTMODE;
    private boolean IV_FLAG_RELATIONSHIP;
    private boolean IV_FLAG_RETREASON;
    private boolean IV_FLAG_STATE;
    private boolean IV_FLAG_STCON;
    private boolean IV_FLAG_TITLE;
    private boolean IV_FLAG_UPDATIONMODE;
    private boolean IV_FLAG_CARDTYPES;
    private boolean IV_FLAG_COMMPRIORITY;
    private boolean IV_FLAG_ADVCANRES;
    private boolean IV_FLAG_CHARVALUES;
    private boolean IV_FLAG_BRANDTINT;
    private boolean IV_FLAG_DEFECTREASON;
    private boolean IV_FLAG_CONDMAP;
    private boolean IV_FLAG_BILLCANCREASON;
    private boolean IV_FLAG_POSPARAM;
    private boolean IV_FLAG_SITES;
    private boolean IV_FLAG_SGL_ATTRIBUTES;
    private boolean IV_FLAG_MASTERS;
    private boolean IV_FLAG_DOCTOR_NAME;
    private boolean IV_FLAG_DELIV_MERCH;
    private boolean IV_FLAG_DELIV_ARTICLE;
    private boolean IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES;
    private boolean IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES;
    private boolean IV_FLAG_CONTACT_LENS_BASE_CURVE;
    private boolean IV_FLAG_CONTACT_LENS_TYPES;

    private com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNSResponse result = null;
    in.co.titan.configurationdetails.MIOBSYNConfigurationDetailsService service = new in.co.titan.configurationdetails.MIOBSYNConfigurationDetailsService();
    // in.co.titan.configurationdetails.MIOBSYNConfigurationDetails port = service.getMIOBSYNConfigurationDetailsPort();
    in.co.titan.configurationdetails.MIOBSYNConfigurationDetails port = service.getHTTPSPort();
    // TODO initialize WS operation arguments here
    com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNS parameters = new com.sap.document.sap.rfc.functions.ZSDFMGETDROPDOWNS();

    public ConfigurationDetails() {
        // setting all the values default first

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
        ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, request_timeout);//http://pitest:50400 SCTIND SCTIND@POS

        ((BindingProvider) port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, XIConnectionDetailsPojo.getUsername());
        ((BindingProvider) port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, XIConnectionDetailsPojo.getPassword());//XIConnectionDetailsPojo.getPassword());//http://pirdev:50000    http://pitest.titan.co.in:50400
        ((BindingProvider) port).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, XIConnectionDetailsPojo.getXi_server_port() + "/XISOAPAdapter/MessageServlet?channel=:BS_TITAN_POS:CC_Sender_ConfigurationDetails");
        System.out.println("" + XIConnectionDetailsPojo.getXi_server_port() + " " + XIConnectionDetailsPojo.getUsername() + " " + XIConnectionDetailsPojo.getPassword());
    }

    public void sendRequest(JTextArea statusArea) {
        try {
            result = port.miOBSYNConfigurationDetails(parameters);
            statusArea.append("\nRequest sent.......");
            // passing values to the managerclass to store to the POS Database
            ConfigurationWebService configurationWebService = new ConfigurationWebService(this, result, statusArea);
        } catch (Exception e) {
            statusArea.append("\nException......." + e.getMessage());
            e.printStackTrace();
        } finally {
        }

    }

    public boolean isIV_FLAG_CITY() {
        return IV_FLAG_CITY;
    }

    public void setIV_FLAG_CITY(boolean IV_FLAG_CITY) {
        this.IV_FLAG_CITY = IV_FLAG_CITY;
        if (IV_FLAG_CITY == true) {
            parameters.setIVFLAGCITY("X");

        } else {
            parameters.setIVFLAGCITY("Y");

        }
    }

    public boolean isIV_FLAG_COUNTRY() {
        return IV_FLAG_COUNTRY;
    }

    public void setIV_FLAG_COUNTRY(boolean IV_FLAG_COUNTRY) {
        this.IV_FLAG_COUNTRY = IV_FLAG_COUNTRY;
        setIV_FLAG_CITY(IV_FLAG_COUNTRY);
        setIV_FLAG_STATE(IV_FLAG_COUNTRY);
        if (IV_FLAG_COUNTRY == true) {
            parameters.setIVFLAGCOUNTRY("X");

        } else {
            parameters.setIVFLAGCOUNTRY("Y");
        }
    }

    public boolean isIV_FLAG_CURRENCYTYPE() {
        return IV_FLAG_CURRENCYTYPE;
    }

    public void setIV_FLAG_CURRENCYTYPE(boolean IV_FLAG_CURRENCYTYPE) {
        this.IV_FLAG_CURRENCYTYPE = IV_FLAG_CURRENCYTYPE;
        if (IV_FLAG_CURRENCYTYPE == true) {
            parameters.setIVFLAGCURRENCYTYPE("X");

        } else {
            parameters.setIVFLAGCURRENCYTYPE("Y");
        }
    }

    public boolean isIV_FLAG_CUSTOMERCATEGORY() {
        return IV_FLAG_CUSTOMERCATEGORY;
    }

    public void setIV_FLAG_CUSTOMERCATEGORY(boolean IV_FLAG_CUSTOMERCATEGORY) {
        this.IV_FLAG_CUSTOMERCATEGORY = IV_FLAG_CUSTOMERCATEGORY;
        if (IV_FLAG_CUSTOMERCATEGORY == true) {
            parameters.setIVFLAGCUSTOMERCATEGORY("X");

        } else {
            parameters.setIVFLAGCUSTOMERCATEGORY("Y");
        }
    }

    public boolean isIV_FLAG_DESIGNATION() {
        return IV_FLAG_DESIGNATION;
    }

    public void setIV_FLAG_DESIGNATION(boolean IV_FLAG_DESIGNATION) {
        this.IV_FLAG_DESIGNATION = IV_FLAG_DESIGNATION;
        if (IV_FLAG_DESIGNATION == true) {
            parameters.setIVFLAGDESIGNATION("X");

        } else {
            parameters.setIVFLAGDESIGNATION("Y");
        }
    }

    public boolean isIV_FLAG_EDUCATION() {
        return IV_FLAG_EDUCATION;
    }

    public void setIV_FLAG_EDUCATION(boolean IV_FLAG_EDUCATION) {
        this.IV_FLAG_EDUCATION = IV_FLAG_EDUCATION;
        if (IV_FLAG_EDUCATION == true) {
            parameters.setIVFLAGEDUCATION("X");

        } else {
            parameters.setIVFLAGEDUCATION("Y");
        }
    }

    public boolean isIV_FLAG_MARTIALSTATUS() {
        return IV_FLAG_MARTIALSTATUS;
    }

    public void setIV_FLAG_MARTIALSTATUS(boolean IV_FLAG_MARTIALSTATUS) {

        this.IV_FLAG_MARTIALSTATUS = IV_FLAG_MARTIALSTATUS;
        if (IV_FLAG_MARTIALSTATUS == true) {
            parameters.setIVFLAGMARTIALSTATUS("X");

        } else {
            parameters.setIVFLAGMARTIALSTATUS("Y");
        }
    }

    public boolean isIV_FLAG_RELATIONSHIP() {
        return IV_FLAG_RELATIONSHIP;
    }

    public void setIV_FLAG_RELATIONSHIP(boolean IV_FLAG_RELATIONSHIP) {
        this.IV_FLAG_RELATIONSHIP = IV_FLAG_RELATIONSHIP;
        if (IV_FLAG_RELATIONSHIP == true) {
            parameters.setIVFLAGRELATIONSHIP("X");

        } else {
            parameters.setIVFLAGRELATIONSHIP("Y");
        }
    }

    public boolean isIV_FLAG_RETREASON() {
        return IV_FLAG_RETREASON;
    }

    public void setIV_FLAG_RETREASON(boolean IV_FLAG_RETREASON) {
        this.IV_FLAG_RETREASON = IV_FLAG_RETREASON;
        if (IV_FLAG_RETREASON == true) {
            parameters.setIVFLAGRETREASON("X");

        } else {
            parameters.setIVFLAGRETREASON("Y");
        }
    }

    public boolean isIV_FLAG_STATE() {
        return IV_FLAG_STATE;
    }

    public void setIV_FLAG_STATE(boolean IV_FLAG_STATE) {
        this.IV_FLAG_STATE = IV_FLAG_STATE;
        setIV_FLAG_CITY(IV_FLAG_STATE);
        if (IV_FLAG_STATE == true) {
            parameters.setIVFLAGSTATE("X");

        } else {
            parameters.setIVFLAGSTATE("Y");
        }
    }

    public boolean isIV_FLAG_TITLE() {
        return IV_FLAG_TITLE;
    }

    public void setIV_FLAG_TITLE(boolean IV_FLAG_TITLE) {
        this.IV_FLAG_TITLE = IV_FLAG_TITLE;
        if (IV_FLAG_TITLE == true) {
            parameters.setIVFLAGTITLE("X");

        } else {
            parameters.setIVFLAGTITLE("Y");
        }
    }

    public boolean isIV_FLAG_UPDATIONMODE() {
        return IV_FLAG_UPDATIONMODE;
    }

    public void setIV_FLAG_UPDATIONMODE(boolean IV_FLAG_UPDATIONMODE) {
        this.IV_FLAG_UPDATIONMODE = IV_FLAG_UPDATIONMODE;
        if (IV_FLAG_UPDATIONMODE == true) {
            parameters.setIVFLAGUPDATIONMODE("X");

        } else {
            parameters.setIVFLAGUPDATIONMODE("Y");
        }
    }

    public boolean isIV_FLAG_CANCELREASON() {
        return IV_FLAG_CANCELREASON;
    }

    public void setIV_FLAG_CANCELREASON(boolean IV_FLAG_CANCELREASON) {

        this.IV_FLAG_CANCELREASON = IV_FLAG_CANCELREASON;
        if (IV_FLAG_CANCELREASON == true) {
            parameters.setIVFLAGCANCELREASON("X");

        } else {
            parameters.setIVFLAGCANCELREASON("");
        }

    }

    public boolean isIV_FLAG_EMPLOYEEMASTER() {
        return IV_FLAG_EMPLOYEEMASTER;
    }

    public void setIV_FLAG_EMPLOYEEMASTER(boolean IV_FLAG_EMPLOYEEMASTER) {
        this.IV_FLAG_EMPLOYEEMASTER = IV_FLAG_EMPLOYEEMASTER;
        if (IV_FLAG_EMPLOYEEMASTER == true) {
            parameters.setIVFLAGEXAMINEDBY("X");

        } else {
            parameters.setIVFLAGEXAMINEDBY("");
        }
    }

    public boolean isIV_FLAG_FOLLOWUP() {
        return IV_FLAG_FOLLOWUP;
    }

    public void setIV_FLAG_FOLLOWUP(boolean IV_FLAG_FOLLOWUP) {
        this.IV_FLAG_FOLLOWUP = IV_FLAG_FOLLOWUP;
        if (IV_FLAG_FOLLOWUP == true) {
            parameters.setIVFLAGFOLLWUP("X");

        } else {
            parameters.setIVFLAGFOLLWUP("");
        }
    }

    public boolean isIV_FLAG_OCCUPATION() {
        return IV_FLAG_OCCUPATION;
    }

    public void setIV_FLAG_OCCUPATION(boolean IV_FLAG_OCCUPATION) {
        this.IV_FLAG_OCCUPATION = IV_FLAG_OCCUPATION;
        if (IV_FLAG_OCCUPATION == true) {
            parameters.setIVFLAGOCCUPATION("X");

        } else {
            parameters.setIVFLAGOCCUPATION("");
        }
    }

    public boolean isIV_FLAG_PAYMENTMODE() {
        return IV_FLAG_PAYMENTMODE;
    }

    public void setIV_FLAG_PAYMENTMODE(boolean IV_FLAG_PAYMENTMODE) {
        this.IV_FLAG_PAYMENTMODE = IV_FLAG_PAYMENTMODE;
        if (IV_FLAG_PAYMENTMODE == true) {
            parameters.setIVFLAGPAYMENTMODES("X");

        } else {
            parameters.setIVFLAGPAYMENTMODES("");
        }
    }

    public boolean isIV_FLAG_CARDTYPES() {
        return IV_FLAG_CARDTYPES;
    }

    public void setIV_FLAG_CARDTYPES(boolean IV_FLAG_CARDTYPES) {
        this.IV_FLAG_CARDTYPES = IV_FLAG_CARDTYPES;
        if (IV_FLAG_CARDTYPES == true) {
            parameters.setIVFLAGCARDTYPES("X");

        } else {
            parameters.setIVFLAGCARDTYPES("");
        }
    }

    public boolean isIV_FLAG_COMMPRIORITY() {
        return IV_FLAG_COMMPRIORITY;
    }

    public void setIV_FLAG_COMMPRIORITY(boolean IV_FLAG_COMMPRIORITY) {
        this.IV_FLAG_COMMPRIORITY = IV_FLAG_COMMPRIORITY;
        if (IV_FLAG_COMMPRIORITY == true) {
            parameters.setIVFLAGCOMMPRIORITY("X");

        } else {
            parameters.setIVFLAGCOMMPRIORITY("");
        }
    }

    public boolean isIV_FLAG_ADVCANRES() {
        return IV_FLAG_ADVCANRES;
    }

    public void setIV_FLAG_ADVCANRES(boolean IV_FLAG_ADVCANRES) {
        this.IV_FLAG_ADVCANRES = IV_FLAG_ADVCANRES;

        if (IV_FLAG_ADVCANRES == true) {
            parameters.setIVFLAGADVCANRES("X");

        } else {
            parameters.setIVFLAGADVCANRES("");
        }
    }

    public boolean isIV_FLAG_CHARVALUES() {
        return IV_FLAG_CHARVALUES;
    }

    public void setIV_FLAG_CHARVALUES(boolean IV_FLAG_CHARVALUES) {
        this.IV_FLAG_CHARVALUES = IV_FLAG_CHARVALUES;

        if (IV_FLAG_CHARVALUES == true) {
            parameters.setIVFLAGCHARACTS("X");

        } else {
            parameters.setIVFLAGCHARACTS("");
        }
    }

    public boolean isIV_FLAG_BRANDTINT() {
        return IV_FLAG_BRANDTINT;
    }

    public void setIV_FLAG_BRANDTINT(boolean IV_FLAG_BRANDTINT) {
        this.IV_FLAG_BRANDTINT = IV_FLAG_BRANDTINT;

        if (IV_FLAG_BRANDTINT == true) {
            parameters.setIVFLAGBRAND("X");

        } else {
            parameters.setIVFLAGBRAND("");
        }
    }

    public boolean isIV_FLAG_DEFECTREASON() {
        return IV_FLAG_DEFECTREASON;
    }

    public void setIV_FLAG_DEFECTREASON(boolean IV_FLAG_DEFECTREASON) {
        this.IV_FLAG_DEFECTREASON = IV_FLAG_DEFECTREASON;
        if (IV_FLAG_DEFECTREASON == true) {
            parameters.setIVFLAGDEFECTREASON("X");

        } else {
            parameters.setIVFLAGDEFECTREASON("");
        }
    }

    public boolean isIV_FLAG_CONDMAP() {
        return IV_FLAG_CONDMAP;
    }

    public void setIV_FLAG_CONDMAP(boolean IV_FLAG_CONDMAP) {
        this.IV_FLAG_CONDMAP = IV_FLAG_CONDMAP;
        if (IV_FLAG_CONDMAP == true) {
            parameters.setIVFLAGCONDMAP("X");

        } else {
            parameters.setIVFLAGCONDMAP("");
        }
    }

    public boolean isIV_FLAG_BILLCANCREASON() {
        return IV_FLAG_BILLCANCREASON;
    }

    public void setIV_FLAG_BILLCANCREASON(boolean IV_FLAG_BILLCANCREASON) {
        this.IV_FLAG_BILLCANCREASON = IV_FLAG_BILLCANCREASON;
        if (IV_FLAG_BILLCANCREASON == true) {
            parameters.setIVFLAGBILLCANCREASON("X");

        } else {
            parameters.setIVFLAGBILLCANCREASON("");
        }
    }

    public boolean isIV_FLAG_POSPARAM() {
        return IV_FLAG_POSPARAM;
    }

    public void setIV_FLAG_POSPARAM(boolean IV_FLAG_POSPARAM) {
        this.IV_FLAG_POSPARAM = IV_FLAG_POSPARAM;
        if (IV_FLAG_POSPARAM == true) {
            parameters.setLVFLAGPOSPARAM("X");

        } else {
            parameters.setLVFLAGPOSPARAM("");
        }
    }

    public boolean isIV_FLAG_SITES() {
        return IV_FLAG_SITES;
    }

    public void setIV_FLAG_SITES(boolean IV_FLAG_SITES) {
        this.IV_FLAG_SITES = IV_FLAG_SITES;
        if (IV_FLAG_SITES == true) {
            parameters.setIVFLAGSITES("X");

        } else {
            parameters.setIVFLAGSITES("");
        }
    }

    public boolean isIV_FLAG_SGL_ATTRIBUTES() {
        return IV_FLAG_SGL_ATTRIBUTES;
    }

    public void setIV_FLAG_SGL_ATTRIBUTES(boolean IV_FLAG_SGL_ATTRIBUTES) {
        this.IV_FLAG_SGL_ATTRIBUTES = IV_FLAG_SGL_ATTRIBUTES;
        if (IV_FLAG_SGL_ATTRIBUTES == true) {
            parameters.setIVFLAGSGLATTRIBUTES("X");
        } else {
            parameters.setIVFLAGSGLATTRIBUTES("");
        }
    }

    public boolean isIV_FLAG_MASTERS() {
        return IV_FLAG_MASTERS;
    }

    public void setIV_FLAG_MASTERS(boolean IV_FLAG_MASTERS) {
        this.IV_FLAG_MASTERS = IV_FLAG_MASTERS;
        if (IV_FLAG_MASTERS == true) {
            parameters.setIVFLAGMASTERS("X");
        } else {
            parameters.setIVFLAGMASTERS("");
        }
    }

    public boolean isIV_FLAG_DOCTOR_NAME() {
        return IV_FLAG_DOCTOR_NAME;
    }

    public void setIV_FLAG_DOCTOR_NAME(boolean IV_FLAG_DOCTOR_NAME) {
        this.IV_FLAG_DOCTOR_NAME = IV_FLAG_DOCTOR_NAME;
        if (IV_FLAG_DOCTOR_NAME == true) {
            parameters.setFLAGSITESEARCH(ServerConsole.siteID);
            parameters.setIVFLAGDOCTORNAME("X");
        } else {
            parameters.setIVFLAGDOCTORNAME("");
        }
    }

    public boolean isIV_FLAG_DELIV_ARTICLE() {
        return IV_FLAG_DELIV_ARTICLE;
    }

    public void setIV_FLAG_DELIV_ARTICLE(boolean IV_FLAG_DELIV_ARTICLE) {
        this.IV_FLAG_DELIV_ARTICLE = IV_FLAG_DELIV_ARTICLE;
        if (this.IV_FLAG_DELIV_ARTICLE = true) {
            parameters.setFLAGSITESEARCH(ServerConsole.siteID);
            parameters.setIVFLAGDELIVARTICLE("X");
        } else {
            parameters.setIVFLAGDELIVARTICLE("");
        }
    }

    public boolean isIV_FLAG_DELIV_MERCH() {
        return IV_FLAG_DELIV_MERCH;
    }

    public void setIV_FLAG_DELIV_MERCH(boolean IV_FLAG_DELIV_MERCH) {
        this.IV_FLAG_DELIV_MERCH = IV_FLAG_DELIV_MERCH;
        if (this.IV_FLAG_DELIV_MERCH = true) {
            parameters.setFLAGSITESEARCH(ServerConsole.siteID);
            parameters.setIVFLAGDELIVMERCH("X");
        } else {
            parameters.setIVFLAGDELIVMERCH("");
        }
    }

    public boolean isIV_FLAG_cONTACT_LENS_PRODUCT_ATTRIBUTES() {
        return IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES;
    }

    public void setIV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES(boolean IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES) {
        this.IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES = IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES;
        if (IV_FLAG_CONTACT_LENS_PRODUCT_ATTRIBUTES == true) {
            parameters.setIVFLAGCLATTRPROD("X");
        } else {
            parameters.setIVFLAGCLATTRPROD("");
        }
    }

    public boolean isIV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES() {
        return IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES;
    }

    public void setIV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES(boolean IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES) {
        this.IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES = IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES;
        if (IV_FLAG_CONTACT_LENS_PACK_ATTRIBUTES == true) {
            parameters.setIVFLAGCLATTRPACK("X");
        } else {
            parameters.setIVFLAGCLATTRPACK("");
        }
    }

    public boolean isIV_FLAG_CONTACT_LENS_BASE_CURVE() {
        return IV_FLAG_CONTACT_LENS_BASE_CURVE;
    }

    public void setIV_FLAG_CONTACT_LENS_BASE_CURVE(boolean IV_FLAG_CONTACT_LENS_BASE_CURVE) {
        this.IV_FLAG_CONTACT_LENS_BASE_CURVE = IV_FLAG_CONTACT_LENS_BASE_CURVE;
        if (IV_FLAG_CONTACT_LENS_BASE_CURVE == true) {
            parameters.setIVFLAGCLATTRBASE("X");
        } else {
            parameters.setIVFLAGCLATTRBASE("");
        }
    }

    public boolean isIV_FLAG_CONTACT_LENS_TYPES() {
        return IV_FLAG_CONTACT_LENS_TYPES;
    }

    public void setIV_FLAG_CONTACT_LENS_TYPES(boolean IV_FLAG_CONTACT_LENS_TYPES) {
        this.IV_FLAG_CONTACT_LENS_TYPES = IV_FLAG_CONTACT_LENS_TYPES;
        if (IV_FLAG_CONTACT_LENS_TYPES == true) {
            parameters.setIVFLAGCLPRODTYPE("X");
        } else {
            parameters.setIVFLAGCLPRODTYPE("");
        }
    }
}

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
 * Class for Generic Data Handling like Drop dowm data ,File Handlers etc
 * 
 * 
 * 
 */package ISRetail.utility.db;

import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import ISRetail.utility.validations.Validations;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import javax.swing.JComboBox;

/**
 *
 * @author Administrator
 */
public class PopulateData {

    //populate sales return -DEFECT REASON
    public Properties populateSR_DefectResons(Connection con) {
        Properties defectReasonValues;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from tbl_parameter where parametertype = 'DEFR' and blocked = '' order by sortid");
            defectReasonValues = new Properties();
            // box.addItem("");
            defectReasonValues.put("0000", "<Choose>");
            while (rs.next()) {
                String id = rs.getString("id");
                String desc = rs.getString("description");
                defectReasonValues.put(id, desc);
                //     box.addItem(desc);
            }
            return defectReasonValues;
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        }
    }

    public Properties populateReasonsforSaleorderCancel(Connection con, JComboBox box) {
        try {
            Properties reasonForCancel;
            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from tbl_parameter where parametertype = 'CRES' and blocked = '' order by sortid");
            reasonForCancel = new Properties();
            box.addItem("");
            while (rs.next()) {
                String cancelcode = rs.getString(2);
                String canceleval = rs.getString(3);
                reasonForCancel.put(cancelcode, canceleval);
                box.addItem(canceleval);
            }
            return reasonForCancel;
        } catch (SQLException sQLException) {
            return null;
        }
    }

    public Properties populateCreditsalereference(Connection con, JComboBox box) {
        try {
            Properties creditsalereferenceno;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select sapcustomerno,institutionname from tbl_creditsalereference ");
            creditsalereferenceno = new Properties();
            if (box != null) {
                box.addItem("");
            }
            while (rs.next()) {
                String customerid = rs.getString(1);
                String institutionname = rs.getString(2);
                creditsalereferenceno.put(customerid, institutionname);
                if (box != null) {
                    box.addItem(rs.getString(2));
                }

            }
            return creditsalereferenceno;
        } catch (SQLException sQLException) {
            return null;
        }

    }

    /*
        * ****************************** The End *****************/
    public String getConfigValByConfigKey(Connection con, String configkey) {
        String configVal = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select configval from dbo.tbl_pos_configparam where configkey = '" + configkey + "'");
            if (rs.next()) {
                configVal = rs.getString("configval");
            }
        } catch (Exception e) {
        } finally {
            rs = null;
            try {
                stmt.close();
            } catch (Exception ea) {
            }
            stmt = null;
        }
        return configVal;
    }

    public String getFTPHostName(Connection con) {
        return getConfigValByConfigKey(con, "ftphostname");
    }

    public String getFTPUserName(Connection con) {
        return getConfigValByConfigKey(con, "ftpusername");
    }

    public String getFTPPassword(Connection con) {
        return getConfigValByConfigKey(con, "ftppassword");
    }

    public String getFTPFilePath(Connection con) {
        return getConfigValByConfigKey(con, "ftppath");
    }

    public String getFTPReceive(Connection con) {
        return getConfigValByConfigKey(con, "ftp_receive");
    }

    public String getFTPSend(Connection con) {
        return getConfigValByConfigKey(con, "ftp_send");

    }

    public String getFTPRemove(Connection con) {
        return getConfigValByConfigKey(con, "ftp_remove");

    }

    public String getConfigValue(Connection con, String key) {//Added by Balachander V on 1.11.2018 To get the config value by passing a config key
        Statement stmt = null;
        ResultSet rs;
        String val = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select configval from tbl_pos_configparam where configkey = '" + key + "' ");
            if (rs.next()) {
                val = rs.getString("configval");
            }
        } catch (SQLException sQLException) {
            sQLException.printStackTrace();
            return null;
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            stmt = null;
            rs = null;

        }
        return val;
    }

    public Properties getValFromProperties() {
        InputStream in;
        URL url;
        URI uri;
        File f;
        try {
            in = PopulateData.class.getClassLoader().getResourceAsStream("Isr_Pos_BackGround_Colors.properties");
            if (in == null) {
                url = getClass().getResource("/ISRetail/properties/Isr_Pos_BackGround_Colors.properties");
                uri = url.toURI();
                f = new File(uri);
                in = new FileInputStream(f);
            }

            Properties p = new Properties();
            p.load(in);
            in.close();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            in = null;
            url = null;
            uri = null;
            f = null;
        }
    }

    public static int getKarnivalinvoicecount(Connection con) {
        Statement stmt = null;
        ResultSet rs;
        int statscount = 0;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("select count(*) as statuscount from tbl_invoiceprint_status where send_status='N'");
            while (rs.next()) {
                if (rs.getInt("statuscount") > 0) {
                    statscount = rs.getInt("statuscount");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statscount;
    }
}

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
 * This is a class to get the Key Pair values from the Properties File
 * 
 * 
 * 
 */
package ISRetail.Webservices;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.ProxyDetailsPOJO;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class BackgroundPropertiesFromFile {

    /**
     * To get the MSDE Connection DetailsPOJO
     */
    public MsdeConnectionDetailsPojo getMsdeConnectionDetails() {
        MsdeConnectionDetailsPojo connectiondetails = new MsdeConnectionDetailsPojo();
        Properties properties;
        String hostname;
        String portno;
        String username;
        String dbname;
        String password = "";
        try {
            properties = getValFromProperties();
            hostname = (String) properties.get("hostname");
            portno = (String) properties.get("port_no");
            username = (String) properties.get("username");
            dbname = (String) properties.get("dbname");
            password = new StringBuffer(username).reverse().toString().trim();
            //            int length = username.length();
//            String Ascii = "";
//            for (int i = length - 1; i >= 0; i--) {
//                password = password.concat("" + username.charAt(i));
//                if(i >= 2){
//                Ascii = Ascii+""+(int)username.toUpperCase().charAt(i);
//               }              
//            }
//            password = password.toUpperCase();   
//            password = password+""+"@"+password.substring(2).toLowerCase()+""+""+Ascii;
//            System.out.println("Password : "+password);
            connectiondetails.setHostname(hostname);
            connectiondetails.setPortno(portno);
            connectiondetails.setUsername(username);
            connectiondetails.setPassword(password);
            connectiondetails.setDbname(dbname);
            try {
                File ff = new File("");
                String gg = ff.getAbsolutePath() + "\\config\\Isr_Pos_BackGround_Colors.properties";
                System.out.println("Current DB selection path is " + new File(gg).getAbsolutePath());
                if (new File(gg).canWrite()) {
                    System.out.println("File set to ready Only mode");
                    new File(gg).setReadOnly();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            properties = null;
            hostname = null;
            portno = null;
            username = null;
            dbname = null;
            password = null;
        }
        return connectiondetails;
    }

    /**
     * To get the XI Connection DetailsPOJO
     */
    public void getXIConnectionDetails() {
        Properties properties;
        String url = null;
        String username;
        String net_ip;
        String password;
        String pi_server_port = null;
        try {
            MsdeConnection msdbc = new MsdeConnection();
            Connection con = msdbc.createConnection();
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("select configval from dbo.tbl_pos_configparam where configkey = 'xi_server_port'");
            if (rs.next()) {
                pi_server_port = rs.getString(1);
            }
            rs = stmt.executeQuery("select configval from dbo.tbl_pos_configparam where configkey = 'url_xi'");
            if (rs.next()) {
                url = rs.getString(1);
            }
            properties = getValFromProperties();
            username = (String) properties.get("username_xi");
            password = (String) properties.get("password_xi");
            if (!Validations.isFieldNotEmpty(url)) {
                url = (String) properties.get("url_xi");
            }
            net_ip = (String) properties.get("url_net");

            XIConnectionDetailsPojo.setUsername(username);
            XIConnectionDetailsPojo.setPassword(password);
            XIConnectionDetailsPojo.setUrl(url);
            XIConnectionDetailsPojo.setIp_net(net_ip);
            XIConnectionDetailsPojo.setXi_server_port(pi_server_port);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            properties = null;
            url = null;
            net_ip = null;
            username = null;
            password = null;
        }
    }

    /**
     * To get all Properties from the Properties File
     */
    public Properties getValFromProperties() {
        Properties p;
        InputStream in;
        URL url;
        URI uri;
        File f;

        try {
            //

            //
            in = BackgroundPropertiesFromFile.class.getClassLoader().getResourceAsStream("Isr_Pos_BackGround_Colors.properties");
            if (in == null) {
                url = getClass().getResource("/ISRetail/properties/Isr_Pos_BackGround_Colors.properties");
                uri = url.toURI();
                System.out.println("Value : " + url);
                System.out.println("Value : " + uri);
                f = new File(uri);
                System.out.println("File path is " + f);
                in = new FileInputStream(f);
            }

            p = new Properties();
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

    /**
     * To get POS Server Port
     */
    public int getPosServerPort() {
        String hostname, port;
        int portno = 0;
        Properties properties;
        try {
            properties = getValFromProperties();
            port = (String) properties.get("posserverport");
            if (port != null) {
                portno = Integer.parseInt(port.trim());
            }
        } catch (Exception e) {

        } finally {
            properties = null;
        }
        return portno;

    }

    public void getProxySettings() {
        try {
            PopulateData backgroundcolors = new PopulateData();
            Properties properties = backgroundcolors.getValFromProperties();
            String proxyhost = (String) properties.get("proxy_host");
            String proxyport = (String) properties.get("proxy_port");
            ProxyDetailsPOJO.setProxyhost(proxyhost);
            ProxyDetailsPOJO.setProxyport(proxyport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

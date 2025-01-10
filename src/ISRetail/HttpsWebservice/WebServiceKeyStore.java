/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.HttpsWebservice;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.PosConfigParamDO;
import ISRetail.utility.validations.Validations;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import posstaging.FTPFilesDownloadThread;

/**
 *
 * @author eyeplus
 */
public class WebServiceKeyStore {

    public static boolean isValid = true;//For keystore revalidation
    private String ClientUpdatecheck = "";

    //Initalize the keystore name and password
    public static void keyStoreValues() {
        try {
            KeyStorePOJO.setKeystorename("./config/server");
            KeyStorePOJO.setKeystorepassword("changeit");
//            KeyStorePOJO.setKeystorename("./config/pirprd.titan.co.in_POSServer.jks");
//            KeyStorePOJO.setKeystorepassword("Secur!ty@ewp0ss");
//            KeyStorePOJO.setKeystorename("./config/pirdev.titan.co.in.jks");
//            KeyStorePOJO.setKeystorepassword("Hello1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Set the keystore name and password
    public void setKeyStoreSettings() {

        try {
            System.out.println("set the properties for keystore ");
            System.setProperty("javax.net.ssl.trustStore", KeyStorePOJO.getKeystorename());
            System.setProperty("javax.net.ssl.trustStorePassword", KeyStorePOJO.getKeystorepassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Remove the keystore name and password
    public void removeKeyStoreSettings() {
        try {
            if (Validations.isFieldNotEmpty(KeyStorePOJO.getKeystorename())) {
                System.getProperties().remove("javax.net.ssl.trustStore");
                System.getProperties().remove("javax.net.ssl.trustStorePassword");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Check the key store Validity. If validity expired download correct keystore
    public void keyStoreValidatidityCheck() {
        String fromdate = "", todate = "", currentdate = "";
        try {
            MsdeConnection msdeConnection = new MsdeConnection();
            Connection con = msdeConnection.createConnection();
            //ClientUpdatecheck = PosConfigParamDO.getValForThePosConfigKey(con, "ftp_send_files_posserver");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date date = new java.util.Date();
            currentdate = dateFormat.format(date);
            System.out.println("Current Date: " + dateFormat.format(date));
            System.out.println("Current Date: " + date);
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            keystore.load(new FileInputStream(KeyStorePOJO.getKeystorename()), KeyStorePOJO.getKeystorepassword().toCharArray());
            Enumeration<String> aliases = keystore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("alias name -- " + alias);
                if (keystore.getCertificate(alias).getType().equals("X.509")) {
                    System.out.println(alias + " To date " + ((X509Certificate) keystore.getCertificate(alias)).getNotAfter().toString());
                    System.out.println(alias + " From date " + ((X509Certificate) keystore.getCertificate(alias)).getNotBefore());
                    fromdate = dateFormat.format(((X509Certificate) keystore.getCertificate(alias)).getNotBefore());
                    todate = dateFormat.format(((X509Certificate) keystore.getCertificate(alias)).getNotAfter());
                    System.out.println("From date : " + fromdate);
                    System.out.println("To date : " + todate);
                    System.out.println("current date : " + currentdate);
                }
            }

//            TimeUnit.MINUTES.sleep(1);
//            JOptionPane.showMessageDialog(null, "SSL certicate Validity Issue. So, Please contact POS Development Team");
//                System.exit(0);
            System.out.println("jyoti : check :");
            if ((Validations.getDiffbtweendates(fromdate, currentdate) >= 0) && (Validations.getDiffbtweendates(currentdate, todate) >= 0)) {
                setKeyStoreSettings();
                System.out.println("It's working-- FROM DATE valid");
            } else if (isValid) {
//                new Thread(new FTPFilesDownloadThread()).start();//commented by charan for skipping the FTPFilesDownloadThread if there no update detected from the POSSClient on 31.0.1.24
//                    TimeUnit.MINUTES.sleep(1);
                //System.out.println("ClientUpdatecheck:" + ClientUpdatecheck);
                new Thread(new FTPFilesDownloadThread()).start();
                //if (ClientUpdatecheck.equalsIgnoreCase("Y")) {//copied from above added by charan for skipping the FTPFilesDownloadThread if there no update detected from the POSSClient on 31.0.1.24
                    TimeUnit.MINUTES.sleep(1);
                //}
                isValid = false;
                keystoreValidation();
            } else {
                JOptionPane.showMessageDialog(null, "SSL certicate Validity Issue. So, Please contact POS Development Team");
                System.exit(0);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Recheck for keystore validation
    public void keystoreValidation() {
        try {
            removeKeyStoreSettings();
            setKeyStoreSettings();
//            keyStoreValidatidityCheck();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void propertiesEdit(String location) {
        try {
            String line = "", propertiesEdit = "";//location = "C:\\Documents and Settings\\eyeplus\\My Documents\\NetBeansProjects\\config\\Isr_Pos_BackGround_Colors.properties";
            File congfigFile = new File(location);

            BufferedReader fileReader = new BufferedReader(new FileReader(congfigFile));
            while ((line = fileReader.readLine()) != null) {
                if (line.matches("url_xi = https://pirprd.titan.co.in:50001/index.html") || line.matches("url_net = pirprd.titan.co.in") || line.matches("xi_server_port   =   https://pirprd.titan.co.in:50001")) {
                    propertiesEdit += line + "\r\n";
                } else if (line.startsWith("url_net")) {
                    propertiesEdit += "url_net = pirprd.titan.co.in" + "\r\n";
                } else if (line.startsWith("url_xi")) {
                    propertiesEdit += "url_xi = https://pirprd.titan.co.in:50001/index.html" + "\r\n";
                } else if (line.startsWith("xi_server_port")) {
                    propertiesEdit += "xi_server_port   =   https://pirprd.titan.co.in:50001" + "\r\n";
                } else {
                    propertiesEdit += line + "\r\n";
                }
            }
            fileReader.close();
            FileWriter writer = new FileWriter(location);
            System.out.println(propertiesEdit);
            writer.write(propertiesEdit);
            writer.close();
            fileReader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}

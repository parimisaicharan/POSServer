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
 * This class file is used to Check Net Connection Availability
 * 
 * 
 * 
 * 
 */
package ISRetail.Helpers;

import ISRetail.Webservices.XIConnectionDetailsPojo;
import ISRetail.serverconsole.ProxyDetailsPOJO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class NetConnection {

    /**
     * To Check XI Connection THe XI Server details are available in the
     * Properties File
     */
    public static int checkXIConnection() {
        int i = 0;
        URL url;
        HttpURLConnection httpurlconnection;
        try {
//setProxy();
            url = new URL(XIConnectionDetailsPojo.getUrl());
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setReadTimeout(60000);
            httpurlconnection.connect();
            if (httpurlconnection.getResponseMessage().equals("OK")) {
                i = 1;
            }
        } catch (MalformedURLException malurlex) {
            i = 2;
        } catch (IOException ioexception) {
            i = 3;
        } catch (Exception superex) {
            i = 4;
        } finally {
            url = null;
            httpurlconnection = null;
        }

        return i;
    }

    /**
     * To Check Net Connection THe Net Server details are available in the
     * Properties File
     */
    public static int checkInternetConnection() {
        /*  int i = 0;
        String iaddress;
        InetAddress iadd;
        try {
            iaddress = XIConnectionDetailsPojo.getIp_net();
            iadd = InetAddress.getByName(iaddress);                      
            if(iadd.isReachable(30000)){
                i = 1;
            } else {                
                i = 2;
            }
        } catch(Exception superex) {
            i = 3;
        } finally {
            iaddress = null;
            iadd = null;
        }        
        return i;
    }*///Commented on 07 Jan 2011 for Windows 7
        int i = 0;
        URL url;
        HttpURLConnection httpurlconnection;
        try {
            String urlxi = XIConnectionDetailsPojo.getUrl();
            System.out.println("url-xi   " + urlxi);
            url = new URL(urlxi);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            System.err.println(httpurlconnection);
            httpurlconnection.setReadTimeout(60000);
            httpurlconnection.connect();
            if (httpurlconnection.getResponseMessage().equals("OK")) {
                i = 1;
            }
        } catch (MalformedURLException malurlex) {
            i = 2;
        } catch (IOException ioexception) {
            i = 3;
        } catch (Exception superex) {
            i = 4;
        } finally {
            url = null;
            httpurlconnection = null;
        }

        return i;
    }



public static void setProxy() {
        try {
            System.out.println("ProxyDetailsPOJO.getProxyhost()" + ProxyDetailsPOJO.getProxyhost());
            System.out.println("ProxyDetailsPOJO.getProxyport()" + ProxyDetailsPOJO.getProxyport());
            if (ProxyDetailsPOJO.getProxyhost() != null) {
                System.setProperty("http.proxySet", "true");
                System.setProperty("http.proxyHost", ProxyDetailsPOJO.getProxyhost());
                System.setProperty("http.proxyPort", ProxyDetailsPOJO.getProxyport());
                System.setProperty("https.proxySet", "true");
                System.setProperty("https.proxyHost", ProxyDetailsPOJO.getProxyhost());
                System.setProperty("https.proxyPort", ProxyDetailsPOJO.getProxyport());
//                System.out.println("System proxy"+ProxyDetailsPOJO.getProxyhost());
//                System.out.println("ProxyDetailsPOJO.getProxyport())"+ProxyDetailsPOJO.getProxyport());
                

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void removeProxy() {
        try {
            System.out.println("Removing network pojo");
            if (ProxyDetailsPOJO.getProxyhost() != null) {
                System.getProperties().remove("http.proxyHost");
                 System.getProperties().remove("http.proxyPort");
                System.getProperties().remove("https.proxyHost");
                System.getProperties().remove("https.proxyPort");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
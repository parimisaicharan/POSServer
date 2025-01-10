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
 * POJO Class For XI Connetion Details 
 * 
 * 
 */
package ISRetail.Webservices;

public class XIConnectionDetailsPojo {

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String aUrl) {
        url = aUrl;
    }

    public static String getIp_net() {
        return ip_net;
    }

    public static void setIp_net(String aIp_net) {
        ip_net = aIp_net;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String aUsername) {
        username = aUsername;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    public static String getXi_server_port() {
        return xi_server_port;
    }

    public static void setXi_server_port(String pi_server_port) {
        xi_server_port = pi_server_port;
    }
    
private static String username;
private static String password;
private static String url;
private static String ip_net;
private static String xi_server_port;
}

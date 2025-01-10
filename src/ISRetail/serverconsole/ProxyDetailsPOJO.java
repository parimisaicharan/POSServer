/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.serverconsole;

/**
 *
 * @author ewitsupport
 */
public class ProxyDetailsPOJO {
    private static String proxyhost;
private static String proxyport;

   
    public static String getProxyhost() {
        return proxyhost;
    }

    
    public static void setProxyhost(String aProxyhost) {
        proxyhost = aProxyhost;
    }

    
    public static String getProxyport() {
        return proxyport;
    }

    
    public static void setProxyport(String aProxyport) {
        proxyport = aProxyport;
    }

}

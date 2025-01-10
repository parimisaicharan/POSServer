/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.ServerRmi;

/**
 *
 * @author eyeplus
 */
import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.Webservices.MsdeConnectionDetailsPojo;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;

public class StartRMIServer {

public static String HOST_NAME;


    public void start () {
        try {
//            BackgroundPropertiesFromFile bf = new BackgroundPropertiesFromFile();
//            MsdeConnectionDetailsPojo msdeConnectionDetailsPojo;
//            msdeConnectionDetailsPojo = bf.getMsdeConnectionDetails();
            PosClientMonitor c = new PosClientMonitorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            System.out.println("RMI Server host : "+StartRMIServer.HOST_NAME);
            if(StartRMIServer.HOST_NAME!=null) {
                Naming.rebind("rmi://"+StartRMIServer.HOST_NAME+":1099/POSClientMonitorService", c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getRunningClients() {
       StringBuffer clients = null;
       HashSet<String> hs = null; 
       try {
           System.out.println("Inside Got Running Clients");
            clients = new StringBuffer();
            //hs = new HashSet<String>();
            PosClientMonitor c = (PosClientMonitor) Naming.lookup("//"+StartRMIServer.HOST_NAME+":1099/POSClientMonitorService");
            hs = c.getClients();
            for (String s : hs) {
               clients.append(s+" ");
                System.out.println("Got Running Clients : "+s);
            }
            return clients.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            hs = null;
        }
   }
}

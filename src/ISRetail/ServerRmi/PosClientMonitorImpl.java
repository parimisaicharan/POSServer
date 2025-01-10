/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.ServerRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;

/**
 *
 * @author eyeplus
 */
public class PosClientMonitorImpl extends UnicastRemoteObject implements PosClientMonitor{

    private static HashSet<String> clients;

    protected PosClientMonitorImpl() throws RemoteException {
        super();
        clients = new HashSet<String>();
    }

    public boolean addHost(String ip) throws RemoteException {
        clients.add(ip);
        return true;
    }

    public boolean removeHost(String ip) throws RemoteException {
        clients.remove(ip);
        return true;
    }

    public HashSet<String> getClients() throws RemoteException  {
        for (String hostname : clients) {
             System.out.println("Running client : "+hostname);
        }
        return clients;
    }

}

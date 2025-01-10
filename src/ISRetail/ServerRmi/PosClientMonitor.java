/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.ServerRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
/**
 *
 * @author eyeplus
 */



public interface  PosClientMonitor extends Remote {

    public boolean addHost(String ip) throws RemoteException;
    public boolean removeHost(String ip) throws RemoteException;
    public HashSet<String> getClients() throws RemoteException;
    
}

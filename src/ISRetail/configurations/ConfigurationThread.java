/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ISRetail.configurations;

import ISRetail.Helpers.NetConnection;
import ISRetail.components.JTextAreaFixed;
import javax.swing.JTextArea;



/**
 *
 * @author Administrator
 */
public class ConfigurationThread  implements Runnable{
Thread t ;
JTextArea statusArea;
ConfigurationDetails details;
private int i = 0;
    

public ConfigurationThread(ConfigurationDetails details, JTextArea statusArea) {
    this.details = details;
    this.statusArea=statusArea;
    
  }

    ConfigurationThread() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
public void run() {
   
    try{
        statusArea.append("\nDownloading Configuration Data.......");
            boolean valid = true;
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3||internetconnection == 2) {
                valid = false;
                 statusArea.append("\nDownload Failed : Check Your Network for Internet Connection !");
            setI(2);
            return;
            }
                else {                    
                    internetconnection = NetConnection.checkXIConnection();
                    if (internetconnection ==2) {
                       valid = false; 
                       setI(3);
                        statusArea.append("\nFailed : Incorrect Server Address : Contact Administrator to Fix the Problem!");
                       return;
                    } else if(internetconnection ==3||internetconnection ==4) {
                        valid = false; 
                        statusArea.append("\nFailed : Unknown Error : Contact Administrator to Fix the Problem!");
                        setI(4);
                        return;
                    }
            }
            if(valid){
                 statusArea.append("\nSending request to ISR...");
                details.sendRequest(statusArea);            
                setI(1);                
            }
            return;
        }catch(Exception e){
            setI(0);
            e.printStackTrace();
            return;
        }
       
       }

    

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}

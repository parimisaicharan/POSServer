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
 * Server Socket for POS Server Application 
 * 
 * 
 * 
 */
package ISRetail.clientconnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JTextArea;

public class clientConnectionAccepterThread implements Runnable {

    private Socket socket;
    ServerSocket serverSocket;

    /**
     * Constructor
     */
    public clientConnectionAccepterThread(ServerSocket serverSocket, int port) throws IOException {
        this.serverSocket = serverSocket;
    }

    /**
     * To run to accept the client connections
     */
    public void run() {
        ObjectInputStream ois;
        String clientIP;
        ObjectOutputStream oos;
        try {
            while (true) {
                try {
                    socket = serverSocket.accept();
                    //
                    // Read a message sent by client application
                    //
                    ois = new ObjectInputStream(socket.getInputStream());
                    clientIP = (String) ois.readObject();
                    //
                    // Send a response information to the client application
                    //
                    oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject("Welcome " + clientIP);
                    ois.close();
                    oos.close();
                    socket.close();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //ois = null;
                    //oos = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ois = null;
            oos = null;
            clientIP = null;
        }
    }
}

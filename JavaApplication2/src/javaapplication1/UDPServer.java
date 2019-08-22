/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author vilel
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
public class UDPServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DatagramPacket dataPacket, returnPacket = null;
        int port = 3333;
        int len = 1024;
        
        try(DatagramSocket datasocket = new DatagramSocket(port);) { // Try with resources - JDK 1.8
            System.out.println("Datagram server started on port " + port + "\n\n");
            byte[] buf = new byte[len];
            while (true) {
                try {
                    dataPacket = new DatagramPacket(buf, buf.length);
                    datasocket.receive(dataPacket);  // This method blocks until a datagram is received
                    
                    byte[] data =  dataPacket.getData();
                    int packageLength = dataPacket.getLength();
                    InetAddress address = dataPacket.getAddress();
                    int portDatagramReceived = dataPacket.getPort();
                   
                    System.out.print("Message data received from client..: ");
                    
                    for (int i = 0; i< data.length; i++) 
                    	System.out.print((char)data[i]);   
                    
                    System.out.println("\nDatagram length..: " + packageLength + " characteres");
                    System.out.println("Client adrress..: " + address);
                    System.out.println("Remote host port the datagram was received..: " + portDatagramReceived + "\n");
                    
                    returnPacket = new DatagramPacket(data, packageLength, address, portDatagramReceived);
                    datasocket.send(returnPacket);
                    
                } catch (IOException e) { System.err.println(e);}
            }
        } catch (SocketException se) { System.err.println(se);}
    }
    
}
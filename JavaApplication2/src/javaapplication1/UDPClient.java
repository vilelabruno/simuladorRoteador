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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String hostName;
        int port = 3333; int len = 1024;
        DatagramPacket sPacket, rPacket;
        hostName = (args.length > 0) ? args[0] : "localhost";
        try {
            InetAddress ia = InetAddress.getByName(hostName);
            System.out.println("Determining the IP address of a host, given the host's name.");  
            System.out.println(InetAddress.getByName(hostName));
            System.out.println("\nAny message you type and hit enter, will be echoed back to you by the server.");
            System.out.println("Type done to quit the client");
            BufferedReader stdinp = new BufferedReader(new InputStreamReader(System.in));
            
            while (true) {
                try (DatagramSocket dataSocket = new DatagramSocket();) { // Try with resources - JDK 1.8
                    String echoline = stdinp.readLine();
                    if (echoline.equalsIgnoreCase("done")) break;
                    byte[] buffer = new byte[echoline.length()];
                    buffer = echoline.getBytes();
                    sPacket = new DatagramPacket(buffer, buffer.length, ia, port);
                    dataSocket.send(sPacket);
                    byte[] rbuffer = new byte[len];
                    rPacket = new DatagramPacket(rbuffer, rbuffer.length);
                    dataSocket.receive(rPacket);
                    String retstring = new String(rPacket.getData(), 0,rPacket.getLength());
                    System.out.println(retstring);
                    System.out.println("\n");
                } catch (IOException e) {
                    System.err.println(e);
                }
            } // while
        } catch (UnknownHostException e) {
            System.err.println(e);
        }
    }
    
}
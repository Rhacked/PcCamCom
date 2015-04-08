/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pccamcom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 *
 * @author Ruben
 */
public class PcCamCom {

    /**
     * @param args the command line arguments
     * For at dette skal funke må man sette opp ip manuelt FOR NOW
     */
    public static void main(String[] args)throws UnknownHostException, IOException {
        //Connection con1 = new Connection("wlan15", 0); //TestCam1
        Connection con2 = new Connection("wlan15", 0); //TestCam2
        //Thread t1 = new Thread(con1);
        Thread t2 = new Thread(con2); 
        //t1.start();
        t2.start();
        
    }
    
}

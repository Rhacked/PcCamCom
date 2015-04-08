/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pccamcom;

/**
 *
 * @author Ruben
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Connection1 implements Runnable{
    PrintWriter out;
    String command = "PW?t=testtest&p=%01";
    Socket socket;
    int delay;
    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
    String IP = "10.5.5.9";
    
    public Connection1(String nifName, int delay) throws IOException, UnknownHostException {
        socket = new Socket();
        NetworkInterface nif = NetworkInterface.getByName(nifName);
        Enumeration<InetAddress> nifAddress = nif.getInetAddresses();
        socket.bind(new InetSocketAddress(nifAddress.nextElement(),0));
        socket.connect(new InetSocketAddress("10.5.5.9",80));
        out = new PrintWriter(socket.getOutputStream(), true);
        this.delay = delay;
        
    }
    
    public Connection1(String IP) throws IOException, UnknownHostException{
        this.delay = 0;
        this.IP = IP;
    }
    

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
            out = new PrintWriter(socket.getOutputStream(),true);
        } catch (InterruptedException ix) {
            ix.printStackTrace(System.err);
        } catch (IOException ioe){
            ioe.printStackTrace(System.err);
            return;
        }
        out.println("GET /bacpac/"+command+" HTTP/1.1");
        out.println("");
        out.flush();
        try{
        socket.close();
        } catch (IOException e){
            e.printStackTrace(System.err);
        } finally {
            System.out.println("Thread finished");
        }
    }
    
    public String bind(String otherNif) throws InterruptedException{
        NetworkInterface nextElement = null;
        while(interfaces.hasMoreElements()){
            nextElement = interfaces.nextElement();
            if(!nextElement.getName().equals(otherNif)){
            try{
                Enumeration<InetAddress> nifAddress = nextElement.getInetAddresses();
                this.socket = new Socket();
                try{
                    socket.bind(new InetSocketAddress(nifAddress.nextElement(),0));
                    socket.connect(new InetSocketAddress(IP,80));
                    System.out.println("****Network found****");
                    System.out.println(nextElement.getName());
                    Thread.sleep(1000);
                    return nextElement.getName();
                } catch (SocketException se){
                    se.printStackTrace(System.err);
                }
            } catch (UnknownHostException uhe){
                uhe.printStackTrace(System.err);
            } catch (IOException ioe){
                ioe.printStackTrace(System.err);
            } catch(NoSuchElementException nee){
                nee.printStackTrace(System.err);
            }
        }
        }
        return "";
    }
    
    public void setCommand(String cmd, String tall){
        this.command = cmd+"?t=testtest&p=%"+tall;
    }
    
    
    public static void main(String[] args)throws UnknownHostException, IOException, InterruptedException {
        Connection1 con = new Connection1("10.5.5.9");
        Connection1 con2 = new Connection1("10.5.5.9");
        con2.bind(con.bind(null));
        
        
        con.run();
        con2.run();
        
    }
    
}

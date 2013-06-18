package org.kde.connect.ComputerLinks;

import android.os.AsyncTask;
import android.util.Log;

import org.kde.connect.Types.NetworkPackage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpComputerLink extends BaseComputerLink {

    final int UDP_PORT = 10601;
    final String IP = "192.168.1.48";
    final int BUFFER_SIZE = 5*1024;

    public UdpComputerLink() {
        new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    Log.e("UdpComputerLink","Waiting for udp datagrams");
                    byte[] buffer = new byte[BUFFER_SIZE];
                    DatagramSocket socket = new DatagramSocket(UDP_PORT);
                    while(true){
                        DatagramPacket packet = new DatagramPacket(buffer, buffer.length );
                        socket.receive(packet);
                        String s = new String(packet.getData(), 0, packet.getLength());
                        packageReceived(NetworkPackage.fromString(s));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void sendPackage(NetworkPackage np) {
        final String messageStr = np.toString();
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    int server_port = UDP_PORT;
                    DatagramSocket s = new DatagramSocket();
                    InetAddress local = InetAddress.getByName(IP);
                    int msg_length = messageStr.length();
                    byte[] message = messageStr.getBytes();
                    DatagramPacket p = new DatagramPacket(message, msg_length,local,server_port);
                    s.send(p);
                    Log.e("Sent", messageStr);
                } catch(Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
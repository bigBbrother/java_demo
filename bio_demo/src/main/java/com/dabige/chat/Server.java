package com.dabige.chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Socket> allSocketOnLine = new ArrayList<Socket>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                allSocketOnLine.add(socket);
                new ThreadServerReader(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

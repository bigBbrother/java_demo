package com.dabige.file;

import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8989);
            while (true) {
                Socket socket = serverSocket.accept();
                new ThreadServerReader(socket).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.dabige.chat;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

public class ThreadServerReader extends Thread {
    private Socket socket;

    public ThreadServerReader(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = bufferedReader.readLine()) != null) {
                sendMsgToAllClient(msg);
            }

        } catch (IOException e) {
            System.out.println("当前有人下线");
            Server.allSocketOnLine.remove(socket);
        }
    }

    private void sendMsgToAllClient(String msg) {
        for (Socket socket : Server.allSocketOnLine) {
            try {
                PrintStream printStream = new PrintStream(socket.getOutputStream());
                printStream.println(msg);
                printStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

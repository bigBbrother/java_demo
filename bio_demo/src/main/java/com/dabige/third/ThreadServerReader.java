package com.dabige.third;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadServerReader extends Thread {
    private Socket socket;

    public ThreadServerReader(Socket socket) {
        this.socket = socket;
    }


    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            //4:把字节输入流转化成字符缓存输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("服务端接受到的" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

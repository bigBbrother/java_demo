package com.dabige.four;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRunnableTarget implements Runnable {
    private Socket socket;

    public ServerRunnableTarget(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            //3：从socket管道中获取字节输入流对象
            InputStream inputStream = socket.getInputStream();
            //4:把字节输入流转化成字符缓存输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("服务端接受到的" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.dabige.two;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        ServerSocket serverSocket;
        {
            try {
                System.out.println("-------------------------服务端启动---------------------------------------");
                //1:定义一个serverSocket对象进行服务端注册
                serverSocket = new ServerSocket(9999);
                //2:监听客户端socket连接请求
                Socket socket = serverSocket.accept();
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

}

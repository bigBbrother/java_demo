package com.dabige.third;

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
                while (true) {
                    Socket socket = serverSocket.accept();
                    new ThreadServerReader(socket).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

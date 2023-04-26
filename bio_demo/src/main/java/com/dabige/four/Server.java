package com.dabige.four;


import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            //创建线程池对象
            HandlerSocketServerPool pool = new HandlerSocketServerPool(3, 3);
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new ServerRunnableTarget(socket);
                pool.execute(runnable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

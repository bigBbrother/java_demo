package com.dabige.one;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        //1：创建socket对象请求服务器的连接
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            //2：获取字节输出流
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            printStream.println("HelloWorld");
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

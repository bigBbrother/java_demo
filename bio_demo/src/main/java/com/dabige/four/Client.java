package com.dabige.four;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        //1：创建socket对象请求服务器的连接
        try {
            Socket socket = new Socket("127.0.0.1", 9999);
            //2：获取字节输出流
            OutputStream outputStream = socket.getOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("请说：");
                String msg = sc.nextLine();
                printStream.println(msg);
                printStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

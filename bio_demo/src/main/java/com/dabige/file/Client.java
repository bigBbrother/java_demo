package com.dabige.file;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (
                InputStream inputStream = new FileInputStream("D:\\Demo2\\大逼哥.jpg");
        ) {
            Socket socket = new Socket("127.0.0.1", 8989);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(".jpg");
            byte[] bytes = new byte[1024];
            int len;

            while ((len = inputStream.read(bytes)) != -1) {
                dataOutputStream.write(bytes, 0, len);
            }
            dataOutputStream.flush();
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

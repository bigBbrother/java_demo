package com.dabige.file;

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
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            //读取文件名后缀
            String suffix = dataInputStream.readUTF();
            System.out.println("开始接受文件,文件类型为" + suffix);
            byte[] bytes = new byte[1024];
            int len;
            OutputStream outputStream = new FileOutputStream("C:\\Users\\liaohongxin\\Desktop\\新建文件夹\\"
                    + UUID.randomUUID().toString() + suffix);
            while ((len = dataInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            System.out.println("服务器接受文件成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

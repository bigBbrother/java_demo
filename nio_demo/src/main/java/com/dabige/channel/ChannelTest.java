package com.dabige.channel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class ChannelTest {
    @Test
    public void copy() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\Demo2\\大逼哥.jpg");
        FileOutputStream fos = new FileOutputStream("D:\\Demo2\\tt\\壁纸.jpg");
        //获取输入管道
        FileChannel fisChannel = fis.getChannel();
        //获取输出管道
        FileChannel fosChannel = fos.getChannel();
        //分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear();
            int flag = fisChannel.read(byteBuffer);
            if (flag == -1) {
                break;
            }
            byteBuffer.flip();
            fosChannel.write(byteBuffer);
        }
        fisChannel.close();
        fosChannel.close();
        System.out.println("文件复制完毕");
    }

    @Test
    public void test1() throws Exception {
        FileInputStream fis = new FileInputStream("D:\\Demo2\\test.txt");
        FileOutputStream fos = new FileOutputStream("D:\\Demo2\\tt\\test123.txt");
        FileChannel fisChannel = fis.getChannel();
        FileChannel fosChannel = fos.getChannel();
        //fosChannel.transferFrom(fisChannel,fisChannel.position(),fisChannel.size());
        fisChannel.transferTo(fisChannel.position(), fisChannel.size(), fosChannel);
        fisChannel.close();
        fosChannel.close();
        System.out.println("复制完成");

    }


}

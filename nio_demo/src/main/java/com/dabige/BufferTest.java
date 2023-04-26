package com.dabige;

import org.junit.Test;

import java.nio.ByteBuffer;

public class BufferTest {
    @Test
    public void test01() {

        //分配一个缓冲区，容量设置为10
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------------------------------------------------------------");
        //put往缓冲区添加数据
        String msg = "大逼哥";
        byteBuffer.put(msg.getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        //调用flip函数 切换为可读模式
        System.out.println("----------------------------------------------------------------");
        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------------------------------------------------------------");

        //get用来数据读取
        char ch = (char) byteBuffer.get();
        System.out.println(ch);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

    }

    @Test
    public void test02() {
        //分配一个缓冲区，容量设置为10
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------------------------------------------------------------");
        //put往缓冲区添加数据
        String msg = "dabige";
        byteBuffer.put(msg.getBytes());
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------------------------------------------------------------");

        //clear清除
        byteBuffer.clear();
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
        System.out.println("----------------------------------------------------------------");
    }
}

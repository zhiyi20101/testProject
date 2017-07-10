package com.scott.testproject.nio;

import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

/**
 * Created by zouzhiyi on 22/03/17.
 */

public class NIOTest
{
    private static final String TAG = NIOTest.class.getSimpleName();
    public static void testBuffer() throws IOException
    {
        RandomAccessFile aFile = new RandomAccessFile("/sdcard/test.txt","rw");
        //获取文件通道
        FileChannel inChannel = aFile.getChannel();
        //创建48个字节的缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(48);
        //从通道读取数据到缓冲区
        int readSize = inChannel.read(buffer);
        while (readSize != -1){
            //将buffer的写模式切换到读模式
            buffer.flip();
            char data;
            while (buffer.hasRemaining()){
                data = (char)buffer.get();
                Log.d(TAG,"data:"+data);
            }
            buffer.clear();
            readSize = inChannel.read(buffer);
        }
        aFile.close();
    }

    public static void testTransferFrom() throws IOException {
        RandomAccessFile fromFile = new RandomAccessFile("/sdcard/test.txt","rw");
        FileChannel fromChannel = fromFile.getChannel();
        RandomAccessFile toFile = new RandomAccessFile("/sdcard/to.txt","rw");
        FileChannel toChannel = toFile.getChannel();
        long position = 0;
        long count = fromChannel.size();
        fromChannel.transferTo(position,count,fromChannel);

    }

    private static void testGather(){
        ByteBuffer header = ByteBuffer.allocate(48);
        ByteBuffer body = ByteBuffer.allocate(1248);
    }
}

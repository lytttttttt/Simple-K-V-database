package server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/*
    处理连接后的事项
 */
public class Handler {
    public void aa(SocketChannel socketChannel) throws IOException {
        //while(true){
            System.out.println("客户端已连接");

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int length;
            do{
                length = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    System.out.print((char)byteBuffer.get());
                }
                byteBuffer.clear();
            }while(length != 0);

            byteBuffer.put("success".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            System.out.println("已写入");

            socketChannel.close();
        //}
    }
}

package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/*
    客户端运行类
 */
public class ClientMain {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        //while(true){
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //byteBuffer.put("客户端请求连接".getBytes());
            byteBuffer.put("hello".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.flip();
            int length;
            while((length = socketChannel.read(byteBuffer)) > 0){
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()){
                    System.out.print((char)byteBuffer.get());
                }
                byteBuffer.clear();
            }
        // }
    }
}

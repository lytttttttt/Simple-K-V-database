package client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/*
    开启客户端线程
 */
public class ClientThread extends Thread {
    private SocketChannel socketChannel;    //创建通道
    private static String hostName;   //本机地址

    public ClientThread(SocketChannel socketChannel, String hostName) {
        this.socketChannel = socketChannel; //接收通道以读取数据
        this.hostName = hostName;   //用于打印地址
    }

    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);  //使用buffer对象读或写数据至通道
        Charset charset = Charset.forName("UTF-8"); //获得“UTF-8”编码对象
        CharsetDecoder charsetDecoder = charset.newDecoder();   //获得解码器

        while (true) {

            try {
                if (socketChannel.isOpen() && (socketChannel.read(byteBuffer) > 0)) {    //读取通道内容至byteBuffer
                    byteBuffer.flip();  //将buffer转换为读模式
                    System.out.print(charsetDecoder.decode(byteBuffer));   //将读到内容解码后打印
                    byteBuffer.clear(); //清空缓冲区以便再次读取
                }
                System.out.print(hostName + ">>");   //提示输入
            } catch (IOException e) {
                System.out.println("通道关闭");
                break;
            }
        }

    }
}

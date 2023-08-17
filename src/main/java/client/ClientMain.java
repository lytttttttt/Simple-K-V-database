package client;

import function.OtherCommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

/*
    客户端运行类
 */
public class ClientMain {
    private static SocketChannel socketChannel; //创建客户端通道
    private static String hostName;   //本机地址
    private static int port; //端口号

    public ClientMain(String hostName, int port){
        try {
            socketChannel = SocketChannel.open();   //打开客户端通道
            socketChannel.connect(new InetSocketAddress(hostName, port));    //将客户端通道连接至地址某个端口
            Charset charset = Charset.forName("UTF-8"); //获得“UTF-8”编码对象
            new ClientThread(socketChannel, hostName).start();

            System.out.print(hostName + ">>");   //提示输入
            while(true){
                String message = "";    //创建空字符串接收输入
                Scanner scanner = new Scanner(System.in);   //获取标准输入对象
                message = scanner.nextLine();    //输入内容，以输入换行结束
                socketChannel.write(charset.encode(message)); //写入内容
                if("exit".equals(message))
                    break;
            }

            socketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

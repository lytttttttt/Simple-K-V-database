package server;


import function.OtherCommand;
import tool.LogTool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/*
    接受和分发任务的类
 */
public class Reactor {
    public Reactor(String hostname, int port) throws IOException, ClassNotFoundException {   //接受端口号
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  //开启服务端通道
        InetSocketAddress inetSocketAddress = new InetSocketAddress(hostname, port);  //本地地址和端口
        serverSocketChannel.bind(inetSocketAddress);    //绑定端口
        serverSocketChannel.configureBlocking(false);   //将通道设定为非阻塞状态

        Selector selector = Selector.open();    //获取选择器

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //服务端接收通道以接收类型注册到选择器中
        new ServerThread(serverSocketChannel).start();  //服务端输入线程
        LogTool.infoOut("服务端接收通道已开启");
        OtherCommand.bgsave();  //后台自动保存

        new Accepter(selector);   //由Accept类处理连接
    }
}

package server;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/*
    接受和分发任务的类
 */
public class Reactor {
    public Reactor(int port) throws IOException {   //接受端口号
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  //开启服务端通道
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",port);  //本地地址和端口
        serverSocketChannel.bind(inetSocketAddress);    //绑定端口
        serverSocketChannel.configureBlocking(false);   //将通道设定为非阻塞状态

        Selector selector = Selector.open();    //获取选择器

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); //服务端接收通道以接收类型注册到选择器中
        System.out.println("服务端接收通道已开启");

        new Accepter(selector);   //由Accept类处理连接
    }
}

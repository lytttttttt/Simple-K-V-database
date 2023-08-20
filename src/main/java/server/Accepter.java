package server;

import java.io.IOException;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/*
    处理服务端通道的连接
 */
public class Accepter {
    private static HashMap<SocketChannel, Handler> clientHandler;   //预备一个存放客户端通道以及对应事件处理的集合

    public Accepter(Selector selector) throws IOException, ClassNotFoundException {
        clientHandler = new HashMap<>(); //创建集合

        while (true) {    //持续历遍选择器
            selector.select();  //查询出已就绪通道

            Set<SelectionKey> selectionKeys = selector.selectedKeys();  //获取选择键集合

            Iterator<SelectionKey> iterator = selectionKeys.iterator(); //通过迭代器历遍
            while (iterator.hasNext()) {  //判断是否还有选择键
                SelectionKey selectionKey = iterator.next();    //获取选择键

                if (selectionKey.isAcceptable()) {    //选择键是 接收 类型
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();    //得到接收用通道
                    SocketChannel clientChannel = serverSocketChannel.accept(); //获取接收到的客户端通道
                    if (clientChannel == null)   //如果接收了已存在的通道将会返回null
                        continue;
                    clientChannel.configureBlocking(false); //将通道设置为非阻塞
                    clientChannel.register(selector, SelectionKey.OP_READ); //以可读类型放入选择器中
                    clientHandler.put(clientChannel, new Handler(clientChannel));    //将客户端通道和对应事件处理存入集合中

                } else if (selectionKey.isReadable()) {    //选择键是 可读 类型

                    SocketChannel clientChannel = (SocketChannel) selectionKey.channel();    //获取客户端通道
                    Handler handler = clientHandler.get(clientChannel); //借由通道获取事件处理
                    handler.readChannel(selector); //处理读取操作

                }

            }

        }
    }
}

package server;

import function.ArrayListCommand;
import function.HashMapCommand;
import function.LinkedListCommand;
import function.OtherCommand;
import tool.TxtTool;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

/*
    处理连接后的事项
 */
public class Handler {
    private SocketChannel socketChannel;    //创建处理事件对应的通道
    private Charset charset;    //预备编码对象

    public Handler(SocketChannel socketChannel){
        this.socketChannel = socketChannel; //连接通道
        charset = Charset.forName("UTF-8"); //获得“UTF-8”编码对象
    }

    public void  readChannel(Selector selector) throws IOException, ClassNotFoundException {   //处理读取操作
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);  //使用buffer对象读或写数据至通道
        CharsetDecoder charsetDecoder = charset.newDecoder();   //获得解码器
        String message = "";    //创建空字符串接收内容

        try {
            if ((socketChannel.read(byteBuffer)) > 0) {    //读取通道内容至byteBuffer
                byteBuffer.flip();  //将buffer转换为读模式
                message += charset.decode(byteBuffer);  //将读到内容解码
                byteBuffer.clear(); //清空缓冲区以便再次写入
            }

        if("".equals(message)){ //未接收到消息
            return; //退出事件处理
        }

        Date date = new Date(); //获取时间
        String log = date + " 127.0.0.1>> " + message + '\n'; //整合客户端发送内容
        System.out.print(log);    //打印客户端发送时间以及内容
        TxtTool.eventLog(log);    //存储日志文件
        TxtTool.binaryLog(log);   //存储二进制日志文件


        if("exit".equals(message)){ //接收到退出信息
            socketChannel.close();  //关闭线程
            HashMapCommand.save(); //保存到map
            return; //退出事件处理
        }

        messageHandler(message);    //对接收消息进行处理
        socketChannel.register(selector, SelectionKey.OP_READ);  //将通道重新以可读类型放入选择器中

        } catch (IOException e) {
            Date date = new Date(); //获取时间
            System.out.println(date + " 客户端强制退出");
            HashMapCommand.save();  //保存map
            socketChannel.close();  //关闭连接
        }
    }

    public void messageHandler(String message) throws IOException, ClassNotFoundException {  //消息处理
        String[] command = message.split(" ");  //将接收到的指令按“ ”分开
        int length = command.length;    //获取指令长度

        switch (length){    //通过长度判断是哪一类指令
            case 1:
                if("help".equals(command[0])){          //HELP指令
                    sendMessage(OtherCommand.help());
                }else if("ping".equals(command[0])){    //PING指令
                    sendMessage(OtherCommand.ping());
                }else {
                    sendMessage("无效指令\n");
                }
                break;
            case 2:
                if("help".equals(command[0])){          //HELP [COMMANDNAME]指令
                    sendMessage(OtherCommand.help(command[1]));
                }else if("get".equals(command[0])) {    //GET [KEY]指令
                    sendMessage(HashMapCommand.get(command[1]));
                }else if("del".equals(command[0])) {    //DEL [KEY]指令
                    sendMessage(HashMapCommand.del(command[1]));
                }else if("len".equals(command[0])) {    //LEN [KEY]指令
                    sendMessage(LinkedListCommand.len(command[1]));
                }else if("lpop".equals(command[0])) {   //LPOP [KEY]指令
                    sendMessage(LinkedListCommand.lpop(command[1]));
                }else if("rpop".equals(command[0])) {   //RPOP [KEY]指令
                    sendMessage(LinkedListCommand.rpop(command[1]));
                }else if("ldel".equals(command[0])) {   //LDEL [KEY]指令
                    sendMessage(LinkedListCommand.ldel(command[1]));
                }else if("hdel".equals(command[0])) {   //HDEL [KEY]指令
                    sendMessage(ArrayListCommand.hdel(command[1]));
                }else {
                    sendMessage("无效指令\n");
                }
                break;
            case 3:
                if("set".equals(command[0])) {          //SET [KEY] [VALUE]指令
                    sendMessage(HashMapCommand.set(command[1], command[2]));
                }else if("lpush".equals(command[0])){   //LPUSH [KEY] [VALUE]指令
                    sendMessage(LinkedListCommand.lpush(command[1], command[2]));
                }else if("rpush".equals(command[0])) {  //RPUSH [KEY] [VALUE]指令
                    sendMessage(LinkedListCommand.rpush(command[1], command[2]));
                }else if("hget".equals(command[0])) {   //HGET [KEY] [FIELD]指令
                    sendMessage(ArrayListCommand.hget(command[1], command[2]));
                }else if("hdel".equals(command[0])) {   //HDEL [KEY] [FIELD]指令
                    sendMessage(ArrayListCommand.hdel(command[1], command[2]));
                }else{
                    sendMessage("无效指令\n");
                }
                break;
            case 4:
                if("range".equals(command[0])){         //RANGE [KEY] [START] [END]指令
                    sendMessage(LinkedListCommand.range(command[1], command[2], command[3]));
                }else if("hset".equals(command[0])){    //HSET [KEY] [FIELD] [VALUE]指令
                    sendMessage(ArrayListCommand.hset(command[1], command[2], command[3]));
                }else {
                    sendMessage("无效指令\n");
                }
                break;
            default:
                sendMessage("无效指令\n");
        }

    }

    public void sendMessage(String message) throws IOException {    //用于发送信息的方法
            socketChannel.write(charset.encode(message));   //将信息编码后发送
    }

}

package server;

import function.OtherCommand;
import tool.LogTool;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.util.Scanner;

public class ServerThread extends Thread {
    private ServerSocketChannel serverSocketChannel;

    public ServerThread(ServerSocketChannel serverSocketChannel){
        this.serverSocketChannel =  serverSocketChannel;
    }

    @Override
    public void run() {
        while (true) {
            String message = "";    //创建空字符串接收输入
            Scanner scanner = new Scanner(System.in);   //获取标准输入对象
            message = scanner.nextLine();    //输入内容，以输入换行结束
            if ("exit".equals(message)) {  //如果写入exit
                try {
                    serverSocketChannel.close();    //关闭服务端通道
                    OtherCommand.save();    //退出前保存
                    LogTool.infoOut("服务端接收通道关闭");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0); //正常退出
            }
        }
    }
}

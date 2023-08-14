package server;

import client.ClientMain;
import tool.PropertyTool;

import java.io.IOException;
import java.util.Properties;

/*
    服务端运行类
 */
public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PropertyTool propertyTool = new PropertyTool(); //创建工具类对象以获取地址和端口号
        new Reactor(propertyTool.getHostname() ,propertyTool.getPort());  //获取地址和端口号开启服务端
    }
}

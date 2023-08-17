package client;

import tool.PropertyTool;

import java.io.IOException;

/*
    模拟用户A
 */
public class AClient {
    public static void main(String[] args) throws IOException {
        PropertyTool propertyTool = new PropertyTool(); //创建工具类对象以获取地址和端口号
        new ClientMain(propertyTool.getHostname() ,propertyTool.getPort());
    }
}

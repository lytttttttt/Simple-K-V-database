package server;

import client.ClientMain;

import java.io.IOException;

/*
    服务端运行类
 */
public class ServerMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new Reactor(9999);
    }
}

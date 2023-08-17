package tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
    提取配置文件内容的类
 */
public class PropertyTool {

    public int getPort() throws IOException {   //获取端口号的方法
        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\files\\colloate.properties")); //获取文件读取对象
        String line = "";   //使用空字符串获取读到的内容
        int port = 0;   //获取端口号以返回
        while ((line = br.readLine()) != null) { //循环读取
            String[] split = line.split("=");   //以“=”分开key和value
            if("port".equals(split[0])) //获取port的value
                port = Integer.parseInt(split[1]);  //将value 转为整形
        }
        br.close(); //关闭读取
        return port;
    }

    public String getHostname() throws IOException {   //获取地址的方法
        BufferedReader br = new BufferedReader(new FileReader("src\\main\\java\\files\\colloate.properties")); //获取文件读取对象
        String line = "";   //使用空字符串获取读到的内容
        String hostname = "";   //获取地址以返回
        while ((line = br.readLine()) != null) { //循环读取
            String[] split = line.split("=");   //以“=”分开key和value
            if("hostname".equals(split[0])) //找到hostname
                hostname = split[1];  //获取地址
        }
        br.close(); //关闭读取
        return hostname;
    }
}

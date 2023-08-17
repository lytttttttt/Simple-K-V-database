package function;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/*
    使用HashMap的指令类
 */
public class HashMapCommand {
    private static HashMap<String, String> hashMap; //创建集合
    private static FileOutputStream fileOutputStream;  //创建写入对象用于 properties
    private static BufferedReader bufferedReader;   //创建读取对象用于 properties
    private static Properties properties;   //创建Properties 对象进行操作

    static {    //使用静态代码块提前加载集合
        hashMap = new HashMap<>();  //加载HashMap
        try {
            open(); //加载hashMap
        } catch (IOException e) {
            e.printStackTrace();    //未找到文件异常
        }
    }

    public static void open() throws IOException {   //预加载
        bufferedReader = new BufferedReader(new FileReader("src\\main\\java\\files\\hashmap.properties")); //获取文件读取对象
        properties = new Properties();  //创建Properties 对象

        String line = "";   //使用空字符串获取读到的内容
        String value = "";  //用于放回内容
        while ((line = bufferedReader.readLine()) != null) { //循环读取
            String[] split = line.split("=");   //以“=”分开key和value
            if(split.length == 2){  //如果获取到的内容是key=value
                hashMap.put(split[0], split[1]);    //放入hashmap中
            }
        }
        bufferedReader.close();

    }

    public static String set(String key, String value) throws IOException {    //存储key - value型数据
        hashMap.put(key, value);    //将key 和value放入HashMap
        return "1\n";
    }

    public static String get(String key) throws IOException { //获取key 对应的value
        if(hashMap.get(key) != null)    //如果key 存在
            return hashMap.get(key) + "\n";    //获取value
        else
            return "null\n";        //key不存在
    }

    public static String del(String key){    //删除key 对应的value
        hashMap.remove(key); //删除key 以及对应value
        return "1\n";
    }

    public static void save() throws IOException { //客户端退出时保存map
        fileOutputStream = new FileOutputStream("src\\main\\java\\files\\hashmap.properties"); //借助FileOutputStream 写入内容

        Set<String> sets = hashMap.keySet();    //获取key 集合
        for(String set : sets){ //历遍key
            properties.setProperty(set, hashMap.get(set)); //将key 和value 保存到Properties 对象中
        }
        properties.store(fileOutputStream, null);   //存储到文件中，注释为日期
        properties.clear(); //清空properties

        open(); //重新加载hashMap
    }

}


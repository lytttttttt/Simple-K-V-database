package tool;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/*
    二进制保存数据相关的类
 */
public class BinaryTool {
    private static ArrayList<String> list; //用于指向key 对应的ArrayList
    private static File timeFile = new File(".\\files\\keytime.dat");   //创建key以及过期时间文件对象
    private static File mapFile = new File(".\\files\\keymap.dat");     //创建key以及ArrayList文件对象

    static {
        if(!timeFile.exists()) {    //如果存放key时间的文件不存在
            try {
                timeFile.createNewFile();   //新建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!mapFile.exists()){      //如果存放keyMap的文件不存在
            try {
                mapFile.createNewFile();    //新建文件
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static HashMap<String, String> getTime() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(timeFile);   //得到字节输入对象
        HashMap<String, String> hashMap = new HashMap<>();  //创建集合，存放数据后返回
        String str = "";    //空字符接收内容
        int read = 0;   //读取文件内容
        while((read = fileInputStream.read()) != -1){   //读取文件直至退出
            str += (char)read;  //拼接读取内容
            if((char)read == '\n'){ //根据换行符区分key
                String[] split = str.split("=");    //根据 = 分开key 和value
                hashMap.put(split[0], split[1]);    //放入key 和value 到集合
                str = "";   //置空字符串重新接收key 和value
            }
        }
        return hashMap;   //返回map
    }

    public static HashMap<String, ArrayList<String>> getMap() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(mapFile); //得到字节输入对象
        HashMap<String, ArrayList<String>> arrayList = new HashMap<>(); //创建集合，存放数据后返回
        String str = "";    //空字符接收内容
        int read = 0;   //读取文件内容
        while((read = fileInputStream.read()) != -1){   //读取文件直至退出
            str += (char)read;  //拼接读取内容
            if((char)read == '\n'){ //根据换行符区分key
                list = new ArrayList<>();   //创建ArrayList 和key 对应，接收value
                String[] split = str.split(" ");//根据空格分开key和list中的value
                arrayList.put(split[0], list);  //放入key 和集合到map中
                for (int i = 1; i < split.length - 1; i++) { //历遍value
                    list.add(split[i]); //放入value
                }
                str = "";   //置空以便读取下一个key 和value
            }
        }
        return arrayList;   //返回map
    }

    public static void setTime(HashMap<String, String> hashMap) throws IOException {    //存储key 以及过期时间
        FileOutputStream fileOutputStream = new FileOutputStream(timeFile); //得到字节输出对象

        Set<String> keys = hashMap.keySet();  //得到key集合
        for(String key: keys){  //历遍key
            fileOutputStream.write(key.getBytes()); //写入key
            fileOutputStream.write("=".getBytes()); //根据 = 分割key 和value
            fileOutputStream.write(hashMap.get(key).getBytes());    //写入key 对应的value
            fileOutputStream.write(" \n".getBytes());    //换行输入下一个key
        }

    }

    public static void setMap(HashMap<String, ArrayList<String>> arrayList) throws IOException {    //存取key 以及ArrayList
        FileOutputStream fileOutputStream = new FileOutputStream(mapFile);  //得到字节输出对象

        Set<String> keys = arrayList.keySet();  //得到key集合
        for(String key: keys){  //历遍key
            list = arrayList.get(key);  //获取key对应的ArrayList
            fileOutputStream.write(key.getBytes()); //写入key
            for (int i = 0; i < list.size(); i++) { //历遍list的值
                fileOutputStream.write((" " + list.get(i)).getBytes()); //以空格分割value
            }
            fileOutputStream.write(" \n".getBytes());    //换行输入下一个key
        }
    }
}
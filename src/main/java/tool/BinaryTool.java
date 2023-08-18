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
    private static File timeFile = new File("src\\main\\java\\files\\keytime.dat");
    private static File mapFile = new File("src\\main\\java\\files\\keymap.dat");

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
        FileInputStream fileInputStream = new FileInputStream(timeFile);
        HashMap<String, String> hashMap = new HashMap<>();  //创建集合,记录时间
        String str = "";
        int read = 0;
        while((read = fileInputStream.read()) != -1){
            str += (char)read;
            if((char)read == '\n'){
                String[] split = str.split("=");
                hashMap.put(split[0], split[1]);
                str = "";
            }
        }
        return hashMap;
    }

    public static HashMap<String, ArrayList<String>> getMap() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(mapFile);
        HashMap<String, ArrayList<String>> arrayList = new HashMap<>(); //创建集合,存放数据
        String str = "";
        int read = 0;
        while((read = fileInputStream.read()) != -1){
            str += (char)read;
            if((char)read == '\n'){
                list = new ArrayList<>();
                String[] split = str.split(" ");
                arrayList.put(split[0], list);
                for (int i = 1; i < list.size(); i++) {
                    list.add(split[i]);
                }
                str = "";
            }
        }
        return arrayList;
    }

    public static void setTime(HashMap<String, String> hashMap) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(timeFile);

        Set<String> keys = hashMap.keySet();
        for(String key: keys){
            fileOutputStream.write(key.getBytes());
            fileOutputStream.write("=".getBytes());
            fileOutputStream.write(hashMap.get(key).getBytes());
            fileOutputStream.write("\n".getBytes());
        }

    }

    public static void setMap(HashMap<String, ArrayList<String>> arrayList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(mapFile);

        Set<String> keys = arrayList.keySet();
        for(String key: keys){
            list = arrayList.get(key);
            fileOutputStream.write(key.getBytes());
            for (int i = 0; i < list.size(); i++) {
                fileOutputStream.write((" " + list.get(i)).getBytes());
            }
            fileOutputStream.write("\n".getBytes());
        }
    }
}
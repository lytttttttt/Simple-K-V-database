package function;

import java.util.ArrayList;
import java.util.HashMap;
/*
    使用ArrayList的指令类
 */
public class ArrayListCommand {
    private static HashMap<String, ArrayList<HashMap<String, String>>> arrayList; //创建集合
    private static ArrayList<HashMap<String, String>> list; //用于指向key 对应的ArrayList
    private static HashMap<String, String> map;     //用于指向ArrayList 的map

    static {    //使用静态代码块提前加载集合
        arrayList = new HashMap<>();  //加载arrayList
    }

    public static String hset(String key, String field, String value){    //存储key 中的field 为value
        if(arrayList.get(key) == null){ //判断存放hashMap 中key 对应ArrayList 是否为空
            map = new HashMap<>();      //创建将放于ArrayList 中的map
            map.put(field, value);      //存储field 和value 到map中
            list = new ArrayList<>();   //创建ArrayList
            list.add(map);              //将map放入ArrayList
            arrayList.put(key, list);   //将key 和ArrayList 放入hashMap
        }else {
            list = arrayList.get(key);  //获取hashMap 中key 对应ArrayList
            map = list.get(0);          //获取ArrayList 中的map
            map.put(field, value);      //将field 和value 放入map 中
        }
        return "1\n";   //存储成功
    }

    public static String hget(String key, String field){ //获取key 中的field 的value
        String value = "无效指令\n";    //创建用于返回的字符串
        if(arrayList.get(key) == null){ //hashMap 中key 对应ArrayList 为空
            return value;               //返回“无效指令”
        }else {
            list = arrayList.get(key);  //获取hashMap 中key 对应ArrayList
            map = list.get(0);          //获取ArrayList 中的map
            value = map.get(field);     //获取map 中field 对应的value
        }
        return value + "\n";            //返回value
    }

    public static String hdel(String key, String field){    //删除key 中的field 的value
        if(arrayList.get(key) == null){ //hashMap 中key 对应ArrayList 为空
            return "0\n";               //删除失败
        }else {
            list = arrayList.get(key);  //获取hashMap 中key 对应ArrayList
            map = list.get(0);          //获取ArrayList 中的map
            map.remove(field);          //移除map 中field 对应的value
        }
        return "1\n";   //删除成功
    }

    public static String hdel(String key){    //删除key 的所有数据
        if(arrayList.get(key) == null){ //hashMap 中key 对应ArrayList 为空
            return "0\n";               //删除失败
        }else {
            arrayList.remove(key);      //移除hashMap 中key 对应ArrayList
        }
        return "1\n";                   //删除成功
    }
}

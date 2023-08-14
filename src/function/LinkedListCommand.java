package function;

import java.util.HashMap;
import java.util.LinkedList;

/*
    使用LinkedList的指令类
 */
public class LinkedListCommand {
    private static HashMap<String, LinkedList<String>> linkedList; //创建集合
    private static LinkedList<String> lists; //用于指向key对应的LinkedList

    static {    //使用静态代码块提前加载集合
        linkedList = new HashMap<>();  //加载HashMap
    }

    public static String lpush(String key, String value){    //可直接放一个数据在左端
        if(linkedList.get(key) == null){    //如果key 对应的LinkedList 为空
            lists = new LinkedList<>();     //新建一个LinkedList
            linkedList.put(key, lists);     //将key 和LinkedList 放入map
            lists.addFirst(value);          //添加LinkedList 的value 到首端
        }else {
            linkedList.get(key).addFirst(value);//LinkedList 不为空，直接添加value 到首端
        }
        return 1 + "\n";    //添加成功
    }

    public static String rpush(String key, String value){    //可直接放一个数据在右端
        if(linkedList.get(key) == null){    //如果key 对应的LinkedList 为空
            lists = new LinkedList<>();     //新建一个LinkedList
            linkedList.put(key, lists);     //将key 和LinkedList 放入map
            lists.addLast(value);           //添加LinkedList 的value 到末端
        }else {
            linkedList.get(key).addLast(value);//LinkedList 不为空，直接添加value 到末端
        }
        return 1 + "\n";    //添加成功
    }

    public static String range(String key, String start, String end){ //从左到右开始，将key 对应 start - end 位置的数据全部返回
        String message = "";    //用于整合返回数据的空字符串
        if(linkedList.get(key) == null) //如果key 对应的LinkedList 为空
            return "无效指令\n";          //指令无效

        lists = linkedList.get(key);    //获取key 对应的LinkedList
        if(lists.size() == 0)           //如果key 对应的LinkedList 没有数据
            return "无效指令\n";          //指令无效

        int start_i = 0;    //接收传入的起点坐标
        int end_i = 0;      //接收传入的终点坐标
        try {
            start_i = Integer.parseInt(start);  //将传入坐标转换为整形
            end_i = Integer.parseInt(end);      //将传入坐标转换为整形
        } catch (NumberFormatException e) {
            return "无效指令\n";    //传入的坐标无效
        }

        if(lists.size() - 1 < end_i || start_i < 0 || end_i < start_i)
            //传入的终点坐标大于集合大小，起点坐标小于0，终点坐标小于起点坐标，均无效
            return "无效指令\n";

        for(int i = end_i; i >= start_i; i--)   //历遍查找的集合范围
            message += lists.get(i) + " ";      //将查找的内容添加到message中
        return message + "\n";                  //返回查找到的内容
    }

    public static String len(String key){    //获取 key 存储数据的个数
        if(linkedList.get(key) == null)      //如果key 对应的LinkedList 为空
            return "无效指令\n";               //获取失败

        lists = linkedList.get(key);         //获取key 对应的LinkedList
        return lists.size() + "\n";          //返回LinkedList存取数据的个数
    }

    public static String lpop(String key){    //获取key最左端的数据并删除
        if(linkedList.get(key) == null)       //如果key 对应的LinkedList 为空
            return "无效指令\n";                //删除失败

        lists = linkedList.get(key);          //获取key 对应的LinkedList
        if(lists.size() == 0)                 //LinkedList存取数据的个数为0
            return "无效指令\n";                //删除失败

        return lists.removeFirst();           //返回key最左端的数据并删除
    }

    public static String rpop(String key){    //获取key最右端的数据并删除
        if(linkedList.get(key) == null)       //如果key 对应的LinkedList 为空
            return "无效指令\n";                //删除失败

        lists = linkedList.get(key);          //获取key 对应的LinkedList
        if(lists.size() == 0)                 //LinkedList存取数据的个数为0
            return "无效指令\n";                //删除失败

        return lists.removeLast();            //返回key最右端的数据并删除
    }

    public static String ldel(String key){    //删除key 所有的数据
        linkedList.remove(key);               //删除key 以及对应LinkedList
        return 1 + "\n";                      //删除成功
    }

}

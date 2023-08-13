package function;

/*
    使用LinkedList的指令类
 */
public class LinkedListCommand {

    public static int lpush(String key, String value){    //可直接放一个数据在左端
        return 1;
    }

    public static int rpush(String key, String value){    //可直接放一个数据在右端
        return 1;
    }

    public static String range(String key, String start, String end){ //从左到右开始，将key 对应 start - end 位置的数据全部返回
        return "";
    }

    public static int len(String key){    //获取 key 存储数据的个数
        return 1;
    }

    public static int lpop(String key){    //获取key最左端的数据并删除
        return 1;
    }

    public static int rpop(String key){    //获取key最右端的数据并删除
        return 1;
    }

    public static int ldel(String key){    //删除key 所有的数据
        return 1;
    }

}

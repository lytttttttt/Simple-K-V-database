package function;

/*
    使用ArrayList的指令类
 */
public class ArrayListCommand {

    public static int hset(String key, String field, String value){    //存储key 中的field 为value
        return 1;
    }

    public static String hget(String key, String field){ //获取key 中的field 的value
        return "";
    }

    public static int hdel(String key, String field){    //删除key 中的field 的value
        return 1;
    }

    public static int hdel(String key){    //删除key 的所有数据
        return 1;
    }
}

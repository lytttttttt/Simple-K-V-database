package function;

import tool.BinaryTool;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class KeyCommand {
    private static HashMap<String, String> hashMap; //创建集合,记录时间
    private static HashMap<String, ArrayList<String>> arrayList; //创建集合,存放数据
    private static ArrayList<String> list; //用于指向key 对应的ArrayList

    static {    //使用静态代码块提前加载集合
        try {
            hashMap = BinaryTool.getTime();
            arrayList = BinaryTool.getMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String expire(String key,String delay) throws ParseException {   //设置key 过期时间
        if(arrayList.get(key) == null || hashMap.get(key) != null){
        return "0\n";   //key不存在,设置失败或者已设置过期时间
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设定输出格式
        if(hashMap.get(key) != null && new Date().compareTo(dateFormat.parse(hashMap.get(key))) > 0){   //比较当前时间和key时间
            hashMap.remove(key);
            arrayList.remove(key);  //移除两个map的key
            return "0\n";   //key已经过期
        }

        int second = Integer.parseInt(delay);  //将时长转为整形，用于传入或计算
        new Timer().schedule(new TimerTask() {  //创建Timer对象，借助schedule完成定时任务
            @Override
            public void run() {     //重写TimerTask的run方法
                hashMap.remove(key);
                arrayList.remove(key);  //移除两个map的key
            }
        }, second * 1000); //传入移除时间(*1000作为秒数)

        Calendar calendar = Calendar.getInstance(); //获取calendar对象以对日期进行计算
        calendar.add(Calendar.SECOND, second);  //传入要增加的秒数
        Date endDate = calendar.getTime();      //得到过期时间
        hashMap.put(key, dateFormat.format(endDate));   //将过期时间转为字符串放入map

        return "1\n";   //设置成功
    }

    public static String ddl(String key) throws ParseException {   //获取key 过期时间
        if(arrayList.get(key) == null || hashMap.get(key) == null){
            return "null\n";   //key不存在
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //设定输出格式
        if(hashMap.get(key) != null && new Date().compareTo(dateFormat.parse(hashMap.get(key))) > 0){   //比较当前时间和key时间
            hashMap.remove(key);
            arrayList.remove(key);  //移除两个map的key
            return "0\n";   //key已经过期
        }

        String end = hashMap.get(key); //获取过期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.now();          //获取当前时间
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);    //将字符串转换为LocalDateTime对象用于计算
        Duration duration = Duration.between(startTime, endTime);   //计算时间差
        long seconds = duration.getSeconds();   //获得相差的秒数

        return seconds + "\n";     //返回距离过期时间
    }

    public static String sadd(String key, String... members){   //向key 加入成员
        if(arrayList.get(key) == null){ //判断key 对应的集合是否已有
            list = new ArrayList<>();   //没有集合，新建
            arrayList.put(key, list);   //放入key 和对应arrayList
        }else {
            list = arrayList.get(key);  //已有集合，通过key 获取
        }

        for (String member : members)   //历遍传入的成员
            list.add(member);           //添加成员
        return "1\n";   //添加成功
    }

    public static String smembers(String key){  //查看key 的全部元素
        if(arrayList.get(key) == null){ //判断key 对应的集合是否已有
            return "null\n";    //集合不存在
        }else {
            list = arrayList.get(key);  //已有集合，通过key 获取
        }

        String message = "";    //用于整合返回数据

        Iterator<String> iterator = list.iterator();    //获取迭代器
        while(iterator.hasNext()){  //通过迭代器遍历集合
            String value = iterator.next(); //获取成员
            message += value + " "; //整合数据
        }

        return message + "\n";  //返回数据
    }

    public static String sismember(String key, String member){  //判断member 是否在key 中
        if(arrayList.get(key) == null){ //判断key 对应的集合是否已有
            return "null\n";    //集合不存在
        }else {
            list = arrayList.get(key);  //已有集合，通过key 获取
        }

        Iterator<String> iterator = list.iterator();    //获取迭代器
        while(iterator.hasNext()){  //通过迭代器遍历集合
            String value = iterator.next(); //获取成员
            if(value.equals(member))    //判断集合成员是否与传入元素相同
                return "1\n";   //元素存在
        }

        return "0\n";   //元素不存在
    }

    public static String srem(String key, String member){   //删除key 中的成员
       Boolean flag = false;

        if(arrayList.get(key) == null){ //判断key 对应的集合是否已有
            return "null\n";    //集合不存在
        }else {
            list = arrayList.get(key);  //已有集合，通过key 获取
        }

        Iterator<String> iterator = list.iterator();    //获取迭代器
        while(iterator.hasNext()){  //通过迭代器遍历集合
            String value = iterator.next(); //获取成员
            if(value.equals(member)){   //判断集合成员是否与传入元素相同
                iterator.remove();      //删除元素
                flag = true;            //删除成功
            }
        }

        if(flag)
            return "1\n";   //元素存在，删除成功
        else
            return "0\n";   //元素不存在，删除失败
    }

    public static void save() throws IOException {
        BinaryTool.setTime(hashMap);
        BinaryTool.setMap(arrayList);
    }
}

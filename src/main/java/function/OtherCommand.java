package function;


//import org.junit.Test;
import server.Handler;
import server.Reactor;
import tool.ClassTool;

import java.io.IOException;
import java.nio.channels.Selector;
import java.util.Timer;
import java.util.TimerTask;

/*
    其他指令
 */
public class OtherCommand {
    public static String ping(){    //心跳指令
        return "PONG\n";
    }

    //@Test   //测试用
    public static String help() throws ClassNotFoundException {    //帮助指令
        return ClassTool.getMethodsName(); //返回所有指令
    }

    public static String help(String commandName) throws ClassNotFoundException {    //可以输入查找对应字符串的帮助指令
        return ClassTool.getMethodsName(commandName);   //返回对应指令
    }

    public static String save() throws IOException {  //手动保存
        HashMapCommand.save();  //保存map
        KeyCommand.save();  //保存map
        return "1\n";
    }

    public static void bgsave(){    //后台保存
        new Timer().schedule(new TimerTask() {  //创建Timer对象，借助schedule完成定时任务
            @Override
            public void run() {     //重写TimerTask的run方法
                try {
                    save();         //执行保存任务
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 10000);   //方法被调用时执行一次保存，之后每隔10000毫秒执行一次
    }

    public static String flushdb(){   //清空缓冲区
        Handler.getByteBuffer().clear();
        return "1\n";
    }
}

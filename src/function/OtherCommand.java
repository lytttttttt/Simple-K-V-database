package function;


//import org.junit.Test;
import tool.ClassTool;

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
}

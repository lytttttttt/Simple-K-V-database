package tool;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/*
    使用反射的类
 */
public class ClassTool {
    private static String[] classNames; //创建字符串数组存储类名
    private static String message = "";    //空字符串用于整合返回数据

    static {    //静态代码块加载内容
        classNames = new String[5];
        classNames[0] = "function.OtherCommand";    //存放类名
        classNames[1] = "function.HashMapCommand";
        classNames[2] = "function.LinkedListCommand";
        classNames[3] = "function.ArrayListCommand";
        classNames[4] = "function.KeyCommand";
    }

    public static String getMethodsName() throws ClassNotFoundException {  //获取方法名和形参名
        message = "";//清空字符串

        for(String className : classNames){
            Class<?> command = Class.forName(className); //获取className类的Class对象
            Method[] methods = command.getDeclaredMethods();    //获取className类中的方法
            for(Method method : methods){   //历遍方法数组
                if(!Modifier.isPublic(method.getModifiers()))
                    continue;   //不返回非public方法
                if(("save".equals(method.getName()) || "open".equals(method.getName())) && "function.HashMapCommand".equals(className))
                    continue;   //不返回hashMap指令的open,save,getHashMap方法
                if("save".equals(method.getName()) && "function.KeyCommand".equals(className))
                    continue;   //不返回KeyCommand指令的save方法
                if("bgsave".equals(method.getName()))
                    continue;   //不返回后台保存方法

                message += (method.getName()).toUpperCase(); //大写的方法名

                Parameter[] parameters = method.getParameters();    //获取方法形参
                for(Parameter parameter : parameters){  //历遍形参
                    message += " [" + (parameter.getName()).toUpperCase() + ']'; //大写形参名
                }
                message += '\n';   //换行
            }
        }

        return message; //返回存有各方法的字符串
    }

    public static String getMethodsName(String commandName) throws ClassNotFoundException {  //根据方法名获取形参名
        message = "";//清空字符串

        for(String className : classNames){
            Class<?> command = Class.forName(className); //获取className类的Class对象
            Method[] methods = command.getDeclaredMethods();    //获取className类中的方法
                for(Method method : methods){   //历遍方法数组
                    if(method.getName().equals(commandName)){
                        if(!Modifier.isPublic(method.getModifiers()))
                            continue;   //不返回非public方法
                        if(("save".equals(method.getName()) || "open".equals(method.getName())) && "function.HashMapCommand".equals(className))
                            continue;   //不返回hashMap指令的open,save,getHashMap方法
                        if("save".equals(method.getName()) && "function.KeyCommand".equals(className))
                            continue;   //不返回KeyCommand指令的save方法
                        if("bgsave".equals(method.getName()))
                            continue;   //不返回后台保存方法

                        message += (method.getName()).toUpperCase(); //大写的方法名

                        Parameter[] parameters = method.getParameters();    //获取方法形参
                        for(Parameter parameter : parameters){  //历遍形参
                            message += " [" + (parameter.getName()).toUpperCase() + ']'; //大写形参名
                        }
                        message += '\n';   //换行
                        return message;
                    }else {
                        continue;
                    }
                }
        }

        return "无效指令\n";  //如果输入方法不存在，返回“无效指令”
    }
}

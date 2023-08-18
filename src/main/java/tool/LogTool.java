package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    日志输出相关的类
 */
public class LogTool {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogTool.class); //获得日志记录器

    public static void infoOut(String message){
        LOGGER.info(message);
    }   //普通信息

    public static void errorOut(String message){
        LOGGER.error(message);
    }   //警告信息
}

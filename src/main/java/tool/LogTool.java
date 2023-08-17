package tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
    日志输出相关的类
 */
public class LogTool {
    public static final Logger LOGGER = LoggerFactory.getLogger(LogTool.class);

    public static void infoOut(String message){
        LOGGER.info(message);
    }

    public static void errorOut(String message){
        LOGGER.error(message);
    }
}

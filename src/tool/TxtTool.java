package tool;

import java.io.*;

/*
    操作日志文件的类
 */
public class TxtTool {
    public static void eventLog(String log) throws IOException {    //存储日志文件
        FileOutputStream fileOutputStream = new FileOutputStream("src\\eventlog.txt", true);    //追加写入
        fileOutputStream.write(log.getBytes()); //将字符串转为字符数组写入
    }

    public static void readTxt() {  //读取日志文件
        //字节数组
        byte[] buf = new byte[8]; //一次读取 8 个字节.
        int readLen = 0;
        FileInputStream fileInputStream = null;
        try {
            //创建 FileInputStream 对象，用于读取 文件
            fileInputStream = new FileInputStream("src\\eventlog.txt");
            //从该输入流读取最多 b.length 字节的数据到字节数组。 此方法将阻塞，直到某些输入可用。
            //如果返回-1 , 表示读取完毕
            //如果读取正常, 返回实际读取的字节数
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));//显示
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭文件流，释放资源.
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void binaryLog(String log){   //以二进制形式存储日志文件
        try{
            File file = new File("src\\eventlog2.txt"); //获取文件对象
            if(!file.exists())  //判断文件是否存在
                file.createNewFile();   //如果文件不存在，新建文件
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);  //追加写入
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);   //获得对象输出流
            objectOutputStream.write(log.getBytes());   //使用对象流写入
            objectOutputStream.close();     //关闭流
            fileOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

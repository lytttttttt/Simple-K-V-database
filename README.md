[TOC]

# 简单K-V数据库

## 一、项目简介

该项目简单实现了通过输入指定命令的方式，响应相关动作如：帮助、存储数据等。

内容的实现依靠HashMap、ArrayList等相关集合，同时还使用了反射、I/O流等内容。

​	

## 二、环境依赖

![External Libraries](https://github.com/lytttttttt/Simple-K-V-database/blob/master/images/External%20Libraries.png?raw=true)

项目所使用的jdk版本为:1.8
根据日志实现的需求，还需要导入slf4j和log4j相关库(如图)



## 三、目录说明

——.idea			 项目配置文件夹

——files		      日志输出，数据保存文件夹

——images		 实例图片文件夹

——src/main	  源代码	

——————java			代码文件

————————client		 客户端相关类

————————function	指令实现类

————————server		服务端相关类

————————tool			文件，反射相关类

——————resources		配置文件夹

——KV.iml		  项目标识文件

——pom.xml	 环境依赖



## 四、项目使用

项目内包含服务端代码和客户端代码，

需要先启动服务端主方法，

```java
public class ServerMain {...}
```

再启动客户端主方法 (AClient或BClient均可) 开启运行。

```java
public class AClient {...}
```

运行后，可以在客户端运行界面输入内容，

![Client](https://github.com/lytttttttt/Simple-K-V-database/blob/master/images/Client.png?raw=true)

服务端将会接收并显示内容，同时发送请求相应内容显示在客户端运行页面。

![Server](https://github.com/lytttttttt/Simple-K-V-database/blob/master/images/Server.png?raw=true)

如果您是第一次使用，可以通过输入help获取帮助指令，

输入后，将会显示可输入指令在客户端运行界面。

![help](https://github.com/lytttttttt/Simple-K-V-database/blob/master/images/help.png?raw=true)

随后，您便可以根据指令按照需要输入，

退出时，输入指令为exit



## 五、代码详细

- server包：启动ServerMain主方法后，由Reactor开启服务端接收通道，在Accept中通过Selector等待接收客户端通道，等待接收消息，接收后交由Handler处理读取消息。

- client包：AClient和BClient模拟具体不同客户，再通过ClientMain开启客户端通道，在其中循环不断输入，同时另外开启一个ClientThread接收服务端返回消息。

- function包：各个类分别通过HashMap，ArrayList，LinkedList集合完成指令实现，其他指令则根据更具体的要求直接实现。

- tool包：PorpertyTool类实现对端口号以及地址的读取，LogTool类完成日志文件的输出，BinaryTool类实现对数据的二进制保存，ClassTool类通过反射获取各个类的方法名以及参数。



## 六、版本更新

1. 客户端与服务端实现初步通讯
2. 实现初步响应客户端发送内容
3. 实现简单命令的完成
4. 追加命令的简单实现
5. 简单日志实现
6. 实现数据二进制保存
7. 文件相关完善及说明文档提交
8. 说明文件图片加载


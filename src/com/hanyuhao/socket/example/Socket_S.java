package com.hanyuhao.socket.example;

import com.hanyuhao.socket.Handler.EchoHandler;
import com.hanyuhao.socket.SocketServer;

import java.util.Scanner;

/**
 * @Classname Socket_S
 * @Description TODO
 * @Date 2020/11/9 11:30
 * @Created by HanYuHao
 */
public class Socket_S {
    private static Scanner inputScanner = new Scanner(System.in);
     public static void main(String[] args)
     {

         SocketServer socketServer = new SocketServer(8088,new EchoHandler());
         System.out.println("服务端创建成功");
         while (true){
             System.out.println("请输入发送的广播消息(输入0退出)：");
             String msg = inputScanner.next();
             if(msg.equals("0")){
                 socketServer.close();
                 break;
             }
             socketServer.sendBroadCast(msg);
         }
     }
}

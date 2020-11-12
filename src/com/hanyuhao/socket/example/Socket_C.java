package com.hanyuhao.socket.example;

import com.hanyuhao.socket.Handler.EchoHandler;
import com.hanyuhao.socket.MessageFlag;
import com.hanyuhao.socket.SocketClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Classname Socket_C
 * @Description TODO
 * @Date 2020/11/9 11:36
 * @Created by HanYuHao
 */
public class Socket_C {
    private static Scanner inputScanner = new Scanner(System.in);
     public static void main(String[] args) throws UnknownHostException {
         SocketClient socketClient = new SocketClient(InetAddress.getLocalHost(),8088,new EchoHandler());
         System.out.println("客户端创建成功");
         while(true){
             System.out.println("请输入发送给服务器的消息(输入0退出)：");
             String msg = inputScanner.next();
             if(msg.equals("0")){
                 socketClient.close();
                 break;
             }else{
                 socketClient.println(msg);
             }
         }
     }
}

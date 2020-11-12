package com.hanyuhao.socket.Handler;

import com.hanyuhao.socket.Connection;

import java.util.Date;

/**
 * 消息处理器
 * @author HYH
 */
public class EchoHandler implements MessageHandler {
    @Override
    public void onReceive(Connection connection, String message) {
        System.out.println("["+new Date().toString() + "]");
        System.out.println("收到如下消息：" + message);
    }
}
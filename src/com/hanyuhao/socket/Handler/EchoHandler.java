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
        System.out.println(String.format("收到[%s:%s]消息：%s", connection.socket.getLocalAddress(),connection.socket.getPort(),message));
    }
}
package com.hanyuhao.socket.Handler;

import com.hanyuhao.socket.Connection;

public interface MessageHandler {
    public void onReceive(Connection connection, String message);
}
package com.hanyuhao.socket;

import java.net.Socket;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 内部有Socket的连接类，可发送消息
 * @author HYH
 */
public class Connection {
    public Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }

    public void println(String message) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new OutputStreamWriter(
                                     socket.getOutputStream()), true);
            writer.println(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
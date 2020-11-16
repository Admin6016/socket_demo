package com.hanyuhao.socket.Thread;

import com.hanyuhao.socket.Connection;
import com.hanyuhao.socket.MessageFlag;
import com.hanyuhao.socket.SocketClient;
import com.hanyuhao.socket.SocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 连接线程类，内部有客户端Socket和服务端ServerSocket,负责服务端的每个客户的通信
 *
 * @author HYH
 */
public class ConnectionThread extends Thread {
    private Socket socket;
    private SocketServer socketServer;
    private Connection connection;
    private boolean isRunning;
    private boolean isServer;
    private SocketClient socketClient;

    public ConnectionThread(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        this.socketServer = socketServer;
        connection = new Connection(socket);
        isRunning = true;
        isServer = true;
    }

    public ConnectionThread(Socket socket, SocketClient socketClient){
        this.socket = socket;
        this.socketClient = socketClient;
        connection = new Connection(socket);
        isRunning = true;
        isServer = false;
    }

    @Override
    public void run() {
        while(isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
                isRunning = false;
                break;
            }
            
            BufferedReader reader;
            try {
                String rawMessage = "";
                if(!socket.isClosed()){
                    reader = new BufferedReader(new InputStreamReader(
                            socket.getInputStream()));
                    rawMessage = reader.readLine();
                }

                if(isServer){
                    String messageFlag = rawMessage.substring(0, 1);
                    String message = rawMessage.substring(1);
                    // Check the message flag.
                    switch (messageFlag) {
                        case MessageFlag.pureMessage:
                            // Handle the message.
                            socketServer.getMessageHandler().onReceive(connection, message);
                            break;
                        case MessageFlag.connectionClosed:
                            stopRunning(String.format("[%s:%s]下线了",socket.getLocalAddress(),socket.getPort()));
                            HostCounter.count--;
                            System.out.println(String.format("[统计]共有%d个客户端在线",HostCounter.count));
                            break;
                        default:
                            break;
                    }
                }else{
                    socketClient.getMessageHandler().onReceive(connection,rawMessage);
                }


            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public boolean sendMessage(String message){
        if(!isRunning){
            return false;
        }
        Connection connection = new Connection(socket);
        connection.println(message);
        return true;
    }

    public boolean isRunning() {
        return isRunning;
    }
    
    public void stopRunning(String msg) {
        isRunning = false;
        try {
            System.out.println(msg);
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
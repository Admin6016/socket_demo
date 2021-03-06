package com.hanyuhao.socket.Thread;

import com.hanyuhao.socket.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * 服务端的线程监听模块,整体管理所有客户端的接入和Socket保存
 * @author HYH
 */
public class ListeningThread extends Thread {
    private SocketServer socketServer;
    private ServerSocket serverSocket;
    private Vector<ConnectionThread> connectionThreads;
    private Vector<ConnectionThread> notRunningConnectionThreads;
    private boolean isRunning;

    public ListeningThread(SocketServer socketServer, ServerSocket serverSocket) {
        this.socketServer = socketServer;
        this.serverSocket = serverSocket;
        this.connectionThreads = new Vector<ConnectionThread>();
        this.notRunningConnectionThreads = new Vector<ConnectionThread>();
        isRunning = true;
    }

    @Override
    public void run() {
        while(isRunning) {
            if (serverSocket.isClosed()) {
                isRunning = false;
                break;
            }

            // Remove not running connection threads.
            for (ConnectionThread connectionThread : connectionThreads) {
                if (!connectionThread.isRunning()) {
                    notRunningConnectionThreads.addElement(connectionThread);
                }
            }
            for (ConnectionThread connectionThread : notRunningConnectionThreads) {
                connectionThreads.remove(connectionThread);
            }
            notRunningConnectionThreads.clear();
            
            try {
                Socket socket;
                socket = serverSocket.accept();

                System.out.println(String.format("客户端[%s:%s]上线了",socket.getLocalAddress(),socket.getPort()));
                HostCounter.count++;
                System.out.println(String.format("[统计]共有%d个客户端在线",HostCounter.count));
                ConnectionThread connectionThread = new ConnectionThread(socket, socketServer);
                connectionThreads.addElement(connectionThread);
                connectionThread.start();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void sendBroadCast(String message){
        for(ConnectionThread ct: connectionThreads){
            ct.sendMessage(message);
        }
    }

    public void stopRunning() {
        for (int i = 0; i < connectionThreads.size(); i++)
            connectionThreads.elementAt(i).stopRunning("- -");
        isRunning = false;
    }
} 
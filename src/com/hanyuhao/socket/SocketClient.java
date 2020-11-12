package com.hanyuhao.socket;

import com.hanyuhao.socket.Handler.MessageHandler;
import com.hanyuhao.socket.Thread.ConnectionThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @author HYH
 * 客户端类，全能
 */
public class SocketClient{
    private Socket socket;
    private MessageHandler messageHandler;
    private ConnectionThread connectionThread;

    public SocketClient(InetAddress ip, int port) {
        try {

            socket = new Socket(ip, port);
            connectionThread = new ConnectionThread(socket,this);
            connectionThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SocketClient(InetAddress ip, int port,MessageHandler messageHandler) {
        try {
            socket = new Socket(ip, port);
            setMessageHandler(messageHandler);
            connectionThread = new ConnectionThread(socket,this);
            connectionThread.start();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setMessageHandler(MessageHandler messageHandler){
        this.messageHandler = messageHandler;
    }

    public MessageHandler getMessageHandler(){
        return this.messageHandler;
    }

    public void println(String message) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new OutputStreamWriter(
                                     socket.getOutputStream()), true);
            writer.println(MessageFlag.pureMessage + message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
    This function blocks.
    */
    public String readLine() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                                        socket.getInputStream()));
            return reader.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
    
    /*
     * Ready for use.
     */
    public void close() {
        try {
            // Send a message to tell the server to close the connection.
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream()), true);
            writer.println(MessageFlag.connectionClosed);

            if (socket != null && !socket.isClosed())
                socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
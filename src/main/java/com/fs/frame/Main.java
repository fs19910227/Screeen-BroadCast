package com.fs.frame;

import com.fs.frame.server.TCPServer;
import com.fs.frame.client.TCPClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String type = args[0];
        String host;
        Integer port;
        switch (type) {
            case "client":
                host = String.valueOf(args[1]);
                port = Integer.valueOf(args[2]);
                int delay;
                delay=Integer.valueOf(args[3]);
                TCPClient tcpClient = new TCPClient(host, port,delay);
                tcpClient.start();
                break;
            case "server":
                port = Integer.valueOf(args[1]);
                TCPServer tcpServer = new TCPServer(port);
                if(args.length>2){
                    int width = Integer.valueOf(args[2]);
                    int height= Integer.valueOf(args[3]);
                    tcpServer.init(width,height);
                }else {
                    tcpServer.init();
                }
                tcpServer.start();
                break;
            default:
                System.out.println("err");
        }

    }
}

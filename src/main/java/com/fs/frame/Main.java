package com.fs.frame;

import com.fs.frame.client.TCPServer;
import com.fs.frame.server.TCPClient;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String type = args[0];
        String host;
        Integer port;
        switch (type) {
            case "client":
                host = String.valueOf(args[1]);
                port = Integer.valueOf(args[2]);
                int delay=900;
                if(args.length>=3){
                    delay=Integer.valueOf(args[3]);
                }
                TCPClient tcpClient = new TCPClient(host, port,delay);
                tcpClient.start();
                break;
            case "server":
                port = Integer.valueOf(args[1]);
                new TCPServer(port).run();
                break;
            default:
                System.out.println("err");
        }

    }
}

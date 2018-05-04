package com.fs.frame.common;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public abstract class CommonClient {
    private String host;
    private int port;
    private SocketChannel channel;
    private Selector selector;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public CommonClient() {
    }

    public CommonClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    protected <T extends CommonClient> T init() throws IOException {
        selector = Selector.open();
        channel = SocketChannel.open();
        System.out.printf("open connection to %s,port=%d\n", host, port);
        return (T) this;
    }

    public void start() throws IOException {
        while (!tryConnect()){

        }
        runServer();
    }

    private void runServer() {
        do {
            try {
                run();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        while (true);
    }


    private void run() throws IOException {
        selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey next = iterator.next();
            if (next.isConnectable()) {
                System.out.println("process connect event");
                connect();
            } else if (next.isWritable()) {
                System.out.println("process write event ...");
                write(channel);
            }
        }
    }

    protected void connect() throws IOException {
        System.out.println("connection interrupt,close channel ...");
        channel.close();
        System.exit(1);
    }

    private boolean tryConnect() throws IOException {
        if (!channel.isConnected()) {
            if (channel.isConnectionPending()) {
                channel.close();
                channel = SocketChannel.open();
            }
            this.channel.configureBlocking(false);
            this.channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_WRITE);
            this.channel.connect(new InetSocketAddress(host, port));
            try{
                channel.finishConnect();
            }catch (ConnectException e){
                System.out.println("try connect failed , seems thar server not open ...");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return false;
            }
        }
        return true;
    }

    protected abstract void write(SocketChannel channel) throws IOException;
}

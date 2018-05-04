package com.fs.frame.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public abstract class CommonServer {
    private int port;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public CommonServer() {
    }

    public CommonServer(int port) {
        this.port = port;
    }

    protected <T extends CommonServer> T init() throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(port));
        System.out.printf("bind port to %d\n", port);
        return (T) this;
    }

    public void start() throws IOException {
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        do {
            run();
        }
        while (true);
    }

    private void run() throws IOException {
        selector.select();
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey next = iterator.next();
            if (next.isAcceptable()) {
                accept();
            }
            if (next.isReadable()) {
                read((SocketChannel) next.channel());
            }
            iterator.remove();
        }
    }

    protected void accept() throws IOException {
        SocketChannel accept = serverSocketChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
        System.out.println("accept connection ");
    }

    protected abstract void read(SocketChannel channel) throws IOException;
}

package com.fs.frame.common;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Config me = new Config();

    public static Config instance() {
        return me;
    }

    private Config() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("./config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can not load config config.properties");
        }
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class Server {
        private int port;
        private int init_width;
        private int init_height;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public int getInit_width() {
            return init_width;
        }

        public void setInit_width(int init_width) {
            this.init_width = init_width;
        }

        public int getInit_height() {
            return init_height;
        }

        public void setInit_height(int init_height) {
            this.init_height = init_height;
        }
    }

    public static class Client {
        private String remote_host;
        private int port;

        public String getRemote_host() {
            return remote_host;
        }

        public void setRemote_host(String remote_host) {
            this.remote_host = remote_host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

}

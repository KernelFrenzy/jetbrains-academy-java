package com.xfrenzy47x.api;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class AppServer {
    HttpServer server;
    boolean serverRunning;
    String theCode;

    public AppServer(int port) {
        serverRunning = false;
        theCode = null;
        try {
            server = HttpServer.create();
            server.bind(new InetSocketAddress(port), 0);

            server.createContext("/", exchange -> {
                String query = exchange.getRequestURI().getQuery();
                String message = query != null && query.contains("code") ? "Got the code. Return back to your program." : "Authorization code not found. Try again.";
                exchange.sendResponseHeaders(200, message.length());
                exchange.getResponseBody().write(message.getBytes());
                exchange.getResponseBody().close();
                if (query != null && query.contains("code") && theCode == null) {
                    theCode = query;
                }
            });

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        startServer();
    }

    public boolean isServerRunning() {
        return this.serverRunning;
    }

    public void startServer() {
        if (!serverRunning) {
            serverRunning = true;
            server.start();
        }
    }

    public void stopServer() {
        if (serverRunning) {
            server.stop(0);
            serverRunning = false;
        }
    }

    public String getCode() {
        return this.theCode;
    }
}

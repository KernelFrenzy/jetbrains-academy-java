package com.xfrenzy47x.server;

import com.xfrenzy47x.server.helper.Utility;
import com.xfrenzy47x.server.model.CoreData;
import com.xfrenzy47x.server.service.CustomThread;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {

    public static void main(String[] args) {
        int poolSize = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        AtomicBoolean exit = new AtomicBoolean(false);
        CoreData coreData = Utility.initializeCoreData();

        try (ServerSocket server = new ServerSocket(23456, 50, InetAddress.getByName("127.0.0.1"))) {
            System.out.println("Server started!");
            while (!exit.get()) {
                Socket socket = server.accept();
                executor.submit(new CustomThread(server, socket, coreData, exit));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

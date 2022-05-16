package com.xfrenzy47x.server.service;

import com.xfrenzy47x.server.helper.Utility;
import com.xfrenzy47x.server.model.CoreData;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomThread extends Thread {
    private final CoreData coreData;
    private final AtomicBoolean exit;
    private final ServerSocket server;
    private final Socket socket;

    public CustomThread(ServerSocket server, Socket socket, CoreData coreData, AtomicBoolean exit) {
        this.server = server;
        this.socket = socket;
        this.coreData = coreData;
        this.exit = exit;
    }

    @Override
    public void run() {
        try (socket; DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            ServerConnection serverConnection = new ServerConnection(input, output);

            String request = serverConnection.getMessage();
            String[] requestSplit = request.split(" ", 3);

            switch (requestSplit[0]) {
                case "GET":
                    getFile(requestSplit, serverConnection);
                    break;
                case "PUT":
                    addFile(requestSplit, serverConnection);
                    break;
                case "DELETE":
                    deleteFile(requestSplit, serverConnection);
                    break;
                default:
                    if (requestSplit[0].equalsIgnoreCase("exit")) {
                        exit.set(true);
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (exit.get()) {
                try {
                    Utility.serialize(coreData, coreData.getCoreDataPath());
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            }
        }
    }

    public void addFile(String[] request, ServerConnection serverConnection) throws IOException {
        coreData.setData("", request[1]);
        String fileName = coreData.getFileName();
        int fileId = coreData.getFileId();
        File file = new File(coreData.getSavePath() + fileName);
        boolean success = true;
        if (file.exists() || file.isDirectory()) {
            success = false;
        } else {
            byte[] bytes = serverConnection.getBytes();
            try {
                Files.createFile(file.toPath());
                Files.write(Paths.get(coreData.getSavePath() + fileName), bytes);
            } catch (IOException ex) {
                success = false;
            }
        }

        if (success) {
            coreData.addData(fileId, fileName);
            serverConnection.send200WithContent(fileId + "");
        } else {
            serverConnection.send403();
        }
    }

    public void getFile(String[] request, ServerConnection serverConnection) throws IOException {
        coreData.setData(request[1], request[2]);
        String fileName = coreData.getFileName();
        File file = new File(coreData.getSavePath() + fileName);
        if (!file.exists() || file.isDirectory()) {
            serverConnection.send404();
        } else {
            byte[] content;
            try (FileInputStream scanner = new FileInputStream(file)) {
                content = scanner.readAllBytes();
            } catch (IOException ex) {
                content = null;
            }

            if (content == null) {
                serverConnection.send403();
            } else {
                serverConnection.send200WithFile(content);
            }
        }
    }

    public void deleteFile(String[] request, ServerConnection serverConnection) throws IOException {
        coreData.setData(request[1], request[2]);
        String fileName = coreData.getFileName();
        int fileId = coreData.getFileId();
        File file = new File(coreData.getSavePath() + fileName);
        if (!file.exists() || file.isDirectory()) {
            serverConnection.send404();
        } else {
            Files.delete(file.toPath());
            coreData.deleteData(fileId);
            serverConnection.send200();
        }
    }
}

package com.xfrenzy47x.client;

import com.xfrenzy47x.client.utility.ClientConnection;
import com.xfrenzy47x.client.utility.ScannerReader;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static final String DATA_DIR = "/home/xfrenzy47x/git/jetbrains-academy-java-for-beginners/file-server/src/main/java/com/xfrenzy47x/client/data";
    static final ScannerReader scannerReader = new ScannerReader();
    static ClientConnection clientConnection;

    public static void main(String[] args) throws InterruptedException {
        createDir();
        Thread.sleep(1000);

        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 23456);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {
            clientConnection = new ClientConnection(input, output, scannerReader);

            System.out.println("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
            String action = scannerReader.readLine();

            switch (action) {
                case "1" -> getFile();
                case "2" -> createFile();
                case "3" -> deleteFile();
                default -> exit();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void createDir() {
        File dir = new File(DATA_DIR);
        try {
            dir.mkdir();
        } catch (SecurityException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void exit() throws IOException {
        clientConnection.setAction("exit");
        clientConnection.send();
    }

    private static void getFile() throws IOException {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id):");
        int getBy = scannerReader.readInt();
        String fileName = getFileName(getBy);

        clientConnection.setAction("GET");
        clientConnection.setGetBy(getBy);
        clientConnection.setFileName(fileName);
        clientConnection.send();
    }

    private static String getFileName(int getBy) {
        if (getBy == 1) {
            System.out.print("Enter name of th file: ");
        } else if (getBy == 2) {
            System.out.print("Enter id: ");
        }
        return scannerReader.readLine();
    }

    private static byte[] readFileContent(String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(DATA_DIR + File.separator + fileName));
    }

    private static void deleteFile() throws IOException {
        System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id):");
        int getBy = scannerReader.readInt();
        String fileName = getFileName(getBy);

        clientConnection.setAction("DELETE");
        clientConnection.setGetBy(getBy);
        clientConnection.setFileName(fileName);
        clientConnection.send();
    }

    private static void createFile() throws IOException {
        System.out.print("Enter name of the file: ");
        String fileToSend = scannerReader.readLine();
        byte[] fileContent = readFileContent(fileToSend);
        System.out.print("Enter name of the file to be saved on server: ");
        String fileName = scannerReader.readLine();

        if (fileName.trim().isEmpty()) {
            fileName = "BLANK";
        }

        clientConnection.setAction("PUT");
        clientConnection.setFileName(fileName);
        clientConnection.setContent(fileContent);
        clientConnection.send();
    }
}

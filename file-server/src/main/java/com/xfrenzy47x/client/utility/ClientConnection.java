package com.xfrenzy47x.client.utility;

import java.io.*;
import java.nio.file.Files;

public class ClientConnection {
    public static final String DATA_DIR = "/home/xfrenzy47x/git/jetbrains-academy-java-for-beginners/file-server/src/main/java/com/xfrenzy47x/client/data";
    private final DataInputStream input;
    private final DataOutputStream output;
    private final ScannerReader scannerReader;

    private String action;
    private String getBy;
    private byte[] content;
    private String fileName;

    public ClientConnection(DataInputStream input, DataOutputStream output, ScannerReader scannerReader) {
        this.input = input;
        this.output = output;
        this.scannerReader = scannerReader;
        action = "";
        fileName = "";
        content = new byte[0];
        getBy = "";
    }

    public void setGetBy(int value) {
        this.getBy = value == 1 ? "BY_NAME" : "BY_ID";
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    private void printResponse() throws IOException {
        String response = input.readUTF();
        String[] responses = response.split(" ", 2);

        switch (this.action) {
            case "PUT":
                handlePUT(responses);
                break;
            case "GET":
                handleGET(responses);
                break;
            case "DELETE":
                handleDELETE(responses);
                break;
            default:
                unexpectedResponse();
                break;
        }
    }

    private void handleDELETE(String[] response) {
        switch (response[0]) {
            case "200":
                System.out.println("The responses say that this file is was deleted successfully!");
                break;
            case "404":
                System.out.println("The response says that this file is not found!");
                break;
            default:
                unexpectedResponse();
                break;
        }
    }

    private void handleGET(String[] response) throws IOException {
        switch (response[0]) {
            case "200":
                int len = input.readInt();
                byte[] bytes = new byte[len];
                input.readFully(bytes, 0, bytes.length);
                saveFile(bytes);
                break;
            case "404":
                System.out.println("The response says that this file is not found!");
                break;
            default:
                unexpectedResponse();
                break;
        }
    }

    private void handlePUT(String[] response) {
        switch (response[0]) {
            case "200":
                System.out.println("Response says that file is saved! ID = " + response[1]);
                break;
            case "403":
                System.out.println("Response say that saving file is forbidden!");
                break;
            default:
                unexpectedResponse();
                break;
        }
    }

    private void unexpectedResponse() {
        System.out.println("Unexpected response returned!");
    }

    private void saveFile(byte[] content) {
        System.out.print("The file was downloaded! Specify a name for it: ");
        String file = scannerReader.readLine();

        File theFile = new File(DATA_DIR + File.separator + file);
        try {
            Files.createFile(theFile.toPath());
            try (FileOutputStream pw = new FileOutputStream(theFile)) {
                pw.write(content);
            }
        } catch (Exception ex) {
            System.out.printf("Error: %s", ex);
        }
        System.out.println("File saved on the hard drive!");
    }

    private String getNoContentCommand() {
        return this.action + " " + this.getBy + " " + this.fileName;
    }

    public void send() throws IOException {
        if (this.action.equalsIgnoreCase("PUT")) {
            sendContent();
        } else {
            sendNoContent();
        }
        System.out.println("The request was sent.");
        if (!action.equalsIgnoreCase("exit"))
            printResponse();
    }

    private void sendContent() throws IOException {
        output.writeUTF("PUT " + this.fileName);
        output.writeInt(this.content.length);
        output.write(this.content);
    }

    private void sendNoContent() throws IOException {
        output.writeUTF(getNoContentCommand());
        output.flush();
    }
}

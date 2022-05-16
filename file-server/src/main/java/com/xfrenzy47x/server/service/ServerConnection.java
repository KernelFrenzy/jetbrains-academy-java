package com.xfrenzy47x.server.service;

import java.io.*;

public class ServerConnection {
    DataInputStream input;
    DataOutputStream output;

    public ServerConnection(DataInputStream input, DataOutputStream output) {
        this.input = input;
        this.output = output;
    }

    public String getMessage() throws IOException {
        return input.readUTF();
    }

    public byte[] getBytes() throws IOException {
        int len = input.readInt();
        byte[] bytes = new byte[len];
        input.readFully(bytes, 0, bytes.length);
        return bytes;
    }

    private void sendMessage(String message) throws IOException {
        output.writeUTF(message);
    }

    public void send200() throws IOException {
        sendMessage("200");
    }

    public void send403() throws IOException {
        sendMessage("403");
    }

    public void send404() throws IOException {
        sendMessage("404");
    }

    public void send200WithContent(String content) throws IOException {
        sendMessage("200 " + content);
    }

    public void send200WithFile(byte[] bytes) throws IOException {
        output.writeUTF("200");
        output.writeInt(bytes.length);
        output.write(bytes);
        output.flush();
    }
}

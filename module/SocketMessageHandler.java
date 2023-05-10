package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketMessageHandler implements Runnable {
    private BufferedReader read;
    private PrintWriter write;

    public SocketMessageHandler(Socket client) {
        try {
            this.write = new PrintWriter(client.getOutputStream());
            this.read = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void sendMessage(String message) {
        this.write.println(message);
    }

    public String receiveMessage() throws IOException {
        return this.read.readLine();
    }

    @Override
    public void run() {
        System.out.println("Thread Successfully Created!");
        System.out.println("Listen into incoming Message ...");
        sendMessage("HTTP/1.1 101 Switching Protocols\nUpgrade: websocket\nConnection: Upgrade\nSec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=\n");
        String message = "";
        while (message != null) {
            try {
                message = receiveMessage();
                if(message.startsWith("")){
                    String[] sec = message.split(" ", 2);
                    sendMessage("HTTP/1.1 101 Switching Protocols\nUpgrade: websocket\nConnection: Upgrade\nSec-WebSocket-Accept: s3pPLMBiTxaQ9kYGzzhZRbK+xOo=\n");
                }
            } catch (IOException e) {
                System.out.println("Client left the chat!");
                break;
            }
            System.out.println("New Message:" + message);
        }
    }
}

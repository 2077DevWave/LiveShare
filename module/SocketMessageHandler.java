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
            this.write = new PrintWriter(client.getOutputStream(),true);
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
        String message = "";
        while (message != null) {
            try {
                message = receiveMessage();
                if(message.startsWith("")){
                    String[] sec = message.split(" ", 2);
                }
            } catch (IOException e) {
                System.out.println("Connection Failed: " + e.getMessage());
                break;
            }
            System.out.println("New Message:" + message);
            if(!message.equals("<<message received>>")){
                sendMessage("<<message received>>");
            }
        }
    }
}

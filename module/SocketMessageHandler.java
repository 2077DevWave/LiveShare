package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketMessageHandler extends MessageHandler {
    private BufferedReader socketReader;
    private PrintWriter socketWriter;
    public String messagePrefix = "newMessage: ";

    public SocketMessageHandler(Socket client) {
        try {
            this.socketWriter = new PrintWriter(client.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String message) {
        this.socketWriter.println(Secure.packetEncode(message));
    }

    public String receiveMessage() throws IOException {
        String message;
        if (!(message = Secure.packetDecode(this.socketReader.readLine())).equals("message received!")){
            sendMessage("message received!");
        }
        return messagePrefix + message;
    }

}
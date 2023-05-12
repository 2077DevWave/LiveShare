package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketMessageHandler extends MessageHandler {
    // object to receive and read new messages from client stream
    private BufferedReader socketReader;
    // object to send message into socket stream
    private PrintWriter socketWriter;
    // prefix to print in output message
    public String messagePrefix = "newMessage: ";

    /**
     * class to receive and send messages into socket stream
     * @param client - the client Socket object
     */
    public SocketMessageHandler(Socket client) {
        try {
            this.socketWriter = new PrintWriter(client.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * to send the message into client
     * @param message - message
     */
    public void sendMessage(String message) {
        this.socketWriter.println(Secure.packetEncode(message));
    }

    /**
     * receive message from client stream
     * @return - received message with prefix
     */
    public String receiveMessage() throws IOException {
        String message;
        if (!(message = Secure.packetDecode(this.socketReader.readLine())).equals("message received!")){
            sendMessage("message received!");
        }
        return messagePrefix + message;
    }

}
package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketMessageHandler extends MessageHandler {
    private BufferedReader read;
    private PrintWriter write;

    public SocketMessageHandler(Socket client) {
        try {
            this.write = new PrintWriter(client.getOutputStream(), true);
            this.read = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void asyncReceiveMessage(){
        MessageReceiver receiver = new MessageReceiver();
        new Thread(receiver).start();
    }

    public void sendMessage(String message) {
        this.write.println(Secure.packetEncode(message));
    }

    public String receiveMessage() throws IOException {
        return Secure.packetDecode(this.read.readLine());
    }
}

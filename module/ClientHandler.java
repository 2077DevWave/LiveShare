package module;

import java.net.Socket;

public class ClientHandler extends SocketMessageHandler{
    public String name = "Client";

    public ClientHandler(Socket client) {
        super(client);
        System.out.println("New Client Connected!");
        System.out.println("Starting new thread...");
        new Thread(this).start();
    }
}

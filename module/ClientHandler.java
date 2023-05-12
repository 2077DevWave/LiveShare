package module;

import java.net.Socket;

public class ClientHandler extends SocketMessageHandler {
    private Socket handler;
    private int secret;
    public String name = "Client";

    public ClientHandler(Socket client) {
        super(client);
        this.handler = client;
        this.messagePrefix = "Client: ";
        asyncReceiveMessage();
    }

    public void setClientVariable(){
        secret = handler.hashCode();
    }

    public Socket getHandler() {
        return handler;
    }

    public int getSECRET() {
        return secret;
    }
}

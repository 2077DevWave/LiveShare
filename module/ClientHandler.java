package module;

import java.net.Socket;

public class ClientHandler extends SocketPacketHandler {
    // socket obj returned from SocketServer to handle user
    private Socket handler;
    // secret based on hash to decode and encode message
    private int secret;
    // client name
    public String name = "Client: ";

    /**
     * its Receiving message with SocketMessageHandler and save client information
     * @param client - Socket object
     */
    public ClientHandler(Socket client) {
        super(client);
        this.handler = client;
        this.messagePrefix = this.name;
        asyncReceiveMessage();
    }

    /**
     * Set optional client information
     */
    public void setClientVariable(){
        secret = handler.hashCode();
    }

    /**
     * get client handler as Socket object
     * @return - Socket object
     */
    public Socket getHandler() {
        return handler;
    }

    /**
     * return secret based on hash for this client
     * its an optional information so may be its 0 for some clients
     * @return - hash code
     */
    public int getSECRET() {
        return secret;
    }
}

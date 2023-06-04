package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;

import lib.Logger;

public class LiveShare extends ServerSocket implements Runnable {
    private boolean isServerRunning;
    public static Clients clientsHandler;

    /**
     * a class to make a realtime connection based on WebSocket
     * @param port - a port to run the server on it
     * @throws IOException - if there is an error to create a Server
     */
    public LiveShare(int port) throws IOException {
        super(port, 0, Inet4Address.getByName(Config.SERVER_IPV4.getStrVal()));
        isServerRunning = true;
    }

    /**
    * Starts the server. This method is called in its own thread to listen for clients and authenticate them until the server is
    */
    @Override
    public void run() {
        clientsHandler = new Clients();
        // This method is called by the server to start the server.
        while (isServerRunning) {
            try {
                Authenticator auth = new Authenticator(this.accept());
                Logger.newLog("new connection!");
                User user;
                // if the authentication is complete
                if((user = auth.fullAuth()) != null) {
                    Logger.newLog("authentication complete for " + user.getId());
                    clientsHandler.newClient(user);
                }
            } catch (IOException e) {
                System.out.println("Server Shutdown ...");
            }
        }
    }

    /**
    * Shuts down the server. This is a no - op if the server is already closed. It does not throw an exception
    */
    public void shutDown() {
        // Closes the server and closes the server.
        if (!this.isClosed()) {
            isServerRunning = false;
            try {
                this.close();
            } catch (IOException e) {
                System.out.println("Server Successfully Closed!");
            }

        }
    }

}

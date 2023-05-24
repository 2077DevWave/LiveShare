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
     * its a Runnable class and you should run it in a separate thread to work correctly
     */
    @Override
    public void run() {
        clientsHandler = new Clients();
        while (isServerRunning) {
            try {
                Authenticator auth = new Authenticator(this.accept());
                Logger.newLog("new connection!");
                User user;
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
     * its used to shutdown the server
     */
    public void shutDown() {
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

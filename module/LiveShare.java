package module;

import java.io.IOException;
import java.net.ServerSocket;

public class LiveShare extends ServerSocket implements Runnable {
    private boolean isServerRunning;

    /**
     * a class to make a realtime connection based on WebSocket
     * @param port - a port to run the server on it
     * @throws IOException - if there is an error to create a Server
     */
    public LiveShare(int port) throws IOException {
        super(port);
        isServerRunning = true;
    }

    /**
     * its a Runnable class and you should run it in a separate thread to work correctly
     */
    @Override
    public void run() {
        Clients clientsHandler = new Clients();
        while (isServerRunning) {
            try {
                clientsHandler.newClient(this.accept());
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

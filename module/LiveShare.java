package module;

import java.io.IOException;
import java.net.ServerSocket;

public class LiveShare extends ServerSocket implements Runnable {
    private boolean isServerRunning;

    public LiveShare(int port) throws IOException {
        super(port);
        isServerRunning = true;
    }

    @Override
    public void run() {
        while (isServerRunning) {
            try {
                new ClientHandler(this.accept());
            } catch (IOException e) {
                System.out.println("Server Shutdown ...");
            }
        }
    }

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

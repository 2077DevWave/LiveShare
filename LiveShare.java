package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LiveShare extends ServerSocket implements Runnable {
    private boolean isServerRunning;
    private ArrayList<ClientHandler> clients;

    public LiveShare(int port) throws IOException {
        super(port);
        isServerRunning = true;
    }

    @Override
    public void run() {
        while (isServerRunning) {
            try {
                ClientHandler client = new ClientHandler(this.accept());
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

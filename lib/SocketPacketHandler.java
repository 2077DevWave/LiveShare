package lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketPacketHandler{
    public Socket handler;
    // object to receive and read new byte from client stream
    private BufferedReader socketReader;
    // object to send byte array into socket stream
    private PrintWriter socketWriter;

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client the client Socket object
     */
    public SocketPacketHandler(Socket handler) {
        try {
            this.handler = handler;
            this.socketWriter = new PrintWriter(handler.getOutputStream(),true);
            this.socketReader = new BufferedReader(new InputStreamReader(handler.getInputStream()));
        } catch (IOException e) {
            Logger.newError("dddd:" + e.getMessage());
        }
    }

    public void sendPacket(String message){
        socketWriter.println(message);
    }

    public String receivedPacket() throws IOException{
        String packet;
        while((packet = this.socketReader.readLine()) != null){
            return packet;
        }
        return null;
    }
}
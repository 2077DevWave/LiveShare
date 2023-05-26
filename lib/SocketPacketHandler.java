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

    /**
    * Sends a packet to the server. This is a blocking call so the connection will be closed when the server returns
    * 
    * @param message - The message to send
    */
    public void sendPacket(String message){
        socketWriter.println(message);
    }

    /**
    * Receives and returns the next packet from the server. This method blocks until a packet is received or the connection is closed.
    * 
    * 
    * @return the next packet from the server or null if there are no more packets to be received from the server
    */
    public String receivedPacket() throws IOException{
        String packet;
        // Returns the next line from the socket.
        while((packet = this.socketReader.readLine()) != null){
            return packet;
        }
        return null;
    }
}
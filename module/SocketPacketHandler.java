package module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

public class SocketPacketHandler extends PacketHandler {
    // object to receive and read new messages from client stream
    private BufferedReader socketReader;
    // object to send message into socket stream
    private PrintWriter socketWriter;
    // prefix to print in output message
    public String messagePrefix = "newMessage: ";

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client - the client Socket object
     */
    public SocketPacketHandler(Socket client) {
        try {
            this.socketWriter = new PrintWriter(client.getOutputStream(), true);
            this.socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * to send the message into client
     * 
     * @param message - message
     */
    public void sendPacket(String message) {
        this.socketWriter.print(Secure.packetEncode(message.getBytes()));
    }

    public void sendPacket(byte[] message) {
        this.socketWriter.print(Secure.packetEncode(message));
        System.out.println("Packet sent");
    }

    /**
     * receive message from client stream
     * 
     * @return - received message with prefix
     */
    public byte[] receivedPacket() throws IOException {
        System.out.println("new packet received");
        return Secure.packetDecode(this.socketReader.readLine().getBytes());
    }

}
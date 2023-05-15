package module;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import org.json.JSONObject;

public class SocketPacketHandler extends PacketHandler {
    // object to receive and read new byte from client stream
    private InputStream socketReader;
    // object to send byte array into socket stream
    private OutputStream socketWriter;
    // prefix to print in output message
    public String messagePrefix = "newMessage: ";

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client - the client Socket object
     */
    public SocketPacketHandler(Socket client) {
        try {
            this.socketWriter = client.getOutputStream();
            this.socketReader = client.getInputStream();
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
        sendPacket(message.getBytes());
    }

    public void sendPacket(byte[] message) {
        try {
            this.socketWriter.write(Secure.packetEncode(message));
            this.socketWriter.flush();
        } catch (IOException e) {
            System.out.println("Error sending packet: " + e.getMessage());
        }
        System.out.println("Packet sent");
    }

    /**
     * receive message from client stream
     * 
     * @return - received message with prefix
     */
    public byte[] receivedPacket() throws IOException {
        System.out.println("new packet received");
        int nByte;
        while ((nByte = socketReader.available()) == 0){}
        return Secure.packetDecode(this.socketReader.readNBytes(nByte));
    }

}
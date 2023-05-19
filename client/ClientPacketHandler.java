package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.json.JSONObject;

import server.Secure;

public class ClientPacketHandler extends PacketHandler {
    // object to receive and read new byte from client stream
    private InputStream socketReader;
    // object to send byte array into socket stream
    private OutputStream socketWriter;

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client the client Socket object
     */
    public ClientPacketHandler(Socket handler) {
        try {
            this.socketWriter = handler.getOutputStream();
            this.socketReader = handler.getInputStream();
        } catch (IOException e) {
           System.out.println("failed to create output or input stream: " + e.getMessage());
        }
    }
    
    /**
     * to send the message into client
     * 
     * @param message message
     */
    public void sendPacket(int toUser,String message) {
        sendPacket(Request.createMessageRequest(toUser, message));
    }

    /**
     * to send json packet into socket stream
     * @param json - JSONObJECT to send
     */
    private void sendPacket(JSONObject json) {
        sendPacket(json.toString().getBytes());
    }

    /**
     * to send the message into client
     * 
     * @param message byte array packet
     */
    private void sendPacket(byte[] packet) {
        try {
            this.socketWriter.write(Secure.packetEncode(packet));
            this.socketWriter.flush();
        } catch (IOException e) {
            System.out.println("Error sending packet: " + e.getMessage());
        }
    }

    /**
     * receive message from client stream
     * 
     * @return received message with prefix
     */
    public byte[] receivedPacket() throws IOException {
        System.out.println("new packet received");
        int nByte;
        while ((nByte = socketReader.available()) == 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } // wait until packet received
        return Secure.packetDecode(this.socketReader.readNBytes(nByte));
    }

}
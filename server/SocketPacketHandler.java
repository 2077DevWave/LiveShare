package server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import org.json.JSONObject;

public abstract class SocketPacketHandler extends PacketHandler {
    // object to receive and read new byte from client stream
    private InputStream socketReader;
    // object to send byte array into socket stream
    private OutputStream socketWriter;

    public abstract String getName();
    public abstract int getId();

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client the client Socket object
     */
    public SocketPacketHandler(Socket handler) {
        try {
            this.socketWriter = handler.getOutputStream();
            this.socketReader = handler.getInputStream();
        } catch (IOException e) {
            Logger.newError(e.getMessage());
        }
    }

    /**
     * to send the message into client
     * 
     * @param message message
     */
    public void sendPacket(String message) {
        sendPacket(message.getBytes());
    }

    /**
     * to send json packet into socket stream
     * @param json - JSONObJECT to send
     */
    public void sendPacket(JSONObject json) {
        sendPacket(json.toString().getBytes());
    }

    /**
     * to send the message into client
     * 
     * @param message byte array packet
     */
    public void sendPacket(byte[] packet) {
        try {
            this.socketWriter.write(Secure.packetEncode(packet));
            this.socketWriter.flush();
        } catch (IOException e) {
            Logger.newError("Error sending packet: " + e.getMessage());
        }
        Logger.newLog("Packet sent");
    }

    /**
     * receive message from client stream
     * 
     * @return received message with prefix
     */
    public byte[] receivedPacket() throws IOException {
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
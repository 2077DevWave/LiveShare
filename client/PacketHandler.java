package client;

import java.io.IOException;
import org.json.JSONObject;

public abstract class PacketHandler implements Runnable {
    // method to receive new messages
    public abstract byte[] receivedPacket() throws IOException;

    public enum PacketType {
        MESSAGE(1),
        FILE(2),
        FILE_SLICE(3),
        PERMISSION(4),
        AUTHENTICATE(5),
        USER(6),
        SYSTEM(7);

        private int value;

        private PacketType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * running in other threads to receive new messages asynchronously
     */
    public void asyncPacketReceiver() {
        new Thread(this).start();
    }

    /**
     * a Runnable method to receive new messages asynchronously
     */
    public void run() {
        System.out.println("Thread Successfully Created!");
        System.out.println("Listen into incoming Message ...");
        JSONObject packet;
        while (true) {
            packet = null;
            try {
                packet = toJsonObject(receivedPacket());
            } catch (IOException e) {
                System.out.println("Failed to receive packet!");
            }
            if (packet != null) {
                handleRequest(packet);
            }
        }
    }

    /**
     * convert received bytes into JSONObject
     * 
     * @param data - bytes to convert
     * @return return JSONObject representation
     */
    public JSONObject toJsonObject(byte[] data) {
        return new JSONObject(new String(data));
    }

    /**
     * Handle incoming Json request
     * 
     * @param packet - packet received
     */
    public void handleRequest(JSONObject packet) {
        if (packet.getInt("request_type") == PacketType.MESSAGE.getValue()) {
            System.out.println("Client_" + packet.getInt("from_user_id") + ": " + packet.getString("message"));
        } else if (packet.getInt("request_type") == PacketType.PERMISSION.getValue()) {
            System.out.println("Client_" + packet.getInt("from_user_id") + ": User Need Permission");
        }else{
            System.out.println("Unknown Packet!");
        }
    }

}
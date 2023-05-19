package server;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class PacketHandler implements Runnable {
    // method to receive new messages
    public abstract byte[] receivedPacket() throws IOException;

    // method to send new packets
    public abstract void sendPacket(byte[] message);

    // method to receive user Id
    public abstract int getId();

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
        Logger.newLog("Thread Successfully Created!");
        Logger.newLog("Listen into incoming Message ...");
        JSONObject packet;
        while (true) {
            packet = null;
            try {
                packet = toJsonObject(receivedPacket());
                Logger.newLog("new packet received from " + getId());
            } catch (IOException e) {
                Logger.newWarning("Failed to receive packet!");
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
        try{
            return new JSONObject(new String(data));
        }catch(JSONException e){
            Logger.newWarning("Failed to cast into JSONObject " + e.getMessage());
        }
        return null;
        
    }

    /**
     * Handle incoming Json request
     * 
     * @param packet - packet received
     */
    public void handleRequest(JSONObject packet) {
        if (packet.getInt("request_type") == PacketType.MESSAGE.getValue()) {
            int destinationUserId = packet.getInt("to_user_id");
            Logger.newLog("Request to send message into " + destinationUserId);
            if (LiveShare.clientsHandler.userExist(destinationUserId)) {
                LiveShare.clientsHandler.findUser(destinationUserId)
                        .sendPacket(Request.createMessageRequest(getId(), packet.getString("message")));
            } else {
                // TODO: user not found
                Logger.newError("user not found to send message request");
            }
        } else if (packet.getInt("request_type") == PacketType.PERMISSION.getValue()) {
            ClientHandler desHandler;
            if ((desHandler = LiveShare.clientsHandler.findUser(packet.getInt("to_user_id"))) != null) {
                desHandler.sendPacket(Request.createUserPermissionRequest(getId()));
            } else {
                // TODO: user not found
                Logger.newError("user not found to send permission request");
            }
        }
    }

}
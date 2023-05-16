package module;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.json.JSONObject;

public class SocketPacketHandler extends PacketHandler {
    // object to receive and read new byte from client stream
    private InputStream socketReader;
    // object to send byte array into socket stream
    private OutputStream socketWriter;
    // prefix to print in output message
    public String messagePrefix = "newMessage: ";
    // to save unconfirmed file
    public ArrayList<FileHandler> unConfirmFileHandler = new ArrayList<FileHandler>();
    // to save unconfirmed Request id
    public ArrayList<Integer> unConfirmRequestId = new ArrayList<Integer>();

    /**
     * class to receive and send messages into socket stream
     * 
     * @param client the client Socket object
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
     * @param message message
     */
    public void sendMessage(String message) {
        sendPacket(createJsonRequest(message).toString().getBytes());
    }

    public void sendFile(String Path) {
        try {
            FileHandler fileHandler = new FileHandler(Path);
            if(fileHandler.canRead()){
                JSONObject requestJson = createJsonRequest(fileHandler.getNameWithoutFormat(), fileHandler.getFormat(), 0);
                unConfirmFileHandler.add(fileHandler);
                unConfirmRequestId.add(requestJson.getInt("id"));
                sendPacket(requestJson.toString().getBytes());
            }
        } catch (SecurityException e) {
            System.out.println("Access denied: " + e.getMessage());
        }
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
            System.out.println("Error sending packet: " + e.getMessage());
        }
        System.out.println("Packet sent");
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
        } // wait until packet received
        return Secure.packetDecode(this.socketReader.readNBytes(nByte));
    }

    /**
     * this method used to get a permission to send a file with specified format
     * 
     * @param fileName      file name you want to send
     * @param fileFormat    file format like "mp4","txt",...
     * @param numberOfSlice Number of all parts
     * @return Json String contain file information
     */
    private JSONObject createJsonRequest(String fileName, String fileFormat, int numberOfSlice) {
        JSONObject json = new JSONObject();
        json.put("type", PacketType.PERMISSION.getValue());
        json.put("format", fileFormat);
        json.put("name", fileName);
        json.put("id", createUniqRequestId());
        json.put("Slice", numberOfSlice);
        return json;
    }

    /**
     * to send a slice of file
     * 
     * @implNote first you must send a file information
     * @param fileId       generated in file request
     * @param fileData     file binary data to send
     * @param currentSlice Slice number
     * @return JsonObject contain all of information
     */
    private JSONObject createJsonRequest(int fileId, byte[] fileData, int currentSlice) {
        JSONObject json = new JSONObject();
        json.put("type", PacketType.FILE_SLICE.getValue());
        json.put("id", fileId);
        json.put("Slice", currentSlice);
        json.put("data", fileData);
        return json;
    }

    /**
     * create a JSON object contain information about the message
     * 
     * @param Message the message you want to send
     * @return a JsonObject contain type, data, id
     */
    private JSONObject createJsonRequest(String Message) {
        JSONObject json = new JSONObject();
        json.put("type", PacketType.STRING.getValue());
        json.put("id", createUniqRequestId());
        json.put("data", Message);
        return json;
    }

    /**
     * create uniq id based in time and hash
     * 
     * @return int uniq id
     */
    private int createUniqRequestId() {
        LocalDateTime time = LocalDateTime.now();
        int id = time.getYear() + time.getMonthValue() + time.getDayOfMonth() + time.getHour() + time.getMinute()
                + time.getSecond() + time.getNano() + time.hashCode();
        return id;
    }

}
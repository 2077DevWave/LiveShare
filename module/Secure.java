package module;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Secure {

    /**
     * to decode packet date received from client or server
     * @param message - String message to decode
     * @return - decoded date
     */
    public static String packetDecode(String message){
        // TODO: make an decode algorithm
        return message;
    }

    /**
     * to encode packet date before send into client or server
     * @param decodedMessage - String message to encode
     * @return - encoded message
     */
    public static String packetEncode(String decodedMessage){
        // TODO: encode, decoded algorithm
        return decodedMessage;
    }

    /**
     * encode String into Sha1
     * @param input - String to encode
     * @return - Sha1 encoded string
     */
    public static String sha1Encode(String input) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * encode String into Base64 encoded string
     * @param text - normal string
     * @return - String encoded into base64
     */
    public static String base64Encode(String text){
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;
    }

    /**
     * decode string encoded as Base64
     * @param text - encoded string
     * @return - decoded string
     */
    public static String base64Decode(String text){
        String decodedString = Base64.getDecoder().decode(text).toString();
        return decodedString;
    }

    /**
     * create handshake Secret value to make Secure connection
     * @param secret - Secret received from client handshake
     * @return - return Secret
     */
    public static String handShakeSecret(String secret){
        String outSecret = secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        outSecret = sha1Encode(outSecret);
        outSecret = base64Encode(outSecret);
        return outSecret;
    }
}

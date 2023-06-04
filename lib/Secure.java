package lib;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Secure {

    /**
    * Decode a packet. This is a static method to avoid having to reimplement it in a subclass.
    * 
    * @param message - the packet to decode ( must be non - null
    */
    public static byte[] packetDecode(byte[] message){
        // TODO: make an decode algorithm
        return message;
    }

    /**
    * Encodes a packet and returns the result. This is used to create a packet from a byte array that can be sent to the network
    * 
    * @param decodedMessage - the message to be
    */
    public static byte[] packetEncode(byte[] decodedMessage){
        // TODO: encode, decoded algorithm
        return decodedMessage;
    }

    /**
    * Encodes a string using SHA - 1. This is used to generate hashes that are valid for the password and other data that can be stored in the database
    * 
    * @param input - The string to be encoded
    * 
    * @return The SHA - 1 encoded string with 0's at the begining if it isn't 32
    */
    public static String sha1Encode(String input) {

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            // Generate a hashtext string for the current hashtext.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
    * Encodes a string using Base64. This is a convenience method that uses the default encoding for the platform.
    * 
    * @param text - The text to encode. Must not be null.
    * 
    * @return The encoded text. Never null but may be empty if text is null or contains non - ASCII characters
    */
    public static String base64Encode(String text){
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;
    }

    /**
    * Decodes Base64 data and returns the decoded string. This is useful for decoding data that was encoded with #base64Encode ( String )
    * 
    * @param text - The text to decode.
    * 
    * @return The decoded text or null if the text was null or not encoded with Base64. Note that a null return does not imply that the input was valid
    */
    public static String base64Decode(String text){
        String decodedString = Base64.getDecoder().decode(text).toString();
        return decodedString;
    }

    /**
    * Takes a secret and handshakes it with SHA1 and base64. This is used to encrypt and decrypt the secret
    * 
    * @param secret - The secret to be encrypted
    * 
    * @return The encrypted and base64 version of the secret that was passed in as a parameter to this method ( in this case it is the same
    */
    public static String handShakeSecret(String secret){
        String outSecret = secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        outSecret = sha1Encode(outSecret);
        outSecret = base64Encode(outSecret);
        return outSecret;
    }
}

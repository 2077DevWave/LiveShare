package module;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Secure {
    public static String packetDecode(String message){
        // TODO: make an decode function
        return message;
    }

    public static String packetEncode(String decodedMessage){
        // TODO: encode, decoded string
        return decodedMessage;
    }

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

    public static String base64Encode(String text){
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;
    }

    public static String base64Decode(String text){
        String decodedString = Base64.getDecoder().decode(text).toString();
        return decodedString;
    }

    public static String handShakeSecret(String secret){
        String outSecret = secret + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
        outSecret = sha1Encode(outSecret);
        outSecret = base64Encode(outSecret);
        return outSecret;
    }
}

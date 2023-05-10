package module;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Secure {
    public static String decodeMessage(String message){
        // TODO: make an decode function
        return message;
    }

    public static String encodeMessage(String decodedMessage){
        // TODO: encode, decoded string
        return decodedMessage;
    }

    public static String sha1(String input) {

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
}

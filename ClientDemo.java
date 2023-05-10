import java.net.Socket;

class ClientDemo{
    public static void main(String[] args) {
        try{
            Socket client = new Socket("127.0.0.1",8090);
            System.out.println("connected!");
        }catch(Exception e){
            System.out.println("faild to connect");
        }

        
    }
}
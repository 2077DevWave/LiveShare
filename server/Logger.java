package server;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

public class Logger {
    private static OutputStream output = System.out;

    enum logType{
        ERROR("Error: "),
        WARNING("Warning: "),
        PROCESS("Process log: ");

        private String Message;
        private logType(String message) {
            this.Message = message;
        }

        public String getMessage() {
            return this.Message;
        }
    }

    public static void newLog(String message){
        try {
            message = currentTime() + " " + logType.PROCESS.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    public static void newWarning(String message){
        try {
            message = currentTime() + " " + logType.WARNING.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    public static void newError(String message){
        try {
            message = currentTime() + " " + logType.ERROR.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    private static String currentTime(){
        LocalDateTime time = LocalDateTime.now();
        String currentTime;
        currentTime = "[" + time.getYear() + "\\" + time.getMonth() + "\\" + time.getDayOfMonth() + " | " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "]";
        return currentTime;
    }

}

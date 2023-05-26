package lib;

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

        /**
        * Returns the message associated with this error. If there is no message associated with this error this method returns null.
        * 
        * 
        * @return the message associated with this error or null if there is no message associated with this error or if there is no
        */
        public String getMessage() {
            return this.Message;
        }
    }

    /**
    * Writes a message to the log. This is used to create a new log entry for the process and log message
    * 
    * @param message - The message to write
    */
    public static void newLog(String message){
        try {
            message = currentTime() + " " + logType.PROCESS.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    /**
    * Writes a warning message to the log file. This is used to log warnings that are in the process of being sent to the client.
    * 
    * @param message - The message to write to the log file. The message will be prefixed with the current time
    */
    public static void newWarning(String message){
        try {
            message = currentTime() + " " + logType.WARNING.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    /**
    * Writes a new error message to the log file. This method is used to log errors that occur during processing such as adding an entry to a database or logging system.
    * 
    * @param message - The message to write to the log file. The message will be prefixed with the current time
    */
    public static void newError(String message){
        try {
            message = currentTime() + " " + logType.ERROR.getMessage() + message + "\n";
            output.write(message.getBytes());
            output.flush();
        } catch (IOException e) {

        }
    }

    /**
    * Returns a string representation of the current time. Used for debugging purposes. It is not recommended to use this method in production code.
    * 
    * 
    * @return String representation of the current time in human readable format ( yyyy - mm - dd hh : mm : ss
    */
    private static String currentTime(){
        LocalDateTime time = LocalDateTime.now();
        String currentTime;
        currentTime = "[" + time.getYear() + "\\" + time.getMonth() + "\\" + time.getDayOfMonth() + " | " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "]";
        return currentTime;
    }

}

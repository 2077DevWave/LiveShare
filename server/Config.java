package server;

public enum Config{
    SERVER_IPV4("127.0.0.1"),
    SERVER_PORT(8980),
    SERVER_AUTH(1),
    MYSQL_IPV4("localhost"),
    MYSQL_PORT(3306),
    MYSQL_DATABASE("liveshare"),
    MYSQL_USERNAME("root"),
    MYSQL_PASSWORD(""),
    // NEED_HANDSHAKE(0),
    MAX_AUTH_RETRY(10),
    // MAX_SERVER_CONNECTIONS(10),
    // MAX_REQUEST_PER_CLIENT(1000),
    // MAX_REQUEST_PER_IP(1000),
    // MAX_PACKET_SIZE(8192),
    // MAX_FILE_SLICE_SIZE(4096),
    ROOM_USER_LIMIT(2),
    GROUP_MEMBER_LIMIT(100),
    SHOW_USER_NAME(1);

    private String strVal;
    private int intVal;

    private Config(String val){
        this.strVal = val;
        try{
            this.intVal = Integer.parseInt(val);
        }catch(NumberFormatException e){}
    }
    
    private Config(int val){
        this.intVal = val;

        try{
            this.strVal = val + "";
        }catch(NumberFormatException e){}
    }

    public int getIntVal() {
        return intVal;
    }

    public Boolean getBooleanVal() {
        if(intVal == 0){
            return false;
        }else{
            return true;
        }
    }

    public String getStrVal() {
        return strVal;
    }
}

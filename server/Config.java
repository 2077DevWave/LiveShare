package server;

public enum Config{
    SERVER_IPV4("127.0.0.1"),
    SERVER_PORT(8980),
    SERVER_AUTH(0),
    NEED_HANDSHAKE(0),
    MAX_SERVER_CONNECTIONS(10),
    MAX_REQUEST_PER_CLIENT(1000),
    MAX_REQUEST_PER_IP(1000),
    MAX_PACKET_SIZE(8192),
    MAX_FILE_SLICE_SIZE(4096)
    ;

    public String strVal;
    public int intVal;

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

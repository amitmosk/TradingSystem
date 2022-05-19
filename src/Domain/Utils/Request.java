package Domain.Utils;

public class Request {
    private int opcode;
    private int data_size;
    private String[] data;
//    private LocalDateTime request_time;

    public Request(int opcode, int data_size, String[] data){
        if (data_size != data.length)
            throw new IllegalArgumentException("bad request");
        this.opcode = opcode;
        this.data_size = data_size;
        this.data = data;
    }

    public int get_opcode() {
        return opcode;
    }

    public int get_data_size() {
        return data_size;
    }

    public String[] get_data() {
        return this.data;
    }
}
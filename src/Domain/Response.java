package Domain;

public class Response<T> {
    private T value;
    private boolean wasException=false;
    private String message;

    public Response(Exception exception, String message){
        wasException=true;
        this.message=message;
    }

    public Response(T value, String message){
        this.value=value;
        this.message=message;
    }

    public T getValue() {
        return value;
    }

    public boolean WasException() {
        return wasException;
    }

    public String getMessage() {
        return message;
    }
}

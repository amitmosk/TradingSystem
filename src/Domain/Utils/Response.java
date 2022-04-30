package Domain.Utils;

public class Response<T> {
    private T value;
    private boolean wasException=false;
    private String message;

    public Response(Exception exception){
        wasException=true;
        message=exception.getMessage();
    }

    public Response(T value, String message){
        this.value=value;
        this.wasException = false;
        this.message = message;
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
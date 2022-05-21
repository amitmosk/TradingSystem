package TradingSystem.server.Domain.Utils;


import TradingSystem.server.Domain.Utils.Exception.MarketException;

public class Response<T> {
    private T value;
    private boolean wasException=false;
    private String message;

    public Response(String message, Exception e){
        this.value = (T) e;
        wasException = true;
        this.message = message;
    }

    public Response(T value, String message){
        this.value = value;
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

    public Response() {
    }

    public boolean isWasException() {
        return wasException;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setWasException(boolean wasException) {
        this.wasException = wasException;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
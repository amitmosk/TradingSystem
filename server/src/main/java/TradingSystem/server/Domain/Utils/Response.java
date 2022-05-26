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
}
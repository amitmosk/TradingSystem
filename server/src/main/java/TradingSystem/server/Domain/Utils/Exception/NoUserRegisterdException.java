package TradingSystem.server.Domain.Utils.Exception;

public class NoUserRegisterdException extends RegisterException {
    public NoUserRegisterdException(String s) {
        super(s);
    }

    public NoUserRegisterdException() {
    }
}

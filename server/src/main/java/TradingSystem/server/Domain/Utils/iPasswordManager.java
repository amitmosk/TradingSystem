package TradingSystem.server.Domain.Utils;


public interface iPasswordManager {

    String hash(String password);
    boolean authenticate(String password, String token);
}
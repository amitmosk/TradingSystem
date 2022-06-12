package TradingSystem.server.Domain.Utils;

import TradingSystem.server.Domain.Utils.Exception.NoUserRegisterdException;

public interface Observer {
    // update method
    void add_notification(String notification) throws NoUserRegisterdException;
}
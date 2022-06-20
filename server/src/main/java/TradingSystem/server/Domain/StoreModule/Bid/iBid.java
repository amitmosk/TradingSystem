package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;

import javax.naming.NoPermissionException;

public interface iBid {

    void add_manager_of_store(String manager_email, boolean owner) throws MarketException;
    void remove_manager(String email) throws MarketException;
    void add_manager_answer(String email, boolean answer, double negotiation_price,  User buyer) throws NoPermissionException, MarketException;
    BidStatus get_status();
}

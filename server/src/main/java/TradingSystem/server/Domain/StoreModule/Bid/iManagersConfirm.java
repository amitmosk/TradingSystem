package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.Domain.Utils.Exception.MarketException;

import javax.naming.NoPermissionException;
import TradingSystem.server.Domain.UserModule.User;



import javax.naming.NoPermissionException;

public interface iManagersConfirm {

    void add_manager_of_store(String manager_email, boolean owner) ;
    void remove_manager(String email) ;
    void add_manager_answer(String email, boolean answer, double negotiation_price) throws NoPermissionException, MarketException;
    BidStatus get_status();
}

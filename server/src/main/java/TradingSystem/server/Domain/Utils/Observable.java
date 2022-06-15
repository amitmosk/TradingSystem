package TradingSystem.server.Domain.Utils;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.Utils.Exception.MarketException;

public interface Observable {
    // attach methods -> add to the list.
    void add_owner(AssignUser appointer, AssignUser new_owner) throws MarketException;
    void add_manager(AssignUser appointer, AssignUser new_manager) throws MarketException;
    // detach methods -> remove from the list.
    void remove_owner(AssignUser remover, AssignUser user_to_delete_appointment) throws MarketException;
    void remove_manager(AssignUser remover, AssignUser user_to_delete_appointment) throws MarketException;
    // notify methods :
    void send_message_to_the_store_stuff(String message, String sender_email);
}

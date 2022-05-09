package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.StoreModule.Store.Store;

public abstract class AssignState {
    public boolean login(String pw) throws MarketException {
        throw new AlreadyRegisterdException("Assign user cannot log in");
    }

    public void addPurchase(UserPurchase purchase) {
    }

    public void check_if_user_buy_from_this_store(int store_id) throws MarketException {
        throw new UserNeverBoughtInTheStoreException("Guest Hasn't made any purchases yet.");
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws MarketException {
        throw new UserNeverBoughtInTheStoreException("Guest Hasn't made any purchases yet.");
    }

    public UserPurchaseHistory view_user_purchase_history() throws MarketException {
        throw new UserNeverBoughtInTheStoreException("Guest Hasn't made any purchases yet.");
    }

    public String get_user_name() throws MarketException {
        throw new NoUserRegisterdException("Guest doesnt have name.");
    }

    public String get_user_last_name() throws MarketException {
        throw new NoUserRegisterdException("Guest doesnt have last name.");
    }

    public String get_user_email() throws MarketException {
        throw new NoUserRegisterdException("Guest doesnt have email.");
    }

    public void check_admin_permission() throws MarketException {
        throw new NoPremssionException("only admin have permissions for this operation.");
    }

    public void unregister(String password) throws MarketException {
        throw new NoUserRegisterdException("guest cant unregister from the system");
    }

    public void edit_name(String pw, String new_name) throws MarketException {
        throw new NoUserRegisterdException("guest cant change is name");
    }

    public void edit_password(String old_password, String password) throws MarketException {
        throw new NoUserRegisterdException("guest cant change is password");
    }

    public void edit_last_name(String pw, String new_last_name) throws MarketException {
        throw new NoUserRegisterdException("guest cant change his last name");
    }

    public String get_sequrity_question() throws MarketException {
        throw new NoUserRegisterdException("guest does not have privacy question");
    }

    public void verify_answer(String answer) throws MarketException {
        throw new NoUserRegisterdException("guest does not have privacy question");
    }

    public void improve_security(String password, String question, String answer) throws MarketException {
        throw new NoUserRegisterdException("guest cannot improve security");
    }

    public void add_founder(Store store, Appointment appointment) throws Exception {throw new Exception("guest cannot be a founder");}
}

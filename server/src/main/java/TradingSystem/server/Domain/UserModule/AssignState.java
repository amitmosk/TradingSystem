package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.Utils.Observer;

import java.util.LinkedList;
import java.util.List;

public abstract class AssignState implements Observer {
    public AssignState() {
    }

    public boolean login(String pw) throws MarketException{
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

    public void edit_name(String new_name) throws MarketException {
        throw new NoUserRegisterdException("guest cant change is name");
    }

    public void edit_password(String old_password, String password) throws MarketException {
        throw new NoUserRegisterdException("guest cant change is password");
    }

    public void edit_last_name(String new_last_name) throws MarketException {
        throw new NoUserRegisterdException("guest cant change his last name");
    }

    public String view_security_question() throws MarketException {
        throw new NoUserRegisterdException("guest does not have privacy question");
    }

    public void verify_answer(String answer) throws MarketException {
        throw new NoUserRegisterdException("guest does not have privacy question");
    }

    public void improve_security(String password, String question, String answer) throws MarketException {
        throw new NoUserRegisterdException("guest cannot improve security");
    }

    public void add_founder(Store store, Appointment appointment) throws MarketException {throw new NoUserRegisterdException("guest cannot be a founder");}

    public AssignUser is_assign() throws NoUserRegisterdException {throw new NoUserRegisterdException("cannot get guest AssignUser");}

    public void add_notification(String notification) throws NoUserRegisterdException { throw new NoUserRegisterdException("cannot add notification to guest");}

    //returns admin if admin else null
    public Admin is_admin(){ return null;}

    public void add_owner(Store store, Appointment appointment_to_add) throws MarketException {throw new NoUserRegisterdException("guest cannot be appointed");}

    public void add_manager(Store store, Appointment appointment_to_add) throws MarketException {throw new NoUserRegisterdException("guest cannot be appointed");}

    public void remove_appointment(Store store) throws MarketException {throw new NoUserRegisterdException("guest cannot be appointed");}


    public UserState find_state(){return UserState.GUEST;}

    public List<Integer> stores_managers_list() {return new LinkedList<>();}
    }

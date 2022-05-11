package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Communication.NotificationHandler;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.MarketSecuirtyException;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.Utils.Exception.NoUserRegisterdException;

import java.util.HashMap;
import java.util.Map;

// TODO: everytime user creates/appoint a store make an appointment
public class AssignUser extends AssignState {
    private String email;
    private Security security;
    private String name;
    private String lastName;
    private UserPurchaseHistory userPurchaseHistory;

    private Map<Store, Appointment> founder;
    private Map<Store, Appointment> owner;
    private Map<Store,Appointment> manager;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.security = new Security(pw);
        this.name = name;
        this.lastName = lastName;
        this.userPurchaseHistory = new UserPurchaseHistory();
        this.founder = new HashMap<>();
        this.owner = new HashMap<>();
        this.manager = new HashMap<>();
    }

    @Override
    public boolean login(String pw) throws MarketException {
        security.check_password(pw);
        return true;
    }

    @Override
    public void addPurchase(UserPurchase purchase) {
        userPurchaseHistory.addPurchase(purchase);
    }

    public void check_if_user_buy_from_this_store(int store_id) throws MarketException {
        this.userPurchaseHistory.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws MarketException {
        this.userPurchaseHistory.check_if_user_buy_this_product(storeID, productID);
    }

    //TODO: verify about removing throws and override
    @Override
    public String get_user_name(){
        return name;
    }

    @Override
    public String get_user_last_name(){
        return lastName;
    }

    @Override
    public String get_user_email(){
        return email;
    }

    @Override
    public UserPurchaseHistory view_user_purchase_history() {
        return this.userPurchaseHistory;
    }

    @Override
    public void unregister(String password) throws MarketException {
        security.check_password(password);
    }

    public void edit_name(String pw, String new_name) throws MarketException {
        security.check_password(pw);
        this.name = new_name;
    }

    public void edit_password(String old_password, String password) throws MarketException {
        this.security.edit_password(old_password, password);
    }

    public void edit_last_name(String pw, String new_last_name) throws MarketException {
        security.check_password(pw);
        this.name = new_last_name;
    }

    public String get_sequrity_question() throws MarketException {
        return security.get_question();
    }

    public void verify_answer(String answer) throws MarketException {
        security.verify_answer(answer);
    }

    public void improve_security(String password, String question, String answer) throws MarketSecuirtyException {
        security.check_password(password);
        security.check_improvable();
        security = new PremiumSecurity(password, question, answer);
    }

    public void add_founder(Store store,Appointment appointment) throws MarketException {
        this.founder.put(store,appointment);
    }

    //TODO: check methods of adding
    public void add_owner(Store store, Appointment appointment_to_add) throws MarketException {
        if(this.owner.containsKey(store))
            throw new AppointmentException(email+" user already appointed");
        this.owner.put(store,appointment_to_add);
    }

    public void add_manager(Store store, Appointment appointment_to_add) throws MarketException {
        if(this.manager.containsKey(store))
            throw new AppointmentException(email+" user already appointed");
        this.manager.put(store,appointment_to_add);
    }

    public void remove_appointment(Store store) throws MarketException {
        if(manager.containsKey(store))
            manager.remove(store);
        else if(owner.containsKey(store))
            owner.remove(store);
        else if(founder.containsKey(store))
            founder.remove(store);
        else
            throw new AppointmentException("user is not appointed to store");
    }

    public AssignUser get_assign(){
        return this;
    }

    public void add_notification(String notification) {
        NotificationHandler.getInstance().add_notification(this.email, notification);
    }
}
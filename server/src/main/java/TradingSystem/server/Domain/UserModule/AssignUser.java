package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Service.NotificationHandler;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// TODO: everytime user creates/appoint a store make an appointment
@Entity
@DiscriminatorValue("1")
public class AssignUser extends AssignState {
    private String email;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Security security;
    private String name;
    private String lastName;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserPurchaseHistory userPurchaseHistory;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "founder_table",
            joinColumns = {@JoinColumn(name = "user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "store_id")
    private Map<Store, Appointment> founder;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "owner_table",
            joinColumns = {@JoinColumn(name = "user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "store_id")
    private Map<Store, Appointment> owner;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "manager_table",
            joinColumns = {@JoinColumn(name = "user", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "appointment_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "store_id")
    private Map<Store,Appointment> manager;

    // ------------------------------ constructors ------------------------------
    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.security = new Security(pw);
        this.name = name;
        this.lastName = lastName;
        this.userPurchaseHistory = new UserPurchaseHistory();
        this.founder = new HashMap<>();
        this.owner = new HashMap<>();
        this.manager = new HashMap<>();
//        HibernateUtils.persist(this.security);
//        HibernateUtils.persist(this.userPurchaseHistory);
//        HibernateUtils.persist(this);
    }

    public AssignUser() {
    }

    // ------------------------------ getters ------------------------------
    public String getEmail() {
        return email;
    }

    public Security getSecurity() {
        return security;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public UserPurchaseHistory getUserPurchaseHistory() {
        return userPurchaseHistory;
    }

    public Map<Store, Appointment> getFounder() {
        return founder;
    }

    public Map<Store, Appointment> getOwner() {
        return owner;
    }

    public Map<Store, Appointment> getManager() {
        return manager;
    }

    // ------------------------------ setters ------------------------------
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserPurchaseHistory(UserPurchaseHistory userPurchaseHistory) {
        this.userPurchaseHistory = userPurchaseHistory;
    }

    public void setFounder(Map<Store, Appointment> founder) {
        this.founder = founder;
    }

    public void setOwner(Map<Store, Appointment> owner) {
        this.owner = owner;
    }

    public void setManager(Map<Store, Appointment> manager) {
        this.manager = manager;
    }

    // ------------------------------ methods ------------------------------

    @Override
    public boolean login(String pw) throws MarketException {
        security.check_correct_password(pw);
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
        security.check_correct_password(password);
    }

    public void edit_name(String new_name) {
        this.name = new_name;
    }

    public void edit_password(String old_password, String password) throws MarketException {
        this.security.edit_password(old_password, password);

    }

    public void edit_last_name(String new_last_name) {
        this.lastName = new_last_name;
    }

    public String view_security_question() throws MarketException {
        return security.find_question();
    }

    public void verify_answer(String answer) throws MarketException {
        security.verify_answer(answer);
    }

    public void improve_security(String password, String question, String answer) throws MarketException {
        security.check_correct_password(password);
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

    public AssignUser is_assign(){
        return this;
    }

    public UserState find_state(){return UserState.ASSIGN_USER;}

    public List<Integer> stores_managers_list() {
        LinkedList<Integer> answer = new LinkedList();
        for (Store store : this.founder.keySet()){
            answer.add(store.getStore_id());
        }
        for (Store store : this.owner.keySet()){
            answer.add(store.getStore_id());
        }
        for (Store store : this.manager.keySet()){
            answer.add(store.getStore_id());
        }
        return answer;
    }


    public void add_notification(String notification) {
        NotificationHandler.getInstance().add_notification(this.email, notification);
    }
}
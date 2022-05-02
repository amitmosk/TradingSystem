package Domain.UserModule;

import Domain.Communication.QuestionHandler;
import Domain.Statistics.Statistic;
import Domain.Statistics.StatisticsManager;
import Domain.StoreModule.Basket;
import Domain.Purchase.Purchase;
import Domain.Purchase.UserPurchase;
import Domain.Purchase.UserPurchaseHistory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController {
    
    // ------------------- fields -------------------------------------
    private Map<String,User> users;              // email,user
    private Map<Integer,User> onlineUsers;       // id,user
    private AtomicInteger ID;
    private AtomicInteger purchaseID;
    private Object usersLock;
    private StatisticsManager statisticsManager;

    public static void load() {
        // no for this version
    }

    // ------------------- singleton class ----------------------------
    private static class SingletonHolder{
        private static UserController instance = new UserController();
    }
    
    // ------------------- constructors --------------------------------
    public UserController() {
        this.ID = new AtomicInteger(0);
        this.purchaseID = new AtomicInteger(0);
        this.users = new ConcurrentHashMap<>() ;        //thread safe
        this.onlineUsers = new ConcurrentHashMap<>();  //thread safe
        this.usersLock = new Object();
        this.statisticsManager = new StatisticsManager();
    }

    
    // ------------------ methods --------------------------------------
    /**
     * @return the instance of the user controller
     */
    public static UserController getInstance(){
        return SingletonHolder.instance;
    }

    /**
     * function that connects new guest to the system.
     * @return guest's online id
     */
    public int guest_login() {
        int cur_guest_id = ID.getAndIncrement();    // synchronized
        User newUser = new User();
        onlineUsers.put(cur_guest_id, newUser);
        statisticsManager.inc_connect_system_count();
        return cur_guest_id;
    }

    /**
     * non synchronized function
     * function that check if user email is already registered in the system
     * @param email represnts the user email
     * @return true if the email is registered in the system else false
     */
    private boolean isRegistered(String email){
        return users.containsKey(email);
    }

//    /**
//     * function that checks if current id exists in online
//     * @param id represent the id of specific online member
//     * @return true if the member is online
//     */
//    private boolean isOnline(int id){
//        return onlineUsers.containsKey(id);
//    }

    /**
     * function that register new user to the system.
     * new user gets an empty cart
     * @param email new email of the user
     * @param pw    new password of the user
     * @param name  the user's name
     * @param lastName the user's last name
     */
    public void register(int ID, String email, String pw, String name, String lastName) throws Exception {
        synchronized (usersLock) {
            if (isRegistered(email))
                throw new IllegalAccessException("user email "+email+" already exists in the system");
            User user = onlineUsers.get(ID);
            user.register(email, pw, name, lastName);
            users.put(email, user);
        }
        statisticsManager.inc_register_count();
    }

    /**
     * @param ID the user's id in the system
     * @param email the user email to log-in
     * @param password the user password
     * @return the status if log-in succeed
     */
    public boolean login(int ID,String email, String password) throws Exception {
        if(isRegistered(email) && users.get(email).login(password)){
            User user = users.get(email);
            onlineUsers.put(ID, user);
            statisticsManager.inc_login_count();
            return true;
        }
        throw new Exception("User email does not match to the password");
    }


    /**
     * @param ID online user's id to logout
     */
    public void logout(int ID){
        User user = onlineUsers.get(ID);
        user.logout();
        onlineUsers.put(ID,new User());
        statisticsManager.inc_logout_count();
    }

    /**
     * @param user id
     * @return the user's cart
     */
    public Cart view_user_cart(int user) {
        return onlineUsers.get(user).getCart();
    }


    /**
     * function that gets the basket by store ID
     * @param userID represents the user id in the online map
     * @param storeID represents the store id
     * @return the store basket from the user's cart
     */
    public Basket getBasketByStoreID(int userID , int storeID) {
        User user = onlineUsers.get(userID);
        return user.getBasketByStoreID(storeID);
    }

    /**
     * function that add basket to the cart
     * @param userID the online user ID.
     * @param storeID represents the store id
     * @param basket
     */
    public void addBasket(int userID, int storeID, Basket basket) {
        User user = onlineUsers.get(userID);
        user.addBasket(storeID,basket);
    }

    /**
     * function that remove basket in case its empty
     * @param loggedUser
     * @param storeID
     * @param storeBasket
     */
    public void removeBasketIfNeeded(int loggedUser, int storeID, Basket storeBasket) {
        User user = onlineUsers.get(loggedUser);
        user.removeBasketIfNeeded(storeID, storeBasket);
    }


    /**
     * function that gets all the baskets from the user's cart
     * @param loggedUser
     * @return cart's baskets
     */
    public Map<Integer, Basket> getBaskets(int loggedUser) {
        User user = onlineUsers.get(loggedUser);
        return user.getBaskets();
    }


    /**
     * function that returns the logged user cart
     * @param loggedUser represents the user ID
     * @return the user's cart
     */
    public Cart getCart(int loggedUser) {
        User user = onlineUsers.get(loggedUser);
        return user.getCart();
    }

    /**
     * function that makes a new purchase and add it to the history & clears the cart
     * @param loggedUser
     */
    public UserPurchase buyCart(int loggedUser, Map<Integer, Purchase> store_id_purchase, double cart_total_price) {
        User user = onlineUsers.get(loggedUser);
        UserPurchase userPurchase = user.buyCart(purchaseID.getAndIncrement(), store_id_purchase, cart_total_price);
        statisticsManager.inc_buy_cart_count();
        return userPurchase;
    }


    public void check_if_user_buy_from_this_store(int loggedUser,int store_id) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int loggedUser,int productID, int storeID) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.check_if_user_buy_this_product(storeID,productID);
    }

    public UserPurchaseHistory view_user_purchase_history(int loggedUser) throws Exception { //admin
        User user = onlineUsers.get(loggedUser);
        return user.view_user_purchase_history();
    }

    public String get_user_name(int loggedUser) throws Exception {
        User user = onlineUsers.get(loggedUser);
        return user.get_user_name();
    }

    public String get_user_last_name(int loggedUser) throws Exception {
        User user = onlineUsers.get(loggedUser);
        return user.get_user_last_name();
    }

    public String get_email(int loggedUser) throws Exception {
        User user = onlineUsers.get(loggedUser);
        return user.get_user_email();
    }

    public void check_admin_permission(int loggedUser) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.check_admin_permission();
    }

    public UserPurchaseHistory admin_view_user_purchase_history(String email) throws Exception { //admin
        if(!isRegistered(email)) throw new Exception("user "+email+"is not registered to the system.");
        User user = users.get(email);
        return user.view_user_purchase_history();
    }

    private void remove_email_from_online_users(String email){ //if exists
        for(Map.Entry<Integer,User> entry : onlineUsers.entrySet()){
            try{
                if(entry.getValue().get_user_email().equals(email)) {
                    onlineUsers.remove(entry.getKey());
                    return;
                }
            }
            catch (Exception e){

            }
        }
    }

    public void remove_user(int ID,String email) throws Exception {
        if(!isRegistered(email)) throw new Exception("failed to remove due to the reason "+email+" is not registered in the system.");
        if(email.equals(get_email(ID))) throw new Exception("failed to remove admin from the system.");
        remove_email_from_online_users(email);
        synchronized (usersLock){ users.remove(email);}
    }

    public String unregister(int ID ,String password) throws Exception {
        String email = get_email(ID);
        User user = onlineUsers.get(ID);
        user.unregister(password); //TODO: add more privacy ?
        synchronized (usersLock) { users.remove(email); }
        onlineUsers.put(ID,new User());
        return email;
    }

    public String edit_name(int loggedUser, String pw, String new_name) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_name(pw,new_name);
        return get_email(loggedUser);
    }

    public String edit_password(int loggedUser, String old_password, String password) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_password(old_password,password);
        return get_email(loggedUser);
    }

    public String edit_last_name(int loggedUser, String pw, String new_last_name) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_last_name(pw,new_last_name);
        return get_email(loggedUser);
    }

    public Statistic get_statistics() {
        return statisticsManager.get_system_statistics();
    }


    public void send_question_to_admin(String user_email, String question) {
        QuestionHandler.getInstance().add_user_question(question, user_email);
    }

    public void answer_user_question(int question_id, String answer) {
        QuestionHandler.getInstance().answer_user_question(question_id, answer);
    }

    public List<String> view_users_questions() {
        return QuestionHandler.getInstance().view_admin_questions();
    }

    public User add_admin(String email, String pw, String name, String lastName) throws Exception {
        User admin = new User();
        admin.set_admin(email,pw,name,lastName);
        synchronized (usersLock){
            users.put(email,admin);
        }
        return admin;
    }

    public String get_user_security_question(int loggedUser) throws Exception {
        User user = onlineUsers.get(loggedUser);
        return user.get_user_sequrity_question();
    }

    public String edit_name_premium(int loggedUser, String pw, String new_name, String answer) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_name_premium(pw,new_name,answer);
        return get_email(loggedUser);
    }

    public String edit_last_name_premium(int loggedUser, String pw, String new_last_name, String answer) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_last_name_premium(pw,new_last_name,answer);
        return get_email(loggedUser);
    }

    public String edit_passsword_premium(int loggedUser, String old_password, String new_password, String answer) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.edit_password_premium(old_password,new_password,answer);
        return get_email(loggedUser);
    }

    public String improve_security(int loggedUser, String password, String question, String answer) throws Exception {
        User user = onlineUsers.get(loggedUser);
        user.improve_security(password,question,answer);
        return get_email(loggedUser);
    }
}

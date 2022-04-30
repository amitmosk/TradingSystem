package Domain.UserModule;

import Domain.StoreModule.Basket;
import Domain.Utils.Utils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


public class User {
    private AssignState state;
    private Cart cart;
    private AtomicBoolean isGuest;
    private AtomicBoolean isLogged;
    public User() { // new login guest
        this.state = new Guest();
        this.cart = new Cart();
        isGuest = new AtomicBoolean(true);
        this.isLogged = new AtomicBoolean(false);
    }



    private void checkDetails(String email, String pw, String name, String lastName) throws Exception {
        Utils.emailCheck(email);
        Utils.nameCheck(name);
        Utils.nameCheck(lastName);
        Utils.passwordCheck(pw);
    }

    public boolean register(String email, String pw, String name, String lastName) throws Exception {
        if(!isGuest.get()) return false;
        checkDetails(email,pw,name,lastName);
        boolean res = this.isLogged.compareAndSet(false,true);
        if(res) {
            this.state = new AssignUser(email, pw, name, lastName);
            isGuest.set(false);
        }
        return res;
    }

    public synchronized boolean login(String password) throws Exception {
        boolean res = this.state.login(password);
        this.isLogged.compareAndSet(false,true);
        return res;
    }

    public void logout(){
        this.isLogged.compareAndSet(true,false);
    }

    public Cart getCart() {
        return cart;
    }

    public Basket getBasketByStoreID(int storeID) {
        String email = "guest";
        try{ email = get_user_email(); }
        catch (Exception e){ }
        return cart.getBasket(storeID,email);
    }

    public void addBasket(int storeID, Basket basket) {
        cart.addBasket(storeID,basket);
    }

    public void removeBasketIfNeeded(int storeID, Basket storeBasket) {
        cart.removeBasketIfNeeded(storeID, storeBasket);
    }

    public Map<Integer,Basket> getBaskets() {
        return cart.getBaskets();
    }

    public UserPurchase buyCart(int purchaseID) {
        //make purchase
        UserPurchase purchase = new UserPurchase(cart.getBaskets(),purchaseID);
        //add to purchaseHistory
        this.state.addPurchase(purchase);
        //clear
        cart.clear();
        return purchase;
    }

    public void check_if_user_buy_from_this_store(int store_id) throws Exception {
        this.state.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws Exception {
        this.state.check_if_user_buy_this_product(storeID, productID);
    }

    public UserHistory view_user_purchase_history() throws Exception {
        return this.state.view_user_purchase_history();
    }

    public String get_user_name() throws Exception {
        return state.get_user_name();
    }

    public String get_user_last_name() throws Exception {
        return state.get_user_last_name();
    }

    public String get_user_email() throws Exception {
        return state.get_user_email();
    }

    public void check_admin_permission() throws Exception {
        state.check_admin_permission();
    }

    public void unregister(String password) throws Exception {
        state.unregister(password);
    }

    public void edit_name(String pw, String new_name) throws Exception {
        Utils.nameCheck(new_name);
        state.edit_name(pw,new_name);
    }

    public void edit_password(String pw, String password) throws Exception {
        Utils.passwordCheck(password);
        state.edit_password(pw,password);
    }

    public void edit_last_name(String pw, String new_last_name) throws Exception {
        Utils.nameCheck(new_last_name);
        state.edit_last_name(pw,new_last_name);
    }
}

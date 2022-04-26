package Domain.UserModule;

import Domain.StoreModule.Basket;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

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

    //TODO: implement
    private boolean emailCheck(String email) throws Exception{
        return false;
    }

    private boolean passwordCheck(String pw)throws Exception{ return false;
    }

    private boolean nameCheck(String name) throws Exception{return false;
    }

    public boolean register(String email, String pw, String name, String lastName) throws Exception {
        if(!isGuest.get()) return false;
        if(!emailCheck(email) || !passwordCheck(pw) || !nameCheck(name) || !nameCheck(lastName)) return false;
        boolean res = this.isLogged.compareAndSet(false,true);
        if(res) {
            this.state = new AssignUser(email, pw, name, lastName);
            isGuest.set(false);
        }
        return res;
    }

    public synchronized boolean login(String password) throws Exception {
        boolean res = this.state.login(password);
        this.isLogged.set(true);
        return res;
    }

    public Cart getCart() {
        return cart;
    }

    public Basket getBasketByStoreID(int storeID) {
        return cart.getBasket(storeID);
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

    public String get_user_last_email() throws Exception {
        return state.get_user_email();
    }
}

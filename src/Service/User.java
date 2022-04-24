package Service;

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

    private boolean emailCheck(String email){
        return false;
    }

    private boolean passwordCheck(String pw){
        return false;
    }

    private boolean nameCheck(String name){
        return false;
    }

    public boolean register(String email, String pw, String name, String lastName) {
        if(!isGuest.get()) return false;
        if(!emailCheck(email) || !passwordCheck(pw) || !nameCheck(name) || !nameCheck(lastName)) return false;
        this.state = new AssignUser(email,pw,name,lastName);
        boolean res = this.isLogged.compareAndSet(false,true);
        if(res) isGuest.set(false);
        return res;
    }

    public synchronized boolean login(String password) {
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

    public void buyCart(int purchaseID) {
        //make purchase
        Purchase purchase = new UserPurchase(cart.getBaskets(),purchaseID);
        //add to purchaseHistory
        this.state.addPurchase(purchase);
        //clear
        cart.clear();
    }
}

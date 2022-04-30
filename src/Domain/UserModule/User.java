package Domain.UserModule;

import Domain.StoreModule.Basket;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class User {
    private final int MaxNamesLength = 10;
    private final int MinPasswordLength = 6;
    private final int MaxPasswordLength = 12;
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

    private void emailCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("Email cannot be null");
        if (!pat.matcher(email).matches())
            throw new Exception("Invalid email");
    }

    private void passwordCheck(String pw)throws Exception{
        boolean containsNum = false;
        boolean containsUpper = false;
        boolean containsLower = false;
        if(pw.length() < MinPasswordLength || pw.length() > MaxPasswordLength)
            throw new Exception("password length should be in range of 6-12");
        char[] pwArray = pw.toCharArray();
        for (char c : pwArray) {
            if (c >= '0' || c <= '9')
                containsNum = true;
            else if (c >= 'a' || c <= 'z')
                containsLower = true;
            else if (c >= 'A' || c <= 'Z')
                containsUpper = true;
            else
                throw new Exception("password should only upper & lower letter and digit");
        }
        if(!(containsLower && containsUpper && containsNum))
            throw new Exception("password should contain at least one upper & lower letter, and digit");
    }

    private void nameCheck(String name) throws Exception {
        if (name == null || name.equals(""))
            throw new Exception("Name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new Exception("Name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new Exception("The name must contain letters only");
        }
    }

    private void checkDetails(String email, String pw, String name, String lastName) throws Exception {
        emailCheck(email);
        nameCheck(name);
        nameCheck(lastName);
        passwordCheck(pw);
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
        nameCheck(new_name);
        state.edit_name(pw,new_name);
    }

    public void edit_password(String pw, String password) throws Exception {
        passwordCheck(password);
        state.edit_password(pw,password);
    }

    public void edit_last_name(String pw, String new_last_name) throws Exception {
        nameCheck(new_last_name);
        state.edit_last_name(pw,new_last_name);
    }
}

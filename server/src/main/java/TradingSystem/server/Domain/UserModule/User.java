package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.Utils.Utils;
import net.bytebuddy.asm.Advice;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private AssignState state;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cart cart;
    private AtomicBoolean isGuest;
    @Transient
    private AtomicBoolean isLogged;
    private String birth_date;

    public User() { // new login guest
        this.state = new Guest();
        this.cart = new Cart();
        isGuest = new AtomicBoolean(true);
        this.isLogged = new AtomicBoolean(false);
        this.birth_date = LocalDate.now().toString();
    }

    public AssignState getState() {
        return state;
    }

    public AtomicBoolean getIsGuest() {
        return isGuest;
    }

    public AtomicBoolean getIsLogged() {
        return isLogged;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setState(AssignState state) {
        this.state = state;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setIsGuest(AtomicBoolean isGuest) {
        this.isGuest = isGuest;
    }

    public void setIsLogged(AtomicBoolean isLogged) {
        this.isLogged = isLogged;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    private void checkDetails(String email, String pw, String name, String lastName) throws MarketException {
        Utils.emailValidCheck(email);
        Utils.nameValidCheck(name);
        Utils.nameValidCheck(lastName);
        Utils.passwordValidCheck(pw);
    }

    public void register(String email, String pw, String name, String lastName, String birth_date) throws MarketException {
        if (!isGuest.get())
            throw new AlreadyRegisterdException("Assigned User cannot register");
        checkDetails(email, pw, name, lastName);
        boolean res = this.isLogged.compareAndSet(false, true);
        if (!res)
            throw new AlreadyRegisterdException("concurrency problem - register method");
        this.state = new AssignUser(email, pw, name, lastName);
        this.birth_date = birth_date;
        isGuest.set(false);
//        HibernateUtils.persist(this.cart);
//        HibernateUtils.persist(this.state);
        HibernateUtils.persist(this);
    }

    public synchronized void login(String password) throws MarketException {
        if (isLogged.get())
            throw new LoginException("User already logged in.");
        this.state.login(password); //verifies password
        boolean res = this.isLogged.compareAndSet(false, true);
        this.isGuest.compareAndSet(true, false);
        if (!res)
            throw new LoginException("User already logged in - concurrency");
        merge();
    }

    public void logout() throws MarketException {
        if (isGuest.get()) throw new NoUserRegisterdException("failed to logout from guest");
        if (!this.isLogged.compareAndSet(true, false))
            throw new NoUserRegisterdException("failed to logout user - concurrency problem");
        this.isGuest.compareAndSet(false, true);
        merge();
    }

    public Cart getCart() {
        return cart;
    }

    public Basket getBasketByStoreID(int storeID) {
        String email = "guest";
        try {
            email = user_email();
        } catch (Exception e) {
        }
        return cart.getBasket(storeID, email);
    }

/*    public void addBasket(int storeID, Basket basket) {
        cart.addBasket(storeID,basket);
    }*/

    public void removeBasketIfNeeded(int storeID, Basket storeBasket) {
        cart.removeBasketIfNeeded(storeID, storeBasket);
        merge();
    }

    public Map<Store, Basket> view_baskets() {
        return cart.getBaskets();
    }

    public UserPurchase buyCart(int purchaseID) throws MarketException {
        //check cart is not empty
        cart.verify_not_empty();
        //check availability
        double price = this.cart.check_cart_available_products_and_calc_price(this.get_age());
        //update stores inventory
        Map<Integer, Purchase> store_id_purchase = cart.update_stores_inventory(purchaseID);
        //make purchase
        UserPurchase purchase = new UserPurchase(purchaseID, store_id_purchase, price);
        //add to purchaseHistory
        this.state.addPurchase(purchase);
        //clear
        cart.clear();
        merge();
        return purchase;
    }

    public void check_if_user_buy_from_this_store(int store_id) throws MarketException {
        this.state.check_if_user_buy_from_this_store(store_id);
    }

    public void check_if_user_buy_this_product(int storeID, int productID) throws MarketException {
        this.state.check_if_user_buy_this_product(storeID, productID);
    }

    public UserPurchaseHistory view_user_purchase_history() throws MarketException {
        return this.state.view_user_purchase_history();
    }

    public String user_name() throws MarketException {
        return state.get_user_name();
    }

    public String user_last_name() throws MarketException {
        return state.get_user_last_name();
    }

    public String user_email() throws MarketException {
        return state.get_user_email();
    }

    public void check_admin_permission() throws MarketException {
        state.check_admin_permission();
    }

    public void unregister(String password) throws MarketException {
        state.unregister(password);
    }

    public void edit_name(String new_name) throws MarketException {
        Utils.nameValidCheck(new_name);
        state.edit_name(new_name);
        merge();
    }

    public void edit_password(String old_password, String password) throws MarketException {
        Utils.passwordValidCheck(password);
        state.edit_password(old_password, password);
        merge();
    }

    public void edit_last_name(String new_last_name) throws MarketException {
        Utils.nameValidCheck(new_last_name);
        state.edit_last_name(new_last_name);
        merge();
    }

    public void set_admin(String email, String pw, String name, String lastName) throws MarketException {
        checkDetails(email, pw, name, lastName);
        this.state = new Admin(email, pw, name, lastName);
    }

    public String user_security_question() throws MarketException {
        return this.state.view_security_question();
    }

    private void verify_answer(String answer) throws MarketException {
        this.state.verify_answer(answer);
    }

    public void edit_name_premium(String new_name, String answer) throws MarketException {
        verify_answer(answer);
        edit_name(new_name);
        merge();
    }

    public void edit_last_name_premium(String new_last_name, String answer) throws MarketException {
        verify_answer(answer);
        edit_last_name(new_last_name);
        merge();
    }

    public void edit_password_premium(String old_password, String new_password, String answer) throws MarketException {
        verify_answer(answer);
        edit_password(old_password, new_password);
        merge();
    }

    public void improve_security(String password, String question, String answer) throws MarketException {
        this.state.improve_security(password, question, answer);
        merge();
    }

    public void remove_product_from_cart(Store store, Product p) throws MarketException {
        this.cart.remove_product_from_cart(store, p);
        merge();
    }

    private String get_identifier_for_basket() {
        String identifier = "guest";
        try {
            identifier = user_email();
        } catch (Exception e) {
        }
        return identifier;
    }

    public void add_product_to_cart(Store store, Product p, int quantity) throws MarketException {
        String basket_identifier = get_identifier_for_basket();
        this.cart.add_product_to_cart(store, p, quantity, basket_identifier, p.getOriginal_price());
    }

    public void add_product_to_cart_from_bid_offer(Store store, Product product, int quantity, double price_per_unit) throws MarketException {
        String basket_identifier = get_identifier_for_basket();
        this.cart.add_product_to_cart(store, product, quantity, basket_identifier, price_per_unit);
        merge();
    }

    public void edit_product_quantity_in_cart(Store store, Product p, int quantity) throws MarketException {
        this.cart.edit_product_quantity_in_cart(store, p, quantity);
        merge();
    }

    public void add_founder(Store store, Appointment appointment) throws MarketException {
        this.state.add_founder(store, appointment);
        merge();
    }

    public AssignUser state_if_assigned() throws NoUserRegisterdException {
        return this.state.is_assign();
    }

    public Admin is_admin() {
        return state.is_admin();
    }

    public int get_age() {
        return Period.between(LocalDate.parse(this.birth_date), LocalDate.now()).getYears();
    }

    public void merge() {
//        User load = HibernateUtils.getEntityManager().find(this.getClass(),id);
        if (!isGuest.get())
            HibernateUtils.merge(this);
    }

    //TODO: method for testing
    public boolean test_isRegistered() {
        return !isGuest.get();
    }

    public boolean test_isLogged() {
        return this.isLogged.get();
    }

    public void add_notification(String notification) {
    }

    public UserState find_state() {
        return state.find_state();
    }

    public List<Integer> stores_managers_list() {
        return state.stores_managers_list();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

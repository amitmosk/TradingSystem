package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.Domain.Communication.QuestionHandler;
import TradingSystem.server.Domain.StoreModule.*;
import TradingSystem.server.Domain.StoreModule.Policy.DiscountPolicy;
import TradingSystem.server.Domain.StoreModule.Policy.PurchasePolicy;
import TradingSystem.server.Domain.StoreModule.Policy.Rule;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Store {

    //TODO: change all methods belongs to stuff_emails to point user.
    //TODO: reviews - should contain users / users_email ? if user changes his email ?

    // -- fields
    private final int store_id;
    private final User founder;
    private Map<User,Appointment> stuffs_and_appointments;
    private String name;
    public String foundation_date;
    private HashMap<Product, Integer> inventory; // product & quantity
    private boolean active;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private StorePurchaseHistory purchases_history;
    private StoreReview storeReview;
    private AtomicInteger product_ids_counter;
    private LinkedList<Rule> rules;

    // -- constructors
    public Store(int store_id, String name, User founder) {
        this.store_id = store_id;
        this.founder = founder;
        this.name = name;
        this.product_ids_counter = new AtomicInteger(1);
        this.active = true;
        this.foundation_date = LocalDate.now().toString();
        this.storeReview = new StoreReview();
        this.purchases_history = new StorePurchaseHistory(this.name);
        this.inventory = new HashMap<>();
        this.stuffs_and_appointments = new HashMap<>();
        this.rules = new LinkedList<Rule>();
    }


    // -- public methods

    public User getFounder() {
        return founder;
    }

    public int get_store_rating() {
        return this.storeReview.getAvgRating();
    }

    public void add_product_review(int product_id, String user_email, String review) throws ObjectDoesntExsitException {
        Product p = this.getProduct_by_product_id(product_id); //throws
        p.add_review(user_email, review);
    }

    public void add_store_rating(User user, int rating) throws Exception {
        if (this.stuffs_and_appointments.containsKey(user))
            throw new NoPremssionException("store members can't rate their store");
        this.storeReview.add_rating(user.get_user_email(), rating);
    }

    public void set_store_purchase_rules(Rule rule) {
        this.rules.add(rule);
    }


    public void add_product_rating(String user_email, int product_id, int rate) throws ObjectDoesntExsitException {
        Product p = this.getProduct_by_product_id(product_id);//throws
        p.add_rating(user_email, rate);
    }

    //TODO: fixed to appoint user & store classes instead of ids
    public Appointment appoint_founder() {
        Appointment appointment = new Appointment(this.founder, this.founder, this, StoreManagerType.store_founder);
        this.stuffs_and_appointments.put(founder, appointment);
        return appointment;
    }

    public void close_store_permanently() throws Exception {
        this.active = false;
        String message = "Store was closed permanently at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);
        this.stuffs_and_appointments = null;
    }


    public void close_store_temporarily(User user) throws Exception {
        this.check_permission(user, StorePermission.close_store_temporarily);
        this.active = false;
        String message = "Store was closed close_store_temporarily at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);
    }


    public void open_close_store(User user) throws Exception {
        this.check_permission(user, StorePermission.open_close_store);
        if (this.is_active())
            throw new StoreMethodException("The store is already open");
        this.active = true;
        String message = "Store was re-open at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);
    }

    public StoreManagersInfo view_store_management_information(User user) throws MarketException, IllegalAccessException {
        this.check_permission(user, StorePermission.view_permissions);
        //TODO: added parse to emails - appointment map
        HashMap<String, Appointment> emails_appointmets = new HashMap<>();
        for(Map.Entry<User,Appointment> en : stuffs_and_appointments.entrySet()){
            emails_appointmets.put(en.getKey().get_user_email(),en.getValue());
        }
        //end
        return new StoreManagersInfo(this.name, emails_appointmets);
    }
    public boolean is_active() {
        return this.active;
    }


    public void set_permissions(User user_who_set_permission, User manager, LinkedList<StorePermission> permissions) throws MarketException {
        // check that the manager appointed by the user
        this.check_permission(user_who_set_permission, StorePermission.edit_permissions); //TODO: verify
        if (!this.get_appointer(manager).equals(user_who_set_permission))
            throw new AppointmentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager.equals(user_who_set_permission))
            throw new NoPremssionException("User cant change himself permissions");

        Appointment manager_permission = this.stuffs_and_appointments.get(manager);
        manager_permission.set_permissions(permissions);

    }

    public List<String> view_store_questions(User user) throws MarketException {
        this.check_permission(user, StorePermission.view_users_questions);
        return QuestionHandler.getInstance().view_buyers_to_store_questions(store_id);
    }

    public void add_question(String user, String question_message) {
        QuestionHandler.getInstance().add_buyer_question(question_message, user, store_id);
    }

    public void answer_question(User user, int question_id, String answer) throws MarketException {
        this.check_permission(user, StorePermission.view_users_questions);
        QuestionHandler.getInstance().answer_buyer_question(question_id, answer);
    }

    public StorePurchaseHistory view_store_purchases_history(User user) throws MarketException {
        this.check_permission(user, StorePermission.view_purchases_history);
        return this.purchases_history;
    }

    public StorePurchaseHistory admin_view_store_purchases_history() {
        return this.purchases_history;
    }

    // -- find product by ----------------------------------------------------------------------------------

    public List<Product> find_products_by_name(String product_name) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getName().equals(product_name)) {
                products.add(p);
            }
        }
        return products;

    }

    public List<Product> find_products_by_category(String category) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getCategory().equals(category)) {
                products.add(p);
            }
        }
        return products;
    }

    public List<Product> find_products_by_key_words(String key_words) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getKey_words().contains(key_words)) {
                products.add(p);
            }
        }
        return products;
    }
    // -----------------------------------------------------------------------------------------------------


    public void add_product(User user, String name, double price, String category, List<String> key_words, int quantity) throws MarketException {
        this.check_permission(user, StorePermission.add_item);
        if (quantity < 1)
            throw new ProductAddingException("quantity must be more then zero");
        int product_id = this.product_ids_counter.getAndIncrement();
        Product product = new Product(name, this.store_id, product_id, price, category, key_words);
        inventory.put(product, quantity);
    }

    public void delete_product(int product_id, User user) throws MarketException {
        Product product_to_remove = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.remove_item);
        inventory.remove(product_to_remove);
    }

    // -- edit product - Start ----------------------------------------------------------------------------------

    public void edit_product_name(User user, int product_id, String name) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_name);
        to_edit.setName(name);
    }

    public void edit_product_price(User user, int product_id, double price) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_price);
        to_edit.setPrice(price);
    }

    public void edit_product_category(User user, int product_id, String category) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_category);
        to_edit.setCategory(category);
    }

    public void edit_product_key_words(User user, int product_id, List<String> key_words) throws MarketException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user, StorePermission.edit_item_keywords);
        to_edit.setKey_words(key_words);
    }

    // -----------------------------------------------------------------------------------------------------


    public double check_available_products_and_calc_price(Basket basket) {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for (Product p : products_and_quantities.keySet()) {
            this.checkAvailablityAndGet(p.getProduct_id(), products_and_quantities.get(p)); }
        return basket.getTotal_price();
    }

    // check product is available - throws if no.
    public Product checkAvailablityAndGet(int product_id, int quantity) throws MarketException {
        Product p = this.getProduct_by_product_id(product_id);
        if (p == null) {
            throw new ProductAddingException("checkAvailablityAndGet: Product is not exist");
            //not suppose to happen
            //add to logger
        }
        int product_quantity = this.inventory.get(p);
        if (quantity <= product_quantity) {
            return p;
        }
        throw new ProductAddingException("Store.checkAvailablityAndGet: Product is not available");
    }

    /**
     * @param basket      we call this method with all the basket of a single cart
     * @param purchase_id the index from store controller
     * @return
     */
    public Purchase remove_basket_products_from_store(Basket basket, int purchase_id) throws MarketException{
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();

        for (Product p : products_and_quantities.keySet()) {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove < 0)
                throw new StoreMethodException("Store.remove_basket_products_from_store: product quantity :" + quantity_to_remove + "" +
                        " is more then available for product id :" + p.getProduct_id());
        }
        for (Product p : products_and_quantities.keySet()) {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove == 0)
                this.inventory.remove(p);
            else
                this.inventory.put(p, first_quantity - quantity_to_remove);
        }
        String buyer_email = basket.get_buyer_email();
        Map<Integer, Integer> p_ids_quantity = basket.get_productsIds_and_quantity();
        Map<Integer, Double> p_ids_price = this.get_product_ids_and_total_price(basket);
        Map<Integer, String> p_ids_name = basket.getProducts_and_names();

        Purchase purchase = new Purchase(p_ids_quantity, p_ids_price, p_ids_name);
        StorePurchase purchase_to_add = new StorePurchase(purchase, buyer_email, purchase_id);
        this.purchases_history.insert(purchase_to_add);
        return purchase;
    }

    public void add_owner(User appointer, User new_owner) throws MarketException {
        this.check_permission(appointer, StorePermission.add_owner);
        Appointment appointment = this.stuffs_and_appointments.get(new_owner);
        if (appointment != null) {
            throw new AppointmentException("User to appoint is already store member");
        }

        Appointment appointment_to_add = new Appointment(new_owner, appointer, this, StoreManagerType.store_owner);
        this.stuffs_and_appointments.put(new_owner, appointment_to_add);
    }

    public void add_manager(User appointer, User new_manager) throws MarketException {
        this.check_permission(appointer, StorePermission.add_manager);
        Appointment appointment = this.stuffs_and_appointments.get(new_manager);
        if (appointment != null) {
            throw new AppointmentException("User to appoint is already store member");
        }
        Appointment appointment_to_add = new Appointment(new_manager, appointer, this, StoreManagerType.store_manager);
        this.stuffs_and_appointments.put(new_manager, appointment_to_add);
    }

    public void remove_manager(User remover, User user_to_delete_appointment) throws MarketException {
        this.check_permission(remover, StorePermission.add_manager);
        Appointment appointment = this.stuffs_and_appointments.get(user_to_delete_appointment);
        if (appointment == null) {
            throw new AppointmentException("User to be removed is not stuff member of this store");
        }
        if (!appointment.is_manager()) {
            throw new AppointmentException("User to be removed is not owner/founder");
        }
        if (!appointment.getAppointer().equals(user_to_delete_appointment)) {
            throw new AppointmentException("User can not remove stuff member that is not appoint by him");
        }
        this.stuffs_and_appointments.remove(user_to_delete_appointment);
    }

    public void remove_owner(User remover, User user_to_delete_appointment) throws MarketException {
        this.check_permission(remover, StorePermission.add_manager);
        Appointment appointment = this.stuffs_and_appointments.get(user_to_delete_appointment);

        if (appointment == null) {
            throw new AppointmentException("User to be removed is not stuff member of this store");
        }

        if (!appointment.is_owner()) {
            throw new AppointmentException("User to be removed is not owner");
        }

        if (!appointment.getAppointer().equals(remover)) {
            throw new AppointmentException("User can not remove stuff member that is not appoint by him");
        }

        for (Appointment appointment1 : this.stuffs_and_appointments.values()) {
            if (appointment1.getAppointer().equals(user_to_delete_appointment)) {
                this.remove_owner(user_to_delete_appointment, appointment1.getMember());
            }
        }
        this.stuffs_and_appointments.remove(user_to_delete_appointment);
    }


    public Product getProduct_by_product_id(int product_id) throws ObjectDoesntExsitException{
        for (Product product : this.inventory.keySet()) {
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new ObjectDoesntExsitException("Store: Product is not exist - product id: " + product_id);
    }


    // -- Private Methods

    // -- Getters ------------------------------------------------------------------------

    public String getFoundation_date() {
        return foundation_date;
    }

    public DiscountPolicy getDiscount_policy() {
        return discountPolicy;
    }

    public StoreReview getStoreReview() {
        return storeReview;
    }

    public HashMap<Product, Integer> getInventory() {
        return this.inventory;
    }

    public StorePurchaseHistory getPurchase_history() {
        return purchases_history;
    }

    public int getStore_id() {
        return store_id;
    }

    public PurchasePolicy getPurchase_policy() {
        return purchasePolicy;
    }

    public String getName() {
        return name;
    }


//---------------------------------------------------------------------- Setters - Start ------------------------------------------------------------------------------------


    public void setName(String name) {
        this.name = name;
    }

    public void setInventory(HashMap<Product, Integer> inventory) {
        this.inventory = inventory;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPurchasePolicy(User user, PurchasePolicy purchasePolicy) throws NoPremssionException {
        check_permission(user, StorePermission.edit_purchase_policy);
        this.purchasePolicy = purchasePolicy;
    }

    public void setDiscountPolicy(User user, DiscountPolicy discountPolicy) throws NoPremssionException {
        check_permission(user, StorePermission.edit_discount_policy);
        this.discountPolicy = discountPolicy;
    }


    public void check_permission(User user, StorePermission permission) throws IllegalAccessException {
        if (!this.stuffs_and_appointments.containsKey(user))
            throw new IllegalAccessException("user is no a store member");
        boolean flag = this.stuffs_and_appointments.get(user).has_permission(permission);
        if (!flag)
            throw new IllegalAccessException("User has no permissions!");
    }

    public Map<Integer, Double> get_product_ids_and_total_price(Basket basket) {
        Map<Integer, Double> productsIds_and_totalPrice = new HashMap<>();
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for (Product p : products_and_quantities.keySet()) {
            int quantity = products_and_quantities.get(p);
            productsIds_and_totalPrice.put(p.getProduct_id(), this.calc_product_price(p, quantity));
        }
        return productsIds_and_totalPrice;
    }

    public User get_appointer(User manager) {
        Appointment appointment = this.stuffs_and_appointments.get(manager);
        if (appointment == null)
            throw new IllegalArgumentException("is not a manager");
        return this.stuffs_and_appointments.get(manager).getAppointer();
    }

    //TODO: pass
    public Double calc_product_price(Product product, int quantity) {
        //TODO :discount policy - version 2
        return product.getPrice() * quantity;
    }

    public void send_message_to_the_store_stuff(String message) throws Exception {
        for (User user : this.stuffs_and_appointments.keySet()) {
            if (!user.equals(founder))
                QuestionHandler.getInstance().add_system_question(message, user.get_user_email());
        }
    }

}



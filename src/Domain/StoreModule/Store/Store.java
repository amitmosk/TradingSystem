package Domain.StoreModule.Store;

import Domain.Communication.Question;
import Domain.Communication.QuestionHandler;
import Domain.StoreModule.*;
import Domain.StoreModule.Policy.DiscountPolicy;
import Domain.StoreModule.Policy.PurchasePolicy;
import Domain.StoreModule.Product.Product;
import Domain.StoreModule.Purchase.StorePurchase;
import Domain.StoreModule.Purchase.StorePurchaseHistory;
import Domain.Utils.Utils;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class Store implements iStore {

    // -- fields

    private final int store_id;
    private final String founder_email;
    private HashMap<String, Appointment> stuff_emails_and_appointments;
    private String name;
    private final LocalDate foundation_date;
    private HashMap<Product, Integer> inventory; // product & quantity
    private boolean active;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;
    private StorePurchaseHistory purchases_history;
    private StoreReview storeReview;
    private AtomicInteger product_ids_counter;


    // -- constructors

    public Store(int store_id, String founder_email, String name) {
        this.store_id = store_id;
        this.founder_email = founder_email;
        this.name = name;
        this.product_ids_counter = new AtomicInteger(1);
        this.active = true;
        this.foundation_date = LocalDate.now();
        this.storeReview = new StoreReview();
        this.purchases_history = new StorePurchaseHistory();
        this.inventory = new HashMap<>();
        this.stuff_emails_and_appointments = new HashMap<>();

    }


    // -- public methods


    public int get_store_rating(){
        return this.storeReview.getAvgRating();
    }
    @Override
    public void add_product_review(int product_id, String user_email, String review) {
        Product p = this.getProduct_by_product_id(product_id); //throws
        p.add_review(user_email, review);
    }
    @Override
    public void add_store_rating(String user_email, int rating) {
        if (this.stuff_emails_and_appointments.containsKey(user_email))
                throw new IllegalArgumentException("store members can't rate their store");
        this.storeReview.add_rating(user_email, rating);
    }
    @Override
    public void add_product_rating(String user_email, int product_id, int rate) {
        Product p = this.getProduct_by_product_id(product_id);//throws
        p.add_rating(user_email, rate);
    }
    @Override
    public void appoint_founder(){
        Appointment founder = new Appointment(founder_email, founder_email, store_id, StoreManagerType.store_founder);
        this.stuff_emails_and_appointments.put(founder_email, founder);
    }
    @Override
    public void close_store_permanently() {
        this.active = false;
        String message = "Store was closed permanently at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);

//        this.founder_email = null;
        this.stuff_emails_and_appointments = null;
    }



    @Override
    public void close_store_temporarily(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.close_store_temporarily);
        this.active = false;
        String message = "Store was closed close_store_temporarily at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);
    }
    @Override
    public void open_close_store(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.open_close_store);
        if (this.is_active())
            throw new IllegalArgumentException("The store is already open");
        this.active = true;
        String message = "Store was re-open at " + LocalDate.now().toString();
        this.send_message_to_the_store_stuff(message);
    }

    @Override
    public StoreManagersInfo view_store_management_information(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_permissions);
        return new StoreManagersInfo(this.name ,this.stuff_emails_and_appointments);
    }
    @Override
    public boolean is_active() {
        return this.active;
    }
    @Override
    public void set_permissions(String user_email, String manager_email, LinkedList<StorePermission> permissions) throws IllegalAccessException {
        // check that the manager appointed by the user
        this.check_permission(user_email, StorePermission.edit_permissions);
        if (!this.get_appointer(manager_email).equals(user_email))
            throw new IllegalArgumentException("The manager is not appointed by user");
        // check that the user is not trying to change his permissions
        if (manager_email.equals(user_email))
            throw new IllegalArgumentException("User cant change himself permissions");

        Appointment manager_permission = this.stuff_emails_and_appointments.get(manager_email);
        manager_permission.set_permissions(permissions);

    }
    @Override
    public List<String> view_store_questions(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_users_questions);
        return QuestionHandler.getInstance().view_store_questions(store_id);
    }
    @Override
    public void add_question(String user_email, String question_message) {
        QuestionHandler.getInstance().add_buyer_question(question_message, user_email, store_id);
    }
    @Override
    public void answer_question(String user_email, int question_id, String answer) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_users_questions);
        QuestionHandler.getInstance().answer_buyer_question(question_id, answer);

    }
    @Override
    public String view_store_purchases_history(String user_email) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.view_purchases_history);
        return this.purchases_history.toString();
    }

    // -- find product by ----------------------------------------------------------------------------------

    @Override
    public List<Product> find_products_by_name(String product_name) {
        List<Product> products = new ArrayList<>();
        for (Product p : inventory.keySet()) {
            if (p.getName().equals(product_name))
            {
                products.add(p);
            }
        }
        return products;

    }
    @Override
    public List<Product> find_products_by_category(String category) {
        List<Product> products = new ArrayList<>();
        for (Product p:inventory.keySet()) {
            if (p.getCategory().equals(category))
            {
                products.add(p);
            }
        }
        return products;
    }
    @Override
    public List<Product> find_products_by_key_words(String key_words) {
        // TODO : String keywords -> List<String>
        List<Product> products = new ArrayList<>();
        for (Product p:inventory.keySet()) {
            if (p.getKey_words().contains(key_words))
            {
                products.add(p);
            }
        }
        return products;
    }
    // -----------------------------------------------------------------------------------------------------


    @Override
    public void add_product(String user_email, String name, double price, String category, List<String> key_words, int quantity) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_item);
        if (quantity < 0)
            throw new IllegalArgumentException("quantity must be more then zero");
        int product_id = this.product_ids_counter.getAndIncrement();
        Product product = new Product(name, this.store_id, product_id, price, category, key_words);
        inventory.put(product, quantity);
    }
    @Override
    public void delete_product(int product_id, String user_email) throws IllegalAccessException {
        Product product_to_remove = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.remove_item);
        inventory.remove(product_to_remove);
    }

    // -- edit product - Start ----------------------------------------------------------------------------------

    @Override
    public void edit_product_name(String user_email, int product_id, String name) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_name);
        to_edit.setName(name);
    }
    @Override
    public void edit_product_price(String user_email, int product_id, double price) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_price);
        to_edit.setPrice(price);
    }
    @Override
    public void edit_product_category(String user_email, int product_id, String category) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_category);
        to_edit.setCategory(category);
    }
    @Override
    public void edit_product_key_words(String user_email, int product_id, List<String> key_words) throws IllegalAccessException {
        Product to_edit = this.getProduct_by_product_id(product_id);
        this.check_permission(user_email, StorePermission.edit_item_keywords);
        to_edit.setKey_words(key_words);
    }

    // -----------------------------------------------------------------------------------------------------


    @Override
    public double check_available_products_and_calc_price(Basket basket) {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for (Product p : products_and_quantities.keySet())
        {
            this.checkAvailablityAndGet(p.getProduct_id(), products_and_quantities.get(p));
        }

        return basket.getTotal_price();
    }

    // check product is available - throws if no.
    public Product checkAvailablityAndGet(int product_id, int quantity) {
        Product p = this.getProduct_by_product_id(product_id);
        if (p == null)
        {
            throw new IllegalArgumentException("checkAvailablityAndGet: Product is not exist");
            //not suppose to happen
            //add to logger
        }
        int product_quantity = this.inventory.get(p);
        if (quantity <= product_quantity)
        {
            return p;
        }
        throw new IllegalArgumentException("Store.checkAvailablityAndGet: Product is not available");
    }
    @Override
    public void remove_basket_products_from_store(Basket basket, int purchase_id) {
        Map<Product, Integer> products_and_quantities = basket.getProducts_and_quantities();

        for (Product p : products_and_quantities.keySet())
        {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove < 0)
                throw new IllegalArgumentException("Store.remove_basket_products_from_store: product quantity :" + quantity_to_remove + "" +
                        " is more then available for product id :"+p.getProduct_id());
        }
        for (Product p : products_and_quantities.keySet())
        {
            int first_quantity = this.inventory.get(p);
            int quantity_to_remove = products_and_quantities.get(p);
            if (first_quantity - quantity_to_remove == 0)
                this.inventory.remove(p);
            else
                this.inventory.put(p, first_quantity - quantity_to_remove);
        }
        String buyer_email = basket.get_buyer_email();
        double price = basket.getTotal_price();
        Map<Integer,Integer> p_ids_quantity = basket.get_productsIds_and_quantity();
        Map<Integer,Double> p_ids_price = this.get_product_ids_and_total_price(basket);


        StorePurchase purchase = new StorePurchase(buyer_email, purchase_id, price,
                p_ids_quantity, p_ids_price);
        this.purchases_history.insert(purchase);

    }

    @Override
    public void add_owner(String user_email, String user_email_to_appoint) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_owner);
        Appointment appointment = this.stuff_emails_and_appointments.get(user_email_to_appoint);
        if (appointment != null)
        {
            throw new IllegalArgumentException("User to appoint is already store member");
        }

        Appointment appointment_to_add = new Appointment(user_email_to_appoint, user_email, this.store_id, StoreManagerType.store_owner);
        this.stuff_emails_and_appointments.put(user_email_to_appoint, appointment_to_add);
    }
    @Override
    public void add_manager(String user_email, String user_email_to_appoint) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_manager);
        Appointment appointment = this.stuff_emails_and_appointments.get(user_email_to_appoint);
        if (appointment != null)
        {
            throw new IllegalArgumentException("User to appoint is already store member");
        }
        Appointment appointment_to_add = new Appointment(user_email_to_appoint, user_email, this.store_id, StoreManagerType.store_manager);
        this.stuff_emails_and_appointments.put(user_email_to_appoint, appointment_to_add);
    }
    @Override
    public void remove_manager(String user_email, String user_email_to_delete_appointment) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_manager);
        Appointment appointment = this.stuff_emails_and_appointments.get(user_email_to_delete_appointment);
        if (appointment == null)
        {
            throw new IllegalArgumentException("User to be removed is not stuff member of this store");
        }
        if (!appointment.is_manager())
        {
            throw new IllegalArgumentException("User to be removed is not owner/founder");
        }
        if (!appointment.getAppointer_email().equals(user_email))
        {
            throw new IllegalArgumentException("User can not remove stuff member that is not appoint by him");
        }
        this.stuff_emails_and_appointments.remove(user_email_to_delete_appointment);
    }
    @Override
    public void remove_owner(String user_email, String user_email_to_delete_appointment) throws IllegalAccessException {
        this.check_permission(user_email, StorePermission.add_manager);
        Appointment appointment = this.stuff_emails_and_appointments.get(user_email_to_delete_appointment);

        if (appointment == null)
        {
            throw new IllegalArgumentException("User to be removed is not stuff member of this store");
        }

        if (!appointment.is_owner())
        {
            throw new IllegalArgumentException("User to be removed is not owner");
        }

        if (!appointment.getAppointer_email().equals(user_email))
        {
            throw new IllegalArgumentException("User can not remove stuff member that is not appoint by him");
        }

        for (Appointment appointment1 : this.stuff_emails_and_appointments.values())
        {
            if (appointment1.getAppointer_email().equals(user_email_to_delete_appointment))
            {
                this.remove_owner(user_email_to_delete_appointment, appointment1.getMember_email());
            }
        }
        this.stuff_emails_and_appointments.remove(user_email_to_delete_appointment);
    }



    @Override
    public Product getProduct_by_product_id(int product_id) {
        for (Product product : this.inventory.keySet()){
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new IllegalArgumentException("Store: Product is not exist - product id: ");
    }



    // -- Private Methods

    private void check_permission(String user_email, StorePermission permission) throws IllegalAccessException {
        if (!this.stuff_emails_and_appointments.containsKey(user_email))
            throw new IllegalAccessException("user is no a store member");
        boolean flag = this.stuff_emails_and_appointments.get(user_email).has_permission(permission);
        if(!flag)
            throw new IllegalAccessException("User has no permissions!");
    }
    private Map<Integer, Double> get_product_ids_and_total_price(Basket basket) {
        Map<Integer, Double> productsIds_and_totalPrice = new HashMap<>();
        Map <Product, Integer> products_and_quantities = basket.getProducts_and_quantities();
        for(Product p: products_and_quantities.keySet())
        {
            int quantity = products_and_quantities.get(p);
            productsIds_and_totalPrice.put(p.getProduct_id(), this.calc_product_price(p, quantity));
        }
        return productsIds_and_totalPrice;
    }

    private String get_appointer(String manager_email) {
        Appointment appointment = this.stuff_emails_and_appointments.get(manager_email);
        if (appointment == null)
            throw new IllegalArgumentException("is not a manager");
        return this.stuff_emails_and_appointments.get(manager_email).getAppointer_email();
    }


    private Double calc_product_price(Product product, int quantity) {
        //TODO :discount policy - version 2
        return product.getPrice() * quantity;
    }

    private void send_message_to_the_store_stuff(String message) {
        for (String email : this.stuff_emails_and_appointments.keySet()) {
            if (!email.equals(founder_email))
                QuestionHandler.getInstance().add_system_question(message, email);
        }
    }


    // -- Getters ------------------------------------------------------------------------

    public LocalDate getFoundation_date() {
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

    public String getFounder_email() {
        return founder_email;
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

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

}



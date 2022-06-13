package TradingSystem.server.Domain.StoreModule;


import TradingSystem.server.Domain.StoreModule.Bid.BidInformation;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountPolicy;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.PurchasePolicy;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Bid.Bid;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.Cart;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.Utils.SystemLogger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreController {
    private ConcurrentHashMap<Integer, Store> stores;
    private AtomicInteger store_ids_counter;
    private AtomicInteger purchase_ids_counter;
    private AtomicInteger bids_ids_counter;
    private AtomicInteger products_id;
    private Object storesLock;

    private static StoreController instance = null;

    public static StoreController get_instance() {
        if (instance == null)
            instance = new StoreController();
        return instance;
    }

    private StoreController() {
        this.store_ids_counter = new AtomicInteger(1);
        this.purchase_ids_counter = new AtomicInteger(1);
        this.bids_ids_counter = new AtomicInteger(1);
        this.stores = new ConcurrentHashMap<>();
        this.storesLock = new Object();
        this.products_id = new AtomicInteger(1);
    }

    public static void load() {
        SystemLogger.getInstance().add_log("store controller load");
    }


    /**
     * @param user     to check if the user allowed to change policiy
     * @param store_id id for the store
     * @param policy   the rules to set
     * @return string that says if the setting worked
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public void set_store_purchase_policy(int store_id, User user, PurchasePolicy policy) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        store.setPurchasePolicy(assignUser, policy);
    }

    /**
     * @param user     to check if the user allowed to change policiy
     * @param store_id id for the store
     * @param policy   the rules to set
     * @return string that says if the setting worked
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public void set_store_discount_policy(int store_id, User user, DiscountPolicy policy) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        store.setDiscountPolicy(assignUser, policy);
    }

    /**
     * @param store_id represent the store we asked to close
     * @param user     the user who asked to close the store
     * @return false if the store was already close, and true if we close the store temporarily
     * @throws if the user is not store founder OR the store or the user are not exist.
     */
    public void close_store_temporarily(User user, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        store.close_store_temporarily(assignUser);
    }

    /**
     * @param store_id represent the store we asked to re-open
     * @param user     the user who asked to re-open the store
     * @return false if the store was already open, and true if the store were re-open
     * @throws if the user is not store founder OR the store or the user are not exist.
     */
    public void open_close_store(User user, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        if (!this.stores.containsKey(store_id)) {
            throw new ObjectDoesntExsitException("The store is not exist - store id: " + store_id);
        }
        Store store = this.stores.get(store_id);
        store.open_close_store(assignUser);
    }

    /**
     * @param user        the user who ask to change the permissions.
     * @param manager     the user who we ask to change his permissions.
     * @param store_id    this method is according specific store.
     * @param permissions a list with all the permissions that we would like give the user.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalArgumentException the manager isn't appointed by user,
     * @throws IllegalArgumentException if the user asking change his own permissions.
     */
    public void edit_manager_specific_permissions(User user, User manager, int store_id, List<StorePermission> permissions) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        AssignUser assignManager = manager.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        store.set_permissions(assignUser, assignManager, permissions);
    }

    /**
     * @param user     who ask to view store information,
     * @param store_id information of a specific store,
     * @return an object with managers & permissions data.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public StoreManagersInfo view_store_management_information(User user, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        StoreManagersInfo info = store.view_store_management_information(assignUser);
        return info;
    }

    /**
     * @param store_id questions from a specific store,
     * @param user     who ask to view store questions,
     * @return an object with store's questions.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public List<String> view_store_questions(User user, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        return store.view_store_questions(assignUser);
    }

    /**
     * @param store_id
     * @param user        the manager who wants to answer the question
     * @param question_id a specific question that the user get from view_store_questions
     * @param answer      the answer of the store manager to the user question.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public void answer_question(User user, int store_id, int question_id, String answer) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        store.answer_question(assignUser, question_id, answer);
    }

    /**
     * @param store_id the store that we want to get all the purchases history
     * @param user     the manager
     * @return a list with all the purchases history
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public StorePurchaseHistory view_store_purchases_history(User user, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        return store.view_store_purchases_history(assignUser);
    }

    /**
     * @param store_id the store who have to close permanently
     * @return true if the store was open and now we close it
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException   the user doesn't have the relevant permission.
     */
    public void close_store_permanently(int store_id) throws MarketException {
        Store store = this.get_store_by_store_id(store_id);
        store.close_store_permanently();
    }

    /**
     * @param store_id - for one store
     * @return The store object
     * @throws IllegalArgumentException if the store not exist
     * @throws IllegalArgumentException if store is not active
     */
    private Store get_store_by_store_id(int store_id) throws StoreException {
        if (!this.stores.containsKey(store_id)) {
            throw new StoreException("The store is not exist - store id: " + store_id);
        } else if (!this.stores.get(store_id).is_active()) {
            throw new StoreException("The store is not active - store id: " + store_id);
        }
        return this.stores.get(store_id);

    }

    /**
     * @param store_id
     * @return store information
     * @throws if                       the store not exist
     * @throws IllegalArgumentException if store does not exist
     * @throws IllegalArgumentException if store isn't active
     */
    public Store find_store_information(int store_id) throws MarketException {
        Store store = this.get_store_by_store_id(store_id);
        return store;
    }

    /**
     * @param product_id
     * @param store_id
     * @return product information
     * @throws IllegalArgumentException if store is not exist
     * @throws IllegalArgumentException if store is not active
     */
    public Product find_product_information(int product_id, int store_id) throws MarketException {
        Store s = get_store_by_store_id(store_id);
        return s.getProduct_by_product_id(product_id);


    }


    //------------------------------------------------find product by - Start ----------------------------------------------------
    public List<Product> find_products_by_name(String product_name) {
        List<Product> to_return = new ArrayList<>();
        for (Store store : stores.values()) {
            if (store.is_active()) {
                List<Product> products_from_store = store.find_products_by_name(product_name);
                to_return.addAll(products_from_store);

            }
        }
        return to_return;

    }

    public List<Product> find_products_by_category(String category) {
        List<Product> to_return = new ArrayList<>();
        for (Store store : stores.values()) {
            if (store.is_active()) {
                List<Product> products_from_store = store.find_products_by_category(category);
                to_return.addAll(products_from_store);
            }
        }
        return to_return;
    }

    public List<Product> find_products_by_key_words(String key_words) {
        List<Product> to_return = new ArrayList<>();
        for (Store store : stores.values()) {
            if (store.is_active()) {
                List<Product> products_from_store = store.find_products_by_key_words(key_words);
                to_return.addAll(products_from_store);
            }
        }
        return to_return;
    }
    //------------------------------------------------find product by - End ----------------------------------------------------


    public Map<Product,Integer> add_product_to_store(User user, int store_id, int quantity, String name, double price, String category, List<String> key_words)
            throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        return store.add_product(assignUser, name, price, category, key_words, quantity);
    }

    public Map<Product, Integer> delete_product_from_store(User user, int product_id, int store_id) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        return store.delete_product(product_id, assignUser);
    }

    //------------------------------------------------ edit product - Start ----------------------------------------------
    public void edit_product_name(User user, int product_id, int store_id, String name) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id); //trows exceptions
        store.edit_product_name(assignUser, product_id, name);
    }

    public void edit_product_price(User user, int product_id, int store_id, double price) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        store.edit_product_price(assignUser, product_id, price);
    }

    public void edit_product_category(User user, int product_id, int store_id, String category) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        store.edit_product_category(assignUser, product_id, category);
    }

    public void edit_product_key_words(User user, int product_id, int store_id, List<String> key_words) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id);
        store.edit_product_key_words(assignUser, product_id, key_words);
    }

    //------------------------------------------------ edit product - End ----------------------------------------------

//    public double check_cart_available_products_and_calc_price(Cart cart) throws MarketException {
//        Map<Store, Basket> baskets_of_storesID = cart.getBaskets();
//        double cart_price = 0;
//        for (Basket basket : baskets_of_storesID.values()) {
//            int store_id = basket.getStore_id();
//            if (stores.containsKey(store_id)) {
//                double basket_price = stores.get(store_id).check_available_products_and_calc_price(basket); // throw if not available
//                cart_price += basket_price;
//            } else {
//                throw new ObjectDoesntExsitException("Store does not exist");
//                //not suppose to happen
//            }
//        }
//        return cart_price;
//    }

    public Product checkAvailablityAndGet(int store_id, int product_id, int quantity) throws MarketException {
        if (!stores.containsKey(store_id)) {
            throw new StoreException("StoreController:checkAvailablityAndGet - Store does not exist , store id: " + store_id);
        }
        return stores.get(store_id).checkAvailablityAndGet(product_id, quantity);
    }


    /**
     * @param cart with all the items we should remove from inventory
     * @return Map with stores id and purchase - for adding to user purchase history
     */
    public Map<Integer, Purchase> update_stores_inventory(Cart cart) throws MarketException {
        Map<Integer, Purchase> store_id_purchase = new HashMap<>();
        Map<Store, Basket> baskets_of_storesID = cart.getBaskets();
        for (Basket basket : baskets_of_storesID.values()) {
            int store_id = basket.getStore_id();
            if (!stores.containsKey(store_id)) {
                throw new ObjectDoesntExsitException("Store does not exist - store id: " + store_id);
            }
        }
        for (Basket basket : baskets_of_storesID.values()) {
            int store_id = basket.getStore_id();
            Purchase purchase = this.stores.get(store_id).remove_basket_products_from_store(basket, this.purchase_ids_counter.getAndIncrement());
            store_id_purchase.put(store_id, purchase);
        }
        return store_id_purchase;
    }

    public int open_store(User founder, String store_name) throws MarketException {
        AssignUser founder_state = founder.state_if_assigned();
        int store_id = this.store_ids_counter.getAndIncrement();
        Store store = new Store(store_id, store_name, founder_state,products_id);
        Appointment appointment = store.appoint_founder();
        founder.add_founder(store, appointment);
        this.stores.put(store_id, store);
        return store_id;
    }

    public void add_review(String user_email, int product_id, int store_id, String review) throws MarketException {
        Store store = this.get_store_by_store_id(store_id);//throws
        store.add_product_review(product_id, user_email, review);

    }

    public void rate_product(String user_email, int product_id, int store_id, int rate) throws MarketException {
        Store store = this.get_store_by_store_id(store_id); //throws
        store.add_product_rating(user_email, product_id, rate);
    }

    public void rate_store(User user, int store_id, int rate) throws MarketException {
        AssignUser user_state = user.state_if_assigned();
        Store to_rate = this.get_store_by_store_id(store_id);//throw exceptions
        to_rate.add_store_rating(user_state, rate);
    }

    public StorePurchaseHistory admin_view_store_purchases_history(int store_id) throws MarketException {
        Store store = this.get_store_by_store_id(store_id);
        return store.admin_view_store_purchases_history();
    }

    public void add_owner(User appointer, User user_to_appoint, int store_id) throws MarketException {
        AssignUser appointer_state = appointer.state_if_assigned();
        AssignUser to_appoint_state = user_to_appoint.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);//throws
        store.add_owner(appointer_state, to_appoint_state);
    }

    public void add_manager(User appointer, User user_to_appoint, int store_id) throws MarketException {
        AssignUser appointer_state = appointer.state_if_assigned();
        AssignUser to_appoint_state = user_to_appoint.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);//throws
        store.add_manager(appointer_state, to_appoint_state);
    }

    public void remove_manager(User remover, User user_to_delete_appointment, int store_id) throws MarketException {
        AssignUser remover_state = remover.state_if_assigned();
        AssignUser to_remove_state = user_to_delete_appointment.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);//throws
        store.remove_manager(remover_state, to_remove_state);
    }

    public void remove_owner(User remover, User user_to_delete_appointment, int store_id) throws MarketException {
        AssignUser remover_state = remover.state_if_assigned();
        AssignUser to_remove_state = user_to_delete_appointment.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);//throws
        store.remove_owner(remover_state, to_remove_state);
    }

    public void add_question(User user, int store_id, String question) throws MarketException {
        AssignUser user_state = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        store.add_question(user_state, question);
    }

    public Product getProduct_by_product_id(int storeID, int productID) throws MarketException {
        Store store = this.get_store_by_store_id(storeID);
        return store.getProduct_by_product_id(productID);
    }

    public Store get_store(int store_id) throws MarketException {
        if (!this.stores.containsKey(store_id))
            throw new ObjectDoesntExsitException("there is no such store");
        return stores.get(store_id);
    }

    public void clear() {
        this.store_ids_counter = new AtomicInteger(1);
        this.purchase_ids_counter = new AtomicInteger(1);
        this.stores = new ConcurrentHashMap<>();
        this.storesLock = new Object();
        this.products_id = new AtomicInteger(1);
    }

    public Map<Integer,Store> get_all_stores() {
        return this.stores;
    }

    public List<Product> get_products_by_store_id(int store_id) throws MarketException {
        Store store = this.get_store_by_store_id(store_id);
        List<Product> to_return=new LinkedList<>();
        for(Product p:store.getInventory().keySet())
        {
            to_return.add(p);
        }
        return to_return;
    }

    public void edit_product_quantity(User user, int product_id, int store_id, int quantity) throws MarketException {
        AssignUser assignUser = user.state_if_assigned();
        Store store = get_store_by_store_id(store_id); //trows exceptions
        store.edit_product_quantity(assignUser, product_id, quantity);
    }

    public List<BidInformation> view_bids_status(int store_id, User user) throws Exception {
        AssignUser user_state = user.state_if_assigned();
        Store store = this.get_store_by_store_id(store_id);
        return store.view_bids_status(user_state);
    }

    public void manager_answer_bid(int storeID, User user, boolean manager_answer, int bidID,
                                   double negotiation_price) throws Exception {
        Store store = get_store_by_store_id(storeID);
        store.add_bid_answer(user, manager_answer, bidID, negotiation_price);
    }


    public int add_bid_offer(int productID, int storeID, int quantity, double offer_price, User buyer)
            throws Exception {
        Store store = get_store_by_store_id(storeID);
        Product product = checkAvailablityAndGet(storeID, productID, quantity);
        int bid_id = this.bids_ids_counter.getAndIncrement();
        return store.add_bid_offer(bid_id, product, quantity, offer_price, buyer);
    }

    public List<String> get_permissions(String manager_email, int store_id) throws StoreException, AppointmentException {
        Store store = get_store_by_store_id(store_id); //trows exceptions
        return store.get_permissions(manager_email);
    }

    public List<String> get_all_categories(int store_id) throws StoreException {
        Store store = get_store_by_store_id(store_id);
        return store.get_all_categories();
    }

    public int getProduct_ids_counter() {
        return products_id.get();
    }

}


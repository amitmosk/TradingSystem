package Domain.StoreModule;

import Domain.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class StoreController {
    private HashMap<Integer, Store> stores;
    private int store_ids;
    private int purchase_ids;
    private static StoreController instance = null;

    public static StoreController get_instance()
    {
        if (instance == null)
            instance = new StoreController();
        return instance;
    }
    private StoreController() {
        this.store_ids = 1;
        this.purchase_ids = 1;
        this.stores = new HashMap<Integer, Store>();
    }

    private int getInc_purchase_ids() {
        // TODO - incerment
        return this.purchase_ids++;
    }

    private int getInc_store_id() {
        // TODO: atomic integer
        this.store_ids++;
        return this.store_ids;
    }

    /**
     *
     * @param store_id represent the store we asked to close
     * @param user_id the user who asked to close the store
     * @throws if the user is not store founder OR the store or the user are not exist.
     * @return false if the store was already close, and true if we close the store temporarily
     */
    public boolean close_store_temporarily(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        return store.close_store_temporarily(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param store_id represent the store we asked to re-open
     * @param user_id the user who asked to re-open the store
     * @throws if the user is not store founder OR the store or the user are not exist.
     * @return false if the store was already open, and true if the store were re-open
     */
    public boolean open_close_store(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        return store.open_close_store(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param user_id the user who ask to change the permissions.
     * @param manager_id the user who we ask to change his permissions.
     * @param store_id this method is according specific store.
     * @param permissions a list with all the permissions that we would like give the user.
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalArgumentException the manager isn't appointed by user,
     * @throws IllegalArgumentException if the user asking change his own permissions.
     */
    public void edit_manager_specific_permissions(int user_id, int manager_id, int store_id, LinkedList<StorePermission> permissions){
        Store store = this.get_store_by_store_id(store_id);
        store.set_permissions(user_id, manager_id, permissions);
        // TODO: 22/04/2022 : update DB @ write to logger

    }

    /**
     *
     * @param user_id who ask to view store information,
     * @param store_id information of a specific store,
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return an object with managers & permissions data.
     */
    public StoreManagersInfo view_store_management_information(int user_id, int store_id) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        return store.view_store_management_information(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id questions from a specific store,
     * @param user_id who ask to view store questions,
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return an object with store's questions.
     */
    public HashMap<Integer, Question> view_store_questions(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        return store.view_store_questions(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id
     * @param user_id the manager who wants to answer the question
     * @param question_id a specific question that the user get from view_store_questions
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @param answer the answer of the store manager to the user question.
     */
    public void answer_question(int store_id, int user_id, int question_id, String answer) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        store.answer_question(store_id, user_id, question_id, answer);
        // TODO: 22/04/2022 : write to logger & DB
    }

    /**
     *
     * @param store_id the store that we want to get all the purchases history
     * @param user_id the manager
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return a list with all the purchases history
     */
    public String view_store_purchases_history(int store_id, int user_id) throws IllegalAccessException {
        Store store = this.get_store_by_store_id(store_id);
        return store.view_store_purchases_history(user_id);
        // TODO: 22/04/2022 : write to logger

    }

    /**
     *
     * @param store_id the store who have to close permanently
     * @param user_id admin
     * @throws IllegalArgumentException if the store not exist,
     * @throws IllegalAccessException the user doesn't have the relevant permission.
     * @return true if the store was open and now we close it
     */
    public boolean close_store_permanently(int store_id, int user_id)
    {
        // TODO: have to check that the user is admin
        Store store = this.get_store_by_store_id(store_id);
        return store.close_store_permanently(user_id);
        // TODO: 22/04/2022 : update DB @ write to logger
    }

    /**
     *
     * @param store_id
     * @throws IllegalArgumentException if the store not exist
     * @throws IllegalArgumentException if store is not active
     * @return The store
     */
    private Store get_store_by_store_id(int store_id) {
        if (!this.stores.containsKey(store_id))
        {
            throw new IllegalArgumentException("The store is not exist - store id: "+store_id);
        }
        else if (!this.stores.get(store_id).is_active())
        {
            throw new IllegalArgumentException("The store is not active - store id: "+store_id);
        }
        return this.stores.get(store_id);

    }

    /**
     *
     * @param store_id
     * @return store information
     * @throws if the store not exist
     */
    public String find_store_information(int store_id) throws IllegalArgumentException {
        Store store = this.get_store_by_store_id(store_id);
        return store.toString();
    }

    /**
     *
     * @param product_id
     * @param store_id
     * @return product information
     * @throws IllegalArgumentException if store is not exist
     * @throws IllegalArgumentException if store is not active
     */
    public String find_product_information(int product_id, int store_id) throws IllegalArgumentException {
        Store s = get_store_by_store_id(store_id); //throws exception
        return s.get_product_information(product_id);



    }


    private boolean is_product_exist(int product_id, int store_id)
    {
        Store s = get_store_by_store_id(store_id); //throws exception
        return s.is_product_exist(product_id, store_id);
    }


    //------------------------------------------------find product by - Start ----------------------------------------------------
    public List<Product> find_products_by_name(String product_name) {
        List<Product> to_return = new ArrayList<>();
        for (Store store:stores.values()) {
            if (store.is_active())
            {
                List<Product> products_from_store = store.find_products_by_name(product_name);
                to_return.addAll(products_from_store);

            }
        }
        return to_return;

    }
    public List<Product> find_products_by_category(String category) {
        List<Product> to_return = new ArrayList<>();
        for (Store store:stores.values()) {
            if (store.is_active())
            {
                List<Product> products_from_store = store.find_products_by_category(category);
                to_return.addAll(products_from_store);
            }
        }
        return to_return;
    }
    public List<Product> find_products_by_key_words(String key_words) {
        List<Product> to_return = new ArrayList<>();
        for (Store store:stores.values()) {
            if (store.is_active())
            {
                List<Product> products_from_store = store.find_products_by_key_words(key_words);
                to_return.addAll(products_from_store);
            }
        }
        return to_return;
    }
    //------------------------------------------------find product by - End ----------------------------------------------------


    public void add_product_to_store(int user_id, int store_id, int quantity, String name, double price, String category, List<String> key_words)
            throws IllegalArgumentException, IllegalAccessException {
        Store store = get_store_by_store_id(store_id);
        store.add_product(user_id, name, price, category, key_words, quantity);
    }

    public void delete_product_from_store(int user_id, int product_id, int store_id) throws IllegalAccessException {
        Store store = get_store_by_store_id(store_id);
        store.delete_product(product_id, user_id);
    }
    //------------------------------------------------ edit product - Start ----------------------------------------------
    public void edit_product_name(int user_id, int product_id, int store_id, String name) throws IllegalAccessException {
        Store store = get_store_by_store_id(store_id); //trows exceptions
        store.edit_product_name(user_id, product_id, name);
    }

    public void edit_product_price(int user_id, int product_id, int store_id, double price) throws IllegalAccessException {
        Store store = get_store_by_store_id(store_id);
        store.edit_product_price(user_id, product_id, price);
    }

    public void edit_product_category(int user_id, int product_id, int store_id, String category) throws IllegalAccessException {
        Store store = get_store_by_store_id(store_id);
        store.edit_product_category(user_id, product_id, category);
    }

    public void edit_product_key_words(int user_id, int product_id, int store_id, List<String> key_words) throws IllegalAccessException {
        Store store = get_store_by_store_id(store_id);
        store.edit_product_key_words(user_id, product_id, key_words);
    }

    //------------------------------------------------ edit product - End ----------------------------------------------

    public double check_cart_available_products_and_calc_price(Cart cart) {
        HashMap<Integer, Basket> baskets_of_storesID = cart.get_baskets();
        double cart_price=0;
        for (Basket basket : baskets_of_storesID.values()){

            int store_id = basket.getStore_id();
            if(stores.containsKey(store_id))
            {

                double basket_price = stores.get(store_id).check_available_products_and_calc_price(basket); // throw if not available
                cart_price += basket_price;
            }
            else
            {
                throw new IllegalArgumentException("Store does not exist - store id: "+ store_id);
                //not suppose to happen
            }

        }
        return cart_price;

    }
    public Product get_product_by_product_id(int product_id, int store_id)
    {
        Store s = this.get_store_by_store_id(store_id); // throws exception if store does not exist
        Product p = s.getProduct_by_product_id(product_id);
        if (p!=null)
        {
            return p;
        }
        throw new IllegalArgumentException("StoreController:get_product_by_product_id - Product does not exist product_id: "+product_id);
    }

    public Product checkAvailablityAndGet(int store_id, int product_id, int quantity)
    {
        if(!stores.containsKey(store_id))
        {
            throw new IllegalArgumentException("StoreController:checkAvailablityAndGet - Store does not exist , store id: "+store_id);
        }
        return  stores.get(store_id).checkAvailablityAndGet(product_id, quantity);
    }

    // TODO: remove the items from store that were bought by the user
    public void update_stores_inventory(Cart cart) {
        HashMap<Integer, Basket> baskets_of_storesID = cart.get_baskets();
        for (Basket basket : baskets_of_storesID.values())
        {
            int store_id = basket.getStore_id();
            if(!stores.containsKey(store_id))
            {
                throw new IllegalArgumentException("Store does not exist - store id: "+ store_id);
            }
        }
        for (Basket basket : baskets_of_storesID.values())
        {
            int store_id = basket.getStore_id();
            this.stores.get(store_id).remove_basket_products_from_store(basket, this.getInc_purchase_ids());
        }

        // TODO: create StorePurchase add the purchase to StorePurchaseHistory
    }


    public void open_store(int founder_id, String store_name) {
        int store_id = this.getInc_store_id();
        Store store = new Store(store_id, founder_id, store_name);
        this.stores.put(store_id, store);
    }

    public void add_review(int user_id, int product_id, int store_id, String review) {
        Product p = this.get_product_by_product_id(product_id, store_id);//throws exceptions
        p.add_review(user_id, review);
    }
}


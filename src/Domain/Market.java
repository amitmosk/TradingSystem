package Domain;

import Domain.StoreModule.*;
import Service.iService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Market implements iService {
    private StoreController store_controller;

    public Market() {
        this.store_controller = StoreController.get_instance();
    }

    //@TODO : all the exceptions in form: Basket.RemoveProduct: (class.method) - with specific data
    // TODO: before get store check that the store is open
    // TODO: all the Controllers ids counters -> AtomicIntegers - getInc
    @Override
    public void init_market() {
        //Tom
        //connect to payment service
        //connect to supply service
        // load
    }

    @Override
    public boolean payment(int price) {
        //Tom
        return true;
    }

    @Override
    public boolean supply(int user_id, int purchase_id) {
        //Tom
        return true;

    }

    @Override
    public double guest_login() {
        return 0;
    }

    @Override
    public double login(String username, String password) {
        return 0;
    }

    @Override
    public double logout() {
        return 0;
    }

    @Override
    public double register() {
        return 0;
    }

    @Override

    public String find_store_information(int store_id) {
        String info="";
        try
        {
            info = this.store_controller.find_store_information(store_id);
        }
        catch (IllegalArgumentException e)
        {
            return "Store does not exist in the market";
        }
        return info;

    }

    @Override

    public String find_product_information(int product_id) {
        String info="";
        try
        {
            info = this.store_controller.find_product_information(product_id);
        }
        catch (IllegalArgumentException e)
        {
            return "Product does not exist in the market";
        }
        return info;
    }

    /**
     *
     * @param product_name
     * @return Product if exist by the identifier
     * @throws if Product does not exist
     */
    @Override
    public Product find_product_by_name(String product_name) throws IllegalArgumentException{
        Product p = this.store_controller.find_product_by_name(product_name);
        if (p==null)
        {
            throw new IllegalArgumentException("Product does not exist - product name: "+product_name);
        }
        return p;
    }

    /**
     *
     * @param category
     * @return Product if exist by the identifier
     * @throws if Product does not exist
     */
    @Override
    public Product find_product_by_category(String category) throws IllegalArgumentException{
        Product p = this.store_controller.find_product_by_category(category);
        if (p==null)
        {
            throw new IllegalArgumentException("Product does not exist - product category: "+category);
        }
        return p;
    }

    /**
     *
     * @param key_word
     * @return Product if exist by the identifier
     * @throws if Product does not exist
     */
    @Override
    public Product find_product_by_keyword(String key_word) throws IllegalArgumentException{
        Product p = this.store_controller.find_product_by_keyword(key_word);
        if (p==null)
        {
            throw new IllegalArgumentException("Product does not exist - product key word: "+key_word);
        }
        return p;
    }

    @Override
    public double view_user_cart() {
        return 0;
    }

    @Override
    public double delete_product_from_cart() {
        return 0;
    }

    @Override
    public double add_product_to_cart() {
        return 0;
    }

    @Override
    public double change_product_quantity_in_cart() {
        return 0;
    }

    /**
     *
     * @return
     */
    @Override
    public int buy_cart() {
        // get information about the payment & supply
        Cart cart = this.user_controller.get_cart(this.user_id);
        double total_price = this.store_controller.check_cart_available_products_and_calc_price(cart);
        this.payment(total_price, paymentInfo);
        this.supply(supplyInfo);
        // success
        // acquire lock of : edit/delete product, both close_store, discount & purchase policy, delete user from system.
        int purchase_id = this.user_controller.clear_cart(this.user_id);
        this.store_controller.update_stores_inventory(cart, purchase_id);
        // failed
    }

    @Override
    public int open_store() {
        return 0;
    }

    @Override
    public int add_review(int product_id) {
        return 0;
    }

    @Override
    public int rate_product(int product_id) {
        return 0;
    }

    @Override
    public int rate_store(int store_id) {
        return 0;
    }

    @Override
    public int send_request_to_store(int store_id, String request) {
        return 0;
    }

    @Override
    public double send_complain() {
        return 0;
    }

    @Override
    public double view_user_purchase_history() {
        return 0;
    }

    @Override
    public double view_account_details() {
        return 0;
    }

    @Override
    public double edit_account_details() {
        return 0;
    }

    @Override
    public double add_security_personal_question() {
        return 0;
    }

    /**
     *
     * @param product
     * @param store_id
     *
     */
    @Override
    public void add_product_to_store(Product product, int store_id, int quantity) {
        try {
            store_controller.add_product_to_store(product, store_id, quantity);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Product already exist");
        }

    }

    /**
     *
     * @param product_id
     */
    @Override
    //maybe return the deleted product
    public void delete_product_from_store(int product_id) {
        this.store_controller.delete_product_from_store(product_id);
    }

    @Override
    public void edit_product_name(int product_id, String name) {
        if (!this.store_controller.edit_product_name(product_id, name))
        {
            System.out.println();
        }
    }

    @Override
    public void edit_product_price(int product_id, double price) {
        if (!this.store_controller.edit_product_price(product_id, price))
        {
            System.out.println();
        }

    }

    @Override
    public void edit_product_category(int product_id, String category) {
        if (!this.store_controller.edit_product_category(product_id, category))
        {
            System.out.println();
        }

    }

    @Override
    public void edit_product_key_words(int product_id, List<String> key_words) {
        if (!this.store_controller.edit_product_key_words(product_id, key_words))
        {
            System.out.println();
        }

    }


    @Override
    public int add_owner() {
        return 0;
    }

    @Override
    public int add_manager() {
        return 0;
    }

    @Override
    public int delete_owner() {
        return 0;
    }

    @Override
    public int delete_manager() {
        return 0;
    }

    @Override
    public void edit_manager_permissions(int user_id, int manager_id, int store_id, LinkedList<StorePermission> permissions) {
        try
        {
            this.store_controller.edit_manager_specific_permissions(user_id, manager_id, store_id, permissions);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean close_store_temporarily(int store_id, int user_id) {
        boolean answer = false;
        try
        {
            answer = this.store_controller.close_store_temporarily(store_id, user_id);
        }
        catch (Exception e)
        {
            return false;
        }
        return answer;
    }

    @Override
    public boolean open_close_store(int store_id, int user_id) {
        boolean answer = false;
        try
        {
            answer = this.store_controller.open_close_store(store_id, user_id);
        }
        catch (Exception e)
        {
            return false;
        }
        return answer;

    }

    @Override
    public String view_store_management_information(int user_id, int store_id) {
        String answer;
        try
        {
            StoreManagersInfo info = this.store_controller.view_store_management_information(user_id, store_id);
            answer = info.toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;
    }

    @Override
    public String view_store_questions(int store_id, int user_id) {
        String answer;
        try
        {
            HashMap<Integer, Question> questions = this.store_controller.view_store_questions(store_id, user_id);
            answer = questions.toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;
    }

    @Override
    public boolean manager_answer_question(int store_id, int user_id, int question_id, String answer) {
        try
        {
            this.store_controller.answer_question(store_id, user_id, question_id, answer);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
        return true;

    }

    @Override
    public String view_store_purchases_history(int store_id, int user_id) {
        String info;
        String answer;
        try
        {
            info = this.store_controller.view_store_purchases_history(store_id, user_id);
            answer = info.toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;

    }

    @Override
    public boolean close_store_permanently(int store_id, int user_id) {
        boolean answer = false;
        try
        {
            answer = this.store_controller.close_store_permanently(store_id, user_id);
        }
        catch (Exception e)
        {
            return false;
        }
        return answer;

    }

    @Override
    public double delete_user_from_system() {
        return 0;
    }

    @Override
    public double view_system_questions() {
        return 0;
    }

    @Override
    public double admin_answer_question() {
        return 0;
    }

    @Override
    public int get_system_statistics() {
        return 0;
    }
}
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
    //TODO: put check validity methods in all set methods
    @Override

    //Requirement 1.1
    public void init_market() {
        //Tom
        //connect to payment service
        //connect to supply service
        // load
    }
    //Requirement 1.3
    @Override
    public boolean payment(int price) {
        //Tom
        return true;
    }
    //Requirement 1.4
    @Override
    public boolean supply(int user_id, int purchase_id) {
        //Tom
        return true;

    }
    //Requirement 2.1.1
    @Override
    public double guest_login() {
        return 0;
    }


    //Requirement 2.1.2 & 2.3.1
    @Override
    public double logout() {
        return 0;
    }
    //Requirement 2.1.3
    @Override
    public double register() {
        return 0;
    }

    //Requirement 2.1.4
    @Override
    public double login(String username, String password) {
        return 0;
    }

    //Requirement 2.2.1 - Store

    /**
     *
     * @param store_id
     * @return store information
     * @throws IllegalArgumentException if store does not exist
     * @throws IllegalArgumentException if store isn't active
     */
    @Override
    public String find_store_information(int store_id) {
        String info="";
        try
        {
            info = this.store_controller.find_store_information(store_id);
        }
        catch (IllegalArgumentException e)
        {
            //return "Store does not exist in the market";
        }
        return info;

    }

    //Requirement 2.2.1 - Product

    /**
     *
     * @param product_id
     * @param store_id
     * @return product information
     * @throws IllegalArgumentException if store does not exist
     * @throws IllegalArgumentException if store isn't active
     */
    @Override
    public String find_product_information(int product_id, int store_id) {
        String info="";
        try
        {
            info = this.store_controller.find_product_information(product_id, store_id);
        }
        catch (IllegalArgumentException e)
        {
            return "Product does not exist in the market";
        }
        return info;
    }

//------------------------------------------------find product by - Start ----------------------------------------------------
    /**
     *
     * @param product_name
     * @return List of Products with the specific name
     */
    //Requirement 2.2.2 - Name
    @Override
    public List<Product> find_products_by_name(String product_name)
    {
        return this.store_controller.find_products_by_name(product_name);
    }

    /**
     *
     * @param category
     * @return List of Products with the specific category
     */
    //Requirement 2.2.2 - Category
    @Override
    public List<Product> find_products_by_category(String category)
    {
        return this.store_controller.find_products_by_category(category);
    }

    /**
     *
     * @param key_words
     * @return List of Products with the specific key_word
     */
    //Requirement 2.2.2 - Key_words
    @Override
    public List<Product> find_products_by_keywords(String key_words)
    {
        return this.store_controller.find_products_by_key_words(key_words);
    }
    //------------------------------------------------find product by - End ----------------------------------------------------

    //Requirement 2.2.3 - Add
    @Override
    public double add_product_to_cart() {
        return 0;
    }
    //Requirement 2.2.3 - Remove
    @Override
    public double delete_product_from_cart() {
        return 0;
    }

    //Requirement 2.2.4
    @Override
    public double view_user_cart() {
        return 0;
    }

    //Requirement 2.2.4
    @Override
    public double change_product_quantity_in_cart() {
        return 0;
    }

    //Requirement 2.2.5
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
        return 0;
    }


    /**
     *
     * @param store_name
     * @precondition : GUI check store name is valid
     * @throws if the user isn't a member
     */
    //Requirement 2.3.2
    @Override
    public void open_store(String store_name) {
        this.user_controller.is_a_member();
        this.store_controller.open_store(this.user_id, store_name);
    }

    /**
     *
     * @param product_id
     * @param store_id
     * @param review
     * throws if Product does not exist
     */
    //Requirement 2.3.3
    @Override
    public void add_review(int product_id, int store_id, String review)  {
        // @TODO GAL : throws if user isnt a buyer
        this.user_controller.check_if_user_buy_this_product(this.user_id, product_id, store_id);
        this.store_controller.add_review(this.user_id, product_id, store_id, review);
    }

    //Requirement 2.3.4 - Product

    /**
     *
     * @param product_id
     * @param store_id
     * @param rate
     * @throws if the product isn
     */
    @Override
    public void rate_product(int product_id, int store_id, int rate) {
        this.user_controller.check_if_user_buy_this_product(this.user_id, product_id, store_id);
        this.store_controller.rate_product(product_id, store_id, rate);
    }

    //Requirement 2.3.4 - Store
    @Override
    public void rate_store(int store_id, int rate) {
        // @TODO GAL : throws if user isnt a buyer
        this.user_controller.check_if_user_buy_from_this_store(this.user_id, store_id);
        this.store_controller.rate_store(store_id, rate);
    }

    //Requirement 2.3.5
    @Override
    public int send_request_to_store(int store_id, String request) {
        return 0;
    }

    //Requirement 2.3.6
    @Override
    public double send_complain() {
        return 0;
    }

    //Requirement 2.3.7
    @Override
    public double view_user_purchase_history() {
        return 0;
    }

    //Requirement 2.3.8 - View
    @Override
    public double view_account_details() {
        return 0;
    }

    //Requirement 2.3.8 - Edit
    @Override
    public double edit_account_details() {
        return 0;
    }

    //Requirement 2.3.9 - Personal question
    @Override
    public double add_security_personal_question() {
        return 0;
    }






    //Requirement 2.4.1 - Add

    /**
     *
     * @param store_id
     * @param quantity
     * @param name
     * @param price
     * @param category
     * @param key_words
     * @throws if store doesnt exist
     * @throws if there is no permission for the user
     */
    @Override
    public void add_product_to_store(int store_id, int quantity,
                                     String name, double price, String category, List<String> key_words) {
        try {
            store_controller.add_product_to_store(this.user_id, store_id, quantity, name, price, category, key_words);
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
    //Requirement 2.4.1 - Delete
    @Override
    //maybe return the deleted product
    public void delete_product_from_store(int product_id, int store_id) {
        try
        {
            this.store_controller.delete_product_from_store(this.user_id, product_id, store_id);
        }
        catch (Exception e)
        {
            System.out.println("amit");
        }
    }




    //------------------------------------------------ edit product - Start ----------------------------------------------
    /**
     *
     * @param product_id
     * @param store_id
     * @param name
     * throws IllegalArgumentException if Product does not exist
     * throws IllegalArgumentException if user does not have permission to this operation
     */
    //Requirement 2.4.1 - Edit name
    @Override
    public void edit_product_name(int product_id, int store_id, String name)  {
        this.store_controller.edit_product_name(this.user_id, product_id, store_id, name);
    }

    /**
     *
     * @param product_id
     * @param store_id
     * @param price
     * throws IllegalArgumentException if Product does not exist
     * throws IllegalArgumentException if user does not have permission to this operation
     */
    //Requirement 2.4.1 - Edit Price
    @Override
    public void edit_product_price(int product_id, int store_id, double price)  {
        this.store_controller.edit_product_price(this.user_id, product_id, store_id, price);
    }

    /**
     *
     * @param product_id
     * @param store_id
     * @param category
     * throws IllegalArgumentException if Product does not exist
     * throws IllegalArgumentException if user does not have permission to this operation
     */
    //Requirement 2.4.1 - Edit category
    @Override
    public void edit_product_category(int product_id, int store_id, String category)  {
        this.store_controller.edit_product_category(this.user_id, product_id, store_id, category);
    }

    /**
     *
     * @param product_id
     * @param store_id
     * @param key_words
     * throws IllegalArgumentException if Product does not exist
     * throws IllegalArgumentException if user does not have permission to this operation
     */
    //Requirement 2.4.1 - Edit key words
    @Override
    public void edit_product_key_words(int product_id, int store_id, List<String> key_words)  {
        this.store_controller.edit_product_key_words(this.user_id, product_id, store_id, key_words);
    }
    //------------------------------------------------ edit product - End ----------------------------------------------

    //Requirement 2.4.2 Cancelled



    //Requirement 2.4.3
    @Override
    public void set_store_purchase_rules(int store_id)  {

    }



    //Requirement 2.4.4
    @Override
    public int add_owner() {
        return 0;
    }
    //Requirement 2.4.5
    @Override
    public int delete_owner() {
        return 0;
    }
    //Requirement 2.4.6
    @Override
    public int add_manager() {
        return 0;
    }

    //Requirement 2.4.7
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


    //Requirement 2.4.8
    @Override
    public int delete_manager() {
        return 0;
    }

    //Requirement 2.4.9
    @Override
    public void close_store_temporarily(int store_id, int user_id) {
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

    //Requirement 2.4.10
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

    //Requirement 2.4.11
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

    //Requirement 2.4.12 - View
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

    //Requirement 2.4.12 - Responses
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

    //Requirement 2.4.13 & 2.6.4
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

    //Requirement 2.6.1
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

    //Requirement 2.6.2
    @Override
    public double delete_user_from_system() {
        return 0;
    }

    //Requirement 2.6.3 - View
    @Override
    public double view_system_questions() {
        return 0;
    }

    //Requirement 2.6.3 - Response by Admin
    @Override
    public double admin_answer_question() {
        return 0;
    }

    //Requirement 2.6.5
    @Override
    public int get_system_statistics() {
        return 0;
    }
}
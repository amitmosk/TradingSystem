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
    //TODO: all the Controllers ids counters -> AtomicIntegers - getInc
    //TODO: put check validity methods in all set methods
    //TODO: add error logger - before any exceptions
    //TODO: add logger for all updates in the market
    //TODO: change all exceptions messages to Client messages
    //TODO: view_management_information - string builder informatoin : store
    //TODO: view_questions - string builder questions : store
    // TODO : string builder in storePurchase history
    // TODO : string builder in userPurchase history
    //TODO: implement storePurchase history and userPurchase history
    //TODO: manage Admin permission function
    //TODO: toString in Response
    //TODO: the controllers returns Response
    //TODO: the market returns Response.toString()
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
        try
        {
            // @TODO: GAL get_email throws if the user is a guest
            String email = this.user_controller.get_email(this.user_id);
            // @TODO: AMIT change user id to email
            this.store_controller.open_store(email, store_name);
        }
        catch (Exception e)
        {

        }
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
        // @TODO GAL : throws if user isn't a buyer
        this.user_controller.check_if_user_buy_this_product(this.user_id, product_id, store_id);
        String user_email = this.user_controller.get_email(this.user_id);
        this.store_controller.add_review(user_email, product_id, store_id, review);
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
        String user_email = this.user_controller.get_email(this.user_id);
        this.store_controller.rate_product(user_email, product_id, store_id, rate);
    }

    //Requirement 2.3.4 - Store
    @Override
    public void rate_store(int store_id, int rate) {
        // @TODO GAL : throws if user isnt a buyer
        this.user_controller.check_if_user_buy_from_this_store(this.user_id, store_id);
        String user_email = this.user_controller.get_email(this.user_id);
        this.store_controller.rate_store(user_email, store_id, rate);
    }

    //Requirement 2.3.5
    @Override
    // @TODO : AMIT implement
    public int send_question_to_store(int store_id, String request) {
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
            String user_email = this.user_controller.get_email(this.user_id);
            store_controller.add_product_to_store(user_email, store_id, quantity, name, price, category, key_words);
        }
        catch (IllegalArgumentException | IllegalAccessException e)
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
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.delete_product_from_store(user_email, product_id, store_id);
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
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.edit_product_name(user_email, product_id, store_id, name);
        }
        catch (Exception e)
        {

        }
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
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.edit_product_price(user_email, product_id, store_id, price);
        }
        catch (Exception e)
        {

        }
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
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.edit_product_category(user_email, product_id, store_id, category);
        }
        catch (Exception e)
        {

        }
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
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.edit_product_key_words(user_email, product_id, store_id, key_words);
        }
        catch (Exception e)
        {

        }

    }
    //------------------------------------------------ edit product - End ----------------------------------------------

    //Requirement 2.4.2 Cancelled



    //Requirement 2.4.3
    @Override
    public void set_store_purchase_rules(int store_id)  {

    }



    //Requirement 2.4.4
// @TODO : have to check that there is at least one owner of each store
    public void add_owner(String user_email_to_appoint, int store_id) {
        String user_email = this.user_controller.get_email(this.user_id);
        this.store_controller.add_owner(user_email, user_email_to_appoint, store_id);
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

    /**
     *
     * @param manager_id - the user we want to change his permission
     * @param store_id
     * @param permissions
     * @throws IllegalArgumentException if the store doesn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalArgumentException if the manager doesnt appointed by user
     * @throws IllegalArgumentException user cant change himself permissions
     */
    @Override
    public void edit_manager_permissions(int manager_id, int store_id, LinkedList<StorePermission> permissions) {
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.edit_manager_specific_permissions(user_email, manager_id, store_id, permissions);
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

    /**
     *
     * @param store_id
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalAccessException if the user hasn't permission for close store
     * @throws IllegalArgumentException if the store is already close
     */
    @Override
    public void close_store_temporarily(int store_id) {
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.close_store_temporarily(user_email, store_id);
        }
        catch (Exception e)
        {
            System.out.println("as");
        }
    }

    //Requirement 2.4.10

    /**
     *
     * @param store_id
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalAccessException if the user hasn't permission for open store
     * @throws IllegalArgumentException if the store is already open
     */
    @Override
    public void open_close_store(int store_id) {
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.open_close_store(user_email, store_id);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    //Requirement 2.4.11

    /**
     *
     * @param store_id
     * @return String with all the information
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalAccessException if the user hasn't permission for view store managment information
     *
     */
    @Override
    public String view_store_management_information(int store_id) {
        String answer;
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            answer = this.store_controller.view_store_management_information(user_email, store_id);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;
    }

    //Requirement 2.4.12 - View

    /**
     *
     * @param store_id
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalAccessException if the user hasn't permission for view store questions
     * @return all the questions
     */
    @Override
    public String view_store_questions(int store_id) {
        String answer;
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            answer = this.store_controller.view_store_questions(user_email, store_id);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;
    }

    //Requirement 2.4.12 - Responses

    /**
     *
     * @param store_id
     * @param question_id
     * @param answer
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalAccessException if the user hasn't permission for answer questions
     * @throws IllegalArgumentException if the question is not exist
     */
    @Override
    public void manager_answer_question(int store_id, int question_id, String answer) {
        try
        {
            String user_email = this.user_controller.get_email(this.user_id);
            this.store_controller.answer_question(user_email, store_id, question_id, answer);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    //Requirement 2.4.13 & 2.6.4

    /**
     *
     * @param store_id
     * @throws IllegalArgumentException if the store isn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalAccessException if the user hasn't permission for view store purchases history
     * @return
     */
    @Override
    public String view_store_purchases_history(int store_id) {
        String answer;
        try
        {
            answer = this.store_controller.view_store_purchases_history(this.user_id, store_id);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;

    }

    //Requirement 2.6.1
    @Override
    public boolean close_store_permanently(int store_id) {
        try
        {
            // @TODO : GAL
            this.user_controller.is_admin();
            this.store_controller.close_store_permanently(this.user_id, store_id);
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
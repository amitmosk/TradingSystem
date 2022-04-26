package Domain;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import Domain.UserModule.*;
import Domain.StoreModule.*;
import Service.iService;

import java.util.Map;
// TODO: before get store check that the store is open
// TODO: all the Controllers ids counters -> AtomicIntegers - getInc
//TODO: put check validity methods in all set methods
//TODO: add error logger - before any exceptions
//TODO: add logger for all updates in the market
//TODO: change all exceptions messages to Client messages
//TODO: view_management_information - string builder information : store
// TODO : string builder in storePurchase history
// TODO : string builder in userPurchase history
//TODO: implement storePurchase history and userPurchase history
//TODO: manage Admin permission function
//TODO: toString in Response
//TODO: the controllers returns Response
//TODO: the market returns Response.toString()
public class Market //implements IMarket{
{
    private UserController uc;
    private StoreController store_controller;
    private int loggedUser;                  //id or email
    private boolean isGuest;                 //represents the state
    private int user_id;

    // @Override
    //Requirement 1.1
    public void init_market() {
        this.isGuest = true;
        //Tom
        //connect to payment service
        //connect to supply service
        // load
    }
    //Requirement 1.3
    // @Override
    public boolean payment(int price) {
        //Tom
        return true;
    }
    //Requirement 1.4
    // @Override
    public boolean supply(int user_id, int purchase_id) {
        //Tom
        return true;

    }
    //Requirement 2.1.1
    // @Override
    public int guest_login() {
        int logged = uc.guest_login();
        this.loggedUser = logged;
        return logged;
    }

    //Requirement 2.1.4
    // @Override
    public double login(String Email, String password) {
        boolean logRes = uc.login(loggedUser, Email, password);
        if(logRes) isGuest = false;
        return 1;
    }

    //Requirement 2.1.2 & 2.3.1
    // @Override
    public void logout() {
        if(isGuest) return; //todo throw error
        this.isGuest = true;
        this.loggedUser = -1;
    }


    //Requirement 2.1.3
    // @Override
    public double register(String Email, String pw, String name, String lastName) throws IllegalAccessException {
        if(!isGuest) return 1; //todo throw error
        uc.register(loggedUser,Email,pw,name,lastName);
        return 1;
    }

    //Requirement 2.2.1 - Store
    /**
     *
     * @param store_id
     * @return store information
     * @throws IllegalArgumentException if store does not exist
     * @throws IllegalArgumentException if store isn't active
     */
    // @Override
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
    // @Override
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
    // @Override
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
    // @Override
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
    // @Override
    public List<Product> find_products_by_keywords(String key_words)
    {
        return this.store_controller.find_products_by_key_words(key_words);
    }
    //------------------------------------------------find product by - End ----------------------------------------------------



    //Requirement 2.2.5
    // @Override
    public int buy_cart() {
        // get information about the payment & supply
        Cart cart = this.uc.get_cart(this.user_id);
        double total_price = this.store_controller.check_cart_available_products_and_calc_price(cart);
        this.payment(total_price, paymentInfo);
        this.supply(supplyInfo);
        // success
        // acquire lock of : edit/delete product, both close_store, discount & purchase policy, delete user from system.
        int purchase_id = this.uc.clear_cart(this.user_id);
        this.store_controller.update_stores_inventory(cart, purchase_id);
        // failed
        return 0;
    }


    /**
     *
     * @param store_name
     * @precondition : GUI check store name is valid
     * @throws if the user is a guest
     */
    //Requirement 2.3.2
    // @Override
    public void open_store(String store_name) {
        try
        {
            // @TODO: GAL get_email throws if the user is a guest
            String email = this.uc.get_email(this.user_id);
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
     * @throws if the user is a guest
     */
    //Requirement 2.3.3
    // @Override
    public void add_review(int product_id, int store_id, String review)  {
        // @TODO GAL : throws if user isn't a buyer
        try
        {
            this.uc.check_if_user_buy_this_product(this.user_id, product_id, store_id);
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.add_review(user_email, product_id, store_id, review);
        }
        catch (Exception e)
        {

        }

    }

    //Requirement 2.3.4 - Product

    /**
     *
     * @param product_id
     * @param store_id
     * @param rate
     * @throws if the product isn't in the store
     * @throws if the user is a guest
     */
    // @Override
    public void rate_product(int product_id, int store_id, int rate) {
        try
        {
            this.uc.check_if_user_buy_this_product(this.user_id, product_id, store_id);
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.rate_product(user_email, product_id, store_id, rate);
        }
        catch (Exception e)
        {

        }

    }

    //Requirement 2.3.4 - Store
    // @Override
    public void rate_store(int store_id, int rate) {
        // @TODO GAL : throws if user isnt a buyer
        try
        {
            this.uc.check_if_user_buy_from_this_store(this.user_id, store_id);
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.rate_store(user_email, store_id, rate);
        }
        catch (Exception e)
        {

        }
    }

    //Requirement 2.3.5

    /**
     *
     * @param store_id - the question is for a specific store
     * @param question - member question
     * @return
     * @throws if the user is a guest
     * @throws if the store isn't exist
     *
     */
    // @Override
    // @TODO : AMIT implement
    public void send_question_to_store(int store_id, String question) {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.add_question(user_email, store_id, question);
        }
        catch (Exception e)
        {

        }

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
    // @Override
    public void add_product_to_store(int store_id, int quantity,
                                     String name, double price, String category, List<String> key_words) {
        try {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    //maybe return the deleted product
    public void delete_product_from_store(int product_id, int store_id) {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public void edit_product_name(int product_id, int store_id, String name)  {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public void edit_product_price(int product_id, int store_id, double price)  {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public void edit_product_category(int product_id, int store_id, String category)  {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public void edit_product_key_words(int product_id, int store_id, List<String> key_words)  {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.edit_product_key_words(user_email, product_id, store_id, key_words);
        }
        catch (Exception e)
        {

        }

    }
    //------------------------------------------------ edit product - End ----------------------------------------------

    //Requirement 2.4.2 Cancelled



    //Requirement 2.4.3
    // @Override
    public void set_store_purchase_rules(int store_id)  {

    }



    //Requirement 2.4.4

    /**
     *
     * @param user_email_to_appoint - the user to appoint
     * @param store_id - the relevant store for the appointment
     * @throws if the store doesn't exist
     * @throws if the appointer has no permission
     * TODO : @throws if we cant appoint the user (already manager or something like that)
     */
// @TODO : have to check that there is at least one owner of each store
    public void add_owner(String user_email_to_appoint, int store_id) {
        // this.user_id - the connected user who ask to appoint some user.
        try
        {
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.add_owner(user_email, user_email_to_appoint, store_id);
        }
        catch (Exception e)
        {

        }

    }
    //Requirement 2.4.5
    // @Override
    public int delete_owner() {
        return 0;
    }
    //Requirement 2.4.6
    // @Override
    public int add_manager() {
        return 0;
    }

    //Requirement 2.4.7

    /**
     *
     * @param manager_email - the user we want to change his permission
     * @param store_id
     * @param permissions
     * @throws IllegalArgumentException if the store doesn't exist
     * @throws IllegalArgumentException if the store isn't active
     * @throws IllegalArgumentException if the manager doesnt appointed by user
     * @throws IllegalArgumentException user cant change himself permissions
     */
    // @Override
    public void edit_manager_permissions(String manager_email, int store_id, LinkedList<StorePermission> permissions) {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
            this.store_controller.edit_manager_specific_permissions(user_email, manager_email, store_id, permissions);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }


    //Requirement 2.4.8
    // @Override
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
    // @Override
    public void close_store_temporarily(int store_id) {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public void open_close_store(int store_id) {
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public String view_store_management_information(int store_id) {
        String answer;
        try
        {
            String user_email = this.uc.get_email(this.user_id);
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
    // @Override
    public String view_store_questions(int store_id) {
        String answer;
        try
        {
            String user_email = this.uc.get_email(this.user_id);
            List<String> store_questions = this.store_controller.view_store_questions(user_email, store_id);
            // @TODO : implement builder -> List of questions to string
            answer = store_questions.toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return answer;
    }

    //Requirement 2.2.3 - Add
    // @Override
    public void add_product_to_cart(int storeID,int productID, int quantity) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             p = sc.checkAvailabilityAndGet(storeID,productID,quantity);
//             basket.addProduct(p,quantity);
            uc.addBasket(loggedUser, storeID, storeBasket);
        }
        catch (Exception e){

        }
    }

    public void edit_product_quantity_in_cart(int storeID,int productID, int quantity) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             p = sc.checkAvailabilityAndGet(storeID,productID,quantity);
//             basket.changeQuantity(p,quantity);
        }
        catch (Exception e){

        }
    }

    public void remove_product_from_cart(int storeID,int productID) {
        Product p = null;
        Basket storeBasket = null;
        try{
            storeBasket = uc.getBasketByStoreID(loggedUser,storeID);
//             basket.removeProduct(p);
            uc.removeBasketIfNeeded(loggedUser, storeID, storeBasket);
        }
        catch (Exception e){

        }
    }

    public Map<Integer,Basket> view_user_cart() {
        return uc.getBaskets(loggedUser);
    }

    public int buy_cart() {
        // get information about the payment & supply
        Cart cart = this.uc.getCart(this.loggedUser);
//        double total_price = this.store_controller.check_cart_available_products_and_calc_price(cart);
//        this.payment(total_price, paymentInfo);
//        this.supply(supplyInfo);
        // success
        // acquire lock of : edit/delete product, both close_store, discount & purchase policy, delete user from system.
        this.uc.buyCart(this.loggedUser);
//        this.store_controller.update_stores_inventory(cart, purchase_id);
        // failed
        return 0;
    }

    public double send_complain() {
        //todo implement request handler
        return 0;
    }

    public UserHistory view_user_purchase_history() throws Exception { //todo remove throws and catch exception
        return uc.view_user_purchase_history(loggedUser);
    }

    public double view_account_details() {
        return 0;
    }

    public String get_user_name() throws Exception { //todo handle exception in try catch
        return this.uc.get_user_name(loggedUser);
    }

    public String get_user_last_name() throws Exception { //todo handle exception in try catch
        return this.uc.get_user_last_name(loggedUser);
    }
}
/*
    //Requirement 2.3.6
    // @Override
    public double send_complain() {
        return 0;
    }

    //Requirement 2.3.7
    // @Override
    public double view_user_purchase_history() {
        return 0;
    }

    //Requirement 2.3.8 - View
    // @Override
    public double view_account_details() {
        return 0;
    }

    //Requirement 2.3.8 - Edit
    // @Override
    public double edit_account_details() {
        return 0;
    }

    //Requirement 2.3.9 - Personal question
    // @Override
    public double add_security_personal_question() {
        return 0;
    }

        //Requirement 2.2.3 - Remove
    // @Override
    public double delete_product_from_cart() {
        return 0;
    }

    //Requirement 2.2.4
    // @Override
    public double view_user_cart() {
        return 0;
    }

    //Requirement 2.2.4
    // @Override
    public double change_product_quantity_in_cart() {
        return 0;
    }

*/

package TradingSystem.server.Domain.Facade;

import java.util.List;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;

import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.SimplePurchaseRule;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.PurchaseRule;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.StoreModule.StorePermission;
import TradingSystem.server.Domain.UserModule.*;
import TradingSystem.server.Domain.Utils.ErrorLogger;
import TradingSystem.server.Domain.Utils.Exception.*;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.Statistics.Statistic;
import TradingSystem.server.Domain.Utils.SystemLogger;
import TradingSystem.server.Domain.Utils.Utils;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;

import java.util.*;

// TODO: when we leave the system - should call logout()

public class MarketFacade {
    public static final Object lock = new Object();
    private UserController user_controller;
    private StoreController store_controller;
    private int loggedUser;                  //id
    private boolean isGuest;                 //represents the state
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;
    private ErrorLogger error_logger;
    private SystemLogger system_logger;

    public MarketFacade(PaymentAdapter payment_adapter, SupplyAdapter supply_adapter) {
        this.isGuest = true;
        this.user_controller = UserController.getInstance();
        this.store_controller = StoreController.get_instance();
        //Requirement 2.1.1 - guest log in
        this.loggedUser = user_controller.guest_login();
        this.payment_adapter = payment_adapter;
        this.supply_adapter = supply_adapter;
        this.error_logger = ErrorLogger.getInstance();
        this.system_logger = SystemLogger.getInstance();
    }

    /**
     * Requirement 2.1.2 & 2.3.1
     * <p>
     * logout - logout assign user
     *
     * @return a string with informative of success/failure to client
     */
    public Response<UserInformation> logout() {
        Response<UserInformation> response = null;
        try {
            User user = user_controller.logout(loggedUser);
            this.isGuest = true;
            UserInformation user_inform = new UserInformation(user);
            system_logger.add_log("User logged out from the system.");
            response = new Response(user_inform, "Logout Successfully");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.1.3
     *
     * @param Email    of new user
     * @param pw       requested encrypted password
     * @param name     the first name of the new user
     * @param lastName the last name of the new user
     * @return a string with informative of success/failure to client
     */
    public Response<UserInformation> register(String Email, String pw, String name, String lastName, String birth_date) {
        Response<UserInformation> response = null;
        try {
            User user = user_controller.register(loggedUser, Email, pw, name, lastName, birth_date);
            this.isGuest = false;
            UserInformation userInformation = new UserInformation(user);
            response = new Response<>(userInformation, "Registration done successfully");
            system_logger.add_log(name + " " + lastName + " has registered to the system");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.1.4
     *
     * @param Email    of the user
     * @param password encrypted
     * @return a string with informative of success/failure to client
     */
    public Response<UserInformation> login(String Email, String password) {
        Response<UserInformation> response = null;
        try {
            User user = user_controller.login(loggedUser, Email, password);
            String user_name = this.user_controller.get_user_name(loggedUser) + " " + this.user_controller.get_user_last_name(loggedUser);
            isGuest = false;
            UserInformation userInformation = new UserInformation(user);
            response = new Response<>(userInformation, "Hey " + user_name + ", Welcome to the trading system market!");
            system_logger.add_log("User " + Email + " logged-in");
        } catch (Exception e) {
            response = Utils.CreateResponse(new LoginException(" "));
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.2.1 - Store
     *
     * @param store_id represents the store id
     * @return store information or action failure reason
     */
    public Response<StoreInformation> find_store_information(int store_id) {
        Response<StoreInformation> response = null;
        try {
            Store store = this.store_controller.find_store_information(store_id);
            StoreInformation storeInformation = new StoreInformation(store);
            response = new Response<>(storeInformation, "Store information received successfully");
            system_logger.add_log("Store (" + store_id + ") information found successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.2.1 - Product
     *
     * @param product_id of the asked product
     * @param store_id   who has the product
     * @return product information or action failure reason
     */
    public Response<Product> find_product_information(int product_id, int store_id) {
        Response<Product> response = null;
        try {
            Product product = this.store_controller.find_product_information(product_id, store_id);
            response = new Response<>(product, "Product information received successfully");
            system_logger.add_log("Product (" + product_id + " from store " + store_id + ") information found successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.2.2 - Name
     *
     * @param product_name the name of the desired product
     * @return List of Products with the specific name or action failure reason
     */

    public Response<List<Product>> find_products_by_name(String product_name) {
        Response<List<Product>> response = null;
        try {
            List<Product> products = this.store_controller.find_products_by_name(product_name);
            response = new Response<>(products, "Product list received successfully");
            system_logger.add_log("List of product " + product_name + " found successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.2.2 - Category
     *
     * @param category the category of the desired product
     * @return List of Products with the specific category or action failure reason
     */

    public Response<List<Product>> find_products_by_category(String category) {
        Response<List<Product>> response = null;
        try {
            List<Product> products = this.store_controller.find_products_by_category(category);
            response = new Response<>(products, "Products received successfully");
            system_logger.add_log("List of products from category " + category + " found successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.2.2 - Key_words
     *
     * @param key_words the keywords of the desired product
     * @return List of Products with the specific key_word or action failure reason
     */

    public Response<List<Product>> find_products_by_keywords(String key_words) {
        Response<List<Product>> response = null;
        try {
            List<Product> products = this.store_controller.find_products_by_key_words(key_words);
            response = new Response<>(products, "Products received successfully");
            system_logger.add_log("List of products with key words- " + key_words + " found successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.2.3 - Add
     *
     * @param storeID   who stores the product
     * @param productID we want to add to the cart
     * @param quantity  of the product
     * @return success/failure message
     */

    public Response<String> add_product_to_cart(int storeID, int productID, int quantity) {
        Response<String> response = null;
        try {
            Store store = store_controller.get_store(storeID);
            Product p = store_controller.checkAvailablityAndGet(storeID, productID, quantity);
            user_controller.add_product_to_cart(loggedUser, store, p, quantity);
            response = new Response<>("", "product " + productID + " added to cart");
            system_logger.add_log("User added to cart " + quantity + " of product- " + productID + " from store- " + storeID);
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.2.4
     *
     * @param storeID   who stores the product
     * @param productID we want to change the quantity
     * @param quantity  new amount
     * @return success/failure message
     */

    public Response<String> edit_product_quantity_in_cart(int storeID, int productID, int quantity) {
        Response<String> response = null;
        try {
            Store store = store_controller.get_store(storeID);
            Product p = store_controller.checkAvailablityAndGet(storeID, productID, quantity);
            user_controller.edit_product_quantity_in_cart(loggedUser, store, p, quantity);
            response = new Response<>("", "product " + productID + " quantity has changed to " + quantity);
            system_logger.add_log("User quantity of product- " + productID + " from store- " + storeID + " in cart to " + quantity);
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.2.3 - Remove
     *
     * @param storeID   who stores the product
     * @param productID we want to remove from the cart
     * @return success/failure message
     */

    public Response<String> remove_product_from_cart(int storeID, int productID) {
        Response<String> response = null;
        try {
            Product p = store_controller.getProduct_by_product_id(storeID, productID);
            Store store = store_controller.get_store(storeID);
            user_controller.remove_product_from_cart(loggedUser, store, p);
            response = new Response<>("", "product " + productID + " has removed from cart");
            system_logger.add_log("User removed from cart product- " + productID + " from store- " + storeID);
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.2.4
     *
     * @return the user cart information
     */
    public Response<CartInformation> view_user_cart() {
        CartInformation cartInformation = user_controller.getCart(loggedUser).cartInformation();
        Response<CartInformation> response = new Response<>(cartInformation, "successfully received user's cart");
        system_logger.add_log("User viewed his cart successfully");
        return response;
    }


    /**
     * Requirement 2.2.5
     *
     * @param paymentInfo info of payment
     * @param SupplyInfo  info of supply
     * @return success/failure message
     */
    //TODO: concurrent & edit
    public Response<UserPurchase> buy_cart(String paymentInfo, String SupplyInfo) {
        Response<UserPurchase> response = null;
        try {
            //if can pay & can supply then ->
            synchronized (lock) {
                double cart_price = 0;
                if (this.payment_adapter.can_pay(cart_price, paymentInfo) && this.supply_adapter.can_supply(SupplyInfo)) {
                    // acquire lock of : edit/delete product, both close_store, discount & purchase policy, delete user from system.
                    UserPurchase userPurchase = this.user_controller.buyCart(this.loggedUser);
                    this.payment_adapter.payment(userPurchase.getTotal_price(), paymentInfo);
                    this.supply_adapter.supply(SupplyInfo);
                    response = new Response<>(userPurchase, "Purchase done successfully");
                }
            }
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.2
     *
     * @param store_name name of the store to be opened
     * @return success/failure message
     * @precondition : GUI check store name is valid
     */
    //TODO: should we return the store ? or should we do something with the store id
    public Response<String> open_store(String store_name) {
        Response<String> response = null;
        try {
            User online_user = user_controller.get_user(loggedUser);
            int store_id = this.store_controller.open_store(online_user, store_name);
            response = new Response<>(null, "Store opened successfully");
            system_logger.add_log("Store " + store_name + " with id = " + store_id + "opened successfully");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.3
     *
     * @param product_id - the product to review
     * @param store_id   - the store who has this product
     * @param review     - user message about the product
     * @return success/failure message
     */
    //TODO: can edit if needed by getting user.
    public Response<String> add_product_review(int product_id, int store_id, String review) {
        Response<String> response = null;
        try {
            this.user_controller.check_if_user_buy_this_product(this.loggedUser, product_id, store_id);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.add_review(user_email, product_id, store_id, review);
            response = new Response<>(null, "Review added successfully");
            system_logger.add_log("New review added for product (" + product_id + ") form store (" + store_id + ") and user:" + user_email);
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.3.4 - Product
     *
     * @param product_id of the product to rate
     * @param store_id   who stores the product
     * @param rate       number in range 1-5
     * @return success/failure message
     */

    public Response<String> rate_product(int product_id, int store_id, int rate) {
        Response<String> response = null;
        try {
            this.user_controller.check_if_user_buy_this_product(this.loggedUser, product_id, store_id);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.rate_product(user_email, product_id, store_id, rate);
            response = new Response<>(null, "Rating added successfully to the product");
            system_logger.add_log("New rating (" + rate + ") added for product (" + product_id + ") form store (" + store_id + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.4 - Store
     *
     * @param store_id id of the store
     * @param rate     the rating enter by the user
     * @return success/failure message
     */
    //TODO: should be fixed
    public Response<String> rate_store(int store_id, int rate) {
        Response<String> response = null;
        try {
            this.user_controller.check_if_user_buy_from_this_store(this.loggedUser, store_id);
            String user_email = this.user_controller.get_email(this.loggedUser);
            User user = user_controller.get_user(loggedUser);
            this.store_controller.rate_store(user, store_id, rate);
            response = new Response<>(null, "Rating added successfully to the store");
            system_logger.add_log("New rating (" + rate + ") added for store (" + store_id + ") from user : " + user_email);
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.3.5
     *
     * @param store_id - the question is for a specific store
     * @param question - member question
     * @return success/failure message
     */

    public Response<String> send_question_to_store(int store_id, String question) {
        Response<String> response = null;
        try {
            User user = this.user_controller.get_user(this.loggedUser);
            this.user_controller.check_if_user_buy_from_this_store(this.loggedUser, store_id);
            this.store_controller.add_question(user, store_id, question);
            response = new Response<>(null, "Question send to the store successfully");
            system_logger.add_log("New question sent to store (" + store_id + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.6
     *
     * @param question to admin
     * @return success/failure message
     */

    public Response send_question_to_admin(String question) {
        Response response = null;
        try {
            this.user_controller.send_question_to_admin(loggedUser, question);
            response = new Response<>(null, "Question send to the admin successfully");
            system_logger.add_log("New question sent to admin");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.7
     *
     * @return user purchase history or failure message
     */

    public Response<UserPurchaseHistory> view_user_purchase_history() {
        Response<UserPurchaseHistory> response = null;
        try {
            UserPurchaseHistory userPurchaseHistory = user_controller.view_user_purchase_history(loggedUser);
            response = new Response<>(userPurchaseHistory, "successfully received user's product history");
            system_logger.add_log("User viewed his purchase history successfully");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.3.8 - View
     *
     * @return user email or failure message
     */

    public Response<String> get_user_email() {
        Response<String> response = null;
        try {
            String email = user_controller.get_email(loggedUser);
            response = new Response<>(email, "successfully received user's email");
            system_logger.add_log("Got user's email successfully");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - View
     *
     * @return user name or failure message
     */

    public Response<String> get_user_name() {
        Response<String> response = null;
        try {
            String name = user_controller.get_user_name(loggedUser);
            response = new Response<>(name, "successfully received user's name");
            system_logger.add_log("Got user's name successfully");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - View
     *
     * @return user last name or failure message
     */

    public Response<String> get_user_last_name() {
        Response<String> response = null;
        try {
            String last_name = this.user_controller.get_user_last_name(loggedUser);
            response = new Response<>(last_name, "Last name received successfully");
            system_logger.add_log("Got user's last name successfully");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.3.8 - edit
     *
     * @param old_password
     * @param password
     * @return success/failure message
     */
    //TODO: password should be sent ?
    public Response<String> edit_password(String old_password, String password) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_password(loggedUser, old_password, password);
            response = new Response<>(password, email + " password has been changed successfully");
            system_logger.add_log("User's (" + email + ")  password has been changed successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - edit
     *
     * @param new_name new first name
     * @return success/failure message
     */
    public Response<String> edit_name(String new_name) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_name(loggedUser, new_name);
            response = new Response<>(new_name, email + " name changed to " + new_name);
            system_logger.add_log("User's (" + email + ") name has been successfully changed to " + new_name + ".");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - edit
     *
     * @param new_last_name new last name
     * @return success/failure message
     */
    public Response<String> edit_last_name(String new_last_name) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_last_name(loggedUser, new_last_name);
            response = new Response<>(new_last_name, email + " last name changed to " + new_last_name);
            system_logger.add_log("User's (" + email + ") last name has been successfully changed to " + new_last_name + ".");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - unregister
     *
     * @param password
     * @return success/failure message
     */
    //TODO: fix method
    public Response<String> unregister(String password) {
        Response<String> response = null;
        try {
            // TODO: version 2
            // close stores permanently
            String email = user_controller.unregister(loggedUser, password);
            // remove user from all owners and managers
            // remove all users complains & questions
            response = new Response<>(email, email + " unregistered successfully");
            system_logger.add_log("User (" + email + ") has been successfully unregistered from the system.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - edit
     *
     * @param new_name new first name
     * @param answer   for the security question
     * @return success/failure message
     */


    public Response<String> edit_name_premium(String new_name, String answer) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_name_premium(loggedUser, new_name, answer);
            response = new Response<>(new_name, email + " name changed to " + new_name);
            system_logger.add_log("User's (" + email + ") name has been successfully changed to " + new_name + ".");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - edit
     *
     * @param new_last_name new last name
     * @param answer        for the security question
     * @return success/failure message
     */

    public Response<String> edit_last_name_premium(String new_last_name, String answer) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_last_name_premium(loggedUser, new_last_name, answer);
            response = new Response<>(new_last_name, email + " last name changed to " + new_last_name);
            system_logger.add_log("User's (" + email + ") last name has been successfully changed to " + new_last_name + ".");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.8 - edit
     *
     * @param old_password ,
     * @param new_password .
     * @param answer       for the security question
     * @return success/failure message
     */

    public Response<String> edit_password_premium(String old_password, String new_password, String answer) {
        Response<String> response = null;
        try {
            String email = user_controller.edit_password_premium(loggedUser, old_password, new_password, answer);
            response = new Response<>(null, email + " password changed");
            system_logger.add_log("User's (" + email + ") password has been successfully changed.");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.3.9
     *
     * @return the security question of the user or failure message
     */

    public Response<String> get_user_security_question() {
        Response<String> response = null;
        try {
            String question = user_controller.get_user_security_question(loggedUser);
            response = new Response<>(question, "successfully received security question");
            system_logger.add_log("Got user's security question successfully");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.3.9
     *
     * @param password for secure the action
     * @param question for improve security
     * @param answer   of the security question
     * @return success or failure message
     */

    public Response<String> improve_security(String password, String question, String answer) {
        Response<String> response = null;
        try {
            String email = user_controller.improve_security(loggedUser, password, question, answer);
            response = new Response<>(null, email + " improved security");
            system_logger.add_log("User's (" + email + ") security has been successfully improved.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.1 - Add
     *
     * @param store_id  adding product to a store
     * @param quantity  for store's inventory
     * @param name      product name
     * @param price     for one unit
     * @param category  for the product
     * @param key_words for searching product
     * @return stores inventory
     */

    //TODO: integration between user
    public Response<Map<Product, Integer>> add_product_to_store(int store_id, int quantity,
                                                                String name, double price, String category, List<String> key_words) {
        Response<Map<Product, Integer>> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            Map<Product, Integer> products = store_controller.add_product_to_store(user, store_id, quantity, name, price, category, key_words);
            response = new Response<>(products, "Product added successfully");
            system_logger.add_log("New product (" + name + ") added to store (" + store_id + ")");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.1 - Delete
     *
     * @param product_id of the product
     * @param store_id   .
     * @return success/failure message
     */
    //TODO: integration
    public Response<Map<Product, Integer>> delete_product_from_store(int product_id, int store_id) {
        Response<Map<Product, Integer>> response = null;
        try {
            synchronized (lock) {
                User user = user_controller.get_user(loggedUser);
                String user_email = this.user_controller.get_email(this.loggedUser);
                Map<Product, Integer> inv = this.store_controller.delete_product_from_store(user, product_id, store_id);
                response = new Response<>(inv, "Product deleted successfully");
                system_logger.add_log("Product (" + product_id + ") was deleted from store (" + store_id + ")");
            }
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;

    }


    //discount policy


    public Response add_predict(int store_id, String category, Product product, boolean above, boolean equel,
                                int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, String name) {
        Response<Predict> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                Predict predict = store.addPredict(category, product, above, equel, num, price, quantity, age, time, year, month, day, name);
                response = new Response(predict, "predict added successfully");
                system_logger.add_log("predict added deleted successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response get_purchase_policy(int store_id) {
        Response<List<String>> response = null;
        try {
            Store store = store_controller.get_store(store_id);
            List<String> policy = store.getPurchasePolicyNames();
            response = new Response(policy, "purchase policy sent");
            system_logger.add_log("purchase policy sent to user");
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response send_predicts(int store_id) {
        Response<List<String>> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                List<String> policy = store.getPredicts();
                response = new Response(policy, "predicts sent");
                system_logger.add_log("predicts sent to user");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response get_discount_policy(int store_id) {
        Response<List<String>> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                List<String> policy = store.getDiscountPolicyNames();
                response = new Response(policy, "discount policy sent");
                system_logger.add_log("composite discount deleted successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response add_complex_discount_rule(int store_id, String nameOfPredict, String nameOfComponent, String nameOfRule) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                ComplexDiscountComponent complex = store.add_complex_discount(nameOfRule, nameOfPredict, nameOfComponent);
                response = new Response(complex, "complex discount added successfully");
                system_logger.add_log("complex discount added successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response add_simple_category_discount_rule(int store_id, String nameOfCategory, double percent, String nameOfRule) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                simpleDiscountComponent simple = store.add_simple_discount(nameOfRule, "c", percent, nameOfCategory);
                response = new Response(simple, "simple category discount added successfully");
                system_logger.add_log("simple category discount added successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response add_simple_product_discount_rule(int store_id, int id, double percent, String nameOfrule) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                simpleDiscountComponent simple = store.add_simple_product_discount(nameOfrule, id, percent);
                response = new Response(simple, "simple product discount added successfully");
                system_logger.add_log("simple product discount added successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response add_simple_store_discount_rule(int store_id, double percent, String nameOfRule) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                simpleDiscountComponent simple = store.add_simple_discount(nameOfRule, "store", percent, "");
                response = new Response(simple, "store discount added successfully");
                system_logger.add_log("Store's (" + store_id + ")discount deleted successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response add_and_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                Ipredict discount = store.CreateAndDisocuntCompnent(NameOfRule, left, right);
                response = new Response(discount, "Store discount and rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") discount and rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response add_or_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                Ipredict discount = store.CreateOrDisocuntCompnent(NameOfRule, left, right);
                response = new Response(discount, "Store discount or rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") discount or rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response add_max_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                DiscountComponent discount = store.CreateMaxDisocuntCompnent(NameOfRule, left, right);
                response = new Response(discount, "Store discount max rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") discount max rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response add_plus_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                DiscountComponent discount = store.CreateplusDisocuntCompnent(NameOfRule, left, right);
                response = new Response(discount, "Store discount plus rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") discount plus rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response add_xor_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                DiscountComponent discount = store.CreateXorDisocuntCompnent(NameOfRule, left, right);
                response = new Response(discount, "Store discount and rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") discount and rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response remove_discount_rule(int store_id, String name) {
        Response<SimplePurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                String res = store.remove_discount_rule(name);
                response = new Response(res, "discount rule removed successfully");
                system_logger.add_log("Store's (" + store_id + ") discount removed successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response remove_purchase_rule(int store_id, String name) {
        Response<SimplePurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                String res = store.remove_purchase_rule(name);
                response = new Response(res, "purchase rule removed successfully");
                system_logger.add_log("Store's (" + store_id + ") purchase removed successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response<SimplePurchaseRule> add_simple_purchase_rule(String PredictName, String NameOfRule, int store_id) {
        Response<SimplePurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                PurchaseRule PurchaseRule = store.addsimplePorchaseRule(NameOfRule, PredictName);
                response = new Response(PurchaseRule, "simple purchase added successfully");
                system_logger.add_log("Store's (" + store_id + ") simple purchase added successfully");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response<PurchaseRule> add_and_purchase_rule(String left, String right, int store_id, String NameOfrule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                PurchaseRule PurchaseRule = store.add_and_purchase_rule(NameOfrule, left, right);
                response = new Response(PurchaseRule, "Store purchase and rule added successfully");
                system_logger.add_log("Store's (" + store_id + ") purchase and rule have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    public Response<PurchaseRule> add_or_purchase_rule(String left, String right, int store_id, String nameOfrule) {
        Response<PurchaseRule> response = null;
        try {
            synchronized (lock) {
                Store store = store_controller.get_store(store_id);
                PurchaseRule PurchaseRule = store.add_or_purchase_rule(nameOfrule, left, right);
                response = new Response(PurchaseRule, "Store purchase rules added successfully");
                system_logger.add_log("Store's (" + store_id + ") purchase rules have been added");
            }
        } catch (MarketException e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.1 - Edit name
     *
     * @param product_id of the product
     * @param store_id   who stores the product
     * @param name       we want to set for the product
     * @return success/failure message
     */
    //TODO: integration
    public Response<String> edit_product_name(int product_id, int store_id, String name) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.edit_product_name(user, product_id, store_id, name);
            response = new Response<>(null, "Product name edit successfully");
            system_logger.add_log("Product (" + product_id + ") name has been changed to " + name + " in store (" + store_id + ")");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.1 - Edit Price
     *
     * @param product_id of the item
     * @param store_id   who stores the product
     * @param price      to set for the product
     * @return success/failure message
     */
    //TODO: integration
    public Response<String> edit_product_price(int product_id, int store_id, double price) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                User user = user_controller.get_user(loggedUser);
                this.store_controller.edit_product_price(user, product_id, store_id, price);
                response = new Response<>(null, "Product price edit successfully");
                system_logger.add_log("Product (" + product_id + ") price has been changed to " + price + " in store (" + store_id + ")");
            }
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.1 - Edit category
     *
     * @param product_id of the item
     * @param store_id   who stores the product
     * @param category
     * @return success/failure message
     */

    public Response<String> edit_product_category(int product_id, int store_id, String category) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            this.store_controller.edit_product_category(user, product_id, store_id, category);
            response = new Response<>(null, "Product category edit successfully");
            system_logger.add_log("Product (" + product_id + ") category has been changed to " + category + " in store (" + store_id + ")");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.1 - Edit key words
     *
     * @param product_id of the item
     * @param store_id   who stores the product
     * @param key_words
     * @return success/failure message
     */


    public Response<String> edit_product_key_words(int product_id, int store_id, List<String> key_words) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            this.store_controller.edit_product_key_words(user, product_id, store_id, key_words);
            response = new Response<>(null, "Product key_words edit successfully");
            system_logger.add_log("Product's (" + product_id + ") key words have been changed to " + key_words + " in store (" + store_id + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.4
     *
     * @param user_email_to_appoint - the user to appoint
     * @param store_id              - the relevant store for the appointment
     * @return success/failure message
     */


    public Response<String> add_owner(String user_email_to_appoint, int store_id) {
        Response<String> response = null;
        try {
            String user_email = this.user_controller.get_email(this.loggedUser);
            User appointer = user_controller.get_user(loggedUser);
            User user_to_appoint = user_controller.get_user_by_email(user_email_to_appoint);
            this.store_controller.add_owner(appointer, user_to_appoint, store_id);
            response = new Response<>(null, "Owner added successfully");
            system_logger.add_log("User- " + user_email_to_appoint + " has been appointed by user- " + user_email + " to store (" + store_id + ") owner");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.5
     *
     * @param user_email_to_delete_appointment the email of the user to be remove his appointment
     * @param store_id                         the id of the store to removed the appoitment from
     * @return success/failure message
     */


    public Response delete_owner(String user_email_to_delete_appointment, int store_id) {
        Response response = null;
        try {
            String user_email = user_controller.get_email(loggedUser);
            User deleter = user_controller.get_user(loggedUser);
            User to_delete = user_controller.get_user_by_email(user_email_to_delete_appointment);
            this.store_controller.remove_owner(deleter, to_delete, store_id);
            response = new Response<>(null, "Owner removed successfully");
            system_logger.add_log("User- " + user_email_to_delete_appointment + " has been unappointed by user- " + user_email + " from store (" + store_id + ") owner");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.6
     *
     * @param user_email_to_appoint - the user to appoint
     * @param store_id              - the relevant store for the appointment
     * @return success/failure message
     */


    public Response add_manager(String user_email_to_appoint, int store_id) {
        Response<String> response = null;
        try {
            User appointer = user_controller.get_user(loggedUser);
            User user_to_apoint = user_controller.get_user_by_email(user_email_to_appoint);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.add_manager(appointer, user_to_apoint, store_id);
            response = new Response<>(null, "Manager added successfully");
            system_logger.add_log("User- " + user_email_to_appoint + " has been appointed by user- " + user_email + " to store (" + store_id + ") manager");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.7
     *
     * @param manager_email - the user we want to change his permission
     * @param store_id      - the store for the permissions
     * @param permissions   list of the new permissions - just the relevant
     * @return success/failure message
     */

    public Response<String> edit_manager_permissions(String manager_email, int store_id, List<StorePermission> permissions) {
        Response<String> response = null;
        try {
            User appointer = user_controller.get_user(loggedUser);
            User manager = user_controller.get_user_by_email(manager_email);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.edit_manager_specific_permissions(appointer, manager, store_id, permissions);
            response = new Response<>(null, "Manager permission edit successfully");
            system_logger.add_log("Manager's (" + manager_email + ") permissions have been updated by user - " + user_email + " in store (" + store_id + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.8
     *
     * @param user_email_to_delete_appointment email of user manager to remove
     * @param store_id                         id of the store
     * @return success/failure message
     */


    public Response<String> delete_manager(String user_email_to_delete_appointment, int store_id) {
        Response<String> response = null;
        try {
            User appointer = user_controller.get_user(loggedUser);
            User manager = user_controller.get_user_by_email(user_email_to_delete_appointment);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.remove_manager(appointer, manager, store_id);
            response = new Response<>(null, "Manager removed successfully");
            system_logger.add_log("User- " + user_email_to_delete_appointment + " has been unappointed by user- " + user_email + " from store (" + store_id + ") manager");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.9
     *
     * @param store_id of the store
     * @return success/failure message
     * @precondition the store is open
     */

    public Response<String> close_store_temporarily(int store_id) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                User user = user_controller.get_user(loggedUser);
                String user_email = this.user_controller.get_email(this.loggedUser);
                this.store_controller.close_store_temporarily(user, store_id);
                response = new Response<>(null, "Store closed temporarily");
                system_logger.add_log("Store (" + store_id + ") has been closed temporarily by user (" + user_email + ")");
            }
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.10
     *
     * @param store_id - the store we want to re-open
     * @return success/failure message
     * @precondition the store is close
     */

    public Response<String> open_close_store(int store_id) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.open_close_store(user, store_id);
            response = new Response<>(null, "Store re-open successfully");
            system_logger.add_log("Store (" + store_id + ") has been re-opened by user (" + user_email + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.11
     *
     * @param store_id - the store we want to get information about
     * @return store management information or failure message
     */
    public Response<StoreManagersInfo> view_store_management_information(int store_id) {
        Response<StoreManagersInfo> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            StoreManagersInfo answer = this.store_controller.view_store_management_information(user, store_id);
            response = new Response<>(answer, "Store information received successfully");
            system_logger.add_log("Store's (" + store_id + ") management information has been viewed by user (" + user_email + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.4.12
     *
     * @param store_id - the store we want to get information about
     * @return all the questions for a store or failure message
     */

    public Response<List<String>> manager_view_store_questions(int store_id) {
        Response<List<String>> response = null;
        try {
            String user_email = this.user_controller.get_email(this.loggedUser);
            User user = user_controller.get_user(loggedUser);
            List<String> store_questions = this.store_controller.view_store_questions(user, store_id);
            response = new Response<>(store_questions, "Store questions received successfully");
            system_logger.add_log("Store's (" + store_id + ") questions has been viewed by user (" + user_email + ")");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.12
     *
     * @param store_id    - each question is related to store
     * @param question_id - each time the manager answer one question only
     * @param answer      - manager answer
     * @return success/failure message
     */

    public Response<String> manager_answer_question(int store_id, int question_id, String answer) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            this.store_controller.answer_question(user, store_id, question_id, answer);
            response = new Response("", "manager answer the question successfully");
            system_logger.add_log("Store (" + store_id + ") question (" + question_id + ") has been answered by user (" + user_email + ")");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.4.13
     *
     * @param store_id ,
     * @return store purchase history or failure message
     */

    public Response<Collection<StorePurchase>> view_store_purchases_history(int store_id) {
        Response<Collection<StorePurchase>> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            String user_email = this.user_controller.get_email(this.loggedUser);
            Collection<StorePurchase> answer = this.store_controller.view_store_purchases_history(user, store_id).getPurchaseID_purchases().values();
            response = new Response<>(answer, "Store purchases history received successfully");
            system_logger.add_log("User received (" + user_email + ") store's (" + store_id + ") purchase history successfully.");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.6.1
     *
     * @param store_id we want to close
     * @return success/failure message
     * @preconition the store is open
     */
    // TODO: ask bar for double use in 2 controllers.
    public Response<String> close_store_permanently(int store_id) {
        Response<String> response = null;
        try {
            synchronized (lock) {
                user_controller.check_admin_permission(loggedUser); // throws
                this.store_controller.close_store_permanently(store_id);
                response = new Response<>(null, "Store closed permanently");
                system_logger.add_log("Store (" + store_id + ") closed permanently.");
            }
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }


    /**
     * Requirement 2.6.2
     *
     * @param email of the user we want to cancel his subscribe
     * @return success/failure message
     */

    public Response<String> remove_user(String email) {
        Response<String> response = null;
        try {
            // TODO: version 2
            // close store permanently
            user_controller.remove_user(loggedUser, email);
            // remove user from all owners and managers
            // remove all users complains & questions
            response = new Response<>(email, email + " Has been removed successfully from the system");
            system_logger.add_log("Removed user (" + email + ") from the system.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.6.3
     *
     * @return all the questions for an admin or failure message
     */

    public Response<List<String>> admin_view_users_questions() {
        Response<List<String>> response = null;
        try {
            this.user_controller.view_users_questions(loggedUser);
            List<String> users_questions = this.user_controller.view_users_questions(loggedUser);
            response = new Response(users_questions, "Admin received users complains successfully.");
            system_logger.add_log("Admin viewed users complains successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.6.3
     *
     * @param question_id answer a specific question
     * @param answer      of the admin
     * @return success/failure message
     */


    public Response<String> admin_answer_user_question(int question_id, String answer) {
        Response<String> response = null;
        try {
            user_controller.check_admin_permission(loggedUser); // throws
            this.user_controller.answer_user_question(loggedUser, question_id, answer);
            response = new Response<>(null, "Admin answered user complaint successfully.");
            system_logger.add_log("Admin answered user's complaint successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.6.4
     *
     * @param store_id who we want to see her purchases
     * @return purchases history information or failure message
     */

    public Response<StorePurchaseHistory> admin_view_store_purchases_history(int store_id) {
        Response<StorePurchaseHistory> response = null;
        try {
            user_controller.check_admin_permission(loggedUser); // throws
            StorePurchaseHistory answer = this.store_controller.admin_view_store_purchases_history(store_id);
            response = new Response<>(answer, "Store purchases history received successfully");
            system_logger.add_log("Admin received store's (" + store_id + ") purchase history successfully.");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.6.4
     *
     * @param user_email who we want to see his purchases
     * @return purchases history information or failure message
     */

    public Response<UserPurchaseHistory> admin_view_user_purchases_history(String user_email) {
        Response<UserPurchaseHistory> response = null;
        try {
            UserPurchaseHistory userPurchaseHistory = user_controller.admin_view_user_purchase_history(loggedUser, user_email);
            response = new Response<>(userPurchaseHistory, "received user's purchase history successfully");
            system_logger.add_log("Admin received user's (" + user_email + ") purchase history successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    /**
     * Requirement 2.6.5
     *
     * @return stats of the market or failure message
     */

    public Response<Statistic> get_market_stats() {
        Response<Statistic> response = null;
        try {
            Statistic stats = user_controller.get_statistics(loggedUser);
            response = new Response(stats, "Received market statistics successfully");
            system_logger.add_log("Admin received market statistics successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response get_all_stores() {
        Response<List<StoreInformation>> response = null;
        try {
            Map<Integer, Store> stores = store_controller.get_all_stores();
            List<StoreInformation> map = new ArrayList<>();
            for (Map.Entry<Integer, Store> en : stores.entrySet()) {
                map.add(new StoreInformation(en.getValue()));
            }
            response = new Response(map, "Received market stores successfully");
            system_logger.add_log("received market stores successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response get_products_by_store_id(int store_id) {
        Response<List<ProductInformation>> response = null;
        try {
            List<Product> products = store_controller.get_products_by_store_id(store_id);
            List<ProductInformation> products_information = new ArrayList<>();
            for (Product p : products) {
                products_information.add(new ProductInformation(p, 0));
            }
            response = new Response(products_information, "Received store products successfully");
            system_logger.add_log("received market stores successfully.");
        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    // TODO: testing functions

    public boolean is_logged() {
        return !isGuest;
    }


    public User get_user_for_tests() {
        return user_controller.get_user_for_tests(loggedUser);
    }

    //TODO: function that clears system for testing

    public void clear() {
        user_controller.clear();
        store_controller.clear();
    }

    public Response get_user_questions() {
        Response<List<String>> response = null;
        try {
            String user_email = this.user_controller.get_email(this.loggedUser);
            List<String> user_questions = QuestionController.getInstance().get_all_user_questions(user_email);
            response = new Response<>(user_questions, "user questions received successfully");
            system_logger.add_log("User's (" + user_email + ") questions has been viewed.");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response edit_product_quantity(int product_id, int store_id, int quantity) {
        Response<String> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            this.store_controller.edit_product_quantity(user, product_id, store_id, quantity);
            response = new Response<>(null, "Product quantity edit successfully");
            system_logger.add_log("Product (" + product_id + ") quantity has been changed to " + quantity + " in store (" + store_id + ")");

        } catch (Exception e) {
            response = Utils.CreateResponse(e);
            error_logger.add_log(e);
        }
        return response;
    }

    public Response online_user() {
        Response<UserInformation> response = null;
        try {
            User user = user_controller.get_user(loggedUser);
            UserInformation userInformation = new UserInformation(user);
            response = new Response<>(userInformation, "");
        } catch (Exception e) {
            response = Utils.CreateResponse(new MarketException("failed to fetch user - connection error"));
            error_logger.add_log(e);
        }
        return response;
    }
}

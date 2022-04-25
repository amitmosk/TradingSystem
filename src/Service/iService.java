package Service;

import Domain.StoreModule.*;

import java.util.LinkedList;
import java.util.List;

public interface iService {
    void init_market();
    boolean payment(int price);
    boolean supply(int user_id, int purchase_id);
    // users
    double guest_login();
    double login(String username, String password);
    double logout(); // have to split according to user state
    double register();
    // guests
    String (int store_id);
    String find_product_information(int product_id, int store_id);
    List<Product> find_products_by_name(String name);find_store_information
    List<Product> find_products_by_category(String category);
    List<Product> find_products_by_keywords(String key_words);

    double view_user_cart();
    double delete_product_from_cart();
    double add_product_to_cart();
    double edit_product_from_cart(); // change quantity, etc...
    int buy_cart(); // calculate price, call payment, supply, add to history, reset cart..

    // assign user
    void open_store(String store_name); // first opening
    void add_review(int product_id, int store_id, String review);
    int rate_product(int product_id);
    int rate_store(int store_id);
    int send_request_to_store(int store_id, String request);
    double send_complain();
    double view_user_purchase_history();
    double view_account_details();
    double edit_account_details();
    // improve security
    double add_security_personal_question(); // add more methods like that

    // store owner - have to write which of these methods is common to store founder & store manager

    void add_product_to_store(int store_id, int quantity, String name, double price, String category, List<String> key_words);
    void delete_product_from_store(int product_id);

    //------------------------------------------------ edit product - Start ----------------------------------------------

    void edit_product_name(int product_id, int store_id, String name);
    void edit_product_price(int product_id, int store_id, double price);
    void edit_product_category(int product_id,int store_id, String category);
    void edit_product_key_words(int product_id,int store_id, List<String> key_words);
    //------------------------------------------------ edit product - End ----------------------------------------------

    void set_store_purchase_rules(int store_id);
    void add_owner(String user_email, int store_id);
    int add_manager();
    int delete_owner();
    int delete_manager();
    // @TODO : Amit
    void edit_manager_permissions(int user_id, int manager_id, int store_id, LinkedList<StorePermission> permissions);
    boolean close_store_temporarily(int store_id, int user_id);
    boolean open_close_store(int store_id, int user_id); // re-open
    String view_store_management_information(int user_id, int store_id);
    String view_store_questions(int store_id, int user_id);
    boolean manager_answer_question(int store_id, int user_id, int question_id, String answer);
    String view_store_purchases_history(int store_id, int user_id); // also admin method

    // admin
    boolean close_store_permanently(int store_id, int user_id);
    double delete_user_from_system();
    double view_system_questions();
    double admin_answer_question();
    int get_system_statistics();





}
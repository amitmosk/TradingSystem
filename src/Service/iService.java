package Service;

import Domain.StoreModule.Product;
import Domain.StoreModule.StorePermission;

import java.util.LinkedList;

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
    String find_store_information(int store_id);
    String find_product_information(int product_id);
    Product find_product_by_name(String name);
    Product find_product_by_category(String category);
    Product find_product_by_keyword(String key_word);

    double view_user_cart();
    double delete_product_from_cart();
    double add_product_to_cart();
    double edit_product_from_cart(); // change quantity, etc...
    int buy_cart(); // calculate price, call payment, supply, add to history, reset cart..

    // assign user
    int open_store(); // first opening
    int add_review(int product_id);
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
    void add_product_to_store(Product product, int store_id);
    void delete_product_from_store();
    void edit_product(int store_id, int product_id);
    void add_discount_rule();
    void delete_discount_rule();
    void add_purchase_rule();
    void delete_purchase_rule();






    int add_owner();
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
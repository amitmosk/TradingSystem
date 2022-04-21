package Service;

public interface Iservice {
    void init_market();
    boolean payment();
    void supply();
    // users
    void guest_login();
    void login(String username, String password);
    void logout(); // have to split according to user state
    void register();
    // guests
    void find_store_information();
    void find_product_information();
    void find_product_by_name();
    void find_product_by_category();
    void find_product_by_keyword();
    void view_cart();
    void delete_product_from_cart();
    void add_product_to_cart();
    void edit_product_from_cart(); // change quantity, etc...
    void buy_cart(); // calculate price, call payment, supply, add to history, reset cart..

    // assign user
    void open_store(); // first opening
    void add_review(int product_id);
    void rate_product(int product_id);
    void rate_store(int store_id);
    void send_request_to_store(int store_id, String request);
    void send_complain();
    void view_user_purchase_history();
    void view_account_details();
    void edit_account_details();
    // improve security
    void add_security_personal_question(); // add more methods like that

    // store owner - have to write which of this methods is common to store founder & store manager
    void add_product_to_store();
    void delete_product_from_store();
    void edit_product(int store_id, int product_id);
    void add_discount_rule();
    void delete_discount_rule();
    void add_purchase_rule();
    void delete_purchase_rule();
    void add_owner();
    void add_manager();
    void delete_owner();
    void delete_manager();
    void edit_manager_permissions();
    void close_store();
    void open_close_store(); // re-open
    void view_store_management_information();
    void view_store_questions();
    void manager_answer_question();
    void view_store_purchases_history(); // also admin method

    // admin
    void close_store_permanentily();
    void delete_user_from_system();
    void view_system_questions();
    void admin_answer_question();
    void get_system_statistics();





}

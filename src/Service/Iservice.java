package Service;

public interface iService {
    void init_market();
    boolean payment();
    void supply();
    // users
    double guest_login();
    double login(String username, String password);
    double logout(); // have to split according to user state
    double register();
    // guests
    void find_store_information();
    void find_product_information();
    void find_product_by_name();
    void find_product_by_category();
    void find_product_by_keyword();

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

    // store owner - have to write which of this methods is common to store founder & store manager
    void add_product_to_store();
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
    //@TODO
    void edit_manager_permissions();
    void close_store();
    void open_close_store(); // re-open
    void view_store_management_information();
    void view_store_questions();
    void manager_answer_question();
    void view_store_purchases_history(); // also admin method

    // admin
    void close_store_permanently();
    double delete_user_from_system();
    double view_system_questions();
    double admin_answer_question();
    int get_system_statistics();





}
package Service;

public interface IMarket {
    void init_market();

    boolean payment(int price);

    boolean supply(int user_id, int purchase_id);

    // users
    User guest_login();

    double login(String Email, String password);

    void logout(); // have to split according to user state

    double register();

    // guests
    String find_store_information(int store_id);

    String find_product_information(int product_id);

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
}

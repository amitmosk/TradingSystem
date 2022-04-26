package Domain;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Product;
import Domain.StoreModule.StorePermission;
import Domain.UserModule.UserHistory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface IMarket {
    //Requirement 1.1
    void init_market();

    boolean payment(int price);

    boolean supply(int user_id, int purchase_id);

    int guest_login();

    double login(String Email, String password);

    void logout();

    double register(String Email, String pw, String name, String lastName) throws IllegalAccessException;

    String find_store_information(int store_id);

    String find_product_information(int product_id, int store_id);
    //Requirement 2.2.2 - Name

    List<Product> find_products_by_name(String product_name);
    //Requirement 2.2.2 - Category

    List<Product> find_products_by_category(String category);
    //Requirement 2.2.2 - Key_words

    List<Product> find_products_by_keywords(String key_words);
    //Requirement 2.3.2

    void open_store(String store_name);
    //Requirement 2.3.3

    void add_review(int product_id, int store_id, String review);

    void rate_product(int product_id, int store_id, int rate);

    void rate_store(int store_id, int rate);

    // @TODO : AMIT implement
    void send_question_to_store(int store_id, String question);

    void add_product_to_store(int store_id, int quantity,
                              String name, double price, String category, List<String> key_words);
    //Requirement 2.4.1 - Delete

    //maybe return the deleted product
    void delete_product_from_store(int product_id, int store_id);

    //Requirement 2.4.1 - Edit name
    void edit_product_name(int product_id, int store_id, String name);
    //Requirement 2.4.1 - Edit Price

    void edit_product_price(int product_id, int store_id, double price);
    //Requirement 2.4.1 - Edit category

    void edit_product_category(int product_id, int store_id, String category);
    //Requirement 2.4.1 - Edit key words

    void edit_product_key_words(int product_id, int store_id, List<String> key_words);

    void set_store_purchase_rules(int store_id);

    // @TODO : have to check that there is at least one owner of each store
    void add_owner(String user_email_to_appoint, int store_id);

    int delete_owner();

    int add_manager();

    void edit_manager_permissions(String manager_email, int store_id, LinkedList<StorePermission> permissions);

    int delete_manager();

    void close_store_temporarily(int store_id);

    void open_close_store(int store_id);

    String view_store_management_information(int store_id);

    String view_store_questions(int store_id);

    void add_product_to_cart(int storeID, int productID, int quantity);

    void edit_product_quantity_in_cart(int storeID, int productID, int quantity);

    void remove_product_from_cart(int storeID, int productID);

    Map<Integer, Basket> view_user_cart();

    int buy_cart();

    double send_complain();

    UserHistory view_user_purchase_history() throws Exception;

    double view_account_details();

    String get_user_email() throws Exception;

    String get_user_name() throws Exception;

    String get_user_last_name() throws Exception;
}

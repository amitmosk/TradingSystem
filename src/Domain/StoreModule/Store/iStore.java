package Domain.StoreModule.Store;

import Domain.StoreModule.Appointment;
import Domain.StoreModule.Basket;
import Domain.StoreModule.Product.Product;
import Domain.StoreModule.StorePermission;

import java.util.LinkedList;
import java.util.List;

public interface iStore {
    void add_product_review(int product_id, String user_email, String review);

    void add_store_rating(String user_email, int rating);

    void add_product_rating(String user_email, int product_id, int rate);

    void open_store();

    void close_store_permanently();

    void close_store_temporarily(String user_email) throws IllegalAccessException;

    void open_close_store(String user_email) throws IllegalAccessException;

    StoreManagersInfo view_store_management_information(String user_email) throws IllegalAccessException;

    boolean is_active();

    void set_permissions(String user_email, String manager_email, LinkedList<StorePermission> permissions) throws IllegalAccessException;

    List<String> view_store_questions(String user_email) throws IllegalAccessException;

    void add_question(String user_email, String question_message);

    void answer_question(String user_email, int question_id, String answer) throws IllegalAccessException;

    String view_store_purchases_history(String user_email) throws IllegalAccessException;

    // -- find product by ----------------------------------------------------------------------------------

    List<Product> find_products_by_name(String product_name);

    List<Product> find_products_by_category(String category);

    List<Product> find_products_by_key_words(String key_words);

    void add_product(String user_email, String name, double price, String category, List<String> key_words, int quantity) throws IllegalAccessException;

    void delete_product(int product_id, String user_email) throws IllegalAccessException;

    void edit_product_name(String user_email, int product_id, String name) throws IllegalAccessException;

    void edit_product_price(String user_email, int product_id, double price) throws IllegalAccessException;

    void edit_product_category(String user_email, int product_id, String category) throws IllegalAccessException;

    void edit_product_key_words(String user_email, int product_id, List<String> key_words) throws IllegalAccessException;

    double check_available_products_and_calc_price(Basket basket);

    void remove_basket_products_from_store(Basket basket, int purchase_id);

    void add_owner(String user_email, String user_email_to_appoint) throws IllegalAccessException;

    void add_manager(String user_email, String user_email_to_appoint) throws IllegalAccessException;

    void remove_manager(String user_email, String user_email_to_delete_appointment) throws IllegalAccessException;

    void remove_owner(String user_email, String user_email_to_delete_appointment) throws IllegalAccessException;

    Product getProduct_by_product_id(int product_id);


}

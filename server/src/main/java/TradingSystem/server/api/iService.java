package TradingSystem.server.api;



import java.util.LinkedList;
import java.util.List;

public interface iService {



    /**
     * Requirement 2.1.2
     */
    Response logout();

    /**
     * Requirement 2.1.3
     */
    Response register(String Email, String pw, String name, String lastName);

    /**
     * Requirement 2.1.4
     */
    Response login(String Email, String password);

    /**
     * Requirement 2.2.1
     */
    Response find_store_information(int store_id);

    Response find_product_information(int product_id, int store_id);

    /**
     * Requirement 2.2.2
     */
    Response find_products_by_name(String product_name);

    Response find_products_by_category(String category);

    Response find_products_by_keywords(String key_words);

    /**
     * Requirement 2.2.3
     */
    Response add_product_to_cart(int storeID, int productID, int quantity);

    Response remove_product_from_cart(int storeID, int productID);

    /**
     * Requirement 2.2.4
     */
    Response view_user_cart();

    Response edit_product_quantity_in_cart(int storeID, int productID, int quantity);

    /**
     * Requirement 2.2.5
     */

    Response buy_cart(String paymentInfo, String supplyInfo);

    /**
     * Requirement 2.3.2
     */
    Response open_store(String store_name);


    /**
     * Requirement 2.3.3
     */
    Response add_product_review(int product_id, int store_id, String review);

    /**
     * Requirement 2.3.4
     */
    Response rate_product(int product_id, int store_id, int rate);

    Response rate_store(int store_id, int rate);

    /**
     * Requirement 2.3.5
     */
    Response send_question_to_store(int store_id, String question);

    /**
     * Requirement 2.3.6
     */
    Response send_question_to_admin(String question); // wrong signature

    /**
     * Requirement 2.3.7
     */
    Response view_user_purchase_history();


    /**
     * Requirement 2.3.8
     */
    Response get_user_email();
    Response get_user_name();
    Response get_user_last_name();

    Response unregister(String password);
    Response edit_name(String pw, String new_name);
    Response edit_last_name(String pw, String new_last_name);
    Response edit_password(String pw, String password);



    /**
     * Requirement 2.3.9
     */

    Response edit_name_premium(String pw, String new_name, String answer);
    Response edit_last_name_premium(String pw, String new_last_name, String answer);
    Response edit_password_premium(String pw, String password, String answer);

    Response get_user_security_question();
    Response improve_security(String password,String question, String answer);

    /**
     * Requirement 2.4.1
     */


    Response add_product_to_store(int store_id, int quantity, String name, double price,
                                String category, List<String> key_words);

    Response delete_product_from_store(int product_id, int store_id);

    Response edit_product_name(int product_id, int store_id, String name);

    Response edit_product_price(int product_id, int store_id, double price);

    Response edit_product_category(int product_id, int store_id, String category);

    Response edit_product_key_words(int product_id, int store_id, List<String> key_words);

    /**
     * Requirement 2.4.2
     */
//    Response set_store_purchase_policy(int store_id, PurchasePolicy policy);
//
//    Response set_store_discount_policy(int store_id, DiscountPolicy policy);

    /**
     * Requirement 2.4.3
     */
//    Response set_store_purchase_rules(int store_id, Rule rule);

    /**
     * Requirement 2.4.4
     */
    Response add_owner(String user_email_to_appoint, int store_id);

    /**
     * Requirement 2.4.5
     */
    Response delete_owner(String user_email_to_delete_appointment, int store_id);

    /**
     * Requirement 2.4.6
     */
    Response add_manager(String user_email_to_appoint, int store_id);

    /**
     * Requirement 2.4.7
     */
//    Response edit_manager_permissions(String manager_email, int store_id, LinkedList<StorePermission> permissions);

    /**
     * Requirement 2.4.8
     */
    Response delete_manager(String user_email_to_delete_appointment, int store_id);

    /**
     * Requirement 2.4.9
     */
    Response close_store_temporarily(int store_id);

    /**
     * Requirement 2.4.10
     */
    Response open_close_store(int store_id);

    /**
     * Requirement 2.4.11
     */
    Response view_store_management_information(int store_id);

    /**
     * Requirement 2.4.12
     */
    Response manager_view_store_questions(int store_id);

    Response manager_answer_question(int store_id, int question_id, String answer);

    /**
     * Requirement 2.4.13
     */
    Response view_store_purchases_history(int store_id);

    /**
     * Requirement 2.6.1
     */
    Response close_store_permanently(int store_id);


    /**
     * Requirement 2.6.2
     */
    Response remove_user(String email);

    /**
     * Requirement 2.6.3
     */
    Response admin_view_users_questions();

    Response admin_answer_user_question(int question_id, String answer);

    /**
     * Requirement 2.6.4
     */
    Response admin_view_store_purchases_history(int store_id);
    Response admin_view_user_purchases_history(String user_email);

    /**
     * Requirement 2.6.5
     */
    Response get_market_stats();

}

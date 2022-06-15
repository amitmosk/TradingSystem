package TradingSystem.server.Service;



import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternSystems.SupplyInfo;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.StorePermission;

import java.util.ArrayList;
import java.util.LinkedList;

public interface iService {


    /**
     * Requirement 2.1.2
     */
    TradingSystem.server.Domain.Utils.Response logout();

    /**
     * Requirement 2.1.3
     */
    TradingSystem.server.Domain.Utils.Response register(String Email, String pw, String name, String lastName, String birth_date);

    /**
     * Requirement 2.1.4
     *
     * @return
     */
    TradingSystem.server.Domain.Utils.Response login(String Email, String password);

    /**
     * Requirement 2.2.1
     */
    TradingSystem.server.Domain.Utils.Response find_store_information(int store_id);

    TradingSystem.server.Domain.Utils.Response find_product_information(int product_id, int store_id);

    /**
     * Requirement 2.2.2
     */
    TradingSystem.server.Domain.Utils.Response find_products_by_name(String product_name);

    TradingSystem.server.Domain.Utils.Response find_products_by_category(String category);

    TradingSystem.server.Domain.Utils.Response find_products_by_keywords(String key_words);

    /**
     * Requirement 2.2.3
     */
    TradingSystem.server.Domain.Utils.Response add_product_to_cart(int storeID, int productID, int quantity);

    TradingSystem.server.Domain.Utils.Response remove_product_from_cart(int storeID, int productID);

    /**
     * Requirement 2.2.4
     */
    TradingSystem.server.Domain.Utils.Response view_user_cart();

    TradingSystem.server.Domain.Utils.Response edit_product_quantity_in_cart(int storeID, int productID, int quantity);

    /**
     * Requirement 2.2.5.1
     */

    TradingSystem.server.Domain.Utils.Response buy_cart(String paymentInfo, String supplyInfo);

    /**
     * Requirement 2.2.5.2
     */

    TradingSystem.server.Domain.Utils.Response add_bid(int storeID, int productID, int quantity, double offer_price);

    TradingSystem.server.Domain.Utils.Response manager_answer_bid(int storeID, int bidID, boolean manager_answer, double negotiation_price);

    TradingSystem.server.Domain.Utils.Response view_bids_status(int storeID);


    /**
     * Requirement 2.3.2
     */
    TradingSystem.server.Domain.Utils.Response open_store(String store_name);


    /**
     * Requirement 2.3.3
     */
    TradingSystem.server.Domain.Utils.Response add_product_review(int product_id, int store_id, String review);

    /**
     * Requirement 2.3.4
     */
    TradingSystem.server.Domain.Utils.Response rate_product(int product_id, int store_id, int rate);

    TradingSystem.server.Domain.Utils.Response rate_store(int store_id, int rate);

    /**
     * Requirement 2.3.5
     */
    TradingSystem.server.Domain.Utils.Response send_question_to_store(int store_id, String question);

    /**
     * Requirement 2.3.6
     */
    TradingSystem.server.Domain.Utils.Response send_question_to_admin(String question); // wrong signature

    /**
     * Requirement 2.3.7
     */
    TradingSystem.server.Domain.Utils.Response view_user_purchase_history();


    /**
     * Requirement 2.3.8
     */
    TradingSystem.server.Domain.Utils.Response get_user_email();

    TradingSystem.server.Domain.Utils.Response get_user_name();

    TradingSystem.server.Domain.Utils.Response get_user_last_name();

    TradingSystem.server.Domain.Utils.Response unregister(String password);

    TradingSystem.server.Domain.Utils.Response edit_name(String new_name);

    TradingSystem.server.Domain.Utils.Response edit_last_name(String new_last_name);

    TradingSystem.server.Domain.Utils.Response edit_password(String pw, String password);


    /**
     * Requirement 2.3.9
     */

    TradingSystem.server.Domain.Utils.Response edit_name_premium(String new_name, String answer);

    TradingSystem.server.Domain.Utils.Response edit_last_name_premium(String new_last_name, String answer);

    TradingSystem.server.Domain.Utils.Response edit_password_premium(String pw, String password, String answer);

    TradingSystem.server.Domain.Utils.Response get_user_security_question();

    TradingSystem.server.Domain.Utils.Response improve_security(String password, String question, String answer);

    /**
     * Requirement 2.4.1
     */


    TradingSystem.server.Domain.Utils.Response add_product_to_store(int store_id, int quantity, String name, double price,
                                                                    String category, String key_words);

    TradingSystem.server.Domain.Utils.Response delete_product_from_store(int product_id, int store_id);

    TradingSystem.server.Domain.Utils.Response edit_product_name(int product_id, int store_id, String name);

    TradingSystem.server.Domain.Utils.Response edit_product_price(int product_id, int store_id, double price);

    TradingSystem.server.Domain.Utils.Response edit_product_category(int product_id, int store_id, String category);

    TradingSystem.server.Domain.Utils.Response edit_product_key_words(int product_id, int store_id, String key_words);

    TradingSystem.server.Domain.Utils.Response edit_product_quantity(int product_id, int store_id, int quantity);

    /**
     * Requirement 2.4.2
     */
//    TradingSystem.server.Domain.Utils.Response set_store_purchase_policy(int store_id, PurchasePolicy policy);
//
//    TradingSystem.server.Domain.Utils.Response set_store_discount_policy(int store_id, DiscountPolicy policy);

    /**
     * Requirement 2.4.3
     */
//    TradingSystem.server.Domain.Utils.Response set_store_purchase_rules(int store_id, Rule rule);

    /**
     * Requirement 2.4.4
     */
    TradingSystem.server.Domain.Utils.Response add_owner(String user_email_to_appoint, int store_id);

    /**
     * Requirement 2.4.5
     */
    TradingSystem.server.Domain.Utils.Response delete_owner(String user_email_to_delete_appointment, int store_id);

    /**
     * Requirement 2.4.6
     */
    TradingSystem.server.Domain.Utils.Response add_manager(String user_email_to_appoint, int store_id);

    /**
     * Requirement 2.4.7
     */
    TradingSystem.server.Domain.Utils.Response edit_manager_permissions(String manager_email, int store_id, String permissions);

    TradingSystem.server.Domain.Utils.Response get_permissions(String manager_email, int store_id);

    TradingSystem.server.Domain.Utils.Response get_all_categories(int store_id);

    /**
     * Requirement 2.4.8
     */
    TradingSystem.server.Domain.Utils.Response delete_manager(String user_email_to_delete_appointment, int store_id);

    /**
     * Requirement 2.4.9
     */
    TradingSystem.server.Domain.Utils.Response close_store_temporarily(int store_id);

    /**
     * Requirement 2.4.10
     */
    TradingSystem.server.Domain.Utils.Response open_close_store(int store_id);

    /**
     * Requirement 2.4.11
     */
    TradingSystem.server.Domain.Utils.Response view_store_management_information(int store_id);

    /**
     * Requirement 2.4.12
     */
    TradingSystem.server.Domain.Utils.Response manager_view_store_questions(int store_id);

    TradingSystem.server.Domain.Utils.Response manager_answer_question(int store_id, int question_id, String answer);

    /**
     * Requirement 2.4.13
     */
    TradingSystem.server.Domain.Utils.Response view_store_purchases_history(int store_id);

    /**
     * Requirement 2.6.1
     */
    TradingSystem.server.Domain.Utils.Response close_store_permanently(int store_id);


    /**
     * Requirement 2.6.2
     */
    TradingSystem.server.Domain.Utils.Response remove_user(String email);

    /**
     * Requirement 2.6.3
     */
    TradingSystem.server.Domain.Utils.Response admin_view_users_questions();

    TradingSystem.server.Domain.Utils.Response admin_answer_user_question(int question_id, String answer);

    /**
     * Requirement 2.6.4
     */
    TradingSystem.server.Domain.Utils.Response admin_view_store_purchases_history(int store_id);

    TradingSystem.server.Domain.Utils.Response admin_view_user_purchases_history(String user_email);

    /**
     * Requirement 2.6.5
     */
    TradingSystem.server.Domain.Utils.Response get_market_stats();

    // additional methods

    TradingSystem.server.Domain.Utils.Response get_products_by_store_id(int store_id);

    TradingSystem.server.Domain.Utils.Response get_all_stores();

    TradingSystem.server.Domain.Utils.Response online_user();
    TradingSystem.server.Domain.Utils.Response save_notifications(String notification);
    TradingSystem.server.Domain.Utils.Response get_notifications_list();


    TradingSystem.server.Domain.Utils.Response add_predict(int store_id, String categorey, int product_id, boolean above, boolean equql, int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, String name);
    TradingSystem.server.Domain.Utils.Response remove_predict(int store_id, String predict_name);
    TradingSystem.server.Domain.Utils.Response get_purchase_policy(int store_id);
    TradingSystem.server.Domain.Utils.Response send_predicts(int store_id);
    TradingSystem.server.Domain.Utils.Response get_discount_policy(int store_id);




    //--------------------------------------------------Discount Rules-----------------------------------

    TradingSystem.server.Domain.Utils.Response add_complex_discount_rule(int store_id, String nameOfPredict, String nameOfPolicy, String nameOfRule);
    TradingSystem.server.Domain.Utils.Response add_simple_category_discount_rule(int store_id, String nameOfCategory, double percent, String nameOfRule);
    TradingSystem.server.Domain.Utils.Response add_simple_product_discount_rule(int store_id, int id, double percent, String nameOfrule);
    TradingSystem.server.Domain.Utils.Response add_simple_store_discount_rule(int store_id, double percent, String nameOfRule);





    TradingSystem.server.Domain.Utils.Response add_and_discount_rule(String left, String right, int store_id, String NameOfRule);
    TradingSystem.server.Domain.Utils.Response add_or_discount_rule(String left, String right, int store_id, String NameOfRule);
    TradingSystem.server.Domain.Utils.Response add_max_discount_rule(String left, String right, int store_id, String NameOfRule);
    TradingSystem.server.Domain.Utils.Response add_plus_discount_rule(String left, String right, int store_id, String NameOfRule);
    TradingSystem.server.Domain.Utils.Response add_xor_discount_rule(String left, String right, int store_id, String NameOfRule);
    TradingSystem.server.Domain.Utils.Response remove_discount_rule(int store_id, String name);




    //--------------------------------------------------Purchase Rules-----------------------------------

    TradingSystem.server.Domain.Utils.Response add_simple_purchase_rule(String PredictName, String NameOfRule, int store_id);
    TradingSystem.server.Domain.Utils.Response add_and_purchase_rule(String left, String right, int store_id, String NameOfrule);
    TradingSystem.server.Domain.Utils.Response add_or_purchase_rule(String left, String right, int store_id, String nameOfrule);
    TradingSystem.server.Domain.Utils.Response remove_purchase_rule(int store_id, String name);
}

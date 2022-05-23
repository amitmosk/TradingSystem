package TradingSystem.server.Domain.Facade;

import TradingSystem.server.Domain.Statistics.Statistic;
import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.SimpleporchaseRule;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.porchaseRule;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.StorePermission;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Domain.Utils.Utils;

import java.util.List;
import java.util.Map;

public interface IFacade {
    Object lock = new Object();

    Response<String> logout();

    Response<User> register(String Email, String pw, String name, String lastName, String birth_date);

    Response<User> login(String Email, String password);

    Response<StoreInformation> find_store_information(int store_id);

    Response<ProductInformation> find_product_information(int product_id, int store_id);

    Response<List<Product>> find_products_by_name(String product_name);

    Response<List<Product>> find_products_by_category(String category);

    Response<List<Product>> find_products_by_keywords(String key_words);

    Response<String> add_product_to_cart(int storeID, int productID, int quantity);

    Response<String> edit_product_quantity_in_cart(int storeID, int productID, int quantity);

    Response<String> remove_product_from_cart(int storeID, int productID);

    Response<Map<Store, Basket>> view_user_cart();

    Response<UserPurchase> buy_cart(String paymentInfo, String SupplyInfo);

    Response<String> open_store(String store_name);

    Response<String> add_product_review(int product_id, int store_id, String review);

    Response<String> rate_product(int product_id, int store_id, int rate);

    Response<String> rate_store(int store_id, int rate);

    Response<String> send_question_to_store(int store_id, String question);

    Response send_question_to_admin(String question);

    Response<UserPurchaseHistory> view_user_purchase_history();

    Response<String> get_user_email();

    Response<String> get_user_name();

    Response<String> get_user_last_name();

    Response<String> edit_password(String old_password, String password);

    Response<String> edit_name(String pw, String new_name);

    Response<String> edit_last_name(String pw, String new_last_name);

    Response<String> unregister(String password);


    Response<String> edit_name_premium(String pw, String new_name, String answer);

    Response<String> edit_last_name_premium(String pw, String new_last_name, String answer);

    Response<String> edit_password_premium(String old_password, String new_password, String answer);

    Response<String> get_user_security_question();

    Response<String> improve_security(String password, String question, String answer);

    Response<Map<Product, Integer>> add_product_to_store(int store_id, int quantity,
                                                         String name, double price, String category, List<String> key_words);

    Response<Map<Product, Integer>> delete_product_from_store(int product_id, int store_id);

    Response add_predict(int store_id, String catgorey, Product product, boolean above, boolean equql, int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, String name);

    Response send_to_user_purchase_policy(int store_id);

    Response send_predicts(int store_id);

    Response send_to_user_discount_policy(int store_id);

    Response add_complex_discount_rule(int store_id, String nameOfPredict, String nameOfPolicy);

    Response add_simple_categorey_discount_rule(int store_id, String name, double precent);

    Response add_simple_product_discount_rule(int store_id, int id, double precent);

    Response add_simple_store_discount_rule(int store_id, String type, String name, double precent);

     Response<String> remove_discount_rule(int store_id, String name) ף

    Response<SimpleporchaseRule> add_simple_purchase_rule(String PredictName, String NameOfRule, int store_id);

    Response<porchaseRule> add_and_purchase_rule(String left, String right, int store_id);

    Response<porchaseRule> add_or_purchase_rule(String left, String right, int store_id);

    Response<String> edit_product_name(int product_id, int store_id, String name);

    Response<String> edit_product_price(int product_id, int store_id, double price);

    Response<String> edit_product_category(int product_id, int store_id, String category);

    Response<String> edit_product_key_words(int product_id, int store_id, List<String> key_words);

    Response<String> add_owner(String user_email_to_appoint, int store_id);

    Response delete_owner(String user_email_to_delete_appointment, int store_id);

    Response add_manager(String user_email_to_appoint, int store_id);

    Response<String> edit_manager_permissions(String manager_email, int store_id, List<StorePermission> permissions);

    Response<String> delete_manager(String user_email_to_delete_appointment, int store_id);

    Response<String> close_store_temporarily(int store_id);

    Response<String> open_close_store(int store_id);

    Response<String> view_store_management_information(int store_id);

    Response<List<String>> manager_view_store_questions(int store_id);

    Response<String> manager_answer_question(int store_id, int question_id, String answer);

    Response<StorePurchaseHistory> view_store_purchases_history(int store_id);

    Response<String> close_store_permanently(int store_id);

    Response<String> remove_user(String email);

    Response<List<String>> admin_view_users_questions();

    Response<String> admin_answer_user_question(int question_id, String answer);

    Response<StorePurchaseHistory> admin_view_store_purchases_history(int store_id);

    Response<UserPurchaseHistory> admin_view_user_purchases_history(String user_email);

    Response<Statistic> get_market_stats();

    boolean is_logged();

    User get_user_for_tests();

    void clear();
}

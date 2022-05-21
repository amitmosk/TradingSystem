package TradingSystem.server.Service;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.UserModule.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import TradingSystem.server.Domain.Utils.Response;

import java.util.List;

@RestController
public class Service implements iService {

    private static Service service = null;

    private MarketFacade marketFacade;
    private NotificationHandler notificationHandler;

    private Service() {
        // -- Market init
        MarketSystem system = new MarketSystem();
        PaymentAdapter paymentAdapter = system.getPayment_adapter();
        SupplyAdapter supplyAdapter = system.getSupply_adapter();
        this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        this.notificationHandler = new NotificationHandler();

    }

    public synchronized static Service getInstance() {
        if (service == null)
            service = new Service();
        return service;
    }

    @RequestMapping(value = "/amit")
    @CrossOrigin
    public String amit(String a) {
        int e=4;
        System.out.println("string amit");
        Response<String> res  = new Response<String>("hello "+a,"yess" );
//        return "hello "+a;
        return "new Gson().toJson(res);";
    }


    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String email, String password) {
        Response answer = marketFacade.login(email, password);
        this.notificationHandler.send_waiting_notifications(email);
        return answer;
    }

    @RequestMapping(value = "/logout")
    @CrossOrigin
    @Override
    public Response logout() {
        Response answer = marketFacade.logout();
        return answer;
    }

    @RequestMapping(value = "/register")
    @CrossOrigin
    @Override
    public Response<User> register(String email, String pw, String name, String lastName, String birth_date) {
        Response<User> answer = marketFacade.register(email, pw, name, lastName, birth_date);
//        Response<Demi> answer = new Response<>(new Demi(),"dad");
//        Response<User> answer = new Response<>(new User(),"dad");
        return answer;
    }


    @RequestMapping(value = "/find_store_information")
    @CrossOrigin
    @Override
    public Response find_store_information(int store_id) {
        Response answer = marketFacade.find_store_information(store_id);
        return answer;
    }

    @RequestMapping(value = "/find_product_information")
    @CrossOrigin
    @Override
    public Response find_product_information(int product_id, int store_id) {
        Response answer = marketFacade.find_product_information(product_id, store_id);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_name")
    @CrossOrigin
    @Override
    public Response find_products_by_name(String product_name) {
        Response answer = marketFacade.find_products_by_name(product_name);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_category")
    @CrossOrigin
    @Override
    public Response find_products_by_category(String category) {
        Response answer = marketFacade.find_products_by_category(category);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_keywords")
    @CrossOrigin
    @Override
    public Response find_products_by_keywords(String key_words) {
        Response answer = marketFacade.find_products_by_keywords(key_words);
        return answer;

    }

    @RequestMapping(value = "/add_product_to_cart")
    @CrossOrigin
    @Override
    public Response add_product_to_cart(int storeID, int productID, int quantity) {
        Response answer = marketFacade.add_product_to_cart(storeID, productID, quantity);
        return answer;

    }

    @RequestMapping(value = "/remove_product_from_cart")
    @CrossOrigin
    @Override
    public Response remove_product_from_cart(int storeID, int productID) {
        Response answer = marketFacade.remove_product_from_cart(storeID, productID);
        return answer;
    }

    @RequestMapping(value = "/view_user_cart")
    @CrossOrigin
    @Override
    public Response view_user_cart() {
        Response answer = marketFacade.view_user_cart();
        return answer;
    }

    @RequestMapping(value = "/edit_product_quantity_in_cart")
    @CrossOrigin
    @Override
    public Response edit_product_quantity_in_cart(int storeID, int productID, int quantity) {
        Response answer = marketFacade.edit_product_quantity_in_cart(storeID, productID, quantity);
        return answer;
    }

    @RequestMapping(value = "/buy_cart(")
    @CrossOrigin
    @Override
    public Response buy_cart(String paymentInfo, String supplyInfo) {
        Response answer = marketFacade.buy_cart(paymentInfo, supplyInfo);
        return answer;
    }

    @RequestMapping(value = "/open_store")
    @CrossOrigin
    @Override
    public Response open_store(String store_name) {
        Response answer = marketFacade.open_store(store_name);
        return answer;
    }

    @RequestMapping(value = "/add_product_review")
    @CrossOrigin
    @Override
    public Response add_product_review(int product_id, int store_id, String review) {
        Response answer = marketFacade.add_product_review(product_id, store_id, review);
        return answer;
    }

    @RequestMapping(value = "/rate_product")
    @CrossOrigin
    @Override
    public Response rate_product(int product_id, int store_id, int rate) {
        Response answer = marketFacade.rate_product(product_id, store_id, rate);
        return answer;
    }

    @RequestMapping(value = "/rate_store")
    @CrossOrigin
    @Override
    public Response rate_store(int store_id, int rate) {
        Response answer = marketFacade.rate_store(store_id, rate);
        return answer;
    }

    @RequestMapping(value = "/send_question_to_store")
    @CrossOrigin
    @Override
    public Response send_question_to_store(int store_id, String question) {
        Response answer = marketFacade.send_question_to_store(store_id, question);
        return answer;
    }

    @RequestMapping(value = "/send_question_to_admin")
    @CrossOrigin
    @Override
    public Response send_question_to_admin(String question) {
        Response answer = marketFacade.send_question_to_admin(question);
        return answer;
    }

    @RequestMapping(value = "/view_user_purchase_history")
    @CrossOrigin
    @Override
    public Response view_user_purchase_history() {
        Response answer = marketFacade.view_user_purchase_history();
        return answer;
    }

    @RequestMapping(value = "/get_user_email")
    @CrossOrigin
    @Override
    public Response get_user_email() {
        Response answer = marketFacade.get_user_email();
        return answer;
    }

    @RequestMapping(value = "/get_user_name")
    @CrossOrigin
    @Override
    public Response get_user_name() {
        Response answer = marketFacade.get_user_name();
        return answer;
    }

    @RequestMapping(value = "/get_user_last_name")
    @CrossOrigin
    @Override
    public Response get_user_last_name() {
        Response answer = marketFacade.get_user_last_name();
        return answer;
    }

    @RequestMapping(value = "/unregister")
    @CrossOrigin
    @Override
    public Response unregister(String password) {
        Response answer = marketFacade.unregister(password);
        return answer;
    }

    @RequestMapping(value = "/edit_name")
    @CrossOrigin
    @Override
    public Response edit_name(String pw, String new_name) {
        Response answer = marketFacade.edit_name(pw, new_name);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name")
    @CrossOrigin
    @Override
    public Response edit_last_name(String pw, String new_last_name) {
        Response answer = marketFacade.edit_last_name(pw, new_last_name);
        return answer;
    }

    @RequestMapping(value = "/edit_password")
    @CrossOrigin
    @Override
    public Response edit_password(String old_pw, String password) {
        Response answer = marketFacade.edit_password(old_pw, password);
        return answer;
    }

    @RequestMapping(value = "/edit_name_premium")
    @CrossOrigin
    @Override
    public Response edit_name_premium(String pw, String new_name, String premAnswer) {
        Response answer = marketFacade.edit_name_premium(pw, new_name, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name_premium")
    @CrossOrigin
    @Override
    public Response edit_last_name_premium(String pw, String new_last_name, String premAnswer) {
        Response answer = marketFacade.edit_last_name_premium(pw, new_last_name, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/edit_password_premium")
    @CrossOrigin
    @Override
    public Response edit_password_premium(String pw, String password, String premAnswer) {
        Response answer = marketFacade.edit_password_premium(pw, password, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/get_user_security_question")
    @CrossOrigin
    @Override
    public Response get_user_security_question() {
        Response answer = marketFacade.get_user_security_question();
        return answer;
    }

    @RequestMapping(value = "/improve_security")
    @CrossOrigin
    @Override
    public Response improve_security(String password, String question, String premAnswer) {
        Response answer = marketFacade.improve_security(password, question, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/add_product_to_store")
    @CrossOrigin
    @Override
    public Response add_product_to_store(int store_id, int quantity, String name, double price, String category, List<String> key_words) {
        Response answer = marketFacade.add_product_to_store(store_id, quantity, name, price, category, key_words);
        return answer;
    }

    @RequestMapping(value = "/delete_product_from_store")
    @CrossOrigin
    @Override
    public Response delete_product_from_store(int product_id, int store_id) {
        Response answer = marketFacade.delete_product_from_store(product_id, store_id);
        return answer;
    }

    @RequestMapping(value = "/edit_product_name")
    @CrossOrigin
    @Override
    public Response edit_product_name(int product_id, int store_id, String name) {
        Response answer = marketFacade.edit_product_name(product_id, store_id, name);
        return answer;
    }

    @RequestMapping(value = "/edit_product_price")
    @CrossOrigin
    @Override
    public Response edit_product_price(int product_id, int store_id, double price) {
        Response answer = marketFacade.edit_product_price(product_id, store_id, price);
        return answer;
    }

    @RequestMapping(value = "/edit_product_category")
    @CrossOrigin
    @Override
    public Response edit_product_category(int product_id, int store_id, String category) {
        Response answer = marketFacade.edit_product_category(product_id, store_id, category);
        return answer;
    }

    @RequestMapping(value = "/edit_product_key_words")
    @CrossOrigin
    @Override
    public Response edit_product_key_words(int product_id, int store_id, List<String> key_words) {
        Response answer = marketFacade.edit_product_key_words(product_id, store_id, key_words);
        return answer;
    }

    @RequestMapping(value = "/add_owner")
    @CrossOrigin
    @Override
    public Response add_owner(String user_email_to_appoint, int store_id) {
        Response answer = marketFacade.add_owner(user_email_to_appoint, store_id);
        return answer;
    }

    @RequestMapping(value = "/delete_owner")
    @CrossOrigin
    @Override
    public Response delete_owner(String user_email_to_delete_appointment, int store_id) {
        Response answer = marketFacade.delete_owner(user_email_to_delete_appointment, store_id);
        return answer;
    }

    @RequestMapping(value = "/add_manager")
    @CrossOrigin
    @Override
    public Response add_manager(String user_email_to_appoint, int store_id) {
        Response answer = marketFacade.add_manager(user_email_to_appoint, store_id);
        return answer;
    }

    @RequestMapping(value = "/delete_manager")
    @CrossOrigin
    @Override
    public Response delete_manager(String user_email_to_delete_appointment, int store_id) {
        Response answer = marketFacade.delete_manager(user_email_to_delete_appointment, store_id);
        return answer;
    }

    @RequestMapping(value = "/close_store_temporarily")
    @CrossOrigin
    @Override
    public Response close_store_temporarily(int store_id) {
        Response answer = marketFacade.close_store_temporarily(store_id);
        return answer;
    }

    @RequestMapping(value = "/open_close_store")
    @CrossOrigin
    @Override
    public Response open_close_store(int store_id) {
        Response answer = marketFacade.open_close_store(store_id);
        return answer;
    }

    @RequestMapping(value = "/view_store_management_information")
    @CrossOrigin
    @Override
    public Response view_store_management_information(int store_id) {
        Response answer = marketFacade.view_store_management_information(store_id);
        return answer;
    }

    @RequestMapping(value = "/manager_view_store_questions")
    @CrossOrigin
    @Override
    public Response manager_view_store_questions(int store_id) {
        Response answer = marketFacade.manager_view_store_questions(store_id);
        return answer;
    }

    @RequestMapping(value = "/manager_answer_question")
    @CrossOrigin
    @Override
    public Response manager_answer_question(int store_id, int question_id, String managerAnswer) {
        Response answer = marketFacade.manager_answer_question(store_id, question_id, managerAnswer);
        return answer;
    }

    @RequestMapping(value = "/view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response view_store_purchases_history(int store_id) {
        Response answer = marketFacade.view_store_management_information(store_id);
        return answer;
    }

    @RequestMapping(value = "/close_store_permanently")
    @CrossOrigin
    @Override
    public Response close_store_permanently(int store_id) {
        Response answer = marketFacade.close_store_permanently(store_id);
        return answer;
    }

    @RequestMapping(value = "/remove_user")
    @CrossOrigin
    @Override
    public Response remove_user(String email) {
        Response answer = marketFacade.remove_user(email);
        return answer;
    }

    @RequestMapping(value = "/admin_view_users_questions")
    @CrossOrigin
    @Override
    public Response admin_view_users_questions() {
        Response answer = marketFacade.admin_view_users_questions();
        return answer;
    }

    @RequestMapping(value = "/admin_answer_user_question")
    @CrossOrigin
    @Override
    public Response admin_answer_user_question(int question_id, String adminAnswer) {
        Response answer = marketFacade.admin_answer_user_question(question_id, adminAnswer);
        return answer;
    }

    @RequestMapping(value = "/admin_view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_store_purchases_history(int store_id) {
        Response answer = marketFacade.admin_view_store_purchases_history(store_id);
        return answer;
    }

    @RequestMapping(value = "/admin_view_user_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_user_purchases_history(String user_email) {
        Response answer = marketFacade.admin_view_user_purchases_history(user_email);
        return answer;
    }

    @RequestMapping(value = "/get_market_stats")
    @CrossOrigin
    @Override
    public Response get_market_stats() {
        Response answer = marketFacade.get_market_stats();
        return answer;
    }
}


package TradingSystem.server.Service;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment.StorePermission;
import TradingSystem.server.Domain.Utils.Exception.ExitException;
import TradingSystem.server.Domain.Utils.Utils;
import com.google.gson.Gson;
import TradingSystem.server.Domain.Utils.Logger.SystemLogger;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import TradingSystem.server.Domain.Utils.Response;
//import com.google.gson.Gson;
import java.util.List;

import java.util.*;

import static TradingSystem.server.Service.MarketSystem.*;


@RestController
public class Service implements iService {

    private static Service service = null;

    private MarketFacade marketFacade;
    private List notifications = new ArrayList();



    private Service() {
        // -- Market init
        MarketSystem system;
        try
        {
            system = new MarketSystem(system_config_path, instructions_config_path);
            PaymentAdapter paymentAdapter = system.getPayment_adapter();
            SupplyAdapter supplyAdapter = system.getSupply_adapter();
            //system.add_admins();
            //system.init_data_to_market_develop(paymentAdapter, supplyAdapter);
            this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        }
        catch (ExitException e) {
            SystemLogger.getInstance().add_log("System Init Fail: "+e.getMessage());
            System.exit(3);
        }
        SystemLogger.getInstance().add_log("System Init Done.");
    }

    public synchronized static Service getInstance() {
        if (service == null)
            service = new Service();
        return service;
    }

    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String email, String password) {
        Response answer = marketFacade.login(email, password);
        return answer;
    }

    @RequestMapping(value = "/get_notifications")
    @CrossOrigin
    public Response get_notifications(String email){
        NotificationHandler.getInstance().send_waiting_notifications(email);
        return new Response("nice", "job");
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
    public Response register(String email, String pw, String name, String lastName, String birth_date) {
        Response answer = marketFacade.register(email, pw, name, lastName, birth_date);
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
    public Response add_product_to_cart(int store_id, int product_id, int quantity) {
        Response answer = marketFacade.add_product_to_cart(store_id, product_id, quantity);
        return answer;

    }

    @RequestMapping(value = "/remove_product_from_cart")
    @CrossOrigin
    @Override
    public Response remove_product_from_cart(int store_id, int product_id) {
        Response answer = marketFacade.remove_product_from_cart(store_id, product_id);
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
    public Response edit_product_quantity_in_cart(int store_id, int product_id, int quantity) {
        Response answer = marketFacade.edit_product_quantity_in_cart(store_id, product_id, quantity);
        return answer;
    }
    @RequestMapping(value = "/remove_predict")
    @CrossOrigin
    @Override
    public Response remove_predict(int store_id, String predict_name){
        Response answer = marketFacade.remove_predict(store_id, predict_name);
        return answer;
    }
    @RequestMapping(value = "/buy_cart")
    @CrossOrigin
    @Override
    public Response buy_cart(String paymentInfo, String supplyInfo) {
        // TODO : GSON
        PaymentInfo p=null;
        SupplyInfo s=null;
        try{
            p = new Gson().fromJson(paymentInfo, PaymentInfo.class);
            s = new Gson().fromJson(supplyInfo, SupplyInfo.class);
            Response answer = marketFacade.buy_cart(p, s);
            return answer;
        }
        catch(Exception e)
        {
            return Utils.CreateResponse(e);
        }


    }
    @RequestMapping(value = "/add_bid")
    @CrossOrigin
    @Override
    public Response add_bid(int storeID, int productID, int quantity, double offer_price) {
        Response answer = marketFacade.add_bid(storeID, productID, quantity, offer_price);
        return answer;
    }
    @RequestMapping(value = "/manager_answer_bid")
    @CrossOrigin
    @Override
    public Response manager_answer_bid(int storeID, int bidID, boolean manager_answer, double negotiation_price) {
        Response answer = marketFacade.manager_answer_bid(storeID, bidID, manager_answer, negotiation_price);
        return answer;
    }
    @RequestMapping(value = "/view_bids_status")
    @CrossOrigin
    @Override
    public Response view_bids_status(int storeID) {
        Response answer = marketFacade.view_bids_status(storeID);
        return answer;
    }

    @RequestMapping(value = "/manager_answer_appointment")
    @CrossOrigin
    @Override
    public Response manager_answer_appointment(int storeID, boolean manager_answer, String candidate_email) {
        Response answer = marketFacade.manager_answer_appointment(storeID, manager_answer, candidate_email);
        return answer;
    }

    @RequestMapping(value = "/view_appointments_status")
    @CrossOrigin
    @Override
    public Response view_appointments_status(int storeID) {
        Response answer = marketFacade.view_appointments_status(storeID);
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
    public Response edit_name(String new_name) {
        Response answer = marketFacade.edit_name(new_name);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name")
    @CrossOrigin
    @Override
    public Response edit_last_name(String new_last_name) {
        Response answer = marketFacade.edit_last_name(new_last_name);
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
    public Response edit_name_premium(String new_name, String premAnswer) {
        Response answer = marketFacade.edit_name_premium(new_name, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name_premium")
    @CrossOrigin
    @Override
    public Response edit_last_name_premium(String new_last_name, String premAnswer) {
        Response answer = marketFacade.edit_last_name_premium(new_last_name, premAnswer);
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
    public Response add_product_to_store(int store_id, int quantity, String name, double price, String category, String key_words) {
        List<String> key_words_list = new LinkedList<>();
        key_words_list.add(key_words);
        Response answer = marketFacade.add_product_to_store(store_id, quantity, name, price, category, key_words_list);
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
    public Response edit_product_key_words(int product_id, int store_id, String key_words) {
        List<String> key_words_list = new LinkedList<>();
        key_words_list.add(key_words);
        Response answer = marketFacade.edit_product_key_words(product_id, store_id, key_words_list);
        return answer;
    }
    @RequestMapping(value = "/edit_product_quantity")
    @CrossOrigin
    @Override
    public Response edit_product_quantity(int product_id, int store_id, int quantity) {
        Response answer = marketFacade.edit_product_quantity(product_id, store_id, quantity);
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
        Response answer = marketFacade.view_store_purchases_history(store_id);

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


    @RequestMapping(value = "/get_all_stores")
    @CrossOrigin
    public Response get_all_stores() {
        Response answer = marketFacade.get_all_stores();
        return answer;
    }

    @RequestMapping(value = "/get_products_by_store_id")
    @CrossOrigin
    public Response get_products_by_store_id(int store_id) {
        Response answer = marketFacade.get_products_by_store_id(store_id);
        return answer;
    }

    @RequestMapping(value = "/get_user_questions")
    @CrossOrigin
    public Response get_user_questions() {
        Response answer = marketFacade.get_user_questions();
        return answer;
    }
    @RequestMapping(value = "/edit_manager_permissions")
    @CrossOrigin
    @Override
    public Response edit_manager_permissions( String manager_email, int store_id,  String permissions) {
        String[] permissions_str = permissions.split("/");
        List<Integer> permissions_numbers = new ArrayList<>();
        for (int i=0;i<permissions_str.length;i++)
        {
            permissions_numbers.add(Integer.valueOf(permissions_str[i]));
        }
        List<StorePermission> permissions_enum = new ArrayList<>();
        for (Integer i:permissions_numbers)
        {
            permissions_enum.add(StorePermission.values()[i]);
        }
        Response answer = marketFacade.edit_manager_permissions(manager_email, store_id, permissions_enum);
        return answer;
    }

    @RequestMapping(value = "/get_permissions")
    @CrossOrigin
    @Override
    public Response get_permissions( String manager_email, int store_id) {
        Response answer = marketFacade.get_permissions(manager_email,store_id);
        return answer;
    }

    @RequestMapping(value = "/get_all_categories")
    @CrossOrigin
    @Override
    public Response get_all_categories(int store_id) {
        Response answer = marketFacade.get_all_categories(store_id);
        return answer;
    }

    @RequestMapping(value = "/online_user")
    @CrossOrigin
    @Override
    public Response online_user() {
        Response answer = marketFacade.online_user();
        return answer;
    }

    @RequestMapping(value = "/save_notifications")
    @CrossOrigin
    @Override
    public Response save_notifications(String notification) {
        this.notifications.add(notification);
        return  new Response();

    }

    @RequestMapping(value = "/get_notifications_list")
    @CrossOrigin
    @Override
    public Response get_notifications_list() {
        return new Response(this.notifications, "Notifications received successfully");
    }








    // -- DISCOUNTS & POLICIES

    @RequestMapping(value = "/add_predict")
    @CrossOrigin
    @Override
    public Response add_predict(int store_id, String catgorey, int product_id, boolean above, boolean equql, int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, String name) {
        Response answer = marketFacade.add_predict(store_id, catgorey, product_id, above, equql, num, price, quantity, age, time, year, month, day, name);
        return answer;
    }



    @RequestMapping(value = "/get_purchase_policy")
    @CrossOrigin
    @Override
    public Response get_purchase_policy(int store_id) {
        Response answer = marketFacade.get_purchase_policy(store_id);
        return answer;
    }

    @RequestMapping(value = "/send_predicts")
    @CrossOrigin
    @Override
    public Response send_predicts(int store_id) {
        Response answer = marketFacade.send_predicts(store_id);
        return answer;
    }

    @RequestMapping(value = "/get_discount_policy")
    @CrossOrigin
    @Override
    public Response get_discount_policy(int store_id) {
        Response answer = marketFacade.get_discount_policy(store_id);
        return answer;
    }

    //------------------------------------------------------ Discount Rules --------------------------------------------------------

    @RequestMapping(value = "/add_complex_discount_rule")
    @CrossOrigin
    @Override
    public Response add_complex_discount_rule(int store_id, String nameOfPredict, String nameOfComponent, String nameOfRule) {
        Response answer = marketFacade.add_complex_discount_rule(store_id, nameOfPredict, nameOfComponent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_categorey_discount_rule")
    @CrossOrigin
    @Override
    public Response add_simple_category_discount_rule(int store_id, String nameOfCategory, double percent, String nameOfRule) {
        Response answer = marketFacade.add_simple_category_discount_rule(store_id, nameOfCategory, percent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_product_discount_rule")
    @CrossOrigin
    @Override
    public Response add_simple_product_discount_rule(int store_id, int id, double percent, String nameOfrule) {
        Response answer = marketFacade.add_simple_product_discount_rule(store_id, id, percent, nameOfrule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_store_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_simple_store_discount_rule(int store_id, double percent, String nameOfRule) {
        Response answer = marketFacade.add_simple_store_discount_rule(store_id, percent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_and_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_and_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response answer = marketFacade.add_and_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_or_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_or_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response answer = marketFacade.add_or_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_max_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_max_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response answer = marketFacade.add_max_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_plus_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_plus_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response answer = marketFacade.add_plus_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_xor_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_xor_discount_rule(String left, String right, int store_id, String NameOfRule) {
        Response answer = marketFacade.add_xor_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/remove_discount_rule")
    @CrossOrigin
    @Override
    public Response  remove_discount_rule(int store_id, String name) {
        Response answer = marketFacade.remove_discount_rule(store_id, name);
        return answer;
    }



    //------------------------------------------------------ Purchase Rules --------------------------------------------------------

    @RequestMapping(value = "/add_simple_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_simple_purchase_rule(String PredictName, String NameOfRule, int store_id) {
        Response answer = marketFacade.add_simple_purchase_rule(PredictName, NameOfRule, store_id);
        return answer;
    }
    @RequestMapping(value = "/add_and_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_and_purchase_rule(String left, String right, int store_id, String NameOfrule) {
        Response answer = marketFacade.add_and_purchase_rule(left, right, store_id, NameOfrule);
        return answer;
    }
    @RequestMapping(value = "/add_or_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_or_purchase_rule(String left, String right, int store_id, String nameOfrule) {
        Response answer = marketFacade.add_or_purchase_rule(left, right, store_id, nameOfrule);
        return answer;
    }
    @RequestMapping(value = "/remove_purchase_rule")
    @CrossOrigin
    @Override
    public Response  remove_purchase_rule(int store_id, String name){
        Response answer = marketFacade.remove_purchase_rule(store_id, name);
        return answer;
    }
}


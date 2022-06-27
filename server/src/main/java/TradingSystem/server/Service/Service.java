package TradingSystem.server.Service;
import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
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
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.*;

import static TradingSystem.server.Service.MarketSystem.*;

@RestController
public class Service implements iService {

    private static HashMap<Integer,MarketFacade> MarketFacdeMap;
    private static Integer session_id=1;
    private List notifications = new ArrayList();
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;

    public Service() {

        // -- Market init
        MarketSystem system;
        try
        {
            system = new MarketSystem(system_config_path, instructions_config_path);
            this.paymentAdapter = system.getPayment_adapter();
            this.supplyAdapter = system.getSupply_adapter();
            //system.add_admins();
            //system.init_data_to_market_develop(paymentAdapter, supplyAdapter);
            this.MarketFacdeMap = new HashMap<>();
        }
        catch (ExitException e) {
            SystemLogger.getInstance().add_log("System Init Fail: "+e.getMessage());
            System.exit(3);
        }
        SystemLogger.getInstance().add_log("System Init Done.");
    }

//    public synchronized static Service getInstance() {
//        if (service == null)
//            service = new Service();
//        return service;
//    }

    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String email, String password,int session_id) {
        Response answer = get_market_facade(session_id).login(email, password);
        return answer;
    }

    @RequestMapping(value = "/get_notifications")
    @CrossOrigin
    public Response get_notifications(String email,int session_id){
        NotificationHandler.getInstance().send_waiting_notifications(email);
        return new Response("nice", "job");
    }

    @RequestMapping(value = "/logout")
    @CrossOrigin
    @Override
    public Response logout(int session_id) {
        Response answer = get_market_facade(session_id).logout();
        return answer;
    }




    @RequestMapping(value = "/register")
    @CrossOrigin
    public Response register(String email, String pw, String name, String lastName, String birth_date,int session_id) {
        Response answer = get_market_facade(session_id).register(email, pw, name, lastName, birth_date);
        return answer;
    }


    @RequestMapping(value = "/find_store_information")
    @CrossOrigin
    @Override
    public Response find_store_information(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).find_store_information(store_id);
        return answer;
    }

    @RequestMapping(value = "/find_product_information")
    @CrossOrigin
    @Override
    public Response find_product_information(int product_id, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).find_product_information(product_id, store_id);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_name")
    @CrossOrigin
    @Override
    public Response find_products_by_name(String product_name,int session_id) {
        Response answer = get_market_facade(session_id).find_products_by_name(product_name);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_category")
    @CrossOrigin
    @Override
    public Response find_products_by_category(String category,int session_id) {
        Response answer = get_market_facade(session_id).find_products_by_category(category);
        return answer;
    }

    @RequestMapping(value = "/find_products_by_keywords")
    @CrossOrigin
    @Override
    public Response find_products_by_keywords(String key_words,int session_id) {
        Response answer = get_market_facade(session_id).find_products_by_keywords(key_words);
        return answer;

    }

    @RequestMapping(value = "/add_product_to_cart")
    @CrossOrigin
    @Override
    public Response add_product_to_cart(int store_id, int product_id, int quantity,int session_id) {
        System.out.println("store id "+store_id+"product id "+ product_id+"qu "+quantity);
        Response answer = get_market_facade(session_id).add_product_to_cart(store_id, product_id, quantity);
        return answer;

    }

    @RequestMapping(value = "/remove_product_from_cart")
    @CrossOrigin
    @Override
    public Response remove_product_from_cart(int store_id, int product_id,int session_id) {
        Response answer = get_market_facade(session_id).remove_product_from_cart(store_id, product_id);
        return answer;
    }

    @RequestMapping(value = "/view_user_cart")
    @CrossOrigin
    @Override
    public Response view_user_cart(int session_id) {
        Response answer = get_market_facade(session_id).view_user_cart();
        return answer;
    }

    @RequestMapping(value = "/edit_product_quantity_in_cart")
    @CrossOrigin
    @Override
    public Response edit_product_quantity_in_cart(int store_id, int product_id, int quantity,int session_id) {
        Response answer = get_market_facade(session_id).edit_product_quantity_in_cart(store_id, product_id, quantity);
        return answer;
    }
    @RequestMapping(value = "/remove_predict")
    @CrossOrigin
    @Override
    public Response remove_predict(int store_id, String predict_name,int session_id){
        Response answer = get_market_facade(session_id).remove_predict(store_id, predict_name);
        return answer;
    }
    @RequestMapping(value = "/buy_cart")
    @CrossOrigin
    @Override
    public Response buy_cart(String paymentInfo, String supplyInfo,int session_id) {
        System.out.println(paymentInfo);
        System.out.println(supplyInfo);
        if(supplyInfo.equals("test")&&paymentInfo.equals("test")){
            supplyInfo=supply;
            paymentInfo=payment;
        }
        PaymentInfo p=null;
        SupplyInfo s=null;
        try{
            p = new Gson().fromJson(paymentInfo, PaymentInfo.class);
            s = new Gson().fromJson(supplyInfo, SupplyInfo.class);

            Response answer = get_market_facade(session_id).buy_cart(p, s);
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
    public Response add_bid(int storeID, int productID, int quantity, double offer_price,int session_id) {
        Response answer = get_market_facade(session_id).add_bid(storeID, productID, quantity, offer_price);
        return answer;
    }
    @RequestMapping(value = "/manager_answer_bid")
    @CrossOrigin
    @Override
    public Response manager_answer_bid(int storeID, int bidID, boolean manager_answer, double negotiation_price,int session_id) {
        Response answer = get_market_facade(session_id).manager_answer_bid(storeID, bidID, manager_answer, negotiation_price);
        return answer;
    }
    @RequestMapping(value = "/view_bids_status")
    @CrossOrigin
    @Override
    public Response view_bids_status(int storeID,int session_id) {
        Response answer = get_market_facade(session_id).view_bids_status(storeID);
        return answer;
    }


    @RequestMapping(value = "/manager_answer_appointment")
    @CrossOrigin
    @Override
    public Response manager_answer_appointment(int storeID, boolean manager_answer, String candidate_email,int session_id) {
        Response answer = get_market_facade(session_id).manager_answer_appointment(storeID, manager_answer, candidate_email);
        return answer;
    }

    @RequestMapping(value = "/view_appointments_status")
    @CrossOrigin
    @Override
    public Response view_appointments_status(int storeID,int session_id) {
        Response answer = get_market_facade(session_id).view_appointments_status(storeID);
        return answer;
    }


    @RequestMapping(value = "/open_store")
    @CrossOrigin
    public Response open_store(String store_name,int session_id) {
        Response answer = get_market_facade(session_id).open_store(store_name);
        return answer;
    }

    @RequestMapping(value = "/add_product_review")
    @CrossOrigin
    @Override
    public Response add_product_review(int product_id, int store_id, String review,int session_id) {
        Response answer = get_market_facade(session_id).add_product_review(product_id, store_id, review);
        return answer;
    }

    @RequestMapping(value = "/rate_product")
    @CrossOrigin
    @Override
    public Response rate_product(int product_id, int store_id, int rate,int session_id) {
        Response answer = get_market_facade(session_id).rate_product(product_id, store_id, rate);
        return answer;
    }

    @RequestMapping(value = "/rate_store")
    @CrossOrigin
    @Override
    public Response rate_store(int store_id, int rate,int session_id) {
        Response answer = get_market_facade(session_id).rate_store(store_id, rate);
        return answer;
    }

    @RequestMapping(value = "/send_question_to_store")
    @CrossOrigin
    @Override
    public Response send_question_to_store(int store_id, String question,int session_id) {
        Response answer = get_market_facade(session_id).send_question_to_store(store_id, question);
        return answer;
    }

    @RequestMapping(value = "/send_question_to_admin")
    @CrossOrigin
    @Override
    public Response send_question_to_admin(String question,int session_id) {
        Response answer = get_market_facade(session_id).send_question_to_admin(question);
        return answer;
    }

    @RequestMapping(value = "/view_user_purchase_history")
    @CrossOrigin
    @Override
    public Response view_user_purchase_history(int session_id) {
        Response answer = get_market_facade(session_id).view_user_purchase_history();
        return answer;
    }

    @RequestMapping(value = "/get_user_email")
    @CrossOrigin
    @Override
    public Response get_user_email(int session_id) {
        Response answer = get_market_facade(session_id).get_user_email();
        return answer;
    }

    @RequestMapping(value = "/get_user_name")
    @CrossOrigin
    @Override
    public Response get_user_name(int session_id) {
        Response answer = get_market_facade(session_id).get_user_name();
        return answer;
    }

    @RequestMapping(value = "/get_user_last_name")
    @CrossOrigin
    @Override
    public Response get_user_last_name(int session_id) {
        Response answer = get_market_facade(session_id).get_user_last_name();
        return answer;
    }

    @RequestMapping(value = "/unregister")
    @CrossOrigin
    @Override
    public Response unregister(String password,int session_id) {
        Response answer = get_market_facade(session_id).unregister(password);
        return answer;
    }

    @RequestMapping(value = "/edit_name")
    @CrossOrigin
    @Override
    public Response edit_name(String new_name,int session_id) {
        Response answer = get_market_facade(session_id).edit_name(new_name);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name")
    @CrossOrigin
    @Override
    public Response edit_last_name(String new_last_name,int session_id) {
        Response answer = get_market_facade(session_id).edit_last_name(new_last_name);
        return answer;
    }

    @RequestMapping(value = "/edit_password")
    @CrossOrigin
    @Override
    public Response edit_password(String old_pw, String password,int session_id) {
        Response answer = get_market_facade(session_id).edit_password(old_pw, password);
        return answer;
    }

    @RequestMapping(value = "/edit_name_premium")
    @CrossOrigin
    @Override
    public Response edit_name_premium(String new_name, String premAnswer,int session_id) {
        Response answer = get_market_facade(session_id).edit_name_premium(new_name, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/edit_last_name_premium")
    @CrossOrigin
    @Override
    public Response edit_last_name_premium(String new_last_name, String premAnswer,int session_id) {
        Response answer = get_market_facade(session_id).edit_last_name_premium(new_last_name, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/edit_password_premium")
    @CrossOrigin
    @Override
    public Response edit_password_premium(String pw, String password, String premAnswer,int session_id) {
        Response answer = get_market_facade(session_id).edit_password_premium(pw, password, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/get_user_security_question")
    @CrossOrigin
    @Override
    public Response get_user_security_question(int session_id) {
        Response answer = get_market_facade(session_id).get_user_security_question();
        return answer;
    }

    @RequestMapping(value = "/improve_security")
    @CrossOrigin
    @Override
    public Response improve_security(String password, String question, String premAnswer,int session_id) {
        Response answer = get_market_facade(session_id).improve_security(password, question, premAnswer);
        return answer;
    }

    @RequestMapping(value = "/add_product_to_store")
    @CrossOrigin
    @Override
    public Response add_product_to_store(int store_id, int quantity, String name, double price, String category, String key_words,int session_id) {
        List<String> key_words_list = new LinkedList<>();
        key_words_list.add(key_words);
        Response answer = get_market_facade(session_id).add_product_to_store(store_id, quantity, name, price, category, key_words_list);
        return answer;
    }

    @RequestMapping(value = "/delete_product_from_store")
    @CrossOrigin
    @Override
    public Response delete_product_from_store(int product_id, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).delete_product_from_store(product_id, store_id);
        return answer;
    }

    @RequestMapping(value = "/edit_product_name")
    @CrossOrigin
    @Override
    public Response edit_product_name(int product_id, int store_id, String name,int session_id) {
        Response answer = get_market_facade(session_id).edit_product_name(product_id, store_id, name);
        return answer;
    }

    @RequestMapping(value = "/edit_product_price")
    @CrossOrigin
    @Override
    public Response edit_product_price(int product_id, int store_id, double price,int session_id) {
        Response answer = get_market_facade(session_id).edit_product_price(product_id, store_id, price);
        return answer;
    }

    @RequestMapping(value = "/edit_product_category")
    @CrossOrigin
    @Override
    public Response edit_product_category(int product_id, int store_id, String category,int session_id) {
        Response answer = get_market_facade(session_id).edit_product_category(product_id, store_id, category);
        return answer;
    }

    @RequestMapping(value = "/edit_product_key_words")
    @CrossOrigin
    @Override
    public Response edit_product_key_words(int product_id, int store_id, String key_words,int session_id) {
        List<String> key_words_list = new LinkedList<>();
        key_words_list.add(key_words);
        Response answer = get_market_facade(session_id).edit_product_key_words(product_id, store_id, key_words_list);
        return answer;
    }
    @RequestMapping(value = "/edit_product_quantity")
    @CrossOrigin
    @Override
    public Response edit_product_quantity(int product_id, int store_id, int quantity,int session_id) {
        Response answer = get_market_facade(session_id).edit_product_quantity(product_id, store_id, quantity);
        return answer;
    }

    @RequestMapping(value = "/add_owner")
    @CrossOrigin
    @Override
    public Response add_owner(String user_email_to_appoint, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).add_owner(user_email_to_appoint, store_id);
        return answer;
    }

    @RequestMapping(value = "/delete_owner")
    @CrossOrigin
    @Override
    public Response delete_owner(String user_email_to_delete_appointment, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).delete_owner(user_email_to_delete_appointment, store_id);
        return answer;
    }

    @RequestMapping(value = "/add_manager")
    @CrossOrigin
    @Override
    public Response add_manager(String user_email_to_appoint, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).add_manager(user_email_to_appoint, store_id);
        return answer;
    }

    @RequestMapping(value = "/delete_manager")
    @CrossOrigin
    @Override
    public Response delete_manager(String user_email_to_delete_appointment, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).delete_manager(user_email_to_delete_appointment, store_id);
        return answer;
    }

    @RequestMapping(value = "/close_store_temporarily")
    @CrossOrigin
    @Override
    public Response close_store_temporarily(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).close_store_temporarily(store_id);
        return answer;
    }

    @RequestMapping(value = "/open_close_store")
    @CrossOrigin
    @Override
    public Response open_close_store(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).open_close_store(store_id);
        return answer;
    }

    @RequestMapping(value = "/view_store_management_information")
    @CrossOrigin
    @Override
    public Response view_store_management_information(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).view_store_management_information(store_id);
        return answer;
    }

    @RequestMapping(value = "/manager_view_store_questions")
    @CrossOrigin
    @Override
    public Response manager_view_store_questions(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).manager_view_store_questions(store_id);
        return answer;
    }

    @RequestMapping(value = "/manager_answer_question")
    @CrossOrigin
    @Override
    public Response manager_answer_question(int store_id, int question_id, String managerAnswer,int session_id) {
        Response answer = get_market_facade(session_id).manager_answer_question(store_id, question_id, managerAnswer);
        return answer;
    }

    @RequestMapping(value = "/view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response view_store_purchases_history(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).view_store_purchases_history(store_id);

        return answer;
    }

    @RequestMapping(value = "/close_store_permanently")
    @CrossOrigin
    @Override
    public Response close_store_permanently(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).close_store_permanently(store_id);
        return answer;
    }

    @RequestMapping(value = "/remove_user")
    @CrossOrigin
    @Override
    public Response remove_user(String email,int session_id) {
        Response answer = get_market_facade(session_id).remove_user(email);
        return answer;
    }

    @RequestMapping(value = "/admin_view_users_questions")
    @CrossOrigin
    @Override
    public Response admin_view_users_questions(int session_id) {
        Response answer = get_market_facade(session_id).admin_view_users_questions();
        return answer;
    }

    @RequestMapping(value = "/admin_answer_user_question")
    @CrossOrigin
    @Override
    public Response admin_answer_user_question(int question_id, String adminAnswer,int session_id) {
        Response answer = get_market_facade(session_id).admin_answer_user_question(question_id, adminAnswer);
        return answer;
    }

    @RequestMapping(value = "/admin_view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_store_purchases_history(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).admin_view_store_purchases_history(store_id);
        return answer;
    }

    @RequestMapping(value = "/admin_view_user_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_user_purchases_history(String user_email,int session_id) {
        Response answer = get_market_facade(session_id).admin_view_user_purchases_history(user_email);
        return answer;
    }

    @RequestMapping(value = "/get_market_stats")
    @CrossOrigin
    @Override
    public Response get_market_stats(int session_id) {
        Response answer = get_market_facade(session_id).get_market_stats();
        return answer;
    }


    @RequestMapping(value = "/get_all_stores")
    @CrossOrigin
    public Response get_all_stores(int session_id) {
        Response answer = get_market_facade(session_id).get_all_stores();
        return answer;
    }

    @RequestMapping(value = "/get_products_by_store_id")
    @CrossOrigin
    public Response get_products_by_store_id(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).get_products_by_store_id(store_id);
        return answer;
    }

    @RequestMapping(value = "/get_user_questions")
    @CrossOrigin
    public Response get_user_questions(int session_id) {
        Response answer = get_market_facade(session_id).get_user_questions();
        return answer;
    }
    @RequestMapping(value = "/edit_manager_permissions")
    @CrossOrigin
    @Override
    public Response edit_manager_permissions( String manager_email, int store_id,  String permissions,int session_id) {
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
        Response answer = get_market_facade(session_id).edit_manager_permissions(manager_email, store_id, permissions_enum);
        return answer;
    }

    @RequestMapping(value = "/get_permissions")
    @CrossOrigin
    @Override
    public Response get_permissions( String manager_email, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).get_permissions(manager_email,store_id);
        return answer;
    }

    @RequestMapping(value = "/get_all_categories")
    @CrossOrigin
    @Override
    public Response get_all_categories(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).get_all_categories(store_id);
        return answer;
    }

    @RequestMapping(value = "/online_user")
    @CrossOrigin
    @Override
    public Response online_user(int session_id) {
        Response answer = get_market_facade(session_id).online_user();
        return answer;
    }

    @RequestMapping(value = "/save_notifications")
    @CrossOrigin
    @Override
    public Response save_notifications(String notification,int session_id) {
        this.notifications.add(notification);
        return  new Response();

    }

    @RequestMapping(value = "/get_notifications_list")
    @CrossOrigin
    @Override
    public Response get_notifications_list(int session_id) {
        return new Response(this.notifications, "Notifications received successfully");
    }








    // -- DISCOUNTS & POLICIES

    @RequestMapping(value = "/add_predict")
    @CrossOrigin
    @Override
    public Response add_predict(int store_id, String catgorey, int product_id, boolean above, boolean equql, int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, String name,int session_id) {
        Response answer = get_market_facade(session_id).add_predict(store_id, catgorey, product_id, above, equql, num, price, quantity, age, time, year, month, day, name);
        return answer;
    }



    @RequestMapping(value = "/get_purchase_policy")
    @CrossOrigin
    @Override
    public Response get_purchase_policy(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).get_purchase_policy(store_id);
        return answer;
    }

    @RequestMapping(value = "/send_predicts")
    @CrossOrigin
    @Override
    public Response send_predicts(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).send_predicts(store_id);
        return answer;
    }

    @RequestMapping(value = "/get_discount_policy")
    @CrossOrigin
    @Override
    public Response get_discount_policy(int store_id,int session_id) {
        Response answer = get_market_facade(session_id).get_discount_policy(store_id);
        return answer;
    }

    //------------------------------------------------------ Discount Rules --------------------------------------------------------

    @RequestMapping(value = "/add_complex_discount_rule")
    @CrossOrigin
    @Override
    public Response add_complex_discount_rule(int store_id, String nameOfPredict, String nameOfComponent, String nameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_complex_discount_rule(store_id, nameOfPredict, nameOfComponent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_categorey_discount_rule")
    @CrossOrigin
    @Override
    public Response add_simple_category_discount_rule(int store_id, String nameOfCategory, double percent, String nameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_simple_category_discount_rule(store_id, nameOfCategory, percent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_product_discount_rule")
    @CrossOrigin
    @Override
    public Response add_simple_product_discount_rule(int store_id, int id, double percent, String nameOfrule,int session_id) {
        Response answer = get_market_facade(session_id).add_simple_product_discount_rule(store_id, id, percent, nameOfrule);
        return answer;
    }
    @RequestMapping(value = "/add_simple_store_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_simple_store_discount_rule(int store_id, double percent, String nameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_simple_store_discount_rule(store_id, percent, nameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_and_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_and_discount_rule(String left, String right, int store_id, String NameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_and_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_or_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_or_discount_rule(String left, String right, int store_id, String NameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_or_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_max_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_max_discount_rule(String left, String right, int store_id, String NameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_max_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_plus_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_plus_discount_rule(String left, String right, int store_id, String NameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_plus_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/add_xor_discount_rule")
    @CrossOrigin
    @Override
    public Response  add_xor_discount_rule(String left, String right, int store_id, String NameOfRule,int session_id) {
        Response answer = get_market_facade(session_id).add_xor_discount_rule(left, right, store_id, NameOfRule);
        return answer;
    }
    @RequestMapping(value = "/remove_discount_rule")
    @CrossOrigin
    @Override
    public Response  remove_discount_rule(int store_id, String name,int session_id) {
        Response answer = get_market_facade(session_id).remove_discount_rule(store_id, name);
        return answer;
    }



    //------------------------------------------------------ Purchase Rules --------------------------------------------------------

    @RequestMapping(value = "/add_simple_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_simple_purchase_rule(String PredictName, String NameOfRule, int store_id,int session_id) {
        Response answer = get_market_facade(session_id).add_simple_purchase_rule(PredictName, NameOfRule, store_id);
        return answer;
    }
    @RequestMapping(value = "/add_and_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_and_purchase_rule(String left, String right, int store_id, String NameOfrule,int session_id) {
        Response answer = get_market_facade(session_id).add_and_purchase_rule(left, right, store_id, NameOfrule);
        return answer;
    }
    @RequestMapping(value = "/add_or_purchase_rule")
    @CrossOrigin
    @Override
    public Response  add_or_purchase_rule(String left, String right, int store_id, String nameOfrule,int session_id) {
        Response answer = get_market_facade(session_id).add_or_purchase_rule(left, right, store_id, nameOfrule);
        return answer;
    }
    @RequestMapping(value = "/remove_purchase_rule")
    @CrossOrigin
    @Override
    public Response  remove_purchase_rule(int store_id, String name,int session_id) {
        Response answer = get_market_facade(session_id).remove_purchase_rule(store_id, name);
        return answer;
    }

    @RequestMapping(value = "/get_session_id")
    @CrossOrigin
    @Override
    public Response  get_session_id() {
        Response answer =new Response(session_id,"session id ");
        session_id++;
        return answer;
    }





    private MarketFacade get_market_facade(int session_id){
        if(this.MarketFacdeMap.get(session_id) == null){
            this.MarketFacdeMap.put(session_id,new MarketFacade(paymentAdapter,supplyAdapter));
        }
        return MarketFacdeMap.get(session_id);
    }
    String payment = "{\"card_number\":\"1234123412341234\",\"month\":\"07\",\"year\":\"2022\",\"holder\":\"amit grumet\",\"ccv\":\"123\",\"id\":\"123456789\"}\n";
    String supply="{\"name\":\"amit\",\"address\":\"grumet\",\"city\":\"pt\",\"country\":\"isreal\",\"zip\":\"12345\"}";
}


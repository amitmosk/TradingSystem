package TradingSystem.server.api;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ServerSocket;
import java.util.List;

@RestController
public class Service implements iService {

    private static Service service = null;


    private Service() {
        // Market init

    }

    public synchronized static Service getInstance() {
        if (service == null)
            service = new Service();
        return service;
    }

    @RequestMapping(value = "/amit")
    @CrossOrigin
    public String amit() {
        return "hello";
    }


    @RequestMapping(value = "/logout")
    @CrossOrigin
    @Override
    public Response logout() {
        return new Response("null","nulllll");
    }

    @RequestMapping(value = "/register")
    @CrossOrigin
    @Override
    public Response register(String Email, String pw, String name, String lastName) {
        return null;
    }



    @RequestMapping(value = "/login")
    @CrossOrigin
    @Override
    public Response login(String Email, String password) {
        return null;
    }

    @RequestMapping(value = "/find_store_information")
    @CrossOrigin
    @Override
    public Response find_store_information(int store_id) {
        return null;
    }
    
    @RequestMapping(value = "/find_product_information")
    @CrossOrigin
    @Override
    public Response find_product_information(int product_id, int store_id) {
        return null;
    }

    @RequestMapping(value = "/find_products_by_name")
    @CrossOrigin
    @Override
    public Response find_products_by_name(String product_name) {
        return null;
    }

    @RequestMapping(value = "/find_products_by_category")
    @CrossOrigin
    @Override
    public Response find_products_by_category(String category) {
        return null;
    }

    @RequestMapping(value = "/find_products_by_keywords")
    @CrossOrigin
    @Override
    public Response find_products_by_keywords(String key_words) {
        return null;
    }

    @RequestMapping(value = "/add_product_to_cart")
    @CrossOrigin
    @Override
    public Response add_product_to_cart(int storeID, int productID, int quantity) {
        return null;
    }

    @RequestMapping(value = "/remove_product_from_cart")
    @CrossOrigin
    @Override
    public Response remove_product_from_cart(int storeID, int productID) {
        return null;
    }

    @RequestMapping(value = "/view_user_cart")
    @CrossOrigin
    @Override
    public Response view_user_cart() {
        return null;
    }

    @RequestMapping(value = "/edit_product_quantity_in_cart")
    @CrossOrigin
    @Override
    public Response edit_product_quantity_in_cart(int storeID, int productID, int quantity) {
        return null;
    }

    @RequestMapping(value = "/buy_cart(")
    @CrossOrigin
    @Override
    public Response buy_cart(String paymentInfo, String supplyInfo) {
        return null;
    }

    @RequestMapping(value = "/open_store")
    @CrossOrigin
    @Override
    public Response open_store(String store_name) {
        return null;
    }

    @RequestMapping(value = "/add_product_review")
    @CrossOrigin
    @Override
    public Response add_product_review(int product_id, int store_id, String review) {
        return null;
    }

    @RequestMapping(value = "/rate_product")
    @CrossOrigin
    @Override
    public Response rate_product(int product_id, int store_id, int rate) {
        return null;
    }

    @RequestMapping(value = "/rate_store")
    @CrossOrigin
    @Override
    public Response rate_store(int store_id, int rate) {
        return null;
    }

    @RequestMapping(value = "/send_question_to_store")
    @CrossOrigin
    @Override
    public Response send_question_to_store(int store_id, String question) {
        return null;
    }

    @RequestMapping(value = "/send_question_to_admin")
    @CrossOrigin
    @Override
    public Response send_question_to_admin(String question) {
        return null;
    }

    @RequestMapping(value = "/view_user_purchase_history")
    @CrossOrigin
    @Override
    public Response view_user_purchase_history() {
        return null;
    }

    @RequestMapping(value = "/get_user_email")
    @CrossOrigin
    @Override
    public Response get_user_email() {
        return null;
    }

    @RequestMapping(value = "/get_user_name")
    @CrossOrigin
    @Override
    public Response get_user_name() {
        return null;
    }

    @RequestMapping(value = "/get_user_last_name")
    @CrossOrigin
    @Override
    public Response get_user_last_name() {
        return null;
    }

    @RequestMapping(value = "/unregister")
    @CrossOrigin
    @Override
    public Response unregister(String password) {
        return null;
    }

    @RequestMapping(value = "/edit_name")
    @CrossOrigin
    @Override
    public Response edit_name(String pw, String new_name) {
        return null;
    }

    @RequestMapping(value = "/edit_last_name")
    @CrossOrigin
    @Override
    public Response edit_last_name(String pw, String new_last_name) {
        return null;
    }

    @RequestMapping(value = "/edit_password")
    @CrossOrigin
    @Override
    public Response edit_password(String pw, String password) {
        return null;
    }

    @RequestMapping(value = "/edit_name_premium")
    @CrossOrigin
    @Override
    public Response edit_name_premium(String pw, String new_name, String answer) {
        return null;
    }

    @RequestMapping(value = "/edit_last_name_premium")
    @CrossOrigin
    @Override
    public Response edit_last_name_premium(String pw, String new_last_name, String answer) {
        return null;
    }

    @RequestMapping(value = "/edit_password_premium")
    @CrossOrigin
    @Override
    public Response edit_password_premium(String pw, String password, String answer) {
        return null;
    }

    @RequestMapping(value = "/get_user_security_question")
    @CrossOrigin
    @Override
    public Response get_user_security_question() {
        return null;
    }

    @RequestMapping(value = "/improve_security")
    @CrossOrigin
    @Override
    public Response improve_security(String password, String question, String answer) {
        return null;
    }

    @RequestMapping(value = "/add_product_to_store")
    @CrossOrigin
    @Override
    public Response add_product_to_store(int store_id, int quantity, String name, double price, String category, List<String> key_words) {
        return null;
    }

    @RequestMapping(value = "/delete_product_from_store")
    @CrossOrigin
    @Override
    public Response delete_product_from_store(int product_id, int store_id) {
        return null;
    }

    @RequestMapping(value = "/edit_product_name")
    @CrossOrigin
    @Override
    public Response edit_product_name(int product_id, int store_id, String name) {
        return null;
    }

    @RequestMapping(value = "/edit_product_price")
    @CrossOrigin
    @Override
    public Response edit_product_price(int product_id, int store_id, double price) {
        return null;
    }

    @RequestMapping(value = "/edit_product_category")
    @CrossOrigin
    @Override
    public Response edit_product_category(int product_id, int store_id, String category) {
        return null;
    }

    @RequestMapping(value = "/edit_product_key_words")
    @CrossOrigin
    @Override
    public Response edit_product_key_words(int product_id, int store_id, List<String> key_words) {
        return null;
    }

    @RequestMapping(value = "/add_owner")
    @CrossOrigin
    @Override
    public Response add_owner(String user_email_to_appoint, int store_id) {
        return null;
    }

    @RequestMapping(value = "/delete_owner")
    @CrossOrigin
    @Override
    public Response delete_owner(String user_email_to_delete_appointment, int store_id) {
        return null;
    }

    @RequestMapping(value = "/add_manager")
    @CrossOrigin
    @Override
    public Response add_manager(String user_email_to_appoint, int store_id) {
        return null;
    }

    @RequestMapping(value = "/delete_manager")
    @CrossOrigin
    @Override
    public Response delete_manager(String user_email_to_delete_appointment, int store_id) {
        return null;
    }

    @RequestMapping(value = "/close_store_temporarily")
    @CrossOrigin
    @Override
    public Response close_store_temporarily(int store_id) {
        return null;
    }

    @RequestMapping(value = "/open_close_store")
    @CrossOrigin
    @Override
    public Response open_close_store(int store_id) {
        return null;
    }

    @RequestMapping(value = "/view_store_management_information")
    @CrossOrigin
    @Override
    public Response view_store_management_information(int store_id) {
        return null;
    }

    @RequestMapping(value = "/manager_view_store_questions")
    @CrossOrigin
    @Override
    public Response manager_view_store_questions(int store_id) {
        return null;
    }

    @RequestMapping(value = "/manager_answer_question")
    @CrossOrigin
    @Override
    public Response manager_answer_question(int store_id, int question_id, String answer) {
        return null;
    }

    @RequestMapping(value = "/view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response view_store_purchases_history(int store_id) {
        return null;
    }

    @RequestMapping(value = "/close_store_permanently")
    @CrossOrigin
    @Override
    public Response close_store_permanently(int store_id) {
        return null;
    }

    @RequestMapping(value = "/remove_user")
    @CrossOrigin
    @Override
    public Response remove_user(String email) {
        return null;
    }

    @RequestMapping(value = "/admin_view_users_questions")
    @CrossOrigin
    @Override
    public Response admin_view_users_questions() {
        return null;
    }

    @RequestMapping(value = "/admin_answer_user_question")
    @CrossOrigin
    @Override
    public Response admin_answer_user_question(int question_id, String answer) {
        return null;
    }

    @RequestMapping(value = "/admin_view_store_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_store_purchases_history(int store_id) {
        return null;
    }

    @RequestMapping(value = "/admin_view_user_purchases_history")
    @CrossOrigin
    @Override
    public Response admin_view_user_purchases_history(String user_email) {
        return null;
    }

    @RequestMapping(value = "/get_market_stats")
    @CrossOrigin
    @Override
    public Response get_market_stats() {
        return new Response("null", "because");
    }
}


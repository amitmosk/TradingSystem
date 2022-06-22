package Acceptance.User.Admin;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;

class AnswerQuestions {
    private MarketFacade facade1;
    private MarketFacade facade2;
    private MarketFacade facade3;
    private MarketFacade facade4;
    private UserController uc;
    private PaymentAdapter pa;
    private SupplyAdapter sa;
    private String email;
    private String password;
    private String birth_date;
    private final int num_of_threads = 100;
    private String user_premium_security_email;
    private String user_password;
    private String user_founder_email;
    private String user_buyer_email;
    private String user_regular_email_1;
    private String user_regular_email_2;
    private String user_admin_email;
    private int store_id;
    private int product_id;
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private String prodname = "";

    public AnswerQuestions(){
        try{
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();

            this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade4 = new MarketFacade(paymentAdapter, supplyAdapter);

            uc = UserController.get_instance();
            pa = new PaymentAdapterImpl();
            sa = new SupplyAdapterImpl();

            // users information
            user_buyer_email = "buyer@email.com";
            user_founder_email = "founder@email.com";
            user_regular_email_1 = "regular1@email.com";
            user_regular_email_2 = "regular2@email.com";
            user_admin_email = "admin@gmail.com";
            user_premium_security_email = "premiumSecurity@email.com";
            user_password = "pass3Chec";
            birth_date =  LocalDate.now().minusYears(30).toString();
            String first_name = "name";
            String last_name = "last";
            email = "somthing@gmail.com";
            password = "aA12345";

            facade1.register(user_founder_email, user_password, first_name, last_name,birth_date);
            facade2.register(user_buyer_email, user_password, first_name, last_name,birth_date);
            facade3.register(user_regular_email_1, user_password, first_name, last_name,birth_date);
            facade4.register(user_regular_email_2, user_password, first_name, last_name,birth_date);

            store_id = open_store_get_id("Checker Store") ;
            product_id = add_prod_make_purchase_get_id(store_id);

            facade1.logout();
            facade2.logout();
            facade3.logout();
            facade4.logout();

            // register user with premium security
            facade1.register(user_premium_security_email, user_password, first_name, last_name,birth_date);
            facade1.improve_security(user_password, "What was your mother's maiden name?", "Sasson");
            facade1.logout();

            // add admin to  the system
            uc.add_admin(user_admin_email, user_password, "Barak", "Bahar");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private int open_store_get_id(String name){
        Response<Integer> response = facade1.open_store(name);
        return response.getValue();

    }
    private boolean check_if_purchase_exists(Response res, String email, int prod){
        boolean flag = false;

        if(res.getValue().getClass() == (new ConcurrentHashMap<Integer, StorePurchase>()).values().getClass()){
            Collection<StorePurchase> his = (Collection<StorePurchase>)res.getValue();
            for(StorePurchase p : his){
                if(p.getBuyer_email() == email && p.getProduct_and_totalPrice().containsKey(prod))
                    flag = true;
            }

        }
        else if(res.getValue().getClass() == StorePurchaseHistory.class){
            StorePurchaseHistory his = (StorePurchaseHistory)res.getValue();
            for(StorePurchase p : his.getPurchaseID_purchases().values()){
                if(p.getBuyer_email() == email && p.getProduct_and_totalPrice().containsKey(prod))
                    flag = true;
            }
        }
        return flag;
    }
    private int add_prod_make_purchase_get_id(int sore_id){
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("\n\ncheck_check\n\n");
        prodname += "l";
        int prod_id = StoreController.get_instance().getProduct_ids_counter();
        facade1.add_product_to_store(sore_id, 100, prodname, 10.0, "checker", new ArrayList<>());

        facade2.add_product_to_cart(sore_id, prod_id, 1);
        facade2.buy_cart(payment_info, supplyInfo);

        return prod_id;
    }
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private String make_assert_exception_message(String test, String test_case, boolean suppose_to_be_exception){
        String test_part = "Test: " + test + "\n";
        String case_part = "In test case: " + test_case + " ";
        if(suppose_to_be_exception)
            case_part = "No exception thrown " + case_part;
        else
            case_part = "Exception thrown " + case_part;

        return test_part + case_part;
    }
    private int num_of_questions(){
        return QuestionController.getInstance().getQuestion_ids_counter();

    }
    private int num_of_admin_questions(){
        return QuestionController.getInstance().view_users_to_admin_questions().size();
    }
    private void valid_purchase_history(Response res, String email, int prod, String test_name, String test_case){
        String message = make_assert_exception_message(test_name, test_case, false);
        assertFalse(check_was_exception(res), message);

        message = "Test: " + test_case + "\nTest case: " + test_case + " failed\nMissing user's (" + email + ") purchase of product " + prod;
        assertTrue(check_if_purchase_exists(res, email, prod), message);
    }
    private void valid_questions(Response res, int num_of_question, String question, String email, boolean answered, String answer, String test_case, String test_name){
        Object res_val = res.getValue();
        String message = make_assert_exception_message(test_name, test_case, false);
        assertFalse(check_was_exception(res), message);
        assertEquals((new LinkedList<String>()).getClass(), res_val.getClass());
        if(num_of_question == 0) {
            assertTrue(((LinkedList<String>)res_val).isEmpty());
        }
        else{
            if(res_val.getClass() == (new LinkedList<String>()).getClass()) {
                System.out.println("\n\nNUM OF QUESTIONS = " + num_of_question + "\nSIZE OF LIST = " + ((LinkedList<String>)res_val).size() + "\n\n");
                assertTrue(num_of_question == ((LinkedList<String>)res_val).size());
                boolean flag = false;
                for(String s : ((LinkedList<String>)res_val)){
                    if(s.contains(question) && s.contains(email) && !answered){
                        flag = true;
                    }
                    else if(s.contains(question) && s.contains(email) && s.contains(answer)){
                        flag = true;
                    }
                }
                assertTrue(flag);
            }
        }
    }
    //------------------------------- Admin operations --------------------------------------------------------------------------

    /**
     * Cases checked:
     * 1. guest tries to view empty admin question list
     * 2. guest tries to send admin question
     * 3. admin views empty question list
     * 4. connected user sends admin a question
     * 5. guest tries to view admin question list
     * 6. regular user tries to view admin question list
     * 7. admin views question list of size 1
     * 8. connected user sends admin another question
     * 9. admin views question list of size 2 (all from same user)
     * 10. user sends admin same question as different user
     * 11. admin views question list of size 3 (2 from same user, 2 same question different user)
     * 12. admin tries to answer question with an id that does not exist
     * 13. regular user tries to answer question
     * 14. admin answers a question
     * 15. admin views question list of size 3 (one of them is answered)
     * 16. admin re-answers user's question
     * 17. admin views question list of size 3 (one of them is re-answered)
     * 18. admin answers a different user's question
     * 19. admin views question list of size 3 (one of them is re-answered, one is answered and one is not answered)
     */
    @Test
    void send_to_admin_view_and_answer_questions(){
        Response res;
        boolean answered = true;
        boolean suppose_to_throw = true;
        String test_name = "send_to_admin_view_and_answer_questions";
        int next_question_id = num_of_questions();
        int admin_question_counter = num_of_admin_questions();
        int question1_id, question3_id;
        String message = make_assert_exception_message(test_name, "guest tries to view empty list of questions sent to admin", suppose_to_throw);

        res = facade2.admin_view_users_questions(); // no user is connected, no questions yet
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "guest user tries to send admin a question", suppose_to_throw);
        String question1 = "guest sends question";
        res = facade2.send_question_to_admin(question1);
        assertTrue(check_was_exception(res), message); // guest user can't send question to admin

//        facade1.login(user_admin_email, user_password);
//
//        res = facade1.admin_view_users_questions(); // admin views empty question list
//        valid_questions(res, question_counter, "", "", !answered,"", "guest tries to send empty list of questions", test_name);
//        facade1.logout();

        message = make_assert_exception_message(test_name, "assigned user tries to send admin a question", !suppose_to_throw);
        facade2.login(user_regular_email_1, user_password);
        question1 = "user sends admin a question in test: " + test_name;
        res = facade2.send_question_to_admin(question1);
        assertFalse(check_was_exception(res), message); // connected user sends admin a question
        question1_id = next_question_id;
        next_question_id++;
        admin_question_counter++;




        message = make_assert_exception_message(test_name, "guest user tries to view list of questions sent to admin", suppose_to_throw);
        res = facade1.admin_view_users_questions(); // no user is connected, there is a question
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "assigned user tries to view list of questions sent to admin", suppose_to_throw);
        res = facade2.admin_view_users_questions(); // regular user is connected, there is a question
        assertTrue(check_was_exception(res), message);

        facade1.login(user_admin_email, user_password);

        res = facade1.admin_view_users_questions(); // admin views question list of size small
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, !answered,"", "admin views question list of size small", test_name);

        message = make_assert_exception_message(test_name, "assigned user sends admin a question", !suppose_to_throw);
        String question2 = "this should work as well in test: " + test_name;
        res = facade2.send_question_to_admin(question2);
        assertFalse(check_was_exception(res), message); // connected user sends admin a question
        next_question_id++;
        admin_question_counter++;


        message = "admin views question list of size 2";
        res = facade1.admin_view_users_questions(); // admin views question list of size 2
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);

        facade2.logout();
        facade2.login(user_regular_email_2, user_password);

        message = make_assert_exception_message(test_name, "two different connected users send admin the same question", !suppose_to_throw);
        String question3 = "this should work as well in test: " + test_name; // different user same question
        res = facade2.send_question_to_admin(question3);
        assertFalse(check_was_exception(res), message); // connected user sends admin same question as different user
        question3_id = next_question_id;
        next_question_id++;
        admin_question_counter++;


        message = "admin views question list of size 3";
        res = facade1.admin_view_users_questions(); // admin views question list of size 3
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question3, user_regular_email_2, !answered,"", message, test_name);

        String answer = "i answer in test: " + test_name;

        message = make_assert_exception_message(test_name, "admin enters question id that does not exist", suppose_to_throw);
        res = facade1.admin_answer_user_question(0, answer); // admin enters question id that does not exist
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "regular user tries to answer question", suppose_to_throw);
        res = facade2.admin_answer_user_question(question1_id, answer); // regular user tries to answer question
        assertTrue(check_was_exception(res), message);

        message = "regular user tries to answer a question sent to admin";
        res = facade1.admin_view_users_questions(); // to make sure non of the 2 above worked
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question3, user_regular_email_2, !answered,"", message, test_name);

        message = make_assert_exception_message(test_name, "admin answers user question", !suppose_to_throw);
        res = facade1.admin_answer_user_question(question1_id, answer); // admin answers a question
        assertFalse(check_was_exception(res), message);

        message = "admin views question list- one is answered";
        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is answered)
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, answered, answer, message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question3, user_regular_email_2, !answered,"", message, test_name);

        answer += "!";
        message = make_assert_exception_message(test_name, "admin re-answers user's question", !suppose_to_throw);
        res = facade1.admin_answer_user_question(question1_id, answer); // admin re-answers user's question
        assertFalse(check_was_exception(res), message);

        message = "admin views question list- one is re-answered";
        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is re-answered)
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, answered, answer, message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question3, user_regular_email_2, !answered,"", message, test_name);

        message = make_assert_exception_message(test_name, "admin answers another user question", !suppose_to_throw);
        res = facade1.admin_answer_user_question(question3_id, answer); // admin answers different user's question
        assertFalse(check_was_exception(res), message);

        message = "admin views question list of size 3- one of them is re-answered, one is answered and one is not answered";
        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is re-answered, one is answered and one is not answered)
        valid_questions(res, admin_question_counter, question1, user_regular_email_1, answered, answer, message, test_name);
        valid_questions(res, admin_question_counter, question2, user_regular_email_1, !answered,"", message, test_name);
        valid_questions(res, admin_question_counter, question3, user_regular_email_2, answered,answer, message, test_name);

        facade2.logout();
        facade1.logout();
    }



    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not an admin
     * 3. admin enters a store id that does not exist
     * 4. admin views user's purchase history
     */
    @Test
    void admin_view_store_purchases_history() throws MarketException {
        Response res;
        String test_name = "admin_view_store_purchases_history";
        boolean suppose_to_throw = true;
        String message;

        message = make_assert_exception_message(test_name, "no one is connected", suppose_to_throw);
        res = facade1.admin_view_store_purchases_history(store_id); // no one is connected
        assertTrue(check_was_exception(res), message);

        facade1.login(user_buyer_email, user_password);

        message = make_assert_exception_message(test_name, "user connected is not an admin", suppose_to_throw);
        res = facade1.admin_view_store_purchases_history(store_id); // user connected is not an admin
        assertTrue(check_was_exception(res), message);

        facade2.login(user_admin_email, user_password);

        message = make_assert_exception_message(test_name, "admin enters a store id that does not exist", suppose_to_throw);
        res = facade2.admin_view_store_purchases_history(store_id + 2); // admin enters a store id that does not exist
        assertTrue(check_was_exception(res), message);

        res = facade2.admin_view_store_purchases_history(store_id); // admin views store's purchase history
        valid_purchase_history(res, user_buyer_email, product_id, test_name, "admin views store's purchase history");


        facade1.logout();
        facade2.logout();

    }

    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not an admin
     * 3. admin views user's empty purchase history
     * 4. admin views user's purchase history
     */
    @Test
    void admin_view_user_purchases_history() throws MarketException {
        Response res;
        boolean suppose_to_throw = true;
        String test_name = "admin_view_user_purchases_history";
        String message;

        message = make_assert_exception_message(test_name, "no one is connected", suppose_to_throw);
        res = facade1.admin_view_user_purchases_history(user_buyer_email); // no one is connected
        assertTrue(check_was_exception(res), message);

        facade1.login(user_premium_security_email, user_password);

        message = make_assert_exception_message(test_name, "user connected is not an admin", suppose_to_throw);
        res = facade1.admin_view_user_purchases_history(user_buyer_email); // user connected is not an admin
        assertTrue(check_was_exception(res), message);

        facade2.login(user_admin_email, user_password);

        message = make_assert_exception_message(test_name, "admin views user's empty purchase history", !suppose_to_throw);
        res = facade2.admin_view_user_purchases_history(user_regular_email_1); // admin views user's empty purchase history
        assertFalse(check_was_exception(res), message);
        if(res.getValue().getClass() == UserPurchaseHistory.class){
            UserPurchaseHistory his = (UserPurchaseHistory)res.getValue();
            assertTrue(his.getHistoryList().isEmpty()); // todo
        }

        facade2.login(user_admin_email, user_password);

        message = make_assert_exception_message(test_name, "admin views user's purchase history", !suppose_to_throw);
        res = facade2.admin_view_user_purchases_history(user_buyer_email); // admin views user's purchase history
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == UserPurchaseHistory.class){
            UserPurchaseHistory his = (UserPurchaseHistory)res.getValue();
            assertTrue(his.check_if_user_buy_from_this_store(store_id)); // todo
            assertTrue(his.check_if_user_buy_this_product(store_id, product_id)); // todo

        }
        facade1.logout();
        facade2.logout();

    }





}
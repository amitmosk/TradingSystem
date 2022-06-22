package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EditUserDetails {
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
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private String prodname = "";

    public EditUserDetails(){
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

            int id = open_store_get_id("Checker Store") ;
            add_prod_make_purchase_get_id(id);

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
        facade1.open_store(name);
        return num_of_stores();
    }
    private int num_of_stores(){
        Response res = facade1.get_all_stores();
        int stores_count = 0;
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            stores_count = ((ArrayList<StoreInformation>) res.getValue()).size();
        }
        return stores_count;
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
    private void valid_all_user_questions(List<String> questions, String test_case){
        Response res = facade2.get_user_questions();
        Object res_val = res.getValue();
        int num_of_question = questions.size();
        ArrayList<String> Qlist = null;
        if(res_val.getClass() == (new ArrayList<String>()).getClass())
            Qlist = (ArrayList<String>)res_val;

        String message = make_assert_exception_message("get_user_questions", test_case, false);
        assertFalse(check_was_exception(res), message);

        assertTrue((Qlist == null && num_of_question == 0) || num_of_question == Qlist.size());
        for(String question : questions)
        {
            boolean flag = false;
            for(String q : Qlist)
                if(q.contains(question)){
                    flag = true;
                    break;
                }

            assertTrue(flag, "Test: get_user_questions\nTest case: " + test_case + "\nQuestion: " + question + " not found");

        }
    }


    //------------------------------- User information --------------------------------------------------------------------------

    /*
     * Cases checked:
     * 1. get last name with no user connected
     * 2. get last name with user connected
     */
    @Test
    void get_user_last_name() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "get_user_last_name";
        String message;

        message = make_assert_exception_message(test_name, "get last name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.get_user_last_name()); // get last name with no user connected
        assertTrue(result, message);

        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "get last name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.get_user_last_name()); // get last name with user connected
        assertFalse(result, message);

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. get name with no user connected
     * 2. get name with user connected
     */
    @Test
    void get_user_name() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "get_user_name";
        String message;

        message = make_assert_exception_message(test_name, "get name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.get_user_name()); // get name with no user connected
        assertTrue(result, message);

        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "get name with user connected", !suppose_to_throw);
        result = check_was_exception(facade1.get_user_name()); // get name with user connected
        assertFalse(result, message);

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. edit password with wrong old password
     * 2. edit password
     * 3. edit password with invalid new password
     * 4. edit password with empty new password
     * 5. edit password with no user connected
     */
    @Test
    void edit_password() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_password";
        String message;

        message = make_assert_exception_message(test_name, "edit password with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_password(user_password, "pass12CH")); // edit password with no user connected
        assertTrue(result, message);

        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "edit password with invalid new password", suppose_to_throw);
        result = check_was_exception(facade1.edit_password(user_password, "pass12r")); // edit password with invalid new password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with empty new password", suppose_to_throw);
        result = check_was_exception(facade1.edit_password(user_password, "")); // edit password with empty new password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with invalid old password", suppose_to_throw);
        result = check_was_exception(facade1.edit_password(user_password + "123", "pass12RT")); // edit password with invalid old password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password", !suppose_to_throw);
        result = check_was_exception(facade1.edit_password(user_password, "pass12rT")); // edit password
        assertFalse(result, message);

        result = check_was_exception(facade1.edit_password("pass12rT", user_password)); // edit password
        assertFalse(result, message);

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. view purchase history with guest user no purchases
     * 2. view purchase history with assigned user no purchases
     * 3. view purchase history with guest user that had purchased
     * 4. view purchase history with assigned user that had purchased
     */
    @Test
    void view_user_purchase_history() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "view_user_purchase_history";
        String message;

        message = make_assert_exception_message(test_name, "view purchase history with guest user no purchases", suppose_to_throw);
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with guest user no purchases
        assertTrue(result, message);

        facade1.add_product_to_cart(1, 1, 1);
        facade1.buy_cart(payment_info, supplyInfo);

        message = make_assert_exception_message(test_name, "view purchase history with guest user that had purchased", suppose_to_throw);
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with guest user that had purchased
        assertTrue(result, message);

        facade1.login(user_buyer_email, user_password);

        message = make_assert_exception_message(test_name, "view purchase history with assigned user that had purchased", !suppose_to_throw);
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with assigned user that had purchased
        assertFalse(result, message);

        facade1.login(user_regular_email_1, user_password);

        message = make_assert_exception_message(test_name, "view purchase history with assigned user no purchases", !suppose_to_throw);
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with assigned user no purchases
        assertFalse(result, message);

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. improve security with no user connected
     * 2. improve security with user connected
     */
    @Test
    void improve_security() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "improve_security";
        String message;

        message = make_assert_exception_message(test_name, "improve security with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.improve_security(user_password, "Where were you born?", "Tel-Aviv")); // improve security with no user connected
        assertTrue(result, message);

        facade1.login(user_regular_email_2, user_password);

        message = make_assert_exception_message(test_name, "improve security with user connected", !suppose_to_throw);
        result = check_was_exception(facade1.improve_security(user_password, "Where were you born?", "Tel-Aviv")); // improve security with user connected
        assertFalse(result, message);

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. edit name with no user connected
     * 2. edit name with user connected
     * 3. edit name to empty name with user connected
     * 4. edit name to invalid name with user connected
     * 5. edit name of premium account with no security improvement
     */
    @Test
    void edit_name() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_name";
        String message;

        message = make_assert_exception_message(test_name, "edit name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name( "Eylon")); // edit name with no user connected
        assertTrue(result, message);

        facade1.login(user_regular_email_1, user_password);

        message = make_assert_exception_message(test_name, "edit name with user connected", !suppose_to_throw);
        result = check_was_exception(facade1.edit_name("Eylon")); // edit name with user connected
        assertFalse(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit name to empty name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name( "")); // edit name to empty name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit name to invalid name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name("EylonintHamellonit")); // edit name to invalid name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue()); // todo

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. edit last name with no user connected
     * 2. edit last name with user connected
     * 3. edit last name to empty last name with user connected
     * 4. edit last name to invalid last name with user connected
     */
    @Test
    void edit_last_name() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_last_name";
        String message;

        message = make_assert_exception_message(test_name, "edit last name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name("Eylon")); // edit last name with no user connected
        assertTrue(result, message);

        facade1.login(user_regular_email_1, user_password);

        message = make_assert_exception_message(test_name, "edit last name with user connected", !suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name( "Eylon")); // edit last name with user connected
        assertFalse(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit last name to empty last name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name( "")); // edit last name to empty last name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit last name to invalid last name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name( "EylonintHamellonit")); // edit last name to invalid last name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        facade1.logout();


    }

    /*
     * Cases checked:
     * 1. edit last name with no user connected
     * 2. edit last name with user connected- wrong security answer
     * 3. edit last name with user connected- correct security answer
     * 4. edit last name to empty last name with user connected
     * 5. edit last name to invalid last name with user connected
     */
    @Test
    void edit_last_name_premium() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_last_name_premium";
        String message;

        message = make_assert_exception_message(test_name, "edit last name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name_premium("Eylon", "Sasson")); // edit last name with no user connected
        assertTrue(result, message);

        facade1.login(user_premium_security_email, user_password);

        message = make_assert_exception_message(test_name, "edit last name with user connected- wrong security answer", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name_premium( "Eylon", "Sade")); // edit last name with user connected- wrong security answer
        assertTrue(result, message);
        assertEquals("last", facade1.get_user_last_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit last name with user connected- correct security answer", !suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name_premium( "Eylon", "Sasson")); // edit last name with user connected- correct security answer
        assertFalse(result, message);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit last name to empty last name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name_premium( "", "Sasson")); // edit last name to empty last name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit last name to invalid last name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_last_name_premium( "EylonintHamellonit", "Sasson")); // edit last name to invalid last name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_last_name().getValue()); // todo

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. edit name with no user connected
     * 2. edit name with user connected- wrong security answer
     * 3. edit name with user connected- correct security answer
     * 4. edit name to empty name with user connected
     * 5. edit name to invalid name with user connected
     */
    @Test
    void edit_name_premium() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_name_premium";
        String message;

        message = make_assert_exception_message(test_name, "edit name with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sasson")); // edit name with no user connected
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit name with user connected- wrong security answer", suppose_to_throw);
        facade1.login(user_premium_security_email, user_password);
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sade")); // edit name with user connected- wrong security answer
        assertTrue(result, message);
        assertEquals("name", facade1.get_user_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit name with user connected- correct security answer", !suppose_to_throw);
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sasson")); // edit name with user connected- correct security answer
        assertFalse(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue()); // todo

        message = make_assert_exception_message(test_name, "edit name to empty name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name_premium( "", "Sasson")); // edit name to empty name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue());

        message = make_assert_exception_message(test_name, "edit name to invalid name with user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_name_premium("EylonintHamellonit", "Sasson")); // edit name to invalid name with user connected
        assertTrue(result, message);
        assertEquals("Eylon", facade1.get_user_name().getValue()); // todo

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. edit password with no user connected
     * 2. edit password with invalid new password
     * 3. edit password with empty new password
     * 4. edit password with wrong old password
     * 5. edit password with wrong security answer
     * 6. edit password
     * 7. edit password
     */
    @Test
    void edit_password_premium() {
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "edit_password_premium";
        String message, message_logged;

        message = make_assert_exception_message(test_name, "edit password with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.edit_password_premium(user_password, "pass12CH", "Sasson")); // edit password with no user connected
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with invalid new password", suppose_to_throw);
        facade1.login(user_premium_security_email, user_password);
        result = check_was_exception(facade1.edit_password_premium(user_password, "pass12r", "Sasson")); // edit password with invalid new password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with empty new password", suppose_to_throw);
        result = check_was_exception(facade1.edit_password_premium(user_password, "", "Sasson")); // edit password with empty new password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with invalid old password", suppose_to_throw);
        result = check_was_exception(facade1.edit_password_premium("pass3ec", "pass12CH", "Sasson")); // edit password with invalid old password
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit password with wrong security answer", suppose_to_throw);
        result = check_was_exception(facade1.edit_password_premium("pass3ec", "pass12CH", "Sade")); // edit password with wrong security answer
        assertTrue(result, message);

        facade1.logout();
        message_logged = "Test: " + test_name + "\n User " + user_premium_security_email + "failed to login after test case: ";
        facade1.login(user_premium_security_email, user_password);
        assertTrue(facade1.is_logged(), message_logged + "edit password with wrong security answer"); // checks that you can still login with old password

        message = make_assert_exception_message(test_name, "edit password", !suppose_to_throw);
        result = check_was_exception(facade1.edit_password_premium(user_password, "pass12rT", "Sasson")); // edit password
        assertFalse(result, message);
        facade1.logout();
        facade1.login(user_premium_security_email, "pass12rT");
        assertTrue(facade1.is_logged(), message_logged + "edit password"); // checks that you can login with new password

        result = check_was_exception(facade1.edit_password_premium("pass12rT", user_password, "Sasson")); // edit password
        assertFalse(result, message);
        facade1.logout();
        facade1.login(user_premium_security_email, user_password);
        assertTrue(facade1.is_logged(), message_logged + "edit password"); // checks that you can login with new password

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. get question with no user connected
     * 2. get question with premium account user connected
     * 3. get question with regular user connected
     */
    @Test
    void get_user_security_question(){
        boolean result;
        boolean suppose_to_throw = true;
        String test_name = "get_user_security_question";
        String message;

        message = make_assert_exception_message(test_name, "get question with no user connected", suppose_to_throw);
        result = check_was_exception(facade1.get_user_security_question()); // get question with no user connected
        assertTrue(result, message);

        facade1.login(user_premium_security_email, user_password);

        message = make_assert_exception_message(test_name, "get security question with premium account user connected", !suppose_to_throw);
        Response response = facade1.get_user_security_question();
        result = check_was_exception(response); // get question with premium account user connected
        assertFalse(result, message + "\n" + response.getMessage());
        assertEquals("What was your mother's maiden name?", response.getValue());

        facade1.logout();
        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "get security question with regular user connected", suppose_to_throw);
        result = check_was_exception(facade1.get_user_security_question()); // get question with regular user connected
        assertTrue(result, message);

        facade1.logout();

    }

    @Test
    void get_user_questions(){
        Response res;
        boolean suppose_to_throw = true;
        String test_name = "get_user_questions";
        int store_counter = num_of_stores();
        String message, test_case, question;
        List<String> questions = new ArrayList<>();
        String founder_email = "founder@user.com";
        String new_user = "new@user.com";
        int store_id;

        // --------------------------- All users register ---------------------------
        message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
        res = facade1.register(founder_email, password, "founder", "founder", birth_date);
        assertFalse(check_was_exception(res), message);

        res = facade2.register(new_user, password, "user", "user", birth_date);
        assertFalse(check_was_exception(res), message);

        message = "Test: " + test_name + "\nFounder opens store\n";
        res = facade1.open_store("Store for test: " + test_name);
        assertFalse(check_was_exception(res), message);
        store_id = num_of_stores();

        question = "This shouldn't work";
        test_case = "user tries to send question to store with no purchase- this should not work";
        facade2.send_question_to_store(store_id, question);
        valid_all_user_questions(questions, test_case);

        question = "I made a purchase so this should work";
        test_case = "user sends question to store- this should work";
        add_prod_make_purchase_get_id(store_id);
        facade2.send_question_to_store(store_id, question);
        questions.add(question);
        valid_all_user_questions(questions, test_case);

        question = "Hello admin, how are you?";
        test_case = "user sends question to admin- this should work";
        facade2.send_question_to_admin(question);
        questions.add(question);
        valid_all_user_questions(questions, test_case);

        question = "I made a mistake with the store ID so this shouldn't work";
        test_case = "user sends question to invalid store ID- this shouldn't work";
        facade2.send_question_to_store(store_id + 3, question);
        valid_all_user_questions(questions, test_case);

        question = "I made a purchase so this should work (100)";
        test_case = "user sends question to store- this should work";
        for(int i = 0; i < 100; i++){
            facade2.send_question_to_store(store_id, i + question);
            questions.add(i + question);
            valid_all_user_questions(questions, test_case);
        }

        question = "I'm sending lots of messages to admin, HAHA (100)";
        test_case = "user sends question to admin- this should work";
        for(int i = 0; i < 100; i++){
            facade2.send_question_to_admin(i + question);
            questions.add(i + question);
            valid_all_user_questions(questions, test_case);
        }

        facade1.logout();
        facade2.logout();


    }
}
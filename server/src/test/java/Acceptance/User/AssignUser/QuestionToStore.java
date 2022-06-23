//package Acceptance.User.AssignUser;
//
//import TradingSystem.server.Domain.ExternalSystems.*;
//import TradingSystem.server.Domain.Facade.MarketFacade;
//import TradingSystem.server.Domain.Questions.QuestionController;
//import TradingSystem.server.Domain.StoreModule.Appointment.Appointment;
//import TradingSystem.server.Domain.StoreModule.Product.Product;
//import TradingSystem.server.Domain.StoreModule.Store.Store;
//import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
//import TradingSystem.server.Domain.StoreModule.StoreController;
//import TradingSystem.server.Domain.UserModule.AssignUser;
//import TradingSystem.server.Domain.UserModule.CartInformation;
//import TradingSystem.server.Domain.UserModule.UserController;
//import TradingSystem.server.Domain.Utils.Exception.MarketException;
//import TradingSystem.server.Domain.Utils.Response;
//import TradingSystem.server.Service.MarketSystem;
//import TradingSystem.server.Service.NotificationHandler;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.Map;
//
//import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
//import static org.junit.jupiter.api.Assertions.*;
//
//class QuestionToStore {
//    private MarketFacade facade1;
//    private MarketFacade facade2;
//    private MarketFacade facade3;
//    private MarketFacade facade4;
//    private final int num_of_products = 50;
//    private final int price = 100;
//    private int productId;
//    private String email = "amit@gmail.com";
//    private String manager_email = "manager@gmail.com";
//    private String name = "amit";
//    private String last_name = "grumet";
//    private String password = "aA123456";
//    private PaymentAdapter paymentAdapter;
//    private SupplyAdapter supplyAdapter;
//    private MarketFacade marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
//    private MarketFacade manager = new MarketFacade(paymentAdapter, supplyAdapter);
//    private MarketFacade general_user = new MarketFacade(paymentAdapter, supplyAdapter);
//    private String birth_date;
//    private SupplyInfo supplyInfo = new SupplyInfo("1", "2", "3", "4", "5");
//    private PaymentInfo payment_info = new PaymentInfo("123", "456", "789", "245", "123", "455");
//    private String user_password;
//    private String user_regular_email_1;
//    private String prodname = "";
//
//    public QuestionToStore() {
//        try{
//            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
//            this.paymentAdapter = marketSystem.getPayment_adapter();
//            this.supplyAdapter = marketSystem.getSupply_adapter();
//        }
//        catch (Exception e){
//            System.out.println(e.getMessage());
//        }
//    }
//
//    @BeforeEach
//    void SetUp() {
//        NotificationHandler.setTestsHandler();
//        MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
//        this.paymentAdapter = marketSystem.getPayment_adapter();
//        this.supplyAdapter = marketSystem.getSupply_adapter();
//
//        this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
//        this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
//        this.facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
//        this.facade4 = new MarketFacade(paymentAdapter, supplyAdapter);
//
//        uc = UserController.get_instance();
//        pa = new PaymentAdapterImpl();
//        sa = new SupplyAdapterImpl();
//
//        // users information
//        user_buyer_email = "buyer@email.com";
//        user_founder_email = "founder@email.com";
//        user_regular_email_1 = "regular1@email.com";
//        user_regular_email_2 = "regular2@email.com";
//        user_admin_email = "admin@gmail.com";
//        user_premium_security_email = "premiumSecurity@email.com";
//        user_password = "pass3Chec";
//        birth_date =  LocalDate.now().minusYears(30).toString();
//        String first_name = "name";
//        String last_name = "last";
//        email = "somthing@gmail.com";
//        password = "aA12345";
//
//        facade1.register(user_founder_email, user_password, first_name, last_name,birth_date);
//        facade2.register(user_buyer_email, user_password, first_name, last_name,birth_date);
//        facade3.register(user_regular_email_1, user_password, first_name, last_name,birth_date);
//        facade4.register(user_regular_email_2, user_password, first_name, last_name,birth_date);
//
//        int id = open_store_get_id("Checker Store") ;
//        add_prod_make_purchase_get_id(id);
//
//        facade1.logout();
//        facade2.logout();
//        facade3.logout();
//        facade4.logout();
//
//        // register user with premium security
//        facade1.register(user_premium_security_email, user_password, first_name, last_name,birth_date);
//        facade1.improve_security(user_password, "What was your mother's maiden name?", "Sasson");
//        facade1.logout();
//
//        // add admin to  the system
//        uc.add_admin(user_admin_email, user_password, "Barak", "Bahar");
//    }
//        catch (Exception e){
//        System.out.println(e.getMessage());
//    }
//
//
//
//
//
//
//
//
//
//
//        this.productId = 1;
//        this.birth_date = LocalDate.now().minusYears(30).toString();
//        marketFacade.clear();
//        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
//        manager = new MarketFacade(paymentAdapter, supplyAdapter);
//        general_user = new MarketFacade(paymentAdapter, supplyAdapter);
//        marketFacade.register(email, password, name, last_name, birth_date);
//        manager.register(manager_email, password, name, last_name, birth_date);
//        general_user.register("general@gmail.com", password, name, last_name, birth_date);
//        marketFacade.open_store("amit store");
//        add_product();
//        marketFacade.add_manager(manager_email, 1);
//    }
//
//    private int add_product() {
//        ArrayList<String> arraylist = new ArrayList<>();
//        arraylist.add("fruits");
//        Response<Map<Product, Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
//        return r.getValue().keySet().stream().findAny().get().getProduct_id();
//    }
//    private void buy_product() {
//        marketFacade.add_product_to_cart(1, productId, 20);
//        Response res = marketFacade.buy_cart(payment_info, supplyInfo);
//    }
//    private boolean check_was_exception(Response response) {
//        return response.WasException();
//    }
//    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }
//    private int num_of_questions(){
//        return QuestionController.getInstance().getQuestion_ids_counter();
//
//    }
//    private int open_store_get_id(String name){
//        facade1.open_store(name);
//        return num_of_stores();
//    }
//    private int num_of_stores(){
//        Response res = facade1.get_all_stores();
//        int stores_count = 0;
//        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
//            stores_count = ((ArrayList<StoreInformation>) res.getValue()).size();
//        }
//        return stores_count;
//    }
//    private void founder_exist(String founder, int store_id) throws MarketException {
//        boolean founder_exist = false;
//        Store store = facade1.get_store(store_id);
//        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
//        for(AssignUser u : staff.keySet()){
//            if(u.get_user_email() == founder){
//                founder_exist = true;
//                break;
//            }
//        }
//        assertTrue(founder_exist, "founder "+ founder + " does not exist in store's " + store_id + " staff list");
//    }
//    private String make_assert_exception_message(String test, String test_case, boolean suppose_to_be_exception){
//        String test_part = "Test: " + test + "\n";
//        String case_part = "In test case: " + test_case + " ";
//        if(suppose_to_be_exception)
//            case_part = "No exception thrown " + case_part;
//        else
//            case_part = "Exception thrown " + case_part;
//
//        return test_part + case_part;
//    }
//
//    private int add_prod_make_purchase_get_id(int sore_id){
//        ArrayList<String> arraylist = new ArrayList<>();
//        arraylist.add("\n\ncheck_check\n\n");
//        prodname += "l";
//        int prod_id = StoreController.get_instance().getProduct_ids_counter();
//        facade1.add_product_to_store(sore_id, 100, prodname, 10.0, "checker", new ArrayList<>());
//
//        facade2.add_product_to_cart(sore_id, prod_id, 1);
//        facade2.buy_cart(payment_info, supplyInfo);
//
//        return prod_id;
//    }
//    private void valid_questions(Response res, int num_of_question, String question, String email, boolean answered, String answer, String test_case, String test_name){
//        Object res_val = res.getValue();
//        String message = make_assert_exception_message(test_name, test_case, false);
//        assertFalse(check_was_exception(res), message);
//        assertEquals((new LinkedList<String>()).getClass(), res_val.getClass());
//        if(num_of_question == 0) {
//            assertTrue(((LinkedList<String>)res_val).isEmpty());
//        }
//        else{
//            if(res_val.getClass() == (new LinkedList<String>()).getClass()) {
//                System.out.println("\n\nNUM OF QUESTIONS = " + num_of_question + "\nSIZE OF LIST = " + ((LinkedList<String>)res_val).size() + "\n\n");
//                assertTrue(num_of_question == ((LinkedList<String>)res_val).size());
//                boolean flag = false;
//                for(String s : ((LinkedList<String>)res_val)){
//                    if(s.contains(question) && s.contains(email) && !answered){
//                        flag = true;
//                    }
//                    else if(s.contains(question) && s.contains(email) && s.contains(answer)){
//                        flag = true;
//                    }
//                }
//                assertTrue(flag);
//            }
//        }
//    }
//
//    //------------------------------------------------ Questions --------------------------------------------------------------------------
//
//
//    @Test
//    void send_question_to_store_happy() {
//        //happy
//        buy_product();
//        String q = "how can i control the world";
//        Response r = marketFacade.send_question_to_store(1, q);
//        check_was_not_exception("Question send to the store successfully", r);
//        Response questions = manager.manager_view_store_questions(1);
//        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
//            boolean flag = false;
//            for (String s : ((LinkedList<String>) questions.getValue())) {
//                if (s.contains(q)) {
//                    flag = true;
//                }
//            }
//            assertTrue(flag);
//        }
//    }
//
//    @Test
//    void send_question_to_store_sad() {
//        //sad
//        String q = "how can i control the worlds?";
//        Response rSad = marketFacade.send_question_to_store(1, q);
//        check_was_exception(rSad);
//        Response questions = manager.manager_view_store_questions(1);
//        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
//            boolean flag = false;
//            for (String s : ((LinkedList<String>) questions.getValue())) {
//                if (s.contains(q)) {
//                    flag = true;
//                }
//            }
//            assertFalse(flag);
//        }
//    }
//
//    @Test
//    void manager_view_store_questions() {
//        Response questions = manager.manager_view_store_questions(1);
//        assertEquals((new LinkedList<String>()).getClass(), questions.getValue().getClass()); // manager view empty list of questions
//
//        Response r = marketFacade.manager_view_store_questions(1);
//        check_was_exception(r); // not a manager, shouldn't be able to view
//
//        buy_product();
//        String q = "how can i control the world";
//        r = marketFacade.send_question_to_store(1, q);
//        check_was_not_exception("Question send to the store successfully", r);
//        questions = manager.manager_view_store_questions(1);
//        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
//            boolean flag = false;
//            for (String s : ((LinkedList<String>) questions.getValue())) {
//                if (s.contains(q)) {
//                    flag = true;
//                }
//            }
//            assertTrue(flag);
//        }
//    }
//
//
//    @Test
//    void manager_answer_question() throws MarketException {
//        Response res;
//        String founder = "founder@managerAnswerQuestion.com";
//        String owner = "owner@managerAnswerQuestion.com";
//        String manager = "manager@managerAnswerQuestion.com";
//        boolean suppose_to_throw = true;
//        String test_name = "manager_answer_question";
//        String message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
//        String test_case, question;
//        int question_counter = num_of_questions()-1;
//        int Qid1;
//
//        // all test characters register the system
//        res = facade1.register(founder, user_password, "founder", "founder", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade4.register(owner, user_password, "owner", "owner", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade3.register(manager, user_password, "manager", "manager", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade2.login(user_regular_email_1, user_password);
//        assertFalse(check_was_exception(res), message);
//
//        // founder opens store
//        int store_id = open_store_get_id("new store for test: " + test_name);
//        founder_exist(founder, store_id);
//
//        // founder adds owner as store owner
//        message = make_assert_exception_message(test_name, "founder adds owner as store owner", !suppose_to_throw);
//        res = facade1.add_owner(owner, store_id);
//        assertFalse(check_was_exception(res), message);
//
//        // owner adds manager1 as store manager
//        message = make_assert_exception_message(test_name, "owner adds manager as store manager", !suppose_to_throw);
//        res = facade4.add_manager(manager, store_id);
//        assertFalse(check_was_exception(res), message);
//
//        question = "How are you my friends?";
//        test_case = "user tries sends store a question";
//        message = "Test: " + test_name + "\nTest case: " + test_case + " failed- exception thrown";
//        add_prod_make_purchase_get_id(store_id);
//        res = facade2.send_question_to_store(store_id,question);
//        assertFalse(check_was_exception(res), message);
//        question_counter++;
//        Qid1 = question_counter;
//
//
//        res = facade3.manager_view_store_questions(store_id);
//        valid_questions(res, question_counter, question, user_regular_email_1, false, "", test_case, test_name);
//
//        message = "Test: " + test_name + "\nTest case: ";
//        test_case = "manager tried to answer a question from a store that does not exist";
//        res = facade3.manager_answer_question(store_id + 3, Qid1, "This shouldn't work");
//        assertTrue(check_was_exception(res), message + test_case + "\n");
//
//        test_case = "manager tried to answer a question from an ID that does not exist";
//        res = facade3.manager_answer_question(store_id, Qid1 + 9, "This shouldn't work");
//        assertTrue(check_was_exception(res), message + test_case + "\n");
//
//        test_case = "manager answers a question";
//        res = facade3.manager_answer_question(store_id, Qid1, "This should work");
//        assertFalse(check_was_exception(res), message + test_case + "\n");
//        res = facade3.manager_view_store_questions(store_id);
//        valid_questions(res, question_counter, question, user_regular_email_1, true, "This should work", test_case, test_name);
//
//        facade1.logout();
//        facade2.logout();
//        facade3.logout();
//        facade4.logout();
//
//    }
//
//}
package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.Appointment.Appointment;
import TradingSystem.server.Domain.StoreModule.Appointment.AppointmentInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OpenCloseStore {
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

    //------------------------- Initialization --------------------------------------------------------------------------



    public OpenCloseStore() {
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
    @BeforeEach
    void setUp() throws MarketException {
        
        // make sure no user is logged in
        facade1.logout();
        facade2.logout();
        facade3.logout();
        facade4.logout();


    }

    //------------------------------- Helper functions --------------------------------------------------------------------------

    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void start_threads(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        } // running all the threads parallel
    }
    private void join_threads(List<Thread> threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (Exception e) {
            assertTrue(false, "there was error while running the threads");
        }
    }
    private int num_of_stores(){
        Response res = facade1.get_all_stores();
        int stores_count = 0;
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            stores_count = ((ArrayList<StoreInformation>) res.getValue()).size();
        }
        return stores_count;
    }
    private boolean find_store(String name, int num_of_stores) {
        Response res = facade1.get_all_stores();
        int counter = 0;

        if (res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            ArrayList<StoreInformation> stores = ((ArrayList<StoreInformation>) res.getValue());
            for (StoreInformation info : stores)
                if (info.getName() == name)
                {
                    counter++;
                    if(num_of_stores == counter)
                        return true;
                }
        }
        return false;
    }
    private boolean find_store(String name, int num_of_stores, boolean active) {
        Response res = facade1.get_all_stores();
        int counter = 0;
        boolean ret = false;
        if (res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            ArrayList<StoreInformation> stores = ((ArrayList<StoreInformation>) res.getValue());
            for (StoreInformation info : stores)
                if (info.getName() == name && info.isActive() == active)
                {
                    counter++;

                }
        }

        if(num_of_stores == counter && counter > 0)
            ret = true;

        return ret;
    }
    private String get_store_founder(String name, int num_of_stores){
        Response res = facade1.get_all_stores();
        int counter = 0;
        if (res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            ArrayList<StoreInformation> stores = ((ArrayList<StoreInformation>) res.getValue());
            for (StoreInformation info : stores)
                if (info.getName() == name)
                {
                    counter++;
                    if(num_of_stores == counter)
                        return info.getFounder_email();
                }
        }
        return "";
    }
    private int open_store_get_id(String name){
        facade1.open_store(name);
        return num_of_stores();
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

    private Response add_prod_make_purchase_closed_store(int sore_id){
        ArrayList<String> arraylist = new ArrayList<>();
        Response res;
        arraylist.add("check_check");
        int prod_id = StoreController.get_instance().getProduct_ids_counter();
        res = facade1.add_product_to_store(sore_id, 100, "CheckItem", 10.0, "checker", new ArrayList<>());
        assertTrue(check_was_exception(res), "Store founder added products to a temporarily \\ permanently closed store");
        res = facade2.add_product_to_cart(sore_id, prod_id, 1);
        assertTrue(check_was_exception(res), "User added products to cart from a temporarily \\ permanently closed store");
        return  facade2.buy_cart(payment_info, supplyInfo);
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

    private void founder_exist(String founder, int store_id) throws MarketException {
        boolean founder_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == founder){
                founder_exist = true;
                break;
            }
        }
        assertTrue(founder_exist, "founder "+ founder + " does not exist in store's " + store_id + " staff list");
    }

    private void owner_exist(String owner, int store_id, boolean should_appear, int staff_size) throws MarketException {
        boolean owner_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == owner){
                owner_exist = true;
                break;
            }
        }
        if(should_appear)
            assertTrue(owner_exist, "owner "+ owner + " does not exist in store's " + store_id + "staff list");
        else
            assertFalse(owner_exist, "owner "+ owner + " exists in store's " + store_id + " staff list" );

        int staff_actual_size = staff.size();
        assertTrue(staff_actual_size == staff_size, "staff size is " + staff_actual_size + " but supposed to be " + staff_size);
    }

    private void manager_exist(String manager, int store_id, boolean should_appear, int staff_size) throws MarketException {
        boolean manager_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == manager){
                manager_exist = true;
                break;
            }
        }
        if(should_appear)
            assertTrue(manager_exist, "manager "+ manager + " does not exist in store's " + store_id + " staff list");
        else
            assertFalse(manager_exist, "manager "+ manager + "  exists in store's " + store_id + "staff list");

        int staff_actual_size = staff.size();
        assertTrue(staff_actual_size == staff_size, "staff size is " + staff_actual_size + " but supposed to be " + staff_size);
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

    private String make_equal_assert_message(String test, String test_case, int actual, int expected, String val_name){
        String test_part = "Test: " + test + "\n";
        String case_part = "Test case: " + test_case + " failed, ";
        String fail_explained = "expected value of " + val_name + " was expected to be - " + expected + " but was actually - " + actual;

        return test_part + case_part + fail_explained;
    }

    private int open_store_helper(boolean success, int facade_num, String test_name, int store_counter, String test_case, String store_name, int stores_same_name) {
        String message;
        Response res;
        int stores_in_market;
        boolean store_found;

        message = make_assert_exception_message(test_name, test_case, success);

        if(facade_num == 1)
            res = facade1.open_store(store_name);
        else if(facade_num == 2)
            res = facade2.open_store(store_name);
        else if(facade_num == 3)
            res = facade3.open_store(store_name);
        else
            res = facade4.open_store(store_name);

        if(success)
            assertTrue(check_was_exception(res), message);
        else
            assertFalse(check_was_exception(res), message);


        stores_in_market = num_of_stores();
        message = make_equal_assert_message(test_name, test_case, stores_in_market, store_counter,"number of stores");
        assertEquals(store_counter, stores_in_market, message);
        store_found = find_store(store_name, stores_same_name, !success);
        
        if(success)
            assertFalse(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") not found in system");
        else
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") found in system");


        return stores_in_market;
    }

    private int open_closed_store_helper(boolean success, int facade_num, String test_name, int store_counter, String test_case, String store_name, int stores_same_name, int store_id, boolean closed) {
        String message;
        Response res;
        int stores_in_market;
        boolean store_found;

        message = make_assert_exception_message(test_name, test_case, success);

        if(facade_num == 1)
            res = facade1.open_close_store(store_id);
        else if(facade_num == 2)
            res = facade2.open_close_store(store_id);
        else if(facade_num == 3)
            res = facade3.open_close_store(store_id);
        else
            res = facade4.open_close_store(store_id);

        if(success)
            assertTrue(check_was_exception(res), message);
        else
            assertFalse(check_was_exception(res), message);

        stores_in_market = num_of_stores();
        message = make_equal_assert_message(test_name, test_case, stores_in_market, store_counter,"number of stores");
        assertEquals(store_counter, stores_in_market, message);
        store_found = find_store(store_name, stores_same_name, !closed);

        if(!closed)
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") not found in system");
        else
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") found in system");

        return stores_in_market;
    }

    private void close_store_permanently_helper(boolean success, int facade_num, String test_name, int store_counter, String test_case, int store_id, String store_name, int stores_same_name, boolean closed) {
        String message;
        Response res;
        int stores_in_market;
        boolean store_found;

        message = make_assert_exception_message(test_name, test_case, success);

        if (facade_num == 1)
            res = facade1.close_store_permanently(store_id);
        else if (facade_num == 2)
            res = facade2.close_store_permanently(store_id);
        else if (facade_num == 3)
            res = facade3.close_store_permanently(store_id);
        else
            res = facade4.close_store_permanently(store_id);

        if (success)
            assertTrue(check_was_exception(res), message);
        else
            assertFalse(check_was_exception(res), message);

        stores_in_market = num_of_stores();
        message = make_equal_assert_message(test_name, test_case, stores_in_market, store_counter, "number of stores");
        assertEquals(store_counter, stores_in_market, message);
        store_found = find_store(store_name, stores_same_name, !closed);

        if (closed)
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") found active in system");
        else
            assertFalse(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") not found in system");
    }

    private void close_store_temporarily_helper(boolean no_success, int facade_num, String test_name, int store_counter, String test_case, int store_id, String store_name, int stores_same_name, boolean closed) {
        String message;
        Response res;
        int stores_in_market;
        boolean store_found;

        message = make_assert_exception_message(test_name, test_case, no_success);

        if(facade_num == 1)
            res = facade1.close_store_temporarily(store_id);
        else if(facade_num == 2)
            res = facade2.close_store_temporarily(store_id);
        else if(facade_num == 3)
            res = facade3.close_store_temporarily(store_id);
        else
            res = facade4.close_store_temporarily(store_id);

        if(!no_success)
            assertFalse(check_was_exception(res), message);
        else
            assertTrue(check_was_exception(res), message);

        stores_in_market = num_of_stores();
        message = make_equal_assert_message(test_name, test_case, stores_in_market, store_counter,"number of stores");
        assertEquals(store_counter, stores_in_market, message);
        store_found = find_store(store_name, stores_same_name, !closed);

        if(closed)
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") found active in system");
        else
            assertTrue(store_found, "Test: " + test_name + "\n" +
                    "Test case: " + test_case + " failed, " +
                    "store (" + store_name + ") found inactive in system");
    }

    List<String> get_staff_names(Response res){
        List<String> staff = new ArrayList<>();
        if(res.getValue().getClass() == StoreManagersInfo.class){
            List<AppointmentInformation> staff_info = ((StoreManagersInfo)res.getValue()).getAppointmentInformationList();
            for(AppointmentInformation appoint : staff_info){
                staff.add(appoint.getMember_email());
            }
        }
        return staff;
    }

    private int num_of_questions(){
            return QuestionController.getInstance().getQuestion_ids_counter();

        }

    private int num_of_admin_questions(){
            return QuestionController.getInstance().view_users_to_admin_questions().size();
        }

    //------------------------------- Testing functions --------------------------------------------------------------------------


    //------------------------------- Open \ Close store --------------------------------------------------------------------------

    @Test
    void open_and_close_store(){
        Response res;
        String founder = "founder";
        boolean suppose_to_throw = true;
        String test_name = "open_and_close_store";
        int store_counter = num_of_stores();
        String message, test_case;
        String fail_name, f1_store1_name, f1_f2_same_store_name, f2_store1_name;
        int f1_store1_id, f1_store2_id, f2_store1_id, f1_store3_id;
        int founder1 = 1,
                founder2 = 2,
                admin = 3,
                guest = 4;

        // --------------------------- All users register ---------------------------
        message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
        res = facade1.register(founder+"1@founder.com", password, founder, founder, birth_date);
        assertFalse(check_was_exception(res), message);

        res = facade2.register(founder+"2@founder.com", password, founder, founder, birth_date);
        assertFalse(check_was_exception(res), message);

        res = facade3.login(user_admin_email, user_password);
        assertFalse(check_was_exception(res), message);

        fail_name = "This souldn't work - open close store";
        test_case = "guest tries to open store: " + fail_name;
        open_store_helper(suppose_to_throw, guest, test_name, store_counter, test_case, fail_name, 0);

        // --------------------------- Founder1 opens store f1_store1 ---------------------------
        f1_store1_name = "Store 1 - open close store";
        test_case = "assigned user tries to open store: " + f1_store1_name;
        store_counter++;
        f1_store1_id = open_store_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store1_name, 1);

        // --------------------------- Founder1 opens store f1_store2 ---------------------------
        f1_f2_same_store_name = "Store 2 - open close store";
        test_case = "assigned user tries to open store: " + f1_f2_same_store_name;
        store_counter++;
        f1_store2_id = open_store_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_f2_same_store_name, 1);

        test_case = "guest user tries to close store temporarily";
        close_store_temporarily_helper(suppose_to_throw, guest, test_name, store_counter, test_case, f1_store1_id, f1_store1_name, 1, false);

        test_case = "random assigned user tries to close store temporarily";
        close_store_temporarily_helper(suppose_to_throw, founder2, test_name, store_counter, test_case, f1_store1_id, f1_store1_name, 1, false);

        test_case = "admin tries to close store temporarily";
        close_store_temporarily_helper(suppose_to_throw, admin, test_name, store_counter, test_case, f1_store1_id, f1_store1_name, 1, false);

        // --------------------------- Founder1 closes store f1_store1 temporarily ---------------------------
        test_case = "store founder closes store temporarily";
        close_store_temporarily_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store1_id, f1_store1_name, 1, true);

        test_case = "store founder tires to close temporarily a store he already closed temporarily";
        close_store_temporarily_helper(suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store1_id, f1_store1_name, 1, true);

        test_case = "guest user tries to open closed store";
        open_closed_store_helper(suppose_to_throw, guest, test_name, store_counter, test_case, f1_store1_name, 1, f1_store1_id, true);

        test_case = "random assigned user tries to open closed store";
        open_closed_store_helper(suppose_to_throw, founder2, test_name, store_counter, test_case, f1_store1_name, 1, f1_store1_id, true);

        test_case = "admin tries to open closed store";
        open_closed_store_helper(suppose_to_throw, admin, test_name, store_counter, test_case, f1_store1_name, 1, f1_store1_id, true);

        // --------------------------- Founder1 re-opens store f1_store1 ---------------------------
        test_case = "store founder opens closed store";
        open_closed_store_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store1_name, 1, f1_store1_id, false);

        test_case = "store founder tries to re-open the closed store he opened already";
        open_closed_store_helper(suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store1_name, 1, f1_store1_id, false);

        // --------------------------- Founder2 opens store f2_store1 ---------------------------
        test_case = "assigned user tries to open store with same name as another store founded by a different user: " + f1_f2_same_store_name;
        store_counter++;
        f2_store1_id = open_store_helper(!suppose_to_throw, founder2, test_name, store_counter, test_case, f1_f2_same_store_name, 2);

        // --------------------------- Founder1 opens store f1_store3 ---------------------------
        test_case = "assigned user tries to open store with same name as two other stores founded by this user and another: " + f1_f2_same_store_name;
        store_counter++;
        f1_store3_id = open_store_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_f2_same_store_name, 3);

        /*
            Test summary - contained in system: (up to this point)

                Founder1: (facade1)
                    1.  f1_store1 - active
                    2.  f1_store2 - active (f1_f2_same_store_name)
                    3.  f1_store3 - active (f1_f2_same_store_name)

                Founder2: (facade2)
                    1.  f2_store1 - active (f1_f2_same_store_name)

         */

        test_case = "store founder tries to close temporarily a store with same name as his stores";
        close_store_temporarily_helper(suppose_to_throw, founder1, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 3, false);

        test_case = "store founder tries to close temporarily a store with same name as his stores";
        close_store_temporarily_helper(suppose_to_throw, founder2, test_name, store_counter, test_case, f1_store3_id, f1_f2_same_store_name, 3, false);

        test_case = "admin tries to close store temporarily";
        close_store_temporarily_helper(suppose_to_throw, admin, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 3, false);

        // --------------------------- Founder1 closes store f2_store2 temporarily ---------------------------
        test_case = "store founder closes store temporarily";
        close_store_temporarily_helper(!suppose_to_throw, founder1, test_name, store_counter, test_case, f1_store2_id, f1_f2_same_store_name, 1, true);
        assertTrue(find_store(f1_f2_same_store_name, 2,true));
        res = add_prod_make_purchase_closed_store(f1_store2_id); // founder2 tries to make purchase from f2_store2
        assertTrue(check_was_exception(res), "User bought products from temporarily closed store successfuly");

        // --------------------------- Founder2 closes store f2_store1 temporarily ---------------------------
        test_case = "store founder closes store temporarily";
        close_store_temporarily_helper(!suppose_to_throw, founder2, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 2, true);

         /*
            Test summary - contained in system: (up to this point)

                Founder1: (facade1)
                    1.  f1_store1 - active
                    2.  f1_store2 - not active (f1_f2_same_store_name)
                    3.  f1_store3 - active (f1_f2_same_store_name)

                Founder2: (facade2)
                    1.  f2_store1 - not active (f1_f2_same_store_name)

         */

        test_case = "store founder tries to close store permanently";
        close_store_permanently_helper(suppose_to_throw, founder2, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 2, true);

        test_case = "guest user tries to close store permanently";
        close_store_permanently_helper(suppose_to_throw, guest, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 2, true);

        test_case = "different store founder tries to close store permanently";
        close_store_permanently_helper(suppose_to_throw, founder1, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 2, true);

        test_case = "admin closes a store that is not closed temporarily permanently";
        close_store_permanently_helper(!suppose_to_throw, admin, test_name, store_counter, test_case, f1_store3_id, f1_f2_same_store_name, 3, true);
        res = add_prod_make_purchase_closed_store(f1_store3_id); // founder2 tries to make purchase from f1_store3
        assertTrue(check_was_exception(res), "User bought products from permanintly closed store successfuly");

          /*
            Test summary - contained in system: (up to this point)

                Founder1: (facade1)
                    1.  f1_store1 - active
                    2.  f1_store2 - not active (f1_f2_same_store_name)
                    3.  f1_store3 - permanently not active (f1_f2_same_store_name)

                Founder2: (facade2)
                    1.  f2_store1 - not active (f1_f2_same_store_name)

         */

        //    test_case = "admin closes a store permanently";
        //    close_store_permanently_helper(!suppose_to_throw, admin, test_name, store_counter, test_case, f2_store1_id, f1_f2_same_store_name, 2, true);

        test_case = "store founder tries to open a permanently closed store";
        open_closed_store_helper(suppose_to_throw, founder1, test_name, store_counter, test_case, f1_f2_same_store_name, 3, f1_store3_id, true);
        res = add_prod_make_purchase_closed_store(f1_store3_id); // founder2 tries to make purchase from f1_store3
        assertTrue(check_was_exception(res), "User bought products from permanintly closed store successfuly");

        test_case = "admin tries to open a permanently closed store";
        open_closed_store_helper(suppose_to_throw, admin, test_name, store_counter, test_case, f1_f2_same_store_name, 3, f2_store1_id, true);
        res = add_prod_make_purchase_closed_store(f1_store3_id); // founder2 tries to make purchase from f1_store3
        assertTrue(check_was_exception(res), "User bought products from permanintly closed store successfuly");

        facade1.logout();
        facade2.logout();
        facade3.logout();
    }

    /**
     * Cases checked:
     * 1. no user is connected
     * 2. store founder opens store number 2
     * 3. store founder opens store with same name
     * 4. store founder opens first store
     */
    @Test
    void open_store(){
        Response res;
        int stores_count = num_of_stores();
        String name;
        String test_name = "open_store";
        boolean suppose_to_throw = true;
        String message;

        name = "this shouldn't work";
        message = make_assert_exception_message(test_name, "no user is connected", suppose_to_throw);
        res = facade1.open_store(name); // no user is connected
        assertTrue(check_was_exception(res), message);
        assertEquals(stores_count, num_of_stores()); // todo
        assertFalse(find_store(name, 1)); // todo

        name = "store number 2";
        message = make_assert_exception_message(test_name, "store founder opens store number 2", !suppose_to_throw);
        facade1.login(user_founder_email, user_password);
        res = facade1.open_store(name); // store founder opens store number 2
        assertFalse(check_was_exception(res), message);
        assertTrue(find_store(name, 1)); // todo
        stores_count++;
        assertEquals(stores_count, num_of_stores()); // todo
        assertEquals(user_founder_email, get_store_founder("store number 2", 1)); // todo

        message = make_assert_exception_message(test_name, "store founder opens store with same name", !suppose_to_throw);
        res = facade1.open_store(name); // store founder opens store with same name
        assertFalse(check_was_exception(res), message);
        stores_count++;
        assertEquals(stores_count, num_of_stores()); // todo
        assertTrue(find_store(name, 2)); // todo
        assertEquals(user_founder_email, get_store_founder("store number 2", 2)); // todo

        facade1.logout();
        facade1.login(user_regular_email_1, user_password);

        name = "Store number 1";
        message = make_assert_exception_message(test_name, "store founder opens first store", !suppose_to_throw);
        res = facade1.open_store(name); // store founder opens first store
        assertFalse(check_was_exception(res), message);
        stores_count++;
        assertEquals(stores_count, num_of_stores()); // todo
        assertTrue(find_store(name, 1)); // todo
        assertEquals(user_regular_email_1, get_store_founder("Store number 1", 1));  // todo

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. get all stores
     */
    @Test
    void get_all_stores(){
        Response res;
        String test_name = "get_all_stores";
        boolean suppose_to_throw = true;
        String message;

        message = make_assert_exception_message(test_name, "get all stores", !suppose_to_throw);
        res = facade1.get_all_stores();
        assertFalse(check_was_exception(res), message);
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()){
            assertEquals(num_of_stores() ,((ArrayList<StoreInformation>)res.getValue()).size()); // todo
        }


    }

    //------------------------------- Staff operations \ appointments --------------------------------------------------------------------------

    @Test
    void add_remove_manager() throws MarketException {
        Response res;
        String founder = "user12@founder.com";
        String owner = "user22@owner.com";
        String manager1 = "user32@manager.com";
        String manager2 = "user42@manager.com";
        int staff_size = 0;
        boolean should_appear = true;
        boolean suppose_to_throw = true;
        String test_name = "add_remove_manager";
        String message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";


        // all test characters register the system
        res = facade1.register(founder, "paSs12", "one", "founder", birth_date);
        assertFalse(check_was_exception(res), message);
        res = facade2.register(owner, "paSs12", "two", "owner", birth_date);
        assertFalse(check_was_exception(res), message);
        res = facade3.register(manager1, "paSs12", "three", "manager", birth_date);
        assertFalse(check_was_exception(res), message);
        res = facade4.register(manager2, "paSs12", "four", "manager", birth_date);
        assertFalse(check_was_exception(res), message);


        // founder opens store
        int store_id = open_store_get_id("new test store");
        founder_exist(founder, store_id);
        staff_size++;

        // founder adds owner as store owner
        message = make_assert_exception_message(test_name, "founder adds owner as store owner", !suppose_to_throw);
        res = facade1.add_owner(owner, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);


        // owner adds manager1 as store manager
        message = make_assert_exception_message(test_name, "owner adds manager1 as store manager", !suppose_to_throw);
        res = facade2.add_manager(manager1, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, should_appear, staff_size);


        // manager1 tries to add manager2 as store manager
        message = make_assert_exception_message(test_name, "manager1 tries to add manager2 as store manager", suppose_to_throw);
        res = facade3.add_manager(manager2, store_id);
        assertTrue(check_was_exception(res), message);
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, should_appear, staff_size);
        manager_exist(manager2, store_id, !should_appear, staff_size);


        // owner adds manager2 as store manager
        message = make_assert_exception_message(test_name, "owner adds manager2 as store manager", !suppose_to_throw);
        res = facade2.add_manager(manager2, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, should_appear, staff_size);
        manager_exist(manager2, store_id, should_appear, staff_size);


        // manager1 tries to remove owner's appointment as store owner
        message = make_assert_exception_message(test_name, "manager1 tries to remove owner's appointment as store owner", suppose_to_throw);
        res = facade3.delete_owner(owner, store_id);
        assertTrue(check_was_exception(res), message);
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, should_appear, staff_size);
        manager_exist(manager2, store_id, should_appear, staff_size);


        // owner removes manager1's appointment as store manager
        message = make_assert_exception_message(test_name, "owner removes manager1's appointment as store manager", !suppose_to_throw);
        res = facade2.delete_manager(manager1, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size--;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, !should_appear, staff_size);
        manager_exist(manager2, store_id, should_appear, staff_size);


        // manager2 tries to remove owner's appointment as store owner
        message = make_assert_exception_message(test_name, "manager2 tries to remove owner's appointment as store owner", suppose_to_throw);
        res = facade4.delete_owner(owner, store_id);
        assertTrue(check_was_exception(res), message);
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, should_appear, staff_size);
        manager_exist(manager1, store_id, !should_appear, staff_size);
        manager_exist(manager2, store_id, should_appear, staff_size);


        // founder removes owner's permissions as store owner
        message = make_assert_exception_message(test_name, "founder removes owner's permissions as store owner", !suppose_to_throw);
        res = facade1.delete_owner(owner, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size -= 2;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id, !should_appear, staff_size);
        manager_exist(manager1, store_id, !should_appear, staff_size);
        manager_exist(manager2, store_id, !should_appear, staff_size);


        // all users logout
        facade1.logout();
        facade2.logout();
        facade3.logout();
        facade4.logout();
    }

    // Bar's test from version 2 meeting
    @Test
    void recursive_appointment_removal() throws MarketException {
        Response res;
        String founder = "user1@founder.com";
        String owner1 = "user2@owner.com";
        String owner2 = "user3@owner.com";
        boolean should_appear = true;
        int staff_size = 0;
        boolean suppose_to_throw = true;
        String test_name = "recursive_appointment_removal";
        String message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";


        // all test characters register the system
        res = facade1.register(founder, user_password, "one", "founder", birth_date);
        assertFalse(check_was_exception(res), message);
        res = facade2.register(owner1, user_password, "two", "owner", birth_date);
        assertFalse(check_was_exception(res), message);
        res = facade3.register(owner2, user_password, "three", "owner", birth_date);
        assertFalse(check_was_exception(res), message);


        // founder opens store
        int store_id = open_store_get_id("new test store");
        founder_exist(founder, store_id);
        staff_size++;


        // founder adds owner1 as store owner
        message = make_assert_exception_message(test_name, "founder adds owner1 as store owner", !suppose_to_throw);
        res = facade1.add_owner(owner1, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id, should_appear, staff_size);


        // owner1 adds owner2 as store owner
        message = make_assert_exception_message(test_name, "owner1 adds owner2 as store owner", !suppose_to_throw);
        res = facade2.add_owner(owner2, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id, should_appear, staff_size);
        owner_exist(owner2, store_id, should_appear, staff_size);


        // owner2 tries to remove owner1's permissions as store owner
        message = make_assert_exception_message(test_name, "owner2 tries to remove owner1's permissions as store owner", suppose_to_throw);
        res = facade3.delete_owner(owner1, store_id);
        assertTrue(check_was_exception(res), message);
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id, should_appear, staff_size);
        owner_exist(owner2, store_id, should_appear, staff_size);


        // founder removes owner1's permissions as store owner
        message = make_assert_exception_message(test_name, "founder removes owner1's permissions as store owner", !suppose_to_throw);
        res = facade1.delete_owner(owner1, store_id);
        assertFalse(check_was_exception(res), message);
        staff_size -= 2;
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id, !should_appear, staff_size);
        owner_exist(owner2, store_id, !should_appear, staff_size);


        // all users logout
        facade1.logout();
        facade2.logout();
        facade3.logout();
    }

    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not the store owner
     * 3. user enters a store id that does not exist
     * 4. store founder views store's purchase history
     * 5. store founder enters a store id that does not exist
     * 6. store founder enters a store id that didn't doesn't have permissions to see
     * 7. store founder views new store's empty purchase history
     * 8. store founder views store's purchase history
     */
    @Test
    void view_store_purchases_history() {
        Response res;
        boolean suppose_to_throw = true;
        String test_name = "view_store_purchases_history";
        String message = make_assert_exception_message(test_name, "guest tries to view store's purchase history list", suppose_to_throw);

        res = facade2.view_store_purchases_history(1); // no one is connected
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "user (with no permissions) tries to view store's purchase history list", suppose_to_throw);
        facade2.login(user_buyer_email, user_password);
        res = facade2.view_store_purchases_history(1); // user connected is not the store owner
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "user tries to view store's purchase history list with store id that does not exist", suppose_to_throw);
        res = facade2.view_store_purchases_history(num_of_stores() + 2); // user enters a store id that does not exist
        assertTrue(check_was_exception(res), message);


        facade1.login(user_founder_email, user_password);
        res = facade1.view_store_purchases_history(1); // store founder views store's purchase history
        valid_purchase_history(res, user_buyer_email, 1, test_name, "store founder views store's purchase history");

        message = make_assert_exception_message(test_name, "store founder enters a store id that does not exist", suppose_to_throw);
        res = facade1.view_store_purchases_history(num_of_stores() + 2); // store founder enters a store id that does not exist
        assertTrue(check_was_exception(res), message);

        int store_id = open_store_get_id("first store for this user"); // store founder opens first store

        message = make_assert_exception_message(test_name, "store founder enters a store id that didn't doesn't have permissions to see", suppose_to_throw);
        res = facade1.view_store_purchases_history(num_of_stores() + 2); // store founder enters a store id that didn't doesn't have permissions to see
        assertTrue(check_was_exception(res), message);

        message = make_assert_exception_message(test_name, "store founder views new store's empty purchase history", !suppose_to_throw);
        res = facade1.view_store_purchases_history(store_id); // store founder views new store's empty purchase history
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == (new ConcurrentHashMap<Integer, StorePurchase>()).values().getClass()){
            Collection<StorePurchase> his = (Collection<StorePurchase>)res.getValue();
            assertTrue(his.isEmpty());
        }

        int prod_id = add_prod_make_purchase_get_id(store_id);

        res = facade1.view_store_purchases_history(store_id); // store founder views store's purchase history
        valid_purchase_history(res, user_buyer_email, prod_id, test_name, "store founder views store's purchase history");

        // all users logout
        facade1.logout();
        facade2.logout();


    }

    @Test
    void view_store_management_information(){
        Response res;
        boolean suppose_to_throw = true;
        String test_name = "view_store_management_information";
        int store_counter = num_of_stores();
        String message, test_case;
        List<String> staff = new ArrayList<>();
        String owner_email = "owner@owner.com";
        String founder_email = "founder@founder.com";
        String manager_email = "manager@manager.com";
        int store_id;
        int founder = 1;

        // --------------------------- All users register ---------------------------
        message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
        res = facade1.register(founder_email, password, "founder", "founder", birth_date);
        assertFalse(check_was_exception(res), message);

        res = facade2.register(owner_email, password, "owner", "owner", birth_date);
        assertFalse(check_was_exception(res), message);

        res = facade3.register(manager_email, user_password, "manager", "manager", birth_date);
        assertFalse(check_was_exception(res), message);


        // --------------------------- Founder opens store ---------------------------
        test_case = "Founder opens store";
        store_counter++;
        store_id = open_store_helper(!suppose_to_throw, founder, test_name, store_counter, test_case, "Store: " + test_name , 1);
        staff.add(founder_email);

        // --------------------------- Founder appoints owner ---------------------------
        message ="Founder (" + founder_email + ") add user (" + owner_email + ") as store (" + store_id + ") owner";
        res = facade1.add_owner(owner_email,store_id);
        assertFalse(check_was_exception(res), message);
        staff.add(owner_email);
        message = "Test: " + test_name + " failed\nIn test case: " + message + "\nEmployee: ";
        res = facade1.view_store_management_information(store_id);
        List<String> staff_info = get_staff_names(res);
        for(String worker : staff){
            assertTrue(staff_info.contains(worker), message + worker + " not found in system");
        }

        // --------------------------- Guest user tries to appoint manager ---------------------------
        message ="Guest (" + founder_email + ") tries to add user (" + manager_email + ") as store (" + store_id + ") manager";
        res = facade4.add_manager(manager_email, store_id);
        assertTrue(check_was_exception(res), message);
        message = "Test: " + test_name + " failed\nIn test case: " + message + "\nEmployee: " + manager_email + "found in system";
        res = facade1.view_store_management_information(store_id);
        staff_info = get_staff_names(res);
        assertFalse(staff_info.contains(manager_email), message);


        // --------------------------- Owner appoints store manager ---------------------------
        message ="Owner (" + owner_email + ") add user (" + manager_email + ") as store (" + store_id + ") manager";
        res = facade2.add_manager(manager_email,store_id);
        assertFalse(check_was_exception(res), message);
        staff.add(manager_email);
        message = "Test: " + test_name + " failed\nIn test case: " + message + "\nEmployee: ";
        res = facade1.view_store_management_information(store_id);
        staff_info = get_staff_names(res);
        for(String worker : staff){
            assertTrue(staff_info.contains(worker), message + worker + " not found in system");
        }


        // --------------------------- Founder deletes store owner ---------------------------
        message ="Founder (" + founder_email + ") removes owner (" + owner_email + ") as store (" + store_id + ") owner";
        res = facade1.delete_owner(owner_email,store_id);
        assertFalse(check_was_exception(res), message);
        staff.remove(owner_email);
        staff.remove(manager_email);
        message = "Test: " + test_name + " failed\nIn test case: " + message + "\nEmployee: ";
        res = facade1.view_store_management_information(store_id);
        staff_info = get_staff_names(res);
        for(String worker : staff){
            assertFalse(staff_info.contains(manager_email), message);
            assertFalse(staff_info.contains(owner_email), message);
        }


        // --------------------------- All users logout ---------------------------
        facade1.logout();
        facade2.logout();
        facade3.logout();

    }






    //----------------------- Concurrent -----------------------

    /**
     * trying to register num_of_threads with same email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads
     * 3. running all the threads in parallel
     * 4. make sure that the email is registered & there was num_of_threads-1 exceptions
     */
    @Test
    void parallel_registration_same_user() {
        //arrange
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);

        //1
        assertFalse(uc.contains_user_email(email)); //make sure that the user is not already registered
        //2
        for (int i = 0; i < num_of_threads; i++) { //initializing all the threads
            threads.add(new Thread(() -> {
                MarketFacade mf = new MarketFacade(pa, sa);
                Response res = mf.register(email, password, "gal", "brown", birth_date);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
            }));
        }
        //3
        start_threads(threads);
        join_threads(threads);
        //4+5
        assertTrue(uc.contains_user_email(email), "failed to register user");
        assertTrue(num_of_exceptions.get() == num_of_threads - 1, "parallel bug");
    }

    /**
     * trying to register num_of_threads with different email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads - that will register the system.
     * 3. running all the threads in parallel
     * 4. make sure that all the emails is registered & there was 0 exceptions
     */
    @Test
    void parallel_registration_different_users() {
        // arrange
        String ending = "@gmail.com";
        String starting = "somthing";
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);

        //1 + 2
        for (int i = 0, num = 3; i < num_of_threads; i++, num++) { //initializing all the threads
            String email = starting + num + ending;
            threads.add(new Thread(() -> {
                MarketFacade mf = new MarketFacade(pa, sa);
                assertFalse(uc.contains_user_email(email)); //make sure that the user is not already registered
                Response res = mf.register(email, password, "gal", "brown", birth_date);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
            }));
        }

        //3
        start_threads(threads);
        join_threads(threads);

        //4
        for (int i = 0, num = 3; i < num_of_threads; i++, num++) {
            assertTrue(uc.contains_user_email(starting + num + ending), "failed to register user");
        }
        assertTrue(num_of_exceptions.get() == 0, "parallel bug");
    }

    /**
     * trying to login num_of_threads with same email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads
     * 3. running all the threads in parallel
     * 4. make sure that the email is registered & there was num_of_threads-1 exceptions
     */
    @Test
    void parallel_logging_same_user() {
        //arrange
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        AtomicInteger num_of_logged_after_operation = new AtomicInteger(0);
        MarketFacade mf = new MarketFacade(pa, sa);
        String reg_email = "loginsame@gmail.com";
        mf.register(reg_email, password, "gal", "brown", birth_date);
        assertTrue(uc.contains_user_email(reg_email), "failed to register user");
        mf.logout();
        assertFalse(mf.is_logged(), "user is logged in before operation");

        //initializing all the threads
        for (int i = 0; i < num_of_threads; i++) {
            threads.add(new Thread(() -> {
                MarketFacade mf1 = new MarketFacade(pa, sa);
                if (mf1.is_logged()) assertTrue(false, "account already logged in before operation");
                Response res = mf1.login(reg_email, password);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
                if (mf1.is_logged()) num_of_logged_after_operation.incrementAndGet();
            }));
        }
        //3
        start_threads(threads);
        join_threads(threads);
        //4+5
        assertTrue(num_of_exceptions.get() == num_of_threads - 1, "parallel bug");
        assertTrue(num_of_logged_after_operation.get() == 1, num_of_logged_after_operation.get() + " logging operation succeed");
    }




}
package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment.Appointment;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
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
import java.util.concurrent.atomic.AtomicInteger;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Appointments {
    private MarketFacade marketFacade;
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
    private int store_id;
    private int product_id;
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


    public Appointments() {
        try{
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();

            this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
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
    @BeforeEach
    void setUp() throws MarketException {

        // make sure no user is logged in
        facade1.logout();
        facade2.logout();
        facade3.logout();
        facade4.logout();


    }
    private int open_store_get_id(String name){
        Response<Integer> response = facade1.open_store(name);
        return response.getValue();
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
    // helper function which starts all threads
    private void start_threads(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        } // running all the threads parallel
    }
    // helper function which join all threads
    private void join_threads(List<Thread> threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (Exception e) {
            Assertions.fail( "there was error while running the threads");
        }
    }
    // helper function which creates num_of_threads users represented by market facades
    private List<MarketFacade> createUsers(String starting) {
        String ending = "@gmail.com";
        List<MarketFacade> facades = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) {
            String email = starting + i + ending;
            MarketFacade mf = new MarketFacade(paymentAdapter, supplyAdapter);
            Response res = mf.register(email, password, "gal", "brown", birth_date);
            check_was_not_exception("failed to register user for testing", res);
            facades.add(mf);
        }
        return facades;
    }
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }
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
    //------------------------------------------------ Manager \ Owner --------------------------------------------------------------------------
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
        facade1.manager_answer_appointment(store_id, true, manager1);
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
        facade1.manager_answer_appointment(store_id, true, manager2);
        facade3.manager_answer_appointment(store_id, true, manager2);
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
        facade1.manager_answer_appointment(store_id, true, owner2);
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

    @Test
    void adding_the_same_user_to_management() {

        MarketFacade owner = new MarketFacade(paymentAdapter, supplyAdapter);
        owner.register("amit1@gmail.com", password, "amit", "mosko", birth_date);
        MarketFacade manger = new MarketFacade(paymentAdapter, supplyAdapter);
        manger.register("amit2@gmail.com", password, "gal", "grumet", birth_date);
        facade1.add_owner("amit1@gmail.com", store_id);

        Response good = facade1.add_manager("amit2@gmail.com", store_id);
        Response bad = owner.add_manager("amit2@gmail.com", store_id);
        check_was_not_exception("Manager added successfully", good);
        check_was_exception(bad);
    }

    //----------------------- Concurrent -----------------------

    @Test
    void concurrent_add_new_owner(){
        List<MarketFacade> marketFacadeList = createUsers("newowner");
        for (int i = 0; i < num_of_threads; i++) { // make all users to owners
            Response res = facade1.add_owner("newowner"+i+"@gmail.com",store_id);
            check_was_not_exception("problem with adding new owner.", res);
            //TODO: check add owner succeed
        }
        MarketFacade new_user = new MarketFacade(paymentAdapter,supplyAdapter);
        new_user.register("check@gmail.com", password,"gal","brown",LocalDate.now().minusYears(30).toString());

        AtomicInteger num_of_success = new AtomicInteger(0);
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) { //make all threads
            int num_of_market_facade = i;
            threads.add(new Thread(() -> {
                Response res = marketFacadeList.get(num_of_market_facade).add_owner("check@gmail.com", store_id);
                if (res.WasException() && res.getValue().getClass().equals(AppointmentException.class))
                    num_of_exceptions.incrementAndGet();
                else if(!res.WasException())
                    num_of_success.getAndIncrement();
            }));
        }
        start_threads(threads);
        join_threads(threads);
        assertTrue(num_of_exceptions.get() == num_of_threads-1,"concurency fail, fail count: "+num_of_exceptions.get());
        assertTrue(num_of_success.get() == 1,"concurency fail, success count: "+num_of_success.get());
        //TODO: check user is owner.
    }

    @Test
    void concurrent_add_new_manager(){
        List<MarketFacade> marketFacadeList = createUsers("newowner");
        for (int i = 0; i < num_of_threads; i++) { // make all users to owners
            Response res = facade1.add_owner("newowner"+i+"@gmail.com",store_id);
            check_was_not_exception("problem with adding new owner.", res);
            //TODO: check add owner succeed
        }
        MarketFacade new_user = new MarketFacade(paymentAdapter,supplyAdapter);
        new_user.register("check@gmail.com", password,"gal","brown",LocalDate.now().minusYears(30).toString());

        AtomicInteger num_of_success = new AtomicInteger(0);
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) { //make all threads
            int num_of_market_facade = i;
            threads.add(new Thread(() -> {
                Response res = marketFacadeList.get(num_of_market_facade).add_manager("check@gmail.com", store_id);
                if (res.WasException())
                    num_of_exceptions.incrementAndGet();
                else if(!res.WasException())
                    num_of_success.getAndIncrement();
            }));
        }
        start_threads(threads);
        join_threads(threads);
        assertTrue(num_of_exceptions.get() == num_of_threads-1,"concurency fail, fail count: "+num_of_exceptions.get());
        assertTrue(num_of_success.get() == 1,"concurency fail, success count: "+num_of_success.get());
        //TODO: check user is manager.
    }

    /**
     * with no other managers in the store -> confirm automatically
     * with more managers -> after everyone confirm the candidate become owner
     * with more managers -> after one reject -> the candidate appointment closed.
     */


}
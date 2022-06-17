//package Acceptance.User.AssignUser;
//
//import TradingSystem.server.Domain.ExternalSystems.*;
//import TradingSystem.server.Domain.Facade.MarketFacade;
//import TradingSystem.server.Domain.StoreModule.Product.Product;
//import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
//import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
//import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
//import TradingSystem.server.Domain.Utils.Exception.MarketException;
//import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
//import TradingSystem.server.Domain.Utils.Response;
//import TradingSystem.server.Service.MarketSystem;
//import TradingSystem.server.Service.NotificationHandler;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//class Appointments {
//    private final int num_of_threads = 100;
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
//
//
//    public Appointments() {
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
//    // helper function which starts all threads
//    private void start_threads(List<Thread> threads) {
//        for (Thread t : threads) {
//            t.start();
//        } // running all the threads parallel
//    }
//    // helper function which join all threads
//    private void join_threads(List<Thread> threads) {
//        try {
//            for (Thread t : threads) {
//                t.join();
//            }
//        } catch (Exception e) {
//            Assertions.fail( "there was error while running the threads");
//        }
//    }
//    // helper function which creates num_of_threads users represented by market facades
//    private List<MarketFacade> createUsers(String starting) {
//        String ending = "@gmail.com";
//        List<MarketFacade> facades = new ArrayList<>();
//        for (int i = 0; i < num_of_threads; i++) {
//            String email = starting + i + ending;
//            MarketFacade mf = new MarketFacade(paymentAdapter, supplyAdapter);
//            Response res = mf.register(email, password, "gal", "brown", birth_date);
//            check_was_not_exception("failed to register user for testing", res);
//            facades.add(mf);
//        }
//        return facades;
//    }
//    private boolean check_was_exception(Response response) {
//        return response.WasException();
//    }
//    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }
//
//
//    //------------------------------------------------ Manager \ Owner --------------------------------------------------------------------------
//    @Test
//    void add_remove_manager() throws MarketException {
//        Response res;
//        String founder = "user12@founder.com";
//        String owner = "user22@owner.com";
//        String manager1 = "user32@manager.com";
//        String manager2 = "user42@manager.com";
//        int staff_size = 0;
//        boolean should_appear = true;
//        boolean suppose_to_throw = true;
//        String test_name = "add_remove_manager";
//        String message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
//
//
//        // all test characters register the system
//        res = facade1.register(founder, "paSs12", "one", "founder", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade2.register(owner, "paSs12", "two", "owner", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade3.register(manager1, "paSs12", "three", "manager", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade4.register(manager2, "paSs12", "four", "manager", birth_date);
//        assertFalse(check_was_exception(res), message);
//
//
//        // founder opens store
//        int store_id = open_store_get_id("new test store");
//        founder_exist(founder, store_id);
//        staff_size++;
//
//        // founder adds owner as store owner
//        message = make_assert_exception_message(test_name, "founder adds owner as store owner", !suppose_to_throw);
//        res = facade1.add_owner(owner, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size++;
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//
//
//        // owner adds manager1 as store manager
//        message = make_assert_exception_message(test_name, "owner adds manager1 as store manager", !suppose_to_throw);
//        res = facade2.add_manager(manager1, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size++;
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, should_appear, staff_size);
//
//
//        // manager1 tries to add manager2 as store manager
//        message = make_assert_exception_message(test_name, "manager1 tries to add manager2 as store manager", suppose_to_throw);
//        res = facade3.add_manager(manager2, store_id);
//        assertTrue(check_was_exception(res), message);
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, should_appear, staff_size);
//        manager_exist(manager2, store_id, !should_appear, staff_size);
//
//
//        // owner adds manager2 as store manager
//        message = make_assert_exception_message(test_name, "owner adds manager2 as store manager", !suppose_to_throw);
//        res = facade2.add_manager(manager2, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size++;
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, should_appear, staff_size);
//        manager_exist(manager2, store_id, should_appear, staff_size);
//
//
//        // manager1 tries to remove owner's appointment as store owner
//        message = make_assert_exception_message(test_name, "manager1 tries to remove owner's appointment as store owner", suppose_to_throw);
//        res = facade3.delete_owner(owner, store_id);
//        assertTrue(check_was_exception(res), message);
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, should_appear, staff_size);
//        manager_exist(manager2, store_id, should_appear, staff_size);
//
//
//        // owner removes manager1's appointment as store manager
//        message = make_assert_exception_message(test_name, "owner removes manager1's appointment as store manager", !suppose_to_throw);
//        res = facade2.delete_manager(manager1, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size--;
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, !should_appear, staff_size);
//        manager_exist(manager2, store_id, should_appear, staff_size);
//
//
//        // manager2 tries to remove owner's appointment as store owner
//        message = make_assert_exception_message(test_name, "manager2 tries to remove owner's appointment as store owner", suppose_to_throw);
//        res = facade4.delete_owner(owner, store_id);
//        assertTrue(check_was_exception(res), message);
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, should_appear, staff_size);
//        manager_exist(manager1, store_id, !should_appear, staff_size);
//        manager_exist(manager2, store_id, should_appear, staff_size);
//
//
//        // founder removes owner's permissions as store owner
//        message = make_assert_exception_message(test_name, "founder removes owner's permissions as store owner", !suppose_to_throw);
//        res = facade1.delete_owner(owner, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size -= 2;
//        founder_exist(founder, store_id);
//        owner_exist(owner, store_id, !should_appear, staff_size);
//        manager_exist(manager1, store_id, !should_appear, staff_size);
//        manager_exist(manager2, store_id, !should_appear, staff_size);
//
//
//        // all users logout
//        facade1.logout();
//        facade2.logout();
//        facade3.logout();
//        facade4.logout();
//    }
//
//    // Bar's test from version 2 meeting
//    @Test
//    void recursive_appointment_removal() throws MarketException {
//        Response res;
//        String founder = "user1@founder.com";
//        String owner1 = "user2@owner.com";
//        String owner2 = "user3@owner.com";
//        boolean should_appear = true;
//        int staff_size = 0;
//        boolean suppose_to_throw = true;
//        String test_name = "recursive_appointment_removal";
//        String message = "Test: " + test_name + "\nexception thrown while: all test characters register the system";
//
//
//        // all test characters register the system
//        res = facade1.register(founder, user_password, "one", "founder", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade2.register(owner1, user_password, "two", "owner", birth_date);
//        assertFalse(check_was_exception(res), message);
//        res = facade3.register(owner2, user_password, "three", "owner", birth_date);
//        assertFalse(check_was_exception(res), message);
//
//
//        // founder opens store
//        int store_id = open_store_get_id("new test store");
//        founder_exist(founder, store_id);
//        staff_size++;
//
//
//        // founder adds owner1 as store owner
//        message = make_assert_exception_message(test_name, "founder adds owner1 as store owner", !suppose_to_throw);
//        res = facade1.add_owner(owner1, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size++;
//        founder_exist(founder, store_id);
//        owner_exist(owner1, store_id, should_appear, staff_size);
//
//
//        // owner1 adds owner2 as store owner
//        message = make_assert_exception_message(test_name, "owner1 adds owner2 as store owner", !suppose_to_throw);
//        res = facade2.add_owner(owner2, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size++;
//        founder_exist(founder, store_id);
//        owner_exist(owner1, store_id, should_appear, staff_size);
//        owner_exist(owner2, store_id, should_appear, staff_size);
//
//
//        // owner2 tries to remove owner1's permissions as store owner
//        message = make_assert_exception_message(test_name, "owner2 tries to remove owner1's permissions as store owner", suppose_to_throw);
//        res = facade3.delete_owner(owner1, store_id);
//        assertTrue(check_was_exception(res), message);
//        founder_exist(founder, store_id);
//        owner_exist(owner1, store_id, should_appear, staff_size);
//        owner_exist(owner2, store_id, should_appear, staff_size);
//
//
//        // founder removes owner1's permissions as store owner
//        message = make_assert_exception_message(test_name, "founder removes owner1's permissions as store owner", !suppose_to_throw);
//        res = facade1.delete_owner(owner1, store_id);
//        assertFalse(check_was_exception(res), message);
//        staff_size -= 2;
//        founder_exist(founder, store_id);
//        owner_exist(owner1, store_id, !should_appear, staff_size);
//        owner_exist(owner2, store_id, !should_appear, staff_size);
//
//
//        // all users logout
//        facade1.logout();
//        facade2.logout();
//        facade3.logout();
//    }
//    @Test
//    void adding_the_same_user_to_management() {
//        SupplyAdapter supply = new SupplyAdapterImpl();
//        PaymentAdapter payment = new PaymentAdapterImpl();
//        MarketFacade owner = new MarketFacade(payment, supply);
//        owner.register("amit1@gmail.com", password, "amit", "mosko", birth_date);
//        MarketFacade manger = new MarketFacade(payment, supply);
//        manger.register("amit2@gmail.com", password, "gal", "grumet", birth_date);
//        marketFacade.add_owner("amit1@gmail.com", 1);
//        Response good = marketFacade.add_manager("amit2@gmail.com", 1);
//        Response bad = owner.add_manager("amit2@gmail.com", 1);
//        check_was_not_exception("Manager added successfully", good);
//        check_was_exception(bad);
//    }
//
//    //----------------------- Concurrent -----------------------
//
//    @Test
//    void concurrent_add_new_owner(){
//        List<MarketFacade> marketFacadeList = createUsers("newowner");
//        for (int i = 0; i < num_of_threads; i++) { // make all users to owners
//            Response res = marketFacade.add_owner("newowner"+i+"@gmail.com",1);
//            check_was_not_exception("problem with adding new owner.", res);
//            //TODO: check add owner succeed
//        }
//        MarketFacade new_user = new MarketFacade(paymentAdapter,supplyAdapter);
//        new_user.register("check@gmail.com", password,"gal","brown",LocalDate.now().minusYears(30).toString());
//
//        AtomicInteger num_of_success = new AtomicInteger(0);
//        AtomicInteger num_of_exceptions = new AtomicInteger(0);
//        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < num_of_threads; i++) { //make all threads
//            int num_of_market_facade = i;
//            threads.add(new Thread(() -> {
//                Response res = marketFacadeList.get(num_of_market_facade).add_owner("check@gmail.com", 1);
//                if (res.WasException() && res.getValue().getClass().equals(AppointmentException.class))
//                    num_of_exceptions.incrementAndGet();
//                else if(!res.WasException())
//                    num_of_success.getAndIncrement();
//            }));
//        }
//        start_threads(threads);
//        join_threads(threads);
//        assertTrue(num_of_exceptions.get() == num_of_threads-1,"concurency fail, fail count: "+num_of_exceptions.get());
//        assertTrue(num_of_success.get() == 1,"concurency fail, success count: "+num_of_success.get());
//        //TODO: check user is owner.
//    }
//
//    @Test
//    void concurrent_add_new_manager(){
//        List<MarketFacade> marketFacadeList = createUsers("newowner");
//        for (int i = 0; i < num_of_threads; i++) { // make all users to owners
//            Response res = marketFacade.add_owner("newowner"+i+"@gmail.com",1);
//            check_was_not_exception("problem with adding new owner.", res);
//            //TODO: check add owner succeed
//        }
//        MarketFacade new_user = new MarketFacade(paymentAdapter,supplyAdapter);
//        new_user.register("check@gmail.com", password,"gal","brown",LocalDate.now().minusYears(30).toString());
//
//        AtomicInteger num_of_success = new AtomicInteger(0);
//        AtomicInteger num_of_exceptions = new AtomicInteger(0);
//        List<Thread> threads = new ArrayList<>();
//        for (int i = 0; i < num_of_threads; i++) { //make all threads
//            int num_of_market_facade = i;
//            threads.add(new Thread(() -> {
//                Response res = marketFacadeList.get(num_of_market_facade).add_manager("check@gmail.com", 1);
//                if (res.WasException())
//                    num_of_exceptions.incrementAndGet();
//                else if(!res.WasException())
//                    num_of_success.getAndIncrement();
//            }));
//        }
//        start_threads(threads);
//        join_threads(threads);
//        assertTrue(num_of_exceptions.get() == num_of_threads-1,"concurency fail, fail count: "+num_of_exceptions.get());
//        assertTrue(num_of_success.get() == 1,"concurency fail, success count: "+num_of_success.get());
//        //TODO: check user is manager.
//    }
//
//    /**
//     * with no other managers in the store -> confirm automatically
//     * with more managers -> after everyone confirm the candidate become owner
//     * with more managers -> after one reject -> the candidate appointment closed.
//     */
//
//
//}
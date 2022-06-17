package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
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
import static org.junit.jupiter.api.Assertions.assertTrue;

class Appointments {
    private final int num_of_threads = 100;
    private final int num_of_products = 50;
    private final int price = 100;
    private int productId;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private MarketFacade marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    private MarketFacade manager = new MarketFacade(paymentAdapter, supplyAdapter);
    private MarketFacade general_user = new MarketFacade(paymentAdapter, supplyAdapter);
    private String birth_date;


    public Appointments() {
        try{
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    void SetUp() {
        NotificationHandler.setTestsHandler();
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade.clear();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        manager = new MarketFacade(paymentAdapter, supplyAdapter);
        general_user = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade.register(email, password, name, last_name, birth_date);
        manager.register(manager_email, password, name, last_name, birth_date);
        general_user.register("general@gmail.com", password, name, last_name, birth_date);
        marketFacade.open_store("amit store");
        add_product();
        marketFacade.add_manager(manager_email, 1);
    }

    private int add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response<Map<Product, Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
        return r.getValue().keySet().stream().findAny().get().getProduct_id();
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


    //------------------------------------------------ Manager \ Owner --------------------------------------------------------------------------

    @Test
    void adding_the_same_user_to_management() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        MarketFacade owner = new MarketFacade(payment, supply);
        owner.register("amit1@gmail.com", password, "amit", "mosko", birth_date);
        MarketFacade manger = new MarketFacade(payment, supply);
        manger.register("amit2@gmail.com", password, "gal", "grumet", birth_date);
        marketFacade.add_owner("amit1@gmail.com", 1);
        Response good = marketFacade.add_manager("amit2@gmail.com", 1);
        Response bad = owner.add_manager("amit2@gmail.com", 1);
        check_was_not_exception("Manager added successfully", good);
        check_was_exception(bad);
    }

    //----------------------- Concurrent -----------------------

    @Test
    void concurrent_add_new_owner(){
        List<MarketFacade> marketFacadeList = createUsers("newowner");
        for (int i = 0; i < num_of_threads; i++) { // make all users to owners
            Response res = marketFacade.add_owner("newowner"+i+"@gmail.com",1);
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
                Response res = marketFacadeList.get(num_of_market_facade).add_owner("check@gmail.com", 1);
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
            Response res = marketFacade.add_owner("newowner"+i+"@gmail.com",1);
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
                Response res = marketFacadeList.get(num_of_market_facade).add_manager("check@gmail.com", 1);
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
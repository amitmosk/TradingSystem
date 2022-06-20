package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;

class BuyCart {
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
    private SupplyInfo supplyInfo = new SupplyInfo("1", "2", "3", "4", "5");
    private PaymentInfo payment_info = new PaymentInfo("123", "456", "789", "245", "123", "455");

    public BuyCart() {
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


    //------------------------------- User makes purchase --------------------------------------------------------------------------

    /**
     * Buy cart
     * 1. user add product to cart and buy - check purchase
     * 2. user try to buy cart again - verify fail because cart is empty
     * 2. user add product to cart log out and buy - should fail
     * 3. user log back in, and buy cart - check purchase
     * */
    @Test
    void buyCart() {
        //step 1
        marketFacade.add_product_to_cart(1,productId,2);
        Response<UserPurchase> res = marketFacade.buy_cart(payment_info, supplyInfo);
        assertTrue(res.getValue().getTotal_price() == 2 * price);
        //step 2
        res = marketFacade.buy_cart(payment_info,supplyInfo);
        assertTrue(check_was_exception(res),"user succeed to buy empty cart");
        //step 3
        marketFacade.add_product_to_cart(1,productId,2);
        marketFacade.logout();
        res = marketFacade.buy_cart(payment_info, supplyInfo);
        assertTrue(check_was_exception(res), "user succeed to buy empty cart right after logout");
        //step 4
        marketFacade.add_product_to_cart(1,productId,2);
        res = marketFacade.buy_cart(payment_info, supplyInfo);
        assertTrue(res.getValue().getTotal_price() == 2 * price);
    }


    @Test
    void BuyHappy() {
        marketFacade.open_store("Amit Store2");
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        marketFacade.add_product_to_cart(2, 2, 100);
        Response res = marketFacade.buy_cart(payment_info, supplyInfo);
        check_was_not_exception("", res);
    }

    @Test
    void BuyTooMuch() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        Response res = marketFacade.add_product_to_cart(2, 1, 120);
        marketFacade.buy_cart(payment_info, supplyInfo);
        check_was_exception(res);
    }

    @Test
    void BuySadminus1() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        Response res = marketFacade.add_product_to_cart(2, 1, -1);
        marketFacade.buy_cart(payment_info, supplyInfo);
        check_was_exception(res);
    }

    @Test
    void two_users_buying_the_same_product() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade.add_product_to_cart(1, productId, 30);
        MarketFacade tmpMarket = new MarketFacade(payment, supply);
        tmpMarket.register("amit1@gmail.com", password, "amit", "mosko", birth_date);
        tmpMarket.add_product_to_cart(1, productId, 50);
        Response goodR = marketFacade.buy_cart(payment_info, supplyInfo);
        Response BadR = tmpMarket.buy_cart(payment_info, supplyInfo);
        check_was_not_exception("Purchase done successfully", goodR);
        check_was_exception(BadR);
    }

    //----------------------- Concurrent -----------------------

    //scenario - there is num of products of specified product in store
    //there is num of threads users who tries to buy all the amount of this product at once.
    //only one user should succeed
    @Test
    void concurrent_buy_same_product_one_user_success() {
        List<MarketFacade> marketFacadeList = createUsers("oneusersuccess");
        for (MarketFacade mf : marketFacadeList) { // add all products to cart
            mf.add_product_to_cart(1, productId, num_of_products);
            List<ProductInformation> res = mf.view_user_cart().getValue().getProducts();
            boolean contains = false;
            for (ProductInformation p : res) {
                if (p.getProduct_id() == productId)
                    contains = true;
            }
            assertTrue(contains, "failed to add product to users cart");
        }

        AtomicInteger num_of_success = new AtomicInteger(0);
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) {
            int num_of_market_facade = i;
            threads.add(new Thread(() -> {
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart(payment_info, supplyInfo);
                if (res.WasException() && res.getValue().getClass().equals(ObjectDoesntExsitException.class))
                    num_of_exceptions.incrementAndGet();
                else if(!res.WasException())
                    num_of_success.getAndIncrement();
            }));
        }
        start_threads(threads);
        join_threads(threads);
        assertTrue(num_of_exceptions.get() == num_of_threads-1,"concurency fail, fail count: "+num_of_exceptions.get());
        assertTrue(num_of_success.get() == 1,"concurency fail, success count: "+num_of_success.get());
    }

    // scenario - there is num of products of specified product in store
    // there is num of threads users who tries to buy 1 item of this product at once.
    // (num of products) users should succeed and the other fails
    @Test
    void concurrent_buy_same_product_half_user_success() {
        List<MarketFacade> marketFacadeList = createUsers("allusersuccess");
        for (MarketFacade mf : marketFacadeList) { // add all products to cart
            mf.add_product_to_cart(1, productId, 1);
            List<ProductInformation> res = mf.view_user_cart().getValue().getProducts();
            boolean contains = false;
            for (ProductInformation p : res) {
                if (p.getProduct_id() == productId)
                    contains = true;
            }
            assertTrue(contains, "failed to add product to users cart");
        }

        AtomicInteger num_of_success = new AtomicInteger(0);
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) {
            int num_of_market_facade = i;
            threads.add(new Thread(() -> {
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart(payment_info, supplyInfo);
                if (res.WasException() && res.getValue().getClass().equals(ObjectDoesntExsitException.class))
                    num_of_exceptions.incrementAndGet();
                else if(!res.WasException())
                    num_of_success.getAndIncrement();
            }));
        }
        start_threads(threads);
        join_threads(threads);
        assertTrue(num_of_exceptions.get() == num_of_threads-num_of_products,"concurency fail, fail count: "+num_of_exceptions.get());
        assertTrue(num_of_success.get() == num_of_products,"concurency fail, success count: "+num_of_success.get());
    }

    // scenario - there is num of products of specified product in store
    // there is num of threads users who tries to buy 1 item of this product at once.
    // all users should succeed.
    @Test
    void concurrent_buy_same_product_all_user_success() {
        List<MarketFacade> marketFacadeList = createUsers("allusersuccess");
        for (MarketFacade mf : marketFacadeList) { // add all products to cart
            mf.add_product_to_cart(1, productId, 1);
            List<ProductInformation> res = mf.view_user_cart().getValue().getProducts();
            boolean contains = false;
            for (ProductInformation p : res) {
                if (p.getProduct_id() == productId)
                    contains = true;
            }
            assertTrue(contains, "failed to add product to users cart");
        }

        AtomicInteger num_of_success = new AtomicInteger(0);
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < num_of_products; i++) {
            int num_of_market_facade = i;
            threads.add(new Thread(() -> {
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart(payment_info, supplyInfo);
                if (res.WasException() && res.getValue().getClass().equals(ObjectDoesntExsitException.class))
                    num_of_exceptions.incrementAndGet();
                else if(!res.WasException())
                    num_of_success.getAndIncrement();
            }));
        }
        start_threads(threads);
        join_threads(threads);
        assertTrue(num_of_exceptions.get() == 0,"concurency fail, fail count: "+num_of_exceptions.get());
        assertTrue(num_of_success.get() == num_of_products,"concurency fail, success count: "+num_of_success.get());
    }


}
package TradingSystem.server.Domain.Facade.AcceptanceTest;

import TradingSystem.server.Domain.ExternSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreMoudleTest {
    final int num_of_threads = 100;
    int productId;
    String Email = "amit@gmail.com";
    String Name = "amit";
    SupplyAdapter supplyAdapter = new SupplyAdapterImpl();
    PaymentAdapter paymentAdapter = new PaymentAdapterImpl();
    MarketFacade marketFacade = new MarketFacade(paymentAdapter,supplyAdapter);
    private String birth_date;

    //------------------------------- helper functions ------------------------------
    private void check_was_not_excption(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response r = marketFacade.add_product_to_store(1, 50, "apple", 100, "fruits", arraylist);
    }
    private void buy_product() {
        marketFacade.add_product_to_cart(1, productId, 20);
        Response res = marketFacade.buy_cart("", "");
    }
    //------------------------- end of helper functions ------------------------------

    @BeforeEach
    void SetUp() {
        this.productId = 1;
        this.birth_date = LocalDateTime.now().minusYears(30).toString();
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade.clear();
        marketFacade = new MarketFacade(payment, supply);
        marketFacade.register("amit@gmail.com", "Aa123456", "amit", "grumet", birth_date);
        marketFacade.open_store("amit store");
        add_product();
    }


    @Test
    void get_user_email_happy() {
        //happy
        Response s = marketFacade.get_user_email();
        check_was_not_excption("successfully received user's email", s);
    }

    @org.junit.jupiter.api.Test
    void get_user_email_sad() {
        marketFacade.logout();
        Response sBad = marketFacade.get_user_email();
        check_was_exception(sBad);
        marketFacade.login("amit@gmail.com", "Aa123456");
    }

    @Test
    void get_user_name_happy() {
        //happy
        marketFacade.logout();
        marketFacade.login("amit@gmail.com", "Aa123456");
        Response s = marketFacade.get_user_name();
        check_was_not_excption("successfully received user's name", s);
    }

    @org.junit.jupiter.api.Test
    void get_user_name_sad() {
        //sad
        marketFacade.logout();
        Response sBad = marketFacade.get_user_name();
        check_was_exception(sBad);
        marketFacade.login("amit@gmail.com", "Aa123456");
    }

    @org.junit.jupiter.api.Test
    void buyCart() {
        marketFacade.buy_cart("", "");
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store_test_happy() {
        //happy
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response r = marketFacade.add_product_to_store(1, 50, "orange", 100, "fruits", arraylist);
        check_was_not_excption("Product added successfully", r);
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store_test_sad() {
        //sad-no product
        Response rSad = marketFacade.add_product_to_store(5, 50, "apple", 100, "fruits", new ArrayList<>());
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store_happy() {
        //happy
        Response r = marketFacade.delete_product_from_store(productId, 1);
        check_was_not_excption("Product deleted successfully", r);
        add_product();
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store_sad() {
        //sad-no product
        Response rSad = marketFacade.delete_product_from_store(productId, 22);
        check_was_exception(rSad);
    }


    @org.junit.jupiter.api.Test
    void edit_product_name_happy() {
        //happy
        Response r = marketFacade.edit_product_name(productId, 1, "orange");
        check_was_not_excption("Product name edit successfully", r);
    }

    @org.junit.jupiter.api.Test
    void edit_product_name_sad() {
        //sad-no product
        Response rSad = marketFacade.edit_product_name(productId, 5, "orange");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_happy() {
        //happy
        Response r = marketFacade.edit_product_price(productId, 1, 90);
        check_was_not_excption("Product price edit successfully", r);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_sad() {
        //sad
        Response rSad = marketFacade.edit_product_price(productId, 3, 90);
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_category_happy() {
        //happy
        Response r = marketFacade.edit_product_category(productId, 1, "food");
        check_was_not_excption("Product category edit successfully", r);

    }

    @org.junit.jupiter.api.Test
    void edit_product_category_sad() {
        //sad
        Response rSad = marketFacade.edit_product_category(productId, 10, "food");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void find_store_information_happy() {
        //happy
        Response r = marketFacade.find_store_information(1);
        check_was_not_excption("Store information received successfully", r);

    }

    @org.junit.jupiter.api.Test
    void find_store_information_sad() {
        Response rSad = marketFacade.find_store_information(100);
        check_was_exception(rSad);
    }


    @Test
    void find_product_information_happy() {
        //happy
        Response r = marketFacade.find_product_information(productId, 1);
        check_was_not_excption("Product information received successfully", r);

    }

    @Test
    void find_product_information_sad() {
        //sad
        Response rSad = marketFacade.find_product_information(productId, 2);
        check_was_exception(rSad);
    }

    @Test
    void find_products_by_name_happy() {
        //happy
        Response r = marketFacade.find_products_by_name("apple");
        check_was_not_excption("Product list received successfully", r);
    }


    @Test
    void find_products_by_category_happy() {
        //happy
        Response r = marketFacade.find_products_by_category("fruits");
        check_was_not_excption("Products received successfully", r);

    }


    @Test
    void find_products_by_keywords_happy() {
        //happy
        Response r = marketFacade.find_products_by_keywords("fruits");
        check_was_not_excption("Products received successfully", r);
        //sad
    }


    @Test
    void add_product_review_happy() {
        //happy
        buy_product();
        Response r = marketFacade.add_product_review(productId, 1, "great product");
        check_was_not_excption("Review added successfully", r);
    }

    @Test
    void add_product_review_sad() {
        //sad
        Response rSad = marketFacade.add_product_review(productId, 2, "great product");
        check_was_exception(rSad);
    }

    @Test
    void rate_product_happy() {
        //happy
        buy_product();
        Response r = marketFacade.rate_product(productId, 1, 5);
        check_was_not_excption("Rating added successfully to the product", r);
    }

    @Test
    void rate_product_sad() {
        //sad
        Response rSad = marketFacade.rate_product(productId, 10, 5);
        check_was_exception(rSad);
    }

    @Test
    void rate_my_store() {
        //happy
        buy_product();
        Response r = marketFacade.rate_store(1, 5);
        check_was_exception(r);
    }

    @Test
    void rate_store_sad() {
        //sad
        Response rSAd = marketFacade.rate_store(1, 5);
        check_was_exception(rSAd);
    }

    @Test
    void BuyHappy() {
        marketFacade.open_store("Amit Store2");
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        marketFacade.add_product_to_cart(2, 1, 100);
        Response res = marketFacade.buy_cart("", "");
        check_was_not_excption("", res);
    }

    @Test
    void BuyTooMuch() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        Response res = marketFacade.add_product_to_cart(2, 1, 120);
        marketFacade.buy_cart("", "");
        check_was_exception(res);
    }

    @Test
    void BuySadminus1() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        Response res = marketFacade.add_product_to_cart(2, 1, -1);
        marketFacade.buy_cart("", "");
        check_was_exception(res);
    }

    @Test
    void send_question_to_store_happy() {
        //happy
        buy_product();
        Response r = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_not_excption("Question send to the store successfully", r);

    }

    @Test
    void send_question_to_store_sad() {
        //sad
        Response rSad = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_exception(rSad);
    }

    @Test
    void edit_product_key_words_happy() {
        //happy
        ArrayList arrayList = new ArrayList();
        arrayList.add("Food");
        Response r = marketFacade.edit_product_key_words(productId, 1, arrayList);
        check_was_not_excption("Product key_words edit successfully", r);

    }

    @Test
    void edit_product_key_words_sad() {
        //sad
        Response rSad = marketFacade.edit_product_key_words(productId, 100, new ArrayList<>());
        check_was_exception(rSad);
    }

/*
    @Test
    void set_store_purchase_policy() {
        //happy
        PurchasePolicy p = new PurchasePolicy();
        Response r = marketFacade.set_store_purchase_policy(1, p);
        check_was_not_excption("Store purchase rules set successfully", r);
    }

    @Test
    void set_store_discount_policy() {
        //happy
        DiscountPolicy p = new DiscountPolicy();
        Response r = marketFacade.set_store_discount_policy(1, p);
        check_was_not_excption("Store discount rules set successfully", r);
    }

*/

    @Test
    void two_users_buying_the_same_product() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade.add_product_to_cart(1, productId, 30);
        MarketFacade tmpMarket = new MarketFacade(payment, supply);
        tmpMarket.register("amit1@gmail.com", "Aa123456", "amit", "mosko", birth_date);
        tmpMarket.add_product_to_cart(1, productId, 50);
        Response goodR = marketFacade.buy_cart("", "");
        Response BadR = tmpMarket.buy_cart("", "");
        check_was_not_excption("Purchase done successfully", goodR);
        check_was_exception(BadR);
    }

    @Test
    void adding_the_same_user_to_manegment() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        MarketFacade owner = new MarketFacade(payment, supply);
        owner.register("amit1@gmail.com", "Aa123456", "amit", "mosko", birth_date);
        MarketFacade manger = new MarketFacade(payment, supply);
        manger.register("amit2@gmail.com", "Aa123456", "gal", "grumet", birth_date);
        marketFacade.add_owner("amit1@gmail.com", 1);
        Response good = marketFacade.add_manager("amit2@gmail.com", 1);
        Response bad = owner.add_manager("amit2@gmail.com", 1);
        check_was_not_excption("Manager added successfully", good);
        check_was_exception(bad);
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


    private List<MarketFacade> createUsers(String starting) {
        String ending = "@gmail.com";
        List<MarketFacade> facades = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) {
            String email = starting + i + ending;
            MarketFacade mf = new MarketFacade(paymentAdapter, supplyAdapter);
            Response res = mf.register(email, "aA123456", "gal", "brown", birth_date);
            check_was_not_excption("failed to register user for testing", res);
            facades.add(mf);
        }
        return facades;
    }

    @Test
    void concurrent_buy_same_product_one_user_success() {
        List<MarketFacade> marketFacadeList = createUsers("oneusersuccess");
        for (MarketFacade mf : marketFacadeList) { // add all products to cart
            mf.add_product_to_cart(1, productId, 50);
            Map<Store, Basket> res = mf.view_user_cart().getValue();
            boolean contains = false;
            for (Basket b : res.values()) {
                if (b.get_productsIds_and_quantity().containsKey(productId))
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
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart("", "");
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
}

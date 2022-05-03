package Tests;

import Domain.ExternSystems.PaymentAdapter;
import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.SupplyAdapter;
import Domain.ExternSystems.SupplyAdapterImpl;
import Domain.Facade.MarketFacade;
import Domain.StoreModule.Policy.DiscountPolicy;
import Domain.StoreModule.Policy.PurchasePolicy;
import Domain.Utils.Response;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;

import java.util.ArrayList;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreMoudleTests {
    int productId = 0;
    MarketFacade marketFacade;
    String Email = "amit@gmail.com";
    String Name = "amit";

    //helper functions
    private void check_was_not_excption(String msg, String r) {
        Response response = new Gson().fromJson(r, Response.class);
        Assertions.assertFalse(response.WasException());
    }


    private void check_was_exception(String r) {
        Response response = new Gson().fromJson(r, Response.class);
        Assertions.assertTrue(response.WasException());
    }

    private void add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        String r = marketFacade.add_product_to_store(1, 50, "apple", 100, "fruits", arraylist);
        productId++;
    }

    private void buy_product() {
        marketFacade.add_product_to_cart(1, productId, 20);
        String res = marketFacade.buy_cart("", "");
    }
    //end of helper functions

    @BeforeAll
    void SetUp() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade = new MarketFacade(payment, supply);
        marketFacade.register("amit@gmail.com", "Aa123456", "amit", "grumet");
        marketFacade.open_store("amit store");
        add_product();
    }


    @Test
    void get_user_email_happy() {
        //happy
        String s = marketFacade.get_user_email();
        check_was_not_excption("successfully received user's email", s);
    }

    @org.junit.jupiter.api.Test
    void get_user_email_sad() {
        marketFacade.logout();
        String sBad = marketFacade.get_user_email();
        check_was_exception(sBad);
        marketFacade.login("amit@gmail.com", "Aa123456");
    }

    @Test
    void get_user_name_happy() {
        //happy
        marketFacade.logout();
        marketFacade.login("amit@gmail.com", "Aa123456");
        String s = marketFacade.get_user_name();
        check_was_not_excption("successfully received user's name", s);
    }

    @org.junit.jupiter.api.Test
    void get_user_name_sad() {
        //sad
        marketFacade.logout();
        String sBad = marketFacade.get_user_name();
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
        String r = marketFacade.add_product_to_store(1, 50, "orange", 100, "fruits", arraylist);
        check_was_not_excption("Product added successfully", r);
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store_test_sad() {
        //sad-no product
        String rSad = marketFacade.add_product_to_store(5, 50, "apple", 100, "fruits", new ArrayList<>());
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store_happy() {
        //happy
        String r = marketFacade.delete_product_from_store(productId, 1);
        check_was_not_excption("Product deleted successfully", r);
        add_product();
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store_sad() {
        //sad-no product
        String rSad = marketFacade.delete_product_from_store(productId, 22);
        check_was_exception(rSad);
    }


    @org.junit.jupiter.api.Test
    void edit_product_name_happy() {
        //happy
        String r = marketFacade.edit_product_name(productId, 1, "orange");
        check_was_not_excption("Product name edit successfully", r);
    }

    @org.junit.jupiter.api.Test
    void edit_product_name_sad() {
        //sad-no product
        String rSad = marketFacade.edit_product_name(productId, 5, "orange");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_happy() {
        //happy
        String r = marketFacade.edit_product_price(productId, 1, 90);
        check_was_not_excption("Product price edit successfully", r);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_sad() {
        //sad
        String rSad = marketFacade.edit_product_price(productId, 3, 90);
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_category_happy() {
        //happy
        String r = marketFacade.edit_product_category(productId, 1, "food");
        check_was_not_excption("Product category edit successfully", r);

    }

    @org.junit.jupiter.api.Test
    void edit_product_category_sad() {
        //sad
        String rSad = marketFacade.edit_product_category(productId, 10, "food");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void find_store_information_happy() {
        //happy
        String r = marketFacade.find_store_information(1);
        check_was_not_excption("Store information received successfully", r);

    }

    @org.junit.jupiter.api.Test
    void find_store_information_sad() {
        String rSad = marketFacade.find_store_information(100);
        check_was_exception(rSad);
    }


    @Test
    void find_product_information_happy() {
        //happy
        String r = marketFacade.find_product_information(productId, 1);
        check_was_not_excption("Product information received successfully", r);

    }

    @Test
    void find_product_information_sad() {
        //sad
        String rSad = marketFacade.find_product_information(productId, 2);
        check_was_exception(rSad);
    }

    @Test
    void find_products_by_name_happy() {
        //happy
        String r = marketFacade.find_products_by_name("apple");
        check_was_not_excption("Product list received successfully", r);
    }


    @Test
    void find_products_by_category_happy() {
        //happy
        String r = marketFacade.find_products_by_category("fruits");
        check_was_not_excption("Products received successfully", r);

    }


    @Test
    void find_products_by_keywords_happy() {
        //happy
        String r = marketFacade.find_products_by_keywords("fruits");
        check_was_not_excption("Products received successfully", r);
        //sad
    }


    @Test
    void add_product_review_happy() {
        //happy
        buy_product();
        String r = marketFacade.add_product_review(productId, 1, "great product");
        check_was_not_excption("Review added successfully", r);
    }

    @Test
    void add_product_review_sad() {
        //sad
        String rSad = marketFacade.add_product_review(productId, 2, "great product");
        check_was_exception(rSad);
    }

    @Test
    void rate_product_happy() {
        //happy
        buy_product();
        String r = marketFacade.rate_product(productId, 1, 5);
        check_was_not_excption("Rating added successfully to the product", r);
    }

    @Test
    void rate_product_sad() {
        //sad
        String rSad = marketFacade.rate_product(productId, 10, 5);
        check_was_exception(rSad);
    }

    @Test
    void rate_my_store() {
        //happy
        buy_product();
        String r = marketFacade.rate_store(1, 5);
        check_was_exception(r);
    }

    @Test
    void rate_store_sad() {
        //sad
        String rSAd = marketFacade.rate_store(1, 5);
        check_was_exception(rSAd);
    }

    @Test
    void BuyHappy() {
        marketFacade.open_store("Amit Store2");
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        marketFacade.add_product_to_cart(2, 1, 100);
        String res = marketFacade.buy_cart("", "");
        check_was_not_excption("", res);
    }

    @Test
    void BuyTooMuch() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        String res = marketFacade.add_product_to_cart(2, 1, 120);
        marketFacade.buy_cart("", "");
        check_was_exception(res);
    }

    @Test
    void BuySadminus1() {
        marketFacade.add_product_to_store(2, 100, "tmp", 100, "tmp", new ArrayList<>());
        String res = marketFacade.add_product_to_cart(2, 1, -1);
        marketFacade.buy_cart("", "");
        check_was_exception(res);
    }

    @Test
    void send_question_to_store_happy() {
        //happy
        buy_product();
        String r = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_not_excption("Question send to the store successfully", r);

    }

    @Test
    void send_question_to_store_sad() {
        //sad
        String rSad = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_exception(rSad);
    }

    @Test
    void edit_product_key_words_happy() {
        //happy
        ArrayList arrayList = new ArrayList();
        arrayList.add("Food");
        String r = marketFacade.edit_product_key_words(productId, 1, arrayList);
        check_was_not_excption("Product key_words edit successfully", r);

    }

    @Test
    void edit_product_key_words_sad() {
        //sad
        String rSad = marketFacade.edit_product_key_words(productId, 100, new ArrayList<>());
        check_was_exception(rSad);
    }

    @Test
    void set_store_purchase_policy() {
        //happy
        PurchasePolicy p = new PurchasePolicy();
        String r = marketFacade.set_store_purchase_policy(1, p);
        check_was_not_excption("Store purchase rules set successfully", r);
    }

    @Test
    void set_store_discount_policy() {
        //happy
        DiscountPolicy p = new DiscountPolicy();
        String r = marketFacade.set_store_discount_policy(1, p);
        check_was_not_excption("Store discount rules set successfully", r);
    }


    @Test
    void two_users_buying_the_same_product() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade.add_product_to_cart(1, productId, 30);
        MarketFacade tmpMarket = new MarketFacade(payment, supply);
        tmpMarket.register("amit1@gmail.com", "Aa123456", "amit", "mosko");
        tmpMarket.add_product_to_cart(1, productId, 50);
        String goodR = marketFacade.buy_cart("", "");
        String BadR = tmpMarket.buy_cart("", "");
        check_was_not_excption("Purchase done successfully", goodR);
        check_was_exception(BadR);
    }

    @Test
    void adding_the_same_user_to_manegment() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        MarketFacade owner = new MarketFacade(payment, supply);
        owner.register("amit1@gmail.com", "Aa123456", "amit", "mosko");
        MarketFacade manger = new MarketFacade(payment, supply);
        manger.register("amit2@gmail.com", "Aa123456", "gal", "grumet");
        marketFacade.add_owner("amit1@gmail.com", 1);
        String good = marketFacade.add_manager("amit2@gmail.com", 1);
        String bad = owner.add_manager("amit2@gmail.com", 1);
        check_was_not_excption("Manager added successfully", good);
        check_was_exception(bad);

    }
}

package Domain.StoreModule;

import Domain.ExternSystems.PaymentAdapter;
import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.SupplyAdapter;
import Domain.ExternSystems.SupplyAdapterImpl;
import Domain.Facade.MarketFacade;
import Domain.StoreModule.Policy.DiscountPolicy;
import Domain.StoreModule.Policy.PurchasePolicy;
import Domain.StoreModule.Product.Product;
import Domain.Utils.Response;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class StoreMoudleTests {
    MarketFacade marketFacade;
    String Email = "amit@gmail.com";
    String Name = "amit";

    @BeforeEach
    void SetUp() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade = new MarketFacade(payment, supply);
        marketFacade.register("amit@gmail.com", "Aa123456", "amit", "grumet");
        marketFacade.login("amit@gmail.com", "Aa123456");
        marketFacade.open_store("amit store");
    }

    private void check_equel_msg(String msg, String r) {
        Response response = new Gson().fromJson(r, Response.class);
        Assertions.assertEquals(response.getMessage(), msg);
    }

    private void check_was_execption(String r) {
        Response response = new Gson().fromJson(r, Response.class);
        Assertions.assertTrue(response.WasException());
    }

    private void check_not_equel_msg(String msg, String r) {
        Response response = new Gson().fromJson(r, Response.class);
        Assertions.assertNotEquals(response.getMessage(), msg);
    }

    @org.junit.jupiter.api.Test
    void get_user_email() throws Exception {
        //happy
        String s = marketFacade.get_user_email();
        check_equel_msg("successfully received user's email", s);
        //sad-no user connected
        marketFacade.logout();
        String sBad = marketFacade.get_user_email();
        check_was_execption(sBad);


    }

    @org.junit.jupiter.api.Test
    void get_user_name() {
        //happy
        String s = marketFacade.get_user_name();
        check_equel_msg("successfully received user's name", s);
        //sad
        marketFacade.logout();
        String sBad = marketFacade.get_user_name();
        check_was_execption(sBad);//dosent no
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store() {
        //happy
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Product p = new Product("apple", 1, 1, 100, "fruits", arraylist);
        String r = marketFacade.add_product_to_store(1, 50, "apple", 100, "fruits", arraylist);
        check_equel_msg("Product added successfully", r);
        //sad-no product
        String rSad = marketFacade.add_product_to_store(1, 50, "apple", 100, "fruits", arraylist);
        check_was_execption(rSad);
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store() {
        //happy
        add_product_to_store();
        String r = marketFacade.delete_product_from_store(1, 1);
        check_equel_msg("Product deleted successfully", r);
        //sad-no product
        String rSad = marketFacade.delete_product_from_store(1, 1);
        check_was_execption(rSad);

    }

    @org.junit.jupiter.api.Test
    void edit_product_name() {
        //happy
        add_product_to_store();
        String r = marketFacade.edit_product_name(1, 1, "orange");
        check_equel_msg("Product name edit successfully", r);
        //sad-no product
        String rSad = marketFacade.edit_product_name(1, 1, "orange");
        check_was_execption(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price() {
        //happy
        add_product_to_store();
        String r = marketFacade.edit_product_price(1, 1, 90);
        check_equel_msg("Product price edit successfully", r);
        //sad
        String rSad = marketFacade.edit_product_price(1, 1, 90);
        check_was_execption(rSad);

    }

    @org.junit.jupiter.api.Test
    void edit_product_category() {
        //happy
        add_product_to_store();
        String r = marketFacade.edit_product_category(1, 1, "food");
        check_equel_msg("Product category edit successfully", r);
        //sad
        String rSad = marketFacade.edit_product_category(1, 1, "food");
        check_was_execption(rSad);
    }

    @Test
    void find_store_information() {
        //happy
        String r = marketFacade.find_store_information(1);
        check_equel_msg("Store information received successfully", r);
        //sad
        String rSad = marketFacade.find_store_information(1);
        check_was_execption(rSad);
    }

    @Test
    void find_product_information() {
        //happy
        add_product_to_store();
        String r = marketFacade.find_product_information(1, 1);
        check_equel_msg("Product information received successfully", r);
        //sad
        String rSad = marketFacade.find_product_information(1, 1);
        check_was_execption(rSad);
    }

    @Test
    void find_products_by_name() {
        //happy
        add_product_to_store();
        String r = marketFacade.find_products_by_name("apple");
        check_equel_msg("Products found successfully", r);
        //sad
        String rSad = marketFacade.find_products_by_name("apple");
        check_was_execption(rSad);
    }

    @Test
    void find_products_by_category() {
        //happy
        add_product_to_store();
        String r = marketFacade.find_products_by_category("fruits");
        check_equel_msg("Products received successfully", r);
        //sad
        String rSad = marketFacade.find_products_by_category("fruits");
        check_was_execption(rSad);
    }

    @Test
    void find_products_by_keywords() {
        //happy
        add_product_to_store();
        String r = marketFacade.find_products_by_keywords("fruits");
        check_equel_msg("Products received successfully", r);
        //sad
        String rSad = marketFacade.find_products_by_category("fruits");
        check_was_execption(rSad);
    }


    @Test
    void add_product_review() {
        //happy
        add_product_to_store();
        String r = marketFacade.add_product_review(1, 1, "great product");
        check_equel_msg("Review added successfully", r);
        //sad
        String rSad = marketFacade.add_product_review(1, 1, "great product");
        check_was_execption(rSad);
    }

    @Test
    void rate_product() {
        //happy
        add_product_to_store();
        String r = marketFacade.rate_product(1, 1, 5);
        check_equel_msg("Rating added successfully to the product", r);
        //sad
        String rSad = marketFacade.rate_product(1, 1, 5);
        check_was_execption(rSad);
    }

    @Test
    void rate_store() {
        //happy
        add_product_to_store();
        String r = marketFacade.rate_store(1, 5);
        check_equel_msg("Rating added successfully to the store", r);
        //sad
        String rSAd = marketFacade.rate_store(1, 5);
        check_was_execption(rSAd);
    }

    @Test
    void send_question_to_store() {
        //happy
        add_product_to_store();
        String r = marketFacade.send_question_to_store(1, "how can i control the world");
        check_equel_msg("Question send to the store successfully", r);
        //sad
        String rSad = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_execption(rSad);
    }

    @Test
    void edit_product_key_words() {
        //happy
        add_product_to_store();
        ArrayList arrayList = new ArrayList();
        arrayList.add("Food");
        String r = marketFacade.edit_product_key_words(1, 1, arrayList);
        check_equel_msg("Product key_words edit successfully", r);
        //sad
        String rSad = marketFacade.edit_product_key_words(1, 1, arrayList);
        check_was_execption(rSad);
    }

    @Test
    void set_store_purchase_policy() {
        //happy
        PurchasePolicy p = new PurchasePolicy();
        String r = marketFacade.set_store_purchase_policy(1, p);
        check_equel_msg("Store purchase rules set successfully", r);
        //sad
    }

    @Test
    void set_store_discount_policy() {
        //happy
        DiscountPolicy p = new DiscountPolicy();
        String r = marketFacade.set_store_discount_policy(1, p);
        check_equel_msg("Store discount rules set successfully", r);
        //sad
    }

    @Test
    void check_cart_available_products_and_calc_price() {

    }

    @Test
    void checkAvailablityAndGet() {
    }

    @Test
    void update_stores_inventory() {
    }

    @Test
    void open_store() {
    }

    @Test
    void add_review() {
    }
}

package TradingSystem.server.Domain.Facade.AcceptanceTest;

import TradingSystem.server.Domain.ExternSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreMoudleTest {
    private final int num_of_threads = 100;
    private final int num_of_products = 50;
    private final int price = 100;
    private int productId;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";
    private final SupplyAdapter supplyAdapter = new SupplyAdapterImpl();
    private final PaymentAdapter paymentAdapter = new PaymentAdapterImpl();
    private MarketFacade marketFacade = new MarketFacade(paymentAdapter,supplyAdapter);
    private MarketFacade manager = new MarketFacade(paymentAdapter,supplyAdapter);
    private MarketFacade general_user = new MarketFacade(paymentAdapter,supplyAdapter);
    private String birth_date;

    //------------------------------- helper functions --------------------------------------------------------------------------

    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }

    private boolean check_was_exception(Response response) {
        return response.WasException();
    }

    private int add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response<Map<Product,Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
        return r.getValue().keySet().stream().findAny().get().getProduct_id();
    }

    private void buy_product() {
        marketFacade.add_product_to_cart(1, productId, 20);
        Response res = marketFacade.buy_cart("", "");
    }

    private boolean check_if_product_exists_inventory(Map<Product,Integer> products, String product_name){
        for(Product p : products.keySet()){
            if(p.getName().equals(product_name))
                return true;
        }
        return false;
    }

    private boolean check_if_product_exists_find(String product_name){
        List<Product> products = marketFacade.find_products_by_category("fruits").getValue();
        for(Product p : products){
            if(p.getName().equals(product_name))
                return true;
        }
        return false;
    }
    //helper function which starts all threads
    private void start_threads(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        } // running all the threads parallel
    }
    //helper function which join all threads
    private void join_threads(List<Thread> threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (Exception e) {
            Assertions.fail( "there was error while running the threads");
        }
    }
    //helper function which creates num_of_threads users represented by market facades
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

    //------------------------- end of helper functions  --------------------------------------------------------------------------

    @BeforeEach
    void SetUp() {
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade.clear();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        manager = new MarketFacade(paymentAdapter,supplyAdapter);
        general_user = new MarketFacade(paymentAdapter,supplyAdapter);
        marketFacade.register(email, password, name, last_name, birth_date);
        manager.register(manager_email,password,name,last_name,birth_date);
        general_user.register("general@gmail.com",password,name,last_name,birth_date);
        marketFacade.open_store("amit store");
        add_product();
        marketFacade.add_manager(manager_email,1);
    }


    /**
     * 1. register to user and get his email
     * 2. log out and get email - make sure it fails
     * 3. log in to the same user and try to get his email
     * */
    @Test
    void get_user_email_happy() {
        //step 1 - get email after register
        Response res = marketFacade.get_user_email();
        check_was_not_exception("failed to get user's email when it should worked", res);
        assertEquals(email,res.getValue(),"expected to get email - "+email+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("failed to logout while it should work.", res);
        res = marketFacade.get_user_email();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_email();
        assertEquals(email,res.getValue(),"after re-login expected to get email - "+email+ "but got - "+res.getValue());
    }

    @org.junit.jupiter.api.Test
    void get_user_email_sad() {
        marketFacade.logout();
        Response sBad = marketFacade.get_user_email();
        check_was_exception(sBad); // TODO: specify exception
    }

    /**
     * 1. register to user and get his name
     * 2. log out and get name - make sure it fails
     * 3. log in to the same user and try to get his name
     * */
    @Test
    void get_user_name_happy() {
        //step 1 - get name after register
        Response res = marketFacade.get_user_name();
        check_was_not_exception("failed to get user's name when it should worked", res);
        assertEquals(name,res.getValue(),"expected to get name - "+name+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("got an name while it shouldn't work", res);
        res = marketFacade.get_user_name();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_name();
        assertEquals(name,res.getValue(),"after re-login expected to get name - "+name+ "but got - "+res.getValue());
    }

    @org.junit.jupiter.api.Test
    void get_user_name_sad() {
        //sad
        marketFacade.logout();
        Response sBad = marketFacade.get_user_name();
        check_was_exception(sBad);
    }

    /**
     * 1. register to user and get his last name
     * 2. log out and get last name - make sure it fails
     * 3. log in to the same user and try to get his last name
     * */
    @Test
    void get_user_last_name_happy() {
        //step 1 - get name after register
        Response res = marketFacade.get_user_last_name();
        check_was_not_exception("failed to get user's last name when it should worked", res);
        assertEquals(last_name,res.getValue(),"expected to get last name - "+last_name+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("got a last name while it shouldn't work", res);
        res = marketFacade.get_user_last_name();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_last_name();
        assertEquals(last_name,res.getValue(),"after re-login expected to get name - "+last_name+ "but got - "+res.getValue());
    }

    @org.junit.jupiter.api.Test
    void get_user_last_name_sad() {
        marketFacade.logout();
        Response sBad = marketFacade.get_user_name();
        check_was_exception(sBad);
    }

    /**
     * 1. user add product to cart and buy - check purchase
     * 2. user try to buy cart again - verify fail because cart is empty
     * 2. user add product to cart log out and buy - should fail
     * 3. user log back in, and buy cart - check purchase
     * */
    @org.junit.jupiter.api.Test
    void buyCart() {
        //step 1
        marketFacade.add_product_to_cart(1,productId,2);
        Response<UserPurchase> res = marketFacade.buy_cart("", "");
        assertTrue(res.getValue().getTotal_price() == 2 * price);
        //step 2
        res = marketFacade.buy_cart("","");
        assertTrue(check_was_exception(res),"user succeed to buy empty cart");
        //step 3
        marketFacade.add_product_to_cart(1,productId,2);
        marketFacade.logout();
        res = marketFacade.buy_cart("", "");
        assertTrue(check_was_exception(res), "user succeed to buy empty cart right after logout");
        //step 4
        marketFacade.add_product_to_cart(1,productId,2);
        res = marketFacade.buy_cart("", "");
        assertTrue(res.getValue().getTotal_price() == 2 * price);
    }

    /**
     * 1. user add product to his own store - should succeed
     * 2. user logout and try to add product to his own store - should fail
     * 3. user log in back and try to add product to his store - succeed
     * 4. add product with store member - should work
     * 5. add product with store member without permissions - fail
     * 6. add product of existing product - fail
     */
    @org.junit.jupiter.api.Test
    void add_product_to_store_test_happy() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        //step 1
        Response<Map<Product,Integer>> res = marketFacade.add_product_to_store(1, 50, "orange", 100, "fruits", arraylist);
        check_was_not_exception("failed to add product to store while it should work - step 1", res);
        assertTrue(check_if_product_exists_inventory(res.getValue(),"orange"),"specified product does not exists in the store's inventory");
        //step 2
        marketFacade.logout();
        res = marketFacade.add_product_to_store(1, 50, "mango", 100, "fruits", arraylist);
        assertTrue(check_was_exception(res));
        assertFalse(check_if_product_exists_find("mango"));
        //step 3
        Response login_res = marketFacade.login(email,password);
        check_was_not_exception("failed to login offline user",login_res);
        res = marketFacade.add_product_to_store(1, 50, "mango", 100, "fruits", arraylist);
        check_was_not_exception("failed to add product to store while it should work - step 3", res);
        assertTrue(check_if_product_exists_inventory(res.getValue(),"mango"),"product does not exists in system - step 3");
        //step 4
        res = manager.add_product_to_store(1, 50, "pineapple", 100, "fruits", arraylist);
        check_was_not_exception("failed to add product to store while it should work - step 4", res);
        assertTrue(check_if_product_exists_inventory(res.getValue(),"pineapple"),"specified product does not exists in the store's inventory - step 4");
        //step 5
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>()); // removes manager permissions
        res = manager.add_product_to_store(1, 50, "banana", 100, "fruits", arraylist);
        assertTrue(check_was_exception(res),"succeed to add product to store while it should fail - step 5");
        assertFalse(check_if_product_exists_find("banana"),"product exists in system - step 5");
        //step 6
        res = marketFacade.add_product_to_store(1, 50, "pineapple", 100, "fruits", arraylist);
        assertTrue(check_was_exception(res),"succeed to add product to store while it should fail - step 6");
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store_test_sad() {
        //sad-no product
        Response rSad = marketFacade.add_product_to_store(5, 50, "apple", 100, "fruits", new ArrayList<>());
        check_was_exception(rSad);
    }

    /**
     * 1. checks if apple belongs in store.
     * 2. removes apple from store - should work.
     * 3. add apple back to store and verify
     * 4. log out and try to remove - should fail.
     * 5. remove with non store member - should fail.
     * 6. log in back to user with store permission and remove - succeed(than add back).
     * 7. remove with store member - succeed(than add back)
     * 8. remove with store member with no permission - fail.
     * */
    @org.junit.jupiter.api.Test
    void delete_product_from_store_happy() {
        //step 1
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        assertTrue(check_if_product_exists_find("apple"),"failed to initialized test - apple does not exists");
        //step 2
        Response<Map<Product,Integer>> res = marketFacade.delete_product_from_store(1,1);
        check_was_not_exception("failed to remove product from store while it should fail - step 1", res);
        assertFalse(check_if_product_exists_inventory(res.getValue(),"apple"),"specified product exists in the store's inventory - step 2");
        //step 3
        productId = add_product();
        assertTrue(check_if_product_exists_inventory(res.getValue(),"apple"),"cannot add product to store - step 3");
        //step 4
        Response logout_res = marketFacade.logout();
        assertFalse(check_was_exception(logout_res),"failed to logout online user");
        res = marketFacade.delete_product_from_store(productId,1);
        assertTrue(check_was_exception(res),"succeed to remove product - guest");
        assertTrue(check_if_product_exists_find("apple"),"apple deleted by guests");
        //step 5
        res = general_user.delete_product_from_store(productId,1);
        assertTrue(check_was_exception(res),"succeed to remove product - general_user");
        assertTrue(check_if_product_exists_find("apple"),"apple deleted by general_user");
        //step 6
        Response user_res = marketFacade.login(email,password);
        check_was_not_exception("failed to log in user - step 6",user_res);
        res = marketFacade.delete_product_from_store(productId,1);
        check_was_not_exception("failed to remove product from store while it should succeed - step 6", res);
        assertFalse(check_if_product_exists_inventory(res.getValue(),"apple"),"specified product exists in the store's inventory - step 6");
        productId = add_product();
        assertTrue(check_if_product_exists_inventory(res.getValue(),"apple"),"cannot add product to store - step 3");
        //step 7
        res = manager.delete_product_from_store(productId,1);
        check_was_not_exception("failed to remove product from store while it should fail - step 6", res);
        assertFalse(check_if_product_exists_inventory(res.getValue(),"apple"),"specified product exists in the store's inventory - step 2");
        productId = add_product();
        assertTrue(check_if_product_exists_inventory(res.getValue(),"apple"),"cannot add product to store - step 3");
        //step 9
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>()); // removes manager permissions
        res = manager.delete_product_from_store(productId,1);
        assertTrue(check_was_exception(res),"succeed to add product while it should fail - step 9");
        assertTrue(check_if_product_exists_find("apple"),"cannot add product to store - step 3");
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store_sad() {
        //sad-no product
        Response rSad = marketFacade.delete_product_from_store(productId, 22);
        check_was_exception(rSad);
    }


    /**
     * 1. edit product name - should work (check new product name)
     * 2. log out - edit product name - should fail.
     * 3. log back in change product name - should work.
     * 4. try to edit product name with general user
     * 5. try to edit product name with store member.
     * 6. try to edit product name with store member without permissions.
     * */
    @org.junit.jupiter.api.Test
    void edit_product_name_happy() {
        //step 1 edit product name - should work (check new product name)
        Response res = marketFacade.edit_product_name(productId, 1, "orange");
        check_was_not_exception("failed to edit product name while it should work. - step 1", res);
        assertTrue(check_if_product_exists_find("orange"),"orange is not exists even when the name changed and it should.");
        assertFalse(check_if_product_exists_find("apple"), "apple is still exists in the system although the name changed.");
        //step 2 log out - edit product name - should fail.
        res = marketFacade.logout();
        check_was_not_exception("failed to logout user while it should work - step 2", res);
        res = marketFacade.edit_product_name(productId, 1, "lime");
        assertTrue(check_was_exception(res),"edited product name with guests - step 2");
        assertTrue(check_if_product_exists_find("orange"),"orange is not exists in the system - step 2");
        assertFalse(check_if_product_exists_find("lime"), "lime added to the system while it shouldn't - step 2");
        //step 3 log back in change product name - should work.
        res = marketFacade.login(email,password);
        check_was_not_exception("failed to login user while it should work - step 3", res);
        res = marketFacade.edit_product_name(productId, 1, "lime");
        check_was_not_exception("failed to edit product name while it should work. - step 3", res);
        assertTrue(check_if_product_exists_find("lime"),"lime is not exists even when the name changed and it should. - step 3");
        assertFalse(check_if_product_exists_find("orange"), "orange is still exists in the system although when the name changed. - step 3");
        //step 4 try to edit product name with general user
        res = general_user.edit_product_name(productId, 1, "banana");
        assertTrue(check_was_exception(res),"edited product name with general user - step 4");
        assertTrue(check_if_product_exists_find("lime"),"lime is not exists in the system - step 4");
        assertFalse(check_if_product_exists_find("banana"), "banana added to the system while it shouldn't - step 4");
        //step 5 try to edit product name with store member.
        res = marketFacade.edit_product_name(productId, 1, "mango");
        check_was_not_exception("failed to edit product name while it should work. - step 5", res);
        assertTrue(check_if_product_exists_find("mango"),"mango is not exists even when the name changed and it should. - step 5");
        assertFalse(check_if_product_exists_find("lime"), "lime is still exists in the system although when the name changed. - step 5");
        //step 6 try to edit product name with store member without permissions.
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>());
        res = manager.edit_product_name(productId, 1, "banana");
        assertTrue(check_was_exception(res),"edited product name with general user - step 6");
        assertTrue(check_if_product_exists_find("mango"),"mango is not exists in the system - step 6");
        assertFalse(check_if_product_exists_find("banana"), "banana added to the system while it shouldn't - step 6");
    }

    @org.junit.jupiter.api.Test
    void edit_product_name_sad() {
        //sad-no product
        Response rSad = marketFacade.edit_product_name(productId, 5, "orange");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_happy() {
        Response<Product> product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        double prev_price = product_res.getValue().getPrice();
        //step 1 edit product price - should work (check new product name)
        Response res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 1", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        double new_price = product_res.getValue().getPrice();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 1");
        prev_price = new_price;
        //step 2 log out -> edit product price - should fail.
        res = marketFacade.logout();
        check_was_not_exception("failed to logout user while it should work - step 2", res);
        res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 2");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 2", product_res);
        new_price = product_res.getValue().getPrice();
        assertFalse(new_price == prev_price+10,"product price has changed - step 2");
        //step 3 log back in change product price - should work.
        res = marketFacade.login(email,password);
        check_was_not_exception("failed to login user while it should work - step 3", res);
        res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 3", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 3", product_res);
        new_price = product_res.getValue().getPrice();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 3");
        prev_price = new_price;
        //step 4 try to edit product price with general user
        res = general_user.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 4");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 4", product_res);
        new_price = product_res.getValue().getPrice();
        assertFalse(new_price == prev_price+10,"product price haven't changed - step 4");
        //step 5 try to edit product price with store member.
        res = manager.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 5", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 5", product_res);
        new_price = product_res.getValue().getPrice();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 5");
        prev_price = new_price;
        //step 6 try to edit product price with store member without permissions.
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>());
        res = manager.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 6");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 6", product_res);
        new_price = product_res.getValue().getPrice();
        assertFalse(new_price == prev_price+10,"product price haven't changed - step 6");
    }

    @org.junit.jupiter.api.Test
    void edit_product_price_sad() {
        //sad
        Response rSad = marketFacade.edit_product_price(productId, 3, 90);
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void edit_product_category_happy() {
        Response<Product> product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        String prev_category = product_res.getValue().getCategory();
        //step 1 edit product category - should work (check new product category)
        Response res = marketFacade.edit_product_category(productId, 1, prev_category+"a");
        check_was_not_exception("failed to edit product category while it should work. - step 1", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        String new_category = product_res.getValue().getCategory();
        assertTrue(new_category.equals(prev_category+"a"),"product category haven't changed - step 1");
        prev_category = new_category;
        //step 2 log out -> edit product category - should fail.
        res = marketFacade.logout();
        check_was_not_exception("failed to logout user while it should work - step 2", res);
        res = marketFacade.edit_product_category(productId, 1, prev_category+"a");
        assertTrue(check_was_exception(res),"succeed to edit product category while it should fail. - step 2");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 2", product_res);
        new_category = product_res.getValue().getCategory();
        assertFalse(new_category.equals(prev_category+"a"),"product category has changed - step 2");
        //step 3 log back in change product category - should work.
        res = marketFacade.login(email,password);
        check_was_not_exception("failed to login user while it should work - step 3", res);
        res = marketFacade.edit_product_category(productId, 1, prev_category+"a");
        check_was_not_exception("failed to edit product category while it should work. - step 3", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 3", product_res);
        new_category = product_res.getValue().getCategory();
        assertTrue(new_category.equals(prev_category+"a"),"product category haven't changed - step 3");
        prev_category = new_category;
        //step 4 try to edit product category with general user
        res = general_user.edit_product_category(productId, 1, prev_category+"a");
        assertTrue(check_was_exception(res),"succeed to edit product category while it should fail. - step 4");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 4", product_res);
        new_category = product_res.getValue().getCategory();
        assertFalse(new_category.equals(prev_category+10),"product category haven't changed - step 4");
        //step 5 try to edit product category with store member.
        res = manager.edit_product_category(productId, 1, prev_category+"a");
        check_was_not_exception("failed to edit product category while it should work. - step 5", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 5", product_res);
        new_category = product_res.getValue().getCategory();
        assertTrue(new_category.equals(prev_category+"a"),"product category haven't changed - step 5");
        prev_category = new_category;
        //step 6 try to edit product category with store member without permissions.
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>());
        res = manager.edit_product_category(productId, 1, prev_category+"a");
        assertTrue(check_was_exception(res),"succeed to edit product category while it should fail. - step 6");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 6", product_res);
        new_category = product_res.getValue().getCategory();
        assertFalse(new_category.equals(prev_category+"a"),"product category haven't changed - step 6");
    }

    @org.junit.jupiter.api.Test
    void edit_product_category_sad() {
        //sad
        Response rSad = marketFacade.edit_product_category(productId, 10, "food");
        check_was_exception(rSad);
    }

    @org.junit.jupiter.api.Test
    void find_store_information_happy() {
        Response<StoreInformation> res = marketFacade.find_store_information(1);
        check_was_not_exception("Store information received successfully", res);
        StoreInformation store = res.getValue();
        assertEquals(email,store.getFounder_email());
        assertEquals("amit store",store.getName());
    }

    @org.junit.jupiter.api.Test
    void find_store_information_sad() {
        Response rSad = marketFacade.find_store_information(100);
        check_was_exception(rSad);
    }


    @Test
    void find_product_information_happy() {
        Response<Product> product_information = marketFacade.find_product_information(productId, 1);
        check_was_not_exception("Product information received successfully", product_information);
        assertEquals(product_information.getValue().getName(),"apple","couldn't find properly product name");
        assertEquals(product_information.getValue().getCategory(),"fruits","couldn't find properly product category");
    }

    @Test
    void find_product_information_sad() {
        //sad
        Response rSad = marketFacade.find_product_information(productId, 2);
        check_was_exception(rSad);
    }

    @Test
    void find_products_by_name_happy() {
        Response<List<Product>> response = marketFacade.find_products_by_name("apple");
        check_was_not_exception("Product list received successfully", response);
        assertEquals(response.getValue().stream().findAny().get().getName(),"apple","couldn't find existing product");
    }


    @Test
    void find_products_by_category_happy() {
        //happy
        Response r = marketFacade.find_products_by_category("fruits");
        check_was_not_exception("Products received successfully", r);

    }


    @Test
    void find_products_by_keywords_happy() {
        //happy
        Response r = marketFacade.find_products_by_keywords("fruits");
        check_was_not_exception("Products received successfully", r);
    }


    @Test
    void add_product_review_happy() {
        //happy
        buy_product();
        Response r = marketFacade.add_product_review(productId, 1, "great product");
        check_was_not_exception("Review added successfully", r);
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
        check_was_not_exception("Rating added successfully to the product", r);
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
        marketFacade.add_product_to_cart(2, 2, 100);
        Response res = marketFacade.buy_cart("", "");
        check_was_not_exception("", res);
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
        check_was_not_exception("Question send to the store successfully", r);

    }

    @Test
    void send_question_to_store_sad() {
        //sad
        Response rSad = marketFacade.send_question_to_store(1, "how can i control the world");
        check_was_exception(rSad);
    }

    private List<String> gen_key_words(List<String> key_words){
        List<String> res = new ArrayList<>();
        String word = key_words.get(0)+"a";
        res.add(word);
        return res;
    }

    @Test
    void edit_product_key_words_happy() {
        Response<Product> product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        List<String> prev_key_words = product_res.getValue().getKey_words();
        //step 1 edit product key words - should work (check new product category)
        Response res = marketFacade.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        check_was_not_exception("failed to edit product key words while it should work. - step 1", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        List<String> new_key_words = product_res.getValue().getKey_words();
        assertTrue(!new_key_words.equals(prev_key_words),"product key word haven't changed - step 1");
        prev_key_words = new_key_words;
        //step 2 log out -> edit product category - should fail.
        res = marketFacade.logout();
        check_was_not_exception("failed to logout user while it should work - step 2", res);
        res = marketFacade.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        assertTrue(check_was_exception(res),"succeed to edit product key words while it should fail. - step 2");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 2", product_res);
        new_key_words = product_res.getValue().getKey_words();
        assertTrue(new_key_words.equals(prev_key_words),"product key words has changed - step 2");
        //step 3 log back in change product category - should work.
        res = marketFacade.login(email,password);
        check_was_not_exception("failed to login user while it should work - step 3", res);
        res = marketFacade.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        check_was_not_exception("failed to edit product key words while it should work. - step 3", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 3", product_res);
        new_key_words = product_res.getValue().getKey_words();
        assertTrue(!new_key_words.equals(prev_key_words),"product key words haven't changed - step 3");
        prev_key_words = new_key_words;
        //step 4 try to edit product category with general user
        res = general_user.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        assertTrue(check_was_exception(res),"succeed to edit product key words while it should fail. - step 4");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 4", product_res);
        new_key_words = product_res.getValue().getKey_words();
        assertTrue(new_key_words.equals(prev_key_words),"product key words haven't changed - step 4");
        //step 5 try to edit product key words with store member.
        res = manager.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        check_was_not_exception("failed to edit product key words while it should work. - step 5", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 5", product_res);
        new_key_words = product_res.getValue().getKey_words();
        assertFalse(new_key_words.equals(prev_key_words),"product key words haven't changed - step 5");
        prev_key_words = new_key_words;
        //step 6 try to edit product key words with store member without permissions.
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>());
        res = manager.edit_product_key_words(productId, 1, gen_key_words(prev_key_words));
        assertTrue(check_was_exception(res),"succeed to edit product key words while it should fail. - step 6");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 6", product_res);
        new_key_words = product_res.getValue().getKey_words();
        assertTrue(new_key_words.equals(prev_key_words),"product key words haven't changed - step 6");
    }

    @Test
    void edit_product_key_words_sad() {
        //sad
        Response rSad = marketFacade.edit_product_key_words(productId, 100, new ArrayList<>());
        check_was_exception(rSad);
    }

    @Test
    void two_users_buying_the_same_product() {
        SupplyAdapter supply = new SupplyAdapterImpl();
        PaymentAdapter payment = new PaymentAdapterImpl();
        marketFacade.add_product_to_cart(1, productId, 30);
        MarketFacade tmpMarket = new MarketFacade(payment, supply);
        tmpMarket.register("amit1@gmail.com", password, "amit", "mosko", birth_date);
        tmpMarket.add_product_to_cart(1, productId, 50);
        Response goodR = marketFacade.buy_cart("", "");
        Response BadR = tmpMarket.buy_cart("", "");
        check_was_not_exception("Purchase done successfully", goodR);
        check_was_exception(BadR);
    }

    @Test
    void adding_the_same_user_to_manegment() {
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

    //scenario - there is num of products of specified product in store
    //there is num of threads users who tries to buy 1 item of this product at once.
    //(num of products) users should succeed and the other fails
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
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart("", "");
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

    //scenario - there is num of products of specified product in store
    //there is num of threads users who tries to buy 1 item of this product at once.
    //all users should succeed.
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
                Response res = marketFacadeList.get(num_of_market_facade).buy_cart("", "");
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
}

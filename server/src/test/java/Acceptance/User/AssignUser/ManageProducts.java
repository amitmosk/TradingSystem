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
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManageProducts {
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


    public ManageProducts() {
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
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }
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
    private List<String> gen_key_words(List<String> key_words){
        List<String> res = new ArrayList<>();
        String word = key_words.get(0)+"a";
        res.add(word);
        return res;
    }

    //------------------------------- Manage products --------------------------------------------------------------------------

    /**
     * Add products to store
     * 1. user add product to his own store - should succeed
     * 2. user logout and try to add product to his own store - should fail
     * 3. user log in back and try to add product to his store - succeed
     * 4. add product with store member - should work
     * 5. add product with store member without permissions - fail
     * 6. add product of existing product - fail
     */
    @Test
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

    @Test
    void add_product_to_store_test_sad() {
        //sad-no product
        Response rSad = marketFacade.add_product_to_store(5, 50, "apple", 100, "fruits", new ArrayList<>());
        check_was_exception(rSad);
    }

    /**
     * Delete products from store
     * 1. checks if apple belongs in store.
     * 2. removes apple from store - should work.
     * 3. add apple back to store and verify
     * 4. log out and try to remove - should fail.
     * 5. remove with non store member - should fail.
     * 6. log in back to user with store permission and remove - succeed(than add back).
     * 7. remove with store member - succeed(than add back)
     * 8. remove with store member with no permission - fail.
     * */
    @Test
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

    @Test
    void delete_product_from_store_sad() {
        //sad-no product
        Response rSad = marketFacade.delete_product_from_store(productId, 22);
        check_was_exception(rSad);
    }

    /**
     * Edit product
     * 1. edit product name - should work (check new product name)
     * 2. log out - edit product name - should fail.
     * 3. log back in change product name - should work.
     * 4. try to edit product name with general user
     * 5. try to edit product name with store member.
     * 6. try to edit product name with store member without permissions.
     * */
    @Test
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

    @Test
    void edit_product_name_sad() {
        //sad-no product
        Response rSad = marketFacade.edit_product_name(productId, 5, "orange");
        check_was_exception(rSad);
    }

    @Test
    void edit_product_price_happy() {
        Response<Product> product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        double prev_price = product_res.getValue().getOriginal_price();
        //step 1 edit product price - should work (check new product name)
        Response res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 1", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 1", product_res);
        double new_price = product_res.getValue().getOriginal_price();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 1");
        prev_price = new_price;
        //step 2 log out -> edit product price - should fail.
        res = marketFacade.logout();
        check_was_not_exception("failed to logout user while it should work - step 2", res);
        res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 2");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 2", product_res);
        new_price = product_res.getValue().getOriginal_price();
        assertFalse(new_price == prev_price+10,"product price has changed - step 2");
        //step 3 log back in change product price - should work.
        res = marketFacade.login(email,password);
        check_was_not_exception("failed to login user while it should work - step 3", res);
        res = marketFacade.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 3", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 3", product_res);
        new_price = product_res.getValue().getOriginal_price();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 3");
        prev_price = new_price;
        //step 4 try to edit product price with general user
        res = general_user.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 4");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 4", product_res);
        new_price = product_res.getValue().getOriginal_price();
        assertFalse(new_price == prev_price+10,"product price haven't changed - step 4");
        //step 5 try to edit product price with store member.
        res = manager.edit_product_price(productId, 1, prev_price+10);
        check_was_not_exception("failed to edit product price while it should work. - step 5", res);
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 5", product_res);
        new_price = product_res.getValue().getOriginal_price();
        assertTrue(new_price == prev_price+10,"product price haven't changed - step 5");
        prev_price = new_price;
        //step 6 try to edit product price with store member without permissions.
        marketFacade.edit_manager_permissions(manager_email,1,new ArrayList<>());
        res = manager.edit_product_price(productId, 1, prev_price+10);
        assertTrue(check_was_exception(res),"succeed to edit product price while it should fail. - step 6");
        product_res = marketFacade.find_product_information(productId,1);
        check_was_not_exception("failed to get product information - step 6", product_res);
        new_price = product_res.getValue().getOriginal_price();
        assertFalse(new_price == prev_price+10,"product price haven't changed - step 6");
    }

    @Test
    void edit_product_price_sad() {
        //sad
        Response rSad = marketFacade.edit_product_price(productId, 3, 90);
        check_was_exception(rSad);
    }

    @Test
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

    @Test
    void edit_product_category_sad() {
        //sad
        Response rSad = marketFacade.edit_product_category(productId, 10, "food");
        check_was_exception(rSad);
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



}
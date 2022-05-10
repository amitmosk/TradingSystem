/*
package Tests.UnitTest;

import Domain.StoreModule.Basket;
import Domain.StoreModule.Product.Product;
import Domain.StoreModule.Store.Store;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class StoreTest2 {

    private Store store;
    private final String founder_email = "mosko@gmail.com";



    @BeforeEach
    void setUp() {
        store = new Store(659,founder_email, "toysRus");
        store.appoint_founder();
    }

    @AfterEach
    void tearDown() {
    }




    @Test
    void find_products_by_name_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);

        }
        catch (Exception e)
        {
            Assert.fail("add product fail");
        }
        List<Product> answer = store.find_products_by_name("phone");
        System.out.println(answer);
        Assert.assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_name_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_name("phone");
        Assert.assertEquals(answer.size(), 0);
    }


    @Test
    void find_products_by_category_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);

        }
        catch (Exception e)
        {
            Assert.fail("add product fail");
        }
        List<Product> answer = store.find_products_by_category("electronic");
        System.out.println(answer);
        Assert.assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_category_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_category("phone");
        Assert.assertEquals(answer.size(), 0);
    }

    @Test
    void find_products_by_keywords_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);

        }
        catch (Exception e)
        {
            Assert.fail("add product fail");
        }
        List<Product> answer = store.find_products_by_key_words("fine");
        System.out.println(answer);
        Assert.assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_key_words_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_key_words("phone");
        Assert.assertEquals(answer.size(), 0);
    }

    @Test
    void fail_add_product_bad_quantity() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, -6);

        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(was_exception);
    }

    @Test
    void fail_add_product_bad_price() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try
        {
            store.add_product(founder_email, "phone", -1990.90, "electronic", keywords, 80);

        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(was_exception);
    }

    @Test
    void good_delete_product() {
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);

        }
        catch (Exception e)
        {
            Assert.fail("add product fail");
        }
        List<Product> answer = store.find_products_by_name("phone");
        System.out.println(answer.size());

        try {
            store.delete_product(1, founder_email);
        } catch (Exception e) {
            Assert.fail("delete product fail");
        }
        answer = store.find_products_by_name("phone");
        System.out.println(answer.size());
        Assert.assertEquals(answer.size(), 0);
    }




    @Test
    void edit_product_name() {
        List<String> keywords = new LinkedList<>();
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            store.edit_product_name(founder_email, 1, "moko");

        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        List<Product> answer = store.find_products_by_name("moko");
        List<Product> answer2 = store.find_products_by_name("phone");
        System.out.println(answer);
        Assert.assertEquals(answer.size(), 1);
        Assert.assertEquals(answer2.size(), 0);
    }

    @Test
    void edit_product_price() {
        List<String> keywords = new LinkedList<>();
        double price =0;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            store.edit_product_price(founder_email, 1, 600);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 1);
            price = store.check_available_products_and_calc_price(basket);


        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        Assert.assertEquals(600, price,1);
    }

    @Test
    void check_available_products_and_calc_price_ZERO_ITEM() {
        List<String> keywords = new LinkedList<>();
        double price =0;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            Basket basket = new Basket(1, "6");
            price = store.check_available_products_and_calc_price(basket);
        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        Assert.assertEquals(0, price,1);

    }

    @Test
    void check_available_products_and_calc_price_ONE_ITEM() {
        List<String> keywords = new LinkedList<>();
        double price =0;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 1);
            price = store.check_available_products_and_calc_price(basket);
        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        Assert.assertEquals(1991, price,1);

    }

    @Test
    void check_available_products_and_calc_price_THREE_ITEMS() {
        List<String> keywords = new LinkedList<>();
        double price =0 ;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 10);
            store.add_product(founder_email, "computer", 5000, "electronic", keywords, 6);
            store.add_product(founder_email, "hat", 85.50, "clothes", keywords, 60);
            Product p1 = store.getProduct_by_product_id(1);
            Product p2 = store.getProduct_by_product_id(2);
            Product p3 = store.getProduct_by_product_id(3);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p1, 3);
            basket.addProduct(p2, 2);
            basket.addProduct(p3, 1);
            price = store.check_available_products_and_calc_price(basket);
            System.out.println(price);
        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        Assert.assertEquals(16058.2, price,1);

    }

    @Test
    void fail_check_available_products_and_calc_price_noAvailableQuantity() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        double price =0;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            store.edit_product_price(founder_email, 1, 600);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 60);
            price = store.check_available_products_and_calc_price(basket);
        }
        catch (Exception e)
        {
            was_exception = true;
        }
        Assert.assertTrue("no available quantity", was_exception);

    }

    @Test
    void checkAvailablityAndGet_1_exist_item() {
        List<String> keywords = new LinkedList<>();
        Product p = null;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 10);
            p = store.checkAvailablityAndGet(1, 8);
        }
        catch (Exception e)
        {
            Assert.fail("checkAvailablityAndGet_1_exist_item");
        }
        Assert.assertEquals(p.getName(),"phone");
    }

    @Test
    void checkAvailablityAndGet_1_exist_item_noQuantity() {
        List<String> keywords = new LinkedList<>();
        Product p = null;
        boolean was_exception = false;
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 10);
            p = store.checkAvailablityAndGet(1, 12);
        }
        catch (Exception e)
        {
            was_exception = true;
        }
        Assert.assertTrue(was_exception);
    }

//    @Test
//    void remove_basket_products_from_store() {
//        boolean was_exception = false;
//        List<String> keywords = new LinkedList<>();
//        double price =0;
//        try
//        {
//            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
//            Product p = store.getProduct_by_product_id(1);
//            store.edit_product_price(founder_email, 1, 600);
//            Basket basket = new Basket(1, "15");
//            basket.addProduct(p, 6);
//            store.remove_basket_products_from_store(basket, 1);
//            store.getProduct_by_product_id(1);
//        }
//        catch (Exception e)
//        {
//            was_exception = true;
//            System.out.println(e.getMessage());
//        }
//        Assert.assertTrue("no available product", was_exception);
//    }

    @Test
    void edit_product_category() {
        List<String> keywords = new LinkedList<>();
        try
        {
            store.add_product(founder_email, "phone", 1990.90, "electronic", keywords, 6);
            store.edit_product_category(founder_email, 1, "sport");

        }
        catch (Exception e)
        {
            Assert.fail("edit product fail");
        }
        List<Product> answer = store.find_products_by_category("electronic");
        List<Product> answer2 = store.find_products_by_category("sport");
        System.out.println(answer);
        Assert.assertEquals(answer.size(), 0);
        Assert.assertEquals(answer2.size(), 1);
    }



    @Test
    void view_store_purchases_history() {
        // @TODO
    }
}
*/

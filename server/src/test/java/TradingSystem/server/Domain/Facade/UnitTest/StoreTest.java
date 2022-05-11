package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.StoreModule.StorePermission;

import static org.junit.jupiter.api.Assertions.*;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.AfterEach;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

class StoreTest {
    private String birth_date;
    private Store store;
    private final String founder_start = "founder";
    private final String general_start = "general";
    private final String owner_start = "owner";
    private final String manager_start = "manager";
    private final String ending = "@gmail.com";
    private int counter = 0;
    private AssignUser founder;
    private AssignUser general_user;
    private AssignUser general_user2;
    private AssignUser owner;
    private AssignUser manager;

    @BeforeEach
    void setUp() throws MarketException {
        this.birth_date = LocalDateTime.now().minusYears(30).toString();
        counter++;
        this.founder = generate_user(founder_start);
        this.general_user = generate_user(general_start);
        this.general_user2 = generate_user("tom");
        this.owner = generate_user(owner_start);
        this.manager = generate_user(manager_start);


        store = new Store(659, "toysRus", founder);
        store.appoint_founder();
    }

    private AssignUser generate_user(String starting) {
/*        MarketFacade marketFacade = new MarketFacade(new PaymentAdapterImpl(),new SupplyAdapterImpl());
        marketFacade.register(starting + counter + ending, "aA123456", "amit", "mosko");
        User user = marketFacade.get_user_for_tests();
        assertTrue(user.isRegistered(),"failed to initialized tests - cannot register user - "+starting);
        return user;*/
        AssignUser to_ret = null;
        try {
            User user = new User();
            user.register(starting + counter + ending, "aA123456", "gal", "brown", birth_date);
            to_ret = user.get_state_if_assigned();
        } catch (Exception e) {
            fail("failed to initialized tests - cannot register user - " + starting);
        }
        return to_ret;
    }

    @AfterEach
    void tearDown() {
    }

    // ----------------------------------------


    @Test
    void success_add_store_rating() {
        try {
            store.add_store_rating(general_user, 5);
            assertEquals(store.get_store_rating(), 5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void fail_add_store_rating_manager() {
        boolean was_exception = false;
        try {
            store.add_store_rating(founder, 5);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception, "store member cant add rating to the store");
    }

/*
    @Test
    void fail_add_store_rating_illegal_input1() {
        boolean was_exception = false;
        try {
            store.add_store_rating("founder@maial,com", -1);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        assertTrue(was_exception);
    }

    @Test
    void fail_add_store_rating_illegal_input2() {
        boolean was_exception = false;
        try {
            store.add_store_rating("founder@maial,com", 6);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception);
    }
*/


    @Test
    void success_appoint_founer() {
        try {
            store.appoint_founder();
            this.founder = store.getFounder();
            boolean has_appointment = store.has_appointment(founder);
            assertEquals(this.founder, store.getFounder(), "store has different founder");
            assertTrue(has_appointment, "founder does not have appointment");
        }catch (Exception e){
            fail(e.getMessage());
        }
    }


    @Test
    void success_close_store_permanently() {
        try {
            store.close_store_permanently();
            assertFalse(store.is_active());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void success_close_store_temporarily() {
        try {
            store.close_store_temporarily(founder);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertFalse(store.is_active());
    }

    @Test
    void fail_close_store_temporarily_noPermission() {
        boolean was_exception = false;
        try {
            store.close_store_temporarily(general_user);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception, "no permission");
    }

    @Test
    void success_open_close_store() {
        try {
            store.close_store_temporarily(founder);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        try {
            store.open_close_store(founder);
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(store.is_active());
    }

    @Test
    void fail_open_close_store() {
        boolean was_exception = false;
        try {
            store.open_close_store(founder);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception, "the close was already open");
    }

    @Test
    void add_question_AND_viewStoreQuestions() {
        try {
            store.add_question("josh@gmail.com", "why the store is always close!?");
            System.out.println(store.view_store_questions(founder));
        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(true);

    }

    @Test
    void good_answer_question() {
        try {
            store.add_question("josh@gmail.com", "why the store is always close!?");
            store.answer_question(founder, 1, "thank you for your question!");
            System.out.println(store.view_store_questions(founder));

        } catch (Exception e) {
            fail(e.getMessage());
        }
        assertTrue(true);
    }

    @Test
    void fail_answer_question_NO_PERMISSION() {
        boolean was_exception = false;
        try {
            store.add_question("josh@gmail.com", "why the store is always close!?");
            store.answer_question(general_user, 1, "thank you for your question!");
            System.out.println(store.view_store_questions(founder));
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception);
    }

    @Test
    void good_add_owner() {
        boolean was_exception = false;
        try {
            store.add_owner(founder, owner);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }

    @Test
    void fail_add_owner_already_member() {
        boolean was_exception = false;
        try {
            store.add_manager(founder, owner);
            store.add_owner(founder, owner);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "the user is already a manager in the store");
    }

    @Test
    void fail_add_manager_by_manager() {
        boolean was_exception = false;
        try {
            store.add_manager(founder, manager);
            store.add_manager(manager, manager);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "manager cant appoint another manager");
    }

    @Test
    void good_add_manager() {
        boolean was_exception = false;
        try {
            store.add_manager(founder, manager);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }

    @Test
    void good_remove_manager() {
        boolean was_exception = false;
        try {
            store.add_manager(founder, manager);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());
            store.remove_manager(founder, manager);
            System.out.println(info.get_management_information());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }

    @Test
    void good_remove_owner() {
        boolean was_exception = false;
        try {
            store.add_owner(founder, owner);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());
            store.remove_owner(founder, owner);
            System.out.println(info.get_management_information());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }

    @Test
    void fail_remove_owner_isNotAmember() {
        boolean was_exception = false;
        try {
            store.remove_owner(founder, owner);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "the asked user is not a store member");
    }

    @Test
    void fail_remove_owner_isFounder() {
        boolean was_exception = false;
        try {
            store.remove_owner(founder, founder);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "cant remove founder appointment");
    }

    @Test
    void fail_remove_owner_AppointBySomeoneElse() {
        boolean was_exception = false;
        try {
            store.add_owner(founder, general_user2);
            store.add_owner(founder, general_user);
            store.remove_owner(general_user, general_user2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "appoint by someone else");
    }


    @Test
    void success_view_store_management_information() {
        boolean was_exception = false;
        try {
            store.add_owner(founder, owner);
            store.add_owner(owner, general_user);
            store.add_manager(owner, manager);
            store.add_manager(general_user, general_user2);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.get_management_information());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }

    @Test
    void fail_view_store_management_information_NoPermission() {
        boolean was_exception = false;
        try {
            store.add_owner(founder, owner);
            store.add_owner(owner, general_user);
            store.add_manager(owner, manager);
            store.add_manager(general_user, general_user2);
            StoreManagersInfo info = store.view_store_management_information(general_user2);
            System.out.println(info.get_management_information());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "no permissions");
    }

    @Test
    void set_permissions() {
        boolean was_exception = false;
        LinkedList<StorePermission> permissions = new LinkedList<>();
        permissions.add(StorePermission.add_manager);
        permissions.add(StorePermission.add_item);
        permissions.add(StorePermission.add_owner);
        permissions.add(StorePermission.remove_item);
        try {
            store.add_owner(founder, owner);
            store.set_permissions(founder, owner, permissions);

            StoreManagersInfo info = store.view_store_management_information(founder);
            Appointment a = info.getMemberAppopintment(owner_start + counter + ending);
            assertTrue(a.has_permission(StorePermission.add_manager));
            assertTrue(a.has_permission(StorePermission.add_item));
            assertTrue(a.has_permission(StorePermission.add_owner));
            assertTrue(a.has_permission(StorePermission.remove_item));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertFalse(was_exception);
    }


    @Test
    void find_products_by_name_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);

        } catch (Exception e) {
            fail("add product fail");
        }
        List<Product> answer = store.find_products_by_name("phone");
        System.out.println(answer);
        assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_name_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_name("phone");
        assertEquals(answer.size(), 0);
    }


    @Test
    void find_products_by_category_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);

        } catch (Exception e) {
            fail("add product fail");
        }
        List<Product> answer = store.find_products_by_category("electronic");
        System.out.println(answer);
        assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_category_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_category("phone");
        assertEquals(answer.size(), 0);
    }

    @Test
    void find_products_by_keywords_ADD_Product() {
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);

        } catch (Exception e) {
            fail("add product fail");
        }
        List<Product> answer = store.find_products_by_key_words("fine");
        System.out.println(answer);
        assertEquals(answer.size(), 1);
    }

    @Test
    void find_products_by_key_words_EMPTY_LIST() {
        List<Product> answer = store.find_products_by_key_words("phone");
        assertEquals(answer.size(), 0);
    }

    @Test
    void fail_add_product_bad_quantity() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, -6);

        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception);
    }

    @Test
    void fail_add_product_bad_price() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try {
            store.add_product(founder, "phone", -1990.90, "electronic", keywords, 80);

        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        assertTrue(was_exception);
    }

    @Test
    void good_delete_product() {
        List<String> keywords = new LinkedList<>();
        keywords.add("fine");
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);

        } catch (Exception e) {
            fail("add product fail");
        }
        List<Product> answer = store.find_products_by_name("phone");
        System.out.println(answer.size());

        try {
            store.delete_product(1, founder);
        } catch (Exception e) {
            fail("delete product fail");
        }
        answer = store.find_products_by_name("phone");
        System.out.println(answer.size());
        assertEquals(answer.size(), 0);
    }


    @Test
    void edit_product_name() {
        List<String> keywords = new LinkedList<>();
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            store.edit_product_name(founder, 1, "moko");

        } catch (Exception e) {
            fail("edit product fail");
        }
        List<Product> answer = store.find_products_by_name("moko");
        List<Product> answer2 = store.find_products_by_name("phone");
        System.out.println(answer);
        assertEquals(answer.size(), 1);
        assertEquals(answer2.size(), 0);
    }

    @Test
    void edit_product_price() {
        List<String> keywords = new LinkedList<>();
        double price = 0;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            store.edit_product_price(founder, 1, 600);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 1);
            price = store.check_available_products_and_calc_price(basket);


        } catch (Exception e) {
            fail("edit product fail");
        }
        assertEquals(600, price, 1);
    }

    @Test
    void check_available_products_and_calc_price_ZERO_ITEM() {
        List<String> keywords = new LinkedList<>();
        double price = 0;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            Basket basket = new Basket(1, "6");
            price = store.check_available_products_and_calc_price(basket);
        } catch (Exception e) {
            fail("edit product fail");
        }
        assertEquals(0, price, 1);

    }

    @Test
    void check_available_products_and_calc_price_ONE_ITEM() {
        List<String> keywords = new LinkedList<>();
        double price = 0;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 1);
            price = store.check_available_products_and_calc_price(basket);
        } catch (Exception e) {
            fail("edit product fail");
        }
        assertEquals(1991, price, 1);

    }

    @Test
    void check_available_products_and_calc_price_THREE_ITEMS() {
        List<String> keywords = new LinkedList<>();
        double price = 0;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 10);
            store.add_product(founder, "computer", 5000, "electronic", keywords, 6);
            store.add_product(founder, "hat", 85.50, "clothes", keywords, 60);
            Product p1 = store.getProduct_by_product_id(1);
            Product p2 = store.getProduct_by_product_id(2);
            Product p3 = store.getProduct_by_product_id(3);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p1, 3);
            basket.addProduct(p2, 2);
            basket.addProduct(p3, 1);
            price = store.check_available_products_and_calc_price(basket);
            System.out.println(price);
        } catch (Exception e) {
            fail("edit product fail");
        }
        assertEquals(16058.2, price, 1);

    }

    @Test
    void fail_check_available_products_and_calc_price_noAvailableQuantity() {
        boolean was_exception = false;
        List<String> keywords = new LinkedList<>();
        double price = 0;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            Product p = store.getProduct_by_product_id(1);
            store.edit_product_price(founder, 1, 600);
            Basket basket = new Basket(1, "6");
            basket.addProduct(p, 60);
            price = store.check_available_products_and_calc_price(basket);
        } catch (Exception e) {
            was_exception = true;
        }
        assertTrue(was_exception, "no available quantity");

    }

    @Test
    void checkAvailablityAndGet_1_exist_item() {
        List<String> keywords = new LinkedList<>();
        Product p = null;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 10);
            p = store.checkAvailablityAndGet(1, 8);
        } catch (Exception e) {
            fail("checkAvailablityAndGet_1_exist_item");
        }
        assertEquals(p.getName(), "phone");
    }

    @Test
    void checkAvailablityAndGet_1_exist_item_noQuantity() {
        List<String> keywords = new LinkedList<>();
        Product p = null;
        boolean was_exception = false;
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 10);
            p = store.checkAvailablityAndGet(1, 12);
        } catch (Exception e) {
            was_exception = true;
        }
        assertTrue(was_exception);
    }

//    @Test
//    void remove_basket_products_from_store() {
//        boolean was_exception = false;
//        List<String> keywords = new LinkedList<>();
//        double price =0;
//        try
//        {
//            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
//            Product p = store.getProduct_by_product_id(1);
//            store.edit_product_price(founder, 1, 600);
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
//        assertTrue("no available product", was_exception);
//    }

    @Test
    void edit_product_category() {
        List<String> keywords = new LinkedList<>();
        try {
            store.add_product(founder, "phone", 1990.90, "electronic", keywords, 6);
            store.edit_product_category(founder, 1, "sport");

        } catch (Exception e) {
            fail("edit product fail");
        }
        List<Product> answer = store.find_products_by_category("electronic");
        List<Product> answer2 = store.find_products_by_category("sport");
        System.out.println(answer);
        assertEquals(answer.size(), 0);
        assertEquals(answer2.size(), 1);
    }


    @Test
    void view_store_purchases_history() {
        // @TODO
    }


}
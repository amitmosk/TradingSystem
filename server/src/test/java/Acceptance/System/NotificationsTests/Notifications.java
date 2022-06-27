package Acceptance.System.NotificationsTests;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class Notifications {
    private static MarketSystem marketSystem;
    private static MarketFacade admin_facade;
    private static MarketFacade marketFacade1;
    private static MarketFacade marketFacade2;
    private static MarketFacade marketFacade3;
    private static MarketFacade marketFacade4;
    private static MarketFacade marketFacade5;
    private static int store_id;
    private static int product_id;
    private static PaymentAdapter paymentAdapter;
    private static SupplyAdapter supplyAdapter;
    private static SupplyInfo supplyInfo = new SupplyInfo("1", "2", "3", "4", "5");
    private static PaymentInfo payment_info = new PaymentInfo("123", "456", "789", "245", "123", "455");
    private static String birth_date;


    @BeforeAll
    static void SetUp() {
        NotificationHandler.setTestsHandler();

        birth_date = LocalDate.now().minusYears(30).toString();
        List<String> keywords = new LinkedList<>();
        try
        {
            marketSystem = new MarketSystem(tests_config_file_path, "");
            paymentAdapter = marketSystem.getPayment_adapter();
            supplyAdapter = marketSystem.getSupply_adapter();

        }
        catch (Exception e){
        }
        marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        admin_facade = new MarketFacade(paymentAdapter, supplyAdapter);
        try{
            UserController.get_instance().add_admin("admin@gmail.com", "12345678aaA", "admin", "admin");
            admin_facade.login("admin@gmail.com", "12345678aaA");
            marketFacade1.register("founder@gmail.com", "12345678aaA", "founder", "founder", birth_date);
            marketFacade2.register("ownerOne@gmail.com", "12345678aaA", "ownerOne", "ownerOne", birth_date);
            marketFacade3.register("ownerTwo@gmail.com", "12345678aaA", "ownerTwo", "ownerTwo", birth_date);
            marketFacade4.register("manager@gmail.com", "12345678aaA", "manager", "manager", birth_date);
            marketFacade5.register("buyer@gmail.com", "12345678aaA", "buyer", "buyer", birth_date);
            Response<Integer> response = marketFacade1.open_store("storeForTest");
            store_id = response.getValue();
            marketFacade1.add_owner("ownerOne@gmail.com", store_id);
            marketFacade1.add_owner("ownerTwo@gmail.com", store_id);
            marketFacade2.manager_answer_appointment(store_id, true, "ownerTwo@gmail.com");
            marketFacade1.add_manager("manager@gmail.com", store_id);
            marketFacade1.add_product_to_store(store_id, 20, "ee", 19.5, "category", keywords);
            Response<List<ProductInformation>> response1 = marketFacade1.get_products_by_store_id(store_id);
            product_id = response1.getValue().get(0).getProduct_id();
            NotificationHandler.getInstance().reset_notifications();



        }
        catch (Exception e){

        }

    }

    @BeforeEach
    void reset(){
        try {
            marketFacade1.open_close_store(store_id);
        }
        catch (Exception e){}
        NotificationHandler.getInstance().reset_notifications();
    }

    // --------

    @Test
    void bid_offer_for_managers(){
        Response<Integer> response = marketFacade5.add_bid(store_id, product_id, 10, 15.5);
        int bid_id = response.getValue();
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");


        assertEquals(NotificationHandler.getInstance().get_user_notifications("founder@gmail.com").size(), 1);
        assertTrue(founder_notifications_list.get(0).contains("bid"));
        assertEquals(NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com").size(), 1);
        assertTrue(owner1_notifications_list.get(0).contains("bid"));
        assertEquals(NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com").size(), 1);
        assertTrue(owner2_notifications_list.get(0).contains("bid"));

    }


    /**
     * this test check that all the managers in the store (except the appointer) get a notification about a new candidate-manager.
     */

    @Test
    void appoint_candidate_notification_for_managers(){
        MarketFacade marketfacade6 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketfacade6.register("candidate@gmail.com", "12345678aA", "ma", "ma", "19-04-95");
        marketFacade1.add_owner("candidate@gmail.com", store_id);
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(NotificationHandler.getInstance().get_user_notifications("founder@gmail.com").size(), 0);
        assertEquals(NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com").size(), 1);
        assertTrue(owner1_notifications_list.get(0).contains("candidate"));
        assertEquals(NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com").size(), 1);
        assertTrue(owner2_notifications_list.get(0).contains("candidate"));
        assertEquals(NotificationHandler.getInstance().get_user_notifications("manager@gmail.com").size(), 1);
        assertTrue(manager_notifications_list.get(0).contains("candidate"));
    }

    /**
     * this test check that a buyer get a notification when his bid is confirmed by store managers.
     */
    @Test
    void confirm_bid_offer_for_buyer(){
        MarketFacade marketfacade6 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketfacade6.register("buyerer@gmail.com", "12345678aA", "ma", "ma", "19-04-95");
        Response<Integer> response = marketfacade6.add_bid(store_id, product_id, 2, 10);
        int bid_id = response.getValue();
        marketFacade1.manager_answer_bid(store_id, bid_id, true, -1);
        marketFacade2.manager_answer_bid(store_id, bid_id, true, -1);
        marketFacade3.manager_answer_bid(store_id, bid_id, true, -1);
        List<String> buyer_notifications_list = NotificationHandler.getInstance().get_user_notifications("buyerer@gmail.com");
        assertEquals(buyer_notifications_list.size(), 1);
        assertTrue(buyer_notifications_list.get(0).contains("bid"));
    }

    /**
     * this test check that all the store managers get notification when an admin close the store.
     */
    @Test
    void close_store_for_managers_by_Admin(){
        admin_facade.close_store_permanently(store_id);
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(founder_notifications_list.size(), 1);
        assertTrue(founder_notifications_list.get(0).contains("close"));
        assertEquals(owner1_notifications_list.size(), 1);
        assertTrue(owner1_notifications_list.get(0).contains("permanently"));
        assertEquals(owner2_notifications_list.size(), 1);
        assertTrue(owner2_notifications_list.get(0).contains("Store"));
        assertEquals(manager_notifications_list.size(), 1);

    }

    /**
     * this test check that all the store managers get notification when the founder close the store.
     */
    @Test
    void close_store_temp_by_founder_to_managers(){
        marketFacade1.close_store_temporarily(store_id);
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(founder_notifications_list.size(), 0);
        assertEquals(owner1_notifications_list.size(), 1);
        assertEquals(owner2_notifications_list.size(), 1);
        assertEquals(manager_notifications_list.size(), 1);
        assertTrue(manager_notifications_list.get(0).contains("close"));
        assertTrue(owner1_notifications_list.get(0).contains("temp"));
        assertTrue(owner2_notifications_list.get(0).contains("Store"));

    }

    /**
     * this test check that all the store managers get notification when the founder re-open the store.
     */
    @Test
    void open_close_store_by_founder_to_managers(){
        marketFacade1.close_store_temporarily(store_id);
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(founder_notifications_list.size(), 0);
        assertEquals(owner1_notifications_list.size(), 1);
        assertEquals(owner2_notifications_list.size(), 1);
        assertEquals(manager_notifications_list.size(), 1);
        assertTrue(manager_notifications_list.get(0).contains("close"));
        assertTrue(owner1_notifications_list.get(0).contains("temp"));
        assertTrue(owner2_notifications_list.get(0).contains("Store"));

        marketFacade1.open_close_store(store_id);
        assertEquals(founder_notifications_list.size(), 0);
        assertEquals(owner1_notifications_list.size(), 2);
        assertEquals(owner2_notifications_list.size(), 2);
        assertEquals(manager_notifications_list.size(), 2);
        assertTrue(owner1_notifications_list.get(1).contains("open"));

    }

    /**
     * this test check that the store managers get notification when a buyer send question to the store.
     */
    @Test
    void add_question_to_managers(){
        marketFacade5.add_product_to_cart(store_id, product_id, 1);
        marketFacade5.buy_cart(payment_info, supplyInfo);
        NotificationHandler.getInstance().reset_notifications();
        marketFacade5.send_question_to_store(store_id, "why?");

        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(founder_notifications_list.size(), 1);
        assertEquals(owner1_notifications_list.size(), 1);
        assertEquals(owner2_notifications_list.size(), 1);
        assertEquals(manager_notifications_list.size(), 1);
        assertTrue(manager_notifications_list.get(0).contains("question"));
    }

    /**
     * this test check that a buyer get notification when his question got answered by one of the store managers.
     */
    @Test
    void answer_question_for_buyer(){
        MarketFacade marketFacadeBuyer = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacadeBuyer.register("buyer123@walla.com","12345678aA", "buyer", "buyer", birth_date);
        marketFacadeBuyer.add_product_to_cart(store_id, product_id, 1);
        marketFacadeBuyer.buy_cart(payment_info, supplyInfo);
        NotificationHandler.getInstance().reset_notifications();
        marketFacadeBuyer.send_question_to_store(store_id, "why?");
        int question_id = QuestionController.getInstance().getQuestion_ids_counter()-1;

        List<String> buyer_notifications_list = NotificationHandler.getInstance().get_user_notifications("buyer123@walla.com");
        List<String> founder_notifications_list = NotificationHandler.getInstance().get_user_notifications("founder@gmail.com");
        List<String> owner1_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerOne@gmail.com");
        List<String> owner2_notifications_list = NotificationHandler.getInstance().get_user_notifications("ownerTwo@gmail.com");
        List<String> manager_notifications_list = NotificationHandler.getInstance().get_user_notifications("manager@gmail.com");

        assertEquals(buyer_notifications_list.size(), 0);
        assertEquals(founder_notifications_list.size(), 1);
        assertEquals(owner1_notifications_list.size(), 1);
        assertEquals(owner2_notifications_list.size(), 1);
        assertEquals(manager_notifications_list.size(), 1);
        assertTrue(manager_notifications_list.get(0).contains("question"));

        marketFacade1.manager_answer_question(store_id, question_id, "like");
        buyer_notifications_list = NotificationHandler.getInstance().get_user_notifications("buyer123@walla.com");
        assertEquals(buyer_notifications_list.size(), 1);
        assertTrue(buyer_notifications_list.get(0).contains("question"));


    }
}
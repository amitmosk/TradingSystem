package Acceptance.System.StatisticsTests;

import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.Statistics.Statistic;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.Mark;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsTests {
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

    private static int expected_num_of_users = 0;
    private static int expected_num_of_guests = 0;
    private static int expected_num_of_online_users = 0;
    private static int expected_num_of_owners_founders = 0;
    private static String tests_config_path = "server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\" +
            "demo_external_services.txt";


    @BeforeAll
    static void SetUp() {

        NotificationHandler.setTestsHandler();

        birth_date = LocalDate.now().minusYears(30).toString();
        List<String> keywords = new LinkedList<>();
        try
        {
            marketSystem = new MarketSystem(tests_config_path, "");
            paymentAdapter = marketSystem.getPayment_adapter();
            supplyAdapter = marketSystem.getSupply_adapter();

        }
        catch (Exception e){
        }
    }

    @BeforeEach
    void init_system(){
        expected_num_of_users = 0;
        expected_num_of_guests = 0;
        expected_num_of_online_users = 0;
        expected_num_of_owners_founders = 0;
        marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade1.clear();
        marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        admin_facade = new MarketFacade(paymentAdapter, supplyAdapter);
        try{
            UserController.get_instance().add_admin("admin@gmail.com", "12345678aaA", "admin", "admin");
        }
        catch (Exception e){
            fail("cant add admin");
        }
        expected_num_of_users++;
        expected_num_of_online_users++;
        admin_facade.login("admin@gmail.com", "12345678aaA");
        marketFacade1.register("founder@gmail.com", "12345678aaA", "founder", "founder", birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        marketFacade2.register("ownerOne@gmail.com", "12345678aaA", "ownerOne", "ownerOne", birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        marketFacade3.register("ownerTwo@gmail.com", "12345678aaA", "ownerTwo", "ownerTwo", birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        marketFacade4.register("manager@gmail.com", "12345678aaA", "manager", "manager", birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        marketFacade5.register("buyer@gmail.com", "12345678aaA", "buyer", "buyer", birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        Response<Integer> response = marketFacade1.open_store("storeForTest");
        expected_num_of_owners_founders++;
        store_id = response.getValue();
        marketFacade1.add_owner("ownerOne@gmail.com", store_id);
        expected_num_of_owners_founders++;
        marketFacade1.add_owner("ownerTwo@gmail.com", store_id);
        marketFacade2.manager_answer_appointment(store_id, true, "ownerTwo@gmail.com");
        expected_num_of_owners_founders++;
        marketFacade1.add_manager("manager@gmail.com", store_id);
    }

    // --------


    /**
     * this test check that there is a failure when a user has no permission to view market stats.
     */
    @Test
    void user_try_to_view_stats(){
        Response<Statistic> statisticResponse1 = marketFacade1.get_market_stats();
        Response<Statistic> statisticResponse2 = marketFacade2.get_market_stats();
        Response<Statistic> statisticResponse3 = marketFacade3.get_market_stats();
        Response<Statistic> statisticResponse4 = marketFacade4.get_market_stats();
        Response<Statistic> statisticResponse5 = marketFacade5.get_market_stats();
        assertTrue(statisticResponse1.WasException(), "No Permission");
        assertTrue(statisticResponse2.WasException(), "No Permission");
        assertTrue(statisticResponse3.WasException(), "No Permission");
        assertTrue(statisticResponse4.WasException(), "No Permission");
        assertTrue(statisticResponse5.WasException(), "No Permission");
    }

    @Test
    void online_users(){
        Response<Statistic> statisticResponse = admin_facade.get_market_stats();
        int online_users = statisticResponse.getValue().getNum_of_onlines();
        assertEquals(online_users, expected_num_of_online_users, "num of online users");
        marketFacade1.logout();
//        expected_num_of_online_users--;
        statisticResponse = admin_facade.get_market_stats();
        online_users = statisticResponse.getValue().getNum_of_onlines();
        assertEquals(online_users, expected_num_of_online_users, "num of online users");
        marketFacade1.login("founder@gmail.com", "12345678aaA");
        expected_num_of_online_users++;


    }

    @Test
    void num_of_users(){
        Response<Statistic> statisticResponse = admin_facade.get_market_stats();
        int num_of_users = statisticResponse.getValue().getNum_of_users();
        assertEquals(num_of_users, expected_num_of_users, "num of registered users");
        MarketFacade facade = new MarketFacade(paymentAdapter, supplyAdapter);
        facade.register("amitamit@walla.com","12345678aA","amit","amit",birth_date);
        expected_num_of_users++;
        expected_num_of_online_users++;
        statisticResponse = admin_facade.get_market_stats();
        num_of_users = statisticResponse.getValue().getNum_of_users();
        assertEquals(num_of_users, expected_num_of_users, "num of registered users");


    }

    @Test
    void num_of_founders(){
        Response<Statistic> statisticResponse = admin_facade.get_market_stats();
        int num_of_founders = statisticResponse.getValue().getOwners_or_founders();
        assertEquals(num_of_founders, expected_num_of_owners_founders, "num of registered users");
        MarketFacade marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade.register("amitamitamit@walla.com", "13245678aA", "amit", "amit", birth_date);
        marketFacade.open_store("ststa");
        expected_num_of_owners_founders++;
        expected_num_of_online_users++;
        expected_num_of_users++;
        statisticResponse = admin_facade.get_market_stats();
        num_of_founders = statisticResponse.getValue().getOwners_or_founders();
        assertEquals(num_of_founders, expected_num_of_owners_founders, "num of registered users");


    }
    @Test
    void num_of_guests(){
        Response<Statistic> statisticResponse = admin_facade.get_market_stats();
        int num_of_guests =statisticResponse.getValue().getNum_of_guests();
        assertEquals(num_of_guests ,expected_num_of_guests);
        marketFacade1.logout();
        expected_num_of_online_users--;
        expected_num_of_guests++;
        Response<Statistic> statisticResponse2 = admin_facade.get_market_stats();
        int num_of_guests1 = statisticResponse2.getValue().getNum_of_guests();
        assertEquals(num_of_guests1 ,expected_num_of_guests);
        marketFacade1.login("founder@gmail.com", "12345678aaA");
        expected_num_of_online_users++;

    }


}
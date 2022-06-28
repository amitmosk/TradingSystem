package Acceptance.System.ExternSystemsTests;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;

import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BuyCartExternalSystems {
    private static final String external_services_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\semi_external_services.txt";
    private static MarketFacade facade1;
    private static MarketFacade facade2;
    private static MarketFacade facade3;
    private static MarketFacade facade4;
    private static int store_id;
    private static int product_id;
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo paymentInfo = new PaymentInfo("123","456","789","245","123","455");
    private static PaymentAdapter paymentAdapter;
    private static SupplyAdapter supplyAdapter;

    //------------------------- Initialization --------------------------------------------------------------------------

    @BeforeAll
    static void setUp() {
        MarketFacade mf = new MarketFacade(paymentAdapter,supplyAdapter);
        mf.clear();
        List<String> keywords = new ArrayList<>();
        keywords.add("aaaa");
        try {
            MarketSystem marketSystem = new MarketSystem(external_services_path, "");
            paymentAdapter = marketSystem.getPayment_adapter();
            supplyAdapter = marketSystem.getSupply_adapter();

            facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
            facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
            facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
            facade4 = new MarketFacade(paymentAdapter, supplyAdapter);



            // users information
            String user_buyer_email = "buyer@email.com";
            String user_founder_email = "founder@email.com";
            String user_regular_email_1 = "regular1@email.com";
            String user_regular_email_2 = "regular2@email.com";
            String user_password = "pass3Chec";
            String birth_date =  LocalDate.now().minusYears(30).toString();
            String first_name = "name";
            String last_name = "last";

            facade1.register(user_founder_email, user_password, first_name, last_name,birth_date);
            facade2.register(user_buyer_email, user_password, first_name, last_name,birth_date);
            facade3.register(user_regular_email_1, user_password, first_name, last_name,birth_date);
            facade4.register(user_regular_email_2, user_password, first_name, last_name,birth_date);
            Response<Integer> response1 = facade1.open_store("nikebike");
            store_id = response1.getValue();
            facade1.add_product_to_store(store_id,20,"kafa",19.5,"hh", keywords);
            Response<List<ProductInformation>>response2 = facade1.get_products_by_store_id(store_id);
            product_id = response2.getValue().get(0).getProduct_id();

        }
        catch (Exception e){}

    }


    @Test
    /**
     * CVV 986 - return string instead of integer.
     * CVV 984 - timeout
     */
    static Stream<Arguments> bad_demo_instructions() {
        return Stream.of(
                arguments("984"),
                arguments("986")
        );
    }
    @ParameterizedTest
    @MethodSource("bad_demo_instructions")
    void buy_cart_bad_case(String cvv){
        facade2.add_product_to_cart(store_id, product_id, 1);
        paymentInfo.setCcv(cvv);
        Response response34 = facade2.buy_cart(paymentInfo, supplyInfo);
        System.out.println(response34.getMessage());
        assertEquals(response34.getMessage(), "Buy Cart Failed: Payment External Service Denied");
    }
}
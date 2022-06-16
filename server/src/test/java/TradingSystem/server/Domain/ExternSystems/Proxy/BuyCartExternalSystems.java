package TradingSystem.server.Domain.ExternSystems.Proxy;

import TradingSystem.server.Domain.ExternSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Questions.QuestionController;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.AppointmentInformation;
import TradingSystem.server.Domain.StoreModule.Product.Product;
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
    private static final String external_services_path =  "..\\server\\src\\test\\java\\TradingSystem\\server\\Service\\ConfigurationTests\\external_services\\semi_external_services.txt";
    private MarketFacade facade1;
    private MarketFacade facade2;
    private MarketFacade facade3;
    private MarketFacade facade4;

    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo paymentInfo = new PaymentInfo("123","456","789","245","123","455");
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private int products_counter;

    //------------------------- Initialization --------------------------------------------------------------------------



    public BuyCartExternalSystems() {
        try{
            MarketSystem marketSystem = new MarketSystem(external_services_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();

            this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade4 = new MarketFacade(paymentAdapter, supplyAdapter);



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
            this.products_counter = 1;



        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @BeforeEach
    void setUp() throws MarketException {
        List<String> keywords = new ArrayList<>();
        keywords.add("aaaa");

        Response<Integer> response1 = facade1.open_store("adidas");
        int store_id = response1.getValue();
        facade1.add_product_to_store(store_id,20,"kafa",19.5,"hh", keywords);
        facade2.add_product_to_cart(store_id, this.products_counter++, 1);




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
        paymentInfo.setCcv(cvv);
        Response response34 = facade2.buy_cart(paymentInfo, supplyInfo);
        System.out.println(response34.getMessage());
        assertEquals(response34.getMessage(), "Buy Cart Failed: External Service Denied, Status -2");

    }
}
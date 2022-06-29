//package Acceptance.System.DatabaseTests;
//
//import TradingSystem.server.DAL.HibernateUtils;
//import TradingSystem.server.Domain.ExternalSystems.ExternPaymentSystemProxy;
//import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
//import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
//import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
//import TradingSystem.server.Domain.Facade.MarketFacade;
//import TradingSystem.server.Domain.Utils.Exception.DatabaseConnectionException;
//import TradingSystem.server.Service.MarketSystem;
//import TradingSystem.server.Service.NotificationHandler;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.time.LocalDate;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.stream.Stream;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.params.provider.Arguments.arguments;
//
//class ConnectionTests {
//
//    private static MarketFacade marketFacade;
//    private static MarketSystem marketSystem;
//    private static PaymentAdapter paymentAdapter;
//    private static SupplyAdapter supplyAdapter;
//    private static String path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\real_external_services.txt";
//
//    @BeforeAll
//    static void SetUp() {
//
//        try
//        {
//            marketSystem = new MarketSystem(path, "");
//            paymentAdapter = marketSystem.getPayment_adapter();
//            supplyAdapter = marketSystem.getSupply_adapter();
//
//        }
//        catch (Exception e){
//        }
//    }
//
//    /**
//     * for this test you should open your DB connection.
//     */
//    @Test
//    void open_connection(){
//        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
//        try
//        {
//            marketFacade.check_database_connection();
//        }
//        catch (DatabaseConnectionException e){
//            fail();
//        }
//    }
//
//    /**
//     * for this test you should close your DB connection.
//     */
//    @Test
//    void on_close_connection(){
//        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
//        boolean was_exception = false;
//        try
//        {
//            marketFacade.check_database_connection();
//        }
//        catch (DatabaseConnectionException e){
//            was_exception = true;
//        }
//        assertTrue(was_exception);
//    }
//
//
//}
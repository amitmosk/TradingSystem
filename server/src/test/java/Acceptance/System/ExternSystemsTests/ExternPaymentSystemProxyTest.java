package Acceptance.System.ExternSystemsTests;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.UserModule.CartInformation;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExternPaymentSystemProxyTest {

    private ExternPaymentSystemProxy externPaymentSystemProxy;


    @Test
    void handshake(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        boolean answer = this.externPaymentSystemProxy.handshake();
        assertTrue(answer);

    }

    @Test
    void payment_success(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","585","455");
        int answer3 = this.externPaymentSystemProxy.payment(500, payment_info);
        assertTrue(answer3-10000 > 0);
        assertTrue(answer3-100000 < 0);
    }

    @Test
    void payment_fail1(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","984","455");
        int answer3 = this.externPaymentSystemProxy.payment(500, payment_info);
        assertEquals(answer3, -2);
    }

    @Test
    void payment_fail2(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","986","455");
        int answer3 = this.externPaymentSystemProxy.payment(500, payment_info);
        assertEquals(answer3, -2);

    }

    static Stream<Arguments> cancel_payment_bad_provider() {
        return Stream.of(
                arguments(9999),
                arguments(1),
                arguments(1000),
                arguments(5000),
                arguments(6000),
                arguments(100001)
        );
    }


    @ParameterizedTest
    @MethodSource("cancel_payment_bad_provider")
    void cancel_payment_bad_input(int transaction_id){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(transaction_id);
        assertEquals(answer, 1);
    }


    static Stream<Arguments> cancel_payment_good_provider() {
        return Stream.of(
                arguments(10000),
                arguments(50000),
                arguments(555555),
                arguments(456789),
                arguments(600500),
                arguments(1000000)
        );
    }


    @ParameterizedTest
    @MethodSource("cancel_payment_good_provider")
    void cancel_payment_good_input(int transaction_id){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(transaction_id);
        assertEquals(answer, 1);
    }


//    /**
//     * this test check rollback of buy cart while an error occurred at the payment process - cvv = 986
//     */
//    @Test
//    void buy_cart_rollback_bad_payment_info_1()
//    {
//        String birth_date = LocalDate.now().minusYears(22).toString();
//        PaymentAdapter paymentInfo = new PaymentAdapterImpl();
//        SupplyAdapter supplyInfo = new SupplyAdapterImpl();
//        MarketFacade store_manager = new MarketFacade(paymentInfo, supplyInfo);
//        store_manager.register("store_manager@walla.com", "12345678aA", "Store", "Manager", birth_date);
//        store_manager.login("store_manager@walla.com", "12345678aA");
//        //Open store
//        int store_id = store_manager.open_store("Store1").getValue();
//        //Add product to store
//        Map<Product, Integer> inventory = store_manager.add_product_to_store(store_id, 20, "Iphone", 2000, "Electronic", new ArrayList<>()).getValue();
//        int product_id = -1;
//        for(Product p : inventory.keySet())
//        {
//            product_id = p.getProduct_id();
//        }
//        MarketFacade buyer = new MarketFacade(paymentInfo, supplyInfo);
//        //Add product to cart
//        buyer.add_product_to_cart(store_id , product_id, 1);
//        CartInformation old_cart =  buyer.view_user_cart().getValue();
//        double total_price_before_purchase = old_cart.getPrice();
//
//        //Bad Payment Info - cvv = 986
//        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","986","455");
//        SupplyInfo supply_info = new SupplyInfo("name", "address", "city", "country", "zip");
//        Response buy_cart_res = buyer.buy_cart(payment_info, supply_info);
//        assertTrue(buy_cart_res.iswas_exception());
//
//        CartInformation new_cart =  buyer.view_user_cart().getValue();
//        double total_price_after_purchase = new_cart.getPrice();
//
//        //Compare cart price
//        assertEquals(total_price_before_purchase, total_price_after_purchase);
//        //Compare cart amount of products
//        assertEquals(new_cart.getProducts().size(), old_cart.getProducts().size());
//
//        // Compare all cart products
//        List<ProductInformation> old_cart_products = old_cart.getProducts();
//        List<ProductInformation> new_cart_products = new_cart.getProducts();
//        for (int i=0;i<old_cart_products.size();i++)
//        {
//            ProductInformation new_cart_p = new_cart_products.get(i);
//            ProductInformation old_cart_p = old_cart_products.get(i);
//            //Compare each product details
//            assertNotEquals(new_cart_p, null);
//            assertEquals(new_cart_p.getPrice(), old_cart_p.getPrice());
//            assertEquals(new_cart_p.getCategory(), old_cart_p.getCategory());
//            assertEquals(new_cart_p.getName(), old_cart_p.getName());
//            assertEquals(new_cart_p.getKey_words().size(), new_cart_p.getKey_words().size());
//            assertEquals(new_cart_p.getQuantity(), old_cart_p.getQuantity());
//
//        }
//
//    }
//
//
//    /**
//     * this test check rollback of buy cart while an error occurred at the payment process - cvv = 984
//     */
//    @Test
//    void buy_cart_rollback_bad_payment_info_2()
//    {
//        String birth_date = LocalDate.now().minusYears(22).toString();
//        PaymentAdapter paymentInfo = new PaymentAdapterImpl();
//        SupplyAdapter supplyInfo = new SupplyAdapterImpl();
//        MarketFacade store_manager = new MarketFacade(paymentInfo, supplyInfo);
//        store_manager.register("store_manager@walla.com", "12345678aA", "Store", "Manager", birth_date);
//        store_manager.login("store_manager@walla.com", "12345678aA");
//        //Open store
//        int store_id = store_manager.open_store("Store1").getValue();
//        //Add product to store
//        Map<Product, Integer> inventory = store_manager.add_product_to_store(store_id, 20, "Iphone", 2000, "Electronic", new ArrayList<>()).getValue();
//        int product_id = -1;
//        for(Product p : inventory.keySet())
//        {
//            product_id = p.getProduct_id();
//        }
//        MarketFacade buyer = new MarketFacade(paymentInfo, supplyInfo);
//        //Add product to cart
//        buyer.add_product_to_cart(store_id , product_id, 1);
//        CartInformation old_cart =  buyer.view_user_cart().getValue();
//        double total_price_before_purchase = old_cart.getPrice();
//
//        //Bad Payment Info - cvv = 984
//        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","984","455");
//        SupplyInfo supply_info = new SupplyInfo("name", "address", "city", "country", "zip");
//        Response buy_cart_res = buyer.buy_cart(payment_info, supply_info);
//        assertTrue(buy_cart_res.iswas_exception());
//
//        CartInformation new_cart =  buyer.view_user_cart().getValue();
//        double total_price_after_purchase = new_cart.getPrice();
//
//        //Compare cart price
//        assertEquals(total_price_before_purchase, total_price_after_purchase);
//        //Compare cart amount of products
//        assertEquals(new_cart.getProducts().size(), old_cart.getProducts().size());
//
//        // Compare all cart products
//        List<ProductInformation> old_cart_products = old_cart.getProducts();
//        List<ProductInformation> new_cart_products = new_cart.getProducts();
//        for (int i=0;i<old_cart_products.size();i++)
//        {
//            ProductInformation new_cart_p = new_cart_products.get(i);
//            ProductInformation old_cart_p = old_cart_products.get(i);
//            //Compare each product details
//            assertNotEquals(new_cart_p, null);
//            assertEquals(new_cart_p.getPrice(), old_cart_p.getPrice());
//            assertEquals(new_cart_p.getCategory(), old_cart_p.getCategory());
//            assertEquals(new_cart_p.getName(), old_cart_p.getName());
//            assertEquals(new_cart_p.getKey_words().size(), new_cart_p.getKey_words().size());
//            assertEquals(new_cart_p.getQuantity(), old_cart_p.getQuantity());
//
//        }
//
//    }
}
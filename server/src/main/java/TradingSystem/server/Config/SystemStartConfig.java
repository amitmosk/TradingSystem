package TradingSystem.server.Config;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.Facade.MarketFacade;

import java.time.LocalDate;
import java.util.LinkedList;

public class SystemStartConfig {
    public static void init_data_to_market(PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        String birth_date = LocalDate.now().minusYears(22).toString();
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        // register
        marketFacade1.register("amit@gmail.com","12345678aA","amit","moskovitz",birth_date);
        marketFacade2.register("tom@gmail.com","12345678aA","tom","nisim",birth_date);
        marketFacade3.register("gal@gmail.com","12345678aA","gal","moskovitz",birth_date);
        marketFacade4.register("grumet@gmail.com","12345678aA","grumet","moskovitz",birth_date);
        marketFacade5.register("eylon@gmail.com","12345678aA","eylon","moskovitz",birth_date);
        // open store
        marketFacade1.open_store("amit store");
        marketFacade2.open_store("tom store");
        marketFacade3.open_store("gal store");
        marketFacade4.open_store("grumet store");
        marketFacade5.open_store("eylon store");
        // add products to stores
        marketFacade1.add_product_to_store(1,50,"iphoneA",2999.9, "electronic",new LinkedList<>());
        marketFacade2.add_product_to_store(2,50,"iphoneT",2999.9, "electronic",new LinkedList<>());
        marketFacade3.add_product_to_store(3,50,"iphoneG",2999.9, "electronic",new LinkedList<>());
        marketFacade4.add_product_to_store(4,50,"iphoneGR",2999.9, "electronic",new LinkedList<>());
        marketFacade5.add_product_to_store(5,50,"iphoneE",2999.9, "electronic",new LinkedList<>());

        marketFacade5.add_product_to_store(5,50,"iphone2E",2999.9, "electronic",new LinkedList<>());
        // add products to cart
        marketFacade1.add_product_to_cart(1,1,20);
        marketFacade1.add_product_to_cart(2,2,20);
        marketFacade1.add_product_to_cart(3,3,20);
        marketFacade1.add_product_to_cart(4,4,20);
        marketFacade1.add_product_to_cart(5,5,20);
        marketFacade1.add_product_to_cart(5,6,20);

        // buy from store
        marketFacade1.buy_cart("Payment Info", "Supply Info");

        //enter more products to cart after purchase
        marketFacade1.add_product_to_cart(1,1,20);
        marketFacade1.add_product_to_cart(2,2,20);
        marketFacade1.add_product_to_cart(3,3,20);
        marketFacade1.add_product_to_cart(4,4,20);

        // logout
        marketFacade1.logout();
        marketFacade2.logout();
        marketFacade3.logout();
        marketFacade4.logout();
        marketFacade5.logout();
    }
}
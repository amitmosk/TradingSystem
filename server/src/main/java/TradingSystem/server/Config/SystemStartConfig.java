package TradingSystem.server.Config;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.Facade.MarketFacade;

import java.util.LinkedList;

public class SystemStartConfig {
    public static void init_data_to_market(PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        MarketFacade marketFacade5 = new MarketFacade(paymentAdapter, supplyAdapter);
        // register
        marketFacade1.register("amit@gmail.com","12345678aA","amit","moskovitz","19-04-95");
        marketFacade2.register("tom@gmail.com","12345678aA","amit","moskovitz","19-04-95");
        marketFacade3.register("gal@gmail.com","12345678aA","amit","moskovitz","19-04-95");
        marketFacade4.register("grumet@gmail.com","12345678aA","amit","moskovitz","19-04-95");
        marketFacade5.register("eylon@gmail.com","12345678aA","amit","moskovitz","19-04-95");
        // open store
        marketFacade1.open_store("amit store");
        marketFacade2.open_store("tom store");
        marketFacade3.open_store("gal store");
        marketFacade4.open_store("grumet store");
        marketFacade5.open_store("eylon store");
        // add products to stores
        marketFacade1.add_product_to_store(1,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade2.add_product_to_store(2,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade3.add_product_to_store(3,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade4.add_product_to_store(4,50,"iphone",2999.9, "electronic",new LinkedList<>());
        marketFacade5.add_product_to_store(5,50,"iphoneS",2999.9, "electronic",new LinkedList<>());

        marketFacade5.add_product_to_store(5,50,"iphone",2999.9, "electronic",new LinkedList<>());
        // add products to cart
        marketFacade1.add_product_to_cart(1,1,20);
        marketFacade1.add_product_to_cart(2,2,20);
        marketFacade1.add_product_to_cart(3,1,20);
        marketFacade1.add_product_to_cart(4,1,20);
        marketFacade1.add_product_to_cart(5,1,20);
        marketFacade1.add_product_to_cart(5,2,20);

        // buy from store
        marketFacade1.buy_cart("Payment Info", "Supply Info");
        // logout
        marketFacade1.logout();
        marketFacade2.logout();
        marketFacade3.logout();
        marketFacade4.logout();
        marketFacade5.logout();
    }
}

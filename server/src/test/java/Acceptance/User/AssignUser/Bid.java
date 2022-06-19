package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.UserModule.CartInformation;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class Bid {

    private final int num_of_products = 50;
    private final int price = 100;
    private int productId;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private MarketFacade marketFacade = new MarketFacade(paymentAdapter,supplyAdapter);
    private MarketFacade manager = new MarketFacade(paymentAdapter,supplyAdapter);
    private MarketFacade general_user = new MarketFacade(paymentAdapter,supplyAdapter);
    private String birth_date;
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");

    @BeforeEach
    void SetUp() {
        NotificationHandler.setTestsHandler();

        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade.clear();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        manager = new MarketFacade(paymentAdapter,supplyAdapter);
        general_user = new MarketFacade(paymentAdapter,supplyAdapter);
        marketFacade.register(email, password, name, last_name, birth_date);
        manager.register(manager_email,password,name,last_name,birth_date);
        general_user.register("general@gmail.com",password,name,last_name,birth_date);
        marketFacade.open_store("amit store");
        productId = add_product();
        marketFacade.add_manager(manager_email,1);
    }

    private int add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response<Map<Product,Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
        return r.getValue().keySet().stream().findAny().get().getProduct_id();
    }

    private boolean check_was_exception(Response response) {
        return response.WasException();
    }

    @Test
    void bid_without_nego_guest(){
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        Response<Integer> response = marketFacade1.add_bid(1,1,3,80);
        int bid_id = response.getValue();
        marketFacade.manager_answer_bid(1, bid_id,true,-1);
        CartInformation cartInformation = marketFacade1.view_user_cart().getValue();
        assertTrue(cartInformation.getPrice() == 240.0);

    }

    /**
     * this test check adding bid by buyer, confirm by all the store managers without negotiation
     */
    @Test
    void bid_without_nego_assign_user(){
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade1.register("assign_user@gmail.com","12345678aA", "assign", "user", "15.01.95");
        Response<Integer> response = marketFacade1.add_bid(1,1,3,80);
        int bid_id = response.getValue();
        marketFacade.manager_answer_bid(1,bid_id,true,-1);
        CartInformation cartInformation = marketFacade1.view_user_cart().getValue();
        assertTrue(cartInformation.getPrice() == 240.0);

    }


    @Test
    void bid_uncofirm(){
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade1.register("assign_user@gmail.com","12345678aA", "assign", "user", "15.01.95");
        Response<Integer> response = marketFacade1.add_bid(1,1,3,80);
        int bid_id = response.getValue();
        marketFacade.manager_answer_bid(1,bid_id,false,-1);
        CartInformation cartInformation = marketFacade1.view_user_cart().getValue();
        assertTrue(cartInformation.getPrice() == 0);

    }

    /**
     * this test check adding bid by buyer, confirm by all the store managers with negotiation from the store founder
     */
    @Test
    void bid_with_nego_assign_user(){
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade1.register("assign_user@gmail.com","12345678aA", "assign", "user", "15.01.95");
        Response<Integer> response = marketFacade1.add_bid(1,1,3,80);
        int bid_id = response.getValue();
        marketFacade.manager_answer_bid(1,bid_id,true,85);
        CartInformation cartInformation = marketFacade1.view_user_cart().getValue();
        assertTrue(cartInformation.getPrice() == 255.0);

    }

    /**
     * this test check adding bid by buyer, confirm by all the store managers with negotiation from the store manager - no permission
     */
    @Test
    void bid_with_nego_assign_user_no_permission(){
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade1.register("assign_user@gmail.com","12345678aA", "assign", "user", "15.01.95");
        Response<Integer> response1 = marketFacade1.add_bid(1,1,3,80);
        int bid_id = response1.getValue();
        marketFacade.manager_answer_bid(1,bid_id,true,-1);
        Response response = manager.manager_answer_bid(1,1,true,50);
        assertTrue(check_was_exception(response));

    }

}
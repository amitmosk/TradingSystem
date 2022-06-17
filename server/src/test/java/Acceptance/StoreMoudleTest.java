package Acceptance;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;

import TradingSystem.server.Domain.UserModule.CartInformation;
import TradingSystem.server.Domain.Utils.Exception.AppointmentException;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StoreMoudleTest {
    private final int num_of_threads = 100;
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

    //------------------------------- Helper functions --------------------------------------------------------------------------

    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }

    private boolean check_was_exception(Response response) {
        return response.WasException();
    }

    private int add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response<Map<Product,Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
        return r.getValue().keySet().stream().findAny().get().getProduct_id();
    }

    private void buy_product() {
        marketFacade.add_product_to_cart(1, productId, 20);
        Response res = marketFacade.buy_cart(payment_info, supplyInfo);
    }

    private boolean check_if_product_exists_inventory(Map<Product,Integer> products, String product_name){
        for(Product p : products.keySet()){
            if(p.getName().equals(product_name))
                return true;
        }
        return false;
    }

    private boolean check_if_product_exists_find(String product_name){
        List<Product> products = marketFacade.find_products_by_category("fruits").getValue();
        for(Product p : products){
            if(p.getName().equals(product_name))
                return true;
        }
        return false;
    }

    // helper function which starts all threads
    private void start_threads(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        } // running all the threads parallel
    }

    // helper function which join all threads
    private void join_threads(List<Thread> threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (Exception e) {
            Assertions.fail( "there was error while running the threads");
        }
    }

    // helper function which creates num_of_threads users represented by market facades
    private List<MarketFacade> createUsers(String starting) {
        String ending = "@gmail.com";
        List<MarketFacade> facades = new ArrayList<>();
        for (int i = 0; i < num_of_threads; i++) {
            String email = starting + i + ending;
            MarketFacade mf = new MarketFacade(paymentAdapter, supplyAdapter);
            Response res = mf.register(email, password, "gal", "brown", birth_date);
            check_was_not_exception("failed to register user for testing", res);
            facades.add(mf);
        }
        return facades;
    }

    private List<String> gen_key_words(List<String> key_words){
        List<String> res = new ArrayList<>();
        String word = key_words.get(0)+"a";
        res.add(word);
        return res;
    }

    //------------------------- Initialization --------------------------------------------------------------------------

    public StoreMoudleTest() {
        try{
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

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
        add_product();
        marketFacade.add_manager(manager_email,1);
    }

    //------------------------------- Testing functions --------------------------------------------------------------------------








}


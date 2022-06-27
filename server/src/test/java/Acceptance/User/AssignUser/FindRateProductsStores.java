package Acceptance.User.AssignUser;


import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FindRateProductsStores {
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
    private MarketFacade marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    private MarketFacade manager = new MarketFacade(paymentAdapter, supplyAdapter);
    private MarketFacade general_user = new MarketFacade(paymentAdapter, supplyAdapter);
    private String birth_date;
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");

    public FindRateProductsStores() {

//        HibernateUtils.clear_db();
//        MarketFacade marketFacade1243 = new MarketFacade(paymentAdapter, supplyAdapter);
//        marketFacade1243.clear();
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
        manager = new MarketFacade(paymentAdapter, supplyAdapter);
        general_user = new MarketFacade(paymentAdapter, supplyAdapter);
        marketFacade.register(email, password, name, last_name, birth_date);
        manager.register(manager_email, password, name, last_name, birth_date);
        general_user.register("general@gmail.com", password, name, last_name, birth_date);
        marketFacade.open_store("amit store");
        add_product();
        marketFacade.add_manager(manager_email, 1);
    }

    private int add_product() {
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("fruits");
        Response<Map<Product, Integer>> r = marketFacade.add_product_to_store(1, num_of_products, "apple", price, "fruits", arraylist);
        return r.getValue().keySet().stream().findAny().get().getProduct_id();
    }
    private void buy_product() {
        marketFacade.add_product_to_cart(1, productId, 20);
        Response res = marketFacade.buy_cart(payment_info, supplyInfo);
    }
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }


    //------------------------------- Find store \ product information --------------------------------------------------------------------------

    @Test
    void find_store_information_happy() {
        Response<StoreInformation> res = marketFacade.find_store_information(1);
        check_was_not_exception("Store information received successfully", res);
        StoreInformation store = res.getValue();
        assertEquals(email,store.getFounder_email());
        assertEquals("amit store",store.getName());
    }

    @Test
    void find_store_information_sad() {
        Response rSad = marketFacade.find_store_information(100);
        check_was_exception(rSad);
    }

    @Test
    void find_product_information_happy() {
        Response<Product> product_information = marketFacade.find_product_information(productId, 1);
        check_was_not_exception("Product information received successfully", product_information);
        assertEquals(product_information.getValue().getName(),"apple","couldn't find properly product name");
        assertEquals(product_information.getValue().getCategory(),"fruits","couldn't find properly product category");
    }

    @Test
    void find_product_information_sad() {
        //sad
        Response rSad = marketFacade.find_product_information(productId, 2);
        check_was_exception(rSad);
    }

    @Test
    void find_products_by_name_happy() {
        Response<List<Product>> response = marketFacade.find_products_by_name("apple");
        check_was_not_exception("Product list received successfully", response);
        assertEquals(response.getValue().stream().findAny().get().getName(),"apple","couldn't find existing product");
    }

    @Test
    void find_products_by_category_happy() {
        //happy
        Response r = marketFacade.find_products_by_category("fruits");
        check_was_not_exception("Products received successfully", r);

    }

    @Test
    void find_products_by_keywords_happy() {
        //happy
        Response r = marketFacade.find_products_by_keywords("fruits");
        check_was_not_exception("Products received successfully", r);
    }


    //---------------------------------------------- Rating and reviews --------------------------------------------------------------------------

    @Test
    void rate_store_sad() {
        //sad
        Response rSAd = marketFacade.rate_store(1, 5);
        check_was_exception(rSAd);
    }

    @Test
    void add_product_review_happy() {
        //happy
        buy_product();
        Response r = marketFacade.add_product_review(productId, 1, "great product");
        check_was_not_exception("Review added successfully", r);
    }

    @Test
    void add_product_review_sad() {
        //sad
        Response rSad = marketFacade.add_product_review(productId, 2, "great product");
        check_was_exception(rSad);
    }

    @Test
    void rate_product_happy() {
        //happy
        buy_product();
        Response r = marketFacade.rate_product(productId, 1, 5);
        check_was_not_exception("Rating added successfully to the product", r);
    }

    @Test
    void rate_product_sad() {
        //sad
        Response rSad = marketFacade.rate_product(productId, 10, 5);
        check_was_exception(rSad);
    }

    @Test
    void rate_my_store() {
        //happy
        buy_product();
        Response r = marketFacade.rate_store(1, 5);
        check_was_exception(r);
    }



}
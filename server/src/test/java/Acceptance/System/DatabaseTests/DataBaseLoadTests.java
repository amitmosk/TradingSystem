package Acceptance.System.DatabaseTests;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment.Appointment;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.UserModule.*;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DataBaseLoadTests {
    private EntityManager em ;
    private SupplyAdapter supplyAdapter;
    private PaymentAdapter paymentAdapter;
    private MarketFacade marketFacade;
    private String birth_date;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";

    public DataBaseLoadTests() {
        try {
            NotificationHandler.setTestsHandler();
            HibernateUtils.set_load_tests_mode();
            this.paymentAdapter = new PaymentAdapterImpl();
//            this.paymentAdapter.handshake();
            this.supplyAdapter = new SupplyAdapterImpl();
//            this.supplyAdapter.handshake();
            this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
            this.marketFacade.clear();
            this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
            this.birth_date = LocalDate.now().minusYears(30).toString();
            em = HibernateUtils.getEntityManager();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        HibernateUtils.set_tests_mode();
    }

    @AfterEach
    public void clean() {
        em = null;
        HibernateUtils.closeEntityManager();
        em = HibernateUtils.getEntityManager();
    }



    @BeforeEach
    public void SetUp() {
        marketFacade.clear();
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    }



    @Test
    void user_creation() throws MarketException {
        Response<UserInformation> res = marketFacade.register(email, password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        UserInformation user = res.getValue();
        User data_user = HibernateUtils.getEntityManager().find(User.class, user.getId());
        assertEquals(data_user.getBirth_date(), birth_date, "failed to load properly birth_date");
        assertEquals(data_user.user_email(), email, "failed to load properly email");
        assertEquals(data_user.user_name(), name, "failed to load properly name");
        assertEquals(data_user.user_last_name(), last_name, "failed to load properly last_name");
    }
//
    @Test
    void store_creation() {
        Response<UserInformation> res = marketFacade.register(email, password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store1");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Store store = HibernateUtils.getEntityManager().find(Store.class, store_id);
        assertEquals(store.getFounder().get_user_email(), email, "failed to load store founder");
        assertEquals(store.getName(), "store1", "failed to load store name");

    }

    @Test
    void product_creation() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
    }

//    @Test
//    void product_creation_sad() {
//        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
//        assertFalse(res.WasException(), "error while trying to register valid user");
//        Response<Integer> open_store_res = marketFacade.open_store("store2");
//        assertFalse(open_store_res.WasException(), "error while trying to open new store");
//        int store_id = open_store_res.getValue();
//        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
//        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
//        assertFalse(res.WasException(), "error while trying to add valid product to store");
//        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
//        assertEquals(null, product);
//    }


    /**
     * register user opening store adding product and buying it
     * check:if the purchase id from the db is correct
     */
    @Test
    void buy_cart() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        marketFacade.add_product_to_cart(store_id, entry.getKey().getProduct_id(), 10);
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");
        SupplyInfo supply_info = new SupplyInfo("name", "address", "city", "country", "zip");
        Response<UserPurchase> res_userPurchase = marketFacade.buy_cart(payment_info, supply_info);
        UserPurchase userPurchas = res_userPurchase.getValue();
        Purchase purchase = userPurchas.getStore_id_purchase().get(store_id);
        Purchase load = HibernateUtils.getEntityManager().find(Purchase.class, purchase.getPurchase_id());
        assertEquals(purchase.getPurchase_id(), load.getPurchase_id());
    }

    /**
     * register user opening store adding product and buying it
     * check:if the db returning null if the id is wrong
     */
    @Test
    //wrong purchase id
    void buy_cart_sad() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        marketFacade.add_product_to_cart(store_id, entry.getKey().getProduct_id(), 10);
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
        Response<UserPurchase> res_userPurchase = marketFacade.buy_cart(new PaymentInfo(), new SupplyInfo());
        UserPurchase userPurchas = res_userPurchase.getValue();
        Purchase purchase = userPurchas.getStore_id_purchase().get(store_id);
//        Purchase load = HibernateUtils.getEntityManager().find(Purchase.class, new Long(-1));
//        assertEquals(null, load);
    }

    /**
     * register user opening store adding product and buying it
     * check:if the purchase id from the db is correct
     */
    @Test
    void buy_cart_inventory_check() throws MarketException {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        marketFacade.add_product_to_cart(store_id, entry.getKey().getProduct_id(), 10);
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
        Response<UserPurchase> res_userPurchase = marketFacade.buy_cart(new PaymentInfo(), new SupplyInfo());
        UserPurchase userPurchas = res_userPurchase.getValue();
        Purchase purchase = userPurchas.getStore_id_purchase().get(store_id);
        Purchase load = HibernateUtils.getEntityManager().find(Purchase.class, purchase.getPurchase_id());
        assertEquals(purchase.getPurchase_id(), load.getPurchase_id());
        Map<Product, Integer> inventory = marketFacade.get_store(store_id).getInventory();
        int inventory_for_product_banana = inventory.get(entry.getKey());
        assertEquals(inventory_for_product_banana, 40);
    }

    @Test
    public void security_upgrade() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Security security = HibernateUtils.getEntityManager().find(Security.class, new Long(1));
        marketFacade.improve_security(password, "where", "yes");
        Security improve_security = HibernateUtils.getEntityManager().find(PremiumSecurity.class, new Long(2));
        assertNull(HibernateUtils.getEntityManager().find(Security.class, new Long(1)));
        assertNotNull(improve_security);
    }

    @Test
    void remove_product() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Product product = HibernateUtils.getEntityManager().find(Product.class, 1);
        assertNull(product, "found a product on uninitialized db test.");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        assertNotNull(product, "failed to add product to system database properly.");
        marketFacade.add_product_to_cart(store_id, entry.getKey().getProduct_id(), 10);
        //starting remove product
        marketFacade.delete_product_from_store(product.getProduct_id(), store_id);
        product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        assertNull(product, "failed to remove product from data_base.");
    }

    @Test
    void remove_owner() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        res = marketFacade1.register("owner@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Appointment appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "found a Appointment on uninitialized db test.");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "found appointment when database should be empty");
        Response<String> owner_res = marketFacade.add_owner("owner@gmail.com", store_id);
        assertFalse(res.WasException(), "error while trying to add new owner to store");
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNotNull(appointment, "failed to add product to system database properly.");
        //starting remove product
        marketFacade.delete_owner("owner@gmail.com", store_id);
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "failed to remove owner appointment from data_base.");
    }
    static Stream<Arguments> cvv_fail() {
        return Stream.of(
                arguments("984"),
                arguments("986")
        );
    }
    /**
     * this test check rollback of buy cart while an error occurred at the payment process - cvv = 986
     */
    @ParameterizedTest
    @MethodSource("cvv_fail")
    void buy_cart_rollback_bad_payment_info_1(String cvv)
    {
        String birth_date = LocalDate.now().minusYears(22).toString();
        MarketFacade store_manager = new MarketFacade(new PaymentAdapterImpl(), new SupplyAdapterImpl());
        store_manager.register("store_manager@walla.com", "12345678aA", "Store", "Manager", birth_date);
        store_manager.login("store_manager@walla.com", "12345678aA");
        //Open store
        int store_id = store_manager.open_store("Store1").getValue();
        //Add product to store
        Map<Product, Integer> inventory = store_manager.add_product_to_store(store_id, 20, "Iphone", 2000, "Electronic", new ArrayList<>()).getValue();
        int product_id = -1;
        for(Product p : inventory.keySet())
        {
            product_id = p.getProduct_id();
        }
        MarketFacade buyer = new MarketFacade(new PaymentAdapterImpl(),new SupplyAdapterImpl());
        //Add product to cart
        buyer.add_product_to_cart(store_id , product_id, 1);
        CartInformation old_cart =  buyer.view_user_cart().getValue();
        double total_price_before_purchase = old_cart.getPrice();
        Response<StoreInformation> store_res_before = store_manager.find_store_information(store_id);
        List<ProductInformation> inventory_before_purchase = store_res_before.getValue().getInventory();
        //Bad Payment Info - cvv = 986
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245",cvv,"455");
        SupplyInfo supply_info = new SupplyInfo("name", "address", "city", "country", "zip");
        buyer.setRollback_flag(true);
        Response buy_cart_res = buyer.buy_cart(payment_info, supply_info);
        buyer.setRollback_flag(false);
        assertTrue(buy_cart_res.iswas_exception());

        CartInformation new_cart =  buyer.view_user_cart().getValue();
        double total_price_after_purchase = new_cart.getPrice();

        //Compare cart price
        assertEquals(total_price_before_purchase, total_price_after_purchase);
        //Compare cart amount of products
        assertEquals(new_cart.getProducts().size(), old_cart.getProducts().size());

        // Compare all cart products
        List<ProductInformation> old_cart_products = old_cart.getProducts();
        List<ProductInformation> new_cart_products = new_cart.getProducts();
        for (int i=0;i<old_cart_products.size();i++)
        {
            ProductInformation new_cart_p = new_cart_products.get(i);
            ProductInformation old_cart_p = old_cart_products.get(i);
            //Compare each product details
            assertNotEquals(new_cart_p, null);
            assertEquals(new_cart_p.getPrice(), old_cart_p.getPrice());
            assertEquals(new_cart_p.getCategory(), old_cart_p.getCategory());
            assertEquals(new_cart_p.getName(), old_cart_p.getName());
            assertEquals(new_cart_p.getKey_words().size(), new_cart_p.getKey_words().size());
            assertEquals(new_cart_p.getQuantity(), old_cart_p.getQuantity());

        }

        //Check store inventory rollback
        Response<StoreInformation> store_res_after = store_manager.find_store_information(store_id);
        List<ProductInformation> inventory_after_purchase = store_res_after.getValue().getInventory();

        for(ProductInformation product_before:inventory_before_purchase)
        {
            for(ProductInformation product_after:inventory_after_purchase)
            {
                if(product_before.getProduct_id() == product_after.getProduct_id())
                {
                    assertEquals(product_before.getQuantity() , product_after.getQuantity(), "store inventory has changes after fail purchase");
                }

            }
        }
    }

    @Test
    void remove_manager() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        MarketFacade marketFacade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        res = marketFacade1.register("manager@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Appointment appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "found a Appointment on uninitialized db test.");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "found appointment when database should be empty");
        Response<String> owner_res = marketFacade.add_manager("manager@gmail.com", store_id);
        assertFalse(res.WasException(), "error while trying to add new owner to store");
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNotNull(appointment, "failed to add product to system database properly.");
        //starting remove product
        marketFacade.delete_manager("manager@gmail.com", store_id);
        appointment = HibernateUtils.getEntityManager().find(Appointment.class, new Long(2));
        assertNull(appointment, "failed to remove owner appointment from data_base.");
    }

//    @Test
//    void remove_policy() {
//        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
//        assertFalse(res.WasException(), "error while trying to register valid user");
//        Response<Integer> open_store_res = marketFacade.open_store("store2");
//        assertFalse(open_store_res.WasException(), "error while trying to open new store");
//        int store_id = open_store_res.getValue();
//        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
//        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
//        assertFalse(res.WasException(), "error while trying to add valid product to store");
//        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
//        Ipredict predict = new Predict("", product, true, false, 10, false,
//                false, false, false, 0, 0, 0);
//        marketFacade.add_predict(store_id, "", productId, true, false, 10, false,
//                false, false, false, 0, 0, 0, "predict_check");
//        Ipredict ipredict = HibernateUtils.getEntityManager().find(Predict.class, new Long(1));
//        assertNotNull(ipredict);
//        marketFacade.remove_predict(store_id, "predict_check");
//        Ipredict ipredict1 = HibernateUtils.getEntityManager().find(Predict.class, new Long(1));
//        assertNull(ipredict1);
//    }
//
//    @Test
//    void remove_discount() {
//        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
//        assertFalse(res.WasException(), "error while trying to register valid user");
//        Response<Integer> open_store_res = marketFacade.open_store("store2");
//        assertFalse(open_store_res.WasException(), "error while trying to open new store");
//        int store_id = open_store_res.getValue();
//        Response simple_res = marketFacade.add_simple_category_discount_rule(store_id, "check", 0.3, "check");
//        DiscountComponent discountComponent = HibernateUtils.getEntityManager().find(DiscountComponent.class, new Long(1));
//        assertNotNull(discountComponent);
//        marketFacade.remove_discount_rule(store_id, "check");
//        discountComponent = HibernateUtils.getEntityManager().find(DiscountComponent.class, new Long(1));
//        assertNull(discountComponent);
//    }
}




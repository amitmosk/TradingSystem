package TradingSystem.server.Domain.Facade;


import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternSystems.*;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Ipredict;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Purchase.Purchase;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchase;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.PremiumSecurity;
import TradingSystem.server.Domain.UserModule.Security;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserInformation;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataBaseLoadTests {
    private EntityManager em = HibernateUtils.getEntityManager();
    private SupplyAdapter supplyAdapter;
    private PaymentAdapter paymentAdapter;
    private MarketFacade marketFacade;
    private String birth_date;
    private final int price = 100;
    private int productId;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";

    public DataBaseLoadTests() {
        try {
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @AfterAll
    void tearDown() {
//        HibernateUtils.set_tests_mode();
    }

    @AfterEach
    void clean() {
//        HibernateUtils.clear_db();
    }

    @BeforeAll
    void setClass() {
//        HibernateUtils.set_load_tests_mode();
//        HibernateUtils.beginTransaction();
        this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
//        HibernateUtils.commit();
//        em = HibernateUtils.getEntityManager();
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
    }

    @BeforeEach
    void SetUp() {
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade.clear();
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
        StoreController storeController = HibernateUtils.getEntityManager().find(StoreController.class, new Long(1));
        int idddd = 1;
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
//        Product product = HibernateUtils.getEntityManager().find(Product.class, 1);
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
        marketFacade.add_product_to_cart(store_id, productId, 10);
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
        Response<UserPurchase> res_userPurchase = marketFacade.buy_cart(new PaymentInfo(), new SupplyInfo());
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
        marketFacade.add_product_to_cart(store_id, productId, 10);
        assertEquals(product, entry.getKey(), "failed to load product properly from database");
        Response<UserPurchase> res_userPurchase = marketFacade.buy_cart(new PaymentInfo(), new SupplyInfo());
        UserPurchase userPurchas = res_userPurchase.getValue();
        Purchase purchase = userPurchas.getStore_id_purchase().get(store_id);
        Purchase load = HibernateUtils.getEntityManager().find(Purchase.class, new Long(-1));
        assertEquals(null, load);
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
        marketFacade.add_product_to_cart(store_id, productId, 10);
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
        marketFacade.add_product_to_cart(store_id, productId, 10);
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

    @Test
    void remove_policy() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response<Map<Product, Integer>> add_prod_res = marketFacade.add_product_to_store(store_id, 50, "banana", 100.0, "fruits", new ArrayList<>());
        Map.Entry<Product, Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class, entry.getKey().getProduct_id());
        Ipredict predict = new Predict("", product, true, false, 10, false,
                false, false, false, 0, 0, 0);
        marketFacade.add_predict(store_id, "", productId, true, false, 10, false,
                false, false, false, 0, 0, 0, "predict_check");
        Ipredict ipredict = HibernateUtils.getEntityManager().find(Predict.class, new Long(1));
        assertNotNull(ipredict);
        marketFacade.remove_predict(store_id, "predict_check");
        Ipredict ipredict1 = HibernateUtils.getEntityManager().find(Predict.class, new Long(1));
        assertNull(ipredict1);
    }

    @Test
    void remove_discount() {
        Response<UserInformation> res = marketFacade.register("email123@gmail.com", password, name, last_name, birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Response simple_res = marketFacade.add_simple_category_discount_rule(store_id, "check", 0.3, "check");
        DiscountComponent discountComponent = HibernateUtils.getEntityManager().find(DiscountComponent.class, new Long(1));
        assertNotNull(discountComponent);
        marketFacade.remove_discount_rule(store_id, "check");
        discountComponent = HibernateUtils.getEntityManager().find(DiscountComponent.class, new Long(1));
        assertNull(discountComponent);
    }
}




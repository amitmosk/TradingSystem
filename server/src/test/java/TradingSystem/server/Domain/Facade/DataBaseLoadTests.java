package TradingSystem.server.Domain.Facade;


import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserInformation;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DataBaseLoadTests {
    private EntityManager em;
    private final SupplyAdapter supplyAdapter = new SupplyAdapterImpl();
    private final PaymentAdapter paymentAdapter = new PaymentAdapterImpl();
    private MarketFacade marketFacade = new MarketFacade(paymentAdapter,supplyAdapter);
    private String birth_date;
    private final int price = 100;
    private int productId;
    private String email = "amit@gmail.com";
    private String manager_email = "manager@gmail.com";
    private String name = "amit";
    private String last_name = "grumet";
    private String password = "aA123456";
    @AfterAll
    void tearDown(){

    }

    @BeforeAll
    void setClass(){
        HibernateUtils.setPersistence_unit("TradingSystemTests");
        em = HibernateUtils.getEntityManager();
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    }

    @BeforeEach
    void SetUp() {
        this.productId = 1;
        this.birth_date = LocalDate.now().minusYears(30).toString();
        marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    }

    @Test
    void user_creation() throws MarketException {
        Response<UserInformation> res = marketFacade.register(email,password,name,last_name,birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        UserInformation user = res.getValue();
        User data_user = HibernateUtils.getEntityManager().find(User.class,user.getId());
        assertEquals(data_user.getBirth_date(),birth_date,"failed to load properly birth_date");
        assertEquals(data_user.user_email(),email,"failed to load properly email");
        assertEquals(data_user.user_name(),name,"failed to load properly name");
        assertEquals(data_user.user_last_name(),last_name,"failed to load properly last_name");
    }

    @Test
    void store_creation(){
        Response<UserInformation> res = marketFacade.register(email,password,name,last_name,birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store1");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
        Store store = HibernateUtils.getEntityManager().find(Store.class,store_id);
        assertEquals(store.getFounder().get_user_email(), email, "failed to load store founder");
        assertEquals(store.getName(), "store1", "failed to load store name");
        StoreController storeController = HibernateUtils.getEntityManager().find(StoreController.class,new Long(1));
        int idddd = 1;
    }

    @Test
    void product_creation(){
        Response<UserInformation> res = marketFacade.register("email123@gmail.com",password,name,last_name,birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        Response<Integer> open_store_res = marketFacade.open_store("store2");
        assertFalse(open_store_res.WasException(), "error while trying to open new store");
        int store_id = open_store_res.getValue();
//        Store store = HibernateUtils.getEntityManager().find(Store.class,store_id);
        Response<Map<Product,Integer>> add_prod_res = marketFacade.add_product_to_store(store_id,50,"banana",100.0,"fruits",new ArrayList<>());
        Map.Entry<Product,Integer> entry = add_prod_res.getValue().entrySet().stream().findAny().get();
        assertFalse(res.WasException(), "error while trying to add valid product to store");
        Product product = HibernateUtils.getEntityManager().find(Product.class,entry.getKey().getProduct_id());
        assertEquals(product,entry.getKey(),"failed to load product properly from database");
    }

}




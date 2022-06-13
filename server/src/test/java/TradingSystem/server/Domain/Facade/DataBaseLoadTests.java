package TradingSystem.server.Domain.Facade;


import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserInformation;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.*;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;

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
    Long one=new Long(1);
    @AfterAll
    void tearDown(){

    }

    @BeforeAll
    void setClass(){
        HibernateUtils.setPersistence_unit("TradingSystemTests");
        em = HibernateUtils.getEntityManager();
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
        Response<UserInformation> res = marketFacade.register(email,password,name,last_name,birth_date);
        assertFalse(res.WasException(), "error while trying to register valid user");
        UserInformation user = res.getValue();
        User data_user = HibernateUtils.getEntityManager().find(User.class,new Long(1));
        assertEquals(data_user.getBirth_date(),birth_date,"failed to load properly birth_date");
        assertEquals(data_user.user_email(),email,"failed to load properly email");
        assertEquals(data_user.user_name(),name,"failed to load properly name");
        assertEquals(data_user.user_last_name(),last_name,"failed to load properly last_name");
    }
}




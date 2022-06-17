package Acceptance.User.AssignUser;
import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GetUserDetails {
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


    public GetUserDetails() {
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
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private void check_was_not_exception(String msg, Response response) { Assertions.assertFalse(response.WasException(), msg); }


    //------------------------------- Get user information --------------------------------------------------------------------------

    /**
     * Get user email
     * 1. register to user and get his email
     * 2. log out and get email - make sure it fails
     * 3. log in to the same user and try to get his email
     * */
    @Test
    void get_user_email_happy() {
        //step 1 - get email after register
        Response res = marketFacade.get_user_email();
        check_was_not_exception("failed to get user's email when it should worked", res);
        assertEquals(email,res.getValue(),"expected to get email - "+email+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("failed to logout while it should work.", res);
        res = marketFacade.get_user_email();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_email();
        assertEquals(email,res.getValue(),"after re-login expected to get email - "+email+ "but got - "+res.getValue());
    }

    @Test
    void get_user_email_sad() {
        marketFacade.logout();
        Response sBad = marketFacade.get_user_email();
        check_was_exception(sBad); // TODO: specify exception
    }


    /**
     * Get user name
     * 1. register to user and get his name
     * 2. log out and get name - make sure it fails
     * 3. log in to the same user and try to get his name
     * */
    @Test
    void get_user_name_happy() {
        //step 1 - get name after register
        Response res = marketFacade.get_user_name();
        check_was_not_exception("failed to get user's name when it should worked", res);
        assertEquals(name,res.getValue(),"expected to get name - "+name+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("got an name while it shouldn't work", res);
        res = marketFacade.get_user_name();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_name();
        assertEquals(name,res.getValue(),"after re-login expected to get name - "+name+ "but got - "+res.getValue());
    }

    @Test
    void get_user_name_sad() {
        //sad
        marketFacade.logout();
        Response sBad = marketFacade.get_user_name();
        check_was_exception(sBad);
    }


    /**
     * Get user last name
     * 1. register to user and get his last name
     * 2. log out and get last name - make sure it fails
     * 3. log in to the same user and try to get his last name
     * */
    @Test
    void get_user_last_name_happy() {
        //step 1 - get name after register
        Response res = marketFacade.get_user_last_name();
        check_was_not_exception("failed to get user's last name when it should worked", res);
        assertEquals(last_name,res.getValue(),"expected to get last name - "+last_name+ "but got - "+res.getValue());
        //step 2 - log out and check it fails
        res = marketFacade.logout();
        check_was_not_exception("got a last name while it shouldn't work", res);
        res = marketFacade.get_user_last_name();
        assertTrue(check_was_exception(res), "succeed to get value while user if offline");
        //step 3 - re log in and check if we still get same result
        marketFacade.login(email, password);
        res = marketFacade.get_user_last_name();
        assertEquals(last_name,res.getValue(),"after re-login expected to get name - "+last_name+ "but got - "+res.getValue());
    }

    @Test
    void get_user_last_name_sad() {
        marketFacade.logout();
        Response sBad = marketFacade.get_user_name();
        check_was_exception(sBad);
    }

}
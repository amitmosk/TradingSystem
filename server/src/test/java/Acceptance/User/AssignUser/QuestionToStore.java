package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.UserModule.CartInformation;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.*;

class QuestionToStore {

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
    private SupplyInfo supplyInfo = new SupplyInfo("1", "2", "3", "4", "5");
    private PaymentInfo payment_info = new PaymentInfo("123", "456", "789", "245", "123", "455");

    public QuestionToStore() {
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

    //------------------------------------------------ Questions --------------------------------------------------------------------------


    @Test
    void send_question_to_store_happy() {
        //happy
        buy_product();
        String q = "how can i control the world";
        Response r = marketFacade.send_question_to_store(1, q);
        check_was_not_exception("Question send to the store successfully", r);
        Response questions = manager.manager_view_store_questions(1);
        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
            boolean flag = false;
            for (String s : ((LinkedList<String>) questions.getValue())) {
                if (s.contains(q)) {
                    flag = true;
                }
            }
            assertTrue(flag);
        }
    }

    @Test
    void send_question_to_store_sad() {
        //sad
        String q = "how can i control the worlds?";
        Response rSad = marketFacade.send_question_to_store(1, q);
        check_was_exception(rSad);
        Response questions = manager.manager_view_store_questions(1);
        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
            boolean flag = false;
            for (String s : ((LinkedList<String>) questions.getValue())) {
                if (s.contains(q)) {
                    flag = true;
                }
            }
            assertFalse(flag);
        }
    }

    @Test
    void manager_view_store_questions() {
        Response questions = manager.manager_view_store_questions(1);
        assertEquals((new LinkedList<String>()).getClass(), questions.getValue().getClass()); // manager view empty list of questions

        Response r = marketFacade.manager_view_store_questions(1);
        check_was_exception(r); // not a manager, shouldn't be able to view

        buy_product();
        String q = "how can i control the world";
        r = marketFacade.send_question_to_store(1, q);
        check_was_not_exception("Question send to the store successfully", r);
        questions = manager.manager_view_store_questions(1);
        if (questions.getValue().getClass() == (new LinkedList<String>()).getClass()) { // manager views question list
            boolean flag = false;
            for (String s : ((LinkedList<String>) questions.getValue())) {
                if (s.contains(q)) {
                    flag = true;
                }
            }
            assertTrue(flag);
        }
    }

}
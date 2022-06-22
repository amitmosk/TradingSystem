package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment.StorePermission;
import TradingSystem.server.Domain.StoreModule.Bid.BidInformation;
import TradingSystem.server.Domain.StoreModule.Bid.BidStatus;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.UserModule.CartInformation;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.NotificationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        marketFacade1.register("buyer1@gmail.com","12345678aA","amit","mosko", birth_date);
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

    //------------------------------------------------------------- Bids Tests --------------------------------------------------------------------------
    /**
     * Cases checked:
     * ADD BID-
     * 1.not_founder_try_to_add_nego
     * 2.manager_without_permissions_try_to_answer_bid
     * 3.complicate uc1
     * 4.complicate uc2
     * 5.complicate uc3
     * 6.complicate uc4
     * 7.complicate uc5


     */




    /**
     * complicate uc1 - no nego , bid confirm
     * 1.add bid for product x in store y - (store y has 2 managers/owners)
     * 2.check bid status - expected - open_waiting_for_answers
     * 3.manager1 confirm the bid - without nego
     * 4.check bid status - expected - open_waiting_for_answers
     * 5.manager2 confirm the bid - without nego
     * 6. check bid status - expected - closed_confirm
     */

    void check_bid_status(BidStatus status, int store_id, int bid_id, MarketFacade store_manager)
    {
        Response<List<BidInformation>> bids_status_res = store_manager.view_bids_status(store_id);
        List<BidInformation> bids_status = bids_status_res.getValue();
        for(BidInformation bid:bids_status)
        {
            assertEquals(bid.getId(), bid_id);
            assertEquals(bid.getStatus(), status.toString());
        }

    }

    @Test
    void complicate_bid_uc1()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");
        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");
        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }
        store_manager.add_manager("store_manager_to_add@gmail.com", store_id);
        //Add permission to answer bid
        List<StorePermission> permissions = new ArrayList<>();
        permissions.add(StorePermission.answer_bid_offer);
        permissions.add(StorePermission.answer_bid_offer_negotiate);
        store_manager.edit_manager_permissions("store_manager_to_add@gmail.com", store_id, permissions);

        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,1800);
        int bid_id = response.getValue();


        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);
        //Manager 1 confirm the bid
        store_manager_to_add.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);
        //Manager 2 confirm the bid
        store_manager.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.closed_confirm, store_id, bid_id, store_manager);

        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");

    }


    /** One manager denied answer should close the bid
     * complicate uc2 - no nego , bid denied
     * 1.add bid for product x in store y - (store y has 2 managers/owners)
     * 2.check bid status - expected - open_waiting_for_answers
     * 3.manager1 denied the bid
     * 4.check bid status - expected - closed_denied
     * 5.manager2 confirm the bid - expected fail
     * 6.check bid status - expected - closed_denied
     */

    @Test
    void complicate_bid_uc2()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");
        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }
        store_manager.add_manager("store_manager_to_add@gmail.com", store_id);
        //Add permission to answer bid
        List<StorePermission> permissions = new ArrayList<>();
        permissions.add(StorePermission.answer_bid_offer);
        permissions.add(StorePermission.answer_bid_offer_negotiate);
        store_manager.edit_manager_permissions("store_manager_to_add@gmail.com", store_id, permissions);
        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,1800);
        int bid_id = response.getValue();


        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);
        //Manager 1 denied the bid
        store_manager_to_add.manager_answer_bid(store_id, bid_id, false, -1);
        check_bid_status(BidStatus.closed_denied, store_id, bid_id, store_manager);


        //Manager 2 try to confirm the CLOSED bid
        store_manager.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.closed_denied, store_id, bid_id, store_manager);
        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");
    }


    /** manager removed during bid
     * complicate uc3 - no nego , bid confirm, manager removed
     * 1.add bid for product x in store y - (store y has 2 managers/owners)
     * 2.check bid status - expected - open_waiting_for_answers
     * 3.manager1 confirm the bid
     * 4.check bid status - expected - open_waiting_for_answers
     * 5.remove manager2 from store management
     * 6.check bid status - expected - closed_confirm
     */

    @Test
    void complicate_bid_uc3()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");
        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }
        store_manager.add_manager("store_manager_to_add@gmail.com", store_id);
        //Add permission to answer bid
        List<StorePermission> permissions = new ArrayList<>();
        permissions.add(StorePermission.answer_bid_offer);
        permissions.add(StorePermission.answer_bid_offer_negotiate);
        store_manager.edit_manager_permissions("store_manager_to_add@gmail.com", store_id, permissions);
        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,1800);
        int bid_id = response.getValue();


        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);
        //Manager 1 confirm the bid
        store_manager.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);


        store_manager.delete_manager("store_manager_to_add@gmail.com", store_id);
        check_bid_status(BidStatus.closed_confirm, store_id, bid_id, store_manager);

        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");
    }

    /** manager added during bid
     * complicate uc4 - no nego , bid confirm, manager added during bid
     * 1.add bid for product x in store y - (store y has 2 managers/owners)
     * 2.check bid status - expected - open_waiting_for_answers
     * 3.manager1 confirm the bid
     * 4.check bid status - expected - open_waiting_for_answers
     * 5.add manager3 to store management
     * 6.manager2 confirm the bid
     * 7.check bid status - expected - open_waiting_for_answers
     * 8.manager3 confirm the bid
     * 9.check bid status - expected - closed_confirm
     */

    @Test
    void complicate_bid_uc4()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");
        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");

        //Store manager 3
        MarketFacade store_manager3 = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager3.register("store_manager3@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();

        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }
        store_manager.add_owner("store_manager_to_add@gmail.com", store_id);

        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,1800);
        int bid_id = response.getValue();


        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);

        //Manager 1 confirm the bid
        store_manager.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);


        //Added third manager
        store_manager.add_owner("store_manager3@gmail.com", store_id);
        manager.manager_answer_appointment(store_id, true,"store_manager3@gmail.com" );
        store_manager_to_add.manager_answer_appointment(store_id, true,"store_manager3@gmail.com" );


        //Manager 2 confirm the bid
        store_manager_to_add.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);

        //Manager 3 confirm the bid
        store_manager3.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.closed_confirm, store_id, bid_id, store_manager);


        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        store_manager3.unregister("12345678aA");
        buyer.unregister("12345678aA");

    }



    /** manager without permissions to answer bid added during bid
     * complicate uc5 - no nego , bid confirm, manager added during bid
     * 1.add bid for product x in store y - (store y has 2 managers/owners)
     * 2.check bid status - expected - open_waiting_for_answers
     * 3.manager1 confirm the bid
     * 4.check bid status - expected - open_waiting_for_answers
     * 5.add manager3 - (without permissions to answer bid) to store management
     * 6.manager2 confirm the bid
     * 7.check bid status - expected - closed_conform
     * 8.manager3 confirm the bid - expected fail
     * 9.check bid status - expected - closed_confirm
     */

    @Test
    void complicate_bid_uc5()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");
        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");

        //Store manager 3
        MarketFacade store_manager3 = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager3.register("store_manager3@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }
        store_manager.add_manager("store_manager_to_add@gmail.com", store_id);
        //Add permission to answer bid
        List<StorePermission> permissions = new ArrayList<>();
        permissions.add(StorePermission.answer_bid_offer);
        permissions.add(StorePermission.answer_bid_offer_negotiate);
        store_manager.edit_manager_permissions("store_manager_to_add@gmail.com", store_id, permissions);
        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,1800);
        int bid_id = response.getValue();


        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);
        //Manager 1 confirm the bid
        store_manager.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.open_waiting_for_answers, store_id, bid_id, store_manager);


        //Added third manager without permissions to answer bid
        store_manager.add_manager("store_manager3@gmail.com", store_id);


        //Manager 2 confirm the bid
        store_manager_to_add.manager_answer_bid(store_id, bid_id, true, -1);
        check_bid_status(BidStatus.closed_confirm, store_id, bid_id, store_manager);

        //Manager 3 confirm the bid  - expected fail
        Response answer_bid_res = store_manager3.manager_answer_bid(store_id, bid_id, true, -1);
        assertTrue(answer_bid_res.iswas_exception());

        check_bid_status(BidStatus.closed_confirm, store_id, bid_id, store_manager);


        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        store_manager3.unregister("12345678aA");
        buyer.unregister("12345678aA");

    }


    /**
     * Adding negotiation answer by manager who is not founder of the store
     */

    @Test
    void not_founder_try_to_add_nego()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");

        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }

        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,80);
        int bid_id = response.getValue();

        store_manager.add_owner("store_manager_to_add@gmail.com", store_id);
        Response add_bid_res = store_manager_to_add.manager_answer_bid(store_id,bid_id,true,50);
        assertTrue(add_bid_res.WasException());


        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");
    }


    /**
     Adding bid answer by user who is not stuff member of the store
     */

    @Test
    void manager_without_permissions_try_to_answer_bid()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");

        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }

        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,80);
        int bid_id = response.getValue();

        store_manager.add_manager("store_manager_to_add@gmail.com", store_id);
        Response add_bid_res = store_manager_to_add.manager_answer_bid(store_id,bid_id,true,-1);
        assertTrue(add_bid_res.WasException());

        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");

    }

    /**
     Manager try to answer close bid
     */

    @Test
    void manager_with_permissions_try_to_answer_closed_bid()
    {
        //Store manager
        MarketFacade store_manager = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager.register("store_manager@gmail.com","12345678aA", "store", "manager", "15.01.95");

        //Another store manager
        MarketFacade store_manager_to_add = new MarketFacade(paymentAdapter, supplyAdapter);
        store_manager_to_add.register("store_manager_to_add@gmail.com","12345678aA", "added", "manager", "15.01.95");


        //Open store
        Response<Integer> open_store_res = store_manager.open_store("ADDBID");
        int store_id = open_store_res.getValue();
        //Add product to store
        Response<Map<Product, Integer>> inventory_res = store_manager.add_product_to_store(store_id, 10, "Iphone", 2000, "Electric", new ArrayList<>());
        Map <Product, Integer> inventory = inventory_res.getValue();
        int product_id=-1;
        for(Product p :inventory.keySet())
        {
            product_id=p.getProduct_id();
        }

        //Buyer
        MarketFacade buyer = new MarketFacade(paymentAdapter, supplyAdapter);
        buyer.register("buyer@gmail.com","12345678aA", "buyer", "user", "15.01.95");
        //Add bid
        Response<Integer> response = buyer.add_bid(store_id,product_id,3,80);
        int bid_id = response.getValue();

        store_manager.add_owner("store_manager_to_add@gmail.com", store_id);
        store_manager_to_add.manager_answer_bid(store_id,bid_id,false,-1);
        Response add_bid_res = store_manager.manager_answer_bid(store_id,bid_id,true,-1);
        assertTrue(add_bid_res.WasException());


        store_manager.unregister("12345678aA");
        store_manager_to_add.unregister("12345678aA");
        buyer.unregister("12345678aA");

    }

}
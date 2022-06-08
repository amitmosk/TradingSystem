package TradingSystem.server.Domain.Facade.AcceptanceTest;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchase;
import TradingSystem.server.Domain.StoreModule.Purchase.StorePurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Purchase.UserPurchaseHistory;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Response;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MarketFacadeTest {
    private MarketFacade facade1;
    private MarketFacade facade2;
    private MarketFacade facade3;
    private MarketFacade facade4;
    private UserController uc;
    private PaymentAdapter pa;
    private SupplyAdapter sa;
    private String email;
    private String password;
    private String birth_date;
    private final int num_of_threads = 100;
    private int prod_counter = 0;

    private boolean check_was_exception(Response response) {
        return response.WasException();
    }

    @BeforeEach
    void setUp() throws MarketException {
        birth_date =  LocalDate.now().minusYears(30).toString();
        PaymentAdapter paymentAdapter = new PaymentAdapterImpl();
        SupplyAdapter supplyAdapter = new SupplyAdapterImpl();
        this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        this.facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
        this.facade4 = new MarketFacade(paymentAdapter, supplyAdapter);
        facade1.register("check1234@email.com", "pass3Chec", "name", "last",birth_date);
        int id = open_store_get_id("Checker Store");
        facade2.register("check12345@email.com", "pass3Chec", "name", "last",birth_date);
        int prod = add_prod_make_purchase_get_id(id);
        facade1.logout();
        facade2.logout();
        facade1.register("check123456@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.logout();
        facade1.register("check123457@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.logout();
        facade1.register("heck1234578@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.improve_security("pass3Chec", "What was your mother's maiden name?", "Sasson");
        facade1.logout();
        UserController.getInstance().add_admin("admin@gmail.com", "12345678aA", "Barak", "Bahar");


        uc = UserController.getInstance();
        pa = new PaymentAdapterImpl();
        sa = new SupplyAdapterImpl();
        email = "somthing@gmail.com";
        password = "aA12345";
    }

    // --------------------------------------------- Helper functions -----------------------------
    private void start_threads(List<Thread> threads) {
        for (Thread t : threads) {
            t.start();
        } // running all the threads parallel
    }

    private void join_threads(List<Thread> threads) {
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (Exception e) {
            assertTrue(false, "there was error while running the threads");
        }
    }

    private int num_of_stores(){
        Response res = facade1.get_all_stores();
        int stores_count = 0;
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            stores_count = ((ArrayList<StoreInformation>) res.getValue()).size();
        }
        return stores_count;
    }

    private boolean find_store(String name, int num_of_stores) {
        Response res = facade1.get_all_stores();
        int counter = 0;
        if (res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            ArrayList<StoreInformation> stores = ((ArrayList<StoreInformation>) res.getValue());
            for (StoreInformation info : stores)
                if (info.getName() == name)
                {
                    counter++;
                    if(num_of_stores == counter)
                        return true;
                }
        }
        return false;
    }

    private String get_store_founder(String name, int num_of_stores){
        Response res = facade1.get_all_stores();
        int counter = 0;
        if (res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            ArrayList<StoreInformation> stores = ((ArrayList<StoreInformation>) res.getValue());
            for (StoreInformation info : stores)
                if (info.getName() == name)
                {
                    counter++;
                    if(num_of_stores == counter)
                        return info.getFounder_email();
                }
        }
        return "";
    }

    private int open_store_get_id(String name){
        facade1.open_store(name);
        return num_of_stores();
    }

    private int add_prod_make_purchase_get_id(int sore_id){
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("check_check");
        facade1.add_product_to_store(sore_id, 100, "CheckItem", 10.0, "checker", new ArrayList<>());
        prod_counter++;
        facade2.add_product_to_cart(sore_id, prod_counter, 1);
        facade2.buy_cart("credit", "address");
        return prod_counter;
    }

    private boolean check_if_purchase_exists(Response res, String email, int prod){
        boolean flag = false;

        if(res.getValue().getClass() == (new ConcurrentHashMap<Integer, StorePurchase>()).values().getClass()){
            Collection<StorePurchase> his = (Collection<StorePurchase>)res.getValue();
            for(StorePurchase p : his){
                if(p.getBuyer_email() == email && p.getProduct_and_totalPrice().containsKey(prod))
                    flag = true;
            }
            System.out.println("\n1\n");

        }
        else if(res.getValue().getClass() == StorePurchaseHistory.class){
            StorePurchaseHistory his = (StorePurchaseHistory)res.getValue();
            for(StorePurchase p : his.getPurchaseID_purchases().values()){
                if(p.getBuyer_email() == email && p.getProduct_and_totalPrice().containsKey(prod))
                    flag = true;
            }
            System.out.println("\n2\n");
        }
        return flag;
    }

    private boolean valid_purchase_history(Response res, String email, int prod){
        return (!check_was_exception(res) && check_if_purchase_exists(res, email, prod));
    }

    private void valid_admin_questions(Response res, int num_of_question, String question, String email, boolean answered, String answer){
        Object res_val = res.getValue();
        assertFalse(check_was_exception(res));
        assertEquals((new LinkedList<String>()).getClass(), res_val.getClass());
        if(num_of_question == 0) {
            assertTrue(((LinkedList<String>)res_val).isEmpty());
        }
        else{
            if(res_val.getClass() == (new LinkedList<String>()).getClass()) {
                assertTrue(num_of_question == ((LinkedList<String>)res_val).size());
                boolean flag = false;
                for(String s : ((LinkedList<String>)res_val)){
                    if(s.contains(question) && s.contains(email) && !answered){
                        flag = true;
                    }
                    else if(s.contains(question) && s.contains(email) && s.contains(answer)){
                        flag = true;
                    }
                }
                assertTrue(flag);
            }
        }
    }

    private void founder_exist(String founder, int store_id) throws MarketException {
        boolean founder_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == founder){
                founder_exist = true;
                break;
            }
        }
        assertTrue(founder_exist);
    }

    private void owner_exist(String owner, int store_id, boolean not, int staff_size) throws MarketException {
        boolean owner_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == owner){
                owner_exist = true;
                break;
            }
        }
        if(!not)
            assertTrue(owner_exist);
        else
            assertFalse(owner_exist);

        assertTrue(staff.size() == staff_size);
    }

    private void manager_exist(String manager, int store_id, boolean not, int staff_size) throws MarketException {
        boolean manager_exist = false;
        Store store = facade1.get_store(store_id);
        Map<AssignUser, Appointment> staff = store.getStuffs_and_appointments();
        for(AssignUser u : staff.keySet()){
            if(u.get_user_email() == manager){
                manager_exist = true;
                break;
            }
        }
        if(!not)
            assertTrue(manager_exist);
        else
            assertFalse(manager_exist);

        assertTrue(staff.size() == staff_size);
    }

    // --------------------------------------------------------------------------------------------------------

    // delete_owner
    // close_store_temporarily
    // open_close_store
    // view_store_management_information
    // manager_answer_question
    // get_market_stats
    // get_products_by_store_id
    // get_user_questions
    // edit_product_quantity
    // online_user
    // close_store_permanently

    @Test
    void add_remove_manager() throws MarketException {
        Response res;
        String founder = "user1@founder.com";
        String owner = "user2@owner.com";
        String manager1 = "user3@manager.com";
        String manager2 = "user4@manager.com";
        int staff_size = 0;

        res = facade1.register(founder, "paSs12", "one", "founder", birth_date);
        assertFalse(check_was_exception(res));
        res = facade2.register(owner, "paSs12", "two", "owner", birth_date);
        assertFalse(check_was_exception(res));
        res = facade3.register(manager1, "paSs12", "three", "manager", birth_date);
        assertFalse(check_was_exception(res));
        res = facade4.register(manager2, "paSs12", "four", "manager", birth_date);
        assertFalse(check_was_exception(res));

        int store_id = open_store_get_id("new test store");
        founder_exist(founder, store_id);
        staff_size++;

        res = facade1.add_owner(owner, store_id);
        assertFalse(check_was_exception(res));
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner,store_id,false, staff_size);

        res = facade2.add_manager(manager1, store_id);
        assertFalse(check_was_exception(res));
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,false, staff_size);

        res = facade3.add_manager(manager2, store_id);
        assertTrue(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,false, staff_size);
        manager_exist(manager2, store_id,true, staff_size);

        res = facade2.add_manager(manager2, store_id);
        assertFalse(check_was_exception(res));
        staff_size++;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,false, staff_size);
        manager_exist(manager2, store_id,false, staff_size);

        res = facade3.delete_owner(owner, store_id);
        assertTrue(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,false, staff_size);
        manager_exist(manager2, store_id,false, staff_size);

        res = facade2.delete_manager(manager1, store_id);
        assertFalse(check_was_exception(res));
        staff_size--;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,true, staff_size);
        manager_exist(manager2, store_id,false, staff_size);

        res = facade3.delete_owner(owner, store_id);
        assertTrue(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,true, staff_size);
        manager_exist(manager2, store_id,false, staff_size);

        res = facade1.delete_owner(owner, store_id);
        assertFalse(check_was_exception(res));
        staff_size = 1;
        founder_exist(founder, store_id);
        owner_exist(owner, store_id,false, staff_size);
        manager_exist(manager1, store_id,true, staff_size);
        manager_exist(manager2, store_id,false, staff_size);

        facade1.logout();
        facade2.logout();
        facade3.logout();
        facade4.logout();
    }

    // Bar's test from version 2 meeting
    @Test
    void recursive_appointment_removal() throws MarketException {
        Response res;
        String founder = "user1@founder.com";
        String owner1 = "user2@owner.com";
        String owner2 = "user3@owner.com";

        res = facade1.register(founder, "paSs12", "one", "founder", birth_date);
        assertFalse(check_was_exception(res));
        res = facade2.register(owner1, "paSs12", "two", "owner", birth_date);
        assertFalse(check_was_exception(res));
        res = facade3.register(owner2, "paSs12", "three", "owner", birth_date);
        assertFalse(check_was_exception(res));

        int store_id = open_store_get_id("new test store");
        founder_exist(founder, store_id);

        res = facade1.add_owner(owner1, store_id);
        assertFalse(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner1,store_id,false, 2);

        res = facade2.add_owner(owner2, store_id);
        assertFalse(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id,false, 3);
        owner_exist(owner2, store_id,false, 3);

        res = facade3.delete_owner(owner1, store_id);
        assertTrue(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id,false, 3);
        owner_exist(owner2, store_id,false, 3);

        res = facade1.delete_owner(owner1, store_id);
        assertFalse(check_was_exception(res));
        founder_exist(founder, store_id);
        owner_exist(owner1, store_id,true, 1);
        owner_exist(owner2, store_id,true, 1);

        facade1.logout();
        facade2.logout();
        facade3.logout();
    }

    /**
     * Cases checked:
     * 1. guest tries to view empty admin question list
     * 2. guest tries to send admin question
     * 3. admin views empty question list
     * 4. connected user sends admin a question
     * 5. guest tries to view admin question list
     * 6. regular user tries to view admin question list
     * 7. admin views question list of size 1
     * 8. connected user sends admin another question
     * 9. admin views question list of size 2 (all from same user)
     * 10. user sends admin same question as different user
     * 11. admin views question list of size 3 (2 from same user, 2 same question different user)
     * 12. admin tries to answer question with an id that does not exist
     * 13. regular user tries to answer question
     * 14. admin answers a question
     * 15. admin views question list of size 3 (one of them is answered)
     * 16. admin re-answers user's question
     * 17. admin views question list of size 3 (one of them is re-answered)
     * 18. admin answers a different user's question
     * 19. admin views question list of size 3 (one of them is re-answered, one is answered and one is not answered)
     */
    @Test
    void send_to_admin_view_and_answer_questions(){
        Response res;

        res = facade2.admin_view_users_questions(); // no user is connected, no questions yet
        assertTrue(check_was_exception(res));

        String question1 = "guest sends question";
        res = facade2.send_question_to_admin(question1);
        assertTrue(check_was_exception(res)); // guest user can't send question to admin

        facade1.login("admin@gmail.com", "12345678aA");

        res = facade1.admin_view_users_questions(); // admin views empty question list
        valid_admin_questions(res, 0, "", "",false,"");
        facade1.logout();

        facade2.login("check123456@email.com", "pass3Chec");
        question1 = "user sends admin a question";
        res = facade2.send_question_to_admin(question1);
        assertFalse(check_was_exception(res)); // connected user sends admin a question

        res = facade1.admin_view_users_questions(); // no user is connected, there is a question
        assertTrue(check_was_exception(res));

        res = facade2.admin_view_users_questions(); // regular user is connected, there is a question
        assertTrue(check_was_exception(res));

        facade1.login("admin@gmail.com", "12345678aA");

        res = facade1.admin_view_users_questions(); // admin views question list of size 1
        valid_admin_questions(res, 1, question1, "check123456@email.com",false,"");

        String question2 = "this should work as well";
        res = facade2.send_question_to_admin(question2);
        assertFalse(check_was_exception(res)); // connected user sends admin a question

        res = facade1.admin_view_users_questions(); // admin views question list of size 2
        valid_admin_questions(res, 2, question1, "check123456@email.com",false,"");
        valid_admin_questions(res, 2, question2, "check123456@email.com",false,"");

        facade2.logout();
        facade2.login("check123457@email.com", "pass3Chec");

        String question3 = "this should work as well"; // different user same question
        res = facade2.send_question_to_admin(question3);
        assertFalse(check_was_exception(res)); // connected user sends admin same question as different user

        res = facade1.admin_view_users_questions(); // admin views question list of size 3
        valid_admin_questions(res, 3, question1, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question2, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question3, "check123457@email.com",false,"");

        String answer = "i answer";

        res = facade1.admin_answer_user_question(0, answer); // admin enters question id that does not exist
        assertTrue(check_was_exception(res));

        res = facade2.admin_answer_user_question(1, answer); // regular user tries to answer question
        assertTrue(check_was_exception(res));

        res = facade1.admin_view_users_questions(); // to make sure non of the 2 above worked
        valid_admin_questions(res, 3, question1, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question2, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question3, "check123457@email.com",false,"");

        res = facade1.admin_answer_user_question(1, answer); // admin answers a question
        assertFalse(check_was_exception(res));

        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is answered)
        valid_admin_questions(res, 3, question1, "check123456@email.com",true, answer);
        valid_admin_questions(res, 3, question2, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question3, "check123457@email.com",false,"");

        answer += "!";

        res = facade1.admin_answer_user_question(1, answer); // admin re-answers user's question
        assertFalse(check_was_exception(res));

        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is re-answered)
        valid_admin_questions(res, 3, question1, "check123456@email.com",true, answer);
        valid_admin_questions(res, 3, question2, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question3, "check123457@email.com",false,"");

        res = facade1.admin_answer_user_question(3, answer); // admin answers different user's question
        assertFalse(check_was_exception(res));

        res = facade1.admin_view_users_questions(); // admin views question list of size 3 (one of them is re-answered, one is answered and one is not answered)
        valid_admin_questions(res, 3, question1, "check123456@email.com",true, answer);
        valid_admin_questions(res, 3, question2, "check123456@email.com",false,"");
        valid_admin_questions(res, 3, question3, "check123457@email.com",true,answer);

        facade2.logout();
        facade1.logout();
    }


    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not the store owner
     * 3. user enters a store id that does not exist
     * 4. store founder views store's purchase history
     * 5. store founder enters a store id that does not exist
     * 6. store founder enters a store id that didn't doesn't have permissions to see
     * 7. store founder views new store's empty purchase history
     * 8. store founder views store's purchase history
     */
    @Test
    void view_store_purchases_history() {
        Response res;

        res = facade1.view_store_purchases_history(1); // no one is connected
        assertTrue(check_was_exception(res));

        facade1.login("heck1234578@email.com", "pass3Chec");
        res = facade1.view_store_purchases_history(1); // user connected is not the store owner
        assertTrue(check_was_exception(res));

        res = facade1.view_store_purchases_history(num_of_stores() + 2); // user enters a store id that does not exist
        assertTrue(check_was_exception(res));

        facade2.login("check1234@email.com", "pass3Chec");
        res = facade2.view_store_purchases_history(1); // store founder views store's purchase history
        assertTrue(valid_purchase_history(res, "check12345@email.com", 1));

        res = facade2.view_store_purchases_history(num_of_stores() + 2); // store founder enters a store id that does not exist
        assertTrue(check_was_exception(res));

        int store_id = open_store_get_id("first store for this user"); // store founder opens first store

        res = facade2.view_store_purchases_history(num_of_stores() + 2); // store founder enters a store id that didn't doesn't have permissions to see
        assertTrue(check_was_exception(res));

        res = facade1.view_store_purchases_history(store_id); // store founder views new store's empty purchase history
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == (new ConcurrentHashMap<Integer, StorePurchase>()).values().getClass()){
            Collection<StorePurchase> his = (Collection<StorePurchase>)res.getValue();
            assertTrue(his.isEmpty());
        }

        int prod_id = add_prod_make_purchase_get_id(store_id);

        res = facade1.view_store_purchases_history(store_id); // store founder views store's purchase history
        System.out.println("\nStore: " + store_id + "\nProd: " + prod_id);
        assertTrue(valid_purchase_history(res, "check1234@email.com", prod_id));


        facade1.logout();
        facade2.logout();


    }


    /**
     * Cases checked:
     * 1. no user is connected
     * 2. store founder opens store number 2
     * 3. store founder opens store with same name
     * 4. store founder opens first store
     */
    @Test
    void open_store(){
        Response res;

        int stores_count = num_of_stores();
        String name = "this shouldn't work";
        res = facade1.open_store(name); // no user is connected
        assertTrue(check_was_exception(res));
        assertEquals(stores_count, num_of_stores());
        assertFalse(find_store(name, 1));

        name = "store number 2";
        facade1.login("check1234@email.com", "pass3Chec");
        res = facade1.open_store(name); // store founder opens store number 2

        assertFalse(check_was_exception(res));
        assertTrue(find_store(name, 1));
        stores_count++;
        assertEquals(stores_count, num_of_stores());
        assertEquals("check1234@email.com", get_store_founder("store number 2", 1));

        res = facade1.open_store(name); // store founder opens store with same name
        assertFalse(check_was_exception(res));
        stores_count++;
        assertEquals(stores_count, num_of_stores());
        assertTrue(find_store(name, 2));
        assertEquals("check1234@email.com", get_store_founder("store number 2", 2));

        facade1.logout();
        facade1.login("check123456@email.com", "pass3Chec");

        name = "Store number 1";
        res = facade1.open_store(name); // store founder opens first store
        assertFalse(check_was_exception(res));
        stores_count++;
        assertEquals(stores_count, num_of_stores());
        assertTrue(find_store(name, 1));
        assertEquals("check123456@email.com", get_store_founder("Store number 1", 1));

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. get all stores
     */
    @Test
    void get_all_stores(){
        Response res;

        res = facade1.get_all_stores();
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()){
            assertEquals(1 ,((ArrayList<StoreInformation>)res.getValue()).size());
        }


    }

    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not an admin
     * 3. admin enters an email that doesn't exist
     * 4. admin removes user
     */
    @Test
    void remove_user(){
        Response res;

        res = facade1.remove_user("check123456@email.com");  // no one is connected
        assertTrue(check_was_exception(res));
        res = facade2.login("check123456@email.com", "pass3Chec"); // check if user can still login -> still exists
        assertFalse(res.WasException());
        facade2.logout();

        facade1.login("heck1234578@email.com", "pass3Chec");
        res = facade1.remove_user("check123456@email.com");  // user connected is not an admin
        assertTrue(check_was_exception(res));
        res = facade2.login("check123456@email.com", "pass3Chec"); // check if user can still login -> still exists
        assertFalse(res.WasException());
        facade2.logout();

        facade1.logout();
        facade1.login("admin@gmail.com", "12345678aA");
        res = facade1.remove_user("idontexist@email.com");  // admin enters an email that doesn't exist
        assertTrue(check_was_exception(res));

        res = facade1.remove_user("check123456@email.com");  // admin removes user
        assertFalse(check_was_exception(res));
        res = facade2.login("check123456@email.com", "pass3Chec"); // check if user can still login -> still exists
        assertTrue(res.WasException());

        facade1.logout();


    }

    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not an admin
     * 3. admin enters a store id that does not exist
     * 4. admin views user's purchase history
     */
    @Test
    void admin_view_store_purchases_history() throws MarketException {
        Response res;

        res = facade1.admin_view_store_purchases_history(1); // no one is connected
        assertTrue(check_was_exception(res));

        facade1.login("heck1234578@email.com", "pass3Chec");
        res = facade1.admin_view_store_purchases_history(1); // user connected is not an admin
        assertTrue(check_was_exception(res));

        facade2.login("admin@gmail.com", "12345678aA");
        res = facade2.admin_view_store_purchases_history(2); // admin enters a store id that does not exist
        assertTrue(check_was_exception(res));

        res = facade2.admin_view_store_purchases_history(1); // admin views store's purchase history
        assertTrue(valid_purchase_history(res, "check12345@email.com", 1));


        facade1.logout();
        facade2.logout();

    }


    /**
     * Cases checked:
     * 1. no one is connected
     * 2. user connected is not an admin
     * 3. admin views user's empty purchase history
     * 4. admin views user's purchase history
     */
    @Test
    void admin_view_user_purchases_history() throws MarketException {
        Response res;

        res = facade1.admin_view_user_purchases_history("check12345@email.com"); // no one is connected
        assertTrue(check_was_exception(res));

        facade1.login("heck1234578@email.com", "pass3Chec");
        res = facade1.admin_view_user_purchases_history("check12345@email.com"); // user connected is not an admin
        assertTrue(check_was_exception(res));

        facade2.login("admin@gmail.com", "12345678aA");
        res = facade2.admin_view_user_purchases_history("check123456@email.com"); // admin views user's empty purchase history
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == UserPurchaseHistory.class){
            UserPurchaseHistory his = (UserPurchaseHistory)res.getValue();
            assertTrue(his.getHistoryList().isEmpty());
        }

        facade2.login("admin@gmail.com", "12345678aA");
        res = facade2.admin_view_user_purchases_history("check12345@email.com"); // admin views user's purchase history
        assertFalse(check_was_exception(res));
        if(res.getValue().getClass() == UserPurchaseHistory.class){
            UserPurchaseHistory his = (UserPurchaseHistory)res.getValue();
            assertTrue(his.check_if_user_buy_from_this_store(1));
            assertTrue(his.check_if_user_buy_this_product(1, 1));

        }
        facade1.logout();
        facade2.logout();

    }


    static Stream<Arguments> user_info_provider1() {
        return Stream.of(
                arguments("check1@email.com", "pass3Chec", "name", "last"),
                arguments("check2@email.com", "pass1Chec", "name", "last"),
                arguments("check3@email.com", "Ch3ckPsw0rd", "checker", "checkcheck")
        );
    }

    /**
     * Cases checked:
     * 1. regular register
     * 2. register with registered user from different facade
     * 3. register with registered user from same facade
     * 4. register with registered user from same facade while logged in
     */
    @ParameterizedTest
    @MethodSource("user_info_provider1")
    void register(String email, String pw, String name, String lastName) {
        //case 1
        Response res = facade1.register(email, pw, name, lastName, birth_date);
        boolean was_exception = check_was_exception(res); // regular register
        assertFalse(was_exception, "failed with regular register");
        Response<String> user_email_res = facade1.get_user_email();
        assertEquals(user_email_res.getValue(),email,"case 1 - failed to add user to system , got different user");
        //case 2
        was_exception = check_was_exception(facade2.register(email, pw, name, lastName, birth_date)); // register with registered user from different facade
        assertTrue(was_exception, "succeed to register with registered user from different facade");
        facade1.logout();
        //case 3
        was_exception = check_was_exception(facade1.register("check1@email.com", "pass3Chec", "name", "last", birth_date)); // register with registered user from same facade
        assertTrue(was_exception, "succeed to register with registered user from same facade");
        //case 4
        facade1.register("check12@email.com", "pass123Chec", "name", "last", birth_date);
        was_exception = check_was_exception(facade1.register("check12@email.com", "pass123Chec", "name", "last", birth_date)); // register with registered user from same facade while logged in
        assertTrue(was_exception,"succeed to register with registered user from same facade while logged in");
        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. regular login
     * 2. login with connected user different facade
     * 3. login with same facade different but user
     */
    static Stream<Arguments> user_info_provider2() {
        return Stream.of(
                arguments("check1@email.com", "pass3Chec"),
                arguments("check2@email.com", "pass1Chec"),
                arguments("check3@email.com", "Ch3ckPsw0rd")
        );
    }

    @ParameterizedTest
    @MethodSource("user_info_provider2")
    void login(String email, String pw) {
        boolean result = check_was_exception(facade1.login(email, pw)); // regular login
        assertFalse(result);
        result = check_was_exception(facade2.login(email, pw)); // same user different facade
        assertTrue(result);
        result = check_was_exception(facade1.login("check1@email.com", "pass3Chec")); // same facade different user
        assertTrue(result);
        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. logout with no user connected
     * 2. regular logout
     * 3. logout second time in a row
     * 4. logout after login failed
     */
    @Test
    void logout() {
        boolean result = check_was_exception(facade1.logout()); // logout with no user connected
        assertTrue(result);
        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.logout()); // regular logout
        assertFalse(result);
        result = check_was_exception(facade1.logout()); // logout second time in a row
        assertTrue(result);
        facade1.login("checrr@email.com", "pass3hec"); // login will fail
        result = check_was_exception(facade1.logout()); // logout after login failed
        assertTrue(result);
    }

    /*
     * Cases checked:
     * 1. get last name with no user connected
     * 2. get last name with user connected
     */
    @Test
    void get_user_last_name() {
        boolean result = check_was_exception(facade1.get_user_last_name()); // get last name with no user connected
        assertTrue(result);
        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.get_user_last_name()); // get last name with user connected
        assertFalse(result);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. get name with no user connected
     * 2. get name with user connected
     */
    @Test
    void get_user_name() {
        boolean result = check_was_exception(facade1.get_user_name()); // get name with no user connected
        assertTrue(result);
        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.get_user_name()); // get name with user connected
        assertFalse(result);
        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. edit password with wrong old password
     * 2. edit password
     * 3. edit password with invalid new password
     * 4. edit password with empty new password
     * 5. edit password with no user connected
     */
    @Test
    void edit_password() {
        boolean result = check_was_exception(facade1.edit_password("pass3Chec", "pass12CH")); // edit password with no user connected
        assertTrue(result);

        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_password("pass3Chec", "pass12r")); // edit password with invalid new password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password("pass3Chec", "")); // edit password with empty new password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password("pass3chec", "pass12RT")); // edit password with invalid old password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password("pass3Chec", "pass12rT")); // edit password
        assertFalse(result);

        result = check_was_exception(facade1.edit_password("pass12rT", "pass3Chec")); // edit password
        assertFalse(result);
        facade1.logout();
    }

    /**
     * Cases checked:
     * ADD-
     * 1. add product when no user is logged in
     * 2. add product when user is logged in
     * 3. add product that doesn't exist
     * 4. add product with negative quantity
     * 5. add product with larger quantity then possible
     * 6. add product that was already added
     * 7. add product from a store that doesn't exist
     *
     * REMOVE-
     * 1. remove product when no user is logged in
     * 2. remove product when user is logged in
     * 3. remove product that doesn't exist in cart
     */
    @Test
    void add_and_remove_product_from_cart() {
        boolean result;
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product when no user is logged in
        assertFalse(result);
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product that was already added
        assertTrue(result);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product when no user is logged in
        assertFalse(result);
        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product when user is logged in
        assertFalse(result);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product when user is logged in
        assertFalse(result);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, 1));  // add product that doesn't exist
        assertTrue(result);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product that doesn't exist in cart
        assertTrue(result);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, -1)); // add product with negative quantity
        assertTrue(result);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, 11)); // add product with larger quantity then possible
        assertTrue(result);
        result = check_was_exception(facade1.add_product_to_cart(30, 1, 1));  // add product from a store that doesn't exist
        assertTrue(result);

        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. edit quantity when no product is in cart
     * 2. edit quantity when user is logged in
     * 3. edit quantity of a product that doesn't exist
     * 4. edit quantity of a product from a store that doesn't exist
     * 5. edit to negative quantity
     * 6. edit to zero quantity
     */
    @Test
    void edit_product_quantity_in_cart() {
        boolean result;
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 1)); // edit quantity when no product is in cart
        assertTrue(result);
        facade1.login("check1234@email.com", "pass3Chec");
        facade1.add_product_to_cart(1, 1, 1);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 1)); // edit quantity when user is logged in
        assertFalse(result);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, 1));  // edit quantity of a product that doesn't exist
        assertTrue(result);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 0)); // edit quantity of a product from a store that doesn't exist
        assertTrue(result);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, -1)); // edit to negative quantity
        assertTrue(result);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, 11)); // edit to zero quantity
        assertTrue(result);
        facade1.remove_product_from_cart(1, 1);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. view cart with guest user
     * 2. view cart with assigned user
     */
    @Test
    void view_user_cart() {
        boolean result;
        result = check_was_exception(facade1.view_user_cart()); // view cart with guest user
        assertFalse(result);
        facade1.login("check1234@email.com", "pass3Chec");
        result = check_was_exception(facade1.view_user_cart()); // view cart with assigned user
        assertFalse(result);
        facade1.logout();

    }

    /*
     * Cases checked:
     * 1. buy cart with guest user
     * 2. buy cart with assigned user
     * 3. buy cart with empty cart
     */
    @Test
    void buy_cart() {
        boolean result;
        facade1.add_product_to_cart(1, 1, 1);
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with guest user
        assertFalse(result);
        facade1.login("check1234@email.com", "pass3Chec");
        facade1.add_product_to_cart(1, 1, 1);
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with assigned user
        assertFalse(result);
        facade1.logout();
        facade1.login("check12345@email.com", "pass3Chec");
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with empty cart
        assertTrue(result);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. view purchase history with guest user no purchases
     * 2. view purchase history with assigned user no purchases
     * 3. view purchase history with guest user that had purchased
     * 4. view purchase history with assigned user that had purchased
     */
    @Test
    void view_user_purchase_history() {
        boolean result;
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with guest user no purchases
        assertTrue(result);
        facade1.add_product_to_cart(1, 1, 1);
        facade1.buy_cart("credit", "address");
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with guest user that had purchased
        assertTrue(result);
        facade1.login("check12345@email.com", "pass3Chec");
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with assigned user that had purchased
        assertFalse(result);
        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.view_user_purchase_history()); // view purchase history with assigned user no purchases
        assertFalse(result);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. unregister guest user
     * 2. unregister assigned user
     * 3. unregister guest user after assigned user unregistered
     */
    @Test
    void unregister() {
        boolean result;
        result = check_was_exception(facade1.unregister("pass3Chec")); // unregister guest user
        assertTrue(result);
        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.unregister("pass3Chec")); // unregister assigned user
        assertFalse(result);
        result = check_was_exception(facade1.unregister("pass3Chec")); // unregister guest user after assigned user unregistered
        assertTrue(result);
        facade1.register("check123456@email.com", "pass3Chec", "name", "last", birth_date);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. improve security with no user connected
     * 2. improve security with user connected
     */
    @Test
    void improve_security() {
        boolean result;
        result = check_was_exception(facade1.improve_security("pass3Chec", "Where were you born?", "Tel-Aviv")); // improve security with no user connected
        assertTrue(result);
        facade1.login("check123457@email.com", "pass3Chec");
        result = check_was_exception(facade1.improve_security("pass3Chec", "Where were you born?", "Tel-Aviv")); // improve security with no user connected
        assertFalse(result);
        facade1.logout();
    }

    /*
     * Cases checked:
     * 1. edit name with no user connected
     * 2. edit name with user connected
     * 3. edit name to empty name with user connected
     * 4. edit name to invalid name with user connected
     * 5. edit name of premium account with no security improvement
     */
    @Test
    void edit_name() {
        boolean result;

        result = check_was_exception(facade1.edit_name( "Eylon")); // edit name with no user connected
        assertTrue(result);

        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_name("Eylon")); // edit name with user connected
        assertFalse(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());

        result = check_was_exception(facade1.edit_name( "")); // edit name to empty name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());

        result = check_was_exception(facade1.edit_name("EylonintHamellonit")); // edit name to invalid name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());

        facade1.logout();

    }

    /*
     * Cases checked:
     * 1. edit last name with no user connected
     * 2. edit last name with user connected
     * 3. edit last name to empty last name with user connected
     * 4. edit last name to invalid last name with user connected
     */
    @Test
    void edit_last_name() {
        boolean result;
        result = check_was_exception(facade1.edit_last_name("Eylon")); // edit last name with no user connected
        assertTrue(result);

        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_last_name( "Eylon")); // edit last name with user connected
        assertFalse(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());

        result = check_was_exception(facade1.edit_last_name( "")); // edit last name to empty last name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());

        result = check_was_exception(facade1.edit_last_name( "EylonintHamellonit")); // edit last name to invalid last name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());

        facade1.logout();


    }

    /*
     * Cases checked:
     * 1. edit last name with no user connected
     * 2. edit last name with user connected- wrong security answer
     * 3. edit last name with user connected- correct security answer
     * 4. edit last name to empty last name with user connected
     * 5. edit last name to invalid last name with user connected
     */
    @Test
    void edit_last_name_premium() {
        boolean result;
        result = check_was_exception(facade1.edit_last_name_premium("Eylon", "Sasson")); // edit last name with no user connected
        assertTrue(result);
        facade1.login("heck1234578@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_last_name_premium( "Eylon", "Sade")); // edit last name with user connected- wrong security answer
        assertTrue(result);
        assertEquals("last", facade1.get_user_last_name().getValue());
        result = check_was_exception(facade1.edit_last_name_premium( "Eylon", "Sasson")); // edit last name with user connected- correct security answer
        assertFalse(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());
        result = check_was_exception(facade1.edit_last_name_premium( "", "Sasson")); // edit last name to empty last name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());
        result = check_was_exception(facade1.edit_last_name_premium( "EylonintHamellonit", "Sasson")); // edit last name to invalid last name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_last_name().getValue());
        facade1.logout();


    }

    /*
     * Cases checked:
     * 1. edit name with no user connected
     * 2. edit name with user connected- wrong security answer
     * 3. edit name with user connected- correct security answer
     * 4. edit name to empty name with user connected
     * 5. edit name to invalid name with user connected
     */
    @Test
    void edit_name_premium() {
        boolean result;
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sasson")); // edit name with no user connected
        assertTrue(result);
        facade1.login("heck1234578@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sade")); // edit name with user connected- wrong security answer
        assertTrue(result);
        assertEquals("name", facade1.get_user_name().getValue());
        result = check_was_exception(facade1.edit_name_premium( "Eylon", "Sasson")); // edit name with user connected- correct security answer
        assertFalse(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());
        result = check_was_exception(facade1.edit_name_premium( "", "Sasson")); // edit name to empty name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());
        result = check_was_exception(facade1.edit_name_premium("EylonintHamellonit", "Sasson")); // edit name to invalid name with user connected
        assertTrue(result);
        assertEquals("Eylon", facade1.get_user_name().getValue());
        facade1.logout();
    }


    /**
     * Cases checked:
     * 1. edit password with no user connected
     * 2. edit password with invalid new password
     * 3. edit password with empty new password
     * 4. edit password with wrong old password
     * 5. edit password with wrong security answer
     * 6. edit password
     * 7. edit password
     */
    @Test
    void edit_password_premium() {
        boolean result = check_was_exception(facade1.edit_password_premium("pass3Chec", "pass12CH", "Sasson")); // edit password with no user connected
        assertTrue(result);

        facade1.login("heck1234578@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_password_premium("pass3Chec", "pass12r", "Sasson")); // edit password with invalid new password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password_premium("pass3Chec", "", "Sasson")); // edit password with empty new password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password_premium("pass3ec", "pass12CH", "Sasson")); // edit password with invalid old password
        assertTrue(result);

        result = check_was_exception(facade1.edit_password_premium("pass3ec", "pass12CH", "Sade")); // edit password with wrong security answer
        assertTrue(result);

        facade1.logout();
        facade1.login("heck1234578@email.com", "pass3Chec");
        assertTrue(facade1.is_logged()); // checks that you can still login with old password

        result = check_was_exception(facade1.edit_password_premium("pass3Chec", "pass12rT", "Sasson")); // edit password
        assertFalse(result);
        facade1.logout();
        facade1.login("heck1234578@email.com", "pass12rT");
        assertTrue(facade1.is_logged()); // checks that you can login with new password

        result = check_was_exception(facade1.edit_password_premium("pass12rT", "pass3Chec", "Sasson")); // edit password
        assertFalse(result);
        facade1.logout();
        facade1.login("heck1234578@email.com", "pass3Chec");
        assertTrue(facade1.is_logged()); // checks that you can login with new password

        facade1.logout();
    }

    /**
     * Cases checked:
     * 1. get question with no user connected
     * 2. get question with premium account user connected
     * 3. get question with regular user connected
     */
    @Test
    void get_user_security_question(){
        boolean result;

        result = check_was_exception(facade1.get_user_security_question()); // get question with no user connected
        assertTrue(result);

        facade1.login("heck1234578@email.com", "pass3Chec");

        Response response = facade1.get_user_security_question();
        result = check_was_exception(response); // get question with premium account user connected
        assertFalse(result);
        assertEquals("What was your mother's maiden name?", response.getValue());

        facade1.logout();
        facade1.login("check1234@email.com", "pass3Chec");

        result = check_was_exception(facade1.get_user_security_question()); // get question with regular user connected
        assertTrue(result);

        facade1.logout();

    }

    /**
     * trying to register num_of_threads with same email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads
     * 3. running all the threads in parallel
     * 4. make sure that the email is registered & there was num_of_threads-1 exceptions
     */
    @Test
    void parallel_registration_same_user() {
        //arrange
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);

        //1
        assertFalse(uc.contains_user_email(email)); //make sure that the user is not already registered
        //2
        for (int i = 0; i < num_of_threads; i++) { //initializing all the threads
            threads.add(new Thread(() -> {
                MarketFacade mf = new MarketFacade(pa, sa);
                Response res = mf.register(email, password, "gal", "brown", birth_date);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
            }));
        }
        //3
        start_threads(threads);
        join_threads(threads);
        //4+5
        assertTrue(uc.contains_user_email(email), "failed to register user");
        assertTrue(num_of_exceptions.get() == num_of_threads - 1, "parallel bug");
    }


    /**
     * trying to register num_of_threads with different email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads - that will register the system.
     * 3. running all the threads in parallel
     * 4. make sure that all the emails is registered & there was 0 exceptions
     */
    @Test
    void parallel_registration_different_users() {
        // arrange
        String ending = "@gmail.com";
        String starting = "somthing";
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);

        //1 + 2
        for (int i = 0, num = 3; i < num_of_threads; i++, num++) { //initializing all the threads
            String email = starting + num + ending;
            threads.add(new Thread(() -> {
                MarketFacade mf = new MarketFacade(pa, sa);
                assertFalse(uc.contains_user_email(email)); //make sure that the user is not already registered
                Response res = mf.register(email, password, "gal", "brown", birth_date);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
            }));
        }

        //3
        start_threads(threads);
        join_threads(threads);

        //4
        for (int i = 0, num = 3; i < num_of_threads; i++, num++) {
            assertTrue(uc.contains_user_email(starting + num + ending), "failed to register user");
        }
        assertTrue(num_of_exceptions.get() == 0, "parallel bug");
    }


    /**
     * trying to login num_of_threads with same email:
     * 1. make sure the email is not registered already.
     * 2. creating num_of_threads threads
     * 3. running all the threads in parallel
     * 4. make sure that the email is registered & there was num_of_threads-1 exceptions
     */
    @Test
    void parallel_logging_same_user() {
        //arrange
        List<Thread> threads = new ArrayList<>();
        AtomicInteger num_of_exceptions = new AtomicInteger(0);
        AtomicInteger num_of_logged_after_operation = new AtomicInteger(0);
        MarketFacade mf = new MarketFacade(pa, sa);
        String reg_email = "loginsame@gmail.com";
        mf.register(reg_email, password, "gal", "brown", birth_date);
        assertTrue(uc.contains_user_email(reg_email), "failed to register user");
        mf.logout();
        assertFalse(mf.is_logged(), "user is logged in before operation");

        //initializing all the threads
        for (int i = 0; i < num_of_threads; i++) {
            threads.add(new Thread(() -> {
                MarketFacade mf1 = new MarketFacade(pa, sa);
                if (mf1.is_logged()) assertTrue(false, "account already logged in before operation");
                Response res = mf1.login(reg_email, password);
                if (check_was_exception(res)) num_of_exceptions.getAndIncrement();
                if (mf1.is_logged()) num_of_logged_after_operation.incrementAndGet();
            }));
        }
        //3
        start_threads(threads);
        join_threads(threads);
        //4+5
        assertTrue(num_of_exceptions.get() == num_of_threads - 1, "parallel bug");
        assertTrue(num_of_logged_after_operation.get() == 1, num_of_logged_after_operation.get() + " logging operation succeed");
    }


}
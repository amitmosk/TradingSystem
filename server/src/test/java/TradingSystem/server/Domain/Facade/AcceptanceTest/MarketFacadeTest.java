package TradingSystem.server.Domain.Facade.AcceptanceTest;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private UserController uc;
    private PaymentAdapter pa;
    private SupplyAdapter sa;
    private String email;
    private String password;
    private String birth_date;
    private final int num_of_threads = 100;


    private boolean check_was_exception(Response response) {
        return response.WasException();
    }

    @BeforeEach
    void setUp() {
        birth_date =  LocalDate.now().minusYears(30).toString();
        PaymentAdapter paymentAdapter = new PaymentAdapterImpl();
        SupplyAdapter supplyAdapter = new SupplyAdapterImpl();
        this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
        this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
        facade1.register("check1234@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.open_store("Checker Store");
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("check_check");
        facade1.add_product_to_store(1, 20, "CheckItem", 10.0, "checker", arraylist);
        facade1.logout();
        facade1.register("check12345@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.add_product_to_cart(1, 1, 1);
        facade1.buy_cart("credit", "address");
        facade1.logout();
        facade1.register("check123456@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.logout();
        facade1.register("check123457@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.logout();
        facade1.register("heck1234578@email.com", "pass3Chec", "name", "last",birth_date);
        facade1.improve_security("pass3Chec", "What was your mother's maiden name?", "Sasson");
        facade1.logout();

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

    // --------------------------------------------------------------------------------------------------------


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
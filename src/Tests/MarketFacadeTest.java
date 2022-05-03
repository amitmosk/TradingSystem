package Tests;

import Domain.ExternSystems.PaymentAdapterImpl;
import Domain.ExternSystems.SupplyAdapterImpl;
import Domain.Facade.MarketFacade;
import Domain.Utils.Response;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MarketFacadeTest {

    private MarketFacade facade1;
    private MarketFacade facade2;

    private boolean check_was_exception(String r) {
        Response response = new Gson().fromJson(r, Response.class);
        return response.WasException();
    }

    @BeforeEach
    void setUp(){
        this.facade1 = new MarketFacade(new PaymentAdapterImpl(), new SupplyAdapterImpl());
        this.facade2 = new MarketFacade(new PaymentAdapterImpl(), new SupplyAdapterImpl());
        facade1.register("check1234@email.com", "pass3Chec", "name","last");
        facade1.open_store("Checker Store");
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("check_check");
        facade1.add_product_to_store(1, 20, "CheckItem", 10.0, "checker", arraylist);
        facade1.logout();
        facade1.register("check12345@email.com", "pass3Chec", "name","last");
        facade1.add_product_to_cart(1,1,1);
        facade1.buy_cart("credit", "address");
        facade1.logout();
        facade1.register("check123456@email.com", "pass3Chec", "name","last");
        facade1.logout();
        facade1.register("check123457@email.com", "pass3Chec", "name","last");
        facade1.logout();

    }

    /*
     * Cases checked:
     * 1. regular register
     * 2. register with registered user from different facade
     * 3. register with registered user from same facade
     * 4. register with registered user from same facade while logged in
     */
    static Stream<Arguments> user_info_provider1() {
        return Stream.of(
                arguments("check1@email.com", "pass3Chec", "name","last"),
                arguments("check2@email.com", "pass1Chec", "name","last"),
                arguments("check3@email.com", "Ch3ckPsw0rd", "checker", "checkcheck")
        );
    }
    @ParameterizedTest
    @MethodSource("user_info_provider1")
    void register(String email, String pw, String name, String lastName) {
        boolean result = check_was_exception(facade1.register(email, pw, name, lastName)); // regular register
        assertFalse(result);
        result = check_was_exception(facade2.register(email, pw, name, lastName)); // register with registered user from different facade
        assertTrue(result);
        facade1.logout();
        result = check_was_exception(facade1.register("check1@email.com", "pass3Chec", "name","last")); // register with registered user from same facade
        assertTrue(result);
        facade1.register("check12@email.com", "pass123Chec", "name","last");
        result = check_was_exception(facade1.register("check12@email.com", "pass123Chec", "name","last")); // register with registered user from same facade while logged in
        assertTrue(result);
        facade1.logout();
    }

    /*
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

    /*
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

    /*
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

    /*
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
        facade1.remove_product_from_cart(1,1);
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
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with guest user
        assertFalse(result);
        facade1.login("check1234@email.com", "pass3Chec");
        facade1.add_product_to_cart(1, 1, 1);
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with assigned user
        assertFalse(result);
        facade1.logout();
        facade1.login("check12345@email.com", "pass3Chec");
        result = check_was_exception(facade1.buy_cart("credit card", "address place")); // buy cart with empty cart
        assertFalse(result);
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
        facade1.add_product_to_cart(1,1,1);
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
        facade1.register("check123456@email.com", "pass3Chec", "name","last");
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
     */
    @Test
    void edit_name() {
        boolean result;
        result = check_was_exception(facade1.edit_name("pass3Chec", "Eylon")); // edit name with no user connected
        assertTrue(result);
        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_name("pass3Chec", "Eylon")); // edit name with user connected
        assertFalse(result);
        result = check_was_exception(facade1.edit_name("pass3Chec", "")); // edit name to empty name with user connected
        assertTrue(result);
        result = check_was_exception(facade1.edit_name("pass3Chec", "EylonintHamellonit")); // edit name to invalid name with user connected
        assertTrue(result);
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
        result = check_was_exception(facade1.edit_last_name("pass3Chec", "Eylon")); // edit last name with no user connected
        assertTrue(result);
        facade1.login("check123456@email.com", "pass3Chec");
        result = check_was_exception(facade1.edit_last_name("pass3Chec", "Eylon")); // edit last name with user connected
        assertFalse(result);
        result = check_was_exception(facade1.edit_last_name("pass3Chec", "")); // edit last name to empty last name with user connected
        assertTrue(result);
        result = check_was_exception(facade1.edit_last_name("pass3Chec", "EylonintHamellonit")); // edit last name to invalid last name with user connected
        assertTrue(result);
        facade1.logout();
    }

}
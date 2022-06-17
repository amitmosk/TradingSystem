package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.ExternalSystems.*;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;
import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static TradingSystem.server.Service.MarketSystem.tests_config_file_path;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UpdateCart {
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
    private String user_premium_security_email;
    private String user_password;
    private String user_founder_email;
    private String user_buyer_email;
    private String user_regular_email_1;
    private String user_regular_email_2;
    private String user_admin_email;
    private SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
    private PaymentInfo payment_info = new PaymentInfo("123","456","789","245","123","455");
    private PaymentAdapter paymentAdapter;
    private SupplyAdapter supplyAdapter;
    private String prodname = "";

    public UpdateCart(){
        try{
            MarketSystem marketSystem = new MarketSystem(tests_config_file_path, "");
            this.paymentAdapter = marketSystem.getPayment_adapter();
            this.supplyAdapter = marketSystem.getSupply_adapter();

            this.facade1 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade2 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade3 = new MarketFacade(paymentAdapter, supplyAdapter);
            this.facade4 = new MarketFacade(paymentAdapter, supplyAdapter);

            uc = UserController.get_instance();
            pa = new PaymentAdapterImpl();
            sa = new SupplyAdapterImpl();

            // users information
            user_buyer_email = "buyer@email.com";
            user_founder_email = "founder@email.com";
            user_regular_email_1 = "regular1@email.com";
            user_regular_email_2 = "regular2@email.com";
            user_admin_email = "admin@gmail.com";
            user_premium_security_email = "premiumSecurity@email.com";
            user_password = "pass3Chec";
            birth_date =  LocalDate.now().minusYears(30).toString();
            String first_name = "name";
            String last_name = "last";
            email = "somthing@gmail.com";
            password = "aA12345";

            facade1.register(user_founder_email, user_password, first_name, last_name,birth_date);
            facade2.register(user_buyer_email, user_password, first_name, last_name,birth_date);
            facade3.register(user_regular_email_1, user_password, first_name, last_name,birth_date);
            facade4.register(user_regular_email_2, user_password, first_name, last_name,birth_date);

            int id = open_store_get_id("Checker Store") ;
            add_prod_make_purchase_get_id(id);

            facade1.logout();
            facade2.logout();
            facade3.logout();
            facade4.logout();

            // register user with premium security
            facade1.register(user_premium_security_email, user_password, first_name, last_name,birth_date);
            facade1.improve_security(user_password, "What was your mother's maiden name?", "Sasson");
            facade1.logout();

            // add admin to  the system
            uc.add_admin(user_admin_email, user_password, "Barak", "Bahar");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private int open_store_get_id(String name){
        facade1.open_store(name);
        return num_of_stores();
    }
    private int num_of_stores(){
        Response res = facade1.get_all_stores();
        int stores_count = 0;
        if(res.getValue().getClass() == (new ArrayList<StoreInformation>()).getClass()) {
            stores_count = ((ArrayList<StoreInformation>) res.getValue()).size();
        }
        return stores_count;
    }
    private int add_prod_make_purchase_get_id(int sore_id){
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add("\n\ncheck_check\n\n");
        prodname += "l";
        int prod_id = StoreController.get_instance().getProduct_ids_counter();
        facade1.add_product_to_store(sore_id, 100, prodname, 10.0, "checker", new ArrayList<>());

        facade2.add_product_to_cart(sore_id, prod_id, 1);
        facade2.buy_cart(payment_info, supplyInfo);

        return prod_id;
    }
    private boolean check_was_exception(Response response) {
        return response.WasException();
    }
    private String make_assert_exception_message(String test, String test_case, boolean suppose_to_be_exception){
        String test_part = "Test: " + test + "\n";
        String case_part = "In test case: " + test_case + " ";
        if(suppose_to_be_exception)
            case_part = "No exception thrown " + case_part;
        else
            case_part = "Exception thrown " + case_part;

        return test_part + case_part;
    }

    //------------------------------- User cart operations --------------------------------------------------------------------------

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
        boolean suppose_to_throw = true;
        String test_name = "add_and_remove_product_from_cart";
        String message;

        message = make_assert_exception_message(test_name, "add product when no user is logged in", !suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product when no user is logged in
        assertFalse(result, message);

        message = make_assert_exception_message(test_name, "add product that was already added", suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product that was already added
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "remove product when no user is logged in", !suppose_to_throw);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product when no user is logged in
        assertFalse(result, message);

        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "add product when user is logged in", !suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 1, 1)); // add product when user is logged in
        assertFalse(result, message);

        message = make_assert_exception_message(test_name, "remove product when user is logged in", !suppose_to_throw);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product when user is logged in
        assertFalse(result, message);

        message = make_assert_exception_message(test_name, "add product that doesn't exist", suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, 1));  // add product that doesn't exist
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "remove product that doesn't exist in cart", suppose_to_throw);
        result = check_was_exception(facade1.remove_product_from_cart(1, 1)); // remove product that doesn't exist in cart
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "add product with negative quantity", suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, -1)); // add product with negative quantity
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "add product with larger quantity then possible", suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(1, 5, 11)); // add product with larger quantity then possible
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "add product from a store that doesn't exist", suppose_to_throw);
        result = check_was_exception(facade1.add_product_to_cart(30, 1, 1));  // add product from a store that doesn't exist
        assertTrue(result, message);

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
        boolean suppose_to_throw = true;
        String test_name = "edit_product_quantity_in_cart";
        String message;

        message = make_assert_exception_message(test_name, "edit quantity when no product is in cart", suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 1)); // edit quantity when no product is in cart
        assertTrue(result, message);

        facade1.login(user_founder_email, user_password);
        facade1.add_product_to_cart(1, 1, 1);

        message = make_assert_exception_message(test_name, "edit quantity when user is logged in", !suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 1)); // edit quantity when user is logged in
        assertFalse(result, message);

        message = make_assert_exception_message(test_name, "edit quantity of a product that doesn't exist", suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, 1));  // edit quantity of a product that doesn't exist
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit quantity of a product from a store that doesn't exist", suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 1, 0)); // edit quantity of a product from a store that doesn't exist
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit quantity of product to negative number", suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, -1)); // edit to negative quantity
        assertTrue(result, message);

        message = make_assert_exception_message(test_name, "edit quantity of product to zero", suppose_to_throw);
        result = check_was_exception(facade1.edit_product_quantity_in_cart(1, 5, 11)); // edit to zero quantity
        assertTrue(result, message);

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
        boolean suppose_to_throw = true;
        String test_name = "view_user_cart";
        String message;

        message = make_assert_exception_message(test_name, "view purchase history with guest user no purchases", !suppose_to_throw);
        result = check_was_exception(facade1.view_user_cart()); // view cart with guest user
        assertFalse(result, message);

        facade1.login(user_founder_email, user_password);

        message = make_assert_exception_message(test_name, "view purchase history with guest user no purchases", !suppose_to_throw);
        result = check_was_exception(facade1.view_user_cart()); // view cart with assigned user
        assertFalse(result, message);

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
        boolean suppose_to_throw = true;
        String test_name = "buy_cart";
        String message;

        facade1.add_product_to_cart(1, 1, 1);

        message = make_assert_exception_message(test_name, "buy cart with guest user", !suppose_to_throw);
        result = check_was_exception(facade1.buy_cart(payment_info, supplyInfo)); // buy cart with guest user
        assertFalse(result, message);

        facade1.login(user_founder_email, user_password);
        facade1.add_product_to_cart(1, 1, 1);

        message = make_assert_exception_message(test_name, "buy cart with assigned user", !suppose_to_throw);
        result = check_was_exception(facade1.buy_cart(payment_info, supplyInfo)); // buy cart with assigned user
        assertFalse(result, message);

        facade1.logout();
        facade1.login(user_buyer_email, user_password);

        message = make_assert_exception_message(test_name, "buy cart with empty cart", suppose_to_throw);
        result = check_was_exception(facade1.buy_cart(payment_info, supplyInfo)); // buy cart with empty cart
        assertTrue(result, message);

        facade1.logout();
    }


}
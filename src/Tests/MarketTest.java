package Tests;

import Domain.StoreModule.StoreController;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.ExpectedException;

import static org.junit.jupiter.api.Assertions.*;

class MarketTest {
    private StoreController storeController;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        storeController = StoreController.get_instance();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void init_market() {
    }

    @org.junit.jupiter.api.Test
    void payment() {
    }

    @org.junit.jupiter.api.Test
    void supply() {
    }

    @org.junit.jupiter.api.Test
    void guest_login() {
    }

    @org.junit.jupiter.api.Test
    void login() {
    }

    @org.junit.jupiter.api.Test
    void logout() {
    }

    @org.junit.jupiter.api.Test
    void register() {
    }

    @org.junit.jupiter.api.Test
    void good_find_store_information() {
        //open store with store id = 1
        String s;
        try
        {
            s= storeController.find_store_information(1);
        }
        catch (Exception e)
        {
            s=null;
        }
        Assertions.assertTrue(s!=null);
    }

    @org.junit.jupiter.api.Test()
    void bad_find_store_information() {
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            storeController.find_store_information(-3);
        });

    }



    @org.junit.jupiter.api.Test
    void find_product_information() {
    }

    @org.junit.jupiter.api.Test
    void find_product_by_name() {
    }

    @org.junit.jupiter.api.Test
    void find_product_by_category() {
    }

    @org.junit.jupiter.api.Test
    void find_product_by_keyword() {
    }

    @org.junit.jupiter.api.Test
    void view_user_cart() {
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_cart() {
    }

    @org.junit.jupiter.api.Test
    void add_product_to_cart() {
    }

    @org.junit.jupiter.api.Test
    void edit_product_from_cart() {
    }

    @org.junit.jupiter.api.Test
    void buy_cart() {
    }

    @org.junit.jupiter.api.Test
    void open_store() {
    }

    @org.junit.jupiter.api.Test
    void add_review() {
    }

    @org.junit.jupiter.api.Test
    void rate_product() {
    }

    @org.junit.jupiter.api.Test
    void rate_store() {
    }

    @org.junit.jupiter.api.Test
    void send_request_to_store() {
    }

    @org.junit.jupiter.api.Test
    void send_complain() {
    }

    @org.junit.jupiter.api.Test
    void view_user_purchase_history() {
    }

    @org.junit.jupiter.api.Test
    void view_account_details() {
    }

    @org.junit.jupiter.api.Test
    void edit_account_details() {
    }

    @org.junit.jupiter.api.Test
    void add_security_personal_question() {
    }

    @org.junit.jupiter.api.Test
    void add_product_to_store() {
    }

    @org.junit.jupiter.api.Test
    void delete_product_from_store() {
    }

    @org.junit.jupiter.api.Test
    void edit_product() {
    }

    @org.junit.jupiter.api.Test
    void add_discount_rule() {
    }

    @org.junit.jupiter.api.Test
    void delete_discount_rule() {
    }

    @org.junit.jupiter.api.Test
    void add_purchase_rule() {
    }

    @org.junit.jupiter.api.Test
    void delete_purchase_rule() {
    }

    @org.junit.jupiter.api.Test
    void add_owner() {
    }

    @org.junit.jupiter.api.Test
    void add_manager() {
    }

    @org.junit.jupiter.api.Test
    void delete_owner() {
    }

    @org.junit.jupiter.api.Test
    void delete_manager() {
    }

    @org.junit.jupiter.api.Test
    void edit_manager_permissions() {
    }

    @org.junit.jupiter.api.Test
    void close_store() {
    }

    @org.junit.jupiter.api.Test
    void open_close_store() {
    }

    @org.junit.jupiter.api.Test
    void view_store_management_information() {
    }

    @org.junit.jupiter.api.Test
    void view_store_questions() {
    }

    @org.junit.jupiter.api.Test
    void manager_answer_question() {
    }

    @org.junit.jupiter.api.Test
    void view_store_purchases_history() {
    }

    @org.junit.jupiter.api.Test
    void close_store_permanently() {
    }

    @org.junit.jupiter.api.Test
    void delete_user_from_system() {
    }

    @org.junit.jupiter.api.Test
    void view_system_questions() {
    }

    @org.junit.jupiter.api.Test
    void admin_answer_question() {
    }

    @org.junit.jupiter.api.Test
    void get_system_statistics() {
    }
}
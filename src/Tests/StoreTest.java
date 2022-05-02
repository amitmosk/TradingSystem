package Tests;

import Domain.StoreModule.Appointment;
import Domain.StoreModule.Store.Store;
import Domain.StoreModule.Store.StoreManagersInfo;
import Domain.StoreModule.StorePermission;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {

    private Store store;
    private final String founder_email = "mosko@gmail.com";
    
    
    
    @BeforeEach
    void setUp() {
         store = new Store(659,founder_email, "toysRus");
         store.appoint_founder();
    }

    @AfterEach
    void tearDown() {
    }

    // ----------------------------------------


    @Test
    void success_add_store_rating() {
        store.add_store_rating("koko_rai", 5);
        Assert.assertEquals(store.get_store_rating(), 5);
    }

    @Test
    void fail_add_store_rating_manager() {
        boolean was_exception = false;
        try
        {
            store.add_store_rating(founder_email, 5);
        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        Assert.assertTrue("store member cant add rating to the store", was_exception);
    }

    @Test
    void fail_add_store_rating_illegal_input1() {
        boolean was_exception = false;
        try
        {
            store.add_store_rating("founder_email@maial,com", -1);
        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        Assert.assertTrue(was_exception);
    }

    @Test
    void fail_add_store_rating_illegal_input2() {
        boolean was_exception = false;
        try
        {
            store.add_store_rating("founder_email@maial,com", 6);
        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        Assert.assertTrue(was_exception);
    }


    @Test
    void success_appoint_founer() {
        store.appoint_founder();
        String founder_mail = store.getFounder_email();
        Assert.assertEquals("", founder_mail, founder_email);
        
    }


    @Test
    void success_close_store_permanently() {
        store.close_store_permanently();
        Assert.assertFalse(store.is_active());
    }

    @Test
    void success_close_store_temporarily() {
        try {
            store.close_store_temporarily(founder_email);
        } catch (IllegalAccessException e) {
            Assert.fail();
        }
        Assert.assertFalse(store.is_active());

    }

    @Test
    void fail_close_store_temporarily_noPermission() {
        boolean was_exception = false;
        try {
            store.close_store_temporarily("kokospa@gmail.com");
        } catch (IllegalAccessException e) {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        Assert.assertTrue("no permission", was_exception);

    }

    @Test
    void success_open_close_store() {
        try
        {
            store.close_store_temporarily(founder_email);
        } catch (IllegalAccessException e) {
            Assert.fail();
        }
        try
        {
            store.open_close_store(founder_email);
        } catch (IllegalAccessException e) {
            Assert.fail();
        }
        Assert.assertTrue(store.is_active());
    }

    @Test
    void fail_open_close_store() {
        boolean was_exception = false;
        try
        {
            store.open_close_store(founder_email);
        } catch (Exception e) {
            was_exception = true;
            System.out.println(e.getMessage());

        }
        Assert.assertTrue("the close was already open",was_exception);
    }

    @Test
    void add_question_AND_viewStoreQuestions() {
        try{
            store.add_question("josh@gmail.com", "why the store is always close!?");
            System.out.println(store.view_store_questions(founder_email));
        }
        catch (Exception e)
        {
            Assert.fail();
        }
        Assert.assertTrue(true);

    }

    @Test
    void good_answer_question() {
        try{
            store.add_question("josh@gmail.com", "why the store is always close!?");
            store.answer_question(founder_email, 1, "thank you for your question!");
            System.out.println(store.view_store_questions(founder_email));

        }
        catch (Exception e)
        {
            Assert.fail();
        }
        Assert.assertTrue(true);
    }

    @Test
    void fail_answer_question_NO_PERMISSION() {
        boolean was_exception = false;
        try{
            store.add_question("josh@gmail.com", "why the store is always close!?");
            store.answer_question("not_founder_email", 1, "thank you for your question!");
            System.out.println(store.view_store_questions(founder_email));

        }
        catch (Exception e)
        {
            was_exception = true;
            System.out.println(e.getMessage());
        }
        Assert.assertTrue(was_exception);
    }

    @Test
    void good_add_owner() {
        boolean was_exception = false;
        try
        {
            store.add_owner(founder_email, "amit@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }

    @Test
    void fail_add_owner_already_member() {
        boolean was_exception = false;
        try
        {
            store.add_manager(founder_email, "amaaait@walla.com");
            store.add_owner(founder_email, "amaaait@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("the user is already a manager in the store", was_exception);
    }

    @Test
    void fail_add_manager_by_manager() {
        boolean was_exception = false;
        try
        {
            store.add_manager(founder_email, "amaaait@walla.com");
            store.add_manager("amaaait@walla.com", "amaaait@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("manager cant appoint another manager", was_exception);
    }

    @Test
    void good_add_manager() {
        boolean was_exception = false;
        try
        {
            store.add_manager(founder_email, "amaaait@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }

    @Test
    void good_remove_manager() {
        boolean was_exception = false;
        try
        {
            store.add_manager(founder_email, "amaaait@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());
            store.remove_manager(founder_email, "amaaait@walla.com");
            System.out.println(info.get_management_information());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }

    @Test
    void good_remove_owner() {
        boolean was_exception = false;
        try
        {
            store.add_owner(founder_email, "amaaait@walla.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());
            store.remove_owner(founder_email, "amaaait@walla.com");
            System.out.println(info.get_management_information());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }

    @Test
    void fail_remove_owner_isNotAmember() {
        boolean was_exception = false;
        try
        {
            store.remove_owner(founder_email, "amaaait@walla.com");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("the asked user is not a store member",was_exception);
    }

    @Test
    void fail_remove_owner_isFounder() {
        boolean was_exception = false;
        try
        {
            store.remove_owner(founder_email, founder_email);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("cant remove founder appointment",was_exception);
    }

    @Test
    void fail_remove_owner_AppointBySomeoneElse() {
        boolean was_exception = false;
        try
        {
            store.add_owner(founder_email, "tom@gmail.com");
            store.add_owner(founder_email, "gal@gmail.com");
            store.remove_owner("tom@gmail.com", "gal@gmail.com");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("appoint by someone else",was_exception);
    }


    @Test
    void success_view_store_management_information() {
        boolean was_exception = false;
        try
        {
            store.add_owner(founder_email, "tom@gmail.com");
            store.add_owner("tom@gmail.com", "gal@gmail.com");
            store.add_manager("tom@gmail.com", "eylon@gmail.com");
            store.add_manager("gal@gmail.com", "lior@gmail.com");
            StoreManagersInfo info = store.view_store_management_information(founder_email);
            System.out.println(info.get_management_information());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }

    @Test
    void fail_view_store_management_information_NoPermission() {
        boolean was_exception = false;
        try
        {
            store.add_owner(founder_email, "tom@gmail.com");
            store.add_owner("tom@gmail.com", "gal@gmail.com");
            store.add_manager("tom@gmail.com", "eylon@gmail.com");
            store.add_manager("gal@gmail.com", "lior@gmail.com");
            StoreManagersInfo info = store.view_store_management_information("lior@gmail.com");
            System.out.println(info.get_management_information());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertTrue("no permissions", was_exception);
    }

    @Test
    void set_permissions() {
        boolean was_exception = false;
        LinkedList<StorePermission> permissions = new LinkedList<>();
        permissions.add(StorePermission.add_manager);
        permissions.add(StorePermission.add_item);
        permissions.add(StorePermission.add_owner);
        permissions.add(StorePermission.remove_item);
        try
        {
            store.add_owner(founder_email, "tom@gmail.com");
            store.set_permissions(founder_email, "tom@gmail.com", permissions);

            StoreManagersInfo info = store.view_store_management_information(founder_email);
            Appointment a = info.getMemberAppopintment("tom@gmail.com");
            Assert.assertTrue(a.has_permission(StorePermission.add_manager));
            Assert.assertTrue(a.has_permission(StorePermission.add_item));
            Assert.assertTrue(a.has_permission(StorePermission.add_owner));
            Assert.assertTrue(a.has_permission(StorePermission.remove_item));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        Assert.assertFalse(was_exception);
    }






}
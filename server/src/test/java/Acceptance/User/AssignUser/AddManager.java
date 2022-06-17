package Acceptance.User.AssignUser;

import TradingSystem.server.Domain.StoreModule.Appointment.StoreManagerType;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.UserModule.AssignUser;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddManager {

    /**
     * with no other managers in the store -> confirm automatically
     * with more managers -> after everyone confirm the candidate become owner
     * with more managers -> after one reject -> the candidate appointment closed.
     */

    @Test
    void happy_add_owner_appointment() {
        AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
        AssignUser owner = new AssignUser("owner@walla.com","12345678aA","owni","owner");
        Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
    }


    @Test
    void happy_manager_answer_appointment(){
        try
        {

            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            AssignUser owner1 = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            AssignUser owner2 = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            store.add_owner(founder, owner1);
            store.add_owner(founder, owner2);
            store.add_appointment_answer(owner1, owner2, true);
            StoreManagerType type = store.getStuffs_and_appointments().get(owner2).getType();
            assertEquals(type, StoreManagerType.store_owner);
        }
        catch (Exception e){

        }
    }

    @Test
    void delete_owner() {
    }

    @Test
    void add_manager() {
    }

    @Test
    void edit_manager_permissions() {
    }

    @Test
    void delete_manager() {
    }


}
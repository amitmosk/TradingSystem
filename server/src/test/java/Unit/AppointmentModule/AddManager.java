package Unit.AppointmentModule;
import static org.junit.jupiter.api.Assertions.*;
import TradingSystem.server.Domain.StoreModule.Appointment.StoreManagerType;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.UserModule.AssignUser;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

class AddManager {


    @Test
    void add_owner_appointment() {
        try{

            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            AssignUser owner = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            store.add_owner(founder, owner);
            assertEquals(store.getStuffs_and_appointments().get(owner).getType(), StoreManagerType.store_owner, "The owner is appointed successfully");
        }
        catch (Exception e){
            fail("add owner fail.");
        }
    }


    @Test
    void manager_answer_appointment(){
        try
        {

            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            AssignUser owner1 = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            AssignUser owner2 = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            store.add_owner(founder, owner1);
            store.add_owner(founder, owner2);
            boolean answer = store.add_appointment_answer(owner1, owner2, true);
            StoreManagerType type = store.getStuffs_and_appointments().get(owner2).getType();
            assertEquals(type, StoreManagerType.store_owner, "The owner appointed successfully");
            assertTrue(answer, "The answer of the manager got successfully");
        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    void success_appoint_founder() {
        try {
            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            store.appoint_founder();
            AssignUser founder1 = store.getFounder();
            boolean has_appointment = store.has_appointment(founder1);
            assertEquals(founder, store.getFounder(), "store has different founder");
            assertTrue(has_appointment, "founder does not have appointment");
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void fail_add_owner_already_member() {
        boolean was_exception = false;
        try {
            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            AssignUser owner1 = new AssignUser("owner@walla.com","12345678aA","owni","owner");
            store.add_manager(founder, owner1);
            store.add_owner(founder, owner1);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "the user is already a manager in the store");
    }

    @Test
    void fail_add_manager_by_manager() {
        boolean was_exception = false;
        try {
            AssignUser founder = new AssignUser("founder@walla.com","12345678aA","founi","founder");
            Store store = new Store(1, "hanot", founder, new AtomicInteger(1));
            AssignUser manager = new AssignUser("manager@walla.com","12345678aA","manager","manager");
            store.add_manager(founder, manager);
            store.add_manager(manager, manager);
            StoreManagersInfo info = store.view_store_management_information(founder);
            System.out.println(info.toString());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            was_exception = true;
        }
        assertTrue(was_exception, "manager cant appoint another manager");
    }




}
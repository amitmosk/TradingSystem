package Unit.BidModule;

import TradingSystem.server.Domain.StoreModule.Appointment.Appointment;
import TradingSystem.server.Domain.StoreModule.Appointment.StoreManagerType;
import TradingSystem.server.Domain.StoreModule.Bid.Bid;
import TradingSystem.server.Domain.StoreModule.Bid.BidStatus;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.StoreModule.Store.StoreManagersInfo;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import org.junit.jupiter.api.Test;

import javax.naming.NoPermissionException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class BidUTests {
//     TODO: remove the comments after merge with tom bid full stack.

    /**
     * this test for adding a new bid.
     */
    @Test
    void add_bid() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());
            assertEquals(bid.get_status(), BidStatus.open_waiting_for_answers, "bid status should be waiting");
            assertEquals(bid.get_offer_price(), 20," bid price should be 20");
            assertEquals(bid.getNegotiation_price(), -1, "bid negotiation price should be -1");

        }
        catch (Exception e){
            fail("bid fail");
        }
    }

    /**
     * this test for bid positive answer.
     */
    @Test
    void add_bid_positive_answer() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());
            bid.add_manager_answer("founder@walla.com", true, -1);
            assertEquals(bid.get_status(), BidStatus.closed_confirm, "bid status should be waiting");
            assertEquals(bid.getNegotiation_price(), -1, "bid negotiation price should be -1");
        }
        catch (Exception e){
            fail("bid fail.");
        }
    }

    /**
     * this test for bid negative answer.
     */

    @Test
    void add_bid_negative_answer() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());            bid.add_manager_answer("founder@walla.com", false, -1);
            assertEquals(bid.get_status(), BidStatus.closed_denied, "bid status should be waiting");
            assertEquals(bid.getNegotiation_price(), -1, "bid negotiation price should be -1");
        }
        catch (Exception e){
            fail("bid fail.");
        }
    }

    /**
     * this test for bid with negotiation price.
     */

    @Test
    void add_bid_negotiation_answer() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());            bid.add_manager_answer("founder@walla.com", true, 22);
            assertEquals(bid.get_status(), BidStatus.negotiation_mode, "bid status should be waiting");
            assertEquals(bid.getNegotiation_price(), 22, "bid negotiation price should be -1");
        }
        catch (Exception e){
            fail("bid fail.");
        }
    }

    /**
     * this test for bid with negotiation price & false answer.
     */

    @Test
    void add_bid_negative_negotiation_answer() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());            bid.add_manager_answer("founder@walla.com", false, 22);
            fail("cant combine false & negotiation price");
        }
        catch (IllegalArgumentException e){
            assertTrue(true, "waiting for IllegalArgumentException - cant combine false & negotiation price");
        }
        catch (Exception e){
            fail("bid fail.");
        }
    }


    /**
     * this test for answer with email who dont manage the store.
     */

    @Test
    void add_bid_email_wrong() {
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());

            bid.add_manager_answer("founder123@walla.com", true, -1);
            fail("bid should fail after get answer from not-member email");
        }
        catch (NoPermissionException e){
            assertTrue(true, "have to get NoPermissionException");
        }
        catch (Exception e){
            fail();
        }
    }

    /**
     * this test for denied answers after bid is closed.
     */
    @Test
    void answer_closed_bid(){
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser buyer = new AssignUser("owner@walla.com","12345678aA","buyer","buyer");
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());            bid.add_manager_answer("founder@walla.com", true, -1);
            assertEquals(bid.get_status(), BidStatus.closed_confirm, "bid status should be waiting");

            bid.add_manager_of_store("amit@gmail.com", true);
            bid.add_manager_answer("amit@gmail.com", true, -5);
        }
        catch (MarketException e){
            assertTrue(true, "MarketException expceted : cant add answer after bid is closed");
        }
        catch (Exception e){
            fail();
        }
    }


    /**
     * this test for waiting for all the managers answers.
     */

    @Test
    void wait_for_all_managers_answers(){
        try{
            List<String> managers_emails = new LinkedList<>();
            managers_emails.add("founder@walla.com");
            Product product = new Product("prod", 19, 25.5, "cate",managers_emails, 5);
            User user = new User();
            AssignUser founder = new AssignUser("founder@walla.com", "12345678aA", "founder", "fo");
            Store store = new Store(5, "amitStore", founder, new AtomicInteger(1));
            Bid bid = new Bid(1, 5, 20, managers_emails, product, user, founder , store.getStuffs_and_appointments());



            bid.add_manager_of_store("owner@walla.com", true);
            bid.add_manager_answer("founder@walla.com", true, -1);
            assertEquals(bid.get_status(), BidStatus.open_waiting_for_answers, "bid status should be waiting");
            bid.add_manager_answer("owner@walla.com", true, -1);
            assertEquals(bid.get_status(), BidStatus.closed_confirm, "bid status should be waiting");
        }
        catch (Exception e){
            fail("bid fail.");
        }

    }



}
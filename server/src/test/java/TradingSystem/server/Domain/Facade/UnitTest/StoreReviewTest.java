package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.StoreModule.Store.StoreReview;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreReviewTest {
    private StoreReview storeReview;
    private final String email = "mosko@gmail.com";

    @BeforeEach
    void setUp() {
        storeReview = new StoreReview();
    }

    @Test
    void add_good_0rating() {
        try {
            storeReview.add_rating(email, 0);
            int temp = storeReview.getRating().get(email);
            assertEquals(0, temp);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void add_good_1rating() {
        try{
        storeReview.add_rating(email, 1);
        int temp = storeReview.getRating().get(email);
        assertEquals(1, temp);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void add_good_2rating() {
        try{
        storeReview.add_rating(email, 2);
        int temp = storeReview.getRating().get(email);
        assertEquals(2, temp);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void add_good_5rating() {
        try{
        storeReview.add_rating(email, 5);
        int temp = storeReview.getRating().get(email);
        assertEquals(5, temp);
        }catch (Exception e){
            fail(e.getMessage());
        }
    }

    @Test
    void add_negative_rating() {
        boolean was_exception = false;
        String message = "";
        try {
            storeReview.add_rating(email, -5);
        }
        catch (Exception e){
            message = e.getMessage();
            was_exception = true;
        }
        System.out.println(message);
        assertTrue(was_exception, message);
    }

    @Test
    void add_negative_rating2() {
        boolean was_exception = false;
        String message = "";
        try {
            storeReview.add_rating(email, -1);
        }
        catch (Exception e){
            message = e.getMessage();
            was_exception = true;
        }
        System.out.println(message);
        assertTrue(was_exception, message);
    }

    @Test
    void add_outOfBound_rating2() {
        boolean was_exception = false;
        String message = "";
        try {
            storeReview.add_rating(email, 6);
        }
        catch (Exception e){
            message = e.getMessage();
            was_exception = true;
        }
        System.out.println(message);
        assertTrue(was_exception, message);
    }



    @Test
    void getRating() {
    }

    @Test
    void getAvgRating() {
    }
}




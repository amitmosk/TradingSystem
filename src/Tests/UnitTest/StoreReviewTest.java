package Tests.UnitTest;

import Domain.StoreModule.Store.StoreReview;
import org.junit.Assert;
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
        storeReview.add_rating(email, 0);
        int temp = storeReview.getRating().get(email);
        Assert.assertEquals(0, temp);
    }

    @Test
    void add_good_1rating() {
        storeReview.add_rating(email, 1);
        int temp = storeReview.getRating().get(email);
        Assert.assertEquals(1, temp);
    }

    @Test
    void add_good_2rating() {
        storeReview.add_rating(email, 2);
        int temp = storeReview.getRating().get(email);
        Assert.assertEquals(2, temp);
    }

    @Test
    void add_good_5rating() {
        storeReview.add_rating(email, 5);
        int temp = storeReview.getRating().get(email);
        Assert.assertEquals(5, temp);
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
        Assert.assertTrue(message, was_exception);
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
        Assert.assertTrue(message, was_exception);
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
        Assert.assertTrue(message, was_exception);
    }



    @Test
    void getRating() {
    }

    @Test
    void getAvgRating() {
    }
}




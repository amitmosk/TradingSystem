package Domain.StoreModule;

import java.util.HashMap;
import java.util.Map;

public class StoreReview {
    private Map<Integer, String> reviews; // user_id & review
    private Map<Integer, Integer> rating; // user_id & rating
    public StoreReview()
    {
        reviews = new HashMap<>();
        rating = new HashMap<>();
    }
    public void add_review(int user_id, String review) {
        this.reviews.put(user_id, review);
    }
    public void add_rating(int user_id, int rating) {
        this.rating.put(user_id, rating);
    }
}

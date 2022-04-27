package Domain.StoreModule.Store;

import java.util.HashMap;
import java.util.Map;

public class StoreReview {
    private Map<String, Integer> rating; // user_email & rating
    public StoreReview()
    {
        rating = new HashMap<>();
    }

    public void add_rating(String user_email, int rating) {
        this.rating.put(user_email, rating);
    }

    public Map<String, Integer> getRating() {
        return rating;
    }

}

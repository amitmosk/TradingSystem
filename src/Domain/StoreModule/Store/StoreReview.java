package Domain.StoreModule.Store;

import java.util.HashMap;
import java.util.Map;

public class StoreReview {
    private Map<String, Integer> rating; // user_email & rating
    private int avg_rating;
    public StoreReview()
    {
        avg_rating = 0;
        rating = new HashMap<>();
    }

    public void add_rating(String user_email, int rating) {
        if (rating < 0 || rating > 5)
            throw new IllegalArgumentException("rating range 1-5");
        this.rating.put(user_email, rating);
        int rating_size = this.rating.size();
        if (rating_size == 1)
            this.avg_rating = rating;
        else
            this.avg_rating = (avg_rating * this.rating.size() - 1 + rating) / (this.rating.size());
    }

    public Map<String, Integer> getRating() {
        return rating;
    }

    public int getAvgRating() {
        if (rating.size() == 0)
            return 3;
        else
            return avg_rating;
    }

}

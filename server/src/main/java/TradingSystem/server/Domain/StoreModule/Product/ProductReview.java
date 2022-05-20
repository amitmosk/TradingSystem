package TradingSystem.server.Domain.StoreModule.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductReview {
    private Map<String, String> reviews; // user_email & review
    private Map<String, Integer> rating; // user_email & rating
    public ProductReview()
    {
        reviews = new HashMap<>();
        rating = new HashMap<>();
    }

    public void add_review(String user_email, String review) {
        this.reviews.put(user_email, review);
    }
    public void add_rating(String user_email, int rating) {
        this.rating.put(user_email, rating);
    }

    // ----------------------------------- getters -------------------------------------

    public Map<String, Integer> getRating() {
        return rating;
    }

    public Map<String, String> getReviews() {
        return reviews;
    }

    // ------------------------------------ setters -------------------------------------

    public void setReviews(Map<String, String> reviews) {
        this.reviews = reviews;
    }

    public void setRating(Map<String, Integer> rating) {
        this.rating = rating;
    }
}

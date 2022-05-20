package TradingSystem.server.Domain.StoreModule.Store;

import java.util.Map;

public class StoreReviewInformation {
    private Map<String, Integer> rating; // user_email & rating

    // ------------------------------ constructors ------------------------------
    public StoreReviewInformation(StoreReview storeReview)
    {
        this.rating = storeReview.getRating();
    }

    public StoreReviewInformation() {
    }

    // ------------------------------ getters ------------------------------
    public Map<String, Integer> getRating() {
        return rating;
    }

    // ------------------------------ setters ------------------------------
    public void setRating(Map<String, Integer> rating) {
        this.rating = rating;
    }
}

package TradingSystem.server.Domain.StoreModule.Store;

import java.util.Map;

public class StoreReviewInformation {
    private Map<String, Integer> rating; // user_email & rating

    public StoreReviewInformation(StoreReview storeReview)
    {
        this.rating = storeReview.getRating();
    }
}

package Domain;

import Domain.StoreModule.StoreReview;

import java.util.Map;

public class StoreReviewInformation {
    private Map<String, String> reviews; // user_email & review
    private Map<String, Integer> rating; // user_email & rating

    public StoreReviewInformation(StoreReview storeReview)
    {
        this.reviews = storeReview.getReviews();
        this.rating = storeReview.getRating();
    }
}

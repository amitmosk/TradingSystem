package Domain;

import Domain.StoreModule.ProductReview;

import java.util.Map;

public class ProductReviewInformation {
    private Map<String, String> reviews; // user_email & review
    private Map<String, Integer> rating; // user_email & rating

    public ProductReviewInformation(ProductReview productReview)
    {
        this.reviews = productReview.getReviews();
        this.rating = productReview.getRating();
    }
}

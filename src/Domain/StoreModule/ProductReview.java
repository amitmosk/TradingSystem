package Domain.StoreModule;

import java.util.Map;

public class ProductReview {
    private Map<Integer, String> reviews; // user_id & review
    public void add_review(int user_id, String review) {
        this.reviews.put(user_id, review);
    }
}

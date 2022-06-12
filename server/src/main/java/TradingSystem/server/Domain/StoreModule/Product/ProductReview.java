package TradingSystem.server.Domain.StoreModule.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_review_id;

    @ElementCollection
    @CollectionTable(name = "reviews")
    @MapKeyColumn(name="user_email")
    @Column(name="review")
    private Map<String, String> reviews; // user_email & review

    @ElementCollection
    @CollectionTable(name = "ratings")
    @MapKeyColumn(name="user_email")
    @Column(name="rating")
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

    public void setProduct_review_id(Long product_review_id) {
        this.product_review_id = product_review_id;
    }

    public Long getProduct_review_id() {
        return product_review_id;
    }


}


export class ProductReview  {
    
    
    constructor(data) {
        // private Map<String, String> reviews; // user_email & review
        // private Map<String, Integer> rating; // user_email & rating
        this.reviews = data.reviews;
        this.rating = data.rating;

    }
    
    
    static create(rating) {
        return new ProductReview({
            reviews: reviews,
            rating: rating,

        })

    }
}

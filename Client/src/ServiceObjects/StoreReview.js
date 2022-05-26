
export class StoreReview  {
    
    
    constructor(data) {
        //private Map<String, Integer> rating;
        this.rating = data.rating;

    }
    
    
    static create(rating) {
        return new StoreReview({
            rating: rating,

        })

    }
}

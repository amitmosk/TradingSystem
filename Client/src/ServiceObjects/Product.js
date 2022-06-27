
import { ProductReview } from "./ProductReview";
export class Product  {
    constructor(data) {
        this.product_id = data.product_id;
        this.name = data.name;
        this.category = data.category;
        this.key_words = data.key_words;
        this.original_price = data.original_price;
        this.productReview = data.productReview;
        this.quantity = data.quantity;
        this.store_id = data.store_id;
    }

    static create(product_id, name, category, key_words, price, productReview,quantity,store_id) {
        return new Product({
            product_id: product_id,
            name: name,
            category:category,
            key_words:key_words,
            original_price:price,
            productReview:productReview,
            quantity:quantity,
            store_id:store_id,
        })
    }
}

import { ProductReview } from "./ProductReview";
export class Product  {
    constructor(data) {
        this.product_id = data.product_id;
        this.name = data.name;
        this.category = data.category;
        this.key_words = data.key_words;
        this.price = data.price;
        this.productReview = new ProductReview(data.productReview)
    }

    static create(product_id, name, category, key_words, price, productReview) {
        return new Product({
            product_id: product_id,
            name: name,
            category:category,
            key_words:key_words,
            price:price,
            productReview:productReview
        })
    }
}
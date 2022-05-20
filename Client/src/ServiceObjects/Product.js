
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


    
    static create( name,  product_id,  price,  category,  key_words, productReview) {
        return new Product({
            name: name,
            product_id: product_id,
            price:price,
            category:category,
            key_words:key_words,
            productReview:productReview
        })
    }
    
    get name1() {return this.name;}
    get category1() {return this.category}
    get price1() {return this.price}
    get productReview1() {return this.productReview}


    

}
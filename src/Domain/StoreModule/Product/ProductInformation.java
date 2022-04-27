package Domain.StoreModule.Product;

import java.util.List;

public class ProductInformation {
    private String name;
    private double price;
    private ProductReviewInformation productReviewInformation;
    private String category;
    private List<String> key_words;

    public ProductInformation(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.productReviewInformation = new ProductReviewInformation(product.getProductReview());
        this.category = product.getCategory();
        this.key_words = product.getKey_words();
    }
}

package TradingSystem.server.Domain.StoreModule.Product;

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

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ProductReviewInformation getProductReviewInformation() {
        return productReviewInformation;
    }

    public String getCategory() {
        return category;
    }

    public List<String> getKey_words() {
        return key_words;
    }
}

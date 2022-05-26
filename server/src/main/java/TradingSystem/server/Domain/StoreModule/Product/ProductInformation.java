package TradingSystem.server.Domain.StoreModule.Product;

import java.util.List;

public class ProductInformation {
    private int product_id;
    private String name;
    private String category;
    private List<String> key_words;
    private double price;
    private ProductReview productReview;
    private int quantity;
    private int store_id;

    public ProductInformation() {
    }

    public ProductInformation(Product prod, int quantity) {
        this.product_id = prod.getProduct_id();
        this.name = prod.getName();
        this.category = prod.getCategory();
        this.key_words = prod.getKey_words();
        this.price = prod.getPrice();
        this.productReview = prod.getProductReview();
        this.store_id = prod.getStore_id();
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<String> getKey_words() {
        return key_words;
    }

    public double getPrice() {
        return price;
    }

    public ProductReview getProductReview() {
        return productReview;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setKey_words(List<String> key_words) {
        this.key_words = key_words;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }
}

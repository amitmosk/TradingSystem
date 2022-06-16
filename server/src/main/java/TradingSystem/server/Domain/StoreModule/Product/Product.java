package TradingSystem.server.Domain.StoreModule.Product;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.Utils.Exception.ProductCreatingException;

import javax.persistence.*;
import java.util.List;

@Entity
public class Product {
    @Id
    private int product_id;
    private String name;
    private String category;
    @ElementCollection
    private List<String> key_words;
    private double original_price;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private ProductReview productReview;
    private int store_id;

    // --------------------------------- constructors --------------------------------------------
    public Product(String name, int product_id, double original_price, String category, List<String> key_words, int store_id) throws ProductCreatingException {
        this.name = name;
        this.product_id = product_id;
        if (original_price <= 0)
            throw new ProductCreatingException("price must be more then 0");
        this.original_price = original_price;
        this.productReview = new ProductReview();
        this.category = category;
        this.key_words = key_words;
        this.store_id = store_id;
        HibernateUtils.persist(this);
    }

    public Product() {
    }

    // -- Public Methods

    public void add_review(String user_email, String review) {
        this.productReview.add_review(user_email, review);
    }

    public void add_rating(String user_email, int rating) {
        this.productReview.add_rating(user_email, rating);
    }


    // -- Getters


    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public List<String> getKey_words() {
        return key_words;
    }

    public ProductReview getProductReview() {
        return productReview;
    }

    public String getCategory() {
        return category;
    }

    // -- Setters
    public void setProduct_id(int product_id) {
        this.product_id = product_id;
        HibernateUtils.merge(this);
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
        HibernateUtils.merge(this);
    }

    public void setProductReview(ProductReview productReview) {
        this.productReview = productReview;
    }

    public void setCategory(String category) {
        this.category = category;
        HibernateUtils.merge(this);
    }

    public void setKey_words(List<String> key_words) {
        this.key_words = key_words;
        HibernateUtils.merge(this);
    }

    public void setName(String name) {
        this.name = name;
        HibernateUtils.merge(this);
    }

    public void remove() {
        HibernateUtils.remove(this);
        HibernateUtils.remove(this.productReview);
    }
}

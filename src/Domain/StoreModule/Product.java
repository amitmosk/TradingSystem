package Domain.StoreModule;

import java.util.List;

public class Product {
    private String name;
    private int store_id;
    private int product_id;
    private double price;
    private int quantity;
    private ProductReview productReview;
    private String category;
    private List<String> key_words;


    public boolean is_avaliable() {
        return quantity>0;
    }

    @Override
    public String toString() {
        String store_name = "----";
        StringBuilder info = new StringBuilder();
        info.append("Product: "+ this.name+"\n");
        info.append("\tStore:  "+ store_name+"\n");
        info.append("\tPrice: "+ this.price+"\n");
        info.append("\tquantity: "+ this.quantity+"\n");
        info.append("\tProduct Review: "+ this.productReview+"\n");
        info.append("\tCategory: "+ this.category+"\n");
        info.append("\tKey words: "+ this.key_words+"\n");

        return info.toString();

    }


    //getters

    public String getName() {
        return name;
    }

    public int getStore_id() {
        return store_id;
    }

    public double getPrice() {
        return price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getQuantity() {
        return quantity;
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
}

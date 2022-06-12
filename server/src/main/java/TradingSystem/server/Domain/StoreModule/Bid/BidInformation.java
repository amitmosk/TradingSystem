package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;

public class BidInformation {
    private ProductInformation product_info;
    private double price;
    private int quantity;
    private String status;

    public BidInformation() {
    }

    public BidInformation(Bid bid) {
        this.price = bid.get_offer_price();
        this.quantity = bid.getQuantity();
        this.status = bid.get_status().toString();
        this.product_info = new ProductInformation(bid.get_product(), quantity);
    }

    public ProductInformation getProduct_info() {
        return product_info;
    }

    public void setProduct_info(ProductInformation product_info) {
        this.product_info = product_info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
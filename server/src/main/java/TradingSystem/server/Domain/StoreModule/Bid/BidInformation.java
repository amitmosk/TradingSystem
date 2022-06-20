package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;

public class BidInformation {

    private ProductInformation product_info;
    private double price;
    private int quantity;
    private String status;
    private String buyer_mail;
    private int id;

    public BidInformation() {
    }

    public BidInformation(Bid bid, int id) throws MarketException {
        this.price = bid.get_offer_price();
        this.quantity = bid.getQuantity();
        this.status = bid.get_status().toString();
        this.product_info = new ProductInformation(bid.getProduct(), quantity, bid.getProduct().getOriginal_price());
        String user_email = "Guest";
        try
        {
            user_email = bid.getBuyer().user_email();
        }
        catch (Exception e)
        {

        }
        this.buyer_mail = user_email;
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getBuyer_mail() {
        return buyer_mail;
    }

    public void setBuyer_mail(String buyer_mail) {
        this.buyer_mail = buyer_mail;
    }
}

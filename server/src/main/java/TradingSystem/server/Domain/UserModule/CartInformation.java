package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.StoreModule.Store.StoreInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartInformation {
    private List<ProductInformation> products;
    private double price;

    public CartInformation(){}


    public CartInformation(HashMap<StoreInformation, Basket> basketHashMap){
        this.price = 0;
        this.products = new ArrayList<>();
        for (Map.Entry<StoreInformation,Basket> basket: basketHashMap.entrySet()){
            products.addAll(basket.getKey().getInventory());
            this.price += basket.getValue().getTotal_price();
        }
    }

    public List<ProductInformation> getProducts() {
        return products;
    }

    public double getPrice() {
        return price;
    }

    public void setProducts(List<ProductInformation> products) {
        this.products = products;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

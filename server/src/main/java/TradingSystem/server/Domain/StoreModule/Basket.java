package TradingSystem.server.Domain.StoreModule;

import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.BasketException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;
import TradingSystem.server.Domain.Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

public class Basket {
    private Pair<String, Integer> basket_id; // <user_email, store_id>
    private Map<Product, Integer> products_and_quantities; //  product & quantity

    public Basket(int store_id, String buyer_email) {
        this.basket_id = new Pair<>(buyer_email, store_id);
        products_and_quantities = new HashMap<>();
    }

    public void changeQuantity(Product product, int quantity) throws MarketException {
        if (quantity < 1)
            throw new BasketException("Basket.changeQuantity: the quantity less then 1. quantity = "+quantity);
        if(!this.products_and_quantities.containsKey(product))
        {
            throw new BasketException("Basket.changeQuantity: Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.put(product, quantity);
    }

    public boolean isEmpty(){
        return this.products_and_quantities.isEmpty();
    }

    public void removeProduct(Product product) throws MarketException {
        if(!this.products_and_quantities.containsKey(product))
        {
            throw new BasketException("Basket.removeProduct: Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.remove(product);
    }

    public void addProduct(Product p, int quantity)throws MarketException {
        if(this.products_and_quantities.containsKey(p))
        {
            throw new BasketException("Product already in the basket - product id: "+p.getProduct_id());
        }
        this.products_and_quantities.put(p, quantity);
    }

    //----------------------------------------Getters--------------------------------------------------

    public Map<Integer, Integer> get_productsIds_and_quantity() {
        Map<Integer, Integer> productsIds_and_quantity = new HashMap<>();
        for(Product p: this.products_and_quantities.keySet())
        {
            productsIds_and_quantity.put(p.getProduct_id(), this.products_and_quantities.get(p));
        }
        return productsIds_and_quantity;
    }
    public int getQuantity(int product_id) throws MarketException {
        Product p = this.get_product_by_product_id(product_id);
        return  this.products_and_quantities.get(p);
    }
    public double getPrice(int product_id, int quantity) throws MarketException {
        Product p = this.get_product_by_product_id(product_id);
        return p.getPrice() * quantity;
        //TODO we return price without discount policy - version 2
    }
    public String getName(int product_id) throws MarketException {
        Product p = this.get_product_by_product_id(product_id);
        return p.getName();

    }
    public List<Integer> getProductsId() {
        List<Integer> product_ids = new ArrayList<>();
        for (Product p:this.products_and_quantities.keySet())
        {
            product_ids.add(p.getProduct_id());
        }
        return product_ids;

    }
    private Product get_product_by_product_id(int product_id) throws MarketException{
        for (Product product : this.products_and_quantities.keySet()){
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new BasketException("Basket.get_product_by_product_id: " +
                "Product does not exist in the basket - basket id: "+basket_id+"  , product id: "+product_id);
    }
    public int getStore_id() {
        return basket_id.getSecond();
    }
    public String get_buyer_email() {
        return basket_id.getFirst();
    }
    public Map<Product, Integer> getProducts_and_quantities() {
        return products_and_quantities;
    }
    public double getTotal_price() {
        double price=0;
        for (Product p: this.products_and_quantities.keySet())
        {
            price += p.getPrice() * this.products_and_quantities.get(p);
        }
        return price;
    }


    public Map<Integer, String> getProducts_and_names() {
        Map<Integer, String> productsIds_and_names = new HashMap<>();
        for(Product p: this.products_and_quantities.keySet())
        {
            productsIds_and_names.put(p.getProduct_id(), p.getName());
        }
        return productsIds_and_names;
    }
}
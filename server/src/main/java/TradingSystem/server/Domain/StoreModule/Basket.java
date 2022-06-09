package TradingSystem.server.Domain.StoreModule;

import TradingSystem.server.DAL.Repo;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.BasketException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;
import TradingSystem.server.Domain.Utils.Pair;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.BackingStoreException;

@Entity
public class Basket implements Serializable {
    @Id
    @OneToOne
    private Pair basket_id; // <user_email, store_id>

    @ElementCollection
    @MapKeyColumn(name = "product_id") // the key column
    @Column(name = "quantity")
    private Map<Product, Integer> products_and_quantities; //  product & quantity

    // ------------------------------ constructors ------------------------------
    public Basket(int store_id, String buyer_email) {
        this.basket_id = new Pair(buyer_email, store_id);
        products_and_quantities = new HashMap<>();
        Repo.persist(this.basket_id);
    }

    public Basket() {
    }

    // ------------------------------ getters ------------------------------
    public Pair getBasket_id() {
        return basket_id;
    }

    // ------------------------------ setters ------------------------------
    public void setBasket_id(Pair basket_id) {
        this.basket_id = basket_id;
    }

    public void setProducts_and_quantities(Map<Product, Integer> products_and_quantities) {
        this.products_and_quantities = products_and_quantities;
    }

    // ------------------------------ methods ------------------------------

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
        if(quantity < 1)
            throw new WrongPermterException("cannot add quantity lower then 1");
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
        // TODO : calculate according discount policy
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

package TradingSystem.server.Domain.StoreModule;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.BasketException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;
import TradingSystem.server.Domain.Utils.Pair;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Basket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Pair basket_id; // <user_email, store_id>

    @ElementCollection
    @MapKeyJoinColumn(name = "product_id") // the key column
    @Column(name = "quantity")
    private Map<Product, Integer> products_and_quantities; //  product & quantity

    @ElementCollection
    @MapKeyClass(value = Product.class)
    @MapKeyJoinColumn(name = "product_id") // the key column
    @Column(name = "price")
    private Map<Product, Double> products_and_price_per_unit; //  product & quantity

    // ------------------------------ constructors ------------------------------
    public Basket(int store_id, String buyer_email) {
        this.basket_id = new Pair(buyer_email, store_id);
        products_and_quantities = new HashMap<>();
        products_and_price_per_unit = new HashMap<>();
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
            throw new BasketException("the quantity less then 1, quantity = "+quantity);
        if(!this.products_and_quantities.containsKey(product))
        {
            throw new BasketException("Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.put(product, quantity);
        HibernateUtils.merge(this);
    }

    public boolean isEmpty(){
        return this.products_and_quantities.isEmpty();
    }

    public void removeProduct(Product product) throws MarketException {
        if(!this.products_and_quantities.containsKey(product) || !this.products_and_price_per_unit.containsKey(product))
        {
            throw new BasketException("Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.remove(product);
        this.products_and_price_per_unit.remove(product);
        HibernateUtils.merge(this);
    }

    public void addProduct(Product p, int quantity, double price_per_unit) throws MarketException {
        if(quantity < 1)
            throw new WrongPermterException("cannot add quantity lower then 1");
        if(price_per_unit < 1)
            throw new WrongPermterException("cannot add price lower then 1");
        if(this.products_and_quantities.containsKey(p) || this.products_and_price_per_unit.containsKey(p))
        {
            throw new BasketException("Product already in the basket - product id: "+p.getProduct_id());
        }
        this.products_and_quantities.put(p, quantity);
        this.products_and_price_per_unit.put(p, price_per_unit);
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
    public Map<Product, Double> getProducts_and_prices() {
        return products_and_price_per_unit;
    }

    public double getTotal_price() {
        double price=0;
        for (Product p: this.products_and_quantities.keySet())
        {
            double price_per_unit = this.products_and_price_per_unit.get(p);
            price += price_per_unit * this.products_and_quantities.get(p);
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void clear() {
        for(Product p : products_and_quantities.keySet()){
            products_and_quantities.remove(p);
            products_and_price_per_unit.remove(p);
        }
        HibernateUtils.merge(this);
    }
}

package Domain.StoreModule;

import Domain.StoreModule.Product.Product;
import Domain.Utils.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private Pair<Integer, Integer> basket_id; // <user_id, store_id>
    private Map<Product, Integer> products_and_quantities; //  product & quantity
    private double total_price;

    public Basket(int store_id, int buyer_id) {
        this.basket_id = new Pair<>(buyer_id, store_id);
        products_and_quantities = new HashMap<>();
        this.total_price=0;
    }

    public void changeQuantity(Product product, int quantity){
        if (quantity < 1)
            throw new IllegalArgumentException("Basket.changeQuantity: the quantity less then 1. quantity = "+quantity);
        if(!this.products_and_quantities.containsKey(product))
        {
            throw new IllegalArgumentException("Basket.changeQuantity: Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.put(product, quantity);
    }

    public boolean isEmpty(){
        return this.products_and_quantities.isEmpty();
    }

    public void removeProduct(Product product){
        if(!this.products_and_quantities.containsKey(product))
        {
            throw new IllegalArgumentException("Basket.removeProduct: Product isn't in the basket - product id: "+product.getProduct_id());
        }
        this.products_and_quantities.remove(product);
    }

    public void addProduct(Product p, int quantity) {
        if(this.products_and_quantities.containsKey(p))
        {
            throw new IllegalArgumentException("Product already in the basket - product id: "+p.getProduct_id());
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
    public int getQuantity(int product_id) {
        Product p = this.get_product_by_product_id(product_id);
        return  this.products_and_quantities.get(p);
    }
    public double getPrice(int product_id, int quantity) {
        Product p = this.get_product_by_product_id(product_id);
        return p.getPrice() * quantity;
        //TODO we return price without discount policy
    }
    public String getName(int product_id) {
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
    private Product get_product_by_product_id(int product_id) {
        for (Product product : this.products_and_quantities.keySet()){
            if (product.getProduct_id() == product_id)
                return product;
        }
        throw new IllegalArgumentException("Basket.get_product_by_product_id: " +
                "Product does not exist in the basket - basket id: "+basket_id+"  , product id: "+product_id);
    }
    public int getStore_id() {
        return basket_id.getSecond();
    }
    public int getBuyer_id() {
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





}

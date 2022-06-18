package TradingSystem.server.Domain.StoreModule.Purchase;


import javax.persistence.*;
import java.util.Map;

@Entity
public class UserPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_purchase_id;

    @ManyToMany
    @JoinTable(name = "user_purchases",
            joinColumns = {@JoinColumn(name = "user_purchase_id", referencedColumnName = "user_purchase_id")})
    @MapKeyColumn(name = "purchase_id") // the key column
    private Map<Integer, Purchase> store_id_purchase;
    private double total_price;

    // ------------------------------ constructors ------------------------------
    public UserPurchase(int user_purchase_id, Map<Integer, Purchase> store_id_purchase, double total_price) {
//        this.user_purchase_id = user_purchase_id;
        this.store_id_purchase = store_id_purchase;
        this.total_price = total_price;
//        HibernateUtils.persist(this);
    }

    public UserPurchase() {

    }

    // ------------------------------ getters ------------------------------


    public Map<Integer, Purchase> getStore_id_purchase() {
        return store_id_purchase;
    }

    // ------------------------------ setters ------------------------------


    public void setStore_id_purchase(Map<Integer, Purchase> store_id_purchase) {
        this.store_id_purchase = store_id_purchase;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    // ------------------------------ methods ------------------------------

    public boolean bought_from_store(int storeID){
        return store_id_purchase.containsKey(storeID);
    }

    public boolean bought_product(int storeID,int productID) {
        if (!bought_from_store(storeID)) return false;
        return this.store_id_purchase.get(storeID).containsProduct(productID);
    }


    public double getTotal_price() {
        return total_price;
    }

}

/*
    private void setMaps(Map<Integer,Basket> cart){
        for(Map.Entry<Integer,Basket> entry : cart.entrySet()) {
            int storeID = entry.getKey();
            Basket basket = entry.getValue();
            List<Integer> storeProductsID = basket.getProductsId();
            this.storeId_productsIDS.put(storeID, storeProductsID);
            setProductsMap(basket,storeProductsID);
        }
    }

    private void setProductsMap(Basket basket,List<Integer> productsID){
        for(int productID : productsID){
            int quantity = basket.getQuantity(productID);
            product_and_quantity.put(productID,quantity);
            product_and_totalPrice.put(productID,basket.getPrice(productID,quantity));
            product_and_name.put(productID,basket.getName(productID));
        }
    }

    public boolean bought_from_store(int storeID){
        return this.storeId_productsIDS.containsKey(storeID);
    }

    public boolean bought_product(int storeID,int productID){
        if(!bought_from_store(storeID)) return false;
        return this.storeId_productsIDS.get(storeID).contains(productID);
    }*/

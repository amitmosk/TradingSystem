package TradingSystem.server.Domain.StoreModule.Purchase;

import java.util.Map;

public class UserPurchase {
    private int purchase_id;
    private Map<Integer, Purchase> store_id_purchase;
    private double total_price;

    // ------------------------------ constructors ------------------------------
    public UserPurchase(int purchase_id, Map<Integer, Purchase> store_id_purchase, double total_price) {
        this.purchase_id = purchase_id;
        this.store_id_purchase = store_id_purchase;
        this.total_price = total_price;
    }

    // ------------------------------ getters ------------------------------


    public Map<Integer, Purchase> getStore_id_purchase() {
        return store_id_purchase;
    }

    // ------------------------------ setters ------------------------------

    public void setPurchase_id(int purchase_id) {
        this.purchase_id = purchase_id;
    }

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

    public int getPurchase_id() {
        return purchase_id;
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

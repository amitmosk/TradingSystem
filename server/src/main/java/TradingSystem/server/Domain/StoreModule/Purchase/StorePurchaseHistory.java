package TradingSystem.server.Domain.StoreModule.Purchase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StorePurchaseHistory {
    private Map<Integer, StorePurchase> purchaseID_purchases;
    private String store_name;

    // ------------------------------ constructors ------------------------------
    public StorePurchaseHistory(String store_name) {
        this.store_name = store_name;
        this.purchaseID_purchases = new ConcurrentHashMap<>();
    }

    public StorePurchaseHistory() {
    }

    // ------------------------------ getters ------------------------------


    public Map<Integer, StorePurchase> getPurchaseID_purchases() {
        return purchaseID_purchases;
    }

    public String getStore_name() {
        return store_name;
    }

    // ------------------------------ setters ------------------------------


    public void setPurchaseID_purchases(Map<Integer, StorePurchase> purchaseID_purchases) {
        this.purchaseID_purchases = purchaseID_purchases;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void insert(StorePurchase purchase)
    {
        this.purchaseID_purchases.put(purchase.getPurchase_id(), purchase);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("Store " + store_name + " Purchase History:\n");
        for (StorePurchase p : this.purchaseID_purchases.values())
        {
            ans.append(p.toString());
        }
        return ans.toString();
    }
}

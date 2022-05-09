package TradingSystem.server.Domain.StoreModule.Purchase;

import java.util.HashMap;
import java.util.Map;

public class StorePurchaseHistory {
    private Map<Integer, StorePurchase> purchaseID_purchases;
    private String store_name;

    public StorePurchaseHistory(String store_name) {
        this.store_name = store_name;
        this.purchaseID_purchases = new HashMap<>();
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

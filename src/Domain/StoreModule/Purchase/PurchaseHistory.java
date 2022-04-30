package Domain.StoreModule.Purchase;

import java.util.HashMap;
import java.util.Map;

public abstract class PurchaseHistory {
    private Map<Integer, Purchase> purchaseID_purchases;

    public PurchaseHistory() {
        this.purchaseID_purchases = new HashMap<Integer, Purchase>();
    }

    public void insert(Purchase purchase)
    {
        this.purchaseID_purchases.put(purchase.get_purchase_id(), purchase);
    }

    @Override
    public String toString() {
        String ans = "";
        for (Purchase p : this.purchaseID_purchases.values())
        {
            ans = ans + p.toString();
        }
        return "PurchaseHistory{" +
                "purchaseID_purchases=" + ans +
                '}';
    }
}

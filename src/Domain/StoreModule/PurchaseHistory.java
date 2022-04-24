package Domain.StoreModule;

import java.util.Map;

public abstract class PurchaseHistory {
    private Map<Integer, Purchase> purchaseID_purchases;

    public void insert(Purchase purchase)
    {
        this.purchaseID_purchases.put(purchase.get_purchase_id(), purchase);
    }

}

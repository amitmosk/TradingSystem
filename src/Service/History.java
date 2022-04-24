package Service;

import java.util.List;

public abstract class History {
    protected List<Purchase> historyList;

    public void addPurchase(Purchase purchase) {
        historyList.add(purchase);
    }
}

package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.UserNeverBoughtInTheStoreException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserPurchaseHistory {
    private List<UserPurchase> historyList;

    public UserPurchaseHistory() {
        this.historyList = new CopyOnWriteArrayList<>();
    }

    public void addPurchase(UserPurchase purchase) {
        historyList.add(purchase);
    }

    public boolean check_if_user_buy_from_this_store(int storeID) throws MarketException {
        for (UserPurchase p : historyList) {
            if (p.bought_from_store(storeID))
                return true;
        }
        throw new UserNeverBoughtInTheStoreException("The user has never bought from this store");
    }

    public boolean check_if_user_buy_this_product(int storeID, int productID) throws MarketException {
        for (UserPurchase p : historyList) {
            if (p.bought_product(storeID, productID))
                return true;
        }
        throw new UserNeverBoughtInTheStoreException("The user has never bought this product");
    }
}

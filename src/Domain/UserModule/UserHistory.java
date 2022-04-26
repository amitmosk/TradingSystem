package Domain.UserModule;

import java.util.ArrayList;
import java.util.List;

public class UserHistory extends History {
    protected List<UserPurchase> historyList;

    public UserHistory() {
        this.historyList = new ArrayList<>();
    }

    public void addPurchase(UserPurchase purchase) {
        historyList.add(purchase);
    }

    public boolean check_if_user_buy_from_this_store(int storeID) throws Exception {
        for(UserPurchase p : historyList){
            if(p.bought_from_store(storeID))
                return true;
        }
        throw new Exception("The user has never bought from this store");
    }

    public boolean check_if_user_buy_this_product(int storeID, int productID) throws Exception {
        for(UserPurchase p : historyList){
            if(p.bought_product(storeID,productID))
                return true;
        }
        throw new Exception("The user has never bought this product");
    }
}

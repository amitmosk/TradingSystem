package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.DAL.Repo;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.UserNeverBoughtInTheStoreException;
import net.bytebuddy.asm.Advice;

import javax.persistence.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
public class UserPurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_history_id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserPurchase> historyList;


    // ------------------------------ constructors ------------------------------
    public UserPurchaseHistory() {
        this.historyList = new CopyOnWriteArrayList<>();
    }

    // ------------------------------ getters ------------------------------

    public List<UserPurchase> getHistoryList() {
        return historyList;
    }

    // ------------------------------ setters ------------------------------

    public void setHistoryList(List<UserPurchase> historyList) {
        this.historyList = historyList;
    }

    // ------------------------------ methods ------------------------------
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

    public void setPurchase_history_id(Long purchase_history_id) {
        this.purchase_history_id = purchase_history_id;
    }

    public Long getPurchase_history_id() {
        return purchase_history_id;
    }
}

package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.DAL.HibernateUtils;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Entity
public class StorePurchaseHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_purchase_history;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StorePurchase> historyList;
    private String store_name;

    // ------------------------------ constructors ------------------------------
    public StorePurchaseHistory(String store_name) {
        this.store_name = store_name;
        this.historyList = new CopyOnWriteArrayList<>();
    }

    public StorePurchaseHistory() {
    }

    // ------------------------------ getters ------------------------------



    public List<StorePurchase> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<StorePurchase> historyList) {
        this.historyList = historyList;
    }

    public String getStore_name() {
        return store_name;
    }

    // ------------------------------ setters ------------------------------


    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void insert(StorePurchase purchase)
    {

        this.historyList.add(purchase);
    }

    @Override
    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append("Store " + store_name + " Purchase History:\n");
        for (StorePurchase p : this.historyList)
        {
            ans.append(p.toString());
        }
        return ans.toString();
    }

    public void setStore_purchase_history(Long store_purchase_history) {
        this.store_purchase_history = store_purchase_history;
    }

    public Long getStore_purchase_history() {
        return store_purchase_history;
    }

}

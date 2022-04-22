package Domain.StoreModule;

import java.util.HashMap;
import java.util.List;

public class Store {
    private int store_id;
    private int founder_id;
    private HashMap<Integer, Permission> owner_ids;
    private HashMap<Integer, Permission> manager_ids;

    private String name;
    private String foundation_date;

    private HashMap<Integer, Product> products;

    private boolean active;

    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;

    private HashMap<Integer, Purchase> purchase_history;

}

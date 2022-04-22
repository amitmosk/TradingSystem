package Domain.StoreModule;

import Domain.Utils.Pair;

import java.util.HashMap;

public class Basket {
    private Pair<Integer, Integer> basket_id; // <user_id, store_id>
    private HashMap<Integer, Product> products;
    private double total_price;

}

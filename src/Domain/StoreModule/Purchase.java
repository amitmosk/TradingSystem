package Domain.StoreModule;

import java.util.HashMap;

public class Purchase {

    private double cost;
    private String date;
    private boolean active;
    private HashMap<Integer, Product> products; // make product_bought
    private HashMap<Integer, Product> products_by_store;

}

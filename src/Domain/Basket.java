package Domain;

import java.util.List;

public class Basket {
    public boolean isEmpty() {
        return false;
    }

    public List<Integer> getProductsId() {
        return null;
    }

    public int getQuantity(int productID) {
        return 0;
    }

    public Double getPrice(int productID, int quantity) {
        return 1.1;
    }

    public String getName(int productID) {
        return "hi Eylon";
    }
}

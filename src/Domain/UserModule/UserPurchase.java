package Domain.UserModule;

import Domain.Basket;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class UserPurchase extends Purchase {
    private Map<Integer, List<Integer>> storeId_productsIDS;        // amit and tom method in basket

    public UserPurchase(Map<Integer, Basket> cart, int purchaseID) {
        this.purchase_id = purchaseID;
        this.transaction_date = LocalDateTime.now();
        this.totalPrice = calculateTotalPrice();
        this.storeId_productsIDS = new HashMap<>();
        this.product_and_quantity = new HashMap<>();
        this.product_and_totalPrice = new HashMap<>();
        this.product_and_name = new HashMap<>();
        setMaps(cart);
    }

    private void setMaps(Map<Integer,Basket> cart){
        for(Map.Entry<Integer,Basket> entry : cart.entrySet()) {
            int storeID = entry.getKey();
            Basket basket = entry.getValue();
            List<Integer> storeProductsID = basket.getProductsId();
            this.storeId_productsIDS.put(storeID, storeProductsID);
            setProductsMap(basket,storeProductsID);
        }
    }

    private void setProductsMap(Basket basket,List<Integer> productsID){
        for(int productID : productsID){
            int quantity = basket.getQuantity(productID);
            product_and_quantity.put(productID,quantity);
            product_and_totalPrice.put(productID,basket.getPrice(productID,quantity));
            product_and_name.put(productID,basket.getName(productID));
        }
    }

    public boolean bought_from_store(int storeID){
        return this.storeId_productsIDS.containsKey(storeID);
    }

    public boolean bought_product(int storeID,int productID){
        if(!bought_from_store(storeID)) return false;
        return this.storeId_productsIDS.get(storeID).contains(productID);
    }
}

// getQuantity(productID) - gets product quantity by id
// getPrice(productID,quantity) - get product price by quantity and id
// getName(productID) - get products name by id
// getProductsIdByStore(storeID) - get list of all products id

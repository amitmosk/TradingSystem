package TradingSystem.server.Domain.StoreModule.Purchase;

import TradingSystem.server.DAL.Repo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Map;

@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long purchase_id;
    private String transaction_date;

    @ElementCollection
    @CollectionTable(name = "purchases_product_and_quantity")
    @MapKeyColumn(name="product_id")
    @Column(name="quantity")
    private Map<Integer, Integer> product_and_quantity;

    @ElementCollection
    @CollectionTable(name = "purchases_product_and_totalPrice")
    @MapKeyColumn(name="product_id")
    @Column(name="totalPrice")
    private Map<Integer, Double> product_and_totalPrice;

    @ElementCollection
    @CollectionTable(name = "purchases_product_and_name")
    @MapKeyColumn(name="product_id")
    @Column(name="name")
    private Map<Integer, String> product_and_name;


    // ------------------------------ constructors ------------------------------
    public Purchase(Map<Integer, Integer> product_and_quantity,
                    Map<Integer, Double> product_and_totalPrice, Map<Integer, String> product_and_name) {
        transaction_date = LocalDate.now().toString();
        this.product_and_quantity = product_and_quantity;
        this.product_and_totalPrice = product_and_totalPrice;
        this.product_and_name = product_and_name;
    }

    public Purchase() {
    }

    // ------------------------------ getters ------------------------------

    public String getTransaction_date() {
        return transaction_date;
    }

    public Map<Integer, Integer> getProduct_and_quantity() {
        return product_and_quantity;
    }

    public Map<Integer, Double> getProduct_and_totalPrice() {
        return product_and_totalPrice;
    }

    public Map<Integer, String> getProduct_and_name() {
        return product_and_name;
    }

    // ------------------------------ setters ------------------------------


    public void setTransaction_date(String transaction_date) {
        this.transaction_date = transaction_date;
    }

    public void setProduct_and_quantity(Map<Integer, Integer> product_and_quantity) {
        this.product_and_quantity = product_and_quantity;
    }

    public void setProduct_and_totalPrice(Map<Integer, Double> product_and_totalPrice) {
        this.product_and_totalPrice = product_and_totalPrice;
    }

    public void setProduct_and_name(Map<Integer, String> product_and_name) {
        this.product_and_name = product_and_name;
    }

    public boolean containsProduct(int productID) {
        return product_and_name.containsKey(productID);
    }

    public double getTotalPrice() {
        double answer = 0;
        for (Double price : product_and_totalPrice.values())
            answer = answer + price;
        return answer;
    }

    public void setPurchase_id(Long purchase_id) {
        this.purchase_id = purchase_id;
    }

    public Long getPurchase_id() {
        return purchase_id;
    }


}

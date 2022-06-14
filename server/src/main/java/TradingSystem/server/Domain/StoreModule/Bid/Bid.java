package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.UserModule.User;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static TradingSystem.server.Domain.StoreModule.Bid.BidStatus.*;

@Entity
public class Bid implements iBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid_id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Product product;
    private int quantity;
    private double offer_price;
    private String buyer_email;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User buyer;
    private double negotiation_price;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "managersEmail_answers",
        joinColumns = {@JoinColumn(name = "bid_id", referencedColumnName = "bid_id")})
    @MapKeyColumn(name = "manager_email") // the key column
    private Map<String, BidManagerAnswer> managersEmail_answers;

    @Enumerated(EnumType.STRING)
    private BidStatus status; // 0 - waiting for answers, 1 - close & denied, 2 - close & confirm.

    public Bid(int quantity, double offer_price, List<String> managers_emails, Product product, User buyer) {
        this.status = open_waiting_for_answers;
//        this.product = new ProductInformation(product, quantity);
        this.product = product;
        this.quantity = quantity;
        this.negotiation_price = -1;
        this.offer_price = offer_price;
        this.buyer_email = buyer_email;
        this.buyer = buyer;
        this.managersEmail_answers = new HashMap<>();
        for (String manager_email : managers_emails){
            BidManagerAnswer temp = new BidManagerAnswer();
            this.managersEmail_answers.put(manager_email, temp);
        }

    }

    public Bid() {

    }

    public BidInformation get_bid_information() {
        return new BidInformation(this);
    }


    @Override
    public void add_manager_of_store(String manager_email){
        this.managersEmail_answers.put(manager_email, new BidManagerAnswer());
    }

    @Override
    public void remove_manager(String email){
        this.managersEmail_answers.remove(email);
    }

    @Override
    public void add_manager_answer(String email, boolean answer, double negotiation_price){
        if (negotiation_price > 0)
        {
            this.negotiation_price = negotiation_price;
            this.managersEmail_answers.get(email).setHas_answer(true);
            this.managersEmail_answers.get(email).setAnswer(true);
            this.status = negotiation_mode;
        }
        else
        {
            this.managersEmail_answers.get(email).setHas_answer(true);
            this.managersEmail_answers.get(email).setAnswer(answer);
            if (!answer)
                this.status = closed_denied;
            else
                this.status = this.update_status();
        }

    }

    private BidStatus update_status() {
        if (this.status == closed_denied)
            return closed_denied;
        if (this.status == negotiation_mode)
            return negotiation_mode;
        for (BidManagerAnswer bid_answer : this.managersEmail_answers.values()){
            if (!bid_answer.get_has_answer())
                return open_waiting_for_answers;
        }
        return closed_confirm;
    }

    @Override
    public BidStatus get_status(){
        return this.status;
    }

    public double get_offer_price(){
        if (this.status == negotiation_mode)
            return this.negotiation_price;
        else
            return this.offer_price;
    }

    public Product get_product(){
        return this.product;
    }

    public String get_buyer_email() {
        return this.buyer_email;
    }

    public User get_buyer() {
        return buyer;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setBid_id(Long bid_id) {
        this.bid_id = bid_id;
    }

    public Long getBid_id() {
        return bid_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getOffer_price() {
        return offer_price;
    }

    public void setOffer_price(double offer_price) {
        this.offer_price = offer_price;
    }

    public String getBuyer_email() {
        return buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
        this.buyer_email = buyer_email;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public double getNegotiation_price() {
        return negotiation_price;
    }

    public void setNegotiation_price(double negotiation_price) {
        this.negotiation_price = negotiation_price;
    }

    public Map<String, BidManagerAnswer> getManagersEmail_answers() {
        return managersEmail_answers;
    }

    public void setManagersEmail_answers(Map<String, BidManagerAnswer> managersEmail_answers) {
        this.managersEmail_answers = managersEmail_answers;
    }

    public BidStatus getStatus() {
        return status;
    }

    public void setStatus(BidStatus status) {
        this.status = status;
    }
}

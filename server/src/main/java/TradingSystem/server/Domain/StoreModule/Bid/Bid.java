package TradingSystem.server.Domain.StoreModule.Bid;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.StoreModule.Appointment;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.StorePermission;
import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Service.NotificationHandler;

import javax.naming.NoPermissionException;
import javax.persistence.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static TradingSystem.server.Domain.StoreModule.Bid.BidStatus.*;

@Entity
public class Bid implements iBid {
    @Id
    public int bid_id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Product product;
    private int quantity;
    private double offer_price;

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

    public Bid(int bid_id, int quantity, double offer_price, List<String> managers_emails, Product product, User buyer,AssignUser store_founder,  Map<AssignUser,Appointment> stuff_and_appoitments) {
        this.status = open_waiting_for_answers;
//        this.product = new ProductInformation(product, quantity);
        this.bid_id = bid_id;
        this.product = product;
        this.quantity = quantity;
        this.negotiation_price = -1;
        this.offer_price = offer_price;
        this.buyer = buyer;
        this.managersEmail_answers = new HashMap<>();
        // Create new answer for every manager that should answer the bid
        boolean has_permission=false;
        boolean has_permission_nego=false;
        for (String manager_email : managers_emails){
            BidManagerAnswer answer;
            if(manager_email.equals(store_founder.getEmail()))
            {
                answer = new BidManagerAnswer(true, true);
            }
            else {
                for (Appointment appointment : stuff_and_appoitments.values()) {
                    if (appointment.has_permission(StorePermission.answer_bid_offer)) {
                        has_permission = true;
                    }
                    if (appointment.has_permission(StorePermission.answer_bid_offer_negotiate)) {
                        has_permission_nego = true;
                    }


                }
                answer = new BidManagerAnswer(has_permission, has_permission_nego);
            }

            this.managersEmail_answers.put(manager_email, answer);
        }
//        HibernateUtils.persist(this);

    }

    public Bid() {

    }

    public BidInformation get_bid_information(Integer id) throws MarketException {
        return new BidInformation(this, id);
    }


    @Override
    public void add_manager_of_store(String manager_email, boolean owner) throws MarketException {
        this.managersEmail_answers.put(manager_email, new BidManagerAnswer(owner, owner));
        this.status = update_status();
        if(status == closed_confirm)
        {
            this.buyer.add_notification("Your bid is confirm by the store managers.");
        }
//        HibernateUtils.merge(this);
    }


    @Override
    public void remove_manager(String email) throws MarketException {
        this.managersEmail_answers.remove(email);
        this.status = update_status();
        if(status == closed_confirm)
        {
            this.buyer.add_notification("Your bid is confirm by the store managers.");
        }

//        HibernateUtils.merge(this);
    }

    @Override
    public void add_manager_answer(String email, boolean answer, double negotiation_price, User buyer) throws NoPermissionException, MarketException {
        if (!this.managersEmail_answers.containsKey(email)){
            throw new NoPermissionException(email + "is no a manager in the store");
        }
        if (this.status == closed_denied || this.status == closed_confirm){
            throw new MarketException("cant answer closed bid");
        }
        if (negotiation_price > 0)
        {
            if (!answer){
                throw new IllegalArgumentException("forbidden combination of negotiation price & false answer");
            }
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
            {
                this.status = closed_denied;
            }

            else
                this.status = this.update_status();
        }

        //        HibernateUtils.merge(this);
    }

    private BidStatus update_status() throws MarketException {

        if (this.status == closed_denied)
        {
            return closed_denied;
        }

        if (this.status == negotiation_mode)
            return negotiation_mode;
        for (String manger_email : this.managersEmail_answers.keySet()){
            BidManagerAnswer bid_answer = this.managersEmail_answers.get(manger_email);
            if (!bid_answer.get_has_answer() && (bid_answer.isHas_permission() || bid_answer.isHas_permission_nego() ))
                return open_waiting_for_answers;
        }
//        HibernateUtils.merge(this);
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

    public int getBid_id() {
        return bid_id;
    }

    public void setBid_id(int bid_id) {
        this.bid_id = bid_id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
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

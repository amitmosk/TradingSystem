package TradingSystem.server.Domain.StoreModule.Store;

import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.UserModule.User;

import java.util.HashMap;
import java.util.List;

import static TradingSystem.server.Domain.StoreModule.Store.BidStatus.*;

public class Bid {

    private ProductInformation product;
    private double offer_price;
    private String buyer_email;
    private User buyer;
    private double negotiation_price;
    private HashMap<String, BidManagerAnswer> managersEmail_answers;
    private BidStatus status; // 0 - waiting for answers, 1 - close & denied, 2 - close & confirm.

    public Bid(String buyer_email, int quantity, double offer_price, List<String> managers_emails, Product product, User buyer) {
        this.status = open_waiting_for_answers;
        this.product = new ProductInformation(product, quantity);
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


    public void add_manager_of_store(String manager_email){
        this.managersEmail_answers.put(manager_email, new BidManagerAnswer());
    }

    public void remove_manager(String email){
        this.managersEmail_answers.remove(email);
    }

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

    public BidStatus get_status(){
        return this.status;
    }

    public double get_offer_price(){
        if (this.status == negotiation_mode)
            return this.negotiation_price;
        else
            return this.offer_price;
    }

    public ProductInformation get_product_information(){
        return this.product;
    }

    public String get_buyer_email() {
        return this.buyer_email;
    }

    public User get_buyer() {
        return buyer;
    }
}

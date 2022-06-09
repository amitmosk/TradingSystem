package TradingSystem.server.Domain.StoreModule.Bid;

public interface iBid {
    void add_manager_of_store(String manager_email);

    void remove_manager(String email);

    void add_manager_answer(String email, boolean answer, double negotiation_price);

    BidStatus get_status();
}

package TradingSystem.server.Domain.StoreModule.Bid;

public enum BidStatus {
    open_waiting_for_answers,
    closed_denied,
    closed_confirm,
    negotiation_mode,
}

package TradingSystem.server.Domain.StoreModule.Store;

public class BidManagerAnswer {
    private boolean has_answer;
    private boolean answer;
    private double negotiation_price;

    public boolean get_has_answer() {
        return has_answer;
    }

    public void setHas_answer(boolean has_answer) {
        this.has_answer = has_answer;
    }

    public boolean get_answer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
    }

    public double getNegotiation_price() {
        return negotiation_price;
    }

    public void setNegotiation_price(double negotiation_price) {
        this.negotiation_price = negotiation_price;
    }
}

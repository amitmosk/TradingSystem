package TradingSystem.server.Domain.StoreModule.Bid;

public class BidManagerAnswer {
    private boolean has_answer;
    private boolean answer;

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

}

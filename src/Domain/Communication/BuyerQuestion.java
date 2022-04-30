package Domain.Communication;

public class BuyerQuestion extends Question {
    private final String buyer_email;
    private final int store_id;

    public BuyerQuestion(int question_id, String message, String buyer_email, int store_id) {
        super(question_id, message);
        this.buyer_email = buyer_email;
        this.store_id = store_id;
    }

    public int get_store_id() {
        return store_id;
    }
}

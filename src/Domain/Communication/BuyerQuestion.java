package Domain.Communication;

public class BuyerQuestion extends Question {
    private final String buyer_email;
    private final int store_id;

    // buyer -> store
    public BuyerQuestion(int question_id, String message, String buyer_email, int store_id) {
        super(question_id, message);
        this.buyer_email = buyer_email;
        this.store_id = store_id;
    }

    public int get_store_id() {
        return store_id;
    }

    @Override
    public String toString() {
        return "BuyerQuestion{" +
                "buyer_email='" + buyer_email + '\'' +
                ", store_id=" + store_id +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", has_answer=" + has_answer +
                '}';
    }
}

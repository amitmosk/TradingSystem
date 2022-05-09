package TradingSystem.server.Domain.Communication;


public class UserQuestion extends Question {
    private final String buyer_email;

    // user -> admin
    public UserQuestion(int question_id, String message, String buyer_email) {
        super(question_id, message);
        this.buyer_email = buyer_email;
    }

    @Override
    public String toString() {
        return "UserQuestion{" +
                "buyer_email='" + buyer_email + '\'' +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", has_answer=" + has_answer +
                '}';
    }
}
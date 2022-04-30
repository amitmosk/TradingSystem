package Domain.Communication;

public class UserQuestion extends Question {
    private final String buyer_email;

    public UserQuestion(int question_id, String message, String buyer_email) {
        super(question_id, message);
        this.buyer_email = buyer_email;
    }
}
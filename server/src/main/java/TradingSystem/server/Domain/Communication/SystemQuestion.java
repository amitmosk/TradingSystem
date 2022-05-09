package TradingSystem.server.Domain.Communication;


public class SystemQuestion extends Question {
    private final String user_email;

    // system -> user
    public SystemQuestion(int question_id, String message, String user_email) {
        super(question_id, message);
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "SystemQuestion{" +
                "user_email='" + user_email + '\'' +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", has_answer=" + has_answer +
                '}';
    }
}

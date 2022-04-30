package Domain.Communication;

public class SystemQuestion extends Question {
    private final String user_email;

    public SystemQuestion(int question_id, String message, String user_email) {
        super(question_id, message);
        this.user_email = user_email;
    }
}

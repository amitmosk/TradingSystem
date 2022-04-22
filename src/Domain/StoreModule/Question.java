package Domain.StoreModule;

public class Question {
    private int question_id;
    private int store_id;
    private int user_id;
    private String message_date;
    private String answer_date;
    private String message;
    private String answer;
    private boolean has_answer;

    public void setAnswer(String answer) {
        this.answer = answer;
        this.has_answer = true;
//        this.answer_date = date.now()
    }
}

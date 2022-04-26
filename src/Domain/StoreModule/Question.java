package Domain.StoreModule;

import java.time.LocalDate;

public class Question {
    private int question_id;
    private int store_id;
    private String user_email;
    private LocalDate message_date;
    private LocalDate answer_date;
    private String message;
    private String answer;
    private boolean has_answer;

    public Question(int question_id, int store_id, String user_email, String message) {
        this.question_id = question_id;
        this.store_id = store_id;
        this.user_email = user_email;
        this.message = message;
        this.has_answer = false;
        this.message_date = LocalDate.now();

    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.has_answer = true;
        this.answer_date = LocalDate.now();
    }


}

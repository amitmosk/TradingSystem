package Domain.Communication;

import java.time.LocalDate;

public abstract class Question {
    private final int question_id;
    public LocalDate message_date;
    public LocalDate answer_date;
    private String message;
    private String answer;
    private boolean has_answer;

    public Question(int question_id, String message) {
        this.question_id = question_id;
        this.message = message;
        this.has_answer = false;
        this.message_date = LocalDate.now();

    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.has_answer = true;
        this.answer_date = LocalDate.now();
    }

    public String toString() {
        return "Question{" +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", has_answer=" + has_answer +
                '}';
    }

}

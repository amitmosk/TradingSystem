package TradingSystem.server.Domain.Questions;

import TradingSystem.server.Domain.UserModule.AssignUser;

import java.time.LocalDate;

public abstract class Question {
    protected final int question_id;
    protected final String message_date;
    protected String answer_date;
    protected String message;
    protected String answer;
    protected boolean has_answer;
    protected final AssignUser sender; // assign user

    public Question(int question_id, String message, AssignUser sender) {
        this.question_id = question_id;
        this.sender = sender;
        this.message = message;
        this.has_answer = false;
        this.message_date = LocalDate.now().toString();

    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.has_answer = true;
        this.answer_date = LocalDate.now().toString();
    }

    public String getMessage() {
        return message;
    }

    public String getAnswer() {
        return answer;
    }

    public AssignUser getSender() {
        return sender;
    }
}

package TradingSystem.server.Domain.Questions;

import TradingSystem.server.Domain.UserModule.AssignUser;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Question {
    @Id
//    //generated
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int question_id;
    protected String message_date;
    protected String answer_date;
    protected String message;
    protected String answer;
    protected boolean has_answer;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    protected AssignUser sender; // assign user

    // ---------------------- Constructor ---------------------------------------
    public Question() {
    }

    public Question(int question_id, String message, AssignUser sender) {
        this.question_id = question_id;
        this.sender = sender;
        this.message = message;
        this.has_answer = false;
        this.message_date = LocalDate.now().toString();
    }

    // --------------------- getters --------------------------------------------
    public int getQuestion_id() {
        return question_id;
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

    public String getMessage_date() {
        return message_date;
    }

    public String getAnswer_date() {
        return answer_date;
    }

    public boolean isHas_answer() {
        return has_answer;
    }

    // -------------------------------- setters -------------------------------------
    public void setAnswer_date(String answer_date) {
        this.answer_date = answer_date;
    }

    public void setHas_answer(boolean has_answer) {
        this.has_answer = has_answer;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.has_answer = true;
        this.answer_date = LocalDate.now().toString();
//        HibernateUtils.merge(this);
    }

    public abstract String toString_for_user();

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public void setSender(AssignUser sender) {
        this.sender = sender;
    }



}

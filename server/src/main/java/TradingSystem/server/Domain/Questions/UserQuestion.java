package TradingSystem.server.Domain.Questions;


import TradingSystem.server.Domain.UserModule.AssignUser;

import javax.persistence.Entity;

@Entity
public class UserQuestion extends Question {

    // user -> admin
    public UserQuestion(int question_id, String message, AssignUser sender) {
        super(question_id, message, sender);
    }
    public UserQuestion() {

    }

    @Override
    public String toString() {
        String ans = has_answer ? answer : "No Answer Yet";
        String has_ans = has_answer ? "Yes" : "No";
        return "UserQuestion{" +
                "buyer_email='" + sender.get_user_email() + '\'' +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message=" + message +
                ", answer=" + ans +
                ", has_answer=" + has_ans +
                '}';
    }


    @Override
    public String toString_for_user() {
        return "Question to admin :" +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message=" + message +
                ", answer=" + answer +
                '}';
    }
}

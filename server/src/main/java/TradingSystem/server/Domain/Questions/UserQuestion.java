package TradingSystem.server.Domain.Questions;


import TradingSystem.server.Domain.UserModule.AssignUser;

public class UserQuestion extends Question {

    // user -> admin
    public UserQuestion(int question_id, String message, AssignUser sender) {
        super(question_id, message, sender);
    }

    @Override
    public String toString() {
        return "UserQuestion{" +
                "buyer_email='" + sender.get_user_email() + '\'' +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message=" + message +
                ", answer=" + answer +
                ", has_answer=" + has_answer +
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

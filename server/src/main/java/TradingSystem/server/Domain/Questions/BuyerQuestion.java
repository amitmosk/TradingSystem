package TradingSystem.server.Domain.Questions;

import TradingSystem.server.Domain.UserModule.AssignUser;

public class BuyerQuestion extends Question {
    private final int store_id;

    // buyer -> store
    public BuyerQuestion(int question_id, String message, AssignUser sender, int store_id) {
        super(question_id, message, sender);
        this.store_id = store_id;
    }

    public int get_store_id() {
        return store_id;
    }

    public AssignUser getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "BuyerQuestion{" +
                "buyer_email='" + sender.get_user_email() + '\'' +
                ", store_id=" + store_id +
                ", question_id=" + question_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                ", has_answer=" + has_answer +
                '}';
    }

    @Override
    public String toString_for_user() {
        return "Question to store :" + store_id +
                ", message_date=" + message_date +
                ", answer_date=" + answer_date +
                ", message='" + message + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }


}

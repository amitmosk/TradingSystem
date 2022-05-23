package TradingSystem.server.Domain.Questions;

import TradingSystem.server.Domain.UserModule.AssignUser;
import TradingSystem.server.Domain.Utils.Exception.ObjectDoesntExsitException;

import java.util.List;

public interface iQuestionController {
    void add_buyer_question(String message, AssignUser sender, int store_id);

    void add_user_question(String message, AssignUser sender);

    void answer_buyer_question(int question_id, String answer) throws ObjectDoesntExsitException;

    void answer_user_question(int question_id, String answer) throws ObjectDoesntExsitException;
    
    List<String> get_all_user_questions(String user_email);

    List<String> view_buyers_to_store_questions(int store_id);

    List<String> view_users_to_admin_questions();
}

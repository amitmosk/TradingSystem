package Domain.Communication;

import java.util.List;

public interface iQuestionHandler {
    void add_buyer_question(String message, String buyer_email, int store_id);

    void add_user_question(String message, String buyer_email);

    void add_system_question(String message, String user_email);

    void answer_buyer_question(int question_id, String answer);

    void answer_user_question(int question_id, String answer);

    List<String> view_buyers_to_store_questions(int store_id);

    List<String> view_users_to_admin_questions();
}
